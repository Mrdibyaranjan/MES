package com.smes.trans.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smes.admin.model.SeqIdDetails;
import com.smes.admin.service.impl.CommonService;
import com.smes.masters.controller.PSNHdrMasterController;
import com.smes.masters.model.PSNProductMasterModel;
import com.smes.masters.model.SpectroLabChemMaster;
import com.smes.masters.service.impl.PSNProductMasterService;
import com.smes.reports.model.EOFHeatLogRpt;
import com.smes.trans.model.EOFCrewDetails;
import com.smes.trans.model.EofElectrodeUsageModel;
import com.smes.trans.model.EofHeatDetails;
import com.smes.trans.model.HeatConsMaterialsDetails;
import com.smes.trans.model.HeatConsMaterialsDetailsLog;
import com.smes.trans.model.HeatConsScrapMtrlDetails;
import com.smes.trans.model.ScrapBucketDetails;
import com.smes.trans.model.ScrapBucketDetailsLog;
import com.smes.trans.model.ScrapBucketHdr;
import com.smes.trans.model.SteelLadleLifeModel;
import com.smes.trans.model.TransDelayDetailsModel;
import com.smes.trans.model.TransDelayEntryHeader;
import com.smes.trans.service.impl.EofProductionService;
import com.smes.trans.service.impl.HeatProceeEventService;
import com.smes.trans.service.impl.ScrapEntryService;
import com.smes.trans.service.impl.TransDelayEntryDtlsService;
import com.smes.trans.service.impl.TransDelayEntryHeaderService;
import com.smes.util.CommonCombo;
import com.smes.util.CommonProcessEvent;
import com.smes.util.Constants;
import com.smes.util.DelayEntryDTO;
import com.smes.util.RestResponse;
import com.smes.util.SpectroLabUtil;
import com.smes.util.ValidationResultsModel;


@Controller
@RequestMapping("EOFproduction")
public class EOFProductionController {

	private static final Logger logger = Logger.getLogger(EOFProductionController.class);

	@Autowired
	private EofProductionService eofProductionService;	
	
	@Autowired
	private HeatProceeEventService heatProcessEventService;

	@Autowired
	private CommonService commonService;

	@Autowired
	TransDelayEntryHeaderService transDelayEntryHeaderService;

	@Autowired
	TransDelayEntryDtlsService transDelayEntryDtlsService;
	
	@Autowired
	ScrapEntryService scrapEntryServ;
	
	@Autowired
	private PSNProductMasterService psnProdMstrService;
	
	@RequestMapping("/EOFProductionView")
	public ModelAndView getEOFProductionView() {
		logger.info(EOFProductionController.class + "...getHeatPlanDetails()");
		ModelAndView model = new ModelAndView("transaction/EOFProduction");
		return model;
	}

	
	@RequestMapping(value = "/getNewHeatNo", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody EofHeatDetails getNewHeatNo(@RequestParam String sub_unit,HttpSession session) {
		logger.info(EOFProductionController.class + "...getSeqIdByQuery()");
		String retHeatNo = null, seqStr, unitStr = null;	
		Integer seqNo, origSeqNo;
		EofHeatDetails obj = new EofHeatDetails();
		
		try {
		List<SeqIdDetails> seqLi = eofProductionService.getSeqIdByQuery(sub_unit);
		seqStr = seqLi.get(0).getTable_name();
		seqNo = seqLi.get(0).getNext_val();
		origSeqNo = seqLi.get(0).getNext_val();
        while(seqNo != 0){
        	seqNo /= 10;
            //++digits;
        }
        String format = String.format("%%0%dd", Constants.HEAT_NO_GEN_SEQ_DIGITS);
        String paddedSeq = String.format(format, origSeqNo);
        if(sub_unit.equals(Constants.SUB_UNIT_EAF1)) 
        	unitStr = Constants.HEAT_NO_GEN_EAF1;
        else if (sub_unit.equals(Constants.SUB_UNIT_EAF2)) 
        	unitStr = Constants.HEAT_NO_GEN_EAF2;
        
		retHeatNo = Constants.HEAT_NO_GEN_PLANT_LOC+seqStr+paddedSeq+unitStr;
		obj.setHeat_id(retHeatNo);
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(EOFProductionController.class + " Inside getSeqIdByQuery Exception..", e);
		}
		return obj;
	}

	@RequestMapping(value = "/EofHeatSave", method = RequestMethod.POST, headers = { "Content-type=application/json" })
	public @ResponseBody RestResponse eofHeatProductionSave(@RequestBody EofHeatDetails eofHeatDetails,
			@RequestParam String production_date, @RequestParam String hm_charge_at,
			@RequestParam String scrap_charge_at, @RequestParam String tap_start_at, @RequestParam String tap_close_at,
			@RequestParam Integer hm_ldl_version, @RequestParam Integer plan_line_version, HttpSession session) {
		String result = "";
		try {

			logger.info(EOFProductionController.class);

			result = eofProductionService.eofHeatProductionSaveAll(eofHeatDetails, production_date, hm_charge_at,
					scrap_charge_at, tap_start_at, tap_close_at,
					Integer.parseInt(session.getAttribute("USER_APP_ID").toString()), hm_ldl_version,
					plan_line_version);
			
			if (result.equals(Constants.SAVE)) {
			   return new RestResponse("SUCCESS", result);

			} else {
				return new RestResponse("FAILURE", result);
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(EOFProductionController.class + " Inside EOFProductionSaveOrUpdate Exception..", e);
			return new RestResponse("FAILURE", e.getMessage());
		}

	}

	@RequestMapping(value = "/eofreceivehmStatusUpdate", method = RequestMethod.POST, headers = { "Content-type=application/json" })
	public @ResponseBody RestResponse  eofreceivehmStatusUpdate(@RequestParam String hmRecvId,HttpSession session) {
		
		try{
			logger.info(EOFProductionController.class);
			String result = "";
			Long recvId=Long.parseLong(hmRecvId);
			if(recvId>0)
			{	
				result=eofProductionService.eofreceivehmStatusUpdate(recvId);
				return new RestResponse("SUCCESS", result);
				
			}
			else{
				return new RestResponse("FAILURE", result);
			}
		}catch (Exception e) {
			//e.printStackTrace();
			logger.error(EOFProductionController.class+" Inside eofreceivehmStatusUpdate Exception..", e);
			return new RestResponse("FAILURE", e.getMessage());
		}
		
	}


	@RequestMapping(value = "/getEOFHeatDetails", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody EofHeatDetails getEOFHeatDetails(Integer trns_si_no) {
		logger.info(PSNHdrMasterController.class + "...getEOFHeatDetails()");
		// EofHeatDetails eofHeatDetails= new EofHeatDetails();

		if (trns_si_no != null && trns_si_no != 0) {

			return heatProcessEventService.getEofHeatDtlsById(trns_si_no);
		} else {
			return null;
		}
	}

	@RequestMapping(value = "/getEOFHeatFormDtlsById", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody EofHeatDetails getEOFHeatFormDtlsById(Integer trns_si_no) {
		logger.info(PSNHdrMasterController.class + "...getEOFHeatDetails()");

		if (trns_si_no != null && trns_si_no != 0) {			
			EofHeatDetails retObj;
			retObj = heatProcessEventService.getEOFHeatDtlsFormByID(trns_si_no);
			List<PSNProductMasterModel> psnProdLi = psnProdMstrService.getPSNProductMstrDtls(retObj.getAim_psn());
			retObj.setPsn_route(psnProdLi.get(0).getLkpProcRoutMstrMdl().getLookup_value());
			return retObj;
		} else {
			return null;
		}
	}

	@RequestMapping(value = "/getEOFHeatEventDetails", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<CommonProcessEvent> getEOFHeatEventDetails(String Heat_no, Integer unit_id,
			Integer heat_counter) {
		logger.info(PSNHdrMasterController.class + "...getEOFHeatDetails()");

		if (Heat_no != null && Heat_no != "") {

			return commonService.getEventDetails(Heat_no, unit_id, heat_counter);
		} else {
			return null;
		}
	}

	@RequestMapping(value = "/EofCrewDetailsSave", method = RequestMethod.POST, headers = {
			"Content-type=application/json" })
	public @ResponseBody RestResponse EofCrewDetailsSave(@RequestBody EOFCrewDetails eofCrewDetails,
			HttpSession session, @RequestParam String user_role_id) {

		try {
			logger.info(EOFProductionController.class);
			String result = "";

			result = eofProductionService.eofCrewDetailsSave(eofCrewDetails, user_role_id,
					Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));

			if (result.equals(Constants.SAVE)) {
				return new RestResponse("SUCCESS", result);

			} else {
				return new RestResponse("FAILURE", result);
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(EOFProductionController.class + " Inside EofCrewDetailsSave Exception..", e);
			return new RestResponse("FAILURE", e.getMessage());
		}

	}

	@RequestMapping(value = "/getEOFCrewDtlsbyHeatNo", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<EOFCrewDetails> getEOFCrewDtlsbyHeatNo(String Heat_no, Integer unit_id,
			Integer heat_counter) {
		logger.info(PSNHdrMasterController.class + "...getEOFCrewDtlsbyHeatNo()");

		if (Heat_no != null && Heat_no != "") {

			return eofProductionService.getCrewDetailsByHeatNo(Heat_no, unit_id, heat_counter);
		} else {
			return null;
		}
	}

	@RequestMapping(value = "/EofLadleMix", method = RequestMethod.GET, headers = { "Content-type=application/json" })
	public @ResponseBody RestResponse EofLadleMix(HttpSession session) {

		try {
			logger.info(EOFProductionController.class);

			Integer MixSeqId = commonService
					.getLookupIdByQuery("select next_val from SeqIdDetails where col_key='MIXSeqId'");
			return new RestResponse("SUCCESS", "MIX" + MixSeqId.toString());

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(EOFProductionController.class + " Inside EofLadleMix Exception..", e);
			return new RestResponse("FAILURE", e.getMessage());
		}

	}

	@RequestMapping(value = "/getHeatsWaitingForLrfProcess", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody HashSet<EofHeatDetails> getLRFHeatDetailsByStatus(@RequestParam("CURRENT_UNIT") String cunit,
			@RequestParam("UNIT_PROCESS_STATUS") String pstatus) {
		logger.info(EOFProductionController.class + "...getLRFHeatDetails()");
		// Integer unit_id=commonService.getLookupIdByQuery("select unit_id from
		// UnitMasterModel where unit_name='"+cunit+"' and record_status=1");
		// Integer pstatus_id=commonService.getLookupIdByQuery("select unit_id from
		// UnitMasterModel where unit_name='"+cunit+"' and record_status=1");
		HashSet<EofHeatDetails> eofset = eofProductionService.getLRFHeatDetailsByStatus(cunit, pstatus);

		return eofset;

	}

	@RequestMapping(value = "/getConsumedBucketsByHeat", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<CommonCombo> getConsumedBuckets(@RequestParam Integer heat_transId) {
		logger.info(EOFProductionController.class + "...getConsumedBuckets()");
		return eofProductionService.getConsumedBuckets(heat_transId);
	}

	@RequestMapping(value = "/getConsumedBucketsDtlsByHeat", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<HeatConsScrapMtrlDetails> getConsumedBucketsDtls(@RequestParam Integer heat_transId) {
		logger.info(EOFProductionController.class + "...getConsumedBucketsDtls()");
		return eofProductionService.getConsumedBucketsDtls(heat_transId);
	}

	@RequestMapping(value = "/checkScrapEntryByTransId", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody boolean checkScrapEntryByTransId(@RequestParam Integer transId) {
		logger.info(EOFProductionController.class + "...checkScrapEntryByTransId()");
		return eofProductionService.checkScrapEntryByTransId(transId);
	}

	@RequestMapping(value = "/checkEOFValidations", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<ValidationResultsModel> checkEOFValidations(@RequestParam Integer eof_tranId) {
		logger.info(EOFProductionController.class + "...checkEOFValidations()");
		return eofProductionService.checkEOFValidations(eof_tranId);
	}

	@RequestMapping(value = "/getLaddlePartsLifeDet", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<SteelLadleLifeModel> getLaddleLifeDet(@RequestParam Integer steelLadleNo) {
		logger.info(EOFProductionController.class + "...getLaddleLifeDet()");
		return eofProductionService.getLaddleLifeDet(steelLadleNo);
	}

	@RequestMapping(value = "/updatePartLifeBySiNo", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody RestResponse updatePartLifeBySiNo(HttpSession session, @RequestParam Integer ladleLifeSiNo) {
		logger.info(EOFProductionController.class + "...getLaddleLifeDet()");
		Integer life_cntr = 0;

		Integer result = eofProductionService.updatePartLifeBySiNo(ladleLifeSiNo, life_cntr,
				Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));

		if (result > 0) {
			return new RestResponse("SUCCESS", Constants.UPDATE);

		} else {
			return new RestResponse("FAILURE", Constants.UPDATE_FAIL);
		}

	}

	@RequestMapping(value = "/EOFSampNoGen", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody RestResponse EOFSampNoGen(@RequestParam String heat_id) {
		logger.info(EOFProductionController.class + "...EOFSampNoGen()");
		try {

			String sample_no = eofProductionService.getSampleNoforHmChem(heat_id);
			return new RestResponse("SUCCESS", sample_no.toString());

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(EOFProductionController.class + " Inside EOFSampNoGen Exception..", e);
			return new RestResponse("FAILURE", e.getMessage());
		}
	}

	@RequestMapping(value = "/activityDelayMstrBySubunit", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<DelayEntryDTO> getdelayMasterBySubUnit(
			@RequestParam("sub_unit_id") Integer sub_unit_id, @RequestParam("trns_si_no") Integer trns_si_no) {
		// eofProductionService.getEofDelayEntriesBySubUnitAndHeat(sub_unit_id,trns_si_no);

		return eofProductionService.getEofDelayEntriesBySubUnitAndHeat(sub_unit_id, trns_si_no);// activityDlyMstrServie.getAllActivityDetalMasterBySubUnit(sub_unit_id);
	}
	@RequestMapping(value = "/presentActivityDelayMstrBySubunit", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<TransDelayEntryHeader> getdelayMasterBySubUnitWithoutComparision(
			@RequestParam("sub_unit_id") Integer sub_unit_id, @RequestParam("trns_si_no") Integer trns_si_no) {
		// eofProductionService.getEofDelayEntriesBySubUnitAndHeat(sub_unit_id,trns_si_no);

		return eofProductionService.getDelayDetailsHdrWithSubUnitAndHeat(sub_unit_id, trns_si_no);// activityDlyMstrServie.getAllActivityDetalMasterBySubUnit(sub_unit_id);
	}

	@RequestMapping(value = "/TransDelaySave", method = RequestMethod.POST, headers = {
			"Content-type=application/json" })
	public @ResponseBody RestResponse TransDelaySave(@RequestParam("heat_id") String heat_id,
			@RequestBody List<DelayEntryDTO> transDetails, HttpSession session) {
		try {
			EofHeatDetails HeatDetails = eofProductionService.getEOFHeatDetailsByHeatNo(heat_id);
			for (int i=0;i<transDetails.size();i++) {
				DelayEntryDTO eoFdelayEntryDTO = transDetails.get(i);
				if (eoFdelayEntryDTO.getTransDelayEntryhdr() == null) {// saving data
					TransDelayEntryHeader newEntry = new TransDelayEntryHeader();
					newEntry.setTrns_delay_entry_hdr_id(null);
					newEntry.setActivity_delay_id(eoFdelayEntryDTO.getActivity_master().getActivity_delay_id());
					newEntry.setActivity_start_time(eoFdelayEntryDTO.getStart_time());
					newEntry.setActivity_end_time(eoFdelayEntryDTO.getEnd_time());
					if(eoFdelayEntryDTO.getDuration()!=null) {
					newEntry.setActivity_duration(eoFdelayEntryDTO.getDuration().intValue());
					}
					if (eoFdelayEntryDTO.getDelay() != null) {
						newEntry.setTotal_delay(eoFdelayEntryDTO.getDelay().intValue());
					}
					newEntry.setTrans_heat_id(HeatDetails.getTrns_si_no());
					newEntry.setCorrective_action(eoFdelayEntryDTO.getCorrective_action());
					newEntry.setCreatedDateTime(new Date());
					newEntry.setCreatedBy(Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
					transDelayEntryHeaderService.saveTransDelayEntryHeader(newEntry);
				} else {// updating data
					try {
						TransDelayEntryHeader updatEntry = eoFdelayEntryDTO.getTransDelayEntryhdr();
						// updatEntry.setActivity_delay_id(eoFdelayEntryDTO.getActivity_master().getActivity_delay_id());
						updatEntry.setActivity_start_time(eoFdelayEntryDTO.getStart_time());
						updatEntry.setActivity_end_time(eoFdelayEntryDTO.getEnd_time());
						updatEntry.setActivity_duration(eoFdelayEntryDTO.getDuration().intValue());
						if (eoFdelayEntryDTO.getDelay() != null) {
							updatEntry.setTotal_delay(eoFdelayEntryDTO.getDelay().intValue());
						}
						// updatEntry.setTrans_heat_id(HeatDetails.getTrns_si_no());
						updatEntry.setCorrective_action(eoFdelayEntryDTO.getCorrective_action());
						updatEntry.setUpdatedDateTime(new Date());
						updatEntry.setUpdatedBy(Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
						String result = transDelayEntryHeaderService.updateTransDelayEntryHeader(updatEntry);
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
				
			}
			return new RestResponse("SUCESS", "Data Saved Successfully..");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(EOFProductionController.class + " Inside SaveDelayDetailsWithHdr Exception..", e);
			return new RestResponse("FAILURE", e.getMessage());
		}

			
	}

	@RequestMapping(value = "/getDelayDtlsByDelayHdr", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<TransDelayDetailsModel> getdelayDtls(
			@RequestParam("trans_delay_entry_hdr_id") Integer trans_delay_entry_hdr_id) {

		return transDelayEntryDtlsService.getTransDelayDtlsByDelayDdrId(trans_delay_entry_hdr_id);
	}

	@RequestMapping(value = "/TransDelayDtlsSave", method = RequestMethod.POST)
	public @ResponseBody RestResponse SaveDelayDetailsWithHdr(@ModelAttribute TransDelayDetailsModel transdelayMstr,
			@RequestParam("trans_delay_entry_hdr_id") Integer trans_delay_entry_hdr_id, HttpSession session) {
		try {
			String result = "";
			transdelayMstr.setTrans_delay_dtl_id(null);
			transdelayMstr.setDelay_entry_hdr_id(trans_delay_entry_hdr_id);
			transdelayMstr.setCreatedDateTime(new Date());
			transdelayMstr.setCreatedBy(Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
			result = transDelayEntryDtlsService.saveTransDelayEntryDtls(transdelayMstr);
			if (result.equals(Constants.SAVE)) {
				return new RestResponse("SUCCESS", result);
			} else {
				return new RestResponse("FAILURE", result);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(EOFProductionController.class + " Inside SaveDelayDetailsWithHdr Exception..", e);
			return new RestResponse("FAILURE", e.getMessage());
		}
	}

	@RequestMapping(value = "/TransDelayDtlsUpdate", method = RequestMethod.POST)
	public @ResponseBody RestResponse UpdateDelayDetailsWithHdr(HttpSession session, HttpServletRequest req,
			@RequestParam("trans_delay_entry_hdr_id") Integer trans_delay_entry_hdr_id) {
		try {
			String result = "";
			TransDelayDetailsModel transdelayMstr = new TransDelayDetailsModel();
			transdelayMstr.setTrans_delay_dtl_id(Integer.parseInt(req.getParameter("trans_delay_dtl_id").toString()));
			transdelayMstr.setDelay_dtl_duration(Integer.parseInt(req.getParameter("delay_dtl_duration").toString()));
			transdelayMstr.setDelay_reason(Integer.parseInt(req.getParameter("delay_reason").toString()));
			transdelayMstr.setDelay_agency(Integer.parseInt(req.getParameter("delay_agency").toString()));
			transdelayMstr.setDelay_entry_hdr_id(trans_delay_entry_hdr_id);
			transdelayMstr.setUpdatedDateTime(new Date());
			transdelayMstr.setUpdatedBy(Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
			result = transDelayEntryDtlsService.updateTransDelayDtls(transdelayMstr);
			if (result.equals(Constants.SAVE)) {
				return new RestResponse("SUCCESS", result);
			} else {
				return new RestResponse("FAILURE", result);
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(EOFProductionController.class + " Inside UpdateDelayDetailsWithHdr Exception..", e);
			return new RestResponse("FAILURE", e.getMessage());
		}
	}
	
	@RequestMapping(value = "/getLiquidSteelValue", method = RequestMethod.GET)
	public @ResponseBody Map<String,Double> UpdateDelayDetailsWithHdr(HttpSession session,
			@RequestParam("trans_si_no") Integer trans_si_no) {
		return  eofProductionService.getLiquidSteelValue(trans_si_no);
	}
	
	@RequestMapping(value="/getSpectroChemData", method = RequestMethod.GET)
	public @ResponseBody List<SpectroLabChemMaster> getl2Dtls(@RequestParam("heat_no") String heat_no,@RequestParam("separated_sample_no") String sample_no,@RequestParam("chem_name") String chem_name) throws ClassNotFoundException, SQLException, IOException {
		logger.info(EOFProductionController.class+"...getl2Dtls()");
		List<SpectroLabChemMaster> objl2=new ArrayList<SpectroLabChemMaster>();
		objl2=SpectroLabUtil.getChemByHeatIdAndSampleNo(heat_no, sample_no,chem_name);
		return objl2;
		
	}
	@RequestMapping("/EofHMScrapConsView")
	public ModelAndView viewEofHMScrapCons() {
		logger.info(EOFProductionController.class + "...viewEofHMScrapCons()");		
		ModelAndView model = new ModelAndView("transaction/EOFMaterialConsumptions");
		return model;
	}
	@RequestMapping(value="/displayT32EofHMScrapDtls", method = RequestMethod.GET)
	public @ResponseBody List<EOFHeatLogRpt> getEofHMScrapDayConsumption(){
		return eofProductionService.getEofHMScrapDayConsumption();
	}

	@RequestMapping(value="/getEofScrapDtls", method = RequestMethod.GET)
	public @ResponseBody List<ScrapBucketDetails> getEofScrapDtls(@RequestParam("scrap_hdr_id") Integer scrap_hdr_id){
		return scrapEntryServ.getScrapEntryDetails(null, null, scrap_hdr_id);
	}
	
	@RequestMapping(value = "/UpdateScrapBucketDtls", method = RequestMethod.POST, headers = { "Content-type=application/json" })
	public @ResponseBody RestResponse updateScrapBucketDtls(HttpSession session, @RequestBody ScrapBucketHdr scrapBktHdrObj) {
		try {
			String result = "";
			ScrapBucketHdr scBktHdrObj = scrapEntryServ.getLoadedScrapBktsByHeaderId(scrapBktHdrObj.getScrap_bkt_header_id());
			scBktHdrObj.setScrap_bkt_qty(scrapBktHdrObj.getScrap_bkt_qty());
			scBktHdrObj.setUpdatedBy(Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
			scBktHdrObj.setUpdatedDateTime(new Date());
			
			for(ScrapBucketDetails dtl : scrapBktHdrObj.getScrapBktDtls()) {
				dtl.setScrap_bkt_header_id(scrapBktHdrObj.getScrap_bkt_header_id());
				dtl.setUpdatedBy(Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
				dtl.setUpdatedDateTime(new Date());
			}
			scBktHdrObj.setScrapBktDtls(scrapBktHdrObj.getScrapBktDtls());
			List<ScrapBucketDetails> scBktDtlsOrigLi = scrapEntryServ.getScrapBktDetList(scrapBktHdrObj.getScrap_bkt_header_id());
			ScrapBucketDetailsLog scBktDtlsLogObj = null;
			List<ScrapBucketDetailsLog> scrapLogLi = scrapEntryServ.getScrapBktDetLogList(scrapBktHdrObj.getScrap_bkt_header_id());
			List<ScrapBucketDetailsLog> scrapBktDtlsLogLi = null;
			if(scrapLogLi.size() == 0) {	
				scrapBktDtlsLogLi = new ArrayList<ScrapBucketDetailsLog>();
				for(ScrapBucketDetails dtl : scBktDtlsOrigLi) {
					scBktDtlsLogObj = new ScrapBucketDetailsLog();
					scBktDtlsLogObj.setScrap_bkt_detail_id(dtl.getScrap_bkt_detail_id());
					scBktDtlsLogObj.setScrap_bkt_header_id(dtl.getScrap_bkt_header_id());
					scBktDtlsLogObj.setMaterial_id(dtl.getMaterial_id());
					scBktDtlsLogObj.setMaterialQtyOrig(dtl.getMaterial_qty());
					scBktDtlsLogObj.setCreatedBy(Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
					scBktDtlsLogObj.setCreatedDateTime(new Date());
				
					scrapBktDtlsLogLi.add(scBktDtlsLogObj);
				}
			}
			result = scrapEntryServ.saveOrUpdateScrapCons(scBktHdrObj, scrapBktDtlsLogLi);
			if (result.equals(Constants.SAVE)) {
				return new RestResponse("SUCCESS", result);
			} else {
				return new RestResponse("FAILURE", result);
			}
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(EOFProductionController.class + " Inside SaveOrUpdateScrapBucketDtls Exception..", e);
			return new RestResponse("FAILURE", e.getMessage());
		}
	}
	@RequestMapping("/EofMaterialAdditionsView")
	public ModelAndView viewEofMaterialCons() {
		logger.info(EOFProductionController.class + "...viewEofMaterialCons()");	
		ObjectMapper mapper = new ObjectMapper();
		ModelAndView model = new ModelAndView("transaction/EOFLRFMaterialAdditions");
		List<String> headerList = eofProductionService.getEOFLRFMaterialConsumptionHdr(Constants.SUB_UNIT_EOF);
		String jsonHdr = "";
		try {
			jsonHdr = mapper.writeValueAsString(headerList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addObject("headerList", jsonHdr);
		model.addObject("subUnit", Constants.SUB_UNIT_EOF);
		model.addObject("consPostFlag", eofProductionService.isMaterialConsumptionPosted());
		
		return model;
	}
	@RequestMapping(value="/displayEofLrfAdditionsHdr", method = RequestMethod.GET)
	public @ResponseBody List<String> getEOFLRFMaterialConsumptionHdr(){
		return eofProductionService.getEOFLRFMaterialConsumptionHdr(Constants.SUB_UNIT_EOF);
	}
	@RequestMapping(value="/displayEofLrfAdditionsDtls", method = RequestMethod.GET)
	public @ResponseBody List<Map<String, Object>> getEOFLRFMaterialConsumptionDtls(@RequestParam String subUnit){
		List<Map<String, Object>> retLi = null;
		if(subUnit.equalsIgnoreCase(Constants.SUB_UNIT_EOF))
			retLi = eofProductionService.getEOFLRFMaterialConsumptionDtls(subUnit);
		else if(subUnit.equalsIgnoreCase(Constants.SUB_UNIT_LRF))
			retLi = eofProductionService.getEOFLRFMaterialConsumptionDtls(subUnit);
		
		return retLi;
	}
	@RequestMapping(value="/getEofLrfAdditions", method = RequestMethod.GET)
	public @ResponseBody List<List<HeatConsMaterialsDetails>> getEofLrfAdditions(@RequestParam Integer trns_si_no, @RequestParam String psn_no,
			@RequestParam String mtrl_type1, @RequestParam String mtrl_type2){
		logger.info(EOFProductionController.class+"...getEofLrfAdditions()");
		List<List<HeatConsMaterialsDetails>> retLi = new ArrayList<List<HeatConsMaterialsDetails>>();
		retLi.add(heatProcessEventService.getMtrlDetailsByType(mtrl_type1, trns_si_no, psn_no));
		retLi.add(heatProcessEventService.getMtrlDetailsByType(mtrl_type2, trns_si_no, psn_no));
		
		return retLi;
	}

	@RequestMapping(value = "/SaveOrUpdateMtrlConsumptions", method = RequestMethod.POST, headers = { "Content-type=application/json" })
	public @ResponseBody RestResponse SaveOrUpdateMtrlConsumptions(HttpSession session, @RequestBody EOFHeatLogRpt trnsObj) {
		try {
			String result = "";
			List<HeatConsMaterialsDetails> matConsLi = heatProcessEventService.getHeatConsMtrlsDtlsByTrnsId(trnsObj.getTrns_si_no());
			List<HeatConsMaterialsDetails>  newUpdLi = new ArrayList<HeatConsMaterialsDetails>();
			List<HeatConsMaterialsDetails>  newLogLi = new ArrayList<HeatConsMaterialsDetails>();
			
			for(HeatConsMaterialsDetails ldl : trnsObj.getLadleAdditions()) {
				ldl.setTrns_eof_si_no(trnsObj.getTrns_si_no());
				if(ldl.getMtr_cons_si_no() != null) {
					HeatConsMaterialsDetails origObj = matConsLi.stream().filter(orig_cons->orig_cons.getMtr_cons_si_no().equals(ldl.getMtr_cons_si_no())).findAny().orElse(null);
					if(!(origObj.getQty().equals(ldl.getQty()))){
						ldl.setUpdatedBy(Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
						ldl.setUpdatedDateTime(new Date());
						newUpdLi.add(ldl);
						newLogLi.add(origObj);
					}
				}else {
					if(ldl.getQty() != null) {
						ldl.setCreatedBy(Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
						ldl.setCreatedDateTime(new Date());
						newUpdLi.add(ldl);
					}
				}
			}
			for(HeatConsMaterialsDetails fur : trnsObj.getFurnaceAdditions()) {
				fur.setTrns_eof_si_no(trnsObj.getTrns_si_no());
				if(fur.getMtr_cons_si_no() != null) {
					HeatConsMaterialsDetails origObj = matConsLi.stream().filter(orig_cons->orig_cons.getMtr_cons_si_no().equals(fur.getMtr_cons_si_no())).findAny().orElse(null);
					if(!(origObj.getQty().equals(fur.getQty()))){
						fur.setUpdatedBy(Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
						fur.setUpdatedDateTime(new Date());
						newUpdLi.add(fur);
						newLogLi.add(origObj);
					}
				}else {
					if(fur.getQty() != null) {
						fur.setCreatedBy(Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
						fur.setCreatedDateTime(new Date());
						newUpdLi.add(fur);
					}
				}
			}
			//List<HeatConsMaterialsDetailsLog> matConsLogLi = eofProductionService.getMtrlConsLogByTrnsId(trnsObj.getTrns_si_no());
			HeatConsMaterialsDetailsLog logObj = null;
			List<HeatConsMaterialsDetailsLog> logLi = null;
			//if(matConsLogLi.size() == 0) {	
				logLi = new ArrayList<HeatConsMaterialsDetailsLog>();
				for(HeatConsMaterialsDetails dtl : newLogLi) {
					logObj = new HeatConsMaterialsDetailsLog();
					logObj.setMtr_cons_si_no(dtl.getMtr_cons_si_no());
					logObj.setTrns_eof_si_no(dtl.getTrns_eof_si_no());
					logObj.setMaterial_id(dtl.getMaterial_id());
					logObj.setQty(dtl.getQty());
					logObj.setCreatedBy(Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
					logObj.setCreatedDateTime(new Date());
					logObj.setConsumption_date(dtl.getConsumption_date());
					logObj.setSap_matl_id(dtl.getSap_matl_id());
					logObj.setValuation_type(dtl.getValuation_type());
					
					logLi.add(logObj);
				}
			//}
			result = eofProductionService.saveOrUpdateMatrlCons(newUpdLi, logLi);
			if (result.equals(Constants.SAVE)) {
				return new RestResponse("SUCCESS", result);
			} else {
				return new RestResponse("FAILURE", result);
			}
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(EOFProductionController.class + " Inside SaveOrUpdateMtrlConsumptions Exception..", e);
			return new RestResponse("FAILURE", e.getMessage());
		}
	}
	
	@RequestMapping(value = "/EOFelectrodeSaveOrUpdate", method = RequestMethod.POST )
	public @ResponseBody RestResponse EOFelectrodeSaveOrUpdate(HttpServletRequest req, HttpSession session,@RequestParam("redirect") int redirect) {
		logger.info(EOFProductionController.class + "...EOFelectrodeSaveOrUpdate()");
		SimpleDateFormat df=new SimpleDateFormat("dd/MM/yyyy HH:mm");
      
		EofElectrodeUsageModel eofObj;
        		
		if(redirect == 1) {
			String result="";
			int etransId=Integer.valueOf(req.getParameter("electrodeTransId").trim());
			if(etransId>0) {
				//do update
				eofObj = eofProductionService.eofElectrodeUsageTrnsById(etransId);
				eofObj.setElectrodeId(Integer.valueOf(req.getParameter("electrodeId")));
				eofObj.setIsAdded(req.getParameter("isAdded"));
				eofObj.setIsAdjusted(req.getParameter("isAdjusted"));		
				eofObj.setUpdatedBy(Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
				eofObj.setUpdatedDateTime(new Date());
			
				result = eofProductionService.eofElectrodeUsageTrnsSaveOrUpdate(eofObj);
			}else{
				eofObj = new EofElectrodeUsageModel();
				eofObj.setElectrodeTransId(null);
				eofObj.setElectrodeId(Integer.valueOf(req.getParameter("electrodeId")));
				eofObj.setEofHeatTransId(Integer.valueOf(req.getParameter("trans_si_no").trim()));
				eofObj.setIsAdded(req.getParameter("isAdded").trim());
				eofObj.setIsAdjusted(req.getParameter("isAdjusted").trim());		
				eofObj.setSubUintId(Integer.valueOf(req.getParameter("sub_unit_id").trim()));
				eofObj.setCreatedBy(Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
				eofObj.setCreatedDateTime(new Date());
				eofObj.setRecordStatus(1);
			
				result = eofProductionService.eofElectrodeUsageTrnsSaveOrUpdate(eofObj);
			}
			return new RestResponse("SUCCESS", result);
		}else {
			EofHeatDetails eofheatDetails = eofProductionService.getById(Integer.valueOf(req.getParameter("trans_si_no").trim()));
			String result1="";
			try {
				eofheatDetails.setElectrodeStartTime(df.parse(req.getParameter("electrode_start_time")));
				eofheatDetails.setElectrodeEndTime(df.parse(req.getParameter("electrode_end_time")));
				result1 = eofProductionService.eofHeatProductionUpdate(eofheatDetails);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return new RestResponse("SUCCESS", result1);
		}
	}
	
	@RequestMapping(value = "/getElectrodeTrans", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<EofElectrodeUsageModel> getElectrodeTrans(@RequestParam("sub_unit_id") Integer sub_unit_id, @RequestParam("trans_si_no") Integer trans_si_no) {
		logger.info(LRFProductionController.class + "...getElectrodeTrans()");

		return eofProductionService.getAllElectrodeUsageTrnsByUnit(sub_unit_id, trans_si_no);
	}
	
	@RequestMapping(value = "/getEofTrnsNo", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody EofHeatDetails getEofTrnsNo(@RequestParam ("heat_no")String heat_id) {
		EofHeatDetails trns_sl_no;
		logger.info(EOFProductionController.class+"....getEofTrnsNo");
		trns_sl_no = eofProductionService.getEofTrnsNo(heat_id);
		return trns_sl_no;
	}
	
}