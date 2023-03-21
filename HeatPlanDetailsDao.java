package com.smes.trans.dao.impl;

import java.util.List;

import com.smes.masters.model.PsnGradeMasterModel;
import com.smes.trans.model.HeatPlanDetails;
import com.smes.trans.model.HeatPlanHdrDetails;
import com.smes.trans.model.HeatPlanLinesDetails;
import com.smes.trans.model.IfacesmsLpDetailsModel;
import com.smes.trans.model.SoHeader;

public interface HeatPlanDetailsDao {

	List<HeatPlanHdrDetails> getHeatPlanHeaderDetailsByStatus(String ids);
	
	List<HeatPlanHdrDetails> getAllPrevHeatPlanDetails();
	
	List<HeatPlanHdrDetails> getDaywiseHeatPlanDetails();
	
	List<HeatPlanHdrDetails> getCasterwiseHeatPlanDetails(Integer caster_type);

	List<HeatPlanLinesDetails> getHeatPlanLineDetailsByStatus(Integer heatplanid);
	public String heatPlanHeaderSave(HeatPlanHdrDetails heatPlanHdrDetails); 
	public String heatPlanLinesSave(HeatPlanLinesDetails heatPlanLinesDetails);
	public Integer getMaxHDRHeatPlan_id();

	String heatPlanLinesUpdate(HeatPlanLinesDetails heatPlanLinesDetails);

	String heatPlanHeaderUpdate(HeatPlanHdrDetails heatPlanHdrDetails);

	List<HeatPlanHdrDetails> getHeatPlanDetailsForAttachByStatus(String ids);

	HeatPlanHdrDetails getHeatPlanHeaderDetailsById(Integer heat_plan_id);

	HeatPlanLinesDetails getHeatPlanLineDetailsById(Integer heat_plan_line_id);

	List<HeatPlanLinesDetails> getHeatPlanDetWithRunId(Integer runId, Integer lstatusId);

	HeatPlanLinesDetails getHeatPlanLineObject(Integer heatplan_linepk);
	
	HeatPlanDetails getHeatPlanDetailsById(Integer heat_plan_dtl_id);
	
	public List<HeatPlanHdrDetails> displayHeatPlanDetailReport(Integer caster, String report_date);
	public List<HeatPlanHdrDetails> displayHeatPlanDetailReportView(Integer caster, String report_date);
	public List<String> getlastEOFHeatNo(String date);
	public List<SoHeader> getSalesOrderDetails(PsnGradeMasterModel psnGradeObj, String compo_mtrl);;
	
	List<HeatPlanDetails> getHeatPlanLineByStatus(Integer heat_plan_id);

	String heatPlanDetailsUpdate(HeatPlanDetails heatPlanDetails,IfacesmsLpDetailsModel ifacObj);
}
