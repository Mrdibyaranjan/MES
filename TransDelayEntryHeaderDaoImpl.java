package com.smes.trans.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.smes.trans.model.TransDelayEntryHeader;
import com.smes.util.Constants;
import com.smes.util.GenericDaoImpl;
@Repository("transDelayDao")
public class TransDelayEntryHeaderDaoImpl extends GenericDaoImpl<TransDelayEntryHeader, Long> implements TransDelayEntryHeaderDao {

	private static final Logger logger = Logger.getLogger(TransDelayEntryHeaderDaoImpl.class);
	@Override
	public TransDelayEntryHeader getTransDelayHeaderByHeatAndActivity(Integer activityId, Integer heatId) {
		// TODO Auto-generated method stub
		Session session=getNewSession();
		TransDelayEntryHeader transHdr=new TransDelayEntryHeader();
		try {
			String hql = "select a from TransDelayEntryHeader a where a.trans_heat_id="+heatId+" and activity_delay_id = "+activityId;
			Query query=session.createQuery(hql);
			transHdr=(TransDelayEntryHeader) query.uniqueResult();
		} catch (Exception e) {
			// TODO: handle exception
			//logger.error("error in getEOFHeatDetailsByHeatNo........"+e);
			e.printStackTrace();
		}
		finally{
			close(session);
		}
		return transHdr;
	}

	@Override
	public String saveTransDelayEntryHeader(TransDelayEntryHeader transDelayEntryHeader) {
		String result = Constants.SAVE;
		try {
			logger.info(EofProductionDaoImpl.class+" Inside eofHeatProductionSave ");
			create(transDelayEntryHeader);

		} catch (Exception e) {
			logger.error(EofProductionDaoImpl.class+" Inside eofHeatProductionSave Exception..", e);
			result = Constants.SAVE_FAIL;
		}

		return result;
	}

	@Override
	public String updateTransDelayEntryHeader(TransDelayEntryHeader transDelayEntryHeader) {
		String result = Constants.UPDATE;
		try {
			logger.info(EofProductionDaoImpl.class+" Inside eofHeatProductionUpdate ");
			update(transDelayEntryHeader);

		} catch (Exception e) {
			logger.error(EofProductionDaoImpl.class+" Inside eofHeatProductionUpdate Exception..", e);
			result = Constants.UPDATE_FAIL;
		}

		return result;
	}

	@Override
	public TransDelayEntryHeader getTransDelayHeaderById(Integer delayHdrId) {
		// TODO Auto-generated method stub
		logger.info("inside .. getTransDelayHeaderById.....");
		TransDelayEntryHeader hdrObj=new TransDelayEntryHeader();
		Session session=getNewSession();
		try {
			hdrObj=(TransDelayEntryHeader) session.get(TransDelayEntryHeader.class, delayHdrId);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("error in getTransDelayHeaderById........"+e);
		}
		finally{
			close(session);
		}
		return hdrObj;
	}

	@Override
	public List<TransDelayEntryHeader> getTransDelayHeaderByTransId(Integer trnsId, Integer sub_unit_id) {
		// TODO Auto-generated method stub
		logger.info("inside .. getTransDelayHeaderByTransId.....");
		List<TransDelayEntryHeader> list=new ArrayList<TransDelayEntryHeader>();
		Session session=getNewSession();
		try {
			String hql = "select a from TransDelayEntryHeader a where a.trans_heat_id="+trnsId+" and a.activity_delay_id in "
					+ " (select activity_delay_id from ActivityDelayMasterModel where sub_unit_id ="+sub_unit_id+" and attribute2='SEQ')";
			list=(List<TransDelayEntryHeader>) session.createQuery(hql).list();
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("error in getTransDelayHeaderByTransId........"+e);
		}
		finally{
			close(session);
		}
		return list;
	}
}
