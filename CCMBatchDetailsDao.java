package com.smes.trans.dao.impl;

import java.util.List;
import java.util.Map;

import com.smes.trans.model.CCMBatchDetailsBkpModel;
import com.smes.trans.model.CCMBatchDetailsModel;
import com.smes.trans.model.CCMProductDetailsBkpModel;
import com.smes.trans.model.CCMProductDetailsModel;
import com.smes.trans.model.HeatStatusTrackingModel;
import com.smes.trans.model.IfacesmsLpDetailsModel;

public interface CCMBatchDetailsDao {

	List<CCMBatchDetailsModel> getBatchDetailsByProduct(Integer product_id);
	
	String saveProductBatch(CCMBatchDetailsModel model);
	
	String updateProductBatch(CCMBatchDetailsModel model);
	
	public String removeProductBatch(CCMBatchDetailsModel model);
	
	String saveUpdateOrDeleteCCMBatches(Map<String, List<CCMProductDetailsModel>> ccmProdMap, Map<String, List<CCMBatchDetailsModel>> ccmBatchMap, List<CCMProductDetailsBkpModel> ccmProdBkpLi, List<CCMBatchDetailsBkpModel> ccmBatchBkpLi);
	
	String ccmProductionPosting(HeatStatusTrackingModel heatTrackObj, IfacesmsLpDetailsModel ifacObj);
	List<CCMProductDetailsBkpModel> getProdDtlsBkpByHeatTrnsId(Integer heatTransSlNo);
	List<CCMBatchDetailsBkpModel> getBatchDtlsBkpByProdTrnsId(Integer ProdTransSlNo);
    String getSteelQty(Integer trns_sl_no,Integer billet_max_length);
    String ccmBatchProductionPosting(HeatStatusTrackingModel heatTrackObj,IfacesmsLpDetailsModel ifacObj);
    String saveCCMBatchDetails(List<CCMBatchDetailsModel> batchLi, List<CCMProductDetailsModel> productLi);
    
}
