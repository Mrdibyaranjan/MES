<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<script src="${pageContext.request.contextPath}/js/LRFOperationDtls.js"></script>
<div title="Return Heats from CCM"
	style="padding-top: 5px; padding-left: 5px; padding-right: 5px;"
	data-options="iconCls:'icon-ok'">
	<table id="t28_returnheat_tbl_id"
		toolbar="#t28_returnheat_tbl_toolbar_div_id"
		title="<spring:message code="label.t28.returnHeatsHeader"/>"
		class="easyui-datagrid" style="width: 100%; height: 500px;"
		url="./LRFproduction/getReturnHeatDetails"
		method="get" iconCls='icon-ok' pagination="true" maximizable="true"
		resizable="true" fitColumns="true" remoteSort="false" pageSize="20"
		rownumbers="true" singleSelect="true">
		<thead>
			<tr>
				<th field="heat_id" sortable="true" width="120"><b><spring:message code="label.t28.heatNo" /></b></th>
				<th field="aim_psn_no" sortable="true" width="120" formatter="(function(v,r,i){return formatColumnData('psnHdrModel.psn_no',v,r,i);})">
					<b><spring:message code="label.t28.psn" /></b></th>
				<th field="return_type" sortable="true" width="120" formatter="(function(v,r,i){return formatColumnData('returnTypeMdl.lookup_value',v,r,i);})">
				<b><spring:message code="label.t28.returntype" /></b></th>
				<th field="steelLadleNo" sortable="true" width="120" formatter="(function(v,r,i){return formatColumnData('steelLdlMstr.steel_ladle_no',v,r,i);})">
				<b><spring:message code="label.t28.ladle" /></b></th>
				<th field="sub_unit_id" sortable="true" width="120" formatter="(function(v,r,i){return formatColumnData('subUnitMstr.sub_unit_name',v,r,i);})">
				<b><spring:message code="label.t28.unit" /></b></th>
				<th field="act_qty" sortable="true" width="100"><b><spring:message code="label.t28.actQty" /></b></th>
				<th field="balance_qty" sortable="true" width="100"><b><spring:message code="label.t28.balanceQty" /></b></th>
				<th field="trns_sl_no" width="0" hidden="true"><b>Trns Id</b></th>
				<th field="heatRefId" width="0" hidden="true"><b>Ref Id</b></th>
				<th field="version" width="0" hidden="true"><b>Version</b></th>
				<th field="heat_counter" width="0" hidden="true"><b>Heat Counter</b></th>
			</tr>
		</thead>
	</table>

	<div id="t28_returnheat_tbl_toolbar_div_id" style="padding-top: 5px;">
		<%@ page import="java.util.*"%>
		<%--Display Buttons  --%>
		<%request.setAttribute("scrn_id", Integer.parseInt(request.getParameter("scrn_id")));%>
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

<div id="t28_returnheat_mix_form_div_id" class="easyui-dialog"
	style="height: 600px; padding: 10px 10px; width: 95%;" closed="true">
	<form id="t28_returnheat_mix_form_id" method="post" novalidate>
		<input name="trns_sl_no" type="hidden"  id="t28_trns_sl_no"/>
		<input name="heatRefId" type="hidden"  id="t28_heatRefId"/>
    	<input name="version" type="hidden"  id="t28_version"/>
    	<input name="trnsType" type="hidden"  id="t28_trnsType"/>
    	<input name="aim_psn_id" type="hidden"  id="t28_aim_psn_id"/>
    	<input name="steel_ladle_id" type="hidden"  id="t28_steel_ladle_id"/>
    	<input name="heat_counter" type="hidden"  id="t28_heat_counter"/>
    	<br>
    	<!-- <h1><c:out value="${param.t28_trnsType}" /></h1>  -->
    	<h1>${param.trnsType}</h1>
    	<br>
		<table style="width: 100%;border-collapse: collapse;border: 1px solid black;" ><tr><td></td></tr><tr><td>
			<table style="width: 100%">
				<tr>
					<td><label><spring:message code="label.t28.heatNo"/></label></td>
					<td><input name="heat_id"  type="text" id="t28_heat_id"  class="easyui-textbox" data-options="required:true,readonly:true"></td>
					<td><label><spring:message code="label.t28.psn" /></label></td>
					<td><input name="aim_psn_no" type="text" id="t28_aim_psn_no"  class="easyui-textbox" data-options="required:true,readonly:true"></td>
					<td><label><spring:message code="label.t28.unit" /></label></td>
					<td><input name="sub_unit_id"  type="text" id="t28_sub_unit_id"  class="easyui-textbox" data-options="required:true,readonly:true"></td>
					<td><label><spring:message code="label.t28.ladle" /></label></td>
					<td><input name="steelLadleNo"  type="text" id="t28_steelLadleNo"  class="easyui-textbox" data-options="required:true,readonly:true"></td>
				</tr>
				<tr>
				<td><label><spring:message code="label.t28.returntype" /></label></td>
					<td><input name="return_type"  type="text" id="t28_return_type"  class="easyui-textbox" data-options="required:true,readonly:true"></td>
					<td><label><spring:message code="label.t28.actQty" /></label></td>
					<td><input name="act_qty"  type="text" id="t28_act_qty"  class="easyui-textbox" data-options="required:true,readonly:true"></td>
					<td><label><spring:message code="label.t28.balanceQty" /></label></td>
					<td><input name="balance_qty"  type="text" id="t28_balance_qty"  class="easyui-textbox" data-options="required:true,readonly:true"></td>
					<td><label><spring:message code="label.t28.newHeatNo" /></label></td>
					<td><input name="new_heat_id"  type="text" id="t28_new_heat_id"  class="easyui-textbox" data-options="readonly:true"></td>
				</tr>
				<tr><td colspan="8">&nbsp;&nbsp;</td></tr>
				<tr>
					<td colspan="7">
						<table id="m28_sel_mix_returnheats_tbl_id" class="easyui-datagrid"
						style="width: 100%; height: auto;" rownumbers="true" iconCls='icon-ok' fitColumns="true">					
						<thead>
						<tr>
							<th field="heat_id" width="120"><spring:message code="label.t28.heatNo" /></th>
							<th field="psn_no" width="120"><spring:message code="label.t28.psn" /></th>
							<!-- <th field="return_type" width="120"><spring:message code="label.t28.returntype" /></th> -->
							<th field="ladle_no" width="120"><spring:message code="label.t28.ladle" /></th>
							<th field="sub_unit_id" width="120"><spring:message code="label.t28.unit" /></th>
							<th field="actQty" width="120"><spring:message code="label.t28.actQty" /></th>
							<th field="pourQty" width="120"><spring:message code="label.t28.pourQty" /></th>  
							<th field="balanceQty" width="120"><spring:message code="label.t28.balanceQty" /></th>
							<!-- <th field="trns_sl_no" width="0" hidden="true">Trns Id</th>  -->
							<th field="reladleHeatDtlId" width="0" hidden="true">Trns Id</th>
							<!-- <th field="heatRefId" width="0" hidden="true">Ref Id</th> -->
							<th field="aimPsn" width="0" hidden="true">Psn</th>
							<th field="steelLadleId" width="0" hidden="true">Ladle Id</th>
							<th field="heat_counter" width="0" hidden="true">Heat Counter</th>
							<!-- <th field="version" width="0" hidden="true">Version</th>  -->
						</tr>
						</thead>
						</table>
					</td>
				</tr>
				<tr><td colspan="8">&nbsp;&nbsp;</td></tr>
				<tr><td colspan="8" align="center">
					<a href="javascript:void(0)" id="t28_return_heat_mix_save_btn" class="easyui-linkbutton" iconCls="icon-save" onclick="saveT28ReturnHeatMix()" style="width:90px">Save</a>
					<a href="javascript:void(0)" id="t28_return_heat_mix_close_btn" class="easyui-linkbutton" iconCls="icon-cancel" onclick="dialogBoxClose('t28_returnheat_mix_form_div_id')" style="width:90px">Close</a>
				</td></tr>
			</table>
		</td></tr>
		</table>
		<br><br><br>
		<table id="t28_returnheat_mix_tbl_id" toolbar="#t28_returnheat_mix_tbl_toolbar_div_id" class="easyui-datagrid" style="width:100%;height: 350px;"
           iconCls='icon-ok' pagination="true" maximizable="true"  resizable="true" remoteSort="false" pageSize="20"
            rownumbers="true"  singleSelect="false" checkOnSelect="false">   
		<thead>
			<tr>
				<th field="sl_no" sortable="false"  checkbox="true" width="40"></th>
				<th field="heat_id" width="120"><spring:message code="label.t28.heatNo" /></th>
				<th field="aim_psn_no" width="120" formatter="(function(v,r,i){return formatColumnData('psnHdrModel.psn_no',v,r,i);})">
					<spring:message code="label.t28.psn" /></th>
				<th field="return_type" width="120" formatter="(function(v,r,i){return formatColumnData('returnTypeMdl.lookup_value',v,r,i);})">
					<spring:message code="label.t28.returntype" /></th>
				<th field="steelLadleNo" width="120" formatter="(function(v,r,i){return formatColumnData('steelLdlMstr.steel_ladle_no',v,r,i);})">
					<spring:message code="label.t28.ladle" /></th>
				<th field="sub_unit_id" width="120" formatter="(function(v,r,i){return formatColumnData('subUnitMstr.sub_unit_name',v,r,i);})">
					<spring:message code="label.t28.unit" /></th>
				<th field="act_qty" width="120"><spring:message code="label.t28.actQty" /></th>
				<th field="pour_qty" data-options="editor:{type:'numberbox',options:{precision:2}}" width="120"><spring:message code="label.t28.pourQty" /></th>
				<th field="balance_qty" width="0" hidden="true"><spring:message code="label.t28.balanceQty" /></th>  
				<th field="trns_sl_no" width="0" hidden="true">Trns Id</th>
				<th field="heatRefId" width="0" hidden="true">Ref Id</th>
				<th field="version" width="0" hidden="true">Version</th>
				<th field="heat_counter" width="0" hidden="true">Heat Counter</th>
			</tr>
		</thead>
	</table>
	</form>
</div>
<div id="t28_returnheat_mix_tbl_toolbar_div_id">
	<a href="javascript:void(0)" id="t28_mix" class="easyui-linkbutton" iconCls="icon-ok" onclick="T28MixSelectedHeats()" style="width:150px">Mix selected heats</a>
	<a href="javascript:void(0)" id="t28_clear" class="easyui-linkbutton" iconCls="icon-ok" onclick="T28ClearSelectedHeats()" style="width:170px">Clear selected heats</a>
</div>
<!-- ReLadle -->      
<div id="t28_return_heat_reladle_div_id"class="easyui-dialog"
		closed="true" style="width: 500px; height: 200px; padding-top: 0px; padding-left: 10px; padding-right: 20px;">	
	<form id="t28_return_heat_reladle_form_id" method="post" novalidate>   
	<div  style="padding-top: 10px;padding-left: 5px;padding-right: 5px;">
		<div style="padding-top: 10px;padding-bottom: 10px;">	    
	    <br>
	    <label style="padding-center: 1%"><spring:message code="label.t28.ladle" /></label>
		<input name="steelLadle"  type="text" id="t28_steelLadle"  class="easyui-combobox" data-options="required:true,panelHeight: 'auto',
		editable:false,valueField:'keyval',textField:'txtvalue' ">
		<label style="padding-center: 15px"></label>
		<a href='javascript:void(0)'  id="t28_reladle_save" class='easyui-linkbutton' style='width:100px' iconCls='icon-save' plain='false' onclick='saveT28ReturnHeatReladle()'>Save</a>
		<a href='javascript:void(0)'  id="t28_reladle_cancel" class='easyui-linkbutton' style='width:100px' conCls="icon-cancel" plain='false' onclick="dialogBoxClose('t28_return_heat_reladle_div_id')">Close</a>
		</div>
	</div>    
	</form>
</div>  
<style type="text/css">
#t2_heatplan_form_id {
	margin: 0;
	padding: 5px 5px;
}
</style>