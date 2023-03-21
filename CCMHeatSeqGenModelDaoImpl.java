package com.smes.trans.dao.impl;


import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.smes.trans.model.CCMHeatSeqGenModel;
import com.smes.util.GenericDaoImpl;

@Repository
public class CCMHeatSeqGenModelDaoImpl  extends GenericDaoImpl<CCMHeatSeqGenModel, Long> implements CCMHeatSeqGenModelDao {

	@Transactional
	@Override
	public CCMHeatSeqGenModel getCCMHeatSeqNoByUnit(Integer unit_id) {
		Session session=getNewSession();
		List<CCMHeatSeqGenModel> lst=null;
		try {

			String hql = "select a from CCMHeatSeqGenModel a where a.record_status =1 and a.unit_id="+unit_id;
			
			lst=(List<CCMHeatSeqGenModel>) session.createQuery(hql).list();

		} catch (Exception e) {
			// TODO: handle exception
			//logger.error("error in getEofHeatDtlsById........"+e);
			e.printStackTrace();
		}finally{
			close(session);
		}
		if(lst!=null && !lst.isEmpty()) {
			return lst.get(0);
		}else {
			return new CCMHeatSeqGenModel() ;
		}
		
	}

}
