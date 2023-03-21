<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div style="padding-top: 10px; padding-left: 10px; padding-right: 10px;">
	<div align="center" style="padding-top: 5px; padding-bottom: 5px;">
		<table>
			<tr>
				<td><label><b><spring:message code="label.t3.prodDate"/></b></label></td>
				<td><input name="productionDate" type="text"
					id="t60_productionDate" style="width: 130px;"
					class="easyui-datebox" data-options="required:true,editable:false"></td>
				<td>
			<label><b>Total Heats(Nos)</b></label>
         	<input name="total_heats"  type="text" id="t61_total_heats"  class="easyui-textbox" style="width: 80px;" data-options="editable:false">
        	</td> 
			</tr>
		</table>
	</div>

	<table id="t60_daywiseMtrlTbl" title="Material List" toolbar="#t60_day_consumptions_btn_div_id" 
		class="easyui-datagrid"  width="90%" style="height:auto;" iconCls='icon-ok'  
		resizable="true" remoteSort="false" rownumbers="true" singleSelect="true">
		<thead>
			<tr>
			<th field="mtrlType" width="150px"><b>Material Type</b></th>
			<th field="subUnit" sortable="true" width="100px"><b>Unit</b></th>
			<th field="mtrlName" sortable="true" width="200px"><b>Material Name</b></th>
			<th field="sapMtrlId"><b>SAP Material ID</b></th>
			<th field="valuationType"><b>Valuation Type</b></th>
			<th field="uom" width="100px"><b>UOM</b></th>
			<th field="consQty"  width="150px" data-options="editor:{type:'numberbox',options:{min:0,precision:3}}"><b>Quantity</b></th>
			<th field="mtrlId" hidden="true" ></th>
			<th field="mtrlConsSlNo"  hidden="true"></th>
			<th field="subUnitId" hidden="true"></th>
			<th field="updateCounter"  hidden="true" data-options="editor:{type:'numberbox',options:{editable:false}}"></th>
			</tr>
		</thead>
	</table>
	
	<div align="left" id="t60_day_consumptions_btn_div_id">
	<a href="javascript:void(0)" class="easyui-linkbutton" id="btnT60Save" iconCls="icon-save" onclick="saveT60DayConsumptions()" style="width:90px">Save</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload" onclick="loadTblData()" style="width:90px">Refresh</a>
	</div>  
</div>
<script>
	$(window).load(setTimeout(loadScreenData, 1));
	$('#t60_daywiseMtrlTbl').edatagrid({	
		onBeginEdit : function(index,row) {
			var editors = $('#t60_daywiseMtrlTbl').datagrid(
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
	function loadScreenData() {
		var d = new Date(); 
		d.setDate(d.getDate() - 1);
		$('#t60_productionDate').datebox({
			value : formatDate(d)
		});
		loadTblData();
		applyT60Filter();
	}
	function isConsPosted(prodDate){
		$.ajax({
			headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json'
			},
			type : 'GET',
			dataType : "json",
			url : './daywiseMtrlCons/checkConsumptionPosting?productionDate='+prodDate,
			success : function(data) {
				if(data){
					$('#btnT60Save').linkbutton('disable');
				}else{
					$('#btnT60Save').linkbutton('enable');
				}
			},
			error : function() {
				$.messager.alert('Processing', 'Error while Processing Ajax...', 'error');
			}
		});
	}
	function applyT60Filter() {
		$('#t60_daywiseMtrlTbl').datagrid('enableFilter');
	}
	$("#t60_productionDate").datebox({
		onSelect : function() {
			loadTotalHeats();
		}
	});
	function loadTblData() {
		var totalHeatsProduced =$('#t61_total_heats').textbox('getText');
		var prodDate = $('#t60_productionDate').datebox('getText');
		
		isConsPosted(prodDate);
		$.ajax({
			headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json'
			},
			type : 'GET',
			//data: JSON.stringify(formData),
			dataType : "json",
			url : './daywiseMtrlCons/getDaywiseMtrlCons?prodDate=' + prodDate,
			success : function(data) {
				$('#t60_daywiseMtrlTbl').datagrid('loadData', data);
			},
			error : function() {
				$.messager.alert('Processing',
						'Error while Processing Ajax...', 'error');
			}
		})
		$('#t60_daywiseMtrlTbl').datagrid({ rowStyler:function(index,row){
			if(row.consQty>0 && row.consQty !=null)
	        return 'background-color:pink;color:blue;font-weight:bold;';
	    } 
	});
	}
	function saveT60DayConsumptions(){
		var table_rows = $("#t60_daywiseMtrlTbl").datagrid("getRows");
		var totalHeatsProduced =$('#t61_total_heats').textbox('getText');
		for(var i=0; i<table_rows.length; i++){
			$('#t60_daywiseMtrlTbl').datagrid('endEdit', i);
 	  	}
		
		var prodDate = $('#t60_productionDate').datebox('getText');
		
		formData = {
			mtrlConsDtoLi : table_rows
		};
		
		$.ajax({
			headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json'
			},
			type : 'POST',
			data : JSON.stringify(formData),
			dataType : "json",
			url : './daywiseMtrlCons/saveOrUpdateDayConsumptions?prodDate=' +prodDate+'&totalHeatsProduced=' +totalHeatsProduced,
			success : function(data) {
				if (data.status == 'SUCCESS') {
					$.messager.alert('Day Consumption', 'Saved Successfully', 'info');
				} else {
					$.messager.alert('Day Consumption', data.comment, 'info');
				}
			},
			error : function() {
				$.messager.alert('Processing',
							'Error while Processing Ajax...', 'error');
			}
		});
	}
	function loadTotalHeats(){
		var prodDate = $('#t60_productionDate').datebox('getText');
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
				//console.log(data);
		    	$("#t61_total_heats").textbox("setText",data.length);
		    	loadTblData();
			},
			error : function() {
				$.messager.alert('Processing',
						'Error while Processing Ajax...', 'error');
			}
		})
	}
	
	
	$('#t60_daywiseMtrlTbl')
			.edatagrid(
					{
						onAfterEdit : function(index, row) {
							if (row.uom === "EA") {
								var result = (row.consQty - Math
										.floor(row.consQty)) != 0;
								if (result) {
									row.consQty = Math.round(row.consQty);
									$.messager.alert('Day Consumption',
											'Decimal number cant be allowed for EA(UOM)it is converted to : '
													+ row.consQty, 'info');
								}
							}
						}
					});
</script>