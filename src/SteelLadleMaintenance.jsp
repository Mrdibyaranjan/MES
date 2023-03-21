<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<div style="height: 485px; padding: 10px 10px; width: 95%;" closed="true">
    <form id="t20_steel_ladle_det_form_id" method="post" novalidate>
      <div id="t20_SteelLadle_maint_tabs_div_id" class="easyui-tabs"  pill="true" style="width: 100%;height: auto;">
      <div title="<spring:message code="label.t20.circulation"/>" id="circ_div" style="padding:10px">
      <input name="t20_stLdl_maint_sts" type="hidden" id="t20_circ_stLdl_maint_sts_hId"><!-- type="hidden"  -->
      <input name="t20_stLdl"  type="hidden" id="t20_circ_stLdl_hId">
      <input name="t20_rec_ver"  type="hidden" id="t20_circ_rec_ver">    
    <br>
    <table style="width: 100%">
            <tr>
                <td><label><spring:message code="label.m26.steelLadleMstrLadleNo" /></label></td>
                <td><input name="ladle_no" type="text" id="t20_circ_ladle_no"
                    class="easyui-combobox"
                    data-options="panelHeight: 'auto',editable:false,required:true,
                    valueField:'keyval',                    
                    textField:'txtvalue'">
                </td>
                <td><label>Current Status</label></td>
                <td><input name="cur_ladle_status" type="text"
                    id="t20_circ_cur_ladle_status" class="easyui-textbox"
                    data-options="required:true,disabled:true"></td>
                <!--  
                <td><label><spring:message code="label.t20.shiftInchrge" /></label></td>
                <td><input name="shift_incharge" type="text" id="t20_circ_stldl_shiftInch"
                    class="easyui-combobox"
                    data-options="panelHeight: 'auto',editable:false,required:true,
                    valueField:'keyval',                    
                    textField:'txtvalue'"></td>
                <td><label><spring:message code="label.t20.srShiftInchrge"/></label></td>
                <td><input name="sr_shift_incharge" type="text" id="t20_circ_stldl_srshiftInch"
                    class="easyui-combobox"
                    data-options="panelHeight: 'auto',editable:false,required:true,
                    valueField:'keyval',                    
                    textField:'txtvalue'"></td>    
            </tr>
            <tr>      -->  
                <td><label><spring:message code="label.t20.ldlMngr"/></label></td>
                <td><input name="stLdl_manager" type="text" id="t20_circ_stldl_Mngr"
                    class="easyui-combobox"
                    data-options="panelHeight: 'auto',editable:false,required:true,
                    valueField:'keyval',                    
                    textField:'txtvalue'"></td>
                <td>Reserved: </td>
                <td><label>Yes</label><input type="radio" class="easyui-validatebox" style="width:25px;height:15px;background:white;border-radius:5px;border:2px solid #555;" name="circ_reserved" value="Y">
                    <label>No</label><input type="radio" class="easyui-validatebox" style="width:25px;height:15px;background:white;border-radius:5px;border:2px solid #555;" name="circ_reserved" value="N" checked="checked"></td>
                
            </tr>
        </table>        
    <br>
     <div id="t20_circ_heating_div" hidden="true">
         <table style="width: 100%">
             <tr>
                 <td><label><spring:message code="label.t20.startDate"/></label></td>
                <td><input name="heat_start_date" type="text" id="t20_circ_heatStartDt"
                            class="easyui-datetimebox" style="width:150px"  data-options="showSeconds:false"></td>
                 <td><label><spring:message code="label.t20.endDate"/></label></td>
                <td><input name="heat_end_date" type="text" id="t20_circ_heatEndDt"
                            class="easyui-datetimebox" style="width:150px" data-options="showSeconds:false"></td>
                <td><label><spring:message code="label.t20.burnerNo" /></label></td>
                <td><input name="heat_burner_no" type="text" id="t20_circ_heatBurnerNo"
                            class="easyui-combobox" style="width:150px"
                            data-options="panelHeight: 'auto',editable:false,required:true,
                            valueField:'keyval',                    
                            textField:'txtvalue'"></td>
                <td><label><spring:message code="label.t20.remarks" /></label></td>
                <td><input name="remarks" type="text" id="t20_circ_rdy_remarks"
                    class="easyui-textbox" style="width:150px"></td>
             </tr>
         </table>
     </div>
     <div align="center">
         <table>
         <tr>     
         <td><a href="javascript:void(0)" id="t20_circ_save_btn_id" class="easyui-linkbutton" 
                    iconCls="icon-save" style="width:80px" onclick="saveT20CircData('NA','N')"><b>Save</b></a></td>
         <td><a href="javascript:void(0)" id="t20_circ_reset_btn_id" class="easyui-linkbutton" 
                    iconCls="icon-reload" style="width:80px" onclick="clearCircData()"><b>Refresh</b></a>
         <td><a href="javascript:void(0)" id="t20_circ_down_btn_id" class="easyui-linkbutton" 
                    style="width:80px" onclick="downCircData()"><b>Down</b></a>
         </td>
        </tr>
        </table></div>
     <div>
     <table id="t20_circ_steel_ladle_det_tbl_id" title="<spring:message code="label.t20.ladlehisthdr"/>"
            toolbar="#t20_circ_steel_ladle_maint_btn_id" class="easyui-datagrid"
            style="width: 90%; height: 200px;" iconCls='icon-ok' rownumbers="true" fitColumns="true"
            singleSelect="true">
            <thead>
                <tr>
                    <th field="stladle_maint_status_id" hidden="true" 
                        editor="{type:'validatebox'}">Ladle Maint Id</th>
                    <th field="part_id" width="100" hieght="150"
                        formatter="(function(v,r,i){return formatColumnData('partsMstrModel.part_name',v,r,i);})" 
                        editor="{type:'combobox',options:{required:true,editable:true,valueField:'keyval',textField:'txtvalue',panelHeight: 'auto',
                        method:'get',url:'./CommonPool/getComboList?col1=part_id&col2=part_name&classname=PartsMasterModel&status=1&wherecol= record_status='}}">Part Name</th>
                    <th field="part_supplier" width="100" hieght="150"
                        formatter="(function(v,r,i){return formatColumnData('lkpSupplier.lookup_value',v,r,i);})"
                        editor="{type:'combobox',options:{required:true,editable:true,valueField:'keyval',textField:'txtvalue',panelHeight: 'auto',
                        method:'get',url:'./CommonPool/getComboList?col1=lookup_id&col2=lookup_value&classname=LookupMasterModel&status=1&wherecol= lookup_type=\'PART_SUPPLIER\' and lookup_status='}}">Part Supplier</th>
                    <th field="change_date" sortable="true" width="150" hieght="150"
                        editor="{type:'datetimebox',options:{required:false,editable:true}}"
                        formatter="(function(v,r,i){return formatDateTime('change_date',v,r,i)})"><spring:message code="label.t20.changeDate" /></th>
                    <th field="remarks" width="200" editor="{type:'textbox'}">Remarks</th>
                    <th field="cdate" width="200" editor="{type:'textbox',editable:true}">cdate</th><!-- hidden="true" -->
                    <th field="stladle_parts_maint_change_datelog_id" width="200" hidden="true" editor="{type:'textbox'}">stLdlPartMaintLogId</th>
                    <th field="record_version" width="200" hidden="true" editor="{type:'textbox'}">rec_version</th>
                </tr>
            </thead>
        </table>
    </div>     
     <br>
        <div>
        <table id="t20_steel_ladle_life_tbl_id"
           title="Steel Ladle Life" class="easyui-datagrid"
           style="width: 100%; height: 80px;" iconCls='icon-ok'
           rownumbers="true" fitColumns="true" singleSelect="true">
           <thead>
               <tr>
                   <th field="heat_id" width="130">Heat No</th>
                   <th field="ladle_life" width="100">Ladle Life</th>
                   <th field="inner_nozzle" width="150">Inner Nozzle</th>
                   <th field="outer_nozzle" width="150">Outer Nozzle</th>
                   <th field="random_plug" width="150">Random Plug</th>
                   <th field="directional_plug" width="150">Directional Plug</th>
                   <th field="hybrid_plug" width="120">Hybrid Plug</th>
                   <th field="fixed_plate" width="120">Fixed Plate</th>
                   <th field="movable_plate" width="120">Movable Plate</th>
               </tr>
           </thead>
         </table></div>
    </div>    
        <div id="t20_circ_steel_ladle_maint_btn_id">
            <a href="#" id="t20_circ_add_btn_id" class="easyui-linkbutton" data-options="iconCls:'icon-add'"
                onclick="addNewCircRow()" plain="true">Add</a>    <!-- javascript:$('#t20_circ_steel_ladle_det_tbl_id').edatagrid('addRow') --> 
            <a href="#" id="t20_circ_remove_btn_id" class="easyui-linkbutton" iconCls="icon-remove" plain="true" 
                onclick="deleteT20CircRow()">Remove</a>
            <a href="#" id="t20_circ_save_btn_id" class="easyui-linkbutton" iconCls="icon-save" plain="true" 
                onclick="saveT20CircData('NA','N')"><b>Save</b></a> 
        </div>
     <div title="<spring:message code="label.t20.newOrDown"/>" id="newdown_div" style="padding:10px">
      <input name="t20_stLdl_maint_sts"  type="hidden" id="t20_dwn_stLdl_maint_sts_hId">
      <input name="t20_stLdl"  type="hidden" id="t20_dwn_stLdl_hId">
      <input name="t20_rec_ver"  type="hidden" id="t20_dwn_rec_ver">
        <table style="width: 100%">
            <tr>
                <td><label><spring:message code="label.m26.steelLadleMstrLadleNo" /></label></td>
                <td><input name="ladle_no" type="text" id="t20_dwn_ladle_no"
                    class="easyui-combobox"
                    data-options="panelHeight: 'auto',editable:false,required:true,
                    valueField:'keyval',                    
                    textField:'txtvalue'">
                </td> 
                <td><label>Current Status</label></td>
                <td><input name="cur_ladle_status" type="text"
                    id="t20_dwn_cur_ladle_status" class="easyui-textbox"
                    data-options="required:true,disabled:true"></td>
                <!-- 
                <td><label><spring:message code="label.t20.shiftInchrge" /></label></td>
                <td><input name="shift_incharge" type="text" id="t20_dwn_stldl_shiftInch"
                    class="easyui-combobox"
                    data-options="panelHeight: 'auto',editable:false,required:true,
                    valueField:'keyval',                    
                    textField:'txtvalue'"></td>
                <td><label><spring:message code="label.t20.srShiftInchrge"/></label></td>
                <td><input name="sr_shift_incharge" type="text" id="t20_dwn_stldl_srshiftInch"
                    class="easyui-combobox"
                    data-options="panelHeight: 'auto',editable:false,required:true,
                    valueField:'keyval',                    
                    textField:'txtvalue'"></td> -->
                <td><label><spring:message code="label.t20.ldlMngr"/></label></td>
                <td><input name="stLdl_manager" type="text" id="t20_dwn_stldl_Mngr"
                    class="easyui-combobox"
                    data-options="panelHeight: 'auto',editable:false,required:true,
                    valueField:'keyval',                    
                    textField:'txtvalue'"></td>    
            </tr>
            <tr>
                <td><label><spring:message code="label.t20.liningType"/></label></td>
                <td><input name="lining_type" type="text" id="t20_dwn_lining_type"
                            class="easyui-combobox" style="width:150px"
                            data-options="panelHeight: 'auto',editable:false,required:true,
                            valueField:'keyval',                    
                            textField:'txtvalue'"></td>
                <td><label><spring:message code="label.t20.liningSupp"/></label></td>
                <td><input name="lining_supp" type="text" id="t20_dwn_lining_supp"
                            class="easyui-combobox" style="width:150px"
                            data-options="panelHeight: 'auto',editable:false,required:true,
                            valueField:'keyval',                    
                            textField:'txtvalue'"></td>
                <td>Heating: </td>
                <td><label>Yes</label><input type="radio" style="width:25px;height:15px;background:white;border-radius:5px;border:2px solid #555;" name="dwn_heating" value="Y">
                    <label>No</label><input type="radio" style="width:25px;height:15px;background:white;border-radius:5px;border:2px solid #555;" name="dwn_heating" value="N" checked="checked"></td>
             </tr>             
        </table>
        <br>
     <div id="t20_dwn_heating_div" hidden="true">
         <table style="width: 100%">
             <tr>
                 <td><label style="width:150px"><spring:message code="label.t20.startDate"/></label></td>
                <td><input name="heat_start_date" type="text" id="t20_dwn_heatStartDt"
                            class="easyui-datetimebox" style="width:150px" data-options="showSeconds:false"></td>
                 <td><label style="width:150px"><spring:message code="label.t20.endDate"/></label></td>
                <td><input name="heat_end_date" type="text" id="t20_dwn_heatEndDt"
                            class="easyui-datetimebox" style="width:150px" data-options="showSeconds:false"></td>
                <td><label style="width:150px"><spring:message code="label.t20.burnerNo" /></label></td>
                <td><input name="heat_burner_no" type="text" id="t20_dwn_heatBurnerNo"
                            class="easyui-combobox" style="width:150px"
                            data-options="panelHeight: 'auto',editable:false,required:true,
                            valueField:'keyval',                    
                            textField:'txtvalue'"></td>
                <td><label><spring:message code="label.t20.remarks" /></label></td>
                <td><input name="remarks" type="text" id="t20_dwn_rdy_remarks"
                    class="easyui-textbox" style="width:150px"></td>
             </tr>
         </table>
     </div>
                   
     <div align="center" >
         <table>
         <tr align="center" >         
            <td align="center"><a href="javascript:void(0)" id="t20_dwn_save_btn_id" class="easyui-linkbutton" 
                iconCls="icon-save" style="width:80px" onclick="saveT20DwnData('NA','N')"><b>Save</b></a></td>
            <td><a href="javascript:void(0)" id="t20_dwn_reset_btn_id" class="easyui-linkbutton" 
                    iconCls="icon-reload" style="width:80px" onclick="clearDwnData()"><b>Refresh</b></a></td>
            <td><a href="javascript:void(0)" id="t20_dwn_chng_avil_btn_id" class="easyui-linkbutton" 
                    style="width:180px" onclick="updateStsDwnT20()"><b>Change to Available</b></a></td>
        </tr>
        </table></div>
         <div>
         <table id="t20_dwn_steel_ladle_det_tbl_id" 
                toolbar="#t20_dwn_steel_ladle_maint_btn_id" class="easyui-datagrid"
                style="width: 100%; height: 200px;"
                title="<spring:message code="label.t20.ladlehisthdr"/>"
                iconCls='icon-ok' rownumbers="true" fitColumns="true"
                singleSelect="true">
                <thead>
                    <tr>
                        <th field="stladle_maint_status_id" hidden="true"
                            editor="{type:'validatebox'}">Ladle Maint Id</th>
                        <th field="part_id" width="100" hieght="150"
                            formatter="(function(v,r,i){return formatColumnData('partsMstrModel.part_name',v,r,i);})" 
                            editor="{type:'combobox',options:{required:true,editable:true,valueField:'keyval',textField:'txtvalue',panelHeight: 'auto',
                            method:'get',url:'./CommonPool/getComboList?col1=part_id&col2=part_name&classname=PartsMasterModel&status=1&wherecol= record_status='}}">Part Name</th>
                        <th field="part_supplier" width="100" hieght="150"
                            formatter="(function(v,r,i){return formatColumnData('lkpSupplier.lookup_value',v,r,i);})"
                            editor="{type:'combobox',options:{required:true,editable:true,valueField:'keyval',textField:'txtvalue',panelHeight: 'auto',
                            method:'get',url:'./CommonPool/getComboList?col1=lookup_id&col2=lookup_value&classname=LookupMasterModel&status=1&wherecol= lookup_type=\'PART_SUPPLIER\' and lookup_status='}}">Part Supplier</th>
                        <th field="change_date" sortable="true" width="150" hieght="150"
                            editor="{type:'datetimebox',options:{required:false,editable:false}}"
                            formatter="(function(v,r,i){return formatDateTime('change_date',v,r,i)})"><spring:message code="label.t20.changeDate" /></th>
                        <th field="remarks" width="200" editor="{type:'validatebox'}">Remarks</th>
                        <th field="cdate" width="200" hidden="true" editor="{type:'textbox'}">cdate</th>
                        <th field="stladle_parts_maint_log_id" hidden="true" width="200" editor="{type:'textbox'}">stLdlPartMaintLogId</th>
                        <th field="record_version" width="200" hidden="true" editor="{type:'textbox'}">rec_version</th>
                    </tr>
                </thead>
            </table>
        </div>  
         <br>
        <div id="t20_dwn_steel_ladle_maint_btn_id">
            <a href="#" id="t20_dwn_add_btn_id" class="easyui-linkbutton" data-options="iconCls:'icon-add'"
                onclick="javascript:$('#t20_dwn_steel_ladle_det_tbl_id').edatagrid('addRow')" plain="true">Add</a>   
            <a href="#" id="t20_dwn_remove_btn_id" class="easyui-linkbutton" iconCls="icon-remove" plain="true"  
                 onclick="deleteT20DwnRow()">Remove</a>
            <a href="#" id="t20_dwn_save_btn_id" class="easyui-linkbutton"
                iconCls="icon-save" plain="true" onclick="saveT20DwnData('NA','N')"><b>Save</b></a> 
        </div>
        </div></div>
        <br><br>        
    </form>
</div>

<script type="text/javascript">

var initialState = $("#containerbox").html();

    callT20Dropdowns();
        
    function callT20Dropdowns() {
        //var url1 = "./CommonPool/getComboList?col1=steel_ladle_si_no&col2=steel_ladle_no&classname=SteelLadleMasterModel&status=1&wherecol= record_status=";
        
        var url1 = "./CommonPool/getComboList?col1=st_ladle_si_no&col2=steelLadleObj.steel_ladle_no&classname=SteelLadleTrackingModel&status=1&wherecol= ladleStatusObj.in_circulation = \'Y\' and ladleStatusObj.recordStatus=";
        //var url1 = "./CommonPool/getComboList?col1=stladle_id&col2=stLdlTrackModel.steelLadleObj.steel_ladle_no&classname=StLadleMaintStatusModel&status=1&wherecol= stLdlTrackModel.ladleStatusObj.in_circulation = \'Y\' and stLdlTrackModel.ladleStatusObj.recordStatus=1 and record_status=";
        var url2 = "./CommonPool/getComboList?col1=steel_ladle_status_id&col2=steel_ladle_status&classname=SteelLadleStatusMasterModel&status=1&wherecol=in_circulation='N' and status in ('DOWN','RESERVED') and record_status=";
        var url2dwn = "./CommonPool/getComboList?col1=steel_ladle_status_id&col2=steel_ladle_status&classname=SteelLadleStatusMasterModel&status=1&wherecol=in_circulation='N' and status in ('DOWN') and record_status=";
        
        getDropdownList(url1, '#t20_circ_ladle_no');
        getDropdownList(url2, '#t20_circ_ladle_status');
        getDropdownList(url2dwn, '#t20_dwn_ladle_status');
        //$('#t20_dwn_ladle_status').combobox('setText','DOWN');
                        
        var url3 = "./CommonPool/getComboList?col1=user_role_id&col2=appUserAccountDetails.user_name&classname=UserRoleMapMasterModel&status=1&wherecol=lookupMasterModel.lookup_code='LADLE_SHIFT_INCHARGE' and lookupMasterModel.lookup_id=lookup_id  and record_status=";
        var url4 = "./CommonPool/getComboList?col1=user_role_id&col2=appUserAccountDetails.user_name&classname=UserRoleMapMasterModel&status=1&wherecol=lookupMasterModel.lookup_code='LADLE_SHIFT_INCHARGE' and lookupMasterModel.lookup_id=lookup_id  and record_status=";//LADLE_TEEMER_MAN
        var url5 = "./CommonPool/getComboList?col1=user_role_id&col2=appUserAccountDetails.user_name&classname=UserRoleMapMasterModel&status=1&wherecol=lookupMasterModel.lookup_code='LADLE_MANAGER' and lookupMasterModel.lookup_id=lookup_id  and record_status=";
        
        //var url6 = "./CommonPool/getComboList?col1=part_id&col2=part_name&classname=PartsMasterModel a&status=1&wherecol=record_status=";
        var url7 = "./CommonPool/getComboList?col1=lookup_id&col2=lookup_value&classname=LookupMasterModel&status=1&wherecol= lookup_type='PART_SUPPLIER' and lookup_status=";
        var url9 = "./CommonPool/getComboList?col1=lookup_id&col2=lookup_value&classname=LookupMasterModel&status=1&wherecol= lookup_type='LINING_TYPE' and lookup_status=";
        var url10 = "./CommonPool/getComboList?col1=lookup_id&col2=lookup_value&classname=LookupMasterModel&status=1&wherecol= lookup_type='LINING_SUPPLIER' and lookup_status=";
        var url11 = "./CommonPool/getComboList?col1=lookup_id&col2=lookup_value&classname=LookupMasterModel&status=1&wherecol= lookup_type='STLADLE_BURNER_NO' and lookup_status=";
        
        var url12 = "./CommonPool/getComboList?col1=part_id&col2=part_name&classname=PartsMasterModel&status=1&wherecol= record_status=";
        
        /*var url12 = "./CommonPool/getComboList?col1=lookup_id&col2=lookup_value&classname=LookupMasterModel&status=1&wherecol= lookup_type in ('PLUG_TYPE','PLATE_TYPE','NOZZLE_TYPE') and lookup_status=";
        getDropdownList(url12, '#t20_circ_parts');
        getDropdownList(url12, '#t20_dwn_parts'); */       
        
        //getDropdownList(url3, '#t20_circ_stldl_shiftInch');
        //getDropdownList(url4, '#t20_circ_stldl_srshiftInch');
        getDropdownList(url5, '#t20_circ_stldl_Mngr');
        //getDropdownList(url3, '#t20_dwn_stldl_shiftInch');
        //getDropdownList(url4, '#t20_dwn_stldl_srshiftInch');
        getDropdownList(url5, '#t20_dwn_stldl_Mngr');
        
        //getDropdownList(url6, '#t20_parts_type');
        getDropdownList(url7, '#t20_circ_partsupplier');
        getDropdownList(url7, '#t20_dwn_partsupplier');
        
        //var url8 = "./CommonPool/getComboList?col1=a.stladle_id&col2=a.stLdlMstrModel.steel_ladle_no&classname=StLadleMaintStatusModel a&status=1&wherecol= a.stLdlStsMstrModel.steel_ladle_status = 'DOWN' and a.stLdlStsMstrModel.in_circulation = 'N' and record_status=";
        var url8 = "./CommonPool/getComboList?col1=st_ladle_si_no&col2=steelLadleObj.steel_ladle_no&classname=SteelLadleTrackingModel&status=1&wherecol= ladleStatusObj.in_circulation = \'N\' and ladleStatusObj.steel_ladle_status = \'DOWN\' and ladleStatusObj.recordStatus=";
        getDropdownList(url8, '#t20_dwn_ladle_no');
        
        getDropdownList(url9, '#t20_dwn_lining_type');
        getDropdownList(url10, '#t20_dwn_lining_supp');
        getDropdownList(url11, '#t20_dwn_heatBurnerNo');
        getDropdownList(url11, '#t20_circ_heatBurnerNo');
        
        //Can be replaced with checkbox
        var url2evnt = "./CommonPool/getComboList?col1=steel_ladle_status_id&col2=steel_ladle_status&classname=SteelLadleStatusMasterModel&status=1&wherecol=in_circulation='N' and status in ('HEATING') and record_status=";
        getDropdownList(url2evnt, '#t20_dwn_ladle_sub_status');
    }
    
    $("#t20_circ_ladle_no")
    .combobox(
            {
                onSelect: function(rec) {
                    callT20Dropdowns();
                    //getStLdlMaintSts(rec.keyval);
                    setCircCurrentStatus(rec.keyval);
                    displayT20StLadleLifeDtl(rec.keyval);
                    loadCircLadleMaintSts();
                    refreshT20CircPartGrid();
                    //loadCircHeatingInfo();
                }
            }
        )
    
        $("#t20_dwn_ladle_no")
    .combobox(
            {
                onSelect: function(rec) {
                    callT20Dropdowns();
                    setDwnCurrentStatus(rec.keyval);                    
                    loadDwnLadleMaintSts();
                    refreshT20DwnPartGrid();
                    //loadDwnHeatingInfo();
                }
            }
        )
        
    /*
    $('#t20_save_btn_id').linkbutton('disable');
    $('#t20_add_btn_id').linkbutton('disable');
    $('#t20_remove_btn_id').linkbutton('disable');
    
    $("#t20_circ_ladle_status")
    .combobox(
            {
                onSelect: function(rec) {
                    if (rec.txtvalue == 'RESERVED') {
                        $('#t20_circ_partChange_div').show();
                        $('#t20_circ_heating_div').hide();
                        var url2evnt = "./CommonPool/getComboList?col1=steel_ladle_status_id&col2=steel_ladle_status&classname=SteelLadleStatusMasterModel&status=1&wherecol=in_circulation='N' and status in ('HEATING') and record_status=";
                        $('#t20_circ_ladle_sub_status').combobox('enable');
                        getDropdownList(url2evnt, '#t20_circ_ladle_sub_status');                        
                    } else if (rec.txtvalue == 'DOWN' || rec.txtvalue == 'CIRCULATION') {
                        $('#t20_circ_heating_div').hide();
                        $('#t20_circ_partChange_div').show(); 
                        $('#t20_circ_ladle_sub_status').combobox('setValue','');
                        $('#t20_circ_ladle_sub_status').combobox('disable');
                    } else {
                        $('#t20_circ_heating_div').hide();
                        $('#t20_circ_partChange_div').show();
                        $('#t20_circ_ladle_sub_status').combobox('setValue','');
                        $('#t20_circ_ladle_sub_status').combobox('disable');
                    }
                    //getStLdlMaintSts(ldlId);
                }
            }
            )
    
    $("#t20_circ_ladle_sub_status")
    .combobox(
            {
                onSelect: function(rec) {
                    if (rec.txtvalue == 'HEATING') {
                        $('#t20_circ_heating_div').show();
                    }else {
                        $('#t20_circ_heating_div').hide();
                    }
                }
            }
            )
            
    $("#t20_dwn_ladle_status")
    .combobox(
            {
                onSelect: function(rec) {
                    if (rec.txtvalue == 'DOWN') {
                        $('#t20_dwn_partChange_div').show();
                        $('#t20_dwn_heating_div').hide();
                        var url2evnt = "./CommonPool/getComboList?col1=steel_ladle_status_id&col2=steel_ladle_status&classname=SteelLadleStatusMasterModel&status=1&wherecol=in_circulation='N' and status in ('HEATING') and record_status=";
                        getDropdownList(url2evnt, '#t20_dwn_ladle_sub_status');
                        $('#t20_dwn_ladle_sub_status').combobox('enable');    
                    } else if (rec.txtvalue == 'AVAILABLE') {
                        $('#t20_dwn_heating_div').hide();
                        $('#t20_dwn_partChange_div').hide();
                        $('#t20_dwn_ladle_sub_status').combobox('setValue','');
                        $('#t20_dwn_ladle_sub_status').combobox('disable');
                    } else {
                        $('#t20_dwn_heating_div').hide();
                        $('#t20_dwn_partChange_div').hide();
                        $('#t20_dwn_ladle_sub_status').combobox('setValue','');
                        $('#t20_dwn_ladle_sub_status').combobox('disable');
                    }
                }
            }
            )        
            
    $("#t20_dwn_ladle_sub_status")
    .combobox(
            {
                onSelect: function(rec) {
                    if (rec.txtvalue == 'HEATING') {
                        //$('#t20_dwn_partChange_div').hide();
                        $('#t20_dwn_heating_div').show();
                    }else {
                        $('#t20_dwn_heating_div').hide();
                        //$('#t20_dwn_partChange_div').hide();
                    }
                }
            }
            )
                
    $("#t20_parts_type")
    .combobox(
            {
                onSelect : function(record) {
                    var partType;
                    $('#t20_parts').combobox('setText','');
                    $('#t20_parts').combobox('setValue','');
                    $('#t20_partsupplier').combobox('setText','');
                    $('#t20_partsupplier').combobox('setValue','');
                    
                    if (record.txtvalue == 'Ladle Plug') {
                        partType = 'PLUG_TYPE';
                        $('#t20_partsupplier').combobox('enable');
                    } else if (record.txtvalue == 'Ladle Nozzle') {
                        partType = 'NOZZLE_TYPE';
                        $('#t20_partsupplier').combobox('enable');
                    } else if (record.txtvalue == 'Ladle Plate') {
                        partType = 'PLATE_TYPE';
                        $('#t20_partsupplier').combobox('disable');
                    } else {
                        partType = '';
                        $('#t20_partsupplier').combobox('disable');
                    }
                    //clearT20SteelLadleDataGrid();
                }
            });
    
    //On change of Status
    $("#t20_ladle_status")
            .combobox(
                    {
                        onSelect : function(record) {
                            //var url3 = "./CommonPool/getComboList?col1=part_id&col2=partsMstrMdl.part_name&classname=StLdlStsPartsMapMasterModel a&status=1&wherecol=s_ldl_sts_id="
                                    //+ record.keyval + " and a.recordStatus=";
                            //getDropdownList(url3, '#t20_parts');
                            //clearT20SteelLadleDataGrid();
                            var ldlId = record.txtvalue;
                            alert('ldlId>>'+ldlId);
                        }
                    });
    
  //On change of Part
    $("#t20_parts").combobox({
        onSelect : function(record) {
                        
            clearT20SteelLadleDataGrid();
            var supp_url = "./CommonPool/getComboList?col1=lookup_id&col2=lookup_value&classname=LookupMasterModel&status=1&wherecol= lookup_type='SUPPLIER' and lookup_code='STEEL_LADLE' and lookup_status=";
            $('#t20_steel_ladle_det_tbl_id').edatagrid('addRow');
            var vr = $('#t20_steel_ladle_det_tbl_id').datagrid('getRows');
            for (var i = 0; i < vr.length; i++) {
                var editors = $('#t20_steel_ladle_det_tbl_id').datagrid('getEditors', i);
                var part_supp = $(editors[2].target);
                
                part_supp.combobox({
                url:supp_url,
                valueField:'keyval',
                textField:'txtvalue',
                method:'get'
                });
            }
        }
    });  
    
    //On change of Ladle
    /*$("#t20_circ_ladle_no")
            .combobox({
                onSelect : function(record) {    
                    setCurrentStatus(record.keyval);
                    displayT20StLadleLifeDtl(record.keyval);
                    $('#t20_ladle_status').combobox('setValue', '');
                    $('#t20_parts').combobox('setValue', '');
                    clearT20SteelLadleDataGrid();
                }                                    
    });
    
    $('#t20_add_btn_id').linkbutton('enable');
    $('#t20_remove_btn_id').linkbutton('enable');
    $('#t20_save_btn_id').linkbutton('enable');*/
    
    function getStLdlMaintSts(ladle_id){
        $.ajax({
            headers: { 
            'Accept': 'application/json',
            'Content-Type': 'application/json' 
            },
            type: 'GET',
            dataType: "json",
            url: "./laddleMaintainance/getStLdlMaintSts?stLadleId="+ladle_id,
            success: function(data) {
                if (data.ladleStatusObj.steel_ladle_status != null) {
                    $('#t20_circ_cur_ladle_status').textbox('setText', data.ladleStatusObj.steel_ladle_status);
                }
            },
            error:function(){ 
                $.messager.alert('Info','Error While Loading','Info');
            }
        });
    }
    
    function deleteT20CircRow() {
        var row = $('#t20_circ_steel_ladle_det_tbl_id').datagrid('getSelected');
        var index = $('#t20_circ_steel_ladle_det_tbl_id').datagrid('getRowIndex', row); // get the row index
        var partMaintLogId = row.stladle_parts_maint_log_id;
        var stladleMaintStsId = row.stladle_maint_status_id;
        
        if(partMaintLogId > 0 && stladleMaintStsId > 0){
               $('#t20_circ_steel_ladle_det_tbl_id').datagrid('deleteRow',index);
               //$('#t2_sav_btn_id').linkbutton('disable');               
               $.ajax({
                   headers : {
                       'Accept' : 'application/json',
                       'Content-Type' : 'application/json'
                   },
                   type : 'GET',
                   dataType : "json",
                   url : './laddleMaintainance/partLogDelete?partMaintLogId='+partMaintLogId+'&stladleMaintStsId='+stladleMaintStsId,
                   success : function(data) {
                       if (data.status == 'SUCCESS') {
                           $.messager.alert('Result Info', data.comment, 'info');
                           refreshT20CircPartGrid();
                       } else {
                           $.messager.alert('Result Info', data.comment, 'info');
                       };
                   },
                   error : function() {
                       $.messager.alert('Processing', 'Error while Deleting Parts...', 'error');
                   }
               });
           }else{
               refreshT20CircPartGrid();
         } 
    }

    function deleteT20DwnRow() {
        var row = $('#t20_dwn_steel_ladle_det_tbl_id').datagrid('getSelected');
        var index = $('#t20_dwn_steel_ladle_det_tbl_id').datagrid('getRowIndex', row);
        var partMaintLogId = row.stladle_parts_maint_log_id;
        var stladleMaintStsId = row.stladle_maint_status_id;
        
        if(partMaintLogId > 0 && stladleMaintStsId > 0){
               $('#t20_dwn_steel_ladle_det_tbl_id').datagrid('deleteRow',index);
               //$('#t2_sav_btn_id').linkbutton('disable');               
               $.ajax({
                   headers : {
                       'Accept' : 'application/json',
                       'Content-Type' : 'application/json'
                   },
                   type : 'GET',
                   dataType : "json",
                   url : './laddleMaintainance/partLogDelete?partMaintLogId='+partMaintLogId+'&stladleMaintStsId='+stladleMaintStsId,
                   success : function(data) {
                       if (data.status == 'SUCCESS') {
                           $.messager.alert('Result Info', data.comment, 'info');
                           refreshT20DwnPartGrid();
                       } else {
                           $.messager.alert('Result Info', data.comment, 'info');
                       };
                   },
                   error : function() {
                       $.messager.alert('Processing', 'Error while Deleting Parts...', 'error');
                   }
               });
           }else{
               refreshT20DwnPartGrid();
         }
    }
    
    function saveT20CircData(stsFlag, updateFlag) {
        
        var curr_status = $('#t20_circ_cur_ladle_status').textbox('getText');        
        var maintStsId; var recVerId; var heatStartDt = null; var heatEndDt = null; var heatBurnerNo = null;
        maintStsId = document.getElementById('t20_circ_stLdl_maint_sts_hId').value;    
        //alert('maintStsId>>'+maintStsId);
        recVerId = document.getElementById('t20_circ_rec_ver').value;
        var ladle_id = $('#t20_circ_ladle_no').combobox('getValue');
        //var shift_incharge = $('#t20_circ_stldl_shiftInch').combobox('getValue');
        var shift_incharge = '';
        //var sr_shift_incharge = $('#t20_circ_stldl_srshiftInch').combobox('getValue');
        var sr_shift_incharge = '';
        var ladle_manager = $('#t20_circ_stldl_Mngr').combobox('getValue');
        
        var ldlstatus = ''; var part_rows;
        var isReserved = $("input[name=circ_reserved]:checked").val();
		
        if (stsFlag == 'DOWN') {
            
            ldlstatus = stsFlag;
            
            formData = {
                    "stladle_id" : ladle_id,
                    "stladle_maint_status_id" : maintStsId,
                    "sr_shift_incharge" : sr_shift_incharge, 
                    "shift_incharge" : shift_incharge, 
                    "ladle_manager" : ladle_manager,
                    "steel_ladle_status" : ldlstatus,
                    "record_status" : 1,
                    "record_version" : recVerId
            };
        } else {  
        	
        	//var vr = $('#t20_circ_steel_ladle_det_tbl_id').datagrid('getRows');
        	part_rows = $('#t20_circ_steel_ladle_det_tbl_id').datagrid('getRows');
            if (part_rows.length > 0) {
            	for ( var k = 0; k < part_rows.length; k++) {
            		$('#t20_circ_steel_ladle_det_tbl_id').datagrid('endEdit', k);
                    var tempDt = formatDate(part_rows[k].change_date);
                    part_rows[k].change_date = null;
                    part_rows[k].cdate = tempDt;
            	}
            } else {
            	part_rows = null;
            }
        	       
            heatStartDt = $('#t20_circ_heatStartDt').textbox('getText');
            heatEndDt = $('#t20_circ_heatEndDt').textbox('getText');
            heatBurnerNo = $('#t20_circ_heatBurnerNo').combobox('getValue');
            heatRemarks = $('#t20_circ_rdy_remarks').textbox('getText');
            
            if (isReserved == 'Y' && curr_status == 'RESERVED') {
                if (heatEndDt != '' && heatEndDt != null) {
                    ldlstatus = 'AVAILABLE';
                } else {
                    ldlstatus = 'RESERVED';
                }
            } else if (isReserved == 'Y') {
                ldlstatus = 'RESERVED';
            } else {
                ldlstatus = curr_status;
            }
                        
            /*alert('part_rows ====>> '+JSON.stringify(part_rows));
            for (var i=0; i<part_rows.length; i++) {
                //var tempDt = part_rows[i].change_date;
                var tempDt = formatDate(part_rows[i].change_date);
                part_rows[i].change_date = null;
                part_rows[i].cdate = tempDt;
            }
            //var partRows = $('#t20_circ_steel_ladle_det_tbl_id').datagrid('getRows');
            //alert('///'+JSON.stringify(partRows));*/
            
            formData = {
                    "stladle_id" : ladle_id,
                    "stladle_maint_status_id" : maintStsId,
                    "sr_shift_incharge" : sr_shift_incharge, 
                    "shift_incharge" : shift_incharge, 
                    "ladle_manager" : ladle_manager,
                    "steel_ladle_status" : ldlstatus,
                    "record_status" : 1,
                    "record_version" : recVerId,
                    "stLdlPartMaintLog" : part_rows,
                    "burner_no" : heatBurnerNo,
                    "remarks" : heatRemarks
            };
        }

        $.ajax({
            headers : {
                'Accept' : 'application/json',
                'Content-Type' : 'application/json'
            },
            type : 'POST',
            data : JSON.stringify(formData),
            //url : './laddleMaintainance/SaveSteelLadleMaintenance?change_date='+ change_date,
            url : './laddleMaintainance/SaveSteelLadleMaintStatus?heatStartDt='+heatStartDt+'&heatEndDt='+heatEndDt+'&updateFlag='+updateFlag,
            success : function(data) {
                if (data.status == 'SUCCESS') {
                    $.messager.alert('Steel Ladle Maintenance', data.comment, 'info');
                    //clearCircData();
                    setCircCurrentStatus(ladle_id);
                    displayT20StLadleLifeDtl(ladle_id);
                    loadCircLadleMaintSts();
                    refreshT20CircPartGrid();
                } else {
                    $.messager.alert('Steel Ladle Maintenance', data.comment, 'info');
                }
            },
            error : function() {
                $.messager.alert('Processing',
                        'Error while Saving...', 'error');
            }
        });
    }
    
function saveT20DwnData(stsFlag, updateFlag) {
        
        var curr_status = $('#t20_dwn_cur_ladle_status').textbox('getText');        
        var maintStsId; var recVerId; var heatStartDt = null; var heatEndDt = null; var heatBurnerNo = null;
        maintStsId = document.getElementById('t20_dwn_stLdl_maint_sts_hId').value;    
        recVerId = document.getElementById('t20_dwn_rec_ver').value;
        var ladle_id = $('#t20_dwn_ladle_no').combobox('getValue');
        //var shift_incharge = $('#t20_dwn_stldl_shiftInch').combobox('getValue');
        var shift_incharge = '';
        //var sr_shift_incharge = $('#t20_dwn_stldl_srshiftInch').combobox('getValue');
        var sr_shift_incharge = '';
        var ladle_manager = $('#t20_dwn_stldl_Mngr').combobox('getValue');
        var lining_type = $('#t20_dwn_lining_type').combobox('getValue');
        var lining_supp = $('#t20_dwn_lining_supp').combobox('getValue');
        
        var avlFlg = 'Y'; 
        
        var ldlstatus = ''; var part_rows;
        var isHeating = $("input[name=dwn_heating]:checked").val();
        
        if (stsFlag == 'AVAILABLE') {
            
            ldlstatus = stsFlag;
            
            if (isHeating == 'Y') {
            	//var stsMaintId = document.getElementById('t20_dwn_stLdl_maint_sts_hId').value;
            	$.ajax({
                    headers : {
                        'Accept' : 'application/json',
                        'Content-Type' : 'application/json'
                    },
                    type : 'GET',
                    dataType : "json",
                    url : './laddleMaintainance/getStlLadleMaintPartsLog?stLdlMaintStsId='+ maintStsId, 
                    success : function(data) {
                    	if (data.heat_end_date == null || data.heat_end_date == '') {
                    		avlFlg = 'N';
                    	} else {
                           	formData = {
                                       "stladle_id" : ladle_id,
                                       //"stladle_maint_status_id" : maintStsId,
                                       "sr_shift_incharge" : sr_shift_incharge, 
                                       "shift_incharge" : shift_incharge, 
                                       "ladle_manager" : ladle_manager,
                                       "steel_ladle_status" : ldlstatus,
                                       "lining_type" : lining_type,
                                       "lining_supplier" : lining_supp,
                                       "record_status" : 1
                               };
                    	}
                    },
                    error : function(e) {
                        if (typeof(e.message) != 'undefined') {
                            $.messager.alert('Loading', 'Error while Loading Down Heating Info...'+e.message, 'error');
                        }
                    }
                });
            } else {
            	formData = {
                        "stladle_id" : ladle_id,
                        //"stladle_maint_status_id" : maintStsId,
                        "sr_shift_incharge" : sr_shift_incharge, 
                        "shift_incharge" : shift_incharge, 
                        "ladle_manager" : ladle_manager,
                        "steel_ladle_status" : ldlstatus,
                        "lining_type" : lining_type,
                        "lining_supplier" : lining_supp,
                        "record_status" : 1
                };
            }
        } else {
            
            heatStartDt = $('#t20_dwn_heatStartDt').textbox('getText');
            heatEndDt = $('#t20_dwn_heatEndDt').textbox('getText');
            heatBurnerNo = $('#t20_dwn_heatBurnerNo').combobox('getValue');
            heatRemarks = $('#t20_dwn_rdy_remarks').textbox('getText');
            
            ldlstatus = curr_status;
            
            /*if (isHeating == 'Y' && curr_status == 'RESERVED') {
                
                if (heatEndDt != '' && heatEndDt != null) {
                    ldlstatus = curr_status; //'AVAILABLE'
                } else {
                    ldlstatus = 'RESERVED';
                }
            } else if (isHeating == 'Y') {
                ldlstatus = 'RESERVED';
            } else {
                ldlstatus = curr_status;
            }*/
            
            part_rows = $('#t20_dwn_steel_ladle_det_tbl_id').datagrid('getRows');
            if (part_rows.length > 0) {
                for ( var i = 0; i < part_rows.length; i++) {
                    $('#t20_dwn_steel_ladle_det_tbl_id').datagrid('endEdit', i);
                    var tempDt = formatDate(part_rows[i].change_date);
                    part_rows[i].change_date = null;
                    part_rows[i].cdate = tempDt;
                }
            } else {
                part_rows = null;
            }
            
            formData = {
                    "stladle_id" : ladle_id,
                    "stladle_maint_status_id" : maintStsId,
                    "sr_shift_incharge" : sr_shift_incharge, 
                    "shift_incharge" : shift_incharge, 
                    "ladle_manager" : ladle_manager,
                    "steel_ladle_status" : ldlstatus,
                    "lining_type" : lining_type,
                    "lining_supplier" : lining_supp,
                    "record_status" : 1,
                    "record_version" : recVerId,
                    "stLdlPartMaintLog" : part_rows,
                    "burner_no" : heatBurnerNo,
                    "remarks" : heatRemarks
            };
        }
                
        if (avlFlg == 'Y') {
	        $.ajax({
	            headers : {
	                'Accept' : 'application/json',
	                'Content-Type' : 'application/json'
	            },
	            type : 'POST',
	            data : JSON.stringify(formData),
	            url : './laddleMaintainance/SaveSteelLadleMaintStatus?heatStartDt='+heatStartDt+'&heatEndDt='+heatEndDt+'&updateFlag='+updateFlag,
	            success : function(data) {
	                if (data.status == 'SUCCESS') {
	                    $.messager.alert('Steel Ladle Maintenance', data.comment, 'info');
	                    //clearDwnData();
	                    setDwnCurrentStatus(ladle_id);                    
	                    loadDwnLadleMaintSts();
	                    refreshT20DwnPartGrid();
	                } else {
	                    $.messager.alert('Steel Ladle Maintenance', data.comment, 'info');
	                }
	            },
	            error : function() {
	                $.messager.alert('Processing',
	                        'Error while Saving...', 'error');
	            }
	        });
        } else {
        	$.messager.alert('Steel Ladle Maintenance', 'Please Enter Haeting Details and Save!', 'info');
        }
    }
    
    $(function(){
        $("input[name=circ_reserved]").change(function(){
            var val = $(this).val();
            /*if (val == 'Y') {
                $('#t20_circ_heating_div').show();
            } else {
                $('#t20_circ_heating_div').hide();
            }*/
        });
    });
    
    $(function(){
        $("input[name=dwn_heating]").change(function(){
            var val = $(this).val();
            if (val == 'Y') {
                $('#t20_dwn_heating_div').show();
            } else {
                $('#t20_dwn_heating_div').hide();
            }
        });
    });
    
    function updateStsDwnT20() {
        saveT20DwnData('AVAILABLE','Y');
    }
    
    function downCircData() {
        saveT20CircData('DOWN','Y');
    }
    
    function clearCircData() {
        $('#t20_save_btn_id').linkbutton('disable');
        $('#t20_circ_ladle_no').combobox('setValue', '');
        $('#t20_circ_cur_ladle_status').textbox('setText', '');
        //$('#t20_circ_stldl_shiftInch').combobox('setValue', '');
        //$('#t20_circ_stldl_srshiftInch').combobox('setValue', '');
        $('#t20_circ_stldl_Mngr').combobox('setValue', '');
        
        //$('#t20_circ_ladle_status').combobox('setValue', '');
        //$('#t20_circ_ladle_sub_status').combobox('setValue', '');
        //$('#t20_circ_parts').combobox('setValue', '');
        //$('#t20_circ_partsupplier').combobox('setValue', '');
        //$('#t20_circ_partChngDt').textbox('setText', '');
        $('#t20_circ_heatStartDt').textbox('setText', '');
        $('#t20_circ_heatEndDt').textbox('setText', '');
        $('#t20_circ_heatBurnerNo').combobox('setValue', '');
        $('#t20_circ_rdy_remarks').textbox('setText', '');
        
        $('#t20_circ_heating_div').hide();
        
        var url1 = "./CommonPool/getComboList?col1=st_ladle_si_no&col2=steelLadleObj.steel_ladle_no&classname=SteelLadleTrackingModel&status=1&wherecol= ladleStatusObj.in_circulation = \'Y\' and ladleStatusObj.recordStatus=";
        //var url1 = "./CommonPool/getComboList?col1=stladle_id&col2=stLdlTrackModel.steelLadleObj.steel_ladle_no&classname=StLadleMaintStatusModel&status=1&wherecol= stLdlTrackModel.ladleStatusObj.in_circulation = \'Y\' and stLdlTrackModel.ladleStatusObj.recordStatus=1 and record_status=";
        getDropdownList(url1, '#t20_circ_ladle_no');
        
        var dummydata = new Array();
        $('#t20_circ_steel_ladle_det_tbl_id').datagrid('loadData', dummydata);        
        $('#t20_steel_ladle_life_tbl_id').datagrid('loadData', dummydata);
    }
    
    function clearDwnData() {
        $('#t20_dwn_ladle_no').combobox('setValue', '');
        $('#t20_dwn_cur_ladle_status').textbox('setText', '');
        //$('#t20_dwn_stldl_shiftInch').combobox('setValue', '');
        //$('#t20_dwn_stldl_srshiftInch').combobox('setValue', '');
        $('#t20_dwn_stldl_Mngr').combobox('setValue', '');
        
        $('#t20_dwn_lining_type').combobox('setValue', '');
        $('#t20_dwn_lining_supp').combobox('setValue', '');
        //$('#t20_dwn_ladle_status').combobox('setValue', '');
        //$('#t20_dwn_ladle_sub_status').combobox('setValue', '');
        $('#t20_dwn_heatStartDt').textbox('setText', '');
        $('#t20_dwn_heatEndDt').textbox('setText', '');
        $('#t20_dwn_heatBurnerNo').combobox('setValue', '');
        //$('#t20_dwn_parts').combobox('setValue', '');
        //$('#t20_dwn_partsupplier').combobox('setValue', '');
        //$('#t20_dwn_partChngDt').textbox('setText', '');
        $('#t20_dwn_rdy_remarks').textbox('setText', '');
        
        $('#t20_dwn_heating_div').hide();
        
        //var url = "./CommonPool/getComboList?col1=a.stladle_id&col2=a.stLdlMstrModel.steel_ladle_no&classname=StLadleMaintStatusModel a&status=1&wherecol= a.stLdlStsMstrModel.steel_ladle_status = 'DOWN' and a.stLdlStsMstrModel.in_circulation = 'N' and record_status=";
        var url = "./CommonPool/getComboList?col1=st_ladle_si_no&col2=steelLadleObj.steel_ladle_no&classname=SteelLadleTrackingModel&status=1&wherecol= ladleStatusObj.in_circulation = \'N\' and ladleStatusObj.steel_ladle_status = \'DOWN\' and ladleStatusObj.recordStatus=";
        getDropdownList(url, '#t20_dwn_ladle_no');
        
        var dummydata = new Array();
        $('#t20_dwn_steel_ladle_det_tbl_id').datagrid('loadData', dummydata);
        
        //var ladle_id = $('#t20_dwn_ladle_no').combobox('getValue')
        //setCurrentStatus(ladle_id);
    }
    
    function clearAllData() {
        //$('#t20_circ_ladle_no').combobox('setValue', '');
        //$('#t20_ladle_status').combobox('setValue', '');
        $('#t20_save_btn_id').linkbutton('disable');
        $('#t20_circ_cur_ladle_status').textbox('setText', '');
        //$('#t20_circ_stldl_shiftInch').combobox('setValue', '');
        //$('#t20_circ_stldl_srshiftInch').combobox('setValue', '');
        $('#t20_circ_stldl_Mngr').combobox('setValue', '');
        
        //$('#t20_circ_ladle_status').combobox('setValue', '');
        //$('#t20_circ_ladle_sub_status').combobox('setValue', '');
        //$('#t20_circ_parts').combobox('setValue', '');
        //$('#t20_circ_partsupplier').combobox('setValue', '');
        $('#t20_circ_heatStartDt').textbox('setText', '');
        $('#t20_circ_heatEndDt').textbox('setText', '');
        $('#t20_circ_heatBurnerNo').combobox('setValue', '');
        //$('#t20_circ_partChngDt').textbox('setText', '');
        //$('#t20_circ_rdy_remarks').textbox('setText', '');
        
        $('#t20_dwn_lining_type').combobox('setValue', '');
        $('#t20_dwn_lining_supp').combobox('setValue', '');
        $('#t20_dwn_cur_ladle_status').textbox('setText', '');
        //$('#t20_dwn_ladle_status').combobox('setValue', '');
        //$('#t20_dwn_ladle_sub_status').combobox('setValue', '');
        $('#t20_dwn_heatStartDt').textbox('setText', '');
        $('#t20_dwn_heatEndDt').textbox('setText', '');
        $('#t20_dwn_heatBurnerNo').combobox('setValue', '');
        //$('#t20_dwn_parts').combobox('setValue', '');
        //$('#t20_dwn_partsupplier').combobox('setValue', '');
        //$('#t20_dwn_partChngDt').textbox('setText', '');
        $('#t20_dwn_rdy_remarks').textbox('setText', '');
        
        $('#t20_circ_heating_div').hide();
        $('#t20_dwn_heating_div').hide();
        
        var ladle_id = $('#t20_circ_ladle_no').combobox('getValue');
        setCircCurrentStatus(ladle_id);
        var dummydata = new Array();
        $('#t20_steel_ladle_life_tbl_id').datagrid('loadData', dummydata);
    }

    function setCircCurrentStatus(ladle_id){
        //alert('ldlId>>'+ladle_id);
        $.ajax({
            headers: { 
            'Accept': 'application/json',
            'Content-Type': 'application/json' 
            },
            type: 'GET',
            dataType: "json",
            url : "./laddleMaintainance/getLadleCurrentStatus?steel_ladle_id="
                 + ladle_id,
            success: function(data) {
                if (data.ladleStatusObj.steel_ladle_status != null) {
                    $('#t20_circ_cur_ladle_status').textbox('setText', data.ladleStatusObj.steel_ladle_status);
                    
                    var sts = data.ladleStatusObj.steel_ladle_status; 
                    if (sts == 'RESERVED') {
                        $("input[name=circ_reserved][type=radio]").attr("disabled",true);
                        $('#t20_circ_heating_div').show();
                        $("input[name=circ_reserved][value='Y']").attr("checked", true);                        
                    } else {
                        $("input[name=circ_reserved][type=radio]").attr("disabled",false);
                        $('#t20_circ_heating_div').hide();
                        $("input[name=circ_reserved][value='N']").attr("checked", true);
                    }
                    
                }
            },
            error:function(){ 
                $.messager.alert('Info','Ladle does not exists','Info');
            }
        });
    }
    
    function setDwnCurrentStatus(ladle_id){
        $.ajax({
            headers: { 
            'Accept': 'application/json',
            'Content-Type': 'application/json' 
            },
            type: 'GET',
            dataType: "json",
            url : "./laddleMaintainance/getLadleCurrentStatus?steel_ladle_id="
                 + ladle_id,
            success: function(data) {
                if (data.ladleStatusObj.steel_ladle_status != null) {
                    $('#t20_dwn_cur_ladle_status').textbox('setText', data.ladleStatusObj.steel_ladle_status);
                    var sts = data.ladleStatusObj.steel_ladle_status; 
                }
            },
            error:function(){ 
                $.messager.alert('Info','Ladle does not exists','Info');
            }
        });
    }
    
    function displayT20StLadleLifeDtl(ladle_id){        
        $.ajax({
            headers : {
            'Accept' : 'application/json',
            'Content-Type' : 'application/json'
            },
            type : 'GET',
            // data: JSON.stringify(formData),
            dataType : "json",
            url : "./laddleMaintainance/getSteelLadleLifeDtls?steel_ladle="+ ladle_id,
            success : function(data) {
                $('#t20_steel_ladle_life_tbl_id').datagrid('loadData', data);
            },
            error : function() {
                $.messager.alert('Processing', 'Error while Processing Ajax...',
                                     'error');
            }
        });
    }
          
    function loadCircLadleMaintSts() {
        var stladle_id = $('#t20_circ_ladle_no').combobox('getValue'); //document.getElementById('t20_circ_ladle_no').value;
        document.getElementById('t20_circ_stLdl_maint_sts_hId').value = '';
        document.getElementById('t20_circ_rec_ver').value = '';
        
        $.ajax({
            headers : {
                'Accept' : 'application/json',
                'Content-Type' : 'application/json'
            },
            type : 'GET',
            dataType : "json",
            url : './laddleMaintainance/getSteelLadleMaintStatus?stladle_id=' + stladle_id, 
            success : function(data) {
                       //$('#t20_circ_stldl_shiftInch').combobox('setValue', data.sr_shift_incharge);
                       //$('#t20_circ_stldl_srshiftInch').combobox('setValue', data.shift_incharge);
                       $('#t20_circ_stldl_Mngr').combobox('setValue', data.ladle_manager);
                       
                       document.getElementById('t20_circ_stLdl_maint_sts_hId').value = data.stladle_maint_status_id;
                       document.getElementById('t20_circ_rec_ver').value = data.record_version;
                       
                       if (data.stladle_maint_status_id != null && data.stladle_maint_status_id > 0) {
                           loadCircHeatingInfo(data.stladle_maint_status_id);
                       }
            },
            error : function(ex) {
                if (ex.status != '200') {
                    $.messager.alert('Processing', ex.status+ ': Parts Data Does Not Exist...',
                    'error');    
                }
            }
        });
    }
    
    function loadDwnLadleMaintSts() {
        var stladle_id = $('#t20_dwn_ladle_no').combobox('getValue'); //document.getElementById('t20_dwn_ladle_no').value;
        document.getElementById('t20_dwn_stLdl_maint_sts_hId').value = '';
        document.getElementById('t20_dwn_rec_ver').value = '';
        $.ajax({
            headers : {
                'Accept' : 'application/json',
                'Content-Type' : 'application/json'
            },
            type : 'GET',
            dataType : "json",
            url : './laddleMaintainance/getSteelLadleMaintStatus?stladle_id=' + stladle_id, 
            success : function(data) {
                //$('#t20_dwn_stldl_shiftInch').combobox('setValue', data.sr_shift_incharge);
                //$('#t20_dwn_stldl_srshiftInch').combobox('setValue', data.shift_incharge);
                $('#t20_dwn_stldl_Mngr').combobox('setValue', data.ladle_manager);
                $('#t20_dwn_lining_type').combobox('setValue', data.lining_type);
             	$('#t20_dwn_lining_supp').combobox('setValue', data.lining_supplier);
                
                document.getElementById('t20_dwn_stLdl_maint_sts_hId').value = data.stladle_maint_status_id;
                document.getElementById('t20_dwn_rec_ver').value = data.record_version;
                
                if (data.stladle_maint_status_id != '' && data.stladle_maint_status_id > 0) {
                    loadDwnHeatingInfo(data.stladle_maint_status_id);
                }
            },
            error : function(ex) {
                if (ex.status != '200') {
                    $.messager.alert('Processing', ex.status+ ': Parts Data Does Not Exist...',
                    'error');    
                }
            }
            /*error : function() {
                $.messager.alert('Processing', 'Error while Loading Parts data...',
                        'error');
            }*/
        });
    }
    
    function loadCircHeatingInfo(maintStsId) {
        //var stLdlMaintStsId = document.getElementById('t20_circ_stLdl_maint_sts_hId').value;
        $.ajax({
            headers : {
                'Accept' : 'application/json',
                'Content-Type' : 'application/json'
            },
            type : 'GET',
            dataType : "json",
            url : './laddleMaintainance/getStlLadleMaintPartsLog?stLdlMaintStsId='+ maintStsId, 
            success : function(data) {
                $('#t20_circ_heatStartDt').datetimebox('setText',formatDate(data.heat_start_date));
                $('#t20_circ_heatEndDt').datetimebox('setText',formatDate(data.heat_end_date));
                $('#t20_circ_heatBurnerNo').combobox('setValue',data.burner_no);
                $('#t20_circ_rdy_remarks').textbox('setText',data.remarks);
            },
            error : function(e) {
                if (typeof(e.message) != 'undefined') {
                    $.messager.alert('Loading', 'Error while Loading Circn. Heating Info...'+e.message, 'error');
                }
            }
        });
    }
    
    function loadDwnHeatingInfo(maintStsId) {
    	/*var stLdlMaintStsId;
        if (maintStsId == null || maintStsId == '' || maintStsId <= 0) {
        	stLdlMaintStsId = document.getElementById('t20_dwn_stLdl_maint_sts_hId').value;
        } else {
        	stLdlMaintStsId = maintStsId;
        }*/
        
        $.ajax({
            headers : {
                'Accept' : 'application/json',
                'Content-Type' : 'application/json'
            },
            type : 'GET',
            dataType : "json",
            url : './laddleMaintainance/getStlLadleMaintPartsLog?stLdlMaintStsId='+ maintStsId, 
            success : function(data) {
                $('#t20_dwn_heatStartDt').datetimebox('setText',formatDate(data.heat_start_date));
                $('#t20_dwn_heatEndDt').datetimebox('setText',formatDate(data.heat_end_date));
                $('#t20_dwn_heatBurnerNo').combobox('setValue',data.burner_no);
                $('#t20_dwn_rdy_remarks').datetimebox('setText',data.remarks);
            },
            error : function(e) {
                if (typeof(e.message) != 'undefined') {
                    $.messager.alert('Loading', 'Error while Loading Down Heating Info...'+e.message, 'error');
                }
            }
        });
    }

    function refreshT20CircPartGrid() {
        var stladle_id = $('#t20_circ_ladle_no').combobox('getValue'); //document.getElementById('t20_circ_ladle_no').value;
        $.ajax({
            headers : {
                'Accept' : 'application/json',
                'Content-Type' : 'application/json'
            },
            type : 'GET',
            // data: JSON.stringify(formData),
            dataType : "json",
            url : './laddleMaintainance/getStLdlPartsDtls?stladle_id='+ stladle_id, 
            success : function(data) {
                    $('#t20_circ_steel_ladle_det_tbl_id').edatagrid('loadData', data);
            },
            error : function() {
                $.messager.alert('Processing', 'Error while Loading Parts data...',
                        'error');
            }
        });
    }
    
    function refreshT20DwnPartGrid() {
        var stladle_id = $('#t20_dwn_ladle_no').combobox('getValue'); //document.getElementById('t20_circ_ladle_no').value;
        $.ajax({
            headers : {
                'Accept' : 'application/json',
                'Content-Type' : 'application/json'
            },
            type : 'GET',
            // data: JSON.stringify(formData),
            dataType : "json",
            url : './laddleMaintainance/getStLdlPartsDtls?stladle_id='+ stladle_id, 
            success : function(data) {
                $('#t20_dwn_steel_ladle_det_tbl_id').edatagrid('loadData', data);
            },
            error : function() {
                $.messager.alert('Processing', 'Error while Loading Parts data...',
                        'error');
            }
        });
    }
    
    function validateT20CircForm() {
        return $('#t20_circ_steel_ladle_det_tbl_id').form('validate');
    }
    
    function validateT20DwnForm() {
        return $('#t20_dwn_steel_ladle_det_tbl_id').form('validate');
    }
    
    function addNewCircRow() {
        if (validateT20CircForm()) {
        //$('#t2_save_heat_plan_btn_id').linkbutton('disable');
        var vr = $('#t20_circ_steel_ladle_det_tbl_id').datagrid('getRows');
        var vlen = vr.length;        
        $('#t20_circ_steel_ladle_det_tbl_id').edatagrid('addRow');
        /* , {
            row : {
                remarks : 'a_a'
            }
        }*/
        }
    }
    
    function addNewDwnRow() {
        if (validateT20CircForm()) {        
            $('#t20_dwn_steel_ladle_det_tbl_id').edatagrid('addRow');
        }
    }
  
    function getFormattedDate(d) {
        var fdt;
        if(d!=null && d!==''){
            var date=new Date(d);
              fdt = addZero(date.getDate())+"/"+ addZero(date.getMonth()+1) + "/" + date.getFullYear() + " " + 
                 addZero(date.getHours()) + ":" + addZero(date.getMinutes()) + ":" + addZero(date.getSeconds());
           } else {
               fdt = '';
           }
        return fdt;
    }
    
    var lastIndex;
    $('#t20_circ_steel_ladle_det_tbl_id').edatagrid({
        onBeforeEdit : function(index,row) {
            var chdt=eval("row.change_date");
            var dt = getFormattedDate(chdt);
            if(row.change_date==null){
    			row.change_date = formatDate(new Date());
    		}
    		else{
    				row.change_date = formatDate(row.change_date);
    		}
            //row.change_date = dt;            
            var stsMntId = document.getElementById('t20_circ_stLdl_maint_sts_hId').value;
            row.stladle_maint_status_id = stsMntId;
        },
        onEndEdit : function(index,row) {
        	var chdt=(row.change_date).split(" ");
	    	var ch_time=new Date(commonGridDtfmt(chdt[0],chdt[1]));
	    	var chtime=ch_time.getTime();
	    	row.change_date =chtime;
        },
           onBeginEdit : function(index, row) {
               var editors = $('#t20_circ_steel_ladle_det_tbl_id').datagrid('getEditors', index);
               var partcmb = $(editors[1].target);
               var partSuppcmb = $(editors[2].target);                
               var changeDt = $(editors[3].target);
               var remarks = $(editors[4].target);
               var partLogId = $(editors[6].target);
               var recVer = $(editors[7].target);
               
               /* changeDt.datetimebox({
                        onChange: function(rec) {                              
                        //row.change_date = formatDate(rec);
                        //row.change_date = rec;
                        //$(editors[3].target).datetimebox('setText',rec);                        
                        //var dt = getFormattedDate(rec);
                        row.cdate = rec;
                           $(editors[5].target).textbox('setText',rec);
                          }
                      }) */
                   partcmb.combobox('options').onSelect = function(rec){                       
                       row.partsMstrModel={
                        "part_id" : parseInt(rec.keyval),
                        "part_name" : rec.txtvalue
                    };
                       row.part_id = parseInt(rec.keyval);
                   }
                partSuppcmb.combobox('options').onSelect = function(rec){
                       row.lkpSupplier={
                        "lookup_id" : parseInt(rec.keyval),
                        "lookup_value" : rec.txtvalue
                    };
                       row.part_supplier = parseInt(rec.keyval);
                   }
                   partLogId.textbox({
                       onChange: function(rec){
                           row.stladle_parts_maint_log_id = rec;
                       }                       
                   })
                   remarks.textbox({
                    onChange: function(rec){
                        $(editors[4].target).textbox('setText',rec)
                         row.remarks = rec;
                     }                       
                   })
               },onError: function(index,row){
                //$.messager.alert('Error While Processing the Part Details!');
                $.messager.alert('Result Error',index+'<msg>'+row.msg,'info');
            }
    })
    
        $('#t20_dwn_steel_ladle_det_tbl_id').edatagrid({
            onBeforeEdit : function(index,row) {
                var chdt=eval("row.change_date");
                var dt = getFormattedDate(chdt);
                if(row.change_date==null){
                    row.change_date = formatDate(new Date());
                }
                else{
                        row.change_date = formatDate(row.change_date);
                }
                //row.change_date = dt;
                var stsMntId = document.getElementById('t20_dwn_steel_ladle_det_tbl_id').value;
                row.stladle_maint_status_id = stsMntId;
            },
            onEndEdit : function(index,row) {
            	var chdt=(row.change_date).split(" ");
    	    	var ch_time=new Date(commonGridDtfmt(chdt[0],chdt[1]));
    	    	var chtime=ch_time.getTime();
    	    	row.change_date =chtime;
            },
            onBeginEdit : function(index, row) {
                   var editors = $('#t20_dwn_steel_ladle_det_tbl_id').datagrid('getEditors', index);
                   var partcmb = $(editors[1].target);
                   var    partSuppcmb = $(editors[2].target);                
                   var changeDt = $(editors[3].target);
                   var    remarks = $(editors[4].target);
                   var    partLogId = $(editors[6].target);
                   var    recVer = $(editors[7].target);
                   
                    /* changeDt.datetimebox({
                        onChange: function(rec) {
                          //row.change_date = rec;
                          //$(editors[3].target).datetimebox('setText',rec);
                          row.cdate = rec;
                             $(editors[5].target).textbox('setText',rec);
                            }
                        }) */
                      partcmb.combobox('options').onSelect = function(rec){                       
                          row.partsMstrModel={
                               "part_id" : parseInt(rec.keyval),
                               "part_name" : rec.txtvalue
                       };
                          row.part_id = parseInt(rec.keyval);
                      }
                       partSuppcmb.combobox('options').onSelect = function(rec){
                          row.lkpSupplier={
                               "lookup_id" : parseInt(rec.keyval),
                               "lookup_value" : rec.txtvalue
                           };
                          row.part_supplier = parseInt(rec.keyval);
                      }
                      partLogId.textbox({
                          onChange: function(rec){
                              row.stladle_parts_maint_log_id = rec;
                          }                       
                      })
                      remarks.textbox({
                       onChange: function(rec){
                           $(editors[4].target).textbox('setText',rec)
                            row.remarks = rec;
                        }                       
                      })
                  },onError: function(index,row){
                   //$.messager.alert('Error While Processing the Part Details!');
                   $.messager.alert('Result Error',index+'<msg>'+row.msg,'info');
               }
        })
</script>

<style type="text/css">
#t20_steel_ladle_det_form_id {
    margin: 0;
    padding: 10px 30px;
}
</style>