package com.smes.trans.dao.impl;


import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import com.smes.reports.model.LRFHeatLogRpt;
import com.smes.trans.model.HeatChemistryHdrDetails;
import com.smes.trans.model.HeatPlanDetails;
import com.smes.trans.model.HeatPlanHdrDetails;
import com.smes.trans.model.HeatStatusTrackingModel;
import com.smes.trans.model.IfacesmsLpDetailsModel;
import com.smes.trans.model.LRFHeatArcingDetailsModel;
import com.smes.trans.model.LRFHeatConsumableLogModel;
import com.smes.trans.model.LRFHeatConsumableModel;
import com.smes.trans.model.LRFHeatDetailsModel;
import com.smes.trans.model.LRFHeatDetailsPsnBkpModel;
import com.smes.trans.model.ReladleProcessDetailsModel;
import com.smes.trans.model.ReladleProcessHdrModel;
import com.smes.trans.model.ReladleTrnsDetailsMdl;
import com.smes.trans.model.StLadleLifeHeatWiseModel;
import com.smes.trans.model.StLdlLifeAtHeat;
import com.smes.trans.model.SteelLadleLifeModel;
import com.smes.trans.model.SteelLadleTrackingModel;


public interface LRFProductionDao {

	String saveAll(Hashtable<String, Object> mod_obj);

	HeatStatusTrackingModel getHeatStatusObject(Integer integer);

	LRFHeatDetailsModel getLRFHeatDtlsFormByID(Integer trns_sl_no);

	List<LRFHeatConsumableModel> getArcAdditions(String lookup_type,Integer subUnitId);

	List<LRFHeatConsumableModel> getArcAdditionsBySampleNo(Integer arc_sl_no,
			String heat_id, Integer heat_cnt);

	String getNextArcNo(String heat_id, Integer heat_counter);

	LRFHeatConsumableModel getLRFHeatConsumablesById(Integer cons_sl_no);

	String lrfArcAdditionsSaveOrUpdate(Hashtable<String, Object> mod_obj);

	LRFHeatArcingDetailsModel getArcDetailsBySlno(Integer arc_sl_no);


	List<LRFHeatArcingDetailsModel> getArcDetailsByHeatId(String heat_id,
			Integer heat_cnt);

	Map<Integer, LRFHeatConsumableModel> getArcAdditionDetailsByArcSiHeat(
			String heat_id, Integer heat_cnt, Integer arc_sl_no);
	
	List<Map<String, Object>> getLRFArcAdditionsTemp(String finalSqlQry);

	LRFHeatDetailsModel getLRFHeatObject(Integer trns_sl_no);

	List<LRFHeatDetailsModel> getHeatsForVDProcess(String cunit,
			String pstatus);

	List<LRFHeatDetailsModel> getLrfChemDetails(String heat_id,
			Integer heat_counter);

	String updateLRFHeatDetails(Hashtable<String, Object> mod_obj);

	List<LRFHeatDetailsModel> getHeatsForCasterProcess(String cunit,
			String pstatus);
	public LRFHeatDetailsModel getLrfPreviousHeatDetailsByHeatNo( Integer trans_si_no);
	public LRFHeatDetailsModel getLrfHeatDetailsById( Integer trans_si_no);
	
	public LRFHeatDetailsModel getLRFHeatDetailsByHeatNo(String heatNo,Integer heat_counter);
	String updateLRFHeatDetPSN(LRFHeatDetailsModel lrfHeatObj, LRFHeatDetailsPsnBkpModel lrfHeatPsnBkp, List<HeatPlanHdrDetails> heatPlanHdrLi, List<HeatPlanDetails> heatPlanDetLi, HeatStatusTrackingModel heatTrackObj,IfacesmsLpDetailsModel ifaceObj);
	List<LRFHeatDetailsModel> getHeatsForLadleMix(String cunit, String pstatus);
	String updateLRFHeatDetForLadleMix(LRFHeatDetailsModel lrfHeatObj1, LRFHeatDetailsModel lrfHeatObj2, HeatStatusTrackingModel heatTrackObj1, HeatStatusTrackingModel heatTrackObj2);
	List<LRFHeatLogRpt> getLrfHeatLogs(String heatId, String LogType);
	public String updatelrfHeatDetails(LRFHeatDetailsModel lrfHeatDetails);
	public List<ReladleTrnsDetailsMdl> getUnProcessedReturnHeatDtls(Integer trns_sl_no);
	public List<ReladleProcessDetailsModel> getReladleDetails(Integer reladle_heat_id);
	public ReladleTrnsDetailsMdl getReturnHeatDetailsById(Integer trns_sl_no);
	String saveReladleDetails(List<ReladleProcessHdrModel> reladleHdrList, List<ReladleTrnsDetailsMdl> reladleHeatDtlLi, List<HeatStatusTrackingModel> heatTrackLi, 
		List<SteelLadleTrackingModel> steelLadleTrackLi, List<SteelLadleLifeModel> stLadlePartsLi, List<StLadleLifeHeatWiseModel> heatwiseStLdlPartsLi, StLdlLifeAtHeat heatwiseStLdlObj);
	String saveDispatchToLrfDtls(LRFHeatDetailsModel lrfHeatObj, LRFHeatDetailsModel newLrfHeatObj, HeatStatusTrackingModel heatTrackObj, ReladleTrnsDetailsMdl reladleTrnsObjj);
	public List<LRFHeatConsumableModel> getLrfAdditionsByHeatNo(String heat_no, String mtrl_type);
	List<LRFHeatConsumableLogModel> getMtrlConsLogByHeatNo(String heatNo);
	String saveOrUpdateMatrlCons(List<LRFHeatConsumableModel> updLi, List<LRFHeatConsumableLogModel> logLi);
	HeatStatusTrackingModel getHeatTrackingObj(Integer heatTrackingId);

	HeatChemistryHdrDetails getHeatDetailsByHeatNo(String heatNo, String logType);
}
