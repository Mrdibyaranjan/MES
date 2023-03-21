package com.smes.trans.dao.impl;

import java.util.Hashtable;
import java.util.List;

import com.smes.masters.model.SteelLadleMaintnModel;
import com.smes.masters.model.SteelLadleMasterModel;
import com.smes.trans.model.HeatStatusTrackingModel;
import com.smes.trans.model.StLadleLifeHeatWiseModel;
import com.smes.trans.model.StLadleMaintStatusModel;
import com.smes.trans.model.StLadlePartsMaintLogModel;
import com.smes.trans.model.StLadleStatusTrackHistoryModel;
import com.smes.trans.model.SteelLadleLifeModel;
import com.smes.trans.model.SteelLadleMaintenanceModel;
import com.smes.trans.model.SteelLadleTrackingModel;

public interface SteelLadleMaintnDao {

	SteelLadleMasterModel getChangeStatusByStLadle(Integer st_ladle_no_id);

	String stLadleSaveAndInsertHist(Hashtable<String, Object> mod_obj);

	SteelLadleMaintnModel getMaxLadle_sl_no(Integer steel_ladle_si_no);

	SteelLadleTrackingModel getSteelLadleTracking(Integer ladle_id);
	
	String saveSteelLadleMaint(SteelLadleMaintenanceModel stlLdlMaintModel, SteelLadleMaintenanceModel prevStlLdlMaintModel,
			SteelLadleLifeModel stLdlLifeObj, SteelLadleTrackingModel stLdlTrackingObj);
	
	HeatStatusTrackingModel getHeatDtlsByStLadle(Integer ladle_id);
	
	SteelLadleMaintenanceModel getPrevSteelLadleMaintByPart(Integer ladle_id, Integer part_id);
	
	List<SteelLadleMaintenanceModel> getStLdlMaintAfterPartChange(Integer ladle_id);
	
	List<SteelLadleMaintenanceModel> getStLdlLifeDtlsForStatusChange(Integer ladle_id);
	
	public List<SteelLadleTrackingModel> getAvailableLadleStatus();
	
	public String  updateSteelLadle(SteelLadleTrackingModel model);
	
	public SteelLadleTrackingModel getAvailableLadleByLadleId(Integer ladleId);

	public String saveSteelLadleMaintSts(StLadleMaintStatusModel prevStLdlMaintSts, StLadleMaintStatusModel stlLdlMaintStsModel,
			StLadlePartsMaintLogModel stlLdlPartMaintLogModel);

	public StLadleMaintStatusModel getPrevStladleMaintSts(Integer ladle_id, Integer part_id);

	public List<StLadlePartsMaintLogModel> getStLdlPartsDtls(Integer StLadleId);

	public String saveStLadleMaintainance(Hashtable<String, Object> modobj);

	public String backupSteelLadlePartLife(List<StLadleLifeHeatWiseModel> stlLdlHeatPartLifeLi, List<SteelLadleLifeModel> stLdlLifeLi);

	public StLadleMaintStatusModel getSteelLadleMaintStatus(Integer stLdlId);

	public StLadlePartsMaintLogModel getStlLadleMaintPartsLog(Integer stLdlMaintStsId, Integer partId);

	public StLadleStatusTrackHistoryModel getStlLadleStsTrackHist(Integer st_ladle_track_id);
	
	List<SteelLadleLifeModel> getSteelLadlePartsByLadleId(Integer stLdlId);

}
