package com.smes.trans.service.impl;

import java.io.IOException;
import java.util.Hashtable;
import java.util.List;

import com.smes.trans.model.CCMHeatConsMaterialsDetails;
import com.smes.trans.model.CCMHeatDetailsModel;
import com.smes.trans.model.EofDispatchDetails;
import com.smes.trans.model.EofHeatDetails;
import com.smes.trans.model.HeatChemistryChildDetails;
import com.smes.trans.model.HeatChemistryChildDetailsHistory;
import com.smes.trans.model.HeatChemistryHdrDetails;
import com.smes.trans.model.HeatConsMaterialsDetails;
import com.smes.trans.model.HeatConsScrapMtrlDetails;
import com.smes.trans.model.HeatProcessEventDetails;
import com.smes.trans.model.HeatProcessParameterDetails;
import com.smes.trans.model.IfacesmsLpDetailsModel;
import com.smes.trans.model.LRFHeatDetailsModel;
import com.smes.trans.model.VDHeatDetailsModel;
import com.smes.wrappers.EOFRequestWrapper;

public interface HeatProceeEventService {

	String saveHeatProcessEvent(HeatProcessEventDetails hped);
	List<HeatConsMaterialsDetails> getMtrlDetailsByType(String mtrlType,Integer eof_trns_sno,String psn_no);
	EofHeatDetails getEofHeatDtlsById(Integer eof_trns_sno);
	EofHeatDetails getEOFHeatDtlsFormByID(Integer eof_trns_sno);
	List<CCMHeatConsMaterialsDetails> getMtrlDetailsByCCMType(String mtrlType,Integer eof_trns_sno,String psn_no);
	String ccmheatConsMtrlsSaveOrUpdate(CCMHeatConsMaterialsDetails heatConsMtrls,Integer sub_unit,String eventname,Integer userid);
	CCMHeatDetailsModel getCCMHeatDtlsFormById(Integer eof_trns_sno);
	CCMHeatConsMaterialsDetails getccmHeatConsMtrlsDtlsById(Integer Mtrl_Cons_ID);
	HeatConsMaterialsDetails getHeatConsMtrlsDtlsById(Integer Mtrl_Cons_ID);
	List<HeatConsMaterialsDetails> getHeatConsMtrlsDtlsByTrnsId(Integer trns_id);
	String furnaceAdditionMtrlsSaveOrUpdate(Hashtable<String, Object> mod_obj);
	String heatConsMtrlsSaveOrUpdate(HeatConsMaterialsDetails heatConsMtrls,Integer sub_unit,String eventname,Integer userid);
	String heatConsScrapMtrlsSaveOrUpdate(HeatConsScrapMtrlDetails heatConsScrapMtrls,String cons_date,Integer sub_unit,String eventname,Integer userid);
	
	List<HeatChemistryChildDetails> getChemDtlsByAnalysis(Integer analysis_id,String heat_id, Integer heat_counter,Integer sub_unit_id,String sample_no, Integer psn_id,Integer hm_recv_id,String copy_chem);
	List<HeatChemistryChildDetails> getChemDtlsBySampleHdr(Integer sample_si_Id);
	/*HeatChemistryHdrDetails getChemHdrDtlsByAnalysis(Integer analysis_id,Integer eof_trns_sno);*/
	List<HeatChemistryHdrDetails> getSampleDtlsByAnalysis(Integer sub_unit_id, String heat_id, Integer analysis_id, Integer heat_counter);
	HeatChemistryHdrDetails getHeatChemistryHdrDtlsById(Integer sample_si_Id,Integer sub_unit_id);
	HeatChemistryChildDetails getHeatChemistryChildDtlsById(Integer dtls_si_Id);
	String heatChemistryAllDtlsSaveOrUpdate(Hashtable<String, Object> mod_obj);
	String chemistryDtlsSaveOrUpdate(HeatChemistryHdrDetails heatChemHdrDtls,String eventname,String sample_date,Integer userid);
	String saveFinalResultiface(HeatChemistryHdrDetails chemHdrObj, IfacesmsLpDetailsModel ifacObj);
	String saveFinalResult(HeatChemistryHdrDetails chemHdrObj);
	String approvechem(HeatChemistryHdrDetails chemHdrObj);
	
	List<HeatProcessEventDetails> getLrfHeatProcessEventDtls(String heat_id,Integer heat_cnt,Integer sub_unit_id, String prev_unit, String psn_route);
	List<HeatProcessEventDetails> getHeatProcessEventDtls(Integer eof_trns_sno,String unit);
	List<HeatProcessEventDetails> getHeatProcessEventDtlsByUnit(String heat_id,Integer heat_cnt,Integer sub_unit_id);
	String heatProcessEventSaveOrUpdate(HeatProcessEventDetails heatProcessEventDtls,Integer userid);
	String heatProcessEventSaveOrUpdate(HeatProcessEventDetails heatProcessEventDtls,Integer trns_sno,String unit,
			Integer userid);
	String eofDispatchSave(EOFRequestWrapper reqWrapper, Integer userid);
	
	EofDispatchDetails getEofDispPreviousDtls(Integer subunitid, String lifeParameter );
	
	List<HeatProcessParameterDetails> getHeatProcParamDtls(String heatid, Integer heatcntr, Integer subunitid,String psn_no);
	String processParamSaveOrUpdate(HeatProcessParameterDetails procParamDtls,Integer userid);
	HeatProcessParameterDetails getProcParamDtlsById(Integer proc_param_id);
	HeatProcessParameterDetails getProcParamDtlsByParmaID(Integer param_id,String heat_id,Integer heat_cnt);
	List<EofDispatchDetails> getEOFCampaignLife(Integer subUnit);
	
	Double getTotalFurnaceWeight(String heat_id,Integer sub_unit_id,Integer counter);
	
	void calcBlowStartEndTime(String heat_id,Integer sub_unit_id,Integer counter,Integer uid);
	List<HeatChemistryChildDetails> getHeatChemistryDtls(String heat_id,
			Integer heat_counter, Integer sub_unit_id, Integer hm_analysis_id,
			Integer result_id);
	String getSampleNo(String heat_id, Integer heat_counter, Integer sub_unit_id, Integer analysis_type);
	LRFHeatDetailsModel getLRFHeatDtlsById(Integer trns_sno); 
	CCMHeatDetailsModel getCCMHeatDtlsById(Integer trns_sno); 
    VDHeatDetailsModel getVdHeatDtlsById(Integer eof_trns_sno);
	List<HeatChemistryChildDetails> getChemDtlsBySampleHdrId(Integer sample_si_id, Integer psn_id, Integer analysis_id);
	List<HeatChemistryHdrDetails> getSampleDtlsByAnalysisType(Integer sub_unit_id, String heat_id, Integer heat_counter, String analysis_type);
	
	List<HeatChemistryChildDetails> getChemDtlsByAnalysis(Integer analysis_id, Integer psn_id);
	List<HeatChemistryChildDetails> getChemDtlsBySpectro(Integer analysis_id, String heat_id, Integer heat_counter,
			Integer sub_unit_id, String sample_no, Integer psn_id, Integer hm_recv_id, String copy_chem,String actual_sample) throws IOException;
	List<HeatChemistryChildDetails> getTundishChem(Integer analysis_id, Integer psn_id, String actual_sample,
			String heat_id, Integer heat_counter) throws IOException;
	Integer checkProdConf(String heatno);
}
