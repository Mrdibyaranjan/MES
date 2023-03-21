package com.smes.trans.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smes.trans.dao.impl.SLShiftDtlsDao;
import com.smes.trans.model.SLShiftDetailsModel;

@Service("SLShiftService")
public class SLShiftDtlsServiceImpl implements SLShiftDtlsService{
	
	@Autowired
	private SLShiftDtlsDao slShiftDao;

	@Override
	public String saveSteelLadle(SLShiftDetailsModel steelLadleStatus, Integer userId) {
		// TODO Auto-generated method stub
		Integer result=slShiftDao.saveSteelLadle(steelLadleStatus);
		if(result!=null)
		return "SAVED";
		else
			return "FAILED";
	}

	@Override
	public String updateSteelLadle(SLShiftDetailsModel steelLadleStatus) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String checkShift(SLShiftDetailsModel steelLadleDtls) {
		String result=slShiftDao.checkShiftDtls(steelLadleDtls);
		
		return result;
		}

	@Override
	public SLShiftDetailsModel shiftAutofill(SLShiftDetailsModel steelLadleDtls) {
		return slShiftDao.shiftAutofill(steelLadleDtls);
		
		}

	@Override
	public SLShiftDetailsModel getShiftDtlsById(Integer shiftId) {
		// TODO Auto-generated method stub
		return slShiftDao.getShiftDtlsById(shiftId);
	}
	
}
