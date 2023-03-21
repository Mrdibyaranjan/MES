package com.smes.trans.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.smes.trans.model.SLHeatingDetailsModel;
import com.smes.trans.model.SLPartsDetailsModel;
import com.smes.trans.model.SLShiftDetailsModel;
import com.smes.trans.model.SLStatusTrackingModel;
import com.smes.trans.service.impl.SLShiftDtlsService;
import com.smes.util.GenericDaoImpl;

@Repository("SLPartsMapDao")
public class SLPartsDtlsDaoImpl extends GenericDaoImpl<SLPartsDetailsModel, Long> implements SLPartsDtlsDao {
	private static final Logger logger = Logger.getLogger(SLPartsDtlsDaoImpl.class);
	@Autowired
	SLShiftDtlsService shftLadleDtlsMap;

	@Transactional
	@Override
	public Integer saveParts(List<SLPartsDetailsModel> parts, HttpSession sessionUser, Integer ladle_man,
			Integer ladle_man_asst) {
		// TODO Auto-generated method stub
		logger.info(SLPartsDtlsDaoImpl.class);
		// create(steelLadleStatus);
		Integer stpartsPk = null;
		Session session = getNewSession();

		try {
			begin(session);

			// updating shift si no key
			SLShiftDetailsModel shiftdtls = new SLShiftDetailsModel();
			shiftdtls = shftLadleDtlsMap.getShiftDtlsById(parts.get(0).getShift_dtl_id());
			shiftdtls.setParts_ladle_man(ladle_man);
			shiftdtls.setParts_ladle_man_asst(ladle_man_asst);
			session.update(shiftdtls);

			int i = 0;
			for (SLPartsDetailsModel slPartsMapModel : parts) {
				
				List<SLPartsDetailsModel> avbleparts = new ArrayList<SLPartsDetailsModel>();
				String checkavbleparts = "select parts from SLPartsDetailsModel parts where parts.parts_si_no ="
						+ slPartsMapModel.getParts_si_no() + " and parts.part_id="
						+ slPartsMapModel.getPart_id()+ " and parts.parts_status=1";
				avbleparts = (List<SLPartsDetailsModel>) session.createQuery(checkavbleparts).list();
				
				if(avbleparts.size()>0) {
					SLPartsDetailsModel slObj = avbleparts.get(0);
				
				if (slPartsMapModel.getParts_si_no() != null && slPartsMapModel.getPart_id().equals(slObj.getPart_id())) {
					slPartsMapModel.setUpdated_date_time(new Date());
					slPartsMapModel.setUpdated_by(Integer.parseInt(sessionUser.getAttribute("USER_APP_ID").toString()));
					//slPartsMapModel.setPart_life(slPartsMapModel.getPart_life() + 1);
					update(slPartsMapModel);
					stpartsPk=1;
				} else if(!slPartsMapModel.getPart_id().equals(slObj.getPart_id())) {
					slPartsMapModel.setCreated_date_time(new Date());
					slPartsMapModel.setParts_si_no(null);
					slPartsMapModel.setCreated_by(Integer.parseInt(sessionUser.getAttribute("USER_APP_ID").toString()));
					slPartsMapModel.setPart_life(1);
					//slPartsMapModel.setParts_status(1);
					create(slPartsMapModel);
					stpartsPk=1;
				}else {
					stpartsPk=2;	
				}
				}
				else {
					slPartsMapModel.setCreated_date_time(new Date());
					slPartsMapModel.setParts_si_no(null);
					slPartsMapModel.setCreated_by(Integer.parseInt(sessionUser.getAttribute("USER_APP_ID").toString()));
					slPartsMapModel.setPart_life(1);
					//slPartsMapModel.setParts_status(1);
					create(slPartsMapModel);
					stpartsPk=1;
				}
			}
			commit(session);
			
		} catch (Exception e) {
			// rollback(session);
			e.printStackTrace();
		} finally {
			close(session);
		}

		return stpartsPk;
	}

	@Override
	public List<SLPartsDetailsModel> getLadleParts(Integer ladle_trns_si_no, Integer ladle_life) {
		// TODO Auto-generated method stub

		Session session = getNewSession();
		List<SLPartsDetailsModel> list = null;
		try {

			String hql = "select a from SLPartsDetailsModel a where a.trns_stladle_track_id =" + ladle_trns_si_no
					+ "and a.trns_stladle_life=" + ladle_life + "and a.parts_status=1";
			list = session.createQuery(hql).list();

		} catch (Exception e) {
			// TODO: handle exception
			logger.error("error in getAvaiableLadleParts........" + e);
			e.printStackTrace();
		} finally {
			close(session);
		}

		return list;
	}
	
	@Override
	public List<SLHeatingDetailsModel> getHeatingDtls(Integer ladle_trns_si_no, Integer ladle_life) {
		// TODO Auto-generated method stub

		Session session = getNewSession();
		List<SLHeatingDetailsModel> list = null;
		try {

			String hql = "select a from SLHeatingDetailsModel a where a.trns_stladle_track_id =" + ladle_trns_si_no
					+ "and a.trns_stladle_life=" + ladle_life;
			list = session.createQuery(hql).list();

		} catch (Exception e) {
			// TODO: handle exception
			logger.error("error in getHeatingDetails........" + e);
			e.printStackTrace();
		} finally {
			close(session);
		}

		return list;
	}

	@Override
	public Integer updateForSLStatus(int ladleId, String curr_unit) {
		// TODO Auto-generated method stub
		logger.info(SLPartsDtlsDaoImpl.class + " .....IN SIde. updateForSLStatus");
		Session session = getNewSession();
		Integer ack = null;
		try {
			SLStatusTrackingModel obj = new SLStatusTrackingModel();
			List<SLStatusTrackingModel> lsobj = session
					.createQuery("select sl from SLStatusTrackingModel sl where sl.steel_ladle_si_no=" + ladleId
							+ " and sl.record_status=1")
					.list();
			obj = lsobj.get(0);
			obj.setStatus("IN_" + curr_unit);

			updateSLTrack(obj);

		} catch (Exception e) {
			// TODO: handle exception
			logger.error("error in  updateForSLStatus........" + e);
			e.printStackTrace();
		} finally {
			close(session);
		}
		return ack;
	}

	public Integer updateSLTrack(SLStatusTrackingModel mdObj) {
		Session session = getNewSession();
		begin(session);
		session.update(mdObj);
		// session.createSQLQuery("update TRNS_SL_TRACKING a set
		// a.STATUS='"+mdObj.getStatus()+"' where
		// a.TRNS_STLADLE_TRACK_ID="+mdObj.getTrns_stladle_track_id());
		commit(session);
		close(session);
		return null;
	}

}
