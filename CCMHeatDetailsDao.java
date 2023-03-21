package com.smes.trans.dao.impl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.smes.trans.model.CCMHeatConsMaterialsDetails;
import com.smes.trans.model.CCMHeatDetailsModel;
import com.smes.util.CommonCombo;

public interface CCMHeatDetailsDao {

	public List<CCMHeatDetailsModel> getCCMheats(Integer sub_unit_id);
	
	public CCMHeatDetailsModel getCCMheatByid(Integer trns_sl_no);
	public List<CCMHeatConsMaterialsDetails> getCCMByProducts(Integer trns_sl_no, String mtrlType);
	public Integer sendChemToSap(String heatno,String heatcounter) throws SQLException, IOException;
	public List<CommonCombo> getCCMCompletedHeats(Integer sub_unit_id);
}
