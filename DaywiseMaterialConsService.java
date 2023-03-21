package com.smes.trans.service.impl;

import java.util.List;

import com.smes.trans.model.HeatStatusTrackingModel;
import com.smes.util.MaterialConsumptionDTO;

public interface DaywiseMaterialConsService {
	//List<MaterialConsumptionDTO> getDaywiseMtrlConsumption(String cons_date, String cons_type);
	boolean isConsumptionPosted(String cons_date);
	String saveOrUpdateDayConsumptions(List<MaterialConsumptionDTO> mtrlConsDtoLi, String consDate,Integer totalHeatsProduced,
			Integer userId);
	//String saveOrUpdateDayConsumptions(MaterialConsumptionDTO mtrlConsDto, String consDate,Integer totalHeatsProduced,String cons_type,
		//	Integer userId);
	List<MaterialConsumptionDTO> getDaywiseMtrlConsumption(String cons_date, String cons_type);
}
