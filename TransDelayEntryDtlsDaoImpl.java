package com.smes.trans.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.smes.trans.model.TransDelayDetailsModel;
import com.smes.util.Constants;
import com.smes.util.GenericDaoImpl;

@Repository("transDelayDtlsRepo")
public class TransDelayEntryDtlsDaoImpl  extends GenericDaoImpl<TransDelayDetailsModel, Long>  implements TransDelayEntryDtlsDao {

	private static final Logger logger = Logger.getLogger(TransDelayEntryDtlsDaoImpl.class);
	@Override
	public List<TransDelayDetailsModel> getTransDelayDtlsByDelayDdrId(Integer trans_delay_entry_hdr_id) {
		Session session=getNewSession();
		List<TransDelayDetailsModel> obj=new ArrayList<>();
		try {
			String hql = "select a from TransDelayDetailsModel a where a.delay_entry_hdr_id="+trans_delay_entry_hdr_id;
			Query query=session.createQuery(hql);
			obj =query.list();
		} catch (Exception e) {
			// TODO: handle exception
			//logger.error("error in getEOFHeatDetailsByHeatNo........"+e);
			e.printStackTrace();
		}
		finally{
			close(session);
		}
		return obj;
	}

	@Override
	public String saveTransDelayEntryDtls(TransDelayDetailsModel transDelayDetailsModel) {
		String result = Constants.SAVE;
		try {
			logger.info(EofProductionDaoImpl.class+" Inside eofHeatProductionSave ");
			create(transDelayDetailsModel);

		} catch (Exception e) {
			logger.error(EofProductionDaoImpl.class+" Inside eofHeatProductionSave Exception..", e);
			result = Constants.SAVE_FAIL;
		}

		return result;
	}

	@Override
	public String updateTransDelayDtls(TransDelayDetailsModel transDelayDetailsModel) {
		logger.info("inside .. TransDelayEntryDtlsDaoImpl....."+TransDelayEntryDtlsDaoImpl.class);
		String result = "";
		Session session=getNewSession();
		try{
			begin(session);	
			session.merge(transDelayDetailsModel);
			commit(session);
			result =Constants.UPDATE;
		} catch (Exception e) {
			e.printStackTrace();
			rollback(session);
			logger.error(TransDelayEntryDtlsDaoImpl.class+" Inside updateTransDelayDtls Exception..", e);
			result = Constants.UPDATE_FAIL;
		}finally {
			close(session);
		}

		return result;
	}

}
