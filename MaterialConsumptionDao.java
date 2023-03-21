package com.smes.trans.dao.impl;

import java.util.List;
import java.util.Map;

import com.smes.trans.model.CCMHeatConsMaterialsDetails;
import com.smes.trans.model.CCMHeatConsMtlsDetailsLog;
import com.smes.trans.model.HeatConsMaterialsDetails;
import com.smes.trans.model.HeatConsMaterialsDetailsLog;
import com.smes.trans.model.HeatConsScrapMtrlDetails;
import com.smes.trans.model.HeatStatusTrackingModel;
import com.smes.trans.model.IntfMaterialConsumptionModel;
import com.smes.trans.model.LRFHeatConsumableLogModel;
import com.smes.trans.model.LRFHeatConsumableModel;
import com.smes.trans.model.LRFHeatDetailsModel;
import com.smes.trans.model.ScrapBucketDetailsLog;
import com.smes.trans.model.VDHeatConsumableModel;
import com.smes.trans.model.VDHeatConsumableModelLog;

public interface MaterialConsumptionDao {
	List<HeatStatusTrackingModel> getProdPostedHeats(String cons_date);
	String saveOrUpdateMtrlConsumptions(Map<String, List> saveUpdateLi);
	String postMtrlConsumptions(List<IntfMaterialConsumptionModel> intfMtrlConsLi, HeatStatusTrackingModel heatTrackingObj);
	HeatConsMaterialsDetails getEOFMtrlConsById(Integer mtrlConsSiNo);
	HeatConsMaterialsDetails getEofMtrlConsByMtrlId(Integer trnsSlNo, Integer mtrlId);
	LRFHeatConsumableModel getLrfMtrlConsByMtrlId(String heatNo, Integer mtrlId);
	VDHeatConsumableModel getVdMtrlConsByMtrlId(String heatNo, Integer mtrlId);
	CCMHeatConsMaterialsDetails getCcmMtrlConsByMtrlId(Integer trnsSlNo, Integer mtrlId);
	List<LRFHeatConsumableModel> getLrfMtrlConsumptions(String heatNo, Integer heatCounter);
	List<VDHeatConsumableModel> getVDMtrlConsumptions(String heatNo);
	List<CCMHeatConsMaterialsDetails> getCCMMtrlConsumptions(Integer trnsSlNo);
	List<LRFHeatDetailsModel> getLrfHeatDetailObj(String heatNo, Integer subUnitId);
	HeatConsScrapMtrlDetails getHeatScrapConsumptionHMRecvId(Integer trns_si_no, Integer hmRecvId);
	
	ScrapBucketDetailsLog getScrapBucketDetLogByConsId(Integer mtrlConsId);
	HeatConsMaterialsDetailsLog getEofHeatConsLogByConsId(Integer mtrlConsId);
	LRFHeatConsumableLogModel getLrfHeatConsLogByConsId(Integer mtrlConsId);
	VDHeatConsumableModelLog getVdHeatConsLogByConsId(Integer mtrlConsId);
	CCMHeatConsMtlsDetailsLog getCcmHeatConsLogByConsId(Integer mtrlConsId);
	List<LRFHeatConsumableModel> getLrfMtrlConsByCtrlMtrlId(String heatNo, Integer heatcounter, Integer mtrlId);
	LRFHeatConsumableModel getLrfHeatConsByConsId(Integer mtrlConsId);
}
