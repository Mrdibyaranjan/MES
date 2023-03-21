package com.smes.trans.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.smes.trans.model.TransDelayEntryHeader;

@Service
public interface TransDelayEntryHeaderService {
	public TransDelayEntryHeader getTransDelayHeaderByHeatAndActivity(Integer activityId, Integer heatId);
	public TransDelayEntryHeader getTransDelayHeaderById(Integer delayHdrId);
	public List<TransDelayEntryHeader> getTransDelayHeaderByTransId(Integer trnsId, Integer sub_unit_id);
	public String saveTransDelayEntryHeader(TransDelayEntryHeader transDelayEntryHeader);
	public String updateTransDelayEntryHeader(TransDelayEntryHeader transDelayEntryHeader);
}
