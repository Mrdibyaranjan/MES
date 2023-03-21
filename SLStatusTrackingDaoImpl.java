package com.smes.trans.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.smes.masters.model.ContainerMasterModel;
import com.smes.masters.model.SteelLadleMasterModel;
import com.smes.trans.model.SLStatusTrackingModel;
import com.smes.trans.model.SteelLadleTrackingModel;
import com.smes.util.GenericDaoImpl;

@Repository("SLStatusTackingDao")
public class SLStatusTrackingDaoImpl extends GenericDaoImpl<SLStatusTrackingModel, Long>implements SLStatusTrackingDao {

	private static final Logger logger = Logger.getLogger(SLStatusTrackingDao.class);
	
	@Override
	public List<SLStatusTrackingModel> getSteelLadleTracking() {
		

		/*
		 * List<SLStatusTrackingModel> list = new ArrayList<SLStatusTrackingModel>();
		 * String sql = "select a from SLStatusTrackingModel a"; list =
		 * (List<SLStatusTrackingModel>) getResultFromNormalQuery(sql); return list;
		 */
		
		List<SLStatusTrackingModel> list = new ArrayList<SLStatusTrackingModel>();
		SLStatusTrackingModel ladleObj = new SLStatusTrackingModel();
		Session session = getNewSession();
		try {

			String hql = "select a from SLStatusTrackingModel a where a.record_status=1";
			list = session.createQuery(hql).list();
			ladleObj = list.get(0);
			
		} catch (Exception e) {
			logger.error("error in getChangeStatusByStLadle.." + e);
			e.printStackTrace();
		} finally {
			close(session);
		}

		return list;
	}
	
	
}
