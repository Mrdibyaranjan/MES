package com.smes.trans.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.smes.admin.service.impl.CommonService;
import com.smes.masters.model.PSNProductMasterModel;
import com.smes.masters.model.PsnChemistryMasterModel;
import com.smes.masters.model.PsnRouteMasterModel;
import com.smes.masters.service.impl.PSNProductMasterService;
import com.smes.masters.service.impl.PsnChemistryMasterService;
import com.smes.masters.service.impl.PsnRouteMasterService;
import com.smes.masters.service.impl.SteelLadleStatusMasterService;
import com.smes.reports.model.LRFHeatLogRpt;
import com.smes.trans.dao.impl.CCMHeatDetailsDao;
import com.smes.trans.dao.impl.LRFProductionDao;
import com.smes.trans.dao.impl.SteelLadleMaintnDao;
import com.smes.trans.model.HeatChemistryChildDetails;
import com.smes.trans.model.HeatPlanDetails;
import com.smes.trans.model.HeatPlanHdrDetails;
import com.smes.trans.model.HeatStatusTrackingModel;
import com.smes.trans.model.IfacesmsLpDetailsModel;
import com.smes.trans.model.LRFHeatArcingDetailsModel;
import com.smes.trans.model.LRFHeatConsumableLogModel;
import com.smes.trans.model.LRFHeatConsumableModel;
import com.smes.trans.model.LRFHeatDetailsModel;
import com.smes.trans.model.LRFHeatDetailsPsnBkpModel;
import com.smes.trans.model.LrfElectrodeTransactions;
import com.smes.trans.model.ReladleProcessDetailsModel;
import com.smes.trans.model.ReladleProcessHdrModel;
import com.smes.trans.model.ReladleTrnsDetailsMdl;
import com.smes.trans.model.StLadleLifeHeatWiseModel;
import com.smes.trans.model.StLdlLifeAtHeat;
import com.smes.trans.model.SteelLadleLifeModel;
import com.smes.trans.model.SteelLadleTrackingModel;
import com.smes.trans.model.TransDelayDetailsModel;
import com.smes.trans.model.TransDelayEntryHeader;
import com.smes.trans.service.impl.EofProductionService;
import com.smes.trans.service.impl.HeatPlanDetailsService;
import com.smes.trans.service.impl.HeatProceeEventService;
import com.smes.trans.service.impl.LRFProductionService;
import com.smes.trans.service.impl.SteelLadleMaintnService;
import com.smes.trans.service.impl.TransDelayEntryDtlsService;
import com.smes.trans.service.impl.TransDelayEntryHeaderService;
import com.smes.util.CommonCombo;
import com.smes.util.Constants;
import com.smes.util.DelayEntryDTO;
import com.smes.util.LRFHeatConsumableDisplay;
import com.smes.util.RestResponse;
import com.smes.wrappers.LRFRequestWrapper;

@Controller
@RequestMapping("LRFproduction")
public class LRFProductionController {

	private static final Logger logger = Logger.getLogger(LRFProductionController.class);

	@Autowired
	private LRFProductionService lrfProductionService;
	
	@Autowired
	private EofProductionService eofProductionService;

	@Autowired
	private CommonService commonService;

	@Autowired
	private TransDelayEntryHeaderService transDelayEntryHeaderService;
	
	@Autowired
	TransDelayEntryDtlsService transDelayEntryDtlsService;
	
	@Autowired
	HeatProceeEventService heatProcessEventService;

	@Autowired
	HeatPlanDetailsService heatPlanService;
	
	@Autowired
	PsnChemistryMasterService psnChemSerivice;
	
	@Autowired
	PsnRouteMasterService psnRouteServ;
	
	@Autowired
	LRFProductionDao lrfProductionDao ;
	
	@Autowired
	LRFProductionService lrfProductionServices;
	
	@Autowired
	SteelLadleMaintnDao  steelLadleMaintnDao;
	
	@Autowired
	CCMHeatDetailsDao ccmHeatDetailDao;
	
	@Autowired
	SteelLadleStatusMasterService steelLadleStatusServ;
	
	@Autowired
	SteelLadleMaintnService steelLadleMaintServ;
	
	@Autowired
	private PSNProductMasterService psnProdMstrService;
	
	@RequestMapping(value="/getLadleStatus", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<CommonCombo> getAvblLadles(){
		return lrfProductionServices.getAllAvbleLadle();
	}
	
	@RequestMapping("/LRFProductionView")
	public ModelAndView getLRFProductionView() {
		logger.info(LRFProductionController.class + "...getLRFProductionView()");
		ModelAndView model = new ModelAndView("transaction/LRFProduction");
		return model;
	}

	@RequestMapping(value = "/getLRFHeatFormDtlsById", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody LRFHeatDetailsModel getLRFHeatFormDtlsById(Integer trns_sl_no) {
		logger.info(LRFProductionController.class + "...getLRFHeatDetails()-trns_sl_no--" + trns_sl_no);

		if (trns_sl_no != null && trns_sl_no != 0) {

			return lrfProductionService.getLRFHeatDtlsFormByID(trns_sl_no);
		} else {
			return null;
		}
	}
	@RequestMapping(value = "/getLRFHeatDtlsById", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody LRFHeatDetailsModel getLrfHeatDetailsById(Integer trns_sl_no) {
		if (trns_sl_no != null && trns_sl_no != 0) {
			return lrfProductionDao.getLrfHeatDetailsById(trns_sl_no);
		} else {
			return null;
		}
	}
	@RequestMapping(value = "/LRFHeatUpdateAr", method = RequestMethod.POST, headers = { "Content-type=application/json" })
	public @ResponseBody RestResponse saveLRFHeatDetails(@RequestParam("trns_si_no") int trans_si_no,@RequestParam("AR_N2_CONSUMPTION") float AR_N2_CONSUMPTION) {
		LRFHeatDetailsModel lrfmodel=lrfProductionService.getLRFHeatDtlsFormByID(trans_si_no);
		lrfmodel.setAR_N2_CONSUMPTION(AR_N2_CONSUMPTION);
		String result="";
		result=lrfProductionService.updateLrfHeatDetails(lrfmodel);
		return new RestResponse("SUCCESS", result);
	}
	

	@RequestMapping(value = "/LRFHeatSave", method = RequestMethod.POST, headers = { "Content-type=application/json" })
	public @ResponseBody RestResponse saveLRFHeatDetails(@RequestBody LRFRequestWrapper reqWrapper,
			HeatStatusTrackingModel lrfHeatStatus, HttpSession session) {
		logger.info(LRFProductionController.class + "...saveLRFHeatDetails()");
		String result = "";

		result = lrfProductionService.saveAll(reqWrapper,
				Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
		try {
			if (result.equals(Constants.SAVE)) {
				return new RestResponse("SUCCESS", result);

			} else {
				return new RestResponse("FAILURE", result);
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(LRFProductionController.class + " Inside saveLRFHeatDetails Exception..", e);
			return new RestResponse("FAILURE", e.getMessage());
		}

	}

	@RequestMapping(value = "/getLRFArcAdditions", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<LRFHeatConsumableModel> getArcAdditions(@RequestParam String lookup_code,
			@RequestParam Integer sub_unit_id,HttpSession session) {
		logger.info(LRFProductionController.class + "...getArcAdditions() lokkuptype---" + lookup_code);

		if (lookup_code != null && lookup_code != "") {
			/*session.setAttribute("H_COL_CNT", null);
			session.setAttribute("H_COL_CNT", lrfProductionService.getArcAdditions(lookup_code).size());*/

			return lrfProductionService.getArcAdditions(lookup_code,sub_unit_id);
		} else {
			return null;
		}
	}

	@RequestMapping(value = "/getLRFArcAdditionsBySampleNo", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<LRFHeatConsumableModel> getArcAdditionsBySampleNo(@RequestParam Integer arc_sl_no,
			@RequestParam String heat_id, @RequestParam Integer heat_cnt) {
		logger.info(LRFProductionController.class + "...getArcAdditionsBySampleNo() lokkuptype---" + arc_sl_no);

		if (arc_sl_no != null) {

			return lrfProductionService.getArcAdditionsBySampleNo(arc_sl_no, heat_id, heat_cnt);
		} else {
			return null;
		}
	}

	@RequestMapping(value = "/getLRFArcAdditionsByHeat", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody LRFHeatConsumableDisplay getLRFArcAdditionsByHeat(@RequestParam String heat_id,
			@RequestParam Integer heat_cnt, HttpSession session, @RequestParam Integer unit_id) {
		logger.info(LRFProductionController.class + "...getLRFArcAdditionsByHeat() lokkuptype---" + heat_id);

		if (heat_id != null) {

			LRFHeatConsumableDisplay header = lrfProductionService.getLRFArcAdditionsByHeat(heat_id, heat_cnt, unit_id);
			// List<LRFHeatConsumableDisplay>
			// header=lrfProductionService.getLRFArcAdditionsByHeat(heat_id,heat_cnt,unit_id);
			//// session.setAttribute("H_COL_CNT", null);
			// session.setAttribute("H_COL_CNT", header.getHeaderdis().size());

			return header;
		} else {
			return null;
		}
	}

	@RequestMapping(value = "/LRFLiftChem", method = RequestMethod.GET, headers = { "Content-type=application/json" })
	public @ResponseBody RestResponse LRFLiftChem(HttpSession session) {
		try {
			logger.info(LRFProductionController.class);
			String sql = "select lookup_id from LookupMasterModel where lookup_type='CHEM_LEVEL' and lookup_value = '"+Constants.LRF_LIFT_CHEM+"' and lookup_status=1";
			Integer ChemId = commonService.getLookupIdByQuery(sql);
			return new RestResponse("SUCCESS", ChemId.toString());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(EOFProductionController.class + " Inside LRFLiftChem Exception..", e);
			return new RestResponse("FAILURE", e.getMessage());
		}
	}

	@RequestMapping(value = "/ChemDtlsSaveOrUpdate", method = RequestMethod.POST, headers = {
			"Content-type=application/json" })
	public @ResponseBody RestResponse chemistryDtlsSaveOrUpdate(@RequestParam String eventname,
			@RequestParam String sample_date, HttpSession session) {
		logger.info(HeatProcessEventController.class + "chemistryDtlsSaveOrUpdate");
		try {

			String result = "";

			// result =
			// heatProcessEventService.chemistryDtlsSaveOrUpdate(heatChemHdrDtls,eventname,sample_date,Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));

			if (result.equals(Constants.SAVE) || result.equals(Constants.UPDATE)) {

				return new RestResponse("SUCCESS", result);
			} else {
				return new RestResponse("FAILURE", result);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(ScrapEntryController.class + " Inside scrapEntryHdrSaveOrUpdate Exception..", e);
			return new RestResponse("FAILURE", e.getMessage());
		}
	}

	@RequestMapping(value = "/SaveOrUpdate", method = RequestMethod.POST, headers = { "Content-type=application/json" })
	public @ResponseBody RestResponse lrfArcAdditionsSaveOrUpdate(@RequestBody LRFRequestWrapper reqWrapper,
			@RequestParam String arc_start_date, @RequestParam String arc_end_date, HttpSession session) {
		logger.info(LRFProductionController.class + "lrfArcAdditionsSaveOrUpdate");
		try {

			String result = "";
			String addition_type;
			if(reqWrapper.getArcDetails().getPrev_unit().substring(0, 2).equals(Constants.PREV_UNIT_VD))
				addition_type = Constants.LRF_ADDITION_AVD;
			else
				addition_type = Constants.LRF_ADDITION_BVD;
			reqWrapper.getArcDetails().setAddition_type(commonService.getLookupIdByQuery("select lookup_id from LookupMasterModel where lookup_type = 'LRF_ADDITION_TYPE' and lookup_status = 1 and lookup_value='"+addition_type+"'"));
			result = lrfProductionService.lrfArcAdditionsSaveOrUpdate(reqWrapper, arc_start_date, arc_end_date,
					Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
			if (result.equals(Constants.SAVE) || result.equals(Constants.UPDATE)) {
				return new RestResponse("SUCCESS", result);
			} else {
				return new RestResponse("FAILURE", result);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(LRFProductionController.class + " Inside lrfArcAdditionsSaveOrUpdate Exception..", e);
			return new RestResponse("FAILURE", e.getMessage());
		}
	}

	@RequestMapping(value = "/getArcAdditionsHdrDtlsBySample", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody LRFHeatArcingDetailsModel getArcHdrDtlsBySample(@RequestParam Integer arc_sl_no) {
		logger.info(LRFProductionController.class + "...getArcAdditionsBySample() arc_sl_no---" + arc_sl_no);

		if (arc_sl_no > 0) {

			return lrfProductionService.getArcDetailsBySlno(arc_sl_no);
		} else {
			return null;
		}
	}
	/*
	 * public static void main(String args[]){ LRFProductionService
	 * lrfProductionService = new LRFProductionServiceImpl();
	 * lrfProductionService.getLRFArcAdditionsTemp(); //LRFProductionDaoImpl
	 * prod=new LRFProductionDaoImpl(); //prod.(); }
	 */

	@RequestMapping(value = "/getLRFArcAdditionsTemp", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<Map<String, Object>> getLRFArcAdditionsTemp(@RequestParam Integer unit_id,
			@RequestParam String heatId, @RequestParam Integer heatCnt) {

		return lrfProductionService.getLRFArcAdditionsTemp(unit_id, heatId, heatCnt);
	}

	@RequestMapping(value = "/getChemDtlsByAnalysis", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<HeatChemistryChildDetails> getChemDtlsByAnalysis(@RequestParam Integer analysis_id,
			@RequestParam String heat_id, @RequestParam Integer heat_counter, @RequestParam Integer sub_unit_id,
			@RequestParam String sample_no, @RequestParam Integer aim_psn_id) {
		logger.info(HeatProcessEventController.class + "...getChemDtlsByAnalysis()");

		return lrfProductionService.getChemDtlsByAnalysis(analysis_id, heat_id, heat_counter, sub_unit_id, sample_no,
				aim_psn_id);

	}
	
	@RequestMapping(value = "/getChemDtlsBySpectro", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<HeatChemistryChildDetails> getChemDtlsBySpectro(@RequestParam Integer analysis_id,
			@RequestParam String heat_id, @RequestParam Integer heat_counter, @RequestParam Integer sub_unit_id,
			@RequestParam String sample_no, @RequestParam Integer aim_psn_id,@RequestParam String actual_sample_no,@RequestParam String actual_heat) {
		logger.info(LRFProductionController.class + "...getChemDtlsBySpectro()");

		return lrfProductionService.getChemDtlsByAnalysisWithSpectro(analysis_id, heat_id, heat_counter, sub_unit_id, sample_no,
				aim_psn_id,actual_sample_no,actual_heat);

	}

	@RequestMapping(value = "/lrfDispatchSave", method = RequestMethod.POST, headers = {
			"Content-type=application/json" })
	public @ResponseBody RestResponse saveLRFDispacthDetails(@RequestBody LRFRequestWrapper reqWrapper,
			HttpSession session) {
		logger.info(LRFProductionController.class + "...saveLRFDispacthDetails()");
		String result = "";
		result = lrfProductionService.saveLrfDispatchDet(reqWrapper,
				Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
		try {
			if (result.equals(Constants.SAVE)) {
				return new RestResponse("SUCCESS", result);

			} else {
				return new RestResponse("FAILURE", result);
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(LRFProductionController.class + " Inside saveLRFDispacthDetails Exception..", e);
			return new RestResponse("FAILURE", e.getMessage());
		}

	}

	@RequestMapping(value = "/getHeatsWaitingForVDProcess", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody HashSet<LRFHeatDetailsModel> getHeatsForVDProcess(@RequestParam("CURRENT_UNIT") String cunit,
			@RequestParam("UNIT_PROCESS_STATUS") String pstatus) {
		logger.info(LRFProductionController.class + "...getHeatsForVDProcess()");

		return lrfProductionService.getHeatsForVDProcess(cunit, pstatus);
	}

	@RequestMapping(value = "/activityDelayMstrBySubunit", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<DelayEntryDTO> getdelayMasterBySubUnit(@RequestParam("sub_unit_id") Integer sub_unit_id,
			@RequestParam("trns_si_no") Integer trns_si_no, @RequestParam("heat_counter") Integer heat_counter, @RequestParam("prev_unit") String prev_unit) {
		// eofProductionService.getEofDelayEntriesBySubUnitAndHeat(sub_unit_id,trns_si_no);

		return lrfProductionService.getLrfDelayEntriesBySubUnitAndHeat(sub_unit_id, trns_si_no, heat_counter, prev_unit);// activityDlyMstrServie.getAllActivityDetalMasterBySubUnit(sub_unit_id);
	}
	@RequestMapping(value = "/presentActivityDelayMstrBySubunit", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<TransDelayEntryHeader> PresentdelayMasterBySubUnit(@RequestParam("sub_unit_id") Integer sub_unit_id,
			@RequestParam("trns_si_no") Integer trns_si_no, @RequestParam("heat_counter") Integer heat_counter) {
		// eofProductionService.getEofDelayEntriesBySubUnitAndHeat(sub_unit_id,trns_si_no);

		return lrfProductionService.getDelayDetailsHdrWithSubUnitAndHeat(sub_unit_id, trns_si_no);// activityDlyMstrServie.getAllActivityDetalMasterBySubUnit(sub_unit_id);
	}

	@RequestMapping(value = "/TransDelaySave", method = RequestMethod.POST, headers = {
			"Content-type=application/json" })
	public @ResponseBody RestResponse TransDelaySave(@RequestParam("heat_id") String heat_id,@RequestParam("heat_counter") Integer heat_count,
			@RequestBody List<DelayEntryDTO> transDetails, HttpSession session) {
		try {
			LRFHeatDetailsModel HeatDetails = lrfProductionService.getLRFHeatDetailsByHeatNo(heat_id,heat_count); // lrfProductionDao.get//
			
			for (int i = 0; i < transDetails.size(); i++) {
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
					newEntry.setTrans_heat_id(HeatDetails.getTrns_sl_no());
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
						if(eoFdelayEntryDTO.getDuration()!=null) {
						updatEntry.setActivity_duration(eoFdelayEntryDTO.getDuration().intValue());
						}
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
			// logger.error(EOFProductionController.class + " Inside SaveDelayDetailsWithHdr
			// Exception..", e);
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

	@RequestMapping(value = "/getPlanGrades", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<HeatPlanDetails> getPlanGrades(){
		logger.info(LRFProductionController.class+"...getPlanGrades()");
		
		return getHeatPlanPSNs();
	}
		
	private List<HeatPlanDetails> getHeatPlanPSNs(){
		List<HeatPlanHdrDetails> planHdrLi = heatPlanService.getDaywiseHeatPlanDetails();
		List<HeatPlanDetails> planDetLi;
		List<HeatPlanDetails> planDetList = new ArrayList<HeatPlanDetails>();
		List<HeatPlanDetails> finalDetLi = new ArrayList<HeatPlanDetails>();
		for (HeatPlanHdrDetails h_obj : planHdrLi){
			planDetLi = new ArrayList<HeatPlanDetails>();
			for (HeatPlanDetails d_obj : h_obj.getHeatPlanDtls()){
				if(d_obj.getStatusMstrModel().getMain_status_desc().equals(Constants.MAINHEAT_STATUS_RELEASE)){
					//d_obj.setPsnHdrId(d_obj.getHeat_plan_dtl_id()+"_"+d_obj.getPsnHdrModel().getPsn_hdr_sl_no());
					//d_obj.setFinalGrade("Plan-"+d_obj.getHeat_plan_id()+" Indent-"+d_obj.getIndent_no()+" Caster-"+d_obj.getPlanHdrModel().getLookupCasterType().getLookup_value()+" PSN-"+d_obj.getPsnHdrModel().getPsn_no());
					d_obj.setTarget_caster(d_obj.getPlanHdrModel().getLookupCasterType().getLookup_value());
					d_obj.setSection(d_obj.getPlanHdrModel().getSmsCapabilityMstrModel().getLookupOutputSection().getLookup_value());
					d_obj.setSection_type(d_obj.getPlanHdrModel().getLookupSectionType().getLookup_value());
					planDetLi.add(d_obj);
				}
			}
			planDetLi.sort((HeatPlanDetails s1, HeatPlanDetails s2)->s1.getIndent_no() -s2.getIndent_no()); 
			for (HeatPlanDetails dtl_obj :  planDetLi){
				planDetList.add(dtl_obj);
			}
		}
		/*
		for (HeatPlanDetails dtl_obj :  planDetList){
			boolean v_insert = true;
			if(finalDetLi.size() > 0){
				for(HeatPlanDetails f_obj : finalDetLi){
					if(f_obj.getAim_psn().equals(dtl_obj.getAim_psn())){
						v_insert = false;
						break;
					}
				}
				if(v_insert)
					finalDetLi.add(dtl_obj);
			}else{
				finalDetLi.add(dtl_obj);
			}
		}
		return finalDetLi;*/
		return planDetList;
	}
	
	@RequestMapping(value = "/getFinalGrades", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<HeatPlanDetails> getFinalGrades(@RequestParam Integer sample_si_id, @RequestParam Integer analysis_id, 
			@RequestParam Integer aim_psn_id, @RequestParam String analysis_type) {
		logger.info(LRFProductionController.class+"...getFinalGrades()");
		List<HeatChemistryChildDetails> lrf_chem_li = heatProcessEventService.getChemDtlsBySampleHdr(sample_si_id);
		List<HeatPlanDetails> planDetLi = getHeatPlanPSNs();
		List<PsnChemistryMasterModel> psn_chem_li;
		List<HeatPlanDetails> final_li = new ArrayList<HeatPlanDetails>();
		boolean v_flag = false;
				
		if(analysis_type.equals(Constants.LRF_LIFT_CHEM)){
			for(HeatPlanDetails planDtlObj : planDetLi){
				psn_chem_li = psnChemSerivice.getPsnChemMstrDtlsByPSN(planDtlObj.getAim_psn(), analysis_id);
				for (HeatChemistryChildDetails lrf_chem : lrf_chem_li){
					v_flag = false;
					for(PsnChemistryMasterModel psn_chem : psn_chem_li){
						
						if(lrf_chem.getElement() == psn_chem.getElement_id()){
							if(lrf_chem.getAim_value() >= psn_chem.getValue_min() && lrf_chem.getAim_value() <= psn_chem.getValue_max()){
								v_flag = true;
								break;
							}else{
								v_flag = false;
								break;
							}
						}
					} //psn_chem_li end;
					if(!v_flag)
						break;
				} //lrf_chem_li end
				if(v_flag)
					final_li.add(planDtlObj);
			}//plan_psn end
		}
		
		return final_li;
	}
	
	@RequestMapping(value = "/updateLRFProdPSN", method = RequestMethod.POST, headers = { "Content-type=application/json" })
	public @ResponseBody RestResponse updateLRFProdPSN(@RequestParam String heat_no, @RequestParam Integer final_plan, 
			@RequestParam Integer trns_si_no, @RequestParam Integer heat_counter, HttpSession session) {
		logger.info(LRFProductionController.class + "...updateLRFProdPSN()");
		String result = "";
		
		LRFHeatDetailsPsnBkpModel lrfHeatBkpObj = new LRFHeatDetailsPsnBkpModel();
		LRFHeatDetailsModel lrfHeatObj = lrfProductionService.getLRFHeatObject(trns_si_no);
		
		lrfHeatBkpObj.setTrns_sl_no(lrfHeatObj.getTrns_sl_no());
		lrfHeatBkpObj.setHeat_id(lrfHeatObj.getHeat_id());
		lrfHeatBkpObj.setHeat_counter(lrfHeatObj.getHeat_counter());
		lrfHeatBkpObj.setSteel_ladle_no(lrfHeatObj.getSteel_ladle_no());
		lrfHeatBkpObj.setSub_unit_id(lrfHeatObj.getSub_unit_id());
		lrfHeatBkpObj.setRecord_status(lrfHeatObj.getRecord_status());
		lrfHeatBkpObj.setRecord_version(lrfHeatObj.getRecord_version());
		lrfHeatBkpObj.setCreatedBy(lrfHeatObj.getCreatedBy());
		lrfHeatBkpObj.setCreatedDateTime(lrfHeatObj.getCreatedDateTime());
		lrfHeatBkpObj.setUpdatedBy(lrfHeatObj.getUpdatedBy());
		lrfHeatBkpObj.setUpdatedDateTime(lrfHeatObj.getUpdatedDateTime());
		lrfHeatBkpObj.setProduction_date(lrfHeatObj.getProduction_date());
		lrfHeatBkpObj.setProduction_shift(lrfHeatObj.getProduction_shift());
		lrfHeatBkpObj.setAim_psn(lrfHeatObj.getAim_psn());
		lrfHeatBkpObj.setPrev_unit(lrfHeatObj.getPrev_unit());
		lrfHeatBkpObj.setSteel_wgt(lrfHeatObj.getSteel_wgt());
		lrfHeatBkpObj.setHeat_plan_id(lrfHeatObj.getHeat_plan_id());
		lrfHeatBkpObj.setHeat_plan_line_no(lrfHeatObj.getHeat_plan_line_no());
		
		Integer line_rel_status_id = commonService
				.getLookupIdByQuery("select main_status_id from MainHeatStatusMasterModel where main_status_desc='"
						+ Constants.MAINHEAT_STATUS_RELEASE	+ "' and status_type='PLAN_LINES'");
		
		Integer line_wip_status_id = commonService
				.getLookupIdByQuery("select main_status_id from MainHeatStatusMasterModel where main_status_desc='"
						+ Constants.MAINHEAT_STATUS_WIP + "' and status_type='PLAN_LINES'");
		
		List<HeatPlanDetails> heatPlanDetLi = new ArrayList<HeatPlanDetails>();
		List<HeatPlanHdrDetails> heatPlanHdrLi = new ArrayList<HeatPlanHdrDetails>();
		HeatPlanDetails prevheatPlanDetObj = heatPlanService.getHeatPlanDetailsById(lrfHeatObj.getHeat_plan_line_no());
		prevheatPlanDetObj.setStatus(line_rel_status_id);
		prevheatPlanDetObj.setAct_heat_id(null);
		prevheatPlanDetObj.setAct_prod_heat_qty(null);
		prevheatPlanDetObj.setUpdatedBy(Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
		prevheatPlanDetObj.setUpdatedDateTime(new Date());
				
		HeatPlanHdrDetails prevheatPlanHdrObj = prevheatPlanDetObj.getPlanHdrModel();//lrfHeatObj.getHeatPlanModel();
		if(prevheatPlanHdrObj.getHeatPlanDtls().size() == 1){
			Integer hdr_rel_status_id = commonService
					.getLookupIdByQuery("select main_status_id from MainHeatStatusMasterModel where main_status_desc='"
							+ Constants.MAINHEAT_STATUS_RELEASE	+ "' and status_type='PLAN_HEADER'");
			prevheatPlanHdrObj.setMain_status_id(hdr_rel_status_id);
			prevheatPlanHdrObj.setUpdatedBy(Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
			prevheatPlanHdrObj.setUpdatedDateTime(new Date());
		}else
			prevheatPlanHdrObj = null;
		
		HeatPlanDetails heatPlanDetObj =  heatPlanService.getHeatPlanDetailsById(final_plan);
		heatPlanDetObj.setStatus(line_wip_status_id);
		heatPlanDetObj.setAct_heat_id(lrfHeatObj.getHeat_id());
		heatPlanDetObj.setAct_prod_heat_qty(lrfHeatObj.getSteel_wgt());
		heatPlanDetObj.setUpdatedBy(Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
		heatPlanDetObj.setUpdatedDateTime(new Date());
		
		HeatPlanHdrDetails heatPlanHdrObj = heatPlanDetObj.getPlanHdrModel();
		if(heatPlanHdrObj.getMainHeatStatusMasterModel().getMain_status_desc().equals(Constants.MAINHEAT_STATUS_RELEASE)){	
			Integer hdr_wip_status_id = commonService
					.getLookupIdByQuery("select main_status_id from MainHeatStatusMasterModel where main_status_desc='"
							+ Constants.MAINHEAT_STATUS_WIP+ "' and status_type='PLAN_HEADER'");
			heatPlanHdrObj.setMain_status_id(hdr_wip_status_id);
			heatPlanHdrObj.setUpdatedBy(Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
			heatPlanHdrObj.setUpdatedDateTime(new Date());
		}else
			heatPlanHdrObj = null;
		
		lrfHeatObj.setAim_psn(heatPlanDetObj.getAim_psn());
		lrfHeatObj.setHeat_plan_line_no(heatPlanDetObj.getHeat_plan_dtl_id());
		lrfHeatObj.setHeat_plan_id(heatPlanDetObj.getHeat_plan_id());
		lrfHeatObj.setTarget_caster_id(heatPlanDetObj.getPlanHdrModel().getCaster_type());
		lrfHeatObj.setUpdatedBy(Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
		lrfHeatObj.setUpdatedDateTime(new Date());
				
		HeatStatusTrackingModel heatTrackObj = commonService.getHeatStatusObject(heat_no, heat_counter);
		heatTrackObj.setHeat_plan_id(heatPlanDetObj.getHeat_plan_id());
		heatTrackObj.setUpdatedBy(Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
		heatTrackObj.setUpdatedDateTime(new Date());
		
		heatPlanDetLi.add(prevheatPlanDetObj);
		heatPlanDetLi.add(heatPlanDetObj);
		heatPlanHdrLi.add(prevheatPlanHdrObj);
		heatPlanHdrLi.add(heatPlanHdrObj);
		
		IfacesmsLpDetailsModel ifaceObj=new IfacesmsLpDetailsModel();
				
		ifaceObj.setMsg_id(null);
		//ifaceObj.setSch_id(heatPlanHdrObj.getLp_schd_id());
		Integer lp_schd_id;
		try {
			lp_schd_id = heatPlanHdrObj.getLp_schd_id();
		}catch(Exception e) {
			lp_schd_id = null;
		}
		ifaceObj.setSch_id(lp_schd_id);
		ifaceObj.setActual_heat_id(lrfHeatObj.getHeat_id());
		ifaceObj.setPrev_sch_id(lrfHeatBkpObj.getHeat_plan_id());
		ifaceObj.setPrev_planned_heat_id(lrfHeatBkpObj.getHeat_plan_id().toString());
		ifaceObj.setGrade(null);
		ifaceObj.setEvent_code("HSWAP");
		ifaceObj.setInterface_status(0);
		ifaceObj.setError_msg(null);
		ifaceObj.setCreatedBy(Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
		ifaceObj.setCreated_Date(new Date());
		ifaceObj.setModified_by(null);
		ifaceObj.setModified_date(null);
		
		
		result = lrfProductionService.updateLRFHeatDetPSN(lrfHeatObj, lrfHeatBkpObj, heatPlanHdrLi, heatPlanDetLi, heatTrackObj,ifaceObj);
		try {
			if (result.equals(Constants.SAVE)) {
				return new RestResponse("SUCCESS", result);
				/*RestResponse resp = new RestResponse("SUCCESS", result);
				resp.setMsg(heatPlanDetObj.getPlanHdrModel().getLookupCasterType().getLookup_value()+"_"+heatPlanDetObj.getPsnHdrModel().getPsn_no());
				return resp;
				*/
			} else {
				return new RestResponse("FAILURE", result);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(LRFProductionController.class + " Inside updateLRFProdPSN Exception..", e);
			return new RestResponse("FAILURE", e.getMessage());
		}
	}
	
	@RequestMapping(value = "/getHeatsForLadleMix", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<LRFHeatDetailsModel> getHeatsForLadleMix(@RequestParam("CURRENT_UNIT") String cunit,
			@RequestParam("UNIT_PROCESS_STATUS") String pstatus) {
		logger.info(LRFProductionController.class + "...getHeatsForLadleMix()");

		return lrfProductionService.getHeatsForLadleMix(cunit, pstatus);
	}
	
	@RequestMapping(value = "/saveLRFLadleMix", method = RequestMethod.POST, headers = { "Content-type=application/json" })
	public @ResponseBody RestResponse saveLRFLadleMix(@RequestParam Integer trns_sl_no1, @RequestParam Integer trns_sl_no2, 
			@RequestParam Integer heat_track_id1, @RequestParam Integer heat_track_id2, @RequestParam Double steel_wt1, @RequestParam Double steel_wt2,
			@RequestParam Integer steel_ladle_no1, @RequestParam Integer steel_ladle_no2, @RequestParam Integer unselected_ladle , HttpSession session) {
		logger.info(LRFProductionController.class + "...saveLRFLadleMix()");
		String result = "";
		
		LRFHeatDetailsModel lrfHeatObj1 = lrfProductionService.getLRFHeatObject(trns_sl_no1);
		LRFHeatDetailsModel lrfHeatObj2 = lrfProductionService.getLRFHeatObject(trns_sl_no2);
		HeatStatusTrackingModel heatTrackObj1 = lrfProductionService.getHeatStatusObject(heat_track_id1);
		HeatStatusTrackingModel heatTrackObj2 = lrfProductionService.getHeatStatusObject(heat_track_id2);
		
		
		lrfHeatObj1.setSteel_ladle_no_old(lrfHeatObj1.getSteel_ladle_no());
		lrfHeatObj1.setSteel_wgt_old(lrfHeatObj1.getSteel_wgt());
		lrfHeatObj1.setHeat_id_old(lrfHeatObj1.getHeat_id()+'M');
		lrfHeatObj1.setSteel_ladle_no(steel_ladle_no1);
		
		lrfHeatObj1.setSteel_wgt(steel_wt1);
		//lrfHeatObj1.setHeat_id(lrfHeatObj1.getHeat_id()+'M');
		lrfHeatObj1.setUpdatedBy(Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
		lrfHeatObj1.setUpdatedDateTime(new Date());
		
		lrfHeatObj2.setSteel_ladle_no_old(lrfHeatObj2.getSteel_ladle_no());
		lrfHeatObj2.setSteel_wgt_old(lrfHeatObj2.getSteel_wgt());
		lrfHeatObj2.setHeat_id_old(lrfHeatObj2.getHeat_id()+'M');
		lrfHeatObj2.setSteel_ladle_no(steel_ladle_no2);
		lrfHeatObj2.setSteel_wgt(steel_wt2);
		//lrfHeatObj2.setHeat_id(lrfHeatObj2.getHeat_id()+'M');
		lrfHeatObj2.setUpdatedBy(Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
		lrfHeatObj2.setUpdatedDateTime(new Date());
		
		heatTrackObj1.setHeat_id_old(heatTrackObj1.getHeat_id()+'M');
		//heatTrackObj1.setHeat_id(heatTrackObj1.getHeat_id()+'M');
		heatTrackObj1.setLadle_id(steel_ladle_no1);
		heatTrackObj1.setUpdatedBy(Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
		heatTrackObj1.setUpdatedDateTime(new Date());
		
		heatTrackObj2.setHeat_id_old(heatTrackObj2.getHeat_id()+'M');
		//heatTrackObj2.setHeat_id(heatTrackObj2.getHeat_id()+'M');
		heatTrackObj2.setLadle_id(steel_ladle_no2);
		heatTrackObj2.setUpdatedBy(Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
		heatTrackObj2.setUpdatedDateTime(new Date());
		result = lrfProductionService.updateLRFHeatDetForLadleMix(lrfHeatObj1, lrfHeatObj2, heatTrackObj1, heatTrackObj2);
		try {
			if (result.equals(Constants.SAVE)) {
				//test
				SteelLadleTrackingModel ladleTrackObj=steelLadleMaintnDao.getAvailableLadleByLadleId(steel_ladle_no1);//steel ladle 1 drop down selected
				SteelLadleTrackingModel ladleTrackObj_2=steelLadleMaintnDao.getAvailableLadleByLadleId(unselected_ladle);//steel ladle 2 drop down unselected
				ladleTrackObj.setLadle_status(commonService.getLookupIdByQuery("select steel_ladle_status_id from SteelLadleStatusMasterModel where steel_ladle_status='"+Constants.ST_LADLE_RCVD_IN_LRF+"' and recordStatus=1"));//RCVD_IN_LRF
				ladleTrackObj_2.setLadle_status(commonService.getLookupIdByQuery("select steel_ladle_status_id from SteelLadleStatusMasterModel where steel_ladle_status='"+Constants.ST_LADLE_AVAILABLE+"' and recordStatus=1"));//AVAILABLE
				steelLadleMaintnDao.updateSteelLadle(ladleTrackObj);
				steelLadleMaintnDao.updateSteelLadle(ladleTrackObj_2);
				return new RestResponse("SUCCESS", result);

			} else {;
				return new RestResponse("FAILURE", result);
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(LRFProductionController.class + " Inside saveLRFLadleMix Exception..", e);
			return new RestResponse("FAILURE", e.getMessage());
		}
	}
	
	@RequestMapping(value = "/getElectrodeTrans", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<LrfElectrodeTransactions> getElectrodeTrans(@RequestParam("sub_unit_id") Integer sub_unit_id,@RequestParam("trans_si_no") Integer trans_si_no) {
		logger.info(LRFProductionController.class + "...getHeatsForLadleMix()");

		return lrfProductionService.getAllElectrodeUsageTrnsByUnit(sub_unit_id,trans_si_no);
	}
	

	
	@RequestMapping(value = "/LRFelectrodeSaveOrUpdate", method = RequestMethod.POST )
	public @ResponseBody RestResponse LRFelectrodeSaveOrUpdate(HttpServletRequest req, HttpSession session,@RequestParam("redirect") int redirect) {
		logger.info(LRFProductionController.class + "...LRFelectrodeSaveOrUpdate()");
		
		SimpleDateFormat df=new SimpleDateFormat("dd/MM/yyyy HH:mm");
      
        LrfElectrodeTransactions lrfET;
        LRFHeatDetailsModel lrfheatDetails=lrfProductionDao.getLrfHeatDetailsById(Integer.valueOf(req.getParameter("trans_si_no").trim()));
		if(redirect==1) {
        String result="";
        int etransId=Integer.valueOf(req.getParameter("electrodeTransId").trim());
        if(etransId>0) {
			//do update
        	
        	lrfET=lrfProductionService.lrfElectrodeUsageTrnsById(etransId);
        	lrfET.setElectrodeId(Integer.valueOf(req.getParameter("electrodeId")));
			lrfET.setIsAdded(req.getParameter("isAdded"));
			lrfET.setIsAdjusted(req.getParameter("isAdjusted"));
			
			
			lrfET.setUpdatedBy(Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
			lrfET.setUpdatedDateTime(new Date());
			result=lrfProductionService.lrfElectrodeUsageTrnsSaveOrUpdate(lrfET);
			
        }else {
			lrfET=new LrfElectrodeTransactions();
			lrfET.setElectrodeTransId(null);
			lrfET.setElectrodeId(Integer.valueOf(req.getParameter("electrodeId")));
			lrfET.setLrfHeatTransId(Integer.valueOf(req.getParameter("trans_si_no").trim()));
			lrfET.setIsAdded(req.getParameter("isAdded").trim());
			lrfET.setIsAdjusted(req.getParameter("isAdjusted").trim());
			//lrfET.setIsAdjusted(req.getParameter("isAdjusted"));
			
			lrfET.setSubUintId(Integer.valueOf(req.getParameter("sub_unit_id").trim()));
			lrfET.setCreatedBy(Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
			lrfET.setCreatedDateTime(new Date());
			lrfET.setRecordStatus(1);
			result=lrfProductionService.lrfElectrodeUsageTrnsSaveOrUpdate(lrfET);
		}
		return new RestResponse("SUCCESS", result);
		}else {
			String result1="";
			try {
				lrfheatDetails.setElectrodeStartTime(df.parse(req.getParameter("electrode_start_time")));
				lrfheatDetails.setElectrodeEndTime(df.parse(req.getParameter("electrode_end_time")));
				result1=lrfProductionDao.updatelrfHeatDetails(lrfheatDetails);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return new RestResponse("SUCCESS", result1);
		}
	}
	@RequestMapping(value = "/getLRFHeatDetailsByHeatNo", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody LRFHeatDetailsModel getLRFHeatDetailsByHeatNo(@RequestParam String heatno,@RequestParam Integer heat_counter) {
		logger.info(LRFProductionController.class + "...getLRFHeatDetailsByHeatNo()-heatno--" + heatno+"& heat counter"+heat_counter);

		return lrfProductionService.getLRFHeatDetailsByHeatNo(heatno,heat_counter);
	}
	@RequestMapping(value = "/getHeatTrackStatus", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody HeatStatusTrackingModel getHeatTrackStatus(@RequestParam String heatno,@RequestParam Integer heatCounter,@RequestParam Integer aimPSN) {
		logger.info(LRFProductionController.class + "...getHeatTrackStatus()-heatno--" + heatno);
		List<PsnRouteMasterModel> psnRouteLi = psnRouteServ.getPsnRouteMstrDtlsByPSN(aimPSN);
		HeatStatusTrackingModel heatTrackObj = commonService.getHeatStatusObject(heatno, heatCounter);
		heatTrackObj.setAct_proc_path(psnRouteLi.get(0).getRouteLkpMstrModel().getLookup_value());
		
		return heatTrackObj;
	}
	@RequestMapping("/CCMReturnHeatView")
	public ModelAndView getReturnHeatDtlsOfCCM() {
		logger.info(LRFProductionController.class + "...getReturnHeatDtlsOfCCM()");		
		
		ModelAndView model = new ModelAndView("transaction/ReturnHeatCCM");
		return model;
	}
	@RequestMapping(value="/getReturnHeatDetails", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<ReladleTrnsDetailsMdl> getReturnHeatDetails(){
		List<ReladleTrnsDetailsMdl> retLi = lrfProductionServices.getUnProcessedReturnHeatDtls(null);
		for(ReladleTrnsDetailsMdl obj : retLi) {
			List<PSNProductMasterModel> psnProdLi = psnProdMstrService.getPSNProductMstrDtls(obj.getAim_psn_no());
			obj.setPsn_route(psnProdLi.get(0).getLkpProcRoutMstrMdl().getLookup_value());
		}
		
		return retLi;
	}
	@RequestMapping(value="/getMixReturnHeatDetails", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<ReladleTrnsDetailsMdl> getMixReturnHeatDetails(@RequestParam Integer trns_sl_no){
		return lrfProductionServices.getUnProcessedReturnHeatDtls(trns_sl_no);
	}
	@RequestMapping(value = "/saveCCMReturnHeatDetails", method = RequestMethod.POST, headers = { "Content-type=application/json" })
	public @ResponseBody RestResponse saveCCMReturnHeatDetails(@RequestBody ReladleProcessHdrModel reladleHdr, HttpSession session) {
		logger.info(LRFProductionController.class + "...saveCCMReturnHeatDetails()");
		String result = "";
		ReladleTrnsDetailsMdl reladleHeatDtlObj = null;
		HeatStatusTrackingModel heatTrackObj = null;
		SteelLadleTrackingModel steelLadelTrackObj = null;
		List<ReladleTrnsDetailsMdl> reladleHeatDtlLi = new ArrayList<ReladleTrnsDetailsMdl>();
		List<HeatStatusTrackingModel> heatTrackLi = new ArrayList<HeatStatusTrackingModel>();
		List<SteelLadleTrackingModel> steelLadleTrackLi = new ArrayList<SteelLadleTrackingModel>();
		List<ReladleProcessHdrModel> reladleHdrList = new ArrayList<ReladleProcessHdrModel>();
		ReladleProcessDetailsModel reladleDtlObj = new ReladleProcessDetailsModel();
		
		reladleHdr.setProcessBy(Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
		reladleHdr.setProcessDate(new Date());
		reladleHdr.setCreatedBy(Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
		reladleHdr.setCreatedDate(new Date());
		reladleHdr.setRecord_status(1);
	
		reladleDtlObj.setReladleHeatDtlId(reladleHdr.getReladleHeatDtlId());
		reladleDtlObj.setSteelLadleId(reladleHdr.getSteelLadleId());
		reladleDtlObj.setActQty(reladleHdr.getActualQty());
		reladleDtlObj.setPourQty(0.0);
		reladleDtlObj.setBalanceQty(reladleHdr.getHeatQty());
		reladleDtlObj.setAimPsn(reladleHdr.getAimPsn());
		reladleDtlObj.setHeat_counter(reladleHdr.getHeat_counter());
		reladleHdr.getReladleProcessDtls().add(reladleDtlObj);
		
		for(ReladleProcessDetailsModel detlObj : reladleHdr.getReladleProcessDtls()){
			detlObj.setCreatedBy(Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
			detlObj.setCreatedDate(new Date());
			detlObj.setRecord_status(1);
			reladleHeatDtlObj = lrfProductionService.getReturnHeatDetailsById(detlObj.getReladleHeatDtlId());
			reladleHeatDtlObj.setBalance_qty(detlObj.getBalanceQty().floatValue());
			reladleHeatDtlObj.setUpdated_by(Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
			reladleHeatDtlObj.setUpdated_date_time(new Date());
			if(reladleHdr.getProcessType().equals(Constants.RETURN_HEAT_TYPE_MIX)){
				if(reladleHeatDtlObj.getTrns_sl_no() == reladleHdr.getReladleHeatDtlId()){
					reladleHeatDtlObj.setHeat_id_h(reladleHeatDtlObj.getHeat_id());
					reladleHeatDtlObj.setHeat_counter_h(reladleHeatDtlObj.getHeat_counter());
					reladleHeatDtlObj.setHeat_id(reladleHdr.getHeatId());
					reladleHeatDtlObj.setHeat_counter(1);
				}
			}
			if(detlObj.getBalanceQty() == 0){
				heatTrackObj = commonService.getHeatStatusObject(reladleHeatDtlObj.getHeat_id(), reladleHeatDtlObj.getHeat_counter());
				steelLadelTrackObj = steelLadleMaintnDao.getSteelLadleTracking(reladleHeatDtlObj.getSteelLadleNo());
				heatTrackObj.setMain_status(Constants.HEAT_TRACK_STATUS_DEAD);
				heatTrackObj.setUnit_process_status(Constants.HEAT_TRACK_STATUS_DEAD);
				heatTrackObj.setUpdatedBy(Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
				heatTrackObj.setUpdatedDateTime(new Date());
				heatTrackLi.add(heatTrackObj);
				steelLadelTrackObj.setLadle_status(commonService.getLookupIdByQuery("select steel_ladle_status_id from SteelLadleStatusMasterModel where steel_ladle_status='"+Constants.ST_LADLE_AVAILABLE+"' and recordStatus=1"));
				steelLadelTrackObj.setUpdatedBy(Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
				steelLadelTrackObj.setUpdatedDateTime(new Date());
				steelLadleTrackLi.add(steelLadelTrackObj);
				
				reladleHeatDtlObj.setIs_processed("Y");
			}			
			reladleHeatDtlLi.add(reladleHeatDtlObj);
		}
		if(reladleHdr.getProcessType().equals(Constants.RETURN_HEAT_TYPE_MIX)){
			reladleHdr.setHeat_counter(1);
			reladleHdrList.add(reladleHdr);
		}else if(reladleHdr.getProcessType().equals(Constants.RETURN_HEAT_TYPE_TRANSFER)){
			ReladleProcessHdrModel reladleHdrObj;
			ReladleProcessDetailsModel detObj = null;
			Set<ReladleProcessDetailsModel> reladleDtlLi;
			for(ReladleProcessDetailsModel dt_obj : reladleHdr.getReladleProcessDtls()){
				if(dt_obj.getReladleHeatDtlId() != reladleHdr.getReladleHeatDtlId()){
					reladleDtlLi = new HashSet<ReladleProcessDetailsModel>();
					reladleHdrObj = new ReladleProcessHdrModel();
					reladleHdrObj.setReladleHeatDtlId(dt_obj.getReladleHeatDtlId());
					reladleHdrObj.setHeatId(dt_obj.getHeat_id());
					reladleHdrObj.setSteelLadleId(dt_obj.getSteelLadleId());
					reladleHdrObj.setProcessType(reladleHdr.getProcessType());
					reladleHdrObj.setHeatQty(dt_obj.getBalanceQty());
					reladleHdrObj.setActualQty(dt_obj.getActQty());
					reladleHdrObj.setAimPsn(dt_obj.getAimPsn());
					reladleHdrObj.setProcessBy(Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
					reladleHdrObj.setProcessDate(new Date());
					reladleHdrObj.setCreatedBy(Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
					reladleHdrObj.setCreatedDate(new Date());
					reladleHdrObj.setRecord_status(1);
					reladleHdrObj.setHeat_counter(dt_obj.getHeat_counter());
					reladleDtlLi.add(dt_obj);
					reladleHdrObj.setReladleProcessDtls(reladleDtlLi);
						
					reladleHdrList.add(reladleHdrObj);
				}else if(dt_obj.getReladleHeatDtlId() == reladleHdr.getReladleHeatDtlId()){
					detObj = dt_obj;
				}
			}
			for(ReladleProcessHdrModel hd_obj : reladleHdrList){
				ReladleProcessDetailsModel newDetObj = new ReladleProcessDetailsModel();
				newDetObj.setActQty(detObj.getActQty());
				newDetObj.setAimPsn(detObj.getAimPsn());
				newDetObj.setBalanceQty(detObj.getBalanceQty());
				newDetObj.setCreatedBy(detObj.getCreatedBy());
				newDetObj.setCreatedDate(detObj.getCreatedDate());
				newDetObj.setHeat_id(detObj.getHeat_id());
				newDetObj.setPourQty(detObj.getPourQty());
				newDetObj.setRecord_status(detObj.getRecord_status());
				newDetObj.setRecord_version(detObj.getRecord_version());
				newDetObj.setReladleHeatDtlId(detObj.getReladleHeatDtlId());
				newDetObj.setReladleProcessHdrId(detObj.getReladleProcessHdrId());
				newDetObj.setSteelLadleId(detObj.getSteelLadleId());
				newDetObj.setUpdatedBy(detObj.getUpdatedBy());
				newDetObj.setUpdatedDate(detObj.getUpdatedDate());
				
				hd_obj.getReladleProcessDtls().add(newDetObj);
			}
		}
		result = lrfProductionService.saveReladleDetails(reladleHdrList, reladleHeatDtlLi, heatTrackLi, steelLadleTrackLi, null, null, null);
		try {
			if (result.equals(Constants.SAVE)) {
				return new RestResponse("SUCCESS", result);
			} else {
				return new RestResponse("FAILURE", result);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(LRFProductionController.class + " Inside saveCCMReturnHeatDetails Exception..", e);
			return new RestResponse("FAILURE", e.getMessage());
		}
	}

	@RequestMapping(value = "/saveReladleReturnHeatDetails", method = RequestMethod.POST )
	public @ResponseBody RestResponse saveReladleReturnHeatDetails(@RequestParam("trans_si_no") int trans_si_no, @RequestParam("steel_ladle_id") int steel_ladle_id, HttpSession session) {
		logger.info(LRFProductionController.class + "...saveReladleReturnHeatDetails()");
		String result = "";
		Integer prev_ladle_id;
		List<ReladleTrnsDetailsMdl> reladleHeatDtlLi = new ArrayList<ReladleTrnsDetailsMdl>();
		List<HeatStatusTrackingModel> heatTrackLi = new ArrayList<HeatStatusTrackingModel>();
		List<SteelLadleTrackingModel> steelLadleTrackLi = new ArrayList<SteelLadleTrackingModel>();
		
		ReladleTrnsDetailsMdl reladleHeatDtlObj = lrfProductionService.getReturnHeatDetailsById(trans_si_no);
		prev_ladle_id = reladleHeatDtlObj.getSteelLadleNo();
		reladleHeatDtlObj.setSteelLadleNo(steel_ladle_id);
		reladleHeatDtlObj.setReladleNo(prev_ladle_id);
		reladleHeatDtlObj.setUpdated_by(Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
		reladleHeatDtlObj.setUpdated_date_time(new Date());
		reladleHeatDtlLi.add(reladleHeatDtlObj);
		
		HeatStatusTrackingModel heatTrackObj = commonService.getHeatStatusObject(reladleHeatDtlObj.getHeat_id(), reladleHeatDtlObj.getHeat_counter());
		SteelLadleTrackingModel newSteelLadelTrackObj = steelLadleMaintnDao.getSteelLadleTracking(steel_ladle_id);
		SteelLadleTrackingModel prevSteelLadelTrackObj = steelLadleMaintnDao.getSteelLadleTracking(prev_ladle_id);
		heatTrackObj.setLadle_id(steel_ladle_id);
		heatTrackObj.setUpdatedBy(Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
		heatTrackObj.setUpdatedDateTime(new Date());
		heatTrackLi.add(heatTrackObj);
		prevSteelLadelTrackObj.setLadle_status(commonService.getLookupIdByQuery("select steel_ladle_status_id from SteelLadleStatusMasterModel where steel_ladle_status='"+Constants.ST_LADLE_AVAILABLE+"' and recordStatus=1"));
		prevSteelLadelTrackObj.setUpdatedBy(Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
		prevSteelLadelTrackObj.setUpdatedDateTime(new Date());
		steelLadleTrackLi.add(prevSteelLadelTrackObj);
		
		Integer st_ldl_status_id = commonService.getLookupIdByQuery("select steel_ladle_status_id from SteelLadleStatusMasterModel where steel_ladle_status='"+Constants.ST_LADLE_RCVD_IN_LRF+"' and recordStatus=1");
		newSteelLadelTrackObj.setSt_ladle_life(newSteelLadelTrackObj.getSt_ladle_life() + 1);
		newSteelLadelTrackObj.setLadle_status(st_ldl_status_id);
		newSteelLadelTrackObj.setUpdatedBy(Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
		newSteelLadelTrackObj.setUpdatedDateTime(new Date());
		steelLadleTrackLi.add(newSteelLadelTrackObj);
		
		List<StLadleLifeHeatWiseModel> heatwiseStLdlPartsLi = new ArrayList<StLadleLifeHeatWiseModel>();
		StLadleLifeHeatWiseModel heatwiseStLdlPartsObj;
		List<SteelLadleLifeModel> stLadlePartsLi = steelLadleMaintServ.getSteelLadlePartsByLadleId(steel_ladle_id);
		for(SteelLadleLifeModel p_obj : stLadlePartsLi){
			heatwiseStLdlPartsObj = new StLadleLifeHeatWiseModel();
			p_obj.setTrns_life(p_obj.getTrns_life()+1);
			
			heatwiseStLdlPartsObj.setHeat_id(reladleHeatDtlObj.getHeat_id());
			heatwiseStLdlPartsObj.setHeat_counter(reladleHeatDtlObj.getHeat_counter());
			heatwiseStLdlPartsObj.setSteel_ladle_no(p_obj.getSteel_ladle_no());
			heatwiseStLdlPartsObj.setEquipment_id(p_obj.getEquipment_id());
			heatwiseStLdlPartsObj.setPart_id(p_obj.getPart_id());
			heatwiseStLdlPartsObj.setTrns_life(p_obj.getTrns_life());
			heatwiseStLdlPartsObj.setRecord_status(1);
			heatwiseStLdlPartsObj.setCreated_by(Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
			heatwiseStLdlPartsObj.setCreated_date_time(new Date());
			
			heatwiseStLdlPartsLi.add(heatwiseStLdlPartsObj);
		}
		StLdlLifeAtHeat heatwiseStLdlObj = new StLdlLifeAtHeat();
		heatwiseStLdlObj.setHist_entry_time(new Date());
		heatwiseStLdlObj.setSteel_ladle_si_no(steel_ladle_id);
		heatwiseStLdlObj.setHeat_id(reladleHeatDtlObj.getHeat_id());
		heatwiseStLdlObj.setHeat_counter(reladleHeatDtlObj.getHeat_counter());
		heatwiseStLdlObj.setStladle_life(newSteelLadelTrackObj.getSt_ladle_life() + 1);
		heatwiseStLdlObj.setStladle_status(st_ldl_status_id);

		result = lrfProductionService.saveReladleDetails(null, reladleHeatDtlLi, heatTrackLi, steelLadleTrackLi, stLadlePartsLi, heatwiseStLdlPartsLi, heatwiseStLdlObj);
		try {
			if (result.equals(Constants.SAVE)) {
				return new RestResponse("SUCCESS", result);
			} else {
				return new RestResponse("FAILURE", result);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(LRFProductionController.class + " Inside saveReladleReturnHeatDetails Exception..", e);
			return new RestResponse("FAILURE", e.getMessage());
		}
	}

	@RequestMapping(value = "/saveProcessReturnHeatDetails", method = RequestMethod.POST )
	public @ResponseBody RestResponse saveProcessReturnHeatDetails(@RequestParam("trans_si_no") int trans_si_no, HttpSession session) {
		logger.info(LRFProductionController.class + "...saveProcessReturnHeatDetails()");
		String result = "";
		List<ReladleTrnsDetailsMdl> reladleHeatDtlLi = new ArrayList<ReladleTrnsDetailsMdl>();
		List<HeatStatusTrackingModel> heatTrackLi = new ArrayList<HeatStatusTrackingModel>();
		List<ReladleProcessDetailsModel> reladleDtlLi;
		
		ReladleTrnsDetailsMdl reladleHeatDtlObj = lrfProductionService.getReturnHeatDetailsById(trans_si_no);
		String heat_id = reladleHeatDtlObj.getHeat_id();
		Integer heat_counter = reladleHeatDtlObj.getHeat_counter();
		String newHeatNo =  heat_id.substring(0, (heat_id.length() - 1)) ;
		reladleDtlLi = lrfProductionService.getReladleDetails(reladleHeatDtlObj.getTrns_sl_no());
		if(reladleHeatDtlObj.getSubUnitMstr().getSub_unit_name().substring(0, 3).equals(Constants.SUB_UNIT_CCM)
			&& !reladleHeatDtlObj.getReturnTypeMdl().getLookup_value().equals(Constants.CCM_RETURN_HEAT_FULL) 
			&& reladleDtlLi.size() == 0){
			char last_char = heat_id.substring(heat_id.length() - 1).charAt(0); 
			if(last_char == Constants.HEAT_NO_GEN_EAF1.charAt(0) || last_char == Constants.HEAT_NO_GEN_EAF2.charAt(0)) {
				reladleHeatDtlObj.setHeat_id(newHeatNo+Constants.HEAT_NO_GEN_MIX );
			}else {
				last_char++; 
				reladleHeatDtlObj.setHeat_id(newHeatNo+last_char);
			}
			reladleHeatDtlObj.setHeat_id_h(heat_id);
			reladleHeatDtlObj.setHeat_counter_h(heat_counter);
			reladleHeatDtlObj.setHeat_counter(1);
		}
		
		reladleHeatDtlObj.setIs_processed("Y");
		reladleHeatDtlObj.setUpdated_by(Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
		reladleHeatDtlObj.setUpdated_date_time(new Date());
		reladleHeatDtlLi.add(reladleHeatDtlObj);
		HeatStatusTrackingModel prevHeatTrackObj;
		HeatStatusTrackingModel heatTrackObj = commonService.getHeatStatusObject(reladleHeatDtlObj.getHeat_id(), reladleHeatDtlObj.getHeat_counter());
		
		if(heatTrackObj == null){
			heatTrackObj = new HeatStatusTrackingModel();
			prevHeatTrackObj = commonService.getHeatStatusObject(reladleHeatDtlObj.getHeat_id_h(), reladleHeatDtlObj.getHeat_counter_h());
			prevHeatTrackObj.setUnit_process_status(Constants.CAST_RETURN_TYPE_RELADLING);
			prevHeatTrackObj.setUpdatedBy(Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
			prevHeatTrackObj.setUpdatedDateTime(new Date());
			heatTrackLi.add(prevHeatTrackObj);
			heatTrackObj.setAct_proc_path(prevHeatTrackObj.getAct_proc_path()+"-"+Constants.SUB_UNIT_RLS);
			heatTrackObj.setUnit_process_status(Constants.UNIT_PROCESS_STATUS_WAIT);
		}else{
			heatTrackObj.setAct_proc_path(heatTrackObj.getAct_proc_path()+"-"+Constants.SUB_UNIT_RLS);
		}
		heatTrackObj.setHeat_id(reladleHeatDtlObj.getHeat_id());
		heatTrackObj.setHeat_counter(reladleHeatDtlObj.getHeat_counter());
		heatTrackObj.setMain_status(Constants.MAINHEAT_STATUS_WIP);
		heatTrackObj.setCurrent_unit(Constants.SUB_UNIT_LRF);
		heatTrackObj.setCreatedBy(Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
		heatTrackObj.setCreatedDateTime(new Date());
		heatTrackObj.setHeat_plan_id(reladleHeatDtlObj.getHeatPlanId());
		heatTrackObj.setLadle_id(reladleHeatDtlObj.getSteelLadleNo());
		heatTrackObj.setRecord_status(1);
		
		heatTrackLi.add(heatTrackObj);
		result = lrfProductionService.saveReladleDetails(null, reladleHeatDtlLi, heatTrackLi, null, null, null, null);
		try {
			if (result.equals(Constants.SAVE)) {
				return new RestResponse("SUCCESS", result);
			} else {
				return new RestResponse("FAILURE", result);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(LRFProductionController.class + " Inside saveProcessReturnHeatDetails Exception..", e);
			return new RestResponse("FAILURE", e.getMessage());
		}
	}
	
	@RequestMapping(value = "/SaveDispatchToLrf", method = RequestMethod.POST )
	public @ResponseBody RestResponse saveDispatchToLrfDetails(@RequestBody LRFHeatDetailsModel newlrfHeatObj, @RequestParam("trans_id") int trns_id, @RequestParam("shift") String shift, HttpSession session) {
		logger.info(LRFProductionController.class + "...SaveDispatchToLrf()");
		String result = "";
		LRFHeatDetailsModel lrfHeatObj = lrfProductionServices.getLRFHeatObject(trns_id);
		lrfHeatObj.setReturnRemarks(newlrfHeatObj.getReturnRemarks());
		lrfHeatObj.setUpdatedBy(Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
		lrfHeatObj.setUpdatedDateTime(new Date());
		lrfHeatObj.setLrf_dispatch_unit(newlrfHeatObj.getSub_unit_id());
		
		HeatStatusTrackingModel prevHeatTrackObj = lrfProductionService.getHeatStatusObject(newlrfHeatObj.getHeat_track_id());
		HeatStatusTrackingModel heatTrackObj;
		if(newlrfHeatObj.getSub_unit_name().equals(Constants.SUB_UNIT_RLS)){
			lrfHeatObj.setLrf_dispatch_date(new Date());
			lrfHeatObj.setLrf_dispatch_unit(newlrfHeatObj.getSub_unit_id());
			lrfHeatObj.setReladling("Y");
			lrfHeatObj.setReturnRemarks(newlrfHeatObj.getReturnRemarks());
			
			
			
			prevHeatTrackObj.setLrf_status(Constants.CAST_RETURN_TYPE_RELADLING);
			prevHeatTrackObj.setUnit_process_status(Constants.CAST_RETURN_TYPE_RELADLING);
			prevHeatTrackObj.setUpdatedBy(Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
			prevHeatTrackObj.setUpdatedDateTime(new Date());
			
			ReladleTrnsDetailsMdl reladleObj = new ReladleTrnsDetailsMdl();
			reladleObj.setHeat_id(lrfHeatObj.getHeat_id());
			reladleObj.setHeat_counter(lrfHeatObj.getHeat_counter());
			reladleObj.setHeatRefId(lrfHeatObj.getTrns_sl_no());
			reladleObj.setSub_unit_id(lrfHeatObj.getSub_unit_id());
			reladleObj.setAim_psn_no(lrfHeatObj.getAim_psn());
			reladleObj.setAct_qty((lrfHeatObj.getSteel_wgt()).floatValue() );
			reladleObj.setSteelLadleNo(lrfHeatObj.getSteel_ladle_no());
			reladleObj.setReturn_date(lrfHeatObj.getProduction_date());
			reladleObj.setReturn_type(commonService.getLookupIdByQuery(
						"select lookup_id from LookupMasterModel where lookup_type='"+Constants.CAST_RETURN_TYPE+"' and lookup_code = '"+Constants.CAST_RETURN_TYPE_RELADLING+"' and  lookup_status=1"));
			reladleObj.setReason(newlrfHeatObj.getReturnRemarks());
			reladleObj.setBalance_qty((lrfHeatObj.getSteel_wgt()).floatValue());
			reladleObj.setRecord_status(1);
			reladleObj.setCreated_by(Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
			reladleObj.setCreated_date_time(new Date());
			reladleObj.setHeatPlanId(lrfHeatObj.getHeat_plan_id());
			reladleObj.setHeatPlanLineNo(lrfHeatObj.getHeat_plan_line_no());
			reladleObj.setIs_processed("N");
			reladleObj.setDisp_temp(lrfHeatObj.getTap_temp());
			
			result = lrfProductionService.saveDispatchToLrfDtls(lrfHeatObj, null, prevHeatTrackObj, reladleObj);
		}else{
			newlrfHeatObj.setSteel_wgt(lrfHeatObj.getSteel_wgt());
			newlrfHeatObj.setHeat_plan_id(lrfHeatObj.getHeat_plan_id());
			newlrfHeatObj.setHeat_plan_line_no(lrfHeatObj.getHeat_plan_line_no());
			newlrfHeatObj.setTarget_caster_id(lrfHeatObj.getTarget_caster_id());
			newlrfHeatObj.setSteel_ladle_no(lrfHeatObj.getSteel_ladle_no());
			newlrfHeatObj.setCreatedBy(Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
			newlrfHeatObj.setCreatedDateTime(new Date());
			newlrfHeatObj.setProduction_date(new Date());
			newlrfHeatObj.setProduction_shift(commonService.getLookupIdByQuery("select lookup_id from LookupMasterModel where lookup_type='SHIFT' and lookup_value = '"+shift+"' and lookup_status=1"));
			newlrfHeatObj.setAim_psn(lrfHeatObj.getAim_psn());
			newlrfHeatObj.setPrev_unit(lrfHeatObj.getPrev_unit());
			newlrfHeatObj.setTap_temp(lrfHeatObj.getTap_temp());
			newlrfHeatObj.setLrf_initial_temp(lrfHeatObj.getLrf_initial_temp());
			newlrfHeatObj.setReturnRemarks(null);
			newlrfHeatObj.setRecord_status(1);
			newlrfHeatObj.setReladling("N");
		
			heatTrackObj = new HeatStatusTrackingModel();
			heatTrackObj.setHeat_id(newlrfHeatObj.getHeat_id());
			heatTrackObj.setHeat_counter(newlrfHeatObj.getHeat_counter());
			heatTrackObj.setMain_status(prevHeatTrackObj.getMain_status());
			heatTrackObj.setAct_proc_path(prevHeatTrackObj.getAct_proc_path()+"-"+newlrfHeatObj.getSub_unit_name());
			heatTrackObj.setCurrent_unit(newlrfHeatObj.getSub_unit_name());
			heatTrackObj.setUnit_process_status(prevHeatTrackObj.getUnit_process_status());
			heatTrackObj.setCreatedBy(Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
			heatTrackObj.setCreatedDateTime(new Date());
			heatTrackObj.setHeat_plan_id(lrfHeatObj.getHeat_plan_id());
			heatTrackObj.setLadle_id(lrfHeatObj.getSteel_ladle_no());
			heatTrackObj.setRecord_status(1);
			
			
			
		
			result = lrfProductionService.saveDispatchToLrfDtls(lrfHeatObj, newlrfHeatObj, heatTrackObj, null);
		}
	
		
		try {
			if (result.equals(Constants.SAVE)) {
				return new RestResponse("SUCCESS", result);
			} else {
				return new RestResponse("FAILURE", result);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(LRFProductionController.class + " Inside SaveDispatchToLrf Exception..", e);
			return new RestResponse("FAILURE", e.getMessage());
		}
	}
	@RequestMapping("/LrfMaterialAdditionsView")
	public ModelAndView viewLrfMaterialCons() {
		logger.info(EOFProductionController.class + "...viewLrfMaterialCons()");	
		ObjectMapper mapper = new ObjectMapper();
		ModelAndView model = new ModelAndView("transaction/EOFLRFMaterialAdditions");
		List<String> headerList = eofProductionService.getEOFLRFMaterialConsumptionHdr(Constants.SUB_UNIT_LRF);
		String jsonHdr = "";
		try {
			jsonHdr = mapper.writeValueAsString(headerList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addObject("headerList", jsonHdr);
		model.addObject("subUnit", Constants.SUB_UNIT_LRF);
		model.addObject("consPostFlag", eofProductionService.isMaterialConsumptionPosted());
		
		return model;
	}
	@RequestMapping(value="/getEofLrfAdditions", method = RequestMethod.GET)
	public @ResponseBody List<LRFHeatConsumableModel> getEofLrfAdditions(@RequestParam String heat_no, @RequestParam String mtrl_type){
		logger.info(LRFProductionController.class+"...getEofLrfAdditions()");
		
		return lrfProductionService.getLrfAdditionsByHeatNo(heat_no, mtrl_type);
	}
	@RequestMapping(value = "/SaveOrUpdateMtrlConsumptions", method = RequestMethod.POST, headers = { "Content-type=application/json" })
	public @ResponseBody RestResponse SaveOrUpdateMtrlConsumptions(HttpSession session, @RequestBody LRFHeatLogRpt trnsObj) {
		try {
			String result = "";
			LRFHeatDetailsModel lrfHeatObj = lrfProductionService.getLRFHeatObject(trnsObj.getTrns_si_no());
			List<LRFHeatArcingDetailsModel> arcLi = lrfProductionDao.getArcDetailsByHeatId(lrfHeatObj.getHeat_id(), lrfHeatObj.getHeat_counter());
			List<LRFHeatConsumableLogModel>  newLogLi = new ArrayList<LRFHeatConsumableLogModel>();
			LRFHeatConsumableLogModel logObj = null;
			
			for(LRFHeatConsumableModel cons : trnsObj.getLrfAdditions()) {
				if(cons.getCons_sl_no() != null) {
					if(!(cons.getTot_qty().equals(cons.getAct_tot_qty()))){
						logObj = new LRFHeatConsumableLogModel();
						logObj.setCons_sl_no(cons.getCons_sl_no());
						logObj.setHeat_id(cons.getHeat_id());
						logObj.setHeat_counter(cons.getHeat_counter());
						logObj.setArc_sl_no(cons.getArc_sl_no());
						logObj.setMaterial_type(cons.getMaterial_type());
						logObj.setMaterial_id(cons.getMaterial_id());
						logObj.setConsumption_qty(cons.getConsumption_qty());
						logObj.setAddition_date_time(cons.getAddition_date_time());
						logObj.setSap_matl_id(cons.getSap_matl_id());
						logObj.setValuation_type(cons.getValuation_type());
						logObj.setCreated_by(Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
						logObj.setCreated_date_time(new Date());
						
						Double cons_qty = cons.getConsumption_qty() + (cons.getTot_qty() - cons.getAct_tot_qty());
						cons.setConsumption_qty(cons_qty);
						cons.setUpdated_by(Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
						cons.setUpdated_date_time(new Date());
						
						newLogLi.add(logObj);
					}
				}else {
					if(cons.getTot_qty() != null) {
						cons.setHeat_id(lrfHeatObj.getHeat_id());
						cons.setHeat_counter(lrfHeatObj.getHeat_counter());
						cons.setArc_sl_no(arcLi.get(0).getArc_sl_no());
						cons.setConsumption_qty(cons.getTot_qty());
						cons.setAddition_date_time(new Date());
						cons.setRecord_status(1);
						cons.setRecord_version(0);
						cons.setCreated_by(Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
						cons.setCreated_date_time(new Date());
					}
				}
			}
			
			result = lrfProductionService.saveOrUpdateMatrlCons(trnsObj.getLrfAdditions(), newLogLi);
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
}