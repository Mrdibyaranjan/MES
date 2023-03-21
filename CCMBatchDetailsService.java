package com.smes.trans.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smes.trans.dao.impl.CCMBatchDetailsDao;
import com.smes.trans.dao.impl.CCMProductDetailsDao;
import com.smes.trans.model.CCMBatchDetailsBkpModel;
import com.smes.trans.model.CCMBatchDetailsModel;
import com.smes.trans.model.CCMProductDetailsBkpModel;
import com.smes.trans.model.CCMProductDetailsModel;
import com.smes.trans.model.HeatStatusTrackingModel;
import com.smes.trans.model.IfacesmsLpDetailsModel;

@Service
public class CCMBatchDetailsService  {

	@Autowired
	CCMBatchDetailsDao ccmProdBatchDao;
	
	@Autowired
	CCMProductDetailsDao cmmprodDtlsDao;
	
	public List<CCMBatchDetailsModel> getBatchDetailsByProduct(Integer product_id) {
		// TODO Auto-generated method stub
		return ccmProdBatchDao.getBatchDetailsByProduct(product_id);
	}

	
	public String saveProductBatch(CCMBatchDetailsModel model) {
		// TODO Auto-generated method stub
		return ccmProdBatchDao.saveProductBatch(model);
	}

	
	public String updateProductBatch(CCMBatchDetailsModel model) {
		// TODO Auto-generated method stub
		return ccmProdBatchDao.updateProductBatch(model);
	}
	
	public String removeBatchEntry(CCMBatchDetailsModel model) {
		return ccmProdBatchDao.	removeProductBatch(model);
	}
	public String saveUpdateOrDeleteCCMBatches(Map<String, List<CCMProductDetailsModel>> ccmProdMap, Map<String, List<CCMBatchDetailsModel>> ccmBatchMap, List<CCMProductDetailsBkpModel> ccmProdBkpLi, List<CCMBatchDetailsBkpModel> ccmBatchBkpLi) {
		return ccmProdBatchDao.saveUpdateOrDeleteCCMBatches(ccmProdMap, ccmBatchMap, ccmProdBkpLi, ccmBatchBkpLi);
	}
	public String ccmProductionPosting(HeatStatusTrackingModel heatTrackObj,IfacesmsLpDetailsModel ifacObj) {
		return ccmProdBatchDao.ccmProductionPosting(heatTrackObj,ifacObj);
	}
	public List<CCMProductDetailsBkpModel> getProdDtlsBkpByHeatTrnsId(Integer heatTransSlNo){
		return ccmProdBatchDao.getProdDtlsBkpByHeatTrnsId(heatTransSlNo);
	}
	public List<CCMBatchDetailsBkpModel> getBatchDtlsBkpByProdTrnsId(Integer ProdTransSlNo){
		return ccmProdBatchDao.getBatchDtlsBkpByProdTrnsId(ProdTransSlNo);
	}
    public String getSteelQty(Integer trns_sl_no,Integer billet_max_length) {
		// TODO Auto-generated method stub
		return ccmProdBatchDao.getSteelQty(trns_sl_no,billet_max_length);
	}
    public String ccmBatchProductionPosting(HeatStatusTrackingModel heatTrackObj, IfacesmsLpDetailsModel ifacObj) {
		return ccmProdBatchDao.ccmBatchProductionPosting(heatTrackObj,ifacObj);
	}
	public String saveCCMBatchDetails(List<CCMBatchDetailsModel> batchLi, Integer trns_sl_no) {
		// TODO Auto-generated method stub
		List<CCMProductDetailsModel> productLi = cmmprodDtlsDao.getccmProdDetailsByTrnsSLno(trns_sl_no);
		productLi.forEach(prod -> {
			Double twgt=0.0;
			List<CCMBatchDetailsModel> lst = getBatchDetailsByProduct(prod.getProd_trns_id());
			if (!lst.isEmpty()) {
				Double sumValue=lst.stream().mapToDouble(wgt->{
					if(wgt.getAct_batch_wgt()!=null) {
						return wgt.getAct_batch_wgt();
					}else {
						return 0.0;
					}
					}).sum();
				twgt+=sumValue;
				prod.setNo_batches((float)lst.size());
				prod.setTot_wgt_batches(twgt.floatValue());
			}
		});
	
		return ccmProdBatchDao.saveCCMBatchDetails(batchLi, productLi);
	}
}
