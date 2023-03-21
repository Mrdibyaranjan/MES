<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<script  src="${pageContext.request.contextPath}/js/common.js"></script>
<script src="${pageContext.request.contextPath}/js/SLStatusTracking.js"></script>
<!-- Main Screen Start..  -->
<div  style="padding-top: 10px;padding-left: 10px;padding-right: 10px;">
 <table id="m45_mstr_steel_ladle_tbl_id" toolbar="#m45_mstr_steel_ladle_tbl_toolbar_div_id"  title="Steel Ladle  Maintenance" class="easyui-datagrid" style="width:100%;height: 500px;"
            url="./slTracking/getSLCurrentStatus" method="get" iconCls='icon-ok'
             pagination="true" maximizable="true"  resizable="true" remoteSort="false" pageSize="20"
            rownumbers="true" fitColumns="true" singleSelect="true">
        <thead>
            <tr>
            <th field="slMasterObj.steel_ladle_si_no" sortable="true" hidden="true"  width="80" formatter="(function(v,r,i){return formatColumnData('slMasterObj.steel_ladle_si_no',v,r,i);})" ><b>TRNS No</b></th>
           <!--  <th field="slMasterObj.steel_ladle_si_no" sortable="true"  width="80" formatter="(function(v,r,i){return formatColumnData('slMasterObj.steel_ladle_si_no',v,r,i);})"  ><b>steel_ladle_si_no</b></th> -->
			<th field="slMasterObj.steel_ladle_no" sortable="true"  width="80" formatter="(function(v,r,i){return formatColumnData('slMasterObj.steel_ladle_no',v,r,i);})" ><b>Ladle No</b></th>
             <th field="status" sortable="true"  width="80"  ><b>Status</b></th>
               <th field="steel_ladle_life" sortable="true"  width="80" ><b>Ladle Life</b></th>
            
                </tr>
        </thead>
    </table>
  
    <div id="m45_mstr_steel_ladle_tbl_toolbar_div_id" style="padding-top: 5px;">
    <a href='javascript:void(0)' class='easyui-linkbutton' iconCls='icon-reload' onclick='addm45SteelLadleShift()'>Shift Details</a>
    <a href='javascript:void(0)' class='easyui-linkbutton' iconCls='icon-cut' onclick='changem45ladleparts()'>Change Parts</a>
    <a href='javascript:void(0)' class='easyui-linkbutton' iconCls='icon-add' onclick='m45ladleheating()'>Heating</a>
    <a href='javascript:void(0)' class='easyui-linkbutton' iconCls='icon-ok' onclick='addm45MstrSteelLadle()'>Last Processed Heats</a>
    <a href='javascript:void(0)' class='easyui-linkbutton' iconCls='icon-cancel' onclick='m45downladle()'>Down</a>
          	
    </div>
    
    </div>
<!--Main Screen over...  -->

<!-- Shift Details Entry Screen -->
<div id="m45_steel_ladle_shift_div_id" class="easyui-dialog"
	style="width: 830px; height: auto; padding: 10px 10px" closed="true"
	buttons="#m45_steel_ladle_shift_btn_div_id">
	<div class="ftitle">Steel Ladle Shift Details</div>
	<form id="m45_mstr_steel_ladle_shift_form_id" method="post" novalidate>
		<table style="width: 100%; text-align: left; padding-right: 20px">
		<input name="trns_shift_si_no" type="hidden"  id="m45_trns_shift_si_no">
			<!-- First Row -->
			<tr height="20">
				<td><label>Date:</label> <input name="recDate" type="text"
					id="m45_recDate" class="easyui-datetimebox"
					data-options="required:true,showSeconds:false"></td>
				<td><label>Shift:</label> <input name="shift" type="text"
					id="m45_shift" class="easyui-combobox" data-options="required:true,panelHeight: 'auto',readonly:true,editable:false,valueField:'keyval',textField:'txtvalue'">

				</td>
				<td><label>Ladle Shift Manager:</label> <input
					name="recordStatus" type="text" id="m45_ladle_shift_manager"
					class="easyui-combobox"
					data-options="required:true,panelHeight: 'auto',editable:false,selected:true,
                  valueField:'keyval',                    
                    textField:'txtvalue'">
				</td>
		
				<td>Senior Shift Incharge:</label> <input
					name="recordStatus" type="text" id="m45_senior_shift_incharge"
					class="easyui-combobox" data-options="required:true,panelHeight: 'auto',editable:false,selected:true,
                  valueField:'keyval',                    
                    textField:'txtvalue'">
				</td>
			
			</tr>
			<!-- Second Row -->
			<tr  height="20" >
			<td></td>
			<td align="center">SMS-1</td>
			<td align="center">SMS-2</td>
			</tr>
			<tr>
			<td><label>Ladle Man:</label></td>
			<td align="center"><input
					name="recordStatus" type="text" id="m45_sms1_ladleman"
					class="easyui-combobox" data-options="required:true,panelHeight: 'auto',editable:false,selected:true,
                  valueField:'keyval',                    
                    textField:'txtvalue'"></td>
			<td align="center"><input
					name="recordStatus" type="text" id="m45_sms2_ladleman"
					class="easyui-combobox" data-options="required:true,panelHeight: 'auto',editable:false,selected:true,
                  valueField:'keyval',                    
                    textField:'txtvalue'"></td>
			</tr>
			<tr>
			<td><label>Ladle Man Assistant:</label></td>
			<td  align="center"><input
					name="recordStatus" type="text" id="m45_sms1_ladlemanassist"
					class="easyui-combobox" data-options="required:true,panelHeight: 'auto',editable:false,selected:true,
                  valueField:'keyval',                    
                    textField:'txtvalue'"></td>
			<td  align="center"><input
					name="recordStatus" type="text" id="m45_sms2_ladlemanassist"
					class="easyui-combobox" data-options="required:true,panelHeight: 'auto',editable:false,selected:true,
                  valueField:'keyval',                    
                    textField:'txtvalue'"></td>
			</tr>
			<!-- Third Row -->
			<tr  height="20">
			<td></td>
			<td  align="center">CCM-1</td>
			<td  align="center">CCM-2</td>
			<td  align="center">CCM-3</td>
			</tr>
			<tr>
			<td><label>Teamer Man:<label></td>
			<td><input
					name="recordStatus" type="text" id="m45_ccm1_teamerman"
					class="easyui-combobox" data-options="required:true,panelHeight: 'auto',editable:false,selected:true,
                  valueField:'keyval',                    
                    textField:'txtvalue'"></td>
			<td  align="center"><input
					name="recordStatus" type="text" id="m45_ccm2_teamerman"
					class="easyui-combobox" data-options="required:true,panelHeight: 'auto',editable:false,selected:true,
                  valueField:'keyval',                    
                    textField:'txtvalue'"></td>
			<td  align="center"><input
					name="recordStatus" type="text" id="m45_ccm3_teamerman"
					class="easyui-combobox" data-options="required:true,panelHeight: 'auto',editable:false,selected:true,
                  valueField:'keyval',                    
                    textField:'txtvalue'"></td>		
			</tr>
			<tr>
			<td><label>Teamer Man Assistant:</label></td>
			<td  align="center"><input
					name="recordStatus" type="text" id="m45_ccm1_teamermanassist"
					class="easyui-combobox" data-options="required:true,panelHeight: 'auto',editable:false,selected:true,
                  valueField:'keyval',                    
                    textField:'txtvalue'"></td>
			<td  align="center"><input
					name="recordStatus" type="text" id="m45_ccm2_teamermanassist"
					class="easyui-combobox" data-options="required:true,panelHeight: 'auto',editable:false,selected:true,
                  valueField:'keyval',                    
                    textField:'txtvalue'"></td>
			<td  align="center"><input
					name="recordStatus" type="text" id="m45_ccm3_teamermanassist"
					class="easyui-combobox" data-options="required:true,panelHeight: 'auto',editable:false,selected:true,
                  valueField:'keyval',                    
                    textField:'txtvalue'"></td>
			</tr>
		</table>


	</form>
</div>

<div id="m45_steel_ladle_shift_btn_div_id">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="savem45SteelLadleShift()">Save</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="cancelm45SteelLadleShift()" >Cancel</a>
    </div>
<!-- Shift Details Entry Screen over.. -->

<!-- Parts Change Screen -->
<div id="m45_steel_ladle_parts_div_id" class="easyui-dialog"
	style="width: 830px; height: auto; padding: 10px 10px" closed="true"
	buttons="#m45_steel_ladle_parts_btn_div_id">
	<div class="ftitle">Steel Ladle Parts</div>
	<form id="m45_mstr_steel_ladle_form_id" method="post" novalidate>
		<table  style="width: 100%; text-align: left; padding-right: 20px">
		<input name="parts_si_no" type="hidden"  id="m45_parts_si_no">
		<input name="shift_si_no" type="hidden"  id="m45_shift_si_no">
			<!-- First Row -->
			<tr height="20">
				<td><label>Ladle No:</label> <input
					name="sl_ladle_no" type="text" id="m45_parts_ladle_no"
					class="easyui-textbox" data-options="readonly:true">
				</td>
				<td><label>Current Status:</label> <input
					name="sl_ladle_status" type="text" id="m45_parts_status" data-options="readonly:true"
					class="easyui-combobox">
				</td>
				<td><label>Date:</label> <input name="recDate" type="text"
					id="m45_parts_date" class="easyui-datetimebox"
					data-options="required:true,showSeconds:false"></td>
				<td><label>Shift:</label> <input name="shift" type="text"
					id="m45_parts_shift" class="easyui-combobox" data-options="required:false,panelHeight: 'auto',readonly:true,editable:false,valueField:'keyval',textField:'txtvalue'">

				</td>
				
			</tr>
			<!-- Second Row -->
			
			<tr>
			<td><label>Ladle Man:</label>
			<input
					name="recordStatus" type="text" id="m45_parts_ladle_man"
					class="easyui-combobox" data-options="required:false,panelHeight: 'auto',editable:false,selected:true,
                  valueField:'keyval',                    
                    textField:'txtvalue'">
					</td>
			<td><label>Ladle Shift Manager:</label><input
					name="recordStatus" type="text" id="m45_parts_ladle_sm"
					class="easyui-combobox" data-options="required:false,panelHeight: 'auto',editable:false,selected:true,readonly:true,
                  valueField:'keyval',                    
                    textField:'txtvalue'"></td>
			<td><label>Senior Shift Incharge:</label><input
					name="recordStatus" type="text" id="m45_parts_ladle_si"
					class="easyui-combobox" data-options="required:false,panelHeight: 'auto',editable:false,selected:true,readonly:true,
                  valueField:'keyval',                    
                    textField:'txtvalue'"></td>
			<td><label>Ladle Man Assistant:</label><input
					name="recordStatus" type="text" id="m45_parts_ladle_assist"
					class="easyui-combobox" data-options="required:false,panelHeight: 'auto',editable:false,selected:true,
                  valueField:'keyval',                    
                    textField:'txtvalue'"></td>
			</tr>
			
			
		</table>
		
		     <div>
     <table id="t20_circ_steel_ladle_det_tbl_id" title="<spring:message code="label.t20.ladlehisthdr"/>"
            toolbar="#t20_circ_steel_ladle_maint_btn_id" class="easyui-datagrid"
            style="width: 90%; height: 200px;" iconCls='icon-ok' rownumbers="true" fitColumns="true"
            singleSelect="true">
            <input name="heating_si_no" type="hidden"  id="m45_heating_si_no">
            <thead>
                <tr>
                  
                     <th field="parts_si_no"
                        editor="{type:'validatebox'}">Ladle Maint Id</th>
                    <th field="part_id" width="100" height="150"
                        formatter="(function(v,r,i){return formatColumnData('lkpPartId.lookup_value',v,r,i);})" 
                        editor="{type:'combobox',options:{required:true,editable:true,valueField:'keyval',textField:'txtvalue',panelHeight: 'auto',
                        method:'get',url:'./CommonPool/getComboList?col1=lookup_id&col2=lookup_value&classname=LookupMasterModel&status=1&wherecol= lookup_type=\'SL_PART_NAME\' and lookup_status='}}">Part Name</th>
                    <th field="part_supp_id" width="100" height="150"
                        formatter="(function(v,r,i){return formatColumnData('lkpSuppId.lookup_value',v,r,i);})"
                        editor="{type:'combobox',options:{required:true,editable:true,valueField:'keyval',textField:'txtvalue',panelHeight: 'auto',
                        method:'get',url:'./CommonPool/getComboList?col1=lookup_id&col2=lookup_value&classname=LookupMasterModel&status=1&wherecol= lookup_type=\'SL_PART_SUPPLIER\' and lookup_status='}}">Part Supplier</th>
                	 <th field="part_type_id" width="100" height="150"
                        formatter="(function(v,r,i){return formatColumnData('lkpTypeId.lookup_value',v,r,i);})"
                        editor="{type:'combobox',options:{required:true,editable:true,valueField:'keyval',textField:'txtvalue',panelHeight: 'auto',
                        method:'get',url:'./CommonPool/getComboList?col1=lookup_id&col2=lookup_value&classname=LookupMasterModel&status=1&wherecol= lookup_type=\'SL_PART_TYPE\' and lookup_status='}}">Part Type</th>
                	 <th field="change_date" sortable="true" width="150" height="150"
                        editor="{type:'datetimebox',options:{required:false,editable:true}}"
                        formatter="(function(v,r,i){return formatDateTime('change_date',v,r,i)})"><spring:message code="label.t20.changeDate" /></th>
                     <th field="part_life" width="200" >Part Life</th>
                       <th field="parts_status" width="100" height="150"
                        formatter="(function(v,r,i){return formatColumnData('lkpSuppId.lookup_value',v,r,i);})"
                        editor="{type:'combobox',options:{required:true,editable:true,valueField:'keyval',textField:'txtvalue',panelHeight: 'auto',
                        method:'get',url:'./CommonPool/getComboList?col1=lookup_value&col2=lookup_code&classname=LookupMasterModel&status=1&wherecol= lookup_type=\'SL_PARTS_STATUS\' and lookup_status='}}">Status</th>
                	
                      <%-- <th field="remarks" width="200" editor="{type:'textbox'}">Remarks</th>
                    <th field="cdate" width="200" editor="{type:'textbox',editable:true}">cdate</th><!-- hidden="true" -->
                    <th field="stladle_parts_maint_change_datelog_id" width="200" hidden="true" editor="{type:'textbox'}">stLdlPartMaintLogId</th>
                    <th field="record_version" width="200" hidden="true" editor="{type:'textbox'}">rec_version</th> --%>
                </tr>
            </thead>
        </table>
    </div>     
	    <div id="t20_circ_steel_ladle_maint_btn_id">
            <a href="#" id="t20_circ_add_btn_id" class="easyui-linkbutton" data-options="iconCls:'icon-add'"
                onclick="javascript:$('#t20_circ_steel_ladle_det_tbl_id').edatagrid('addRow')" plain="true">Add</a>    <!-- javascript:$('#t20_circ_steel_ladle_det_tbl_id').edatagrid('addRow') --> 
         <a href="#" id="t20_circ_remove_btn_id" class="easyui-linkbutton" iconCls="icon-remove" plain="true" 
                onclick="deleteT20CircRow()">Remove</a>
        	   <!--	<a href="#" id="t20_circ_save_btn_id" class="easyui-linkbutton" iconCls="icon-save" plain="true" 
                onclick="saveM45Parts()"><b>Save</b></a>  -->
        </div>
		

	</form>
</div>

<div id="m45_steel_ladle_parts_btn_div_id">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveM45Parts()" >Save</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="cancelm45ladleparts()" >Cancel</a>
    </div>
<!--     <div id="m43_mstr_steel_ladle_tbl_toolbar_div_id" style="padding-top: 5px;">
    <a href='javascript:void(0)' class='easyui-linkbutton' iconCls='icon-add' >Add</a>
    <a href='javascript:void(0)' class='easyui-linkbutton' iconCls='icon-remove' >Remove</a>
          	
    </div> -->
<!--  Parts Change Screen over.. -->

<!-- Ladle HEating Screen -->
<div id="m45_steel_ladle_heating_div_id" class="easyui-dialog"
	style="width: 930px; height: auto; padding: 10px 10px" closed="true"
	buttons="#m45_steel_ladle_heating_btn_div_id">
	<div class="ftitle">Steel Ladle Heating Details</div>
	<form id="m45_mstr_steel_ladle_form_id" method="post" novalidate>
		<table  style="width: 100%; text-align: left; padding-right: 20px">
			<!-- First Row -->
			<tr height="20">
				<td><label>Ladle No:</label> <input
					name="recordStatus" type="text" id="m45_heating_ladle_no"
					class="easyui-textbox" data-options="readonly:true"></td>
			</tr>
			<!-- Second Row -->
			<tr>
			<td><label>Heating Start:</label>
			<input name="recDate" type="text"
					id="m45_heating_st_date" class="easyui-datetimebox" formatter="(function(v,r,i){return formatDateTime('recDate',v,r,i)})"
					data-options="required:true,showSeconds:false">
					</td>
			<td><label>Burner No:</label><input
					name="recordStatus" type="text" id="m45_heating_burner"
					class="easyui-combobox" data-options="required:false,panelHeight: 'auto',editable:false,selected:true,readonly:false,
                  valueField:'keyval',                    
                    textField:'txtvalue'"></td>
			<td><label>Ladle Shift Manager:</label><input
					name="recordStatus" type="text" id="m45_heating_st_ladlesm"
					class="easyui-combobox" data-options="required:false,panelHeight: 'auto',editable:false,selected:true,readonly:false,
                  valueField:'keyval',                    
                    textField:'txtvalue'"></td>
			<td><label>Ladle Man:</label><input
					name="recordStatus" type="text" id="m45_heating_st_ladleman"
					class="easyui-combobox" data-options="required:false,panelHeight: 'auto',editable:false,selected:true,readonly:false,
                  valueField:'keyval',                    
                    textField:'txtvalue'"></td>
			<td><label>Ladle Man Assistant:</label><input
					name="recordStatus" type="text" id="m45_heating_st_ladleman_assit"
					class="easyui-combobox" data-options="required:false,panelHeight: 'auto',editable:false,selected:true,readonly:false,
                  valueField:'keyval',                    
                    textField:'txtvalue'"></td>
			</tr>
			<!-- Third Row  -->
			<tr>
			<td><label>Heating End:</label>
			<input name="recDate" type="text"
					id="m45_heating_end_date" class="easyui-datetimebox"
					data-options="required:true,showSeconds:false">
					</td>
			<td> </td>
			<td><label>Ladle Shift Manager:</label><input
					name="recordStatus" type="text" id="m45_heating_end_ladlesm"
					class="easyui-combobox" data-options="required:false,panelHeight: 'auto',editable:false,selected:true,readonly:false,
                  valueField:'keyval',                    
                    textField:'txtvalue'"></td>
			<td><label>Ladle Man:</label><input
					name="recordStatus" type="text" id="m45_heating_end_ladleman"
					class="easyui-combobox" data-options="required:false,panelHeight: 'auto',editable:false,selected:true,readonly:false,
                  valueField:'keyval',                    
                    textField:'txtvalue'"></td>
			<td><label>Ladle Man Assistant:</label><input
					name="recordStatus" type="text" id="m45_heating_end_ladleman_assit"
					class="easyui-combobox" data-options="required:false,panelHeight: 'auto',editable:false,selected:true,readonly:false,
                  valueField:'keyval',                    
                    textField:'txtvalue'"></td>
			</tr>
			<tr>
			<td><label>Remarks:</label><input
					name="recordStatus" type="text" id="m45_heating_remarks"
					class="easyui-textbox"></td>
			</tr>
			
		</table>
	</form>
</div>

<div id="m45_steel_ladle_heating_btn_div_id">
 		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload" onclick="changeToCirculation()" id="m45changetocirculation">Change To Circulation</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="savem45HeatingSave()" id="m45heatsave">Save</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="cancelm45ladleheating()" >Cancel</a>
    </div>
   
<!--  Ladle HEating Screen over.. -->


 <script type="text/javascript">   
		//Auto-complete combobox functionss..
	 	var shift="./CommonPool/getComboList?col1=lookup_id&col2=lookup_value&classname=LookupMasterModel&status=1&wherecol=lookup_type='SHIFT' and lookup_status=";
	  	var ladle_shift_mgr="./CommonPool/getComboList?col1=user_role_id&col2=appUserAccountDetails.user_name&classname=UserRoleMapMasterModel&status=1&wherecol=lookupMasterModel.lookup_code='SL_SHIFT_INCHARGE' and lookupMasterModel.lookup_id=lookup_id  and record_status=";
		var sr_shft_incrge="./CommonPool/getComboList?col1=user_role_id&col2=appUserAccountDetails.user_name&classname=UserRoleMapMasterModel&status=1&wherecol=lookupMasterModel.lookup_code='SL_SENIOR_SHIFT_INCHARGE' and lookupMasterModel.lookup_id=lookup_id  and record_status=";
		var sl_ladle_man="./CommonPool/getComboList?col1=user_role_id&col2=appUserAccountDetails.user_name&classname=UserRoleMapMasterModel&status=1&wherecol=lookupMasterModel.lookup_code='SL_LADLE_MAN' and lookupMasterModel.lookup_id=lookup_id  and record_status=";
		var sl_ladle_man_assist="./CommonPool/getComboList?col1=user_role_id&col2=appUserAccountDetails.user_name&classname=UserRoleMapMasterModel&status=1&wherecol=lookupMasterModel.lookup_code='SL_LADLE_ASSISTANT' and lookupMasterModel.lookup_id=lookup_id  and record_status=";
		var sl_teamer_man="./CommonPool/getComboList?col1=user_role_id&col2=appUserAccountDetails.user_name&classname=UserRoleMapMasterModel&status=1&wherecol=lookupMasterModel.lookup_code='SL_TEAMER_MAN' and lookupMasterModel.lookup_id=lookup_id  and record_status=";
		var sl_teamer_man_assist="./CommonPool/getComboList?col1=user_role_id&col2=appUserAccountDetails.user_name&classname=UserRoleMapMasterModel&status=1&wherecol=lookupMasterModel.lookup_code='SL_TEAMER_MAN_ASSISTANT' and lookupMasterModel.lookup_id=lookup_id  and record_status=";
		var heating_burnerno="./CommonPool/getComboList?col1=lookup_id&col2=lookup_value&classname=LookupMasterModel&status=1&wherecol=lookup_type='SL_BURNER_NO' and lookup_status=";
        
		
    	 getDropdownList(shift,'#m45_shift');
    	 getDropdownList(shift,'#m45_parts_shift');
    	 getDropdownList(ladle_shift_mgr,'#m45_ladle_shift_manager');
    	 getDropdownList(ladle_shift_mgr,'#m45_parts_ladle_sm');
    	 getDropdownList(sr_shft_incrge,'#m45_senior_shift_incharge');
    	 getDropdownList(sr_shft_incrge,'#m45_parts_ladle_si');
    	 getDropdownList(sl_ladle_man,'#m45_sms1_ladleman'); getDropdownList(sl_ladle_man,'#m45_sms2_ladleman');
    	 getDropdownList(sl_ladle_man,'#m45_parts_ladle_man'); 
    	 getDropdownList(sl_ladle_man_assist,'#m45_parts_ladle_assist');
    	 getDropdownList(sl_ladle_man_assist,'#m45_sms1_ladlemanassist');getDropdownList(sl_ladle_man_assist,'#m45_sms2_ladlemanassist');
    	 getDropdownList(sl_teamer_man,'#m45_ccm1_teamerman');getDropdownList(sl_teamer_man,'#m45_ccm2_teamerman');getDropdownList(sl_teamer_man,'#m45_ccm3_teamerman');
    	 getDropdownList(sl_teamer_man_assist,'#m45_ccm1_teamermanassist');getDropdownList(sl_teamer_man_assist,'#m45_ccm2_teamermanassist');getDropdownList(sl_teamer_man_assist,'#m45_ccm3_teamermanassist');
    	 getDropdownList(heating_burnerno,'#m45_heating_burner');
    	 getDropdownList(sl_ladle_man,'#m45_heating_st_ladleman'); getDropdownList(sl_ladle_man,'#m45_heating_end_ladleman');
    	 getDropdownList(sl_ladle_man_assist,'#m45_heating_st_ladleman_assit'); getDropdownList(sl_ladle_man_assist,'#m45_heating_end_ladleman_assit');
    	 getDropdownList(ladle_shift_mgr,'#m45_heating_st_ladlesm'); getDropdownList(ladle_shift_mgr,'#m45_heating_end_ladlesm');
    	 
		//Date formattin for shift date and based on time determine the shift A,B or C....
		$('#m45_recDate').datetimebox({

			value : (formatDate(new Date()))

		});

		var recdate = ($('#m45_recDate').datetimebox('getValue'));
		$('#m45_shift').combobox(
				{
					onLoadSuccess : function() {

						value: autoShift(recdate, 'm45_shift', $('#m45_shift')
								.combobox('getData'))
					
					}
				});
		$('#m45_recDate').datetimebox(
				{
					onChange : function(date) {

						value: autoShift(($('#m45_recDate')
								.datetimebox('getValue')), 'm45_shift', $(
								'#m45_shift').combobox('getData'));
						
					}
				});
		
		
		//Shift Details checking......
		$('#m45_ladle_shift_manager').combobox({
				onChange : function(data){
					var shift_date_time=commonDateISOformat($('#m45_recDate').datetimebox('getValue'));
					var shift_type=$('#m45_shift').combobox('getText');
					var formData = {
							"shift_date_time" : shift_date_time,
							"shift_type" : shift_type
					}
					 $.ajax({
							headers : {
								'Accept' : 'application/json',
								'Content-Type' : 'application/json'
							},
							type : 'POST',
							data : JSON.stringify(formData),
							dataType : "json",
							url : './SLShiftCntrl/CheckShift',
							success : function(data) {
							if (data.comment != '') {
								$.messager.alert('Shift Details Info',
										data.comment, 'info');
								$('#m45_steel_ladle_shift_div_id').form('clear');
							}
							
							},
							error : function() {
								$.messager.alert('Processing',
										'Error while Processing shift detais...', 'error');
							}
						}) 
				}
		});
		
		//shift details screen...   
		function addm45SteelLadleShift() {
			$('#m45_steel_ladle_shift_div_id').dialog({
				modal : true,
				cache : true
			});
			$('#m45_steel_ladle_shift_div_id').dialog('open').dialog('center')
					.dialog('setTitle', 'New Steel Ladle Shift Entry');
			$('#m45_steel_ladle_shift_div_id').form('clear');
			document.getElementById('m45_trns_shift_si_no').value = '0';
		}
		function cancelm45SteelLadleShift() {
			$('#m45_steel_ladle_shift_div_id').dialog('close');
		}
		function savem45SteelLadleShift() {
			    var trns_shift_si_no=document.getElementById('m45_trns_shift_si_no').value;
				var shift_date_time=commonDateISOformat($('#m45_recDate').datetimebox('getValue'));
				var shift_type=$('#m45_shift').combobox('getText');
				var ladle_shift_manager=$('#m45_ladle_shift_manager').combobox('getValue');
				var senior_shift_inchage=$('#m45_senior_shift_incharge').combobox('getValue');
				var ladle_man_sms1=$('#m45_sms1_ladleman').combobox('getValue');
				var ladle_man_sms2=$('#m45_sms2_ladleman').combobox('getValue');
				var ladle_man_asst_sms1=$('#m45_sms1_ladlemanassist').combobox('getValue');
				var ladle_man_asst_sms2=$('#m45_sms2_ladlemanassist').combobox('getValue');
				var teamer_man_ccm1=$('#m45_ccm1_teamerman').combobox('getValue');
				var teamer_man_ccm2=$('#m45_ccm2_teamerman').combobox('getValue');
				var teamer_man_ccm3=$('#m45_ccm3_teamerman').combobox('getValue');
				var teamer_man_asst_ccm1=$('#m45_ccm1_teamermanassist').combobox('getValue');
				var teamer_man_asst_ccm2=$('#m45_ccm2_teamermanassist').combobox('getValue');
				var teamer_man_asst_ccm3=$('#m45_ccm3_teamermanassist').combobox('getValue');
			
				var formData = {
					"trns_shift_si_no" : trns_shift_si_no,
					"shift_date_time" : shift_date_time,
					"shift_type" : shift_type,
					"ladle_shift_manager" : ladle_shift_manager,
					"senior_shift_inchage" : senior_shift_inchage,
					"ladle_man_sms1" : ladle_man_sms1,
					"ladle_man_sms2" : ladle_man_sms2,
					"ladle_man_asst_sms1" : ladle_man_asst_sms1,
					"ladle_man_asst_sms2" : ladle_man_asst_sms2,
					"teamer_man_ccm1" : teamer_man_ccm1,
					"teamer_man_ccm2" : teamer_man_ccm2,
					"teamer_man_ccm3" : teamer_man_ccm3,
					"teamer_man_asst_ccm1" : teamer_man_asst_ccm1,
					"teamer_man_asst_ccm2" : teamer_man_asst_ccm2,
					"teamer_man_asst_ccm3" : teamer_man_asst_ccm3
				};
			 	$.ajax({
					headers : {
						'Accept' : 'application/json',
						'Content-Type' : 'application/json'
					},
					type : 'POST',
					data : JSON.stringify(formData),
					dataType : "json",
					url : './SLShiftCntrl/SaveOrUpdate',
					success : function(data) {
						if (data.status == 'SUCCESS') {
							$('#m45_steel_ladle_shift_div_id').dialog('close');
							$.messager.alert('Steel Ladle Details Info',
									data.comment, 'info');
							
						//	cancelm44MstrSteelLadle();
							//$('#m45_steel_ladle_shift_div_id').form('clear');
						} else {
							$.messager.alert('Steel Ladle Details Info',
									data.comment, 'info');
						}
					},
					error : function() {
						$.messager.alert('Processing',
								'Error while Saving Shift savem45SteelLadleShift()...', 'error');
						
					}
				}) 
			}
		
		//=============================================================================================================================================================
		//change parts screen
		function changem45ladleparts() {
			 var row = $('#m45_mstr_steel_ladle_tbl_id').datagrid('getSelected');
			 
			 if(row.status==="NEW"){
			$('#m45_steel_ladle_parts_div_id').dialog({
				modal : true,
				cache : true
			});
			$('#m45_steel_ladle_parts_div_id').dialog('open').dialog('center')
					.dialog('setTitle', 'Ladle Parts Entry');
			$('#m45_steel_ladle_parts_div_id').form('clear');
			//document.getElementById('m45_steel_ladle_si_no').value = '0';
			$('#m45_parts_ladle_no').textbox('setText',row.slMasterObj.steel_ladle_no);
			$('#m45_parts_status').combobox('setText',row.status);
			var ladle_trns_si_no=row.trns_stladle_track_id;
			var ladle_life=row.steel_ladle_life;
			var dummydata = new Array();
			 $.ajax({
					headers : {
						'Accept' : 'application/json',
						'Content-Type' : 'application/json'
					},
					type : 'POST',
					//data : JSON.stringify(formData),
					dataType : "json",
					url : './slTracking/getPartsById?ladle_trns_si_no='+ladle_trns_si_no+'&ladle_life='+ladle_life,
					success : function(data) {
						console.log("SLPartsDetailsModel");
						console.log(data);
						$('#t20_circ_steel_ladle_det_tbl_id').datagrid('loadData', data);
					},
					error : function() {
						$.messager.alert('Processing',
								'Error while getting Ladle parts...', 'error');
						
					}
				})
			
			
		
			var recdate = ($('#m45_parts_date').datetimebox('getValue'));
			$('#m45_parts_shift').combobox(
					{
						onLoadSuccess : function() {

							value: autoShift(recdate, 'm45_parts_shift', $('#m45_parts_shift')
									.combobox('getData'))
						
						}
					});
			$('#m45_parts_date').datetimebox(
					{
						onChange : function(date) {

							value: autoShift(($('#m45_parts_date')
									.datetimebox('getValue')), 'm45_parts_shift', $(
									'#m45_parts_shift').combobox('getData'));
							
						}
					});
			
			
			$('#m45_parts_shift').combobox({
				onChange : function(data){
					var shift_date_time=commonDateISOformat($('#m45_parts_date').datetimebox('getValue'));
					var shift_type=$('#m45_parts_shift').combobox('getText');
					var formData = {
							"shift_date_time" : shift_date_time,
							"shift_type" : shift_type
					}
					 $.ajax({
							headers : {
								'Accept' : 'application/json',
								'Content-Type' : 'application/json'
							},
							type : 'POST',
							data : JSON.stringify(formData),
							dataType : "json",
							url : './SLShiftCntrl/ShiftAutofill',
							success : function(data) {
								console.log("SHIFT AUTO")
							console.log(data);
								$('#m45_parts_ladle_sm').combobox('setValue',data.ladle_shift_manager);
								$('#m45_parts_ladle_si').combobox('setValue',data.senior_shift_inchage);
								
								$('#m45_shift_si_no').val(data.trns_shift_si_no);
							
							},
							error : function() {
								 $.messager.alert('Processing',
										'No shift detais found!Please Make Entry ', 'error');
											$('#m45_steel_ladle_parts_div_id').dialog('close'); 
							}
						}) 
				}
		});
			}
			 else{
			    	$.messager.alert('Information','Ladle @ Ready State or Please Select Ladle...!','info');
			    }
			 }
		function cancelm45ladleparts() {
			$('#m45_steel_ladle_parts_div_id').dialog('close');
		}
		
		
		
		function saveM45Parts(){
			//when row is not filled properly.....
			var row = $('#t20_circ_steel_ladle_det_tbl_id').datagrid('getSelected');
		    var index = $('#t20_circ_steel_ladle_det_tbl_id').datagrid('getRowIndex', row); 
		    var checking=$('#t20_circ_steel_ladle_det_tbl_id').datagrid('validateRow',index);
			//if row is incomplete row will be deleted......
			if(checking===false){
				  $('#t20_circ_steel_ladle_det_tbl_id').datagrid('deleteRow',index);
			}
			$('#t20_circ_steel_ladle_det_tbl_id').edatagrid('saveRow');
			 var row = $('#m45_mstr_steel_ladle_tbl_id').datagrid('getSelected');
			var ladle_trns_si_no=row.trns_stladle_track_id;
			var ladle_life=row.steel_ladle_life;
			
			var shift_si_no=$('#m45_shift_si_no').val();
			var shift_date_time=commonDateISOformat($('#m45_parts_date').datetimebox('getValue'));
			var shift_type=$('#m45_parts_shift').combobox('getText');
			var ladle_shift_manager=$('#m45_parts_ladle_sm').combobox('getValue');
			var senior_shift_inchage=$('#m45_parts_ladle_si').combobox('getValue');
			var ladle_man=$('#m45_parts_ladle_man').combobox('getValue');
			var ladle_man_asst=$('#m45_parts_ladle_assist').combobox('getValue');
			var formData=$('#t20_circ_steel_ladle_det_tbl_id').datagrid('getData').rows;
			
			console.log("teable "+JSON.stringify(formData));
			console.log("Check "+ladle_trns_si_no+' '+ladle_life+' '+shift_si_no);
			if(ladle_trns_si_no===null || ladle_life===null || formData===null || shift_si_no===null){
				$.messager.alert('Parts Info',"ladle_trns_si_no!=null || ladle_life!=null || formData!=null", 'info');
				return false;
			}
			
			 $.ajax({
				headers : {
					'Accept' : 'application/json',
					'Content-Type' : 'application/json'
				},
				type : 'POST',
				data : JSON.stringify(formData),
				dataType : "json",
				url : './slTracking/partsSaveOrUpdate?ladle_trns_si_no='+ladle_trns_si_no+'&ladle_life='+ladle_life+'&shift_si_no='+shift_si_no+'&ladle_man='+ladle_man+'&ladle_man_asst='+ladle_man_asst,
				success : function(data) {
					if (data.status == 'SUCCESS') {
						$.messager.alert('Steel Ladle Details Info',
								data.comment, 'info');
						$('#t20_circ_steel_ladle_det_tbl_id').datagrid('reload');
						$('#m45_steel_ladle_parts_div_id').form('clear');
						$('#m45_steel_ladle_parts_div_id').dialog('close');
						$('#m45_mstr_steel_ladle_tbl_id').datagrid('reload');
						cancelm44MstrSteelLadle();
					} else {
						$.messager.alert('Steel Ladle Details Info',
								data.comment, 'info');
					}
				},
				error : function() {
					$.messager.alert('Processing',
							'Error while Saving Ladle parts...', 'error');
					
				}
			}) 
		}


		

		//=============================================================================================================================================================
		//Ladle heating screen
		function m45ladleheating() {
			var row = $('#m45_mstr_steel_ladle_tbl_id').datagrid('getSelected');
			 if(row.status=="NEW"){
			$('#m45_steel_ladle_heating_div_id').dialog({
				modal : true,
				cache : true
			});
			$('#m45_steel_ladle_heating_div_id').dialog('open')
					.dialog('center').dialog('setTitle', 'Ladle Heating Entry');
			$('#m45_steel_ladle_heating_div_id').form('clear');
			$('#m45_heating_ladle_no').textbox('setText',row.slMasterObj.steel_ladle_no);
			document.getElementById('m45_heating_si_no').value = '0';
			var ladle_trns_si_no=row.trns_stladle_track_id;
			var ladle_life=row.steel_ladle_life;
			 $.ajax({
					headers : {
						'Accept' : 'application/json',
						'Content-Type' : 'application/json'
					},
					type : 'POST',
					//data : JSON.stringify(formData),
					dataType : "json",
					url : './slTracking/getHeatingById?ladle_trns_si_no='+ladle_trns_si_no+'&ladle_life='+ladle_life,
					success : function(data) {
						console.log("SLHeatingDetailsModel");
						console.log(data);
						
						if(data.length>0){
						$('#m45_heating_si_no').val(data[0].heating_si_no);
						$('#m45_heating_remarks').textbox('setText',data[0].heating_remarks);
						// start_date_time and end_date_time => enable change to circulation button alone
						if( (formatDate(data[0].heating_start_time)!=null && formatDate(data[0].heating_start_time)!='') && (formatDate(data[0].heating_end_time)!=null && formatDate(data[0].heating_end_time)!='')){
							$('#m45_heating_st_date').datetimebox('setValue',formatDate(data[0].heating_start_time));
							$('#m45_heating_burner').combobox('setValue',data[0].heating_burner_no);
							$('#m45_heating_st_ladlesm').combobox('setValue',data[0].heating_start_ladle_sm);
							$('#m45_heating_st_ladleman').combobox('setValue',data[0].heating_start_ladle_man);
							$('#m45_heating_st_ladleman_assit').combobox('setValue',data[0].heating_start_ladle_asst);
							$('#m45_heating_st_date').datetimebox('readonly',true);
							$('#m45_heating_st_ladlesm').combobox('readonly',true);
							$('#m45_heating_st_ladleman').combobox('readonly',true);
							$('#m45_heating_st_ladleman_assit').combobox('readonly',true);
							$('#m45_heating_burner').combobox('readonly',true);
							$('#m45_heating_end_date').datetimebox('setValue',formatDate(data[0].heating_end_time));
							$('#m45_heating_end_ladlesm').combobox('setValue',data[0].heating_end_ladle_sm);
							$('#m45_heating_end_ladleman').combobox('setValue',data[0].heating_end_ladle_man);
							$('#m45_heating_end_ladleman_assit').combobox('setValue',data[0].heating_end_ladle_asst);
							$('#m45_heating_end_date').datetimebox('readonly',true);
							$('#m45_heating_end_ladlesm').combobox('readonly',true);
							$('#m45_heating_end_ladleman').combobox('readonly',true);
							$('#m45_heating_end_ladleman_assit').combobox('readonly',true);
							$('#m45heatsave').linkbutton('disable');
							$('#m45changetocirculation').linkbutton('enable');
							//alert("S & E");
						}
						//start_date_time alone => enable save button alone
						else if(formatDate(data[0].heating_start_time)!=null && formatDate(data[0].heating_start_time)!=''){
							$('#m45_heating_st_date').datetimebox('setValue',formatDate(data[0].heating_start_time));
							$('#m45_heating_burner').combobox('setValue',data[0].heating_burner_no);
							$('#m45_heating_st_ladlesm').combobox('setValue',data[0].heating_start_ladle_sm);
							$('#m45_heating_st_ladleman').combobox('setValue',data[0].heating_start_ladle_man);
							$('#m45_heating_st_ladleman_assit').combobox('setValue',data[0].heating_start_ladle_asst);
							$('#m45_heating_st_date').datetimebox('readonly',true);
							$('#m45_heating_st_ladlesm').combobox('readonly',true);
							$('#m45_heating_st_ladleman').combobox('readonly',true);
							$('#m45_heating_st_ladleman_assit').combobox('readonly',true);
							$('#m45_heating_burner').combobox('readonly',true);
							$('#m45changetocirculation').linkbutton('disable');
							$('#m45heatsave').linkbutton('enable');
							//alert("S");
						}
						//fresh entry => enable save button alone
						else{
							
						}
						}
						//no data from server...
						else{
							$('#m45_heating_st_date').datetimebox('readonly',false);
							$('#m45_heating_st_ladlesm').combobox('readonly',false);
							$('#m45_heating_st_ladleman').combobox('readonly',false);
							$('#m45_heating_st_ladleman_assit').combobox('readonly',false);
							$('#m45_heating_burner').combobox('readonly',false);
							$('#m45_heating_end_date').datetimebox('readonly',false);
							$('#m45_heating_end_ladlesm').combobox('readonly',false);
							$('#m45_heating_end_ladleman').combobox('readonly',false);
							$('#m45_heating_end_ladleman_assit').combobox('readonly',false);
							$('#m45changetocirculation').linkbutton('disable');
							$('#m45heatsave').linkbutton('enable');
							//alert("NULL");
						}
						
					},
					error : function() {
						$.messager.alert('Processing',
								'Error while getting Heating Details ...', 'error');
						
					}
				})
			
			 }
			 else{
				 $.messager.alert('Information','Ladle @ Ready State or Please Select Ladle...!','info');
			 }
		}
		function cancelm45ladleheating() {
			$('#m45_steel_ladle_heating_div_id').dialog('close');
		}
		function changeToCirculation(){
			var row = $('#m45_mstr_steel_ladle_tbl_id').datagrid('getSelected');
			var stladle_track_id=row.trns_stladle_track_id;
			var stladle_life=row.steel_ladle_life;
			var heat_id=document.getElementById('m45_heating_si_no').value;
			var heating_start_time=commonDateISOformat($('#m45_heating_st_date').datetimebox('getValue'));
			var heating_end_time=commonDateISOformat($('#m45_heating_end_date').datetimebox('getValue'));
			var heating_burner_no=$('#m45_heating_burner').combobox('getValue');
			var heating_start_ladle_sm=$('#m45_heating_st_ladlesm').combobox('getValue');
			var heating_start_ladle_man=$('#m45_heating_st_ladleman').combobox('getValue');
			var heating_start_ladle_asst=$('#m45_heating_st_ladleman_assit').combobox('getValue');
			var heating_end_ladle_sm=$('#m45_heating_end_ladlesm').combobox('getValue');
			var heating_end_ladle_man=$('#m45_heating_end_ladleman').combobox('getValue');
			var heating_end_ladle_asst=$('#m45_heating_end_ladleman_assit').combobox('getValue');
			
			
			var formData={
					"heating_si_no":heat_id,
					"heating_start_time":heating_start_time,
					"heating_burner_no":heating_burner_no,
					"heating_start_ladle_sm":heating_start_ladle_sm,
					"heating_start_ladle_man":heating_start_ladle_man,
					"heating_start_ladle_asst":heating_start_ladle_asst,
					"heating_end_time":heating_end_time,
					"heating_end_ladle_sm":heating_end_ladle_sm,
					"heating_end_ladle_man":heating_end_ladle_man,
					"heating_end_ladle_asst":heating_end_ladle_asst
		}
			//alert(heating_start_time);
				console.log("checkHeating FORMADATA");
				console.log(JSON.stringify(formData));
				
			if(heating_start_time!='Invalid Date' && heating_end_time!='Invalid Date'){
			//	alert("change to circulation....");
				$.ajax({
					headers : {
						'Accept' : 'application/json',
						'Content-Type' : 'application/json'
					},
					type : 'POST',
					//data : JSON.stringify(formData),
					dataType : "json",
					url : './slTracking/updateLadleStatus?heat_id='+heat_id+'&stladle_track_id='+stladle_track_id+'&stladle_life='+stladle_life,
					success : function(data) {
						$.messager.alert('Processing',
								data.comment, 'info');
						//$('#m45_mstr_steel_ladle_tbl_id').datagrid('reload');
						$('#m45_steel_ladle_heating_div_id').dialog('close');
						$('#m45_mstr_steel_ladle_tbl_id').datagrid('reload');
					},
					error : function() {
						$.messager.alert('Processing',
								'Error while Updating ladle status...', 'error');
						
					}
				}) 
				
			}
			else{
				//alert("cannot change to circulation....");
				 $.messager.alert('Information','cannot change to circulation..!','info');
			}
		}
		function savem45HeatingSave(){
			var row = $('#m45_mstr_steel_ladle_tbl_id').datagrid('getSelected');
			var trns_stladle_track_id=row.trns_stladle_track_id;
			var trns_stladle_life=row.steel_ladle_life;
			var heating_si_no=document.getElementById('m45_heating_si_no').value;
			var heating_start_time=commonDateISOformat($('#m45_heating_st_date').datetimebox('getValue'));
			var heating_end_time=commonDateISOformat($('#m45_heating_end_date').datetimebox('getValue'));
			var heating_burner_no=$('#m45_heating_burner').combobox('getValue');
			var heating_start_ladle_sm=$('#m45_heating_st_ladlesm').combobox('getValue');
			var heating_start_ladle_man=$('#m45_heating_st_ladleman').combobox('getValue');
			var heating_start_ladle_asst=$('#m45_heating_st_ladleman_assit').combobox('getValue');
			var heating_end_ladle_sm=$('#m45_heating_end_ladlesm').combobox('getValue');
			var heating_end_ladle_man=$('#m45_heating_end_ladleman').combobox('getValue');
			var heating_end_ladle_asst=$('#m45_heating_end_ladleman_assit').combobox('getValue');
			var heating_remarks=$('#m45_heating_remarks').textbox('getText');
			
			var formData={
				"heating_si_no":heating_si_no,
				"heating_start_time":heating_start_time,
				"heating_burner_no":heating_burner_no,
				"heating_start_ladle_sm":heating_start_ladle_sm,
				"heating_start_ladle_man":heating_start_ladle_man,
				"heating_start_ladle_asst":heating_start_ladle_asst,
				"heating_end_time":heating_end_time,
				"heating_end_ladle_sm":heating_end_ladle_sm,
				"heating_end_ladle_man":heating_end_ladle_man,
				"heating_end_ladle_asst":heating_end_ladle_asst,
				"trns_stladle_track_id":trns_stladle_track_id,
				"trns_stladle_life":trns_stladle_life,
				"heating_remarks":heating_remarks
				
			}
			console.log("HEATING FORMADATA");
			console.log(JSON.stringify(formData));
			console.log("Check "+trns_stladle_track_id+' '+trns_stladle_life);
			if(trns_stladle_track_id===null || trns_stladle_life===null){
				$.messager.alert('Heating Info',"trns_stladle_track_id===null || trns_stladle_life===null", 'info');
				return false;
			}
			
			$.ajax({
				headers : {
					'Accept' : 'application/json',
					'Content-Type' : 'application/json'
				},
				type : 'POST',
				data : JSON.stringify(formData),
				dataType : "json",
				url : './slTracking/heatingSaveOrUpdate',
				success : function(data) {
					if (data.status == 'SUCCESS') {
						$.messager.alert('Steel Ladle Details Info',
								data.comment, 'info');				
						$('#m45_steel_ladle_heating_div_id').form('clear');
						$('#m45_steel_ladle_heating_div_id').dialog('close');
						$('#m45_mstr_steel_ladle_tbl_id').datagrid('reload');
					} else {
						$.messager.alert('Steel Ladle Details Info',
								data.comment, 'info');
					}
				},
				error : function() {
					$.messager.alert('Processing',
							'Error while Saving Heat Details...', 'error');
					
				}
			}) 
			
			
			
			
			
		}
		
		
		//====================================================================================================
		//Set Steel Ladle status to DOWN
		function m45downladle(){
			var row = $('#m45_mstr_steel_ladle_tbl_id').datagrid('getSelected');
			if(row){
			var stladle_track_id=row.trns_stladle_track_id;
			
			$.ajax({
				headers : {
					'Accept' : 'application/json',
					'Content-Type' : 'application/json'
				},
				type : 'POST',
				//data : JSON.stringify(formData),
				dataType : "json",
				url : './slTracking/setLadleStatusDown?stladle_track_id='+stladle_track_id,
				success : function(data) {
					$.messager.alert('Processing',
							data.status, 'info');
					$('#m45_mstr_steel_ladle_tbl_id').datagrid('reload');
				},
				error : function() {
					$.messager.alert('Processing',
							'Error while downing ladle...', 'error');
					
				}
			}) 
			}
			 else{
				 $.messager.alert('Information','Please Select Ladle...!','info');
			 }
		}
	</script>

	<style type="text/css">
        #m45_mstr_steel_ladle_form_id{
            margin:0;
            padding:10px 30px;
        }
    </style>