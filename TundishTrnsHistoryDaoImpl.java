package com.smes.trans.dao.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.smes.masters.model.TundishMaintanaceLogModel;
import com.smes.trans.model.TundishTrnsHistoryModel;
import com.smes.util.Constants;
import com.smes.util.GenericDaoImpl;

@Repository
public class TundishTrnsHistoryDaoImpl extends GenericDaoImpl<TundishTrnsHistoryModel, Long> implements TundishTrnsHistoryDao {
	private static final Logger logger = Logger.getLogger(TundishTrnsHistoryDaoImpl.class);

	
	@Transactional
	public List<TundishTrnsHistoryModel> getTundishStatusHistoryByTundishId(Integer tundishId) {
		Session session=getNewSession();
		List<TundishTrnsHistoryModel> lst=null;
		try {

			String hql = "select a from TundishTrnsHistoryModel a where a.recordStatus =1 and a.tundish_id="+tundishId;
			
			lst=(List<TundishTrnsHistoryModel>) session.createQuery(hql).list();

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
	public List<TundishTrnsHistoryModel> getTundishStatusHistoryByTundishId(Integer tundishId,Integer status) {
		Session session=getNewSession();
		List<TundishTrnsHistoryModel> lst=null;
		try {

			String hql = "select a from TundishTrnsHistoryModel a where  a.tundish_id="+tundishId;
			
			lst=(List<TundishTrnsHistoryModel>) session.createQuery(hql).list();

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
	public List<TundishMaintanaceLogModel> getTundishEventsByTnsId(Integer trnsId) {
		Session session=getNewSession();
		List<TundishMaintanaceLogModel> lst=null;
		try {

			String hql = "select a from TundishMaintanaceLogModel a where a.recordStatus =1 and a.trns_tundish_id="+trnsId;
			
			lst=(List<TundishMaintanaceLogModel>) session.createQuery(hql).list();

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
	public TundishTrnsHistoryModel getTundishById(Integer trnsId) {
		Session session=getNewSession();
		TundishTrnsHistoryModel lst=null;
		try {

			String hql = "select a from TundishTrnsHistoryModel a where  a.trns_id="+trnsId;
			
			lst=(TundishTrnsHistoryModel) session.createQuery(hql).uniqueResult();

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
	public List<TundishMaintanaceLogModel> getTundishEventsByTnsIdAndEventId(Integer trnsId,Integer event_id) {
		Session session=getNewSession();
		List<TundishMaintanaceLogModel> lst=null;
		try {

			String hql = "select a from TundishMaintanaceLogModel a where a.recordStatus =1 and a.trns_tundish_id="+trnsId+" and a.tundishEventId="+event_id;
			
			lst=(List<TundishMaintanaceLogModel>) session.createQuery(hql).list();

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
	public String hstryUpdate(TundishTrnsHistoryModel tundishTrnsHistoryModel) {
		// TODO Auto-generated method stub
		String result = Constants.UPDATE;
			logger.info("inside tundishHistroyUpdate() of "+TundishTrnsHistoryDaoImpl.class);
			update(tundishTrnsHistoryModel);

		return result;
	}
	@Transactional
	@Override
	public String hstrySave(TundishTrnsHistoryModel tundishTrnsHistoryModel) {
		// TODO Auto-generated method stub
		String result = Constants.SAVE;
			logger.info("inside ctundishHistroySave() of "+TundishTrnsHistoryDaoImpl.class);
			create(tundishTrnsHistoryModel);

		return result;
	}
}
