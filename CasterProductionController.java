package com.smes.trans.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.smes.admin.service.impl.CommonService;
import com.smes.masters.dao.impl.LookupMasterDao;
import com.smes.masters.dao.impl.PsnGradeMasterDao;
import com.smes.masters.model.LookupMasterModel;
import com.smes.masters.model.ProductSectionMasterModel;
import com.smes.masters.model.PsnQualityCharValuesMasterModel;
import com.smes.masters.model.TundishStatusTrackModel;
import com.smes.masters.service.impl.LookupMasterService;
import com.smes.masters.service.impl.MTubeMasterService;
import com.smes.masters.service.impl.ProductSectionMasterService;
import com.smes.masters.service.impl.PsnQACharValuesMasterServiceImpl;
import com.smes.masters.service.impl.TundishMstrService;
import com.smes.masters.service.impl.UnitMasterService;
import com.smes.reports.service.impl.HeatTrackingReportService;
import com.smes.trans.dao.impl.CCMHeatDetailsDao;
import com.smes.trans.dao.impl.CCMHeatSeqGenModelDao;
import com.smes.trans.dao.impl.CasterProductionDao;
import com.smes.trans.dao.impl.HeatProceeEventDao;
import com.smes.trans.dao.impl.LRFProductionDao;
import com.smes.trans.dao.impl.MtubeTrnsModelDao;
import com.smes.trans.dao.impl.TundishTrnsHistoryDao;
import com.smes.trans.model.CCMBatchDetailsBkpModel;
import com.smes.trans.model.CCMBatchDetailsModel;
import com.smes.trans.model.CCMHeatConsMaterialsDetails;
import com.smes.trans.model.CCMHeatDetailsModel;
import com.smes.trans.model.CCMHeatProcessParameterDetails;
import com.smes.trans.model.CCMHeatSeqGenModel;
import com.smes.trans.model.CCMProductDetailsBkpModel;
import com.smes.trans.model.CCMProductDetailsModel;
import com.smes.trans.model.CCMSeqGroupDetails;
import com.smes.trans.model.CCMTundishDetailsModel;
import com.smes.trans.model.CastRunningStatusModel;
import com.smes.trans.model.HeatChemistryChildDetails;
import com.smes.trans.model.HeatPlanDetails;
import com.smes.trans.model.HeatPlanHdrDetails;
import com.smes.trans.model.HeatPlanLinesDetails;
import com.smes.trans.model.HeatStatusTrackingModel;
import com.smes.trans.model.IfacesmsLpDetailsModel;
import com.smes.trans.model.LRFHeatDetailsModel;
import com.smes.trans.model.MtubeTrnsModel;
import com.smes.trans.model.ReladleTrnsDetailsMdl;
import com.smes.trans.model.SeqTransactionEvent;
import com.smes.trans.model.TransDelayDetailsModel;
import com.smes.trans.model.TransDelayEntryHeader;
import com.smes.trans.model.TundishTrnsHistoryModel;
import com.smes.trans.service.impl.CCMBatchDetailsService;
import com.smes.trans.service.impl.CCMProcessParamService;
import com.smes.trans.service.impl.CCMProductDetailsService;
import com.smes.trans.service.impl.CCMTundishDetailsService;
import com.smes.trans.service.impl.CasterProductionService;
import com.smes.trans.service.impl.HeatPlanDetailsService;
import com.smes.trans.service.impl.HeatProceeEventService;
import com.smes.trans.service.impl.LRFProductionService;
import com.smes.trans.service.impl.SeqTransactionEventService;
import com.smes.trans.service.impl.SteelLadleMaintnService;
import com.smes.trans.service.impl.TransDelayEntryDtlsService;
import com.smes.trans.service.impl.TransDelayEntryHeaderService;
import com.smes.trans.service.impl.TundishTrnsHistoryService;
import com.smes.util.CommonCombo;
import com.smes.util.Constants;
import com.smes.util.DelayEntryDTO;
import com.smes.util.GenericClass;
import com.smes.util.RestResponse;
import com.smes.wrappers.CasterRequestWrapper;

@Controller
@RequestMapping("casterProduction")
public class CasterProductionController {
	private static final Logger logger = Logger.getLogger(CasterProductionController.class);


	@Autowired
	private LookupMasterDao lookupMstrDao;
	
	@Autowired
	HeatProceeEventDao heatProceeEventDao;
	@Autowired
	CasterProductionService casterProdService;

	@Autowired
	LRFProductionService lrfProdService;

	@Autowired
	CommonService commonDao;
	
	@Autowired
	PsnGradeMasterDao psnGradeDao;
	
	@Autowired
	LRFProductionDao lrfProductionDao;
	
	@Autowired
	private TransDelayEntryHeaderService transDelayEntryHeaderService;
	
	@Autowired
	TransDelayEntryDtlsService transDelayEntryDtlsService;
	
	@Autowired
	LookupMasterService lookupService;
	
	@Autowired
	HeatPlanDetailsService heatPlanServ;

	@Autowired
	PsnQACharValuesMasterServiceImpl psnQAcharService;

	@Autowired
	LookupMasterService lookupmstrDao;

	@Autowired
	UnitMasterService unitMasterService;

	@Autowired
	CCMHeatDetailsDao ccmheatDao;

	@Autowired
	CCMProductDetailsService ccmProdDtlsService;

	@Autowired
	CCMTundishDetailsService ccmTundishDtlsService;

	@Autowired
	CCMHeatSeqGenModelDao ccmHeatSeqGenDao;

	@Autowired
	CCMBatchDetailsService ccmBatchDetailsService;

	@Autowired
	private HeatProceeEventService heatProcessEventService;

	@Autowired
	CCMProcessParamService ccmProcParmService;

	@Autowired
	private TundishMstrService tundishMstrService;

	@Autowired
	TundishTrnsHistoryService tundishHstyService;

	@Autowired
	TundishTrnsHistoryDao trnsHistoryDao;

	@Autowired
	MtubeTrnsModelDao mtubeDao;

	@Autowired
	CasterProductionDao casterProdDao;

	@Autowired
	HeatTrackingReportService heatTrackStatsService;

	@Autowired
	SeqTransactionEventService seqTrnsEvntService;

	@Autowired
	SteelLadleMaintnService steelLaleService;

	@Autowired
	MTubeMasterService mtubeMstrService;
	
	@Autowired
	ProductSectionMasterService prodsecmstrService;
	
	@RequestMapping("/casterProductionView")
	public ModelAndView getLRFProductionView() {
		logger.info(CasterProductionController.class + "...casterProductionView()");
		ModelAndView model = new ModelAndView("transaction/CasterProduction");
		return model;
	}
	
	@RequestMapping("/batchReconcileView")
	public ModelAndView getCCMProductionView() {
		logger.info(CasterProductionController.class + "...getCCMProductionView()");
		ModelAndView model = new ModelAndView("transaction/BatchProductionReconcile");
		return model;
	}

	@RequestMapping(value = "/getHeatPlanHeaderDetailsByCaster", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<HeatPlanHdrDetails> getHeatPlanHeaderDetailsByCaster(
			@RequestParam("PLAN_STATUS") String planstatus, @RequestParam("CASTER_TYPE") String casterType) {
		logger.info(CasterProductionController.class + "...getRunningIdDetByHeatPlanNo-trns_sl_no--" + casterType);
		return casterProdService.getHeatPlanHeaderDetailsByCaster(planstatus, casterType);

	}
	
	@RequestMapping(value = "/getheatId", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<CommonCombo> getheatId(Integer lookup_id) {
		logger.info(CasterProductionController.class + "...getheatId");
		return casterProdService.getheatId(lookup_id);
	}
	
	@RequestMapping(value = "/attachHeatGenerateRunId", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody RestResponse attachHeatAndGenerateRunId(@RequestBody CastRunningStatusModel crsModel,
			@RequestParam String cast_start_date, HttpSession session) {
		logger.info(CasterProductionController.class + "...attachHeatAndGenerateRunId-heat Id-----"
				+ crsModel.getHeat_plan_id());
		String result = "";
		try {
			result = casterProdService.attachHeatAndGenerateRunId(crsModel, cast_start_date,
					Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));

			if (result.contains(Constants.SAVE)) {
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

	@RequestMapping(value = "/getRunningIdDet", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody CastRunningStatusModel getRunningIdDetByHeatPlanNo(Integer heatplanId) {
		logger.info(CasterProductionController.class + "...getRunningIdDetByHeatPlanNo-trns_sl_no--" + heatplanId);
		CastRunningStatusModel obj = null;
		try {

			obj = casterProdService.getRunningIdDetByHeatPlanNo(heatplanId);

			return obj;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}

	@RequestMapping(value = "/getRunIdDetWithRunId", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody CastRunningStatusModel getRunIdDetWithRunId(Integer runId) {
		logger.info(CasterProductionController.class + ".. getRunIdDetWithRunId--runId--" + runId);
		CastRunningStatusModel obj = null;
		try {

			obj = casterProdService.getRunIdDetWithRunId(runId);

			return obj;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}

	@RequestMapping(value = "/getHeatPlanDetWithRunId", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<HeatPlanLinesDetails> getHeatPlanDetWithRunId(Integer runId) {
		logger.info(CasterProductionController.class + "...getHeatPlanDetWithRunId-runId--" + runId);
		return casterProdService.getHeatPlanDetWithRunId(runId);

	}

	@RequestMapping(value = "/saveRunningIdDet", method = RequestMethod.POST, headers = {
			"Content-type=application/json" })
	public @ResponseBody RestResponse saveRunningIdDetails(@RequestBody CastRunningStatusModel runObj,
			@RequestParam String cast_start_date, @RequestParam String cast_end_date, HttpSession session) {
		String result = "";
		try {
			result = casterProdService.saveRunningIdDetails(runObj, cast_start_date, cast_end_date,
					Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
			if (result.equals(Constants.SAVE)) {
				return new RestResponse("SUCCESS", result);

			} else {
				return new RestResponse("FAILURE", result);
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(CasterProductionController.class + " Inside saveRunningIdDetails Exception..", e);
			return new RestResponse("FAILURE", e.getMessage());
		}

	}

	@RequestMapping(value = "/getHeatsWaitingForCasterProcess", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody HashSet<LRFHeatDetailsModel> getHeatsForCasterProcess(
			@RequestParam("UNIT_PROCESS_STATUS") String pstatus,@RequestParam("cunit") String cunit) {
		logger.info(CasterProductionController.class + "...getHeatsWaitingForCasterProcess()");
		HashSet<LRFHeatDetailsModel> list = lrfProdService.getHeatsForCasterProcess(cunit, pstatus);

		return list;
	}

	@RequestMapping(value = "/CasterHeatSave", method = RequestMethod.POST, headers = {
			"Content-type=application/json" })
	public @ResponseBody RestResponse saveCasterHeatDetails(@RequestBody CasterRequestWrapper reqWrapper,
			HttpSession session) {
		logger.info(CasterProductionController.class + "...saveCasterHeatDetails()");
		String result = "";
		result = casterProdService.saveAll(reqWrapper,
				Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
		if (result.equals(Constants.SAVE)) {
			return new RestResponse("SUCCESS", result);

		} else {
			return new RestResponse("FAILURE", result);
		}
	}

	@RequestMapping(value = "/CasterHeatDtlsSave", method = RequestMethod.POST, headers = {
			"Content-type=application/json" })
	public @ResponseBody RestResponse saveCasterHeatDetails1(@RequestBody CCMHeatDetailsModel model,
			HttpSession session) {

		model.setHeat_counter(1);
		model.setCreated_date_time(new Date());
		model.setRecord_version(0);
		model.setCreated_by(Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
		try {
			String result = casterProdService.saveCastDetails(model);
			if (result.equals(Constants.SAVE)) {
				return new RestResponse("SUCCESS", result);

			} else {
				return new RestResponse("FAILURE", result);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new RestResponse("FAILURE", e.getMessage());
		}
	}

	@RequestMapping(value = "/heatTrackStatus", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody Map<String, Object> getHeatTrackStatus(String heatId, Integer heat_counter, Integer psn_hdr_sl_no,
			Integer unit_id) {
		HashMap<String, Object> reply = new HashMap<String, Object>();
		logger.info(CasterProductionController.class + "...getHeatTrachStatus()");
		int unitid = unitMasterService.getByName("CCM").getUnit_id();
		List<LookupMasterModel> retTypes = lookupmstrDao.getLookupDtlsByLkpTypeAndStatus("PSN_REQUIREMENT_TYPE", 1);
		LookupMasterModel model = retTypes.stream().filter(p -> p.getLookup_code().equals("GENERAL_REQUIREMENT"))
				.collect(Collectors.toList()).get(0);
		List<PsnQualityCharValuesMasterModel> qCharValues = psnQAcharService.getPsnQACharValMstrDtls(psn_hdr_sl_no,
				unitid, model.getLookup_id());
		PsnQualityCharValuesMasterModel q = null;
		
		if(qCharValues.size() > 0) {
			try {
			q = qCharValues.stream()
				.filter(p -> p.getQa_char_desc().equals("CASTING_POWDER")).findFirst().get();
			}catch(Exception e) {
				q = null;
			}
		}
		reply.put("status", commonDao.getHeatStatusObject(heatId, heat_counter));
		reply.put("qc", q);
		
		return reply;
	}

	@RequestMapping(value = "/getCCMsavedHeats", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<CCMHeatDetailsModel> getccmheats(@RequestParam Integer unit_id) {
		// logger.info(CasterProductionController.class+"...getCasterHeatDetailsById-trns_sl_no--"+casterHeatPk);
		List<CCMHeatDetailsModel> values = ccmheatDao.getCCMheats(unit_id);
		List<CCMHeatDetailsModel> valuesRet = new ArrayList<>();
		values.forEach(ccmH -> {
			ReladleTrnsDetailsMdl mdl = casterProdDao.getReladleMdlByCasterId(ccmH.getTrns_sl_no());
			if (mdl != null) {
				ccmH.setReturn_qty(mdl.getBalance_qty());
				ccmH.setIs_processed(mdl.getIs_processed());
				ccmH.setReturn_reason(mdl.getReason());
			}
			//HeatStatusTrackingModel trckStus = casterProdDao.getHeatStatusTrackingModelByHeatId(ccmH.getHeat_no());
			//if (trckStus == null) {
				LRFHeatDetailsModel lrfObj = lrfProdService.getLRFHeatDetailsByHeatNo(ccmH.getHeat_no(), ccmH.getHeat_counter());
				ccmH.setPrev_unit(lrfObj.getSubUnitMstrMdl().getSub_unit_name());
				ccmH.setLift_temp(lrfObj.getLrf_dispatch_temp());

				/*StringBuilder chemDetails=new StringBuilder("");
				List<LRFHeatDetailsModel> lrfChemDet =lrfProdService.getLrfChemDetails(ccmH.getHeat_no(), ccmH.getHeat_counter());
				lrfChemDet.forEach(chemVal->{
					if(chemVal.getChem_element_name().equalsIgnoreCase("C") || chemVal.getChem_element_name().equalsIgnoreCase("Ti") || chemVal.getChem_element_name().equalsIgnoreCase("Mn")
							|| chemVal.getChem_element_name().equalsIgnoreCase("S") || chemVal.getChem_element_name().equalsIgnoreCase("P") || chemVal.getChem_element_name().equalsIgnoreCase("Si")){
						chemDetails.append(" | "+chemVal.getChem_element_name() +"("+chemVal.getElement_aim_value()+")");
					}
				});
				ccmH.setChem_details(chemDetails.toString());*/
				
				valuesRet.add(ccmH);
			//}
		});
		
		return valuesRet;
	}

	@RequestMapping(value = "/getLiftChem", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<HeatChemistryChildDetails> getLiftChemDtls(@RequestParam String heat_no, @RequestParam Integer heat_counter, @RequestParam Integer aim_psn) {
		return casterProdService.getLrfLiftChemDetails(heat_no, heat_counter, aim_psn);
	}
	
	@RequestMapping(value = "/getCsWtg", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody ProductSectionMasterModel getCsWtg(@RequestParam Integer section) {
		return prodsecmstrService.getSecDetailsBySecId(section);
	}
	
	@RequestMapping(value = "/getCCMProdDtls", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<CCMProductDetailsModel> getCCMprodDtls(@RequestParam Integer trns_sl_no) {
		// logger.info(CasterProductionController.class+"...getCasterHeatDetailsById-trns_sl_no--"+casterHeatPk);

		CCMHeatDetailsModel heatDtls = ccmheatDao.getCCMheatByid(trns_sl_no);
		List<CCMProductDetailsModel> lst = ccmProdDtlsService.getccmProdDetailsByTrnsSLno(trns_sl_no);
		List<LookupMasterModel> ccmStandNos = lookupmstrDao.getLookupDtlsByLkpTypeAndStatus("CCM_STAND_NO", 1);
		if (lst.isEmpty()) {
			List<CCMProductDetailsModel> reply = new ArrayList<CCMProductDetailsModel>();
			
			List<HeatPlanLinesDetails> heatPlanLineLi = new ArrayList<HeatPlanLinesDetails>();
		
			for (HeatPlanLinesDetails obj : heatDtls.getHeatPlanMdl().getHeatPlanLine()) {
				if(obj.getAim_psn().equals(heatDtls.getPsn_no())){
					heatPlanLineLi.add(obj);
				}
			}
			
			ccmStandNos.forEach(standNo -> {
				Optional<CCMProductDetailsModel> mdl = lst.stream()
						.filter(prod -> prod.getStand_id().equals(standNo.getLookup_id())).findFirst();

				if (mdl.isPresent()) {
					reply.add((CCMProductDetailsModel) mdl.get());
				} else {
					CCMProductDetailsModel prodDtls = new CCMProductDetailsModel();
					prodDtls.setStand_id(standNo.getLookup_id());
					prodDtls.setStandlkpMdl(standNo);
					double pdt_wt = heatDtls.getProductMasterMdl().getSection_wgt();
					Float pdt_steel_wgt = heatDtls.getSteel_ladle_wgt();																
					prodDtls.setCs_size(heatDtls.getHeatPlanMdl().getSmsCapabilityMstrModel().getLookupOutputSection()
							.getLookup_id());
					prodDtls.setCsSizeMdl(
							heatDtls.getHeatPlanMdl().getSmsCapabilityMstrModel().getLookupOutputSection());
					prodDtls.setCs_wgt(Float.valueOf(pdt_wt + "")); 
					Float f = (float) Math.round((pdt_steel_wgt / ccmStandNos.size())*100)/100;
					prodDtls.setTot_wgt_batches(f);
					float cut_len = 0.0f;
					if(heatPlanLineLi.size() == 1){
						cut_len = heatPlanLineLi.get(0).getPlan_cut_length().floatValue();
						Double temp = (((pdt_steel_wgt / pdt_wt) * 1000) / cut_len);
						prodDtls.setHeat_plan_line_no(heatPlanLineLi.get(0).getHeat_line_id());
						prodDtls.setCut_length(heatPlanLineLi.get(0).getPlan_cut_length().floatValue());
						prodDtls.setNo_batches((float) Math.round(temp.floatValue() / ccmStandNos.size()));
					}
					reply.add(prodDtls);
				}
			});
			return reply;
		} else {
			List<CCMProductDetailsModel> temp = new ArrayList<CCMProductDetailsModel>();
			lst.forEach(l -> {
				ccmStandNos.forEach(standNo -> {
					Optional<CCMProductDetailsModel> mdl = lst.stream()
							.filter(prod -> prod.getStand_id().equals(standNo.getLookup_id())).findFirst();
					if (mdl.isPresent()) {
						if(mdl.get().getMtubeTrnsMdl() != null){
						MtubeTrnsModel mtubeLifeObj = casterProdService.getMtubelife(mdl.get().getMtubeTrnsMdl().getCcm_mtube_sl_no());
						mdl.get().getMtubeTrnsMdl().setCleaning_life(mtubeLifeObj.getCleaning_life());
						mdl.get().getMtubeTrnsMdl().setTotal_life(mtubeLifeObj.getTotal_life());
						}
						temp.add((CCMProductDetailsModel) mdl.get());
					} else {
						/*CCMProductDetailsModel prodDtls = new CCMProductDetailsModel();
						prodDtls.setStand_id(standNo.getLookup_id());
						prodDtls.setStandlkpMdl(standNo);
						prodDtls.setCs_size(null);
						prodDtls.setCsSizeMdl(null);*/
						temp.add(null);
					}
				});
			});
			return temp;
		}
	}

	@RequestMapping(value = "/getCCMProdDtlsDisableEnable", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody CCMProductDetailsModel getCCMprodDtlsDisableEnable(@RequestParam Integer trns_sl_no, @RequestParam Integer no_of_strands) {
		// logger.info(CasterProductionController.class+"...getCasterHeatDetailsById-trns_sl_no--"+casterHeatPk);
		List<CCMProductDetailsModel> reply = new ArrayList<>();
		CCMHeatDetailsModel heatDtls = ccmheatDao.getCCMheatByid(trns_sl_no);
		List<CCMProductDetailsModel> lst = ccmProdDtlsService.getccmProdDetailsByTrnsSLno(trns_sl_no);
		List<LookupMasterModel> ccmStandNos = lookupmstrDao.getLookupDtlsByLkpTypeAndStatus("CCM_STAND_NO", 1);
		
		List<HeatPlanLinesDetails> heatPlanLineLi = new ArrayList<HeatPlanLinesDetails>();
		for (HeatPlanLinesDetails obj : heatDtls.getHeatPlanMdl().getHeatPlanLine()) {
			if(obj.getAim_psn().equals(heatDtls.getPsn_no())){
				heatPlanLineLi.add(obj);
			}
		}

		ccmStandNos.forEach(standNo -> {
			Optional<CCMProductDetailsModel> mdl = lst.stream()
					.filter(prod -> prod.getStand_id().equals(standNo.getLookup_id())).findFirst();

			if (mdl.isPresent()) {
				reply.add((CCMProductDetailsModel) mdl.get());
			} else {
				CCMProductDetailsModel prodDtls = new CCMProductDetailsModel();
				prodDtls.setStand_id(standNo.getLookup_id());
				prodDtls.setStandlkpMdl(standNo);
				double pdt_wt = heatDtls.getProductMasterMdl().getSection_wgt();
				Float pdt_steel_wgt = heatDtls.getSteel_ladle_wgt();			
				prodDtls.setCs_size(heatDtls.getHeatPlanMdl().getSmsCapabilityMstrModel().getLookupOutputSection()
						.getLookup_id());
				prodDtls.setCsSizeMdl(
						heatDtls.getHeatPlanMdl().getSmsCapabilityMstrModel().getLookupOutputSection());
				prodDtls.setCs_wgt(Float.valueOf(pdt_wt + "")); 
				
				Float f = (float) Math.round((pdt_steel_wgt / no_of_strands)*100)/100;
				prodDtls.setTot_wgt_batches(f);
				float cut_len = 0.0f;
				
				if(heatPlanLineLi.size() == 1){
					cut_len = heatPlanLineLi.get(0).getPlan_cut_length().floatValue();
					Double temp = (((pdt_steel_wgt / pdt_wt) * 1000) / cut_len);
					prodDtls.setHeat_plan_line_no(heatPlanLineLi.get(0).getHeat_line_id());
					prodDtls.setCut_length(heatPlanLineLi.get(0).getPlan_cut_length().floatValue());
					prodDtls.setNo_batches((float) Math.round(temp.floatValue() / no_of_strands));
				}
				
				reply.add(prodDtls);				
			}
		});
		if (!reply.isEmpty()) {
			return reply.get(0);
		} else {
			return new CCMProductDetailsModel();
		}
	}

	@RequestMapping(value = "/getStrandBatchSeq", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody List<CCMBatchDetailsModel> getStrandBatchSeq(@RequestParam Integer trns_sl_no,
			@RequestParam Integer strand_id, HttpSession session) {
		//List<LookupMasterModel> ccmStandNos = lookupmstrDao.getLookupDtlsByLkpTypeAndStatus("CCM_STAND_NO", 1);
		List<CCMProductDetailsModel> plst = ccmProdDtlsService.getccmProdDetailsByTrnsSLno(trns_sl_no);

		CCMHeatDetailsModel heatDtls = ccmheatDao.getCCMheatByid(trns_sl_no);

		List<CCMBatchDetailsModel> productBatch = new ArrayList<CCMBatchDetailsModel>();
		String lkp_type = "BILLET_MAX_LENGTH";
		Integer lkp_status = 1;
		List<LookupMasterModel> billet_max_length= lookupMstrDao.getLookupDtlsByLkpTypeAndStatus(lkp_type ,lkp_status);
        String ccmobj = ccmBatchDetailsService.getSteelQty(trns_sl_no,Integer.parseInt(billet_max_length.get(0).getLookup_code()));
		if(ccmobj.contains("TRUE")) {
				return productBatch;
		}
		else {
		BigDecimal batchWgt = new BigDecimal(0);
		plst.forEach(prod -> {
			List<CCMBatchDetailsModel> lst = ccmBatchDetailsService.getBatchDetailsByProduct(prod.getProd_trns_id());

			if (lst.isEmpty()) {
				float cut_len = 0.0f;
				//for (HeatPlanLinesDetails obj : heatDtls.getHeatPlanMdl().getHeatPlanLine()) {
					//cut_len = obj.getPlan_cut_length().floatValue();
					cut_len = prod.getCut_length();
					String batchIdseq = null;
					for (int i = 1; i <= prod.getNo_batches(); i++) {
						CCMBatchDetailsModel batchObj = new CCMBatchDetailsModel();
						if(batchIdseq == null)
                            batchIdseq = i+"";
                        else
                            batchIdseq = GenericClass.getNextSeqChar(batchIdseq);
                        String batchNo = heatDtls.getHeat_no() + prod.getStandlkpMdl().getLookup_code() + batchIdseq;
						
						batchObj.setBatch_no(batchNo);
						batchObj.setProduct(prod.getProd_trns_id());
						batchObj.setSection(
								heatDtls.getProductMasterMdl().getProdSecMtrlLookupModel().getLookup_value());
						batchObj.setHeat_plan_line_id(prod.getHeat_plan_line_no());
						batchObj.setHeat_plan_id(heatDtls.getHeat_plan_id());
						batchObj.setPlnd_len(cut_len);
						batchObj.setAct_len(cut_len);
						productBatch.add(batchObj);
					}
				//}
			} else {
				lst.forEach(batch -> {
					float cut_len = 0.0f;
					//for (HeatPlanLinesDetails obj : heatDtls.getHeatPlanMdl().getHeatPlanLine()) {
						//cut_len = obj.getPlan_cut_length().floatValue();
					cut_len = prod.getCut_length();
					//}
					batchWgt.add(new BigDecimal(batch.getAct_batch_wgt()));
					batch.setSection(heatDtls.getProductMasterMdl().getProdSecMtrlLookupModel().getLookup_value());
					batch.setPlnd_len(cut_len);
					// batch.setAct_batch_wgt(act_batch_wgt);
					productBatch.add(batch);

				});

			}
		});

		plst.forEach(prodA -> {

			if (prodA.getStand_id().equals(strand_id)) {				
				List<CCMBatchDetailsModel> lst = ccmBatchDetailsService
						.getBatchDetailsByProduct(prodA.getProd_trns_id());
				//Integer b_no = lst.size();
				String lastBatchNo = lst.get(lst.size()-1).getBatch_no();
				String batchIdseq = GenericClass.getNextSeqChar(lastBatchNo.substring(lastBatchNo.length() - 1));
				
				CCMBatchDetailsModel batchObj = productBatch.get(0);
				batchObj.setProduct(prodA.getProd_trns_id());
				batchObj.setBatch_trns_id(null);
				float cut_len = 0.0f;
                String batchNo = heatDtls.getHeat_no() + prodA.getStandlkpMdl().getLookup_code() + batchIdseq;
                cut_len = prodA.getCut_length();
				//String batchNo = heatDtls.getHeat_no() + prodA.getStandlkpMdl().getLookup_code() + (b_no.intValue() + 1) + "";
				batchObj.setBatch_no(batchNo);
				batchObj.setAct_len(cut_len);
				double pdt_wt = heatDtls.getProductMasterMdl().getSection_wgt();
				batchObj.setAct_batch_wgt((float) ((pdt_wt * cut_len) / 1000));
				batchObj.setCreated_by(Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
				batchObj.setCreated_date_time(new Date());
				ccmBatchDetailsService.saveProductBatch(batchObj);
				productBatch.add(batchObj);
				ccmProdDtlsService.updateProdBasedOnBatch(trns_sl_no);
			}
		});
			}
		return productBatch;
	}

	@RequestMapping(value = "/getBatchDetailsProd", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<CCMBatchDetailsModel> getBatchDetailsProd(@RequestParam Integer trns_sl_no) {
		List<CCMBatchDetailsModel> reply = new ArrayList<>();
		List<LookupMasterModel> ccmStandNos = lookupmstrDao.getLookupDtlsByLkpTypeAndStatus("CCM_STAND_NO", 1);
		List<CCMProductDetailsModel> plst = ccmProdDtlsService.getccmProdDetailsByTrnsSLno(trns_sl_no);
		ccmStandNos.forEach(standNo -> {
			Optional<CCMProductDetailsModel> mdl = plst.stream()
					.filter(prod -> prod.getStand_id().equals(standNo.getLookup_id())).findFirst();
			if (mdl.isPresent()) {
				List<CCMBatchDetailsModel> batches = ccmBatchDetailsService
						.getBatchDetailsByProduct(mdl.get().getProd_trns_id());
				
				reply.addAll(batches);
			} else {

			}
		});
		return reply;
	}

	@RequestMapping(value = "/getCCMByProductsByTrnsId", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<Integer> getCCMByProductsByTrnsId(@RequestParam Integer trns_sl_no, @RequestParam String mtrl_type) {
		List<CCMHeatConsMaterialsDetails> list = ccmheatDao.getCCMByProducts(trns_sl_no, mtrl_type);
		List<Integer> retLi = new ArrayList<Integer>();
		for(CCMHeatConsMaterialsDetails consObj : list) {
			retLi.add(consObj.getMtr_cons_si_no());
		}
		return retLi;
	}
	
	@RequestMapping(value = "/removeBatchEntry", method = RequestMethod.DELETE, produces = "application/json")
	public @ResponseBody RestResponse removeBatchEntry(@RequestBody CCMBatchDetailsModel batch, HttpSession session) {
		String result = "";
		result = ccmBatchDetailsService.removeBatchEntry(batch);
		ccmProdDtlsService.updateProdBasedOnBatch(batch.getProductMdl().getTrns_sl_no());
		if (result.equals(Constants.SAVE)) {
			return new RestResponse("SUCCESS", result);

		} else {
			return new RestResponse("FAILURE", result);
		}
	}

	@RequestMapping(value = "/saveCCMProdDtls", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody RestResponse getCCMprodDtls(@RequestBody List<CCMProductDetailsModel> values,
			HttpSession session) {
		// logger.info(CasterProductionController.class+"...getCasterHeatDetailsById-trns_sl_no--"+casterHeatPk);
		String result = "";
		List<LookupMasterModel> ccmStandNos = lookupmstrDao.getLookupDtlsByLkpTypeAndStatus("CCM_STAND_NO", 1);
		List<CCMProductDetailsModel> saveLi = new ArrayList<CCMProductDetailsModel>();
		try {
			values.forEach(prod -> {
				if (prod.getStatus().equals("Y")) {
					int lkp_id = ccmStandNos.stream()
							.filter(std_no -> std_no.getLookup_value().equals(prod.getStand_no()))
							.mapToInt(std_no -> std_no.getLookup_id()).findAny().getAsInt();
					List<CCMProductDetailsModel> produts = ccmProdDtlsService
							.getccmProdDetailsByTrnsSLno(prod.getTrns_sl_no());
					Optional<CCMProductDetailsModel> oldProdValue = produts.stream()
							.filter(pd -> pd.getProd_trns_id().equals(prod.getProd_trns_id())).findFirst();
					if (!oldProdValue.isPresent()) {
						HeatPlanLinesDetails heatPlanLineObj = heatPlanServ.getHeatPlanLineDetailsById(prod.getHeat_plan_line_no());
						prod.setCut_length(heatPlanLineObj.getPlan_cut_length().floatValue());
						prod.setStand_id(lkp_id);
						prod.setCreated_by(Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
						prod.setCreated_date_time(new Date());
						prod.setRecord_status(1);
						
						saveLi.add(prod);
						//ccmProdDtlsService.saveccmProdDetails(prod);
					} else {
						CCMProductDetailsModel oldProd = oldProdValue.get();
						oldProd.setCs_size(prod.getCs_size());
						oldProd.setCs_wgt(prod.getCs_wgt());
						oldProd.setCasting_speed(prod.getCasting_speed());
						oldProd.setUpdated_by(Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
						oldProd.setUpdated_date_time(new Date());
						
						saveLi.add(oldProd);
						//ccmProdDtlsService.updateccmProdDetails(oldProd);
					}
				}
			});
			result = ccmProdDtlsService.saveCcmProdDetailsList(saveLi);
			//result = Constants.SAVE;
		} catch (Exception ex) {
			ex.printStackTrace();
			result = Constants.SAVE_FAIL;
		}

		if (result.equals(Constants.SAVE)) {
			return new RestResponse("SUCCESS", result);

		} else {
			return new RestResponse("FAILURE", result);
		}

	}

	@Transactional
	@RequestMapping(value = "/saveCCMTundishDtls", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody RestResponse getCCMTundishDtls(@RequestBody CCMTundishDetailsModel model,
			@RequestParam("unit_id") Integer unit_id, HttpSession session) {

		String result = "";
		TundishStatusTrackModel trackStatus = tundishMstrService
				.getTundishTrackStatusByTundishIDAndHeat(model.getTun_id(), unit_id);
		if (model.getTun_trns_id() == null) {
			model.setCreated_date_time(new Date());
			model.setRecord_version(1);
			model.setRecord_status(1);
			model.setCreated_by(Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
			result = ccmTundishDtlsService.saveccmTundishDetails(model);
			if (trackStatus == null) {
				TundishStatusTrackModel Status = new TundishStatusTrackModel();
				Status.setTundishId(model.getTun_id());
				Status.setCreatedBy(Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
				Status.setCreatedDateTime(new Date());
				List<TundishTrnsHistoryModel> hstry = trnsHistoryDao
						.getTundishStatusHistoryByTundishId(model.getTun_id());
				if (!hstry.isEmpty()) {
					TundishTrnsHistoryModel tunHstry = hstry.get(0);
					Status.setTundishStatusId(tunHstry.getTrns_id());
					Status.setHeatIntundish(unit_id);
					// prevHeat=tundishMstrService.getTundishTrackStatusByTundishID(model.getTun_id());
					Status.setTundishLife(tunHstry.getTundish_life() + 1);
					// TundishTrnsHistoryModel
					// persistObj=trnsHistoryDao.getTundishById(tunHstry.getTrns_id());
					result = tundishMstrService.saveTundishTrackStatus(Status);
					// tunHstry.setTrns_id(persistObj.getTrns_id());
					tunHstry.setTundish_life(tunHstry.getTundish_life() + 1);
					tunHstry.setUpdated_date_time(new Date());
					tundishHstyService.saveTundishHistry(tunHstry);
				}
			}

		} else {
			model.setRecord_status(1);
			model.setUpdated_by(Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
			model.setUpdated_date_time(new Date());
			result = ccmTundishDtlsService.updateccmTundishDetails(model);
		}

		if (result.equals(Constants.SAVE)) {
			return new RestResponse("SUCCESS", result);

		} else {
			return new RestResponse("FAILURE", result);
		}

	}

	@RequestMapping(value = "/getCCMTundishDtls", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody CCMTundishDetailsModel getCCMtundishDtls(@RequestParam Integer trns_sl_no) {
		List<CCMTundishDetailsModel> lst = ccmTundishDtlsService.getccmTundishDtlsByTrnsSlNo(trns_sl_no);
		if (!lst.isEmpty()) {
			return lst.get(0);
		} else {
			return new CCMTundishDetailsModel();
		}
	}

	@RequestMapping(value = "/getSeqNoByHeat", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody CCMHeatSeqGenModel getSeqNoByHeat(Integer unit_id) {
		// logger.info(CasterProductionController.class+"...getCasterHeatDetailsById-trns_sl_no--"+casterHeatPk);
		CCMHeatSeqGenModel seqGenObj = ccmHeatSeqGenDao.getCCMHeatSeqNoByUnit(unit_id);
		CCMSeqGroupDetails segGroupObj = seqTrnsEvntService.getCCMSeqGroupDtlBySeqNoAndUnit(seqGenObj.getLast_fly_cast_no(), unit_id);
		seqGenObj.setSeq_group_status(segGroupObj.getSeq_status() );
		
		return seqGenObj;

	}

	@RequestMapping(value = "/getCCMProductBatchDetails", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<CCMBatchDetailsModel> getCCMProductBatchDetails(@RequestParam Integer trns_sl_no) {
		// List<CCMBatchDetailsModel> lst =
		// ccmTundishDtlsService.getccmTundishDtlsByTrnsSlNo(trns_sl_no);
		CCMHeatDetailsModel heatDtls = ccmheatDao.getCCMheatByid(trns_sl_no);
		List<CCMProductDetailsModel> products = ccmProdDtlsService.getccmProdDetailsByTrnsSLno(trns_sl_no);
		List<CCMBatchDetailsModel> productBatch = new ArrayList<CCMBatchDetailsModel>();
		products.forEach(prod -> {
			List<CCMBatchDetailsModel> lst = ccmBatchDetailsService.getBatchDetailsByProduct(prod.getProd_trns_id());
			if (lst.isEmpty()) {
				float cut_len = 0.0f;
				String batchIdseq = null;
				//for (HeatPlanLinesDetails obj : heatDtls.getHeatPlanMdl().getHeatPlanLine()) {
					//cut_len = obj.getPlan_cut_length().floatValue();     
					cut_len = prod.getCut_length();
					for (int i = 1; i <= prod.getNo_batches(); i++) {
						CCMBatchDetailsModel batchObj = new CCMBatchDetailsModel();
						if(batchIdseq == null)
							batchIdseq = i+"";
						else
							batchIdseq = GenericClass.getNextSeqChar(batchIdseq);
						String batchNo = heatDtls.getHeat_no() + prod.getStandlkpMdl().getLookup_code() + batchIdseq;
						batchObj.setBatch_no(batchNo);
						batchObj.setProduct(prod.getProd_trns_id());
						batchObj.setProductMdl(prod);
						batchObj.setSection(
								heatDtls.getProductMasterMdl().getProdSecMtrlLookupModel().getLookup_value());
						//batchObj.setHeat_plan_line_id(obj.getHeat_line_id());
						//batchObj.setHeat_plan_id(obj.getHeat_plan_id());
						batchObj.setHeat_plan_line_id(prod.getHeat_plan_line_no());
						batchObj.setHeat_plan_id(heatDtls.getHeat_plan_id());
						batchObj.setPlnd_len(cut_len);
						batchObj.setAct_len(cut_len);
						double pdt_wt = heatDtls.getProductMasterMdl().getSection_wgt();
						batchObj.setAct_batch_wgt((float) ((pdt_wt * cut_len) / 1000));
						
						productBatch.add(batchObj);
					}
				//}
			} else {
				lst.forEach(batch -> {
					float cut_len = 0.0f;
					//for (HeatPlanLinesDetails obj : heatDtls.getHeatPlanMdl().getHeatPlanLine()) {
						//cut_len = obj.getPlan_cut_length().floatValue();
					cut_len = prod.getCut_length();
					//}
					batch.setSection(heatDtls.getProductMasterMdl().getProdSecMtrlLookupModel().getLookup_value());
					batch.setPlnd_len(cut_len);
					productBatch.add(batch);
				});
			}
		});
		
		return productBatch;
	}

	
	@RequestMapping(value = "/saveCCMBatchDtls", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody RestResponse saveCCMProdBatchDtls(@RequestBody List<CCMBatchDetailsModel> batches,
			Integer trns_sl_no, HttpSession session) {
		
		List<LookupMasterModel> udCodeLi = lookupmstrDao.getLookupDtlsByLkpTypeAndStatus("SAP_UD_CODE", 1);
		LookupMasterModel model = udCodeLi.stream().filter(p -> p.getLookup_code().equals("OK"))
				.collect(Collectors.toList()).get(0);
		String result = "";
		
		try {
			if (!batches.isEmpty()) {
				batches.forEach(batch -> {
					if (batch.getBatch_trns_id() != null) {
						batch.setUpdated_by(Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
						batch.setUpdated_date_time(new Date());
						//ccmBatchDetailsService.updateProductBatch(batch);

					} else {
						batch.setCreated_by(Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
						batch.setCreated_date_time(new Date());
						batch.setRecord_status(1);
						batch.setProd_date(new Date());
						batch.setSap_ud_code(model.getLookup_id());
						
						//ccmBatchDetailsService.saveProductBatch(batch);
					}
				});

				//result = Constants.SAVE;
			}
			//ccmProdDtlsService.updateProdBasedOnBatch(trns_sl_no);
			result = ccmBatchDetailsService.saveCCMBatchDetails(batches, trns_sl_no);
		} catch (Exception ex) {
			ex.printStackTrace();
			result = Constants.SAVE_FAIL;
		}

		if (result.equals(Constants.SAVE)) {
			return new RestResponse("SUCCESS", result);

		} else {
			return new RestResponse("FAILURE", result);
		}

	}

	@RequestMapping(value = "/getChemDtlsByAnalysis", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<HeatChemistryChildDetails> getChemDtlsByAnalysis(@RequestParam Integer analysis_id,
			@RequestParam Integer psn_id) {
		List<LookupMasterModel> analysisTypes = lookupmstrDao.getLookupDtlsByLkpTypeAndStatus("CHEM_LEVEL", 1);
		int lkp_id = analysisTypes.stream().filter(lkp -> lkp.getLookup_value().equals(Constants.LRF_LIFT_CHEM))
				.mapToInt(mapper -> mapper.getLookup_id()).findFirst().getAsInt();
		List<HeatChemistryChildDetails> sample_li = heatProcessEventService.getChemDtlsByAnalysis(lkp_id, psn_id);
		return sample_li;
	}
	
	@RequestMapping(value = "/getTundishChem", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<HeatChemistryChildDetails> getTundishChem(@RequestParam Integer analysis_id,
			@RequestParam Integer psn_id,String actual_sample,
			String heat_id,@RequestParam Integer heat_counter) throws IOException {
		List<LookupMasterModel> analysisTypes = lookupmstrDao.getLookupDtlsByLkpTypeAndStatus("CHEM_LEVEL", 1);
		int lkp_id = analysisTypes.stream().filter(lkp -> lkp.getLookup_value().equals(Constants.LRF_LIFT_CHEM))
				.mapToInt(mapper -> mapper.getLookup_id()).findFirst().getAsInt();
		List<HeatChemistryChildDetails> sample_li = heatProcessEventService.getTundishChem(lkp_id, psn_id, actual_sample, heat_id,heat_counter);
		return sample_li;
	}

	// process param
	@RequestMapping(value = "/getProcessParam", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<CCMHeatProcessParameterDetails> getProcessParamStrandWise(@RequestParam Integer unit_id,
			@RequestParam String heat_no, @RequestParam Integer heat_counter) {

		return ccmProcParmService.getProcessParamForAllStrand(unit_id, heat_no, heat_counter);
	}

	@RequestMapping(value = "/saveCCMProcParam", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody RestResponse saveProcessParam(@RequestBody List<CCMHeatProcessParameterDetails> procparams,
			String heatNo, HttpSession session) {
		String user = session.getAttribute("USER_APP_ID").toString();
		String result = ccmProcParmService.saveOrUpdateProcParam(procparams, user, heatNo);
		if (result.equals(Constants.SAVE)) {
			return new RestResponse("SUCCESS", result);

		} else {
			return new RestResponse("FAILURE", result);
		}
	}
	
	@RequestMapping(value = "/activityDelayMstrBySubunit", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<DelayEntryDTO> getDelayMasterBySubUnit(@RequestParam("sub_unit_id") Integer sub_unit_id, @RequestParam("trns_si_no") Integer trns_si_no, 
			@RequestParam("heat_no") String heat_no, @RequestParam("heat_counter") Integer heat_counter) {

		return casterProdService.getDelayEntriesBySubUnitAndHeat(sub_unit_id, trns_si_no, heat_no, heat_counter);
	}

	@RequestMapping(value = "/TransDelayDtlsUpdate", method = RequestMethod.POST)
	public @ResponseBody RestResponse UpdateDelayDetailsWithHdr(HttpSession session, HttpServletRequest req, @RequestParam("seq_group_no") Integer seq_no,
			@RequestParam("trans_delay_entry_hdr_id") Integer trans_delay_entry_hdr_id, @RequestParam("sub_unit") Integer sub_unit) {
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
			
			if(seq_no != 0){
				CCMSeqGroupDetails seqObj;
				boolean isDelayEntered = false;
				List<TransDelayEntryHeader> hdrObjLi = transDelayEntryHeaderService.getTransDelayHeaderByTransId(seq_no, sub_unit);
				for(TransDelayEntryHeader hdrObj : hdrObjLi){
					int tot_duration = 0;
					List<TransDelayDetailsModel> delayDtlLi = transDelayEntryDtlsService.getTransDelayDtlsByDelayDdrId(hdrObj.getTrns_delay_entry_hdr_id());
					for(TransDelayDetailsModel d : delayDtlLi){
						tot_duration = tot_duration + d.getDelay_dtl_duration();
					}
					if(tot_duration == hdrObj.getTotal_delay())
						isDelayEntered = true;
					else{
						isDelayEntered = false;
						break;
					}
				}
				if(isDelayEntered){
					seqObj = seqTrnsEvntService.getCCMSeqGroupDtlById(seq_no);
					seqObj.setDelay_entry("Y");
					seqObj.setUpdated_by(Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
					seqObj.setUpdated_date_time(new Date());
				
					result = seqTrnsEvntService.updateCCMSeqGroupDtls(seqObj);
				}
			}
			
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
	
	@RequestMapping(value = "/TransDelayDtlsSave", method = RequestMethod.POST)
	public @ResponseBody RestResponse SaveDelayDetailsWithHdr(@ModelAttribute TransDelayDetailsModel transdelayMstr, HttpSession session,
			@RequestParam("trans_delay_entry_hdr_id") Integer trans_delay_entry_hdr_id, @RequestParam("seq_group_no") Integer seq_no, @RequestParam("sub_unit") Integer sub_unit) {
		try {
			String result = "";
			transdelayMstr.setTrans_delay_dtl_id(null);
			transdelayMstr.setDelay_entry_hdr_id(trans_delay_entry_hdr_id);
			transdelayMstr.setCreatedDateTime(new Date());
			transdelayMstr.setCreatedBy(Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
			result = transDelayEntryDtlsService.saveTransDelayEntryDtls(transdelayMstr);
			if(seq_no != 0){
				CCMSeqGroupDetails seqObj;
				boolean isDelayEntered = false;
				List<TransDelayEntryHeader> hdrObjLi = transDelayEntryHeaderService.getTransDelayHeaderByTransId(seq_no, sub_unit);
				
				for(TransDelayEntryHeader hdrObj : hdrObjLi){
					int tot_duration = 0;
					List<TransDelayDetailsModel> delayDtlLi = transDelayEntryDtlsService.getTransDelayDtlsByDelayDdrId(hdrObj.getTrns_delay_entry_hdr_id());
					
					for(TransDelayDetailsModel d : delayDtlLi){
						tot_duration = tot_duration + d.getDelay_dtl_duration();
					}
					
					if(tot_duration == hdrObj.getTotal_delay())
						isDelayEntered = true;
					else{
						isDelayEntered = false;
						break;
					}
				}
				if(isDelayEntered){
					seqObj = seqTrnsEvntService.getCCMSeqGroupDtlById(seq_no);
					seqObj.setDelay_entry("Y");
					seqObj.setUpdated_by(Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
					seqObj.setUpdated_date_time(new Date());
				
					result = seqTrnsEvntService.updateCCMSeqGroupDtls(seqObj);
				}
			}
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
	
	@RequestMapping(value = "/TransDelaySave", method = RequestMethod.POST, headers = {"Content-type=application/json" })
	public @ResponseBody RestResponse TransDelaySave(@RequestParam("trns_si_no") Integer trns_si_no, @RequestBody List<DelayEntryDTO> transDetails, HttpSession session) {
		String result = "";
		try {		
			for (int i = 0; i < transDetails.size(); i++) {
				DelayEntryDTO delayEntryDTO = transDetails.get(i);
				TransDelayEntryHeader delayHdrObj = new TransDelayEntryHeader();
				delayHdrObj.setActivity_start_time(delayEntryDTO.getStart_time());
				delayHdrObj.setActivity_end_time(delayEntryDTO.getEnd_time());
				if(delayEntryDTO.getDuration()!=null) {
					delayHdrObj.setActivity_duration(delayEntryDTO.getDuration().intValue());
				}
				if (delayEntryDTO.getDelay() != null) {
					delayHdrObj.setTotal_delay(delayEntryDTO.getDelay().intValue());
				}
				delayHdrObj.setCorrective_action(delayEntryDTO.getCorrective_action());
				
				if (delayEntryDTO.getTransDelayEntryhdr() == null) {// saving data
					delayHdrObj.setTrns_delay_entry_hdr_id(null);
					delayHdrObj.setActivity_delay_id(delayEntryDTO.getActivity_master().getActivity_delay_id());
					delayHdrObj.setTrans_heat_id(trns_si_no);
					delayHdrObj.setCreatedDateTime(new Date());
					delayHdrObj.setCreatedBy(Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
					
					result = transDelayEntryHeaderService.saveTransDelayEntryHeader(delayHdrObj);
				} else {// updating data				
					delayHdrObj.setUpdatedDateTime(new Date());
					delayHdrObj.setUpdatedBy(Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
				
					result = transDelayEntryHeaderService.updateTransDelayEntryHeader(delayHdrObj);
				}
			}
			return new RestResponse("SUCCESS", result);
		} catch (Exception e) {
			e.printStackTrace();
			return new RestResponse("FAILURE", e.getMessage());
		}
	}
	
	@RequestMapping(value = "/getMouldTubeLife", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody MtubeTrnsModel getMouldTubeLife(@RequestParam("ccm_mtube_trns_id") Integer mtube_trns_id) {
		logger.info(CasterProductionController.class + "...getMouldTubeLife-trns_sl_no--" + mtube_trns_id);
		MtubeTrnsModel obj = mtubeDao.getMtubeTrnsByid(mtube_trns_id);
		MtubeTrnsModel retObj = casterProdService.getMtubelife(obj.getCcm_mtube_sl_no());
		retObj.setMtubeMasterMdl(obj.getMtubeMasterMdl());
		
		return retObj;
	}
	
	@RequestMapping(value = "/updateToCCMMould", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody RestResponse getCCMMouldDtls(@RequestBody List<CCMProductDetailsModel> values,
			Integer trns_sl_no, HttpSession session) {
		String result = "";
		try {
			List<LookupMasterModel> strandNos = lookupmstrDao.getLookupDtlsByLkpTypeAndStatus("CCM_STAND_NO", 1);
			List<CCMProductDetailsModel> lst = ccmProdDtlsService.getccmProdDetailsByTrnsSLno(trns_sl_no);
			for(CCMProductDetailsModel prod:values) {
				if(prod.getCcm_mtube_trns_id()!=null) {
				
				int lkp_id = strandNos.stream().filter(lkp -> lkp.getLookup_value().equals(prod.getStand_no()))
						.mapToInt(mapper -> mapper.getLookup_id()).findFirst().getAsInt();
				
				Optional<CCMProductDetailsModel> ccmProdObj = lst.stream()
						.filter(old_strm -> old_strm.getStand_id().equals(lkp_id)).findFirst();
				if (ccmProdObj.isPresent()) {

					CCMProductDetailsModel uObj = ccmProdObj.get();
					if (uObj != null) {
						if (uObj.getMtube_life() == null || uObj.getMtube_life() == 0) {// restricting to life
																						// continuous update
							MtubeTrnsModel mTrnsMdl = mtubeDao.getMtubeTrnsByid(prod.getCcm_mtube_trns_id());
							uObj.setCcm_mtube_trns_id(prod.getCcm_mtube_trns_id());
							uObj.setUpdated_date_time(new Date());
							uObj.setUpdated_by(Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
							uObj.setRecord_status(1);
							uObj.setMtube_life(mTrnsMdl.getMtube_life() + 1);							
							// updating Mtube Trns Entry
							mTrnsMdl.setMtube_life(mTrnsMdl.getMtube_life() + 1);
							mTrnsMdl.setRecord_status(1);
							mTrnsMdl.setMtube_status(MTSTAUS.RUNNING.toString());
							mTrnsMdl.setUpdated_date_time(new Date());
							mTrnsMdl.setUpdated_by(Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
							mTrnsMdl.setLast_strand_no(lkp_id);
							result=mtubeMstrService.validateMtubeTrnsStatus(mTrnsMdl.getCcm_mtube_sl_no(),mTrnsMdl.getJacket_no(),Constants.MTUBE_AT_CCM_SAVE);							
							if(result.equals("OK"))
							{
								result = mtubeDao.updateccmMtubeTrns(mTrnsMdl);
								ccmProdDtlsService.updateccmProdDetails(uObj);
								result = Constants.SAVE;
							}
							else
							{
								return new RestResponse("FAILURE", result);							}							
						}
					}
				}
			}
			};					
		} catch (Exception ex) {
			result = Constants.SAVE_FAIL;
			ex.printStackTrace();
		}
		if (result.equals(Constants.SAVE)) {
			return new RestResponse("SUCCESS", result);

		} else {
			return new RestResponse("FAILURE", result);
		}

	}

	@RequestMapping(value = "/updateToCCMMouldClean", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody RestResponse setMouldClean(@RequestBody List<CCMProductDetailsModel> values,
			HttpSession session) {
		String result = "";
		try {
			for(CCMProductDetailsModel prod:values) {
				if (prod.getClean().equals("Y")) {					
					MtubeTrnsModel mTrnsMdl = mtubeDao.getMtubeTrnsByid(prod.getCcm_mtube_trns_id());
					result=mtubeMstrService.validateMtubeTrnsStatus(mTrnsMdl.getCcm_mtube_sl_no(),mTrnsMdl.getJacket_no(),Constants.MTUBE_AT_CCM_CLEAN);							
					if(result.equals("OK"))
					{
					mTrnsMdl.setCleaned_by(Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
					mTrnsMdl.setCleaned_date(new Date());
					mTrnsMdl.setUpdated_date_time(new Date());
					mTrnsMdl.setMtube_status(MTSTAUS.DISCARD.toString());
					mTrnsMdl.setUpdated_by(Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
					mtubeDao.updateccmMtubeTrns(mTrnsMdl);
					mTrnsMdl.setCcm_mtube_trns_id(null);
					mTrnsMdl.setCreated_by(Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
					mTrnsMdl.setCreated_date_time(new Date());
					mTrnsMdl.setAssembled_date(null);
					mTrnsMdl.setAssembled_by(null);
					mTrnsMdl.setJacket_no(null);
					mTrnsMdl.setIs_cleaned("Y");
					mTrnsMdl.setMtube_status(MTSTAUS.CLEANED.toString());
					mTrnsMdl.setMtube_life(0);
					mtubeDao.saveccmMtubeTrns(mTrnsMdl);
				  }
				else
				{
						return new RestResponse("FAILURE", result);							}							
				}
			}
			result = Constants.SAVE;
		} catch (Exception ex) {
			result = Constants.SAVE_FAIL;
			ex.printStackTrace();
		}
		if (result.equals(Constants.SAVE)) {
			return new RestResponse("SUCCESS", result);

		} else {
			return new RestResponse("FAILURE", result);
		}

	}

	@RequestMapping(value = "/updateToCCMMouldScrap", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody RestResponse setMouldScrap(@RequestBody List<CCMProductDetailsModel> values,
			HttpSession session) {
		String result = "";
		try {
			  for(CCMProductDetailsModel prod:values) {
				if (prod.getClean().equals("Y")) {
					MtubeTrnsModel mTrnsMdl = mtubeDao.getMtubeTrnsByid(prod.getCcm_mtube_trns_id());
					result=mtubeMstrService.validateMtubeTrnsStatus(mTrnsMdl.getCcm_mtube_sl_no(),mTrnsMdl.getJacket_no(),Constants.MTUBE_AT_CCM_DISCARD);							
					if(result.equals("OK"))
					{
					mTrnsMdl.setMtube_status(MTSTAUS.DISCARD.toString());
					mTrnsMdl.setUpdated_date_time(new Date());
					mTrnsMdl.setUpdated_by(Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
					mtubeDao.updateccmMtubeTrns(mTrnsMdl);
					}
					else
					{
							return new RestResponse("FAILURE", result);							}							
					}
			     }
			result = Constants.SAVE;
		} catch (Exception ex) {
			result = Constants.SAVE_FAIL;
			ex.printStackTrace();
		}
		if (result.equals(Constants.SAVE)) {
			return new RestResponse("SUCCESS", result);

		} else {
			return new RestResponse("FAILURE", result);
		}
	}

	@RequestMapping(value = "/updateCCMHeatRetQty", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody RestResponse setCCMRetQty(@RequestBody CCMHeatDetailsModel model, Float return_qty,
			String heat_no, HttpSession session) {
		model.setReturn_qty(return_qty);
		String result = casterProdDao.saveCCMretQty(model, session.getAttribute("USER_APP_ID").toString(), heat_no);
		if (result.equals(Constants.SAVE)) {
			return new RestResponse("SUCCESS", result);
		} else {
			return new RestResponse("FAILURE", result);
		}
	}

	@RequestMapping(value = "/completeCCMHeatTrnsaction", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody RestResponse completeCCMHeatTrnsaction(String heat_no, Integer trns_sl_no, Integer heat_counter,
			HttpSession session) {
		
		String result = "";
		//try {
			HeatStatusTrackingModel heatTrackObj = casterProdDao.getHeatStatusTrackingModelByHeatIdWithoutStatus(heat_no, heat_counter);
			
			
			heatTrackObj.setUnit_process_status(Constants.HEAT_TRACK_STATUS_COMPLETED);
			heatTrackObj.setMain_status(Constants.HEAT_TRACK_STATUS_PRD_POST);
			heatTrackObj.setProdPostedBy(Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
			heatTrackObj.setProdPostedDate(new Date());
			heatTrackObj.setInspection_done("N");
			//heatTrackObj.setUpdatedBy(Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
			//heatTrackObj.setUpdatedDateTime(new Date());
			//-----LP_INTERFACE START--------------
			CCMHeatDetailsModel ccmObj = ccmheatDao.getCCMheatByid(trns_sl_no);	
			HeatPlanDetails list = heatPlanServ.getHeatPlanDetailsById(ccmObj.getHeat_plan_line_no());
			IfacesmsLpDetailsModel ifacObj= new IfacesmsLpDetailsModel();
			ifacObj.setMsg_id(null);
			ifacObj.setSch_id(list.getLp_schd_id());
            ifacObj.setPlanned_heat_id(list.getLp_plan_heat_id());
            ifacObj.setActual_heat_id(ccmObj.getHeat_no());
            ifacObj.setPrev_sch_id(null);
            ifacObj.setPrev_planned_heat_id(null);
            ifacObj.setGrade(null);
            ifacObj.setEvent_code("TCMPROD");
            ifacObj.setInterface_status(0);
            ifacObj.setError_msg(null);
            ifacObj.setCreatedBy(Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
            ifacObj.setCreated_Date(new Date());
            ifacObj.setModified_by(null);
            ifacObj.setModified_date(null);
            
			
			result = ccmBatchDetailsService.ccmProductionPosting(heatTrackObj,ifacObj);
		
		/*}catch (Exception e) {
			e.printStackTrace();
			logger.error(CasterProductionController.class
					+ " Inside CCMProductionPosting Exception..", e);
		}*/
		if(result.equals(Constants.PROD_POST)){
			return new RestResponse("SUCCESS", result);
		}else{
			return new RestResponse("FAILURE", result);
		}
		
		/*String rply = "";
		HeatStatusTrackingModel result = casterProdDao.getHeatStatusTrackingModelByHeatIdWithoutStatus(heat_no, heat_counter);
		result.setUnit_process_status(Constants.HEAT_TRACK_STATUS_COMPLETED);
		result.setUpdatedBy(Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
		result.setUpdatedDateTime(new Date());
		rply = casterProdDao.updateHeatStatusTrack(result);
		CCMHeatDetailsModel heatDtls = ccmheatDao.getCCMheatByid(trns_sl_no);

		SteelLadleTrackingModel ladlemdl = steelLaleService
				.getSteelLadleTracking(heatDtls.getSteelLadleObj().getSteel_ladle_si_no());
		ladlemdl.setLadle_status(1);// 1 -AVAILABLE
		steelLaleService.steelLadleStatusUpdate(ladlemdl);
		//result = ccmBatchDetailsService.ccmProductionPosting(result);
		if (rply.equals(Constants.SAVE)) {
			return new RestResponse("SUCCESS", rply);
		} else {
			//casterProdDao.sendFailedHeatData(heat_no);
			return new RestResponse("FAILURE", rply);
		}*/
	}

	@RequestMapping(value = "/getCCMProdBatchDtlsByTrnsId", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<CCMHeatDetailsModel> getCCMProdBatchDtlsByTrnsId(Integer trns_sl_no,
			HttpSession session) {
		List<CCMProductDetailsModel> produts = ccmProdDtlsService.getccmProdDetailsByTrnsSLno(trns_sl_no);
		boolean dataSaved = false;
		Double totWeight = 0.0;
		List<CCMHeatDetailsModel> ccmHeatLi = new ArrayList<CCMHeatDetailsModel>();
		List<CCMBatchDetailsModel> totBatchLi = new ArrayList<CCMBatchDetailsModel>();
		for(CCMProductDetailsModel ccmProdObj : produts){
			List<CCMBatchDetailsModel> batchLi = ccmBatchDetailsService.getBatchDetailsByProduct(ccmProdObj.getProd_trns_id());
			
			if(batchLi.size() == 0){
				if(ccmProdObj.getNo_batches() == 0)
					dataSaved = true;
				else{
					dataSaved = false;
					break;
				}
			}else{
				dataSaved = true;
				for(CCMBatchDetailsModel batchObj : batchLi) {
					totWeight = totWeight + batchObj.getAct_batch_wgt();
					totBatchLi.add(batchObj);
				}
			}
		}
		if(dataSaved) {
			DecimalFormat df = new DecimalFormat("#.###");		
			CCMHeatDetailsModel heatObj = new CCMHeatDetailsModel();
			heatObj.setTotNoOfBatches(totBatchLi.size());
			heatObj.setTotBatchWeight(Double.parseDouble(df.format(totWeight)));
			ccmHeatLi.add(heatObj);
			
			return ccmHeatLi;
		}else
			return new ArrayList<CCMHeatDetailsModel>();
	}
	
	@RequestMapping(value = "/getCCMProdDtlsByTrnsId", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<CCMProductDetailsModel> getCCMProdDtlsByTrnsId(Integer trns_sl_no,
			HttpSession session) {
		List<CCMProductDetailsModel> produts = ccmProdDtlsService.getccmProdDetailsByTrnsSLno(trns_sl_no);
		boolean dataSaved = false;
		
		if(produts.size() == 0)
			dataSaved = false;
		else
			dataSaved = true;

		if(dataSaved)
			return produts;
		else
			return new ArrayList<CCMProductDetailsModel>();
	}

	@RequestMapping(value = "/getCCMSeqEvents", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<SeqTransactionEvent> getCCMSeqEvents(@RequestParam Integer groupNo,
			@RequestParam Integer sub_unit_id, HttpSession session) {
		List<SeqTransactionEvent> seqEvnts = seqTrnsEvntService.getSeqTrnasEventsByGroupNo(groupNo, sub_unit_id);
		List<SeqTransactionEvent> seqEvntsRply = new ArrayList<SeqTransactionEvent>();
		List<LookupMasterModel> ccmSeqLkpevents = lookupmstrDao.getLookupDtlsByLkpTypeAndStatus("CCM_SEQ_EVENTS", 1);
		ccmSeqLkpevents.forEach(lkpSeqEvt -> {

			Optional<SeqTransactionEvent> mdl = seqEvnts.stream()
					.filter(trns -> trns.getEvent_id().equals(lkpSeqEvt.getLookup_id())).findFirst();
			if (mdl.isPresent()) {
				seqEvntsRply.add(mdl.get());
			} else {
				SeqTransactionEvent obj = new SeqTransactionEvent();
				obj.setEventLkpMdl(lkpSeqEvt);
				obj.setEvent_id(lkpSeqEvt.getLookup_id());
				seqEvntsRply.add(obj);
			}

		});

		return seqEvntsRply;
	}

	@RequestMapping(value = "/saveOrUpdateCCMSeqEvents", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody RestResponse getCCMSeqEvents(@RequestBody List<SeqTransactionEvent> values,
			HttpSession session) {
		String result = "";
		int strandsCount = 0;
		Integer seqGroupId = null;
		
		try {
			for(SeqTransactionEvent seqEvt : values){
				if (seqEvt.getEvent_sl_no() == null) {// new entry
					seqEvt.setCreated_by(Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
					seqEvt.setCreated_date_time(new Date());
					seqEvt.setRecord_status(1);
					seqTrnsEvntService.saveCCMSeqEvent(seqEvt);
				} else {
					seqEvt.setUpdated_by(Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
					seqEvt.setUpdated_date_time(new Date());
					seqEvt.setRecord_status(1);
					seqTrnsEvntService.updateCCMSeqEvent(seqEvt);
				}
				if(seqEvt.getEventLkpMdl().getLookup_value().contains("CAST STOP")){
					strandsCount = strandsCount + 1;
					seqGroupId = seqEvt.getGroup_seq_no();
				}
			}
			if(strandsCount == 3){
				CCMSeqGroupDetails seqGroupTrnsObj = seqTrnsEvntService.getCCMSeqGroupDtlById(seqGroupId);
				seqGroupTrnsObj.setSeq_status(0);
				seqGroupTrnsObj.setSeq_close_by(Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
				seqGroupTrnsObj.setSeq_close_date_time(new Date());
				seqGroupTrnsObj.setUpdated_by(Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
				seqGroupTrnsObj.setUpdated_date_time(new Date());
				
				boolean isDelay = true;
				List<DelayEntryDTO> seqDelays = casterProdService.getDelayEntriesBySubUnitAndHeat(seqGroupTrnsObj.getSub_unit_id() , seqGroupId, "0", 0);
				
				for(DelayEntryDTO delayObj : seqDelays){
					if(delayObj.getDelay() == null || delayObj.getDelay() == 0)
						isDelay = false;
					else{
						isDelay = true;
						break;
					}
				}
				if(!isDelay){
					seqGroupTrnsObj.setDelay_entry("Y");					
				}
				seqTrnsEvntService.updateCCMSeqGroupDtls(seqGroupTrnsObj);
			}
			result = Constants.SAVE;
		} catch (Exception ex) {
			result = Constants.SAVE_FAIL;
		}
		if (result.contains(Constants.SAVE)) {
			return new RestResponse("SUCCESS", result);

		} else {
			return new RestResponse("FAILURE", result);
		}
	}
	@RequestMapping(value = "/getSeqGroupNos", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<CCMSeqGroupDetails> getSeqGroupNos(Integer unit_id) {
		
		return casterProdDao.getGroupSeqNos(unit_id);
	}
	
	@RequestMapping(value = "/getCloseSeqGroupNos", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<CCMSeqGroupDetails> getCloseSeqGroupNos(Integer unit_id) {
		
		return casterProdDao.getCloseGroupSeqNos(unit_id);
	}
	
	@RequestMapping(value = "/getSeqGroupNosHeats", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<CCMHeatDetailsModel> getSeqGroupNosHeats(Integer unit_id,String  groupSeqNo) {
		
		return casterProdDao.getSeqGroupNosHeats(unit_id, groupSeqNo);
	}

	@RequestMapping(value = "/getCCMByProducts", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<CCMHeatConsMaterialsDetails> getCCMMtrlDetailsByType(String mtrlType,
			Integer ccm_trns_sno, String psn_no, Float heat_qty) {
		return heatProceeEventDao.getCCMMtrlDetailsByType(mtrlType, ccm_trns_sno, psn_no, heat_qty);
	}

	@RequestMapping(value = "/saveOrUpdateCCMByProd", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody RestResponse saveOrUpdateCCMByProd(@RequestBody List<CCMHeatConsMaterialsDetails> values,
			Integer trns_ccm_si_no, HttpSession session) {
		String result = "";
		try {
			values.forEach(con -> {
				if (con.getMtr_cons_si_no() == null) {// new entry
					if(con.getQty() != null){
					con.setRecord_status(1);
					con.setCreatedBy(Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
					con.setCreatedDateTime(new Date());
					con.setTrns_ccm_si_no(trns_ccm_si_no);

					casterProdDao.saveORUpdatetCCMConsMtrl(con);
					}
				} else {
					if(con.getQty() != null){
					con.setRecord_status(1);
					con.setUpdatedBy(Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
					con.setUpdatedDateTime(new Date());
					con.setTrns_ccm_si_no(trns_ccm_si_no);
					casterProdDao.saveORUpdatetCCMConsMtrl(con);
					}
				}
			});
			result = Constants.SAVE;
		} catch (Exception ex) {
			result = Constants.SAVE_FAIL;
		}
		if (result.contains(Constants.SAVE)) {
			return new RestResponse("SUCCESS", result);

		} else {
			return new RestResponse("FAILURE", result);
		}
	}
	
	@RequestMapping(value = "/getCcmHeatByTrns", method = RequestMethod.GET, produces = "application/json")//Getting chemistry hdr,child by heat_no via trns_sl_no
	public @ResponseBody CCMHeatDetailsModel testheat(Integer trns_sl_no) {
		Integer lift_chem_id = commonDao.getLookupIdByQuery("select lookup_id from LookupMasterModel where lookup_type='CHEM_LEVEL' and lookup_value='"+Constants.LRF_LIFT_CHEM+"' and lookup_status=1");
		CCMHeatDetailsModel retObj = ccmheatDao.getCCMheatByid(trns_sl_no);	
		retObj.setLiftChemId(lift_chem_id);
		
		return retObj;
	}
	
	@RequestMapping(value = "/sendChemToSap", method = RequestMethod.POST, produces = "application/json")//Sending chemistry data to sap intf table
	public @ResponseBody RestResponse sendChemToSap(@RequestParam String heatno,@RequestParam String heatcounter) throws SQLException, IOException {
		Integer result;
		result = ccmheatDao.sendChemToSap(heatno, heatcounter);
		if (result==1) {
			return new RestResponse("SUCCESS", Constants.SAVE);

		} else {
			return new RestResponse("FAILURE", Constants.SAVE_FAIL);
		}
	}
	
	@RequestMapping(value = "/getCCMCompletedHeats", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<CommonCombo> getCCMCompletedHeats(@RequestParam Integer sub_unit_id) {
		
		return ccmheatDao.getCCMCompletedHeats(sub_unit_id);
	}
	
	@RequestMapping(value = "/getBatchNo", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody RestResponse getBatchNo(@RequestParam Integer trns_sl_no, @RequestParam Integer strand_id, @RequestParam String strand_no) {
		String retValue;
		CCMHeatDetailsModel heatDtls = ccmheatDao.getCCMheatByid(trns_sl_no);
		List<CCMProductDetailsModel> ccmProdLi = (List<CCMProductDetailsModel>) heatDtls.getCcmProdHeatDtls();// ccmProdDtlsService.getccmProdDetailsByTrnsSLno(trns_sl_no);
		CCMProductDetailsModel prodObj = ccmProdLi.stream().filter(p -> p.getStand_id().equals(strand_id)).collect(Collectors.toList()).get(0);
		String batchIdseq; Integer ccmProdId;
		if(prodObj != null) {
			List<CCMBatchDetailsModel> batchLi = (List<CCMBatchDetailsModel>) prodObj.getCcmBatchDtls(); //ccmBatchDetailsService.getBatchDetailsByProduct(prodObj.getProd_trns_id());
			batchLi.sort((CCMBatchDetailsModel s1, CCMBatchDetailsModel s2) -> s1.getBatch_no().compareTo(s2.getBatch_no()));
			String lastBatch = batchLi.get(batchLi.size()-1).getBatch_no();
			batchIdseq = GenericClass.getNextSeqChar(lastBatch.substring(lastBatch.length()-1));
			ccmProdId = prodObj.getProd_trns_id();
		}else {
			batchIdseq = 1+"";
			ccmProdId = 0;
		}
		String batchNo = heatDtls.getHeat_no() + strand_no + batchIdseq;
		retValue = batchNo+'_'+ccmProdId;
		
		return new RestResponse("SUCCESS",retValue);
	}
	/*@RequestMapping(value = "/CCMProductionPosting", method = RequestMethod.POST, headers = { "Content-type=application/json" })
	public @ResponseBody RestResponse CCMProductionPosting(@RequestParam Integer ccm_heat_trns_sno, @RequestParam String heat_no, @RequestParam Integer heat_counter,
			HttpSession session) {
		String result = "";
		try {
			HeatStatusTrackingModel heatTrackObj = casterProdDao.getHeatStatusTrackingModelByHeatIdWithoutStatus(heat_no, heat_counter);
			heatTrackObj.setMain_status(Constants.HEAT_TRACK_STATUS_PRD_POST);
			heatTrackObj.setProdPostedBy(Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
			heatTrackObj.setProdPostedDate(new Date());
			//heatTrackObj.setUpdatedBy(Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
			//heatTrackObj.setUpdatedDateTime(new Date());
			//heatTrackObj.setInspection_done("N");
			result = ccmBatchDetailsService.ccmProductionPosting(heatTrackObj);
		
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(CasterProductionController.class
					+ " Inside CCMProductionPosting Exception..", e);
		}
		if(result.equals(Constants.PROD_POST)){
			return new RestResponse("SUCCESS", result);
		}else{
			return new RestResponse("FAILURE", result);
		}
	}
	*/
	
	//New Batch prodcution posting 
	
	@RequestMapping(value = "/CCMBatchProductionPosting", method = RequestMethod.POST, headers = { "Content-type=application/json" })
	public @ResponseBody RestResponse CCMBatchProductionPosting(@RequestParam Integer ccm_heat_trns_sno, @RequestParam String heat_no, @RequestParam Integer heat_counter,
			HttpSession session) {
		String result = "";
		try {
			HeatStatusTrackingModel heatTrackObj = casterProdDao.getHeatStatusTrackingModelByHeatIdWithoutStatus(heat_no, heat_counter);
			//heatTrackObj.setMain_status(Constants.HEAT_TRACK_STATUS_PRD_POST);
			//heatTrackObj.setProdPostedBy(Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
			//heatTrackObj.setProdPostedDate(new Date());
			//heatTrackObj.setUpdatedBy(Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
			//heatTrackObj.setUpdatedDateTime(new Date());
			heatTrackObj.setInspection_done("Y");
			
			CCMHeatDetailsModel ccmObj = ccmheatDao.getCCMheatByid(ccm_heat_trns_sno);	
			HeatPlanDetails list = heatPlanServ.getHeatPlanDetailsById(ccmObj.getHeat_plan_line_no());
			IfacesmsLpDetailsModel ifacObj= new IfacesmsLpDetailsModel();
			ifacObj.setMsg_id(null);
			ifacObj.setSch_id(list.getLp_schd_id());
            ifacObj.setPlanned_heat_id(list.getLp_plan_heat_id());
            ifacObj.setActual_heat_id(ccmObj.getHeat_no());
            ifacObj.setPrev_sch_id(null);
            ifacObj.setPrev_planned_heat_id(null);
            ifacObj.setGrade(null);
            ifacObj.setEvent_code("SMSPROD");
            ifacObj.setInterface_status(0);
            ifacObj.setError_msg(null);
            ifacObj.setCreatedBy(Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
            ifacObj.setCreated_Date(new Date());
            ifacObj.setModified_by(null);
            ifacObj.setModified_date(null);
			result = ccmBatchDetailsService.ccmBatchProductionPosting(heatTrackObj,ifacObj);
		
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(CasterProductionController.class
					+ " Inside CCMBatchProductionPosting Exception..", e);
		}
		if(result.equals(Constants.PROD_POST)){
			return new RestResponse("SUCCESS", result);
		}else{
			return new RestResponse("FAILURE", result);
		}
	}
	
	@RequestMapping(value = "/BatchReconcileSaveOrUpdate", method = RequestMethod.POST, headers = { "Content-type=application/json" })
	public @ResponseBody RestResponse BatchReconcileSaveOrUpdate(@RequestBody CasterRequestWrapper ccmReqWrapper,
			@RequestParam Integer ccm_heat_trns_sno, HttpSession session) {
		String result = "";
		try {
			CCMHeatDetailsModel heatDtls = ccmheatDao.getCCMheatByid(ccm_heat_trns_sno);
			List<CCMProductDetailsModel> ccmProdLi = heatDtls.getCcmProdHeatDtls();
			List<CCMProductDetailsBkpModel> ccmProdBkpTblLi = ccmBatchDetailsService.getProdDtlsBkpByHeatTrnsId(ccm_heat_trns_sno);
			List<CCMBatchDetailsBkpModel> ccmBatckBkpTblLi = ccmBatchDetailsService.getBatchDtlsBkpByProdTrnsId(ccmProdLi.get(0).getProd_trns_id());
			List<CCMBatchDetailsModel> origCCMBatchLi = new ArrayList<CCMBatchDetailsModel>();
			List<CCMBatchDetailsModel> removeBatchLi = new ArrayList<CCMBatchDetailsModel>();
			List<CCMBatchDetailsModel> saveOrUpdateBatchLi = new ArrayList<CCMBatchDetailsModel>();
			Map<String, List<CCMBatchDetailsModel>> finalBatchMap = new HashMap<String, List<CCMBatchDetailsModel>>();
			List<LookupMasterModel> strandNosLi = lookupmstrDao.getLookupDtlsByLkpTypeAndStatus("CCM_STAND_NO", 1);
			Map<String, List<CCMProductDetailsModel>> finalProdMap = new HashMap<String, List<CCMProductDetailsModel>>();
			List<CCMProductDetailsModel> saveOrUpdateProdLi = new ArrayList<CCMProductDetailsModel>();
			List<CCMProductDetailsModel> removeProdLi = new ArrayList<CCMProductDetailsModel>();
			List<CCMBatchDetailsModel> actCCMBatchLi = ccmReqWrapper.getCcmBatchDetails();
			CCMProductDetailsBkpModel ccmProdBkpObj;
			List<CCMProductDetailsBkpModel> ccmProdBkpLi = new ArrayList<CCMProductDetailsBkpModel>();
			CCMBatchDetailsBkpModel ccmBatchBkpObj;
			List<CCMBatchDetailsBkpModel> ccmBatchBkpLi = new ArrayList<CCMBatchDetailsBkpModel>();
			
			for(LookupMasterModel strandLkpObj : strandNosLi) {
				float strandTotalWeight = 0;
				strandTotalWeight = (float) actCCMBatchLi.stream().filter(o -> o.getStrandNo().equals(strandLkpObj.getLookup_id())).mapToDouble(o -> o.getAct_batch_wgt()).sum();
				List<CCMBatchDetailsModel> strandWiseBatchLi = actCCMBatchLi.stream().filter(o -> o.getStrandNo().equals(strandLkpObj.getLookup_id())).collect(Collectors.toList());
				List<CCMProductDetailsModel> ccmProdObjLi = ccmProdLi.stream().filter(p -> p.getStand_id().equals(strandLkpObj.getLookup_id())).collect(Collectors.toList());
				CCMProductDetailsModel ccmProdObj = null;
				if(ccmProdObjLi.size() > 0)
					ccmProdObj = ccmProdObjLi.get(0);
				float no_of_batches = strandWiseBatchLi.size();
				
				if(ccmProdObj == null && no_of_batches > 0) { //Add
					CCMProductDetailsModel newProdObj = new CCMProductDetailsModel();
					newProdObj.setTrns_sl_no(ccm_heat_trns_sno);
					newProdObj.setStand_id(strandLkpObj.getLookup_id());
					newProdObj.setCut_length(ccmProdLi.get(0).getCut_length()); 
					newProdObj.setNo_batches(no_of_batches);
					newProdObj.setCs_size(ccmProdLi.get(0).getCs_size());
					newProdObj.setCs_wgt(ccmProdLi.get(0).getCs_wgt());
					newProdObj.setTot_wgt_batches(strandTotalWeight);
					newProdObj.setCreated_by(Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
					newProdObj.setCreated_date_time(new Date());
					newProdObj.setRecord_status(1);
					newProdObj.setHeat_plan_line_no(ccmProdLi.get(0).getHeat_plan_line_no());
					saveOrUpdateProdLi.add(ccmProdObj);
				}else if(ccmProdObj != null && no_of_batches == 0) { //Delete
					removeProdLi.add(ccmProdObj);
				}else if(ccmProdObj != null) {
					ccmProdObj.setTot_wgt_batches(strandTotalWeight);
					ccmProdObj.setNo_batches(no_of_batches);
					ccmProdObj.setUpdated_by(Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
					ccmProdObj.setUpdated_date_time(new Date());
					saveOrUpdateProdLi.add(ccmProdObj);
				}
			}
			finalProdMap.put("SAVE_CCM_PROD", saveOrUpdateProdLi);
			finalProdMap.put("DELETE_CCM_PROD", removeProdLi);
			
			for(CCMProductDetailsModel ccmProdObj : ccmProdLi) {
				if(ccmProdBkpTblLi.size() == 0) {
				ccmProdBkpObj = new CCMProductDetailsBkpModel();
				ccmProdBkpObj.setProdHistEntryDate(new Date());
				ccmProdBkpObj.setProdHistUserId(Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
				ccmProdBkpObj.setProd_trns_id(ccmProdObj.getProd_trns_id());
				ccmProdBkpObj.setTrns_sl_no(ccmProdObj.getTrns_sl_no());
				ccmProdBkpObj.setStand_id(ccmProdObj.getStand_id());
				ccmProdBkpObj.setCut_length(ccmProdObj.getCut_length());
				ccmProdBkpObj.setNo_batches(ccmProdObj.getNo_batches());
				ccmProdBkpObj.setCs_size(ccmProdObj.getCs_size());
				ccmProdBkpObj.setCs_wgt(ccmProdObj.getCs_wgt());
				ccmProdBkpObj.setTot_wgt_batches(ccmProdObj.getTot_wgt_batches());
				ccmProdBkpObj.setCasting_speed(ccmProdObj.getCasting_speed());
				ccmProdBkpObj.setEms_cf(ccmProdObj.getEms_cf());
				ccmProdBkpObj.setModule_jackt_no(ccmProdObj.getModule_jackt_no());
				ccmProdBkpObj.setCreated_by(ccmProdObj.getCreated_by());
				ccmProdBkpObj.setCreated_date_time(ccmProdObj.getCreated_date_time());
				ccmProdBkpObj.setUpdated_by(ccmProdObj.getCreated_by());
				ccmProdBkpObj.setUpdated_date_time(ccmProdObj.getCreated_date_time());
				ccmProdBkpObj.setRecord_status(ccmProdObj.getRecord_status());
				ccmProdBkpObj.setRecord_version(ccmProdObj.getRecord_version());
				ccmProdBkpObj.setCcm_mtube_trns_id(ccmProdObj.getCcm_mtube_trns_id());
				ccmProdBkpObj.setMtube_life(ccmProdObj.getMtube_life());
				ccmProdBkpObj.setHeat_plan_line_no(ccmProdObj.getHeat_plan_line_no());
				ccmProdBkpLi.add(ccmProdBkpObj);
				}
				for(CCMBatchDetailsModel origBatchObj : ccmProdObj.getCcmBatchDtls()) {
					if(ccmBatckBkpTblLi.size() == 0) {
					ccmBatchBkpObj = new CCMBatchDetailsBkpModel();
					ccmBatchBkpObj.setBatchTrnsHEntryTime(new Date());
					ccmBatchBkpObj.setBatchTrnsHistUserId(Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
					ccmBatchBkpObj.setBatch_trns_id(origBatchObj.getBatch_trns_id());
					ccmBatchBkpObj.setTrns_sl_no(origBatchObj.getTrns_sl_no());
					ccmBatchBkpObj.setBatch_no(origBatchObj.getBatch_no());
					ccmBatchBkpObj.setProduct(origBatchObj.getProduct());
					ccmBatchBkpObj.setPlnd_len(origBatchObj.getPlnd_len());
					ccmBatchBkpObj.setAct_len(origBatchObj.getAct_len());
					ccmBatchBkpObj.setAct_batch_wgt(origBatchObj.getAct_batch_wgt());
					ccmBatchBkpObj.setCreated_by(origBatchObj.getCreated_by());
					ccmBatchBkpObj.setCreated_date_time(origBatchObj.getCreated_date_time());
					ccmBatchBkpObj.setUpdated_by(origBatchObj.getUpdated_by());
					ccmBatchBkpObj.setUpdated_date_time(origBatchObj.getUpdated_date_time());
					ccmBatchBkpObj.setRecord_status(origBatchObj.getRecord_status());
					ccmBatchBkpObj.setHeat_plan_id(origBatchObj.getHeat_plan_id());
					ccmBatchBkpObj.setHeat_plan_line_id(origBatchObj.getHeat_plan_line_id());
					ccmBatchBkpObj.setIs_send_sap(origBatchObj.getIs_send_sap());
					ccmBatchBkpObj.setProd_date(origBatchObj.getProd_date());
					ccmBatchBkpLi.add(ccmBatchBkpObj);
					}
					List<CCMBatchDetailsModel> ccmBatchObjLi = actCCMBatchLi.stream().filter(p -> p.getBatch_trns_id().equals(origBatchObj.getBatch_trns_id())).collect(Collectors.toList());
					CCMBatchDetailsModel ccmBatchObj = null; 
					if(ccmBatchObjLi.size() > 0)
						ccmBatchObj = ccmBatchObjLi.get(0);
					if(ccmBatchObj == null) {
						removeBatchLi.add(origBatchObj);
					}else if(ccmBatchObj != null && (ccmBatchObj.getAct_len() != origBatchObj.getAct_len() || ccmBatchObj.getAct_batch_wgt() != origBatchObj.getAct_batch_wgt())) {
						ccmBatchObj.setPlnd_len(origBatchObj.getPlnd_len());
						ccmBatchObj.setCreated_by(origBatchObj.getCreated_by());
						ccmBatchObj.setCreated_date_time(origBatchObj.getCreated_date_time());
						ccmBatchObj.setUpdated_by(Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
						ccmBatchObj.setUpdated_date_time(new Date());
						ccmBatchObj.setRecord_status(origBatchObj.getRecord_status());
						ccmBatchObj.setHeat_plan_id(origBatchObj.getHeat_plan_id());
						ccmBatchObj.setHeat_plan_line_id(origBatchObj.getHeat_plan_line_id());
						ccmBatchObj.setProd_date(origBatchObj.getProd_date());
						//ccmBatchObj.setSap_ud_code(origBatchObj.getSap_ud_code());
					
						saveOrUpdateBatchLi.add(ccmBatchObj);
					}
					origCCMBatchLi.add(origBatchObj);
				}
			}
			for(CCMBatchDetailsModel actBatchObj : actCCMBatchLi) {
				List<CCMBatchDetailsModel> batchObjLi = origCCMBatchLi.stream().filter(p -> p.getBatch_trns_id().equals(actBatchObj.getBatch_trns_id())).collect(Collectors.toList());
				CCMBatchDetailsModel ccmBatchObj = null;
				if(batchObjLi.size() > 0)
					ccmBatchObj = batchObjLi.get(0);
				if(ccmBatchObj == null && actBatchObj.getBatch_trns_id() == 0) {
					actBatchObj.setBatch_trns_id(null);
					actBatchObj.setPlnd_len(origCCMBatchLi.get(0).getPlnd_len());
					actBatchObj.setCreated_by(Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
					actBatchObj.setCreated_date_time(new Date());
					actBatchObj.setRecord_status(1);
					actBatchObj.setHeat_plan_id(origCCMBatchLi.get(0).getHeat_plan_id());
					actBatchObj.setHeat_plan_line_id(origCCMBatchLi.get(0).getHeat_plan_line_id());
					actBatchObj.setProd_date(new Date());
					saveOrUpdateBatchLi.add(actBatchObj);
				}
			}
			finalBatchMap.put("SAVE_CCM_BATCH", saveOrUpdateBatchLi);
			finalBatchMap.put("DELETE_CCM_BATCH", removeBatchLi);
			
			result = ccmBatchDetailsService.saveUpdateOrDeleteCCMBatches(finalProdMap, finalBatchMap, ccmProdBkpLi, ccmBatchBkpLi);
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(CasterProductionController.class
					+ " Inside BatchReconcileSaveOrUpdate Exception..", e);
		}
		if(result.equals(Constants.SAVE) || result.equals(Constants.UPDATE)){
			return new RestResponse("SUCCESS", result);
		}else{
			return new RestResponse("FAILURE", result);
		}
	}
}

enum MTSTAUS {
	DISCARD, RUNNING,CLEANED
}