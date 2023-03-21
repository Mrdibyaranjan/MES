package com.smes.trans.service.impl;


import java.util.List;
import java.util.Map;

import com.smes.trans.model.HeatChemistryChildDetails;
import com.smes.trans.model.HeatProcessEventDetails;
import com.smes.trans.model.LRFHeatDetailsModel;
import com.smes.trans.model.VDHeatConsumableModel;
import com.smes.trans.model.VDHeatDetailsModel;
import com.smes.trans.model.VdAdditionsDetailsModel;
import com.smes.util.DelayEntryDTO;
import com.smes.util.VDHeatConsumableDisplay;
import com.smes.wrappers.VDRequestWrapper;



public interface VDProductionService {

	VDHeatDetailsModel getVDHeatDtlsFormByID(Integer trns_sl_no);

	String saveAll(VDRequestWrapper reqWrapper, int parseInt);

	String heatProcessEventSaveOrUpdate(
			HeatProcessEventDetails heatProcessEventDtls, int parseInt);

	String saveVdDispatchDet(VDRequestWrapper reqWrapper, int parseInt);
	
	List<DelayEntryDTO> getVDDelayEntriesBySubUnitAndHeat(Integer sub_unit_id,Integer trans_heat_id,Integer heat_counter);

	public VDHeatDetailsModel getVDHeatDetailsByHeatNo(String heatNo);

	List<VDHeatConsumableModel> getVdAdditions(String lookup_code, Integer sub_unit_id);

	public VdAdditionsDetailsModel getAddDetailsBySlno(Integer arc_sl_no);

	public String VDAdditionsSaveOrUpdate(VDRequestWrapper reqwrapper, String arc_start_date, String arc_end_date, int userId);

	VDHeatConsumableModel getVDHeatConsumablesById(Integer cons_sl_no);

	VDHeatConsumableDisplay getVDAdditionsByHeat(String heat_id, Integer heat_cnt, Integer unit_id);

	List<Map<String, Object>> getVDAdditionsTemp(Integer unit_id, String heatId, Integer heatCnt);
	
	List<HeatChemistryChildDetails> getChemDtlsByAnalysis(Integer analysis_id,
			String heat_id, Integer heat_counter, Integer sub_unit_id,
			String sample_no, Integer aim_psn_id);
}
