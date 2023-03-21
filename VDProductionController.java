package com.smes.trans.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


import com.smes.admin.service.impl.CommonService;
import com.smes.trans.model.HeatChemistryChildDetails;
import com.smes.trans.model.HeatProcessEventDetails;
import com.smes.trans.model.LRFHeatConsumableModel;
import com.smes.trans.model.LRFHeatDetailsModel;
import com.smes.trans.model.TransDelayEntryHeader;
import com.smes.trans.model.VDHeatConsumableModel;
import com.smes.trans.model.VDHeatDetailsModel;
import com.smes.trans.service.impl.LRFProductionService;
import com.smes.trans.service.impl.TransDelayEntryHeaderService;
import com.smes.trans.service.impl.VDProductionService;
import com.smes.util.Constants;
import com.smes.util.DelayEntryDTO;
import com.smes.util.LRFHeatConsumableDisplay;
import com.smes.util.RestResponse;
import com.smes.util.VDHeatConsumableDisplay;
import com.smes.wrappers.LRFRequestWrapper;
import com.smes.wrappers.VDRequestWrapper;

@Controller
@RequestMapping("VDproduction")
public class VDProductionController {

	private static final Logger logger = Logger.getLogger(VDProductionController.class);

	@Autowired
	VDProductionService vdProductionService;

	@Autowired
	private TransDelayEntryHeaderService transDelayEntryHeaderService;

	@Autowired
	private LRFProductionService lrfProductionService;

	@Autowired
	private CommonService commonService;
	
	@RequestMapping("/vdProductionView")
	public ModelAndView getLRFProductionView() {
		logger.info(VDProductionController.class + "...getLRFProductionView()");
		ModelAndView model = new ModelAndView("transaction/VDProduction");
		return model;
	}

	@RequestMapping(value = "/getVDHeatFormDtlsById", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody VDHeatDetailsModel getVDHeatFormDtlsById(Integer trns_sl_no) {
		logger.info(VDProductionController.class + "...getVDHeatFormDtlsById()-trns_sl_no--" + trns_sl_no);

		if (trns_sl_no != null && trns_sl_no != 0) {

			return vdProductionService.getVDHeatDtlsFormByID(trns_sl_no);
		} else {
			return null;
		}
	}

	@RequestMapping(value = "/VDHeatSave", method = RequestMethod.POST, headers = { "Content-type=application/json" })
	public @ResponseBody RestResponse saveVDHeatDetails(@RequestBody VDRequestWrapper reqWrapper, HttpSession session) {
		logger.info(VDProductionController.class + "...saveVDHeatDetails()");
		String result = "";

		result = vdProductionService.saveAll(reqWrapper,
				Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
		try {
			if (result.equals(Constants.SAVE)) {
				return new RestResponse("SUCCESS", result);

			} else {
				return new RestResponse("FAILURE", result);
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(VDProductionController.class + " Inside saveVDHeatDetails Exception..", e);
			return new RestResponse("FAILURE", e.getMessage());
		}

	}

	@RequestMapping(value = "/VDeventSave", method = RequestMethod.POST, headers = { "Content-type=application/json" })
	public @ResponseBody RestResponse heatProcessEventSave(@RequestBody HeatProcessEventDetails heatProcessEventDtls,
			HttpSession session) {
		logger.info(HeatProcessEventController.class + "heatProcessEventSave");
		try {

			String result = "";

			result = vdProductionService.heatProcessEventSaveOrUpdate(heatProcessEventDtls,
					Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));

			if (result.equals(Constants.SAVE) || result.equals(Constants.UPDATE)) {

				return new RestResponse("SUCCESS", result);
			} else {
				return new RestResponse("FAILURE", result);
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(VDProductionController.class + " Inside heatProcessEventSave Exception..", e);
			return new RestResponse("FAILURE", e.getMessage());
		}
	}

	@RequestMapping(value = "/vdDispatchSave", method = RequestMethod.POST, headers = {
			"Content-type=application/json" })
	public @ResponseBody RestResponse saveVdDispacthDetails(@RequestBody VDRequestWrapper reqWrapper,
			HttpSession session, @RequestParam("trns_si_no") int trans_si_no,
			@RequestParam("AR_N2_CONSUMPTION") float AR_N2_CONSUMPTION) {
		logger.info(VDProductionController.class + "...saveVdDispacthDetails()");
		String result = "";
		result = vdProductionService.saveVdDispatchDet(reqWrapper,
				Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
		try {
			if (result.equals(Constants.SAVE)) {
				LRFHeatDetailsModel lrfmodel = lrfProductionService.getLRFHeatDtlsFormByID(trans_si_no);
				if (lrfmodel != null) {
					// lrfmodel.setAR_N2_CONSUMPTION(AR_N2_CONSUMPTION);
					String result1 = "";
					result1 = lrfProductionService.updateLrfHeatDetails(lrfmodel);
				}
				return new RestResponse("SUCCESS", result);

			} else {
				return new RestResponse("FAILURE", result);
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(VDProductionController.class + " Inside saveVdDispacthDetails Exception..", e);
			return new RestResponse("FAILURE", e.getMessage());
		}

	}

	@RequestMapping(value = "/activityDelayMstrBySubunit", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<DelayEntryDTO> getdelayMasterBySubUnit(@RequestParam("sub_unit_id") Integer sub_unit_id,
			@RequestParam("trns_si_no") Integer trns_si_no, @RequestParam("heat_counter") Integer heat_counter) {
		// eofProductionService.getEofDelayEntriesBySubUnitAndHeat(sub_unit_id,trns_si_no);

		return vdProductionService.getVDDelayEntriesBySubUnitAndHeat(sub_unit_id, trns_si_no, heat_counter);// activityDlyMstrServie.getAllActivityDetalMasterBySubUnit(sub_unit_id);
	}

	@RequestMapping(value = "/TransDelaySave", method = RequestMethod.POST, headers = {
			"Content-type=application/json" })
	public @ResponseBody RestResponse TransDelaySave(@RequestParam("heat_id") String heat_id,
			@RequestBody List<DelayEntryDTO> transDetails, HttpSession session) {
		try {
			VDHeatDetailsModel HeatDetails = vdProductionService.getVDHeatDetailsByHeatNo(heat_id); // lrfProductionDao.get//
			for (int i = 0; i < transDetails.size(); i++) {
				DelayEntryDTO eoFdelayEntryDTO = transDetails.get(i);
				if (eoFdelayEntryDTO.getTransDelayEntryhdr() == null) {// saving data
					TransDelayEntryHeader newEntry = new TransDelayEntryHeader();
					newEntry.setTrns_delay_entry_hdr_id(null);
					newEntry.setActivity_delay_id(eoFdelayEntryDTO.getActivity_master().getActivity_delay_id());
					newEntry.setActivity_start_time(eoFdelayEntryDTO.getStart_time());
					newEntry.setActivity_end_time(eoFdelayEntryDTO.getEnd_time());
					newEntry.setActivity_duration(eoFdelayEntryDTO.getDuration().intValue());
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
			// logger.error(EOFProductionController.class + " Inside SaveDelayDetailsWithHdr
			// Exception..", e);
			return new RestResponse("FAILURE", e.getMessage());
		}

	}
	
	@RequestMapping(value = "/getVdAdditions", method = RequestMethod.GET, produces = "application/json")
		public @ResponseBody List<VDHeatConsumableModel> getVdAdditions(@RequestParam String lookup_code,
				@RequestParam Integer sub_unit_id,HttpSession session) {
			logger.info(VDProductionController.class + "...getVdAdditions() lokkuptype---" + lookup_code);

			if (lookup_code != null && lookup_code != "") {
				/*session.setAttribute("H_COL_CNT", null);
				session.setAttribute("H_COL_CNT", lrfProductionService.getArcAdditions(lookup_code).size());*/

				return vdProductionService.getVdAdditions(lookup_code,sub_unit_id);
			} else {
				return null;
			}
		}

	@RequestMapping(value = "/SaveOrUpdate", method = RequestMethod.POST, headers = { "Content-type=application/json" })
	public @ResponseBody RestResponse VdAdditionsSaveOrUpdate(@RequestBody VDRequestWrapper reqWrapper,
			@RequestParam String arc_start_date, @RequestParam String arc_end_date, HttpSession session) {
		logger.info(VDProductionController.class + "VdAdditionsSaveOrUpdate");
		try {

			String result = "";
			String addition_type;
//			if(reqWrapper.getArcDetails().getPrev_unit().substring(0, 2).equals(Constants.PREV_UNIT_VD))
//				addition_type = Constants.LRF_ADDITION_AVD;
//			else
				addition_type = Constants.LRF_ADDITION_BVD;
			reqWrapper.getArcDetails().setAddition_type(commonService.getLookupIdByQuery("select lookup_id from LookupMasterModel where lookup_type = 'LRF_ADDITION_TYPE' and lookup_status = 1 and lookup_value='"+addition_type+"'"));
			result = vdProductionService.VDAdditionsSaveOrUpdate(reqWrapper, arc_start_date, arc_end_date,
					Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
			if (result.equals(Constants.SAVE) || result.equals(Constants.UPDATE)) {
				return new RestResponse("SUCCESS", result);
			} else {
				return new RestResponse("FAILURE", result);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(VDProductionController.class + " Inside VdAdditionsSaveOrUpdate Exception..", e);
			return new RestResponse("FAILURE", e.getMessage());
		}
	}
	
	@RequestMapping(value = "/getVDAdditionsByHeat", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody VDHeatConsumableDisplay getVDAdditionsByHeat(@RequestParam String heat_id,
			@RequestParam Integer heat_cnt, HttpSession session, @RequestParam Integer unit_id) {
		logger.info(VDProductionController.class + "...getVDAdditionsByHeat() lokkuptype---" + heat_id);

		if (heat_id != null) {

			VDHeatConsumableDisplay header = vdProductionService.getVDAdditionsByHeat(heat_id, heat_cnt, unit_id);
			// List<LRFHeatConsumableDisplay>
			// header=lrfProductionService.getLRFArcAdditionsByHeat(heat_id,heat_cnt,unit_id);
			//// session.setAttribute("H_COL_CNT", null);
			// session.setAttribute("H_COL_CNT", header.getHeaderdis().size());

			return header;
		} else {
			return null;
		}
	}
	
	@RequestMapping(value = "/getVDAdditionsTemp", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<Map<String, Object>> getVDAdditionsTemp(@RequestParam Integer unit_id,
			@RequestParam String heatId, @RequestParam Integer heatCnt) {

		return vdProductionService.getVDAdditionsTemp(unit_id, heatId, heatCnt);
	}
	

	@RequestMapping(value = "/VDLiftChem", method = RequestMethod.GET, headers = { "Content-type=application/json" })
	public @ResponseBody RestResponse LRFLiftChem(HttpSession session) {
		try {
			logger.info(LRFProductionController.class);
			String sql = "select lookup_id from LookupMasterModel where lookup_type='CHEM_LEVEL' and lookup_value = '"+Constants.VD_CHEM+"' and lookup_status=1";
			Integer ChemId = commonService.getLookupIdByQuery(sql);
			return new RestResponse("SUCCESS", ChemId.toString());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(EOFProductionController.class + " Inside LRFLiftChem Exception..", e);
			return new RestResponse("FAILURE", e.getMessage());
		}
	}
	
	@RequestMapping(value = "/getChemDtlsByAnalysis", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<HeatChemistryChildDetails> getChemDtlsByAnalysis(@RequestParam Integer analysis_id,
			@RequestParam String heat_id, @RequestParam Integer heat_counter, @RequestParam Integer sub_unit_id,
			@RequestParam String sample_no, @RequestParam Integer aim_psn_id) {
		logger.info(HeatProcessEventController.class + "...getChemDtlsByAnalysis()");
		//analysis_id=686;
		return vdProductionService.getChemDtlsByAnalysis(analysis_id, heat_id, heat_counter, sub_unit_id, sample_no,
				aim_psn_id);

	}
	

	

}
