package com.smes.trans.controller;

import java.util.Date;
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

import com.smes.admin.controller.RoleDetailsController;
import com.smes.admin.service.impl.CommonService;
import com.smes.trans.model.EofHeatDetails;
import com.smes.trans.model.HmReceiveBaseDetails;
import com.smes.trans.service.impl.EofProductionService;
import com.smes.trans.service.impl.HmLadleMixDetailsService;
import com.smes.trans.service.impl.HotMetalReceiveService;
import com.smes.util.Constants;
import com.smes.util.GenericClass;
import com.smes.util.RestResponse;
import com.smes.util.JPODDetailsUtil;

@Controller
@RequestMapping("HMReceive")
public class HotMetalReceiveController {
	
	private static final Logger logger = Logger.getLogger(HotMetalReceiveController.class);
	@Autowired
	JPODDetailsUtil util;
	
	@Autowired
	private HotMetalReceiveService hotMetalReceiveService;
	
	@Autowired
	private EofProductionService eofProductionservice;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private HmLadleMixDetailsService hmMixService;
	
	@RequestMapping("/HMReceiveTransView")
	public ModelAndView getHMReceiveDetails() {
		logger.info(HotMetalReceiveController.class+"...getHMReceiveDetails()");
		ModelAndView model = new ModelAndView("transaction/ReceiveHotMetalDetails");
		return model;
	}
	@RequestMapping(value = "/getHMDetailsbyId", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody HmReceiveBaseDetails getHmReceieveDetailsById(@RequestParam("recvid") Integer recvid) {
		return hotMetalReceiveService.getHmReceiveDetailsById(recvid);
	}
	
	@RequestMapping(value = "/getHMDetailsbyStatus", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<HmReceiveBaseDetails> getHMDetailsbyStatus(@RequestParam("STAGE") String stage,
			@RequestParam("LADLE_STATUS") String ladlestatus) {
		logger.info(HotMetalReceiveController.class+"...getHMDetailsbyStatus()");

		Integer stageid=commonService.getLookupIdByQuery("select lookup_id from LookupMasterModel where lookup_type='HM_LADLE_STAGE_STATUS_TYPE' and lookup_code='"+stage+"' and lookup_status=1");
		//Integer ladlestatusid=commonService.getLookupIdByQuery("select lookup_id from LookupMasterModel where lookup_type='HM_LADLE_STATUS' and lookup_code='"+ladlestatus+"' and lookup_status=1");
		
		String planstatusiddata[]=ladlestatus.split(",");
		
		String ids="";
		
		for(Integer i=0;i<planstatusiddata.length;i++)
		{
			Integer ladlestatusid=commonService.getLookupIdByQuery("select lookup_id from LookupMasterModel where lookup_type='HM_LADLE_STATUS' and lookup_code='"+planstatusiddata[i]+"' and lookup_status=1");
			ids=ladlestatusid+","+ids;
		}	
		
		ids=ids.substring(0,ids.length()-1);
		return hotMetalReceiveService.getHMDetailsbyStatus(stageid,ids);

	}
	
	
	@RequestMapping(value = "/getHMDetailsbyInterface", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody RestResponse getHMDetailsbyInterface() {
		logger.info(HotMetalReceiveController.class+"...getHMDetailsbyInterface()");
	try{
		Integer result=hotMetalReceiveService.getHMDetailsbyInterface();
		
		if(result>0)
		{
			return new RestResponse("SUCCESS", Constants.INTF_SUCCESS);
		}else{
			return new RestResponse("FAILURE", Constants.INTF_NODATA);
		}}catch(Exception e)
		{
			//e.printStackTrace();
			logger.error(RoleDetailsController.class+" Inside getHMDetailsbyInterface Exception..", e);
			return new RestResponse("FAILURE", Constants.INTF_FAILURE);
		}

	}
	
	@RequestMapping(value = "/getHMDetailsFromJPOD", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<HmReceiveBaseDetails> getHMDetailsFromJPOD() 
	{
		
		//JPODDetailsUtil util=new JPODDetailsUtil();
		List lst =util.receieveHMdetailsFromJPOD();
		return lst;
		//return null;
		
	}
	
	
	
	@RequestMapping(value = "/SaveOrUpdate", method = RequestMethod.POST, headers = { "Content-type=application/json" })
	public @ResponseBody RestResponse hotMetalReceiveDetailsSaveOrUpdate(
			@RequestBody HmReceiveBaseDetails hmRecvDetails,HttpSession session,@RequestParam String ladle_no) {
		
		try{
			logger.info(HotMetalReceiveController.class);
			String result = "";
		
		if((hmRecvDetails.getHmRecvId())>0)
		{
			
			hmRecvDetails.setHmRecvDate(GenericClass.getDateObject("dd/MM/yyyy HH:mm", hmRecvDetails.getHmRecvDate_s()));
			hmRecvDetails.setHmLadleProdDt(GenericClass.getDateObject("dd/MM/yyyy HH:mm", hmRecvDetails.getHmLadleProdDt_s()));
		
			
 			hmRecvDetails.setUpdatedBy(Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
			hmRecvDetails.setUpdatedDateTime(new Date());
			
			result=hotMetalReceiveService.hotMetalReceiveDetailsUpdate(hmRecvDetails);
			
		}else{
			
			hmRecvDetails.setHmRecvDate(GenericClass.getDateObject("dd/MM/yyyy HH:mm", hmRecvDetails.getHmRecvDate_s()));
			hmRecvDetails.setHmLadleProdDt(GenericClass.getDateObject("dd/MM/yyyy HH:mm", hmRecvDetails.getHmLadleProdDt_s()));
			hmRecvDetails.setHmRecvId(null);
			hmRecvDetails.setCreatedBy(Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
			hmRecvDetails.setCreatedDateTime(new Date());
			//hmRecvDetails.setUpdatedBy(Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
			//hmRecvDetails.setUpdatedDateTime(new Date());
			
			
			Integer stageid=commonService.getLookupIdByQuery("select lookup_id from LookupMasterModel where lookup_type='HM_LADLE_STAGE_STATUS_TYPE' and lookup_code='AVAILABLE' and lookup_status=1");
			Integer ladlestatusid=commonService.getLookupIdByQuery("select lookup_id from LookupMasterModel where lookup_type='HM_LADLE_STATUS' and lookup_code='RECVD' and lookup_status=1");
			Integer datasourceid=commonService.getLookupIdByQuery("select lookup_id from LookupMasterModel where lookup_type='DATA_SOURCE_TYPE' and lookup_code='MANUAL' and lookup_status=1");
		
			hmRecvDetails.setHmLadleStageStatus(stageid);
			hmRecvDetails.setHmLadleStatus(ladlestatusid);
			hmRecvDetails.setDataSource(datasourceid);
			
			result = hotMetalReceiveService.hotMetalReceiveDetailsSave(hmRecvDetails);
		}
		if(result.equals(Constants.SAVE)){
			commonService.updateRecordByQuery("update HmLadleMasterModel set hm_ladle_status='NOT_AVAILABLE',updated_date_time=sysdate,updated_by="+Integer.parseInt(session.getAttribute("USER_APP_ID").toString())+" where hm_ladle_no='"+hmRecvDetails.getHmLadleNo()+"'");
		}
		
		if(result.equals(Constants.UPDATE)){
			commonService.updateRecordByQuery("update HmLadleMasterModel set hm_ladle_status='NOT_AVAILABLE',updated_date_time=sysdate,updated_by="+Integer.parseInt(session.getAttribute("USER_APP_ID").toString())+" where hm_ladle_no='"+hmRecvDetails.getHmLadleNo()+"'");
			commonService.updateRecordByQuery("update HmLadleMasterModel set hm_ladle_status='AVAILABLE',updated_date_time=sysdate,updated_by="+Integer.parseInt(session.getAttribute("USER_APP_ID").toString())+" where hm_ladle_no='"+ladle_no+"'");
		}
		
		if(result.equals(Constants.SAVE) || result.equals(Constants.UPDATE))
		{
			return new RestResponse("SUCCESS", result);
		}else{
			return new RestResponse("FAILURE", result);
		}
		}catch(Exception e)
		{
			//e.printStackTrace();
			logger.error(HotMetalReceiveController.class+" Inside hotMetalReceiveDetailsSaveOrUpdate Exception..", e);
			return new RestResponse("FAILURE", e.getMessage());
		}
	}
	
	
	@RequestMapping(value = "/hotMetalReceiveStatusUpdate", method = RequestMethod.POST, headers = { "Content-type=application/json" })
	public @ResponseBody RestResponse  hotMetalReceiveStatusUpdate(@RequestParam String hmRecvId,HttpSession session) {
		
		try{
			logger.info(HotMetalReceiveController.class);
			String result = "";
			Long recvId=Long.parseLong(hmRecvId);
			if(recvId>0)
			{	
				result=hotMetalReceiveService.hotMetalReceiveStatusUpdate(recvId);
				return new RestResponse("SUCCESS", result);
				
			}
			else{
				return new RestResponse("FAILURE", result);
			}
		}catch (Exception e) {
			//e.printStackTrace();
			logger.error(HotMetalReceiveController.class+" Inside hotMetalReceiveDetailsSaveOrUpdate Exception..", e);
			return new RestResponse("FAILURE", e.getMessage());
		}
		
	}
	
	
		

	@RequestMapping(value = "/HMNextProcessUnit", method = RequestMethod.POST, headers = { "Content-type=application/json" })
	public @ResponseBody RestResponse hotMetalNextProcessUnit(
			@RequestBody HmReceiveBaseDetails hmRecvDetails,HttpSession session) {
		
		try{
			logger.info(HotMetalReceiveController.class);
			String result = "";
			
				result =hotMetalReceiveService.hotMetalLadleNextProcessUpdate(hmRecvDetails,Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
			
			
		if(result.equals(Constants.SAVE) || result.equals(Constants.UPDATE))
		{
			return new RestResponse("SUCCESS", result);
		}else{
			return new RestResponse("FAILURE", result);
		}
		}catch(Exception e)
		{
			//e.printStackTrace();
			logger.error(HotMetalReceiveController.class+" Inside hotMetalReceiveDetailsSaveOrUpdate Exception..", e);
			return new RestResponse("FAILURE", e.getMessage());
		}
	}
	
	
	
	@RequestMapping(value = "/HMMixSave", method = RequestMethod.POST, headers = { "Content-type=application/json" })
	public @ResponseBody RestResponse hotMetalMixSave(
			@RequestBody HmReceiveBaseDetails hmRecvDetails,@RequestParam String t4n_allids,HttpSession session) {
		
		try{
			logger.info(HotMetalReceiveController.class);
			String result = "";
			
			result=hotMetalReceiveService.hotMetalMixDetailsSaveAll(hmRecvDetails,t4n_allids,Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
			
		if(result.equals(Constants.SAVE) || result.equals(Constants.UPDATE))
		{
			return new RestResponse("SUCCESS", result);
		}else{
			return new RestResponse("FAILURE", result);
		}
		}catch(Exception e)
		{
			e.printStackTrace();
			logger.error(HotMetalReceiveController.class+" Inside hotMetalMixSave Exception..", e);
			return new RestResponse("FAILURE", e.getMessage());
		}
	}
	
	
	@RequestMapping(value = "/SaveHMDataArray", method = RequestMethod.POST, headers = { "Content-type=application/json" })
	public @ResponseBody RestResponse saveHMDataArray(
			@RequestBody HmReceiveBaseDetails hmRecvDetails,HttpSession session) {
		
		try{
			logger.info(HotMetalReceiveController.class);
			String result = "";
			
			result=hotMetalReceiveService.saveHMDataArray(hmRecvDetails,Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
			
		if(result.equals(Constants.SAVE) || result.equals(Constants.UPDATE))
		{
			return new RestResponse("SUCCESS", result);
		}else{
			return new RestResponse("FAILURE", result);
		}
		}catch(Exception e)
		{
			e.printStackTrace();
			logger.error(HotMetalReceiveController.class+" Inside hotMetalMixSave Exception..", e);
			return new RestResponse("FAILURE", e.getMessage());
		}
	}
	
	@RequestMapping(value = "/hmMetalReceiptUpdate", method = RequestMethod.POST, headers = { "Content-type=application/json" })
	public @ResponseBody RestResponse hotMetalReceiveDetailsSaveOrUpdate(@RequestParam("hmRevId") Integer hmRevId,@RequestParam("tareWeight") Double tareWeight,@RequestParam("trans_si_no") Integer trans_si_no,HttpSession session) {
		
		try{
		Double hmWt=hotMetalReceiveService.updateHmDetails(hmRevId,tareWeight);
		EofHeatDetails eofHeatDetails=new EofHeatDetails();
		eofHeatDetails =eofProductionservice.getById(trans_si_no);
				
		eofHeatDetails.setHm_wt(hmWt);
		eofHeatDetails.setUpdatedDateTime(new Date());
		eofHeatDetails.setUpdatedBy(Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
		String result = "";
		result=eofProductionservice.eofHeatProductionUpdate(eofHeatDetails);
		if(result.equals(Constants.SAVE) || result.equals(Constants.UPDATE))
		{
			return new RestResponse("SUCCESS",hmWt+"");
		}else{
			return new RestResponse("FAILURE", result);
		}
		}catch(Exception e) {
			e.printStackTrace();
			return new RestResponse("FAILURE", e.getMessage());
		}
		
	}
}
