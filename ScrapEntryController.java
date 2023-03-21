package com.smes.trans.controller;

import java.util.ArrayList;
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

import com.smes.admin.service.impl.CommonService;
import com.smes.masters.service.impl.ScrapBucketStatusMasterService;
import com.smes.trans.model.ScrapBucketDetails;
import com.smes.trans.model.ScrapBucketHdr;
import com.smes.trans.model.ScrapWeightDetails;
import com.smes.trans.service.impl.ScrapEntryService;
import com.smes.util.Constants;
import com.smes.util.RestResponse;

@Controller
@RequestMapping("scrapEntry")
public class ScrapEntryController {

private static final Logger logger = Logger.getLogger(ScrapEntryController.class);
	

	@Autowired
	private ScrapEntryService scrapEntryService;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private ScrapBucketStatusMasterService scrapBucketStatusMasterService;
	
	@RequestMapping("/scrapEntryView")
	public ModelAndView getScrapDetails() {
		logger.info(ScrapEntryController.class+"...getScrapDetails()");
		ModelAndView model = new ModelAndView("transaction/ScrapEntryDetails");
		return model;
	}
	//som
	@RequestMapping(value = "/getLoadedScrapBktsByHeaderId", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody ScrapBucketHdr getLoadedScrapBktsByHeaderId(@RequestParam Integer scrap_bkt_hrd_id) {
		logger.info(ScrapEntryController.class+"...getLoadedScrapBktsByHeaderId()");
		ScrapBucketHdr hdr = new ScrapBucketHdr();
		//Integer load_statusId =commonService.getLookupIdByQuery("select lookup_id from LookupMasterModel where lookup_type = '"+Constants.SCRAP_BKT_STATUS+"' and lookup_code='"+Constants.SCRAP_BKT_LOAD_STATUS+"'");
		hdr = scrapEntryService.getLoadedScrapBktsByHeaderId(scrap_bkt_hrd_id);
		return hdr;
	}
	
	@RequestMapping(value = "/getLoadedScrapBktNos", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<ScrapBucketHdr> getLoadedScrapBktNos() {
		logger.info(ScrapEntryController.class+"...getLoadedScrapBktNos()");
		List<ScrapBucketHdr> hdr = new ArrayList<ScrapBucketHdr>();
		//Integer load_statusId =commonService.getLookupIdByQuery("select lookup_id from LookupMasterModel where lookup_type = '"+Constants.SCRAP_BKT_STATUS+"' and lookup_code='"+Constants.SCRAP_BKT_LOAD_STATUS+"'");
		hdr = scrapEntryService.getLoadedScrapBktNos();
		return hdr;
	}
	
	@RequestMapping(value = "/getScrapEntryDetails", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<ScrapBucketDetails> getScrapEntryDetails(@RequestParam Integer scrap_bucket_id, @RequestParam Integer scrap_pattern_id, @RequestParam Integer bucket_header_id) {
		logger.info(ScrapEntryController.class+"...getScrapDtlsEntryDetails()");
		return scrapEntryService.getScrapEntryDetails(scrap_bucket_id, scrap_pattern_id, bucket_header_id);
	}
		
	@RequestMapping(value = "/SaveOrUpdate", method = RequestMethod.POST, headers = { "Content-type=application/json" })
	public @ResponseBody RestResponse scrapEntryHdrSaveOrUpdate(@RequestBody ScrapBucketDetails scrapBktDetails,@RequestParam(required=false,name="totQty") Double totQty,@RequestParam(required=false,name="scrap_bkt_id") Integer scrap_bkt_id,@RequestParam(required=false,name="scrap_patrn_id") Integer scrap_patrn_id,@RequestParam(required=false,name="scrap_prod_dt") String scrap_prod_dt, HttpSession session) {
		logger.info(ScrapEntryController.class+"scrapEntryHdrSaveOrUpdate");
	try{
		logger.info(ScrapEntryController.class+"scrapEntryHdrSaveOrUpdate"+ scrap_patrn_id);
		String result="";
		result =scrapEntryService.scrapBktDtlsSaveOrUpdate(scrapBktDetails,totQty,scrap_bkt_id,scrap_prod_dt,Integer.parseInt(session.getAttribute("USER_APP_ID").toString()),scrap_patrn_id);
		if(result.equals(Constants.SAVE)|| result.equals(Constants.UPDATE))
		{
			return new RestResponse("SUCCESS", result);
		}else{
			return new RestResponse("FAILURE", result);
		}
	}
	catch(Exception e)
	{
		e.printStackTrace();
		logger.error(ScrapEntryController.class+" Inside scrapEntryHdrSaveOrUpdate Exception..", e);
		return new RestResponse("FAILURE", e.getMessage());
	}
 }
	
	@RequestMapping(value = "/getScrapQty", method = RequestMethod.POST, headers = { "Content-type=application/json" })
	public @ResponseBody Integer getScrapQty() {
		logger.info(ScrapEntryController.class+" getScrapQty");
		Integer scrapQty = 0;
	try{
		scrapQty = commonService.getLookupIdByQuery("select lookup_value from LookupMasterModel where lookup_type='SCRAP_QUANTITY' and lookup_code='TOTAL_SCRAP_QTY' and lookup_status=1");
		return scrapQty;
	}
	catch(Exception e)
	{
		logger.error(ScrapEntryController.class+" Inside getScrapQty Exception..", e);
		return scrapQty;
	}
 }
	
	@RequestMapping(value = "/getScrapWeightfrmIntf", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<ScrapWeightDetails> getScrapEntryDetails(@RequestParam String work_center) {
		List<ScrapWeightDetails> lst=scrapEntryService.getScrapDetailsFrmIntf(work_center);
		return lst;	
	}
	
	@RequestMapping(value = "/updateScrapIntfEntry", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody RestResponse updateScrapWeightIntfEntry(@RequestParam Integer trans_no) {
		
		
		if(scrapEntryService.updateIntfScrapEntry(trans_no).equals("SUCCESS"))
		{
			return new RestResponse("SUCCESS", "UPDATED");
		}else {
			return new RestResponse("FAILED", "NOT UPDATED");
		}
		
	}
	
}
