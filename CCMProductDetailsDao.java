package com.smes.trans.dao.impl;

import java.util.List;

import com.smes.trans.model.CCMProductDetailsModel;

public interface CCMProductDetailsDao {

	List<CCMProductDetailsModel> getccmProdDetailsByTrnsSLno(Integer trns_sl_no);
	String saveccmProdDetails(CCMProductDetailsModel model) ;
	public String updateccmProdDetails(CCMProductDetailsModel model);
	String saveCcmProdDetailsList(List<CCMProductDetailsModel> model);
}
