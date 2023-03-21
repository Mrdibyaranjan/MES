<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<script src="${pageContext.request.contextPath}/js/VDProductionDetails.js"></script>
<!-- Accordion Start -->
<div class="easyui-accordion" data-options="multiple:true" style="width:100%;height: auto">
 
 
 <!-- div_1_Begin Crew Details -->
<div title="Crew Details" data-options="iconCls:'icon-ok'"  style="padding-top: 10px;padding-left: 10px;padding-right: 10px;">
    
    <form id="t22_crew_form_id" method="post" novalidate> 
    <table style="width: 100%;"   class="easyui-panel" >
       
        <!-- first row style="padding-right:40px"-->
        <tr style="height: 30px;">
        <td  style="width: 100px;"> 
        <label><spring:message code="label.t22.vdProductionRecDate"/></label></td>
       
        <td style="width: 150px;" > 
         <input name="recDate"  type="text" id="t22_recDate"  class="easyui-datetimebox" data-options="required:true,showSeconds:false,onSelect:callT22Dropdowns()">
        </td>
        
        <td align="right" style="width: 100px;"> 
        <label><spring:message code="label.t22.vdProductionShift"/></label>
        </td>
        <td style="width: 100px;"> 
         <input name="shift"  type="text" id="t22_shift"  class="easyui-combobox" data-options="required:true,panelHeight: 'auto',editable:false,valueField:'keyval',textField:'txtvalue'">
        </td>
        <td  align="right" style="width: 100px;" > 
        <label><spring:message code="label.t22.vdProductionUnit"/></label>
        </td>
        <td> 
         <input name="unit"  type="text" id="t22_unit"  class="easyui-combobox" data-options="required:true,panelHeight: 'auto',editable:false,valueField:'keyval',textField:'txtvalue'">
        </td>
        </tr>
        
        <tr style="height: 30px;">
        <td  style="width: 100px;"> 
        <label><spring:message code="label.t22.vdProductionShiftMgr"/></label>
        </td>
        <td> 
         <input name="shiftMgr"  type="text" id="t22_shiftMgr"  class="easyui-combobox" data-options="required:true,panelHeight: 'auto',editable:false,valueField:'keyval',textField:'txtvalue'">
        </td>
        
        <td  style="width:80px;padding-left: 40px" align="right">  
        <label><spring:message code="label.t22.vdManager"/></label>
        </td>
        <!-- 
        <td> 
         <input name="t22_vdInCharge"  type="text" id="t22_vdInCharge"  class="easyui-combobox" data-options="required:true,panelHeight: 'auto',editable:false,valueField:'keyval',textField:'txtvalue'">
        </td>
        <td  align="right" style="width: 100px;" > 
        <label><spring:message code="label.t22.vdOperator"/></label>
        </td>
         -->
        <td> 
         <input name="vdOptr"  type="text" id="t22_vdOperator"  class="easyui-combobox" data-options="required:true,panelHeight: 'auto',editable:false,valueField:'keyval',textField:'txtvalue'">
        </td>
        </tr>
        </table>
        </form>
     </div>
 <!-- div_1_End Crew Details -->

  <!-- div_2_Begin St ladle Waiting for Vd process -->
  
<div title="<spring:message code="label.t22.vdProcessWaiting"/>" data-options="iconCls:'icon-ok'" style="padding-top: 10px;padding-left: 10px;padding-right: 10px;">
	 <div id="T22ladleDetailsId">
	<table id="t22_vd_production_tbl_id" toolbar="#t22_vd_production_tbl_toolbar_div_id"  class="easyui-datagrid" style="width:100%;height: 200px;"
       	url="./LRFproduction/getHeatsWaitingForVDProcess?CURRENT_UNIT=VD&UNIT_PROCESS_STATUS=WAITING FOR PROCESSING" method="get" iconCls='icon-ok'
        pagination="true" maximizable="true"  resizable="true" remoteSort="false" pageSize="10"
        rownumbers="true"   singleSelect="true"> 
     <thead>
     	<tr>
        	<th field="heat_id" sortable="false" width="90"><spring:message code="label.t22.heatno"/></th>
            <th field="steel_ladle_name" sortable="false" width="100"><spring:message code="label.t22.ladleNo"/></th>
            <th field="lrf_dispatch_temp" sortable="true" width="100"><spring:message code="label.t22.dispatchTemp"/></th> 
            <th field="steel_wgt" sortable="true" width="100" > <spring:message code="label.t22.steelWgt"/></th>
            <th field="target_caster_name" sortable="true" width="120" > <spring:message code="label.t22.targetCaster"/></th>
            <th field="prev_unit" sortable="true" width="80" > <spring:message code="label.t22.lrfUnit"/></th>
          	<th field="aim_psn_char" sortable="true"  width="100" > <spring:message code="label.t22.aimpsn"/></th>
          	<th field="act_proc_path" sortable="true" hidden="true"></th>
          	
            <th field="heat_track_id" sortable="true" hidden="true"></th>
            <th field="aim_psn" sortable="true" hidden="true" ></th>
            <th field="heat_plan_id" sortable="true" hidden="true"> <spring:message code="label.t13.planNo"/></th>
            <th field="heat_plan_line_no" sortable="true" hidden="true"> <spring:message code="label.t13.planLineNo"/></th>
            <th field="target_caster_id" sortable="true"  width="50" >CasterId
            <th field="lrf_C" sortable="true" width="50" >C</th>
            <th field="lrf_S" sortable="true" width="50" >S</th>
            <th field="lrf_P" sortable="true" width="50" >P</th>
            <th field="lrf_MN" sortable="true" width="50" >Mn</th>
            <th field="lrf_Si" sortable="true" width="50" >Si</th>
            <th field="lrf_Ti" sortable="true" width="50" >Ti</th>
         </tr>
         </thead>
    </table>
    </div>
    <br>
    <div id="t22_vd_production_tbl_toolbar_div_id">
     <%@ page import="java.util.*" %>
    <%--Display Buttons  --%>
   
    <%
    request.setAttribute("scrn_id", Integer.parseInt(request.getParameter("scrn_id")));
    %>
    <table>
    <tr>
    <td width="80%">
   <c:forEach var="disbtn" items="${display_btn}">
   <c:forEach var="btn_view" items="${disbtn.value}">
     <c:if test='${disbtn.key == scrn_id}'>
     <%--Setting Button Position  --%>
      <c:set var="btn_position" value="${fn:split(btn_view, '$$$')}"/>
       <c:if test="${btn_position[1] eq 'PORTION1'}">
       ${btn_position[0]}
       </c:if>
     
     </c:if>
   </c:forEach>
   </c:forEach>
   </td>
   </tr>
   </table>
   </div>
   
         <form id="t22_heat_hdr_form_id" method="post" >
     
    <table style="width: 100%" class="easyui-panel" title="VD Heat Details">
    <!-- Hidden elements to carry id values -->
    <input name="aim_psn_id" type="hidden"  id="t22_aim_psn_id"/>
    <input name="heat_counter" type="hidden"  id="t22_heat_cnt"/>
    <input name="trns_sl_no"  type="hidden" id="t22_trns_si_no"/>
    <input name="caster_id" type="hidden"  id="t22_caster_id"/>
    <input name="sub_unit_id" type="hidden"  id="t22_sub_unit_id"/>
    <input name="act_path" type="hidden"  id="t22_act_path"/>
    <input name="heat_track_id" type="hidden"  id="t22_heat_track_id"/>
    <input name="steel_ladleno_id" type="hidden"  id="t22_steel_ladleno_id"/>
    <input name="plan_no" type="hidden"  id="t22_plan_no"/>
    <input name="plan_line_no" type="hidden"  id="t22_plan_line_no"/>
    <tr style="height: 30px;">
        <td align="right"> 
        <label style="padding-right: 10px">Heat No</label>
       </td>
       <td>
         <input name="heat_id"  type="text" id="t22_heat_id"  class="easyui-combobox" data-options="required:true,valueField:'keyval',                    
                    textField:'txtvalue'">
         
        </td>
        
        <td align="right"> 
        <label style="padding-right: 10px">Aim PSN</label>
       </td>
       <td>
         <input name="aim_psn"  type="text" id="t22_aim_psn"  class="easyui-textbox" data-options="required:true,validType:{length:[1,60]},readonly:true">
         
        </td>
        
        <td align="right">
        <label style="padding-right: 10px">Target Caster</label>
       </td>
       <td>
         <input name="tar_caster"  type="text" id="t22_tar_caster"  class="easyui-textbox" data-options="required:true,validType:{length:[1,60]},readonly:true">
         
        </td>
          <td align="right">
        <label style="padding-right: 10px">Prev Unit</label>
       </td>
       <td>
         <input name="prev_unit"  type="text" id="t22_prev_unit"   class="easyui-textbox" data-options="required:true,validType:{length:[1,60]},readonly:true">
         
        </td>
        </tr>
        
        
       <tr style="height: 30px;">
        <td align="right"> 
        <label style="padding-right: 10px">Ladle No</label>
       </td>
       <td>
         <input name="laddle_no"  type="text" id="t22_laddle_no"   class="easyui-textbox" data-options="required:true,validType:{length:[1,60]},readonly:true">
         
        </td>
       <td align="right"> 
        <label style="padding-right: 10px">LRF temp</label>
       </td>
       <td>
         <input name="lrf_temp"  type="text" id="t22_lrf_temp"   class="easyui-textbox" data-options="required:true,validType:{length:[1,60]},readonly:true">
         
        </td>
         <td align="right" > 
        <label  style="padding-right: 10px">Steel Wgt</label>
       </td>
       <td>
         <input name="tap_wgt"  type="text" id="t22_steel_wgt"  class="easyui-textbox" data-options="required:true,validType:{length:[1,60]},readonly:true">
         
        </td>
        </tr>
        <tr style="height: 30px;">
        <td align="right" > 
        <label  style="padding-right: 10px">1MBAR Achieved Time</label>
       </td>
       <td>
         <input name="lowest_m_bar"  type="text" id="t22_lowest_mbar"  class="easyui-textbox" data-options="required:true,validType:{length:[1,60]},readonly:false">
       </td>
       <td align="right" > 
        <label  style="padding-right: 10px">1MBAR Retention Time</label>
       </td>
       <td>
         <input name="holding_time"  type="text" id="t22_holding_mbar"  class="easyui-textbox" data-options="required:true,validType:{length:[1,60]},readonly:false">
       </td>
       </tr>
         <!--  <td> 
        <label style="padding-right: 10px">Plan No</label>
       </td>
       <td>
         <input name="plan_no"  type="text" id="t22_plan_no" class="easyui-textbox"  data-options="required:true,validType:{length:[1,10]},readonly:true">
         
        </td>
           <td> 
        <label style="padding-right: 10px">Initial Temp</label>
       </td>
       <td>
         <input name="initial_temp"  type="text" id="t22_initial_temp" class="easyui-textbox"  data-options="required:false,validType:{length:[1,60]},readonly:true">
         
        </td> 
        </tr>
       <!--  <tr>
          <td> 
        <label style="padding-right: 10px">PlanLine No</label>
       </td>
       <td>
         <input name="plan_line_no"  type="text" id="t22_plan_line_no"   class="easyui-textbox" data-options="required:true,validType:{length:[1,10]},readonly:true">
         
        </td>
        </tr> -->
        <tr>
        <td colspan="8">
        <div  id="t22_heat_hdr_btn_div_id">
      <%@ page import="java.util.*" %>
    <%--Display Buttons  --%>
   
    <%
    request.setAttribute("scrn_id", Integer.parseInt(request.getParameter("scrn_id")));
    %>
    
   <c:forEach var="disbtn" items="${display_btn}">
   <c:forEach var="btn_view" items="${disbtn.value}">
     <c:if test='${disbtn.key == scrn_id}'>
     <%--Setting Button Position  --%>
      <c:set var="btn_position" value="${fn:split(btn_view, '$$$')}"/>
       <c:if test="${btn_position[1] eq 'PORTION2'}">
       ${btn_position[0]}
       </c:if>
     
     </c:if>
   </c:forEach>
   </c:forEach>
   </div>
    
    
     </td>
     </tr>
     </table>
      
    </form>
    
	</div>
 <!-- div_2_End St ladle Waiting for Vd process -->
    </div>
<!-- Accordion End -->


  <!-- Event Button  Screen Start -->
 <div id="t22_vd_events_form_div_id" class="easyui-dialog" style="height:450px;padding: 0 50px 0 50px;"  closed="true">
      <form id="t22_vd_events_form_id" method="post" novalidate>
       <table style="width: 100%;padding-left: 20px;padding-right: 20px;" >
       <tr height="40">
       <td> <label style="padding-right: 10px;"><b><spring:message code="label.t22.vdEventHeatNo"/></b></label></td>
       <td>
       <input name="event_heat_no"  type="text" id="t22_event_heat_no"  class="easyui-textbox" data-options="editable:false"></td>
       
       <td> 
       <label style="padding-right: 10px"><b><spring:message code="label.t22.vdEventAimPsn"/></b></label></td>
       <td>
       <input name="event_aim_psn"  type="text" id="t22_event_aim_psn"  class="easyui-textbox" data-options="editable:false"></td>
       
    </tr>   
      	</table>
        
        <br>   
      
       <table id="t22_vd_events_tbl_id"  toolbar="#t22_vd_events_form_btn_div_id"  title="<spring:message code="label.t22.vdEvents"/>" class="easyui-datagrid" style="width:80%;"
           iconCls='icon-ok' maximizable="false"  fitColumns="true"  resizable="true" remoteSort="false"  rownumbers="true" singleSelect="true"> 
       
        <thead>
            <tr>
             <th field="event_id" formatter="(function(v,r,i){return formatColumnData('eventName',v,r,i);})" sortable="false" width="200"><b><spring:message code="label.t22.vdEventName"/></b></th>
              <th field="event_date_time" sortable="false" editor="{type:'datetimebox',options:{editable:true}}" formatter="(function(v,r,i){return formatDateTime('event_date_time',v,r,i)})"  width="150"><b><spring:message code="label.t22.vdEventDate"/></b></th>
                <th field="heat_proc_event_id"  hidden="true" width="0"><b><spring:message code="label.t22.pkId"/></b></th>
              <th field="event_unit_seq" hidden="true" width="0"><b><spring:message code="label.t22.pkId"/></b></th>
         </tr>
         </thead>
    </table>
   
    <br>
         	<div align="center" id="t22_vd_events__form_btn_div_id">
         <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="saveT22EventDtls()" id="T22SaveBtn" style="width:90px">Save</a>
         <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload" onclick="cancelT22EventDtls()" style="width:90px">Refresh</a>
         <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="dialogBoxClose('t22_vd_events_form_div_id')" style="width:90px">Close</a>
    </div>
        </form>
    </div>
   
 <!-- Events Button Screen End -->
 
 
 
 
  <!-- Chemical Details Screen Start -->
   
         
	<div id="t22_vd_Chemistry_form_div_id" class="easyui-dialog" style="height:600px;padding:5px 5px;" closed="true">
		<form id="t22_vd_Chemistry_form_id" method="post" novalidate>
        <input name="t22_sample_si_no"  type="hidden" id="t22_sample_si_no">
        <input name="t22_chem_level"  type="hidden" id="t22_chem_level">      
       	<table style="width: 100%;padding-left: 20px;padding-right: 20px;margin-left:15px" class="easyui-panel" >
	        <tr height="40">
       			<td style="width: 120px;"><label style="padding-right: 5px;display:block; width:x; height:y; text-align:left;">
       				<b><spring:message code="label.t17.lrfChemHeatNo"/></b></label></td>
       			<td><input name="heat_no"  type="text" id="t22_heat_no"  class="easyui-textbox" data-options="editable:false" style="width:90px"></td>
       			<td style="width: 100px;"><label style="padding-right: 5px;display:block; width:x; height:y; text-align:right;">
       				<b><spring:message code="label.t17.lrfChemAimPsn"/></b></label></td>
				<td><input name="aim_psn"  type="text" id="t22_che_aim_psn"  class="easyui-textbox" data-options="editable:false" style="width:150px"></td >
        		<!-- <td style="width: 100px;"> 
        		<label style="padding-right: 5px;display:block; width:x; height:y; text-align:right;" ><b><spring:message code="label.t17.lrfChemAnalysis" /></b></label>
       			</td><td>
                	<input name="analysis_type"  type="text" id="t22_analysis_type"  class="easyui-combobox"  data-options="panelHeight: 'auto',editable:true,required:true,
                    valueField:'keyval',                    
                    textField:'txtvalue'" style="width:150px">
       			</td>  -->
        		<td style="width: 100px;">
        		<label style="padding-right: 0px;display:block; width:x; height:y;text-align:right;"><b><spring:message code="label.t17.lrfChemSampleno"/></b></label>
        		</td><td style="text-align:left;">
         		<input name="sample_no"  type="text" id="t22_sample_no" class="easyui-combobox" data-options="readonly:false,required:false,valueField:'keyval',                    
                    textField:'txtvalue',hasDownArrow:false">
        		</td>
         		<td colspan="2">
       	  			<a href="javascript:void(0)" class="easyui-linkbutton" id="t22_getSample_btn" iconCls="icon-ok" onclick="GetSample()" >Generate Sample No</a>
       				<a href="javascript:void(0)" id="t22_spectro"  class="easyui-linkbutton" iconCls="icon-ok" onclick="lrf_level2_server()" style="width:30px"></a>
  
       			</td>
    		</tr> 
	        <tr height="40">  
         		<td style="width: 100px;">
        			<label style="padding-right: 5px;display:block; width:x; height:y; text-align:left;"><b><spring:message code="label.t17.lrfChemSampleTemp"/></b></label>
        		</td><td>
         			<input name="sample_temp"  type="text" id="t22_sample_temp"  class="easyui-numberbox" data-options="editable:true,required:true" style="width:90px">
        		</td>
         		<td style="width: 100px;"> 
        			<label style="padding-right: 0px;display:block; width:x; height:y; text-align:right;"><b><spring:message code="label.t17.lrfChemSampleDate"/></b></label></td><td>
         			<input name="sample_date_time"  type="text" id="t22_sample_date_time"  class="easyui-datetimebox" data-options="required:true,showSeconds:false" style="width:150px">
        		</td>
		         <td style="width: 100px;"> 
        			<label style="padding-right: 0px;display:block; width:x; height:y; text-align:right;"><b><spring:message code="label.t17.lrfChemSampleResult"/></b></label>
        		</td><td>
                	<input name="sample_result"  type="text" id="t22_sample_result"  class="easyui-combobox"  data-options="panelHeight: 'auto',editable:true,required:true,
                    valueField:'keyval',                    
                    textField:'txtvalue'" style="width:150px">
       			</td>
       			<!-- <td><a href="javascript:void(0)" class="easyui-linkbutton" id="t22_gradeChange_btn" iconCls="icon-ok" onclick="gradeChange()" style="margin-left: 0px">Grade Change</a></td>
       			<td>
        			<a href="javascript:void(0)" class="easyui-linkbutton" id="t22_gradeFinalize_btn" iconCls="icon-ok" onclick="gradeFinalize()" style="margin-left: 0px">Grade Finalize</a>
		        </td> -->
        	</tr>
        	<tr height="40">
				<td> 
        			<label style="padding-right: 5px;display:block; width:x; height:y; text-align:left;"><b><spring:message code="label.t17.lrfChemRemarks"/></b></label>
       			</td><td  colspan="8">
         			<input name="remarks"  type="text" id="t22_remarks"  data-options="multiline:true" style="height:30px;width: 100%" class="easyui-textbox">
		        </td>
	        </tr>
        </table>
        <br>   
       	<table style="width: 100%;">
		<tr>
		<td width="70%">
		<div title="<spring:message code="label.t17.lrfChemDetails"/>" style="padding:10px;height: 300px">
       	<table id="t22_vd_Chemistry_tbl_id" toolbar="#t22_vd_Chemistry__form_btn_div_id"  title="<spring:message code="label.t17.lrfChemDetails"/>" class="easyui-datagrid" style="width:100%;height:auto;"
           iconCls='icon-ok' maximizable="true" nowrap="false" resizable="true" remoteSort="false"  rownumbers="true" singleSelect="true" > 
        <thead>
            <tr>
              <th rowspan="2" field="element" formatter="(function(v,r,i){return formatColumnData('elementName',v,r,i);})" sortable="false" width="100"><b>ELEMENT</b></th>
              <th rowspan="2" field="uom" formatter="(function(v,r,i){return formatColumnData('uom',v,r,i);})" sortable="false" width="50"><b><spring:message code="label.t5.uom"/></b></th>
              <th colspan="6"><b>PSN VALUES</b></th>
			</tr>
            <tr>
              <th field="min_value" width="100"><b>MIN_VALUE</b></th>
              <th field="max_value" width="100"><b>MAX_VALUE</b></th>
              <th field="psn_aim_value" width="100"><b>AIM_VALUE</b></th>
              <th field="aim_value" align="right"  editor="{type:'numberbox',options:{precision:4,min:0}}"  width="120"><b>ACTUAL_VALUE<font color="red">*</font></b></th>
              <th field="dtls_si_no" sortable="true" hidden="true" width="0"><b><spring:message code="label.t7.pkId"/></b></th>
         		 <th field="remarks" sortable="false" align="left" width="165" hidden="true"><b>SPECTRO LAB REMARKS</b></th>
         	</tr>
        </thead>
    	</table>
    	</div>
    	</td>
		<td width="30%">
    	<!-- second grid -->
		<div title="Samples" style="padding: 10px; height: 300px">					
			<table id="t22_vd_chem_samp_tbl_id"
							toolbar="#t22_vd_Chem_Sample_form_btn_div_id"
							title="List of Samples"
							class="easyui-datagrid" style="width: 100%"
							iconCls='icon-ok' maximizable="false" resizable="true"
							remoteSort="false" rownumbers="true" singleSelect="true">
				<thead>
					<tr>
						<th field="sample_no" width="140"><b>Sample No</b></th>
						<th field="action" width="140" formatter="viewT22SampleDtls"><b>Action     </b></th>
						<th field="sample_si_no" hidden="true" width="0"><b>Chem Header Id</b></th>
						<th field="sample_temp" hidden="true" width="0"><b>Samp Temp</b></th>
						<th field="sample_result" hidden="true" width="0"><b>Samp Result</b></th>
						<th field="sample_date_time" formatter="(function(v,r,i){return formatDateTime('sample_date_time',v,r,i)})"
						hidden="true" width="0"><b>Samp Date</b></th>
						<th field="remarks" hidden="true" width="0"><b>remarks</b></th>
						<th field="final_result" hidden="true" width="0"><b>Final Result</b></th>
					</tr>
				</thead>
			</table>
		</div>
   		</td>
		</tr>
		</table> 
         <div id="t22_vd_Chemistry__form_btn_div_id">
         	<a href="javascript:void(0)" class="easyui-linkbutton" id="t22_save_chem_btn_id" iconCls="icon-save" onclick="saveT22ChemistryDtls()" style="width:90px">Save</a>
        	<!-- <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload" onclick="cancelT22ChemistryDtls()" style="width:90px">Refresh</a> -->
         	<a href="javascript:void(0)" class="easyui-linkbutton" id="t22_close_chem_btn_id" iconCls="icon-cancel" onclick="dialogBoxClose('t22_vd_Chemistry_form_div_id')" style="width:90px">Close</a>
    	</div>
    	<div id="t22_vd_Chem_Sample_form_btn_div_id">
			<a href="javascript:void(0)" class="easyui-linkbutton" id="t22_final_result_btn_id" onclick="saveFinalResult()"
				style="width: 90px">Final Result</a>
		</div>	
        </form>
    </div>
    
    
 <!-- Chemical Details Screen End -->
 
 
 
 
 
 
 
 <!-- Delay Entry Start-->
       <div id="t22_delay_entry_form_div_id" class="easyui-dialog" style="height:450px;margin:5px;" 
            closed="true" data-rowchange="0">
             
        <form id="t22_delay_entry_form_id" method="post" novalidate>
       
       
       <table style="width: 100%" class="easyui-panel" >
       
        <!-- first row -->
        <tr>
        <td>
        <label>HEAT NO</label>
        
         <input name="heat_no"  type="text" id="t22_delay_heat_no"  class="easyui-textbox" data-options="editable:false">
        </td>
        
       <td> 
        <label>AIM PSN</label>
         <input name="aim_psn"  type="text" id="t22_delay_aim_psn"  class="easyui-textbox" data-options="editable:false">
         
        </td>         
        </tr>
      </table>
       
       <table id="t22_delay_entry_tbl_id"  toolbar="#t22_delay_entry_form_btn_div_id"  title="Delay Details Entry" class="easyui-datagrid" style="width:90%;height:auto;"
           iconCls='icon-ok' maximizable="false" fitColumns="true"  resizable="true" remoteSort="false"  rownumbers="true" singleSelect="true"> 

        <thead>
            <tr>         
              <th field="activity_master.activities" formatter="(function(v,r,i){return formatColumnData('activity_master.activities',v,r,i);})"  sortable="false" width="80">Activity</th>
              <th field="activity_master.delay_details" formatter="(function(v,r,i){return formatColumnData('activity_master.delay_details',v,r,i);})" sortable="false" width="80" >Delay Details</th>
              <th field="start_time" sortable="false" formatter="(function(v,r,i){return formatDateTime('start_time',v,r,i)})" width="80">Start Time</th>
              <th field="end_time" sortable="false" formatter="(function(v,r,i){return formatDateTime('end_time',v,r,i)})" width="80">End Time</th>
              <th field="duration" sortable="false" width="40" >Duration(min)</th>
              <th field="activity_master.std_cycle_time" formatter="(function(v,r,i){return formatColumnData('activity_master.std_cycle_time',v,r,i);})" sortable="false"  width="40">Standard Duration(min)</th>
              <th field="delay" sortable="false"  width="40">Delay(min)</th>
               <th field="corrective_action" sortable="false"  width="80" editor="{type:'textbox'}">Delay Reason</th>
              	
            </tr>
         </thead>
    </table>
    <br>
    <div id="t22_delay_entry_form_btn_div_id">
         <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="saveT22DelaytDtls()" style="width:90px">Save</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload" onclick="getT22DelayDetails()" style="width:90px">Refresh</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload" onclick="t221addDelayDetails()" style="width:150px">Add Delay Details</a>
         <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="dialogBoxClose('t22_delay_entry_form_div_id')" style="width:90px">Close</a>
    </div>
        </form>
    </div>
    
      <!-- Details Entry -Sub -->
       
     <div id="t221_delay_entry_form_div_id" class="easyui-dialog" style="height:450px;margin:5px;" 
            closed="true" data-change="0">
             
       <form id="t221_delay_entry_form_id" method="post" novalidate>
       <input name="t221activity_value"  type="hidden" id="t221activity_value"  >
       <input name="t221delay_details"  type="hidden" id="t221delay_details"  >
       <input name="t221delay"  type="hidden" id="t221delay"  >
       <input name="trans_delay_dtl_id_value"  type="hidden" id="trans_delay_dtl_id"  >
       
       <table style="width: 100%" class="easyui-panel" >
       
        <!-- first row -->
        <tr>
        <td>
        <label>HEAT NO</label>
        
         <input name="heat_no"  type="text" id="t221_delay_dtls_heat_no"  class="easyui-textbox" data-options="editable:false">
        </td>
        
       <td> 
        <label>AIM PSN</label>
         <input name="aim_psn"  type="text" id="t221_delay_dtls_aim_psn"  class="easyui-textbox" data-options="editable:false">
         
        </td>   
         <td> 
        <label>Activity</label>
         <input name="aim_psn"  type="text" id="t221_delay_dtls_activity"  class="easyui-textbox" data-options="editable:false">
         
        </td>         
        </tr>
      </table>
       
       <table id="t221_delay_entry_tbl_id"  toolbar="#t221_delay_entry_form_btn_div_id" 
        title="Delay Details Entry" class="easyui-datagrid" style="width:90%;height:auto;"
        iconCls='icon-ok' maximizable="false" fitColumns="true" 
        data-options="fitColumns:true,singleSelect:true" 
        resizable="true" remoteSort="false"  rownumbers="true" singleSelect="true" data-delay="0"> 

        <thead>
            <tr>      
             <th field="activities"  formatter="(function(v,r,i){return formatT22ActivityColumnData('activities',v,r,i);})"  sortable="false" width="60">Activity</th>
              <th field="delay_details"   formatter="(function(v,r,i){return formatT22DlyDtlsColumnData('delay_details',v,r,i);})" sortable="false" width="80" >Delay Details</th>
              
              <th field="delay_reason" width="60"
					formatter="(function(v,r,i){return formatColumnData('delayResonMstrMdl.delay_reason_desc',v,r,i);})"
					editor="{type:'combobox',options:{required:true,editable:true,valueField:'keyval',textField:'txtvalue',
					method:'get',url:'./CommonPool/getComboList?col1=delay_reason_id&col2=delay_reason_desc&classname=DelayReasonMasterModel&status=1&wherecol= delay_status='}}">Reason</th>
              <th field="delay_agency" width="50"
					formatter="(function(v,r,i){return formatColumnData('delayAgencyMstrMdl.agency_desc',v,r,i);})"
					editor="{type:'combobox',options:{required:true,editable:true,valueField:'keyval',textField:'txtvalue',
					method:'get',url:'./CommonPool/getComboList?col1=delay_agency_id&col2=agency_desc&classname=DelayAgencyMasterModel&status=1&wherecol= agency_status='}}">Agency</th>
        
               <th field="delay_dtl_duration" sortable="false"  width="45" editor="{type:'numberbox'}">Delay (Minutes)</th>
               <th field="comments" sortable="false"  width="150" editor="{type:'textbox'}">Comments</th>
               <th field="trans_delay_dtl_id" hidden="true"  width="0">edit</th>
              	
            </tr>
         </thead>
    </table>
    <br>
    
   
    <div id="t221_delay_entry_form_btn_div_id">
    
     <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true"
			onclick="javascript:$('#t221_delay_entry_tbl_id').edatagrid('addRow')" ><b>Add</b></a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-save" plain="true"
			onclick="javascript:$('#t221_delay_entry_tbl_id').edatagrid('saveRow')"><b>Save</b></a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-reload"
			plain="true" onclick="t221ReferenshData()"><b>Refresh</b></a>
    
    </div>
        </form>
    </div>
 
 <!-- End -->
 
 
<!-- VD PSNDOC Button Screen  Begin -->
  <div id="t22_PSN_Docs_form_div_id" class="easyui-dialog" style="height:550px;width:auto; padding:10px 10px 10px 10px"  closed="true">
       <form id="t22_PSN_Docs_form_id" method="post" novalidate>
        <p style="padding-left: 400px;"><b>PSN NUMBER</b> <input name="t22_psn_no_docs"  type="text" id="t22_psn_no_docs"  class="easyui-textbox" data-options="editable:false"></p> 
        <table id="t22_PSN_Docs_tbl_id" title="PSN Docs Details View" class="easyui-datagrid" style="width:100%;height:auto;padding: 20px 20px 20px 20px;" 
            pagination="true" maximizable="true"  resizable="true" remoteSort="false" pageSize="20"  rownumbers="true" fitColumns="true" singleSelect="true">
        <thead>
        <tr>
          <th field="doc_sl_no" hidden="true"  editor="{type:'validatebox'}"><b>DOC_SL_NO</b></th>
          <th field="psn_hdr_sl_no" hidden="true"  editor="{type:'validatebox'}"><b>PSN_HDR_SL_NO</b></th>
          <th field="upload_file_name" width="100"><b>FILE NAME</b></th>
          <th field="description" width="100"><b>DESCRIPTION</b></th>
          <th field="action" width="80" align="center" formatter="t22viewPSNFile"><b>Action</b></th>
         </tr>
        </thead>
           
           </table>
           <div  align="center" id="t22_PSN_Docs_btn_div_id">
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload" onclick="cancelT22PSNDocs()" style="width:90px">Refresh</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="dialogBoxClose('t22_PSN_Docs_form_div_id')" style="width:90px">Close</a>
           </div>
           </form>
            
            
    </div>
    <!-- VD PSNDOC Button Screen End -->
<!-- Process Parameter Button Screen Start -->
  <div id="t22_process_param_form_div_id" class="easyui-dialog" style="height:550px;padding:10px 10px" closed="true">
            
   <div id="t12_process_param_tabs_div_id" class="easyui-tabs"  pill="true" style="width: 100%;height: auto;">
     
     <div title="PROCESS PARAMETERS" style="padding:10px">
     <table style="width: 100%" class="easyui-panel" >
       
        <!-- first row -->
        <tr>
        <td>
        <label>HEAT NO</label>
        
         <input name="heat_no"  type="text" id="t22_proc_heat_no"  class="easyui-textbox" data-options="editable:false">
        </td>
        
       <td> 
        <label>AIM PSN</label>
         <input name="aim_psn"  type="text" id="t22_proc_aim_psn"  class="easyui-textbox" data-options="editable:false">
         
        </td>
         
        </tr>
      </table>
     <table id="t22_process_param_tbl_id" toolbar="#t22_process_param_form_btn_div_id"  title="PROCESS PARAMETERS" class="easyui-datagrid" style="width:100%;height:auto;"
           iconCls='icon-ok' maximizable="true"  resizable="true" remoteSort="false"  rownumbers="true" singleSelect="true"> 
        <thead>
             <tr>
              	<th field="param_id" formatter="(function(v,r,i){return formatColumnData('proc_para_name',v,r,i);})" sortable="false" width="150"><b>PARAMETERS</b></th>
               	<th field="uom" formatter="(function(v,r,i){return formatColumnData('uom',v,r,i);})" sortable="false" width="150"><b>UOM</b></th>
             	<th field="param_value_min" align="right" width="100" ><b>MIN_VALUE</b></th>
             	<th field="param_value_max" align="right" width="100" ><b>MAX_VALUE</b></th>
               	<th field="param_value_aim" align="right" width="150"><b>AIM_VALUE</b></th>
                <th field="param_value_actual" align="right" data-options="editor:{type:'numberbox',options:{precision:4,min:0}}"  width="150"><b>ACT_VALUE<font color="red">*</font></b></th>
              	<th field="process_date_time" sortable="true" editor="{type:'datetimebox',options:{required:false,editable:true}}" formatter="(function(v,r,i){return formatDateTime('process_date_time',v,r,i)})"  width="150"><b>PROCESS DATE</b></th>
              	<th field="proc_param_event_id" sortable="true" hidden="true" width="0"><b>PROCESS_ID</b></th>
         </tr>
         </thead>
    </table>
      <div id="t22_process_param_form_btn_div_id">
         <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="saveT22ProcessParamDtls()" style="width:90px">Save</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload" onclick="cancelT22ProcParamDtls()" style="width:90px">Refresh</a>
         <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="dialogBoxClose('t22_process_param_form_div_id')" style="width:90px">Close</a>
    	</div>
    	
    	</div>  
    	</div>  
    	</div>  
  <!-- End Process parameter Button -->
  
    <!-- Begin Additions Form  -->
  <div id="t30_arc_det_form_div_id" class="easyui-dialog" style="height:550px;padding:10px 10px;" closed="true" data-change="0">
  	<form id="t30_arc_det_form_id" method="post" novalidate>
		<input name="t30_heat_cnt"  type="text" id="t30_heat_cnt"  hidden="true">
		<input name="t30_arc_sl_no"  type="text" id="t30_arc_sl_no"  hidden="false" >
		<table style="width: 100%;padding-left: 20px;padding-right: 20px;"  class="easyui-panel">
        <tr height="40">
        <td>
        <label style="display:block; width:x; height:y; text-align:left;"><b><spring:message code="label.t15.heatNo"/></b></label></td>
        <td>
        <input name="t30_heat_no"  type="text" id="t30_heat_no"  class="easyui-textbox" data-options="editable:false" style="width:120px;text-align:left;">
        </td>
        <td>
        <label style="display:block; width:x; height:y; text-align:right;"><b>Unit</b></label></td>
        <td>
        <input name="t30_unit"  type="text" id="t30_unit"  class="easyui-textbox" data-options="editable:false" style="width:80px">
        </td>
        <td>
        <label style="display:block; width:x; height:y; text-align:right;"><b>PSN</b></label></td>
        <td>
        <input name="t30_psn_no"  type="text" id="t30_psn_no"  class="easyui-textbox" data-options="editable:false" style="width:120px">
        </td>
        <!-- 
        <td> 
       	<label style="padding-right: 10px"><b><spring:message code="lable.t15.sampleNo"/></b></label>
       	</td>
       	<td>
       	<input name="t15_sample_no"  type="text" id="t15_sample_no"  class="easyui-combobox"  data-options="panelHeight: 'auto',editable:true,required:true,
                    valueField:'keyval',                    
                    textField:'txtvalue'">
                    
                    <a id="sample_add_btn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'"></a>
       	</td> -->
       	
       	<%-- <td style="width: 100px"> 
       	<label style="display:block; width:x; height:y; text-align:right;"><b><spring:message code="label.t15.arcStartDate"/></b></label></td>
       	<td>
       	<input name="t15_arc_start_date" type="text" id="t15_arc_start_date" formatter="formatDate('t15_arc_start_date')"
       	 class="easyui-datetimebox" data-options="required:true,showSeconds:false" style="width:125px">
       	</td> --%>
       	
       	<!-- 
       	<td style="width: 100px"> 
       	<label style="display:block; width:x; height:y; text-align:right;"><b><spring:message code="label.t15.temp"/></b></label></td>
       	<td>
       	<input name="t15_emp"  type="text" id="t15_temp"  class="easyui-numberbox" data-options="editable:true">
       	</td>   -->
       	
       	<%-- <td style="width: 100px">
       <label style="display:block; width:x; height:y; text-align:right;"><b><spring:message code="label.t15.arcEndDate"/></b></label></td><td>
       <input name="t15_arc_end_date"  type="text" id="t15_arc_end_date"  
       	class="easyui-datetimebox" data-options="required:true,showSeconds:false" style="width:125px">        
       </td>
       <td style="width: 60px;text-align:left"> 
       <label style="display:block; width:x; height:y; "><b><spring:message code="lable.t15.consumption"/></b></label></td>
       <td>
       <input name="t15_consumption"  type="text" id="t15_consumption"  class="easyui-numberbox" data-options="editable:true" style="width:120px">
       </td> --%>
       
        </tr>
       <!-- <tr>
        <td style="width: 100px"> 
       	<label style="display:block; width:x; height:y; text-align:right;"><b><spring:message code="lable.t15.additionType"/></b></label></td>
       	<td colspan="2">
       	<input name="t15_addition_type"  type="text" id="t15_addition_type" hidden="true" class="easyui-combobox"  data-options="editable:false,required:true,
                    valueField:'keyval',                    
                    textField:'txtvalue'" style="width:120px">
       	</td> 
       </tr>-->
      </table>
     
      <br>
      
    <%--   <div title="<spring:message code="label.t15.lrfArchDetails"/>" style="padding:10px;"> --%>
       	<%-- <table id="t30_vd_add_tbl_id" toolbar="#t15_lrf_arc_add_form_btn_div_id"  title="<spring:message code="label.t15.lrfArchDetails"/>" class="easyui-datagrid" 
           maximizable="true"  resizable="true" remoteSort="false"  rownumbers="true" singleSelect="true" data-options="fitColumns:true" style="width: 100%; height: auto;">  --%>

    <table id="t30_vd_add_tbl_id"  toolbar="#t30_vd_add_form_btn_div_id"  title='<spring:message code="label.t15.lrfArchDetails"/>' class="easyui-datagrid"
           iconCls='icon-ok'   rownumbers="true" singleSelect="true"  style="width:70%;height: 230px;"
           data-options=" fitColumns:true, singleSelect:true"> 
         <thead> 
		 <tr>
              <th  field="material_name" ><b>Material Name</b></th>
              <th field="sap_matl_id" ><b>SAP Material Id</b></th> 
              <th field="valuation_type"><b>Valuation Type</b></th> 
              <th field="consumption_qty" data-options="editor:{type:'numberbox',options:{min:0,precision:2}}" sortable="false"   ><b>Quantity</b></th> 
              <th field="uom" formatter="(function(v,r,i){return formatColumnData('uom',v,r,i);})" sortable="false"><b>UOM</b></th>
              <th field="addition_date_time"  editor="{type:'datetimebox',options:{required:true,editable:true}}" 
                  formatter="(function(v,r,i){return formatDateTime('addition_date_time',v,r,i)})"sortable="false"  ><b>Consumption Date<font color="red">*</font></b></th> 
              <th field="material_id" hidden="true" ><b>MatId</b></th>
              <th field="cons_sl_no" hidden="true" ><b>ConPk</b></th>
              <th field="material_type" hidden="true"><b>Mat Type</b></th>              
         </tr>
         </thead>  
    	</table> 
    	
    <div id="t30_vd_add_form_btn_div_id">
    	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="saveT30VDAdditions()" style="width:90px">Save</a>
    	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload" onclick="cancelT15ArcDet()" style="width:90px">Refresh</a>
    	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload" onclick="viewT30VDAdditions()" style="width:90px">Display</a>
    	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-tip" onclick="viewT30LRFDtls()" style="width:120px">LRF Details</a>
   		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" id="temp1" onclick="dialogBoxClose('t30_arc_det_form_div_id')" style="width:90px">Close</a> <!--  dialogBoxClose('t30_arc_det_form_div_id') -->
    </div>
   <!--  dialogBoxCloseAfterConfirmation('t30_arc_det_form_div_id') -->
   <!--  </div> -->
   	</form>
   	<div align="center" id="t30_vd_disp_div_id" style="visibility: hidden">
   	 <table id="t30_vd_arc_disp" title="VD Addition Summary Details" class="easyui-datagrid" style="width: 100%;padding-left: 20px;padding-right: 20px;"  rownumbers="true" iconCls="icon-ok">
  
</table>
</div>
 <div id="t30_reuse_arc_det_div" class="easyui-dialog" style="height:500px;width:90%" closed="true" data-change="0"> 
<table id="t15_reuse_lrf_arc_add_tbl_id"  toolbar=""  title='<spring:message code="label.t15.lrfArchDetails"/>' class="easyui-datagrid"
           iconCls='icon-ok'   rownumbers="true" singleSelect="true"  style="width:60%;height:50%;"
           data-options=" fitColumns:true, singleSelect:true"> 
         <thead> 
		 <tr>
              <th  field="material_name" ><b>Material Name</b></th>
              <th field="sap_matl_id" ><b>SAP Material Id</b></th> 
              <th field="valuation_type"><b>Valuation Type</b></th> 
              <th field="consumption_qty" data-options="editor:{type:'numberbox',options:{min:0,precision:2}}" sortable="false"   ><b>Quantity</b></th> 
              <th field="uom" formatter="(function(v,r,i){return formatColumnData('uom',v,r,i);})" sortable="false"><b>UOM</b></th>
              <th field="addition_date_time"  editor="{type:'datetimebox',options:{required:true,editable:true}}" 
                  formatter="(function(v,r,i){return formatDateTime('addition_date_time',v,r,i)})"sortable="false"  ><b>Consumption Date<font color="red">*</font></b></th> 
              <th field="material_id" hidden="true" ><b>MatId</b></th>
              <th field="cons_sl_no" hidden="true" ><b>ConPk</b></th>
              <th field="material_type" hidden="true"><b>Mat Type</b></th>              
         </tr>
         </thead>  
    	</table> 
<table id="t15_reuse_lrf_arc_disp" title="LRF ARC Addition Details" class="easyui-datagrid" style="width: 100%;height:50%;padding-left: 20px;padding-right: 20px;"  rownumbers="true" iconCls="icon-ok">
  </table>
 </div> 
 </div>
	</div>
	
     <!-- End Additions Form  -->
  
  
  
  
     <!-- Start  VD Dispatch Button -->
       <div id="t22_vd_dispatch_div_id" class="easyui-dialog" style="height:250px;width:400px;" closed="true">
     <form id="t22_vd_dispatch_form_id" method="post"  >
		<table   style="width: 100%;">
		
			<tr style="height: 30px;">
        		<td  style="width: 100px;padding-right:20px" align="right" > 
       			<label><spring:message code="label.t22.vdDispatchWgt"/></label>
        		</td>
       			<td style="width: 150px;" > 
         		<input name="vdDispatchWgt"  type="text" id="t22_vdDispatchWgt"  class="easyui-numberbox" data-options="required:true,precision:2,validType:{length:[1,10]},editable:false">
       			</td>
       			</tr>
       			
       			<tr style="height: 30px;">
        		<td  style="width: 100px;padding-right:20px" align="right" > 
       			<label><spring:message code="label.t22.vdDispatchStartTemp"/></label>
        		</td>
       			<td style="width: 150px;" > 
         		<input name="vdDispatchStartTemp"  type="text" id="t22_vdDispatchStartTemp"   class="easyui-numberbox" data-options="required:true,precision:2,validType:{length:[1,10]},editable:true">
       			</td>
       			</tr>
       			
       			<tr style="height: 30px;">
        		<td  style="width: 100px;padding-right:20px" align="right" > 
       			<label><spring:message code="label.t22.vdDispatchTemp"/></label>
        		</td>
       			<td style="width: 150px;" > 
         		<input name="vdDispatchTemp"  type="text" id="t22_vdDispatchTemp"   class="easyui-numberbox" data-options="required:true,precision:2,validType:{length:[1,10]},editable:true">
       			</td>
       			</tr>
       			
       			<tr style="height: 30px;">
        		<td  style="width: 100px;padding-right:20px" align="right" > 
       			<label><spring:message code="label.t22.vdDispatchTo"/></label>
        		</td>
       			<td style="width: 150px;" > 
       			<input name="dispatch_to"  type="text" id="t22_dispatchTo"  class="easyui-combobox"  data-options="panelHeight: 'auto',editable:false,required:true,
                    valueField:'keyval',                    
                    textField:'txtvalue'">
       			</td>
       			</tr>
       			<tr style="height: 30px;">
        		<td  style="width: 100px;padding-right:20px;" align="right" > 
       			<label><spring:message code="label.t22.remarks"/></label>
        		</td>
       			<td style="width: 150px;" > 
         		<input name="remarks"  type="text" id="t22_remarks"  class="easyui-textbox" data-options="validType:{length:[1,50]}"  multiline="true" style="height:40px;align:top"  >
       			</td>
       			</tr>
   </table>
 
    <br>
         	<div align="center" id="t22_vd_dispatch_form_btn_div_id">         
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload" onclick="resetForm()" style="width:90px">Refresh</a>
         <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="saveT22VdDispatch()" style="width:90px">Save</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="dialogBoxClose('t22_vd_dispatch_div_id')" style="width:90px">Close</a>
    </div>
        </form>
    </div>
     
     <!-- end VD Dispatch Button -->
  <script>
  
  $('#t22_unit').combobox({
		 onSelect: function(record){
			clearT22HeatHdrform();
		    getT22UpdatedHeatList();
			 $("#T22ladleDetailsId").show();
			 $('#t22_vd_save_btn_id').linkbutton('disable');
			 
			}
			});
  $('#t22_recDate').datetimebox({
		
	    value: (formatDate(new Date())) 
	   
	});
  
  var recdate= ($('#t22_recDate').datetimebox('getValue'));
	 $('#t22_shift').combobox({
		 onLoadSuccess: function(){  
	 	
	    		value: autoShift(recdate,'t22_shift',$('#t22_shift').combobox('getData'));
	
		  }
	}); 

	 	
			$('#t22_recDate').datetimebox({
		onChange: function(date){
	 		 	
	 		 			 value: autoShift(($('#t22_recDate').datetimebox('getValue')),'t22_shift',$('#t22_shift').combobox('getData')); 
	 		 	
	 		}
			});
  function callT22Dropdowns()
  {
	 	var user_id= <%=session.getAttribute("USER_APP_ID") %>  
	 	
  	var url1="./CommonPool/getComboList?col1=lookup_id&col2=lookup_value&classname=LookupMasterModel&status=1&wherecol=lookup_type='SHIFT' and lookup_status=";
  	var url2="./CommonPool/getComboList?col1=sub_unit_id&col2=subUnitMstrMdl.sub_unit_name&classname=UserSubUnitMasterModel&status=1&wherecol=app_user_id="+user_id+" and subUnitMstrMdl.unitDetailsMstrMdl.unit_name='VD' and record_status=";
  	var url3="./CommonPool/getComboList?col1=user_role_id&col2=appUserAccountDetails.user_name&classname=UserRoleMapMasterModel&status=1&wherecol=lookupMasterModel.lookup_code='VD_SHIFT_INCHARGE' and lookupMasterModel.lookup_id=lookup_id  and record_status=";
  	//var url4="./CommonPool/getComboList?col1=user_role_id&col2=appUserAccountDetails.user_name&classname=UserRoleMapMasterModel&status=1&wherecol=lookupMasterModel.lookup_code='VD_SECTION_INCHARGE' and lookupMasterModel.lookup_id=lookup_id  and record_status=";
 	var url5="./CommonPool/getComboList?col1=user_role_id&col2=appUserAccountDetails.user_name&classname=UserRoleMapMasterModel&status=1&wherecol=lookupMasterModel.lookup_code='VD_OPERATOR' and lookupMasterModel.lookup_id=lookup_id  and record_status=";
  	
  	getDropdownList(url1,'#t22_shift');
  	getDropdownList(url2,'#t22_unit');
  	getDropdownList(url3,'#t22_shiftMgr');
  	//getDropdownList(url4,'#t22_vdInCharge');
  	getDropdownList(url5,'#t22_vdOperator');
  	
  	
 //var url6="./CommonPool/getComboList?col1=trns_si_no&col2=heat_id&classname=VDHeatDetailsModel&status=1&wherecol=dispatch_to_unit=NULL and record_status=";
 //getDropdownList(url6,'#t22_heat_id');
  }

 
  </script>
       <style type="text/css">
        #t22_vd_form_id{
            margin:0;
            padding:10px 30px;
        }
        
    </style>
