package com.smes.trans.dao.impl;

import javax.servlet.http.HttpSession;

import com.smes.trans.model.SLHeatingDetailsModel;

public interface SLHeatingDtlsDao {
	public Integer heatingSave(SLHeatingDetailsModel heating,HttpSession sessionUser);

	public Integer updateLadleStatus(Integer heat_id, Integer stladle_track_id);
	
	public Integer setLadleStatusDown(Integer stladle_track_id,HttpSession sessionUser);
}
