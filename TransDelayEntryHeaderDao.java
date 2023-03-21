package com.smes.trans.dao.impl;

import java.util.List;

import com.smes.trans.model.TransDelayEntryHeader;

public interface TransDelayEntryHeaderDao {
	public TransDelayEntryHeader getTransDelayHeaderByHeatAndActivity(Integer activityId,Integer heatId);
	
	public TransDelayEntryHeader getTransDelayHeaderById(Integer delayHdrId);
	
	public List<TransDelayEntryHeader> getTransDelayHeaderByTransId(Integer trnsId, Integer sub_unit_id);
	
	public String saveTransDelayEntryHeader(TransDelayEntryHeader transDelayEntryHeader);
	
	public String updateTransDelayEntryHeader(TransDelayEntryHeader transDelayEntryHeader);
}
