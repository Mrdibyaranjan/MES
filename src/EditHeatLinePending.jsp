<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<script src="${pageContext.request.contextPath}/js/smesHeatPlan.js"></script>

		<table id="t5_prev_heatplan_tbl_id"
		title="Pending Heat Plan Details"
		toolbar="#t5_heat_tbl_pln_toolbar_div_id"
		class="easyui-datagrid" style="width: 100%; height: 250px;"
		url="./HeatPlan/getAllPrevHeatPlanDetails"
		method="get" iconCls='icon-ok' pagination="true" maximizable="true"
				resizable="true" remoteSort="false" pageSize="10" rownumbers="true"
				singleSelect="true">
		<thead>
			<tr>
			<th field="heat_plan_id"  rowspan="total" sortable="true" align="right" width="75"><b>Plan No.</b></th>
				<th field="prod_start_date" sortable="true" rowspan="total"
					formatter="(function(v,r,i){return formatDateTime('prod_start_date',v,r,i)})"
					width="100"><b><spring:message
							code="label.t2.prodStartDate" /></b></th>
				<th field="plan_sequence"  rowspan="total" sortable="true" align="right" width="70"><b><spring:message
							code="label.t2.sequence" /></b></th>
				<th field="caster_type" rowspan="total" sortable="true" width="60"
					formatter="(function(v,r,i){return formatColumnData('lookupCasterType.lookup_value',v,r,i);})"><b><spring:message
							code="label.t2.caster" /></b></th>
				<th field="section_type" rowspan="total" sortable="true" width="90"
					formatter="(function(v,r,i){return formatColumnData('lookupSectionType.lookup_value',v,r,i);})"><b><spring:message
							code="label.t2.secType" /></b></th>
				<th field="section_planned" rowspan="total" sortable="true" width="120"
					formatter="(function(v,r,i){return formatColumnData('smsCapabilityMstrModel.lookupOutputSection.lookup_value',v,r,i);})"><b><spring:message
							code="label.t2.section" /></b></th>
						
				<th field="psn_no" sortable="true" width="150"><b><spring:message
							code="label.r1.heatPlanRptPsn" /></b></th>	
						
		       <th field="grade" sortable="true" width="70" ><b><spring:message
							code="lable.t2.grade" /></b></th>	
							
			   <th field="sales_Order" sortable="true" width="140"><b><spring:message
							code="lable.t2.Salesorder" /></b></th>							
				
				<th field="no_of_heats_planned" rowspan="total" sortable="true" align="right"
					width="100"><b><spring:message
							code="label.t2.noOfheatPlanned" /></b></th>
				<th field="pending_heats" rowspan="total" sortable="true" align="right"
					width="120"><b><spring:message
							code="label.t2.pendingHeats" /></b></th>
							
			</tr>
		</thead>
	</table>
	<div id="t5_heat_tbl_pln_toolbar_div_id">
	<a href="javascript:void(0)" id="t2_close" class="easyui-linkbutton" iconCls="icon-edit"  onclick="editT5LinePlan()" style="width:130px">Edit Heat Plan</a> 
	         
</div>

<form id="t23_heatplan_form_id" method="post" novalidate> 
<input name="heat_plan_id" type="hidden" id="t2_heat_plan_id">
<input name="heat_plan_dtl_id" type="hidden" id="t2_heat_plan_dtl_id">
<input name="status" type="hidden" id="t2_status">
<!-- <table id="t5_heat_line_tbl_id" title="Heat Plan Line Details"
			class="easyui-datagrid" style="width: 100%; height: 250px;"
			toolbar="#t5_heatplan_form_btn_div_id" 
			fitColumns="true" singleSelect="true"
			method="get"   iconCls='icon-ok' pagination="true" maximizable="true"  resizable="true" remoteSort="false" pageSize="20"
            rownumbers="true"  singleSelect="false" checkOnSelect="false">  -->  
          
      
          <table id="t5_heat_line_tbl_id" title="Heat Plan Line Details" toolbar="#t5_heatplan_form_btn_div_id"  class="easyui-datagrid" style="width:100%;height: 300px;"
           iconCls='icon-ok' pagination="true" maximizable="true"  resizable="true" remoteSort="false" pageSize="20"
           fitColumns="true" rownumbers="true"  singleSelect="false" checkOnSelect="false">      
			<thead >
				<tr >
				    <th field="heat_plan_dtl_id" id="t2_heat_plan_Id"hidden="true" width="120"><b>Heat Plan Dtl Id</b></th>
				    <th field="sl_no" sortable="false"  checkbox="true" width="40"></th>
					<th field="heat_plan_id"  width="20" sortable="true" editor="{type:'validatebox'}"><b>Plan No</b></th>
					<th field="main_status_desc" sortable="true" width="20" ><b>Status</b></th>
					<th field="status" editor="{type:'validatebox'}" hidden="true"  width="20"><b>Status</b></th>
					<th field="psn_grade" width="20"><b>Grade</b></th>
				    <th field="act_heat_id" width="30"><b>Attached Heat</b></th>
				    <th field="indent_no" width="20" hidden="true"><b>Indent_no</b></th>
				   
						
					</tr>
			</thead>
		</table> 
	
	</form>
</div>

<div id="t5_heatplan_form_btn_div_id">
	<a href="javascript:void(0)" id="t5_ok_heat_plan_btn_id" class="easyui-linkbutton" iconCls="icon-cancel"
		onclick="t5_closeheatplanLine()" >Close Heat Plan</a>
	<!-- <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" plain="true" onclick="dialogBoxClose('t5_heatplan_form_div_id')" style="width:90px">Close</a> -->
</div>

 <style type="text/css">
#t5_heatplan_form_id {
	margin: 0;
	padding: 5px 5px;
}
</style> 