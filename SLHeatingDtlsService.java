package com.smes.trans.service.impl;

import javax.servlet.http.HttpSession;

import com.smes.trans.model.SLHeatingDetailsModel;

public interface SLHeatingDtlsService {
	public Integer heatingSave(SLHeatingDetailsModel slHeatingMdl,HttpSession sessionUser);
	public Integer updateLadleStatus(Integer heat_id, Integer stladle_track_id);
	public Integer setLadleStatusDown(Integer stladle_track_id,HttpSession sessionUser);
}
