package com.smes.trans.service.impl;

import java.util.List;

import com.smes.trans.model.HeatStatusTrackingModel;
import com.smes.trans.model.IntfMaterialConsumptionModel;
import com.smes.trans.model.LRFHeatDetailsModel;
import com.smes.util.MaterialConsumptionDTO;

public interface MaterialConsumptionService {
	List<HeatStatusTrackingModel> getProdPostedHeats(String cons_date);
	List<IntfMaterialConsumptionModel> getHeatwiseMtrlCons(String heatNo, Integer heatTrackId, String cons_date);
	List<List<MaterialConsumptionDTO>> getProdUnitwiseMtrlCons(String heatNo, String subUnitName, Integer subUnitId, Integer heatTrackId, Double heatQty, Integer totalHeats, String cons_date);
	//List<List<MaterialConsumptionDTO>> getProdUnitwiseMtrlCons(MaterialConsumptionDTO mtrlConsDTO, String cons_date);
	String saveOrUpdateMtrlConsumptions(MaterialConsumptionDTO mtrlConsDto, String consDate, Integer userId);
	String postMtrlConsumptions(IntfMaterialConsumptionModel intfMtrlConsObj, Integer userId);
	List<LRFHeatDetailsModel> getLrfHeatDetailObj(String heatNo, Integer subUnitId);
}
