<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<script  src="${pageContext.request.contextPath}/js/common.js"></script>

<div title="EAF/LRF Additions"
	style="padding-top: 5px; padding-left: 5px; padding-right: 5px;"
	data-options="iconCls:'icon-ok'">
	<br>
<table id="t33_eof_lrf_add_tbl_id" 
		class="easyui-datagrid" style="width: 100%; height: 450px;"
		iconCls='icon-ok' pagination="true" maximizable="true"
		resizable="true" fitColumns="true" remoteSort="false" pageSize="20"
		rownumbers="true" singleSelect="true">
</table>
</div>
<!--  EAF ADDTIONS -->
<div id="t33_eof_lrf_addition_div_id" class="easyui-dialog" style="width:100%;height:600px;padding:10px 10px"
            closed="true" data-change="0">
	<input name="trns_si_no"  type="hidden" id="t33_trns_si_no">
	<table style="width: 95%" class="easyui-panel" >
	<tr>
		<td>
        	<label><spring:message code="label.t6.eofLadleHeatNo"/></label>
         	<input name="heat_no"  type="text" id="t33_heat_no"  class="easyui-textbox" data-options="editable:false">
        </td>  
        <td>
        	<label><spring:message code="label.t4.eofProductionUnit"/></label>
         	<input name="unit"  type="text" id="t33_unit"  class="easyui-textbox" data-options="editable:false">
        </td>   
       	<td> 
			<label><spring:message code="label.t5.eofFurnaceAimPsn"/></label>
         	<input name="aim_psn"  type="text" id="t33_aim_psn"  class="easyui-textbox" data-options="editable:false">
        </td>
		<td>
			<label><spring:message code="label.t4.grade"/></label>
         	<input name="aim_psn"  type="text" id="t33_grade"  class="easyui-textbox" data-options="editable:false">
        </td>  
	</tr>
    </table>
    <br>
    <table style="width: 95%">
    <tr>
    <td style="width: 50%" valign="top">
    <table id="t33_eof_lrf_addition_tbl_1_id" title="<spring:message code="label.t6.eofLadleAddition"/>" class="easyui-datagrid" style="width:100%;height:auto;"
           iconCls='icon-ok' maximizable="false"  resizable="true" showFooter="true" remoteSort="false"  rownumbers="true" singleSelect="true"> 
        <thead>
            <tr>
				<th field="material_id" formatter="(function(v,r,i){return formatColumnData('mtrlName',v,r,i);})" sortable="false" width="150"><spring:message code="label.t5.materialName"/></th>
				<th field="sap_matl_id" width="140">SAP Matl ID</th>
				<th field="valuation_type" width="120">Valuation Type</th>
				<th field="qty" align="right" data-options="editor:{type:'numberbox',options:{precision:2,min:0}}"  width="80"><spring:message code="label.t5.Qty"/></th>
              	<th field="uom" formatter="(function(v,r,i){return formatColumnData('uom',v,r,i);})" sortable="false" width="60"><spring:message code="label.t5.uom"/></th>
				<th field="mtr_cons_si_no" sortable="true" hidden="true" width="0"><spring:message code="label.t5.pkId"/></th>
				<th field="version" hidden="true" align="right" width="0">Version</th>
         </tr>
         </thead>
    </table>
    
    </td>
    <td style="width: 50%">
    <table id="t33_eof_lrf_addition_tbl_2_id" title="<spring:message code="label.t5.eofFurnaceAddition"/>" class="easyui-datagrid" style="width:100%;height:auto;"
           iconCls='icon-ok' maximizable="false"  resizable="true" remoteSort="false"  rownumbers="true" singleSelect="true" showFooter="true"> 
        <thead>
            <tr>
              	<th field="material_id" formatter="(function(v,r,i){return formatColumnData('mtrlName',v,r,i);})" sortable="false" width="120"><spring:message code="label.t5.materialName"/></th>
              	<th field="sap_matl_id"  width="140">SAP Matl ID</th>
				<th field="valuation_type" width="120">Valuation Type</th>
              	<th field="qty" align="right" data-options="editor:{type:'numberbox',options:{precision:2,min:0}}"  width="80"><spring:message code="label.t5.Qty"/></th>
              	<th field="uom" formatter="(function(v,r,i){return formatColumnData('uom',v,r,i);})" sortable="false" width="60"><spring:message code="label.t5.uom"/></th>
              	<th field="mtr_cons_si_no" sortable="true" hidden="true" width="0"><spring:message code="label.t5.pkId"/></th>
              	<th field="version" hidden="true" align="right" width="0">Version</th>
         </tr>
         </thead>
    </table>
    </td>
    </tr><tr><td></td></tr>
    <tr>
    <td colspan="2" align="center">
		<a href="javascript:void(0)" class="easyui-linkbutton" id="btnT33Save"  iconCls="icon-save" onclick="saveT33EofLrfAddDtls()" style="width:90px">Save</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload" onclick="refreshT33EofLrfAddDtls()" style="width:90px">Refresh</a>
	</td>
    </tr>
    </table>
</div> 
<!-- LRF ADDTIONS -->
<div id="t33_2_eof_lrf_addition_div_id" class="easyui-dialog" style="width:80%;height:600px;padding:10px 10px"
            closed="true" data-change="0">
	<input name="t33_2_trns_si_no"  type="hidden" id="t33_2_trns_si_no">
	<table style="width: 95%" class="easyui-panel" >
	<tr>
		<td>
        	<label><spring:message code="label.t6.eofLadleHeatNo"/></label>
         	<input name="heat_no"  type="text" id="t33_2_heat_no"  class="easyui-textbox" data-options="editable:false">
        </td>  
        <td>
        	<label><spring:message code="label.t4.eofProductionUnit"/></label>
         	<input name="unit"  type="text" id="t33_2_unit"  class="easyui-textbox" data-options="editable:false">
        </td>   
       	<td> 
			<label><spring:message code="label.t5.eofFurnaceAimPsn"/></label>
         	<input name="aim_psn"  type="text" id="t33_2_aim_psn"  class="easyui-textbox" data-options="editable:false">
        </td>
		<td>
			<label><spring:message code="label.t4.grade"/></label>
         	<input name="aim_psn"  type="text" id="t33_2_grade"  class="easyui-textbox" data-options="editable:false">
        </td>  
	</tr>
    </table>
    <br>
    <table style="width: 95%">
    <tr>
    <td style="width: 85%" valign="top">
    <table id="t33_2_eof_lrf_addition_tbl_id" title="<spring:message code="label.t6.eofLadleAddition"/>" class="easyui-datagrid" style="width:100%;height:auto;"
           iconCls='icon-ok' maximizable="false"  resizable="true" showFooter="true" remoteSort="false"  rownumbers="true" singleSelect="true"> 
        <thead>
            <tr>
				<th field="material_id" formatter="(function(v,r,i){return formatColumnData('material_name',v,r,i);})" sortable="false" width="190"><spring:message code="label.t5.materialName"/></th>
				<th field="sap_matl_id" width="160">SAP Matl ID</th>
				<th field="valuation_type" width="140">Valuation Type</th>
				<th field="tot_qty" align="right" data-options="editor:{type:'numberbox',options:{precision:2,min:0}}"  width="120"><spring:message code="label.t5.Qty"/></th>
              	<th field="uom" formatter="(function(v,r,i){return formatColumnData('uom',v,r,i);})" sortable="false" width="90"><spring:message code="label.t5.uom"/></th>
				<th field="cons_sl_no" hidden="true" width="0"><spring:message code="label.t5.pkId"/></th>
				<th field="consumption_qty" hidden="true" width="0">cons_qty</th>
				<th field="version" hidden="true" width="0">Version</th>
				<th field="arc_sl_no" hidden="true" width="0">arc_sl_no</th>
				<th field="act_tot_qty" hidden="true" width="0">act_tot_qty</th>
         </tr>
         </thead>
    </table>
    </td>
    <tr><td></td></tr>
    <tr>
    <td colspan="2" align="center">
		<a href="javascript:void(0)" class="easyui-linkbutton" id="btnT33_2Save" iconCls="icon-save" onclick="saveT33_2EofLrfAddDtls()" style="width:90px">Save</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload" onclick="refreshT33_2EofLrfAddDtls()" style="width:90px">Refresh</a>
	</td>
    </tr>
    </table>
</div> 
<script>
	var headerList = ${headerList};
	var subUnit = "${subUnit}";
	var consPostFlag = "${consPostFlag}";
	var $header = {};
	var columns = new Array();
	var f_Name = "col_view";
	columns.push({ "field": f_Name, "formatter" : function(value,row,index){
		return '<a href="#" onclick="viewT33ConsDetails(\'' +row.col0+','+row.col1+','+row.col2+','+row.col3+','+row.trns_si_no+ '\')">View</a> '	
	} 
	});
	$.each(headerList, function( index, value ) {
		var fieldName = "col"+index;
		columns.push({ "field": fieldName, "title": value});
	});
	f_Name = "trns_si_no";
	columns.push({ "field": f_Name, "title": f_Name, "hidden": true });
	$header.columns = new Array(columns);
	$('#t33_eof_lrf_add_tbl_id').datagrid($header);
	loadT33TblData(subUnit);

/*
$.ajax({
	headers : {
		'Accept' : 'application/json',
		'Content-Type' : 'application/json'
	},
	type : 'GET',
	dataType : "json",
	url : "./EOFproduction/displayEofLrfAdditionsHdr",
	success : function(data) {
		var $header = {};
   		var columns = new Array();
		alert("data[0] "+data.length);
		var f_Name = "col_view";
		columns.push({ "field": f_Name, "formatter" : function(value,row,index){
			return '<a href="#" onclick="viewT33ConsDetails(\'' +row.col0+','+row.col1+','+row.col2+','+row.col3+','+row.trns_si_no+ '\')">View</a> '	
			} 
		});
		for(var j=0; j<data.length; j++){
			var fieldName = "col"+j;
			columns.push({ "field": fieldName, "title": data[j]});
		}
		
		f_Name = "trns_si_no";
		columns.push({ "field": f_Name, "title": f_Name, "hidden": true });
		$header.columns = new Array(columns);
   	 	$('#t33_eof_lrf_add_tbl_id').datagrid($header);
		loadT33TblData();
	}
});
*/
	function loadT33TblData(subUnit){
		$.ajax({
		headers : {
			'Accept' : 'application/json',
			'Content-Type' : 'application/json'
		},
		type : 'GET',
		dataType : "json",
		url : "./EOFproduction/displayEofLrfAdditionsDtls?subUnit="+subUnit,
		success : function(data) {
			$("#t33_eof_lrf_add_tbl_id").datagrid("loadData",data);
		}
		});
	}
	function viewT33ConsDetails(heat_dtls){	
		var h_dtl=heat_dtls.split(",");
		var heat_no = h_dtl[0];
		var unit = h_dtl[1]; 
		var aim_psn = h_dtl[2];
		var steel_grade = h_dtl[3];
		var trns_id = h_dtl[4];
		if(subUnit=='EAF'){
			if(consPostFlag == '1'){
				$('#btnT33Save').linkbutton('disable');
			}else{
				$('#btnT33Save').linkbutton('enable');
			}
			$('#t33_eof_lrf_addition_tbl_1_id').edatagrid({	});
			$('#t33_eof_lrf_addition_tbl_2_id').edatagrid({	});
			$("#t33_heat_no").textbox("setText","");
			$("#t33_unit").textbox("setText","");
			$("#t33_aim_psn").textbox("setText","");
			$("#t33_grade").textbox("setValue","");
			document.getElementById('t33_trns_si_no').value = trns_id;
	
			var dummydata = new Array();
			$('#t33_eof_lrf_addition_tbl_1_id').datagrid('loadData', dummydata);
			$('#t33_eof_lrf_addition_tbl_2_id').datagrid('loadData', dummydata);
	
			$('#t33_eof_lrf_addition_div_id').dialog({
				modal : true,
				cache : true
			});
			$("#t33_heat_no").textbox("setText", heat_no);
			$("#t33_aim_psn").textbox("setText", aim_psn);
			$("#t33_grade").textbox("setText", steel_grade);
			$("#t33_unit").textbox("setValue", unit);
	
			$('#t33_eof_lrf_addition_div_id').dialog('open').dialog('center').dialog(
			'setTitle', 'EAF Addition Details');
	
			$.ajax({
			headers : {
			'Accept' : 'application/json',
			'Content-Type' : 'application/json'
			},
			type : 'GET',
			dataType : "json",
			url : "./EOFproduction/getEofLrfAdditions?trns_si_no="+trns_id+"&psn_no="+aim_psn+"&mtrl_type1=FURNACE_ADDITIONS&mtrl_type2=LADLE_ADDITIONS",
			success : function(data) {
			$("#t33_eof_lrf_addition_tbl_1_id").datagrid("loadData",data[0]);
			$("#t33_eof_lrf_addition_tbl_2_id").datagrid("loadData",data[1]);
			}
			});
		}else if(subUnit=='LRF'){
			if(consPostFlag == '1'){
				$('#btnT33_2Save').linkbutton('disable');
			}else{
				$('#btnT33_2Save').linkbutton('enable');
			}
			$('#t33_2_eof_lrf_addition_tbl_id').edatagrid({	});
			$("#t33_2_heat_no").textbox("setText","");
			$("#t33_2_unit").textbox("setText","");
			$("#t33_2_aim_psn").textbox("setText","");
			$("#t33_2_grade").textbox("setValue","");
			document.getElementById('t33_2_trns_si_no').value = trns_id;
			var dummydata = new Array();
			$('#t33_2_eof_lrf_addition_tbl_id').datagrid('loadData', dummydata);
	
			$('#t33_2_eof_lrf_addition_div_id').dialog({
				modal : true,
				cache : true
			});
			$("#t33_2_heat_no").textbox("setText", heat_no);
			$("#t33_2_aim_psn").textbox("setText", aim_psn);
			$("#t33_2_grade").textbox("setText", steel_grade);
			$("#t33_2_unit").textbox("setValue", unit);
	
			$('#t33_2_eof_lrf_addition_div_id').dialog('open').dialog('center').dialog(
			'setTitle', 'LRF Addition Details');
			
			$.ajax({
				headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json'
				},
				type : 'GET',
				dataType : "json",
				url : "./LRFproduction/getEofLrfAdditions?heat_no="+heat_no+"&mtrl_type=LRF_ARC_ADDITIONS",
				success : function(data) {
				$("#t33_2_eof_lrf_addition_tbl_id").datagrid("loadData",data);
				}
			});
		}
	}

	function saveT33EofLrfAddDtls(){
		var ldl_addns = $('#t33_eof_lrf_addition_tbl_1_id').datagrid('getRows');
		var fur_addns = $('#t33_eof_lrf_addition_tbl_2_id').datagrid('getRows');
	
		for (var i = 0; i < ldl_addns.length; i++) {
			$('#t33_eof_lrf_addition_tbl_1_id').datagrid('endEdit', i);
		}
		for (var i = 0; i < fur_addns.length; i++) {
			$('#t33_eof_lrf_addition_tbl_2_id').datagrid('endEdit', i);
		}
		
		formData = {
		"trns_si_no" : document.getElementById('t33_trns_si_no').value,
		"ladleAdditions" : ldl_addns,
		"furnaceAdditions" : fur_addns 
		};
		
		$.ajax({
		headers: { 
	 	'Accept': 'application/json',
	 	'Content-Type': 'application/json' 
	 	},
	 	type: 'POST',
	 	data: JSON.stringify(formData),
	 	dataType: "json",
	 	url: './EOFproduction/SaveOrUpdateMtrlConsumptions',
	 	success: function(data) {
			if(data.status == 'SUCCESS'){
   		    	$.messager.alert('EAF Additions',data.comment,'info');
   		    	refreshT33EofLrfAddDtls();
			}else {
   		    	$.messager.alert('EAF Additions',data.comment,'info');
			}
		},
	 	error:function(){      
			$.messager.alert('Processing','Error while Processing Ajax...','error');
	 	}
		});
	}
	function refreshT33EofLrfAddDtls(){
		var trns_id = document.getElementById('t33_trns_si_no').value;
		var aim_psn = $("#t33_aim_psn").textbox("getText");
		$.ajax({
			headers : {
			'Accept' : 'application/json',
			'Content-Type' : 'application/json'
			},
			type : 'GET',
			dataType : "json",
			url : "./EOFproduction/getEofLrfAdditions?trns_si_no="+trns_id+"&psn_no="+aim_psn+"&mtrl_type1=FURNACE_ADDITIONS&mtrl_type2=LADLE_ADDITIONS",
			success : function(data) {
			$("#t33_eof_lrf_addition_tbl_1_id").datagrid("loadData",data[0]);
			$("#t33_eof_lrf_addition_tbl_2_id").datagrid("loadData",data[1]);
			}
		});
	}
	function saveT33_2EofLrfAddDtls(){
		var fur_addns = $('#t33_2_eof_lrf_addition_tbl_id').datagrid('getRows');
	
		for (var i = 0; i < fur_addns.length; i++) {
			$('#t33_2_eof_lrf_addition_tbl_id').datagrid('endEdit', i);
		}
		
		formData = {
		"trns_si_no" : document.getElementById('t33_2_trns_si_no').value,
		"lrfAdditions" : fur_addns 
		};
		
		$.ajax({
		headers: { 
	 	'Accept': 'application/json',
	 	'Content-Type': 'application/json' 
	 	},
	 	type: 'POST',
	 	data: JSON.stringify(formData),
	 	dataType: "json",
	 	url: './LRFproduction/SaveOrUpdateMtrlConsumptions',
	 	success: function(data) {
			if(data.status == 'SUCCESS'){
   		    	$.messager.alert('LRF Additions',data.comment,'info');
   		    	refreshT33_2EofLrfAddDtls();
			}else {
   		    	$.messager.alert('LRF Additions',data.comment,'info');
			}
		},
	 	error:function(){      
			$.messager.alert('Processing','Error while Processing Ajax...','error');
	 	}
		});
	}
	function refreshT33_2EofLrfAddDtls(){
		var heat_no = $("#t33_2_heat_no").textbox("getText");
		$.ajax({
			headers : {
			'Accept' : 'application/json',
			'Content-Type' : 'application/json'
			},
			type : 'GET',
			dataType : "json",
			url : "./LRFproduction/getEofLrfAdditions?heat_no="+heat_no+"&mtrl_type=LRF_ARC_ADDITIONS",
			success : function(data) {
			$("#t33_2_eof_lrf_addition_tbl_id").datagrid("loadData",data);
			}
		});
	}
</script>