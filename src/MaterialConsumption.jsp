<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div style="padding-top: 10px; padding-left: 10px; padding-right: 10px;">
	<div style="padding-top: 5px; padding-bottom: 5px;">
		<table style="width: 98%">
		<tr>
        	<td><label><b><spring:message code="label.t3.prodDate"/></b></label>
			<input name="productionDate" type="text" id="t61_productionDate" style="width: 100px;"
				class="easyui-datebox" data-options="required:true,editable:false"></td> 
			<td align="left">
        	<label><b><spring:message code="label.t6.eofLadleHeatNo"/></b></label>
         	<input name="heat_no"  type="text" id="t61_heatNo"  class="easyui-combobox" data-options="panelHeight:'auto',editable:false,required:true,
     		valueField:'keyval', textField:'txtvalue'"  style="width:100px">
        	</td>  
        	<td>
        	<label><b><spring:message code="label.t4.eofProductionUnit"/></b></label>
         	<!-- <input name="sub_unit_id" type="text" id="t61_productionUnit" class="easyui-combobox" style="width:100px;" 
        	data-options="required:true,panelHeight: 'auto',editable:false,url:'./CommonPool/getComboList?col1=sub_unit_id&col2=sub_unit_name&classname=SubUnitMasterModel&status=1&wherecol=record_status=',
        	method:'get',valueField:'keyval',textField:'txtvalue'" />  -->
        	<input name="sub_unit_id" type="text" id="t61_productionUnit" class="easyui-combobox" style="width:80px;" 
        	data-options="required:true,panelHeight: 'auto',editable:false,valueField:'keyval',textField:'txtvalue'" />
        	</td>
       		<td> 
			<label><b><spring:message code="label.t5.eofFurnaceAimPsn"/></b></label>
         	<input name="aim_psn"  type="text" id="t61_aim_psn"  class="easyui-textbox" data-options="editable:false" style="width:140px">
        	</td>
        	<td> 
			<label><b>Grade</b></label>
         	<input name="grade"  type="text" id="t61_grade"  class="easyui-textbox" data-options="editable:false" style="width:80px">
        	</td>
        	<td> 
			<label><b>Actual Path</b></label>
         	<input name="actPath"  type="text" id="t61_actPath"  class="easyui-textbox" data-options="editable:false" style="width:180px">
        	</td>
        	<td> 
			<label><b>Heat Quantity</b></label>
         	<input name="heatQty"  type="text" id="t61_heatQty"  class="easyui-textbox" data-options="editable:false" style="width:80px">
        	</td>
			<td>
			<label><b>Total Heats(Nos)</b></label>
         	<input name="total_heats"  type="text" id="t61_total_heats"  class="easyui-textbox" style="width: 80px;" data-options="editable:false">
        	</td>  
		</tr>
    	</table>
	</div>

	<table width="95%">
	<tr><td valign="top">
	<table id="t61_mtrlConsTbl" title="Material List" toolbar="#t61_mtrl_cons_btn_div_id"
		class="easyui-datagrid" iconCls='icon-ok' width="90%" style="height:auto;"
		resizable="true" remoteSort="false" rownumbers="true" singleSelect="true" showFooter="true">
		<thead>
			<tr>
			<th field="mtrlType" width="150px"><b>Material Type</b></th>
			<th field="mtrlName" sortable="true" width="220px"><b>Material Name</b></th>
			<th field="sapMtrlId" width="150px"><b>SAP Material ID</b></th>
			<th field="valuationType"><b>Valuation Type</b></th>
			<th field="uom" width="100px"><b>UOM</b></th>
			<th field="consQty"  width="150px" data-options="editor:{type:'numberbox',options:{min:0,precision:3}}"><b>Quantity</b></th>
			<th field="mtrlId" hidden="true" ></th>
			<th field="mtrlConsSlNo"  hidden="true"></th>
			<th field="trnsSlNo"  hidden="true"></th>
			<th field="updateCounter"  hidden="true" data-options="editor:{type:'numberbox',options:{editable:false}}"></th>
			</tr>
		</thead>
	</table>
	<div align="left" id="t61_mtrl_cons_btn_div_id">
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="saveT61MtrlConsumptions()" style="width:90px">Save</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload" onclick="refreshTblData()" style="width:90px">Refresh</a>
	</div>  
	</td></tr>
	</table>
	
	<table width="95%">
	<tr><td valign="top">
	<table id="t61_dayMtrlConsTbl" title="Daywise Material List" class="easyui-datagrid" iconCls='icon-ok' width="90%" style="height:auto;"
		resizable="true" remoteSort="false" rownumbers="true" singleSelect="true" showFooter="true">
		<thead>
			<tr>
			<th field="mtrlType" width="150px"><b>Material Type</b></th>
			<th field="mtrlName" sortable="true" width="220px"><b>Material Name</b></th>
			<th field="sapMtrlId" width="150px"><b>SAP Material ID</b></th>
			<th field="valuationType"><b>Valuation Type</b></th>
			<th field="uom" width="100px"><b>UOM</b></th>
			<th field="consQty"  width="150px" data-options="editor:{type:'numberbox',options:{min:0,precision:3}}"><b>Quantity</b></th>
			<th field="mtrlId" hidden="true" ></th>
			<th field="mtrlConsSlNo"  hidden="true"></th>
			<th field="trnsSlNo"  hidden="true"></th>
			<th field="updateCounter"  hidden="true" data-options="editor:{type:'numberbox',options:{editable:false}}"></th>
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
		$('#t61_productionDate').datebox({
			value : formatDate(d)
		});
		//loadProdUnitCombo();
		loadHeatNoCombo();
	}
	$("#t61_productionDate").datebox({
		onSelect : function() {
			$('#t61_heatNo').combobox('setValue', '');
			setDefaultValues();
			loadHeatNoCombo();
		}
	});
	/* function loadProdUnitCombo(){	
		$.ajax({
			headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json'
			},
			type : 'GET',
			//data: JSON.stringify(formData),
			dataType : "json",
			url : './mtrlConsumption/getProductionUnits',
			success : function(data) {
				$("#t61_productionUnit").combobox('loadData', data);
			},
			error : function() {
				$.messager.alert('Processing',
						'Error while Processing Ajax...', 'error');
			}
		})
	} */
	function loadProdUnitCombo(heatTrackId){
		$.ajax({
			headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json'
			},
			type : 'GET',
			//data: JSON.stringify(formData),
			dataType : "json",
			url : './mtrlConsumption/getHeatProductionUnits?heat_track_id='+heatTrackId,
			success : function(data) {
				$("#t61_productionUnit").combobox('loadData', data);
			},
			error : function() {
				$.messager.alert('Processing',
						'Error while Processing Ajax...', 'error');
			}
		})
	}
	function loadHeatNoCombo(){
		var prodDate = $('#t61_productionDate').datebox('getText');
		
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
		    	$("#t61_heatNo").combobox('loadData', heatNoLi);
				}else{
					var dummydata = new Array();
					$('#t61_heatNo').combobox('loadData', dummydata);
					$('#t61_heatNo').combobox('setValue', '');
				}
		    	$("#t61_total_heats").textbox("setValue",data.length);
			},
			error : function() {
				$.messager.alert('Processing',
						'Error while Processing Ajax...', 'error');
			}
		})
	}
	$("#t61_heatNo").combobox({
		onSelect : function(record) {
			setDefaultValues();
			loadProdUnitCombo(record.keyval);
		}
	});
	function setDefaultValues(){
		//$('#t61_productionUnit').combobox('setValue', '');
		$('#t61_aim_psn').textbox('setValue', '');
		$('#t61_grade').textbox('setValue', '');
		$('#t61_actPath').textbox('setValue', '');
		var dummydata = new Array();
		$('#t61_mtrlConsTbl').datagrid('loadData', dummydata);
		$('#t61_dayMtrlConsTbl').datagrid('loadData', dummydata);
	}
	$("#t61_productionUnit").combobox({
		onSelect : function(record) {
			$('#t61_aim_psn').textbox('setValue', '');
			$('#t61_grade').textbox('setValue', '');
			$('#t61_actPath').textbox('setValue', '');
			var dummydata = new Array();
			$('#t61_mtrlConsTbl').datagrid('loadData', dummydata);
			$('#t61_dayMtrlConsTbl').datagrid('loadData', dummydata);
			//loadTblData(record.keyval, record.txtvalue);
			loadHeatDetails(record.keyval, record.txtvalue);
		}
	});
	function refreshTblData(){
		var sub_unit_id = $('#t61_productionUnit').combobox('getValue');
		var sub_unit_name = $('#t61_productionUnit').combobox('getText');
		loadTblData(sub_unit_id, sub_unit_name);
	}
	function loadHeatDetails(sub_unit_id, sub_unit_name){
		var heatNo = $('#t61_heatNo').combobox('getText');
		var heatTrackId = $('#t61_heatNo').combobox('getValue');

	   $.ajax({
			headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json'
			},
			type : 'GET',
			//data: JSON.stringify(formData),
			dataType : "json",
			url : './mtrlConsumption/getHeatDetails?heatTrackId='+heatTrackId+'&heatNo='+heatNo+'&subUnitName='+sub_unit_name+'&subUnitId='+sub_unit_id,
			success : function(data) {
				$('#t61_aim_psn').textbox('setValue', data.aimPSN);
				$('#t61_grade').textbox('setValue', data.grade);
				$('#t61_actPath').textbox('setValue', data.actualPath);
				$('#t61_heatQty').textbox('setValue', data.heatQty);
				loadTblData(sub_unit_id, sub_unit_name);
			},
			error : function() {
				$.messager.alert('Processing',
						'Error while Processing Ajax...', 'error');
			}
		})
	}
	function loadTblData(sub_unit_id, sub_unit_name){
		var heatNo = $('#t61_heatNo').combobox('getText');
		var heatTrackId = $('#t61_heatNo').combobox('getValue');
		var heatQty = $('#t61_heatQty').textbox('getValue');
		var totalHeats = parseInt($('#t61_total_heats').textbox('getText'));
		var prodDate = $('#t61_productionDate').datebox('getText');
		
		/*formData={"heatNo": heatNo,"heatTrackId": heatTrackId,"subUnitId":sub_unit_id,"subUnit":sub_unit_name,
      		    "heatQty":heatQty,"totalHeatsProduced":totalHeats};*/
		
		$.ajax({
			headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json'
			},
			type : 'GET',
			//data: JSON.stringify(formData),
			dataType : "json",
			url : './mtrlConsumption/getProdUnitwiseMtrlCons?subUnitId='+sub_unit_id+'&heatNo='+heatNo+'&subUnitName='+sub_unit_name+'&heatTrackId='+heatTrackId+'&heatQty='+heatQty+'&totalHeats='+totalHeats+'&prodDate='+prodDate,
			//url : './mtrlConsumption/getProdUnitwiseMtrlCons?prodDate='+prodDate,
			success : function(data) {
				$('#t61_mtrlConsTbl').datagrid('loadData', data[0]);
				$('#t61_dayMtrlConsTbl').datagrid('loadData', data[1]);
			},
			error : function() {
				$.messager.alert('Processing',
						'Error while Processing Ajax...', 'error');
			}
		})
		$('#t61_mtrlConsTbl').datagrid({ rowStyler:function(index,row){
			if(row.consQty>0 && row.consQty !=null)
	        return 'background-color:pink;color:blue;font-weight:bold;';
	    } 
	});
	   $('#t61_dayMtrlConsTbl').datagrid({ rowStyler:function(index,row){
			if(row.consQty>0 && row.consQty !=null)
	        return 'background-color:pink;color:blue;font-weight:bold;';
	    } 
	});
	}
	
	$('#t61_mtrlConsTbl').edatagrid({	
		onBeginEdit : function(index,row) {
			var editors = $('#t61_mtrlConsTbl').datagrid(
			'getEditors', index);
	        var quantity = $(editors[0].target);            
	        var updCounter = $(editors[1].target);
	        quantity.numberbox({
				onChange : function() {
					updCounter.numberbox("setValue",1);
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
	
	function saveT61MtrlConsumptions(){
		var table_rows = $("#t61_mtrlConsTbl").datagrid("getRows");
		var day_cons_tbl_rows = $("#t61_dayMtrlConsTbl").datagrid("getRows");
		
		for(var i=0; i<table_rows.length; i++){
			$('#t61_mtrlConsTbl').datagrid('endEdit', i);
 	  	}

		var consDate = $('#t61_productionDate').datebox('getText');
		var subUnitName =  $('#t61_productionUnit').combobox('getText');
		var subUnitId = $('#t61_productionUnit').combobox('getValue');
		var heatNo = $('#t61_heatNo').combobox('getText');
		
		formData = {
			heatNo : heatNo,
			subUnitId : subUnitId,
			subUnit : subUnitName,
			mtrlConsDtoLi : table_rows,
			dayConsDtoLi : day_cons_tbl_rows
		};
		
		$.ajax({
			headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json'
			},
			type : 'POST',
			data : JSON.stringify(formData),
			dataType : "json",
			url : './mtrlConsumption/saveOrUpdateMtrlConsumptions?consDate='+consDate,
			success : function(data) {
				if (data.status == 'SUCCESS') {
					$.messager.alert('Material Consumption', 'Saved Successfully', 'info');
				} else {
					$.messager.alert('Material Consumption', data.comment, 'info');
				}
				loadTblData(subUnitId, subUnitName);
			},
			error : function() {
				$.messager.alert('Processing',
							'Error while Processing Ajax...', 'error');
			}
		});		
	}
</script>