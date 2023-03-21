package com.smes.trans.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smes.masters.model.TundishMaintanaceLogModel;
import com.smes.trans.model.TundishTrnsHistoryModel;
import com.smes.trans.service.impl.TundishTrnsHistoryService;
import com.smes.util.Constants;
import com.smes.util.RestResponse;
import com.smes.util.TundishTransactionDTO;

@Controller
@RequestMapping("trnsTundish")
public class TundishTrnsController {
	@Autowired
	TundishTrnsHistoryService tundishTrnsHistoryService;
	
	
	@RequestMapping(value = "/getTundishDetails", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody TundishTrnsHistoryModel getTundishStatusHistoryByTundishId( Integer tundishId){
		return tundishTrnsHistoryService.getTundishStatusHistoryByTundishId(tundishId);
	}
	
	@RequestMapping(value = "/getTundishLogEvent", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<TundishMaintanaceLogModel> getTundishStatusHistoryByEvent( Integer tundishTransId){
		return tundishTrnsHistoryService.getTundishEventsByTnsId(tundishTransId);
	}
	
	@RequestMapping(value = "/getTundishTrnsHistory", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<TundishTrnsHistoryModel>  getTundishStatusHistor( Integer tundishTransId){
		return tundishTrnsHistoryService.getAllhistory(tundishTransId);
	}
	
	@RequestMapping(value = "/saveTundishLogEvent", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody RestResponse saveTrnsHistoryEvents(@RequestBody TundishTransactionDTO trnsHstryMdl ,HttpSession session) {
		
		
		 String result=tundishTrnsHistoryService.saveTundishHistoryandEvents(trnsHstryMdl,session.getAttribute("USER_APP_ID").toString());
		 if(result.equals(Constants.SAVE)) {
			 return new RestResponse("SUCCESS", result);
		 }else {
			 return new RestResponse("FAILURE", result);
		 }
	}
	
	

}
