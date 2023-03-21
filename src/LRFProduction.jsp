<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<script src="${pageContext.request.contextPath}/js/LRFOperationDtls.js"></script>
<script  src="${pageContext.request.contextPath}/js/PSNReportDisplay.js"></script>
<div class="easyui-accordion" data-options="multiple:true" style="width:100%;height: auto">
 
<div title="Crew Details" data-options="iconCls:'icon-ok'"  style="padding-top: 10px;padding-left: 10px;padding-right: 10px;">
    
    <form id="t13_crew_form_id" method="post" novalidate> 
    <table style="width: 100%;"   class="easyui-panel" >
       
        <!-- first row style="padding-right:40px"-->
        <tr style="height: 30px;">
        <td  style="width: 100px;"> 
        <label style="padding-right: 5px;display:block; width:x; height:y; text-align:right;"><spring:message code="label.t13.lrfProductionRecDate"/></label>
        </td>
        <td style="width: 150px;" > 
         <input name="recDate"  type="text" id="t13_recDate"  class="easyui-datetimebox" data-options="required:true,showSeconds:false"><!-- onSelect:callT13Dropdowns() -->
        </td>
        
        <td  style="width: 100px;"> 
        <label style="padding-right: 5px;display:block; width:x; height:y; text-align:right;" ><spring:message code="label.t13.lrfProductionShift"/></label>
        </td>
        <td style="width: 100px;"> 
         <input name="shift"  type="text" id="t13_shift"  class="easyui-combobox" data-options="required:true,panelHeight: 'auto',editable:false,valueField:'keyval',textField:'txtvalue'">
        </td>
        <td style="width: 60px;" > 
        <label style="padding-right: 5px;display:block; width:x; height:y; text-align:right;"><spring:message code="label.t13.lrfProductionUnit"/></label>
        </td>
        <td > 
         <input name="unit"  type="text" id="t13_unit"  class="easyui-combobox" data-options="required:true,panelHeight: 'auto',editable:false,valueField:'keyval',textField:'txtvalue'">
        </td>
        </tr>
        
        <tr style="height: 30px;">
        <td> 
        <label style="padding-right: 5px;display:block; width:x; height:y; text-align:right;"><spring:message code="label.t13_lrfInCharge"/></label>
        </td>
        <td> 
         <input name="shiftMgr"  type="text" id="t13_shiftMgr"  class="easyui-combobox" data-options="required:true,panelHeight: 'auto',editable:false,valueField:'keyval',textField:'txtvalue'">
        </td>
        <td style="width: 40px;"> 
        <label style="padding-right: 5px;display:block; width:x; height:y; text-align:right;"><spring:message code="label.t13_lrfEngineer"/></label>
        </td>
        <td> 
         <input name="lrfMgr"  type="text" id="t13_lrfInCharge"  class="easyui-combobox" data-options="required:true,panelHeight: 'auto',editable:false,valueField:'keyval',textField:'txtvalue'">
        </td>
        
        </tr>
        </table>
        </form>
     </div>
        <div title="<spring:message code="label.t13.lrfProductionHotMetalProd"/>" data-options="iconCls:'icon-ok'" style="padding-top: 10px;padding-left: 10px;padding-right: 10px;">
              <div id="T13ladleDetailsId">
            <table id="t13_LRF_production_tbl_id" toolbar="#t13_LRF_production_tbl_toolbar_div_id"  class="easyui-datagrid" style="width:100%;height: 200px;"
            url="./EOFproduction/getHeatsWaitingForLrfProcess?CURRENT_UNIT=LRF&UNIT_PROCESS_STATUS=WAITING FOR PROCESSING" method="get" iconCls='icon-ok'
             pagination="true" maximizable="true"  resizable="true" remoteSort="false" pageSize="10"
            rownumbers="true"  singleSelect="true"> 
         <thead>
            <tr>
                <th field="steel_ladle_name" sortable="false" width="50"><spring:message code="label.t13.ladleNo"/></th>
                 <th field="steel_ladle_no" hidden="true" width="30"><spring:message code="label.t13.ladleNo"/></th>
                <th field="tap_temp" sortable="false" width="70"><spring:message code="label.t13.tapTemp"/></th>
                <th field="heat_id" sortable="true" width="80"><spring:message code="label.t13.heatno"/></th> 
                <th field="aim_psn_char" sortable="true" width="120" > <spring:message code="label.t13.aimpsn"/></th>               
                <th field="tap_wt" sortable="true" width="70" > <spring:message code="label.t13.lsweight"/></th>
                <th field="heat_plan_id" sortable="true" width="0" hidden="true" > <spring:message code="label.t13.planNo"/></th>
                <th field="heat_plan_line_id" sortable="true" width="0" hidden="true"  > <spring:message code="label.t13.planLineNo"/></th>
                <th field="caster_type" sortable="true" width="50" > <spring:message code="label.t13.targetCaster"/></th>
                 <th field="ccm_sec_size" sortable="true" width="120" > Target Section</th>
                <th field="sub_unit_name" sortable="true" width="100" > <spring:message code="label.t13.prevoiusUnit"/></th>
                 <th field="dispatch_date"  width="120" formatter="(function(v,r,i){return formatDateTime('dispatch_date',v,r,i)})"> Last Unit Dispatch Date</th>
                <th field="heat_counter" sortable="true" hidden="true"></th>
                <th field="caster_id" sortable="true"  hidden="true"></th>
                <th field="sub_unit_id" sortable="true"  hidden="true"></th>
                <th field="act_proc_path" sortable="true" hidden="true"></th>
                <th field="heat_track_id" sortable="true" hidden="true"></th>
                <th field="aim_psn" sortable="true"   hidden="true"></th>
                
                <th field="eof_C" sortable="true" width="50" >C</th>
                <th field="eof_S" sortable="true" width="50" >S</th>
                <th field="eof_P" sortable="true" width="50" >P</th>
                <th field="eof_MN" sortable="true" width="50" >Mn</th>
                <th field="eof_Si" sortable="true" width="50" >Si</th>
                <th field="eof_Ti" sortable="true" width="50" >Ti</th>
                <th field="unit_process_status" sortable="true" width="180" >Status</th>
                <th field="trns_si_no" hidden="true" sortable="true" width="0" >prevUnitTrId</th>
                
         </tr>
         </thead>
    </table>
    </div>
    <br>
      <form id="t13_heat_hdr_form_id" method="post" >
     <!-- Hidden elements to carry id values -->
    <input name="aim_psn_id" type="hidden"  id="t13_aim_psn_id"/>
    <input name="heat_counter" type="hidden"  id="t13_heat_cnt"/>
     <input name="trns_sl_no"  type="hidden" id="t13_trns_si_no">
    <input name="caster_id" type="hidden"  id="t13_caster_id"/>
    <input name="sub_unit_id" type="hidden"  id="t13_sub_unit_id"/>
    <input name="act_path" type="hidden"  id="t13_act_path"/>
    <input name="heat_track_id" type="hidden"  id="t13_heat_track_id"/>
    <input name="steel_ladleno_id" type="hidden"  id="t13_steel_ladleno_id"/>
    <input name="plan_no" type="hidden"  id="t13_plan_no"/>
    <input name="plan_line_no" type="hidden"  id="t13_plan_line_no"/>
    <input name="t13_cstatus" type="hidden"  id="t13_cstatus"/>
    <input name="t25_steel_ladle02_hidden" type="hidden"  id="t25_steel_ladle02_hidden"/>
    <input name="reladling" type="hidden"  id="t13_reladling"/>
    <input name="lrf_disp_unit" type="hidden"  id="t13_lrf_disp_unit"/>
    <input name="lrf_entry" type="hidden"  id="t13_lrf_entry"/>
   
    
    <table style="width: 100%" class="easyui-panel" title="LRF Heat Details" data-options="
                tools:[{
                    iconCls:'icon-reload',
                    handler:function(){
                     getT13UpdatedHeatList();
 		    	clearT13HeatHdrform();
                    }
                }]
            ">    
    <tr style="height: 30px;">
        <td style="width: 100px;"> 
        <label  style="padding-right: 5px;display:block; width:x; height:y; text-align:right;">Heat No</label>
       </td>
       <td>
         <input name="heat_id"  type="text" id="t13_heat_id"  class="easyui-combobox" data-options="required:true,valueField:'keyval',                    
                    textField:'txtvalue'">
         
        </td>
        <td style="width: 100px;"> 
        <label style="padding-right: 5px;display:block; width:x; height:y; text-align:right;">Aim PSN</label>
       </td>
       <td>
         <input name="aim_psn"  type="text" id="t13_aim_psn"  class="easyui-textbox" data-options="required:true,validType:{length:[1,60]},readonly:true">
         
        </td>
        
         <td style="width: 100px;"> 
        <label style="padding-right: 5px;display:block; width:x; height:y; text-align:right;">Target Caster</label>
       </td>
       <td>
         <input name="tar_caster"  type="text" id="t13_tar_caster"  class="easyui-textbox" data-options="required:true,validType:{length:[1,60]},readonly:true">
         
        </td>
          <td style="width: 100px;"> 
        <label style="padding-right: 5px;display:block; width:x; height:y; text-align:right;">Prev Unit</label>
       </td>
       <td>
         <input name="prev_unit"  type="text" id="t13_prev_unit"   class="easyui-textbox" data-options="required:true,validType:{length:[1,60]},readonly:true">
         
        </td>
        </tr>
        <tr>
        <td style="width: 100px;"> 
        <label style="padding-right:5px;display:block; width:x; height:y; text-align:right;">Ladle No</label>
       </td>
       <td>
         <input name="laddle_no"  type="text" id="t13_laddle_no"   class="easyui-textbox" data-options="required:true,validType:{length:[1,60]},readonly:true">
         
        </td>
        <td style="width: 100px;"> 
        <label style="padding-right: 5px;display:block; width:x; height:y; text-align:right;">Tap temp</label>
       </td>
       <td>
         <input name="tap_temp"  type="text" id="t13_tap_temp"   class="easyui-textbox" data-options="required:true,validType:{length:[1,60]},readonly:true">
         
        </td>
         <td align="right" style="width: 100px;" > 
        <label  style="padding-right: 5px;display:block; width:x; height:y; text-align:right;">Tap Wgt</label>
       </td>
       <td>
         <input name="tap_wgt"  type="text" id="t13_tap_wgt"  class="easyui-textbox" data-options="required:true,validType:{length:[1,60]},readonly:true">
         
        </td>
         <!--  <td> 
        <label style="padding-right: 10px">Plan No</label>
       </td>
       <td>
         <input name="plan_no"  type="text" id="t13_plan_no" class="easyui-textbox"  data-options="required:true,validType:{length:[1,10]},readonly:true">
         
        </td> -->
           <td style="width: 100px;"> 
        <label style="padding-right: 5px;display:block; width:x; height:y; text-align:right;">Initial Temp</label>
       </td>
       <td>
         <input name="initial_temp"  type="text" id="t13_initial_temp" class="easyui-textbox"  data-options="required:false,validType:{length:[1,60]},readonly:true">
         
        </td>
        </tr>
        <tr>
          <td style="width: 100px;"> 
        <label style="padding-right: 0px;display:block; width:x; height:y; text-align:right;">Purging Medium</label>
       </td>
       <td>
         <input name="purging_medium"  type="text" id="t13_purging_medium"   class="easyui-combobox" data-options="required:true,valueField:'keyval',                    
                    textField:'txtvalue'">
         
        </td>
         <td style="width: 100px;"> 
        <label style="padding-right: 5px;display:block; width:x; height:y; text-align:right;">Cut Length</label>
       </td>
       <td>
         <input name="plan_cut_length"  type="text" id="t13_plan_cut_length" class="easyui-textbox"  data-options="required:false,validType:{length:[1,10]},readonly:true">
         
        </td>
        <!-- 
        <td style="width: 100px;"> 
        <label style="padding-right:5px;display:block; width:x; height:y; text-align:right;">Process Control</label>
       </td>
       <td>
         <input name="process_control"  type="text" id="t13_process_control"   class="easyui-combobox" data-options="required:true,valueField:'keyval',                    
                    textField:'txtvalue'">
         
        </td>
        
         <td style="width: 100px;"> 
        <label style="padding-right: 5px;display:block; width:x; height:y; text-align:right;">Vessel car</label>
       </td>
       <td>
         <input name="vessel_car_no"  type="text" id="t13_vessel_car_no"   class="easyui-combobox" data-options="required:true,valueField:'keyval',                    
                    textField:'txtvalue'">
        </td>
         -->
        </tr> 
        <tr>
        <td colspan="8">
        <div  id="t13_heat_hdr_btn_div_id">
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
 
  
    </div>
 <div id="t13_LRF_production_tbl_toolbar_div_id">
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
   
   <!-- PSN Documents Start -->      
 <div id="t14_PSN_Docs_form_div_id"class="easyui-dialog"
		closed="true" style="width: 1150px; height: 600px; padding-top: 0px; padding-left: 10px; padding-right: 20px;">	
    <form id="t14_PSN_Docs_form_id" method="post" novalidate>   
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
	    <input name="t14_psn_no" type="hidden"  id="t14_psn_no">
        <label style="padding-left: 1%">PSN NO</label>
        <input name="t14_rep_psn_desc"  type="text" id="t14_rep_psn_desc" class="easyui-textbox" data-options="editable:false" style="width: 100px;">
		<label style="padding-left: 15px"></label>
		<a href='javascript:void(0)'   id="t14_print" disabled="true" class='easyui-linkbutton' style='width:100px' iconCls='icon-print' plain='false' onclick='executePrintPsn()'>Print</a>
		<a href='javascript:void(0)'  id="t14_export" disabled="true" class='easyui-linkbutton' style='width:100px' iconCls='icon-edit' plain='false' onclick='executeExportPsnDoc()'>Word</a>
		<a href='javascript:void(0)'  id="t14_cancel" class='easyui-linkbutton' style='width:100px' iconCls='icon-save' plain='false' onclick=''>Close</a>
	  </div>
	<div id="t14_psn_report_id" style="background:white" width="100%" class="easyui-panel" closed="true">
	</div>
	</div>    
	</form>
    </div>   
  <!-- PSN Documents Screen End -->
     
 
 
   <!-- Chemical Details Screen Start -->
   
         
	<div id="t17_lrf_Chemistry_form_div_id" class="easyui-dialog" style="height:600px;padding:5px 5px;" closed="true">
		<form id="t17_lrf_Chemistry_form_id" method="post" novalidate>
        <input name="t17_sample_si_no"  type="hidden" id="t17_sample_si_no">
        <input name="t17_chem_level"  type="hidden" id="t17_chem_level">      
       	<table style="width: 100%;padding-left: 20px;padding-right: 20px;margin-left:15px" class="easyui-panel" >
	        <tr height="40">
       			<td style="width: 120px;"><label style="padding-right: 5px;display:block; width:x; height:y; text-align:left;">
       				<b><spring:message code="label.t17.lrfChemHeatNo"/></b></label></td>
       			<td><input name="heat_no"  type="text" id="t17_heat_no"  class="easyui-textbox" data-options="editable:false" style="width:90px"></td>
       			<td style="width: 100px;"><label style="padding-right: 5px;display:block; width:x; height:y; text-align:right;">
       				<b><spring:message code="label.t17.lrfChemAimPsn"/></b></label></td>
				<td><input name="aim_psn"  type="text" id="t17_aim_psn"  class="easyui-textbox" data-options="editable:false" style="width:150px"></td >
        		<!-- <td style="width: 100px;"> 
        		<label style="padding-right: 5px;display:block; width:x; height:y; text-align:right;" ><b><spring:message code="label.t17.lrfChemAnalysis" /></b></label>
       			</td><td>
                	<input name="analysis_type"  type="text" id="t17_analysis_type"  class="easyui-combobox"  data-options="panelHeight: 'auto',editable:true,required:true,
                    valueField:'keyval',                    
                    textField:'txtvalue'" style="width:150px">
       			</td>  -->
        		<td style="width: 100px;">
        		<label style="padding-right: 0px;display:block; width:x; height:y;text-align:right;"><b><spring:message code="label.t17.lrfChemSampleno"/></b></label>
        		</td><td style="text-align:left;">
         		<input name="sample_no"  type="text" id="t17_sample_no" class="easyui-combobox" data-options="readonly:true,required:true,valueField:'keyval',                    
                    textField:'txtvalue',hasDownArrow:false">
        		</td>
         		<td colspan="2">
       	  			<a href="javascript:void(0)" class="easyui-linkbutton" id="t17_getSample_btn" iconCls="icon-ok" onclick="GetSample()" >Generate Sample No</a>
       				<a href="javascript:void(0)" id="t17_spectro"  class="easyui-linkbutton" iconCls="icon-ok" onclick="lrf_level2_server()" style="width:30px"></a>
  
       			</td>
    		</tr> 
	        <tr height="40">  
         		<td style="width: 100px;">
        			<label style="padding-right: 5px;display:block; width:x; height:y; text-align:left;"><b><spring:message code="label.t17.lrfChemSampleTemp"/></b></label>
        		</td><td>
         			<input name="sample_temp"  type="text" id="t17_sample_temp"  class="easyui-numberbox" data-options="editable:true,required:true" style="width:90px">
        		</td>
         		<td style="width: 100px;"> 
        			<label style="padding-right: 0px;display:block; width:x; height:y; text-align:right;"><b><spring:message code="label.t17.lrfChemSampleDate"/></b></label></td><td>
         			<input name="sample_date_time"  type="text" id="t17_sample_date_time"  class="easyui-datetimebox" data-options="required:true,showSeconds:false" style="width:150px">
        		</td>
		         <td style="width: 100px;"> 
        			<label style="padding-right: 0px;display:block; width:x; height:y; text-align:right;"><b><spring:message code="label.t17.lrfChemSampleResult"/></b></label>
        		</td><td>
                	<input name="sample_result"  type="text" id="t17_sample_result"  class="easyui-combobox"  data-options="panelHeight: 'auto',editable:true,required:true,
                    valueField:'keyval',                    
                    textField:'txtvalue'" style="width:150px">
       			</td>
       			<td><a href="javascript:void(0)" class="easyui-linkbutton" id="t17_gradeChange_btn" iconCls="icon-ok" onclick="gradeChange()" style="margin-left: 0px">Grade Change</a></td>
       			<td>
        			<a href="javascript:void(0)" class="easyui-linkbutton" id="t17_gradeFinalize_btn" iconCls="icon-ok" onclick="gradeFinalize()" style="margin-left: 0px">Grade Finalize</a>
		        </td>
        	</tr>
        	<tr height="40">
				<td> 
        			<label style="padding-right: 5px;display:block; width:x; height:y; text-align:left;"><b><spring:message code="label.t17.lrfChemRemarks"/></b></label>
       			</td><td  colspan="8">
         			<input name="remarks"  type="text" id="t17_remarks"  data-options="multiline:true" style="height:30px;width: 100%" class="easyui-textbox">
		        </td>
	        </tr>
        </table>
        <br>   
       	<table style="width: 100%;">
		<tr>
		<td width="70%">
		<div title="<spring:message code="label.t17.lrfChemDetails"/>" style="padding:10px;height: 300px">
       	<table id="t17_lrf_Chemistry_tbl_id" toolbar="#t17_lrf_Chemistry__form_btn_div_id"  title="<spring:message code="label.t17.lrfChemDetails"/>" class="easyui-datagrid" style="width:100%;height:auto;"
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
			<table id="t17_lrf_chem_samp_tbl_id"
							toolbar="#t17_lrf_Chem_Sample_form_btn_div_id"
							title="List of Samples"
							class="easyui-datagrid" style="width: 100%"
							iconCls='icon-ok' maximizable="false" resizable="true"
							remoteSort="false" rownumbers="true" singleSelect="true">
				<thead>
					<tr>
						<th field="sample_no" width="140"><b>Sample No</b></th>
						<th field="action" width="140" formatter="viewT17SampleDtls"><b>Action     </b></th>
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
         <div id="t17_lrf_Chemistry__form_btn_div_id">
         	<a href="javascript:void(0)" class="easyui-linkbutton" id="t17_save_chem_btn_id" iconCls="icon-save" onclick="saveT17ChemistryDtls()" style="width:90px">Save</a>
        	<!-- <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload" onclick="cancelT17ChemistryDtls()" style="width:90px">Refresh</a> -->
         	<a href="javascript:void(0)" class="easyui-linkbutton" id="t17_close_chem_btn_id" iconCls="icon-cancel" onclick="dialogBoxClose('t17_lrf_Chemistry_form_div_id')" style="width:90px">Close</a>
    	</div>
    	<div id="t17_lrf_Chem_Sample_form_btn_div_id">
			<a href="javascript:void(0)" class="easyui-linkbutton" id="t17_final_result_btn_id" onclick="saveFinalResult()"
				style="width: 90px">Final Result</a>
		</div>	
        </form>
    </div>
    
    <!-- Grade Finalization Starts -->
    <div id="t17_lrf_GradeFinalize_form_div_id" class="easyui-dialog" style="height:600px;padding:10px 10px;width: 60%;"
            closed="true">             
		<form id="t17_lrf_GradeFinalize_form_id" method="post" novalidate>
			<br>
			<!-- 
			<table style="width: 100%;padding-left: 20px;padding-right: 20px;" >
			<tr>
		 		<td> 
        			<label style="padding-right: 20px"><b><spring:message code="label.t17.lrfChemAimPsn"/></b></label>
       			</td>
       			<td>
                    <input name="final_psn"  type="text" id="t17_final_psn"  class="easyui-combobox"  data-options="panelHeight: 'auto',editable:true,required:true,
                    valueField:'id',                    
                    textField:'value'">
       			</td>
       		</tr>
       		<tr><td colspan="2"></td></tr>
       		<tr><td colspan="2"></td></tr>
       		<tr><td colspan="2"></td></tr>
       		<tr><td colspan="2"></td></tr>
       		<tr>
       			<td align="right"> <a href="javascript:void(0)" class="easyui-linkbutton" id="t17_grade_final_save_btn_id" onclick="saveFinalGrade()"
					style="width: 90px">Save</a>
				</td>
				<td align="left"> <a href="javascript:void(0)" class="easyui-linkbutton" id="t17_grade_final_close_btn_id" iconCls="icon-cancel" onclick="closeFinalGrade()" style="width:90px">Close</a>
				</td>
       		</tr>
       		</table>  -->
       		
       		<table id="t17_lrf_heatplan_tbl_id" toolbar="#t17_lrf_heatplan_tbl_toolbar_div_id"  class="easyui-datagrid" style="width:95%;height: 500px;"
            	iconCls='icon-ok' pagination="false" maximizable="true"  resizable="true" fitColumns="true" remoteSort="false" pageSize="20"
            	rownumbers="true"  singleSelect="true"> 
	          	<thead>
    	       	<tr>
        	   	<th field="heat_plan_id"  sortable="true" width="60">Heat Plan No</th>
	          	<th field="indent_no" sortable="true" width="60">Heat Line No</th>
				<th field="aim_psn_id" sortable="false" width="100" formatter="(function(v,r,i){return formatColumnData('psnHdrModel.psn_no',v,r,i);})">Aim PSN</th>
		        <th field="section_type" sortable="false" width="100">Section Type</th>
		        <th field="section" sortable="false" width="100">Section</th>
		        <th field="target_caster" sortable="false" width="70">Caster Type</th>
				<th field="heat_plan_dtl_id" hidden="true">Detail Id</th>  
				</tr>
	        	</thead>
    	    </table>
     
     		<div id="t17_lrf_heatplan_tbl_toolbar_div_id">
     			<a href="javascript:void(0)" class="easyui-linkbutton" id="t17_grade_final_save_btn_id" iconCls="icon-save" onclick="saveFinalGrade()" style="width: 90px">Save</a>
     			<!-- <a href="javascript:void(0)" class="easyui-linkbutton" id="t17_grade_final_close_btn_id" iconCls="icon-cancel" onclick="closeFinalGrade()" style="width:90px">Close</a> -->
     			<a href="javascript:void(0)" class="easyui-linkbutton" id="t17_grade_final_close_btn_id" iconCls="icon-cancel" onclick="dialogBoxClose('t17_lrf_GradeFinalize_form_div_id')" style="width:90px">Close</a>
     		</div>
		</form>
	</div>
	
	<div id="t17_lrf_ladleChange_form_div_id" class="easyui-dialog" style="height:300px;padding:10px 10px;" 
            closed="true">             
		<form id="t17_lrf_ladleChange_form_id" method="post" novalidate>
			<br><br>
			<table style="width: 100%;padding-left: 20px;padding-right: 20px;" >
			<tr>
		 		<td> 
        			<label style="padding-right: 20px"><b>Ladle</b></label>
       			</td>
       			<td>
                	<input name="ladle_no"  type="text" id="t17_ladle_no"  class="easyui-combobox"  data-options="panelHeight: 'auto',editable:true,required:true,
                    valueField:'keyval',                    
                    textField:'txtvalue'">
       			</td>
       		</tr>
       		<tr><td colspan="2"></td></tr>
       		<tr>
       			<td colspan="2" align="center"> <a href="javascript:void(0)" class="easyui-linkbutton" id="t17_ladle_change_save__btn_id" onclick="saveLadleNo()"
					style="width: 90px">Save</a>
				</td>
       		</tr>
       		</table>
        </form>
	</div>
       
       
    
    <!-- Chemical Details Screen End --> 
    
  <!-- Process Parameter Screen Start -->
  <div id="t19_process_param_form_div_id" class="easyui-dialog" style="height:550px;padding:10px 10px"
            closed="true" data-change="0">
            
     <div id="t19_process_param_tabs_div_id" class="easyui-tabs"  narrow="true" style="width: 100%;height: auto;">
     
     <div title="PROCESS PARAMETERS" style="padding:10px">
     <table style="width: 100%;padding:10px" class="easyui-panel" >
       
        <!-- first row -->
        <tr>
        <td>
        <label><b>HEAT NO</b></label>
        
         <input name="heat_no"  type="text" id="t19_heat_no"  class="easyui-textbox" data-options="editable:false">
        </td>
        
       <td> 
        <label><b>AIM PSN</b></label>
         <input name="aim_psn"  type="text" id="t19_aim_psn"  class="easyui-textbox" data-options="editable:false">
         
        </td>
         
        </tr>
      </table>
     <table id="t19_process_param_tbl_id"   title="PROCESS PARAMETERS" class="easyui-datagrid" style="width:100%;height:auto;"
           iconCls='icon-ok' maximizable="true"  resizable="true" remoteSort="false"  rownumbers="true" singleSelect="false"> 
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
              	<th field="is_mandatory" sortable="false" width="150 "><b>MANDATORY</b></th> 
              
         </tr>
         </thead>
    </table>
      <div align="center" id="t19_process_param_form_btn_div_id">
         <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="savet19ProcessParamDtls()" style="width:90px">Save</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload" onclick="cancelt19ProcParamDtls()" style="width:90px">Refresh</a>
         <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="dialogBoxClose('t19_process_param_form_div_id')" style="width:90px">Close</a>
    	</div>
		</div>
		
		<!-- second tab -->
		<div title="ELECTRODE USAGE" >
		
		<table style="width: 100%" class="easyui-panel" >
       
        <!-- first row -->
        <tr>
        <td style="width:100px">
        <label style='padding-left:10px;padding-right:10px'>HEAT NO</label>
        
         <input name="heat_no"  type="text" id="t191_heat_no"  class="easyui-textbox" data-options="editable:false">
        </td>
        
       <td style="width:100px"> 
        <label style='padding-left:10px;padding-right:10px'>AIM PSN</label>
         <input name="aim_psn"  type="text" id="t191_aim_psn"  class="easyui-textbox" data-options="editable:false">
         
        </td>
         
        
        <td style="width:100px">
        <label style='padding-left:10px;padding-right:10px'>START TIME</label>
        
         <input name="electrode_start_time"  type="text" id="t191_electrode_start_time"   class="easyui-datetimebox"
						data-options="required:true,showSeconds:false">
        </td>
        
       <td style="width:100px"> 
        <label style='padding-left:10px;padding-right:10px'>END TIME</label>
         <input name="electrode_end_time"  type="text" id="t191_electrode_end_time"  class="easyui-datetimebox"
						data-options="required:true,showSeconds:false">
         
        </td>
         
        </tr>
      </table>
		
		<table id="t19_electrode_usage"   title="ELECTRODES" class="easyui-datagrid" style="width:50%;height:auto;" toolbar="#t19_electrode_usage_btn_div_id"
           iconCls='icon-ok' maximizable="true"  resizable="true" remoteSort="false"  rownumbers="true" singleSelect="false"> 
        <thead>
            <tr>
              
               	<!-- <th field="electrodeId" formatter="(function(v,r,i){return formatColumnData('electrodeLkpMstrModel.lookup_value',v,r,i);})" sortable="false" width="150"><b>ELECTRODE</b></th>
             	<th field="delay_reason" width="60"
					formatter="(function(v,r,i){return formatColumnData('lookup_value',v,r,i);})"
					editor='{
					type:"combobox",
					options:{
					required:true,editable:true,valueField:"keyval",textField:"txtvalue",
					method:"get",
					url:"./CommonPool/getComboList?col1=lookup_value&col2=lookup_value&classname=LookupMasterModel&status=&wherecol= lookup_type="ACTIVE_STATUS" and lookup_status= "}}'><b>ADDED</b></th>
             	
             	<th field="electrodeTransId" sortable="true" hidden="true" width="0"><b>PROCESS_ID</b></th> -->
         </tr>
         </thead>
    </table>
    <script>
    function saveDataAfterValidation(){
        javascript:$('#t19_electrode_usage').edatagrid('saveRow');
     }
    </script>
      <div id="t19_electrode_usage_btn_div_id">
        <a href="#" class="easyui-linkbutton" iconCls="icon-save" plain="true"
			onclick="saveDataAfterValidation();"><b>Save</b></a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-reload"
			plain="true" onclick="refresht19lrfElectrodeData()"><b>Refresh</b></a>
    	</div>
		</div>
		
		
		</div>
  
   </div>
   
    </div>
    <!-- Process Parameter Screen End -->
                

    <!-- Events Screen Start -->
   
   <div id="t18_lrf_events_form_div_id" class="easyui-dialog" style="height:480px;padding: 0 50px 0 50px;" 
            closed="true" data-change="0">
             
        <form id="t18_lrf_events_form_id" method="post" novalidate>
       
       
       <table style="width: 80%;padding-left: 20px;padding-right: 20px;" >
       
      
        <tr height="40">
       <td> <label style="padding-right: 10px"><b><spring:message code="label.t18.eofEventHeatNo"/></b></label></td>
        
       <td>
         <input name="heat_no"  type="text" id="t18_heat_no"  class="easyui-textbox" data-options="editable:false">
        </td>
        
       <td> 
        <label style="padding-right: 10px;"><b><spring:message code="label.t18.eofEventAimPsn"/></b></label></td>
        
       <td>
         <input name="aim_psn"  type="text" id="t18_aim_psn"  class="easyui-textbox" data-options="editable:false">
         
        </td>
        <td></td>
        <td>
        <input type="checkbox" class="easyui-checkbox" name="breakdown" id="t18_breakdown" style="width:35px;height:20px;background:white;border-radius:5px;border:2px solid #555;">
        </td>
        <td><label><b><spring:message code="label.t18.breakdown"/></b></label></td>
    </tr>     
        </table>
        <br>   
      
       <table id="t18_lrf_events_tbl_id"  toolbar="#t18_lrf_events_form_btn_div_id"  title="<spring:message code="label.t18.eofEvents"/>" class="easyui-datagrid" style="width:80%;"
           iconCls='icon-ok'  fitColumns="true"  resizable="true" remoteSort="false"  rownumbers="true" singleSelect="true"> 
        <thead>
            <tr>                               
               <th field="event_id" formatter="(function(v,r,i){return formatColumnData('eventName',v,r,i);})" sortable="false" width="200"><b><spring:message code="label.t8.eofEventName"/></b></th>
              <th field="event_date_time" sortable="false" editor="{type:'datetimebox',options:{editable:true}}" formatter="(function(v,r,i){return formatDateTime('event_date_time',v,r,i)})"  width="150"><b><spring:message code="label.t8.eofEventDate"/></b></th>
              <th field="heat_proc_event_id"  hidden="true" width="0"><b><spring:message code="label.t8.pkId"/></b></th>
         	  <th field="event_unit_seq"  hidden="true" width="0"><b><spring:message code="label.t8.pkId"/></b></th>
         </tr>
         </thead>
    </table>
   
    <br>
         	<div align="center" id="t18_eof_events__form_btn_div_id">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" id="T18SaveBtn" onclick="saveT18EventDtls()" style="width:90px">Save</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload" onclick="cancelT18EventDtls()" style="width:90px">Refresh</a>
         <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="dialogBoxClose('t18_lrf_events_form_div_id')" style="width:90px">Close</a>
    </div>
        </form>
    </div>
   
    <!-- Events Screen End --> 
    
    <!-- Delay Entry Start -->
       <div id="t24_delay_entry_form_div_id" class="easyui-dialog" style="height:450px;margin:5px;" 
            closed="true" data-rowchange="0">
             
        <form id="t24_delay_entry_form_id" method="post" novalidate>
       
       
       <table style="width: 100%" class="easyui-panel" >
       
        <!-- first row -->
        <tr>
        <td>
        <label>HEAT NO</label>
        
         <input name="heat_no"  type="text" id="t24_heat_no"  class="easyui-textbox" data-options="editable:false">
        </td>
        
       <td> 
        <label>AIM PSN</label>
         <input name="aim_psn"  type="text" id="t24_aim_psn"  class="easyui-textbox" data-options="editable:false">
         
        </td>         
        </tr>
      </table>
       
       <table id="t24_delay_entry_tbl_id"  toolbar="#t24_delay_entry_form_btn_div_id"  title="Delay Details Entry" class="easyui-datagrid" style="width:90%;height:auto;"
           iconCls='icon-ok' maximizable="false" fitColumns="true"  resizable="true" remoteSort="false"  rownumbers="true" singleSelect="true"> 

        <thead>
            <tr>         
              <th field="activity_master.activities" formatter="(function(v,r,i){return formatColumnData('activity_master.activities',v,r,i);})"  sortable="false" width="60">Activity</th>
              <th field="activity_master.delay_details" formatter="(function(v,r,i){return formatColumnData('activity_master.delay_details',v,r,i);})" sortable="false" width="120" >Delay Details</th>
              <th field="start_time" sortable="false" formatter="(function(v,r,i){return formatDateTime('start_time',v,r,i)})" width="70">Start Time</th>
              <th field="end_time" sortable="false" formatter="(function(v,r,i){return formatDateTime('end_time',v,r,i)})" width="70">End Time</th>
              <th field="duration" sortable="false" width="45" >Duration(min)</th>
              <th field="activity_master.std_cycle_time" formatter="(function(v,r,i){return formatColumnData('activity_master.std_cycle_time',v,r,i);})" sortable="false"  width="40">Standard Duration(min)</th>
              <th field="delay" sortable="false"  width="40">Delay(min)</th>
               <th field="corrective_action" sortable="false"  width="80" editor="{type:'textbox'}">Delay Reason</th>
              	
            </tr>
         </thead>
    </table>
    <br>
    <div id="t24_delay_entry_form_btn_div_id">
         <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="saveT24DelaytDtls()" style="width:90px">Save</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload" onclick="getT24DelayDetails()" style="width:90px">Refresh</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload" onclick="t241addDelayDetails()" style="width:150px">Add Delay Details</a>
         <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="dialogBoxClose('t24_delay_entry_form_div_id')" style="width:90px">Close</a>
    </div>
        </form>
    </div>
    
    <!-- Details Entry -Sub -->
       
     <div id="t241_delay_entry_form_div_id" class="easyui-dialog" style="height:450px;margin:5px;" 
            closed="true" data-change="0">
             
        <form id="t241_delay_entry_form_id" method="post" novalidate>
       <input name="t241activity_value"  type="hidden" id="t241activity_value"  >
       <input name="t241delay_details"  type="hidden" id="t241delay_details"  >
       <input name="t241delay"  type="hidden" id="t241delay"  >
       <input name="trans_delay_dtl_id_value"  type="hidden" id="trans_delay_dtl_id"  >
       
       <table style="width: 100%" class="easyui-panel" >
       
        <!-- first row -->
        <tr>
        <td>
        <label>HEAT NO</label>
        
         <input name="heat_no"  type="text" id="t241_heat_no"  class="easyui-textbox" data-options="editable:false">
        </td>
        
       <td> 
        <label>AIM PSN</label>
         <input name="aim_psn"  type="text" id="t241_aim_psn"  class="easyui-textbox" data-options="editable:false">
         
        </td>   
         <td> 
        <label>Activity</label>
         <input name="aim_psn"  type="text" id="t241_activity"  class="easyui-textbox" data-options="editable:false">
         
        </td>         
        </tr>
      </table>
       
       <table id="t241_delay_entry_tbl_id"  toolbar="#t241_delay_entry_form_btn_div_id" 
        title="Delay Details Entry" class="easyui-datagrid" style="width:90%;height:auto;"
        iconCls='icon-ok' maximizable="false" fitColumns="true" 
        data-options="fitColumns:true,singleSelect:true" 
        resizable="true" remoteSort="false"  rownumbers="true" singleSelect="true" data-delay="0"> 

        <thead>
            <tr>      
              <th field="activities"  formatter="(function(v,r,i){return formatT24ActivityColumnData('activities',v,r,i);})"  sortable="false" width="60">Activity</th>
              <th field="delay_details"   formatter="(function(v,r,i){return formatT24DlyDtlsColumnData('delay_details',v,r,i);})" sortable="false" width="80" >Delay Details</th>
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
    
   
    <div id="t241_delay_entry_form_btn_div_id">
    
     <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true"
			onclick="javascript:$('#t241_delay_entry_tbl_id').edatagrid('addRow')" ><b>Add</b></a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-save" plain="true"
			onclick="javascript:$('#t241_delay_entry_tbl_id').edatagrid('saveRow')"><b>Save</b></a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-reload"
			plain="true" onclick="t241ReferenshData()"><b>Refresh</b></a>
    
    </div>
        </form>
    </div>
    
    <!-- End -->
    
    <!-- Delay Entry End -->
    
    
    <!-- Begin Arcing Details Form  -->
  <div id="t15_arc_det_form_div_id" class="easyui-dialog" style="height:550px;padding:10px 10px;" closed="true" data-change="0">
  	<form id="t15_arc_det_form_id" method="post" novalidate>
		<input name="t15_heat_cnt"  type="text" id="t15_heat_cnt"  hidden="true">
		<input name="t15_arc_sl_no"  type="text" id="t15_arc_sl_no"  hidden="true" >
		<table style="width: 100%;padding-left: 20px;padding-right: 20px;"  class="easyui-panel">
        <tr height="40">
        <td>
        <label style="display:block; width:x; height:y; text-align:left;"><b><spring:message code="label.t15.heatNo"/></b></label></td>
        <td>
        <input name="t15_heat_no"  type="text" id="t15_heat_no"  class="easyui-textbox" data-options="editable:false" style="width:120px">
        </td>
        <td>
        <label style="display:block; width:x; height:y; text-align:right;"><b>Unit</b></label></td>
        <td>
        <input name="t15_unit"  type="text" id="t15_unit"  class="easyui-textbox" data-options="editable:false" style="width:80px">
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
       	<td style="width: 100px"> 
       	<label style="display:block; width:x; height:y; text-align:right;"><b><spring:message code="label.t15.arcStartDate"/></b></label></td>
       	<td>
       	<input name="t15_arc_start_date" type="text" id="t15_arc_start_date" formatter="formatDate('t15_arc_start_date')"
       	 class="easyui-datetimebox" data-options="required:true,showSeconds:false" style="width:125px">
       	</td>
       	<!-- 
       	<td style="width: 100px"> 
       	<label style="display:block; width:x; height:y; text-align:right;"><b><spring:message code="label.t15.temp"/></b></label></td>
       	<td>
       	<input name="t15_emp"  type="text" id="t15_temp"  class="easyui-numberbox" data-options="editable:true">
       	</td>   -->
       	<td style="width: 100px">
       <label style="display:block; width:x; height:y; text-align:right;"><b><spring:message code="label.t15.arcEndDate"/></b></label></td><td>
       <input name="t15_arc_end_date"  type="text" id="t15_arc_end_date"  
       	class="easyui-datetimebox" data-options="required:true,showSeconds:false" style="width:125px">        
       </td>
       <td style="width: 60px;text-align:left"> 
       <label style="display:block; width:x; height:y; "><b><spring:message code="lable.t15.consumption"/></b></label></td>
       <td>
       <input name="t15_consumption"  type="text" id="t15_consumption"  class="easyui-numberbox" data-options="editable:true" style="width:120px">
       </td>
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
       	<%-- <table id="t15_lrf_arc_add_tbl_id" toolbar="#t15_lrf_arc_add_form_btn_div_id"  title="<spring:message code="label.t15.lrfArchDetails"/>" class="easyui-datagrid" 
           maximizable="true"  resizable="true" remoteSort="false"  rownumbers="true" singleSelect="true" data-options="fitColumns:true" style="width: 100%; height: auto;">  --%>
	
	<table id="t15_lrf_arc_add_tbl_id"  toolbar="#t15_lrf_arc_add_form_btn_div_id"  title='<spring:message code="label.t15.lrfArchDetails"/>' class="easyui-datagrid"
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
    	
    <div id="t15_lrf_arc_add_form_btn_div_id">
    	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="saveT15ArcAdditions()" style="width:90px">Save</a>
    	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload" onclick="cancelT15ArcDet()" style="width:90px">Refresh</a>
    	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload" onclick="viewT15ArcAdditions()" style="width:90px">Display</a>
    	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-tip" onclick="viewT15PrevDtls()" style="width:120px">EAF Details</a>
   		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" id="temp1" onclick="dialogBoxClose('t15_arc_det_form_div_id')" style="width:90px">Close</a> <!--  dialogBoxClose('t15_arc_det_form_div_id') -->
    </div>
    <!-- </div> -->
   	</form>
   	<div align="center" id="t15_lrf_arc_disp_div_id" style="visibility: hidden">
   	 <table id="t15_lrf_arc_disp" title="ARC Addition Summary Details" class="easyui-datagrid" style="width: 100%;padding-left: 20px;padding-right: 20px;"  rownumbers="true" iconCls="icon-ok">
  
</table>
</div>
</div>
<!-- Showing EAF addition in LRF arching screen -->
<div id="t6_reuse_eof_Ladle_Addition_div" class="easyui-dialog" style="height:500px;width:90%" closed="true" data-change="0">
<table id="t6_eof_Ladle_Addition_tbl_id" toolbar="#t6_eof_Ladle_Addition_form_btn_div_id" title='<spring:message code="label.t6.eofLadleHeader"/>'  class="easyui-datagrid" style="width:100%;height:auto;"
           iconCls='icon-ok' maximizable="false"  resizable="true" showFooter="true" remoteSort="false"  rownumbers="true" singleSelect="true"> 
        <thead>
            <tr>
              <th field="material_id" formatter="(function(v,r,i){return formatColumnData('mtrlName',v,r,i);})" sortable="false" width="200"><spring:message code="label.t5.materialName"/></th>
              <th field="sap_matl_id" align="right" width="150">SAP Matl ID</th>
              <th field="valuation_type" align="center" width="150">Valuation Type</th>
              <th field="qty" align="right" data-options="editor:{type:'numberbox',options:{precision:2,min:0}}"  width="150"><spring:message code="label.t5.Qty"/></th>
               <th field="val_min" align="right" width="100">Min Value</th>
              <th field="val_max" align="right" width="100">Max Value</th>              
              <th field="uom" formatter="(function(v,r,i){return formatColumnData('uom',v,r,i);})" sortable="false" width="150"><spring:message code="label.t5.uom"/></th>
              <th field="mtr_cons_si_no" sortable="true" hidden="true" width="0"><spring:message code="label.t5.pkId"/></th>
              <th field="consumption_date" sortable="true" editor="{type:'datetimebox',options:{required:false,editable:true}}" formatter="(function(v,r,i){return formatDateTime('consumption_date',v,r,i)})"  width="150"><spring:message code="label.t6.consDate"/></th>
         <th field="version" hidden="true" align="right" width="100">Version</th>
         </tr>
         </thead>
    </table>
      <div  id="t6_eof_Ladle_Addition_form_btn_div_id">
         <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="dialogBoxClose('t6_reuse_eof_Ladle_Addition_div')" style="width:90px">Close</a>
    </div>
</div>
 <!-- End Arcing Details Form  -->
     <div id="t21_lrf_dispatch_div_id" class="easyui-dialog" style="height:300px;width:400px;" closed="true">
     <form id="t21_lrf_dispatch_form_id" method="post"  >
     <br>
		<table   style="width: 100%;text-align: left;">
		
			<tr style="height: 30px;">
        		<td  style="width: 100px;padding-right:20px" align="right" > 
       			<label><spring:message code="label.t21.lrfDispatchWgt"/></label>
        		</td>
       			<td style="width: 150px;" > 
         		<input name="lrfDispatchWgt"  type="text" id="t21_lrfDispatchWgt"  class="easyui-numberbox" data-options="required:true,precision:2,validType:{length:[1,10]},editable:true">
       			</td>
       			</tr>      			
       			<tr style="height: 30px;">
        		<td  style="width: 100px;padding-right:20px" align="right" > 
       			<label><spring:message code="label.t21.lrfDispatchTemp"/></label>
        		</td>
       			<td style="width: 150px;" > 
         		<input name="lrfDispatchTemp"  type="text" id="t21_lrfDispatchTemp"   class="easyui-numberbox" data-options="required:true,precision:2,validType:{length:[1,10]},editable:true">
       			</td>
       			</tr>
       			
       			<tr style="height: 30px;">
        		<td  style="width: 100px;padding-right:20px" align="right" > 
       			<label>AR N2 Consumption(NM3)</label>
        		</td>
       			<td style="width: 150px;" > 
         		<input name="ar_n2_consumption"  type="text" id="t21_ar_n2_consumption"   class="easyui-numberbox" data-options="required:true,precision:2,editable:true">
       			</td>
       			</tr>
       			
       			
       			
       			<tr style="height: 30px;">
        		<td  style="width: 100px;padding-right:20px" align="right" > 
       			<label><spring:message code="label.t21.dispatchTo"/></label>
        		</td>
       			<td style="width: 150px;" > 
       			<input name="dispatch_to"  type="text" id="t21_dispatchTo"  class="easyui-combobox"  data-options="panelHeight: 'auto',editable:false,required:true,
                    valueField:'keyval',                    
                    textField:'txtvalue'">
       			</td>
       			</tr>
       			<tr style="height: 30px;">
        		<td  style="width: 100px"  align="right" > 
       			<label><spring:message code="label.t21.remarks"/></label>
        		</td>
       			<td style="width: 150px;" > 
         		<input name="remarks"  type="text" id="t21_remarks"  class="easyui-textbox" data-options="validType:{length:[1,50]}"  multiline="true" style="height:40px;align:top"  >
       			</td>
       			</tr>
   </table>
 
    <br>
         	<div align="center" id="t21_lrf_dispatch_form_btn_div_id">
         
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload" onclick="resetForm()" style="width:90px">Refresh</a>
         <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="saveT21LRFDispatch()" style="width:90px">Save</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="dialogBoxClose('t21_lrf_dispatch_div_id')" style="width:90px">Close</a>
    </div>
        </form>
    </div>
     
     <!-- Start LRF Dispatch Form  -->

    
       <!-- End Lrf Dispatch Form -->
       
	<!-- Start LRF Ladle Mix  -->
    
    <div id="t25_lrf_ladle_mix_div_id" class="easyui-dialog" style="height:500px;padding:5px 5px;" 
            closed="true">   
            
        <table id="t25_lrf_ladle_mix_tbl_id" toolbar="#t25_lrf_ladle_mix_btn_div_id" class="easyui-datagrid" style="width:100%;height: 230px;"
           iconCls='icon-ok' pagination="true" maximizable="true"  resizable="true" remoteSort="false" pageSize="10"
            rownumbers="true"  singleSelect="false" checkOnSelect="false">           
        <thead>
            <tr>
            	<th field="heat_counter" sortable="false"  checkbox="true" width="40"></th>
                <th field="heat_id" width="100"><spring:message code="label.t13.heatno"/></th>
                 <th field="sub_unit_name" width="100"><spring:message code="label.t13.lrfProductionUnit"/></th>
                <th field="aim_psn_char" width="100" > <spring:message code="label.t13.aimpsn"/></th>
                <th field="heat_plan_id" width="100">  <spring:message code="label.t13.planNo"/></th>
            	<th field="steel_ladle_name" width="100"><spring:message code="label.t13.ladleNo"/></th>
            	<th field="prev_unit" width="120"> <spring:message code="label.t13.prevoiusUnit"/></th>
            	<th field="target_caster_name" width="120" > <spring:message code="label.t13.targetCaster"/></th>
            	<th field="steel_wgt" width="120" > <spring:message code="label.t13.steelWeight"/></th>
            	<th field="mix_qty" data-options="editor:{type:'numberbox',options:{precision:2}}" width="100">Mix Qty</th>  
            	<th field="trns_sl_no"  hidden="true" > </th>
            	<th field="heat_track_id"  hidden="true" > </th>          	
         	</tr>
         </thead>
        </table>  
    <br>
	<div id="t25_lrf_ladle_mix_det_div_id" class="easyui-panel" title="Ladle Mix Details" >
     <br>
      <table style="width: 65%;">
      	<tr style="height: 30px;">
        	<td style="width:100px;">Heat No 1</td>
        	<td style="width:150px;" align="left">  
				<input name="act_heat_no"  type="text" id="t25_act_heat_no1"  class="easyui-textbox" data-options="required:true" readonly="readonly">
        	</td>
        	<!-- 
        	<td style="width: 20%;">Heat No after Mix</td>
        	<td style="width: 10%;">
				<input name="mod_heat_no"  type="text" id="t25_mod_heat_no1"  class="easyui-textbox" data-options="required:true" readonly="readonly">
        	</td>  
        	 -->
        	<td style="width:100px;"><spring:message code="label.t13.steelWeight"/> </td>
        	<td style="width:150px;" align="left">
                <input name="steel_wt"  type="text" id="t25_steel_wt1"  class="easyui-numberbox"  data-options="required:true,precision:2" readonly="readonly">
        	</td>
        	<!-- 
        	<td style="width:70px;">Mix Qty</td>
        	<td style="width:150px;" align="left">
                <input name="mix_qty"  type="text" id="t25_mix_qty1"  class="easyui-numberbox"  data-options="required:true,precision:2">
        	</td>
        	 -->
        	<td style="width:100px;"> 
        		<label><spring:message code="label.t13.ladleNo"/></label>
        	</td>
        	<td style="width:150px;" align="left">
        		<input name="steel_ladle_no"  type="text" id="t25_steel_ladle_no1"  class="easyui-combobox" data-options="required:true,valueField:'keyval',textField:'txtvalue',validType:{length:[1,4]}">
        	</td>
		</tr>
		<tr style="height: 30px;">
			<td style="width:100px;">Heat No 2</td>
        	<td style="width:150px;">
				<input name="act_heat_no"  type="text" id="t25_act_heat_no2"  class="easyui-textbox" data-options="required:true" readonly="readonly">
        	</td>
        	<!-- 
        	<td style="width: 20%;">Heat No after Mix</td>
        	<td style="width: 10%;">
				<input name="mod_heat_no"  type="text" id="t25_mod_heat_no2"  class="easyui-textbox" data-options="required:true" readonly="readonly">
        	</td>
        	 -->
        	<td style="width:100px;"><spring:message code="label.t13.steelWeight"/></td>
        	<td style="width:150px;">
                <input name="steel_wt"  type="text" id="t25_steel_wt2"  class="easyui-numberbox"  data-options="required:true,precision:2" readonly="readonly">
        	</td>
        	<!-- 
        	<td style="width:100px;">Mix Qty</td>
        	<td style="width:150px;">
                <input name="mix_qty"  type="text" id="t25_mix_qty2"  class="easyui-numberbox"  data-options="required:true,precision:2">
        	</td>
        	 -->
        	<td style="width:100px;"> 
        		<label><spring:message code="label.t13.ladleNo"/></label>
        	</td>
        	<td style="width:150px;">
        		<input name="steel_ladle_no"  type="text" id="t25_steel_ladle_no2"  class="easyui-combobox" data-options="required:true,valueField:'keyval',textField:'txtvalue',validType:{length:[1,4]}">
        	</td>
		</tr>
		<tr><td colspan="6" align="center"></td></tr>
		<tr>
        <td colspan="8" align="center">
			<a href="javascript:void(0)" id="t25_lrf_ladle_mix_save_btn" class="easyui-linkbutton" iconCls="icon-save" onclick="saveT25LadleMixDtls()" style="width:90px">Save</a>
			<a href="javascript:void(0)" id="t25_lrf_ladle_mix_close_btn" class="easyui-linkbutton" iconCls="icon-cancel" onclick="dialogBoxClose('t25_lrf_ladle_mix_div_id')" style="width:90px">Close</a>
        </td>
        </tr>
      </table>
    </div>  
    <div id="t25_lrf_ladle_mix_btn_div_id">
     <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="T25MixSelectedLadles()" style="width:150px">Mix Selected Ladles</a>
    </div>  
	</div>  
    <!-- End LRF Ladle Mix -->

    <!-- Start Dispatch to LRF -->
    <div id="t29_disp_to_lrf_div_id" class="easyui-dialog" style="height:300px;padding:10px;" closed="true">
    <form id="t29_disp_to_lrf_form_id" method="post">
    	<br><br> 
		<table style="width: 100%;padding-left: 20px;padding-right: 20px;" class="easyui-panel">
			<tr height="40">			
				<!-- <td><label><b><spring:message code="label.t17.lrfChemHeatNo"/></b></label></td>
       			<td><input name="heat_no"  type="text" id="t29_heat_no"  class="easyui-textbox" data-options="editable:false" style="width:90px"></td>  -->
				<td> 
        			<label><b><spring:message code="label.t13.lrfProductionUnit"/></b></label>
        		</td>
        		<td> 
         			<input name="unit"  type="text" id="t29_unit"  class="easyui-combobox" style="padding-left: 5px;display:block; width:x; height:y" 
         			data-options="required:true,panelHeight: 'auto',editable:false,valueField:'keyval',textField:'txtvalue'">
        		</td>
		        <td style="width: 120px;"> 
			        <label style="padding-right: 0px;display:block; width:x; height:y; text-align:right;"><b>Purging Medium</b></label>
       			</td>
       			<td>
         			<input name="purging_medium"  type="text" id="t29_purging_medium"   class="easyui-combobox" data-options="valueField:'keyval',                    
                    textField:'txtvalue'">
		        </td>
        		<td style="width: 120px;"> 
        			<label style="padding-right:5px;display:block; width:x; height:y; text-align:right;"><b>Process Control</b></label>
       			</td>
       			<td>
         			<input name="process_control"  type="text" id="t29_process_control"   class="easyui-combobox" data-options="valueField:'keyval',                    
                    textField:'txtvalue'">
		        </td>
		        <td style="width: 100px;"> 
        			<label style="padding-right: 0px;display:block; width:x; height:y; text-align:right;"><b>Vessel car</b></label>
       			</td>
       			<td>
					<input name="vessel_car_no"  type="text" id="t29_vessel_car_no"   class="easyui-combobox" data-options="valueField:'keyval',                    
                    textField:'txtvalue'">
        		</td>
	        </tr>
			<tr>
				<td> 
        			<label style="padding-right: 5px;display:block; width:x; height:y; text-align:right;"><b><spring:message code="label.t17.lrfChemRemarks"/></b></label>
        		</td>
        		<td colspan="6">
         			<input name="remarks"  type="text" id="t29_remarks"  data-options="multiline:true" style="height:30px;width: 100%" class="easyui-textbox">
				</td>
        	</tr>
        	<tr><td colspan="7" align="center">&nbsp;&nbsp;</td></tr>
			<tr>
        		<td colspan="7" align="center">
					<a href="javascript:void(0)" id="t29_disp_to_lrf_save_btn" class="easyui-linkbutton" iconCls="icon-save" onclick="SaveT29DispatchToLrf()" style="width:90px">Save</a>
					<a href="javascript:void(0)" id="t29_disp_to_lrf_close_btn" class="easyui-linkbutton" iconCls="icon-cancel" onclick="dialogBoxClose('t29_disp_to_lrf_div_id')" style="width:90px">Close</a>
        		</td>
        	</tr>
        </table>
	</form>
    </div>
 <script type="text/javascript">
 	callT13Dropdowns();
 	$('#t13_recDate').datetimebox({
		value: (formatDate(new Date()))    
 	});
 	var recdate= ($('#t13_recDate').datetimebox('getValue'));
	$('#t13_shift').combobox({
		onLoadSuccess: function(){  
			value: autoShift(recdate,'t13_shift',$('#t13_shift').combobox('getData'));
		}
	}); 
	$('#t13_recDate').datetimebox({
		onChange: function(date){
			value: autoShift(($('#t13_recDate').datetimebox('getValue')),'t13_shift',$('#t13_shift').combobox('getData')); 
		}
	});
 	function callT13Dropdowns(){
	 	var user_id= <%=session.getAttribute("USER_APP_ID") %>
    	var url1="./CommonPool/getComboList?col1=lookup_id&col2=lookup_value&classname=LookupMasterModel&status=1&wherecol=lookup_type='SHIFT' and lookup_status=";
    	var url2="./CommonPool/getComboList?col1=sub_unit_id&col2=subUnitMstrMdl.sub_unit_name&classname=UserSubUnitMasterModel&status=1&wherecol=app_user_id="+user_id+" and subUnitMstrMdl.unitDetailsMstrMdl.unit_name='LRF' and record_status=";
    	var url3="./CommonPool/getComboList?col1=user_role_id&col2=appUserAccountDetails.user_name&classname=UserRoleMapMasterModel&status=1&wherecol=lookupMasterModel.lookup_code='LRF_SHIFT_INCHARGE' and lookupMasterModel.lookup_id=lookup_id  and record_status=";
    	var url4="./CommonPool/getComboList?col1=user_role_id&col2=appUserAccountDetails.user_name&classname=UserRoleMapMasterModel&status=1&wherecol=lookupMasterModel.lookup_code='LRF_ENGINEER' and lookupMasterModel.lookup_id=lookup_id  and record_status=";
    	
    	getDropdownList(url1,'#t13_shift');
    	getDropdownList(url2,'#t13_unit');
    	getDropdownList(url3,'#t13_shiftMgr');
    	getDropdownList(url4,'#t13_lrfInCharge');
     	//var url5="./CommonPool/getComboList?col1=trns_sl_no&col2=heat_id&classname=LRFHeatDetailsModel&status=1&wherecol=lrf_dispatch_unit=NULL and record_status=";
     	//getDropdownList(url5,'#t13_heat_id');
    }

	$('#t13_unit').combobox({
	 	onSelect: function(record){
			getT13UpdatedHeatList();
			clearT13HeatHdrform();
			$("#T13ladleDetailsId").show();
			$('#t13_lrf_heat_save_btn').linkbutton('disable');
		}
	});
 
 	function viewT15ArcAdditions() {
		document.getElementById('t15_lrf_arc_disp_div_id').style.visibility="hidden";
		var hdrCnt=$('#t15_lrf_arc_add_tbl_id').datagrid('getData').total;
		var heatId=$('#t13_heat_id').combobox('getText');
		var heatCnt=document.getElementById('t13_heat_cnt').value;
		var unit_id=$('#t13_unit').combobox('getValue');
	  
		$.ajax({
		headers: { 
	   	'Accept': 'application/json',
	   	'Content-Type': 'application/json' 
	   	},
	   	type: 'GET',
	   	//data: JSON.stringify(formData),
	   	dataType: "json",
	   	url: "./LRFproduction/getLRFArcAdditionsByHeat?heat_id="+heatId+"&heat_cnt="+heatCnt+"&unit_id="+unit_id,
	   	success: function(data) {
			var $n1 = {};
	   		var columns = new Array();
	   		 /*var colarray=new Array(
	   					{"test_id":'A1'},
	   					{"test_id":'N1'},
	   					{"test_id":'O1'}
	   					
	   			
	   			);*/
	   		var v=$('#t15_lrf_arc_add_tbl_id').datagrid('getRows')[0].material_name;
	   		var arr = data.headerdis;
	   		//columns.push({ "field": data.ar_si_no, title:data.ar_si_no, "width": 100, "sortable": true });
	   		columns.push({ "field": data.bath_sample_no, title:data.bath_sample_no, "width": 100, "sortable": true });
	   		columns.push({ "field": data.bath_temp, title:data.bath_temp, "width": 100, "sortable": true });
	   		columns.push({ "field": data.arc_start_date_time, title:data.arc_start_date_time,
	   		"formatter":function(v,r,i){return formatDateTime(data.arc_start_date_time,v,r,i)}, "width": 100, "sortable": true });
	   		columns.push({ "field": data.arc_end_date_time, title:data.arc_end_date_time,
	   		"formatter":function(v,r,i){return formatDateTime(data.arc_end_date_time,v,r,i)}, "width": 100, "sortable": true });
	   		columns.push({ "field": data.power_consumption, title:data.power_consumption, "width": 100, "sortable": true });
	   		for(var i=0;i<hdrCnt;i++){
				var str =  data.headerdis[i];
    			var resField = str.split("(");
    			columns.push({ "field": resField[0], "title": data.headerdis[i], "width": 120, "sortable": true });
	   		}
			/* $.each(arr, function (i, item) {
	   		 columns.push({ "field": item.test_id, "title": item.test_id, "width": 100, "sortable": true });
	   		 });*/
	   	 	$n1.columns = new Array(columns);
	   	 	$('#t15_lrf_arc_disp').datagrid($n1);
		   	 loadDataDummy();
		},
	   	error:function(){      
			$.messager.alert('Processing','Error while Processing Ajax...','error');
		}
		});
 	}
 
 	function urlFormat(){
		return "./CommonPool/getComboList?col1=lookup_value&col2=lookup_value&classname=LookupMasterModel&status=&wherecol= lookup_type='ACTIVE_STATUS'"
 	}
	///PSN Report Display for New PSNs wating for Review
	function executePrintPsn(){
		printPsn("t14");
	}
	function executeExportPsnDoc(){
		exportPsnDoc("t14");
	}
	function executeReportDisplay(){
		<%  
	    String url=request.getRequestURL().toString(); // URL base page      
      	String imageUrl=url.substring(0,url.indexOf(request.getRequestURI()))+request.getContextPath()+"/images/jsw.gif"; // image absolute url
		%>
		var s="<%=imageUrl%>"; 
		reportDisplay("t14",s);
	}
	//-------------Level 2 server accessing-------------- 
	function lrf_level2_server(){
		var connection_check=0;// 0-Connected 1-Not Connected
		var heat_sample_no=$('#t17_sample_no').textbox('getText');
		var actual_sample_no=heat_sample_no.substring(heat_sample_no.indexOf("/") + 1); 
		var actual_heat=heat_sample_no. substring(heat_sample_no.indexOf("E") ,heat_sample_no.indexOf("/") );
		var heat_id=$('#t13_heat_id').combobox('getText');
	  	var heat_counter=	document.getElementById('t13_heat_cnt').value;
	  	var analysis_id = document.getElementById('t17_chem_level').value;
	  	var sample_no = $('#t17_sample_no').combobox('getText');
	  	var sub_unit_id=document.getElementById('t13_sub_unit_id').value;
	  	var aim_psnId=document.getElementById('t13_aim_psn_id').value;

	  	if(heat_id!=null && heat_id!='' && heat_counter!=null && heat_counter!=''){	
	  		$.ajax({
 				headers : {
 					'Accept' : 'application/json',
 					'Content-Type' : 'application/json'
 				},
 				type : 'GET',
 				// data: JSON.stringify(formData),
 				dataType : "json",
 				url : "./heatProcessEvent/getTestSpectro",
 				success : function(data) {
 				if(data.status!="SUCCESS"){//Connection is failed
 					connection_check++;
 					$.messager.alert('Processing',
							'Connection With Spectro Server Can`t Establish,Continue With Manual Entry', 'error');
 					
 				}
 					
 				
 				},
 				error : function() {
 					$.messager.alert('Processing',
 							'Error Connecting Spectro Server...', 'error');
 				}
 			}); 
	  		if(connection_check==0){
	  	$.ajax({
	   		headers: { 
	   		'Accept': 'application/json',
	   		'Content-Type': 'application/json' 
	   		},
	   		type: 'GET',
	   		//data: JSON.stringify(formData),
	   		dataType: "json",
	   		url: "./LRFproduction/getChemDtlsBySpectro?analysis_id="+analysis_id+"&heat_id="+heat_id+"&heat_counter="+heat_counter+"&sub_unit_id="+sub_unit_id+"&sample_no="+sample_no+
	   				"&aim_psn_id="+aim_psnId+"&actual_sample_no="+actual_sample_no+"&actual_heat="+actual_heat,
	   		success: function(data) {
	   			var spectro_flag= 0;
	   			var sample_flag=0;
	   			$('#t17_lrf_Chemistry_tbl_id').datagrid('showColumn', 'remarks'); 
	   			
	   		 $('#t17_lrf_Chemistry_tbl_id').datagrid('loadData', data);
					for(var i=0;i<data.length;i++){
						$('#t17_sample_date_time').datetimebox('setValue', data[0].sample_date);
						var minval=data[i].min_value;
			          	var maxval=data[i].max_value;
			          	var aVal=data[i].aim_value;
			          	
			          	if ((aVal != null && aVal != '')
								&& (minval != null && minval != '')
								&& (maxval != null && maxval != '')) {
				          			minmax_flag = validateMinMax(aVal, minval, maxval);
				          			
				          			if (!minmax_flag) {
				          				sample_flag++;
				          				
				          			}
				          			if(sample_flag>=1){
				          				setDefaultCustomComboValues('t17_sample_result', 'REJECT', $('#t17_sample_result').combobox('getData'));
				          			}
				          			else{
				          				 setDefaultCustomComboValues('t17_sample_result', 'OK', $('#t17_sample_result').combobox('getData')); 
				          				
				          			}
				          		}
						if(data[i].spectro_flag==1)
							spectro_flag++;
					}
					if(spectro_flag>0){
					$.messager.alert('Warning',
							'Data Retrieved From Spectro Server', 'info');
					}
	   		},
	   		error:function(){      
	   			$.messager.alert('Processing','Error while Processing Ajax...','error');
	   		}
	   		});
	  		}
	  	}
	}
	
</script>
       <style type="text/css">
        #t13_Lrf_form_id{
            margin:0;
            padding:10px 30px;
        }
	    </style>