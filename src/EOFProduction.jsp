<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page import = "java.util.logging.Logger" %>
<script  src="${pageContext.request.contextPath}/js/EOFOperationDtls.js"></script>
<script  src="${pageContext.request.contextPath}/js/common.js"></script>
<script  src="${pageContext.request.contextPath}/js/PSNReportDisplay.js"></script>


<div class="easyui-accordion" id="eofAccDivId" data-options="multiple:true" style="width:100%;height: auto">
<!-- Crew Details -->
<div title="Crew Details" data-options="iconCls:'icon-ok'"  style="padding-top: 4px;padding-left: 10px;padding-right: 10px;">
    
    <form id="t4_crew_form_id" method="post" novalidate> 
    
    <table style="width: 100%" class="easyui-panel" border="1">
    <tr>
    <td width="55%">
    
    <table style="width: 100%;text-align: right;padding-right: 20px">
       
        <!-- first row -->
        <tr height="20">
        <td> 
        <label><spring:message code="label.t4.eofProductionRecDate"/></label>
       </td><td>
         <input name="recDate"  type="text" id="t4_recDate"  class="easyui-datetimebox" data-options="required:true,showSeconds:false">
        </td>
        <td> 
        <label><spring:message code="label.t4.eofProductionShift"/></label>
       </td><td>
         <input name="shift"  type="text" id="t4_shift"  class="easyui-combobox" data-options="required:true,panelHeight: 'auto',editable:false,valueField:'keyval',textField:'txtvalue'">
        </td>
        <td> 
        <label><spring:message code="label.t4.eofProductionUnit"/></label>
       </td><td>
         <input name="unit"  type="text" id="t4_unit"  class="easyui-combobox" data-options="required:true,panelHeight: 'auto',editable:false,valueField:'keyval',textField:'txtvalue'">
        </td>
        </tr>
        
        <tr height="20">
        <td> 
        <label><spring:message code="label.t4.eofProductionShiftMgr"/></label>
       </td><td>
         <input name="shiftMgr"  type="text" id="t4_shiftMgr"  class="easyui-combobox" data-options="required:true,panelHeight: 'auto',editable:false,valueField:'keyval',textField:'txtvalue'">
        </td>
        <td> 
        <label><spring:message code="label.t4.eofProductionEOFMgr"/></label>
       </td><td>
         <input name="eofMgr"  type="text" id="t4_eofMgr"  class="easyui-combobox" data-options="required:true,panelHeight: 'auto',editable:false,valueField:'keyval',textField:'txtvalue'">
        </td>
        <!-- 
        <td> 
        <label><spring:message code="label.t4.eofProductionTapOperator"/></label>
       </td><td>
         <input name="tapOperator"  type="text" id="t4_tapOperator"  class="easyui-combobox" data-options="required:true,panelHeight: 'auto',editable:false,valueField:'keyval',textField:'txtvalue'">
        </td>
         -->
        </tr>
        </table>
        </td>
        
        <td width="45%" >
         <table style="width: 100%;text-align:right ;border: 1px;">
       
        <!-- first row -->
       <tr>
        <td> 
        <label>Campaign No</label>
        </td><td>
         <input name="eofCamp" hidden="true" type="text" id="eofCamp"  class="easyui-textbox" style="width: 80px;text-align: right;" data-options="editable:false">
        </td>      
         <td> 
        <!-- <label>Container Life</label> --><label>Wall Life</label>
        </td><td>
         <input name="container_life" hidden="true" type="text" id="container_life"  class="easyui-textbox" style="width: 80px;text-align: right;" data-options="editable:false">
        </td> 
        </tr>
        <tr>
         <td> 
        <label>Bottom Life</label>
       </td>
        <td>
         <input name="furnaceLife" type="text" id="furnaceLife"  class="easyui-textbox" style="width: 80px;text-align: right;" data-options="editable:false">
        </td>
        <td> 
        <label>EBT Life</label>
       </td>
        <td>
         <input name="tapHoleLife" type="text" id="tapHoleLife"  class="easyui-textbox" style="width: 80px;text-align: right;" data-options="editable:false">
        </td>
        </tr>
        <tr >
        <td> 
        <label>Delta Life</label>
       </td><td>
         <input name="steelLaunderLife"  type="text" id="steelLaunderLife"  class="easyui-textbox" style="width: 80px;text-align: right;" data-options="editable:false">
        </td>
        <td> 
        <label>HM Launder Life</label>
       </td>
        <td>
         <input name="hmLaunderLife" type="text" id="hmLaunderLife"  class="easyui-textbox" style="width: 80px;text-align: right;" data-options="editable:false">
        </td>
        </tr>

        </table>
        </td>
        
        </tr>
        </table>
        
        </form>
     </div>
     
     <div  title="<spring:message code="label.t4.eofProductionHotMetalProd"/>" data-options="iconCls:'icon-ok'" style="padding-top: 10px;padding-left: 10px;padding-right: 10px;">
    
     <div id="T4ladleDetailsId">
     <table id="t4_EOF_production_tbl_id" toolbar="#t4_EOF_production_tbl_toolbar_div_id"  class="easyui-datagrid" style="width:100%;height: 200px;"
            url="./HMReceive/getHMDetailsbyStatus?STAGE=AVAILABLE&LADLE_STATUS=SEEOF,PARTC" method="get" iconCls='icon-ok'
             pagination="true" maximizable="true"  resizable="true" remoteSort="false" pageSize="10"
            rownumbers="true"  singleSelect="true"> 
        <thead data-options="frozen:true">
            <tr>
            	<th field="version" sortable="false" hidden="true" width="40"><spring:message code="label.t4.hmRecvId"/></th>
                <th field="hmRecvId" sortable="false" hidden="true" width="40"><spring:message code="label.t4.hmRecvId"/></th>
                <th field="castNo" sortable="false" width="70"><spring:message code="label.t4.castNo"/></th>
                <th field="hmLadleNo" sortable="false" width="70"><spring:message code="label.t4.hmLadleNo"/></th>
                <th field="hmLadleProdDt" sortable="true" formatter="(function(v,r,i){return formatDateTime('hmLadleProdDt',v,r,i)})"  width="150"><spring:message code="label.t4.prodDate"/></th> 
                <th field="lookupHmSource.lookup_id" sortable="true" width="70" formatter="(function(v,r,i){return formatColumnData('lookupHmSource.lookup_value',v,r,i);})"><spring:message code="label.t4.hmSource"/></th>
         </tr>
         </thead>
         <thead>
           <tr>
           <th field="hmLadleTempEof" sortable="false" align="right" align="right" width="80"><spring:message code="label.t4.hmLadleTempAtBf"/></th>
           <th field="hmLadleGrossWt" hidden="true" align="right" sortable="false" width="80"><spring:message code="label.t4.hmGrossWt"/></th>
           <th field="hmLadleTareWt" hidden="true" align="right" sortable="false" width="80"><spring:message code="label.t4.hmTareWt"/></th>
           
           <th field="hmLadleNetWt" hidden="true" align="right" sortable="false" width="80"><spring:message code="label.t4.hmNetWt"/></th>
           <th field="hmAvlblWt" sortable="false" align="right" width="80"><spring:message code="label.t4.hmAvlblWt"/></th>
           <th field="hmSmsMeasuredWt" sortable="false" align="right" width="80"><spring:message code="label.t4.hmSmsWt"/></th>
            <th field="hmLadleC" sortable="false" align="right" width="50"><spring:message code="label.t4.c"/></th>
           <th field="hmLadleMn" sortable="false" align="right" width="50"><spring:message code="label.t4.mn"/></th>
           <th field="hmLadleS" sortable="false" align="right" width="50"><spring:message code="label.t4.s"/></th>
           
           <th field="hmLadleP" sortable="false" align="right" width="50"><spring:message code="label.t4.p"/></th>
           <th field="hmLadleSi" sortable="false" align="right" width="50"><spring:message code="label.t4.si"/></th>
           <th field="hmLadleTi" sortable="false" align="right" width="50"><spring:message code="label.t4.ti"/></th>
           <th field="remarks" sortable="false"  width="250"><spring:message code="label.t4.remarks"/></th>
           </tr>
        </thead>
    </table>
    </div>
    
    <br>
    
    <form id="t4_heat_hdr_form_id" method="post" novalidate>
    <!-- Hidden fields -->
     <input name="heat_counter"  type="hidden" id="t4_heat_cnt">
     <input name="trns_si_no"  type="hidden" id="t4_trns_si_no">
     <input name="sub_unit_id" type="hidden"  id="t4_sub_unit_id"/>
    <table style="width: 100%" class="easyui-panel" title="Heat Details" data-options="
                tools:[{
                    iconCls:'icon-reload',
                    handler:function(){
                     GetT4UpdatedHeatList();
 		    	clearT4HeatHdrform();
                    }
                }]
            ">
       
       <!-- Hidden fields -->
       <input name="heat_receieve_id" type="hidden"  id="t4_heat_receieve_id" value="0">
       <input name="aim_psn_id" type="hidden"  id="t4_aim_psn_id">
       <input name="heat_line_id" type="hidden"  id="t4_heat_line_id">
       <input name="plan_heat_qty" type="hidden"  id="t4_plan_heat_qty">
       <input name="hmRecvId" type="hidden"  id="t4_hmRecvId">
       <input name="hm_hmAvlblWt"  type="hidden" id="t4_hm_hmAvlblWt">
       <input name="version" type="hidden" id="t4_version">
       <input name="hm_ldl_version" type="hidden" id="t4_hm_ldl_version">
       <input name="plan_line_version" type="hidden" id="t4_plan_line_version">
       <input name="t4_hmSmsMeasuredWt"  type="hidden" id="t4_hmSmsMeasuredWt">
       <input name="t4_container_trns_id"  type="hidden" id="t4_container_trns_id">
        <input name="t4_container_life"  type="hidden" id="t4_container_life">
       <input name="t4_psn_route"  type="hidden" id="t4_psn_route">
        <!-- first row -->
        <tr style="height: 30px;">
        <td align="right"> 
        <label style="padding-right: 20px">Heat No</label>
       </td>
       <td>
         <input name="heat_id"  type="text" id="t4_heat_id"  class="easyui-combobox" data-options="editable:false,required:true,valueField:'keyval',                    
                    textField:'txtvalue'">
         
        </td>
        <td align="right"> 
        <label style="padding-right: 20px">Aim PSN</label>
       </td>
       <td>
         <input name="aim_psn"  type="text" id="t4_aim_psn"  class="easyui-textbox" data-options="required:true,validType:{length:[1,60]},readonly:true,editable:false">
         
        </td>
        <td align="right"> 
        <label style="padding-right: 20px">Plan No</label>
       </td>
       <td>
         <input name="heat_plan_id"  type="text" id="t4_heat_plan_id"  class="easyui-numberbox" data-options="required:true,validType:{length:[1,60]},readonly:true,editable:false">
        </td>
        
        <td align="right"> 
        <label style="padding-right: 20px">Line No</label>
       </td>
       <td>
         <input name="heat_plan_line_no"  type="text" id="t4_heat_plan_line_no"  class="easyui-numberbox" data-options="required:true,validType:{length:[1,60]},readonly:true,editable:false">
        </td>
        
        </tr>
        
        <tr style="height: 30px;">
        <td align="right"> 
        <label style="padding-right: 20px">Scrap Charge At</label>
       </td>
       <td>
         <input name="scrap_charge_at"  type="text" id="t4_scrap_charge_at"  class="easyui-datetimebox" data-options="required:false,showSeconds:false,editable:false">
        </td>
        
        <td align="right"> 
        <label style="padding-right: 20px">HM Ladle No</label>
       </td>
       <td>
       <input name="hm_ladle_no"  type="text" id="t4_hm_ladle_no"  class="easyui-textbox" data-options="required:true,validType:{length:[1,60]},editable:false">
        </td>
        
         <td align="right"> 
        <label style="padding-right: 20px">HM Weight</label>
       </td>
       <td>
       <input name="hm_wt"  type="text" id="t4_hm_wt"  class="easyui-numberbox" data-options="precision:2,validType:{length:[1,60]},editable:false">
       
       
        </td>
        
         <td align="right"> 
        <label style="padding-right: 20px">HM Temp</label>
       </td>
       <td>
       <input name="hm_temp"  type="text" id="t4_hm_temp"  class="easyui-numberbox" data-options="required:true,validType:{length:[1,60]},editable:false">
        </td>
        
        </tr>
        
        
       <tr style="height: 30px;">
        <td align="right"> 
        <label style="padding-right: 20px">HM Charge at</label>
       </td>
       <td>
         <input name="hm_charge_at"  type="text" id="t4_hm_charge_at"  class="easyui-datetimebox" data-options="required:false,showSeconds:false,onChange:function(){scrapChargeAtValidation();}">
        </td>
        
        <td align="right"> 
        <label style="padding-right: 20px">Tap Start AT</label>
       </td>
       <td>
       <input name="tap_start_at"  type="text" id="t4_tap_start_at"  class="easyui-datetimebox" data-options="required:false,showSeconds:false,onChange:function(){tapStartAtValidation();}">
        </td>
        
         <td align="right"> 
        <label style="padding-right: 20px">Tap Close AT</label>
       </td>
       <td>
       <input name="tap_close_at"  type="text" id="t4_tap_close_at"  class="easyui-datetimebox" data-options="required:false,showSeconds:false,onChange:function(){tapCloseAtValidation();}">
        </td>
        
        </tr>
        
        <tr>
        <td colspan="8">
        <div  id="t4_heat_hdr_btn_div_id">
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
    
    <div id="t4_EOF_production_tbl_toolbar_div_id">
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
   <td align="center" width="20%">
      <div id="dvImage" style="height: 30px; width: 30px" onclick="javascript:void(0)"  class="easyui-tooltip"> <!-- data-options="
                    hideEvent: 'none',
                    content: function(){
                        return gridT4LadleValidation();
                    },
                    onShow: function(){
                        var t = $(this);
                        t.tooltip('tip').focus().unbind().bind('blur',function(){
                            t.tooltip('hide');
                        });
                    }
                " -->
                </div>
                </td>
                </tr>
                </table>
    </div>
    
    <!-- Heat Plan Display -->
    
    <div title="Heat Plan Details" closed="true" class="easyui-dialog" id="t4_EOF_HeatPlanDetailsView" data-options="iconCls:'icon-ok'" style="height:600px;padding:10px 10px;width: 95%;">
     
     
     <table id="t4_eof_heatplan_tbl_id" toolbar="#t4_eof_heatplan_tbl_toolbar_div_id"  title="<spring:message code="label.t2.heatPlanHeader"/>" class="easyui-datagrid" style="width:100%;height: 500px;"
            iconCls='icon-ok'
             pagination="false" maximizable="true"  resizable="true" fitColumns="true" remoteSort="false" pageSize="20"
            rownumbers="true"  singleSelect="true"> 
            
          <thead>
           <tr>
           <th field="heat_plan_id"  sortable="true" width="100">Heat Plan No</th>
          
          <th field="prod_start_date" sortable="false" formatter="(function(v,r,i){return formatDateTime('prod_start_date',v,r,i);})" width="130">Prod Start Date</th>
          
          <th field="aim_psn_id" sortable="false" width="120" formatter="(function(v,r,i){return formatColumnData('psnHdrModel.psn_no',v,r,i);})">Aim PSN</th>
          
          <th field="grade" sortable="true" width="100" >Grade</th>
          
          <th field="line_no" sortable="true" width="50" hidden="true" >Heat Line No</th>
          
          <th field="section_type" sortable="false" width="100" formatter="(function(v,r,i){return formatColumnData('lookupSectionType.lookup_value',v,r,i);})">Section Type</th>
          
          <th field="section_planned" sortable="false" width="120" formatter="(function(v,r,i){return formatColumnData('smsCapabilityMstrModel.lookupOutputSection.lookup_value',v,r,i);})">Section</th>
          
           <th field="cut_length" align="right" sortable="false" width="70">Cut Length</th>
          
          <th field="plan_qty" align="right" sortable="false" width="70">Qty</th>
          
          <th field="caster_type" sortable="false" width="80" formatter="(function(v,r,i){return formatColumnData('lookupCasterType.lookup_value',v,r,i);})">Caster Type</th>
                    
          <!--  <th field="plan_remarks" sortable="false" width="100">Remarks</th> -->
          
          <th field="line_status" sortable="false" width="100">Line Status</th>
       
         <th field="line_version" hidden="true"   width="100">Version</th>  
       
           </tr>
           
        </thead>
            
    </table>
     
     
     </div>
     
     <div id="t4_eof_heatplan_tbl_toolbar_div_id">
     <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="getT4AttachedHeatProcess()" style="width:150px">Process Heat</a>
     </div>
    
    
    <!-- Heat Plan End -->
    
    <!-- HM Mix details toolbar="#t4_HMMix_production_tbl_toolbar_div_id"-->
    <div id="t4_mixbutton_tbl_toolbar_div_id">
     <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="getT4MixSelectedLadles()" style="width:150px">Mix Selected Ladles</a>
     <label style="padding-left: 400px">EAF UNIT</label>
     <input name="unit"  type="text" id="t5_unit"  class="easyui-textbox" data-options="editable:false">
    </div>
    <div title="HotMetal Mix Window"  closed="true" class="easyui-dialog" id="t4_HM_MixDetailsView" data-options="iconCls:'icon-ok'" style="height:600px;padding:10px 10px;width: 95%;">
     
     
     <table id="t4_HMMix_tbl_id" toolbar="#t4_mixbutton_tbl_toolbar_div_id"  class="easyui-datagrid" style="width:100%;height: 230px;"
             method="get" iconCls='icon-ok'
             pagination="true" maximizable="true"  resizable="true" remoteSort="false" pageSize="10"
            rownumbers="true"  singleSelect="false" checkOnSelect="false"> 
        <thead data-options="frozen:true">
            <tr>
                <th field="hmRecvId" sortable="false"  checkbox="true" width="40"><spring:message code="label.t4.hmRecvId"/></th>
                <th field="castNo" sortable="false" width="70"><spring:message code="label.t4.castNo"/></th>
                <th field="hmLadleNo" sortable="false" width="70"><spring:message code="label.t4.hmLadleNo"/></th>
                <th field="hmLadleProdDt" sortable="true" formatter="(function(v,r,i){return formatDateTime('hmLadleProdDt',v,r,i)})"  width="150"><spring:message code="label.t4.prodDate"/></th> 
                <th field="lookupHmSource.lookup_id" sortable="true" width="70" formatter="(function(v,r,i){return formatColumnData('lookupHmSource.lookup_value',v,r,i);})"><spring:message code="label.t4.hmSource"/></th>
         </tr>
         </thead>
         <thead>
           <tr>
           <th field="hmLadleTempEof"  align="right" sortable="false" width="80"><spring:message code="label.t4.hmLadleTempAtBf"/></th>
           <th field="hmLadleGrossWt" align="right" sortable="false" width="80"><spring:message code="label.t4.hmGrossWt"/></th>
           <th field="hmLadleTareWt" align="right" sortable="false" width="80"><spring:message code="label.t4.hmTareWt"/></th>
           
           <%-- <th field="hmLadleNetWt" align="right" sortable="false" width="80"><spring:message code="label.t4.hmNetWt"/></th> --%>
           <th field="hmAvlblWt" align="right" sortable="false" width="80"><spring:message code="label.t4.hmAvlblWt"/></th>
           <!-- <th field="isDoubleladle" align="right" data-options=" 
                   editor:{
                   type:'combobox',
                   options:{
							url:'./CommonPool/getComboList?col1=lookup_id&col2=lookup_value&classname=LookupMasterModel&status=1&wherecol=lookup_type=\'DISPATCH_STATUS\' and lookup_status=',
							method:'get',
						    valueField:'txtvalue',
			                textField:'txtvalue'
						}}" 
					sortable="false" width="80" >Double laddle</th> -->
                               
            <th field="mixqty" align="right" data-options="editor:{type:'numberbox',options:{precision:2}}" sortable="false" width="80">Mix Qty</th>
            <th field="hmLadleC" align="right" sortable="false" width="50"><spring:message code="label.t4.c"/></th>
            <th field="hmLadleSi"  align="right" sortable="false" width="50"><spring:message code="label.t4.si"/></th>
           <th field="hmLadleMn" align="right" sortable="false" width="50"><spring:message code="label.t4.mn"/></th>
            <th field="hmLadleP" align="right" sortable="false" width="50"><spring:message code="label.t4.p"/></th>
           <th field="hmLadleS" align="right" sortable="false" width="50"><spring:message code="label.t4.s"/></th>
           <th field="hmLadleTi" align="right" sortable="false" width="50"><spring:message code="label.t4.ti"/></th>
           <th field="remarks" sortable="false" width="150"><spring:message code="label.t4.remarks"/></th>
           </tr>
        </thead>
    </table>
    
    <br>
 <div id="t4n_HMMix_form_div_id" align="center" class="easyui-panel" style="width: 60%" title="HotMetal Mix Details">
    
      <table  style="width: 100%;">
       <span>
       <input name="t4n_allids"  type="hidden" id="t4n_allids">
       
        <input name="hmLadleStatus" type="hidden" class="textboxhidden"    id="t4n_hmLadleStatus">
        <input name="dataSource" type="hidden" class="textboxhidden"    id="t4n_dataSource">
        <input name="hmLadleStageStatus" type="hidden" class="textboxhidden"   id="t4n_hmLadleStageStatus">
         <input name="version" type="hidden" class="textboxhidden"   id="t4n_version">
       </span>
        <!-- first row -->
        <tr style="height: 30px;">
        <td> 
        <label><spring:message code="label.t1.castNo"/></label>
        </td>
        <td>
                <input name="castNo"  type="text" id="t4n_castNo"  class="easyui-textbox" data-options="required:true,validType:{length:[1,20]}">
        </td>
        
        <td> 
        <label><spring:message code="label.t1.hmLadleNo"/></label>
        </td>
        <td>
            
                <input name="hmLadleNo"  type="text" id="t4n_hmLadleNo"  class="easyui-combobox" data-options="required:true,editable:false,valueField:'keyval',textField:'txtvalue',validType:{length:[1,4]}">
            
        </td>
        
        <td> 
        <label><spring:message code="label.t1.hmReceiveDt"/></label>
        </td>
        
         <td>
                 <input name="hmRecvDate"  type="text" id="t4n_hmRecvDate"  class="easyui-datetimebox" data-options="required:true,showSeconds:false">
   
        </td>
        
        </tr>
       
         <!-- second row -->
         <tr style="height: 30px;">
        <td> 
        <label><spring:message code="label.t1.hmTareWt"/></label>
        </td>
        <td>
                <input name="hmLadleTareWt"  type="text" id="t4n_hmLadleTareWt"  class="easyui-numberbox"  data-options="precision:2">
        </td>
        
        <td> 
        <label><spring:message code="label.t1.hmGrossWt"/></label>
        </td>
        <td>
            
                <input name="hmLadleGrossWt"  type="text" id="t4n_hmLadleGrossWt"  class="easyui-numberbox" data-options="required:true,precision:2" >
            
        </td>
        
        <td> 
        <label><spring:message code="label.t1.hmNetWt"/></label>
        </td>
        
         <td>
                 <input name="hmLadleNetWt"  type="text" id="t4n_hmLadleNetWt"  class="easyui-numberbox" data-options="precision:2" >
   
        </td>
        
        </tr>
        
         
         <!-- 3rd row -->
       
       
       <tr style="height: 30px;">
        <td> 
        <label><spring:message code="label.t1.prodDate"/></label>
        </td>
        <td>
                <input name="hmLadleProdDt"  type="text" id="t4n_hmLadleProdDt"  class="easyui-datetimebox" data-options="required:true,showSeconds:false">
        </td>
        
        <td> 
        <label><spring:message code="label.t1.tempAtBf"/></label>
        </td>
        <td>
            
                <input name="hmLadleTempEof"  type="text" id="t4n_hmLadleTempBf"  class="easyui-numberbox" data-options="required:true,validType:{length:[1,4]}">
            
        </td>
        
        <td> 
        <label><spring:message code="label.t1.tempAtEof"/></label>
        </td>
        
         <td>
                 <input name="hmLadleTempEof"  type="text" id="t4n_hmLadleTempEof"  class="easyui-numberbox" data-options="required:true,validType:{length:[1,4]}">
   
        </td>
        
        </tr>
     
          <!-- 5th row -->
       <tr style="height: 30px;">
        <td> 
        <label><spring:message code="label.t1.c"/></label>
        </td>
        <td>
                <input name="hmLadleC"  type="text" id="t4n_hmLadleC"  class="easyui-numberbox" data-options="required:true,precision:3">
        </td>
        
        <td> 
        <label><spring:message code="label.t1.mn"/></label>
        </td>
        <td>
            
                <input name="hmLadleMn"  type="text" id="t4n_hmLadleMn"  class="easyui-numberbox" data-options="required:true,precision:3">
            
        </td>
        
        <td> 
        <label><spring:message code="label.t1.s"/></label>
        </td>
        
         <td>
                 <input name="hmLadleS"  type="text" id="t4n_hmLadleS" class="easyui-numberbox" data-options="required:true,precision:3">
   
        </td>
        
        </tr>
       
       
           <!-- 6th row -->
       <tr style="height: 30px;">
        <td> 
        <label><spring:message code="label.t1.p"/></label>
        </td>
        <td>
                <input name="hmLadleP"  type="text" id="t4n_hmLadleP"  class="easyui-numberbox" data-options="required:true,precision:3">
        </td>
        
        <td> 
        <label><spring:message code="label.t1.si"/></label>
        </td>
        <td>
            
                <input name="hmLadleSi"  type="text" id="t4n_hmLadleSi"  class="easyui-numberbox" data-options="required:true,precision:3">
            
        </td>
        
        <td> 
        <label><spring:message code="label.t1.ti"/></label>
        </td>
        
         <td>
                 <input name="hmLadleTi"  type="text" id="t4n_hmLadleTi"  class="easyui-numberbox" data-options="required:false,precision:3">
   
        </td>
        
        </tr>
        
        <!-- 7th row -->
        <tr style="height: 50px;">
          <td> 
        <label><spring:message code="label.t1.remarks"/></label>
        </td>
       <td colspan="5">
         <input name="remarks"  type="text" id="t4n_remarks"  class="easyui-textbox" data-options="multiline:true" style="height:50px;width: 97%">
        </td>
       </tr>
       <tr>
        <td colspan="6" align="center">
           <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="saveT4HMMixDtls()" style="width:90px">Save</a>
         <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="dialogBoxClose('t4_HM_MixDetailsView')" style="width:90px">Close</a>
 
        </td>
        </tr>
        </table>
        </div>
       
     </div>

<!-- HM Mix End -->    

 <!-- Furnace Addition Screen Start -->   
    
      <div id="t5_FurnaceAdditions_form_div_id" class="easyui-dialog" style="height:550px;padding:10px 10px"
            closed="true" data-change="0">
            
     <div id="t5_FurnaceAdditions_tabs_div_id" class="easyui-tabs"  pill="true" style="width: 100%;height: auto;">
     
   
       
     <div title="<spring:message code="label.t5.eofFurnaceScrapDtls"/>" style="padding:10px">
      <input name="t5_scrapBkt_hId"  type="hidden" id="t5_scrapBkt_hId">
     <table style="width: 100%" class="easyui-panel" >
       
        <!-- first row -->
        <tr>
        <td>
        <label><spring:message code="label.t5.eofFurnaceHeatNo"/></label>
        </td>
        
         <td>
         <input name="heat_no"  type="text" id="t5_heat_no2"  class="easyui-textbox"  style="width:100px;" data-options="editable:false">
        </td>
        
       <td> 
        <label><spring:message code="label.t5.eofFurnaceAimPsn"/></label></td>
        
         <td>
         <input name="aim_psn"  type="text" id="t5_aim_psn2"  class="easyui-textbox" data-options="editable:false">
         
        </td>
        <td> 
        <label><spring:message code="label.t5.scrapBucket"/></label></td>
        
         <td>
       <select class="easyui-combobox" id="t5_combo_scrapBktId" name="t5_combo_scrapBktId"  style="width:100px;"
        data-options="required:true,panelHeight: 'auto',editable:false,valueField:'keyval',textField:'txtvalue' ">
    </select>
       
        </td>
        
         <td> 
        <label><spring:message code="label.t5.consDate"/></label></td>
        
         <td>
         <input name="t5_cons_date2" id="t5_cons_date2" type="text" formatter="(function(v,r,i){return formatDateTime('t5_cons_date2',v,r,i)})"  class="easyui-datetimebox"   data-options="required:true,showSeconds:false" >
         
        </td>
        
        </tr> 
       
       <tr>
       <td colspan="10">
       <table width="100%">
        <tr>
        <td valign='top'>
         <table id="t5_eof_furnace_entry_tab2_tbl_id3"   title="<spring:message code="label.t5.eofFurnanceScrapCons"/>" class="easyui-datagrid" style="width:100%;height:auto;"
           iconCls='icon-ok' maximizable="true"  resizable="true" remoteSort="false"  rownumbers="true" singleSelect="true" showFooter="true"> 
        <thead>
            <tr>
              <th field="scrap_bkt_no"  sortable="false" width="180"><spring:message code="label.t5.bktno"/></th>             
              <th field="qty" align="right"  width="100" formatter="(function(v,r,i){return roundOffVal('qty',v,r,i)})"><spring:message code="label.t5.bktqty"/></th>
              <th field="consumption_date"  sortable="false" formatter="(function(v,r,i){return formatDateTime('consumption_date',v,r,i)})" width="150"><spring:message code="label.t5.condate"/></th>
              <th field="scrap_bkt_header_id" sortable="true" width="40">HDR ID</th><!--  hidden="true" -->
         </tr>
         </thead>
    </table>
        </td>
        
        <td width="50%">
         <table id="t5_eof_furnace_entry_tbl_id2" toolbar="#t5_eof_furnace_entry_form_btn_div_id2" title="<spring:message code="label.t5.eofFurnaceScrapDtls"/>" class="easyui-datagrid" style="width:100%;height:auto;"
           iconCls='icon-ok' maximizable="false"  resizable="true" remoteSort="false"  rownumbers="true" singleSelect="true" showFooter="true"> 
        <thead>
            <tr>
              <th field="material_id" formatter="(function(v,r,i){return formatColumnData('mtrlName',v,r,i);})" sortable="false" width="180"><spring:message code="label.t5.materialName"/></th>
              <th field="material_qty" align="right" data-options="editor:{type:'numberbox',options:{precision:2,min:0}}"  width="100"><spring:message code="label.t5.Qty"/></th>
              <th field="uom" formatter="(function(v,r,i){return formatColumnData('uom',v,r,i);})" sortable="false" width="100"><spring:message code="label.t5.uom"/></th>
              <th field="cons_si_no" sortable="true" hidden="true" width="0"><spring:message code="label.t5.pkId"/></th>
         </tr>
         </thead>
    </table>
        </td>
        </tr>
        </table>
    </td>
    </tr>
    
   
    
    </table>
     
     <div  id="t5_eof_furnace_entry_form_btn_div_id2">
         <a href="javascript:void(0)" id="t5_save_scrap_btn_id" class="easyui-linkbutton" iconCls="icon-save" onclick="saveT5ScrapDtls()" style="width:90px">Save</a>
         <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload" onclick="cancelT5ScrapDtls()" style="width:90px">Refresh</a>
         <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="dialogBoxClose('t5_FurnaceAdditions_form_div_id')" style="width:90px">Close</a>
  
    </div>
     
     </div>
      <!--      second tab div close -->
     
        <div title="<spring:message code="label.t5.eofFurnaceDtls"/>" style="padding:10px">
     <table style="width: 100%" class="easyui-panel" >
       
        <!-- first row -->
        <tr>
        <td>
        <label><spring:message code="label.t5.eofFurnaceHeatNo"/></label>
        
         <input name="heat_no"  type="text" id="t5_heat_no1"  class="easyui-textbox" data-options="editable:false">
        </td>
        
       <td> 
        <label><spring:message code="label.t5.eofFurnaceAimPsn"/></label>
         <input name="aim_psn"  type="text" id="t5_aim_psn1"  class="easyui-textbox" data-options="editable:false">
         
        </td>
         <%--  <td> 
        <label style="padding-right: 20px"><spring:message code="label.t5.consDate"/></label>
         <input name="t5_cons_date1" id="t5_cons_date1" type="text" formatter="(function(v,r,i){return formatDateTime('t5_cons_date1',v,r,i)})"  class="easyui-datetimebox"   data-options="required:true,showSeconds:false" >
         
        </td> --%>
        </tr>
      </table>
     
            
     <table id="t5_eof_furnace_entry_tbl_id1" toolbar="#t5_eof_furnace_entry_form_btn_div_id1"  title="<spring:message code="label.t5.eofFurnaceDtls"/>" class="easyui-datagrid" style="width:100%;height:auto;"
           iconCls='icon-ok' maximizable="false"  fitColumns="true" resizable="true" remoteSort="false"  rownumbers="true" singleSelect="true" showFooter="true"> 
        <thead>
            <tr>
              <th field="material_id" formatter="(function(v,r,i){return formatColumnData('mtrlName',v,r,i);})" sortable="false" width="200"><spring:message code="label.t5.materialName"/></th>
              <th field="sap_matl_id" align="right" width="150">SAP Matl ID</th>
              <th field="valuation_type" align="center" width="150">Valuation Type</th>
              <th field="qty" align="right" editor="{type:'numberbox',options:{precision:0,editable:true,min:0}}"  width="100"><spring:message code="label.t5.Qty"/></th>
              <th field="val_aim" align="right" width="100">Aim Value</th>
              <th field="val_min" align="right" width="100">Min Value</th>
              <th field="val_max" align="right" width="100">Max Value</th>
              
              <th field="uom" formatter="(function(v,r,i){return formatColumnData('uom',v,r,i);})" sortable="false" width="150"><spring:message code="label.t5.uom"/></th>
              <th field="mtr_cons_si_no" sortable="true" hidden="true" width="0"><spring:message code="label.t5.pkId"/></th>
              <th field="consumption_date" sortable="true" editor="{type:'datetimebox',options:{required:false,editable:true}}" formatter="(function(v,r,i){return formatDateTime('consumption_date',v,r,i)})"  width="150"><spring:message code="label.t5.consDate"/></th>
        	  <th field="version" hidden="true" align="right" width="100">Version</th>
         </tr>
         
         </thead>
    </table>
      <div  id="t5_eof_furnace_entry_form_btn_div_id1">
         <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="saveT5FurnaceDtls()" style="width:90px">Save</a>
          <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload" onclick="cancelT5FurnaceDtls()" style="width:90px">Refresh</a>
         <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="dialogBoxClose('t5_FurnaceAdditions_form_div_id')" style="width:90px">Close</a>
 
    </div>
     
    
     </div>
     <!--      first tab div close -->
     
     <div title="<spring:message code="label.t5.eofFurnaceGasCons"/>" style="padding:10px">
     <table style="width: 100%" class="easyui-panel" >
       
        <!-- first row -->
         <tr>
        <td>
        <label><spring:message code="label.t5.eofFurnaceHeatNo"/></label>
        
         <input name="heat_no"  type="text" id="t5_heat_no3"  class="easyui-textbox" data-options="editable:false">
        </td>
        
       <td> 
        <label><spring:message code="label.t5.eofFurnaceAimPsn"/></label>
         <input name="aim_psn"  type="text" id="t5_aim_psn3"  class="easyui-textbox" data-options="editable:false">
         
        </td>
         <%--  <td> 
        <label style="padding-right: 20px"><spring:message code="label.t5.consDate"/></label>
         <input name="t5_cons_date3" id="t5_cons_date3" type="text" formatter="(function(v,r,i){return formatDateTime('t5_cons_date3',v,r,i)})"  class="easyui-datetimebox"   data-options="required:true,showSeconds:false" >
         
        </td> --%>
        </tr> </table>
     
     <table id="t5_eof_furnace_entry_tbl_id3" toolbar="#t5_eof_furnace_entry_form_btn_div_id3"  title="<spring:message code="label.t5.eofFurnaceGasCons"/>" class="easyui-datagrid" style="width:100%;height:auto;"
           iconCls='icon-ok' maximizable="false"  resizable="true" remoteSort="false" rownumbers="true" singleSelect="true"> 
        <thead>
            <tr>
              <th field="material_id" formatter="(function(v,r,i){return formatColumnData('mtrlName',v,r,i);})" sortable="false" width="200"><spring:message code="label.t5.materialName"/></th>
              <th field="val_min" align="right" width="100">Min Value</th>
              <th field="val_max" align="right" width="100">Max Value</th>
              <th field="qty" align="right" data-options="editor:{type:'numberbox',options:{precision:2,editable:true,min:0}}"  width="150"><spring:message code="label.t5.Qty"/></th>
              <th field="uom" formatter="(function(v,r,i){return formatColumnData('uom',v,r,i);})" sortable="false" width="150"><spring:message code="label.t5.uom"/></th>
              <th field="mtr_cons_si_no" sortable="true" hidden="true" width="0"><spring:message code="label.t5.pkId"/></th>
              <th field="consumption_date" sortable="true"  editor="{type:'datetimebox',options:{required:false,editable:true}}" formatter="(function(v,r,i){return formatDateTime('consumption_date',v,r,i)})"  width="150"><spring:message code="label.t5.consDate"/></th>
         	 <th field="version" hidden="true" align="right" width="100">Version</th>
         </tr>
         </thead>
    </table>
      <div  id="t5_eof_furnace_entry_form_btn_div_id3">
         <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="saveT5GasConsDtls()" style="width:90px">Save</a>
         <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload" onclick="cancelT5GasConsDtls()" style="width:90px">Refresh</a>
         <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="dialogBoxClose('t5_FurnaceAdditions_form_div_id')" style="width:90px">Close</a>
  
    </div>
     
     </div>
     
     <!--      third tab div close -->
    
    </div>
<!--       tab div close -->
  
     </div>
     <!--       dialog form  div close -->
     
  
  <!-- Furnace Addition Screen End -->  
  
  
  <!-- Ladle Addition Screen Start --> 
    
      <div id="t6_eof_Ladle_Addition_form_div_id" class="easyui-dialog" style="height:550px;padding:10px 10px"
            closed="true" data-change="0">
            
     <div id="t6_eof_Ladle_Addition_tabs_div_id" class="easyui-tabs"  pill="true" style="width: 100%;height: auto;">
     
     <div title="<spring:message code="label.t6.eofLadleAddition"/>" style="padding:10px">
     <table style="width: 100%" class="easyui-panel" >
       
        <!-- first row -->
        <tr>
        <td>
        <label><spring:message code="label.t6.eofLadleHeatNo"/></label>
        
         <input name="heat_no"  type="text" id="t6_heat_no"  class="easyui-textbox" data-options="editable:false">
        </td>
        
       <td> 
        <label><spring:message code="label.t5.eofFurnaceAimPsn"/></label>
         <input name="aim_psn"  type="text" id="t6_aim_psn"  class="easyui-textbox" data-options="editable:false">
         
        </td>
         
        </tr>
      </table>
     <table id="t6_eof_Ladle_Addition_tbl_id" toolbar="#t6_eof_Ladle_Addition_form_btn_div_id"  title="<spring:message code="label.t6.eofLadleAddition"/>" class="easyui-datagrid" style="width:100%;height:auto;"
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
         <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="saveT6LadleDtls()" style="width:90px">Save</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload" onclick="cancelT6LadleDtls()" style="width:90px">Refresh</a>
         <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="dialogBoxClose('t6_eof_Ladle_Addition_form_div_id')" style="width:90px">Close</a>
    </div>
     
    
     </div>
     <!--      first tab div close -->
     
<%--      <div title="<spring:message code="label.t6.eofSteelLadleInfo"/>" style="padding:10px"> --%>
     
<!--      </div> -->
     
     <!--      Second tab div close -->
    
    </div>
<!--       tab div close -->
   
     </div>
     <!--       dialog form  div close -->
  
  <!-- Ladle Addition Screen End --> 
   <!-- Chemitry Details Screen Start -->
   
   <input name="t7_scrapBkt_hId"  type="hidden" id="t7_scrapBkt_hId">
   <div id="t7_eof_Chemistry_form_div_id" class="easyui-dialog" style="height:550px;padding:10px 10px;" closed="true">
             
        <form id="t7_eof_Chemistry_form_id" method="post" novalidate>
       
        <input name="t7_sample_si_no"  type="hidden" id="t7_sample_si_no">
       
       <table style="width: 100%;" >
        <tr height="30">
       <td align="right"><label style="padding-right: 20px"><spring:message code="label.t7.eofChemHeatNo"/></label></td><td>
       <input name="heat_no"  type="text" id="t7_heat_no"  class="easyui-textbox" data-options="editable:false" style="width:80px">
        </td>
        
       <td align="right"> 
        <label style="padding-right: 20px"><spring:message code="label.t7.eofChemAimPsn"/></label></td><td>
        <input name="aim_psn"  type="text" id="t7_aim_psn"  class="easyui-textbox" data-options="editable:false">
         
        </td>
         <td align="right"> 
        <label style="padding-right: 20px"><spring:message code="label.t7.eofChemAnalysis"/></label>
       </td><td>
                <input name="analysis_type"  type="text" id="t7_analysis_type"  class="easyui-combobox"  data-options="panelHeight: 'auto',editable:false,required:true,
                    valueField:'keyval',                    
                    textField:'txtvalue'">
       </td>
        <td align="right">
        <label style="padding-right: 20px"><spring:message code="label.t7.eofChemSampleno"/></label>
        </td><td>
         <input name="sample_no"  type="text" id="t7_sample_no" class="easyui-combobox" data-options="required:true,valueField:'keyval',                    
                    textField:'txtvalue'">
          <!-- <a href="javascript:void(0)" id="t7_spectro"  class="easyui-linkbutton" iconCls="icon-ok" onclick="level2_server()" style="width:30px"></a> -->
          
        </td>
    </tr> 
     <tr height="30">
         <td align="right">
        <label style="padding-right: 20px"><spring:message code="label.t7.eofChemSampleTemp"/></label>
        </td><td >
         <input name="sample_temp"  type="text" id="t7_sample_temp"  class="easyui-numberbox" data-options="editable:true,required:true" style="width:80px">
        </td>
         <td align="right"> 
        <label style="padding-right: 20px"><spring:message code="label.t7.eofChemSampleDate"/></label></td><td>
         <input name="sample_date_time"  type="text" id="t7_sample_date_time"  formatter="(function(v,r,i){return formatDateTime('sample_date_time',v,r,i)})" class="easyui-datetimebox" data-options="required:true,showSeconds:false">
        </td>
        
         <td align="right"> 
        <label style="padding-right: 20px"><spring:message code="label.t7.eofChemSampleResult"/></label>
        </td><td>
                <input name="sample_result"  type="text" id="t7_sample_result"  class="easyui-combobox"  data-options="panelHeight: 'auto',editable:true,required:true,
                    valueField:'keyval',                    
                    textField:'txtvalue'">
       </td>
         <td colspan="2">
         <a href="javascript:void(0)"  class="easyui-linkbutton" onclick="GetSample()">Generate Sample No</a>
        </td> 
        
        </tr>
    
        <tr height="30">
         <td align="right"> 
        <label style="padding-right: 20px"><spring:message code="label.t7.eofChemRemarks"/></label>
       </td><td  colspan="6">
         <input name="remarks"  type="text" id="t7_remarks"  data-options="multiline:true" style="height:30px;width: 50%" class="easyui-textbox">
       </td>
        
        </tr>
            
        </table>
     
       <table style="width: 100%;">
		<tr>
		<td width="70%">
        <div title="<spring:message code="label.t7.eofChemDetails"/>" style="padding:10px;height: 300px">
       	<table id="t7_eof_Chemistry_tbl_id" toolbar="#t7_eof_Chemistry_Addition_form_btn_div_id"  title="<spring:message code="label.t7.eofChemDetails"/>" class="easyui-datagrid" style="width:100%;height:500px;"
           iconCls='icon-ok' maximizable="false"  resizable="true" remoteSort="false"  rownumbers="true" singleSelect="true"> 

        <thead>
            <tr>
                               
              <th field="element" rowspan="2" formatter="(function(v,r,i){return formatColumnData('elementName',v,r,i);})" sortable="false" width="80">ELEMENT</th>
               <th field="uom" rowspan="2" formatter="(function(v,r,i){return formatColumnData('uom',v,r,i);})" sortable="false" width="60"><spring:message code="label.t5.uom"/></th>
               
              <th colspan="6">PSN VALUES</th>
              </tr>
              
              <tr>
              <th field="min_value" align="right" width="80">MIN</th>
              <th field="max_value" align="right" width="80">MAX</th>
               <th field="psn_aim_value" align="right" width="80">AIM</th>
               <th field="aim_value" align="right"  editor="{type:'numberbox',options:{precision:4,min:0}}"  width="80">ACTUAL<font color="red">*</font></th>
              
              <th field="dtls_si_no"  sortable="true" hidden="true" width="0"><spring:message code="label.t7.pkId"/></th>
          <th field="remarks" sortable="false" align="left" width="200" hidden="true" >SPECTRO LAB REMARKS</th>
        
         </tr>
         </thead>
    </table>
    	</div>
		</td>
		<td width="30%">
		<!-- second grid -->
		<div title="Samples" style="padding: 10px; height: 300px">					
			<table id="t7_eof_chem_samp_tbl_id"
							toolbar="#t7_eof_Chem_Sample_form_btn_div_id"
							title="List of Samples"
							class="easyui-datagrid" style="width: 100%"
							iconCls='icon-ok' maximizable="false" resizable="true"
							remoteSort="false" rownumbers="true" singleSelect="true">
				<thead>
					<tr>
						<th field="sample_no" width="170">Sample No</th>
						<th field="action" width="170" formatter="viewT7SampleDtls">Action     </th>
						<th field="sample_si_no" hidden="true" width="0">Chem Header Id</th>
						<th field="sample_temp" hidden="true" width="0">Samp Temp</th>
						<th field="sample_result" hidden="true" width="0">Samp Result</th>
						<th field="sample_date_time" formatter="(function(v,r,i){return formatDateTime('sample_date_time',v,r,i)})"
						hidden="true" width="0">Samp Date</th>
						<th field="remarks" hidden="true" width="0">remarks</th>
						<th field="final_result" hidden="true" width="0">Final Result</th>
						
					</tr>
				</thead>
			</table>
		</div>
		</td>
		</tr>
		</table>    
    
         	<div  id="t7_eof_Chemistry_Addition_form_btn_div_id">
         <a href="javascript:void(0)" class="easyui-linkbutton" id="t7_save_chem_btn_id" iconCls="icon-save" onclick="saveT7ChemistryDtls()" style="width:90px">Save</a>
       <!--  <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload" onclick="cancelT7ChemistryDtls()" style="width:90px">Refresh</a>-->
         <a href="javascript:void(0)" class="easyui-linkbutton" id="t7_close_chem_btn_id" iconCls="icon-cancel" onclick="dialogBoxClose('t7_eof_Chemistry_form_div_id')" style="width:90px">Close</a>
       <!-- <a href="javascript:void(0)" class="easyui-linkbutton" id="t7_ok_chem_btn_id" iconCls="icon-save" onclick="enableSave()" style="width:90px">Ok</a> -->
    
    </div>
    <div id="t7_eof_Chem_Sample_form_btn_div_id">
		 <a href="javascript:void(0)" class="easyui-linkbutton" id="t7_final_result_btn_id" onclick="saveFinalResult()"
				style="width: 90px">Final Result</a> 
	</div>	
    </form>
    </div>
   
    <!-- Chemical Details Screen End --> 
    
    <!-- Events Screen Start -->
   
   <div id="t8_eof_events_form_div_id" class="easyui-dialog" style="height:450px;padding: 0 50px 0 50px;" 
            closed="true" data-change="0">
             
        <form id="t8_eof_events_form_id" method="post" novalidate>
       
       
       <table style="width: 100%;padding-left: 20px;padding-right: 20px;" >
       
      
        <tr height="40">
       <td> <label style="padding-right: 20px;"><spring:message code="label.t8.eofEventHeatNo"/></label>
         <input name="heat_no"  type="text" id="t8_heat_no"  class="easyui-textbox" data-options="editable:false">
        </td>
        
       <td> 
        <label style="padding-right:20px"><spring:message code="label.t8.eofEventAimPsn"/></label>
         <input name="aim_psn"  type="text" id="t8_aim_psn"  class="easyui-textbox" data-options="editable:false">
         
        </td>
        <!--  
        <td> 
        <label style="padding-right: 20px">Tare Weight</label>
         <input name="tare_weight"  type="text" id="t8_tare_weight"  class="easyui-textbox" data-options="editable:false">
         <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="getTareWeightDetails()" style="width:30px"></a>
        </td>
       	-->
    </tr> 
      
        </table>
        
        <br>
    
       
       <table id="t8_eof_events_tbl_id"  toolbar="#t8_eof_events_form_btn_div_id"  title="<spring:message code="label.t8.eofEvents"/>" class="easyui-datagrid" style="width:80%;height:auto;"
           iconCls='icon-ok' maximizable="false" fitColumns="true"  resizable="true" remoteSort="false"  rownumbers="true" singleSelect="true"> 

        <thead>
            <tr>
                               
              <th field="event_id" formatter="(function(v,r,i){return formatColumnData('eventName',v,r,i);})" sortable="false" width="200"><spring:message code="label.t8.eofEventName"/></th>
              <th field="event_date_time" sortable="false" class="test" editor="{type:'datetimebox', options:{editable:true}}" formatter="(function(v,r,i){return formatDateTime('event_date_time',v,r,i)})"  width="150"><spring:message code="label.t8.eofEventDate"/></th>
              <th field="heat_proc_event_id"  hidden="true" width="0"><spring:message code="label.t8.pkId"/></th>
         	  <th field="event_unit_seq" hidden="true" width="0"><spring:message code="label.t8.pkId"/></th>	
         </tr>
         </thead>
    </table>
    <br>
         	<div id="t8_eof_events_form_btn_div_id">
         <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" id="T8SaveBtn" onclick="saveT8EventDtls()" style="width:90px">Save</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload" onclick="cancelT8EventDtls()" style="width:90px">Refresh</a>
         <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="dialogBoxClose('t8_eof_events_form_div_id')" style="width:90px">Close</a>
    </div>
        </form>
    </div>
   
    <!-- Events Screen End --> 
    
     <!-- Delay Entry Screen Start -->
   
   <div id="t9_delay_entry_form_div_id" class="easyui-dialog" style="height:450px;margin:5px;" 
            closed="true" data-change="0" data-rowchange="0">
             
        <form id="t9_delay_entry_form_id" method="post" novalidate>
       
       
       <table style="width: 100%" class="easyui-panel" >
       
        <!-- first row -->
        <tr>
        <td>
        <label>HEAT NO</label>
        
         <input name="heat_no"  type="text" id="t9_heat_no"  class="easyui-textbox" data-options="editable:false">
        </td>
        
       <td> 
        <label>AIM PSN</label>
         <input name="aim_psn"  type="text" id="t9_aim_psn"  class="easyui-textbox" data-options="editable:false">
         
        </td>         
        </tr>
      </table>
       
       <table id="t9_delay_entry_tbl_id"  toolbar="#t9_delay_entry_form_btn_div_id"  title="Delay Details Entry" class="easyui-datagrid" style="width:100%;height:auto;"
           iconCls='icon-ok' maximizable="false" fitColumns="true"  resizable="true" remoteSort="false"  rownumbers="true" singleSelect="true"> 

        <thead>
            <tr>         
              <th field="activity_master.activities" formatter="(function(v,r,i){return formatColumnData('activity_master.activities',v,r,i);})"  sortable="false" width="60">Activity</th>
              <th field="activity_master.delay_details" formatter="(function(v,r,i){return formatColumnData('activity_master.delay_details',v,r,i);})" sortable="false" width="120" >Delay Details</th>
              <th field="start_time" sortable="false" formatter="(function(v,r,i){return formatDateTime('start_time',v,r,i)})" width="70">Start Time</th>
              <th field="end_time" sortable="false" formatter="(function(v,r,i){return formatDateTime('end_time',v,r,i)})" width="70">End Time</th>
              <th field="duration" sortable="false" width="40" >Duration(min)</th>
              <th field="activity_master.std_cycle_time" formatter="(function(v,r,i){return formatColumnData('activity_master.std_cycle_time',v,r,i);})" sortable="false"  width="40">Standard Duration(min)</th>
              <th field="delay" sortable="false"  width="40">Delay(min)</th>
               <th field="corrective_action" sortable="false" formatter="(function(v,r,i){return formatColumnData('corrective_action',v,r,i);})"  width="80" editor="{type:'textbox'}">Corrective Action</th>
              	
            </tr>
         </thead>
    </table>
    <br>
    <div id="t9_delay_entry_form_btn_div_id">
         <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="saveT9DelaytDtls()" style="width:90px">Save</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload" onclick="getT9DelayDetails()" style="width:90px">Refresh</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload" onclick="t9addDelayDetails()" style="width:150px">Add Delay Details</a>
         <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="dialogBoxClose('t9_delay_entry_form_div_id')" style="width:90px">Close</a>
    </div>
        </form>
    </div>
    <!-- TEST -->
    
     <div id="t91_delay_entry_form_div_id" class="easyui-dialog" style="height:450px;margin:5px;" 
            closed="true" data-change="0">
             
        <form id="t91_delay_entry_form_id" method="post" novalidate>
       <input name="t91activity_value"  type="hidden" id="t91activity_value"  >
       <input name="t91delay_details"  type="hidden" id="t91delay_details"  >
       <input name="t91delay"  type="hidden" id="t91delay"  >
       <input name="trans_delay_dtl_id_value"  type="hidden" id="trans_delay_dtl_id"  >
       
       <table style="width: 100%" class="easyui-panel" >
       
        <!-- first row -->
        <tr>
        <td>
        <label>HEAT NO</label>
        
         <input name="heat_no"  type="text" id="t91_heat_no"  class="easyui-textbox" data-options="editable:false">
        </td>
        
       <td> 
        <label>AIM PSN</label>
         <input name="aim_psn"  type="text" id="t91_aim_psn"  class="easyui-textbox" data-options="editable:false">
         
        </td>   
         <td> 
        <label>Activity</label>
         <input name="aim_psn"  type="text" id="t91_activity"  class="easyui-textbox" data-options="editable:false">
         
        </td>         
        </tr>
      </table>
       
       <table id="t91_delay_entry_tbl_id"  toolbar="#t91_delay_entry_form_btn_div_id" 
        title="Delay Details Entry" class="easyui-datagrid" style="width:90%;height:auto;"
        iconCls='icon-ok' maximizable="false" fitColumns="true" 
        data-options="fitColumns:true,singleSelect:true" 
        resizable="true" remoteSort="false"  rownumbers="true" singleSelect="true" data-delay="0"> 

        <thead>
            <tr>      
             <th field="activities"  formatter="(function(v,r,i){return formatT9ActivityColumnData('activities',v,r,i);})"  sortable="false" width="60">Activity</th>
              <th field="delay_details"   formatter="(function(v,r,i){return formatT9DlyDtlsColumnData('delay_details',v,r,i);})" sortable="false" width="80" >Delay Details</th>
              
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
    
   
    <div id="t91_delay_entry_form_btn_div_id">
    
     <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true"
			onclick="javascript:$('#t91_delay_entry_tbl_id').edatagrid('addRow')" ><b>Add</b></a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-save" plain="true"
			onclick="javascript:$('#t91_delay_entry_tbl_id').edatagrid('saveRow')"><b>Save</b></a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-reload"
			plain="true" onclick="t91ReferenshData()"><b>Refresh</b></a>
    </div>
        </form>
    </div>
    
    <!-- END  -->   
   
   
    <!-- Delay Entry Screen End --> 
    
    
<!-- PSN Documents Start -->      
 <div id="t10_PSN_Docs_form_div_id"class="easyui-dialog"
		closed="true" style="width: 1150px; height: 600px; padding-top: 0px; padding-left: 10px; padding-right: 20px;">	
    <form id="t10_PSN_Docs_form_id" method="post" novalidate>   
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
	    <input name="t10_psn_no" type="hidden"  id="t10_psn_no">
        <label style="padding-left: 1%">PSN NO</label>
        <input name="t10_rep_psn_desc"  type="text" id="t10_rep_psn_desc" class="easyui-textbox" data-options="editable:false" style="width: 100px;">
		<label style="padding-left: 15px"></label>
		<a href='javascript:void(0)'   id="t10_print" disabled="true" class='easyui-linkbutton' style='width:100px' iconCls='icon-print' plain='false' onclick='executePrintPsn()'>Print</a>
		<a href='javascript:void(0)'  id="t10_export" disabled="true" class='easyui-linkbutton' style='width:100px' iconCls='icon-edit' plain='false' onclick='executeExportPsnDoc()'>Word</a>
		<a href='javascript:void(0)'  id="t10_cancel" class='easyui-linkbutton' style='width:100px' iconCls='icon-save' plain='false' onclick=''>Close</a>
	  </div>
	<div id="t10_psn_report_id" style="background:white" width="100%" class="easyui-panel" closed="true">
	</div>
	</div>    
	</form>
    </div>   
  <!-- PSN Documents Screen End -->
     
  <!-- Process Parameter Screen Start -->
  <div id="t12_process_param_form_div_id" class="easyui-dialog" style="height:550px;padding:10px 10px"
            closed="true" data-change="0">
	<div id="t12_process_param_tabs_div_id" class="easyui-tabs"  pill="true" style="width: 100%;height: auto;">
     
    	<div title="PROCESS PARAMETERS" style="padding:10px">
     	<table style="width: 100%" class="easyui-panel" >
	        <!-- first row -->
		<tr>
		<td>
        	<label>HEAT NO</label>       
         	<input name="heat_no"  type="text" id="t12_heat_no"  class="easyui-textbox" data-options="editable:false">
        </td>
       	<td> 
        	<label>AIM PSN</label>
         	<input name="aim_psn"  type="text" id="t12_aim_psn"  class="easyui-textbox" data-options="editable:false">
	    </td>         
    	</tr>
		</table>
     	<table id="t12_process_param_tbl_id" toolbar="#t12_process_param_form_btn_div_id"  title="PROCESS PARAMETERS" class="easyui-datagrid" style="width:100%;height:auto;"
           iconCls='icon-ok' maximizable="true"  resizable="true" remoteSort="false"  rownumbers="true" singleSelect="true"> 
        <thead>
			<tr>
              	<th field="param_id" formatter="(function(v,r,i){return formatColumnData('proc_para_name',v,r,i);})" sortable="false" width="150">PARAMETERS</th>
               	<th field="uom" formatter="(function(v,r,i){return formatColumnData('uom',v,r,i);})" sortable="false" width="150">UOM</th>
             	<th field="param_value_aim" align="right" width="150">AIM_VALUE</th>
             	<th field="param_value_min" align="right" width="100" >MIN_VALUE</th>
             	<th field="param_value_max" align="right" width="100" >MAX_VALUE</th>
                <th field="param_value_actual" align="right" data-options="editor:{type:'numberbox',options:{precision:4,min:0}}"  width="150">ACT_VALUE<font color="red">*</font></th>
              	<th field="process_date_time" sortable="true" editor="{type:'datetimebox',options:{required:false,editable:true}}" formatter="(function(v,r,i){return formatDateTime('process_date_time',v,r,i)})"  width="150">PROCESS DATE</th>
              	<th field="proc_param_event_id" sortable="true" hidden="true" width="0">PROCESS_ID</th>
         	</tr>
         </thead>
    	</table>
    	<div id="t12_process_param_form_btn_div_id">
         	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="saveT12ProcessParamDtls()" style="width:90px">Save</a>
        	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload" onclick="cancelT12ProcParamDtls()" style="width:90px">Refresh</a>
         	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="dialogBoxClose('t12_process_param_form_div_id')" style="width:90px">Close</a>
    	</div>
    	</div>  
    	
		<!-- second tab -->
		<div title="ELECTRODE USAGE" >
		<table style="width: 100%" class="easyui-panel" >      
        	<!-- first row -->
        	<tr>
        	<td style="width:100px">
        	<label style='padding-left:10px;padding-right:10px'>HEAT NO</label>
			<input name="heat_no"  type="text" id="t12_e_heat_no"  class="easyui-textbox" data-options="editable:false">
        	</td>
       		<td style="width:100px"> 
        	<label style='padding-left:10px;padding-right:10px'>AIM PSN</label>
         	<input name="aim_psn"  type="text" id="t12_e_aim_psn"  class="easyui-textbox" data-options="editable:false">
	        </td>           
    	    <td style="width:100px">
        	<label style='padding-left:10px;padding-right:10px'>START TIME</label>
			<input name="electrode_start_time"  type="text" id="t12_e_start_time"   class="easyui-datetimebox"
						data-options="required:true,showSeconds:false">
        	</td>
			<td style="width:100px"> 
        	<label style='padding-left:10px;padding-right:10px'>END TIME</label>
         	<input name="electrode_end_time"  type="text" id="t12_e_end_time"  class="easyui-datetimebox"
						data-options="required:true,showSeconds:false">
	        </td>
        </tr>
      	</table>
		
		<table id="t12_electrode_usage"   title="ELECTRODES" class="easyui-datagrid" style="width:50%;height:auto;" toolbar="#t12_electrode_usage_btn_div_id"
           iconCls='icon-ok' maximizable="true"  resizable="true" remoteSort="false"  rownumbers="true" singleSelect="false"> 
    	</table>
      	<div id="t12_electrode_usage_btn_div_id">
			<a href="#" class="easyui-linkbutton" iconCls="icon-save" plain="true"
			onclick="javascript:$('#t12_electrode_usage').edatagrid('saveRow')"><b>Save</b></a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-reload"
			plain="true" onclick="refreshEofElectrodeData()"><b>Refresh</b></a>
    	</div>
		</div>
	</div>  
  </div>  
     <!-- Dispatch Screen Start -->     
    
    
   <div id="t11_eof_dispatch_win" class="easyui-window" title="Dispatch" data-options="iconCls:'icon-save',closed:true,minimizable:false,collapsible:false,maximizable:false,modal:true" style="width:1000px;height:550px;padding:5px;">
        <div class="easyui-layout" data-options="fit:true">
            <div data-options="region:'east',split:true" style="width:285px">
         
            
        <table id="t11_eof_dispatch_validation" title="Validation Results" class="easyui-datagrid" style="width:281px;height:auto;padding: 20px 20px 20px 20px;"
             resizable="true">
        <thead>
            <tr>
            	
            	<th field="validation_compo" width="200px;">Validation Section</th>
                <th field="validation_result" width="80px;"  formatter="viewT11DispatchImg">Result</th>
               
            </tr>
        </thead>
    </table>
            
            </div>
            <div data-options="region:'center'" style="padding:10px;">
    
    <div id="t11_eof_dispatch_form_div_id" class="easyui-dialog" style="height:320px; width:650px; padding:5px 0px 5px 5px;" 
            closed="true">
             
        <form id="t11_eof_dispatch_form_id" method="post" novalidate>
       
        <input name="t11_campaign_id"  type="hidden" id="t11_campaign_id">
       
       <table>
       
      
       <tr>
       <td colspan=2 ><label><spring:message code="label.t11.eofDispatchCompaign"/></label>
       	<input name="campaign_status"  type="text" id="t11_campaign_status"  class="easyui-combobox" data-options="required:true,panelHeight: 'auto',editable:false,
                    valueField:'keyval',                    
                    textField:'txtvalue'">
       </td>
        <td colspan=3 style="padding: 10px;width:100px"> <label >Is new Ladle Campaign</label><input name="ladle_campaign_status"  type="text" id="t11_ladle_campaign_status"  class="easyui-combobox" data-options="required:true,panelHeight: 'auto',editable:false,
                    valueField:'keyval',                    
                    textField:'txtvalue'"> </td>
       </tr>
       <tr>
       <td colspan=5><label><spring:message code="label.t11.eofDispatchRemarks"/></label>
        <input name="campaign_remarks"  type="text" id="t11_campaign_remarks"  data-options="multiline:true" style="height:40px;width: 100%" class="easyui-textbox">
        </td>
        </tr>
        <tr >
       	<td colspan=5>
       	<label >Ladle Campaign Remarks</label>  <input name="ladle_campaign_remarks"  type="text" id="t11_ladle_campaign_remarks"  data-options="multiline:true" style="height:40px;width: 100%" class="easyui-textbox">
        </td>
    	</tr>     	
        
      
       <tr rowspan=10>
       <td colspan=3>
       <div>
       <table>
        
       <tr> <td > <label >Change Wall</label></td><td> <input name="refractory_status"  type="text" id="t11_refractory_status"  class="easyui-combobox" data-options="required:true,panelHeight: 'auto',editable:false,
                    valueField:'keyval',                    
                    textField:'txtvalue'"> </td></tr>
       <tr>
        <td > <label>  Change Bottom</label></td><td>
        	<input name="tuyer_status"  type="text" id="t11_tuyer_status"  class="easyui-combobox" data-options="required:true,panelHeight: 'auto',editable:false,
                    valueField:'keyval',                    
                    textField:'txtvalue'"> </td></tr>
        <tr> <td > <label >Change EBT</label></td><td> <input name="tap_hole_status"  type="text" id="t11_tap_hole_status"  class="easyui-combobox" data-options="required:true,panelHeight: 'auto',editable:false,
                    valueField:'keyval',                    
                    textField:'txtvalue'"> </td></tr>
         <tr> <td > <label >Change Delta</label></td><td><input name="steel_launder_status"  type="text" id="t11_steel_launder_status"  class="easyui-combobox" data-options="required:true,panelHeight: 'auto',editable:false,
                    valueField:'keyval',                    
                    textField:'txtvalue'"> </td></tr>
          <tr> <td > <label >Change HM Launder</label></td><td> <input name="hm_launder_status"  type="text" id="t11_hm_launder_status"  class="easyui-combobox" data-options="required:true,panelHeight: 'auto',editable:false,
                    valueField:'keyval',                    
                    textField:'txtvalue'"> </td> </tr> 
           <tr>
         <td > <label>Steel Ladle</label></td><td>
         <input name="steel_ladle"  type="text" id="t11_steel_ladle"  class="easyui-combobox" data-options="required:true,panelHeight: 'auto',editable:false,
                    valueField:'keyval',                    
                    textField:'txtvalue'">
        </td></tr>
         <tr><td>  <label >Dispatch To</label></td><td>
                <input name="dispatch_to"  type="text" id="t11_dispatch_to"  class="easyui-combobox"  data-options="panelHeight: 'auto',editable:false,required:true,
                    valueField:'keyval',                    
                    textField:'txtvalue'">
       </td>       
       </tr>
       <tr>
         <td >
       <label >Tap Weight(In Tons)</label></td><td>
         <input name="tap_weight"  type="text" id="t11_tap_weight"  class="easyui-numberbox" data-options="precision:2,align:'right'">
        </td></tr><tr><td>
           <label >Tap Temperature</label></td><td>
        <input name="tap_temp"  type="text" id="t11_tap_temp"  class="easyui-numberbox"  data-options="required:true,editable:true,align:'right'">
        </td>        
      </tr>                  
                   </table> 
       </div>             
       </td>
     
       <td colspan=2>
    <!-- <div id="t11_parts_life_det_div_id" style="width:320px">
			<table id="t11_parts_life_det_tbl_id" toolbar="#t11_parts_life_det_reset_div"   class="easyui-datagrid" style="width:100%;height:auto;"
           iconCls='icon-ok' maximizable="true"  resizable="true" remoteSort="false"  rownumbers="false" singleSelect="true"> 
        <thead>
            <tr>
            	<th field="ladle_life_sl_no" width="0"  hidden="true" >SlNo</th>
            	<th field="equipment_id" width="0" hidden="true" >EqId</th>
             	<th field="part_id" width="0" hidden="true" >partId</th>
             	<th field="part_name" width="150" >Part Name</th>
             	<th field="trns_life" width="80" data-options="align:'right'" >Life</th>
               	<th field="max_life_value" width="80" data-options="align:'right'">Max Life</th>
         </tr>
         </thead>
    </table>	
    <div id="t11_parts_life_det_reset_div">
         <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="resetT11PartLife()" style="width:90px">Reset Life</a>
    	</div>
    </div> -->
   
		</td>
		
		
		</tr> 
		
   
		
       
    
        </table>
        <br>
        <div id="t11_eof_dispatch_form_btn_div_id" align="center">
        <a href="javascript:void(0)" id="t11_send_btn_id" class="easyui-linkbutton" data-options="iconCls:'icon-ok',disabled:'true'" onclick="saveT11EofDispatch()" style="width:120px">Save & Send</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="$('#t11_eof_dispatch_win').window('close')" style="width:120px">Close</a>
        </div>
        </form>
    </div>    
  </div>            
        </div>
    </div>

     <!-- Dispatch Screen End --> 
    
       
      <div id="t17_eof_byProducts_form_div_id" class="easyui-dialog" style="height:550px;padding:10px 10px"
            closed="true" data-change="0">
            
     <div title="<spring:message code="label.t17.eofbyProducts"/>" style="padding:10px">
     <table style="width: 100%" class="easyui-panel" >
        <tr>
        <td>
        <label>HeatNo</label>
         <input name="heat_no"  type="text" id="t17_heat_no"  class="easyui-textbox" data-options="editable:false">
        </td>
        
       <td> 
        <label>PSN No</label>
         <input name="aim_psn"  type="text" id="t17_aim_psn"  class="easyui-textbox" data-options="editable:false">
         
        </td>
         
        </tr>
      </table>
     <table id="t17_eof_byProducts_tbl_id" toolbar="#t17_eof_byProducts_form_btn_div_id"  title="<spring:message code="label.t17.eofbyProducts"/>" class="easyui-datagrid" style="width:100%;height:auto;"
           iconCls='icon-ok' maximizable="false"  resizable="true" showFooter="true" remoteSort="false"  rownumbers="true" singleSelect="true"> 
        <thead>
            <tr>
              <th field="material_id" formatter="(function(v,r,i){return formatColumnData('mtrlName',v,r,i);})" sortable="false" width="170">ByProduct Name</th>
              <th field="sap_matl_id" align="left" sortable="false" width=120 >SAP Matl ID</th>
              <th field="qty" align="right" formatter="(function(v,r,i){return formatColumnData('qty',v,r,i);})" data-options="editor:{type:'numberbox',options:{precision:2,min:0}}"  width="100">Qty</th>
               <th field="uom" formatter="(function(v,r,i){return formatColumnData('uom',v,r,i);})" sortable="false" width="100">UOM</th>
               <th field="val_min" align="right" hidden="true">Min Value</th>
              <th field="val_max" align="right" hidden="true">Max Value</th>
              <th field="mtr_cons_si_no" sortable="true" hidden="true" width="0"><spring:message code="label.t5.pkId"/></th>
              <th field="consumption_date" sortable="true" editor="{type:'datetimebox',options:{required:false,editable:true}}" formatter="(function(v,r,i){return formatDateTime('consumption_date',v,r,i)})"  width="150">Produced Date</th>
         <th field="version" hidden="true" align="right" width="100">Version</th>
         </tr>
         </thead>
    </table>
      <div  id="t17_eof_byProducts_form_btn_div_id">
         <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="saveT17ByProductsDtls()" style="width:90px">Save</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload" onclick="cancelT17ByProductsDtls()" style="width:90px">Refresh</a>
         <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="dialogBoxClose('t17_eof_byProducts_form_div_id')" style="width:90px">Close</a>
    </div>
     
    
     </div>
   
     </div>
    
    <!-- EOF CONTAINER CHANGE SCREEN STARTS -->
    <div id="t17_eof_container_change" class="easyui-dialog" style="height:400px;padding:10px 10px"
            closed="true" data-change="0">
            
     <div title="Shell Change" style="padding:10px">
   
    <table id="t12_mstr_eof_container_life_tbl_id"
		toolbar="#t12_mstr_eof_container_life_toolbar_div_id"
		title="Shell Life" class="easyui-datagrid"
		style="width: 100%; height: 300px;"
		url="./MstrContainerLife/getContainerDtls" method="get"
		iconCls='icon-ok' pagination="true" maximizable="true"
		resizable="true" remoteSort="false" pageSize="20" rownumbers="true"
		fitColumns="true" singleSelect="true">     <thead>
			<tr>
				<th field="container_id" sortable="true" hidden="true" width="20"></th>
				<th field="container_name" sortable="true" width="40"><b>Shell
						Name</b></th>
				<th field="supplier_id" sortable="true" width="40"><b>Supplier</b></th>
				<th field="container_life" sortable="true" width="20"><b>Shell
						Life</b></th>
				<th field="status" sortable="true" width="40"><b>Status</b></th>
				<th field="unit" formatter="(function(v,r,i){return formatColumnData('subUnitMdl.sub_unit_name',v,r,i);})" sortable="true" width="20"><b>UNIT</b></th>

			</tr>
		</thead>
    </table>
     <div  id="t12_mstr_eof_container_life_toolbar_div_id">
         <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload" onclick="T12ContainerChange()" style="width:90px">Change</a>
     </div>
     </div>
   
     </div>
     <div id="t12_mstr_eof_container_life_form_div_id" class="easyui-dialog"
	style="width: 430px; height:300px; padding: 10px 10px" closed="true"
	buttons="#t12_mstr_eof_container_life_form_btn_div_id">
	<div class="ftitle">
		<b>Shell Change</b>
	</div>
	<form id="t17_mstr_lkp_form_id" method="post" novalidate>
		<input name="container_id" type="hidden" id="t12_container_id">
		

		<div class="fitem">
			<label>Shell Name</label> <input name="container_name"
				type="text" id="t12_container_name" class="easyui-textbox"
				data-options="required:true,validType:{length:[1,60]}">
		</div>

		<div class="fitem">
			<label>Supplier</label> <input name="supplier_id" type="text"
				id="t12_supplier_id" class="easyui-combobox"
				data-options="required:true,panelHeight: 'auto',editable:false,selected:true,
                  valueField:'keyval',                    
                    textField:'txtvalue'">
		</div>

		<!-- <div class="fitem">
			<label>SUPPLIER</label> <input name="" type="text"
				id="t12_supplier_id" class="easyui-combobox"
				data-options="required:true,panelHeight: 'auto',editable:false,selected:true,
                  valueField:'keyval',                    
                    textField:'txtvalue'">
		</div> -->

		<div class="fitem">
			<label>Shell Life</label> <input name="container_life"
				type="text" id="t12_container_life" class="easyui-numberbox"
				data-options="required:true,validType:{length:[1,300]}">
		</div>


		<div class="fitem">
			<label>Status</label> <input name="container_status" type="text"
				id="t12_status" class="easyui-combobox"
				data-options="required:false,panelHeight: 'auto',editable:false,selected:true,
                  valueField:'keyval',                    
                    textField:'txtvalue'">

		</div>

		<div class="fitem">
			<label>Unit</label> <input name="container_unit" type="text"
				id="t12_unit" class="easyui-combobox"
				data-options="required:true,panelHeight: 'auto',editable:false,selected:true,
                  valueField:'keyval',                    
                    textField:'txtvalue'">

		</div>

		<div id="running_container_details" class="fitem">
			<hr>
			Currently Running Shell
			<hr>
			<input name="current_container_id" type="hidden" id="t12_current_container_id">
			
			

		<div class="fitem">
			<label>Shell Name</label> <input name="running_container_name"
				type="text" id="t12_running_container_name" class="easyui-textbox"
				data-options="required:true,validType:{length:[1,60]}">
		</div>

		<div class="fitem">
			<label>Supplier</label> <input name="running_supplier_id" type="text"
				id="t12_running_supplier_id" class="easyui-combobox"
				data-options="required:true,panelHeight: 'auto',editable:false,selected:true,
                  valueField:'keyval',                    
                    textField:'txtvalue'">
		</div>

	
		<div class="fitem">
			<label>Shell Life</label> <input name="running_container_life"
				id="t12_running_container_life" class="easyui-numberbox"
				data-options="required:true,validType:{length:[1,300]}">
		</div>


		<div class="fitem">
			<label>Status</label> <input name="running_container_status" type="text"
				id="t12_running_container_status" class="easyui-combobox"
				data-options="required:true,panelHeight: 'auto',editable:false,selected:true,
                  valueField:'keyval',                    
                    textField:'txtvalue'">

		</div>

	 <div class="fitem">
			<label>Unit</label> <input name="running_container_unit" type="text"
				id="t12_running_unit" class="easyui-combobox"
				data-options="required:true,panelHeight: 'auto',editable:false,selected:true,
                  valueField:'keyval',                    
                    textField:'txtvalue'">

		</div>
		</div> 
	</form>
	<div id="t12_mstr_eof_container_life_form_btn_div_id">
		<a href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-ok" onclick="saveContainerLife()"
			style="width: 90px">Save</a> <a href="javascript:void(0)"
			class="easyui-linkbutton" iconCls="icon-cancel"
			onclick="cancelT12ContainerChange()" style="width: 90px">Cancel</a>
	</div>
</div>
    <!-- EOF CONTAINER CHANGE SCREEN END -->
    
   
 <script type="text/javascript">

 callT4Dropdowns();
 $('#t4_tap_start_at').datetimebox({
//	    value: (formatDate(new Date()))
	  onSelect:function()
	    {
	    	
	    	$('#t4_tap_close_at').datebox('setText','');
	    	
	    	//restrictDateRange('t4_tap_start_at','t4_tap_close_at',0);
	    	restrictDateAndHourRange('t15_arc_start_date','t15_arc_end_date',2,'Arc end date should not be more than 2 Hours from Arc Start date.');
	    	
	    }
	   
	});
 
 
 $('#t4_recDate').datetimebox({
	
	    value: (formatDate(new Date()))
	    
	   
	});
 
  var recdate= ($('#t4_recDate').datetimebox('getValue'));
 	 $('#t4_shift').combobox({
   		 onLoadSuccess: function(){  
 	 	
		    		value: autoShift(recdate,'t4_shift',$('#t4_shift').combobox('getData')) 
		
   		  }
 	}); 
 
 	 	
 			$('#t4_recDate').datetimebox({
	 		onChange: function(date){
 	 		 	
 	 		 			 value: autoShift(($('#t4_recDate').datetimebox('getValue')),'t4_shift',$('#t4_shift').combobox('getData')); 
 	 		 	
 	 		}
 			});
 	    
 $(window).load(setTimeout(applyT4Filter,1));  //1000 ms = 1 second.
 
 function applyT4Filter()
 {
	 //getEOFCampaignLife();
		 $('#t4_EOF_production_tbl_id').datagrid('enableFilter');
		 
 }
 function callT4Dropdowns()
    {
	// alert('callT4Dropdowns');
	 	var user_id= <%=session.getAttribute("USER_APP_ID") %>  
	 	//var sub_unit_id = $('#t4_unit').combobox('getValue');
	 	
    	
    	var url2="./CommonPool/getComboList?col1=sub_unit_id&col2=subUnitMstrMdl.sub_unit_name&classname=UserSubUnitMasterModel&status=1&wherecol=app_user_id="+user_id+" and subUnitMstrMdl.unitDetailsMstrMdl.unit_name='EAF' and record_status=";
    	var url3="./CommonPool/getComboList?col1=user_role_id&col2=appUserAccountDetails.user_name&classname=UserRoleMapMasterModel&status=1&wherecol=lookupMasterModel.lookup_code='EAF_SHIFT_IN_CHARGE' and lookupMasterModel.lookup_id=lookup_id  and record_status=";
    	var url4="./CommonPool/getComboList?col1=user_role_id&col2=appUserAccountDetails.user_name&classname=UserRoleMapMasterModel&status=1&wherecol=lookupMasterModel.lookup_code='EAF_SHIFT_ENGINEER' and lookupMasterModel.lookup_id=lookup_id  and record_status=";
    	//var url5="./CommonPool/getComboList?col1=user_role_id&col2=appUserAccountDetails.user_name&classname=UserRoleMapMasterModel&status=1&wherecol=lookupMasterModel.lookup_code='EAF_TAP_OPERATOR' and lookupMasterModel.lookup_id=lookup_id  and record_status=";
    	 var url1="./CommonPool/getComboList?col1=lookup_id&col2=lookup_value&classname=LookupMasterModel&status=1&wherecol=lookup_type='SHIFT' and lookup_status=";
    	 getDropdownList(url1,'#t4_shift');
    
    	getDropdownList(url2,'#t4_unit');
    	getDropdownList(url3,'#t4_shiftMgr');
    	getDropdownList(url4,'#t4_eofMgr');
    	//getDropdownList(url5,'#t4_tapOperator');
    	
    	/* if(sub_unit_id!=null && sub_unit_id!=''){
    	var url6="./CommonPool/getComboList?col1=trns_si_no&col2=heat_id&classname=EofHeatDetails&status=1&wherecol=dispatch_to_unit=null and sub_unit_id="+sub_unit_id+" and record_status=";
    	getDropdownList(url6,'#t4_heat_id');
    	} */
    }
 
 $('#t5_eof_furnace_entry_tab2_tbl_id3').datagrid({
	 onSelect: function(record){
		 var row = $('#t5_eof_furnace_entry_tab2_tbl_id3').datagrid('getSelected');
		 document.getElementById('t5_scrapBkt_hId').value = row.scrap_bkt_header_id;
		 	$('#t5_combo_scrapBktId').combobox('setValue', '');
		 	getT5ScrapDtlsGrid();
		}
		});
 
 
 $('#t4_unit').combobox({
	 onSelect: function(record){
		 	
		 GetT4UpdatedHeatList(); 
		 clearT4HeatHdrform();
		 getEOFCampaignLife();
		 $("#T4ladleDetailsId").show();
		 
		 
		}
		});
 
 function GetT4UpdatedHeatList()
 {
	 var sub_unit_id=$('#t4_unit').combobox('getValue');
	 var url6="./CommonPool/getComboList?col1=trns_si_no&col2=heat_id&classname=EofHeatDetails&status=1&wherecol=dispatch_to_unit=null and sub_unit_id="+sub_unit_id+" and record_status=";
 	getDropdownList(url6,'#t4_heat_id');
 }
 
 function getT4HeatAttachedPlan()
 {
	 var valid=false;
	 
	 var row = $('#t4_EOF_production_tbl_id').datagrid('getSelected');
	 clearT4HeatHdrform();
	    if (row){
	    	 document.getElementById('t4_heat_receieve_id').value=row.hmRecvId;
	    	//$("#t4_heat_receieve_id").val(row.mRecvId);
	   
	  //sub-unit level qunatity validation
	  var sub_unit_name=$('#t4_unit').combobox('getText');
	 
	  if(sub_unit_name!=null && sub_unit_name!='')
	  {
		  
	if(sub_unit_name=='EAF1' && row.hmAvlblWt<=45)
	{
			  valid=true;
	}else if(sub_unit_name=='EAF2' && row.hmAvlblWt>=45){
		valid=true;
	}
	  if(Boolean(true))// made to true to remove validation
	  {
	 $('#t4_EOF_HeatPlanDetailsView').dialog({modal:true,cache: true});
	 $('#t4_EOF_HeatPlanDetailsView').dialog('open').dialog('center').dialog('setTitle','Heat Plan Details');
	// $('#t4_eof_heatplan_tbl_id').datagrid('enableFilter');
	 
	 $('#t4_hm_wt').numberbox('setValue',row.hmAvlblWt); 
	 
	 $('#t4_hm_temp').numberbox('setValue',row.hmLadleTempEof);
	 
	 $('#t4_hm_ladle_no').textbox('setText',row.hmLadleNo);
	 
	 document.getElementById('t4_hmRecvId').value=row.hmRecvId;
	 document.getElementById('t4_hm_hmAvlblWt').value=row.hmAvlblWt;
	 document.getElementById('t4_hmSmsMeasuredWt').value=row.hmSmsMeasuredWt;
	 
	 document.getElementById('t4_hm_ldl_version').value=row.version;
	 
	 loadT4PlanWindow();
	  }else{
		  $.messager.alert('Information','Please Select HM Ladle when (EAF1-HM WT<=45 ton) and (EAF2-HM WT>=45)....!','info');
	  }
	  }else{
		  $.messager.alert('Information','Please Select Subunit...!','info');
	  } 
	    }else{
	    	$.messager.alert('Information','Please Select HM Ladle to Attach...!','info');
	    }
	 
 }
 
 
 function loadT4PlanWindow()
 {
	 $('#t4_eof_heatplan_tbl_id').datagrid('loadData', []);
	 
	 loadIngStart('t4_eof_heatplan_tbl_id','Loading....Please Wait......');
	
	 $.ajax({
	  		headers: { 
	  		'Accept': 'application/json',
	  		'Content-Type': 'application/json' 
	  		},
	  		type: 'GET',
	  		//data: JSON.stringify(formData),
	  		dataType: "json",
	  		url: "./HeatPlan/getHeatPlanDetailsForAttachByStatus?PLAN_STATUS=RELEASED",
	  		success: function(data) {
  			 	$('#t4_eof_heatplan_tbl_id').datagrid('loadData', data);
	  			$('#t4_eof_heatplan_tbl_id').datagrid('enableFilter');
	  			loadIngEnd('t4_eof_heatplan_tbl_id');
	  		  },
	  		error:function(){      
	  			$.messager.alert('Processing','Error while Processing Ajax...','error');
	  		  }
	  		});
 }
 
 
 function getT4AttachedHeatProcess()
 {
	 var row = $('#t4_eof_heatplan_tbl_id').datagrid('getSelected');
	
	    if (row){
	    	
	    	var sub_unit_name = $('#t4_unit').combobox('getText');
	    		    	
	    	if((row.line_status=='RELEASED'))
    			{
	    	 $('#t4_aim_psn').textbox('setText',row.psnHdrModel.psn_no);
	    	
	    	 document.getElementById('t4_aim_psn_id').value=row.psnHdrModel.psn_hdr_sl_no;
	    	 
	    	 $('#t4_heat_plan_id').numberbox('setValue',row.heat_plan_id);
	    	 
	    	 $('#t4_heat_plan_line_no').numberbox('setValue',row.line_no);
	    	 
	    	 document.getElementById('t4_heat_line_id').value=row.line_id;
	    	 
	    	 document.getElementById('t4_plan_heat_qty').value=row.plan_qty;
	    	 
	    	 document.getElementById('t4_plan_line_version').value=row.line_version;
	    	
	    	 $('#t4_EOF_HeatPlanDetailsView').dialog('close');
	    	 
	    	 var row1 = $('#t4_EOF_production_tbl_id').datagrid('getSelected');
	    	 var psn_hdr_sl_no= row.psnHdrModel.psn_hdr_sl_no;
	    	 
	    	 $.ajax({
	 	  		headers: { 
	 	  		'Accept': 'application/json',
	 	  		'Content-Type': 'application/json' 
	 	  		},
	 	  		type: 'GET',
	 	  		//data: JSON.stringify(formData),
	 	  		dataType: "json",
	 	  		url: "./psnChemMstr/getPsnChemMstrByElement?psn_hdr_sl_no="+psn_hdr_sl_no+"&chemlevel=HM_CHEM&element=S",
	 	  		success: function(data) {
	 	  				if((row1.hmLadleS < data.value_min) || (row1.hmLadleS > data.value_max)){
	 	  				 $.messager.alert('Warning','S Value is not within Min amd Max Range','warning');
	 	  				}
	 	  				var i = generateHeatNo(sub_unit_name);
	 	  		  },
	 	  		error:function(){      
	 	  			//$.messager.alert('Processing','Error while Processing Ajax...','error');
	 	  			$.messager.alert('Processing','Master data Not Available','error');
	 	  		  }
	 	  		});
	    	 
	    		}else{
	    			clearT4HeatHdrform();
	    			$.messager.alert('Information','Selected Plan Under Processing or Not Released...Please Select Available Plan..','info');
	    			
	    		}
	    	container_trns_dtls();
	    }else{
	    	$.messager.alert('Information','Please Select Heat Plan...!','info');
	    }
 }
 
 function validateT4EOFCrewDetailsform(){
     return $('#t4_crew_form_id').form('validate');
 }
 
 function validateT4EOFHeatDetailsform(){
	
     return $('#t4_heat_hdr_form_id').form('validate');

 }
 
 function validateTapEvent(){
   	var tap_start_at=$('#t4_tap_start_at').datetimebox('getText');
	  var tap_close_at=$('#t4_tap_close_at').datetimebox('getText');
	  var tap_start_date= commonDateISOformat(tap_start_at);
	  var tap_close_date= commonDateISOformat(tap_close_at);
	  if((tap_start_at!='' && tap_close_at!='') && (tap_start_date.getTime() > tap_close_date.getTime())){
		  return false;
	  }else{
		  return true;
	  }
 }
 function saveT4EOFHeatDetails()
 {
	 if(validateT4EOFHeatDetailsform()){
		 if(validateT4EOFCrewDetailsform()){
			if(validateTapEvent()){
	 var trns_si_no=document.getElementById('t4_trns_si_no').value;
	 
	 if(trns_si_no==0){
		 trns_si_no=0;
	 }
	 //var trns_si_no=$('#t4_heat_id').combobox('getValue');
	 var heat_id=$('#t4_heat_id').combobox('getText'); 
	 
	 var heat_counter=1;
	 	 
	 var aim_psn=document.getElementById('t4_aim_psn_id').value; 
	 
	 var heat_plan_id=$('#t4_heat_plan_id').numberbox('getValue');
	 var heat_plan_line_id=document.getElementById('t4_heat_line_id').value;
	 var production_date=$('#t4_recDate').datetimebox('getText'); 
	 
	 var production_shift=$('#t4_shift').combobox('getValue'); 
	 
	 var plan_wt=document.getElementById('t4_plan_heat_qty').value;
	 var sub_unit_id=$('#t4_unit').combobox('getValue');
	 
	 var sub_unit_name=$('#t4_unit').combobox('getText');
	 
     var hm_wt=$('#t4_hm_wt').numberbox('getValue');
	 
	 var hm_temp=$('#t4_hm_temp').numberbox('getValue');
	 
	 var hmRecvId=document.getElementById('t4_hmRecvId').value;
	 
	 var hm_charge_at=$('#t4_hm_charge_at').datetimebox('getText');
	 
	 var scrap_charge_at=$('#t4_scrap_charge_at').datetimebox('getText');
	 
	 var tap_start_at=$('#t4_tap_start_at').datetimebox('getText');
	 
	 var tap_close_at=$('#t4_tap_close_at').datetimebox('getText');
	 
	 var hmAvlblWt=document.getElementById('t4_hm_hmAvlblWt').value; 
	 
	 var version= document.getElementById('t4_version').value; 
	 
	 var hm_ldl_version= document.getElementById('t4_hm_ldl_version').value; 
	 var hm_receipt_id=document.getElementById('t4_heat_receieve_id').value;
	 //container_trns_id for container_life and furnace life tracking purpose against EOF heat number...
	 var container_trns_id=document.getElementById('t4_container_trns_id').value;
	 var container_life=document.getElementById('t4_container_life').value;
	 var furnace_life=$('#furnaceLife').textbox('getValue');
	 var plan_line_version= document.getElementById('t4_plan_line_version').value; 
	 var hm_mwt=document.getElementById('t4_hmSmsMeasuredWt').value; 
	 var formData = {"trns_si_no":trns_si_no,"sub_unit_id":sub_unit_id,"sub_unit_name":sub_unit_name,"heat_counter":heat_counter,"heat_id":heat_id,"aim_psn":aim_psn,
	     		"heat_plan_id":heat_plan_id,"heat_plan_line_id":heat_plan_line_id,
	     		"plan_wt":plan_wt,"hm_wt":hm_wt,"hm_temp":hm_temp,"hmRecvId":hmRecvId,"available_hm":hmAvlblWt,"version":version,"production_shift":production_shift,
	     		"hm_mwt":hm_mwt,"hm_receipt_id":hm_receipt_id,"container_trns_id":container_trns_id,"container_life":container_life,"furnace_life":furnace_life
	     		};
 
		 
 	$.ajax({
 		headers: { 
 		'Accept': 'application/json',
 		'Content-Type': 'application/json' 
 		},
 		type: 'POST',
 		data: JSON.stringify(formData),
 		dataType: "json",
 		url: './EOFproduction/EofHeatSave?production_date='+production_date+
 				'&scrap_charge_at='+scrap_charge_at+'&tap_start_at='+tap_start_at+'&tap_close_at='+tap_close_at+'&hm_charge_at='+hm_charge_at+'&hm_ldl_version='+hm_ldl_version+'&plan_line_version='+plan_line_version,
 		success: function(data) {
 		    if(data.status == 'SUCCESS') 
 		    	{
 		    	if(trns_si_no==0){
 		    		saveT4EOFCrewDetails();
 		    		updateContainerLife();
 	 		    	getEOFCampaignLife();
 		    	}
 		    	$.messager.alert('EAF Heat Details Info',data.comment,'info');
 		    	
 		    	getT4RefreshHotmetalGrid();
 		    	GetT4UpdatedHeatList();
 		    	clearT4HeatHdrform();
 		    	$("#T4ladleDetailsId").show();
 		    	$('#T4ladleDetailsId').datagrid('reload');
 		    	//setT4HeatFormData();
 		    	}else {
 		    		$.messager.alert('EAF Heat Details Info',data.comment,'info');
 		    	}
 		  },
 		error:function(){      
 			$.messager.alert('Processing','Error while Processing Ajax...','error');
 		  }
 		}) 
		 }else{
			 $.messager.alert('Alert','Tap Start At should be less Tap Close At','info');
			 }
	 }else{
		 $.messager.alert('Info','Please Select Crew Details','info');
	 }
	 }
 }
 
 
 function getT4RefreshHotmetalGrid()
 {
 	$.ajax({
  		headers: { 
  		'Accept': 'application/json',
  		'Content-Type': 'application/json' 
  		},
  		type: 'GET',
  		//data: JSON.stringify(formData),
  		dataType: "json",
  		url: "./HMReceive/getHMDetailsbyStatus?STAGE=AVAILABLE&LADLE_STATUS=SEEOF,PARTC",
  		success: function(data) {
  			 $('#t4_EOF_production_tbl_id').datagrid('loadData', data);
  		  },
  		error:function(){      
  			$.messager.alert('Processing','Error while Processing Ajax...','error');
  		  }
  		})
 	
 }
 
 function clearT4HeatHdrform()
	{
	 $('#t4_heat_hdr_form_id').form('clear');
	}

 function RefreshladleDetails()
	{
		// $("#T4ladleDetailsId").show();
	 	//$('T4ladleDetailsId').datagrid('reload');
	 	getT4RefreshHotmetalGrid();
	} 
 
$('#t4_heat_id').combobox({

	onSelect: function(){
			setT4HeatFormData();
			$("#T4ladleDetailsId").hide();
			
},onChange: function(){
	 var t4_trns_si_no=$('#t4_heat_id').combobox('getValue');
	if(t4_trns_si_no=="0"){
		doDisableEnableButtons('t4_heat_hdr_btn_div_id','disable');
		$("#T4ladleDetailsId").hide();
	}else{
	 var sub_unit_id=$('#t4_unit').combobox('getValue');
	 if((sub_unit_id!=null && sub_unit_id!='')){
	//document.getElementById('t4_trns_si_no').value=0;
	//var t4_trns_si_no_txt=$('#t4_heat_id').combobox('getText');
	//alert("ok "+t4_trns_si_no);
	//var url6="./CommonPool/getComboList?col1=trns_si_no&col2=heat_id&classname=EofHeatDetails&status=1&wherecol=dispatch_to_unit=null and sub_unit_id="+sub_unit_id+" and record_status=";
   // getDropdownList(url6,'#t4_heat_id');
	if(!(t4_trns_si_no!=null && t4_trns_si_no!='')){
		
		
		 $("#T4ladleDetailsId").show();
		 
	  	document.getElementById('t4_trns_si_no').value='';
		
	  	document.getElementById('t4_sub_unit_id').value='';
	  	
		// $('#t4_heat_id').textbox('setText',''); 
		  
		 document.getElementById('t4_aim_psn_id').value=''; 
		 
		 $('#t4_heat_plan_id').numberbox('setValue','');
		 
		 $('#t4_aim_psn').textbox('setValue','');
		 
		 //$('#t4_unit').textbox('setValue','');
		 
		 $('#t4_heat_plan_line_no').textbox('setValue','');
		 
		 document.getElementById('t4_heat_line_id').value='';
		 
		 document.getElementById('t4_plan_heat_qty').value='';
		 
		  $('#t4_hm_ladle_no').textbox('setValue','');
		  
	     $('#t4_hm_wt').numberbox('setValue','');
		 
		 $('#t4_hm_temp').numberbox('setValue','');
		 
		 document.getElementById('t4_hmRecvId').value='';
		 
		 $('#t4_hm_charge_at').datetimebox('setText','');
		 
		$('#t4_scrap_charge_at').datetimebox('setText','');
		 
		 $('#t4_tap_start_at').datetimebox('setText','');
		 
		 $('#t4_tap_close_at').datetimebox('setText','');
		 
		document.getElementById('t4_hm_hmAvlblWt').value='';
		
		document.getElementById('t4_version').value='';
} 
	 }else{
		 $('#t4_heat_id').textbox('setValue','');
		 $('#t4_heat_id').textbox('setText','');
	$.messager.alert('Info','Please select SubUnit From Crew details window','info');
}
	 /* setT4HeatFormData();
	 $("#T4ladleDetailsId").hide();	  */
	}
}

});
function isSavedT4HeatData(){
	 var t4_trns_si_no=$('#t4_heat_id').combobox('getValue');
	 
	 if(t4_trns_si_no!=null && t4_trns_si_no!=''){
		 $.ajax({
				headers: { 
				'Accept': 'application/json',
				'Content-Type': 'application/json' 
				},
				type: 'GET',
				//data: JSON.stringify(formData),
				dataType: "json",
				url: './EOFproduction/getEOFHeatFormDtlsById?trns_si_no='+t4_trns_si_no,
				success: function(data) {
					if(data.status=="FAILURE"){
						doDisableEnableButtons('t4_heat_hdr_btn_div_id','disable');
						
						return false;
					}else{
						
						doDisableEnableButtons('t4_heat_hdr_btn_div_id','enable');
						return true;
					}
				}
		 })
	 }
}

/* function doDisableEnableButtons(divId,mode){
	var div="#"+divId;
	var i=1;
	$(div).find('a').each(function(e) {
		if(i>1){
			 $(this).linkbutton(mode);
		}
		i++;
	})
} */
	
 function setT4HeatFormData()
 {
	
	 var t4_trns_si_no=$('#t4_heat_id').combobox('getValue');
	
		if(t4_trns_si_no!=null && t4_trns_si_no!='' &&t4_trns_si_no!="0" ){
			doDisableEnableButtons('t4_heat_hdr_btn_div_id','enable');
			clearT4HeatHdrform();
		$.ajax({
		headers: { 
		'Accept': 'application/json',
		'Content-Type': 'application/json' 
		},
		type: 'GET',
		//data: JSON.stringify(formData),
		dataType: "json",
		url: './EOFproduction/getEOFHeatFormDtlsById?trns_si_no='+t4_trns_si_no,
		success: function(data) {
			
			 document.getElementById('t4_trns_si_no').value=data.trns_si_no;
			 document.getElementById('t4_heat_cnt').value=data.heat_counter; 
			 document.getElementById('t4_aim_psn_id').value=data.aim_psn;
			 document.getElementById('t4_heat_line_id').value=data.heat_plan_line_id;
			// document.getElementById('t4_plan_heat_qty').value=data.plan_wt;
			 document.getElementById('t4_hmRecvId').value=data.hmRecvId;
			 document.getElementById('t4_hm_hmAvlblWt').value=data.available_hm;
			 document.getElementById('t4_version').value=data.version;
			 document.getElementById('t4_heat_receieve_id').value=data.hmRecvId;     
			 document.getElementById('t4_sub_unit_id').value=data.sub_unit_id;
			 document.getElementById('t4_psn_route').value=data.psn_route;
			 
			 $('#t4_recDate').datetimebox('setText',formatDate(data.production_date));
			 
			 $('#t4_shift').combobox('setValue',data.production_shift);
			 
			 $('#t4_heat_id').combobox('setValue',data.trns_si_no);
			 
			 $('#t4_heat_id').textbox('setText',data.heat_id);			
			 
 			 $('#t4_unit').combobox('setValue',data.sub_unit_id);
			 
			 $('#t4_unit').textbox('setText',data.sub_unit);
			 		 
		     $('#t4_aim_psn').textbox('setValue',data.psn_no);
			 
			 $('#t4_heat_plan_id').textbox('setValue',data.heat_plan_id);
			 
 			 $('#t4_heat_plan_line_no').textbox('setValue',data.heat_plan_line_no);
			 
 			$('#t4_hm_ladle_no').textbox('setValue',data.hm_ladle_no);
			 
			 $('#t4_hm_wt').textbox('setValue',data.hm_wt);
			 
			 $('#t4_hm_temp').textbox('setValue',data.hm_temp);
			 
			 SetEventDates();
			 
			
		  },
		error:function(){ 
			$.messager.alert('Info','Heat Number does not exists','info');
		  }
		})
		}else{
			$.messager.alert('Info','Please Enter Heat Number','info');
		}
	 
 }
 
 function SetEventDates(){
	 var heat_no=$('#t4_heat_id').combobox('getText');
	 var heat_counter=document.getElementById('t4_heat_cnt').value
	 var unit_id=$('#t4_unit').combobox('getValue');
		
		if(heat_no!=null && heat_no!=''){
			//clearT4HeatHdrform();
		$.ajax({
		headers: { 
		'Accept': 'application/json',
		'Content-Type': 'application/json' 
		},
		type: 'GET',
		//data: JSON.stringify(formData),
		dataType: "json",
		url: './EOFproduction/getEOFHeatEventDetails?Heat_no='+heat_no+'&unit_id='+unit_id+'&heat_counter='+heat_counter,
		success: function(data) {
			 setT4CrewDetails();
			for(var i=0;i<data.length;i++){
				if(data[i].event_desc=='HM_CHARGE_START'){
					 $('#t4_hm_charge_at').datetimebox('setText',formatDate(data[i].event_date_time));
				}
				if(data[i].event_desc=='SCRAP_CHARGE_START'){
					$('#t4_scrap_charge_at').datetimebox('setText',formatDate(data[i].event_date_time));
				}
				if(data[i].event_desc=='TAP_START'){
					$('#t4_tap_start_at').datetimebox('setText',formatDate(data[i].event_date_time));
				}
				if(data[i].event_desc=='TAP_END'){
					$('#t4_tap_close_at').datetimebox('setText',formatDate(data[i].event_date_time));
				}
			}
			 //$('#t4_hm_charge_at').datetimebox('setText','');
			 
			//$('#t4_scrap_charge_at').datetimebox('setText','');
			 
			// $('#t4_tap_start_at').datetimebox('setText','');
			 
			// $('#t4_tap_close_at').datetimebox('setText','');
			 
		  },
		error:function(){ 
			$.messager.alert('Info','Heat Number does not exists','info');
		  }
		})
		}else{
			$.messager.alert('Info','Please Enter Heat Number','info');
		}
		 
 }
 
 function saveT4EOFCrewDetails(){
	 var shift_mgr=$('#t4_shiftMgr').combobox('getValue');
	 var eof_mgr=$('#t4_eofMgr').combobox('getValue');
	 //var tap_operator=$('#t4_tapOperator').combobox('getValue');
	 
	 
	 var heat_id=$('#t4_heat_id').textbox('getText');
	 var heat_cnt= 1;  //document.getElementById('t4_heat_cnt').value
	 var sub_unit_id=$('#t4_unit').combobox('getValue');
	 
	 //var user_role_id= shift_mgr+"@"+eof_mgr+"@"+tap_operator+"@";
	 var user_role_id= shift_mgr+"@"+eof_mgr+"@";
	 var formData = {"heat_id":heat_id,"heat_counter":heat_cnt,"sub_unit_id":sub_unit_id}; 
			 
			
	 	$.ajax({
	 		headers: { 
	 		'Accept': 'application/json',
	 		'Content-Type': 'application/json' 
	 		},
	 		type: 'POST',
	 		data: JSON.stringify(formData),
	 		dataType: "json",
	 		url: './EOFproduction/EofCrewDetailsSave?user_role_id='+user_role_id,
	 				
	 		success: function(data) {
	 		    if(data.status == 'SUCCESS') 
	 		    	{
	 		    	//$.messager.alert('EAF Heat Details Info',data.comment,'info');
	 		    	//refreshT2HeatPlan();
	 		    	}else {
	 		    	//	$.messager.alert('EAF Crew Details Info',data.comment,'info');
	 		    	}
	 		  },
	 		error:function(){      
	 			$.messager.alert('Processing','Error while Processing Ajax...','error');
	 		  }
	 		});
 }
 
 function setT4CrewDetails(){
	 var heat_no=$('#t4_heat_id').combobox('getText');
	 var heat_counter=document.getElementById('t4_heat_cnt').value
	 var unit_id=$('#t4_unit').combobox('getValue');
		if(heat_no!=null && heat_no!=''){
			//clearT4HeatHdrform();
		$.ajax({
		headers: { 
		'Accept': 'application/json',
		'Content-Type': 'application/json' 
		},
		type: 'GET',
		//data: JSON.stringify(formData),
		dataType: "json",
		url: './EOFproduction/getEOFCrewDtlsbyHeatNo?Heat_no='+heat_no+'&unit_id='+unit_id+'&heat_counter='+heat_counter,
		success: function(data) {
			for(var i=0;i<data.length;i++){
				if(data[i].userRoleMapMstrMdl.lookupMasterModel.lookup_code=='EAF_SHIFT_IN_CHARGE'){
					 $('#t4_shiftMgr').combobox('setText',data[i].userRoleMapMstrMdl.appUserAccountDetails.user_name);
					 $('#t4_shiftMgr').combobox('setValue',data[i].user_role_id);
				}
				if(data[i].userRoleMapMstrMdl.lookupMasterModel.lookup_code=='EAF_SHIFT_ENGINEER'){
					$('#t4_eofMgr').combobox('setText',data[i].userRoleMapMstrMdl.appUserAccountDetails.user_name);
					$('#t4_eofMgr').combobox('setValue',data[i].user_role_id);
				}
				/*
				if(data[i].userRoleMapMstrMdl.lookupMasterModel.lookup_code=='EAF_TAP_OPERATOR'){
					$('#t4_tapOperator').combobox('setText',data[i].userRoleMapMstrMdl.appUserAccountDetails.user_name);
					$('#t4_tapOperator').combobox('setValue',data[i].user_role_id);
				}
				*/
			}
		  },
		error:function(){ 
			$.messager.alert('Info','Heat Number does not exists','info');
		  }
		});
		}else{
			$.messager.alert('Info','Please Enter Heat Number','info');
		}
 }


 function DeleteLadleDetails(){
	 $.messager.confirm('Confirm', 'Do you want to remove the selected heat ?', function(r){
			if (r){	 
					 var row = $('#t4_EOF_production_tbl_id').datagrid('getSelected');
					 if (row){
					$.ajax({
				    		headers: { 
				    		'Accept': 'application/json',
				    		'Content-Type': 'application/json' 
				    		},
				    		type: 'POST',
				    		//data: JSON.stringify(formData),
				    		dataType: "json",
				    		url: './EOFproduction/eofreceivehmStatusUpdate?hmRecvId='+row.hmRecvId,
				    		success: function(data) {
				    		    if(data.status == 'SUCCESS') 
				    		    	{
				    		    	$.messager.alert('Hot Metal Receive Info',data.comment,'info');
				    		    	cancelT1HMReceive();
				    		    	getT4RefreshHotmetalGrid();
				    		    	}else {
				    		    					    		    		
				    		    		$.messager.alert('Hot Metal Receive Info',data.comment,'info');
				    		    	}
				    		  },
				    		error:function(){      
				    			$.messager.alert('Processing','Error while Processing Ajax...','error');
				    		  }
				    		}) 
						}else{
				     	$.messager.alert('Information','Please Select Record...!','info');
				     }
			}});
}
 
 
 function getT4MixLadleWindow()
 {
	var sub_unit= $('#t4_unit').combobox('getText');
	if(sub_unit!=''){
	 $('#t4_HM_MixDetailsView').dialog({modal:true,cache: true});
	 $('#t4_HM_MixDetailsView').dialog('open').dialog('center').dialog('setTitle','HotMetal Mix Details');
	 var url7="./CommonPool/getComboList?col1=hm_ladle_no&col2=hm_ladle_no&classname=HmLadleMasterModel&status=1&wherecol=hm_ladle_status='AVAILABLE' and record_status=";
	getDropdownList(url7,'#t4n_hmLadleNo');
	 getT4RefreshHotmetalMixGrid();
	 resetT4HMMixladleDetails();
	 $('#t5_unit').textbox('setValue',sub_unit);
	 $("#t5_unit").css({"background-color": "red"});
	}else{
		$.messager.alert('Info','Please Select Unit in Crew Details','info');
	}
 }
 /*
 $('#t4_HMMix_tbl_id').edatagrid({
     onBeginEdit:function(index,row){
    	var chkrow= $('#t4_HMMix_tbl_id').datagrid('getChecked');
    	var editors = $('#t4_HMMix_tbl_id').datagrid('getEditors', index);
    	var isDouble=$(editors[0].target);
    	var mix=$(editors[1].target);
    	
    	var avlqty=row.hmAvlblWt;
    	mix.textbox({
    		onChange:function(){
    			var mixqty=mix.textbox('getValue');
  				 $('#t4n_hmLadleNo').combobox('setText',row.hmLadleNo);
    		}
    	})
    	isDouble.combobox({
    		onSelect: function(value){
    			if(value.txtvalue=="YES"){
    				row.mixqty=row.hmAvlblWt;
    				mix.textbox('setValue',row.hmAvlblWt)
    			}
    			else{
    				$.ajax({
    					headers : {
    						'Accept' : 'application/json',
    						'Content-Type' : 'application/json'
    					},
    					type : 'GET',
    					// data: JSON.stringify(formData),
    					dataType : "json",
    					url : "./WBIntf/getWBDoubleLadle?laddleNo="+row.hmLadleNo+"&castNo="+row.castNo,
    					success : function(data) {
    						row.mixqty=data;
    	    				mix.textbox('setValue',data)
    					},
    					error : function() {
    						$.messager.alert('Processing', 'Error while getting Sample No',
    								'error');
    					}
    				});
    			}
    		}
    	})    	
     }
})  
*/
 function validateT4MixLadles()
 {
	 var rows=$('#t4_HMMix_tbl_id').datagrid('getChecked');
	 
	 var allrows=$('#t4_HMMix_tbl_id').datagrid('getRows');
	 
	 if(rows.length>=2)
		 {
	  for(var i=0;i<allrows.length;i++){
		 $('#t4_HMMix_tbl_id').datagrid('endEdit', i);
		 }
	
	 var retcnt='0';
	
	 if(rows){
		
    		for(var i=0;i<rows.length;i++){
    			if(!(rows[i].mixqty!='' && rows[i].mixqty!=null))
    				{
    				$.messager.alert('Information','Please Enter valid Mix Quanity...!','info');
    				//$('#t4_HMMix_tbl_id').datagrid('beginEdit', i);
    				retcnt=1;
    				return false;
    				}else{
    					retcnt=0;
    				}
    		}
    		
    		if(retcnt==1)
    			{
    			return false;
    			}else{
    				return true;
    			}
    		}
		 }else{
			 $.messager.alert('Information','Please Select Minimum 2 Ladles...!','info');
			 return false;
		 }
 }
 

 function getT4MixSelectedLadles()
 {
	 if(validateT4MixLadles()){
		 
	 var rows=$('#t4_HMMix_tbl_id').datagrid('getChecked');
	 
	 document.getElementById('t4n_allids').value='0';
	 
	 var recvid_avlwt_mixwt_arry = '';
	 
	 	if(rows){
    		
	 		var wt='0',c='0',mn='0',s='0',p='0',si='0',ti='0',t4remarks='',hmtemp='0',recvid='',avalwt='',mixwt='';
    		
    		for(var i=0;i<rows.length;i++){
    			if(i==0)
    				{
    				t4remarks='MIX_'+rows[i].castNo+'_'+rows[i].hmLadleNo;     //rows[i].hmLadleNo;
    				}else{
    					t4remarks=t4remarks+' and '+rows[i].castNo+'_'+rows[i].hmLadleNo;    //rows[i].hmLadleNo;
    				}
    			recvid_avlwt_mixwt_arry += rows[i].hmRecvId+'@'+(rows[i].hmAvlblWt==null?0:rows[i].hmAvlblWt)+'@'+rows[i].mixqty+'@'+rows[i].hmLadleNo+'IDS';
    			
    			
    			c=parseFloat(c)+(parseFloat(rows[i].hmLadleC)*parseFloat(rows[i].mixqty));
    			
    			mn=parseFloat(mn)+(parseFloat(rows[i].hmLadleMn)*parseFloat(rows[i].mixqty));
    			
    			s=parseFloat(s)+(parseFloat(rows[i].hmLadleS)*parseFloat(rows[i].mixqty));
    			
    			p=parseFloat(p)+(parseFloat(rows[i].hmLadleP)*parseFloat(rows[i].mixqty));
    			
    			si=parseFloat(si)+(parseFloat(rows[i].hmLadleSi)*parseFloat(rows[i].mixqty));
    			
    			ti=parseFloat(ti)+(parseFloat(rows[i].hmLadleTi)*parseFloat(rows[i].mixqty));
    			
    			wt=parseFloat(wt)+parseFloat(rows[i].mixqty);
    			
    			hmtemp=parseFloat(hmtemp)+(parseFloat(rows[i].mixqty)*parseFloat(rows[i].hmLadleTempEof));
    			
    		}
    		
    		
   
    	//	alert(recvid_avlwt_mixwt_arry);
  	 
    document.getElementById('t4n_allids').value=recvid_avlwt_mixwt_arry;
    
    setT4HotmetalMixCastNo();
    
    $('#t4n_hmLadleC').numberbox('setValue',parseFloat(c)/parseFloat(wt));
    
    $('#t4n_hmLadleMn').numberbox('setValue',parseFloat(mn)/parseFloat(wt));
    
    $('#t4n_hmLadleS').numberbox('setValue',parseFloat(s)/parseFloat(wt));
    
    $('#t4n_hmLadleP').numberbox('setValue',parseFloat(p)/parseFloat(wt));
    
    $('#t4n_hmLadleSi').numberbox('setValue',parseFloat(si)/parseFloat(wt));
    
    $('#t4n_hmLadleTi').numberbox('setValue',parseFloat(ti)/parseFloat(wt));
    
    $('#t4n_hmLadleNetWt').numberbox('setValue',wt);
    
    $('#t4n_remarks').textbox('setValue',t4remarks);
    
    $('#t4n_hmLadleTempBf').numberbox('setValue',parseFloat(hmtemp)/parseFloat(wt));
    
    formHighlightValidation();
    		
 }
	 }
 }
 

 function resetT4HMMixladleDetails()
 {
	 document.getElementById('t4n_allids').value='0';
	 
	 	$('#t4n_hmLadleC').numberbox('setValue','');
	    
	    $('#t4n_hmLadleMn').numberbox('setValue','');
	    
	    $('#t4n_hmLadleS').numberbox('setValue','');
	    
	    $('#t4n_hmLadleP').numberbox('setValue','');
	    
	    $('#t4n_hmLadleSi').numberbox('setValue','');
	    
	    $('#t4n_hmLadleTi').numberbox('setValue','');
	    
	    $('#t4n_hmLadleNetWt').numberbox('setValue','');
	    
	    $('#t4n_castNo').textbox('setValue','');
	    
	    $('#t4n_remarks').textbox('setValue','');
	    
	    $('#t4n_hmLadleTempBf').numberbox('setValue','');
	    
	    $('#t4n_hmLadleGrossWt').numberbox('setValue','');
	    
	    $('#t4n_hmLadleTempEof').numberbox('setValue','');
	    
	    $('#t4n_hmLadleNo').textbox('setValue','');
	    
		$('#t4n_hmLadleTareWt').numberbox('setValue','');
		
		$('#t4n_hmLadleGrossWt').numberbox('setValue','');
		
 }
 
 $('#t4_HMMix_tbl_id').edatagrid({
	 onEndEdit: function(index,field,changes){
			$(this).datagrid('beginEdit', index);
			
		}
	});
 
 $('#t4_HMMix_tbl_id').edatagrid({
	 onCheck: function(index,row){
		
		 $(this).datagrid('beginEdit', index);
		 
		 
		},onUncheck:function(index,row){
			 $(this).datagrid('cancelEdit', index);
		}
	});
 
 $('#t4n_hmRecvDate').datetimebox({
		
	    value: (formatDate(new Date())) 
	   
	});
 
 $('#t4n_hmLadleProdDt').datetimebox({
		
	    value: (formatDate(new Date())) 
	   
	});
 
 function getT4RefreshHotmetalMixGrid()
 {
 	$.ajax({
  		headers: { 
  		'Accept': 'application/json',
  		'Content-Type': 'application/json' 
  		},
  		type: 'GET',
  		//data: JSON.stringify(formData),
  		dataType: "json",
  		url: "./HMReceive/getHMDetailsbyStatus?STAGE=AVAILABLE&LADLE_STATUS=SEEOF,PARTC",
  		success: function(data) {
  			 $('#t4_HMMix_tbl_id').datagrid('loadData', data);
  			resetT4HMMixladleDetails();
  		  },
  		error:function(){      
  			$.messager.alert('Processing','Error while Processing Ajax...','error');
  		  }
  		})
 	
 }
 
 function validateT4HmMixform(){
	
     return $('#t4n_HMMix_form_div_id').form('validate');
 }
 
function tareValidation(){
	 var tare_wt= $('#t4n_hmLadleTareWt').numberbox('getValue');
	 var gross_wt= $('#t4n_hmLadleGrossWt').numberbox('getValue');
	 
	 
	 if(!(parseFloat(tare_wt)>0)){
	 $.messager.alert('Information','Please Enter Tare Weight','info');
	 $('#t4n_hmLadleGrossWt').numberbox('setValue','');
	 }
	
 }
 
 function grossNetWtValidation(){
	
	 var retFlag=false;
	 var mix_qty= $('#t4n_hmLadleNetWt').numberbox('getValue');
	 var tare_wt= $('#t4n_hmLadleTareWt').numberbox('getValue');
	 var gross_wt= $('#t4n_hmLadleGrossWt').numberbox('getValue');

/* 	 if(tare_wt == null || tare_wt == ''){
		 $.messager.alert('Alert','please enter Tare weight...!','info');
		 return retFlag;
	} */
	 if(gross_wt == null || gross_wt == ''){
		 $.messager.alert('Alert','please enter Gross weight...!','info');
		 return retFlag;
	}
	 var tot=parseFloat(gross_wt) - parseFloat(tare_wt)
	
	 /* if(!(parseFloat(tot) == parseFloat(mix_qty))){
	 $.messager.alert('Information','Please Change Gross Wt or Tare Wt to match Net Wt...!','info');
	 return retFlag;
	 }else{
		 
	 } */
	 retFlag=true; 
	return retFlag;
 }
 
 
   
 function formHighlightValidation(){
	 var temp_bf= $('#t4n_hmLadleTempBf').numberbox('getValue');
	 var temp_eof= $('#t4n_hmLadleTempEof').numberbox('getValue');
	 var c= $('#t4n_hmLadleC').numberbox('getValue');
	 var Mn= $('#t4n_hmLadleMn').numberbox('getValue');
	 var S= $('#t4n_hmLadleS').numberbox('getValue');
	 var P= $('#t4n_hmLadleP').numberbox('getValue');
	 var Si= $('#t4n_hmLadleSi').numberbox('getValue');
	 var Ti= $('#t4n_hmLadleTi').numberbox('getValue');
	 
	 if(!(temp_bf>1400 && temp_bf<1600)){
		 $('#t4n_hmLadleTempBf').numberbox('textbox').css({"background-color": "tan", "font-weight": "bold"});
	 }
	 if(!(temp_eof>1400 && temp_eof<1600)){
		 $('#t4n_hmLadleTempEof').numberbox('textbox').css({"background-color": "tan", "font-weight": "bold"});
	 }
	 if(c!='' && c>1){
		 $('#t4n_hmLadleC').numberbox('textbox').css({"background-color": "tan", "font-weight": "bold"});
	 }
	  if(Mn!=''&& Mn>1){
		  $('#t4n_hmLadleMn').numberbox('textbox').css({"background-color": "tan", "font-weight": "bold"});
	 }
	 if(S!='' && S>1){
		 $('#t4n_hmLadleS').numberbox('textbox').css({"background-color": "tan", "font-weight": "bold"});
	 }
	 if(P!='' && P>1){
		 $('#t4n_hmLadleP').numberbox('textbox').css({"background-color": "tan", "font-weight": "bold"});
	 }
	 if(Si!='' && Si>1){
		 $('#t4n_hmLadleSi').numberbox('textbox').css({"background-color": "tan", "font-weight": "bold"});
	 }
	if(Ti!='' && Ti>1 ){
		$('#t4n_hmLadleTi').numberbox('textbox').css({"background-color": "tan", "font-weight": "bold"});
	} 
 }
 
 
 
 function saveT4HMSubFunction()
 {
	 if(validateT4HmMixform()){
		 var t4n_allids=document.getElementById('t4n_allids').value;
		 
		  var hmLadleNo=$('#t4n_hmLadleNo').textbox('getText');
		  
			var castNo=$('#t4n_castNo').textbox('getText');
			
			var hmSource =1;
			
			var hmLadleTareWt=0;//$('#t4n_hmLadleTareWt').numberbox('getValue');
			
			var hmLadleGrossWt=$('#t4n_hmLadleGrossWt').numberbox('getValue');
			
			var hmLadleNetWt=0//$('#t4n_hmLadleNetWt').numberbox('getValue');
			
			var hmAvlblWt=0;//$('#t4n_hmLadleNetWt').numberbox('getValue');
			
			var hmLadleProdDt=$('#t4n_hmLadleProdDt').datetimebox('getText');
			
			var hmLadleTempBf=$('#t4n_hmLadleTempBf').numberbox('getValue');
			var hmLadleTempEof=$('#t4n_hmLadleTempEof').numberbox('getValue');
			var hmRecvDate=$('#t4n_hmRecvDate').datetimebox('getText');
			
			var hmLadleC=$('#t4n_hmLadleC').numberbox('getValue');
			var hmLadleMn=$('#t4n_hmLadleMn').numberbox('getValue');
			var hmLadleP=$('#t4n_hmLadleP').numberbox('getValue');
			var hmLadleS=$('#t4n_hmLadleS').numberbox('getValue');
			var hmLadleSi=$('#t4n_hmLadleSi').numberbox('getValue');
			var hmLadleTi=$('#t4n_hmLadleTi').numberbox('getValue');
			var remarks=$('#t4n_remarks').textbox('getText');
			
			var hmRecvId=0;//document.getElementById('t1_hmRecvId').value;
			
			var dataSource=document.getElementById('t4n_dataSource').value;//$('#t1_dataSource').textbox('getText');
			
			var hmLadleStageStatus=document.getElementById('t4n_hmLadleStageStatus').value;
			var hmLadleStatus=document.getElementById('t4n_hmLadleStatus').value;
			var version=document.getElementById('t4n_version').value;
	     	
	var formData = {"hmLadleNo":hmLadleNo,
	     				"castNo":castNo,"hmSource":hmSource,
	     				"hmLadleTareWt":hmLadleTareWt,"hmLadleGrossWt":hmLadleGrossWt,"hmLadleNetWt":hmLadleNetWt,"hmAvlblWt":hmAvlblWt,"hmLadleProdDt_s":hmLadleProdDt
	     				,"hmLadleTempBf":hmLadleTempBf,"hmLadleTempEof":hmLadleTempEof,"hmRecvDate_s":hmRecvDate,"hmLadleC":hmLadleC,"hmLadleMn":hmLadleMn
	     				,"hmLadleP":hmLadleP,"hmLadleS":hmLadleS,"hmLadleSi":hmLadleSi,"hmLadleTi":hmLadleTi,"remarks":remarks,"hmRecvId":hmRecvId
	     				 ,"hmLadleStatus":hmLadleStatus,"dataSource":dataSource,"hmLadleStageStatus":hmLadleStageStatus,"version":version };
	 
			
	     	$.ajax({
	     		headers: { 
	     		'Accept': 'application/json',
	     		'Content-Type': 'application/json' 
	     		},
	     		type: 'POST',
	     		data: JSON.stringify(formData),
	     		dataType: "json",
	     		url: './HMReceive/HMMixSave?t4n_allids='+t4n_allids,
	     		success: function(data) {
	     		    if(data.status == 'SUCCESS') 
	     		    	{
	     		    	$.messager.alert('Hot Metal Receive Info',data.comment,'info');
	     		    	getT4RefreshHotmetalMixGrid();
	     		    	getT4RefreshHotmetalGrid();
	     		    	}else {
	     		    		$.messager.alert('Hot Metal Receive Info',data.comment,'info');
	     		    	}
	     		  },
	     		error:function(){      
	     			$.messager.alert('Processing','Error while Processing Ajax...','error');
	     		  }
	     		})
	 	}
 }
 
 function saveT4HMMixDtls()
 {
	 var flag=grossNetWtValidation();
	 if(flag){
	 var sub_unit= $('#t5_unit').combobox('getText');
	 var mix_qty= $('#t4n_hmLadleNetWt').numberbox('getValue');
	
	 if(sub_unit=='EAF1' && parseFloat(mix_qty)<100){
	
		 $.messager.confirm('Confirm','Net Quantity less than 100 ton.. Do you want Process ?',function(r){
				if (r){
					saveT4HMSubFunction();
				}
		 });
	 }else if(sub_unit=='EAF2' && parseFloat(mix_qty)<100){
		 $.messager.confirm('Confirm','Net Quantity less than 100 ton.. Do you want Process ?',function(r){
				if (r){
					saveT4HMSubFunction();
				}
		 });
	 }else
		 {
		 $.messager.alert('Information','Net Weight '+mix_qty+' should be less than equal to capactiy of the Sub Unit','info');
		 }
 }
	
 }
 function setT4HotmetalMixCastNo()
 {
 	$.ajax({
  		headers: { 
  		'Accept': 'application/json',
  		'Content-Type': 'application/json' 
  		},
  		type: 'GET',
  		//data: JSON.stringify(formData),
  		dataType: "json",
  		url: './EOFproduction/EofLadleMix',
  		success: function(data) {
  			$('#t4n_castNo').textbox('setText',data.comment);
  		  },
  		error:function(){      
  			$.messager.alert('Processing','Error while Processing Ajax...','error');
  		  }
  		}) 	
 }
 
 function scrapChargeAtValidation(){
	 var scrap_charge_at=$('#t4_scrap_charge_at').datetimebox('getText');
	 var hm_charge_at=$('#t4_hm_charge_at').datetimebox('getValue');
	
	 var hm_charge_date= commonDateISOformat(hm_charge_at);
	 var scrap_charge_date=  commonDateISOformat(scrap_charge_at);
	 if(!(scrap_charge_date.getTime() > hm_charge_date.getTime())){
		 $.messager.alert('Warning','Scrap charge At should be greater than HM Charge At','warning');
		 }
 	}
 
 function tapStartAtValidation(){
	 var hm_charge_at=$('#t4_hm_charge_at').datetimebox('getText');
	 var scrap_charge_at=$('#t4_scrap_charge_at').datetimebox('getText');
	 var tap_start_at=$('#t4_tap_start_at').datetimebox('getValue');
	 
	 var hm_charge_date= commonDateISOformat(hm_charge_at);
	 var scrap_charge_date=  commonDateISOformat(scrap_charge_at);
	 var tap_start_date=  commonDateISOformat(tap_start_at);
	 
	 if(!(scrap_charge_date.getTime() < tap_start_date.getTime())){
		 $.messager.alert('Warning','Scrap Charge At should be less than Tap Start At','warning');
		 }
	 if(!(hm_charge_date.getTime() < tap_start_date.getTime())){
		 $.messager.alert('Warning','HM Charge At should be less than Tap Start At','warning');
		 }
 }
 
 function tapCloseAtValidation(){
	 var hm_charge_at=$('#t4_hm_charge_at').datetimebox('getText');
	 var scrap_charge_at=$('#t4_scrap_charge_at').datetimebox('getText');
	 var tap_start_at=$('#t4_tap_start_at').datetimebox('getText');
	 var tap_close_at=$('#t4_tap_close_at').datetimebox('getValue');
	 
	 var hm_charge_date= commonDateISOformat(hm_charge_at);
	 var scrap_charge_date=  commonDateISOformat(scrap_charge_at);
	 var tap_start_date=  commonDateISOformat(tap_start_at);
	 var tap_close_date=commonDateISOformat(tap_close_at);
	 
	
	 if(!(scrap_charge_date.getTime() < tap_close_date.getTime())){
		 $.messager.alert('Warning','Scrap Charge At should be less than Tap Close At','warning');
		 }
	 if(!(hm_charge_date.getTime() < tap_close_date.getTime())){
		 $.messager.alert('Warning','HM Charge At should be less than Tap Close At','warning');
		 }
	}
  	function gridT4imgChange()
	 {
 		var images = ["warningSign.jpg","success.jpg","warning.jpg"];
		
		 $("#dvImage").css("background-image", "");
	 }
 	
 	function gridT4LadleValidation(){
 		var rows=$('#t4_EOF_production_tbl_id').datagrid('getRows');
 		var sys_date=new Date();
 		var txtmsg="";
 		var msg="";
 		if(rows.length>8){
 			msg= '<p style="color: red;"><b> More than 8 Ladles pending for process in Hot metal receive </b><p> <br>';
 			txtmsg=txtmsg+ msg;
 		}
 		for(i=0;i<rows.length;i++){
 		
 		if(((sys_date-rows[i].hmLadleProdDt)/(1000*60*60))>4){
 			
 			msg= '<b>('+parseInt(i+1)+') Cast'+rows[i].castNo + 'and ladle'+rows[i].hmLadleNo+' waiting for process more than 4 hours </b> <br>';
 	         txtmsg=txtmsg+ msg;
 		}
 		
 	}
 	return txtmsg;
 	} 
 	
 	function gridT4LadleValidationBreak(){
 		var rows=$('#t4_EOF_production_tbl_id').datagrid('getRows');
 		var sys_date=new Date();
 		var txtmsg="";
 		var msg="";
 		for(i=0;i<rows.length;i++){
 			if(rows.length>8){
 	 			msg= '<p style="color: red;"><b> More than 8 Ladles pending for process in Hot metal receive </b><p> <br>';
 	 			txtmsg=txtmsg+ msg;
 	 		}
 			
 		if(((sys_date-rows[i].hmLadleProdDt)/(1000*60*60))>4){
 			
 			msg= '<b>('+parseInt(i+1)+') Cast'+rows[i].castNo + 'and ladle'+rows[i].hmLadleNo+' waiting for process more than 4 hours </b> <br>';
 	         txtmsg=txtmsg+ msg;
 		}
 		break;
 		
 	  }
 	  return txtmsg;
 	} 

 	/*$('#t7_eof_Chemistry_tbl_id').edatagrid({
		onLoadSuccess: function(data){
			$('#t7_ok_chem_btn_id').linkbutton('enable');
	 		$('#t7_save_chem_btn_id').linkbutton('disable');	
		},
  	});
 	*/
 	function getEOFCampaignLife()
 	 {
		var sub_unit_id=$('#t4_unit').combobox('getValue');
 		$('#eofCamp').textbox('setValue',null);
 		$('#container_life').textbox('setValue',null);
 		$('#furnaceLife').textbox('setValue',null);
 		$('#tapHoleLife').textbox('setValue',null);
 		$('#steelLaunderLife').textbox('setValue',null);
 		$('#hmLaunderLife').textbox('setValue',null); 		 		
 		
 	 	$.ajax({
 	  		headers: { 
 	  		'Accept': 'application/json',
 	  		'Content-Type': 'application/json' 
 	  		},
 	  		type: 'GET',
 	  		//data: JSON.stringify(formData),
 	  		dataType: "json",
 	  		url: "./heatProcessEvent/getEOFCampaignLife?subUnit="+sub_unit_id,
 	  		success: function(data) {
				for(var i=0;i<data.length;i++){ 
					$('#eofCamp').textbox('setValue',data[i].campaign_number);
					
 	  				if(data[i].lifeParameterName=="WALL LIFE"){
 	 	  				$('#container_life').textbox('setValue',data[i].total_heats);
 	 	  			}
 	  				else if(data[i].lifeParameterName=="BOTTOM LIFE"){
 	 	  				$('#furnaceLife').textbox('setValue',data[i].total_heats);
 	 	  			}
 	  				else if(data[i].lifeParameterName=="EBT LIFE"){
	 	  				$('#tapHoleLife').textbox('setValue',data[i].total_heats);
	 	  			}
 	  				else if(data[i].lifeParameterName=="DELTA LIFE"){
 	  					$('#steelLaunderLife').textbox('setValue',data[i].total_heats);
 	  				}
 	  				else if(data[i].lifeParameterName=="HM LAUNDER LIFE"){
 	  					$('#hmLaunderLife').textbox('setValue',data[i].total_heats);
 	  				}
 	  			}
			},
 	  		error:function(){      
 	  			$.messager.alert('Processing','Error while Processing Ajax...','error');
 	  		}
 	  		})
 	  		
 	  		/*getting Container life which is @ running status against the unit ID....
 	  			$.ajax({
 	  		headers: { 
 	  		'Accept': 'application/json',
 	  		'Content-Type': 'application/json' 
 	  		},
 	  		type: 'POST',
 	  		//data: JSON.stringify(formData),
 	  		dataType: "json",
 	  		url: "./MstrContainerLife/getCurrentContainer?Unit="+sub_unit_id,
 	  		success: function(data) {
 	  			$('#container_life').textbox('setValue',data[0].container_life);
 	  		  },
 	  		error:function(){      
 	  			$.messager.alert('Processing','Error while Processing Ajax...','error');
 	  		  }
 	  		})
 	 	*/
 	 }
 	 
 	 /*function resetT11PartLife()
 	 {
 		var row = $('#t11_parts_life_det_tbl_id').datagrid('getSelected');
        var steel_ladle=$('#t11_steel_ladle').combobox('getValue');
 		
 		if ( steel_ladle == '' ){
 			$.messager.alert('Part Life','Select steel ladle before part reset');
 		}
 		else{
        var steel_ladle=$('#t11_steel_ladle').combobox('getValue');
 		
 		if ( steel_ladle == '' ){
 			$.messager.alert('Part Life','Select steel ladle before part reset');
 		}
 		else{
		if (row){
 		
	 	$.ajax({
	 		headers: { 
	 		'Accept': 'application/json',
	 		'Content-Type': 'application/json' 
	 		},
	 		type: 'GET',
	 		//data: JSON.stringify(formData),
	 		dataType: "json",
	 		url:'./EOFproduction/updatePartLifeBySiNo?ladleLifeSiNo='+row.ladle_life_sl_no,
	 		success: function(data) {
	 			 if(data.status == 'SUCCESS') 
    		    	{
	 				$.messager.alert('Part Life Counter',data.comment,'info');
	 			//getLaddleLifeDet();
    		    	}else{
    		    		$.messager.alert('Part Life Counter',data.comment,'info');
    		    	}
	 		  },
	 		error:function(){      
	 			$.messager.alert('Processing','Error while Processing Ajax...','error');
	 		  }
	 		})
	 		}else{
            	$.messager.alert('Information','Please Select Record...!','info');
            }
 		}
 	 }
 	 }*/
 	 
 	///PSN Report Display for New PSNs wating for Review
 	
 	function executePrintPsn()
 	{
 		 printPsn("t10");
 	}
 	
 	function executeExportPsnDoc()
 	{
 		exportPsnDoc("t10");
 	}
 	
 	function executeReportDisplay()
 	{
 		<%  
 	    String url=request.getRequestURL().toString(); // URL base page      
         String imageUrl=url.substring(0,url.indexOf(request.getRequestURI()))+request.getContextPath()+"/images/jsw.gif"; // image absolute url
 		 %>
 		var s="<%=imageUrl%>"; 
 		reportDisplay("t10",s);
 	}
 	
 	//-----CONTAINER LIFE EOF FUNCTIONS START----------
 	function updateContainerLife(){
 		var sub_unit_id=$('#t4_unit').combobox('getValue');
 		$.ajax({
			headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json'
			},
			type : 'POST',
			//data : JSON.stringify(formData),
			dataType : "json",
			url : './MstrContainerLife/updateContainerLife?Unit='+sub_unit_id,
			success : function(data) {
				console.log("updateContainerLife");
			},
			error : function() {
				$.messager.alert('Processing',
						'Error while Processing updateContainerLife()...', 'error');
			}
		}) 
 		
 	}
 	function container_trns_dtls(){
 		var sub_unit_id=$('#t4_unit').combobox('getValue');
 		
 		$.ajax({
			headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json'
			},
			type : 'POST',
			//data : JSON.stringify(formData),
			dataType : "json",
			url : './MstrContainerLife/getTrnsNoContainer?Unit='+sub_unit_id,
			success : function(data) {
				document.getElementById('t4_container_trns_id').value=data.trns_si_no;
				document.getElementById('t4_container_life').value=data.containerMstrMdl.container_life;
				
			},
			error : function() {
				$.messager.alert('Processing',
						'Error while Processing updateContainerLife()...', 'error');
			}
		}) 
 		
 	}
 	//Cancel button action
	function cancelT12ContainerChange() {
		$('#t12_mstr_eof_container_life_form_div_id').dialog('close');
	}
 	
 	function saveContainerLife(){
 		if($('#t17_mstr_lkp_form_id').form('validate')){
 			var container_id = $('#t12_container_id').val();
			var container_name = $('#t12_container_name').textbox('getText');
			var supplier_id = $('#t12_supplier_id').combobox('getValue');
			var container_life = $('#t12_container_life').numberbox('getValue');
			var status = $('#t12_status').textbox('getValue');
			var unit = $('#t12_unit').combobox('getValue');
			var prev_container_id=$('#t12_current_container_id').val();
			var prev_container_name=$('#t12_running_container_name').textbox('getText');
			var prev_container_status=$('#t12_running_container_status').textbox('getValue');
			var prev_supplier_id = $('#t12_running_supplier_id').combobox('getValue');
			var prev_container_life = $('#t12_running_container_life').numberbox('getValue');
			var prev_unit = $('#t12_running_unit').combobox('getValue'); 

			var formData = [
				
					{
				"container_id" : container_id,
				"container_name" : container_name,
				"supplier_id" : supplier_id,
				"container_life" : container_life,
				"status" : status,
				"unit" : unit},
				{
				"container_id":prev_container_id,
				"container_name":prev_container_name,
				"status":prev_container_status,
				"supplier_id":prev_supplier_id,
				"container_life":prev_container_life,
				"unit":prev_unit
				}
 		];

			if(prev_container_id!=null || prev_container_id!=''){
		 	$.ajax({
				headers : {
					'Accept' : 'application/json',
					'Content-Type' : 'application/json'
				},
				type : 'POST',
				data : JSON.stringify(formData),
				dataType : "json",
				url : './MstrContainerLife/SaveOrUpdate',
				success : function(data) {
					if (data.status == 'SUCCESS') {
						$.messager.alert('Container Info', data.comment,
								'info');
						$('#t17_eof_container_change').dialog(
								'close');
						$('#t12_mstr_eof_container_life_tbl_id').datagrid(
								'reload');
						$('#t12_mstr_eof_container_life_form_div_id').dialog(
						'close');

					} else {
						$.messager.alert('Lookup Details Info', data.comment,
								'info');
						$('#t17_eof_container_change').dialog(
								'close');
						$('#t12_mstr_eof_container_life_tbl_id').datagrid(
								'reload');
						$('#t12_mstr_eof_container_life_form_div_id').dialog(
						'close');
					}
				},
				error : function() {
					$.messager.alert('Processing',
							'Error while Processing Ajax...', 'error');
				}
			}) 
			}
			else{
				$.messager.alert('Processing',
						'Container Status can`t be same!', 'error');
			}
 		}
 		else{
 			var container_id = $('#t12_container_id').val();
			var container_name = $('#t12_container_name').textbox('getText');
			var supplier_id = $('#t12_supplier_id').combobox('getValue');
			var container_life = $('#t12_container_life').numberbox('getValue');
			var status = $('#t12_status').textbox('getValue');
			var unit = $('#t12_unit').combobox('getValue');
			

			var formData = [
				
					{
				"container_id" : container_id,
				"container_name" : container_name,
				"supplier_id" : supplier_id,
				"container_life" : container_life,
				"status" : status,
				"unit" : unit}
 		];
			
		 	$.ajax({
				headers : {
					'Accept' : 'application/json',
					'Content-Type' : 'application/json'
				},
				type : 'POST',
				data : JSON.stringify(formData),
				dataType : "json",
				url : './MstrContainerLife/SaveOrUpdate',
				success : function(data) {
					if (data.status == 'SUCCESS') {
						$.messager.alert('Lookup Details Info', data.comment,
								'info');
						$('#t17_eof_container_change').dialog(
								'close');
						
						$('#t12_mstr_eof_container_life_tbl_id').datagrid(
								'reload');
						//reloading container master table
						$('#m43_mstr_eof_container_life_tbl_id').datagrid(
						'reload');
						$('#t12_mstr_eof_container_life_form_div_id').dialog(
						'close');

					} else {
						$.messager.alert('Lookup Details Info', data.comment,
								'info');
						$('#t17_eof_container_change').dialog(
								'close');
						$('#t12_mstr_eof_container_life_tbl_id').datagrid(
								'reload');
						$('#t12_mstr_eof_container_life_form_div_id').dialog(
						'close');
					}
				},
				error : function() {
					$.messager.alert('Processing',
							'Error while Save/Update Container...', 'error');
				}
			})
 		
 	}
 	}
 	//-----CONTAINER LIFE EOF FUNCTIONS END----------
 	//-------------Level 2 server accessing-------------- 

 	function level2_server(){
 		var connection_check=0;// 0-Connected 1-Not Connected
 		var heat_counter = document.getElementById('t4_heat_cnt').value;
 		var analysis_id = $('#t7_analysis_type').combobox('getValue');
 		var analysis_type = $('#t7_analysis_type').combobox('getText');
 		var sample_no = $('#t7_sample_no').combobox('getText');
 		var sample_id =$('#t7_sample_no').textbox('getText');
 		var actual_sample_no=sample_id.substring(sample_id.indexOf("L") + 0);
 		var heat_id = $('#t4_heat_id').combobox('getText');
 		//var actual_sample_no='LP1';
 		//var heat_id='A6766';
 		var sub_unit_id = document.getElementById('t4_sub_unit_id').value;
 		var psn_id = document.getElementById('t4_aim_psn_id').value;
 		var hm_recv_id = document.getElementById('t4_hmRecvId').value;
 		if (heat_id != null && heat_id != '' && heat_counter != null
 				&& heat_counter != '') {
 			
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
 				if(data.status!="SUCCESS"){
 					$('#t7_eof_Chemistry_tbl_id').datagrid('hideColumn', 'remarks');
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
	  			$('#t7_eof_Chemistry_tbl_id').datagrid('showColumn', 'remarks');
 			$.ajax({
 				headers : {
 					'Accept' : 'application/json',
 					'Content-Type' : 'application/json'
 				},
 				type : 'GET',
 				// data: JSON.stringify(formData),
 				dataType : "json",
 				url : "./heatProcessEvent/getChemDtlsBySpectro?analysis_id="
 						+ analysis_id + "&heat_id=" + heat_id + "&heat_counter="
 						+ heat_counter + "&sub_unit_id=" + sub_unit_id
 						+ "&sample_no=" + sample_no + "&psn_id=" + psn_id
 						+ "&hm_recv_id=" + hm_recv_id+"&analysis_type="+analysis_type+"&actual_sample="+actual_sample_no,
 				success : function(data) {
 					$('#t7_sample_date_time').datetimebox('setValue', data[0].sample_date);
 					var spectro_flag= 0;
 					var sample_flag=0;
 					$('#t7_eof_Chemistry_tbl_id').datagrid('loadData', data);
 					for(var i=0;i<data.length;i++){
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
				          				setDefaultCustomComboValues('t7_sample_result', 'REJECT', $('#t7_sample_result').combobox('getData'));
				          			}
				          			else{
				          				 setDefaultCustomComboValues('t7_sample_result', 'OK', $('#t7_sample_result').combobox('getData')); 
				          				
				          			}
				          		}
 						if(data[i].spectro_flag==1)
 							spectro_flag++;
 					}
 				 if(spectro_flag>0 && spectro_flag>9){
 					$.messager.alert('Warning',
 							'Data Retrieved From Spectro Server', 'info');
 					
 				 }
 				 else if (spectro_flag<10 && spectro_flag>0){
 					$.messager.alert('Warning',
 							'Partial Data Retrieved From Spectro Server', 'info');
 				 } else{
 					$.messager.alert('Warning',
 							'No Data Retrieved From Spectro Server', 'info');
 				 }
 				},
 				error : function() {
 					$.messager.alert('Processing',
 							'Error Connecting Spectro Server...', 'error');
 				}
 			}); 
	  		}
 		}
 	}
  </script>
       <style type="text/css">
        #t4_Eof_form_id{
            margin:0;
            padding:10px 30px;
        }
      
           </style>