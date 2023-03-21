package com.smes.trans.dao.impl;

import com.smes.trans.model.MtubeTrnsModel;

public interface MtubeTrnsModelDao {

	//List<MtubeTrnsModel> getMtubeTrns
	MtubeTrnsModel getMtubeTrnsByid(Integer trns_id);
	public String saveccmMtubeTrns(MtubeTrnsModel model);
	public String updateccmMtubeTrns(MtubeTrnsModel model);
}