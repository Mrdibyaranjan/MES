package com.smes.trans.controller;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.smes.trans.model.SLShiftDetailsModel;
import com.smes.trans.service.impl.SLShiftDtlsService;
import com.smes.util.Constants;
import com.smes.util.RestResponse;

@RestController
@RequestMapping("SLShiftCntrl")
public class SLShiftDtlsController {
	private static final Logger logger = Logger.getLogger(SLShiftDtlsController.class);
	
	@Autowired
	private SLShiftDtlsService slShiftDtlsService;
	
	//save or update
		@RequestMapping(value = "/SaveOrUpdate", method = RequestMethod.POST, headers = { "Content-type=application/json" })
		public @ResponseBody RestResponse LadleShiftSaveOrUpdate(@RequestBody SLShiftDetailsModel steelLadleDtls,HttpSession session) {
			logger.info(SLShiftDtlsController.class+"...LadleShiftSaveOrUpdate()");
				String result = "";
			if(steelLadleDtls.getTrns_shift_si_no()>0){
				steelLadleDtls.setUpdated_by(Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
				steelLadleDtls.setUpdated_date_time(new Date());
				result = slShiftDtlsService.updateSteelLadle(steelLadleDtls);
			}else{
				steelLadleDtls.setCreated_by(Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
				steelLadleDtls.setCreated_date_time(new Date());
				steelLadleDtls.setTrns_shift_si_no(null);
				//steelLadleDtls.setSteel_ladle_rcvd(steelLadleDtls.getSteel_ladle_rcvd());
				
				result = slShiftDtlsService.saveSteelLadle(steelLadleDtls,Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
			}
			
			if(result.equals(Constants.SAVE) || result.equals(Constants.UPDATE)){
				return new RestResponse("SUCCESS", result);
			}else{
				return new RestResponse("FAILURE", result);
			}	
		}
		
		
		@RequestMapping(value="/CheckShift",method = RequestMethod.POST, headers = { "Content-type=application/json" })
		public @ResponseBody RestResponse CheckShiftByDate(@RequestBody SLShiftDetailsModel steelLadleDtls){
			String result = "";
			result=slShiftDtlsService.checkShift(steelLadleDtls);
			return new RestResponse("result", result);
		}
		

		@RequestMapping(value="/ShiftAutofill",method = RequestMethod.POST, headers = { "Content-type=application/json" })
		public SLShiftDetailsModel ShiftAutofill(@RequestBody SLShiftDetailsModel steelLadleDtls){
			SLShiftDetailsModel result = new SLShiftDetailsModel();
			result=slShiftDtlsService.shiftAutofill(steelLadleDtls);
			if(result!=null)
				return result;
			else
				return result;
			
		}
		
}
