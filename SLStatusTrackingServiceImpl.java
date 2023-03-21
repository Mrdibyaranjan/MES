package com.smes.trans.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smes.trans.dao.impl.SLStatusTrackingDao;
import com.smes.trans.model.SLStatusTrackingModel;
import com.smes.trans.model.SteelLadleTrackingModel;
import com.smes.util.GenericDaoImpl;

@Service("SLStatusTracking")
public class SLStatusTrackingServiceImpl extends GenericDaoImpl<SLStatusTrackingModel, Long> implements SLStatusTrackingService {
	
	@Autowired
	SLStatusTrackingDao steelLadleMaintnDao;
	
	
	@Override
	public List<SLStatusTrackingModel> getSteelLadleTracking() {
		// TODO Auto-generated method stub
		return steelLadleMaintnDao.getSteelLadleTracking();
	}
	
}
