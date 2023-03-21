<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<script src="${pageContext.request.contextPath}/js/CasterProductionDetails.js" async></script>
<script  src="${pageContext.request.contextPath}/js/common.js"></script>

<!-- Accordion Start t23_heat_hdr_form_id -->
<div class="easyui-accordion" id="casterAccDivId" data-options="multiple:true" style="width:98%;height: auto">

<input type="hidden" value="null" id="mtube1">
<input type="hidden" value="null" id="mtube2">
<input type="hidden" value="null" id="mtube3">
<input type="hidden" value="null" id="mtube4">
<input type="hidden" value="null" id="mtube5">
<input type="hidden" value="null" id="mtube6">

<input type="hidden" value="null" id="cs_strand1">
<input type="hidden" value="null" id="cs_strand2">
<input type="hidden" value="null" id="cs_strand3">
<input type="hidden" value="null" id="cs_strand4">
<input type="hidden" value="null" id="cs_strand5">
<input type="hidden" value="null" id="cs_strand6">

<input type="hidden" value="null" id="cutlength_std1">
<input type="hidden" value="null" id="cutlength_std2">
<input type="hidden" value="null" id="cutlength_std3">
<input type="hidden" value="null" id="cutlength_std4">
<input type="hidden" value="null" id="cutlength_std5">
<input type="hidden" value="null" id="cutlength_std6">
 <div title="Crew Details" data-options="iconCls:'icon-ok'" style="padding-bottom: 4px;padding-top: 4px;padding-left: 10px;padding-right: 10px;">
    
    <form id="t23_crew_form_id" method="post" novalidate> 
    <input name="trns_si_no"  type="hidden" id="t23_trns_si_no">
    <input name="sub_unit_id" type="hidden" id="t23_sub_unit_id">
    <table style="width: 100%;"   class="easyui-panel" >
  <!-- first row style="padding-right:40px"-->
        <tr style="height: 30px;">
        <td  style="width: 100px;"> 
        <label style="padding-right: 5px;display:block; width:x; height:y; text-align:right;"><spring:message code="label.t23.casterProductionRecDate"/></label>
        </td>
        <td style="width: 150px;" > 
         <input name="recDate"  type="text" id="t23_recDate"  class="easyui-datetimebox" data-options="required:true,showSeconds:false,onSelect:callT23Dropdowns()">
        </td>
        
        <td  style="width: 100px;"> 
        <label style="padding-right: 5px;display:block; width:x; height:y; text-align:right;" ><spring:message code="label.t23.casterProductionShift"/></label>
        </td>
        <td style="width: 100px;"> 
         <input name="shift"  type="text" id="t23_shift"  class="easyui-combobox" data-options="required:true,panelHeight: 'auto',editable:false,valueField:'keyval',textField:'txtvalue'">
        </td>
        <td style="width: 60px;" > 
        <label style="padding-right: 5px;display:block; width:x; height:y; text-align:right;"><spring:message code="label.t23.casterProductionUnit"/></label>
        </td>
        <td > 
         <input name="unit"  type="text" id="t23_casterUnit"  class="easyui-combobox" data-options="required:true,panelHeight: 'auto',editable:false,valueField:'keyval',textField:'txtvalue'">
        </td>
        </tr>
        
        <tr style="height: 30px;">
        <!-- 
        <td> 
        <label style="padding-right: 5px;display:block; width:x; height:y; text-align:right;"><spring:message code="label.t23.casterProductionShiftMgr"/></label>
        </td>
        <td> 
         <input name="shiftMgr"  type="text" id="t23_shiftMgr"  class="easyui-combobox" data-options="required:true,panelHeight: 'auto',editable:false,valueField:'keyval',textField:'txtvalue'">
        </td> -->
        <td style="width: 40px;"> 
        <label style="padding-right: 5px;display:block; width:x; height:y; text-align:right;"><spring:message code="label.t23.shiftIncharge"/></label>
        </td>
        <td> 
         <input name="lrfMgr"  type="text" id="t23_ccmInCharge"  class="easyui-combobox" data-options="required:true,panelHeight: 'auto',editable:false,valueField:'keyval',textField:'txtvalue'">
        </td>
        <td style="width: 60px;"> 
        <label style="padding-right: 5px;display:block; width:x; height:y; text-align:right;">Caster Operator</label>
        </td>
        <td> 
         <input name="lrfMcdOpr"  type="text" id="t23_ccmMcdOpr"  class="easyui-combobox" data-options="required:true,panelHeight: 'auto',editable:false,valueField:'keyval',textField:'txtvalue'">
        </td>
        <!--  
        <td style="width: 80px;"> 
        <label style="padding-right: 1px;display:block; width:x; height:y; text-align:left;">Gas Cutter</label>
        </td>
        <td> 
         <input name="gasCutter"  type="text" id="t23_gasCutter"  class="easyui-combobox" data-options="required:true,panelHeight: 'auto',editable:false,valueField:'keyval',textField:'txtvalue'">
        </td>
        -->
        </tr>
        </table>
        </form>
     </div>
   
 <!-- Running Id Heat Details End -->
 <!--Accordin For Crew Details End  -->

<div title="Heats Waiting for Casting"  data-options="iconCls:'icon-ok'"  style="padding-top: 0px;padding-left: 0px;padding-right: 0px;width:100%;">

<table  style="width: 100%;">

<tr>

<td >
<!-- second grid -->
 <div class="easyui-panel" data-options="iconCls:'icon-ok'"  style="padding-top: 5px;padding-left: 0px;padding-right: 0px;width: 100%">
    <div id="t23_caster_waiting_tbl_toolbar_div_id">
     <%@ page import="java.util.*" %>
    <%--Display Buttons  --%>
   
    <%
    request.setAttribute("scrn_id", Integer.parseInt(request.getParameter("scrn_id")));
    %>
    <table>
    <tr>
    <td width="80%" style="padding-right: 30px">
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
    
<table id="t23_caster_waitingforcast_tbl_id"  class="easyui-datagrid" style="width:100%;height: 180px;"
            url="" method="get" iconCls='icon-ok'
             pagination="true" maximizable="true"  resizable="true" remoteSort="false" pageSize="10"
            rownumbers="true"  singleSelect="true"> 
         <thead>
            <tr>
                <th field="steel_ladle_name"  width="60">Ladle No</th> 
                <th field="steel_ladle_no"  width="0" hidden='true'>Ladle Id</th> 
                <th field="heat_id"  width="75" >Heat No</th>
                <th field="aim_psn_char"  width="120" >PSN</th>
                <th field="ccm_section_size"  width="120" >Sec Size</th>
                <!-- <th field="target_caster_name"  width="60">Caster</th> -->
                <th field="lrf_dispatch_temp"  width="80">Ladle Temp</th>
                <th field="prev_unit"  width="82">Last Process Unit</th>
                <th field="lrf_dispatch_date"  width="120" formatter="(function(v,r,i){return formatDateTime('lrf_dispatch_date',v,r,i)})"> LastUnit Dispatch Date</th>
                <th field="heat_plan_id"  width="60">Plan No</th>
              	<th field="heat_plan_line_no"  width="60" hidden="true">Line No</th>
                <th field="steel_wgt"  width="67">Steel Wgt</th>
                <th field="chem_details" sortable="true" width="800" >Chem Details</th>
                <th field="heat_counter" width="0" hidden='true'>heat_counter</th>
                <!-- <th field="lrf_C" sortable="true" width="50" >C</th>
                <th field="lrf_S" sortable="true" width="50" >S</th>
                <th field="lrf_P" sortable="true" width="50" >P</th>
                <th field="lrf_MN" sortable="true" width="50" >Mn</th>
                <th field="lrf_Si" sortable="true" width="50" >Si</th>
                <th field="lrf_Ti" sortable="true" width="50" >Ti</th> -->
         </tr>
         </thead>
    </table>
    </div>
</td>
</tr>
</table>
  <div  data-options="iconCls:'icon-ok'"  style="padding-top: 0px;padding-left: 0px;padding-right: 0px;width:100%;">

            <table id="t23_caster_production_tbl_id"  class="easyui-datagrid" style="width:100%;height: 160px;padding-top: 10px;padding-left: 10px;padding-right: 10px;"
            url="" method="get" iconCls='icon-ok' title="Caster Details" 
             pagination="false" maximizable="true" remoteSort="false" rownumbers="true" > 
         <thead>
            <tr>
               <th field="heat_plan_id"  width="50">PlanNo</th> 
               <th field="heat_no"  width="100">HeatNo</th> 
               <th field="psn_no"  width="100" formatter="(function(v,r,i){return formatColumnData('psnHdrMstrMdl.psn_no',v,r,i);})"> Psn No</th>
               <th field="steel_ladle_no"  width="70" formatter="(function(v,r,i){return formatColumnData('steelLadleObj.steel_ladle_no',v,r,i);})"> Ladle No</th>
               <th field="steel_ladle_wgt"  width="70"> Steel Qty</th>
               <th field="product_id" formatter="(function(v,r,i){return formatColumnData('productMasterMdl.prodSecMtrlLookupModel.lookup_value',v,r,i);})" width="70">Product</th>
               <th field="seq_no"  width="50"   formatter="(function(v,r,i){return formatColumnData('seq_no',v,r,i);})" > Seq No</th>
               <th field="seq_group_no"  width="70"   formatter="(function(v,r,i){return formatColumnData('seq_group_no',v,r,i);})" > Seq Group No</th>	
               <th field="return_qty"  width="50"  editor="{
		              	  type:'numberbox',options:{precision:2}
		                }"	> Ret Qty</th>
		       <th field="return_reason"  width="90"  editor="{
		              	  type:'textbox'}"	> Ret Reason</th>
		       <th field="grade"  width="80" formatter="(function(v,r,i){return formatColumnData('psnGradeMasterMdl.psn_grade',v,r,i);})">Steel Grade</th>
		       <th field="prev_unit"  width="80">Last Process Unit</th>
		       <th field="lift_temp"  width="80">Lifting temp</th>
		       <th field="prod_date"  width="60" formatter="(function(v,r,i){return formatDateTime('prod_date',v,r,i)})">Received time</th>
		       <th field="chem_details" width="140" formatter="viewT23LiftChem">Lift chem</th>
               <th field="trns_sl_no" width="100" hidden="true">castHeat_pk</th>
               <th field="sub_unit_id" width="100" hidden="true" >Sub Unit</th>
               <th field="heat_counter" width="0" hidden='true'>heat_counter</th>
         </tr>
         </thead>
    </table>
       
    <div id="t23_caster_production_tbl_toolbar_div_id">
     <%@ page import="java.util.*" %>
    <%--Display Buttons  --%>
   
    <%
    request.setAttribute("scrn_id", Integer.parseInt(request.getParameter("scrn_id")));
    %>
    <table>
    <tr>
    <td width="80%" style="padding-right: 30px">
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
   </td>
   </tr>
   </table>
   </div>
   
    </div>
<!-- Accordion End -->
</div>
  </div>
<!-- process heat dialog -->

  <div id="t24_process_heat_form_div_id" class="easyui-dialog" style="width:70%;height:260px;padding:10px 10px"
            closed="true">
            <input type="hidden" id="hidden_primary" value="0"/>
            <input type="hidden" id="hidden_fly" value="0"/>
            <input type="hidden" id="cast_powder" value="null"/>
            <input type="hidden" id="prev_tund_no" value="null"/>
            <table style="width: 100%" >
            <tr>
            <td>
             <label style="padding-right:20px" align="right">Heat No</label>
        
         <input name="t24_process_heat_no"  type="text" id="t24_process_heat_no"  class="easyui-textbox" data-options="editable:false">
            </td>
            <td>
             <label style="padding-right:35px" align="right">PSN No</label>
        
         <input name="t24_process_psn_no"  type="text" id="t24_process_psn_no"  class="easyui-textbox" data-options="editable:false">
            </td>
             <td>
             <label style="padding-right:30px" align="right">Grade</label>
        
         
         <input name="t24_process_grade_no"  type="text" id="t24_process_grade_no"  class="easyui-combobox" data-options="required:true,valueField:'keyval',textField:'txtvalue',editable:false">
            </td>
            
            <td>
             <label style="padding-right:65px" align="right">Route</label>
        
         <input name="t24_process_route"  type="text" id="t24_process_route"  class="easyui-textbox" data-options="editable:false">
            </td>
            <td>
             <label style="padding-right:5px" align="right">Cast Powder</label>
        
         <input name="t24_process_cast_powder"  type="text" id="t24_process_cast_powder"  class="easyui-combobox" data-options="required:true,editable:true, valueField:'keyval',                    
                    textField:'txtvalue'" >
            </td>
             
            </tr>
            <!-- second row -->
            <tr>
            
             <td>
             <label style="padding-right:20px" align="right">EAF WC</label>
        
         <input name="t24_process_eof"  type="text" id="t24_process_eof"  class="easyui-textbox" data-options="editable:false">
            </td>
            <td>
             <label style="padding-right:20px" align="right">Prod Date </label>
        
         <input name="t24_process_prod_date"  type="text" id="t24_process_prod_date"  class="easyui-datetimebox" data-options="editable:false,showSeconds:false" style="width:150px;">
            </td>
             <td>
             <label style="padding-right:20px" align="right">CS Size(mm)</label>
        
         <input name="t24_heat_no"  type="text" id="t24_process_cs_size"  class="easyui-combobox" data-options="required:true,valueField:'keyval',textField:'txtvalue',editable:false">
            </td>
            <td>
             <label style="padding-right:20px" align="right">Product</label>
        
        <!--  <input name="t24_process_product"  type="text" id="t24_process_product"  class="easyui-textbox" data-options="editable:true"> -->
         
         <input name="t24_process_product"  type="text" id="t24_process_product" class="easyui-combobox" data-options="required:true,valueField:'keyval',textField:'txtvalue'">
         
            </td>
                <td>
             <label style="padding-right:25px" align="right">Turret Arm</label>
        
             <input name="t24_ladleCar"  type="text" id="t24_ladleCar"  class="easyui-combobox" data-options="panelHeight: 'auto',editable:true,required:true,
                    valueField:'keyval',                    
                    textField:'txtvalue'">
            </td> 
            
            </tr>
        
           
            <tr> 
            <td>
             <label style="padding-right:25px" align="right">Heat Seq</label>
              
              <select id="t24_seq_no" class="easyui-combobox" id="t24_seq_no" style="width:150px;">
                 <option selected></option>
                 <option value="primary">Lead Heat</option>
                 <option value="continue">Continue</option>
                 <option value="fly" >Fly</option>
                 <option value="last_heat" >Last Heat</option>
               </select>
             
            </td>
            <td>
             <label style="padding-right:25px" align="right">Seq No</label>
        
         <input name="t24_heat_no"  type="text" id="t24_process_seq_no"  class="easyui-textbox" data-options="editable:true">
            </td>
            <!-- Tundish Car -->
			 <td>
             <label style="padding-right:25px" align="right">Tundish Car</label>
        
             <input name="t24_TundishCar"  type="text" id="t24_TundishCar"  class="easyui-combobox" data-options="panelHeight: 'auto',editable:true,required:true,
                    valueField:'keyval',                    
                    textField:'txtvalue'">
            </td> 
            
             
              <td>
             <label style="padding-right:25px" align="right">Tundish No</label>
        
            <input name="t23_tundishNo1"  type="text" id="t23_tundishNo1"  class="easyui-combobox" data-options="panelHeight: 'auto',editable:true,required:true,
                   valueField:'keyval',                    
                   textField:'txtvalue'">
            </td> 
            <td>
             <label align="right"> Ladle Free Open</label>
        
            <select name="t23_ladleOpen1" id="t23_ladleOpen1" class="easyui-combobox" style="width:150px;">
            <option value=""></option>
             <option value="Free">Free</option>
             <option  value="Lancing">Lancing</option>
             </select>
            </td> 
            
            </tr>
            <tr>
             <td>
            	<label align="right"> No of Pipes</label>
            	<select id="t23_noOfPipes" class="easyui-combobox" style="width:150px;"><option value="0"></option>
            	<option value="1">1</option> <option value="2">2</option>
            	<option value="3">3</option> <option value="4">4</option>
            	<option value="5">5</option> <option value="6">6</option>
            	<option value="7">7</option> <option value="8">8</option>
            	<option value="9">9</option> <option value="10">10</option>
            	</select>
             </td>
             <td>
             	<label style="padding-right:20px" align="right">Shroud Change</label>
             	
             	<select name="t23_shroud_change" id="t23_shroud_change" class="easyui-combobox" style="width:150px;">
             	<option value="Y">YES</option>
             	<option  value="N">NO</option>
             	</select>
             </td>
             <td>
             <label style="padding-right:25px" align="right">Shroud Make</label>
        
             <input name="t23_shroud_make"  type="text" id="t23_shroud_make"  class="easyui-combobox" data-options="panelHeight: 'auto',editable:true,required:true,
                    valueField:'keyval',                    
                    textField:'txtvalue'">
            </td> 
            </tr>
            
             </table>   
             <br/>       
            <div style="margin-left:40%">
            
        
       <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="t24_process_heat_save()" style="width:90px">Save</a>
       <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="dialogBoxClose('t24_process_heat_form_div_id')" style="width:90px">Close</a>
            </div>
            
            
           
             <!-- <input name="t24_process_ret_qty"  type="hidden" id="t24_process_ret_qty"  class="easyui-textbox" data-options="editable:false"> -->
  </div>
<!-- end -->

<!-- ccm additions screen start -->

 <div id="t23_ccm_additions_form_div_id" class="easyui-dialog" style="height:550px;padding:10px 10px"
            closed="true" data-change="0">
            
     <div id="t23_ccm_additions_tabs_div_id" class="easyui-tabs"  pill="true" style="width: 100%;height: auto;">
     
     <div title="<spring:message code="label.t23.ccmAdditions"/>" style="padding:10px">
     <table style="width: 100%" class="easyui-panel" >
       
        <!-- first row -->
        <tr>
        <td>
        <label><spring:message code="label.t23.additionsHeatNo"/></label>
        
         <input name="heat_no"  type="text" id="t23_1_heat_no"  class="easyui-textbox" data-options="editable:false">
        </td>
        
       <td> 
        <label><spring:message code="label.t23.additionsAimPsn"/></label>
         <input name="aim_psn"  type="text" id="t23_1_aim_psn"  class="easyui-textbox" data-options="editable:false">
         
        </td>
         
        </tr>
        </table>

   <table id="t23_ccm_additions_tbl_id" toolbar="#t23_ccm_additions_form_btn_div_id"  title="<spring:message code="label.t23.ccmAdditions"/>" class="easyui-datagrid" style="width:100%;height:auto;"
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
      <div  id="t23_ccm_additions_form_btn_div_id">
         <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="saveT23CCMDtls()" style="width:90px">Save</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload" onclick="cancelT23CCMDtls()" style="width:90px">Refresh</a>
         <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="dialogBoxClose('t23_ccm_additions_form_div_id')" style="width:90px">Close</a>
    </div>
    </div>
    </div>
    </div>
    
    <!-- ccm additions screen end -->


<!-- Process Parameter Screen Start -->
	<div id="t24_process_param_form_div_id" class="easyui-dialog" style="height:550px;padding:10px 10px"
            closed="true">
	<div id="t24_process_param_tabs_div_id" class="easyui-tabs"  narrow="true" style="width: 100%;height: auto;">
     
	<div title="Heatwise Parameters" style="padding:10px">
		<table style="width: 100%" class="easyui-panel" >      
        <!-- first row -->
        <tr>
        <td>
        <label><b>HEAT NO</b></label>
         <input name="t24_1_heat_no"  type="text" id="t24_1_heat_no"  class="easyui-textbox" data-options="editable:false">
        </td>       
       	<td> 
        <label><b>AIM PSN</b></label>
        <input name="t24_1_aim_psn"  type="text" id="t24_1_aim_psn"  class="easyui-textbox" data-options="editable:false">
        </td>        
        </tr>
      	</table>
     	
    	<table id="t24_heatwise_process_param_tbl_id"  class="easyui-datagrid" style="width:100%;height:auto;"
           iconCls='icon-ok' maximizable="true"  resizable="true" remoteSort="false"  rownumbers="true" singleSelect="false"> 
        <thead>
			<tr>
				<th field="param_id" formatter="(function(v,r,i){return formatColumnData('proc_para_name',v,r,i);})" sortable="false" width="160"><b>PARAMETERS</b></th>
               	<th field="uom" formatter="(function(v,r,i){return formatColumnData('uom',v,r,i);})" sortable="false" width="100"><b>UOM</b></th>
             	<th field="param_value_min" align="right" width="100" ><b>MIN_VALUE</b></th>
             	<th field="param_value_max" align="right" width="100" ><b>MAX_VALUE</b></th>
               	<th field="param_value_aim" align="right" width="100"><b>AIM_VALUE</b></th>
                <th field="param_value_actual" align="right" data-options="editor:{type:'numberbox',options:{precision:4,min:0}}"  width="150"><b>ACT_VALUE<font color="red">*</font></b></th>
              	<th field="process_date_time" sortable="true" editor="{type:'datetimebox',options:{required:true,editable:true}}" formatter="(function(v,r,i){return formatDateTime('process_date_time',v,r,i)})"  width="150"><b>PROCESS DATE</b></th>
              	<th field="proc_param_event_id" sortable="true" hidden="true" width="0"><b>PROCESS_ID</b></th>
        	</tr>
         </thead>
    	</table>    
    	
		<div align="center" id="t24_1_process_param_form_btn_div_id">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="savet24_1ProcessParamDtls()" style="width:90px">Save</a>
        	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload" onclick="cancelt24_1ProcParamDtls()" style="width:90px">Refresh</a>
         	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="dialogBoxClose('t24_process_param_form_div_id')" style="width:90px">Close</a>
    	</div>
	</div>
  
  	<div title="Strandwise Parameters" style="padding:10px">
	  	<table style="width: 100%" class="easyui-panel" >
        <tr>
        <td>
        <label><b>HEAT NO</b></label>
			<input name="t24_heat_no"  type="text" id="t24_heat_no"  class="easyui-textbox" data-options="editable:false">
        </td>
	    <td> 
        <label><b>AIM PSN</b></label>
			<input name="t24_aim_psn"  type="text" id="t24_aim_psn"  class="easyui-textbox" data-options="editable:false">
        </td>
		</tr>
      	</table>
     	
     	<div id="t24_blt_pp">
     	<table id="t24_process_param_tbl_id"   title="PROCESS PARAMETERS" class="easyui-datagrid" style="width:100%;height:auto;"
           iconCls='icon-ok' maximizable="true"  resizable="true" remoteSort="false"  rownumbers="true" singleSelect="false"> 
        <thead>
		<tr>
			<th field="param_id" formatter="(function(v,r,i){return formatColumnData('procParaMstrMdl.param_desc',v,r,i);})" sortable="false" width="150"><b>PARAMETERS</b></th>
            <th field="uom" formatter="(function(v,r,i){return formatColumnData('procParaMstrMdl.uomMdl.lookup_code',v,r,i);})" sortable="false" width="100"><b>UOM</b></th>
            <th field="strand1_value" align="right" width="100" data-options="editor:{type:'numberbox',options:{precision:4,min:0}}"><b>Strand#1</b></th>
            <th field="strand2_value" align="right" width="100" data-options="editor:{type:'numberbox',options:{precision:4,min:0}}"><b>Strand#2</b></th>
            <th field="strand3_value" align="right" width="100" data-options="editor:{type:'numberbox',options:{precision:4,min:0}}"><b>Strand#3</b></th>
            <th field="strand4_value" align="right" width="100" data-options="editor:{type:'numberbox',options:{precision:4,min:0}}"><b>Strand#4</b></th>
            <th field="strand5_value" align="right" width="100" data-options="editor:{type:'numberbox',options:{precision:4,min:0}}"><b>Strand#5</b></th>
            <th field="strand6_value" align="right" width="100" data-options="editor:{type:'numberbox',options:{precision:4,min:0}}"><b>Strand#6</b></th>
            <th field="process_date_time" sortable="true" editor="{type:'datetimebox',options:{required:true,editable:true}}" formatter="(function(v,r,i){return formatDateTime('process_date_time',v,r,i)})"  width="150"><b>PROCESS DATE</b></th>
            <th field="proc_param_event_id" sortable="true" hidden="true" width="0"><b>PROCESS_ID</b></th>
         </tr>
         </thead>
    	</table>
  		</div>
  		<div id="t24_slb_pp">
  		<table id="t24_process_param_tbl_id_2"   title="PROCESS PARAMETERS" class="easyui-datagrid" style="width:100%;height:auto;"
           iconCls='icon-ok' maximizable="true"  resizable="true" remoteSort="false"  rownumbers="true" singleSelect="false"> 
        <thead>
		<tr>
			<th field="param_id" formatter="(function(v,r,i){return formatColumnData('procParaMstrMdl.param_desc',v,r,i);})" sortable="false" width="150"><b>PARAMETERS</b></th>
            <th field="uom" formatter="(function(v,r,i){return formatColumnData('procParaMstrMdl.uomMdl.lookup_code',v,r,i);})" sortable="false" width="100"><b>UOM</b></th>
            <th field="strand1_value" align="right" width="100" data-options="editor:{type:'numberbox',options:{precision:4,min:0}}"><b>Strand#1</b></th>
            <th field="process_date_time" sortable="true" editor="{type:'datetimebox',options:{required:true,editable:true}}" formatter="(function(v,r,i){return formatDateTime('process_date_time',v,r,i)})"  width="150"><b>PROCESS DATE</b></th>
            <th field="proc_param_event_id" sortable="true" hidden="true" width="0"><b>PROCESS_ID</b></th>
         </tr>
         </thead>
    	</table>
  		</div>
  		<div align="center" id="t24_process_param_form_btn_div_id">
         	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="savett24ProcessParamDtls()" style="width:90px">Save</a>
        	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload" onclick="cancelt24ProcParamDtls()" style="width:90px">Refresh</a>
         	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="dialogBoxClose('t24_process_param_form_div_id')" style="width:90px">Close</a>
    	</div>
  	</div>
  
   	</div>
    </div>
    <!-- Process Parameter Screen End -->
    
     
    <!-- Events Screen Start -->
   
   <div id="t23_caster_events_form_div_id" class="easyui-dialog" style="height:450px;padding: 0 50px 0 50px;" 
            closed="true">
             
        <form id="t23_caster_events_form_id" method="post" novalidate>
       
       
       <table style="width: 100%;padding-left: 20px;padding-right: 20px;" >
       
      
        <tr height="40">
       <td> <label style="padding-right: 10px;"><b><spring:message code="label.t18.eofEventHeatNo"/></b></label></td>
        
       <td>
         <input name="heat_no"  type="text" id="t23_heat_no"  class="easyui-textbox" data-options="editable:false">
        </td>
        
       <td> 
        <label style="padding-right: 10px"><b><spring:message code="label.t18.eofEventAimPsn"/></b></label></td>
        
       <td>
         <input name="aim_psn"  type="text" id="t23_aim_psn"  class="easyui-textbox" data-options="editable:false">
         
        </td>
       
    </tr> 
      
      <tr height="40">
        
     <%--     <td> 
        <label style="padding-right: 10px"><b><spring:message code="label.t18.eofEventName"/></b></label>
       </td><td>
                <input name="event_id"  type="text" id="t23_event_id"  class="easyui-combobox"  data-options="panelHeight: 'auto',editable:true,required:true,
                    valueField:'keyval',                    
                    textField:'txtvalue'">
       </td>
        <td>
        <label style="padding-right: 10px"><b><spring:message code="label.t18.eofEventDate"/></b></label>
        </td><td>
        <input name="event_date_time"  type="text" id="t23_event_date_time"  class="easyui-datetimebox" data-options="required:true,showSeconds:false">
        </td> --%>
        
      <!--  <td colspan="2" align="left">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="saveT23EventDtls()" style="width:90px">Save</a>
        </td> -->
        
        </tr>
        </table>
        
        <br>
    
      
       <table id="t23_caster_events_tbl_id"  toolbar="#t23_caster_events_form_btn_div_id"  title="<spring:message code="label.t18.eofEvents"/>" class="easyui-datagrid" style="width:80%;"
           iconCls='icon-ok'  fitColumns="true"  resizable="true" remoteSort="false"  rownumbers="true" singleSelect="true"> 

        <thead>
            <tr>
                               
              <th field="event_id" formatter="(function(v,r,i){return formatColumnData('eventMstrMdl.event_desc',v,r,i);})" sortable="false" width="200"><b><spring:message code="label.t18.eofEventName"/></b></th>
              <th field="event_date_time" sortable="true" editor="{type:'datetimebox',options:{required:true,editable:true}}" formatter="(function(v,r,i){return formatDateTime('event_date_time',v,r,i)})"  width="150"><b><spring:message code="label.t18.eofEventDate"/></b></th>
              <th field="heat_proc_event_id" sortable="true" hidden="true" width="0"><b><spring:message code="label.t18.pkId"/></b></th>
         </tr>
         </thead>
    </table>
   
    <br>
         	<div align="center" id="t23_caster_events_form_btn_div_id">
          <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="saveT23EventDtls()" style="width:90px">Save</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload" onclick="cancelT23EventDtls()" style="width:90px">Refresh</a>
         <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="dialogBoxClose('t23_caster_events_form_div_id')" style="width:90px">Close</a>
    </div>
        </form>
    </div>
   
    <!-- Events Screen End --> 
    
    
       <!-- Seq Events Screen Start -->
   
   <div id="t23_caster_seq_events_form_div_id" class="easyui-dialog" style="height:550px;padding: 0 50px 0 50px;" 
            closed="true">
             
        <form id="t23_caster_seq_events_form_id" method="post" novalidate>
       
       
       <table style="width: 80%;padding-left: 10px;padding-right: 10px;" >
       
      
        <tr height="20">
       <td> <label style="padding-right: 2px;">Casting Sequence</label></td>
        
       <td>
         <input name="heat_no"  type="text" id="t23_group_seq_no"  class="easyui-combobox" data-options="required:true,panelHeight: 'auto',editable:true,
                    valueField:'id',                    
                    textField:'value'" style="width:100px">
        </td>        
       <td> 
        <label style="padding-right: 5px">Unit</label></td>
        
       <td>
         <input name="aim_psn"  type="text" id="t23_seq_unit"  class="easyui-textbox" data-options="editable:false">
         
        </td>
       
    </tr> 
    
        </table>
        
        <br>
    
      
       <table id="t23_seq_caster_events_tbl_id"  toolbar="#t23_seq_caster_events_form_btn_div_id"  title="<spring:message code="label.t18.eofEvents"/>" class="easyui-datagrid" style="width:80%;"
           iconCls='icon-ok'  fitColumns="true"  resizable="true" remoteSort="false"  rownumbers="true" singleSelect="true"> 

        <thead>
            <tr>
                               
              <th field="event_id" formatter="(function(v,r,i){return formatColumnData('eventLkpMdl.lookup_value',v,r,i);})" sortable="false" width="200"><b><spring:message code="label.t18.eofEventName"/></b></th>
              <th field="event_date_time" sortable="true" editor="{type:'datetimebox',options:{required:true,editable:true}}" formatter="(function(v,r,i){return formatDateTime('event_date_time',v,r,i)})"  width="150"><b><spring:message code="label.t18.eofEventDate"/></b></th>
              <th field="event_sl_no" sortable="true" hidden="true" width="0"><b><spring:message code="label.t18.pkId"/></b></th>
         </tr>
         </thead>
    </table>
   
  
         	<div align="center" id="#t23_seq_caster_events_form_btn_div_id">
          <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="saveT23SeqEventDtls()" style="width:90px">Save</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload" onclick="cancelT23SeqEventDtls()" style="width:90px">Refresh</a>
         <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="dialogBoxClose('t23_caster_seq_events_form_div_id')" style="width:90px">Close</a>
         <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" onclick="addT31CastSeqDelayEntry()" style="width:120px">Delay Entry</a>
    </div>
    
     <br>
       <br>
   
   <table id="t23_heats"    title="Heats in Seq" class="easyui-datagrid" style="width:80%;"
           iconCls='icon-ok'  fitColumns="true"  resizable="true" remoteSort="false"  rownumbers="true" singleSelect="true"> 

        <thead>
            <tr>
               <th field="seq_group_no" formatter="(function(v,r,i){return formatColumnData('seq_group_no',v,r,i);})" sortable="false" width="200">Seq Group No</th>               
              <th field="heat_no" formatter="(function(v,r,i){return formatColumnData('heat_no',v,r,i);})" sortable="false" width="200">Heat No</th>
              
         </tr>
         </thead>
    </table>
    
          </form>
    </div>
   
   <!-- Cast Seq Delay Entry Start -->
   <div id="t31_seq_delay_entry_div_id" class="easyui-dialog" style="width:90%;height:350px;margin:5px;" 
            closed="true" data-rowchange="0">
	<form id="t31_seq_delay_entry_form_id" method="post" novalidate>      
		<table style="width: 50%">
		<!-- first row -->
		<tr>
        <td>
			<label>Group seq No</label>
			<!-- <input name="t31_group_seq_no"  type="text" id="t31_group_seq_no"  class="easyui-textbox" data-options="editable:false">  -->
			
			<input name="t31_group_seq_no"  type="text" id="t31_group_seq_no"  class="easyui-combobox" data-options="required:true,panelHeight: 'auto',editable:true,
                    valueField:'id',                    
                    textField:'value'" style="width:100px">
        </td>
		<td> 
			<label>Unit</label>
			<input name="t31_unit"  type="text" id="t31_unit"  class="easyui-textbox" data-options="editable:false">
        </td>         
        </tr>
		</table>
		<br>
		<table id="t31_seq_delay_entry_tbl_id"  toolbar="#t31_seq_delay_entry_form_btn_div_id"  title="Delay Details Entry" class="easyui-datagrid" style="width:90%;height:auto;"
           iconCls='icon-ok' maximizable="false" fitColumns="true"  resizable="true" remoteSort="false"  rownumbers="true" singleSelect="true"> 
		<thead>
            <tr>         
              <th field="activity_master.activities" formatter="(function(v,r,i){return formatColumnData('activity_master.activities',v,r,i);})"  sortable="false" width="65">Activity</th>
              <th field="activity_master.delay_details" formatter="(function(v,r,i){return formatColumnData('activity_master.delay_details',v,r,i);})" sortable="false" width="110" >Delay Details</th>
              <th field="start_time" sortable="false" formatter="(function(v,r,i){return formatDateTime('start_time',v,r,i)})" width="70">Start Time</th>
              <th field="end_time" sortable="false" formatter="(function(v,r,i){return formatDateTime('end_time',v,r,i)})" width="70">End Time</th>
              <th field="duration" sortable="false" width="40" >Duration(min)</th>
              <th field="activity_master.std_cycle_time" formatter="(function(v,r,i){return formatColumnData('activity_master.std_cycle_time',v,r,i);})" sortable="false"  width="40">Standard Duration(min)</th>
              <th field="delay" sortable="false"  width="40">Delay(min)</th>
              <th field="corrective_action" sortable="false"  width="90" editor="{type:'textbox'}">Corrective Action</th>
            </tr>
		</thead>
		</table>
		<br>
		<div id="t31_seq_delay_entry_form_btn_div_id">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="saveT31CastSeqDelaytDtls()" style="width:90px">Save</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload" onclick="getT31CastSeqDelayDetails()" style="width:90px">Refresh</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload" onclick="t31addCastSeqDelayDetails()" style="width:150px">Add Delay Details</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="dialogBoxClose('t31_seq_delay_entry_div_id')" style="width:90px">Close</a>
		</div>
	</form>
</div>
    
    <!-- Details Entry -Sub -->
<div id="t31_seq_delay_agency_div_id" class="easyui-dialog" style="width:100%;height:450px;margin:5px;" 
            closed="true" data-change="0">            
	<form id="t31_seq_delay_agency_form_id" method="post" novalidate>
		<input name="t31seq_activity_value"  type="hidden" id="t31seq_activity_value"  >
		<input name="t31seq_delay_details"  type="hidden" id="t31seq_delay_details"  >
		<input name="t31seq_delay"  type="hidden" id="t31seq_delay"  >
		<input name="seq_trans_delay_dtl_id_value"  type="hidden" id="seq_trans_delay_dtl_id_value"  >

		<table style="width: 60%" class="easyui-panel" >      
		<tr>
        <td>
			<label>Group seq No</label>
			<input name="t31_2_group_seq_no"  type="text" id="t31_2_group_seq_no"  class="easyui-textbox" data-options="editable:false">
        </td>
		<td> 
			<label>Activity</label>
			<input name="t31_seq_2_activity"  type="text" id="t31_seq_2_activity"  class="easyui-textbox" data-options="editable:false">
        </td>         
        </tr>
		</table>
       
		<table id="t31_seq_delay_agency_tbl_id"  toolbar="#t31_seq_delay_agency_form_btn_div_id" 
        title="Delay Details Entry" class="easyui-datagrid" style="width:90%;height:auto;"
        iconCls='icon-ok' maximizable="false" fitColumns="true" 
        data-options="fitColumns:true,singleSelect:true" 
        resizable="true" remoteSort="false"  rownumbers="true" singleSelect="true" data-delay="0"> 
        <thead>
			<tr>      
            <th field="activities" formatter="(function(v,r,i){return formatT31CastSeqActivityColumnData('activities',v,r,i);})"  sortable="false" width="60">Activity</th>
            <th field="delay_details" formatter="(function(v,r,i){return formatT31CastSeqDlyDtlsColumnData('delay_details',v,r,i);})" sortable="false" width="100" >Delay Details</th>
            <th field="delay_reason" width="50"
					formatter="(function(v,r,i){return formatColumnData('delayResonMstrMdl.delay_reason_desc',v,r,i);})"
					editor="{type:'combobox',options:{required:true,editable:true,valueField:'keyval',textField:'txtvalue',
					method:'get',url:'./CommonPool/getComboList?col1=delay_reason_id&col2=delay_reason_desc&classname=DelayReasonMasterModel&status=1&wherecol= delay_status='}}">Reason</th>
			<th field="delay_agency" width="50"
					formatter="(function(v,r,i){return formatColumnData('delayAgencyMstrMdl.agency_desc',v,r,i);})"
					editor="{type:'combobox',options:{required:true,editable:true,valueField:'keyval',textField:'txtvalue',
					method:'get',url:'./CommonPool/getComboList?col1=delay_agency_id&col2=agency_desc&classname=DelayAgencyMasterModel&status=1&wherecol= agency_status='}}">Agency</th>
            <th field="delay_dtl_duration" sortable="false" width="45" editor="{type:'numberbox'}">Delay (Minutes)</th>
            <th field="comments" sortable="false"  width="150" editor="{type:'textbox'}">Comments</th>
            <th field="trans_delay_dtl_id" hidden="true" width="0">edit</th>             	
            </tr>
		</thead>
		</table>
		<br>
        <div id="t31_seq_delay_agency_form_btn_div_id">
			<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true"
			onclick="javascript:$('#t31_seq_delay_agency_tbl_id').edatagrid('addRow')" ><b>Add</b></a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-save" plain="true"
			onclick="javascript:$('#t31_seq_delay_agency_tbl_id').edatagrid('saveRow')"><b>Save</b></a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-reload"
			plain="true" onclick="t31CastSeqRefreshData()"><b>Refresh</b></a>
		</div>
        </form>
</div>
<!-- Cast Seq Delay Entry End -->
   
    <!-- Events Screen End --> 
    
    
    
    
<!-- Heat Details Screen Start -->
  <div id="t23_heatdetails_form_div_id" class="easyui-dialog" style="width:98%;height:600px;padding:5px 5px"
            closed="true">   
     <!-- <div title="Heat Production Details" style="padding:10px">  -->
     <table align="center">
     <tr>
       <td> <label style="padding-right: 10px;"><b><spring:message code="label.t18.eofEventHeatNo"/></b></label></td>
       <td>
        <input name="heat_no"  type="text" id="t23_hdetails_heat_no"  class="easyui-textbox" data-options="editable:false">
        </td>
        <td> &nbsp; &nbsp;&nbsp;&nbsp;</td>
        <td>
        <label style="padding-right: 15px;"><b><spring:message code="label.t18.eofEventSteelQty"/></b></label></td>
        <td> 
        <input name="steel_qty"  type="text" id="t23_hdetails_steel_qty"  class="easyui-textbox" data-options="editable:false">
        </td>
	</tr>
    </table>
     
    <table width="100%">
    <tr>
    <td style="width:62%" valign="top">
    	<table id="t23_heat_plan_line_tbl" title="Plan Summary" class="easyui-datagrid" 
           iconCls='icon-ok'  fitColumns="true"  resizable="true" remoteSort="false"  rownumbers="true" singleSelect="true" style="padding-left:20px;padding-right:20px;"> 
        <thead>
            <tr>
              <th field="plant_id" formatter="(function(v,r,i){return formatColumnData('plant_id',v,r,i);})" sortable="false" width="100">Plan Id</th>             
              <th field="psn_no" formatter="(function(v,r,i){return formatColumnData('psn_no',v,r,i);})" sortable="false" width="170">PSN No</th>
              <th field="section" sortable="true"  formatter="(function(v,r,i){return formatColumnData('section',v,r,i)})"  width="170">Section</th>
              <th field="cut_length" sortable="true" >Cut Length(m)</b></th>
              <th field="plant_qty" sortable="true"> Plan Qty (Tons)   </th>
         	</tr>
        </thead>
    	</table>
    </td>
    <td style="width:38%" rowspan="2" valign="top">
    	<table id="t23_caster_batch_dtls_tbl_id1"  toolbar="#t23_caster_batches_form_btn_div_id1"  title="Actual Batch Details" class="easyui-datagrid" 
			iconCls='icon-ok' style="height:500px" remoteSort="false"  rownumbers="true" singleSelect="true" showFooter="true" > 
    	</table>
    </td>
    <!-- style="width:650px;height:400px"  -->
    </tr>
    <tr>
    <td valign="top">
    	<table id="t23_caster_products_dtls_tbl_id"  toolbar="#t23_caster_products_form_btn_div_id"  title="Heat Details-Billet/Bloom Details" class="easyui-datagrid" 
			 iconCls='icon-ok' remoteSort="false"  rownumbers="true" singleSelect="true">        
    	</table>
    	<!-- style="width:420px;height:550px" -->
    </td>
    </tr>
    </table> 
       	
    <div align="left" id="t23_caster_batches_form_btn_div_id1">
         <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="saveT23ProductBatchDtls()" style="width:90px">Save</a>
     <!--    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" onclick="addBatchEntry()" id="btn_add_batch" style="width:90px">Add</a> -->
       <!--  <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" onclick="removeBatchEntry()"  id="rmv_add_batch" style="width:90px">Remove</a> -->
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="dialogBoxClose('t23_heatdetails_form_div_id')" style="width:90px">Close</a>
    </div>   
    <div align="left" id="t23_caster_products_form_btn_div_id">
       <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="savetT23HeatProdDetails()"  id="save_prod_btn" style="width:90px">Save</a>
       <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload" onclick="T23ProduceBatches()" id="produce_batches_btn" style="width:150px">Produce Batches</a>
 <!-- <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload" onclick="cancelt23ProcParamDtls()" style="width:150px">Insert Cut length</a> -->
   	</div>	
    </div>
 
    <!-- Heat Details Screen End -->
    
    <!-- Tundish Details Screen Start -->
   <div id="t23_tundishdetails_form_div_id" class="easyui-dialog" style="height:270px;width:65%;padding:5px 5px"
           closed="true">
	<div title="Tundish Details" style="padding:10px">
	<input type="hidden" id="t23_tund_trns_id">
	<input type="hidden" id="t23_tundish_id">
    <table style="width: 100%" class="easyui-panel" >    
		<tr style="height:30px;">
			<td style="padding-left: 40px"><label>Heat No</label></td>
       		<td><input name="t23_plant_weight_calculated"  type="text" id="t23_tundishHeatNo" size="15" class="easyui-textbox" data-options="panelHeight: 'auto',editable:false,required:true "> </td>
       		<td style="padding-left: 40px"><label>Tundish Loss (Ton)</label></td>
       		<td><input name="t23_heat_section"  type="text" id="t23_tundishLoss" size="15" class="easyui-textbox" data-options="editable:true"> </td>
       		<td style="padding-left: 40px"><label>Tundish Level at Ladle Open(Ton)</label></td>
       		<td><input name="t23_heat_section"  type="text" id="t23_tundishLevel" size="15" class="easyui-textbox" data-options="editable:true"> </td>
      	</tr>
      	<tr style="height:30px;">
       		<td style="padding-left: 40px"><label>Tundish No</label></td>
       		<!-- <td><input name="t23_plant_weight_calculated"  type="text" id="t23_tundishNo" size="15" class="easyui-combobox" data-options="panelHeight: 'auto',editable:true,required:true,readonly:true,
                   valueField:'keyval',                    
                   textField:'txtvalue'"> </td>  -->
         	<td><input type="text" id="t23_tundishNo" size="15" class="easyui-textbox" data-options="panelHeight: 'auto',editable:false,required:true "> </td>           
       		<td style="padding-left: 40px"><label>Liq Temp</label></td>
       		<td><input name="t23_liqTemp"  type="text" id="t23_liqTemp" size="15" class="easyui-textbox" data-options="editable:false"> </td>
       		
       		<td style="padding-left: 40px"><label>Tundish Heating Temp</label></td>
       		<td><input name="t23_tundish_ar_flow"  type="text" id="t23_tundish_ar_flow" size="15" class="easyui-textbox" data-options="editable:true"> </td>
       </tr>    
       <tr style="height:30px;">
       		<td style="padding-left: 40px"><label>Tundish Temp-1</label></td>
       		<td><input name="t23_tundish_temp1"  id="t23_tundish_temp_1" size="15" class="easyui-numberbox" data-options="editable:true"> </td>
       		<td style="padding-left: 40px"><label>Tundish Temp-2</label></td>
       		<td><input name="t23_tundish_temp2"  id="t23_tundish_temp_2" size="15" class="easyui-numberbox" data-options="editable:true"> </td>
       		<td style="padding-left: 40px"><label>Tundish Temp-3</label></td>
       		<td><input name="t23_tundish_temp3"  id="t23_tundish_temp_3" size="15" class="easyui-numberbox" data-options="editable:true"> </td>
       </tr>
       <tr style="height:30px;">
       		<td style="padding-left: 40px"><label>Super Heat 1</label></td>
       		<td><input name="t23_super_heat1"  id="t23_super_heat1" size="15" class="easyui-numberbox" data-options="editable:false"> </td>
       		<td style="padding-left: 40px"><label>Super Heat 2</label></td>
       		<td><input name="t23_super_heat2"  id="t23_super_heat2" size="15" class="easyui-numberbox" data-options="editable:false"> </td>
       		<td style="padding-left: 40px"><label>Super Heat 3</label></td>
       		<td><input name="t23_super_heat3"  id="t23_super_heat3" size="15" class="easyui-numberbox" data-options="editable:false"> </td>
       </tr>
    </table>
	<br>
	<div align="center" id="t23_process_param_form_btn_div_id">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="savett23TundishDtls()" style="width:90px">Save</a>
       	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload" onclick="cancelt23ProcParamDtls()" style="width:90px">Refresh</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="dialogBoxClose('t23_tundishdetails_form_div_id')" style="width:90px">Close</a>
   	</div>
</div>
</div>
<!-- Tundish Details Screen End -->   
<div id="t23_mould_details_form_div_id" class="easyui-dialog" style="height:400px;width:65%;padding:5px 5px"
            closed="true">
    
   
     <div title="Mould Details" style="padding:10px">
     
     <table id="t23_caster_mould_dtls_tbl_id"  toolbar="#t23_mould_form_btn_div_id"  title="Mould Details Entry" class="easyui-datagrid" 
		style="width:700px;height:300px"
           iconCls='icon-ok'     remoteSort="false"  rownumbers="true" singleSelect="true"> 
    </table>
    
    <div align="left" id="t23_mould_form_btn_div_id">
    <input type="hidden" id="t2_billet_max_length" >
       <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" id="t23_btn_save" onclick="savetT23MTube()" style="width:90px">Save</a>
       <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload" onclick="T23MtubeSendCleaning()" style="width:150px">Send for cleaning</a>
  <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload" onclick="T23MtubeSendScrap()" style="width:150px">Scrap/Discard</a>
   	</div>
	 </div>
</div>
<!-- Mould Details Screen start -->

<!-- End -->

<!-- Chemistry Screen Start -->
<input type="hidden" value="null" id="rawdata_heatno">
<input type="hidden" value="null" id="rawdata_heatcounter">
<input type="hidden" value="null" id="rawdata_subunit">
<input type="hidden" value="null" id="rawdata_psnno">
<input type="hidden" value="null" id="rawdata_spectrochem">
<input type="hidden" value="null" id="t23_lift_chem_id">
<!-- <input type="hidden" value=0 id="t23_tundish_chem"> -->

<div id="t23_chemDetails_form_div_id" class="easyui-dialog" style="height:600px;padding:5px 5px;"
           closed="true">
            <form id="t23_ccm_Chemistry_form_id" method="post" novalidate>
            <input name="t23_sample_si_no"  type="hidden" id="t23_sample_si_no">
   			<input name="t23_chem_level"  type="hidden" id="t23_chem_level">
 <table style="width: 100%;padding-left: 20px;padding-right: 20px;margin-left:15px" class="easyui-panel" >
         <tr height="40">
<!--        <input name="sample_si_no" type="hidden"  id="t7_sample_si_no"> -->
       <td style="width: 100px;"> 
       <label style="padding-right: 5px;display:block; width:x; height:y; text-align:left;"><b><spring:message code="label.t17.lrfChemHeatNo"/></b></label></td><td>
        <!--  <input name="heat_no"  type="text" id="t23_chem_heat_no"  class="easyui-textbox" data-options="editable:false" style="width:90px"> -->
          <input name="heat_no"  type="text" id="t23_chem_heat_no"  class="easyui-combobox" data-options="panelHeight:100,editable:true,required:true,
                    valueField:'keyval',                    
                    textField:'txtvalue'"  style="width:90px">
        </td>
        
       <td style="width: 100px;"> 
        <label style="padding-right: 5px;display:block; width:x; height:y; text-align:right;"><b><spring:message code="label.t17.lrfChemAimPsn"/></b></label></td><td>
         <input name="aim_psn"  type="text" id="t23_chem_aim_psn"  class="easyui-textbox" data-options="editable:false" style="width:150px">
         
        </td >
         <td style="width: 100px;"> 
        <label style="padding-right: 5px;display:block; width:x; height:y; text-align:right;" ><b><spring:message code="label.t17.lrfChemAnalysis" /></b></label>
       </td><td>
                <input name="analysis_type"  type="text" id="t23_analysis_type"  class="easyui-combobox"  data-options="panelHeight: 'auto',editable:true,required:true,
                    valueField:'keyval',                    
                    textField:'txtvalue'" style="width:150px">
       </td>
        <td style="width: 100px;">
        <label style="padding-right: 0px;display:block; width:x; height:y; text-align:right;"><b><spring:message code="label.t17.lrfChemSampleno"/></b></label>
        </td><td>
         <input name="sample_no"  type="text" id="t23_sample_no" class="easyui-combobox" data-options="readonly:true,required:true,valueField:'keyval',                    
                    textField:'txtvalue',hasDownArrow:false">
         <!-- <a href="javascript:void(0)" id="t7_spectro"  class="easyui-linkbutton" iconCls="icon-ok" onclick="ccm_level2_server()" style="width:30px"></a> -->
        </td>
    </tr> 
   
        <tr height="40">
        
         <td style="width: 100px;">
        <label style="padding-right: 5px;display:block; width:x; height:y; text-align:left;"><b><spring:message code="label.t17.lrfChemSampleTemp"/></b></label>
        </td><td>
         <input name="sample_temp"  type="text" id="t23_sample_temp"  class="easyui-numberbox" data-options="editable:true" style="width:90px">
        </td>
         <td style="width: 100px;"> 
        <label style="padding-right: 0px;display:block; width:x; height:y; text-align:right;"><b><spring:message code="label.t17.lrfChemSampleDate"/></b></label></td><td>
         <input name="sample_date_time"  type="text" id="t23_sample_date_time"  class="easyui-datetimebox" data-options="required:true,showSeconds:false" style="width:150px">
        </td>
        
         <td style="width: 100px;"> 
        <label style="padding-right: 0px;display:block; width:x; height:y; text-align:right;"><b><spring:message code="label.t17.lrfChemSampleResult"/></b></label>
        </td><td>
                <input name="sample_result"  type="text" id="t23_sample_result"  class="easyui-combobox"  data-options="panelHeight: 'auto',required:true,
                    editable:false,valueField:'keyval',                    
                    textField:'txtvalue'" style="width:150px">
       </td>
       <td colspan="2" style="padding-left: 40px">
       	  <a href="javascript:void(0)" class="easyui-linkbutton" id="t23_getSample_btn" iconCls="icon-ok" onclick="GetSample()" >Generate Sample No</a>
       </td>
        <td >
        
        </td>
        
        </tr>
        
        <tr height="40">
       
         <td> 
        <label style="padding-right: 5px;display:block; width:x; height:y; text-align:left;"><b><spring:message code="label.t17.lrfChemRemarks"/></b></label>
       </td>
       <td  colspan="2">
         <input name="remarks"  type="text" id="t23_remarks"  data-options="multiline:true" style="height:30px;width: 100%" class="easyui-textbox">
        
        </td>
        </tr>
 </table>
 
      <br>
    
       <table style="width: 100%;">
		<tr>
		<td width="70%">
		<div title="<spring:message code="label.t17.lrfChemDetails"/>" style="padding:10px;height: 300px">
       	<table id="t23_caster_Chemistry_tbl_id" toolbar="#t23_ccm_Chemistry__form_btn_div_id"  title="<spring:message code="label.t17.lrfChemDetails"/>" class="easyui-datagrid" style="width:100%;height:auto;"
           iconCls='icon-ok' maximizable="true" nowrap="false"  resizable="true" remoteSort="false"  rownumbers="true" singleSelect="true" > 

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
              <th field="aim_value" align="right" editor="{type:'numberbox',options:{precision:4,min:0}}"   width="120"><b>ACTUA_VALUE<font color="red">*</font></b></th>
              <th field="dtls_si_no" sortable="true" hidden="true" width="0"><b><spring:message code="label.t7.pkId"/></b></th>
               <th field="remarks" sortable="false" align="left" width="165" hidden="true" ><b>SPECTRO LAB REMARKS</b></th>
         </tr>
         </thead>
    	</table>
    	
    	</div>
    	</td>
		<td width="30%">
    	<!-- second grid -->
		<div title="Samples" style="padding: 10px; height: 300px">					
			<table id="t23_caster_chem_samp_tbl_id"
							toolbar="#t23_ccm_Chem_Sample_form_btn_div_id"
							title="List of Samples"
							class="easyui-datagrid" style="width: 100%"
							iconCls='icon-ok' maximizable="false" resizable="true"
							remoteSort="false" rownumbers="true" singleSelect="true">
				<thead>
					<tr>
					<th field="sample_si_no" hidden="true" width="0"><b>Chem Header Id</b></th>
						<th field="sample_no" width="80"><b>Sample No</b></th>
						<th field="action" width="80" formatter="viewT23SampleDtls"><b>Action     </b></th>
					    <th field="approved_by"  width="100"><b>APPROVED BY</b></th>
					    <th field="updatedDateTime" formatter="(function(v,r,i){return formatDateTime('updatedDateTime',v,r,i)})"
					      width="100"><b>APPROVE DATE/TIME</b></th>
						<th field="approve" width="50"><b>APPROVE</b></th>
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
		
		<div id="t23_ccm_Chemistry__form_btn_div_id">
         	<a href="javascript:void(0)" class="easyui-linkbutton" id="t23_save_chem_btn_id" iconCls="icon-save" onclick="saveT23ChemistryDtls()" style="width:90px">Save</a>
        	<!-- <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload" onclick="cancelT17ChemistryDtls()" style="width:90px">Refresh</a> -->
         	<a href="javascript:void(0)" class="easyui-linkbutton" id="t23_close_chem_btn_id" iconCls="icon-cancel" onclick="dialogBoxClose('t23_chemDetails_form_div_id')" style="width:90px">Close</a>
    	</div>
    	<div id="t23_ccm_Chem_Sample_form_btn_div_id">
			<a href="javascript:void(0)" class="easyui-linkbutton" id="t23_final_result_btn_id" onclick="saveFinalResult()"
				style="width: 90px">Final Result</a>
				
				<!-- <a href="javascript:void(0)" class="easyui-linkbutton" id="t23_final_result_apprv_id" onclick="approved()"
				style="width: 90px">Approved</a> -->
		</div>
</form>
</div>
<!-- Chemistry Screen End -->
<!-- Approve chem screen start-->
<input type="hidden" value="null" id="rawdata_heatno">
<input type="hidden" value="null" id="rawdata_heatcounter">
<input type="hidden" value="null" id="rawdata_subunit">
<input type="hidden" value="null" id="rawdata_psnno">
<input type="hidden" value="null" id="rawdata_spectrochem">
<input type="hidden" value="null" id="t24_lift_chem_id">
<!-- <input type="hidden" value=1 id="t24_tundish_chem"> --> 
<div id="t23_chemDetails_apprv_div_id" class="easyui-dialog" style="height:600px;padding:5px 5px;"
           closed="true">
            <form id="t23_ccm_Chemistry_form_id12" method="post" novalidate>
            <input name="t24_sample_si_no"  type="hidden" id="t24_sample_si_no">
   			<input name="t24_chem_level"  type="hidden" id="t24_chem_level">
 <table style="width: 100%;padding-left: 20px;padding-right: 20px;margin-left:15px" class="easyui-panel" >
         <tr height="40">
<!--        <input name="sample_si_no" type="hidden"  id="t7_sample_si_no"> -->
        
     <td style="width: 100px;">
      <label style="padding-right: 5px;display:block; width:x; height:y; text-align:left;"><b><spring:message code="label.t17.lrfChemmaterialtype"/></b></label></td><td>
        <input name="product_id"  type="text" id="t24_product_id"  class="easyui-combobox" data-options="panelHeight:100,editable:true,required:true,
                    valueField:'keyval',                    
                    textField:'txtvalue'"  style="width:100px">
        </td>
        
       <td style="width: 100px;"> 
       <label style="padding-right: 5px;display:block; width:x; height:y; text-align:left;"><b><spring:message code="label.t17.lrfChemHeatNo"/></b></label></td><td>
        <!--  <input name="heat_no"  type="text" id="t23_chem_heat_no"  class="easyui-textbox" data-options="editable:false" style="width:90px"> -->
          <input name="heat_no"  type="text" id="t24_chem_heat_no"  class="easyui-combobox" data-options="panelHeight:100,editable:true,required:true,
                    valueField:'keyval',                    
                    textField:'txtvalue'"  style="width:90px">
        </td>
        
       <td style="width: 100px;"> 
        <label style="padding-right: 5px;display:block; width:x; height:y; text-align:right;"><b><spring:message code="label.r1.PsnListRptPsnNo"/></b></label></td><td>
         <input name="aim_psn"  type="text" id="t24_chem_aim_psn"  class="easyui-textbox" data-options="editable:false" style="width:150px">
         
        </td >
         <td style="width: 100px;"> 
        <label style="padding-right: 5px;display:block; width:x; height:y; text-align:right;" ><b><spring:message code="label.t17.lrfChemAnalysis" /></b></label>
       </td><td>
                <input name="analysis_type"  type="text" id="t24_analysis_type"  class="easyui-combobox"  data-options="panelHeight: 'auto',editable:true,required:true,
                    valueField:'keyval',                    
                    textField:'txtvalue'" style="width:150px">
       </td>
        <td style="width: 100px;">
        <label style="padding-right: 0px;display:block; width:x; height:y; text-align:right;"><b><spring:message code="label.t17.lrfChemSampleno"/></b></label>
        </td><td>
         <input name="sample_no"  type="text" id="t24_sample_no" class="easyui-combobox" data-options="readonly:true,required:true,valueField:'keyval',                    
                    textField:'txtvalue',hasDownArrow:false">
         <!-- <a href="javascript:void(0)" id="t7_spectro"  class="easyui-linkbutton" iconCls="icon-ok" onclick="ccm_level2_server()" style="width:30px"></a> -->
        </td>
    </tr> 
   
        <tr height="40">
        
         <td style="width: 100px;">
        <label style="padding-right: 5px;display:block; width:x; height:y; text-align:left;"><b><spring:message code="label.t17.lrfChemSampleTemp"/></b></label>
        </td><td>
         <input name="sample_temp"  type="text" id="t24_sample_temp"  class="easyui-numberbox" data-options="editable:true" style="width:90px">
        </td>
         <td style="width: 100px;"> 
        <label style="padding-right: 0px;display:block; width:x; height:y; text-align:right;"><b><spring:message code="label.t17.lrfChemSampleDate"/></b></label></td><td>
         <input name="sample_date_time"  type="text" id="t24_sample_date_time"  class="easyui-datetimebox" data-options="required:true,showSeconds:false" style="width:150px">
        </td>
        
         <td style="width: 100px;"> 
        <label style="padding-right: 0px;display:block; width:x; height:y; text-align:right;"><b><spring:message code="label.t17.lrfChemSampleResult"/></b></label>
        </td><td>
                <input name="sample_result"  type="text" id="t24_sample_result"  class="easyui-combobox"  data-options="panelHeight: 'auto',required:true,
                    editable:false,valueField:'keyval',                    
                    textField:'txtvalue'" style="width:150px">
       </td>
       <td style="width: 100px;"> 
        <label style="padding-right: 5px;display:block; width:x; height:y; text-align:right;"><b><spring:message code="label.t17.lrfChemGrade"/></b></label></td><td>
         <input name="grade"  type="text" id="t24_chem_grade"  class="easyui-textbox" data-options="editable:false" style="width:150px">
         
        </td >
       <td colspan="2" style="padding-left: 40px">
       	  <a href="javascript:void(0)" class="easyui-linkbutton" id="t24_getSample_btn" iconCls="icon-ok" onclick="GetSample1()" >Generate Sample No</a>
       </td>
        <td >
        
        </td>
        
        </tr>
        
        <tr height="40">
       
         <td> 
        <label style="padding-right: 5px;display:block; width:x; height:y; text-align:left;"><b><spring:message code="label.t17.lrfChemRemarks"/></b></label>
       </td>
       <td  colspan="2">
         <input name="remarks"  type="text" id="t24_remarks"  data-options="multiline:true" style="height:30px;width: 100%" class="easyui-textbox">
        
        </td>
        </tr>
 </table>
 
      <br>
    
       <table style="width: 100%;">
		<tr>
		<td width="70%">
		<div title="<spring:message code="label.t17.lrfChemDetailsApp"/>" style="padding:10px;height: 300px">
       	<table id="t24_caster_Chemistry_tbl_id" toolbar="#t23_ccm_Chemistry__form_btn_div_id1"  title="<spring:message code="label.t17.lrfChemDetailsApp"/>" class="easyui-datagrid" style="width:100%;height:auto;"
           iconCls='icon-ok' maximizable="true" nowrap="false"  resizable="true" remoteSort="false"  rownumbers="true" singleSelect="true" > 

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
              <th field="aim_value" align="right" editor="{type:'numberbox',options:{precision:4,min:0}}"   width="120"><b>ACTUAL_VALUE<font color="red">*</font></b></th>
              <th field="dtls_si_no" sortable="true" hidden="true" width="0"><b><spring:message code="label.t7.pkId"/></b></th>
               <th field="remarks" sortable="false" align="left" width="165" hidden="true" ><b>SPECTRO LAB REMARKS</b></th>
         </tr>
         </thead>
    	</table>
    	
    	</div>
    	</td>
		<td width="30%">
    	<!-- second grid -->
		<div title="Samples" style="padding: 10px; height: 300px">					
			<table id="t24_caster_chem_samp_tbl_id"
							toolbar="#t23_ccm_Chem_Sample_form_btn_div_id12"
							title="List of Samples"
							class="easyui-datagrid" style="width: 100%"
							iconCls='icon-ok' maximizable="false" resizable="true"
							remoteSort="false" rownumbers="true" singleSelect="true">
				<thead>
					<tr>
					<th field="sample_si_no" hidden="true" width="0"><b>Chem Header Id</b></th>
						<th field="sample_no" width="140"><b>Sample No</b></th>
						<th field="action" width="140" formatter="viewT24SampleDtls"><b>Action     </b></th>
						<th field="approve" width="140"><b>APPROVE</b></th>
						
						
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
		
		<div id="t23_ccm_Chemistry__form_btn_div_id1">
         	<a href="javascript:void(0)" class="easyui-linkbutton" id="t24_save_chem_btn_id" iconCls="icon-save" onclick="saveT24ChemistryDtls()" style="width:90px">Save</a>
        	<!-- <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload" onclick="cancelT17ChemistryDtls()" style="width:90px">Refresh</a> -->
         	<a href="javascript:void(0)" class="easyui-linkbutton" id="t23_close_chem_btn_id" iconCls="icon-cancel" onclick="dialogBoxClose('t23_chemDetails_apprv_div_id')" style="width:90px">Close</a>
    	</div>
    	<div id="t23_ccm_Chem_Sample_form_btn_div_id12">
			
				<a href="javascript:void(0)" class="easyui-linkbutton" id="t23_final_result_apprv_id" onclick="approved()"
				style="width: 90px">Approve</a>
		</div>
</form>
</div>


<!-- Approve chem screen end -->
<!-- Strand Selection dia-->
<div id="t23_strand_selection_id" class="easyui-dialog" style="height:100px;padding:5px 5px;width:40%"
           closed="true">
           
             <table>
             <tr>Stand No<td>
             </td>
             <td>
                <input name="strand_no"  type="text" id="t23_strand_no"  class="easyui-combobox"  data-options="panelHeight: 'auto',editable:true,required:true,
                    valueField:'keyval',                    
                    textField:'txtvalue'" style="width:100px">
           
       <td colspan="2" style="padding-left: 40px">
       	  <a href="javascript:void(0)" class="easyui-linkbutton" id="t23_getSample_btn" iconCls="icon-ok" onclick="saveAndLoadBatchEntry()" >Proceed</a>
      </td>
          </tr>
           
             </table>
</div>
<!-- end -->

<!-- By Products -->
      <div id="t23_ccm_byProducts_form_div_id" class="easyui-dialog" style="height:550px;padding:10px 10px"
            closed="true" data-change="0">
            
     <div title="CCM BY Products" style="padding:10px">
     <table style="width: 100%" class="easyui-panel" >
        <tr>
        <td>
        <label>HeatNo</label>
         <input name="heat_no"  type="text" id="t23_ccm_heat_no"  class="easyui-textbox" data-options="editable:false">
        </td>
        
       <td> 
        <label>PSN No</label>
         <input name="aim_psn"  type="text" id="t23_ccm_aim_psn"  class="easyui-textbox" data-options="editable:false">
         
        </td>
         
        </tr>
      </table>
     <table id="t23_ccm_byProducts_tbl_id" toolbar="#t23_eof_byProducts_form_btn_div_id"  title="CCM By Products" class="easyui-datagrid" style="width:100%;height:auto;"
           iconCls='icon-ok' maximizable="false"  resizable="true" showFooter="true" remoteSort="false"  rownumbers="true" singleSelect="true"> 
        <thead>
            <tr>
              <th field="material_id" formatter="(function(v,r,i){return formatColumnData('mtrlName',v,r,i);})" sortable="false" width="200">ByProduct Name</th>
              <th field="qty" align="right" formatter="(function(v,r,i){return formatColumnData('qty',v,r,i);})" data-options="editor:{type:'numberbox',options:{precision:2,min:0}}"  width="100">Qty</th>
              <th field="sap_matl_id" align="right" formatter="(function(v,r,i){return formatColumnData('sap_matl_id',v,r,i);})" width="150">SAP Matl ID</th>
               <th field="uom" formatter="(function(v,r,i){return formatColumnData('uom',v,r,i);})" sortable="false" width="100">UOM</th>
               <th field="val_min" align="right" hidden="true">Min Value</th>
              <th field="val_max" align="right" hidden="true">Max Value</th>
              <th field="mtr_cons_si_no" sortable="true" hidden="true" width="0"><spring:message code="label.t5.pkId"/></th>
              <th field="consumption_date" sortable="true" editor="{type:'datetimebox',options:{required:false,editable:true}}" formatter="(function(v,r,i){return formatDateTime('consumption_date',v,r,i)})"  width="150">Produced Date</th>
         <th field="version" hidden="true" align="right" width="100">Version</th>
         </tr>
         </thead>
    </table>
      <div  id="t23_eof_byProducts_form_btn_div_id">
         <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="saveT23CCMByProductsDtls()" style="width:90px">Save</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload" onclick="cancelT17ByProductsDtls()" style="width:90px">Refresh</a>
         <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="dialogBoxClose('t23_ccm_byProducts_form_div_id')" style="width:90px">Close</a>
    </div>
     
    
     </div>
   
     </div>
     <!-- End -->
     <!-- Mould Selection dia-->
<div id="t23_mould_selection_id" class="easyui-dialog" style="height:100px;padding:5px 5px;width:40%"
           closed="true">
           
             <table>
             <tr>
             <!--  <td>
                <input name="strand_no"  type="text" id="t23_std_no_c"  class="easyui-combobox"  data-options="panelHeight: 'auto',editable:true,required:true,
                    valueField:'keyval',                    
                    textField:'txtvalue'" style="width:150px">
       </td> -->
              <td>
                <input name="strand_no"  type="text" id="t23_jkt_no"  class="easyui-combobox"  data-options="panelHeight: 'auto',editable:true,required:true,
                    valueField:'keyval',                    
                    textField:'txtvalue'" style="width:150px">
       </td> 
       <td colspan="2" style="padding-left: 40px">
       	  <a href="javascript:void(0)" class="easyui-linkbutton" id="t23_getSample_btn" iconCls="icon-ok" onclick="ProceedT23MtubeSendCleaning()" >Proceed</a>
       </td>
             </tr>
             </table>
</div>
<!-- end -->

    <!-- Mould Selection dia-->
<div id="t23_mould_R_selection_id" class="easyui-dialog" style="height:100px;padding:5px 5px;width:40%"
           closed="true">
           
             <table>
             <tr>
             <!--  <td>
                <input name="strand_no"  type="text" id="t23_Rstd_no_c"  class="easyui-combobox"  data-options="panelHeight: 'auto',editable:true,required:true,
                    valueField:'keyval',                    
                    textField:'txtvalue'" style="width:150px">
       </td> -->
            <td>
                <input name="strand_no"  type="text" id="t23_Rjkt_no"  class="easyui-combobox"  data-options="panelHeight: 'auto',editable:true,required:true,
                    valueField:'keyval',                    
                    textField:'txtvalue'" style="width:150px">
       </td> 
       <td colspan="2" style="padding-left: 40px">
       	  <a href="javascript:void(0)" class="easyui-linkbutton" id="t23_getSample_btn" iconCls="icon-ok" onclick="ProceedT23MtubeSendScrap()" >Proceed</a>
       </td>
             </tr>
             </table>
</div>
<!-- Lift Chemistry Screen Start -->
<div id="t23_liftChemDet_form_div_id" class="easyui-dialog" style="width:85%;height:600px;padding:5px 5px;" closed="true">
	<form id="t23_liftChemDet_form_id" method="post" novalidate>
		<table style="width: 100%;padding-left: 20px;padding-right: 20px;margin-left:15px" class="easyui-panel" >
			<tr height="40">
				<td style="width: 100px;"> 
					<label style="padding-right: 5px;display:block; width:x; height:y; text-align:left;"><b><spring:message code="label.t17.lrfChemHeatNo"/></b></label></td><td>
					<input name="heat_no"  type="text" id="t23_lift_chem_heat_no"  class="easyui-textbox" data-options="editable:false" style="width:90px">
				</td>
				<td style="width: 100px;"> 
					<label style="padding-right: 5px;display:block; width:x; height:y; text-align:right;"><b><spring:message code="label.t17.lrfChemAimPsn"/></b></label></td><td>
					<input name="aim_psn"  type="text" id="t23_lift_chem_aim_psn"  class="easyui-textbox" data-options="editable:false" style="width:150px">
				</td>
				<td style="width: 100px;"> 
					<label style="padding-right: 5px;display:block; width:x; height:y; text-align:right;"><b>Grade</b></label></td><td>
					<input name="grade"  type="text" id="t23_lift_chem_grade"  class="easyui-textbox" data-options="editable:false" style="width:150px">
				</td>
			</tr> 
		</table>
		<br>
		<div title="<spring:message code="label.t17.lrfChemDetails"/>" style="padding:10px;height: 300px">
			<table id="t23_liftChemDet_tbl_id" padding-left: 40px;padding-right: 20px;margin-left:25px" title="<spring:message code="label.t17.lrfChemDetails"/>" class="easyui-datagrid" style="width:80%;height:auto;"
				iconCls='icon-ok' maximizable="true"  resizable="true" remoteSort="false"  rownumbers="true" singleSelect="true" > 
			<thead>
            <tr>                
				<th rowspan="2" field="element" formatter="(function(v,r,i){return formatColumnData('elementName',v,r,i);})" sortable="false" width="120"><b>ELEMENT</b></th>
				<th rowspan="2" field="uom" formatter="(function(v,r,i){return formatColumnData('uom',v,r,i);})" sortable="false" width="100"><b><spring:message code="label.t5.uom"/></b></th>
				<th colspan="4"><b>PSN VALUES</b></th>
			</tr>
			<tr>
				<th field="min_value" width="115"><b>MIN_VALUE</b></th>
				<th field="max_value" width="115"><b>MAX_VALUE</b></th>
				<th field="psn_aim_value" width="115"><b>AIM_VALUE</b></th>
				<th field="aim_value" align="right"  editor="{type:'numberbox',options:{precision:4,min:0}}"  width="120"><b>ACTUAL_VALUE<font color="red">*</font></b></th>
				<th field="dtls_si_no" sortable="true" hidden="true" width="0"><b><spring:message code="label.t7.pkId"/></b></th>
			</tr>
			</thead>
			</table>
			<br>
		</div>		
	</form>
</div>
<!-- Lift Chemistry Screen End -->

    <!-- Delay Entry Start -->
<div id="t31_delay_entry_div_id" class="easyui-dialog" style="width:90%;height:350px;margin:5px;" 
            closed="true" data-rowchange="0">
	<form id="t31_delay_entry_form_id" method="post" novalidate>      
		<table style="width: 50%">
		<!-- first row -->
		<tr>
        <td>
			<label><spring:message code="label.t17.lrfChemHeatNo"/></label>
			<input name="heat_no"  type="text" id="t31_heat_no"  class="easyui-textbox" data-options="editable:false">
        </td>
		<td> 
			<label><spring:message code="label.t17.lrfChemAimPsn"/></label>
			<input name="aim_psn"  type="text" id="t31_aim_psn"  class="easyui-textbox" data-options="editable:false">
        </td>         
        </tr>
		</table>
		<br>
		<table id="t31_delay_entry_tbl_id"  toolbar="#t31_delay_entry_form_btn_div_id"  title="Delay Details Entry" class="easyui-datagrid" style="width:90%;height:auto;"
           iconCls='icon-ok' maximizable="false" fitColumns="true"  resizable="true" remoteSort="false"  rownumbers="true" singleSelect="true"> 
		<thead>
            <tr>         
              <th field="activity_master.activities" formatter="(function(v,r,i){return formatColumnData('activity_master.activities',v,r,i);})"  sortable="false" width="90">Activity</th>
              <th field="activity_master.delay_details" formatter="(function(v,r,i){return formatColumnData('activity_master.delay_details',v,r,i);})" sortable="false" width="80" >Delay Details</th>
              <th field="start_time" sortable="false" formatter="(function(v,r,i){return formatDateTime('start_time',v,r,i)})" width="70">Start Time</th>
              <th field="end_time" sortable="false" formatter="(function(v,r,i){return formatDateTime('end_time',v,r,i)})" width="70">End Time</th>
              <th field="duration" sortable="false" width="40" >Duration(min)</th>
              <th field="activity_master.std_cycle_time" formatter="(function(v,r,i){return formatColumnData('activity_master.std_cycle_time',v,r,i);})" sortable="false"  width="40">Standard Duration(min)</th>
              <th field="delay" sortable="false"  width="40">Delay(min)</th>
              <th field="corrective_action" sortable="false"  width="90" editor="{type:'textbox'}">Corrective Action</th>
            </tr>
		</thead>
		</table>
		<br>
		<div id="t31_delay_entry_form_btn_div_id">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="saveT31DelaytDtls()" style="width:90px">Save</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload" onclick="getT31DelayDetails()" style="width:90px">Refresh</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload" onclick="t31addDelayDetails()" style="width:150px">Add Delay Details</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="dialogBoxClose('t31_delay_entry_div_id')" style="width:90px">Close</a>
		</div>
	</form>
</div>
    
    <!-- Details Entry -Sub -->
<div id="t31_delay_agency_div_id" class="easyui-dialog" style="width:100%;height:450px;margin:5px;" 
            closed="true" data-change="0">            
	<form id="t31_delay_agency_form_id" method="post" novalidate>
		<input name="t31activity_value"  type="hidden" id="t31activity_value"  >
		<input name="t31delay_details"  type="hidden" id="t31delay_details"  >
		<input name="t31delay"  type="hidden" id="t31delay"  >
		<input name="trans_delay_dtl_id_value"  type="hidden" id="trans_delay_dtl_id"  >

		<table style="width: 100%" class="easyui-panel" >      
		<tr>
        <td>
			<label><spring:message code="label.t17.lrfChemHeatNo"/></label>
			<input name="heat_no"  type="text" id="t31_2_heat_no"  class="easyui-textbox" data-options="editable:false">
        </td>
		<td> 
			<label><spring:message code="label.t17.lrfChemAimPsn"/></label>
			<input name="aim_psn"  type="text" id="t31_2_aim_psn"  class="easyui-textbox" data-options="editable:false">
        </td>   
         <td> 
			<label>Activity</label>
			<input name="aim_psn"  type="text" id="t31_2_activity"  class="easyui-textbox" data-options="editable:false">
        </td>         
        </tr>
		</table>
       
		<table id="t31_delay_agency_tbl_id"  toolbar="#t31_delay_agency_form_btn_div_id" 
        title="Delay Details Entry" class="easyui-datagrid" style="width:90%;height:auto;"
        iconCls='icon-ok' maximizable="false" fitColumns="true" 
        data-options="fitColumns:true,singleSelect:true" 
        resizable="true" remoteSort="false"  rownumbers="true" singleSelect="true" data-delay="0"> 
        <thead>
			<tr>      
            <th field="activities" formatter="(function(v,r,i){return formatT31ActivityColumnData('activities',v,r,i);})"  sortable="false" width="70">Activity</th>
            <th field="delay_details" formatter="(function(v,r,i){return formatT31DlyDtlsColumnData('delay_details',v,r,i);})" sortable="false" width="100" >Delay Details</th>
            <th field="delay_reason" width="50"
					formatter="(function(v,r,i){return formatColumnData('delayResonMstrMdl.delay_reason_desc',v,r,i);})"
					editor="{type:'combobox',options:{required:true,editable:true,valueField:'keyval',textField:'txtvalue',
					method:'get',url:'./CommonPool/getComboList?col1=delay_reason_id&col2=delay_reason_desc&classname=DelayReasonMasterModel&status=1&wherecol= delay_status='}}">Reason</th>
			<th field="delay_agency" width="50"
					formatter="(function(v,r,i){return formatColumnData('delayAgencyMstrMdl.agency_desc',v,r,i);})"
					editor="{type:'combobox',options:{required:true,editable:true,valueField:'keyval',textField:'txtvalue',
					method:'get',url:'./CommonPool/getComboList?col1=delay_agency_id&col2=agency_desc&classname=DelayAgencyMasterModel&status=1&wherecol= agency_status='}}">Agency</th>
            <th field="delay_dtl_duration" sortable="false" width="80" editor="{type:'numberbox'}">Delay (Minutes)</th>
            <th field="trans_delay_dtl_id" hidden="true" width="0">edit</th>             	
            </tr>
		</thead>
		</table>
		<br>
        <div id="t31_delay_agency_form_btn_div_id">
			<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true"
			onclick="javascript:$('#t31_delay_agency_tbl_id').edatagrid('addRow')" ><b>Add</b></a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-save" plain="true"
			onclick="javascript:$('#t31_delay_agency_tbl_id').edatagrid('saveRow')"><b>Save</b></a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-reload"
			plain="true" onclick="t31RefreshData()"><b>Refresh</b></a>
		</div>
        </form>
</div>
<!-- Delay Entry End -->

<!-- end -->  
  <script>
  var user_id= <%=session.getAttribute("USER_APP_ID") %>;
  $(window).load(setTimeout(applyT2Filter,1));  //1000 ms = 1 second.
	
  function applyT2Filter(){
	$('#t23_heatplan_tbl_id').datagrid('enableFilter'); 	
  }
 
  function callT23Dropdowns(){
	var user_id= <%=session.getAttribute("USER_APP_ID") %>
	 	
  	var url1="./CommonPool/getComboList?col1=lookup_id&col2=lookup_value&classname=LookupMasterModel&status=1&wherecol=lookup_type='SHIFT' and lookup_status=";
  	var url2="./CommonPool/getComboList?col1=sub_unit_id&col2=subUnitMstrMdl.sub_unit_name&classname=UserSubUnitMasterModel&status=1&wherecol=app_user_id="+user_id+" and subUnitMstrMdl.unitDetailsMstrMdl.unit_name in ('CCM') and record_status=";
	getDropdownList(url1,'#t23_shift');
  	getDropdownList(url2,'#t23_casterUnit');
 	
  }
function getCrewDetails(){
	//alert('in het crew ');
}
  
 $("#t23_casterUnit").combobox({
  	onSelect:function(record){
  		var selUnit = record.txtvalue; 
  		var unitIncharge=null;
 		var unitMgr=null;
 		var ccmMcdOpr=null;
 		var ccmGasCutter=null;

  		unitIncharge='CCM_SHIFT_INCHARGE';
 		//unitMgr='CCM_SHIFT_MGR';//
 		ccmMcdOpr='CCM_OPERATOR';
 		//ccmGasCutter='CCM_GAS_CUTTER';
  		//url3 = "./CommonPool/getComboList?col1=user_role_id&col2=appUserAccountDetails.user_name&classname=UserRoleMapMasterModel&status=1&wherecol=lookupMasterModel.lookup_code='"+unitMgr+"' and lookupMasterModel.lookup_id=lookup_id  and record_status=";
 		url4="./CommonPool/getComboList?col1=user_role_id&col2=appUserAccountDetails.user_name&classname=UserRoleMapMasterModel&status=1&wherecol=lookupMasterModel.lookup_code='"+unitIncharge+"' and lookupMasterModel.lookup_id=lookup_id  and record_status=";
 		url5="./CommonPool/getComboList?col1=user_role_id&col2=appUserAccountDetails.user_name&classname=UserRoleMapMasterModel&status=1&wherecol=lookupMasterModel.lookup_code='"+ccmMcdOpr+"' and lookupMasterModel.lookup_id=lookup_id  and record_status=";
 		//url7="./CommonPool/getComboList?col1=user_role_id&col2=appUserAccountDetails.user_name&classname=UserRoleMapMasterModel&status=1&wherecol=lookupMasterModel.lookup_code='"+ccmGasCutter+"' and lookupMasterModel.lookup_id=lookup_id  and record_status=";
 		//tundish car load combo
 		var url6="./CommonPool/getComboList?col1=lookup_id&col2=lookup_value&classname=LookupMasterModel&status=1&wherecol=lookup_type='TUNDISH_CAR' and lookup_code='"+ $('#t23_casterUnit').combobox('getText')+"' and lookup_status=";
 		var url61="./CommonPool/getComboList?col1=lookup_id&col2=lookup_value&classname=LookupMasterModel&status=1&wherecol=lookup_type='LADLE_CAR' and lookup_status=";
	 	//getDropdownList(url3,'#t23_shiftMgr');
  	 	getDropdownList(url4,'#t23_ccmInCharge');
  	 	getDropdownList(url5,'#t23_ccmMcdOpr');
  	 	getDropdownList(url6,'#t24_TundishCar');
  	 	getDropdownList(url61,'#t24_ladleCar');
  	 	//getDropdownList(url7,'#t23_gasCutter');
		getHeatsWaitingForCaster();
		getccmsavedheats(); 	
  	},onChange:function(){
         $('#t23_ccmInCharge').combobox('setValue','');
	}
 }); 

$('#t23_tundishtemp_tbl_id').edatagrid({
onSave: function(index,row){
//alert("hai index save");
},onSuccess: function(index,row){
$.messager.alert('Result Info',row.msg,'info');
},onDestroy:function(index,row){
//deleteT2HeatLinePlan(row.heat_line_id);
},onError: function(index,row){
	$.messager.alert('Result Error',row.msg,'info');
}
});

$('#t23_recDate').datetimebox({
    value: (formatDate(new Date())) 
});

var recdate= ($('#t23_recDate').datetimebox('getValue'));
 $('#t23_shift').combobox({
	 onLoadSuccess: function(){  
    		value: autoShift(recdate,'t23_shift',$('#t23_shift').combobox('getData'));
	  }
});
document.getElementById('t23_sample_si_no').value='0';

$('#t23_tundish_temp_1').numberbox({
	onChange: function(value){
		if(value != null && value != 0){
			var liqd_temp;
			if($("#t23_liqTemp").numberbox("getValue") != null && $("#t23_liqTemp").numberbox("getValue") != ""){
				liqd_temp = $("#t23_liqTemp").numberbox("getValue");
			}else{
				liqd_temp = $("#t23_liqTemp").numberbox("getText");
			}
			var super_heat = value - parseFloat(liqd_temp);
			$('#t23_super_heat1').numberbox('setValue', super_heat);
		}else{
			$('#t23_super_heat1').numberbox('setValue', '');
		}
	}
});

document.getElementById('t24_sample_si_no').value='0';

$('#t23_tundish_temp_2').numberbox({
	onChange: function(value){
		if(value != null && value != 0){
			var liqd_temp;
			if($("#t23_liqTemp").numberbox("getValue") != null && $("#t23_liqTemp").numberbox("getValue") != ""){
				liqd_temp = $("#t23_liqTemp").numberbox("getValue");
			}else{
				liqd_temp = $("#t23_liqTemp").numberbox("getText");
			}
			var super_heat = value - parseFloat(liqd_temp);
			$('#t23_super_heat2').numberbox('setValue', super_heat);
		}else{
			$('#t23_super_heat2').numberbox('setValue', '');
		}
	}
});
$('#t23_tundish_temp_3').numberbox({
	onChange: function(value){
		if(value != null && value != 0){
			var liqd_temp;
			if($("#t23_liqTemp").numberbox("getValue") != null && $("#t23_liqTemp").numberbox("getValue") != ""){
				liqd_temp = $("#t23_liqTemp").numberbox("getValue");
			}else{
				liqd_temp = $("#t23_liqTemp").numberbox("getText");
			}
			var super_heat = value - parseFloat(liqd_temp);
			$('#t23_super_heat3').numberbox('setValue', super_heat);
		}else{
			$('#t23_super_heat3').numberbox('setValue', '');
		}
	}
});

	//-------------Level 2 server accessing-------------- 
function ccm_level2_server(){
		var samp_no=$('#t23_sample_no').combobox('getText');
		var connection_check=0;// 0-Connected 1-Not Connected
		var heat_id = $('#t23_chem_heat_no').combobox('getText');
		var spectro_chem=	parseInt($('#rawdata_spectrochem').val());
		if(heat_id!='' && heat_id!=null && samp_no!='' && samp_no!=null){
		$.ajax({
			headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json'
			},
			type : 'GET',
			// data: JSON.stringify(formData),
			dataType : "json",
			url : "./heatProcessEvent/getTestSpectro",//Pinging spectro server , if active connection_check=0
			success : function(data) {
			if(data.status!="SUCCESS"){
				$('#t23_caster_Chemistry_tbl_id').datagrid('hideColumn', 'remarks');
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
		if(connection_check==0 && spectro_chem==0){
  			var analysis_id=$("#t23_analysis_type").combobox("getValue");
  			//var analysis_id=$("#t23_analysis_type1").combobox("getValue");
  			var psn_id=$('#rawdata_psnno').val();
  			var heat_counter=$('#rawdata_heatcounter').val();
  			var actual_sample='p';
  			$('#t23_caster_Chemistry_tbl_id').datagrid('showColumn', 'remarks');
			$.ajax({
				headers : {
					'Accept' : 'application/json',
					'Content-Type' : 'application/json'
				},
				type : 'GET',
				// data: JSON.stringify(formData),
				dataType : "json",
				url: "./casterProduction/getTundishChem?analysis_id="+analysis_id+"&psn_id="+psn_id+"&actual_sample="+actual_sample+"&heat_id="+heat_id+"&heat_counter="+heat_counter,
				success : function(data) {
					var spectro_flag= 0;
					var sample_flag=0;
					$('#t23_caster_Chemistry_tbl_id').datagrid('loadData', data);
					for(var i=0;i<data.length;i++){
						$('#t23_sample_date_time').datetimebox('setValue', data[0].sample_date);
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
				          				setDefaultCustomComboValues('t23_sample_result', 'REJECT', $('#t23_sample_result').combobox('getData'));
				          			}
				          			else{
				          				 setDefaultCustomComboValues('t23_sample_result', 'OK', $('#t23_sample_result').combobox('getData')); 
				          				
				          			}
				          		}
			          	if(data[i].spectro_flag==1)
							spectro_flag++;
					}
				 if(spectro_flag>0){
					$.messager.alert('Warning',
							'Data Retrieved From Spectro Server', 'info');
					
				 }
				 else{
					 setDefaultCustomComboValues('t23_sample_result',
								'REJECT', $('#t23_sample_result').combobox(
										'getData'));
					$.messager.alert('Warning',
							'No Data Retrieved From Spectro Server', 'info');
				 }
				},
				error : function() {
					$.messager.alert('Spectro Server Processing',
							'Error Connecting Spectro Server...', 'error');
				}
			}); 
  		}
		}
		else{
			$.messager.alert('Spectro Server Processing',
					'Heat no or Sample no is not generated...', 'error');
		}
	}
	
	
	
	///////level 3 server/////
	function ccm_level3_server(){
		var samp_no=$('#t24_sample_no').combobox('getText');
		var connection_check=0;// 0-Connected 1-Not Connected
		var heat_id = $('#t24_chem_heat_no').combobox('getText');
		var spectro_chem=	parseInt($('#rawdata_spectrochem').val());
		if(heat_id!='' && heat_id!=null && samp_no!='' && samp_no!=null){
		$.ajax({
			headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json'
			},
			type : 'GET',
			// data: JSON.stringify(formData),
			dataType : "json",
			url : "./heatProcessEvent/getTestSpectro",//Pinging spectro server , if active connection_check=0
			success : function(data) {
			if(data.status!="SUCCESS"){
				$('#t24_caster_Chemistry_tbl_id').datagrid('hideColumn', 'remarks');
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
		if(connection_check==0 && spectro_chem==0){
  			var analysis_id=$("#t24_analysis_type").combobox("getValue");
  			var psn_id=$('#rawdata_psnno').val();
  			var heat_counter=$('#rawdata_heatcounter').val();
  			var actual_sample='p';
  			$('#t24_caster_Chemistry_tbl_id').datagrid('showColumn', 'remarks');
			$.ajax({
				headers : {
					'Accept' : 'application/json',
					'Content-Type' : 'application/json'
				},
				type : 'GET',
				// data: JSON.stringify(formData),
				dataType : "json",
				url: "./casterProduction/getTundishChem?analysis_id="+analysis_id+"&psn_id="+psn_id+"&actual_sample="+actual_sample+"&heat_id="+heat_id+"&heat_counter="+heat_counter,
				success : function(data) {
					var spectro_flag= 0;
					var sample_flag=0;
					$('#t24_caster_Chemistry_tbl_id').datagrid('loadData', data);
					for(var i=0;i<data.length;i++){
						$('#t24_sample_date_time').datetimebox('setValue', data[0].sample_date);
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
				          				setDefaultCustomComboValues('t24_sample_result', 'REJECT', $('#t24_sample_result').combobox('getData'));
				          			}
				          			else{
				          				 setDefaultCustomComboValues('t24_sample_result', 'OK', $('#t24_sample_result').combobox('getData')); 
				          				
				          			}
				          		}
			          	if(data[i].spectro_flag==1)
							spectro_flag++;
					}
				 if(spectro_flag>0){
					$.messager.alert('Warning',
							'Data Retrieved From Spectro Server', 'info');
					
				 }
				 else{
					 setDefaultCustomComboValues('t24_sample_result',
								'REJECT', $('#t24_sample_result').combobox(
										'getData'));
					$.messager.alert('Warning',
							'No Data Retrieved From Spectro Server', 'info');
				 }
				},
				error : function() {
					$.messager.alert('Spectro Server Processing',
							'Error Connecting Spectro Server...', 'error');
				}
			}); 
  		}
		}
		else{
			$.messager.alert('Spectro Server Processing',
					'Heat no or Sample no is not generated...', 'error');
		}
	}
	
</script>
       <style type="text/css">
        #t23_caster_form_id{
            margin:0;
            padding:10px 30px;
        }
</style>
