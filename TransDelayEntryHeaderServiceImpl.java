package com.smes.trans.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smes.trans.dao.impl.TransDelayEntryHeaderDao;
import com.smes.trans.model.TransDelayEntryHeader;
@Transactional
@Service
public class TransDelayEntryHeaderServiceImpl implements TransDelayEntryHeaderService{

	@Autowired
	TransDelayEntryHeaderDao transDelayDao;
	@Override
	public TransDelayEntryHeader getTransDelayHeaderByHeatAndActivity(Integer activityId, Integer heatId) {
		// TODO Auto-generated method stub
		return transDelayDao.getTransDelayHeaderByHeatAndActivity(activityId, heatId);
	}
	@Override
	public String saveTransDelayEntryHeader(TransDelayEntryHeader transDelayEntryHeader) {
		// TODO Auto-generated method stub
		return transDelayDao.saveTransDelayEntryHeader(transDelayEntryHeader);
	}
	@Override
	public String updateTransDelayEntryHeader(TransDelayEntryHeader transDelayEntryHeader) {
		// TODO Auto-generated method stub
		return transDelayDao.updateTransDelayEntryHeader(transDelayEntryHeader);
	}
	@Override
	public TransDelayEntryHeader getTransDelayHeaderById(Integer delayHdrId) {
		// TODO Auto-generated method stub
		return transDelayDao.getTransDelayHeaderById(delayHdrId) ;
	}
	@Override
	public List<TransDelayEntryHeader> getTransDelayHeaderByTransId(Integer trnsId, Integer sub_unit_id) {
		// TODO Auto-generated method stub
		return transDelayDao.getTransDelayHeaderByTransId(trnsId, sub_unit_id) ;
	}

}
