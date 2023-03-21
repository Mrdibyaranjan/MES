<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<script src="${pageContext.request.contextPath}/js/CasterProductionDetails.js" async></script>
<script  src="${pageContext.request.contextPath}/js/common.js"></script>

<div  style="padding-top: 20px;padding-left: 20px;padding-right: 5px;">
<table style="width: 95%" class="easyui-panel">
	<tr style="height: 30px;">
	<td>
		<label style="padding-left: 5px"><b><spring:message code="label.t23.casterProductionUnit"/></b></label>
        <input name="sub_unit_id" type="text" id="t51_sub_unit_id" 
       		class="easyui-combobox" style="width:100px;" 
        	data-options="required:true,panelHeight: 'auto',editable:false,
        	url:'./CommonPool/getComboList?col1=sub_unit_id&col2=sub_unit_name&classname=SubUnitMasterModel&status=1&wherecol=unitDetailsMstrMdl.unit_name in (\'CCM\') and record_status=',
        	method:'get',valueField:'keyval',textField:'txtvalue'" />
	</td>
	<td>&nbsp;</td>
	<td>
		<label style="padding-left: 5px"><b><spring:message code="label.t17.lrfChemHeatNo"/></b></label>
		<input name="heat_no"  type="text" id="t51_heat_no"  class="easyui-combobox" data-options="panelHeight:100,editable:true,required:true,
     	valueField:'keyval', textField:'txtvalue'"  style="width:120px">
	</td>
	<td>
		<label style="padding-left: 1px"><b>Steel Qty(Tons)</b></label>
    	<input name="steel_qty"  type="text" id="t51_steel_qty"  class="easyui-textbox" data-options="editable:false" style="width:100px">
	</td>
	<td>&nbsp;</td>
	<td>
		<label style="padding-left: 5px"><b><spring:message code="label.t17.lrfChemAimPsn"/></b></label>
    	<input name="aim_psn"  type="text" id="t51_aim_psn"  class="easyui-textbox" data-options="editable:false" style="width:150px">
	</td>
	<td>&nbsp;</td>
	<td>
		<label style="padding-left: 5px"><b>Plan Id</b></label>
    	<input name="plan_id"  type="text" id="t51_plan_id"  class="easyui-textbox" data-options="editable:false" style="width:80px">
	</td>
	<td>
		<label style="padding-left: 1px"><b>Plan Section</b></label>
    	<input name="plan_section"  type="text" id="t51_plan_section"  class="easyui-textbox" data-options="editable:false" style="width:120px">
	</td>
	<td>
		<label style="padding-left: 1px"><b>Plan Length(m)</b></label>
    	<input name="plan_length"  type="text" id="t51_plan_length"  class="easyui-textbox" data-options="editable:false" style="width:80px">
	</td>
	<td>
		<label style="padding-left: 1px"><b>Plan Qty(Tons)</b></label>
    	<input name="plan_id"  type="text" id="t51_plan_qty"  class="easyui-textbox" data-options="editable:false" style="width:80px">
	</td>
	</tr>
</table>
<input type="hidden" id="t51_cs_weight">
<input type="hidden" id="t51_billet_max_length">
<input type="hidden" id="t51_material">
<input type="hidden" id="t51_heat_counter">
<!-- <input type="hidden" id="t51_sap_ud_code"> -->
<br>
<table width="90%" id="t51_prod_batches_reconcile_tbl_id" toolbar="#t51_prod_batches_reconcile_btn_div_id"  title="Actual Batch Details" class="easyui-datagrid" style="height:auto;"
	iconCls='icon-ok' resizable="true" remoteSort="false" rownumbers="true" singleSelect="true" showFooter="true"> 
	<thead>
	<tr>
	
		<th field="batch_no" sortable="true" width="150px">Batch No</th>
	     <th field="section" sortable="false" width="120px">Material</th>
	    <th field="act_len" width="150" data-options="editor:{type:'numberbox',options:{precision:3,required:true}}">Actual Length(m)</th>
	    <th field="act_batch_wgt" width="150" data-options="editor:{type:'numberbox',options:{precision:3,editable:false}}">Actual Weight(Tons)</th>         
	    <th field="batch_trns_id" hidden="true"></th>
	    <th field="strandNo" hidden="true">Strand No</th>
	    <th field="product" hidden="true">CCM Product Id</th>
	   
	   
	   <!-- <th field="sap_ud_code" width="150px" class="easyui-combobox"
					formatter="(function(v,r,i){return formatColumnData('CCMBatchDetailsModel.LookupMasterModel.lookup_value',v,r,i);})"
					editor="{type:'combobox',options:{required:true,editable:true,valueField:'keyval',textField:'txtvalue',
					method:'get',url:'./CommonPool/getComboList?col1=lookup_id&col2=lookup_value&classname=LookupMasterModel&status=1&wherecol= lookup_type=\'SAP_UD_CODE\' and lookup_status='}}">SAP UD CODE</th> -->
	 	<th field="chem_si_no" hidden="true" editor="{type:'validatebox'}"><b>CHEM_SI_NO</b></th>
					<th field="sap_ud_code" width="130px"
					formatter="(function(v,r,i){return formatColumnData('sapudcodeLkpModel.lookup_value',v,r,i);})"  
					editor="{type:'combobox',options:{required:false,editable:true,valueField:'keyval',textField:'txtvalue',method:'get',url:'./CommonPool/getComboList?col1=lookup_id&col2=lookup_value&classname=LookupMasterModel&status=1&wherecol=lookup_type=\'SAP_UD_CODE\' and lookup_status='}}">Sap ud code</th>
					
					<!-- formatter="(function(v,r,i){return formatColumnData('CCMBatchDetailsModel.lookup_value',v,r,i);})" -->					
	</tr>
	</thead>	
</table>

<div align="left" id="t51_prod_batches_reconcile_btn_div_id">
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="saveT51BatchDtls()" style="width:90px">Save</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" onclick="addT51BatchEntry()" id="btn_add_batch" style="width:90px">Add</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" onclick="removeT51BatchEntry()"  id="rmv_add_batch" style="width:90px">Remove</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="sendT51Production()"  style="width:110px">Post to SAP</a>
</div>   
</div>
<div id="t51_strand_selection_id" class="easyui-dialog" style="height:100px;padding:5px 5px;width:40%" closed="true">
	<table>
		<tr>
		<td>Stand No</td>
	    <td><input name="t51_strand_no" type="text" id="t51_strand_no" class="easyui-combobox"  data-options="panelHeight: 'auto',editable:true,required:true,
             valueField:'keyval', textField:'txtvalue'" style="width:100px">
       	<td colspan="2" style="padding-left: 40px">
       	  <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="getBatchId()">Proceed</a>
      	</td>
        </tr>
	</table>
</div>
<script type="text/javascript">

$('#t51_prod_batches_reconcile_tbl_id').edatagrid({	
	onAfterEdit:function(index, row){
		var table_rows = $("#t51_prod_batches_reconcile_tbl_id").datagrid("getRows");
		
		footer_BatchWght(table_rows);
	},
	onBeginEdit : function(index,row) {
		var editors = $('#t51_prod_batches_reconcile_tbl_id').datagrid(
		'getEditors', index);
        var length = $(editors[0].target);            
        var wgtBox = $(editors[1].target);
        
        length.numberbox({
			onChange : function() {
				var billet_max_length=document.getElementById('t51_billet_max_length').value;
				var cut_len = parseFloat(length.numberbox('getText'));
				var cs_wgt = parseInt(document.getElementById('t51_cs_weight').value);
				var wgt = cs_wgt/1000;
				var batch_no = row.batch_no;
				
				if(cut_len<=parseFloat(billet_max_length) && cut_len>=0){
					wgtBox.textbox("setText",(wgt*cut_len));
				}else{
                    $.messager.alert('INFO','Actual length should be less than '+parseFloat(billet_max_length)+" for:"+batch_no,'info');
				}
	 		}
        });
			
		var dg = $(this);
		var editors = dg.edatagrid('getEditors',index);
		for(var i=0; i<editors.length; i++){
			$(editors[i].target).numberbox('textbox').bind('keydown',function(e){
			if (e.keyCode == 13){
		        dg.edatagrid('endEdit', index);
		        if (index<dg.edatagrid('getRows').length-1){
		          dg.edatagrid('selectRow', index+1).edatagrid('beginEdit', index+1);
		        }
			}
			})
		}
		if (editors.length){
			$(editors[0].target).numberbox('textbox').focus();
		}
	}
});

$("#t51_sub_unit_id").combobox({
  	onSelect:function(record){
  		loadHeatNoCombo(record.keyval);
  		getMaxBilletLength();
  		}
});
function getMaxBilletLength(){
	var lkp_type = 'BILLET_MAX_LENGTH';
	var lkp_status = 1;
	$.ajax({
		headers : {
			'Accept' : 'application/json',
			'Content-Type' : 'application/json'
		},
		type : 'GET',
		dataType : "json",
		url : './MstrLookup/getLookupDetailsbyTypeStatus?lookupType='+lkp_type+'&status='+lkp_status,
		success : function(data) {
			for (var i=0;i<data.length;i++){
				document.getElementById('t51_billet_max_length').value = data[i].lookup_value;
			}
		}
	});
}
function loadHeatNoCombo(sub_unit_id){
	$.ajax({
		headers: { 
			'Accept': 'application/json',
	     	'Content-Type': 'application/json' 
	    },
	    type: 'GET',
	    url: './casterProduction/getCCMCompletedHeats?sub_unit_id='+sub_unit_id,
	    dataType: 'json',
	    success: function(data){
	    	var heatNoLi=[];
	    	for (var i=0;i<data.length;i++){
				var v={
					id:data[i].keyval,
 					keyval:data[i].keyval,
 					txtvalue:data[i].txtvalue
 				};
				heatNoLi.push(v);		
 			}
	    	
	    	$("#t51_heat_no").combobox('loadData', heatNoLi);
	    },
	    error: function(){
	    	$.messager.alert('Processing','Error while Processing Ajax...','error');
	    }
	});
}
$("#t51_heat_no").combobox({
  	onSelect:function(record){
  		$.ajax({
  			headers: { 
  				'Accept': 'application/json',
  		     	'Content-Type': 'application/json' 
  		    },
  		    type: 'GET',
  		    url: './casterProduction/getCcmHeatByTrns?trns_sl_no='+record.keyval,
  		    dataType: 'json',
  		    success: function(data){
  		    	console.log(data);
  		    	$("#t51_steel_qty").textbox("setText",data.steel_ladle_wgt);
  		    	$("#t51_aim_psn").textbox("setText",data.psnHdrMstrMdl.psn_no);
  		    	$("#t51_plan_id").textbox("setText",data.heat_plan_id);
  		    	$("#t51_plan_section").textbox("setText",data.sectionLookupModel.lookup_value);
  		    	document.getElementById('t51_heat_counter').value = data.heat_counter;
  		    	document.getElementById('t51_cs_weight').value = data.productMasterMdl.section_wgt;
  		    	var material = data.productMasterMdl.prodSecMtrlLookupModel.lookup_value;
  		    	document.getElementById('t51_material').value = material;
  		    	//$("#t51_sap_ud_code").textbox("setText",data.ccmProdHeatDtls.ccmBatchDtls.LookupMasterModel.sap_ud_code);
  		    	/* document.getElementById('t51_sap_ud_code').value = data.CCMBatchDetailsModel.LookupMasterModel.sap_ud_code; */
  		    	/* var sap_ud_code =data.sap_ud_code;
  		    	document.getElementById('t5_sap_ud_code').value = sap_ud_code;
  		    	alert(sap_ud_code); */
  		      /*  var sapudcode=data.ccmProdHeatDtls.ccmBatchDtls.sap_ud_code;
  		    	document.getElementById('t51_sap_ud_code').value = sapudcode;
  		    	alert(sapudcode);   */
  		        var batchDtlsLi=[];
  		    	for (var j = 0; j < data.ccmProdHeatDtls.length; j++) {
  		    		$("#t51_plan_length").textbox("setText", data.ccmProdHeatDtls[j].cut_length);
  		    		for(var k = 0; k < data.ccmProdHeatDtls[j].ccmBatchDtls.length; k++){
  		    			var udObj = {
  		  	  				"lookup_id" : data.ccmProdHeatDtls[j].ccmBatchDtls[k].sapudcodeLkpModel.lookup_id,
  		  	  				"lookup_value" : data.ccmProdHeatDtls[j].ccmBatchDtls[k].sapudcodeLkpModel.lookup_value
  		  	  			};
  		    			var v={
  		    				batch_no:data.ccmProdHeatDtls[j].ccmBatchDtls[k].batch_no,
  		    				section:material,
  		   					act_len:data.ccmProdHeatDtls[j].ccmBatchDtls[k].act_len,
  		   					act_batch_wgt:data.ccmProdHeatDtls[j].ccmBatchDtls[k].act_batch_wgt,
  		   					batch_trns_id:data.ccmProdHeatDtls[j].ccmBatchDtls[k].batch_trns_id,
  		   					strandNo:data.ccmProdHeatDtls[j].stand_id,
  		   					product:data.ccmProdHeatDtls[j].prod_trns_id,
  		   				    sap_ud_code:data.ccmProdHeatDtls[j].ccmBatchDtls[k].sapudcodeLkpModel.lookup_id,
  		   					sapudcodeLkpModel:udObj
  	 					};
  		    			
  		    			batchDtlsLi.push(v);
  		    		}
  		    	}
  		    	 batchDtlsLi.sort(compare);
  		    	$("#t51_plan_qty").textbox("setText",data.heatPlanMdl.planned_qty);
  		    	footer_BatchWght(batchDtlsLi);
  		    	$("#t51_prod_batches_reconcile_tbl_id").datagrid("loadData",batchDtlsLi);
  		    },
  		    error: function(){
  		    	$.messager.alert('Processing','Error while Processing Ajax...','error');
  		    }
  		});
  	},onChange:function(){
  		$("#t51_steel_qty").textbox("setText", '');
  		$("#t51_aim_psn").textbox("setText", '');
  		$("#t51_plan_id").textbox("setText", '');
  		$("#t51_plan_section").textbox("setText", '');
	    $("#t51_plan_length").textbox("setText", '');
	    $("#t51_plan_qty").textbox("setText", '');
	    document.getElementById('t51_heat_counter').value = '';
	    document.getElementById('t51_cs_weight').value = '';
	    document.getElementById('t51_material').value = '';
	   // document.getElementById('t51_sap_ud_code').value = '';
	    $('#t51_prod_batches_reconcile_tbl_id').datagrid("loadData",[]);
	}
});
function addT51BatchEntry(){
	$('#t51_strand_selection_id').dialog({
		modal : true,
		cache : true
	});
	$('#t51_strand_selection_id').dialog('open').dialog('center').dialog(
			'setTitle', 'Add Batch Entry');
	var url1 = "./CommonPool/getComboList?col1=lookup_id&col2=lookup_value&classname=LookupMasterModel&status=1&wherecol=lookup_type='CCM_STAND_NO' and lookup_status=";
	getDropdownList(url1, '#t51_strand_no');
}
function getBatchId(){
	var rows = $('#t51_prod_batches_reconcile_tbl_id').datagrid('getRows');
	var act_len = $("#t51_plan_length").textbox("getText");
	var wgt = (parseInt(document.getElementById('t51_cs_weight').value)/1000) * parseFloat(act_len);
	var strandId = $("#t51_strand_no").combobox("getValue");
	
	$.ajax({
		headers : {
			'Accept' : 'application/json',
			'Content-Type' : 'application/json'
		},
		type : 'GET',
		dataType : "json",
		url : './casterProduction/getBatchNo?trns_sl_no='+$("#t51_heat_no").combobox("getValue")+'&strand_id='+strandId+'&strand_no='+$("#t51_strand_no").combobox("getText"),
		success : function(data) {
			var retVal = data.comment;
			var retValArr = retVal.split("_");
			$('#t51_prod_batches_reconcile_tbl_id').edatagrid('addRow', {
				row : {
					batch_no:retValArr[0],
	    			section:document.getElementById('t51_material').value,
	   				act_len:act_len,
	   				act_batch_wgt:wgt,
	   				batch_trns_id:0,
	   				strandNo:strandId,
	   				product:retValArr[1],
			      //  sap_ud_code:sapudcode
	   				
				}
			});
			$('#t51_strand_selection_id').dialog('close');
		}
	});
}
function removeT51BatchEntry(){
	var row = $('#t51_prod_batches_reconcile_tbl_id').datagrid('getSelected');
	var index = $('#t51_prod_batches_reconcile_tbl_id').datagrid('getRowIndex',row); 
	$('#t51_prod_batches_reconcile_tbl_id').datagrid('deleteRow',index);
	$.messager.alert('INFO',row.batch_no+" removed. Please click on save",'info');
}
function saveT51BatchDtls(){
	var tbl_rows = $('#t51_prod_batches_reconcile_tbl_id').datagrid('getRows');	
	var ccm_heat_trns_sno = $("#t51_heat_no").combobox("getValue");
	var billet_max_length = document.getElementById('t51_billet_max_length').value;
	
	for (var i = 0; i < tbl_rows.length; i++) {
		$('#t51_prod_batches_reconcile_tbl_id').datagrid('endEdit', i);
		var batch_no = tbl_rows[i].batch_no;
		if(tbl_rows[i].act_len>parseFloat(billet_max_length)||tbl_rows[i].act_len<=parseFloat(0)){
			 $.messager.alert('INFO',"Actual length must be in range 1 to "+parseFloat(billet_max_length)+" for:"+batch_no,'info');
			 return;	
		}
		if(tbl_rows[i].act_batch_wgt<=parseFloat(0)){
			 $.messager.alert('INFO',"Actual weight cannot be 0 for:"+batch_no,'info');
			 return;
		}
	}
	formData = {
			ccmBatchDetails : tbl_rows
	};
	
	$.ajax({
		headers : {
			'Accept' : 'application/json',
			'Content-Type' : 'application/json'
		},
		type : 'POST',
		data : JSON.stringify(formData),
		dataType : "json",
		url : './casterProduction/BatchReconcileSaveOrUpdate?ccm_heat_trns_sno='+ccm_heat_trns_sno,
		success : function(data) {
			if (data.status == 'SUCCESS') {
				$.ajax({
					headers: { 
		            	'Accept': 'application/json',
		                'Content-Type': 'application/json' 
					},
		            type: 'GET',
		            url: './casterProduction/getCcmHeatByTrns?trns_sl_no='+record.keyval,
		            dataType: 'json',
		            success: function(data){
		            	var batchDtlsLi=[];
		                var material = data.productMasterMdl.prodSecMtrlLookupModel.lookup_value;
		                for (var j = 0; j < data.ccmProdHeatDtls.length; j++) {                 
							for(var k = 0; k < data.ccmProdHeatDtls[j].ccmBatchDtls.length; k++){
								var udObj = {
			  		  	  				"lookup_id" : data.ccmProdHeatDtls[j].ccmBatchDtls[k].sapudcodeLkpModel.lookup_id,
			  		  	  				"lookup_value" : data.ccmProdHeatDtls[j].ccmBatchDtls[k].sapudcodeLkpModel.lookup_value
			  		  	  			};
		                    	var v={
		                        	batch_no:data.ccmProdHeatDtls[j].ccmBatchDtls[k].batch_no,
		                            section:material,
		                            act_len:data.ccmProdHeatDtls[j].ccmBatchDtls[k].act_len,
		                            act_batch_wgt:data.ccmProdHeatDtls[j].ccmBatchDtls[k].act_batch_wgt,
		                            batch_trns_id:data.ccmProdHeatDtls[j].ccmBatchDtls[k].batch_trns_id,
		                            strandNo:data.ccmProdHeatDtls[j].stand_id,
		                            product:data.ccmProdHeatDtls[j].prod_trns_id,
		                            sap_ud_code:data.ccmProdHeatDtls[j].ccmBatchDtls[k].sap_ud_code,
		                            sap_ud_code:data.ccmProdHeatDtls[j].ccmBatchDtls[k].sapudcodeLkpModel.lookup_id,
		  		   					sapudcodeLkpModel:udObj
		                           
								};
		                        batchDtlsLi.push(v);
							}
							
						}
		                batchDtlsLi.sort(compare);
		                footer_BatchWght(batchDtlsLi);
		                
		                $("#t51_prod_batches_reconcile_tbl_id").datagrid("loadData",batchDtlsLi);
		                
		               
		              },
		              error: function(){
		                  $.messager.alert('Processing','Error while Processing Ajax...','error');
		              }
		          });
				$.messager.alert('Production Reconcile', 'Batches Saved Successfully', 'info');
			} else {
				$.messager.alert('Production Reconcile', data.comment, 'info');
			}
		},
		error : function() {
			$.messager.alert('Processing',
						'Error while Processing Ajax...', 'error');
		}
	});
}
function sendT51Production(){
	$.messager.confirm('Confirm', 'Do you want to post production data to SAP', function(r){
		if (r){
			var ccm_heat_trns_sno = $("#t51_heat_no").combobox("getValue");
			var heat_no = $("#t51_heat_no").combobox("getText");
			var heat_counter = document.getElementById('t51_heat_counter').value;
			
			$.ajax({
				headers : {
					'Accept' : 'application/json',
					'Content-Type' : 'application/json'
				},
				type : 'POST',
				dataType : "json",
				url : './casterProduction/CCMBatchProductionPosting?ccm_heat_trns_sno='+ccm_heat_trns_sno+'&heat_no='+heat_no+'&heat_counter='+heat_counter,
				success : function(data) {
					if (data.status == 'SUCCESS') {
						
						$.messager.alert('Production Reconcile', data.comment, 'info');
						
						$('#t51_heat_no').combobox('setValue', '');
						$('#t51_sub_unit_id').combobox('setValue','');
						
						//loadHeatNoCombo(sub_unit_id);
						//reloadCombos();
						
					} else {
						$.messager.alert('Production Reconcile', data.comment, 'info');
					}
				},
				error : function() {
					$.messager.alert('Processing',
								'Error while Processing Ajax...', 'error');
				}
			});			
		}
	});
}
/* $(document).ready(function(){
	$('.t51_heat_no').combobox() */
/* function reset(){	
	$("#reset").on("click",function(){
		$('.t51_heat_no').val('');
		$('.t51_heat_no').data('t51_heat_no','refresh');
	});

}); */
/* function reloadCombos() {
	 $('#t51_sub_unit_id').combobox('reload', './CommonPool/getComboList?col1=sub_unit_id&col2=sub_unit_name&classname=SubUnitMasterModel&status=1&wherecol=unitDetailsMstrMdl.unit_name in (\'CCM\') and record_status=');
	 $('#t51_sub_unit_id').combobox('getValue');
	 
} */
function compare(a, b) {
	const arr1 = a.batch_no;
	const arr2 = b.batch_no;

	let comparison = 0;
	if (arr1 > arr2) {
	   comparison = 1;
	} else if (arr1 < arr2) {
		comparison = -1;
	}
	return comparison;
}
function footer_BatchWght(data){
	footer_act_wght=0;
	for(i=0;i<data.length;i++){
		if(data[i].act_batch_wgt===null){
			$.messager.alert('Processing','NULL value in actaul weight.Please check','info');
		}
		footer_act_wght = footer_act_wght + parseFloat(data[i].act_batch_wgt);
	}
	$('#t51_prod_batches_reconcile_tbl_id').datagrid('reloadFooter',[
		{act_len: 'Total Weight', act_batch_wgt: Number(footer_act_wght).toFixed(3)}
	]);		
}
</script>