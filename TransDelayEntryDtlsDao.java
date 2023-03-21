package com.smes.trans.dao.impl;

import java.util.List;

import com.smes.trans.model.TransDelayDetailsModel;

public interface TransDelayEntryDtlsDao {

	public List<TransDelayDetailsModel> getTransDelayDtlsByDelayDdrId(Integer trans_delay_entry_hdr_id);
	
    public String saveTransDelayEntryDtls(TransDelayDetailsModel transDelayDetailsModel);
	
	public String updateTransDelayDtls(TransDelayDetailsModel transDelayDetailsModel);
}
