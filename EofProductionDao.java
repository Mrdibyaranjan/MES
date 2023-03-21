package com.smes.trans.dao.impl;

import java.util.Hashtable;
import java.util.List;

import com.smes.reports.model.GraphReportModel;
import com.smes.trans.model.EOFCrewDetails;
import com.smes.trans.model.EofElectrodeUsageModel;
import com.smes.trans.model.EofHeatDetails;
import com.smes.trans.model.HeatConsMaterialsDetails;
import com.smes.trans.model.HeatConsMaterialsDetailsLog;
import com.smes.trans.model.HeatConsScrapMtrlDetails;
import com.smes.trans.model.SteelLadleLifeModel;
import com.smes.util.CommonCombo;
import com.smes.util.DelayEntryDTO;
import com.smes.util.ValidationResultsModel;
import com.smes.admin.model.SeqIdDetails;
import com.smes.reports.model.EOFHeatLogRpt;

public interface EofProductionDao {
	
	String eofHeatProductionSave(EofHeatDetails eofHeatDetails);
	String eofHeatProductionUpdate(EofHeatDetails eofHeatDetails);
	String eofCrewDetailsSave(List<EOFCrewDetails> eofCrewDetails);
	String eofCrewDetailsUpdate(EOFCrewDetails eofCrewDetails);
	List<EOFCrewDetails> getCrewDetailsByHeatNo(String heat_no,Integer unit_id,Integer heat_counter);
	String eofHeatProductionSaveAll(Hashtable<String, Object> mod_obj);
	List<EofHeatDetails> getLRFHeatDetailsByStatus(String cunit,
			String pstatus);
	List<CommonCombo> getConsumedBuckets(Integer heat_transId);
	List<HeatConsScrapMtrlDetails> getConsumedBucketsDtls(Integer heat_transId);
	Integer checkScrapEntryByTransId(Integer transId);
	List<ValidationResultsModel> checkEOFValidations(Integer eof_tranId);
	List<SteelLadleLifeModel> getLaddleLifeDet(Integer steelLadleNo);
	String getSampleNoforHmChem(String heat_id);

	List<EofHeatDetails> getEOFHeatDetailsByDate(String fdate, String tdate, Integer subunit);

	List<HeatConsScrapMtrlDetails> getHeatScrapConsumptionByEofId(
			Integer trns_si_no);

	List<GraphReportModel> getHeatsWithInDates(String fdate, String tdate,
			Integer sub_unit_id);

	List<EofHeatDetails> getEofChemDetails(String heat_id, Integer heat_counter);
	
	List<SeqIdDetails> getSeqIdByQuery(String sub_unit);
	
	EofHeatDetails getEOFHeatDetailsByHeatNo(String heatNo);

	Integer getHMRecptIdByHeatNo(String heatNo);
	
	public EofHeatDetails getEOFPreviousHeatDetailsByHeatNo( Integer trans_si_no);
	public EofHeatDetails getEOFHeatDetailsById( Integer trans_si_no);
	
	public String eofreceivehmStatusUpdate(Long hmRecvID) ;
	
	public DelayEntryDTO getEofDelayEntriesBySubUnitAndHeat(Integer sub_unit_id,Integer trans_heat_id);
	
	List<EOFHeatLogRpt> getEofHeatLogs(String heatId, String LogType);

	String getCrewRoleUser(String heatId, String crewUserRole);
	
	List<EOFHeatLogRpt> getEofHMScrapDayConsumption();	
	
	Integer isMaterialConsumptionPosted();
	
	List<String> getConsumedMaterialList(String sub_unit);
	List<EOFHeatLogRpt> getHeatPostedList(String sub_unit);
	List<String> getHeatwiseActualMaterialConsumed(Integer transSiNo, String sub_unit, String heat_number);
	List<HeatConsMaterialsDetailsLog> getMtrlConsLogByTrnsId(Integer trns_id);
	String saveOrUpdateMatrlCons(List<HeatConsMaterialsDetails> updLi, List<HeatConsMaterialsDetailsLog> logLi);
	String eofElectrodeUsageTrnsSaveOrUpdate(EofElectrodeUsageModel obj);
	EofElectrodeUsageModel eofElectrodeUsageTrnsById(Integer id);
	EofElectrodeUsageModel getElectrodeStatusByUnitAndLkp(Integer sub_unit_id,Integer electrodeId,Integer trans_si_no);
	EofHeatDetails getEofTrnsNo(String heat_no);
}
