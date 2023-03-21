package com.smes.trans.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smes.masters.model.SteelLadleMaintnModel;
import com.smes.masters.model.SteelLadleMasterModel;
import com.smes.masters.service.impl.SteelLadleStatusMasterService;
import com.smes.trans.dao.impl.SteelLadleMaintnDao;
import com.smes.trans.model.HeatStatusTrackingModel;
import com.smes.trans.model.StLadleLifeHeatWiseModel;
import com.smes.trans.model.StLadleMaintStatusModel;
import com.smes.trans.model.StLadlePartsMaintLogModel;
import com.smes.trans.model.StLadleStatusTrackHistoryModel;
import com.smes.trans.model.StLdlHeatingDtls;
import com.smes.trans.model.SteelLadleLifeModel;
import com.smes.trans.model.SteelLadleMaintenanceModel;
import com.smes.trans.model.SteelLadleTrackingModel;
import com.smes.util.Constants;
import com.smes.util.GenericClass;

@Service("SteelLadleMaintnService")
public class SteelLadleMaintnServiceImpl implements
		SteelLadleMaintnService {
	
	@Autowired
	SteelLadleStatusMasterService steelLdlStsMstrService;
	
	@Autowired
	SteelLadleMaintnDao steelLadleMaintnDao;
	
	@Override
	public SteelLadleMasterModel getChangeStatusByStLadle(Integer st_ladle_no_id) {
		// TODO Auto-generated method stub
		return steelLadleMaintnDao.getChangeStatusByStLadle(st_ladle_no_id);
	}

	
	public String stLadleSaveAndInsertHist(Hashtable<String, Object> mod_obj) {
		// TODO Auto-generated method stub
		return steelLadleMaintnDao.stLadleSaveAndInsertHist(mod_obj);
	}
	
    public String steelLadleStatusUpdate(SteelLadleTrackingModel steelLadleDtls) {
		
		String result="";
		result=steelLadleMaintnDao.updateSteelLadle(steelLadleDtls);
		return result;
	}
	
	@Override
	public String steelLadleStatusSave(SteelLadleMasterModel steelLadleDtls) {
		// TODO Auto-generated method stub

		// TODO Auto-generated method stub

		// TODO Auto-generated method stub
		Hashtable<String, Object> mod_obj = null;
		String result=null;
		mod_obj=new Hashtable<String, Object>();
		//mastrLadleObj=steelLadleStatusMasterDao.getStLadlleTrackObjById(Steel_ladle_id)(steelLadleDtls.getSteel_ladle_si_no());
		//condlkId=commonService.getLookupIdByQuery("select al.lookup_id from LookupMasterModel al where  al.lookup_type = 'STEEL_LADLE_CONDITION' and al.lookup_code = 'IN_REPAIR'");

		//mastrLadleObj.setSteel_ladle_condition(steelLadleDtls.getSteel_ladle_condition());
		//mastrLadleObj.setSteel_ladle_status(steelLadleDtls.getSteel_ladle_status());
		//mastrLadleObj.setUpdatedBy(steelLadleDtls.getUpdatedBy());
		//mastrLadleObj.setUpdatedDateTime(new Date());
		////mastrLadleObj.setRecordStatus(1);
		//mod_obj.put("UPDATE_ST_LADLE_MSTR", mastrLadleObj);
		/*if(steelLadleDtls.getSteel_ladle_status().equalsIgnoreCase("NOT_AVAILABLE")){
			mastrLadleObj=steelLadleStatusMasterDao.getSteelLadleDtlsById(steelLadleDtls.getSteel_ladle_si_no());		
			condlkId=commonService.getLookupIdByQuery("select al.lookup_id from LookupMasterModel al where  al.lookup_type = 'STEEL_LADLE_CONDITION' and al.lookup_code = 'IN_REPAIR'");
			mastrLadleObj.setSteel_ladle_condition(condlkId);
			mastrLadleObj.setSteel_ladle_status("NOT_AVAILABLE");
			mastrLadleObj.setUpdatedBy(steelLadleDtls.getUpdatedBy());
			mastrLadleObj.setUpdatedDateTime(new Date());
			mastrLadleObj.setRecordStatus(1);
		}else{
			mastrLadleObj=steelLadleStatusMasterDao.getSteelLadleDtlsById(steelLadleDtls.getSteel_ladle_si_no());
			//condlkId=commonService.getLookupIdByQuery("select al.lookup_id from LookupMasterModel al where  al.lookup_type = 'STEEL_LADLE_CONDITION' and al.lookup_code = 'CIRCULATION'");
			
			mastrLadleObj.setSteel_ladle_condition(steelLadleDtls.getSteel_ladle_condition());
			mastrLadleObj.setSteel_ladle_status("AVAILABLE");
			mastrLadleObj.setUpdatedBy(steelLadleDtls.getUpdatedBy());
			mastrLadleObj.setUpdatedDateTime(new Date());
			mastrLadleObj.setRecordStatus(1);
		}*/
		
		
	
		
		SteelLadleMaintnModel ladlehistObj=null;
	/*	if(steelLadleDtls.getSteel_ladle_status().equalsIgnoreCase("NOT_AVAILABLE")){
		 ladlehistObj=new SteelLadleMaintnModel();
		ladlehistObj.setSt_ladle_sl_no(null);
		ladlehistObj.setSteel_ladle_no(mastrLadleObj.getSteel_ladle_si_no());
		ladlehistObj.setFor_repair_date(steelLadleDtls.getChanged_date());
		ladlehistObj.setRecordStatus(1);
		ladlehistObj.setCreatedBy(steelLadleDtls.getUpdatedBy());
		ladlehistObj.setCreatedDateTime(new Date());
		mod_obj.put("INSERT_ST_LADLE_HIST", ladlehistObj);
		}else{
			
			ladlehistObj=steelLadleMaintnDao.getMaxLadle_sl_no(mastrLadleObj.getSteel_ladle_si_no());
			ladlehistObj.setIn_circulation_date(steelLadleDtls.getChanged_date());
			ladlehistObj.setUpdatedBy(steelLadleDtls.getUpdatedBy());
			ladlehistObj.setUpdatedDateTime(new Date());
			mod_obj.put("UPDATE_ST_LADLE_HIST", ladlehistObj);
		}
		*/
		
		if(ladlehistObj!=null){
			result= stLadleSaveAndInsertHist(mod_obj);
		}
		return result;	
	}

	@Override
	public SteelLadleTrackingModel getSteelLadleTracking(Integer ladle_id) {
		// TODO Auto-generated method stub
		return steelLadleMaintnDao.getSteelLadleTracking(ladle_id) ;
	}

	@Override
	public String saveSteelLadleMaint(
			SteelLadleMaintenanceModel stlLdlMaintModel, SteelLadleMaintenanceModel prevStlLdlMaintModel,
			SteelLadleLifeModel stLdlLifeObj, SteelLadleTrackingModel stLdlTrackingObj) {
		// TODO Auto-generated method stub
		return steelLadleMaintnDao.saveSteelLadleMaint(stlLdlMaintModel, prevStlLdlMaintModel, stLdlLifeObj, stLdlTrackingObj) ;
	}

	@Override
	public HeatStatusTrackingModel getHeatDtlsByStLadle(Integer ladle_id) {
		// TODO Auto-generated method stub
		return steelLadleMaintnDao.getHeatDtlsByStLadle(ladle_id);
	}


	@Override
	public SteelLadleMaintenanceModel getPrevSteelLadleMaintByPart(Integer ladle_id,
			Integer part_id) {
		// TODO Auto-generated method stub
		return steelLadleMaintnDao.getPrevSteelLadleMaintByPart(ladle_id, part_id) ;
	}


	@Override
	public List<SteelLadleMaintenanceModel> getStLdlMaintAfterPartChange(
			Integer ladle_id) {
		// TODO Auto-generated method stub
		return steelLadleMaintnDao.getStLdlMaintAfterPartChange(ladle_id);
	}


	@Override
	public List<SteelLadleMaintenanceModel> getStLdlLifeDtlsForStatusChange(
			Integer ladle_id) {
		// TODO Auto-generated method stub
		return steelLadleMaintnDao.getStLdlLifeDtlsForStatusChange(ladle_id) ;
	}
	
	@Override
	public String saveSteelLadleMaintSts(StLadleMaintStatusModel prevStLdlMaintSts, StLadleMaintStatusModel stlLdlMaintStsModel,
			StLadlePartsMaintLogModel stlLdlPartMaintLogModel) {
		return steelLadleMaintnDao.saveSteelLadleMaintSts(prevStLdlMaintSts, stlLdlMaintStsModel, stlLdlPartMaintLogModel);
	}
	
	@Override
	public StLadleMaintStatusModel getPrevStladleMaintSts(Integer maint_sts_id, Integer ladle_id) {
		return steelLadleMaintnDao.getPrevStladleMaintSts(maint_sts_id, ladle_id);
	}
	
	@Override
	public List<StLadlePartsMaintLogModel> getStLdlPartsDtls(Integer StLadleId) {
		return steelLadleMaintnDao.getStLdlPartsDtls(StLadleId);
	}
	
	@Override
	public String saveStLadleMaintainance(Integer UserId, StLadleMaintStatusModel stlLdlMaintStsModel, String ldlStsUIFlg) {
		
		/* StLadleMaintStatusModel stlLdlMaintStsModel, SteelLadleTrackingModel stLdlTrackingObj, 
			List<StLadleLifeHeatWiseModel> stlLdlHeatPartLifeLi, List<SteelLadleLifeModel> stLdlLifeLi,
			StLadleStatusTrackHistoryModel stLdlTrackHist*/
		
		Hashtable<String, Object> modobj = new Hashtable<String, Object>();
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		String result = ""; Integer StLdlNo = 0; Integer maintStsId = 0; String HeatId = ""; Integer HeatCntr = 0;
		SteelLadleTrackingModel stLdlTrackingObj = null; Integer LdlStsId = 0;
		
		List<StLadleLifeHeatWiseModel> stlLdlLifeHeatLi = new ArrayList<StLadleLifeHeatWiseModel>();
		StLadleLifeHeatWiseModel stlLdlLifeHeatObj = new StLadleLifeHeatWiseModel();
		List<SteelLadleLifeModel> stLdlLifeLi = new ArrayList<SteelLadleLifeModel>();
		SteelLadleLifeModel stLdlLifeObj = new SteelLadleLifeModel();			
		//StLdlHeatingDtls heatingObj = new StLdlHeatingDtls();
		StLdlHeatingDtls ldlHeatingObj = null;
	 	
		StLdlNo = stlLdlMaintStsModel.getStladle_id();
		LdlStsId = stlLdlMaintStsModel.getStladle_status_id();
		if (stlLdlMaintStsModel.getStladle_maint_status_id() != null) {
			maintStsId = stlLdlMaintStsModel.getStladle_maint_status_id();
		}
		
		HeatStatusTrackingModel heatTrackObj = getHeatDtlsByStLadle(StLdlNo);
		if (heatTrackObj != null) {
			HeatId = heatTrackObj.getHeat_id();
			HeatCntr = heatTrackObj.getHeat_counter();
		}
		
		stLdlTrackingObj = getSteelLadleTracking(StLdlNo);
		stLdlTrackingObj.setLadle_status(LdlStsId);
		stLdlTrackingObj.setRecord_status(1);
		stLdlTrackingObj.setUpdatedBy(UserId);
		stLdlTrackingObj.setUpdatedDateTime(new Date());
		
		if(ldlStsUIFlg.equals("I_DOWN")) {
			///////////////////////////////////////////////Update Ldl Status in maintSts & ldlTrack, Update n Bkp Parts Dtls if any, Update n Bkp Heating Info
			List<StLadlePartsMaintLogModel> ldlPartList = new ArrayList<StLadlePartsMaintLogModel>();			 	
		 	ldlPartList = getStLdlPartsDtls(StLdlNo);
		 	
			if (ldlPartList != null && ldlPartList.size() > 0) {
				for (StLadlePartsMaintLogModel partObj : ldlPartList){
					
					stlLdlLifeHeatObj = new StLadleLifeHeatWiseModel();
					
					try {
						stLdlLifeObj = steelLdlStsMstrService.getLadleLifeByParts(StLdlNo, partObj.getPartsMstrModel().getPart_name(), Constants.EQUIPMENT_STEEL_LADLE);
					} catch (Exception e) {
						stLdlLifeObj = null;
						e.printStackTrace();
					}
					
					stlLdlLifeHeatObj.setHeat_id(HeatId);
					stlLdlLifeHeatObj.setHeat_counter(HeatCntr);
					stlLdlLifeHeatObj.setSteel_ladle_no(StLdlNo);
					stlLdlLifeHeatObj.setEquipment_id(stLdlLifeObj.getEquipment_id());
					stlLdlLifeHeatObj.setPart_id(partObj.getPart_id());
					stlLdlLifeHeatObj.setTrns_life(stLdlLifeObj.getTrns_life());
					stlLdlLifeHeatObj.setRecord_status(stLdlLifeObj.getRecord_status());
					stlLdlLifeHeatObj.setCreated_by(stLdlLifeObj.getCreated_by());
					stlLdlLifeHeatObj.setCreated_date_time(stLdlLifeObj.getCreated_date_time());
					stlLdlLifeHeatObj.setUpdated_by(stLdlLifeObj.getUpdated_by());
					stlLdlLifeHeatObj.setUpdated_date_time(stLdlLifeObj.getUpdated_date_time());
					stlLdlLifeHeatObj.setRecord_version(stLdlLifeObj.getRecord_version());
					
					stlLdlLifeHeatObj.setLdl_down_date(null);
					stlLdlLifeHeatObj.setPart_chng_ldl_sts(null);
					stlLdlLifeHeatObj.setPart_change_date(null);
					stlLdlLifeHeatObj.setLdl_down_heating_date(null);
					stlLdlLifeHeatObj.setLdl_circ_reserved_date(null);
					stlLdlLifeHeatObj.setLdl_heat_change_date(null);
					
					stlLdlLifeHeatObj.setLdl_down_date(new Date());
										
					stlLdlLifeHeatLi.add(stlLdlLifeHeatObj);
					
				}
			}
			
			if (maintStsId != null) {
		 		StLadlePartsMaintLogModel heatPartIdObj = getStlLadleMaintPartsLog(maintStsId, null);
		 		ldlHeatingObj = new StLdlHeatingDtls();
		 		ldlHeatingObj.setHist_entry_time(new Date());
			 	ldlHeatingObj.setSteel_ladle_si_no(StLdlNo);
			 	ldlHeatingObj.setHeat_id(HeatId);
			 	ldlHeatingObj.setHeat_counter(HeatCntr);
			 	ldlHeatingObj.setHeating_start_dt(heatPartIdObj.getHeat_start_date());
			 	ldlHeatingObj.setHeating_end_dt(heatPartIdObj.getHeat_end_date());
			 	ldlHeatingObj.setBurner_no(heatPartIdObj.getBurner_no());
			 	ldlHeatingObj.setStladle_status(stLdlTrackingObj.getLadle_status());
			 	ldlHeatingObj.setCreated_by(heatPartIdObj.getCreated_by());
			 	ldlHeatingObj.setCreated_date_time(heatPartIdObj.getCreated_date_time());
			 	ldlHeatingObj.setUpdated_by(heatPartIdObj.getUpdated_by());
			 	ldlHeatingObj.setUpdated_date_time(heatPartIdObj.getUpdated_date_time());
		 	}
						
			stLdlTrackingObj.setSt_ladle_life(0);
			
			modobj.put("MAINT_STATUS", stlLdlMaintStsModel); //PartsMaintSts & PartsLog
			modobj.put("STLDL_TRACK", stLdlTrackingObj); //CurrentLdlStsAndLife
			modobj.put("STLDL_HEAT_PART_LIFE", stlLdlLifeHeatLi); //BkpAllPartsLife
			modobj.put("STLDL_PART_LIFE", null); //ResetAllPartsLife
			modobj.put("STLDL_HEATING_INFO", ldlHeatingObj); //BkpHeatingDtls
			
		} /*else if (ldlStsUIFlg.equals("U_DOWN")) {
			/////////////////////////////////////////////Update maintSts, Parts & Heating Info Update
		}*/ else if((ldlStsUIFlg.equals("AVAIL"))) {
			/////////////////////////////////////////////Update Status as Available
			
			modobj.put("MAINT_STATUS", stlLdlMaintStsModel); //PartsMaintSts --& PartsLog
			modobj.put("STLDL_TRACK", stLdlTrackingObj); //CurrentLdlStsAndLife
			modobj.put("STLDL_HEAT_PART_LIFE", null); //BkpAllPartsLife
			modobj.put("STLDL_PART_LIFE", null); //ResetAllPartsLife
			modobj.put("STLDL_HEATING_INFO", null); //HeatingHist
						
		} else if((ldlStsUIFlg.equals("CIRCN")) || ldlStsUIFlg.equals("U_DOWN")){
			/////////////////////////////////////////////I/U Ldl Status in maintSts & ldlTrack, Update n Bkp Parts Dtls, Update n Bkp Heating Info
						
			if (stlLdlMaintStsModel.getStLdlPartMaintLog() != null) {
				for (StLadlePartsMaintLogModel obj : stlLdlMaintStsModel.getStLdlPartMaintLog()){
				
					StLadlePartsMaintLogModel idObj = getStlLadleMaintPartsLog(stlLdlMaintStsModel.getStladle_maint_status_id(), obj.getPart_id());
				
					if (idObj != null) {
						if (idObj.getStladle_parts_maint_log_id() != null && idObj.getStladle_parts_maint_log_id() > 0) {
							obj.setStladle_parts_maint_log_id(idObj.getStladle_parts_maint_log_id());
							obj.setRecord_version(idObj.getRecord_version());
						}
					} else {
						obj.setCreated_date_time(new Date());
						obj.setCreated_by(UserId);
						obj.setRecord_version(0);
					}
				
					if (obj.getCdate() != null) {
						try {
							obj.setChange_date(GenericClass.getDateObject("dd/MM/yyyy HH:mm", obj.getCdate()));
						} catch(Exception e) {
							e.printStackTrace();
							Date dt = null;
							try {
								dt = (Date) formatter.parseObject(obj.getCdate() + "");
							} catch (ParseException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							obj.setChange_date(dt);
						}
					}
				
					obj.setUpdated_date_time(new Date());
					obj.setUpdated_by(UserId);
					obj.setRecord_status(1);
					obj.setStLdlMainStstModel(stlLdlMaintStsModel);
					
					try {
						stLdlLifeObj = steelLdlStsMstrService.getLadleLifeByParts(StLdlNo, obj.getPartsMstrModel().getPart_name(), Constants.EQUIPMENT_STEEL_LADLE);
					} catch (Exception e) {
						stLdlLifeObj = null;
						e.printStackTrace();
					}
					
					if(stLdlLifeObj != null) {							
						stlLdlLifeHeatObj.setHeat_id(HeatId);
						stlLdlLifeHeatObj.setHeat_counter(HeatCntr);
						stlLdlLifeHeatObj.setSteel_ladle_no(stLdlLifeObj.getSteel_ladle_no());
						stlLdlLifeHeatObj.setEquipment_id(stLdlLifeObj.getEquipment_id());
						stlLdlLifeHeatObj.setPart_id(stLdlLifeObj.getPart_id());
						stlLdlLifeHeatObj.setTrns_life(stLdlLifeObj.getTrns_life());
						stlLdlLifeHeatObj.setRecord_status(stLdlLifeObj.getRecord_status());
						stlLdlLifeHeatObj.setCreated_by(stLdlLifeObj.getCreated_by());
						stlLdlLifeHeatObj.setCreated_date_time(stLdlLifeObj.getCreated_date_time());
						stlLdlLifeHeatObj.setUpdated_by(stLdlLifeObj.getUpdated_by());
						stlLdlLifeHeatObj.setUpdated_date_time(stLdlLifeObj.getUpdated_date_time());
						stlLdlLifeHeatObj.setRecord_version(stLdlLifeObj.getRecord_version());
						/*New Inserts*/
						stlLdlLifeHeatObj.setPart_chng_ldl_sts(LdlStsId);
						stlLdlLifeHeatObj.setPart_change_date(new Date());
						stlLdlLifeHeatObj.setLdl_down_date(null);
						stlLdlLifeHeatObj.setLdl_down_heating_date(null);
						stlLdlLifeHeatObj.setLdl_circ_reserved_date(null);
						stlLdlLifeHeatObj.setLdl_heat_change_date(null);
						//stlLdlLifeHeatObj.setRecord_version(0);
						
						stlLdlLifeHeatLi.add(stlLdlLifeHeatObj);
						
						if (idObj != null) {
							if (idObj.getPart_supplier() != obj.getPart_supplier() || idObj.getChange_date() != obj.getChange_date()) {
								stLdlLifeObj.setTrns_life(0);
								stLdlLifeObj.setUpdated_by(UserId);
								stLdlLifeObj.setUpdated_date_time(new Date());
								
								stLdlLifeLi.add(stLdlLifeObj);
							}
						}						
					}
					
					if (maintStsId != null) {
				 		StLadlePartsMaintLogModel heatPartIdObj = getStlLadleMaintPartsLog(maintStsId, null);
				 		ldlHeatingObj = new StLdlHeatingDtls();
				 		ldlHeatingObj.setHist_entry_time(new Date());
					 	ldlHeatingObj.setSteel_ladle_si_no(StLdlNo);
					 	ldlHeatingObj.setHeat_id(HeatId);
					 	ldlHeatingObj.setHeat_counter(HeatCntr);
					 	ldlHeatingObj.setHeating_start_dt(heatPartIdObj.getHeat_start_date());
					 	ldlHeatingObj.setHeating_end_dt(heatPartIdObj.getHeat_end_date());
					 	ldlHeatingObj.setBurner_no(heatPartIdObj.getBurner_no());
					 	ldlHeatingObj.setStladle_status(stLdlTrackingObj.getLadle_status());
					 	ldlHeatingObj.setCreated_by(heatPartIdObj.getCreated_by());
					 	ldlHeatingObj.setCreated_date_time(heatPartIdObj.getCreated_date_time());
					 	ldlHeatingObj.setUpdated_by(heatPartIdObj.getUpdated_by());
					 	ldlHeatingObj.setUpdated_date_time(heatPartIdObj.getUpdated_date_time());
				 	}
				}
			}
			
			modobj.put("MAINT_STATUS", stlLdlMaintStsModel); //PartsMaintSts & PartsLog
			modobj.put("STLDL_TRACK", stLdlTrackingObj); //CurrentLdlStsAndLife
			modobj.put("STLDL_HEAT_PART_LIFE", stlLdlLifeHeatLi); //BkpAllPartsLife
			modobj.put("STLDL_PART_LIFE", stLdlLifeLi); //ResetAllPartsLife
			modobj.put("STLDL_HEATING_INFO", ldlHeatingObj); //HeatingHist
			
		} else {
			//Return Nothing
			modobj.put("MAINT_STATUS", null); //PartsMaintSts & PartsLog
			modobj.put("STLDL_TRACK", null); //CurrentLdlStsAndLife
			modobj.put("STLDL_HEAT_PART_LIFE", null); //BkpAllPartsLife
			modobj.put("STLDL_PART_LIFE", null); //ResetAllPartsLife
			modobj.put("STLDL_HEATING_INFO", null); //HeatingHist
		}
				
		return steelLadleMaintnDao.saveStLadleMaintainance(modobj);
	}
	
	@Override
	public String backupSteelLadlePartLife(List<StLadleLifeHeatWiseModel> stlLdlHeatPartLifeLi, List<SteelLadleLifeModel> stLdlLifeLi) {
		return steelLadleMaintnDao.backupSteelLadlePartLife(stlLdlHeatPartLifeLi, stLdlLifeLi);
	}
	
	@Override
	public StLadleMaintStatusModel getSteelLadleMaintStatus(Integer stLdlId) {
		return steelLadleMaintnDao.getSteelLadleMaintStatus(stLdlId);
	}
	
	@Override
	public StLadlePartsMaintLogModel getStlLadleMaintPartsLog(Integer stLdlMaintStsId, Integer partId) {
		return steelLadleMaintnDao.getStlLadleMaintPartsLog(stLdlMaintStsId, partId);
	}
	
	@Override
	public StLadleStatusTrackHistoryModel getStlLadleStsTrackHist(Integer st_ladle_track_id) {
		return steelLadleMaintnDao.getStlLadleStsTrackHist(st_ladle_track_id);
	}


	@Override
	public List<SteelLadleLifeModel> getSteelLadlePartsByLadleId(Integer stLdlId) {
		// TODO Auto-generated method stub
		return steelLadleMaintnDao.getSteelLadlePartsByLadleId(stLdlId) ;
	}
}
