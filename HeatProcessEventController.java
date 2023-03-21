package com.smes.trans.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smes.admin.model.AppUserAccountDetails;
import com.smes.admin.service.impl.CommonService;
import com.smes.admin.service.impl.UserAccountDetailsService;
import com.smes.masters.model.PSNDocsMasterModel;
import com.smes.masters.model.PSNProductMasterModel;
import com.smes.masters.service.impl.PSNDocsMasterService;
import com.smes.masters.service.impl.PSNProductMasterService;
import com.smes.trans.dao.impl.CCMHeatDetailsDao;
import com.smes.trans.dao.impl.CasterProductionDao;
import com.smes.trans.dao.impl.HeatProceeEventDao;
import com.smes.trans.model.CCMHeatConsMaterialsDetails;
import com.smes.trans.model.CCMHeatDetailsModel;
import com.smes.trans.model.EofDispatchDetails;
import com.smes.trans.model.HeatChemistryChildDetails;
import com.smes.trans.model.HeatChemistryHdrDetails;
import com.smes.trans.model.HeatConsMaterialsDetails;
import com.smes.trans.model.HeatConsScrapMtrlDetails;
import com.smes.trans.model.HeatProcessEventDetails;
import com.smes.trans.model.HeatProcessParameterDetails;
import com.smes.trans.model.IfacesmsLpDetailsModel;
import com.smes.trans.service.impl.HeatProceeEventService;
import com.smes.util.Constants;
import com.smes.util.RestResponse;
import com.smes.util.SpectroLabUtil;
import com.smes.wrappers.EOFRequestWrapper;


@Controller
@RequestMapping("heatProcessEvent")
public class HeatProcessEventController {
	
	private static final Logger logger = Logger.getLogger(HeatProcessEventController.class);

	@Autowired
	private HeatProceeEventService heatProcessEventService;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private PSNDocsMasterService psnDocsMstrService;
	
	@Autowired
	private PSNProductMasterService psnProdMstrService;
		
	@Autowired
	private HeatProceeEventDao heatProcessEventDao;
	
	@Autowired
	private CasterProductionDao ccmProductionDao;
	
	@Autowired
	SpectroLabUtil spectroLabUtil;
	
	@Autowired
	CCMHeatDetailsDao ccmheatDao;
	
	@Autowired
	private UserAccountDetailsService userAccountDetailsService;
	
	@RequestMapping(value = "/getMtrlDetailsByType", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<HeatConsMaterialsDetails> getMtrlDetailsByType(@RequestParam String mtrlType,@RequestParam Integer eof_trns_sno,@RequestParam String psn_no) {
		logger.info(HeatProcessEventController.class+"...getMtrlDetailsByType()");
		return heatProcessEventService.getMtrlDetailsByType(mtrlType,eof_trns_sno,psn_no);

	}

	@RequestMapping(value = "/getMtrlDetailsByCCMType", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<CCMHeatConsMaterialsDetails> getMtrlDetailsByCCMType(@RequestParam String mtrlType,@RequestParam Integer eof_trns_sno,@RequestParam String psn_no) {
		logger.info(HeatProcessEventController.class+"...getMtrlDetailsByType()");
		return heatProcessEventService.getMtrlDetailsByCCMType(mtrlType,eof_trns_sno,psn_no);

	}
	

    @RequestMapping(value = "/SaveOrUpdate", method = RequestMethod.POST, headers = { "Content-type=application/json" })
	public @ResponseBody RestResponse heatConsmtrlsSaveOrUpdate(@RequestBody HeatConsMaterialsDetails heatConsMtrls,
			@RequestParam Integer sub_unit,@RequestParam String eventname, HttpSession session) {
		logger.info(HeatProcessEventController.class+"heatConsmtrlsSaveOrUpdate");
	try{
		
		String result="";
		
		result = heatProcessEventService.heatConsMtrlsSaveOrUpdate(heatConsMtrls,sub_unit,eventname,Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
		
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
			logger.error(HeatProcessEventController.class+" Inside heatConsmtrlsSaveOrUpdate Exception..", e);
			return new RestResponse("FAILURE", e.getMessage());
		}
 }
	
   @RequestMapping(value = "/ccmSaveOrUpdate", method = RequestMethod.POST, headers = { "Content-type=application/json" })
	public @ResponseBody RestResponse ccmheatConsmtrlsSaveOrUpdate(@RequestBody CCMHeatConsMaterialsDetails ccmheatConsMtrls,
			@RequestParam Integer sub_unit,@RequestParam String eventname, HttpSession session) {
		logger.info(HeatProcessEventController.class+"heatConsmtrlsSaveOrUpdate");
	try{
		
		String result="";
		
		result = heatProcessEventService.ccmheatConsMtrlsSaveOrUpdate(ccmheatConsMtrls,sub_unit,eventname,Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
		
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
			logger.error(HeatProcessEventController.class+" Inside heatConsmtrlsSaveOrUpdate Exception..", e);
			return new RestResponse("FAILURE", e.getMessage());
		}
}
	

    @RequestMapping(value = "/ScrapSaveOrUpdate", method = RequestMethod.POST, headers = { "Content-type=application/json" })
	public @ResponseBody RestResponse  heatConsScrapMtrlsSaveOrUpdate(@RequestBody HeatConsScrapMtrlDetails heatConsScrapMtrls,
			@RequestParam String cons_date,@RequestParam Integer sub_unit,@RequestParam String eventname,
			 HttpSession session) {
		logger.info(HeatProcessEventController.class+"heatConsScrapMtrlsSaveOrUpdate");
	try{
		
		String result="";
		
		result = heatProcessEventService.heatConsScrapMtrlsSaveOrUpdate(heatConsScrapMtrls,cons_date,sub_unit,eventname,Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
		
		
		if(result.equals(Constants.SAVE)|| result.equals(Constants.UPDATE))
		{
		return new RestResponse("SUCCESS", result);
		}else{
		return new RestResponse("FAILURE", result);
	  }
		
	}//TRY
	catch(Exception e)
	{
		e.printStackTrace();
		logger.error(HeatProcessEventController.class+" Inside heatConsScrapMtrlsSaveOrUpdate Exception..", e);
		return new RestResponse("FAILURE", e.getMessage());
	}
 }
	
	@RequestMapping(value = "/getPSNDocsByPSN", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<PSNDocsMasterModel> getPSNDocsByPSN(@RequestParam String psn_no, @RequestParam Integer psn_hdr_sl_no, HttpSession session) {
		logger.info(HeatProcessEventController.class+"...getPSNDocsByPSN()");
		
		return psnDocsMstrService.getPSNDocsMasterDetails(psn_hdr_sl_no);

	}
	
	
	@RequestMapping(value = "/PSNDocsView", method = RequestMethod.GET)
	public void PSNDocsView(
			HttpSession session,HttpServletResponse response,@RequestParam Integer doc_sl_no) {
		PSNDocsMasterModel psnDocsMdl=new PSNDocsMasterModel();
		psnDocsMdl=psnDocsMstrService.PSNDocsDownload(doc_sl_no);
		try{
			
			logger.info(HeatProcessEventController.class+"PSNDocsView");
			response.setHeader("Content-Disposition", "inline;filename=\"" +psnDocsMdl.getUpload_file_name()+ "\"");
			response.setContentType(psnDocsMdl.getFile_content_type());
			OutputStream out = response.getOutputStream();
			FileCopyUtils.copy(psnDocsMdl.getFile_data().getBinaryStream(),out);
			out.close();
			out.flush();
			
			
			
		}	
		catch(Exception e)
		{
			e.printStackTrace();
			logger.error(HeatProcessEventController.class+" Inside PSNDocsView Exception..", e);
			//return "error";
		}
		//return null;
	}
		
	
	@RequestMapping(value = "/getChemDtlsByAnalysis", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<HeatChemistryChildDetails> getChemDtlsByAnalysis(@RequestParam Integer analysis_id,@RequestParam String heat_id,
			@RequestParam Integer heat_counter,@RequestParam Integer sub_unit_id,@RequestParam String sample_no,
			@RequestParam Integer psn_id,@RequestParam Integer hm_recv_id,@RequestParam String analysis_type) {
		logger.info(HeatProcessEventController.class+"...getChemDtlsByAnalysis()");
		String copy_chem = "N";
		/*commented on 11/09/2018 testing observation - suma
		if(analysis_type.equals("EAF_QCA_CHEM")){
			analysis_id=commonService.getLookupIdByQuery("select lookup_id from LookupMasterModel where lookup_type='CHEM_LEVEL' and lookup_value='EAF_TAP_CHEM' and lookup_status=1");
		}*/
		List<HeatChemistryHdrDetails> sample_li = heatProcessEventService.getSampleDtlsByAnalysis(sub_unit_id, heat_id, analysis_id, heat_counter);
		if(analysis_type.equals("HM_CHEM") && sample_li.size() == 0){
			copy_chem = "Y";
		}
		return heatProcessEventService.getChemDtlsByAnalysis(analysis_id,heat_id,heat_counter,sub_unit_id,sample_no,psn_id,hm_recv_id,copy_chem);

	}
	
	@RequestMapping(value = "/getChemDtlsBySpectro", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<HeatChemistryChildDetails> getChemDtlsBySpectro(@RequestParam Integer analysis_id,@RequestParam String heat_id,
			@RequestParam Integer heat_counter,@RequestParam Integer sub_unit_id,@RequestParam String sample_no,
			@RequestParam Integer psn_id,@RequestParam Integer hm_recv_id,@RequestParam String analysis_type,@RequestParam String actual_sample) throws IOException {
		logger.info(HeatProcessEventController.class+"...getChemDtlsBySpectro()");
		String copy_chem = "N";
	
		List<HeatChemistryHdrDetails> sample_li = heatProcessEventService.getSampleDtlsByAnalysis(sub_unit_id, heat_id, analysis_id, heat_counter);
		if(analysis_type.equals("HM_CHEM") && sample_li.size() == 0){
			copy_chem = "Y";
		}
		return heatProcessEventService.getChemDtlsBySpectro(analysis_id,heat_id,heat_counter,sub_unit_id,sample_no,psn_id,hm_recv_id,copy_chem,actual_sample);

	}
	
	@RequestMapping(value = "/getTestSpectro", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody RestResponse getTestSpectro() throws IOException {
		logger.info(HeatProcessEventController.class+"...getChemDtlsBySpectro()");
		Boolean activeConnect=spectroLabUtil.testConnection();
		if(activeConnect==true) {
			return new RestResponse("SUCCESS",activeConnect+"");
		}
		else {
			return new RestResponse("Failed",activeConnect+"");
		}
	}
	
	@RequestMapping(value = "/getChemDtlsBySampleHdrId", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<HeatChemistryChildDetails> getChemDtlsBySampleHdrId(@RequestParam Integer sample_si_id, @RequestParam Integer psn_id, @RequestParam Integer analysis_id) {
		logger.info(HeatProcessEventController.class+"...getChemDtlsBySampleHdrId()");
				
		return heatProcessEventService.getChemDtlsBySampleHdrId(sample_si_id, psn_id, analysis_id);
	}

	/*@RequestMapping(value = "/getChemHdrDtlsByAnalysis", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody HeatChemistryHdrDetails getChemHdrDtlsByAnalysis(@RequestParam Integer analysis_id,@RequestParam Integer eof_trns_sno) {
		logger.info(HeatProcessEventController.class+"...getChemDtlsByAnalysis()");
		return heatProcessEventService.getChemHdrDtlsByAnalysis(analysis_id,eof_trns_sno);

	}*/
	
	@RequestMapping(value = "/getSampleDtlsByAnalysis", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<HeatChemistryHdrDetails> getSampleDtlsByAnalysis(@RequestParam Integer sub_unit_id,@RequestParam Integer analysis_id,
			@RequestParam String heat_id, @RequestParam Integer heat_counter) {
		logger.info(HeatProcessEventController.class+"...getSampleDtlsByAnalysis()");
		return heatProcessEventService.getSampleDtlsByAnalysis(sub_unit_id, heat_id, analysis_id, heat_counter);

	}
	
	@RequestMapping(value = "/getHeatChemistryHdrDtlsById", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody HeatChemistryHdrDetails getHeatChemistryHdrDtlsById(@RequestParam Integer sample_si_Id,@RequestParam Integer sub_unit_id) {
		logger.info(HeatProcessEventController.class+"...getChemDtlsByAnalysis()");
		return heatProcessEventService.getHeatChemistryHdrDtlsById(sample_si_Id,sub_unit_id);

	}
	
	@RequestMapping(value = "/ChemDtlsSaveOrUpdate", method = RequestMethod.POST, headers = { "Content-type=application/json" })
	public @ResponseBody RestResponse  chemistryDtlsSaveOrUpdate(@RequestBody HeatChemistryHdrDetails heatChemHdrDtls,@RequestParam String eventname,@RequestParam String sample_date,HttpSession session) {
		logger.info(HeatProcessEventController.class+"chemistryDtlsSaveOrUpdate");
		try{
			String result="";
			String analysis_type;
			String samp_no = heatChemHdrDtls.getSample_no();
			//LRFHeatDetailsModel lrfHeatDet = lrfProdService.getLRFHeatDetailsByHeatNo(heatChemHdrDtls.getHeat_id(), heatChemHdrDtls.getHeat_counter());
			if(eventname.equals("LRF_CHEM_DETAILS"))
			{
			if(samp_no.substring(samp_no.length()-1, samp_no.length()).equals("1"))
				analysis_type = Constants.LRF_INITIAL_CHEM;
			else{
				if(heatChemHdrDtls.getPrev_unit().substring(0, 2).equals(Constants.PREV_UNIT_VD))
					analysis_type = Constants.LRF_AVD_CHEM;
				else
					analysis_type = Constants.LRF_CHEM;
			}
			heatChemHdrDtls.setAnalysis_type(commonService.getLookupIdByQuery("select lookup_id from LookupMasterModel where lookup_type = 'CHEM_LEVEL' and lookup_status = 1 and lookup_value='"+analysis_type+"'"));
			heatChemHdrDtls.setAnalysisType(analysis_type);
			}
			else
			{
				analysis_type = heatChemHdrDtls.getAnalysisType();
				heatChemHdrDtls.setAnalysis_type(commonService.getLookupIdByQuery("select lookup_id from LookupMasterModel where lookup_type = 'CHEM_LEVEL' and lookup_status = 1 and lookup_value='"+analysis_type+"'"));
			}
			result = heatProcessEventService.chemistryDtlsSaveOrUpdate(heatChemHdrDtls,eventname,sample_date,Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
			RestResponse resObj;
			if(result.equals(Constants.SAVE)|| result.equals(Constants.UPDATE))	{
				resObj=new RestResponse("SUCCESS", result);
				
				HeatChemistryHdrDetails obj=heatProcessEventDao.getChemHdrDtls(heatChemHdrDtls.getSample_no());
				resObj.setMsg(obj.getSample_si_no()+"");
				return resObj;
				
			}else{
				return new RestResponse("FAILURE", result);
			}
		}
		catch(Exception e){
			e.printStackTrace();
			logger.error(ScrapEntryController.class+" Inside scrapEntryHdrSaveOrUpdate Exception..", e);
			return new RestResponse("FAILURE", e.getMessage());
		}
	 }
	
	@RequestMapping(value = "/TundishChemDtlsSaveOrUpdate", method = RequestMethod.POST, headers = { "Content-type=application/json" })
	public @ResponseBody RestResponse  tundishchemistryDtlsSaveOrUpdate(@RequestBody HeatChemistryHdrDetails heatChemHdrDtls,@RequestParam String eventname,@RequestParam String sample_date,HttpSession session) {
		logger.info(HeatProcessEventController.class+"chemistryDtlsSaveOrUpdate");
		try{
			String result="";
			String analysis_type;
			String samp_no = heatChemHdrDtls.getSample_no();
			CCMHeatDetailsModel ccmHeatHDR=null;
			//LRFHeatDetailsModel lrfHeatDet = lrfProdService.getLRFHeatDetailsByHeatNo(heatChemHdrDtls.getHeat_id(), heatChemHdrDtls.getHeat_counter());
			if(eventname.equals("LRF_CHEM_DETAILS"))
			{
			if(samp_no.substring(samp_no.length()-1, samp_no.length()).equals("1"))
				analysis_type = Constants.LRF_INITIAL_CHEM;
			else{
				if(heatChemHdrDtls.getPrev_unit().substring(0, 2).equals(Constants.PREV_UNIT_VD))
					analysis_type = Constants.LRF_AVD_CHEM;
				else
					analysis_type = Constants.LRF_CHEM;
			}
			heatChemHdrDtls.setAnalysis_type(commonService.getLookupIdByQuery("select lookup_id from LookupMasterModel where lookup_type = 'CHEM_LEVEL' and lookup_status = 1 and lookup_value='"+analysis_type+"'"));
			heatChemHdrDtls.setAnalysisType(analysis_type);
			}
			else
			{
				analysis_type = heatChemHdrDtls.getAnalysisType();
				heatChemHdrDtls.setAnalysis_type(commonService.getLookupIdByQuery("select lookup_id from LookupMasterModel where lookup_type = 'CHEM_LEVEL' and lookup_status = 1 and lookup_value='"+analysis_type+"'"));
			}
			ccmHeatHDR=ccmProductionDao.getHeatDtlsByHeatId(heatChemHdrDtls.getHeat_id(),heatChemHdrDtls.getHeat_counter());//getting trns_ccm_heat_hdr
			result = heatProcessEventService.chemistryDtlsSaveOrUpdate(heatChemHdrDtls,eventname,sample_date,Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
		   
			RestResponse resObj;
			if(result.equals(Constants.SAVE)|| result.equals(Constants.UPDATE))	{
				resObj=new RestResponse("SUCCESS", result);
				
				HeatChemistryHdrDetails obj=heatProcessEventDao.getChemHdrDtls(heatChemHdrDtls.getSample_no());
				ccmHeatHDR.setSpectro_chem(1);
				resObj.setMsg(obj.getSample_si_no()+"");
				ccmProductionDao.saveCastDetails(ccmHeatHDR);
				return resObj;
				
			}else{
				ccmHeatHDR.setSpectro_chem(0);
				ccmProductionDao.saveCastDetails(ccmHeatHDR);
				return new RestResponse("FAILURE", result);
			}
		}
		catch(Exception e){
			e.printStackTrace();
			logger.error(ScrapEntryController.class+" Inside scrapEntryHdrSaveOrUpdate Exception..", e);
			return new RestResponse("FAILURE", e.getMessage());
		}
	 }
	
	@RequestMapping(value="/approvechem",method= RequestMethod.POST, headers= {"Content-type=application/json"})
	public @ResponseBody RestResponse approvechem (@RequestParam Integer sample_si_no,@RequestParam Integer sub_unit_id, HttpSession session) {
		logger.info(HeatProcessEventController.class+"approvechem");
		/*String userName="";
		AppUserAccountDetails appuseraccntdtls = new AppUserAccountDetails();
		appuseraccntdtls= userAccountDetailsService.getMstrUserAccDtlsbyId(Integer.valueOf(session.getAttribute("USER_APP_ID").toString()));
		userName=appuseraccntdtls.getUser_name();*/
		try {
			HeatChemistryHdrDetails chemHdrObj = heatProcessEventService.getHeatChemistryHdrDtlsById(sample_si_no, sub_unit_id);
			String result="";
			String userName="";
			AppUserAccountDetails appuseraccntdtls = new AppUserAccountDetails();
			
			appuseraccntdtls= userAccountDetailsService.getMstrUserAccDtlsbyId(Integer.valueOf(session.getAttribute("USER_APP_ID").toString()));
			userName=appuseraccntdtls.getUser_name();
			
			chemHdrObj.setApprove(Constants.CHEM_APPROVE);
			chemHdrObj.setUpdatedBy(Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
			chemHdrObj.setUpdatedDateTime(new Date());
			chemHdrObj.setApproved_by(userName);
			result = heatProcessEventService.approvechem(chemHdrObj);
			
			if(result.equals(Constants.SAVE)|| result.equals(Constants.UPDATE)){
				return new RestResponse("SUCCESS", result);
			}else{
				return new RestResponse("FAILURE", result);
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error(HeatProcessEventController.class+" Inside saveFinalResult Exception..", e);
			return new RestResponse("FAILURE", e.getMessage());
		}
	}
	
	
	
	@RequestMapping(value = "/saveFinalResult", method = RequestMethod.POST, headers = { "Content-type=application/json" })
	public @ResponseBody RestResponse  saveFinalResult(@RequestParam Integer sample_si_no, @RequestParam Integer sub_unit_id, HttpSession session) {
		logger.info(HeatProcessEventController.class+"saveFinalResult");
		try{
			HeatChemistryHdrDetails chemHdrObj = heatProcessEventService.getHeatChemistryHdrDtlsById(sample_si_no, sub_unit_id);
			String result="";	
			chemHdrObj.setFinal_result(Constants.CHEM_FINAL_RESULT);
			chemHdrObj.setUpdatedBy(Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
			chemHdrObj.setUpdatedDateTime(new Date());
			if(chemHdrObj.getSubUnitMasterDtls().getSub_unit_name().substring(0, 3).equals(Constants.SUB_UNIT_LRF)){
				chemHdrObj.setAnalysis_type(commonService.getLookupIdByQuery("select lookup_id from LookupMasterModel where lookup_type = 'CHEM_LEVEL' and lookup_status = 1 and lookup_value='"+Constants.LRF_LIFT_CHEM+"'"));
			}
			
			//CCMHeatDetailsModel ccmObj = ccmheatDao.getCCMheatByid(chemHdrObj.getHeat_id());
			if(chemHdrObj.getSubUnitMasterDtls().getSub_unit_name().substring(0, 3).equals(Constants.SUB_UNIT_CCM)) {
				IfacesmsLpDetailsModel ifacObj=new IfacesmsLpDetailsModel();
				
				ifacObj.setMsg_id(null);
				ifacObj.setSch_id(null);
				ifacObj.setPlanned_heat_id(null);
				ifacObj.setActual_heat_id(chemHdrObj.getHeat_id());
				ifacObj.setPrev_sch_id(null);
				ifacObj.setPrev_planned_heat_id(null);
				ifacObj.setGrade(null);
				ifacObj.setEvent_code("HEATCHEM");
				ifacObj.setInterface_status(0);
				ifacObj.setError_msg(null);
				ifacObj.setCreatedBy(Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
				ifacObj.setCreated_Date(new Date());
				ifacObj.setModified_by(null);     
				ifacObj.setModified_date(null);
				
				result = heatProcessEventService.saveFinalResultiface(chemHdrObj,ifacObj);
			}else {
				
			    result = heatProcessEventService.saveFinalResult(chemHdrObj);
			      }
			if(result.equals(Constants.SAVE)|| result.equals(Constants.UPDATE)){
				return new RestResponse("SUCCESS", result);
			}else{
				return new RestResponse("FAILURE", result);
			}
			
		}catch(Exception e){
			e.printStackTrace();
			logger.error(HeatProcessEventController.class+" Inside saveFinalResult Exception..", e);
			return new RestResponse("FAILURE", e.getMessage());
		}
	 }
	
	@RequestMapping(value = "/getHeatProcessEventDtls", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<HeatProcessEventDetails> getHeatProcessEventDtls(@RequestParam Integer eof_trns_sno,@RequestParam String unit) {
		logger.info(HeatProcessEventController.class+"...getHeatProcessEventDtls()");
		return heatProcessEventService.getHeatProcessEventDtls(eof_trns_sno,unit);

	}
	
	@RequestMapping(value = "/getHeatProcessEventDtlsByUnit", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<HeatProcessEventDetails> getHeatProcessEventDtlsByUnit(@RequestParam String heat_id,@RequestParam Integer heat_counter,@RequestParam Integer sub_unit_id) {
		logger.info(HeatProcessEventController.class+"...getHeatProcessEventDtlsByUnit()");
		return heatProcessEventService.getHeatProcessEventDtlsByUnit(heat_id, heat_counter, sub_unit_id);

	}
	
	@RequestMapping(value = "/EventSaveOrUpdate", method = RequestMethod.POST, headers = { "Content-type=application/json" })
	public @ResponseBody RestResponse heatProcessEventSaveOrUpdate(@RequestBody HeatProcessEventDetails heatProcessEventDtls,
			@RequestParam Integer trns_sno,@RequestParam String unit, HttpSession session) {
		logger.info(HeatProcessEventController.class+"heatProcessEventSaveOrUpdate");
	try{
		String result="";
		
		result = heatProcessEventService.heatProcessEventSaveOrUpdate(heatProcessEventDtls,trns_sno,unit,Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
		
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
			logger.error(HeatProcessEventController.class+" Inside heatProcessEventSaveOrUpdate Exception..", e);
			return new RestResponse("FAILURE", e.getMessage());
		}
 }
	
	@RequestMapping(value = "/EventSave", method = RequestMethod.POST, headers = { "Content-type=application/json" })
	public @ResponseBody RestResponse heatProcessEventSave(@RequestBody HeatProcessEventDetails heatProcessEventDtls, HttpSession session) {
		logger.info(HeatProcessEventController.class+"heatProcessEventSave");
	try{
		
		String result="";
		
		result = heatProcessEventService.heatProcessEventSaveOrUpdate(heatProcessEventDtls,	Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
		
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
			logger.error(HeatProcessEventController.class+" Inside heatProcessEventSave Exception..", e);
			return new RestResponse("FAILURE", e.getMessage());
		}
 }
	
	@RequestMapping(value = "/eofDispatchSave", method = RequestMethod.POST, headers = { "Content-type=application/json" })
	public @ResponseBody RestResponse eofDispatchSave(
			@RequestBody EOFRequestWrapper reqWrapper, HttpSession session) {
		
		try{
			logger.info(HeatProcessEventController.class);
			String result = "";
			
				result =heatProcessEventService.eofDispatchSave(reqWrapper,Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
			
			
		if(result.equals(Constants.SAVE) || result.equals(Constants.UPDATE))
		{
				
			return new RestResponse("SUCCESS", result);
		}else{
			return new RestResponse("FAILURE", result);
		}
		}catch(Exception e)
		{
			//e.printStackTrace();
			logger.error(HeatProcessEventController.class+" Inside eofDispatchSave Exception..", e);
			return new RestResponse("FAILURE", e.getMessage());
		}
	}
	
	@RequestMapping(value = "/getHeatHeatProcParamDtls", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<HeatProcessParameterDetails> getHeatHeatProcParamDtls(@RequestParam String heatid,@RequestParam Integer heat_cntr,@RequestParam Integer subunitid,@RequestParam String psn_no) {
		logger.info(HeatProcessEventController.class+"...getHeatHeatProcParamDtls()");
		return heatProcessEventService.getHeatProcParamDtls(heatid,heat_cntr,subunitid,psn_no);
		

	}
	
	@RequestMapping(value = "/procParamSaveOrUpdate", method = RequestMethod.POST, headers = { "Content-type=application/json" })
	public @ResponseBody RestResponse  procParamSaveOrUpdate(@RequestBody HeatProcessParameterDetails heatProcParamDtls,HttpSession session) {
		logger.info(HeatProcessEventController.class+"procParamSaveOrUpdate");
		try{
			
			String result="";
			
			result = heatProcessEventService.processParamSaveOrUpdate(heatProcParamDtls,Integer.parseInt(session.getAttribute("USER_APP_ID").toString()));
			
			
			
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
			logger.error(HeatProcessEventController.class+" Inside procParamSaveOrUpdate Exception..", e);
			return new RestResponse("FAILURE", e.getMessage());
		}
	 }
	
	@RequestMapping(value = "/getEOFCampaignLife", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<EofDispatchDetails> getEOFCampaignLife(@RequestParam Integer subUnit) {
		logger.info("in camp controller");
		return heatProcessEventService.getEOFCampaignLife(subUnit);

	}

	
	@RequestMapping(value = "/totalWtValidation", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody RestResponse totalWtValidation(@RequestParam String heat_id,@RequestParam Integer sub_unit_id,@RequestParam Integer counter ) {
		logger.info(HeatProcessEventController.class+"...totalWtValidation()");
		try{
			
		 Double totalWt=heatProcessEventService.getTotalFurnaceWeight(heat_id, sub_unit_id, counter);
		 if(totalWt>0)
		 {
		 return new RestResponse("SUCCESS",totalWt.toString());
		 }else{
			 return new RestResponse("SUCCESS","0");
		 }
		}catch(Exception e)
		{
			e.printStackTrace();
			logger.error(HeatProcessEventController.class+" Inside totalWtValidation Exception..", e);
			return new RestResponse("FAILURE", "-1");
		}
	}
	
	@RequestMapping(value = "/getSampleNo", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody RestResponse getSampleNo(@RequestParam String heat_id,@RequestParam Integer heat_counter,@RequestParam Integer sub_unit_id,@RequestParam Integer analysis_type) {
		logger.info("...getSampleNo");
		try{
			//
		 String sample_no=heatProcessEventService.getSampleNo(heat_id,heat_counter,sub_unit_id,analysis_type);
		 if(sample_no.isEmpty()==false)
		 {
		 return new RestResponse("SUCCESS",sample_no);
		//	 return sample_no;
		 }else{
			 return new RestResponse("SUCCESS","0");
			// return  "";
		 }
		}catch(Exception e)
		{
			e.printStackTrace();
			logger.error(HeatProcessEventController.class+" Inside getSampleNo Exception..", e);
			//return new RestResponse("FAILURE", "-1");
			return new RestResponse("FAILURE", "-1");
		}
	}
	
	@RequestMapping(value = "/getSampleDtlsByAnalysisType", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<HeatChemistryHdrDetails> getSampleDtlsByAnalysisType(@RequestParam Integer sub_unit_id, @RequestParam Integer heat_counter, 
			@RequestParam String heat_id, @RequestParam String analysis_type) {
		logger.info(HeatProcessEventController.class+"...getSampleDtlsByAnalysisType()");
		return heatProcessEventService.getSampleDtlsByAnalysisType(sub_unit_id, heat_id, heat_counter, analysis_type);

	}
	
	@RequestMapping(value = "/getLrfHeatProcessEventDtls", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<HeatProcessEventDetails> getLrfHeatProcessEventDtls(@RequestParam String heat_id, @RequestParam Integer heat_counter, 
			@RequestParam Integer sub_unit_id, @RequestParam String prev_unit, @RequestParam Integer psn_hdr_id) {
		logger.info(HeatProcessEventController.class+"...getLrfHeatProcessEventDtls()");
		List<PSNProductMasterModel> psnProdLi = psnProdMstrService.getPSNProductMstrDtls(psn_hdr_id);
		
		return heatProcessEventService.getLrfHeatProcessEventDtls(heat_id, heat_counter, sub_unit_id, prev_unit, psnProdLi.get(0).getLkpProcRoutMstrMdl().getLookup_value());
	}
	
	@RequestMapping(value = "/getProdConf", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody RestResponse getProdConfDtls(@RequestParam String heat_id) {
		logger.info(HeatProcessEventController.class+"...getProdConfDtls()");
		String result="";
		int resultValue=heatProcessEventService.checkProdConf(heat_id);
		if(resultValue>0) {
			return new RestResponse("SUCCESS","0");
		}
		else {
		return new RestResponse("FAILURE", "-1");
		}
	}
	
}
