<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<script src="${pageContext.request.contextPath}/js/smesHeatPlan.js"></script>
<div title="Heat Plan List"
	style="padding-top: 5px; padding-left: 5px; padding-right: 5px;"
	data-options="iconCls:'icon-ok'">
	<table id="t2_heatplan_tbl_id"
		toolbar="#t2_heatplan_tbl_toolbar_div_id"
		title="<spring:message code="label.t2.heatPlanHeader"/>"
		class="easyui-datagrid" style="width:150%; height: 500px;"
		url="./HeatPlan/getHeatPlanHeaderDetailsByStatus?PLAN_STATUS=WIP,RELEASED"   
		method="get" iconCls='icon-ok' pagination="true" maximizable="true"
		resizable="true" fitColumns="true" remoteSort="false" pageSize="20"
		rownumbers="true" singleSelect="true">
		<thead>
			<tr>
				<th field="heat_plan_id" sortable="true" width="70" align="right"><b><spring:message
							code="label.t2.heatPlanNo" /></b></th>
			    <th field="plan_create_date" sortable="true"
					formatter="(function(v,r,i){return formatDateTime('plan_create_date',v,r,i)})"
					width="150"><b><spring:message
							code="label.t2.creationDate" /></b></th>
				<th field="prod_start_date" sortable="true"
					formatter="(function(v,r,i){return formatDateTime('prod_start_date',v,r,i)})"
					width="150"><b><spring:message
							code="label.t2.prodStartDate" /></b></th>
				<th field="caster_type" sortable="true" width="80"
					formatter="(function(v,r,i){return formatColumnData('lookupCasterType.lookup_value',v,r,i);})"><b><spring:message
							code="label.t2.caster" /></b></th>
				<th field="section_type" sortable="true" width="100"
					formatter="(function(v,r,i){return formatColumnData('lookupSectionType.lookup_value',v,r,i);})"><b><spring:message
							code="label.t2.secType" /></b></th>
				 <th field="sales_Order" sortable="true" width="150"><b><spring:message
							code="lable.t2.Salesorder" /></b></th>	
				 <th field="grade" sortable="true" width="80" ><b><spring:message
							code="lable.t2.grade" /></b></th>
				<th field="section_planned" sortable="true" width="80"
					formatter="(function(v,r,i){return formatColumnData('smsCapabilityMstrModel.lookupOutputSection.lookup_value',v,r,i);})"><b><spring:message
							code="label.t2.section" /></b></th>
				<th field="psn_no" sortable="true" width="100"
				formatter="(function(v,r,i){return formatColumnData('heatPlanLinesDetails.psnHdrModel.psn_no',v,r,i);})"><b><spring:message
							code="label.r1.heatPlanRptPsn" /></b></th>	
				<th field="target_eof" sortable="true" width="70"
					formatter="(function(v,r,i){return formatColumnData('subUnitTargetEof.sub_unit_name',v,r,i);})"><b><spring:message
							code="label.t2.targetEof" /></b></th>
				<th field="soldToPartyName" sortable="true" width="150" 
				formatter="(function(v,r,i){return formatColumnData('heatPlanLinesDetails.soHeaderModel.soldToPartyName',v,r,i);})"><b><spring:message
							code="label.t2.custName"  /><b><spring:message/></b></th>
				<th field="plan_cut_length" sortable="true" width="50"
				formatter="(function(v,r,i){return formatColumnData('heatPlanLinesDetails.plan_cut_length',v,r,i);})"><b><spring:message
							code="label.t2.cutLength" /></b></th>
				<th field="alter_cut_length_min" sortable="true" width="50" 
				formatter="(function(v,r,i){return formatColumnData('heatPlanLinesDetails.alter_cut_length_min',v,r,i);})"><b><spring:message
							code="label.t2.lengthMin" /></b></th>
				<th field="alter_cut_length_max" sortable="true" width="50"
					formatter="(function(v,r,i){return formatColumnData('heatPlanLinesDetails.alter_cut_length_max',v,r,i);})"><b><spring:message
							code="label.t2.lengthMax" /></b></th>
			    <!-- 
				<th field="aim_psn_id" sortable="true" width="120"
					formatter="(function(v,r,i){return formatColumnData('psnHdrModel.psn_no',v,r,i);})"><b><spring:message
							code="label.t2.aimpsn" /></b></th>
				<th field="tundish_type" sortable="true" width="140"
					formatter="(function(v,r,i){return formatColumnData('lookupTundishType.lookup_value',v,r,i);})"><b><spring:message
							code="label.t2.tundishType" /></b></th> -->
				<th field="no_of_heats_planned" sortable="true" align="right"
					width="50"><b><spring:message
							code="label.t2.noOfheatPlanned" /></b></th>
				<th field="plan_sequence" sortable="true" align="right" width="50"><b><spring:message
							code="label.t2.sequence" /></b></th>
				<th field="main_status_id" sortable="false" width="80"
					formatter="(function(v,r,i){return formatColumnData('mainHeatStatusMasterModel.main_status_desc',v,r,i);})"><b><spring:message
							code="label.t2.heatPlanStaus" /></b></th>
				<th field="pending_heats" rowspan="total" sortable="true" align="right"
					width="50"><b><spring:message
							code="label.t2.pendingHeats" /></b></th>
				<th field="line_remarks" sortable="false" width="80"
				formatter="(function(v,r,i){return formatColumnData('heatPlanLinesDetails.line_remarks',v,r,i);})"><b><spring:message
							code="label.t2.heatplanremarks" /></b></th>
				<th field="version" width="80" align="right" hidden="true"><b>Version</b></th>
			</tr>
		</thead>
	</table>

	<div id="t2_heatplan_tbl_toolbar_div_id" style="padding-top: 5px;">
		<%@ page import="java.util.*"%>
		<%--Display Buttons  --%>
		<%
			request.setAttribute("scrn_id",
					Integer.parseInt(request.getParameter("scrn_id")));
		%>
		<c:forEach var="disbtn" items="${display_btn}">
			<c:forEach var="btn_view" items="${disbtn.value}">
				<c:if test='${disbtn.key == scrn_id}'>
					<%--Setting Button Position  --%>
					<c:set var="btn_position" value="${fn:split(btn_view, '$$$')}" />
					<c:if test="${btn_position[1] eq 'PORTION1'}">
	   					${btn_position[0]}
	   				</c:if>
				</c:if>
			</c:forEach>
		</c:forEach>
	</div>
</div>

<div id="t2_heatplan_form_div_id" class="easyui-dialog"
	style="height: 600px; padding: 10px 10px; width: 95%;" closed="true">
	<div class="easyui-accordion" id="eofAccDivId"
		data-options="multiple:true" style="width: 98%; height: auto">
		
		<table id="t2_prev_heatplan_tbl_id"
		toolbar="#t2_heat_tbl_toolbar_div_id"
		title="Pending Heat Plan Details"
		class="easyui-datagrid" style="width: 100%; height: 200px;"
		url="./HeatPlan/getAllPrevHeatPlanDetails"
		method="get" iconCls='icon-ok' pagination="true" maximizable="true"
				resizable="true" remoteSort="false" pageSize="10" rownumbers="true"
				singleSelect="false"  checkOnSelect="false">
		<thead>
			<tr>
			<!-- <th field="sl_no" sortable="false"  checkbox="true" width="40"></th> -->
			<th field="heat_plan_id"  rowspan="total" sortable="true" align="right" width="80"><b>Plan No.</b></th>
				<th field="prod_start_date" sortable="true" rowspan="total"
					formatter="(function(v,r,i){return formatDateTime('prod_start_date',v,r,i)})"
					width="170"><b><spring:message
							code="label.t2.prodStartDate" /></b></th>
				<th field="plan_sequence"  rowspan="total" sortable="true" align="right" width="80"><b><spring:message
							code="label.t2.sequence" /></b></th>
				<th field="caster_type" rowspan="total" sortable="true" width="80"
					formatter="(function(v,r,i){return formatColumnData('lookupCasterType.lookup_value',v,r,i);})"><b><spring:message
							code="label.t2.caster" /></b></th>
				<th field="section_type" rowspan="total" sortable="true" width="120"
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
				<!--  <th field="heatPlanLine.tundish_type" sortable="true" width="100"
					formatter="(function(v,r,i){return formatColumnData('lookupTundishType.lookup_value',v,r,i);})"><b><spring:message
							code="label.t2.tundishType" /></b></th>-->
				<th field="no_of_heats_planned" rowspan="total" sortable="true" align="right"
					width="100"><b><spring:message
							code="label.t2.noOfheatPlanned" /></b></th>
				<th field="pending_heats" rowspan="total" sortable="true" align="right"
					width="120"><b><spring:message
							code="label.t2.pendingHeats" /></b></th>
							
			</tr>
		</thead>
	</table>
	</div>
	
	<br>
	<form id="t2_heatplan_form_id" method="post" novalidate>
		<input name="heat_plan_id" type="hidden" id="t2_heat_plan_id">
		<input name="t2_plan_hdr_version" type="hidden" id="t2_plan_hdr_version">
		<input name="length_min" type="hidden" id="t2_length_min">
		<input name="length_max" type="hidden" id="t2_length_max">
		<input name="avg_heat_size" type="hidden" id="t2_avg_heat_size">
		<input name="max_heat_size" type="hidden" id="t2_max_heat_size">
		<input name="planned_qty" type="hidden"  id="t2_planned_qty"/>
		<table style="width: 100%;border-collapse: collapse;border: 1px solid black;" ><tr><td>
		<table style="width: 100%">
			<tr>
				<td><label><spring:message code="label.t2.caster" /></label></td>
				<td><input name="caster_type" type="text" id="t2_caster_type"
					class="easyui-combobox"
					data-options="panelHeight: 'auto',editable:false,required:true,
                    valueField:'keyval',                    
                    textField:'txtvalue'">
				</td>
				<td><label><spring:message code="label.t2.secType" /></label></td>
				<td><input name="section_type" type="text" id="t2_section_type"
					class="easyui-combobox"
					data-options="panelHeight: 'auto',editable:false,required:true,
                    valueField:'keyval',                    
                    textField:'txtvalue'">
				</td>
				<td><label><spring:message code="label.t2.section" /></label></td>
				<td><input name="section_planned" type="text"
					id="t2_section_planned" class="easyui-combobox"
					data-options="panelHeight: 'auto',editable:false,required:true,
                    valueField:'keyval',                    
                    textField:'txtvalue'">
				</td>
				<td><label><spring:message code="label.t2.targetEof" /></label></td>
				<td><input name="target_eof" type="text" id="t2_target_eof"
					class="easyui-combobox"
					data-options="panelHeight: 'auto',editable:false,required:true,
                    valueField:'keyval',                    
                    textField:'txtvalue'">
				</td>
			</tr>
			<tr>
				<!-- <td><label><spring:message code="label.t2.aimpsn" /></label></td>
				<td><input name="aim_psn_id" type="text" id="t2_aim_psn_id"
					class="easyui-combobox"
					data-options="panelHeight: 'auto',editable:true,required:true,
                    valueField:'keyval',                    
                    textField:'txtvalue'">
				</td>
				<td><label><spring:message code="label.t2.tundishType" /></label></td>
				<td><input name="tundish_type" type="text" id="t2_tundish_type"
					class="easyui-combobox"
					data-options="panelHeight: 'auto',editable:false,required:true,
                    valueField:'keyval',                    
                    textField:'txtvalue'">
				</td>  -->
				
				<td><label><spring:message code="label.t2.creationDate" /></label></td>
				<td><input name="plan_create_date" type="text"
					id="t2_plan_create_date"
					formatter="(function(v,r,i){return formatDateTime('plan_create_date',v,r,i)})"
					class="easyui-datetimebox" data-options="required:true,showSeconds:false,readonly:true"></td>
				<td><label><spring:message
							code="label.t2.prodStartDate" /></label></td>
				<td><input name="prod_start_date" type="text"
					id="t2_prod_start_date"
					class="easyui-datebox" 
					formatter="(function(v,r,i){return formatDate('prod_start_date',v,r,i)})"
					 data-options="required:true,"></td>
				
				<td><label><spring:message
							code="label.t2.noOfheatPlanned" /></label></td>
				<td><input name="no_of_heats_planned" type="text"
					id="t2_no_of_heats_planned" class="easyui-numberbox"
					data-options="required:true,disabled:'true',validType:{length:[1,10]}"></td>
				<td><label><spring:message code="label.t2.sequence" /></label></td>
				<td><input name="plan_sequence" type="text"
					id="t2_plan_sequence" class="easyui-numberbox"
					data-options="required:true,disabled:'true'"></td>
			</tr>
			<tr>
				
				<td><label><spring:message
							code="label.t2.heatplanremarks" /></label></td>
				<td colspan="7"><input name="plan_remarks" type="text"
					id="t2_plan_remarks" data-options="multiline:true"
					style="height: 30px; width: 96%" class="easyui-textbox"></td>
			</tr>
		</table>
		</td></tr></table>
		<br>
		  
		<table id="t2_heat_line_tbl_id" title="Heat Plan Details Entry"
			class="easyui-datagrid" style="width: 100%; height: 350px;"
			toolbar="#t2_heatplan_form_btn_div_id" rownumbers="true"
			fitColumns="true" singleSelect="true" fitColumns="true">
			<thead>
				<tr>
					<th field="heat_plan_id" hidden="true"
						editor="{type:'validatebox'}">Heat Plan Id</th>
					<th field="heat_line_id" hidden="true"
						editor="{type:'validatebox'}">Heat Plan Line Id</th>
					<th field="heat_plan_line_no" width="40"
						editor="{type:'validatebox',options:{required:true,editable:true}}">Indent
							ID</th>
					<th field="aim_psn" width="100" 		
						formatter="(function(v,r,i){return formatColumnData('psnHdrModel.psn_no',v,r,i);})" 
						editor="{type:'combobox',options:{required:true,editable:true,valueField:'keyval',textField:'txtvalue',panelHeight:150,
						method:'get',url:'./CommonPool/getComboList?col1=psn_hdr_sl_no&col2=psn_no&classname=PSNHdrMasterModel&status=1&wherecol=psn_status=\'APPROVED\' and record_status='}}">PSN</th>
					<th field="soHeaderId" width="100" formatter="(function(v,r,i){return formatColumnData('soHeaderModel.soHeaderItem',v,r,i);})" 
						editor="{type:'combobox',options:{required:true,editable:true,panelHeight:150}}">Sales Order</th>
					<th field="plan_cut_length" width="65" editor="{type:'numberbox', options:{precision:2,required:true,editable:true}}">Cut
							Length (Mtr)</th>
					<th field="alter_cut_length_min" width="115"
						editor="{type:'numberbox',options:{precision:2}}">Alternative
							Cut Length min (Mtr)</th>
					<th field="alter_cut_length_max" width="115"
						editor="{type:'numberbox',options:{precision:2}}">Alternative
							Cut Length max (Mtr)</th>
					<th field="plan_heat_qty" width="60"
						editor="{type:'numberbox',options:{precision:2,required:true,editable:true}}">Plan Qty (Tons)</th>
						
					<th field="casting_order_req" width="60" 		
						editor="{type:'combobox',options:{required:true,editable:true,valueField:'keyval',textField:'txtvalue',panelHeight: 100,
                        method:'get',url:'./CommonPool/getComboList?col1=lookup_code&col2=lookup_code&classname=LookupMasterModel&status=1&wherecol= lookup_type=\'CASTING_ORDER_REQ\' and lookup_status='}}">Order Type</th>						
					<th field="line_remarks" width="95" editor="{type:'validatebox'}">Remarks</th>
					<th field="version" hidden="true" editor="{type:'validatebox'}">Version</th>
					<th field="line_status" hidden="true" editor="{type:'validatebox'}">Line
							Status</th>
				</tr>
			</thead>
		</table> 
		
		<!-- 
		<table id="t2_heat_line_tbl_id"
		title="Heat Plan Details Entry" class="easyui-datagrid"
		style="width: 100%; height: auto;"
		toolbar="#t2_heatplan_form_btn_div_id" rownumbers="true"
		fitColumns="true" singleSelect="true">
		</table>
		 -->
	</form>
</div>

<div id="t2_heatplan_form_btn_div_id">
	<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'"
		plain="true" onclick="addT2LinePlan()">Add</a> 
	<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true"
		onclick="deleteT2HeatLinePlan()">Remove</a>
	<a href="#"  id="t2_save_heat_plan_btn_id" class="easyui-linkbutton" iconCls="icon-save" plain="true"
		onclick="saveHeatPlan()">Save</a>
	<a href="#" id="t2_ok_heat_plan_btn_id" class="easyui-linkbutton" iconCls="icon-ok" plain="true"
		onclick="validateCutLength()">Ok</a>
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" plain="true" onclick="dialogBoxClose('t2_heatplan_form_div_id')" style="width:90px">Close</a>
</div>
<!-- Day Plan Report -->      
<div id="t2_heat_plan_day_rpt_div_id"class="easyui-dialog"
		closed="true" style="width: 500px; height: 200px; padding-top: 0px; padding-left: 10px; padding-right: 20px;">	
	<form id="t2_heat_plan_day_rpt_form_id" method="post" novalidate>   
	<style>
	page {
	  background: white;
	  display: block;
	  margin: 0 auto;
	  margin-bottom: 0.5cm;
	  box-shadow: 0 0 0.5cm rgba(0,0,0,0.5);
	}	
	</style>
	<div  style="padding-top: 10px;padding-left: 5px;padding-right: 5px;">
		<div style="padding-top: 10px;padding-bottom: 10px;">	    
	    <%@ page import="java.util.*" %>
	    <br>
	    <label style="padding-center: 1%">Caster</label>
		<input name="caster"  type="text" id="t2_caster"  class="easyui-combobox" data-options="required:true,panelHeight: 'auto',
		editable:false,valueField:'keyval',textField:'txtvalue' ">
		<label style="padding-center: 15px"></label>
		<a href='javascript:void(0)'  id="t2_export" class='easyui-linkbutton' style='width:100px' iconCls='icon-edit' plain='false' onclick='exportExcel()'>Download</a>
		<a href='javascript:void(0)'  id="t2_cancel" class='easyui-linkbutton' style='width:100px' conCls="icon-cancel" plain='false' onclick="dialogBoxClose('t2_heat_plan_day_rpt_div_id')">Close</a>
		</div>
		<!--  <div id="t2_heat_plan_day_rpt_id" style="background:white" width="100%" class="easyui-panel" closed="true">
		</div> -->
	</div>    
	</form>
</div> 

<!-- Heat Plan Detail Report -->
<div id="t2_heat_plan_det_rpt_div_id"class="easyui-dialog"
		closed="true" style="width: 700px; height: 200px; padding-top: 0px; padding-left: 10px; padding-right: 20px;">	
	<form id="t2_heat_plan_det_rpt_form_id" method="post" novalidate>   
	<div style="padding-top: 10px;padding-left: 5px;padding-right: 5px;">
		<div style="padding-top: 10px;padding-bottom: 10px;">	    
	    <%@ page import="java.util.*" %>
	    <br>
	    <label style="padding-center: 1%">Caster </label>
		<input name="caster"  type="text" id="t2_rpt_caster"  class="easyui-combobox" data-options="required:true,panelHeight: 'auto',
		editable:false,valueField:'keyval',textField:'txtvalue' ">
		<label style="padding-center: 1%">From Date</label>
	    <input name="fromDate" type="text" id="r1_fromDate" style="width: 100px;" class="easyui-datebox"
		data-options="required:true,editable:false">
	    <label style="padding-center: 2%">To Date</label>
		<input name="toDate" type="text" id="r1_toDate" style="width: 100px;" class="easyui-datebox"
		data-options="required:true,editable:false">
		<!-- <label style="padding-center: 1%">Date </label>
		<input name="prod_start_date" type="text" id="t2_rpt_date" class="easyui-datebox" 
		formatter="(function(v,r,i){return formatDate('prod_start_date',v,r,i)})" data-options="required:true,"> -->		
		</div>
		<!--  <div id="t2_heat_plan_day_rpt_id" style="background:white" width="100%" class="easyui-panel" closed="true">
		</div> -->
	</div>    
	<div style="padding-top: 20px;padding-left: 95px;padding-right: 5px;">
		<a href='javascript:void(0)'  id="t2_export" class='easyui-linkbutton' style='width:100px' iconCls='icon-edit' plain='false' onclick='expHeatPlanDetRpt()'>Download</a>
		<a href='javascript:void(0)'  id="t2_cancel" class='easyui-linkbutton' style='width:100px' iconCls='icon-save' plain='false' onclick="dialogBoxClose('t2_heat_plan_det_rpt_div_id')">Close</a>
	</div>
	</form>
</div> 
<script type="text/javascript">
	$(window).load(setTimeout(applyT2Filter, 1)); //1000 ms = 1 second.

	function applyT2Filter() {
		$('#t2_heatplan_tbl_id').datagrid('enableFilter');
	}
    $(function(){
    	restrictDays('t2_prod_start_date');
    });
</script>

<style type="text/css">
#t2_heatplan_form_id {
	margin: 0;
	padding: 5px 5px;
}
</style>