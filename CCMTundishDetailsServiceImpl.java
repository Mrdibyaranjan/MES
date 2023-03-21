package com.smes.trans.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smes.trans.dao.impl.CCMTundishDetailsDao;
import com.smes.trans.model.CCMTundishDetailsModel;

@Service
public class CCMTundishDetailsServiceImpl implements CCMTundishDetailsService{
	
	@Autowired
	CCMTundishDetailsDao ccmtundishDtlsDao;

	
	@Override
	public List<CCMTundishDetailsModel> getccmTundishDtlsByTrnsSlNo(Integer trns_sl_no) {
		// TODO Auto-generated method stub
		return ccmtundishDtlsDao.getccmTundishDetailsByTrnsSlno(trns_sl_no);
	}
	
	@Override
	public String saveccmTundishDetails(CCMTundishDetailsModel model) {
		// TODO Auto-generated method stub
		return ccmtundishDtlsDao.saveccmTundishDetails(model);
	}

	@Override
	public String updateccmTundishDetails(CCMTundishDetailsModel model) {
		// TODO Auto-generated method stub
		return ccmtundishDtlsDao.updateccmTundishDetails(model);
	}


	
	
	

}
