package com.smes.trans.dao.impl;

import java.util.List;

import com.smes.trans.model.CCMHeatProcessParameterDetails;

public interface CCMHeatProcessParameterDetailsDao {

	List<CCMHeatProcessParameterDetails> getProcessParamByHeatNo(String heatNo);
	List<CCMHeatProcessParameterDetails> getProcessParamByHeatNoAndParm(String heatNo, Integer heat_counter, Integer param_id);
	public String processParamsUpdate(
			CCMHeatProcessParameterDetails ppmMaster);
	public String processParamsSave(CCMHeatProcessParameterDetails ppmMaster);
}
