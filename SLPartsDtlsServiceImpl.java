package com.smes.trans.service.impl;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smes.trans.dao.impl.SLPartsDtlsDao;
import com.smes.trans.model.SLHeatingDetailsModel;
import com.smes.trans.model.SLPartsDetailsModel;

@Service("SLPartsMap")
public class SLPartsDtlsServiceImpl implements SLPartsDtlsService {
@Autowired SLPartsDtlsDao slPartsMapDao;
	
	@Transactional
	@Override
	public Integer saveParts(List<SLPartsDetailsModel> parts,HttpSession sessionUser,Integer ladle_man,Integer ladle_man_asst) {
		// TODO Auto-generated method stub
		return slPartsMapDao.saveParts(parts,sessionUser,ladle_man,ladle_man_asst);
	}
	@Override
	public List<SLPartsDetailsModel> getLadleParts(Integer ladle_trns_si_no, Integer ladle_life) {
		// TODO Auto-generated method stub
		return slPartsMapDao.getLadleParts(ladle_trns_si_no, ladle_life);
	}
	@Override
	public List<SLHeatingDetailsModel> getHeatingDtls(Integer ladle_trns_si_no, Integer ladle_life) {
		// TODO Auto-generated method stub
		return slPartsMapDao.getHeatingDtls(ladle_trns_si_no, ladle_life);
	}
	

}
