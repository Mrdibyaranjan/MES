package com.smes.trans.service.impl;


import java.util.List;

import com.smes.trans.model.CCMHeatDetailsModel;
import com.smes.trans.model.CastRunningStatusModel;
import com.smes.trans.model.HeatChemistryChildDetails;
import com.smes.trans.model.HeatPlanHdrDetails;
import com.smes.trans.model.HeatPlanLinesDetails;
import com.smes.trans.model.MtubeTrnsModel;
import com.smes.util.CommonCombo;
import com.smes.util.DelayEntryDTO;
import com.smes.wrappers.CasterRequestWrapper;

public interface CasterProductionService {

	CastRunningStatusModel getRunningIdDetByHeatPlanNo(Integer heat_planno);

	String saveRunningIdDetails(CastRunningStatusModel runObj,
			String cast_start_dt, String cast_close_dt, int parseInt);

	List<HeatPlanHdrDetails> getHeatPlanHeaderDetailsByCaster(
			String planstatus, String casterType);

	String attachHeatAndGenerateRunId(
			CastRunningStatusModel crsModel, String start_date, Integer userId);

	List<HeatPlanLinesDetails> getHeatPlanDetWithRunId(Integer runId);

	CastRunningStatusModel getRunIdDetWithRunId(Integer runId);

	String saveAll(CasterRequestWrapper reqWrapper, int parseInt);

	public String saveCastDetails(CCMHeatDetailsModel model);
	
	public MtubeTrnsModel getMtubelife(Integer mtube_sl_no);
	
	public MtubeTrnsModel getMouldDetails(Integer mtube_Trans_id);
		
	public List<HeatChemistryChildDetails> getLrfLiftChemDetails(String heat_id, Integer heat_counter, Integer aim_psn);
	public List<DelayEntryDTO> getDelayEntriesBySubUnitAndHeat(Integer sub_unit_id, Integer trns_si_no, String heat_no, Integer heat_counter);

	List<CommonCombo> getheatId(Integer lookup_id);

}
