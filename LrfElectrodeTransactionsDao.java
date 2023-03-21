package com.smes.trans.dao.impl;

import java.util.List;

import com.smes.trans.model.LrfElectrodeTransactions;

public interface LrfElectrodeTransactionsDao {

	List<LrfElectrodeTransactions> getAllElectrodeUsageTrnsByUnit(Integer sub_unit_id);
	LrfElectrodeTransactions getElectrodeStatusByUnitAndLkp(Integer sub_unit_id,Integer electrodeId,Integer trans_si_no);
	String lrfElectrodeUsageTrnsSaveOrUpdate(LrfElectrodeTransactions obj);
	public LrfElectrodeTransactions lrfElectrodeUsageTrnsById(Integer id);
}
