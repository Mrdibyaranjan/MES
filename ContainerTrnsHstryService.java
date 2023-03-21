package com.smes.trans.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smes.trans.dao.impl.ContainerTrnsHstryDao;
import com.smes.trans.model.ContainerMasterTrnsHistoryModel;

@Service
public class ContainerTrnsHstryService {

	@Autowired
	ContainerTrnsHstryDao containerMasterTrnsDao;
	
	//Save operation
	public String saveContainerHistry(ContainerMasterTrnsHistoryModel model) {
		String result = "";
		result = containerMasterTrnsDao.hstrySave(model);
		return result;
	}

	//Update operation
	public String UpdateHistry(ContainerMasterTrnsHistoryModel model) {
		String result = "";
		result = containerMasterTrnsDao.hstryUpdate(model);
		return result;
	}

	//Get details by container id.....
	public List<ContainerMasterTrnsHistoryModel> getHstryById(Integer container_id) {
		return containerMasterTrnsDao.getHstryById(container_id);
	}

}
