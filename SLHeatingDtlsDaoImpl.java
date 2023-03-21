package com.smes.trans.dao.impl;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.smes.trans.model.SLHeatingDetailsModel;
import com.smes.trans.model.SLStatusTrackingModel;
import com.smes.util.GenericDaoImpl;

@Repository("SLHeatingDtlsDao")
public class SLHeatingDtlsDaoImpl extends GenericDaoImpl<SLHeatingDetailsModel,Long> implements SLHeatingDtlsDao {
	private static final Logger logger = Logger.getLogger(SLPartsDtlsDaoImpl.class);
	
	@Transactional
	@Override
	public Integer heatingSave(SLHeatingDetailsModel heating,HttpSession sessionUser) {
		// TODO Auto-generated method stub
		logger.info(SLHeatingDtlsDaoImpl.class + " |------Inside----heatingSave----|");
		// create(steelLadleStatus);
		Integer stpartsPk = null;
		Session session = getNewSession();
		try {
			begin(session);
			if(heating.getHeating_si_no()==0) {
			heating.setCreated_date_time(new Date());
			heating.setCreated_by(Integer.parseInt(sessionUser.getAttribute("USER_APP_ID").toString()));
			session.save(heating);
			stpartsPk=1;
			}
			else {				
				SLHeatingDetailsModel list = null;
				String hql = "select a from SLHeatingDetailsModel a where a.heating_si_no ="+heating.getHeating_si_no();
				list = (SLHeatingDetailsModel) session.createQuery(hql).list().get(0);
				heating.setCreated_date_time(list.getCreated_date_time());
				heating.setCreated_by(list.getCreated_by());
				heating.setUpdated_date_time(new Date());
				heating.setUpdated_by(Integer.parseInt(sessionUser.getAttribute("USER_APP_ID").toString()));
				update(heating);
				stpartsPk=2;
			}
			commit(session);
		} catch (Exception e) {
			rollback(session);
			e.printStackTrace();
		} finally {
			close(session);
		}

		return stpartsPk;
	}
	
	@Transactional
	@Override
	public Integer updateLadleStatus(Integer heat_id,Integer stladle_track_id) {
		Integer status=null;
		Session session = getNewSession();
		SLHeatingDetailsModel heatingModel = null;
		SLStatusTrackingModel trackingModel = null;
		String heating_hql = "select a from SLHeatingDetailsModel a where a.heating_si_no ="+heat_id;
		String tracking_hql = "select a from SLStatusTrackingModel a where a.trns_stladle_track_id ="+stladle_track_id;
		try {
		heatingModel = (SLHeatingDetailsModel) session.createQuery(heating_hql).list().get(0);
		trackingModel = (SLStatusTrackingModel) session.createQuery(tracking_hql).list().get(0);
		heatingModel.setHeating_status(1);
		trackingModel.setStatus("READY");
		begin(session);
		update(heatingModel);
		status=1;
		commit(session);
		updateTrack(trackingModel);
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			close(session);
		}
		return status;
	}
	
	public void updateTrack(SLStatusTrackingModel trackingModel) {
		Session session=getNewSession();
		try {
			begin(session);
			session.update(trackingModel);
			commit(session);
		} catch (Exception e) {
			// TODO: handle exception
		}finally {
			close(session);
		}
	}
	
	@Transactional
	public Integer setLadleStatusDown(Integer stladle_track_id,HttpSession sessionUser) {
		Integer status=null;
		Session session = getNewSession();
		SLStatusTrackingModel trackingMdl = null;
		SLStatusTrackingModel duplicateMdl=null;
		String tracking_hql = "select a from SLStatusTrackingModel a where a.trns_stladle_track_id ="+stladle_track_id;
		try {
		trackingMdl = (SLStatusTrackingModel) session.createQuery(tracking_hql).list().get(0);
		duplicateMdl=trackingMdl;
		trackingMdl.setRecord_status(0);
		trackingMdl.setStatus("DOWN");
		updateTrack(trackingMdl);
		createTrack(duplicateMdl,sessionUser);
		status=1;
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			close(session);
		}
		return status;
		
	}
	
	public void createTrack(SLStatusTrackingModel trackingModel,HttpSession sessionUser) {
		Session session=getNewSession();
		try {
			begin(session);
			trackingModel.setTrns_stladle_track_id(null);
			trackingModel.setStatus("NEW");
			trackingModel.setRecord_status(1);
			trackingModel.setSteel_ladle_life(1);
			trackingModel.setCreated_by(Integer.parseInt(sessionUser.getAttribute("USER_APP_ID").toString()));
			trackingModel.setCreated_date_time(new Date());
			session.save(trackingModel);
			commit(session);
		} catch (Exception e) {
			// TODO: handle exception
		}finally {
			close(session);
		}
		
	}
}
