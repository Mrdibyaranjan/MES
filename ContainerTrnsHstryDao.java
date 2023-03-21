package com.smes.trans.dao.impl;

import java.util.List;

import com.smes.trans.model.ContainerMasterTrnsHistoryModel;
import com.smes.trans.model.TundishTrnsHistoryModel;

public interface ContainerTrnsHstryDao {

	public String hstrySave(ContainerMasterTrnsHistoryModel containerTrnsHistoryModel);

	public List<ContainerMasterTrnsHistoryModel> getHstryById(Integer container_id);

	String hstryUpdate(ContainerMasterTrnsHistoryModel containerTrnsHistoryModel);
}
