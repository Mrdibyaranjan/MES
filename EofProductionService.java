package com.smes.trans.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Map;


import com.smes.reports.model.GraphReportModel;
import com.smes.trans.model.EOFCrewDetails;
import com.smes.trans.model.EofElectrodeUsageModel;
import com.smes.trans.model.EofHeatDetails;
import com.smes.trans.model.HeatConsMaterialsDetails;
import com.smes.trans.model.HeatConsMaterialsDetailsLog;
import com.smes.trans.model.HeatConsScrapMtrlDetails;
import com.smes.trans.model.SteelLadleLifeModel;
import com.smes.trans.model.TransDelayEntryHeader;
import com.smes.util.CommonCombo;
import com.smes.util.DelayEntryDTO;
import com.smes.util.ValidationResultsModel;
import com.smes.admin.model.SeqIdDetails;
import com.smes.reports.model.EOFHeatLogRpt;

public interface EofProductionService {

	String eofHeatProductionSave(EofHeatDetails eofHeatDetails);
	
	String eofHeatProductionUpdate(EofHeatDetails eofHeatDetails);
	
	String eofCrewDetailsUpdate(EOFCrewDetails eofCrewDetails);
	
	List<EOFCrewDetails> getCrewDetailsByHeatNo(String heat_no,Integer unit_id,Integer heat_counter);

	String eofHeatProductionSaveAll(
			EofHeatDetails eofHeatDetails,String production_date,String hm_charge_at,String scrap_charge_at,
			 String tap_start_at, String tap_close_at,Integer uid, Integer hm_ldl_version, Integer plan_line_version);

	String eofCrewDetailsSave(EOFCrewDetails eofCrewDetails,
			String user_role_id, Integer uid);

	HashSet<EofHeatDetails> getLRFHeatDetailsByStatus(String cunit,
			String pstatus);

	List<CommonCombo> getConsumedBuckets(Integer heat_transId);

	List<HeatConsScrapMtrlDetails> getConsumedBucketsDtls(Integer heat_transId);

	boolean checkScrapEntryByTransId(Integer transId);

	List<ValidationResultsModel> checkEOFValidations(Integer eof_tranId);
	
	List<SteelLadleLifeModel> getLaddleLifeDet(Integer steelLadleNo);
	
	String getSampleNoforHmChem(String heat_id);

	List<EofHeatDetails> getEOFHeatDetailsByDate(String fdate, String tdate, Integer subunit);

	Integer updatePartLifeBySiNo(Integer ladleLifeSiNo, Integer life_cntr, Integer uid);

	List<HeatConsScrapMtrlDetails> getHeatScrapConsumptionByEofId(
			Integer trns_si_no);

	List<GraphReportModel> getHeatsWithInDates(String fdate, String tdate,
			Integer sub_unit_id);

	List<SeqIdDetails> getSeqIdByQuery(String sub_unit);
	
	EofHeatDetails getEOFHeatDetailsByHeatNo(String heatNo);

	Integer getHMRecptIdByHeatNo(String heatNo);
	
	List<DelayEntryDTO> getEofDelayEntriesBySubUnitAndHeat(Integer sub_unit_id,Integer trans_heat_id);
	
	List<EOFHeatLogRpt> getEofHeatLogs(String heatId, String LogType);
	
	public List<TransDelayEntryHeader> getDelayDetailsHdrWithSubUnitAndHeat(Integer sub_unit_id, Integer trans_heat_id);
	
	public String eofreceivehmStatusUpdate(Long hmRecvID);

	String getCrewRoleUser(String heatId, String crewUserRole);
	
	EofHeatDetails getById(Integer trans_Si_No);
	
	Map<String,Double> getLiquidSteelValue(Integer trans_Si_No);
	public Integer isMaterialConsumptionPosted();
	List<EOFHeatLogRpt> getEofHMScrapDayConsumption();
	List<String> getEOFLRFMaterialConsumptionHdr(String sub_unit);
	List<Map<String, Object>> getEOFLRFMaterialConsumptionDtls(String sub_unit);
	List<HeatConsMaterialsDetailsLog> getMtrlConsLogByTrnsId(Integer trns_id);
	String saveOrUpdateMatrlCons(List<HeatConsMaterialsDetails> updLi, List<HeatConsMaterialsDetailsLog> logLi);
	String eofElectrodeUsageTrnsSaveOrUpdate(EofElectrodeUsageModel obj);
	EofElectrodeUsageModel eofElectrodeUsageTrnsById(Integer id);
	public List<EofElectrodeUsageModel> getAllElectrodeUsageTrnsByUnit(Integer sub_unit_id, Integer trans_no);

	EofHeatDetails getEofTrnsNo(String heat_no);
}
