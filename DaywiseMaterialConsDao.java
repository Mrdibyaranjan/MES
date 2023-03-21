package com.smes.trans.dao.impl;

import java.util.List;
import java.util.Map;

import com.smes.trans.model.CCMHeatConsMaterialsDetails;
import com.smes.trans.model.DaywiseMaterialConsModel;
import com.smes.trans.model.HeatConsMaterialsDetails;
import com.smes.trans.model.LRFHeatConsumableModel;

public interface DaywiseMaterialConsDao {
	List<DaywiseMaterialConsModel> getDaywiseMtrlConsumption(String cons_date);
	//String saveOrUpdateDayConsumptions(List<DaywiseMaterialConsModel> saveUpdLi);
	DaywiseMaterialConsModel getDaywiseMaterialConsById(Integer mtrlConsTrnsSlNo);
	boolean isConsumptionPosted(String cons_date);
	String saveOrUpdateMtrlConsumptions(Map<String, List> saveUpdateLi);
	List<HeatConsMaterialsDetails> getEofConsSlNo(String cons_date);
	List<HeatConsMaterialsDetails> getEofConsQty(String cons_date,String cons_type);
	List<LRFHeatConsumableModel> getLrfConsQty(String cons_date, String cons_type);
	List<CCMHeatConsMaterialsDetails> getCcmConsQty(String cons_date, String cons_type);
	
		
}
