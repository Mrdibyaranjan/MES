package com.smes.trans.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.smes.masters.model.ContainerMasterModel;
import com.smes.trans.model.SLHeatingDetailsModel;
import com.smes.trans.model.SLPartsDetailsModel;
import com.smes.trans.model.SLShiftDetailsModel;
import com.smes.trans.model.SLStatusTrackingModel;
import com.smes.trans.service.impl.SLHeatingDtlsService;
import com.smes.trans.service.impl.SLPartsDtlsService;
import com.smes.trans.service.impl.SLShiftDtlsService;
import com.smes.trans.service.impl.SLStatusTrackingService;
import com.smes.util.RestResponse;

@RestController
@RequestMapping("slTracking")
public class SLStatusTrackingController {
	private static final Logger logger = Logger.getLogger(SLStatusTrackingController.class);

	@Autowired
	SLStatusTrackingService steelLadleMaintnService;

	@Autowired
	SLPartsDtlsService steelLadlePartsMap;
	
	@Autowired
	SLHeatingDtlsService steelLadleHeatingMap;
	


	@RequestMapping("/showladleTrackView")
	public ModelAndView viewSteelLadleMaintenance() {
		logger.info(SLStatusTrackingController.class + "...getAllStLadlesTracks()");
		ModelAndView model = new ModelAndView("transaction/SLStatusTracking");
		return model;
	}

	@RequestMapping(value = "/getSLCurrentStatus", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<SLStatusTrackingModel> getLadleCurrentStatus() {
		logger.info(SteelLadleMaintnController.class + "...getLadleCurrentStatus()");
		return steelLadleMaintnService.getSteelLadleTracking();
	}
	
	@Transactional
	@RequestMapping(value = "/partsSaveOrUpdate", method = RequestMethod.POST,  headers = {"Content-type=application/json" })
	public @ResponseBody RestResponse partsMapSave(@RequestBody List<SLPartsDetailsModel> slPartsMapMdl,@RequestParam("ladle_trns_si_no") Integer ladle_trns_si_no,@RequestParam("ladle_life") Integer ladle_life,@RequestParam("shift_si_no") Integer shift_si_no,@RequestParam("ladle_man") Integer ladle_man,@RequestParam("ladle_man_asst") Integer ladle_man_asst,HttpSession sessionUser) {
		logger.info(SLStatusTrackingController.class + "...partsMapSave()");
		String result="";
		for (SLPartsDetailsModel slPartsDtl : slPartsMapMdl) {
			slPartsDtl.setTrns_stladle_track_id(ladle_trns_si_no);
			slPartsDtl.setTrns_stladle_life(ladle_life);
			slPartsDtl.setShift_dtl_id(shift_si_no);
			
		}
		Integer response = steelLadlePartsMap.saveParts(slPartsMapMdl,sessionUser,ladle_man,ladle_man_asst);
		if(response==1) {
			result="Parts Added Successfully...";
		}
		else if(response==2||response==3){
			result="Parts Duplicate Entry Please Check...";
		}
		else {
			result="Parts Adding Failed...";
		}
		return new RestResponse("SUCCESS", result);
	}

	@RequestMapping(value = "/heatingSaveOrUpdate", method = RequestMethod.POST,  headers = {"Content-type=application/json" })
	public @ResponseBody RestResponse heatingDetailsSave(@RequestBody SLHeatingDetailsModel slHeatingMdl,HttpSession sessionUser) {
		logger.info(SLStatusTrackingController.class + "...heatingDetailsSave()");
		
		String result="";
		Integer response =steelLadleHeatingMap.heatingSave(slHeatingMdl,sessionUser);
		
		if(response==1 || response==2) {
			result="Heating Save Successfully...";
		}
		else {
			result="Heat Saving Failed...";
		}
		return new RestResponse("SUCCESS", result);
	}
	
	
	@RequestMapping(value = "/getPartsById", method = RequestMethod.POST,  headers = {"Content-type=application/json" })
	public List<SLPartsDetailsModel> getLadleParts(@RequestParam("ladle_trns_si_no") Integer ladle_trns_si_no,@RequestParam("ladle_life") Integer ladle_life) {
		logger.info(SLStatusTrackingController.class + "...getLadleParts()");
		
		return steelLadlePartsMap.getLadleParts(ladle_trns_si_no,ladle_life);
		
	}
	
	@RequestMapping(value = "/getHeatingById", method = RequestMethod.POST,  headers = {"Content-type=application/json" })
	public List<SLHeatingDetailsModel> getHeatingDtls(@RequestParam("ladle_trns_si_no") Integer ladle_trns_si_no,@RequestParam("ladle_life") Integer ladle_life) {
		logger.info(SLStatusTrackingController.class + "...getHeatingDtls()");
		
		return steelLadlePartsMap.getHeatingDtls(ladle_trns_si_no,ladle_life);
		
	}
	
	
	@RequestMapping(value = "/updateLadleStatus", method = RequestMethod.POST,  headers = {"Content-type=application/json" })
	public @ResponseBody RestResponse updateLadleStatus(@RequestParam("heat_id") Integer heat_id,@RequestParam("stladle_track_id") Integer stladle_track_id,@RequestParam("stladle_life") Integer stladle_life) {
		logger.info(SLStatusTrackingController.class + "...updateLadleStatus()");
		Integer result=null;
		List<SLPartsDetailsModel> partsCount=null;
		partsCount=steelLadlePartsMap.getLadleParts(stladle_track_id, stladle_life);
		if(partsCount.size()>0) {
		result=steelLadleHeatingMap.updateLadleStatus(heat_id, stladle_track_id);
		if(result==1) {
			return new RestResponse("SUCCESS", "Updated Ladle Status");
		}
		return new RestResponse("SUCCESS", "Error while updating ladle status");
		}
		else {
			return new RestResponse("ERROR", "No Parts Are Added to the selected ladle!!");
		}
		
	}

	
	@RequestMapping(value = "/setLadleStatusDown", method = RequestMethod.POST,  headers = {"Content-type=application/json" })
	public @ResponseBody RestResponse setLadleStatusDown(@RequestParam("stladle_track_id") Integer stladle_track_id,HttpSession sessionUser) {
		logger.info(SLStatusTrackingController.class + "...setLadleDown()");
		Integer result=null;
		result=steelLadleHeatingMap.setLadleStatusDown(stladle_track_id,sessionUser);
		if(result==1) {
			return new RestResponse("SUCCESS", result+"");
		}
		return new RestResponse("SUCCESS", "Error while downing ladle");
		
	}
	
}
