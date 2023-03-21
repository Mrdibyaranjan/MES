package com.smes.trans.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import com.smes.masters.model.SteelLadleMasterModel;
import com.smes.masters.model.SteelLadleStatusMasterModel;
import com.smes.masters.service.impl.SteelLadleStatusMasterService;
import com.smes.trans.model.StLadleMaintStatusModel;
import com.smes.trans.model.StLadlePartsMaintLogModel;
import com.smes.trans.model.SteelLadleMaintenanceModel;
import com.smes.trans.model.SteelLadleTrackingModel;
import com.smes.trans.service.impl.SteelLadleMaintnService;
import com.smes.util.Constants;
import com.smes.util.GenericClass;
import com.smes.util.RestResponse;

@Controller
@RequestMapping("laddleMaintainance")
public class SteelLadleMaintnController {
	private static final Logger logger = Logger
			.getLogger(SteelLadleMaintnController.class);

	@Autowired
	SteelLadleMaintnService steelLadleMaintnService;
	
	@Autowired
	SteelLadleStatusMasterService steelLdlStsMstrService;
	
	@Autowired
	CommonService commonService;
	
	//@Autowired
	//SteelLadlessMasterService steelLadleStatusService;

	@RequestMapping("/showladleMaintainanceView")
	public ModelAndView viewSteelLadleMaintenance() {
		logger.info(SteelLadleMaintnController.class + "...getAllStLadles()");
		ModelAndView model = new ModelAndView(
				"transaction/SteelLadleMaintenance");
		return model;
	}

	@RequestMapping(value = "/getLadleCurrentStatus", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody SteelLadleTrackingModel getLadleCurrentStatus(
			@RequestParam Integer steel_ladle_id) {
		logger.info(SteelLadleMaintnController.class
				+ "...getLadleCurrentStatus()");

		return steelLadleMaintnService.getSteelLadleTracking(steel_ladle_id);
	}

	@RequestMapping(value = "/getSteelLadleMaintStatus", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody StLadleMaintStatusModel getSteelLadleMaintStatus(@RequestParam Integer stladle_id) {
		logger.info(SteelLadleMaintnController.class + "...getSteelLadleMaintStatus()");

		return steelLadleMaintnService.getSteelLadleMaintStatus(stladle_id);
	}
	
	@RequestMapping(value = "/getStLdlPartsDtls", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<StLadlePartsMaintLogModel> getStLdlPartsDtls(
			@RequestParam Integer stladle_id) {
		logger.info(SteelLadleMaintnController.class
				+ "...getStLdlPartsDtls()");
		List<StLadlePartsMaintLogModel> li = new ArrayList<StLadlePartsMaintLogModel>();
		li = steelLadleMaintnService.getStLdlPartsDtls(stladle_id);
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		for(int i=0;i<li.size();i++)
		{
			//slpmObj = li.get(i);
			if (li.get(i).getChange_date() != null) {
				String cdt = formatter.format(li.get(i).getChange_date());
				//li.get(i).setCdate((null==li.get(i).getChange_date())?null:li.get(i).getChange_date().toString());
				li.get(i).setCdate(cdt);
			}
		}
		
		return li;
	}
	
	@RequestMapping(value = "/getStlLadleMaintPartsLog", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody StLadlePartsMaintLogModel getStlLadleMaintPartsLog(
			@RequestParam Integer stLdlMaintStsId) {
		logger.info(SteelLadleMaintnController.class
				+ "...getStlLadleMaintPartsLog()");

		return steelLadleMaintnService.getStlLadleMaintPartsLog(stLdlMaintStsId, null);
	}
	
	@RequestMapping(value = "/getSteelLadleLifeDtls", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<SteelLadleMaintenanceModel> getSteelLadleLifeDtls(@RequestParam Integer steel_ladle) {
		logger.info(SteelLadleMaintnController.class + "...getSteelLadleLifeDtls()");

		return steelLadleMaintnService.getStLdlLifeDtlsForStatusChange(steel_ladle);
	}
	
	@RequestMapping(value = "/getChangeStatus", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody SteelLadleMasterModel getChangeStatus(
			@RequestParam Integer st_ladle_no) {
		logger.info(SteelLadleMaintnController.class + "...getChangeStatus()");
		
		return steelLadleMaintnService.getChangeStatusByStLadle(st_ladle_no);
	}

	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST, headers = { "Content-type=application/json" })
	public @ResponseBody RestResponse steelLadleSaveOrUpdate(
			@RequestBody SteelLadleMasterModel steelLadleDtls,
			@RequestParam String changed_date, HttpSession session) {
		logger.info(SteelLadleMaintnController.class
				+ "...steelLadleSaveOrUpdate()");
		String result = "";
		try {
			// if(steelLadleDtls.getSteel_ladle_si_no()>0) {
			steelLadleDtls.setUpdatedBy(Integer.parseInt(session.getAttribute(
					"USER_APP_ID").toString()));
			// steelLadleDtls.setUpdatedDateTime(new Date());
			// steelLadleDtls.setChanged_date(GenericClass.getDateObject("dd/MM/yyyy HH:mm:ss",
			// changed_date));
			result = steelLadleMaintnService
					.steelLadleStatusSave(steelLadleDtls);
			// }
			/*
			 * else{
			 * 
			 * steelLadleDtls.setCreatedBy(Integer.parseInt(session.getAttribute(
			 * "USER_APP_ID").toString()));
			 * steelLadleDtls.setCreatedDateTime(new Date());
			 * steelLadleDtls.setSteel_ladle_si_no(null); result =
			 * steelLadleMaintnService.steelLadleStatusSave(steelLadleDtls); }
			 */

			if (result.equals(Constants.SAVE)
					|| result.equals(Constants.UPDATE)) {
				return new RestResponse("SUCCESS", result);
			} else {
				return new RestResponse("FAILURE", result);
			}
		} catch (Exception e) {
			logger.error(SteelLadleMaintnController.class
					+ " Inside steelLadleSaveOrUpdate() Exception..", e);

		//if(steelLadleDtls.getSteel_ladle_si_no()>0)	{
			steelLadleDtls.setUpdatedBy(Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
			//steelLadleDtls.setUpdatedDateTime(new Date());
//			steelLadleDtls.setChanged_date(GenericClass.getDateObject("dd/MM/yyyy HH:mm:ss", changed_date));
			//steelLadleDtls.setChanged_date(GenericClass.getDateObject("dd/MM/yyyy HH:mm:ss", changed_date));
			//steelLadleDtls.setChanged_date(GenericClass.getDateObject("dd/MM/yyyy HH:mm:ss", changed_date));
			//steelLadleDtls.setChanged_date(GenericClass.getDateObject("dd/MM/yyyy HH:mm:ss", changed_date));
			//steelLadleDtls.setChanged_date(GenericClass.getDateObject("dd/MM/yyyy HH:mm:ss", changed_date));
			result = steelLadleMaintnService.steelLadleStatusSave(steelLadleDtls);
		//}
		/*else{
		
			steelLadleDtls.setCreatedBy(Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
			steelLadleDtls.setCreatedDateTime(new Date());
			steelLadleDtls.setSteel_ladle_si_no(null);
			result = steelLadleMaintnService.steelLadleStatusSave(steelLadleDtls);
		}*/
		
		if(result.equals(Constants.SAVE) || result.equals(Constants.UPDATE))
		{
			return new RestResponse("SUCCESS", result);
		}else{
			return new RestResponse("FAILURE", result);
		}
		}
	}
	
	@RequestMapping(value = "/SaveSteelLadleMaintStatus", method = RequestMethod.POST, headers = { "Content-type=application/json" })
	public @ResponseBody RestResponse SaveSteelLadleMaintStatus(@RequestBody StLadleMaintStatusModel stlLdlMaintStsModel,
			@RequestParam String heatStartDt, @RequestParam String heatEndDt, @RequestParam String updateFlag,
			HttpSession session) {
		logger.info(SteelLadleMaintnController.class+"...SaveSteelLadleMaintStatus()");
		try{
									
			String result = ""; Integer StLdlNo = 0;
			String ldlStsUIFlg = "";
			//StLadleStatusTrackHistoryModel stLdlTrackHist = null;
			
			StLadleMaintStatusModel stLdlStsMaint = new StLadleMaintStatusModel();
									
			StLdlNo = stlLdlMaintStsModel.getStladle_id();
			
			SteelLadleStatusMasterModel stldlStsObj = steelLdlStsMstrService.getSteelLadleStatusId(stlLdlMaintStsModel.getSteel_ladle_status());
			stlLdlMaintStsModel.setStladle_status_id(stldlStsObj.getSteel_ladle_status_id());
			if (stlLdlMaintStsModel.getStladle_maint_status_id() != null && stlLdlMaintStsModel.getStladle_maint_status_id() > 0) {
				stLdlStsMaint = steelLadleMaintnService.getSteelLadleMaintStatus(StLdlNo);
				stlLdlMaintStsModel.setUpdated_by(stLdlStsMaint.getCreated_by());
				stlLdlMaintStsModel.setUpdated_date_time(stLdlStsMaint.getCreated_date_time());
				//stlLdlMaintStsModel.setRecord_version(stLdlStsMaint.getRecord_version());
			} else {
				stlLdlMaintStsModel.setCreated_by(Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
				stlLdlMaintStsModel.setCreated_date_time(new Date());
				stlLdlMaintStsModel.setTrans_date(new Date());
				//stlLdlMaintStsModel.setRecord_version(0);
			}			
			stlLdlMaintStsModel.setUpdated_by(Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
			stlLdlMaintStsModel.setUpdated_date_time(new Date());
						
			if ((stlLdlMaintStsModel.getSteel_ladle_status().equals("RESERVED") && (!heatStartDt.equals("") || !heatEndDt.equals("")))
					|| (stlLdlMaintStsModel.getSteel_ladle_status().equals("DOWN") && (!heatStartDt.equals("") || !heatEndDt.equals("")))
					|| (stlLdlMaintStsModel.getSteel_ladle_status().equals("AVAILABLE") && updateFlag.equals("N") && (!heatStartDt.equals("") 
							|| !heatEndDt.equals("")))) {
				StLadlePartsMaintLogModel stlLdlPartMaintLogModel = new StLadlePartsMaintLogModel();
				StLadlePartsMaintLogModel heatPartIdObj = steelLadleMaintnService.getStlLadleMaintPartsLog(stlLdlMaintStsModel.getStladle_maint_status_id(), null);
				stlLdlPartMaintLogModel.setStLdlMainStstModel(stlLdlMaintStsModel);
				stlLdlPartMaintLogModel.setPart_id(null);
				stlLdlPartMaintLogModel.setPart_supplier(null);
				stlLdlPartMaintLogModel.setPart_type(null);
				stlLdlPartMaintLogModel.setChange_date(null);
				if (heatPartIdObj != null) {
					stlLdlPartMaintLogModel.setStladle_parts_maint_log_id(heatPartIdObj.getStladle_parts_maint_log_id());
					stlLdlPartMaintLogModel.setRecord_version(heatPartIdObj.getRecord_version());
					stlLdlPartMaintLogModel.setCreated_by(heatPartIdObj.getCreated_by());
					stlLdlPartMaintLogModel.setCreated_date_time(heatPartIdObj.getCreated_date_time());
				}
				else
				{
					stlLdlPartMaintLogModel.setRecord_version(0);
					stlLdlPartMaintLogModel.setCreated_by(Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
					stlLdlPartMaintLogModel.setCreated_date_time(new Date());
				}
				
				if (!heatStartDt.equals(""))
					stlLdlPartMaintLogModel.setHeat_start_date(GenericClass.getDateObject("dd/MM/yyyy HH:mm", heatStartDt));
				if (!heatEndDt.equals(""))
					stlLdlPartMaintLogModel.setHeat_end_date(GenericClass.getDateObject("dd/MM/yyyy HH:mm", heatEndDt));
				stlLdlPartMaintLogModel.setBurner_no(stlLdlMaintStsModel.getBurner_no());
				stlLdlPartMaintLogModel.setRemarks(stlLdlMaintStsModel.getRemarks());
				stlLdlPartMaintLogModel.setUpdated_by(Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
				stlLdlPartMaintLogModel.setUpdated_date_time(new Date());
				stlLdlPartMaintLogModel.setRecord_status(1);
				
				if (stlLdlMaintStsModel.getStladle_maint_status_id() != null && stlLdlMaintStsModel.getStladle_maint_status_id() > 0) {
					stlLdlPartMaintLogModel.setStladle_maint_status_id(stlLdlMaintStsModel.getStladle_maint_status_id());
				}
				if (stlLdlMaintStsModel.getStLdlPartMaintLog() == null && stlLdlPartMaintLogModel != null) {
					Set<StLadlePartsMaintLogModel> stLdlPartMaintLog = new HashSet<StLadlePartsMaintLogModel>();
					stLdlPartMaintLog.add(stlLdlPartMaintLogModel);
					stlLdlMaintStsModel.setStLdlPartMaintLog(stLdlPartMaintLog);
				}	
				else 
					stlLdlMaintStsModel.getStLdlPartMaintLog().add(stlLdlPartMaintLogModel);
			}
			
			if(stlLdlMaintStsModel.getSteel_ladle_status().equals(Constants.ST_LDL_STATUS_DOWN) && updateFlag.equals("Y")) { 
				ldlStsUIFlg = "I_DOWN";
			} else if (stlLdlMaintStsModel.getSteel_ladle_status().equals(Constants.ST_LDL_STATUS_DOWN) && !updateFlag.equals("Y")) {
				ldlStsUIFlg = "U_DOWN";
			} else if((stlLdlMaintStsModel.getSteel_ladle_status().equals("AVAILABLE") && updateFlag.equals("Y"))) {
				ldlStsUIFlg = "AVAIL";
			} else {
				ldlStsUIFlg = "CIRCN";
			}
			
			Integer UserId = Integer.parseInt(session.getAttribute("USER_APP_ID").toString());
			result = steelLadleMaintnService.saveStLadleMaintainance(UserId, stlLdlMaintStsModel, ldlStsUIFlg);
							
			if (result.equals("")) {
				result = "Please Check Data Entered!!";
			}
			if(result.equals(Constants.SAVE) || result.equals(Constants.UPDATE)){
				return new RestResponse("SUCCESS", result);
			}else{
				return new RestResponse("FAILURE", result);
			}
		}catch(Exception e){
			logger.error(SteelLadleMaintnController.class+" Inside SaveSteelLadleMaintStatus() Exception..", e);
			return new RestResponse("FAILURE", e.getMessage());
		}
	}	

	@RequestMapping(value = "/partLogDelete", method = RequestMethod.GET)
	public @ResponseBody RestResponse partLogDelete(HttpSession session,
			@RequestParam("partMaintLogId") Integer partMaintLogId,
			@RequestParam("stladleMaintStsId") Integer stladleMaintStsId) {
		try {
			logger.info(SteelLadleMaintnController.class + "partLogDelete");
			Integer result = 0; 

			result = commonService.deleteRecordByQuery("delete from StLadlePartsMaintLogModel where stladle_parts_maint_log_id="
							+ partMaintLogId + " and stladle_maint_status_id="+stladleMaintStsId);
			if (result > 0) {
				return new RestResponse("SUCCESS", Constants.DELETE);
			} else {
				return new RestResponse("FAILURE", Constants.UPDATE_FAIL);
			}			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(SteelLadleMaintnController.class
					+ " Inside partLogDelete Exception..", e);
			return new RestResponse("FAILURE", e.getMessage());
		}
	}
	
}
