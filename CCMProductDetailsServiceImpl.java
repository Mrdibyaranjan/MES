package com.smes.trans.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smes.trans.dao.impl.CCMProductDetailsDao;
import com.smes.trans.model.CCMBatchDetailsModel;
import com.smes.trans.model.CCMProductDetailsModel;

@Service
public class CCMProductDetailsServiceImpl implements CCMProductDetailsService {

	@Autowired
	CCMProductDetailsDao cmmprodDtlsDao;
	
	@Autowired
	CCMBatchDetailsService ccmBatchDetailsService;
	
	@Override
	public List<CCMProductDetailsModel> getccmProdDetailsByTrnsSLno(Integer trns_sl_no) {
		// TODO Auto-generated method stub
		return cmmprodDtlsDao.getccmProdDetailsByTrnsSLno(trns_sl_no);
	}

	@Override
	public String saveccmProdDetails(CCMProductDetailsModel model) {
		// TODO Auto-generated method stub
		return cmmprodDtlsDao.saveccmProdDetails(model);
	}
	@Override
	public String updateccmProdDetails(CCMProductDetailsModel model) {
		// TODO Auto-generated method stub
		return cmmprodDtlsDao.updateccmProdDetails(model);
	}
	@Override
	public boolean updateProdBasedOnBatch(Integer trns_sl_no) {
		
		List<CCMProductDetailsModel> products =getccmProdDetailsByTrnsSLno(trns_sl_no);
		products.forEach(prod -> {
			Double twgt=0.0;
			List<CCMBatchDetailsModel> lst = ccmBatchDetailsService.getBatchDetailsByProduct(prod.getProd_trns_id());
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
				updateccmProdDetails(prod);
			}
			
		});
		return true;
	}

	@Override
	public String saveCcmProdDetailsList(List<CCMProductDetailsModel> model) {
		// TODO Auto-generated method stub
		return cmmprodDtlsDao.saveCcmProdDetailsList(model);
	}

}
