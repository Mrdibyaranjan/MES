package com.smes.trans.service.impl;

import java.util.List;

import com.smes.masters.model.SteelLadleMasterModel;
import com.smes.trans.model.HeatStatusTrackingModel;
import com.smes.trans.model.StLadleLifeHeatWiseModel;
import com.smes.trans.model.StLadleMaintStatusModel;
import com.smes.trans.model.StLadlePartsMaintLogModel;
import com.smes.trans.model.StLadleStatusTrackHistoryModel;
import com.smes.trans.model.SteelLadleLifeModel;
import com.smes.trans.model.SteelLadleMaintenanceModel;
import com.smes.trans.model.SteelLadleTrackingModel;

public interface SteelLadleMaintnService {
	SteelLadleMasterModel getChangeStatusByStLadle(Integer st_ladle_no_id);

	String steelLadleStatusSave(SteelLadleMasterModel steelLadleDtls);

	SteelLadleTrackingModel getSteelLadleTracking(Integer ladle_id);
	
	String saveSteelLadleMaint(SteelLadleMaintenanceModel stlLdlMaintModel, SteelLadleMaintenanceModel prevStlLdlMaintModel,
			SteelLadleLifeModel stLdlLifeObj, SteelLadleTrackingModel stLdlTrackingObj);
	
	HeatStatusTrackingModel getHeatDtlsByStLadle(Integer ladle_id);
	
	SteelLadleMaintenanceModel getPrevSteelLadleMaintByPart(Integer ladle_id, Integer part_id);
	
	List<SteelLadleMaintenanceModel> getStLdlMaintAfterPartChange(Integer ladle_id);
	
	public String steelLadleStatusUpdate(SteelLadleTrackingModel steelLadleDtls);
	
	List<SteelLadleMaintenanceModel> getStLdlLifeDtlsForStatusChange(Integer ladle_id);

	String saveSteelLadleMaintSts(StLadleMaintStatusModel prevStLdlMaintSts, StLadleMaintStatusModel stlLdlMaintStsModel,
			StLadlePartsMaintLogModel stlLdlPartMaintLogModel);
	
	StLadleMaintStatusModel getPrevStladleMaintSts(Integer ladle_id, Integer part_id);
	
	List<StLadlePartsMaintLogModel> getStLdlPartsDtls(Integer StLadleId);

	String saveStLadleMaintainance(Integer UserId, StLadleMaintStatusModel stlLdlMaintStsModel, String updateFlg);
	
	String backupSteelLadlePartLife(List<StLadleLifeHeatWiseModel> stlLdlHeatPartLifeLi, List<SteelLadleLifeModel> stLdlLifeLi);

	StLadleMaintStatusModel getSteelLadleMaintStatus(Integer stLdlId);
	
	StLadlePartsMaintLogModel getStlLadleMaintPartsLog(Integer stLdlMaintStsId, Integer partId);

	StLadleStatusTrackHistoryModel getStlLadleStsTrackHist(Integer st_ladle_track_id);
	
	List<SteelLadleLifeModel> getSteelLadlePartsByLadleId(Integer stLdlId);
}
