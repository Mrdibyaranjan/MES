package com.smes.trans.service.impl;

import java.util.List;

import com.smes.trans.model.CCMTundishDetailsModel;

public interface CCMTundishDetailsService {
		//saving CCMTundish details
		List<CCMTundishDetailsModel> getccmTundishDtlsByTrnsSlNo(Integer trns_sl_no);
		String saveccmTundishDetails(CCMTundishDetailsModel model);
		public String updateccmTundishDetails(CCMTundishDetailsModel model);
}
