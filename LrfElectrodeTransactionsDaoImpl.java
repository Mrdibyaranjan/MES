package com.smes.trans.dao.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

import com.smes.trans.model.LrfElectrodeTransactions;
import com.smes.util.Constants;
import com.smes.util.GenericDaoImpl;
@Repository("lrfElectordeDao")
public class LrfElectrodeTransactionsDaoImpl extends GenericDaoImpl<LrfElectrodeTransactions, Long> implements LrfElectrodeTransactionsDao {

	@Override
	public List<LrfElectrodeTransactions> getAllElectrodeUsageTrnsByUnit(Integer sub_unit_id) {
		List ls =null;
		try {
		String sql="select a from LrfElectrodeTransactions a where subUintId="+sub_unit_id;
		 ls =(List<LrfElectrodeTransactions>) getResultFromNormalQuery(sql);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return ls;
	}

	@Transactional
	@Override
	public LrfElectrodeTransactions getElectrodeStatusByUnitAndLkp(Integer sub_unit_id, Integer electrodeId,Integer trans_si_no) {
		// TODO Auto-generated method stub
		LrfElectrodeTransactions ls=null;
		try {
			String sql="select a from LrfElectrodeTransactions a where subUintId="+sub_unit_id +" and electrodeId ="+electrodeId +" and lrfHeatTransId="+trans_si_no;
			List lst =  getResultFromNormalQuery(sql);
			if(!lst.isEmpty()) {
			ls =(LrfElectrodeTransactions) getResultFromNormalQuery(sql).get(0);
			}
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		return ls;
	}

	@Transactional
	@Override
	public String lrfElectrodeUsageTrnsSaveOrUpdate(LrfElectrodeTransactions obj) {
		String result="";
		Session session = getNewSession();
		try {
			begin(session);
			if(obj.getElectrodeTransId()!=null) {
				session.update(obj);
			}else {
				session.save(obj);
			}
			
			commit(session);
			result = Constants.SAVE;
		} catch (DataIntegrityViolationException e) {
			
			result = Constants.DATA_EXIST;
			rollback(session);
		} catch (ConstraintViolationException e) {
			
			result = Constants.DATA_EXIST;
			rollback(session);
		} catch (Exception e) {
			e.printStackTrace();
			rollback(session);
			
			result = Constants.SAVE_FAIL;
		} finally {
			close(session);
		}
		return result;
	}
	@Transactional
	@Override
	public LrfElectrodeTransactions lrfElectrodeUsageTrnsById(Integer id) {
		LrfElectrodeTransactions ls=null;
		try {
			String sql="select a from LrfElectrodeTransactions a where electrodeTransId="+id ;
			List lst =  getResultFromNormalQuery(sql);
			if(!lst.isEmpty()) {
			ls =(LrfElectrodeTransactions) getResultFromNormalQuery(sql).get(0);
			}
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		return ls;
	}

}
