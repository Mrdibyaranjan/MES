package com.smes.trans.dao.impl;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.smes.trans.model.SLHeatingDetailsModel;
import com.smes.trans.model.SLPartsDetailsModel;

public interface SLPartsDtlsDao {
public Integer saveParts(List<SLPartsDetailsModel> parts,HttpSession sessionUser,Integer ladle_man,Integer ladle_man_asst);
public List<SLPartsDetailsModel> getLadleParts(Integer ladle_trns_si_no, Integer ladle_life);
public Integer updateForSLStatus(int ladleId,String curr_unit);
public List<SLHeatingDetailsModel> getHeatingDtls(Integer ladle_trns_si_no, Integer ladle_life);
}
