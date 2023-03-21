package com.smes.trans.dao.impl;

import java.util.Hashtable;
import java.util.List;

import com.smes.trans.model.CCMHeatConsMaterialsDetails;
import com.smes.trans.model.CCMHeatDetailsModel;
import com.smes.trans.model.CCMSeqGroupDetails;
import com.smes.trans.model.CastRunningStatusModel;
import com.smes.trans.model.HeatChemistryChildDetails;
import com.smes.trans.model.HeatStatusTrackingModel;
import com.smes.trans.model.MtubeTrnsModel;
import com.smes.trans.model.ReladleTrnsDetailsMdl;
import com.smes.util.CommonCombo;

public interface CasterProductionDao {

	CastRunningStatusModel getRunningIdDetByHeatPlanNo(Integer heat_planno);

	String saveRunningIdDet(CastRunningStatusModel runObj, Integer userId);

	String saveAllCastDetails(Hashtable<String, Object> htObj);

	CastRunningStatusModel getRunIdDetWithRunId(Integer runId);

	String saveAllCastHeatDetails(Hashtable<String, Object> htObj);

	String saveCastDetails(CCMHeatDetailsModel model);
	public String saveCCMretQty(CCMHeatDetailsModel model,String user,String heat_id);
	public ReladleTrnsDetailsMdl getReladleMdlByCasterId(Integer  casterID);
	
	public HeatStatusTrackingModel getHeatStatusTrackingModelByHeatId(String casterID);
	public HeatStatusTrackingModel getHeatStatusTrackingModelByHeatIdWithoutStatus(String heat_id, Integer heat_counter);
	
	public String updateHeatStatusTrack(HeatStatusTrackingModel model);
	
	public String saveORUpdatetCCMConsMtrl(CCMHeatConsMaterialsDetails model);
	
	public List<CCMSeqGroupDetails> getGroupSeqNos(Integer unit_id);
	public List<CCMSeqGroupDetails> getCloseGroupSeqNos(Integer unit_id);
	List<CCMHeatDetailsModel> getSeqGroupNosHeats(Integer unit_id,String  groupSeqNo);
	public MtubeTrnsModel getMtubelife(Integer mtube_sl_no);
	public List<HeatChemistryChildDetails> getLrfLiftChemDetails(String heat_id, Integer heat_counter, Integer aim_psn);
	public CCMHeatDetailsModel getHeatDtlsByHeatId(String heat_id,Integer heat_counter);
	public void sendFailedHeatData(String heat_no);
	public List<CommonCombo> getheatId(Integer lookup_id);
}
