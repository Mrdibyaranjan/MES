package com.smes.trans.service.impl;

import com.smes.trans.model.SLShiftDetailsModel;

public interface SLShiftDtlsService {

	//save
		String saveSteelLadle(SLShiftDetailsModel steelLadleStatus,Integer userId);
		//update
		String updateSteelLadle(SLShiftDetailsModel steelLadleStatus);
		
		String checkShift(SLShiftDetailsModel steelLadleDtls);
		
		SLShiftDetailsModel shiftAutofill(SLShiftDetailsModel steelLadleDtls);
		
		SLShiftDetailsModel getShiftDtlsById(Integer shiftId);
}
