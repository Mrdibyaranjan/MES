package com.smes.trans.service.impl;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.smes.trans.model.SLHeatingDetailsModel;
import com.smes.trans.model.SLPartsDetailsModel;

public interface SLPartsDtlsService {
public Integer saveParts(List<SLPartsDetailsModel> parts,HttpSession sessionUser,Integer ladle_man,Integer ladle_man_asst);
public List<SLPartsDetailsModel> getLadleParts(Integer ladle_trns_si_no, Integer ladle_life);
public List<SLHeatingDetailsModel> getHeatingDtls(Integer ladle_trns_si_no, Integer ladle_life);
}
