package com.smes.trans.dao.impl;


import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import com.smes.trans.model.CCMProductDetailsModel;
import com.smes.util.Constants;
import com.smes.util.GenericDaoImpl;

@Repository
public class CCMProductDetailsDaoImpl  extends GenericDaoImpl<CCMProductDetailsModel, Long> implements CCMProductDetailsDao {

	private static final Logger logger = Logger.getLogger(HeatPlanDetailsDaoImpl.class);
	
	@Transactional
	@Override
	public List<CCMProductDetailsModel> getccmProdDetailsByTrnsSLno(Integer trns_sl_no) {
		List<CCMProductDetailsModel> lst=new ArrayList<>();
		Session session=getNewSession();
		try {

			String hql = "select a from CCMProductDetailsModel a where a.trns_sl_no="+trns_sl_no+" and a.record_status =1 order by a.stand_id asc";
			
			lst=(List<CCMProductDetailsModel>) session.createQuery(hql).list();

		} catch (Exception e) {
			// TODO: handle exception
			//logger.error("error in getEofHeatDtlsById........"+e);
			e.printStackTrace();
		}finally{
			close(session);
		}
		return lst;
		
	}

	@Transactional
	@Override
	public String saveccmProdDetails(CCMProductDetailsModel model) {
		String result = Constants.SAVE;
		create(model);
		return result;
	}

	@Transactional
	@Override
	public String updateccmProdDetails(CCMProductDetailsModel model) {
		String result = Constants.SAVE;
		update(model);
		return result;
	}

	@Override
	public String saveCcmProdDetailsList(List<CCMProductDetailsModel> model) {
		// TODO Auto-generated method stub
		logger.info("inside .. saveCcmProdDetailsList....." + CCMProductDetailsDaoImpl.class);
		String result = "";
		Session session = getNewSession();
		try {
			begin(session);
			model.forEach(obj -> {
				session.saveOrUpdate(obj);
			});
			commit(session);
			result = Constants.SAVE;
		} catch (Exception e) {
			e.printStackTrace();
			rollback(session);
			logger.error(CCMProductDetailsDaoImpl.class + " Inside saveCcmProdDetailsList Exception..", e);
			result = Constants.SAVE_FAIL;
		} finally {
			close(session);
		}

		return result;
	}

}
