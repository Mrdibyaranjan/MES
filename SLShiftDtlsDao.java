package com.smes.trans.dao.impl;

import com.smes.masters.model.SLMasterModel;
import com.smes.trans.model.SLShiftDetailsModel;

public interface SLShiftDtlsDao {
	//save
		Integer saveSteelLadle(SLShiftDetailsModel steelLadleStatus);
		//update
		String updateSteelLadle(SLShiftDetailsModel steelLadleStatus);
		
		String checkShiftDtls(SLShiftDetailsModel steelLadleDtls);
		
		SLShiftDetailsModel shiftAutofill(SLShiftDetailsModel steelLadleDtls);
		
		SLShiftDetailsModel getShiftDtlsById(Integer shiftId);
		
}
