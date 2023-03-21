package com.smes.trans.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smes.trans.dao.impl.TransDelayEntryDtlsDao;
import com.smes.trans.model.TransDelayDetailsModel;
@Transactional
@Service
public class TransDelayEntryDtlsServiceImpl implements TransDelayEntryDtlsService {

	@Autowired
	TransDelayEntryDtlsDao transDelayEntryDtlsDao;
	@Override
	public List<TransDelayDetailsModel> getTransDelayDtlsByDelayDdrId(Integer trans_delay_entry_hdr_id) {
		// TODO Auto-generated method stub
		return transDelayEntryDtlsDao.getTransDelayDtlsByDelayDdrId(trans_delay_entry_hdr_id);
	}

	@Override
	public String saveTransDelayEntryDtls(TransDelayDetailsModel transDelayDetailsModel) {
		// TODO Auto-generated method stub
		return transDelayEntryDtlsDao.saveTransDelayEntryDtls(transDelayDetailsModel);
	}

	@Override
	public String updateTransDelayDtls(TransDelayDetailsModel transDelayDetailsModel) {
		// TODO Auto-generated method stub
		return transDelayEntryDtlsDao.updateTransDelayDtls(transDelayDetailsModel);
	}

}
