package com.smes.trans.dao.impl;

import java.util.List;

import com.smes.masters.model.TundishMaintanaceLogModel;
import com.smes.trans.model.TundishTrnsHistoryModel;

public interface TundishTrnsHistoryDao {

	public List<TundishTrnsHistoryModel> getTundishStatusHistoryByTundishId(Integer unit_id);
	
	public List<TundishMaintanaceLogModel> getTundishEventsByTnsId(Integer trnsId);
	
	public List<TundishMaintanaceLogModel> getTundishEventsByTnsIdAndEventId(Integer trnsId,Integer event_id);
	
	public String hstryUpdate(TundishTrnsHistoryModel tundishTrnsHistoryModel);
	
	public String hstrySave(TundishTrnsHistoryModel tundishTrnsHistoryModel);
	
	public TundishTrnsHistoryModel getTundishById(Integer trnsId);
	
	public List<TundishTrnsHistoryModel> getTundishStatusHistoryByTundishId(Integer tundishId,Integer status) ;
}
