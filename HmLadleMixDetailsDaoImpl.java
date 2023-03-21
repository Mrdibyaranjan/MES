package com.smes.trans.dao.impl;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.smes.trans.model.HotMetalLadleMixDetails;
import com.smes.util.Constants;
import com.smes.util.GenericDaoImpl;

@Repository("hmLadleMixDao")
public class HmLadleMixDetailsDaoImpl extends GenericDaoImpl<HotMetalLadleMixDetails, Long> implements HmLadleMixDetailsDao {

	private static final Logger logger = Logger.getLogger(HmLadleMixDetailsDaoImpl.class);
	
	
	@Override
	public String hotMetalLadleMixDetailsSave(HotMetalLadleMixDetails hmLadleMixDtls) {
		// TODO Auto-generated method stub
		String result = Constants.SAVE;
		try {
			logger.info(HmLadleMixDetailsDaoImpl.class+" Inside HmLadleMixDetailsSave ");
			create(hmLadleMixDtls);

		} catch (Exception e) {
			logger.error(HmLadleMixDetailsDaoImpl.class+" Inside HmLadleMixDetailsSave Exception..", e);
			result = Constants.SAVE_FAIL;
		}

		return result;
	}

}
