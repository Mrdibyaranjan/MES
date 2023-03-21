<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div style="padding-top: 10px; padding-left: 10px; padding-right: 10px;">
	<div style="padding-top: 5px; padding-bottom: 5px;">
		<table style="width: 90%">
		<tr>
		
        	<td><label><b><spring:message code="label.t3.prodDate"/></b></label>
			<input name="productionDate" type="text" id="t62_productionDate" style="width: 120px;"
				class="easyui-datebox" data-options="required:true,editable:false"></td> 
			<td align="left">
        	<label><b><spring:message code="label.t6.eofLadleHeatNo"/></b></label>
         	<input name="heat_no"  type="text" id="t62_heatNo"  class="easyui-combobox" data-options="panelHeight:'auto',editable:false,required:true,
     		valueField:'keyval', textField:'txtvalue'"  style="width:120px">
        	</td>  
        	<td> 
			<label><b>Heat Quantity</b></label>
         	<input name="heatQty"  type="text" id="t62_heatQty"  class="easyui-textbox" data-options="editable:false" style="width:80px">
        	</td>
			<td>
			<label><b>Total Heats(Nos)</b></label>
         	<input name="total_heats"  type="text" id="t62_total_heats"  class="easyui-textbox" style="width: 80px;" data-options="editable:false">
        	</td>  
        	<td>
       	  	<a href="javascript:void(0)"  style="width: 150px;" class="easyui-linkbutton" id="t62_postSap_btn" iconCls="icon-ok" onclick="postT62MtrlConsumptions()" >Post to SAP</a>
       		</td>
		</tr>
    	</table>
	</div>

	<table width="97%">
	<tr><td valign="top">
	<table id="t62_mtrlConsTbl" title="Material List" class="easyui-datagrid" iconCls='icon-ok' width="99%" style="height:auto;"
		resizable="true" remoteSort="false" rownumbers="true" singleSelect="true" showFooter="true">
		<thead>
			<tr>
			<th field="workCenter" width="70px"><b>Prod Unit<b></b></th>
			<th field="mtrlType" width="160px"><b>Material Type</b></th>
			<th field="mtrlName" sortable="true" width="200px"><b>Material Name</b></th>
			<th field="component" width="150px"><b>SAP Material ID</b></th>
			<th field="componentType" width="80px"><b>Para Type</b></th>
			<th field="valuationType"><b>Valuation Type</b></th>
			<!-- <th field="uom" width="80px"><b>UOM</b></th> -->
			<th field="uom" width="80px"><b>UOM</b></th>
			<th field="storageLocation" width="80px" ><b>Storage Location</b></th>
			<th field="qty" width="80px"><b>Quantity</b></th>
			<th field="sapuom" width="80px" hidden="true"><b>SAP UOM</b></th>
			
			</tr>
		</thead>
	</table>
	</td></tr>
	</table>
</div>
<script>
	$(window).load(setTimeout(loadScreenData, 1));
	function loadScreenData() {
		var d = new Date(); 
		d.setDate(d.getDate() - 1);
		$('#t62_productionDate').datebox({
			value : formatDate(d)
		});
		loadHeatNoCombo();
	}
	$("#t62_productionDate").datebox({
		onSelect : function() {
			$('#t62_heatNo').combobox('setValue', '');
			$('#t62_heatQty').textbox('setValue', '');
			var dummydata = new Array();
			$('#t62_mtrlConsTbl').datagrid('loadData', dummydata);
			loadHeatNoCombo();
		}
	});

	function loadHeatNoCombo(){
		var prodDate = $('#t62_productionDate').datebox('getText');
		$.ajax({
			headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json'
			},
			type : 'GET',
			//data: JSON.stringify(formData),
			dataType : "json",
			url : './mtrlConsumption/getProdPostedHeats?prodDate=' + prodDate,
			success : function(data) {
				if(data.length > 0){
				var heatNoLi=[];
		    	for (var i=0;i<data.length;i++){
					var v={
						id:data[i].heat_track_id,
	 					keyval:data[i].heat_track_id,
	 					txtvalue:data[i].heat_id
	 				};
					heatNoLi.push(v);		
	 			}
		    	$("#t62_heatNo").combobox('loadData', heatNoLi);
				}else{
					var dummydata = new Array();
					$('#t62_heatNo').combobox('loadData', dummydata);
					$('#t62_heatNo').combobox('setValue', '');
				}
		    	$("#t62_total_heats").textbox("setText",data.length);
			},
			error : function() {
				$.messager.alert('Processing',
						'Error while Processing Ajax...', 'error');
			}
		})
	}
	$("#t62_heatNo").combobox({
		onSelect : function(record) {
			$('#t62_heatQty').textbox('setValue', '');
			var dummydata = new Array();
			$('#t62_mtrlConsTbl').datagrid('loadData', dummydata);
			setHeatQtyAndTblData(record.keyval, record.txtvalue);
		}
	});
	
	function setHeatQtyAndTblData(heatTrackId, heatNo){		
		$.ajax({
			headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json'
			},
			type : 'GET',
			//data: JSON.stringify(formData),
			dataType : "json",
			url : './mtrlConsumption/getHeatQuantity?heatTrackId='+heatTrackId+'&heatNo='+heatNo,
			success : function(data) {
				$('#t62_heatQty').textbox('setValue', data.heatQty);
				loadTblData();
			},
			error : function() {
				$.messager.alert('Processing',
						'Error while Processing Ajax...', 'error');
			}
		})
	}
	function loadTblData(){
		var heatNo = $('#t62_heatNo').combobox('getText');
		var heatTrackId = $('#t62_heatNo').combobox('getValue');
		var heatQty = $('#t62_heatQty').textbox('getValue');
		var totalHeats = $('#t62_total_heats').textbox('getValue');
		var prodDate = $('#t62_productionDate').datebox('getText');
		
		$.ajax({
			headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json'
			},
			type : 'GET',
			//data: JSON.stringify(formData),
			dataType : "json",
			url : './mtrlConsumption/getHeatwiseMtrlCons?heatNo='+heatNo+'&heatTrackId='+heatTrackId+'&heatQty='+heatQty+'&totalHeats='+totalHeats+'&prodDate='+prodDate,
			success : function(data) {
				$('#t62_mtrlConsTbl').datagrid('loadData', data);
			},
			error : function() {
				$.messager.alert('Processing',
						'Error while Processing Ajax...', 'error');
			}
		})
	}
	
	function postT62MtrlConsumptions(){
		var table_rows = $("#t62_mtrlConsTbl").datagrid("getRows");
		
		for(var j=0; j<table_rows.length; j++){
			$('#t62_mtrlConsTbl').datagrid('endEdit', j);
 	  	}
	    for(var i=0; i<table_rows.length; i++){
	    	if(table_rows[i].component == null || table_rows[i].component == ''){
	    		$.messager.alert('Info','SAP Material ID is missing for the Material '+table_rows[i].mtrlName);

	    		return false;
	    	}
	    	if(table_rows[i].storageLocation == null || table_rows[i].storageLocation == ''){
	    		$.messager.alert('Info','Storage Location is missing for the Material '+table_rows[i].mtrlName);

	    		return false;
	    	}
	    }
		var consDate = $('#t62_productionDate').datebox('getText');
		var heatNo = $('#t62_heatNo').combobox('getText');
		
		formData = {
			actHeatNo : heatNo,
			heatTrackId : $('#t62_heatNo').combobox('getValue'),
			mtrlConsLi : table_rows
		};
		
		$.ajax({
			headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json'
			},
			type : 'POST',
			data : JSON.stringify(formData),
			dataType : "json",
			url : './mtrlConsumption/postMtrlConsumptions?consDate='+consDate,
			success : function(data) {
				if (data.status == 'SUCCESS') {
					$.messager.alert('Material Consumption', 'Posted Successfully', 'info');
				} else {
					$.messager.alert('Material Consumption', data.comment, 'info');
				}
				$('#t62_heatNo').combobox('setValue', '');
				$('#t62_heatQty').textbox('setValue', '');
				var dummydata = new Array();
				$('#t62_mtrlConsTbl').datagrid('loadData', dummydata);
				loadHeatNoCombo();
			},
			error : function() {
				$.messager.alert('Processing',
							'Error while Processing Ajax...', 'error');
			}
		});
	}
</script>