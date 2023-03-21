package com.smes.trans.service.impl;

import java.util.List;

import com.smes.trans.model.CCMProductDetailsModel;

public interface CCMProductDetailsService {
	List<CCMProductDetailsModel> getccmProdDetailsByTrnsSLno(Integer trns_sl_no);
	String saveccmProdDetails(CCMProductDetailsModel model) ;
	public String updateccmProdDetails(CCMProductDetailsModel model);
	public boolean updateProdBasedOnBatch(Integer trns_sl_no);
	String saveCcmProdDetailsList(List<CCMProductDetailsModel> model);
}
