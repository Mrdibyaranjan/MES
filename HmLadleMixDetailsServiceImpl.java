package com.smes.trans.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smes.trans.dao.impl.HmLadleMixDetailsDao;
import com.smes.trans.model.HotMetalLadleMixDetails;

@Service("hmLadleMixService")
public class HmLadleMixDetailsServiceImpl implements HmLadleMixDetailsService {

	@Autowired
	private HmLadleMixDetailsDao hmLadleMixDao;
	
	@Transactional
	@Override
	public String HmLadleMixDetailsSave(HotMetalLadleMixDetails hmLadleMixDtls) {
		// TODO Auto-generated method stub
		return hmLadleMixDao.hotMetalLadleMixDetailsSave(hmLadleMixDtls);
	}

}
