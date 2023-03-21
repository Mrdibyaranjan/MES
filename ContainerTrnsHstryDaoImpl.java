package com.smes.trans.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.smes.masters.dao.impl.ContainerMasterDaoImpl;
import com.smes.trans.model.ContainerMasterTrnsHistoryModel;
import com.smes.util.Constants;
import com.smes.util.GenericDaoImpl;

@Repository
public class ContainerTrnsHstryDaoImpl extends GenericDaoImpl<ContainerMasterTrnsHistoryModel, Long>
		implements ContainerTrnsHstryDao {

	private static final Logger logger = Logger.getLogger(ContainerTrnsHstryDaoImpl.class);

	// Save operation
	@Transactional
	@Override
	public String hstrySave(ContainerMasterTrnsHistoryModel containerTrnsHistoryModel) {
		// TODO Auto-generated method stub
		String result = Constants.SAVE;
		logger.info("inside containerTrnsHistoryModel() of " + ContainerTrnsHstryDaoImpl.class);
		create(containerTrnsHistoryModel);

		return result;
	}

	// Update operation
	@Transactional
	@Override
	public String hstryUpdate(ContainerMasterTrnsHistoryModel containerTrnsHistoryModel) {
		// TODO Auto-generated method stub
		String result = Constants.UPDATE;
		logger.info("inside containerTrnsHistoryModel() of " + ContainerTrnsHstryDaoImpl.class);
		update(containerTrnsHistoryModel);

		return result;
	}

	// Find by container id
	@Transactional
	public List<ContainerMasterTrnsHistoryModel> getHstryById(Integer container_id) {
		logger.info("inside getHstryById() of " + ContainerTrnsHstryDaoImpl.class);
		List<ContainerMasterTrnsHistoryModel> list = new ArrayList<ContainerMasterTrnsHistoryModel>();
		String sql = "select a from ContainerMasterTrnsHistoryModel a where a.container_id=" + container_id
				+ "order by a.trns_si_no desc";
		list = (List<ContainerMasterTrnsHistoryModel>) getResultFromNormalQuery(sql);
		return list;
	}

}
