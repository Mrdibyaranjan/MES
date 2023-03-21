package com.smes.trans.service.impl;


import java.util.HashSet;
import java.util.List;
import java.util.Map;

import com.smes.reports.model.LRFHeatLogRpt;
import com.smes.trans.model.HeatChemistryChildDetails;
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
import com.smes.trans.model.LrfElectrodeTransactions;
import com.smes.trans.model.ReladleProcessDetailsModel;
import com.smes.trans.model.ReladleProcessHdrModel;
import com.smes.trans.model.ReladleTrnsDetailsMdl;
import com.smes.trans.model.StLadleLifeHeatWiseModel;
import com.smes.trans.model.StLdlLifeAtHeat;
import com.smes.trans.model.SteelLadleLifeModel;
import com.smes.trans.model.SteelLadleTrackingModel;
import com.smes.trans.model.TransDelayEntryHeader;
import com.smes.util.CommonCombo;
import com.smes.util.DelayEntryDTO;
import com.smes.util.LRFHeatConsumableDisplay;
import com.smes.wrappers.LRFRequestWrapper;

public interface LRFProductionService  {

	String saveAll(LRFRequestWrapper reqWrapper,Integer UserId);
    

	LRFHeatDetailsModel getLRFHeatDtlsFormByID(Integer trns_sl_no);


	List<LRFHeatConsumableModel> getArcAdditions(String lookup_type,Integer sub_unit_id);


	List<LRFHeatConsumableModel> getArcAdditionsBySampleNo(Integer arc_sl_no,
			String heat_id, Integer heat_cnt);


	String lrfArcAdditionsSaveOrUpdate(LRFRequestWrapper reqWrapper,String arc_start_date,String arc_end_date, int userId);


	LRFHeatConsumableModel getLRFHeatConsumablesById(Integer cons_sl_no);


	LRFHeatArcingDetailsModel getArcDetailsBySlno(Integer arc_sl_no);
	
	//List<LRFHeatConsumableDisplay> getLRFArcAdditionsByHeat(String heat_id,	Integer heat_cnt, Integer unit_id);


	LRFHeatConsumableDisplay getLRFArcAdditionsByHeat(String heat_id,
			Integer heat_cnt,Integer unit_id);


	List<Map<String, Object>> getLRFArcAdditionsTemp(Integer unit_id, String heatId, Integer heatCnt);


	List<HeatChemistryChildDetails> getChemDtlsByAnalysis(Integer analysis_id,
			String heat_id, Integer heat_counter, Integer sub_unit_id,
			String sample_no, Integer aim_psn_id);


	String saveLrfDispatchDet(LRFRequestWrapper reqWrapper, int parseInt);

	HashSet<LRFHeatDetailsModel> getHeatsForVDProcess(String cunit,String pstatus);


	HashSet<LRFHeatDetailsModel> getHeatsForCasterProcess(String cunit,
			String pstatus);
	
	List<DelayEntryDTO> getLrfDelayEntriesBySubUnitAndHeat(Integer sub_unit_id,Integer trans_heat_id,Integer heat_counter, String prev_unit);
	
	public LRFHeatDetailsModel getLRFHeatDetailsByHeatNo(String heatNo,Integer heat_counter);
	String updateLRFHeatDetPSN(LRFHeatDetailsModel lrfHeatObj, LRFHeatDetailsPsnBkpModel lrfHeatPsnBkp, List<HeatPlanHdrDetails> heatPlanHdrLi, List<HeatPlanDetails> heatPlanDetLi, HeatStatusTrackingModel heatTrackObj,IfacesmsLpDetailsModel ifaceObj);
	List<LRFHeatDetailsModel> getHeatsForLadleMix(String cunit, String pstatus);
	HeatStatusTrackingModel getHeatStatusObject(Integer integer);
	String updateLRFHeatDetForLadleMix(LRFHeatDetailsModel lrfHeatObj1, LRFHeatDetailsModel lrfHeatObj2, HeatStatusTrackingModel heatTrackObj1, HeatStatusTrackingModel heatTrackObj2);
	LRFHeatDetailsModel getLRFHeatObject(Integer trns_sl_no);
	public List<TransDelayEntryHeader> getDelayDetailsHdrWithSubUnitAndHeat(Integer sub_unit_id, Integer trans_heat_id);
	List<LRFHeatLogRpt> getLrfHeatLogs(String heatId, String LogType);
	
	
	public List<LrfElectrodeTransactions> getAllElectrodeUsageTrnsByUnit(Integer sub_unit_id,Integer trans_no);
	
	String lrfElectrodeUsageTrnsSaveOrUpdate(LrfElectrodeTransactions obj);
	
	LrfElectrodeTransactions lrfElectrodeUsageTrnsById(Integer id);
	
	String updateLrfHeatDetails(LRFHeatDetailsModel model);
	
	public List<CommonCombo> getAllAvbleLadle() ;
	
	public List<ReladleTrnsDetailsMdl> getUnProcessedReturnHeatDtls(Integer trns_sl_no);
	public List<ReladleProcessDetailsModel> getReladleDetails(Integer reladle_heat_id);
	public ReladleTrnsDetailsMdl getReturnHeatDetailsById(Integer trns_sl_no);
	String saveReladleDetails(List<ReladleProcessHdrModel> reladleHdrList, List<ReladleTrnsDetailsMdl> reladleHeatDtlLi, List<HeatStatusTrackingModel> heatTrackLi,
		List<SteelLadleTrackingModel> steelLadleTrackLi, List<SteelLadleLifeModel> stLadlePartsLi, List<StLadleLifeHeatWiseModel> heatwiseStLdlPartsLi, StLdlLifeAtHeat heatwiseStLdlObj);
	String saveDispatchToLrfDtls(LRFHeatDetailsModel lrfHeatObj, LRFHeatDetailsModel newLrfHeatObj, HeatStatusTrackingModel heatTrackObj, ReladleTrnsDetailsMdl reladleTrnsObj);
	List<LRFHeatDetailsModel> getLrfChemDetails(String heat_id, Integer heat_counter);
	List<HeatChemistryChildDetails> getChemDtlsByAnalysisWithSpectro(Integer analysis_id, String heat_id,
				Integer heat_counter, Integer sub_unit_id, String sample_no, Integer psn_id, String actual_sample_no, String actual_heat);
	public List<LRFHeatConsumableModel> getLrfAdditionsByHeatNo(String heat_no, String mtrl_type);
	String saveOrUpdateMatrlCons(List<LRFHeatConsumableModel> updLi, List<LRFHeatConsumableLogModel> logLi);
    HeatStatusTrackingModel getHeatTrackingObj(Integer heat_track_id);
	List<Object> getActualPathUnits(String act_proc_path);
	
	public HeatChemistryHdrDetails getHeatDetailsByHeatNo(String heatNo,String LogType);
}
