package com.smes.trans.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Repository;


import com.smes.trans.model.CCMHeatProcessParameterDetails;
import com.smes.util.Constants;
import com.smes.util.GenericDaoImpl;

@Repository
public class CCMHeatProcessParameterDetailsDaoImpl extends GenericDaoImpl<CCMHeatProcessParameterDetails,Long> implements CCMHeatProcessParameterDetailsDao{

	private static final Logger logger = Logger.getLogger(CCMHeatProcessParameterDetailsDaoImpl.class);
	@Transactional
	public List<CCMHeatProcessParameterDetails> getProcessParamByHeatNo(String heatNo) {
		String sql="select a from CCMHeatProcessParameterDetails a where a.heat_id='"+heatNo+"'";
		List ls=new ArrayList<>();
		try{
			
			 ls =(List<CCMHeatProcessParameterDetails>) getResultFromNormalQuery(sql);
		}catch (Exception e) {
			// TODO: handle exception
		}
		return ls;
	}
	@Transactional
	@Override
	public List<CCMHeatProcessParameterDetails> getProcessParamByHeatNoAndParm(String heatNo, Integer heat_counter, Integer param_id) {
		String sql="select a from CCMHeatProcessParameterDetails a where a.heat_id='"+heatNo +"' and a.param_id="+param_id;
		List ls=new ArrayList<>();
		try{
			 ls =(List<CCMHeatProcessParameterDetails>) getResultFromNormalQuery(sql);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return ls;
	}
	@Transactional
	@Override
	public String processParamsUpdate(
			CCMHeatProcessParameterDetails ppmMaster) {
		// TODO Auto-generated method stub
		String result = Constants.UPDATE;
		try {
			logger.info(CCMHeatProcessParameterDetailsDaoImpl.class+" inside processParamsMasterUpdate");
			update(ppmMaster);

		} catch (HibernateException e) {
			logger.error(CCMHeatProcessParameterDetailsDaoImpl.class+" inside processParamsMasterUpdate Exception..", e);
			result =Constants.UPDATE_FAIL;
		}

		return result;
	}
	
	@Transactional
	@Override
	public String processParamsSave(CCMHeatProcessParameterDetails ppmMaster) {
		String result = Constants.SAVE;
		try {
			logger.info(CCMHeatProcessParameterDetailsDaoImpl.class+" inside processParamsMasterSave");
			create(ppmMaster);

		} catch (HibernateException e) {
			logger.error(CCMHeatProcessParameterDetailsDaoImpl.class+" inside processParamsMasterSave Exception..", e);
			result =Constants.SAVE_FAIL;
		}

		return result;
	}
	
	

}
