package com.smes.trans.controller;

import java.util.List;

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

import com.smes.trans.service.impl.DaywiseMaterialConsService;
import com.smes.util.Constants;
import com.smes.util.MaterialConsumptionDTO;
import com.smes.util.RestResponse;

@Controller
@RequestMapping("daywiseMtrlCons")
public class DaywiseMaterialConsController {
	private static final Logger logger = Logger.getLogger(DaywiseMaterialConsController.class);
	
	@Autowired
	DaywiseMaterialConsService dayMtrlConsServ;
	
	@RequestMapping("/dayMtrlConsView")
	public ModelAndView getDaywiseMtrlConsView() {
		logger.info(DaywiseMaterialConsController.class + "...getDaywiseMtrlConsView()");
		ModelAndView model = new ModelAndView("transaction/DaywiseMtrlConsumption");
		return model;
	}
	
	@RequestMapping(value = "/getDaywiseMtrlCons", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<MaterialConsumptionDTO> getDaywiseMtrlCons(String prodDate) {
		logger.info(DaywiseMaterialConsController.class + " getDaywiseMtrlCons " + prodDate);
		return dayMtrlConsServ.getDaywiseMtrlConsumption(prodDate,Constants.MTRL_CONS_DAY);
	}
	
	@RequestMapping(value = "/saveOrUpdateDayConsumptions", method = RequestMethod.POST, headers = { "Content-type=application/json" })
	public @ResponseBody RestResponse saveOrUpdateDayConsumptions(@RequestBody MaterialConsumptionDTO mtrlConsDtoObj,
			@RequestParam String prodDate,Integer totalHeatsProduced,HttpSession session) {
		String result = "";
		try {
			List<MaterialConsumptionDTO> mtrlConsDtoLi = mtrlConsDtoObj.getMtrlConsDtoLi();
			Integer userId = Integer.parseInt(session.getAttribute("USER_APP_ID").toString());
			result = dayMtrlConsServ.saveOrUpdateDayConsumptions(mtrlConsDtoLi,prodDate,totalHeatsProduced,userId);
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(CasterProductionController.class
					+ " Inside saveOrUpdateDayConsumptions Exception..", e);
		}
		if(result.equals(Constants.SAVE) || result.equals(Constants.UPDATE)){
			return new RestResponse("SUCCESS", result);
		}else{
			return new RestResponse("FAILURE", result);
		}
	}
	
	@RequestMapping(value = "/checkConsumptionPosting", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody boolean checkConsumptionPosting(@RequestParam String productionDate) {
		logger.info(DaywiseMaterialConsController.class + "...checkConsumptionPosting()");
		return dayMtrlConsServ.isConsumptionPosted(productionDate);
	}
}