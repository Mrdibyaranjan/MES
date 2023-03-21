package com.smes.trans.dao.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.smes.masters.dao.impl.SLMasterDaoImpl;
import com.smes.trans.model.SLShiftDetailsModel;
import com.smes.util.Constants;
import com.smes.util.GenericDaoImpl;

@Repository("SLShiftDtlsDao")
public class SLShiftDtlsDaoImpl extends GenericDaoImpl<SLShiftDetailsModel, Long> implements SLShiftDtlsDao {
	private static final Logger logger = Logger.getLogger(SLShiftDtlsDaoImpl.class);
	
	//saving....
	@Override
	public Integer saveSteelLadle(SLShiftDetailsModel steelLadleStatus) {
		// TODO Auto-generated method stub
			logger.info(SLMasterDaoImpl.class+"saveSteelLadle()");
			//create(steelLadleStatus);
			Integer stLadlePk = null;
			Session session = getNewSession();
			try{
			begin(session);
			 stLadlePk=(Integer) session.save(steelLadleStatus);
			 commit(session);
			}catch(Exception e){
				rollback(session);
				e.printStackTrace();
			}
			finally{
				close(session);
			}
		return stLadlePk;
	}
	
	//updating..
	@Override
	public String updateSteelLadle(SLShiftDetailsModel steelLadleStatus) {
		// TODO Auto-generated method stub
		String result = Constants.UPDATE;
			logger.info(SLMasterDaoImpl.class);
			update(steelLadleStatus);

		return result;
	}
	
	//check shift against the date
	public String checkShiftDtls(SLShiftDetailsModel steelLadleDtls) {
		logger.info(SLShiftDtlsDaoImpl.class+"checkShiftDtls()");
		Session session = getNewSession();
		List<SLShiftDetailsModel> shiftdtls=new ArrayList<>();
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date onlydate=steelLadleDtls.getShift_date_time();
		String test=formatter.format(onlydate);
		String hql = "select a from SLShiftDetailsModel a WHERE TRUNC(a.shift_date_time)=to_date('"+test+"','DD/MM/YYYY') and a.shift_type='"+steelLadleDtls.getShift_type()+"'";
		shiftdtls = session.createQuery(hql).list();
		close(session);
		if(shiftdtls.size()>=1) {
			return "Shift Details Already Exists!!";
		}
		else {
			
		}
		
		return "";
	}
	
	//shift auto generate
		public SLShiftDetailsModel shiftAutofill(SLShiftDetailsModel steelLadleDtls) {
			logger.info(SLShiftDtlsDaoImpl.class+"shiftAutofill()");
			Session session = getNewSession();
			List<SLShiftDetailsModel> shiftdtls=new ArrayList<>();
			SLShiftDetailsModel current_shift=new SLShiftDetailsModel();
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			Date onlydate=steelLadleDtls.getShift_date_time();
			String test=formatter.format(onlydate);
			String hql = "select a from SLShiftDetailsModel a WHERE TRUNC(a.shift_date_time)=to_date('"+test+"','DD/MM/YYYY') and a.shift_type='"+steelLadleDtls.getShift_type()+"' order by a.created_date_time desc";
			shiftdtls = session.createQuery(hql).list();
			close(session);
			if(shiftdtls.size()>0) {
			current_shift=shiftdtls.get(0);
			return current_shift;
			
			}
			else {
				return null;
			}
			
		}

		@Override
		public SLShiftDetailsModel getShiftDtlsById(Integer shiftId) {
			// TODO Auto-generated method stub
			Session session = getNewSession();
			SLShiftDetailsModel current_shift=new SLShiftDetailsModel();
			String hql = "select a from SLShiftDetailsModel a WHERE a.trns_shift_si_no="+shiftId;
			try {
			current_shift = (SLShiftDetailsModel) session.createQuery(hql).list().get(0);
			}catch (Exception e) {
				// TODO: handle exception
			}finally {
				close(session);
			}
			
			return current_shift;
		}
	
}
