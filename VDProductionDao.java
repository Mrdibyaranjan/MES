package com.smes.trans.dao.impl;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import com.smes.trans.model.VDHeatConsumableModel;
import com.smes.trans.model.VDHeatDetailsModel;
import com.smes.trans.model.VdAdditionsDetailsModel;

public interface VDProductionDao {

	VDHeatDetailsModel getVDHeatDtlsFormByID(Integer trns_sl_no);

	String saveAll(Hashtable<String, Object> mod_obj);

	String processEventDtlsSaveOrUpdate(Hashtable<String, Object> mod_obj);

	VDHeatDetailsModel getVdHeatObject(Integer trns_si_no);

	String updateVDHeatDetails(Hashtable<String, Object> mod_obj);
	
	public VDHeatDetailsModel getVDPreviousHeatDetailsByHeatNo( Integer trans_si_no);
	
	public VDHeatDetailsModel getVDHeatDetailsByHeatNo(String heatNo);

	List<VDHeatConsumableModel> getVdAdditions(String lookup_code, Integer subUnitId);

	VdAdditionsDetailsModel getAddDetailsBySlno(Integer arc_sl_no);

	VDHeatConsumableModel getVDHeatConsumablesById(Integer cons_sl_no);

	String getNextAddNo(String heat_id, Integer heat_counter);

	String VDAddSaveOrUpdate(Hashtable<String, Object> mod_obj);

	List<Map<String, Object>> getVDAdditionsTemp(String qry);
	
}
