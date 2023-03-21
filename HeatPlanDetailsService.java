package com.smes.trans.service.impl;

import java.util.List;

import com.smes.trans.model.HeatPlanDetails;
import com.smes.trans.model.HeatPlanHdrDetails;
import com.smes.trans.model.HeatPlanLinesDetails;
import com.smes.trans.model.IfacesmsLpDetailsModel;
import com.smes.trans.model.SoHeader;

public interface HeatPlanDetailsService {

	List<HeatPlanHdrDetails> getHeatPlanHeaderDetailsByStatus(String ids);

	List<HeatPlanLinesDetails> getHeatPlanLineDetailsByStatus(Integer heatplanid);
	
	List<HeatPlanHdrDetails> getAllPrevHeatPlanDetails();
	
	List<HeatPlanHdrDetails> getDaywiseHeatPlanDetails();
	
	List<HeatPlanHdrDetails> getCasterwiseHeatPlanDetails(Integer caster_type);
	
	public String heatPlanHeaderSave(HeatPlanHdrDetails heatPlanHdrDetails);
	public String heatPlanLinesSave(HeatPlanLinesDetails heatPlanLinesDetails);
	public Integer getMaxHDRHeatPlan_id();

	String heatPlanLinesUpdate(HeatPlanLinesDetails heatPlanLinesDetails);

	String heatPlanHeaderUpdate(HeatPlanHdrDetails heatPlanHdrDetails);

	List<HeatPlanHdrDetails> getHeatPlanDetailsForAttachByStatus(String ids);

	HeatPlanHdrDetails getHeatPlanHeaderDetailsById(Integer heat_plan_id);

	HeatPlanLinesDetails getHeatPlanLineDetailsById(
			Integer heat_plan_line_id);

	HeatPlanDetails getHeatPlanDetailsById(Integer heat_plan_dtl_id);
			
	List<HeatPlanLinesDetails> getHeatPlanDetWithRunId(Integer runId, Integer linestatusid);

	HeatPlanLinesDetails getHeatPlanLineObject(Integer integer);
	
	public List<HeatPlanHdrDetails> displayHeatPlanDetailReport(Integer caster, String report_date);
	public List<HeatPlanHdrDetails> displayHeatPlanDetailReportView(Integer caster, String report_date);
	public List<String> getlastEOFHeatNo(String date);
	public List<SoHeader> getSalesOrderDetails(Integer psn_hdr_id, String section_type);
	
	List<HeatPlanDetails> getHeatPlanLineByStatus(Integer heatplanid);
	
    String heatPlanDetailsUpdate(HeatPlanDetails heatPlanDetails,IfacesmsLpDetailsModel ifacObj);
}
