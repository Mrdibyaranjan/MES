package com.smes.trans.dao.impl;

import java.util.List;

import com.smes.trans.model.CCMTundishDetailsModel;
import com.smes.trans.model.MtubeTrnsModel;

public interface CCMTundishDetailsDao {
	List<CCMTundishDetailsModel> getccmTundishDetailsByTrnsSlno(Integer trns_sl_no);
	String saveccmTundishDetails(CCMTundishDetailsModel model);
	public String updateccmTundishDetails(CCMTundishDetailsModel model);
	
	MtubeTrnsModel mtubeTrns(Integer mtube_Trans_id);

}
