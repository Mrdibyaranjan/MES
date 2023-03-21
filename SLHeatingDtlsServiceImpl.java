package com.smes.trans.service.impl;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smes.trans.dao.impl.SLHeatingDtlsDao;
import com.smes.trans.model.SLHeatingDetailsModel;

@Service("SLHeatingService")
public class SLHeatingDtlsServiceImpl implements SLHeatingDtlsService {
	@Autowired
	SLHeatingDtlsDao steelLadleHeatingDao;
	
	@Override
	public Integer heatingSave(SLHeatingDetailsModel heating,HttpSession sessionUser) {
		// TODO Auto-generated method stub
		return steelLadleHeatingDao.heatingSave(heating,sessionUser);
	}

	@Override
	public Integer updateLadleStatus(Integer heat_id, Integer stladle_track_id) {
		// TODO Auto-generated method stub
		return steelLadleHeatingDao.updateLadleStatus(heat_id, stladle_track_id);
	}

	@Override
	public Integer setLadleStatusDown(Integer stladle_track_id,HttpSession sessionUser) {
		// TODO Auto-generated method stub
		return steelLadleHeatingDao.setLadleStatusDown(stladle_track_id,sessionUser);
	}
	
}
