package com.smes.trans.controller;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.smes.masters.model.PsnGradeMasterModel;
import com.smes.masters.model.SubUnitMasterModel;
import com.smes.masters.service.impl.PsnGradeMasterService;
import com.smes.masters.service.impl.SubUnitMasterService;
import com.smes.reports.service.impl.HeatTrackingReportService;
import com.smes.trans.dao.impl.CasterProductionDao;
import com.smes.trans.model.CCMBatchDetailsModel;
import com.smes.trans.model.CCMHeatDetailsModel;
import com.smes.trans.model.CCMProductDetailsModel;
import com.smes.trans.model.EofHeatDetails;
import com.smes.trans.model.HeatStatusTrackingModel;
import com.smes.trans.model.IntfMaterialConsumptionModel;
import com.smes.trans.model.LRFHeatDetailsModel;
import com.smes.trans.model.VDHeatDetailsModel;
import com.smes.trans.service.impl.CCMBatchDetailsService;
import com.smes.trans.service.impl.CCMProductDetailsService;
import com.smes.trans.service.impl.EofProductionService;
import com.smes.trans.service.impl.LRFProductionService;
import com.smes.trans.service.impl.MaterialConsumptionService;
import com.smes.trans.service.impl.VDProductionService;
import com.smes.util.CommonCombo;
import com.smes.util.Constants;
import com.smes.util.MaterialConsumptionDTO;
import com.smes.util.RestResponse;

@Controller
@RequestMapping("mtrlConsumption")
public class MaterialConsumptionController {
	private static final Logger logger = Logger.getLogger(MaterialConsumptionController.class);
	
	@Autowired
	MaterialConsumptionService mtrlConsServ;
	
	@Autowired
	EofProductionService eofProdServ;
	
	@Autowired
	LRFProductionService lrfProdServ;
	
	@Autowired
	PsnGradeMasterService psnGradeServ;
	
	@Autowired
	SubUnitMasterService subUnitServ;
	
	@Autowired
	VDProductionService vdProdServ;
	
	@Autowired
	CasterProductionDao ccmProdServ;
	
	@Autowired
	CCMProductDetailsService ccmProdDetServ;
	
	@Autowired
	CCMBatchDetailsService ccmBatchServ;
	
	@Autowired
	HeatTrackingReportService HeatTrackingReportservice;
	
	@RequestMapping("/materialConsView")
	public ModelAndView getMaterialConsView() {
		logger.info(MaterialConsumptionController.class + "...getMaterialConsView()");
		ModelAndView model = new ModelAndView("transaction/MaterialConsumption");
		return model;
	}
	
	@RequestMapping("/mtrlConsPostingView")
	public ModelAndView getMaterialConsPostingView() {
		logger.info(MaterialConsumptionController.class + "...getMaterialConsPostingView()");
		ModelAndView model = new ModelAndView("transaction/MaterialConsumptionPosting");
		return model;
	}
	
	/*@RequestMapping(value = "/getProductionUnits", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<CommonCombo> getProductionUnits() {
		logger.info(MaterialConsumptionController.class + " getProductionUnits ");
		List<SubUnitMasterModel> subUnitLi = subUnitServ.getSubUnitMasterDetails("1");
		List<CommonCombo> retLi = new ArrayList<CommonCombo>();
		CommonCombo commonObj; //Integer id = 100;
		List<SubUnitMasterModel> subUnitActiveLi = subUnitLi.stream().filter(p -> p.getRecord_status().equals(1)).collect(Collectors.toList());
		subUnitActiveLi.sort((SubUnitMasterModel s1, SubUnitMasterModel s2) -> s1.getSub_unit_id().compareTo(s2.getSub_unit_id()));
		List<Integer> sI= new ArrayList<Integer>();
		sI = null;  
		for(SubUnitMasterModel obj : subUnitActiveLi) {
			commonObj = new CommonCombo();
			commonObj.setId(obj.getSub_unit_id());
			commonObj.setKeyval(obj.getSub_unit_id().toString());
			commonObj.setTxtvalue(obj.getSub_unit_name());
			retLi.add(commonObj);
		}
		commonObj = new CommonCombo();
		commonObj.setId(id);
		commonObj.setKeyval(id.toString());
		commonObj.setTxtvalue(Constants.MTRL_CONS_DAY);
		retLi.add(commonObj);
		
		return retLi;
	}*/
	
	@RequestMapping(value = "/getHeatProductionUnits", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<CommonCombo> getProductionUnits(Integer heat_track_id) {
		logger.info(MaterialConsumptionController.class + " getProductionUnits ");
		List<SubUnitMasterModel> subUnitLi = subUnitServ.getSubUnitMasterDetails("1");
		List<CommonCombo> retLi = new ArrayList<CommonCombo>();
		CommonCombo commonObj; //Integer id = 100;
		List<SubUnitMasterModel> subUnitActiveLi = subUnitLi.stream().filter(p -> p.getRecord_status().equals(1)).collect(Collectors.toList());
		subUnitActiveLi.sort((SubUnitMasterModel s1, SubUnitMasterModel s2) -> s1.getSub_unit_id().compareTo(s2.getSub_unit_id()));
		List<Integer> sI= new ArrayList<Integer>();
		sI = null;  
		HeatStatusTrackingModel heatTrackObj= lrfProdServ.getHeatTrackingObj(heat_track_id);
		String act_path=heatTrackObj.getAct_proc_path();
		List<Object> heatSubUnitsList=lrfProdServ.getActualPathUnits(act_path);
	    for(SubUnitMasterModel obj : subUnitActiveLi) {
	    	Integer sizeI = heatSubUnitsList.stream().filter(p -> p.toString().contains(obj.getSub_unit_name())).collect(Collectors.toList()).size();
	    	if(sizeI>0)
	    	{
	    		commonObj = new CommonCombo();
				commonObj.setId(obj.getSub_unit_id());
				commonObj.setKeyval(obj.getSub_unit_id().toString());
				commonObj.setTxtvalue(obj.getSub_unit_name());
				retLi.add(commonObj);
	    	}
	    }
		return retLi;
	}
	
	@RequestMapping(value = "/getProdPostedHeats", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<HeatStatusTrackingModel> getProdPostedHeats(String prodDate) {
		logger.info(MaterialConsumptionController.class + " getProdPostedHeats " + prodDate);
		return mtrlConsServ.getProdPostedHeats(prodDate);
	}
	
	@RequestMapping(value = "/getProdUnitwiseMtrlCons", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<List<MaterialConsumptionDTO>> getProdUnitwiseMtrlCons(String heatNo, String subUnitName, Integer subUnitId, 
			Integer heatTrackId, Double heatQty, Integer totalHeats, String prodDate) {
		logger.info(MaterialConsumptionController.class + " getProdUnitwiseMtrlCons " + heatNo);
		return mtrlConsServ.getProdUnitwiseMtrlCons(heatNo, subUnitName, subUnitId, heatTrackId, heatQty, totalHeats, prodDate);
	}
	
	@RequestMapping(value = "/getHeatwiseMtrlCons", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<IntfMaterialConsumptionModel> getHeatwiseMtrlCons(String heatNo, Integer heatTrackId, String prodDate) {
		logger.info(MaterialConsumptionController.class + " getHeatwiseMtrlCons " + heatNo);
		return mtrlConsServ.getHeatwiseMtrlCons(heatNo, heatTrackId, prodDate);
	}
	
	@RequestMapping(value = "/getHeatQuantity", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody MaterialConsumptionDTO getHeatQuantity(String heatNo, Integer heatTrackId) {
        logger.info(MaterialConsumptionController.class + "...getHeatQuantity-heatTrackId " + heatTrackId);
        MaterialConsumptionDTO obj = null;
        try {
        	Double totalBatchQty = 0.0;
        	DecimalFormat df = new DecimalFormat("#.###");
            df.setRoundingMode(RoundingMode.CEILING);
        	obj = new MaterialConsumptionDTO();
        	HeatStatusTrackingModel heatTrackObj = lrfProdServ.getHeatStatusObject(heatTrackId);
            
        	CCMHeatDetailsModel ccmHeatObj = ccmProdServ.getHeatDtlsByHeatId(heatNo, heatTrackObj.getHeat_counter());
        	List<CCMProductDetailsModel>  ccmProdLi = ccmHeatObj.getCcmProdHeatDtls();
        	for(CCMProductDetailsModel ccmProdObj : ccmProdLi) {
    			List<CCMBatchDetailsModel> ccmBatchLi = ccmProdObj.getCcmBatchDtls();
    			totalBatchQty = totalBatchQty + ccmBatchLi.stream().mapToDouble(o -> o.getAct_batch_wgt()).sum();
    		}
        	obj.setHeatQty(Double.parseDouble(df.format(totalBatchQty)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }
	
	/*@RequestMapping(value = "/getProdUnitwiseMtrlCons", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<List<MaterialConsumptionDTO>> getProdUnitwiseMtrlCons(@RequestBody MaterialConsumptionDTO mtrlConsDtoObj, String prodDate) {
		logger.info(MaterialConsumptionController.class + " getProdUnitwiseMtrlCons " + prodDate);
		return mtrlConsServ.getProdUnitwiseMtrlCons(mtrlConsDtoObj, prodDate);
	}*/
	@RequestMapping(value = "/getHeatDetails", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody MaterialConsumptionDTO getHeatDetails(String heatNo, String subUnitName, Integer heatTrackId, Integer subUnitId) {
        logger.info(MaterialConsumptionController.class + "...getHeatDetails-heatTrackId " + heatTrackId);
        MaterialConsumptionDTO obj = null;
        String aimPSN = null;
        Integer aimPsnHdrId = null;
        try {
        	obj = new MaterialConsumptionDTO();
        	HeatStatusTrackingModel heatTrackObj = lrfProdServ.getHeatStatusObject(heatTrackId);
        	obj.setActualPath(heatTrackObj.getAct_proc_path());
        	Double totalBatchQty = 0.0;
        	DecimalFormat df = new DecimalFormat("#.###");
            df.setRoundingMode(RoundingMode.CEILING);

        	CCMHeatDetailsModel ccmHeatObj = ccmProdServ.getHeatDtlsByHeatId(heatNo, heatTrackObj.getHeat_counter());
        	List<CCMProductDetailsModel>  ccmProdLi = ccmHeatObj.getCcmProdHeatDtls();
        	for(CCMProductDetailsModel ccmProdObj : ccmProdLi) {
    			List<CCMBatchDetailsModel> ccmBatchLi = ccmProdObj.getCcmBatchDtls();
    			totalBatchQty = totalBatchQty + ccmBatchLi.stream().mapToDouble(o -> o.getAct_batch_wgt()).sum();
    		}
        	if(subUnitName.substring(0, 3).equals(Constants.SUB_UNIT_EOF)){
        		EofHeatDetails eofHeatObj = eofProdServ.getEOFHeatDetailsByHeatNo(heatNo);
        		aimPSN = eofHeatObj.getPsnHdrMstrMdl().getPsn_no();
        		aimPsnHdrId = eofHeatObj.getAim_psn();
        	}else if(subUnitName.substring(0, 3).equals(Constants.SUB_UNIT_LRF)) {
        		List<LRFHeatDetailsModel> lrfHeatObjLi =  mtrlConsServ.getLrfHeatDetailObj(heatNo, subUnitId);
        		aimPSN = lrfHeatObjLi.get(0) .getPsnHdrMstrMdl().getPsn_no();
        		aimPsnHdrId = lrfHeatObjLi.get(0).getAim_psn();
        	}else if(subUnitName.substring(0, 2).equals(Constants.SUB_UNIT_VD)) {
        		VDHeatDetailsModel vdHeatObj = vdProdServ.getVDHeatDetailsByHeatNo(heatNo);
        		aimPSN = vdHeatObj.getPsnHdrMstrMdl().getPsn_no();
        		aimPsnHdrId = vdHeatObj.getAim_psn();
        	}else if(subUnitName.substring(0, 3).equals(Constants.SUB_UNIT_CCM)) {
        		aimPSN = ccmHeatObj.getPsnHdrMstrMdl().getPsn_no();
        		aimPsnHdrId = ccmHeatObj.getPsn_no();	
        	}
        	List<PsnGradeMasterModel> psnGradeLi = psnGradeServ.getPSNGradeMstrDtls(aimPsnHdrId);
        	obj.setAimPSN(aimPSN);
        	if(psnGradeLi.size() > 0)
        		obj.setGrade(psnGradeLi.get(0).getPsn_grade());
        	obj.setHeatQty(Double.parseDouble(df.format(totalBatchQty)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }
	
	@RequestMapping(value = "/saveOrUpdateMtrlConsumptions", method = RequestMethod.POST, headers = { "Content-type=application/json" })
	public @ResponseBody RestResponse saveOrUpdateMtrlConsumptions(@RequestBody MaterialConsumptionDTO mtrlConsDtoObj, String consDate, HttpSession session) {
		String result = "";
		try {
			//List<MaterialConsumptionDTO> mtrlConsDtoLi = mtrlConsDtoObj.getMtrlConsDtoLi();
			Integer userId = Integer.parseInt(session.getAttribute("USER_APP_ID").toString());
			result = mtrlConsServ.saveOrUpdateMtrlConsumptions(mtrlConsDtoObj, consDate, userId);
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(CasterProductionController.class
					+ " Inside saveOrUpdateMtrlConsumptions Exception..", e);
		}
		if(result.equals(Constants.SAVE) || result.equals(Constants.UPDATE)){
			return new RestResponse("SUCCESS", result);
		}else{
			return new RestResponse("FAILURE", result);
		}
	}
	
	@RequestMapping(value = "/postMtrlConsumptions", method = RequestMethod.POST, headers = { "Content-type=application/json" })
	public @ResponseBody RestResponse postMtrlConsumptions(@RequestBody IntfMaterialConsumptionModel intfMtrlConsObj, String consDate,  HttpSession session) {
		String result = "";
		try {
			Integer userId = Integer.parseInt(session.getAttribute("USER_APP_ID").toString());
			result = mtrlConsServ.postMtrlConsumptions(intfMtrlConsObj, userId);
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(MaterialConsumptionController.class
					+ " Inside postMtrlConsumptions Exception..", e);
		}
		if(result.equals(Constants.SAVE) || result.equals(Constants.UPDATE)){
			return new RestResponse("SUCCESS", result);
		}else{
			return new RestResponse("FAILURE", result);
		}
	}

}