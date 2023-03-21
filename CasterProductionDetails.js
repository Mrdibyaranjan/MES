function getHeatsWaitingForCaster() {
	
	/*var url='./casterProduction/getHeatsWaitingForCasterProcess?UNIT_PROCESS_STATUS=WAITING FOR PROCESSING&cunit='+$("#t23_casterUnit").combobox("getText");
    $('#t23_caster_waitingforcast_tbl_id').datagrid({url:url});	
	
    var url_caster_detail='./casterProduction/getCCMsavedHeats?unit_id='+$('#t23_casterUnit').combobox('getValue');
    $('#t23_caster_production_tbl_id').datagrid({url:url_caster_detail});	
	
    */
	$.ajax({
				headers : {
					'Accept' : 'application/json',
					'Content-Type' : 'application/json'
				},
				type : 'GET',
				// data: JSON.stringify(formData),
				dataType : "json",
				url : './casterProduction/getHeatsWaitingForCasterProcess?UNIT_PROCESS_STATUS=WAITING FOR PROCESSING&cunit='+$("#t23_casterUnit").combobox("getText"),
				success : function(data) {
					$('#t23_caster_waitingforcast_tbl_id').datagrid('loadData',
							data);
				},
				error : function() {
					$.messager.alert('Processing',
							'Error while Processing Ajax...', 'error');
				}
			});
}

$(function() {
	$('#t23_tundishtemp_tbl_id').edatagrid({
		saveUrl : './casterProduction/saveTundishTempDet'
	});
});

function openHeatPlansWindow() {
	$('#t23_genRunningId_form_div_id').dialog({
		modal : true,
		cache : true
	});
	$('#t23_genRunningId_form_div_id').dialog('open').dialog('center').dialog(
			'setTitle', 'Running Id Generation against HeatPlan');
	$('#t23_genRunningId_form_id').form('clear');
	document.getElementById('t23_casterType').value=$('#t23_casterUnit').combobox('getValue');
	clearRunIdWindow();
	getWipHeatPlanDet();
	//getAllCasters();
}
function clearRunIdWindow() {
	document.getElementById('t23_casterType').value='';
	$('#t23_casterStDate').datetimebox('setText', '');
}

function getWipHeatPlanDet() {
	var casterType = document.getElementById('t23_casterType').value;
	if (casterType != null && casterType != '') {
		$
				.ajax({
					headers : {
						'Accept' : 'application/json',
						'Content-Type' : 'application/json'
					},
					type : 'GET',
					// data: JSON.stringify(formData),
					dataType : "json",
					url : './casterProduction/getHeatPlanHeaderDetailsByCaster?PLAN_STATUS=WIP&CASTER_TYPE='
							+ casterType,
					success : function(data) {
						$('#t23_heatplan_tbl_id').datagrid('loadData', data);
					},
					error : function() {
						$.messager.alert('Processing',
								'Error while Processing Ajax...', 'error');
					}
				});
	}
}

function generateRunningId() {
	var row = $('#t23_heatplan_tbl_id').datagrid('getSelected');
	if (row) {
		$.messager
				.confirm(
						'Confirm',
						'Do you want to Generate NewRunId with this Heat..?',
						function(r) {
							if (r) {
								var formData = {
									"castRunDet" : {
										"heat_counter" : heatCnt,
										"arc_sl_no" : arc_sl_no,
										"sample_no" : sampleNo,
										"bath_temp" : temp,
										"power_consumption" : consumption,
										"addition_type" : addition_type
									},
									"conDetails" : {
										"arc_grid_arry" : grid_arry,
										"heat_id" : heatId,
										"heat_counter" : heatCnt
									}
								};
								$
										.ajax({
											headers : {
												'Accept' : 'application/json',
												'Content-Type' : 'application/json'
											},
											type : 'POST',
											data : JSON.stringify(formData),
											// data: rows,
											dataType : "json",
											url : './LRFproduction/SaveOrUpdate?arc_start_date='
													+ arc_start_date
													+ '&arc_end_date='
													+ arc_end_date,
											success : function(data) {
												if (data.status == 'SUCCESS') {
													$.messager
															.alert(
																	'LRF Arc Additions Details Info',
																	data.comment,
																	'info');
													cancelT15ArcDet();
													// getT15ArcDetGridBySampleNo(arc_sl_no);
													getT15SampleNos();
												} else {
													$.messager
															.alert(
																	'LRF Arc Additions Details Info',
																	data.comment,
																	'info');
												}
											},
											error : function() {
												$.messager
														.alert(
																'Processing',
																'Error while Processing Ajax...',
																'error');
											}
										});

							}
						});

	} else {
		$.messager.alert('RunId Generation ',
				'Please Select One Record to Attach Heat ...!', 'info');
	}
}
$('#t23_caster_production_tbl_id').edatagrid({

// saveUrl: './scrapEntry/DtlsSaveOrUpdate',
	
});
/*
$('#t23_caster_production_tbl_id')
		.datagrid(
				{

					onBeforeEdit : function(index, row) {
						var row = $('#t23_caster_production_tbl_id').datagrid('getSelected');
						var col1 = $(this).datagrid('getColumnOption','std1');
						if(row.is_processed==="Y"){
							col1.editor=null;
						}
					},
					onEndEdit : function(index, row) {
					},
					onBeginEdit : function(index, row) {
					}
				});*/
function getRowIndex(target){
    var tr = $(target).closest('tr.datagrid-row');
    return parseInt(tr.attr('datagrid-row-index'));
}
/*
function onEndEdit(index, row){
    var ed = $('#t23_caster_production_tbl_id').datagrid('getEditor', {
        index: index,
        field: 'IsReturned'
    });
    row.txtvalue = $(ed.target).combobox('getText');
    row.keyval = $(ed.target).combobox('getValue');
}
*/
function checkCompatibilityValidations(plan_grade,ladle_grade){
	if(plan_grade == ladle_grade)return true;
	$.messager.alert('process Heat ', 'Plan Grade & Ladle Grade should be same to process Ladle','info');
	return false;
}
function getRunningIdDet() {
	var heat_planNo = $('#t23_casterHeatPlanNo').combobox('getValue');
	if (heat_planNo != null && heat_planNo != '') {

		$
				.ajax({
					headers : {
						'Accept' : 'application/json',
						'Content-Type' : 'application/json'
					},
					type : 'GET',
					dataType : "json",
					url : './HeatPlan/getHeatPlanHeaderDetailsByStatus?PLAN_STATUS=WIP',
					success : function(data) {

					},
					error : function() {
						$.messager.alert('Info', 'Heat Number does not exists',
								'Info');
					}
				});
	} else {
		$.messager.alert('Info', 'Please Enter Heat Number', 'Info');
	}

}
function getT23CasterHeatPlanNo() {

	var url2 = "./CommonPool/getComboList?col1=heat_plan_id&col2=heat_plan_id&classname=HeatPlanHdrDetails&status=1&wherecol= mainHeatStatusMasterModel.main_status_desc='WIP' and mainHeatStatusMasterModel.status_type='PLAN_HEADER' and record_status=";
	getDropdownList(url2, '#t23_casterHeatPlanNo');

}

$('#t23_casterStDate').datetimebox({

	value : (formatDate(new Date()))

});

$('#t23_casterEndDate').datetimebox({

	value : (formatDate(new Date()))

});

/* CCm additions SCreen start*/

function T6LadleAdditionOpen() {
	var minmax_flag = false, color_flag = false;
	$('#t23_ccm_additions_tbl_id')
	.edatagrid(
			{
				onBeforeEdit : function(index, row) {
					row.consumption_date = formatDate(row.consumption_date);
					$('#t23_ccm_additions_tbl_id').datagrid(
							'selectRow', index);
				},
				onEndEdit : function(index, row) {
					$('#t23_ccm_additions_tbl_id').datagrid(
							'selectRow', index);
					var dt = (row.consumption_date).split(" ");
					var cons_time = new Date(commonGridDtfmt(dt[0],
							dt[1]));
					var time = cons_time.getTime();
					row.consumption_date = time;
				},
				onBeginEdit : function(index, row) {
					var dg = $(this);
					color_flag = false;
					var editors = $('#t23_ccm_additions_tbl_id')
					.datagrid('getEditors', index);
					var minval = row.val_min;
					var maxval = row.val_max;
					var actVal = $(editors[0].target);
					if ((minval == null || minval == '')
							&& (maxval == null || maxval == '')) {
						return false;
					} else {
						$('#t23_ccm_additions_form_div_id').attr("data-change","1")
						actVal
						.textbox({
							onChange : function() {
								var aVal = actVal
								.textbox('getText');
								if ((aVal != null && aVal != '')
										&& (minval != null && minval != '')
										&& (maxval != null && maxval != '')) {
									minmax_flag = validateMinMax(
											aVal, minval,
											maxval);
									if (!minmax_flag) {
										$.messager
										.alert(
												'Information',
												'Actual value '
												+ aVal
												+ ' should be in between Min '
												+ minval
												+ ' & Max '
												+ maxval
												+ ' Values...!',
										'info');
										color_flag = true;
									}
								}

							}

						});
					}
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
	assignRowChangeDetectionListener('t23_ccm_additions_form_div_id','t23_ccm_additions_tbl_id');
	closeActionBindEvt('t23_ccm_additions_form_div_id');
}

function T23CCMAdditions() {
	var hrow = $('#t23_caster_production_tbl_id').datagrid('getSelected');
	console.log(hrow);
	var t23_trns_si_no =  document.getElementById('t23_trns_si_no').value=hrow.trns_sl_no;
	document.getElementById('t23_sub_unit_id').value=hrow.subUnitMstrMdl.sub_unit_id;
	console.log(hrow.subUnitMstrMdl.sub_unit_id);
	if (t23_trns_si_no != '') {
    	if(hrow){
    		
    		$('#t23_1_heat_no').textbox('setValue', hrow.heat_no);
    		$('#t23_1_aim_psn').textbox('setValue', hrow.psnHdrMstrMdl.psn_no);
    	}
		$('#t23_ccm_additions_form_div_id').dialog({
			modal : true,
			cache : true                                        
		});
		$('#t23_ccm_additions_form_div_id').dialog('open').dialog('center')
		.dialog('setTitle', 'CCM Addition Details Form');
		
		//var heatId = $('#t24_process_heat_no').textbox('getText');
		//var heatcnt = document.getElementById('t23_heat_cnt').value;
		//var aimpsn = $('#t24_process_psn_no').textbox('getText');

		T6LadleAdditionOpen();
		//$('#t23_1_heat_no').textbox('setText', heatId);
		//$('#t23_1_aim_psn').textbox('setText', aimpsn);
		getT6LadleAdditionDtlsGrid();

		$('#t23_ccm_additions_tabs_div_id').tabs({
			border : false,
			onSelect : function(title) {
				getT6LadleAdditionDtlsGrid();
			}
		});
	} else {
		$.messager.alert('Information', 'Please Select Heat...!', 'info');
	}
}// end

function getT6LadleAdditionDtlsGrid() {
	var eof_trns_sno = document.getElementById('t23_trns_si_no').value;
	var psn_no = document.getElementById('t23_1_aim_psn').value;
	var Heat_no=document.getElementById('t23_1_heat_no').value;
	$
	.ajax({
		headers : {
			'Accept' : 'application/json',
			'Content-Type' : 'application/json'
		},
		type : 'GET',
		// data: JSON.stringify(formData),
		dataType : "json",
		url : "./heatProcessEvent/getMtrlDetailsByCCMType?mtrlType=CCM_ADDITIONS"
			+ "&eof_trns_sno=" + eof_trns_sno +"&psn_no="+psn_no,
			success : function(data) {
				var cost = 0;
				for (var i = 0; i < data.length; i++) {
					cost += data[i].qty;
				}

				$('#t23_ccm_additions_tbl_id').datagrid('loadData', {
					"total" : '',
					"rows" : data,
					"footer" : [ {
						"val_max" : "<b>Total Quantity</b>",
						"qty" : parseFloat(cost).toFixed(2)
					} ]
				});

			},
			error : function() {
				$.messager.alert('Processing',
						'Error while Processing Ajax...', 'error');
			}
	});
}

function cancelT23CCMDtls() {
	getT6LadleAdditionDtlsGrid();

}
function saveT23CCMDtls() {
	var eof_trns_sno = document.getElementById('t23_trns_si_no').value;
	var sub_unit = document.getElementById('t23_sub_unit_id').value;
	var eventname = 'CCM_ADDITIONS';
	var ladle_rows = $('#t23_ccm_additions_tbl_id').datagrid('getRows');
	for (var i = 0; i < ladle_rows.length; i++) {
		$('#t23_ccm_additions_tbl_id').datagrid('endEdit', i);
	}
    var mtr_cons_si_id = 0;
	var grid_arry = '';
	for (var i = 0; i < ladle_rows.length; i++) {
		$('#t23_ccm_additions_tbl_id').datagrid('endEdit', i);

		if ((ladle_rows[i].qty != null && ladle_rows[i].qty != '')
				&& (ladle_rows[i].consumption_date != null && ladle_rows[i].consumption_date != '')) {
			var consdate = formatDate(ladle_rows[i].consumption_date);
			grid_arry += ladle_rows[i].material_id + '@' + ladle_rows[i].qty
			+ '@' + consdate + '@' + ladle_rows[i].mtr_cons_si_no + '@'
			+ ladle_rows[i].version + '@' + ladle_rows[i].sap_matl_id + '@' + ladle_rows[i].valuation_type+'SIDS';
		}
	}
    formData = {
			"grid_arry" : grid_arry,
			"trns_ccm_si_no" : eof_trns_sno
	};

	$.ajax({
		headers : {
			'Accept' : 'application/json',
			'Content-Type' : 'application/json'
		},
		type : 'POST',
		data : JSON.stringify(formData),
		// data: rows,
		dataType : "json",
		url : './heatProcessEvent/ccmSaveOrUpdate?sub_unit=' + sub_unit
		+ '&eventname=' + eventname,
		success : function(data) {
			if (data.status == 'SUCCESS') {
				$.messager.alert('CCM Additions Details Info', data.comment,
				'info');
				cancelT23CCMDtls();
			} else {
				$.messager.alert('CCM Additions Details Info', data.comment,
				'info');
			}
		},
		error : function() {
			$.messager.alert('Processing', 'Error while Processing Ajax...',
			'error');
		}
	});
}

$(window).load(setTimeout(applyT6Filter, 1)); // 1000 ms = 1 second.

function applyT6Filter() {
	$('#t23_ccm_additions_tbl_id').datagrid('enableFilter');
}

/*ccm additions screen end */

/* Caster Event Window Begin */
function casterT23EventsDetails() {
	var row= $('#t23_caster_production_tbl_id').datagrid('getSelected');
	$('#t23_caster_events_form_div_id').dialog({
		modal : true,
		cache : true
	});
	$('#t23_caster_events_form_div_id').dialog('open').dialog('center').dialog(
			'setTitle', 'Heat Events Entry Form ');
	 $('#t23_heat_no').textbox("setText",row.heat_no);
	 $('#t23_aim_psn').textbox("setText",row.psnHdrMstrMdl.psn_no);
	 $('#t23_caster_events_tbl_id').edatagrid({
			onBeforeEdit : function(index, row) {
				if(row.event_date_time==null){
					row.event_date_time = formatDate(new Date());
					}
					else{
						row.event_date_time = formatDate(row.event_date_time);
					}
					$('#t23_caster_events_tbl_id').datagrid('selectRow', index);
			},
			onEndEdit : function(index, row) {
				$('#t23_caster_events_form_div_id').attr("data-change","1");
				$('#t23_caster_events_tbl_id').datagrid('selectRow', index);
				var dt = (row.event_date_time).split(" ");

				var cons_time = new Date(commonGridDtfmt(dt[0], dt[1]));

				var time = cons_time.getTime();
				$('#t23_caster_events_form_div_id').attr("data-change","1");
				row.event_date_time = time;
			},
			onAfterEdit:function(index,row){
				row.editing = false;
				$('#t23_caster_events_form_div_id').attr("data-change","1");
			}
		});
	 getT23EvetnsDetails();
}
function getT23EvetnsDetails(){
	var row = $('#t23_caster_production_tbl_id').datagrid('getSelected');
	if (row) {
		$.ajax({
				headers : {
					'Accept' : 'application/json',
					'Content-Type' : 'application/json'
				},
				type : 'GET',
				dataType : "json",
				url : "./heatProcessEvent/getHeatProcessEventDtlsByUnit?heat_id="+row.heat_no+"&heat_counter="+row.heat_counter+"&sub_unit_id="+row.sub_unit_id,
				success : function(data) {
					$('#t23_caster_events_tbl_id').datagrid('loadData', data);
					
					for (var i = 0; i < data.length; i++) {
						if(data[i].event_date_time==null){
							}
					}				
				},
				error : function() {
					$.messager.alert('Processing', 'Error while Processing Ajax...',
							'error');
				}
			});
	}
	else {
		$.messager.alert('Caster Production ',
		'Please Select Heat ...!', 'info');
		}
}

function saveT23EventDtls(){
	var row = $('#t23_caster_production_tbl_id').datagrid('getSelected');
	var ccm_trns_sno = row.trns_sl_no;
	var sel_rows = $('#t23_caster_events_tbl_id').datagrid('getRows');
	var grid_arry = '';
	   var date_prev_val='';
	   var isDateValid ='NO';
	   var isDateNull ='YES';
	   var eventdate = '';

	for (var i = 0; i < sel_rows.length; i++) {
		$('#t23_caster_events_tbl_id').datagrid('endEdit', i);
		if (i == 0){
			if (sel_rows[i].event_date_time == '' || sel_rows[i].event_date_time== null || sel_rows[i].event_date_time== ' '){
				isDateValid ='NO';
				break;
			}else{			
				date_prev_val = formatDate(sel_rows[i].event_date_time);
				   eventdate = formatDate(sel_rows[i].event_date_time);
				   grid_arry += sel_rows[i].event_id + '@' + eventdate + '@'
					+ sel_rows[i].heat_proc_event_id + 'SIDS';
				   isDateValid ='YES';
			  }
		} 
		else{
			if (sel_rows[i].event_date_time=='' || sel_rows[i].event_date_time==null || sel_rows[i].event_date_time==' '){
				if(i == sel_rows.length-1 ){
	   					isDateValid ='YES';
	   					isDateNull ='NO';
	   				}else{
				for (var j = i+1; j < sel_rows.length; j++) {
					if (sel_rows[j].event_date_time !='' && sel_rows[j].event_date_time != null && sel_rows[j].event_date_time !=null){
						isDateNull ='YES';
						break;
					}
					else {
						isDateValid ='YES';
						isDateNull ='NO';
					}
				  }
	   				}
				if (isDateNull =='YES'){
					isDateNull ='NO';
					isDateValid ='NO';
					break;
				}
				else{
					eventdate = formatDate(sel_rows[i].event_date_time);
					/*grid_arry += sel_rows[i].event_id + '@' + eventdate + '@'
					+ sel_rows[i].heat_proc_event_id + 'SIDS';*/
					isDateValid ='YES';
				}
			}
			else{
				 eventdate = formatDate(sel_rows[i].event_date_time);
				if (eventdate < date_prev_val){
					isDateValid ='NO';
					break;
				}
				else{
					eventdate = formatDate(sel_rows[i].event_date_time);
					date_prev_val =formatDate(sel_rows[i].event_date_time);
					grid_arry += sel_rows[i].event_id + '@' + eventdate + '@'
					+ sel_rows[i].heat_proc_event_id + 'SIDS';
					isDateValid ='YES';
				}
			}
		}
	}
			formData = {
		"grid_arry" : grid_arry
	};
	
	if(isDateValid == 'YES'){
		$.ajax({
		headers : {
			'Accept' : 'application/json',
			'Content-Type' : 'application/json'
		},
		type : 'POST',
		data : JSON.stringify(formData),
		// data: rows,
		dataType : "json",
		url : './heatProcessEvent/EventSaveOrUpdate?trns_sno='
				+ ccm_trns_sno+'&unit=CCM',
		success : function(data) {
			if (data.status == 'SUCCESS') {
				$.messager.alert('Event Details Info', data.comment, 'info');
				$('#t23_caster_events_form_div_id').attr("data-change","0");
				//cancelT8EventDtls();
				
			} else {
				$.messager.alert('Event Details Info', data.comment, 'info');
			}
		},
		error : function() {
			$.messager.alert('Processing', 'Error while Processing Ajax...',
					'error');
		}
	});
    
	}
    else{
    	$.messager.alert('Event Details Info','Date entered is not valid','error');
    }   
}

function getTundishNoByType(tunType){
	var url0 ="./CommonPool/getComboList?col1=tundish_sl_no&col2=tundish_no&classname=TundishMasterModel&status='AVAILABLE'&wherecol=tundish_type="+tunType+" and tundish_status=";
	 getDropdownList(url0,'#t23_tundish_no');
}

$('#t23_tundish_type').combobox({
	onSelect : function() {
		var tunType=$('#t23_tundish_type').combobox('getValue');
		getTundishNoByType(tunType);	
	},
	onChange : function() {
	}
});


/** Heat  Details Entry **/
function getT23CasterHeatDetails() {
var row = $('#t23_caster_production_tbl_id').datagrid('getSelected');
$('#t23_caster_batch_dtls_tbl_id1').datagrid("loadData",[]);
$('#produce_batches_btn').linkbutton('disable');
$('#save_prod_btn').linkbutton('enable');

checkCCMProductDtls(row,function(data){
	if(data){
		$('#produce_batches_btn').linkbutton('enable');
		$('#save_prod_btn').linkbutton('disable');
		loadProductBatches(row.trns_sl_no);
	}
});
 $('#t23_hdetails_heat_no').textbox('setText',row.heat_no);
 $('#t23_hdetails_steel_qty').textbox('setText',row.steel_ladle_wgt);
var heatPlanData=[];
	$.ajax({
 		headers: { 
 		'Accept': 'application/json',
 		'Content-Type': 'application/json' 
 		},
 		type: 'GET',
 		//data: JSON.stringify(formData),heatid,heat_cntr,subunitid
 		dataType: "json",
 		url: './HeatPlan/getHeatPlanHdrById?plan_id='+row.heat_plan_id,
 		success: function(data) {
 			for(var i=0;i<row.heatPlanMdl.heatPlanLine.length;i++){
 				var sub=row.heatPlanMdl.heatPlanLine[i];
 			    var details={
 				plant_id:data.heat_plan_id,
 				section: data.smsCapabilityMstrModel.lookupOutputSection.lookup_value,
 				cut_length:sub.plan_cut_length,
 				plant_qty:sub.plan_heat_qty,
 				heat_obj:data,
 				psn_no:sub.psnHdrModel.psn_no,
 				plan_obj:sub
 			};
 			heatPlanData.push(details);
 			}
 			$("#t23_heat_plan_line_tbl").datagrid("loadData",heatPlanData);
 		}		
	});

if (row) {
	$('#t23_heatdetails_form_div_id').dialog({
		modal : true,
		cache : true
	});
	
	$('#t23_heatdetails_form_div_id').dialog('open').dialog('center').dialog(
			'setTitle', 'Heat Details Entry View');
	
	//loadCombos(row);
	//getT23TundishDtls(row.trns_sl_no);
	getT23HeatProdDetails(row.trns_sl_no, row);
} else {
	$.messager.alert('Caster Production ',
			'Please Select Heat ...!', 'info');
}
}

function getT23HeatProdDetails(trns_sl_no, hrow){
	var product = hrow.productMasterMdl.prodSecMtrlLookupModel.lookup_value;
	//alert(product);
	if(product.includes('SLB'))
		getT23HeatProdDetailsForSlabCast(trns_sl_no, hrow);
	else
		getT23HeatProdDetailsForBltCaster(trns_sl_no, hrow);
}

function getT23HeatProdDetailsForBltCaster(trns_sl_no, hrow){
	var tableData=[];
	var sub_unit = $('#t23_casterUnit').combobox('getValue');
	var heat_plan_hdr = hrow.heat_plan_id;
	var aim_psn = hrow.psn_no;
	var cs_wt = null;
	var p_row = $('#t23_caster_production_tbl_id').datagrid('getSelected');
	
	$("#t23_caster_products_dtls_tbl_id").edatagrid({
		columns:[[
	        {field:'label',title:'',width:110},
	        {field:'std1',title:'',width:110,
                  editor:{
                	  type:'numberbox',options:{precision:1}
                  }	
	        },
	        {field:'std2',title:'',width:110,
	        	editor:{
              	  type:'numberbox',options:{precision:1}
                }	
	        	
	        },
	        {field:'std3',title:'',width:110,
	        	editor:{
              	  type:'numberbox',options:{precision:1}
                }	
	        },
	        {field:'std4',title:'',width:110,
	        	editor:{
              	  type:'numberbox',options:{precision:1}
                }	
	        },
	        {field:'std5',title:'',width:110,
	        	editor:{
              	  type:'numberbox',options:{precision:1}
                }	
	        },
	        {field:'std6',title:'',width:110,
	        	editor:{
              	  type:'numberbox',options:{precision:1, disabled:true,editable:false,readonly:true}
                }	
	        }
	    ]],
	    onBeginEdit:function(index,row){
	    	var ed = $(this).datagrid('getEditor',{index:index,field:'std1'});
	    	var ed2 = $(this).datagrid('getEditor',{index:index,field:'std2'});
	    	var ed3 = $(this).datagrid('getEditor',{index:index,field:'std3'});
	    	var ed4 = $(this).datagrid('getEditor',{index:index,field:'std4'});
	    	var ed5 = $(this).datagrid('getEditor',{index:index,field:'std5'});
	    	var ed6 = $(this).datagrid('getEditor',{index:index,field:'std6'});
	    	if(row.label=="Status" ){ 
	    		$(ed.target).bind('change',function(e){
	    			reRoladProdDetls();
	    		});
	    		$(ed2.target).bind('change',function(e){
	    			reRoladProdDetls();
	    		});
	    		$(ed3.target).bind('change',function(e){
	    			reRoladProdDetls();
	    		});
	    		$(ed4.target).bind('change',function(e){
	    			reRoladProdDetls();
	    		});
	    		$(ed5.target).bind('change',function(e){
	    			reRoladProdDetls();
	    		});
	    		$(ed6.target).bind('change',function(e){
	    			reRoladProdDetls();
	    		});
	    	}
	    },
	    onBeforeEdit: function(index,row){
			var col1 = $(this).datagrid('getColumnOption','std1');
			var col2 = $(this).datagrid('getColumnOption','std2');
			var col3 = $(this).datagrid('getColumnOption','std3');
			var col4 = $(this).datagrid('getColumnOption','std4');
			var col5 = $(this).datagrid('getColumnOption','std5');
			var col6 = $(this).datagrid('getColumnOption','std6');
						
			if(row.label=="C.S Size(mm)" ){
				col1.editor = {
						type: 'combobox',
						formatter :function (v,r,i){return formatColumnData('std1',v,r,i);},
						options:{
							url:"./CommonPool/getComboList?col1=prodSecIdLookupModel.lookup_id&col2=prodSecIdLookupModel.lookup_value&classname=ProductSectionMasterModel&status=1&wherecol= ccm_subunit_id="+sub_unit+" and record_status=",
							method:'get',
							valueField:'keyval',
			                textField:'txtvalue'
						}
				};
				col2.editor = {
						type: 'combobox',
						formatter :function (v,r,i){return formatColumnData('std2',v,r,i);},
						options:{
							url:"./CommonPool/getComboList?col1=prodSecIdLookupModel.lookup_id&col2=prodSecIdLookupModel.lookup_value&classname=ProductSectionMasterModel&status=1&wherecol= ccm_subunit_id="+sub_unit+" and record_status=",
							method:'get',
							valueField:'keyval',
			                textField:'txtvalue',
			                onSelect:function(value){
			                	getCsWeigth(value.keyval);
			                }
						}
				};
				col3.editor = {
						type: 'combobox',
						formatter :function (v,r,i){return formatColumnData('std3',v,r,i);},
						options:{
							url:"./CommonPool/getComboList?col1=prodSecIdLookupModel.lookup_id&col2=prodSecIdLookupModel.lookup_value&classname=ProductSectionMasterModel&status=1&wherecol= ccm_subunit_id="+sub_unit+" and record_status=",
							method:'get',
							valueField:'keyval',
			                textField:'txtvalue'
						}
				};
				col4.editor = {
						type: 'combobox',
						formatter :function (v,r,i){return formatColumnData('std4',v,r,i);},
						options:{
							url:"./CommonPool/getComboList?col1=prodSecIdLookupModel.lookup_id&col2=prodSecIdLookupModel.lookup_value&classname=ProductSectionMasterModel&status=1&wherecol= ccm_subunit_id="+sub_unit+" and record_status=",
							method:'get',
							valueField:'keyval',
			                textField:'txtvalue'
						}
				};
				col5.editor = {
						type: 'combobox',
						formatter :function (v,r,i){return formatColumnData('std5',v,r,i);},
						options:{
							url:"./CommonPool/getComboList?col1=prodSecIdLookupModel.lookup_id&col2=prodSecIdLookupModel.lookup_value&classname=ProductSectionMasterModel&status=1&wherecol= ccm_subunit_id="+sub_unit+" and record_status=",
							method:'get',
							valueField:'keyval',
			                textField:'txtvalue'
						}
				};
				col6.editor = {
						type: 'combobox',
						formatter :function (v,r,i){return formatColumnData('std6',v,r,i);},
						options:{
							url:"./CommonPool/getComboList?col1=prodSecIdLookupModel.lookup_id&col2=prodSecIdLookupModel.lookup_value&classname=ProductSectionMasterModel&status=1&wherecol= ccm_subunit_id="+sub_unit+" and record_status=",
							method:'get',
							valueField:'keyval',
			                textField:'txtvalue'
						}
				};
			}else if(row.label==="Cut to Length" ){
				col1.editor = {
						type: 'combobox',
						formatter :function (v,r,i){return formatColumnData('std1',v,r,i);},
						options:{
							url:"./CommonPool/getComboList?col1=heat_line_id&col2=plan_cut_length&classname=HeatPlanLinesDetails&status=1&wherecol=heat_plan_id="+heat_plan_hdr+" and aim_psn="+aim_psn+" and record_status=",
							method:'get',
							valueField:'keyval',
			                textField:'txtvalue',
			                onSelect:function(value){
			                	$("#cutlength_std1").val(value.keyval);
			                	
			                	var dataV=$("#t23_caster_products_dtls_tbl_id").datagrid("getData");
			                	if(cs_wt != null){
			                		var temp = (((p_row.steel_ladle_wgt / cs_wt) * 1000) / value.txtvalue);
			                		var no_batches = Math.round(parseFloat(temp) / 3);
			                		for(var i=0;i<dataV.rows.length;i++){
			                			var datas =dataV.rows;
			                			if(datas[i].label==="No of Batches"){
			                				datas[i]['std1'] = no_batches;
			                				break;
			                			}
			                		}
			                	}
			                }
						}
				};
				col2.editor = {
						type: 'combobox',
						formatter :function (v,r,i){return formatColumnData('std2',v,r,i);},
						options:{
							url:"./CommonPool/getComboList?col1=heat_line_id&col2=plan_cut_length&classname=HeatPlanLinesDetails&status=1&wherecol=heat_plan_id="+heat_plan_hdr+" and aim_psn="+aim_psn+" and record_status=",
							method:'get',
							valueField:'keyval',
			                textField:'txtvalue',
			                onSelect:function(value){
			                	$("#cutlength_std2").val(value.keyval);
			                	
			                	var dataV=$("#t23_caster_products_dtls_tbl_id").datagrid("getData");
			                	if(cs_wt != null){
			                		var temp = (((p_row.steel_ladle_wgt / cs_wt) * 1000) / value.txtvalue);
			                		var no_batches = Math.round(parseFloat(temp) / 3);
			                		for(var i=0;i<dataV.rows.length;i++){
			                			var datas =dataV.rows;
			                			if(datas[i].label==="No of Batches"){
			                				datas[i]['std2'] = no_batches;
			                				break;
			                			}
			                		}
			                	}
			                }
						}
				};
				col3.editor = {
						type: 'combobox',
						formatter :function (v,r,i){return formatColumnData('std3',v,r,i);},
						options:{
							url:"./CommonPool/getComboList?col1=heat_line_id&col2=plan_cut_length&classname=HeatPlanLinesDetails&status=1&wherecol=heat_plan_id="+heat_plan_hdr+" and aim_psn="+aim_psn+" and record_status=",
							method:'get',
							valueField:'keyval',
			                textField:'txtvalue',
			                onSelect:function(value){
			                	$("#cutlength_std3").val(value.keyval);
			                	
			                	var dataV=$("#t23_caster_products_dtls_tbl_id").datagrid("getData");
			                	if(cs_wt != null){
			                		var temp = (((p_row.steel_ladle_wgt / cs_wt) * 1000) / value.txtvalue);
			                		var no_batches = Math.round(parseFloat(temp) / 3);
			                		for(var i=0;i<dataV.rows.length;i++){
			                			var datas =dataV.rows;
			                			if(datas[i].label==="No of Batches"){
			                				datas[i]['std3'] = no_batches;
			                				break;
			                			}
			                		}
			                	}
			                }
						}
				};
				col4.editor = {
						type: 'combobox',
						formatter :function (v,r,i){return formatColumnData('std4',v,r,i);},
						options:{
							url:"./CommonPool/getComboList?col1=heat_line_id&col2=plan_cut_length&classname=HeatPlanLinesDetails&status=1&wherecol=heat_plan_id="+heat_plan_hdr+" and aim_psn="+aim_psn+" and record_status=",
							method:'get',
							valueField:'keyval',
			                textField:'txtvalue',
			                onSelect:function(value){
			                	$("#cutlength_std4").val(value.keyval);
			                	
			                	var dataV=$("#t23_caster_products_dtls_tbl_id").datagrid("getData");
			                	if(cs_wt != null){
			                		var temp = (((p_row.steel_ladle_wgt / cs_wt) * 1000) / value.txtvalue);
			                		var no_batches = Math.round(parseFloat(temp) / 3);
			                		for(var i=0;i<dataV.rows.length;i++){
			                			var datas =dataV.rows;
			                			if(datas[i].label==="No of Batches"){
			                				datas[i]['std4'] = no_batches;
			                				break;
			                			}
			                		}
			                	}
			                }
						}
				};
				col5.editor = {
						type: 'combobox',
						formatter :function (v,r,i){return formatColumnData('std5',v,r,i);},
						options:{
							url:"./CommonPool/getComboList?col1=heat_line_id&col2=plan_cut_length&classname=HeatPlanLinesDetails&status=1&wherecol=heat_plan_id="+heat_plan_hdr+" and aim_psn="+aim_psn+" and record_status=",
							method:'get',
							valueField:'keyval',
			                textField:'txtvalue',
			                onSelect:function(value){
			                	$("#cutlength_std5").val(value.keyval);
			                	
			                	var dataV=$("#t23_caster_products_dtls_tbl_id").datagrid("getData");
			                	if(cs_wt != null){
			                		var temp = (((p_row.steel_ladle_wgt / cs_wt) * 1000) / value.txtvalue);
			                		var no_batches = Math.round(parseFloat(temp) / 3);
			                		for(var i=0;i<dataV.rows.length;i++){
			                			var datas =dataV.rows;
			                			if(datas[i].label==="No of Batches"){
			                				datas[i]['std5'] = no_batches;
			                				break;
			                			}
			                		}
			                	}
			                }
						}
				};
				col6.editor = {
						type: 'combobox',
						formatter :function (v,r,i){return formatColumnData('std6',v,r,i);},
						options:{
							url:"./CommonPool/getComboList?col1=heat_line_id&col2=plan_cut_length&classname=HeatPlanLinesDetails&status=1&wherecol=heat_plan_id="+heat_plan_hdr+" and aim_psn="+aim_psn+" and record_status=",
							method:'get',
							valueField:'keyval',
			                textField:'txtvalue',
			                onSelect:function(value){
			                	$("#cutlength_std6").val(value.keyval);
			                	
			                	var dataV=$("#t23_caster_products_dtls_tbl_id").datagrid("getData");
			                	if(cs_wt != null){
			                		var temp = (((p_row.steel_ladle_wgt / cs_wt) * 1000) / value.txtvalue);
			                		var no_batches = Math.round(parseFloat(temp) / 3);
			                		for(var i=0;i<dataV.rows.length;i++){
			                			var datas =dataV.rows;
			                			if(datas[i].label==="No of Batches"){
			                				datas[i]['std6'] = no_batches;
			                				break;
			                			}
			                		}
			                	}
			                }
						}
				};
			}else if(row.label=="Status"){
	    		col1.editor={
	    				type:'checkbox',
	    				options:{
	    					on: 'Y',
	    					off: 'N'
	    				}
	    		};
	    		col2.editor={
	    				type:'checkbox',
		    			options:{
		    				on: 'Y',
		    				off: 'N'
		    			}
	    		};
	    		col3.editor={
		    			type:'checkbox',
		    			options:{
		    				on: 'Y',
		    				off: 'N'
		    			}
		    	};
	    		col4.editor={
		    			type:'checkbox',
		    			options:{
		    				on: 'Y',
		    				off: 'N'
		    			}
		    	};
	    		col5.editor={
		    			type:'checkbox',
		    			options:{
		    				on: 'Y',
		    				off: 'N'
		    			}
		    	};
	    		col6.editor={
		    			type:'checkbox',
		    			options:{
		    				on: 'Y',
		    				off: 'N'
		    			}
		    	};
	    	}else if(row.label=="Wgt(Tons)"){
		   		col1.editor = {
		   				type:'numberbox',options:{
		   					onChange: function(value) {
		              			 if(value==0){
		              				$.messager.alert('Info','Wgt(Tons) cannot be 0 Please Disable" : 1','info');
		              				 return false;
		              			 }
		   					}}
		   		} ;
		   		col2.editor = {
		   				type:'numberbox',options:{
		   					onChange: function(value) {
		   						if(value==0){
		   							$.messager.alert('Info','Wgt(Tons) cannot be 0 Please Disable Stand No : 2','info');
		   							return false;
		   						}
		   					}}
		   		};
				col3.editor = {
						type:'numberbox',options:{
							onChange: function(value) {
								if(value==0){
									$.messager.alert('Info','Wgt(Tons) cannot be 0 Please Disable Stand No : 3','info');
				              		return false;
								}
							}}
				};
				col4.editor = {
						type:'numberbox',options:{
							onChange: function(value) {
								if(value==0){
									$.messager.alert('Info','Wgt(Tons) cannot be 0 Please Disable Stand No : 4','info');
				              		return false;
								}
							}}
				};
				col5.editor = {
						type:'numberbox',options:{
							onChange: function(value) {
								if(value==0){
									$.messager.alert('Info','Wgt(Tons) cannot be 0 Please Disable Stand No : 5','info');
									return false;
								}
							}}
				};
				col6.editor = {
						type:'numberbox',options:{
							onChange: function(value) {
								if(value==0){
									$.messager.alert('Info','Wgt(Tons) cannot be 0 Please Disable Stand No : 6','info');
				              		return false;
								}
							}}
				};
				}else if(row.label=="No of Batches"){
		   		col1.editor = {
		   				type:'numberbox',options:{
		   					onChange: function(value) {
		              			 if(value==0){
		              				$.messager.alert('Info','Please Disable Stand No : 1','info');
		              				 return false;
		              			 }
		   					}}
		   		};
		   		col2.editor = {
		   				type:'numberbox',options:{
		   					onChange: function(value) {
		   						if(value==0){
		   							$.messager.alert('Info','Please Disable Stand No : 2','info');
		   							return false;
		   						}
		   					}}
		   		};
				col3.editor = {
						type:'numberbox',options:{
							onChange: function(value) {
								if(value==0){
									$.messager.alert('Info','Please Disable Stand No : 3','info');
				              		return false;
								}
							}}
				};
				col4.editor = {
						type:'numberbox',options:{
							onChange: function(value) {
								if(value==0){
									$.messager.alert('Info','Please Disable Stand No : 4','info');
				              		return false;
								}
							}}
				};
				col5.editor = {
						type:'numberbox',options:{
							onChange: function(value) {
								if(value==0){
									$.messager.alert('Info','Please Disable Stand No : 5','info');
									return false;
								}
							}}
				};
				col6.editor = {
						type:'numberbox',options:{
							onChange: function(value) {
								if(value==0){
									$.messager.alert('Info','Please Disable Stand No : 6','info');
				              		return false;
								}
							}}
				};
		   	}else if(row.label=="C.S wgt(Kgs/Mtr)"){
		   		col1.editor = {
		   				type:'numberbox',options:{readonly:true}
		   		};
		   		col2.editor = {
		   				type:'numberbox',options:{readonly:true}
		   		};
				col3.editor = {
						type:'numberbox',options:{readonly:true}
				};
				col4.editor = {
						type:'numberbox',options:{readonly:true}
				};
				col5.editor = {
						type:'numberbox',options:{readonly:true}
				};
				col6.editor = {
						type:'numberbox',options:{readonly:true}
				};
		   	}else{
				col1.editor = {
						type:'numberbox',options:{precision:1}
                };
				col2.editor = {
						type:'numberbox',options:{precision:1}
				};
				col3.editor = {
						type:'numberbox',options:{precision:1}
				};
				col4.editor = {
						type:'numberbox',options:{precision:1}
				};
				col5.editor = {
						type:'numberbox',options:{precision:1}
				};
				col6.editor = {
						type:'numberbox',options:{precision:1}
				};
			}
		}
	});
	
	
	$('input[type="checkbox"]').click(function(){
		alert("Clicked..........");
	});
	
	//$('#t23_caster_products_dtls_tbl_id').datagrid('gotoCell', 'down');
	var hrow = $('#t23_caster_production_tbl_id').datagrid('getSelected');
	$.ajax({
 		headers: { 
 		'Accept': 'application/json',
 		'Content-Type': 'application/json' 
 		},
 		type: 'GET',
 		dataType: "json",
 		url: './casterProduction/getCCMProdDtls?trns_sl_no='+trns_sl_no,
 		success: function(data) {
 				var details={
 						label:"Stand No",
 						std1:data[0]!=null?data[0].standlkpMdl.lookup_value:"1",
 						std2:data[1]!=null?data[1].standlkpMdl.lookup_value:"2",
 						std3:data[2]!=null?data[2].standlkpMdl.lookup_value:"3",
 						std4:data[3]!=null?data[3].standlkpMdl.lookup_value:"4",
 						std5:data[4]!=null?data[4].standlkpMdl.lookup_value:"5",
 						std6:data[5]!=null?data[5].standlkpMdl.lookup_value:"6"
 				};
 				tableData.push(details);
 				
 				var details1={
 						label:"Status",
 						std1:data[0]!=null?"Y":"N",
 						std2:data[1]!=null?"Y":"N",
 						std3:data[2]!=null?"Y":"N",
 						std4:data[3]!=null?"Y":"N",
 						std5:data[4]!=null?"Y":"N",
 						std6:data[5]!=null?"Y":"N",
 				};
 				tableData.push(details1);				
 				
 				var details6={
						label:"C.S Size(mm)",
						std1:data[0]!=null?data[0].csSizeMdl.lookup_value:hrow.heatPlanMdl.smsCapabilityMstrModel.lookupOutputSection.lookup_value,
						std2:data[1]!=null?data[1].csSizeMdl.lookup_value:hrow.heatPlanMdl.smsCapabilityMstrModel.lookupOutputSection.lookup_value,
						std3:data[2]!=null?data[2].csSizeMdl.lookup_value:hrow.heatPlanMdl.smsCapabilityMstrModel.lookupOutputSection.lookup_value,
						std4:data[3]!=null?data[3].csSizeMdl.lookup_value:hrow.heatPlanMdl.smsCapabilityMstrModel.lookupOutputSection.lookup_value,
						std5:data[4]!=null?data[4].csSizeMdl.lookup_value:hrow.heatPlanMdl.smsCapabilityMstrModel.lookupOutputSection.lookup_value,
						std6:data[5]!=null?data[5].csSizeMdl.lookup_value:hrow.heatPlanMdl.smsCapabilityMstrModel.lookupOutputSection.lookup_value
				};
 				tableData.push(details6);
 				if(data[0]!=null){
 					$("#cs_strand1").val(data[0].cs_size);
 				}else{
 					$("#cs_strand1").val(hrow.heatPlanMdl.smsCapabilityMstrModel.section);
 				}
 				if(data[1]!=null){
 					$("#cs_strand2").val(data[1].cs_size);
 				}else{
 					$("#cs_strand2").val(hrow.heatPlanMdl.smsCapabilityMstrModel.section);
 				}
 				if(data[2]!=null){
 					$("#cs_strand3").val(data[2].cs_size);
 				}else{
 					$("#cs_strand3").val(hrow.heatPlanMdl.smsCapabilityMstrModel.section);
 				}
 				if(data[3]!=null){
 					$("#cs_strand4").val(data[3].cs_size);
 				}else{
 					$("#cs_strand4").val(hrow.heatPlanMdl.smsCapabilityMstrModel.section);
 				}
 				if(data[4]!=null){
 					$("#cs_strand5").val(data[4].cs_size);
 				}else{
 					$("#cs_strand5").val(hrow.heatPlanMdl.smsCapabilityMstrModel.section);
 				}
 				if(data[5]!=null){
 					$("#cs_strand6").val(data[5].cs_size);
 				}else{
 					$("#cs_strand6").val(hrow.heatPlanMdl.smsCapabilityMstrModel.section);
 				}
 				var details7={
						label:"C.S wgt(Kgs/Mtr)",
						std1:data[0]!=null?data[0].cs_wgt:"", 
						std2:data[1]!=null?data[1].cs_wgt:"",
						std3:data[2]!=null?data[2].cs_wgt:"",
						std4:data[3]!=null?data[3].cs_wgt:"",
						std5:data[4]!=null?data[4].cs_wgt:"",
						std6:data[5]!=null?data[5].cs_wgt:"",
				};	
 				if(data[0]!=null){
 					cs_wt = data[0].cs_wgt;
 				}
 				tableData.push(details7);
 				
 				var details2={
 						label:"Wgt(Tons)",
 						std1:data[0]!=null?data[0].tot_wgt_batches:"",
 						std2:data[1]!=null?data[1].tot_wgt_batches:"",
 						std3:data[2]!=null?data[2].tot_wgt_batches:"",
 						std4:data[3]!=null?data[3].tot_wgt_batches:"",
 						std5:data[4]!=null?data[4].tot_wgt_batches:"",
 						std6:data[5]!=null?data[5].tot_wgt_batches:""
 				};
 				tableData.push(details2);
 				
 				var details8={
						label:"Cut to Length",
						std1:data[0]!=null?data[0].cut_length:"",
						std2:data[1]!=null?data[1].cut_length:"",
						std3:data[2]!=null?data[2].cut_length:"",
						std4:data[3]!=null?data[3].cut_length:"",
						std5:data[4]!=null?data[4].cut_length:"",
						std6:data[5]!=null?data[5].cut_length:"",
				};
 				tableData.push(details8);
 				if(data[0]!=null){
                    $("#cutlength_std1").val(data[0].heat_plan_line_no);
                }
                if(data[1]!=null){
                    $("#cutlength_std2").val(data[1].heat_plan_line_no);
                }
                if(data[2]!=null){
                    $("#cutlength_std3").val(data[2].heat_plan_line_no);
                }
                if(data[3]!=null){
                    $("#cutlength_std4").val(data[3].heat_plan_line_no);
                }
                if(data[4]!=null){
                    $("#cutlength_std5").val(data[4].heat_plan_line_no);
                }
                if(data[5]!=null){
                    $("#cutlength_std6").val(data[5].heat_plan_line_no);
                }
                var details9={
						label:"No of Batches",
						std1:data[0]!=null?data[0].no_batches:"",
						std2:data[1]!=null?data[1].no_batches:"",
						std3:data[2]!=null?data[2].no_batches:"",
						std4:data[3]!=null?data[3].no_batches:"",
						std5:data[4]!=null?data[4].no_batches:"",
						std6:data[5]!=null?data[5].no_batches:""
				};
 				tableData.push(details9);
 				
 		        $("#t23_caster_products_dtls_tbl_id").datagrid("loadData",tableData);
 		  },
 		error:function(){      
 			$.messager.alert('Processing','Error while Processing Ajax...','error');
 		  }
 		});
}

function savetT23HeatProdDetails(){
	$("#t23_caster_products_dtls_tbl_id").datagrid("acceptChanges");
	var dataV=$("#t23_caster_products_dtls_tbl_id").datagrid("getData");
	var formData=[];
	var row = $('#t23_caster_production_tbl_id').datagrid('getSelected');
	var product = row.productMasterMdl.prodSecMtrlLookupModel.lookup_value;
	var label=[];
	if(product.includes('SLB'))
		label=['std1'];
	else
		label=['std1','std2','std3', 'std4', 'std5', 'std6'];
	
	//alert(label.length)
	for(var k=0;k<label.length;k++){
		var cut_length;
		var no_batches;
		var cs_size;
		var cs_wgt;
		var tot_wgt_batches;
		var casting_speed;
		var ems_cf;
		var stand_no;
		var status;
		var wgt;
		
		for(var i=0;i<dataV.rows.length;i++){
			var datas =dataV.rows;
			if(datas[i].label==="Cut to Length"){
				//cut_length=datas[i][label[k]];
				cut_length=$("#cutlength_std"+(k+1)).val(); 
			}
			if(datas[i].label==="No of Batches"){
				if(datas[i][label[k]]>0){
					no_batches=datas[i][label[k]];	
				}else if(datas[i][label[k]]===''){
					no_batches=datas[i][label[k]];	
				}else{
					$.messager.alert('Processing','Strand '+(k+1)+' Not Disabled, Data Not Saved!','error');
					return false;
				}
			}
			if(datas[i].label==="Wgt(Tons)"){
				if(datas[i][label[k]]>0){
					wgt=datas[i][label[k]];	
				}else if(datas[i][label[k]]===''){
					wgt=datas[i][label[k]];	
				}else{
					$.messager.alert('Processing','Strand '+(k+1)+' Not Disabled, Data Not Saved!','error');
					return false;
				}
			}
			if(datas[i].label==="C.S Size(mm)"){
				//cs_size=datas[i][label[k]];
				cs_size=$("#cs_strand"+(k+1)).val(); 
			}
			if(datas[i].label==="C.S wgt(Kgs/Mtr)"){
				cs_wgt=datas[i][label[k]];	
			}
			if(datas[i].label==="Wgt(Tons)"){
				tot_wgt_batches=datas[i][label[k]];	
			}
			if(datas[i].label==="Casting Speed"){
				casting_speed=datas[i][label[k]];	
			}
			if(datas[i].label==="EMS C/F"){
				ems_cf=datas[i][label[k]];	
			}
			if(datas[i].label==="Mould JacketNo"){
				module_jackt_no=datas[i][label[k]];	
			}
			if(datas[i].label==="Stand No"){
				stand_no=datas[i][label[k]];	
			}
			if(datas[i].label==="Status"){
				status=datas[i][label[k]];	
			}
		}

		var casterHeat_pk=row.trns_sl_no;
		var details={
			heat_plan_line_no:cut_length,
			no_batches:no_batches,
			cs_size:cs_size,
			cs_wgt:cs_wgt,
			tot_wgt_batches:tot_wgt_batches,
			casting_speed:casting_speed,
			ems_cf:ems_cf,
			stand_no:stand_no,
			trns_sl_no:casterHeat_pk,
			status:status
			//module_jackt_no:module_jackt_no
		};
	
		formData.push(details);
	}

	$.ajax({
 		headers: { 
 		'Accept': 'application/json',
 		'Content-Type': 'application/json' 
 		},
 		type: 'POST',
 		data: JSON.stringify(formData),
 		dataType: "json",
 		url: './casterProduction/saveCCMProdDtls',
 		success: function(data) {
 			if(data.status=="SUCCESS"){
 				$('#save_prod_btn').linkbutton('disable');
 				$('#produce_batches_btn').linkbutton('enable');
 				$.messager.alert('SUCCESS',data.comment,'info');
 			}
 		},
 		error:function(){      
 			$.messager.alert('Processing','Error while Processing Ajax...','error');
 		}
 		});
}

/* for Process Parameters Button*/
function openCasterT24ProcessParam(){
	$('#t24_process_param_form_div_id').dialog({
		modal : true,
		cache : true
	});
	$('#t24_process_param_form_div_id').dialog('open').dialog('center').dialog(
			'setTitle', 'Caster Process Parameters');
	var hrow = $('#t23_caster_production_tbl_id').datagrid('getSelected');
	if(hrow){
		$('#t24_heat_no').textbox('setValue', hrow.heat_id);
		$('#t24_aim_psn').textbox('setValue', hrow.psnHdrMstrMdl.psn_no);
		getT24ProcParamDtlsGrid(hrow.heat_id);
	}
}

function dateFormat (date) {
    var y = date.getFullYear();
    var d = date.getDate();
    var h=date.getHours();
    var m=date.getMinutes();
    
	// date format dd/mm/yyyy
    var r = (d < 10 ? ('0' + d) : d) + '/' + 
            (m < 10 ? ('0' + m) : m) + '/' + 
            y+" "+h+":"+m;
    return r;
}
$("#t24_seq_no").combobox({
	onSelect:function(selValue){
		doGenSeqNo(selValue.value, true, false);
	}	
});
$("#t23_shroud_change").combobox({
	onSelect:function(selValue){
		if(selValue.value==="Y"){
			$("#t23_shroud_make").combobox('enable'); 
		}else if(selValue.value==="N"){
			doGenSeqNo('N', false, true);
		}
	}	
});
$("#t23_ladleOpen1").combobox({
	onSelect:function(selValue){
		if(selValue.value==="Lancing"){
			$("#t23_noOfPipes").combobox('enable'); 
		}else{
			$("#t23_noOfPipes").combobox('disable');
		}
	}	
});
function doGenSeqNo(value, setTundish, setShroud){
	var sub_unit_id=$("#t23_casterUnit").combobox("getValue");
	$.ajax({
 		headers: { 
 		'Accept': 'application/json',
 		'Content-Type': 'application/json' 
 		},
 		type: 'GET',
 		//data: JSON.stringify(formData),
 		dataType: "json",
 		url: './casterProduction/getSeqNoByHeat?unit_id='+sub_unit_id,
 		success: function(data) {
 			$("#prev_tund_no").val(data.tundish_no);
 			if(setTundish){
 				loadTundishCombo(value);
 			var seq="";
 			if(value==="primary"){
 				seq="L";
 				$("#t24_TundishCar").combobox('enable'); 
 				$("#t23_tundishNo1").combobox('enable');
 				$("#t24_TundishCar").combobox("setValue",'');
 				$("#t23_tundishNo1").combobox("setValue",'');
 			}
 			if(value==="fly"){
 				seq="F"+(data.fly_seq_si+1);
 				$("#t24_TundishCar").combobox('enable'); 
 				$("#t23_tundishNo1").combobox('enable'); 
 				$("#t24_TundishCar").combobox("setValue",'');
 				$("#t23_tundishNo1").combobox("setValue",'');
 			}if(value==="continue" || value==="last_heat"){
 				if(data.seq_group_status==0){
 					$.messager.alert('Process Heat ', 'You cannot continue, Sequence is closed', 'info');
 				}else{
 					if(data.is_primary==1){
 	 					dt=data.primary_seq+1;
 	 					seq="L+"+dt;
 	 				}
 	 				if(data.is_flying==1){
 	 					dt=data.fly_seq+1;
 	 					seq="F"+data.fly_seq_si+"+"+dt;
 	 				}
 	 				$("#t24_TundishCar").combobox("setValue",data.tundish_car_no);
 	 				$("#t23_tundishNo1").combobox("setValue",data.tundish_no);
 	 				$("#t24_TundishCar").combobox('disable'); 
 	 				$("#t23_tundishNo1").combobox('disable');
 				}
 			}
 			$("#t24_process_seq_no").textbox("setValue",seq);
 			}
 			if(setShroud){
 				$("#t23_shroud_make").combobox('disable'); 
 				$("#t23_shroud_make").combobox("setValue",data.shroud_make);
 			}
 		}
	});
}

$("#t24_process_cs_size").combobox({
	onSelect : function(record) {
		setProduct(record.keyval);
	}
});

function setProduct(section){
	$('#t24_process_product').combobox('setValue', '');
	var sub_unit = $('#t23_casterUnit').combobox('getValue');
	var url6 = "./CommonPool/getComboList?col1=ccm_mat_sec_id&col2=prodSecMtrlLookupModel.lookup_value&classname=ProductSectionMasterModel&status=1&wherecol=section_size_id="+section+" and ccm_subunit_id="+sub_unit+" and record_status=";
	$.ajax({
		headers: { 
			'Accept': 'application/json',
	     	'Content-Type': 'application/json' 
	    },
	    type: 'GET',
	    url: url6,
	    dataType: 'json',
	    success: function(data){
	    	$("#t24_process_product").combobox('loadData', data);
	    	if(data.length == 1){
	    		$("#t24_process_product").combobox("setValue",data[0].keyval);
	    	}
	    },
	    error: function(){
	    	$.messager.alert('Processing','Error while Processing Ajax...','error');
	    }
	});
}

function processHeatInCaster(){
	if(validateT24CCMCrewDetailsform()){
		$("#t23_shroud_change").combobox("setValue",'N');
		$("#t23_noOfPipes").combobox('disable'); 
		$("#t24_seq_no").combobox("setValue","continue");
		$("#prev_tund_no").val("0");
		doGenSeqNo($("#t24_seq_no").combobox("getValue"), true, true);
		var d=new Date();
		$('#t24_process_prod_date').datetimebox({
		    value: formatDate(d),
		    required: false,
		    showSeconds: false,
		    editable:false
		});
		
		var row = $('#t23_caster_waitingforcast_tbl_id').datagrid('getSelected');
		
		$("#hidden_primary").val("0");
		$("#hidden_fly").val("0");
		$("#cast_powder").val("");
		var unit_id = $('#t23_casterUnit').combobox('getValue');

		if(row){
			loadCombos(row);
			$("#t24_process_cs_size").combobox("setValue",row.heatPlanModel.smsCapabilityMstrModel.section);
			setProduct(row.heatPlanModel.smsCapabilityMstrModel.section);//added suma on 27/12/2018
			$('#t24_process_heat_form_div_id').dialog({modal:true});
			$('#t24_process_heat_form_div_id').dialog('open').dialog('center').dialog(
						'setTitle', 'Heat Details');
			$("#t24_TundishCar").combobox('enable'); 
			$("#t23_tundishNo1").combobox('enable');
			$.ajax({
		 		headers: { 
		 		'Accept': 'application/json',
		 		'Content-Type': 'application/json' 
		 		},
		 		type: 'GET',
		 		//data: JSON.stringify(formData),
		 		dataType: "json",
		 		url: './casterProduction/heatTrackStatus?heatId='+row.heat_id+"&heat_counter="+row.heat_counter+"&psn_hdr_sl_no="+row.aim_psn+"&unit_id="+unit_id,
		 		success: function(data) {
		 		$("#t24_process_route").textbox("setText",data.status.act_proc_path);
		 		//$("#cast_powder").val(data.qc.qa_char_value);
		 		  },
		 		error:function(){      
		 			$.messager.alert('Processing','Error while Processing Ajax...','error');
		 		  }
		 		});
			
			$("#t24_process_heat_no").textbox("setText",row.heat_id);
			$("#t24_process_psn_no").textbox("setText", row.aim_psn_char);//heatPlanModel
			$("#t24_process_eof").textbox("setText",row['heatPlanModel']['subUnitTargetEof']['sub_unit_name']);
		}
		else {
			$.messager.alert('Process Heat ',
					'Please Select One Record to Process Heat ...!', 'info');
		}
	}else{
		$.messager.alert('Process Heat ',
				'Select Crew Details!', 'info');
	}
}

function loadCombos(row){
	if(typeof(row.aim_psn)!="undefined"){
		var url="./CommonPool/getComboList?col1=psn_grade_sl_no&col2=psn_grade&classname=PsnGradeMasterModel&status=&" +
		"wherecol= psn_hdr_sl_no = "+row.aim_psn+"";
		getDropdownList(url,'#t24_process_grade_no');
		
		var url3 = "./CommonPool/getComboList?col1=lookup_id&col2=lookup_value&classname=LookupMasterModel&status=1&"
			+ "wherecol=lookup_type='CCM_CASTING_POWDER' and lookup_status=";
		var url4 = "./CommonPool/getComboList?col1=lookup_id&col2=lookup_value&classname=LookupMasterModel&status=1&"
			+ "wherecol=lookup_type='SHROUD_MAKE' and lookup_status=";
			
		var sub_unit = $('#t23_casterUnit').combobox('getValue');
		var url2="./CommonPool/getComboList?col1=prodSecIdLookupModel.lookup_id&col2=prodSecIdLookupModel.lookup_value&classname=ProductSectionMasterModel&status=1&wherecol= ccm_subunit_id="+sub_unit+" and record_status=";
			
		getDropdownList(url2,'#t24_process_cs_size');
		getDropdownList(url3,'#t24_process_cast_powder');
		getDropdownList(url4,'#t23_shroud_make');
		
		$.ajax({
	 		headers: { 
	 		'Accept': 'application/json',
	 		'Content-Type': 'application/json' 
	 		},
	 		type: 'GET',
	 		//data: JSON.stringify(formData),
	 		dataType: "json",
	 		url: url,
	 		success: function(data) {
	 			if(data.length>0){
	 			$("#t24_process_grade_no").combobox("setValue",data[0].keyval);
	 			}
	 		}
		});	
	}
}
function casterSavedHeatsRefresh(){
	
}

function validateT24CCMCrewDetailsform(){
    return $('#t23_crew_form_id').form('validate');
}
function saveT23CCMCrewDetails(){
	 //var shift_mgr=$('#t23_shiftMgr').combobox('getValue');
	 var ccmInCharge=$('#t23_ccmInCharge').combobox('getValue');
	 var ccmMcdOpr=$('#t23_ccmMcdOpr').combobox('getValue');
	 //var ccmGasCutter=$('#t23_gasCutter').combobox('getValue');
	 var row = $('#t23_caster_waitingforcast_tbl_id').datagrid('getSelected');
	 
	 var heat_id=row.heat_id;
	 var heat_cnt= row.heat_counter;  //document.getElementById('t4_heat_cnt').value
	 var sub_unit_id=$('#t23_casterUnit').combobox('getValue');
	 
	 //var user_role_id= shift_mgr+"@"+ccmInCharge+"@"+ccmMcdOpr+"@"+ccmGasCutter+"@";
	 var user_role_id= ccmInCharge+"@"+ccmMcdOpr+"@";
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
	 		  },
	 		error:function(){      
	 			$.messager.alert('Processing','Error while Processing Ajax...','error');
	 		  }
	 		});
}


function t24_process_heat_save(){
	if(validateT24CCMCrewDetailsform()){
		$('#t24_process_heat_form_div_id').dialog('open').dialog('center').dialog(
			'setTitle', 'Heat Details');
		var row = $('#t23_caster_waitingforcast_tbl_id').datagrid('getSelected');
		var unit_id = $('#t23_casterUnit').combobox('getValue');
		var process_ret_qty = 0;//$('#t24_process_ret_qty').textbox('getValue');
		var grade=$('#t24_process_grade_no').combobox("getText");
		var cz_size=$('#t24_process_cs_size').textbox('getValue');
		var cast_powder=$('#cast_powder').val();
		var route=$('#t24_process_route').combobox("getText");
		var isReturned=0;
		var eof_wc=row['heatPlanModel']['target_eof'];
		var product=$('#t24_process_product').combobox("getValue");
		var tundishCar=$('#t24_TundishCar').combobox("getValue");
		var seqType=$("#t24_seq_no").combobox("getValue");
		var pdata=$("#t24_process_prod_date").datebox('getValue');
		
		var prod_shift=$("#t23_shift").combobox('getValue');
		var ladle_car_id=$("#t24_ladleCar").combobox('getValue');
		
		var dt = (pdata).split(" ");
		var proc_date = new Date(commonGridDtfmt(dt[0],dt[1]));
		var time = proc_date.getTime();

		var prodDate=time;
		var d=new Date();
		d.setMinutes (d.getMinutes()+1);
		    
		if(prodDate>d.getTime())
		{
		  $.messager.alert('Information', ' Production date should not be more than sysdate. ');	
		  return;		  
		}
		
		if(ladle_car_id===null || ladle_car_id===''){
			 $.messager.alert('Information', ' Select Ladle Car/Arm.. ');	
			  return;		
		}
		
		if(process_ret_qty!=""){
			isReturned=1;
		}else{
			process_ret_qty=0;
		}
	
		var is_primary=0;
		if(seqType==="primary"){
			is_primary=1;
		}
		var is_flying=0;
	
		if(seqType==="fly"){
			is_flying=1;
		}
		/*
		if(cast_powder != $('#t24_process_cast_powder').combobox("getText")){
			$.messager.alert('Information','Selected Cast powder is not matching with PSN Cast powder '+cast_powder,'info');
		}
		*/
		//added rezon on 12/4/2019
		if(grade===null || grade===''){
			$.messager.alert('Information','Grade Not Available For The Selected PSN','info');
			return false;
		}
	
		var formData={		
			"casterHeatDetails":{"heat_no":row.heat_id,
			"heat_counter":row.heat_counter,
			"cs_size":cz_size,
			"return_qty":process_ret_qty,
			"steel_ladle_no":row.steel_ladle_no,
			"psn_no":row.aim_psn,
			"sub_unit_id":unit_id,
			"casting_powder":$('#t24_process_cast_powder').combobox("getText"),
			"is_returned":isReturned,
			"heat_plan":row.heat_plan_id,
			"heat_line":row.heat_plan_line_no,
			"act_psn_grade":grade,
			"steel_ladle_wgt":row.steel_wgt,
			"eof_wc":eof_wc,
			"route":route,
			"heat_plan_id":row.heat_plan_id,
			"product_id":product,
			"seq_no":$("#t24_process_seq_no").textbox('getValue'),
			"tundish_car_no":tundishCar,
			"grade":$("#t24_process_grade_no").combobox('getValue'),
			"heat_plan_line_no":row.heat_plan_line_no,
			"prod_date":prodDate,
			"casting_powder":$("#t24_process_cast_powder").combobox('getValue'),
			"ladle_car_no":ladle_car_id,
			"ladle_open_type":$('#t23_ladleOpen1').combobox('getValue'),
			"shroud_change":$("#t23_shroud_change").combobox('getValue'),
			"shroud_make":$("#t23_shroud_make").combobox('getValue'),
			"no_of_pipes":$("#t23_noOfPipes").combobox('getValue'),
			"production_shift":prod_shift
			},
			"casterHeatStatus":{
				"main_status":"WIP",
				"current_unit": $('#t23_casterUnit').combobox('getText'),
				"unit_process_status":"PROCESSING",
				"blt_cas_status":"PROCESSING"
			},
			"ccmHeatSeq" :{
				seq_no:$("#t24_process_seq_no").textbox('getValue'),
				is_primary:is_primary,
				is_flying:is_flying,
				primary_seq:$("#hidden_primary").val(),
				fly_seq:$("#hidden_fly").val(),
				seq_type:seqType,
				"tundish_no":$('#t23_tundishNo1').combobox('getValue'),
				"tundish_car_no":tundishCar,
				"shroud_make":$("#t23_shroud_make").combobox("getValue")
			},
			"ccmTundish":{
				  "tun_id":$('#t23_tundishNo1').combobox('getValue'),
				  "prev_tund_id":$("#prev_tund_no").val()
			}
		};
	
		$.ajax({
 		headers: { 
 		'Accept': 'application/json',
 		'Content-Type': 'application/json' 
 		},
 		type: 'POST',
 		data: JSON.stringify(formData),
 		dataType: "json",
 		url: './casterProduction/CasterHeatSave',
 		success: function(data) {
 			if(data.status == 'SUCCESS') 
		    	{
		    	$.messager.alert('CCM Heat Saved',data.comment,'info');
		    	$('#t24_process_heat_form_div_id').dialog('close');
		    	$("#hidden_primary").val("0");
				$("#hidden_fly").val("0");
				$("#cast_powder").val("");
		    	getHeatsWaitingForCaster();
		    	saveT23CCMCrewDetails();
		    	getccmsavedheats();
		    	}
 		},
 		error:function(){      
 			$.messager.alert('Processing','Error while Processing Ajax...','error');
 		}
		});
	}else{
		alert("Select Crew Details");
	}
}
function getccmsavedheats(){
	var unit_id = $('#t23_casterUnit').combobox('getValue');
	
	
	$.ajax({
 		headers: { 
 		'Accept': 'application/json',
 		'Content-Type': 'application/json' 
 		},
 		type: 'GET',
 		//data: JSON.stringify(formData),
 		dataType: "json",
 		url: './casterProduction/getCCMsavedHeats?unit_id='+unit_id,
 		success: function(data) {
 		
 			 $('#t23_caster_production_tbl_id').datagrid('loadData', data);
 		  },
 		error:function(){      
 			$.messager.alert('Processing','Error while Processing Ajax...','error');
 		  }
 		});
}

//CCMTundishDetails save or update
function savett23TundishDtls(){
	var row = $('#t23_caster_production_tbl_id').datagrid('getSelected');
	
	var tranSlNo=row.trns_sl_no;
	var formData={
			tun_trns_id:$("#t23_tund_trns_id").val(),
			tun_id:$("#t23_tundish_id").val(),
			//tun_id:$("#t23_tundishNo").combobox("getValue"),
			tun_loss:$("#t23_tundishLoss").textbox("getValue"),
			liq_temp:$("#t23_liqTemp").numberbox("getValue"),
			tun_temp1:$("#t23_tundish_temp_1").numberbox("getValue"),
			tun_temp2:$("#t23_tundish_temp_2").numberbox("getValue"),
			tun_temp3:$("#t23_tundish_temp_3").numberbox("getValue"),
			super_heat1:$("#t23_super_heat1").numberbox("getValue"),
			super_heat2:$("#t23_super_heat2").numberbox("getValue"),
			super_heat3:$("#t23_super_heat3").numberbox("getValue"),
			trns_sl_no:tranSlNo
	};

	$.ajax({
 		headers: { 
 		'Accept': 'application/json',
 		'Content-Type': 'application/json' 
 		},
 		type: 'POST',
 		data: JSON.stringify(formData),
 		dataType: "json",
 		url: './casterProduction/saveCCMTundishDtls?unit_id='+row.trns_sl_no,
 		success: function(data) {
 			if(data.status=="SUCCESS"){
 				$.messager.alert('SUCCESS',data.comment,'info');
 			}
 		  },
 		error:function(){      
 			$.messager.alert('Processing','Error while Processing Tundish...','error');
 		  }
 		});

}
var footer_act_wght=0;
var byProduct_total=0;
function T23ProduceBatches(){
	var lkp_type = 'BILLET_MAX_LENGTH';
	var lkp_status = 1;
	var row = $('#t23_caster_production_tbl_id').datagrid('getSelected');
	var cs_weight = row.productMasterMdl.section_wgt;
	
	//$("#btn_add_batch").linkbutton("disable");
 	//$("#rmv_add_batch").linkbutton("disable");
 	
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
				document.getElementById('t2_billet_max_length').value = data[i].lookup_value;
			}
		}
	});
 	/*
	$.ajax({
 		headers: { 
 		'Accept': 'application/json',
 		'Content-Type': 'application/json' 
 		},
 		type: 'GET',
 		//data: JSON.stringify(formData),
 		dataType: "json",
 		url: './casterProduction/getBatchDetailsProd?trns_sl_no='+row.trns_sl_no,
 		success: function(data) {
 			if(data.length>0){
 				$("#btn_add_batch").linkbutton("enable");
 				$("#rmv_add_batch").linkbutton("enable");
 				$('#produce_batches_btn').linkbutton('disable');
 				
 			}else{
 				$('#produce_batches_btn').linkbutton('enable');
 			}
 		  },
 		error:function(){      
 			$.messager.alert('Processing','Error while Processing Tundish...','error');
 		  }
 		});
	*/
	$("#t23_caster_batch_dtls_tbl_id1").datagrid({
		columns:[[
			{field:'batch_no',title:'Batch No',width:100,sortable:true},
			{field:'section',title:'Section',width:85},
			{field:'plnd_len',title:'Plan Length (m)',width:77},
			{field:'act_len',title:'Act Length (m)',width:80,
				 editor:{
               	  type:'numberbox',
			       options:{
			       precision:3,
				   required:true
			       }
                 }},
			{field:'act_batch_wgt',title:'Act Weight (t)',width:75,
			editor:{
          	  type:'numberbox',
          	 options:{
				   required:true,
				   precision:3
			       }
            }}
	       
			]]
	});
	$("#t23_caster_batch_dtls_tbl_id1").edatagrid({
		height:500,
		onAfterEdit:function(index, row){
			var table_rows=$("#t23_caster_batch_dtls_tbl_id1").datagrid("getRows");
			footer_BatchWght(table_rows);
		},
		onBeginEdit : function(index,row) {
			var editors = $('#t23_caster_batch_dtls_tbl_id1').datagrid(
			'getEditors', index);
            var prod = $(editors[0].target);            
	        var wgtBox = $(editors[1].target);
 			prod.numberbox({
				onChange : function() {
					var billet_max_length=document.getElementById('t2_billet_max_length').value;
					var product = prod.numberbox('getText');
					var wgt=cs_weight/1000;
					var cut_len =product;
					var batch_no = row.batch_no;
					if(cut_len<=parseFloat(billet_max_length) && cut_len>=0){
						wgtBox.textbox("setText",(wgt*cut_len));
					}else{
						//prod.numberbox("setValue",0);
                        $.messager.alert('INFO','Actual length should be less than '+parseFloat(billet_max_length)+" for:"+batch_no,'info');
					}
		 }});
 			
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
	//var casterHeat_pk=document.getElementById('t28_casterHeat_pk').value;

	$.ajax({
 		headers: { 
 		'Accept': 'application/json',
 		'Content-Type': 'application/json' 
 		},
 		type: 'GET',
 		//data: JSON.stringify(formData),heatid,heat_cntr,subunitid
 		dataType: "json",
 		url: './casterProduction/getCCMProductBatchDetails?trns_sl_no='+row.trns_sl_no,
 		success: function(data) {
 			if(data.length>0){
 				//$("#btn_add_batch").linkbutton("enable");
 				//$("#rmv_add_batch").linkbutton("enable");
 				$('#produce_batches_btn').linkbutton('disable');
 				
 			}else{
 				$('#produce_batches_btn').linkbutton('enable');
 			}
 			//calculating footer rows...
 			footer_BatchWght(data);
 			$("#t23_caster_batch_dtls_tbl_id1").datagrid("loadData",data);
 		}
	});
}

function loadProductBatches(trns_sl_no){
	$.ajax({
 		headers: { 
 		'Accept': 'application/json',
 		'Content-Type': 'application/json' 
 		},
 		type: 'GET',
 		//data: JSON.stringify(formData),heatid,heat_cntr,subunitid
 		dataType: "json",
 		url: './casterProduction/getCCMProductBatchDetails?trns_sl_no='+trns_sl_no,
 		success: function(data) {
 			$("#t23_caster_batch_dtls_tbl_id1").datagrid("loadData",data);
 			T23ProduceBatches();
 		}
	});
}

function loadTundishCombo(seq){
	var tund_sts1, tund_sts2;
	if(seq=="continue"){
		tund_sts1 = "READY";
		tund_sts2 = "RUNNING";
	}else{
		tund_sts1 = "READY";
		tund_sts2 = "READY";
	}
	$.ajax({
 		headers: { 
 		'Accept': 'application/json',
 		'Content-Type': 'application/json' 
 		},
 		type: 'GET',
 		//data: JSON.stringify(formData),
 		dataType: "json",
 		url: './tundishMstr/getTundishMstrDetails',
 		success: function(data) {
 			var tundishData=[];
 			var unit_id = $('#t23_casterUnit').combobox('getValue');
 			for (var i=0;i<data.length;i++){
 				var t=data[i];
 				if(t.casterUnitId==unit_id && (t.tundish_status==tund_sts1 || t.tundish_status==tund_sts2) ){
 					var v={
 							id:t.tundish_sl_no,
 							keyval:t.tundish_sl_no,
 							txtvalue:t.tundish_no
 					};
 					tundishData.push(v);
 				}				
 			}
  			$('#t23_tundishNo1').combobox('loadData',tundishData);
 		},
 		error:function(){      
 			$.messager.alert('Processing','Error while Processing Ajax...','error');
 		  }
 		});
}

/*Tundish Screen*/
function getT23CasterTundishDetails(){
	var row = $('#t23_caster_production_tbl_id').datagrid('getSelected');
	//loadTundishCombo();
	if (row) {
		clearTundishEntryScrn();
		$('#t23_tundishdetails_form_div_id').dialog({
			modal : true,
			cache : true
		});
		$("#t23_tundishHeatNo").textbox("setText",row.heat_no);
		$('#t23_tundishdetails_form_div_id').dialog('open').dialog('center').dialog(
				'setTitle', 'Tundish Details Entry View');
		//loadCombos(row);
		getT23TundishDtls(row.trns_sl_no);
	} else {
		$.messager.alert('Caster Production ', 'Please Select Heat ...!', 'info');
	}
}

function clearTundishEntryScrn(){
	$("#t23_tundishNo").textbox("setText","");
	$("#t23_tundishLoss").textbox("setText","");
	$("#t23_tundishLevel").textbox("setText","");
	$("#t23_ladleOpen").textbox("setText","");
	$("#t23_liqTemp").textbox("setText","");
	$("#t23_tundish_temp_1").textbox("setText","");
	$("#t23_tundish_temp_2").textbox("setText","");
	$("#t23_tundish_temp_3").textbox("setText","");
	
	document.getElementById('t23_tund_trns_id').value = '0';
	document.getElementById('t23_tundish_id').value = '0';
}

function getT23TundishDtls(trns_sl_no){
	$.ajax({
 		headers: { 
 		'Accept': 'application/json',
 		'Content-Type': 'application/json' 
 		},
 		type: 'GET',
 		//data: JSON.stringify(formData),heatid,heat_cntr,subunitid
 		dataType: "json",
 		url: './casterProduction/getCCMTundishDtls?trns_sl_no='+trns_sl_no,
 		success: function(data) {
 			//$("#t23_tundishNo").combobox("setValue",data.tun_id);			
 			$("#t23_tund_trns_id").val(data.tun_trns_id);
 			$("#t23_tundish_id").val(data.tun_id);
 			$("#t23_tundishNo").textbox("setValue",data.tundMstrModel.tundish_no);
			$("#t23_tundishLoss").textbox("setValue",data.tun_loss);
			//$("#t23_tundishLevel").textbox("setValue",data);
			$("#t23_ladleOpen").combobox("setValue",data.ladle_open);
			if(data.liq_temp!=null){
				$("#t23_liqTemp").numberbox("setValue",data.liq_temp);
			}else{
				var row = $('#t23_caster_production_tbl_id').datagrid('getSelected');
				var psn_id=row.psn_no;
				$.ajax({
						headers: { 
						'Accept': 'application/json',
						'Content-Type': 'application/json' 
						},
						type: 'GET',
						//data: JSON.stringify(formData),
						dataType: "json",
						url: "./casterProduction/getChemDtlsByAnalysis?analysis_id=1"+"&psn_id="+psn_id,
						success: function(data1) {
							for(var i=0;i<data1.length;i++){
								if(data1[i].elementName==="Liquidus Temperature"){
									$("#t23_liqTemp").numberbox("setText",data1[i].psn_aim_value);
								}
							}						
						},
						error:function(){      
							$.messager.alert('Processing','Error while Processing Ajax...','error');
						}
				});
			}		
			$("#t23_tundish_temp_1").numberbox("setValue",data.tun_temp1);
			$("#t23_tundish_temp_2").numberbox("setValue",data.tun_temp2);
			$("#t23_tundish_temp_3").numberbox("setValue",data.tun_temp3);	
 		  },
 		  error:function(){      
 			$.messager.alert('Processing','Error while Processing Ajax...','error');
 		  }
 		});
}

function saveT23ProductBatchDtls(){
	$('#t23_caster_batch_dtls_tbl_id1').datagrid("acceptChanges"); 
	//t23_caster_batch_dtls_tbl_id1
	var row = $('#t23_caster_production_tbl_id').datagrid('getSelected');
	var billet_max_length=document.getElementById('t2_billet_max_length').value;
	var formData=$('#t23_caster_batch_dtls_tbl_id1').datagrid('getData').rows;
	for(var i=0;i<formData.length;i++)
		{
		//||formData[i].act_batch_wgt==parseFloat(billet_max_length)
			var batch_no = formData[i].batch_no;
			if(formData[i].act_len>parseFloat(billet_max_length)||formData[i].act_len<=parseFloat(0)){
				 $.messager.alert('INFO',"Actual length must be in range 1 to "+parseFloat(billet_max_length)+" for:"+batch_no,'info');
				 return;	
			}
			if(formData[i].act_batch_wgt<=parseFloat(0)){
				 $.messager.alert('INFO',"Actual weight cannot be 0 for:"+batch_no,'info');
				 return;
			}
			
		}
	$.ajax({
 		headers: { 
 		'Accept': 'application/json',
 		'Content-Type': 'application/json' 
 		},
 		type: 'POST',
 		data: JSON.stringify(formData),
 		dataType: "json",
 		url: './casterProduction/saveCCMBatchDtls?trns_sl_no='+row.trns_sl_no,
 		success: function(data) {
 			$.messager.alert('Processing','Batch details saved','info');
 			//$("#btn_add_batch").linkbutton("enable");
 		 	//$("#rmv_add_batch").linkbutton("enable");
 		 	getT23HeatProdDetails(row.trns_sl_no,row);	
 		},
 		error:function(){   
 			//$("#btn_add_batch").linkbutton("disable");
 		 	//$("#rmv_add_batch").linkbutton("disable");
 			$.messager.alert('Processing','Error while Processing Ajax...','error');
 		}
	});
}
/**chemistry details screen start
 **/
function T23CasterChemDetails(){
	$("#t23_chem_aim_psn").textbox('setText','');
	$('#t23_caster_Chemistry_tbl_id').datagrid('hideColumn', 'remarks');
	var row = $('#t23_caster_production_tbl_id').datagrid('getSelected');
    var nospectro_url = "./CommonPool/getComboList?col1=trns_sl_no&col2=heat_no&classname=CCMHeatDetailsModel&status=1 &wherecol= spectro_chem in(0,1) and record_status=";
	getDropdownList(nospectro_url, '#t23_chem_heat_no');
	$("#t23_chem_heat_no").combobox(
			{
				onSelect : function(record) {
					$.ajax({
				 		headers: { 
				 		'Accept': 'application/json',
				 		'Content-Type': 'application/json' 
				 		},
				 		type: 'GET',
				 		//data: JSON.stringify(formData),
				 		dataType: "json",
				 		url: './casterProduction/getCcmHeatByTrns?trns_sl_no='+record.keyval,
				 		success: function(data) {
				 		$("#t23_chem_aim_psn").textbox("setText",data.psnHdrMstrMdl.psn_no);
				 		$('#rawdata_heatno').val(data.heat_no); //rawdata_heatcounter,rawdata_subunit,rawdata_psnno
				 		$('#rawdata_heatcounter').val(data.heat_counter);
				 		$('#rawdata_subunit').val(data.sub_unit_id);
				 		$('#rawdata_psnno').val(data.psn_no);
				 		$('#rawdata_spectrochem').val(data.spectro_chem);
				 		$('#t23_lift_chem_id').val(data.liftChemId);
				 		
				 		$("#t23_analysis_type").combobox('clear');
				 		setDefaultCustomComboValues('t23_analysis_type',
								'TUNDISH_CHEM', $('#t23_analysis_type').combobox(
										'getData'));
				 		setDefaultCustomComboValues('t23_sample_result',
								'OK', $('#t23_sample_result').combobox(
										'getData'));
				 		//generate sample no,get data from spectro is automated in below functions.....
				 		getT23ChemistryDtlsGrid();
						getT23ChemSampleDtls();
						GetSample();
						//readonly elements are set to false...
						$('#t23_sample_temp').textbox('readonly',false);
						$('#t23_remarks').textbox('readonly',false);
						$('#t23_sample_date_time').datetimebox('readonly',false);
						$('#t23_sample_result').combobox('readonly',false);
				 		  },
				 		error:function(){      
				 			$.messager.alert('Processing','Error while Processing Ajax...','error');
				 		  }
				 		});
				
				}
			});
	//if(row){
	clearAllData();
	$("#t23_analysis_type").combobox('clear');
	getT23Dropdowns();
	$('#t23_chemDetails_form_div_id').dialog({
		modal : true,
		cache : true
		});
	
	$('#t23_chemDetails_form_div_id').dialog('open').dialog('center').dialog(
			'setTitle', 'Chemistry Details');

	var dummydata = new Array();
	$('#t23_caster_Chemistry_tbl_id').datagrid('loadData', dummydata);
	$('#t23_caster_chem_samp_tbl_id').datagrid('loadData', dummydata);
	
	var minmax_flag = false, color_flag = false, rej_flag = '';
  	$('#t23_caster_Chemistry_tbl_id').edatagrid({
  		// saveUrl: './scrapEntry/DtlsSaveOrUpdate',
  	});
	
	    $('#t23_caster_Chemistry_tbl_id').datagrid({  	      	 
	    	  onBeforeEdit:function(index,row){ 
	    		       $('#t23_caster_Chemistry_tbl_id').datagrid('selectRow', index);
	          },
	          onBeginEdit:function(index,row){
	        			  var dg = $(this);
	        			 // var editors = dg.edatagrid('getEditors',index);
	        	color_flag = false;
	        	var editors = $('#t23_caster_Chemistry_tbl_id').datagrid('getEditors', index);
	        	
	          	var minval=row.min_value;
	          	var maxval=row.max_value;
	         	var actVal=$(editors[0].target);
	          	if ((minval == null || minval == '')&& (maxval == null || maxval == '')) {
	          		return false;
	          	}else{
	          		actVal.textbox({onChange : function() {
	          		var aVal = actVal.textbox('getText');
	          		if (aVal != null && aVal != ''){ 
	          			if((minval != null && minval !='') && (maxval != null && maxval !='' )  )  {
						minmax_flag = validateMinMax(aVal, minval,maxval);
						if (!minmax_flag) {
	          				$.messager.alert('Information', 'Actual value ' + aVal + ' should be in between Min '
								+ minval + ' & Max '+ maxval+ ' Values...!','info');
	          				color_flag = true;
	          				rej_flag = index;
	          				setDefaultCustomComboValues('t23_sample_result', 'REJECT', $('#t23_sample_result').combobox('getData'));
	          			}
	          		}else if(minval != null && minval !='' && aVal < minval){
	          			    $.messager.alert('Information', 'Actual value ' + aVal + ' should be more than Min '
								+ minval + '  Values...!','info');
	          				color_flag = true;
	          				rej_flag = index;
	          				setDefaultCustomComboValues('t23_sample_result', 'REJECT', $('#t23_sample_result').combobox('getData'));
	          		}else if(maxval != null && maxval !='' && aVal > maxval){
	          			    $.messager.alert('Information', 'Actual value ' + aVal + ' should be less than Max '+ maxval+ ' Values...!','info');
	          				color_flag = true;
	          				rej_flag = index;
	          				setDefaultCustomComboValues('t23_sample_result', 'REJECT', $('#t23_sample_result').combobox('getData'));
	          		}else{
          				color_flag = false;
          				if(rej_flag != '' && rej_flag == index){
          					setDefaultCustomComboValues('t23_sample_result', 'OK', $('#t23_sample_result').combobox('getData'));
          				}
          			}
	          		}
	          		} });
	          	}
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
	          },
	          rowStyler:function(index, row) {
				if (color_flag) {
					return 'background-color:#ffee00;color:red;';
				}
			 },
			 onEndEdit : function(index, row) {
				$('#t23_caster_Chemistry_tbl_id').datagrid('selectRow', index);	
			 }
	    });
}
function setProductMtrl(){
	$('#t24_product_id').combobox('setValue', '');
	var url3 = "./CommonPool/getComboList?col1=lookup_id&col2=attribute1&classname=LookupMasterModel&status=1&"
		+ "wherecol=lookup_type='CCM_MATERIAL' and lookup_status=";
	 getDropdownList(url3, '#t24_product_id');
	
}

/*Approve chem screen*/
function T23CasterChemApprove(){
	$("#t24_chem_aim_psn").textbox('setText','');
	$('#t23_caster_Chemistry_tbl_id').datagrid('hideColumn', 'remarks');
	setProductMtrl();
	var row = $('#t23_caster_production_tbl_id').datagrid('getSelected');
	$("#t24_product_id").combobox(
			{
				onSelect : function(record) {
					$.ajax({
				 		headers: { 
				 		'Accept': 'application/json',
				 		'Content-Type': 'application/json' 
				 		},
				 		type: 'GET',
				 		//data: JSON.stringify(formData),
				 		dataType: "json",
				 		url: './casterProduction/getheatId?lookup_id='+record.keyval,
				 		success: function(data) {
				 			$("#t24_chem_heat_no").combobox('loadData',data);
				 		
				 		},
				 		error:function(){      
				 			$.messager.alert('Processing','Error while Processing Ajax...','error');
				 		  }
				 		
			})
				}
			});
				
	/*var nospectro_url = "./CommonPool/getComboListmultiple?col1=trns_sl_no&col2=heat_no&classname=CCMHeatDetailsModel&status=1 &wherecol= spectro_chem in(1) and record_status=";
    getDropdownList(nospectro_url, '#t24_chem_heat_no');*/
	$("#t24_chem_heat_no").combobox(
			{
				onSelect : function(record) {
					$.ajax({
				 		headers: { 
				 		'Accept': 'application/json',
				 		'Content-Type': 'application/json' 
				 		},
				 		type: 'GET',
				 		//data: JSON.stringify(formData),
				 		dataType: "json",
				 		url: './casterProduction/getCcmHeatByTrns?trns_sl_no='+record.keyval,
				 		success: function(data) {
				 		$("#t24_chem_aim_psn").textbox("setText",data.psnHdrMstrMdl.psn_no);
				 		$('#rawdata_heatno').val(data.heat_no); //rawdata_heatcounter,rawdata_subunit,rawdata_psnno
				 		$('#rawdata_heatcounter').val(data.heat_counter);
				 		$('#rawdata_subunit').val(data.sub_unit_id);
				 		$('#rawdata_psnno').val(data.psn_no);
				 		$('#rawdata_spectrochem').val(data.spectro_chem);
				 		$('#t24_lift_chem_id').val(data.liftChemId);
				 		$("#t24_chem_grade").textbox("setText",data.psnGradeMasterMdl.psn_grade);
				 		
				 		$("#t24_analysis_type").combobox('clear');
				 		setDefaultCustomComboValues('t24_analysis_type',
								'TUNDISH_CHEM', $('#t24_analysis_type').combobox(
										'getData'));
				 		setDefaultCustomComboValues('t24_sample_result',
								'OK', $('#t24_sample_result').combobox(
										'getData'));
				 		//generate sample no,get data from spectro is automated in below functions.....
				 		getT24ChemistryDtlsGrid();
						getT24ChemSampleDtls();
						GetSample1();
						//readonly elements are set to false...
						$('#t24_sample_temp').textbox('readonly',false);
						$('#t24_remarks').textbox('readonly',false);
						$('#t24_sample_date_time').datetimebox('readonly',false);
						$('#t24_sample_result').combobox('readonly',false);
				 		  },
				 		error:function(){      
				 			$.messager.alert('Processing','Error while Processing Ajax...','error');
				 		  }
				 		});
				
				}
			});
	//if(row){
	clearAllData1();
	$("#t24_analysis_type").combobox('clear');
	getT24Dropdowns();
	$('#t23_chemDetails_apprv_div_id').dialog({
		modal : true,
		cache : true
		});
	
	$('#t23_chemDetails_apprv_div_id').dialog('open').dialog('center').dialog(
			'setTitle', 'Approval Chemistry Details');

	var dummydata = new Array();
	$('#t24_caster_Chemistry_tbl_id').datagrid('loadData', dummydata);
	$('#t24_caster_chem_samp_tbl_id').datagrid('loadData', dummydata);
	
	var minmax_flag = false, color_flag = false, rej_flag = '';
  	$('#t24_caster_Chemistry_tbl_id').edatagrid({
  		// saveUrl: './scrapEntry/DtlsSaveOrUpdate',
  	});
	
	    $('#t24_caster_Chemistry_tbl_id').datagrid({  	      	 
	    	  onBeforeEdit:function(index,row){ 
	    		       $('#t24_caster_Chemistry_tbl_id').datagrid('selectRow', index);
	          },
	          onBeginEdit:function(index,row){
	        			  var dg = $(this);
	        			 // var editors = dg.edatagrid('getEditors',index);
	        	color_flag = false;
	        	var editors = $('#t24_caster_Chemistry_tbl_id').datagrid('getEditors', index);
	        	
	          	var minval=row.min_value;
	          	var maxval=row.max_value;
	          	var actVal=$(editors[0].target);
	          	if ((minval == null || minval == '')
						&& (maxval == null || maxval == '')) {
	          		return false;
	          	}else{
	          		actVal.textbox({onChange : function() {
	          		var aVal = actVal.textbox('getText');
	          		if ((aVal != null && aVal != '')
					&& (minval != null && minval != '')
					&& (maxval != null && maxval != '')) {
	          			minmax_flag = validateMinMax(aVal, minval, maxval);
	          			
	          			if (!minmax_flag) {
	          				$.messager.alert('Information', 'Actual value ' + aVal + ' should be in between Min '
								+ minval + ' & Max '+ maxval+ ' Values...!','info');
	          				color_flag = true;
	          				rej_flag = index;
	          				setDefaultCustomComboValues('t24_sample_result', 'REJECT', $('#t24_sample_result').combobox('getData'));
	          			}else{
	          				color_flag = false;
	          				if(rej_flag != '' && rej_flag == index){
	          					setDefaultCustomComboValues('t24_sample_result', 'OK', $('#t24_sample_result').combobox('getData'));
	          				}
	          			}
	          		}
	          		} });
	          	}
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
	          },
	          rowStyler:function(index, row) {
				if (color_flag) {
					return 'background-color:#ffee00;color:red;';
				}
			 },
			 onEndEdit : function(index, row) {
				$('#t24_caster_Chemistry_tbl_id').datagrid('selectRow', index);	
			 }
	    });
}


//Final Result Datagrid t17_lrf_chem_samp_tbl_id
function getT23ChemSampleDtls() {
	var row = $('#t23_caster_production_tbl_id').datagrid('getSelected');
	var heat_id = $('#rawdata_heatno').val();
	var heat_counter=	$('#rawdata_heatcounter').val();
	var analysis_id = $('#t23_analysis_type').combobox('getValue');
	var sub_unit_id =$('#rawdata_subunit').val();

	if (heat_id != null && heat_id != '') {
		$.ajax({
			headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json'
			},
			type : 'GET',
			dataType : "json",
			url : "./heatProcessEvent/getSampleDtlsByAnalysis?analysis_id="
					+ analysis_id + "&heat_id=" + heat_id + "&sub_unit_id=" + sub_unit_id+"&heat_counter="+heat_counter,
			success : function(data) {
				$('#t23_caster_chem_samp_tbl_id').datagrid('loadData', data);
			},
			error : function() {
				$.messager.alert('Processing',
						'Error while Processing Ajax...', 'error');
			}
		});
	}
}

function getT24ChemSampleDtls() {
	var row = $('#t23_caster_production_tbl_id').datagrid('getSelected');
	var heat_id = $('#rawdata_heatno').val();
	var heat_counter=	$('#rawdata_heatcounter').val();
	var analysis_id = $('#t24_analysis_type').combobox('getValue');
	var sub_unit_id =$('#rawdata_subunit').val();

	if (heat_id != null && heat_id != '') {
		$.ajax({
			headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json'
			},
			type : 'GET',
			dataType : "json",
			url : "./heatProcessEvent/getSampleDtlsByAnalysis?analysis_id="
					+ analysis_id + "&heat_id=" + heat_id + "&sub_unit_id=" + sub_unit_id+"&heat_counter="+heat_counter,
			success : function(data) {
				$('#t24_caster_chem_samp_tbl_id').datagrid('loadData', data);
			},
			error : function() {
				$.messager.alert('Processing',
						'Error while Processing Ajax...', 'error');
			}
		});
	}
}

function getT23ChemistryDtlsGrid(){
	var analysis_id=$("#t23_analysis_type").combobox("getValue");

	var row = $('#t23_caster_production_tbl_id').datagrid('getSelected');
	
	var psn_id=$('#rawdata_psnno').val();
	$.ajax({
   		headers: { 
   		'Accept': 'application/json',
   		'Content-Type': 'application/json' 
   		},
   		type: 'GET',
   		//data: JSON.stringify(formData),
   		dataType: "json",
   		url: "./casterProduction/getChemDtlsByAnalysis?analysis_id="+analysis_id+"&psn_id="+psn_id,
   		success: function(data) {
   			 $('#t23_caster_Chemistry_tbl_id').datagrid('loadData', data);
   		  },
   		error:function(){      
   			$.messager.alert('Processing','Error while Processing Ajax...','error');
   		  }
   		});
}
function validateT23HeatChemForm(){
	    return $('#t23_ccm_Chemistry_form_id').form('validate');
	    }

function getT24ChemistryDtlsGrid(){
	var analysis_id=$("#t24_analysis_type").combobox("getValue");

	var row = $('#t23_caster_production_tbl_id').datagrid('getSelected');
	
	var psn_id=$('#rawdata_psnno').val();
	$.ajax({
   		headers: { 
   		'Accept': 'application/json',
   		'Content-Type': 'application/json' 
   		},
   		type: 'GET',
   		//data: JSON.stringify(formData),
   		dataType: "json",
   		url: "./casterProduction/getChemDtlsByAnalysis?analysis_id="+analysis_id+"&psn_id="+psn_id,
   		success: function(data) {
   			 $('#t24_caster_Chemistry_tbl_id').datagrid('loadData', data);
   		  },
   		error:function(){      
   			$.messager.alert('Processing','Error while Processing Ajax...','error');
   		  }
   		});
}

function validateT24HeatChemForm(){
   
    return $('#t23_ccm_Chemistry_form_id12').form('validate');
}

/*
function saveT23ChemistryDtls(){
	  if(validateT23HeatChemForm()){ 	
		  var row = $('#t23_caster_production_tbl_id').datagrid('getSelected');
		  var sample_date_time = $('#t23_sample_date_time').datetimebox('getValue');
		  var sample_no=$('#t23_sample_no').combobox('getText');	
		  var sample_temp=$('#t23_sample_temp').numberbox('getValue'); 
		  var sub_unit =$('#rawdata_subunit').val();
		  var analysis_type =$('#t23_analysis_type').combobox('getValue');
		  var analysis_type_txt =$('#t23_analysis_type').combobox('getText');
		  var final_result =$('#t23_sample_result').combobox('getValue');
		  var remarks = $('#t23_remarks').textbox('getValue');
		  var sample_si_no = document.getElementById('t23_sample_si_no').value;
		  var heat_id = $('#rawdata_heatno').val();
		  var heat_counter=	$('#rawdata_heatcounter').val();
		  var spectro_chem=	parseInt($('#rawdata_spectrochem').val());
	 
		  if(sample_si_no == null || sample_si_no == ''){
			  sample_si_no = 0;
		  }
    	  var eventname = 'CCM_CHEM_DETAILS';
    	  var rows = $('#t23_caster_Chemistry_tbl_id').datagrid('getRows');
    	  for(var i=0; i<rows.length; i++){
	    	   $('#t23_caster_Chemistry_tbl_id').datagrid('endEdit', i);
    	  }
	      var child_rows = $('#t23_caster_Chemistry_tbl_id').datagrid('getRows');
  	  var grid_arry = '';
  	  for(var i=0; i<child_rows.length; i++){
  		 if((child_rows[i].aim_value !=null )){//child_rows[i].aim_value !=''
  			  grid_arry += child_rows[i].element+'@'+child_rows[i].aim_value+'@'+child_rows[i].min_value+'@'+child_rows[i].max_value+'@'+child_rows[i].dtls_si_no+'SIDS';
  		 }
  		 else{
  		 }
  		
  	  }
  	  
  	formData={"heat_id": heat_id,"heat_counter": heat_counter,"sample_no": sample_no,
		    "sample_temp":sample_temp,"sub_unit_id":sub_unit,
		    "sub_unit_id":sub_unit,"analysis_type":analysis_type,"sample_result":final_result,
		    "analysisType":analysis_type_txt,"remarks":remarks,"sample_si_no":sample_si_no,"grid_arry": grid_arry};
  	
  	
	//Production confirmation from MES to SAP for sending chemistry details
	$.ajax({
  	  headers: { 
  		  'Accept': 'application/json',
  		  'Content-Type': 'application/json' 
  	  },
  	  type: 'GET',
  	  dataType : "json",
  	  url: './heatProcessEvent/getProdConf?heat_id='+heat_id,
  	  success: function(data) {
  		 //Only if production consumption & confirmation is done , chemistry will be sent to SAP....
  		 if (data.status == 'SUCCESS') {
  			$.messager
  			.confirm(
  					'Confirm',
  					'Chemistry will be saved and sent to SAP INTF Table. Please confirm..?',
  					function(r) {
  						if (r) {
  							switch (spectro_chem){
  							case 0://chem get from spectro,save it,finalize it,and send to SAP
  										$.ajax({
  									      	  headers: { 
  									      		  'Accept': 'application/json',
  									      		  'Content-Type': 'application/json' 
  									      	  },
  									      	  type: 'POST',
  									      	  data: JSON.stringify(formData),
  									      	  url: './heatProcessEvent/TundishChemDtlsSaveOrUpdate?eventname='+eventname+'&sample_date='+sample_date_time,
  									      	  success: function(data) {
  									      		  if(data.status == 'SUCCESS'){
  									      			saveFinalResult(data.msg);
  									      			document.getElementById('t23_sample_si_no').value=data.msg;
  									      			$.ajax({
  												      	  headers: { 
  												      		  'Accept': 'application/json',
  												      		  'Content-Type': 'application/json' 
  												      	  },
  												      	  type: 'POST',
  												      	  data: JSON.stringify(formData),
  												      	  url: './casterProduction/sendChemToSap?heatno='+heat_id+'&heatcounter='+heat_counter,
  												      	  success: function(data) {
  												      		 $.messager.alert('SAP Updation',data.status,'info');
  												      		 //reloading the heat no combo box....
  												      		 var nospectro_url = "./CommonPool/getComboList?col1=trns_sl_no&col2=heat_no&classname=CCMHeatDetailsModel&status=1 &wherecol= spectro_chem in(0,1) and record_status=";
  												      		 getDropdownList(nospectro_url, '#t23_chem_heat_no');
  												      		$("#t23_chem_heat_no").combobox('setValue','');
  												      		clearAllData();
  												      	},
  												      	  error:function(){      
  												      		  $.messager.alert('Processing','Error while Sending To SAP INTF Table...','error');
  												      	  }
  												        });
  									      		  }else {
  									      			  $.messager.alert('Chemical Details Info',data.comment,'info');
  									      		  }
  									      	  },
  									      	  error:function(){      
  									      		  $.messager.alert('Processing','Error while Saving Tundish Chem...','error');
  									      	  }
  									        });
  										break;
  							case 1://send to SAP alone...
  								$.ajax({
  							      	  headers: { 
  							      		  'Accept': 'application/json',
  							      		  'Content-Type': 'application/json' 
  							      	  },
  							      	  type: 'POST',
  							      	  data: JSON.stringify(formData),
  							      	  url: './casterProduction/sendChemToSap?heatno='+heat_id+'&heatcounter='+heat_counter,
  							      	  success: function(data) {
  							      		 $.messager.alert('SAP Updation',data.status,'info');
  							      		//reloading the heat no combo box....
  							      		 var nospectro_url = "./CommonPool/getComboList?col1=trns_sl_no&col2=heat_no&classname=CCMHeatDetailsModel&status=1 &wherecol= spectro_chem in(0,1) and record_status=";
  							      		 getDropdownList(nospectro_url, '#t23_chem_heat_no');
  							      		$("#t23_chem_heat_no").combobox('setValue','');
  							      		clearAllData();
  							      	  },
  							      	  error:function(){      
  							      		  $.messager.alert('Processing','Error while Sending To SAP INTF Table...','error');
  							      	  }
  							        });
  								break;
  							default :
  								$.messager.alert('Exception',"Default case :> "+spectro_chem,'info');
  							break;
  							}
  						}//end confirm.....
  					});
		  } else {
			  $.messager.alert('Processing','Heat No : '+heat_id+' is in progress, try again later','info');
		  }
  	  },
  	  error:function(){      
  		  $.messager.alert('Processing','Heat No : '+heat_id+' process error','error');
  	  }
    });
		}//end valid if		  	
}*/

function saveT23ChemistryDtls(){
	if(validateT23HeatChemForm()){ 	
		
		var row = $('#t23_caster_production_tbl_id').datagrid('getSelected');
		var sample_date_time = $('#t23_sample_date_time').datetimebox('getValue');
		var sample_no=$('#t23_sample_no').combobox('getText');	
		var sample_temp=$('#t23_sample_temp').numberbox('getValue'); 
		var sub_unit =$('#rawdata_subunit').val();
		var analysis_type =$('#t23_analysis_type').combobox('getValue');
		var analysis_type_txt =$('#t23_analysis_type').combobox('getText');
		var final_result =$('#t23_sample_result').combobox('getValue');
		var remarks = $('#t23_remarks').textbox('getValue');
		var sample_si_no = document.getElementById('t23_sample_si_no').value;
		var heat_id = $('#rawdata_heatno').val();
		var heat_counter=	$('#rawdata_heatcounter').val();
		var spectro_chem=	parseInt($('#rawdata_spectrochem').val());
		if(sample_si_no == null || sample_si_no == ''){
		  sample_si_no = 0;
		}
  	  	var eventname = 'CCM_CHEM_DETAILS';
  	  	var rows = $('#t23_caster_Chemistry_tbl_id').datagrid('getRows');
  	  	for(var i=0; i<rows.length; i++){
	    	   $('#t23_caster_Chemistry_tbl_id').datagrid('endEdit', i);
  	  	}
	    var child_rows = $('#t23_caster_Chemistry_tbl_id').datagrid('getRows');
	    var grid_arry = '';
	    for(var i=0; i<child_rows.length; i++){
	    	if((child_rows[i].aim_value !=null && child_rows[i].aim_value !='')){
			  grid_arry += child_rows[i].element+'@'+child_rows[i].aim_value+'@'+child_rows[i].min_value+'@'+child_rows[i].max_value+'@'+child_rows[i].dtls_si_no+'SIDS';
	    	}
	    	else{
	    	}
	    }
	  
	    formData={"heat_id": heat_id,"heat_counter": heat_counter,"sample_no": sample_no,
		    "sample_temp":sample_temp,"sub_unit_id":sub_unit,
		    "sub_unit_id":sub_unit,"analysis_type":analysis_type,"sample_result":final_result,
		    "analysisType":analysis_type_txt,"remarks":remarks,"sample_si_no":sample_si_no,"grid_arry": grid_arry};
	    
	
	    //Production confirmation from MES to SAP for sending chemistry details
	    $.ajax({
	    	headers: { 
	    	'Accept': 'application/json',
	    	'Content-Type': 'application/json' 
	    },
	    type: 'GET',
	    dataType : "json",
	    url: './heatProcessEvent/getProdConf?heat_id='+heat_id,
	    success: function(data) {
	    	//Only if production consumption & confirmation is done , chemistry will be sent to SAP....
	    	if (data.status == 'SUCCESS') {
	    		$.ajax({
			      	  headers: { 
			      		  'Accept': 'application/json',
			      		  'Content-Type': 'application/json' 
			      	  },
			      	  type: 'POST',
			      	  data: JSON.stringify(formData),
			      	  url: './heatProcessEvent/TundishChemDtlsSaveOrUpdate?eventname='+eventname+'&sample_date='+sample_date_time,
			      	  success: function(data) {
			      		  $.messager.alert('Chemical Details Info',data.comment,'info');	
			      		  
			      		  $('#t23_sample_date_time').datetimebox({value : ('')});
			      		  $('#t23_sample_no').combobox('setValue', '');
			      		  $('#t23_sample_temp').numberbox('setValue', '');
			      		  $('#t23_sample_result').combobox('setValue', '');
			      		  $('#t23_remarks').textbox('setText', '');
			      		  document.getElementById('t23_sample_si_no').value = '0';
			      		  var dummydata = new Array();
			      		  $('#t23_sample_no').combobox('loadData', dummydata);
			      		  $('#t23_caster_Chemistry_tbl_id').datagrid('loadData', dummydata);
			      		  getT23ChemSampleDtls();
			      		  getT23ChemistryDtlsGrid();
			      	  },
				      error:function(){      
				    	  $.messager.alert('Processing','Error while Saving Tundish Chem...','error');
				      }
	    		});	  
	    	} else {
	    		$.messager.alert('Processing','Heat No : '+heat_id+' is not yet completed, try again later','info');
	    	}
	    },
	    error:function(){      
	    	$.messager.alert('Processing','Heat No : '+heat_id+' process error','error');
	    }
	    });
	}//end valid if		  	
}

$('#t23_caster_chem_samp_tbl_id').datagrid({
	  rowStyler:function(index,row){
		  if (row.final_result == 1){
			  var row = $('#t23_caster_production_tbl_id').datagrid('getSelected');
	        	var analysis_id =$('#t23_analysis_type').combobox('getValue');
	        	var psn_id = $('#rawdata_psnno').val();
	        	var chem_hdr_id = document.getElementById('t23_sample_si_no').value;
	        	var spectro_flag=$('#rawdata_spectrochem').val();
	        	
	        	if(spectro_flag==2){
	        	$('#t23_final_result_btn_id').linkbutton('disable');
	        	$('#t23_save_chem_btn_id').linkbutton('disable');
	        	$('#t23_close_chem_btn_id').linkbutton('disable');
	        	$('#t23_getSample_btn').linkbutton('disable');
	        	$('#t7_spectro').linkbutton('disable');
	        	}
	        	
	        	$.ajax({
	        		headers : {
	        				'Accept' : 'application/json',
	        				'Content-Type' : 'application/json'
	        		},
	        		type : 'GET',
	        		dataType : "json",
	        		url : "./heatProcessEvent/getChemDtlsBySampleHdrId?sample_si_id="
	        				+ chem_hdr_id +"&psn_id=" + psn_id +"&analysis_id="+analysis_id,
	        		success : function(data) {
	        			$('#t23_caster_Chemistry_tbl_id').datagrid('loadData', data);
	        			$('#t23_sample_no').combobox('setValue', $('#t23_sample_no').combobox('getText'));
	        			$('#t23_sample_date_time').datetimebox({	value : formatDate(row.sample_date_time)});
	        			$('#t23_sample_temp').numberbox('setValue', row.sample_temp);
	        			$('#t23_sample_result').combobox('setValue', row.sample_result);
	        			if(row.remarks != 'null'){
	        				$('#t23_remarks').textbox('setText', row.remarks);}
	        			},
	        		error : function() {
	        			$.messager.alert('Processing',
	        						'Error while Processing Ajax...', 'error');
	        		}
	        	});
	        	 
	            return 'background-color:pink;color:blue;font-weight:bold;';
	        }
	    }
});

function saveT24ChemistryDtls(){
	if(validateT24HeatChemForm()){ 	
		
		var row = $('#t23_caster_production_tbl_id').datagrid('getSelected');
		var sample_date_time = $('#t24_sample_date_time').datetimebox('getValue');
		var sample_no=$('#t24_sample_no').combobox('getText');	
		var sample_temp=$('#t24_sample_temp').numberbox('getValue'); 
		var sub_unit =$('#rawdata_subunit').val();
		var analysis_type =$('#t24_analysis_type').combobox('getValue');
		var analysis_type_txt =$('#t24_analysis_type').combobox('getText');
		var final_result =$('#t24_sample_result').combobox('getValue');
		var remarks = $('#t24_remarks').textbox('getValue');
		var sample_si_no = document.getElementById('t24_sample_si_no').value;
		var heat_id = $('#rawdata_heatno').val();
		var heat_counter=	$('#rawdata_heatcounter').val();
		var spectro_chem=	parseInt($('#rawdata_spectrochem').val());
		if(sample_si_no == null || sample_si_no == ''){
		  sample_si_no = 0;
		}
  	  	var eventname = 'CCM_CHEM_DETAILS';
  	  	var rows = $('#t24_caster_Chemistry_tbl_id').datagrid('getRows');
  	  	for(var i=0; i<rows.length; i++){
	    	   $('#t24_caster_Chemistry_tbl_id').datagrid('endEdit', i);
  	  	}
	    var child_rows = $('#t24_caster_Chemistry_tbl_id').datagrid('getRows');
	    var grid_arry = '';
	    for(var i=0; i<child_rows.length; i++){
	    	if((child_rows[i].aim_value !=null && child_rows[i].aim_value !='')){
			  grid_arry += child_rows[i].element+'@'+child_rows[i].aim_value+'@'+child_rows[i].min_value+'@'+child_rows[i].max_value+'@'+child_rows[i].dtls_si_no+'SIDS';
	    	}
	    	else{
	    	}
	    }
	  
	    formData={"heat_id": heat_id,"heat_counter": heat_counter,"sample_no": sample_no,
		    "sample_temp":sample_temp,"sub_unit_id":sub_unit,
		    "sub_unit_id":sub_unit,"analysis_type":analysis_type,"sample_result":final_result,
		    "analysisType":analysis_type_txt,"remarks":remarks,"sample_si_no":sample_si_no,"grid_arry": grid_arry};
	
	    //Production confirmation from MES to SAP for sending chemistry details
	    $.ajax({
	    	headers: { 
	    	'Accept': 'application/json',
	    	'Content-Type': 'application/json' 
	    },
	    type: 'GET',
	    dataType : "json",
	    url: './heatProcessEvent/getProdConf?heat_id='+heat_id,
	    success: function(data) {
	    	//Only if production consumption & confirmation is done , chemistry will be sent to SAP....
	    	if (data.status == 'SUCCESS') {
	    		$.ajax({
			      	  headers: { 
			      		  'Accept': 'application/json',
			      		  'Content-Type': 'application/json' 
			      	  },
			      	  type: 'POST',
			      	  data: JSON.stringify(formData),
			      	  url: './heatProcessEvent/TundishChemDtlsSaveOrUpdate?eventname='+eventname+'&sample_date='+sample_date_time,
			      	  success: function(data) {
			      		  $.messager.alert('Chemical Details Info',data.comment,'info');	
			      		  
			      		  $('#t24_sample_date_time').datetimebox({value : ('')});
			      		  $('#t24_sample_no').combobox('setValue', '');
			      		  $('#t24_sample_temp').numberbox('setValue', '');
			      		  $('#t24_sample_result').combobox('setValue', '');
			      		  $('#t24_remarks').textbox('setText', '');
			      		  document.getElementById('t23_sample_si_no').value = '0';
			      		  var dummydata = new Array();
			      		  $('#t24_sample_no').combobox('loadData', dummydata);
			      		  $('#t24_caster_chem_samp_tbl_id').datagrid('loadData', dummydata);
			      		  getT24ChemSampleDtls();
			      		  getT24ChemistryDtlsGrid();
			      	  },
				      error:function(){      
				    	  $.messager.alert('Processing','Error while Saving Tundish Chem...','error');
				      }
	    		});	  
	    	} else {
	    		$.messager.alert('Processing','Heat No : '+heat_id+' is not yet completed, try again later','info');
	    	}
	    },
	    error:function(){      
	    	$.messager.alert('Processing','Heat No : '+heat_id+' process error','error');
	    }
	    });
	}//end valid if		  	
}
$('#t24_caster_chem_samp_tbl_id').datagrid({
	  rowStyler:function(index,row){
		  if (row.final_result == 1){
			  var row = $('#t23_caster_production_tbl_id').datagrid('getSelected');
	        	var analysis_id =$('#t24_analysis_type').combobox('getValue');
	        	var psn_id = $('#rawdata_psnno').val();
	        	var chem_hdr_id = document.getElementById('t24_sample_si_no').value;
	        	var spectro_flag=$('#rawdata_spectrochem').val();
	        	
	        	if(spectro_flag==2){
	        	$('#t24_final_result_btn_id').linkbutton('disable');
	        	$('#t24_save_chem_btn_id').linkbutton('disable');
	        	$('#t24_close_chem_btn_id').linkbutton('disable');
	        	$('#t24_getSample_btn').linkbutton('disable');
	        	$('#t7_spectro').linkbutton('disable');
	        	}
	        	
	        	$.ajax({
	        		headers : {
	        				'Accept' : 'application/json',
	        				'Content-Type' : 'application/json'
	        		},
	        		type : 'GET',
	        		dataType : "json",
	        		url : "./heatProcessEvent/getChemDtlsBySampleHdrId?sample_si_id="
	        				+ chem_hdr_id +"&psn_id=" + psn_id +"&analysis_id="+analysis_id,
	        		success : function(data) {
	        			$('#t24_caster_Chemistry_tbl_id').datagrid('loadData', data);
	        			$('#t24_sample_no').combobox('setValue', $('#t24_sample_no').combobox('getText'));
	        			$('#t24_sample_date_time').datetimebox({	value : formatDate(row.sample_date_time)});
	        			$('#t24_sample_temp').numberbox('setValue', row.sample_temp);
	        			$('#t24_sample_result').combobox('setValue', row.sample_result);
	        			if(row.remarks != 'null'){
	        				$('#t24_remarks').textbox('setText', row.remarks);}
	        			},
	        		error : function() {
	        			$.messager.alert('Processing',
	        						'Error while Processing Ajax...', 'error');
	        		}
	        	});
	        	 
	            return 'background-color:pink;color:blue;font-weight:bold;';
	        }
	    }
});

function clearAllData1() {
	  $('#t24_sample_date_time').datetimebox({
			value : ('')
	  });
	  $('#t24_sample_no').combobox('setValue', '');
	  $('#t24_sample_temp').numberbox('setValue', '');
	  $('#t24_chem_aim_psn').textbox('setValue', '');
	  $('#t24_analysis_type').combobox('setValue', '');
	  $('#t24_sample_result').combobox('setValue', '');
	  $('#t24_remarks').textbox('setText', '');
	  document.getElementById('t24_sample_si_no').value = '0';
	  var dummydata = new Array();
	  $('#t24_sample_no').combobox('loadData', dummydata);
	  $('#t24_caster_Chemistry_tbl_id').datagrid('loadData', dummydata);
	  $('#t24_caster_chem_samp_tbl_id').datagrid('loadData', dummydata);
		
}


function clearAllData() {
	 /* $('#t23_sample_date_time').datetimebox({
			value : (formatDate(new Date()))
	  });*/
	  $('#t23_sample_date_time').datetimebox({
			value : ('')
	  });
	  $('#t23_sample_no').combobox('setValue', '');
	  $('#t23_sample_temp').numberbox('setValue', '');
	  $('#t23_chem_aim_psn').textbox('setValue', '');
	  $('#t23_analysis_type').combobox('setValue', '');
	  $('#t23_sample_result').combobox('setValue', '');
	  $('#t23_remarks').textbox('setText', '');
	  document.getElementById('t23_sample_si_no').value = '0';
	  var dummydata = new Array();
	  $('#t23_sample_no').combobox('loadData', dummydata);
	  $('#t23_caster_Chemistry_tbl_id').datagrid('loadData', dummydata);
	  $('#t23_caster_chem_samp_tbl_id').datagrid('loadData', dummydata);
		
}

function viewT23SampleDtls(value, row) {
	  /*return '<a href="#" onclick="viewT17ChemDtls(\''
		+ row.sample_si_no +','+row.sample_no+','+row.sample_temp+','+formatDate(row.sample_date_time)+','
		+row.sample_result+','+row.remarks+','+row.chem_validation+ '\')">View Detail</a>';*/
	  return '<a href="#" onclick="viewT23ChemDtls(\''
		+ row.sample_si_no +','+row.sample_no+','+row.sample_temp+','+formatDate(row.sample_date_time)+','
		+row.sample_result+','+row.remarks+ '\')">View Detail</a>';
}

function viewT23ChemDtls(chem_dtls){
	var row = $('#t23_caster_production_tbl_id').datagrid('getSelected');
	var analysis_id = $('#t23_lift_chem_id').val(); //$('#t23_analysis_type').combobox('getValue');
	var psn_id = $('#rawdata_psnno').val();
	var spectro_flag=$('#rawdata_spectrochem').val();
	var c_dtl=chem_dtls.split(",");
	chem_hdr_id = c_dtl[0]; samp_no = c_dtl[1]; samp_temp = c_dtl[2];
	samp_date = c_dtl[3]; samp_res = c_dtl[4]; remarks = c_dtl[5];
	
	document.getElementById('t23_sample_si_no').value=chem_hdr_id;
	//document.getElementById('t17_chem_validation').value=c_dtl[6];
	
	$.ajax({
		headers : {
			'Accept' : 'application/json',
			'Content-Type' : 'application/json'
		},
		type : 'GET',
		dataType : "json",
		url : "./heatProcessEvent/getChemDtlsBySampleHdrId?sample_si_id="
				+ chem_hdr_id +"&psn_id=" + psn_id +"&analysis_id="+analysis_id,
		success : function(data) {
			$('#t23_caster_Chemistry_tbl_id').datagrid('loadData', data);
			
			$('#t23_sample_no').combobox('setValue', samp_no);
			$('#t23_sample_date_time').datetimebox({	value : samp_date});
			$('#t23_sample_temp').numberbox('setValue', samp_temp);
			$('#t23_sample_result').combobox('setValue', samp_res);
			if(spectro_flag==1){
			$('#t23_sample_temp').textbox('readonly');
			$('#t23_remarks').textbox('readonly');
			$('#t23_sample_date_time').datetimebox('readonly');
			$('#t23_sample_result').combobox('readonly');
			}
			if(remarks != 'null'){
			$('#t23_remarks').textbox('setText', remarks);}				
		},
		error : function() {
			$.messager.alert('Processing',
					'Error while Processing Ajax...', 'error');
		}
	})
	$('#t23_caster_Chemistry_tbl_id').datagrid({ rowStyler:function(index,row){
		console.log('rows data '+row);
		if(row.min_value != null && row.max_value != null){
		    if(row.aim_value > row.max_value || row.aim_value < row.min_value )
			return 'background-color:yellow;color:red;font-weight:bold;';
		}else if(row.min_value == null && row.max_value != null ){
		      if(row.aim_value > row.max_value)
			   return 'background-color:yellow;color:red;font-weight:bold;';
		}else if(row.min_value != null && row.max_value == null ){
		      if(row.aim_value < row.min_value)
				   return 'background-color:yellow;color:red;font-weight:bold;';
				
			}
	
	}
        
    
});
}


function viewT24SampleDtls(value, row) {
	  /*return '<a href="#" onclick="viewT17ChemDtls(\''
		+ row.sample_si_no +','+row.sample_no+','+row.sample_temp+','+formatDate(row.sample_date_time)+','
		+row.sample_result+','+row.remarks+','+row.chem_validation+ '\')">View Detail</a>';*/
	  return '<a href="#" onclick="viewT24ChemDtls(\''
		+ row.sample_si_no +','+row.sample_no+','+row.sample_temp+','+formatDate(row.sample_date_time)+','
		+row.sample_result+','+row.remarks+ '\')">View Detail</a>';
}

function viewT24ChemDtls(chem_dtls){
	
	var row = $('#t23_caster_production_tbl_id').datagrid('getSelected');
	var analysis_id = $('#t24_lift_chem_id').val(); //$('#t23_analysis_type').combobox('getValue');
	var psn_id = $('#rawdata_psnno').val();
	var spectro_flag=$('#rawdata_spectrochem').val();
	var c_dtl=chem_dtls.split(",");
	
	chem_hdr_id = c_dtl[0]; 
	samp_no = c_dtl[1]; 
	samp_temp = c_dtl[2];
	samp_date = c_dtl[3]; 
	samp_res = c_dtl[4]; 
	remarks = c_dtl[5];
	document.getElementById('t24_sample_si_no').value=chem_hdr_id;
	//document.getElementById('t17_chem_validation').value=c_dtl[6];
	
	$.ajax({
		headers : {
			'Accept' : 'application/json',
			'Content-Type' : 'application/json'
		},
		type : 'GET',
		dataType : "json",
		url : "./heatProcessEvent/getChemDtlsBySampleHdrId?sample_si_id="
				+ chem_hdr_id +"&psn_id=" + psn_id +"&analysis_id="+analysis_id,
		success : function(data) {
			//console.log('-----> '+data);
			$('#t24_caster_Chemistry_tbl_id').datagrid('loadData', data);
			
			$('#t24_sample_no').combobox('setValue', samp_no);
			$('#t24_sample_date_time').datetimebox({	value : samp_date});
			$('#t24_sample_temp').numberbox('setValue', samp_temp);
			$('#t24_sample_result').combobox('setValue', samp_res);
			if(spectro_flag==1){
			$('#t24_sample_temp').textbox('readonly');
			$('#t24_remarks').textbox('readonly');
			$('#t24_sample_date_time').datetimebox('readonly');
			$('#t24_sample_result').combobox('readonly');
			}
			if(remarks != 'null'){
			$('#t24_remarks').textbox('setText', remarks);}				
		},
		error : function() {
			$.messager.alert('Processing',
					'Error while Processing Ajax...', 'error');
		}
	})
	$('#t24_caster_Chemistry_tbl_id').datagrid({ rowStyler:function(index,row){
		console.log('rows data '+row);
		if(row.min_value != null && row.max_value != null){
		    if(row.aim_value > row.max_value || row.aim_value < row.min_value )
			return 'background-color:yellow;color:red;font-weight:bold;';
		}else if(row.min_value == null && row.max_value != null ){
		      if(row.aim_value > row.max_value)
			   return 'background-color:yellow;color:red;font-weight:bold;';
		}else if(row.min_value != null && row.max_value == null ){
		      if(row.aim_value < row.min_value)
				   return 'background-color:yellow;color:red;font-weight:bold;';
			}
        
    } 
});
}
/*function saveFinalResult(sample_si_no){
	  var row = $('#t23_caster_chem_samp_tbl_id').datagrid('getSelected');
	  var samp_hdr_id = sample_si_no;
	  var sub_unit_id =$('#rawdata_subunit').val();
		
	  $.ajax({
		  headers : {
			  'Accept' : 'application/json',
			  'Content-Type' : 'application/json'
		  },
		  type : 'POST',
		  // data: JSON.stringify(formData),
		  dataType : "json",
		  url : './heatProcessEvent/saveFinalResult?sample_si_no='+samp_hdr_id+"&sub_unit_id="+sub_unit_id,
		  success : function(data) {
			  if (data.status == 'SUCCESS') {
				 // $.messager.alert('Result Info', "Data Finalized Successfully", 'info');
				  getT23ChemSampleDtls();
			  } else {
				  $.messager.alert('Result Info', data.comment, 'info');
			  };
		  },
		  error : function() {
			  $.messager.alert('Processing', 'Error while finalizing tundish chem...',
						'error');
		  }
	  });
}*/

function approved(){
	
	var row = $('#t24_caster_chem_samp_tbl_id').datagrid('getSelected');
	var samp_hdr_id = row.sample_si_no;
	var approv=row.approve;
	var sub_unit_id =$('#rawdata_subunit').val();
	var heat_id = $('#rawdata_heatno').val();
	var heat_counter=	$('#rawdata_heatcounter').val();
	     if (row) {
			approve="0";
			$.ajax({
				headers : {
					'Accept' : 'application/json',
					'Content-Type' : 'application/json'
				},
				type : 'POST',
				dataType : "json",
				 url : './heatProcessEvent/approvechem?sample_si_no='+samp_hdr_id+"&sub_unit_id="+sub_unit_id,
				success : function(data) {
					$.messager.alert('Chemistry Approval Status Info', 'Approved');
					 var nospectro_url = "./CommonPool/getComboListmultiple?col1=trns_sl_no&col2=heat_no&classname=CCMHeatDetailsModel&status=1 &wherecol= spectro_chem in(1,0) and record_status=";
                        getDropdownList(nospectro_url, '#t24_chem_heat_no');
			      		$("#t24_chem_heat_no").combobox('setValue','');
			      		clearAllData1();
				}
			});
			}
   }



function saveFinalResult(){
	  var row = $('#t23_caster_chem_samp_tbl_id').datagrid('getSelected');
	  var samp_hdr_id = row.sample_si_no;
	  var sub_unit_id =$('#rawdata_subunit').val();
	  var heat_id = $('#rawdata_heatno').val();
	  var heat_counter=	$('#rawdata_heatcounter').val();
	  var rows = $('#t23_caster_Chemistry_tbl_id').datagrid('getRows');
	  var approv=row.approve;
	  if(approv == 0){
	  for(var i=0; i<rows.length; i++){
		  if(rows[i].aim_value < rows[i].min_value || rows[i].aim_value > rows[i].max_value ){
				$.messager.alert('INFO','Cannot POST TO SAP,Required HOD Approval...','info');
				return false;
		}
	  }
	 }else{
	  $.messager.confirm('Confirm', 'Chemistry will be finalized and sent to SAP. Please confirm..?',
		function(r) {
		  if (r) {
			  $.ajax({
				  headers : {
					  'Accept' : 'application/json',
					  'Content-Type' : 'application/json'
				  },
				  type : 'POST',
				  dataType : "json",
				  url : './heatProcessEvent/saveFinalResult?sample_si_no='+samp_hdr_id+"&sub_unit_id="+sub_unit_id,
				  success : function(data) {
					  if (data.status == 'SUCCESS') {
						  
						  document.getElementById('t23_sample_si_no').value=data.msg;
			      		  $.ajax({
			      			  headers: { 
			      				  'Accept': 'application/json',
						      	  'Content-Type': 'application/json' 
			      			  },
						      type: 'POST',
						      dataType : "json",
						      url: './casterProduction/sendChemToSap?heatno='+heat_id+'&heatcounter='+heat_counter,
						      success: function(data) {
						    	  	$.messager.alert('Result Info', data.comment, 'info');
						    	  	var nospectro_url = "./CommonPool/getComboList?col1=trns_sl_no&col2=heat_no&classname=CCMHeatDetailsModel&status=1 &wherecol= spectro_chem in(0,1) and record_status=";
						      		getDropdownList(nospectro_url, '#t23_chem_heat_no');
						      		$("#t23_chem_heat_no").combobox('setValue','');
						      		clearAllData();
						      },
						      error:function(){      
						      		  $.messager.alert('Processing','Error while Sending To SAP INTF Table...','error');
						      }
			      		  });
			      		  getT23ChemSampleDtls();
					  } else {
						  $.messager.alert('Result Info', data.comment, 'info');
					  };
				  },
				  error : function() {
					  $.messager.alert('Processing', 'Error while finalizing tundish chem...',
								'error');
				  }
			  });			        			
		  }//end confirm.....
	  	}
	  );
	 } 
	  
}

function GetSample(){
	  //refreshT17Chem();
	  GenerateSampleNo();
}

function GenerateSampleNo() {
 	var analysis_id=$("#t23_analysis_type").combobox("getValue");
	var row = $('#t23_caster_production_tbl_id').datagrid('getSelected');
	var heat_no=$('#rawdata_heatno').val();
	var heat_counter=$('#rawdata_heatcounter').val();
	var sub_unit_id=$('#rawdata_subunit').val();
 	var v_url = './heatProcessEvent/getSampleNo?heat_id='+heat_no+'&heat_counter='+heat_counter
 	+'&sub_unit_id=' + sub_unit_id +  '&analysis_type='+ analysis_id;
 	
 	$.ajax({
 		headers : {
 			'Accept' : 'application/json',
 			'Content-Type' : 'application/json'
 		},
 		type : 'GET',
 		// data: JSON.stringify(formData),
 		dataType : "json",
 		url : v_url,
 		success : function(data) {
 			$('#t23_sample_no').textbox('setText',data.comment);
 			$("#t23_sample_no").combobox('disable');	
 			//ccm_level2_server();//getting chem from spectro in CasterProduction.jsp-->cm_level2_server()
 		},
 		error : function() {
 			$.messager.alert('Processing', 'Error while getting Sample No',
 					'error');
 		}
 	});
 }


function getT23Dropdowns(){
    var url1="./CommonPool/getComboList?col1=lookup_id&col2=lookup_value&classname=LookupMasterModel&status=1&" +
	    "wherecol=lookup_type='CHEM_LEVEL' and lookup_value in ('TUNDISH_CHEM') and lookup_status=";
    var url2="./CommonPool/getComboList?col1=lookup_id&col2=lookup_value&classname=LookupMasterModel&status=1&" +
	    "wherecol=lookup_type='CHEM_TEST_RESULT' and lookup_status=";
  		
    getDropdownList(url1,'#t23_analysis_type');
    getDropdownList(url2,'#t23_sample_result');
  }

function GetSample1(){
	 //refreshT17Chem();
	  GenerateSampleNo1();
}

function GenerateSampleNo1() {
	var analysis_id=$("#t24_analysis_type").combobox("getValue");
	var row = $('#t23_caster_production_tbl_id').datagrid('getSelected');
	var heat_no=$('#rawdata_heatno').val();
	var heat_counter=$('#rawdata_heatcounter').val();
	var sub_unit_id=$('#rawdata_subunit').val();
	var v_url = './heatProcessEvent/getSampleNo?heat_id='+heat_no+'&heat_counter='+heat_counter
	+'&sub_unit_id=' + sub_unit_id +  '&analysis_type='+ analysis_id;
	
	$.ajax({
		headers : {
			'Accept' : 'application/json',
			'Content-Type' : 'application/json'
		},
		type : 'GET',
		// data: JSON.stringify(formData),
		dataType : "json",
		url : v_url,
		success : function(data) {
			$('#t24_sample_no').textbox('setText',data.comment);
			$("#t24_sample_no").combobox('disable');	
			//ccm_level2_server();//getting chem from spectro in CasterProduction.jsp-->cm_level2_server()
		},
		error : function() {
			$.messager.alert('Processing', 'Error while getting Sample No',
					'error');
		}
	});
}


function getT24Dropdowns(){
    var url1="./CommonPool/getComboList?col1=lookup_id&col2=lookup_value&classname=LookupMasterModel&status=1&" +
	    "wherecol=lookup_type='CHEM_LEVEL' and lookup_value in ('TUNDISH_CHEM') and lookup_status=";
    var url2="./CommonPool/getComboList?col1=lookup_id&col2=lookup_value&classname=LookupMasterModel&status=1&" +
	    "wherecol=lookup_type='CHEM_TEST_RESULT' and lookup_status=";
  		
    getDropdownList(url1,'#t24_analysis_type');
    getDropdownList(url2,'#t24_sample_result');
  }

//process param

function T23ProcessParam(){
	var row = $('#t23_caster_production_tbl_id').datagrid('getSelected');
	var unit_id=$("#t23_casterUnit").combobox("getValue");
	if (row) {		
		$("#t24_heat_no").textbox("setValue",row.heat_no);
		$("#t24_aim_psn").textbox("setValue",row.psnHdrMstrMdl.psn_no);
		$("#t24_1_heat_no").textbox("setValue",row.heat_no);
		$("#t24_1_aim_psn").textbox("setValue",row.psnHdrMstrMdl.psn_no);
		$('#t24_process_param_form_div_id').dialog({
			modal : true,
			cache : true
		});
		$('#t24_process_param_form_div_id').dialog('open').dialog('center')
				.dialog('setTitle', 'Process Parameters Details Form');
		$('#t24_heatwise_process_param_tbl_id').edatagrid({
		     // saveUrl: './scrapEntry/DtlsSaveOrUpdate',
		});
		
		$("#t24_heatwise_process_param_tbl_id").edatagrid({
			onBeforeEdit : function(index, row) {
				row.process_date_time = formatDate(row.process_date_time);
				$('#t24_heatwise_process_param_tbl_id').datagrid(
						'selectRow', index);
			},
			onEndEdit : function(index, row) {
				$('#t24_heatwise_process_param_tbl_id').datagrid(
						'selectRow', index);
				var dt = (row.process_date_time).split(" ");
				var proc_date = new Date(commonGridDtfmt(dt[0],
						dt[1]));
				var time = proc_date.getTime();
				row.process_date_time = time;
			},
		});
		
		var product = row.productMasterMdl.prodSecMtrlLookupModel.lookup_value;
		//alert(product);
		if(product.includes('SLB')){
			$('#t24_slb_pp').show();
			$('#t24_blt_pp').hide();
			
			$("#t24_process_param_tbl_id_2").edatagrid({
				onBeforeEdit : function(index, row) {
					row.process_date_time = formatDate(row.process_date_time);
					$('#t24_process_param_tbl_id_2').datagrid(
							'selectRow', index);
				},
				onEndEdit : function(index, row) {
					$('#t24_process_param_tbl_id_2').datagrid(
							'selectRow', index);
					var dt = (row.process_date_time).split(" ");
					var proc_date = new Date(commonGridDtfmt(dt[0],
							dt[1]));
					var time = proc_date.getTime();
					row.process_date_time = time;
				},
			});
		}else{
			$('#t24_blt_pp').show();
			$('#t24_slb_pp').hide();
			
			$("#t24_process_param_tbl_id").edatagrid({
				onBeforeEdit : function(index, row) {
					row.process_date_time = formatDate(row.process_date_time);
					$('#t24_process_param_tbl_id').datagrid(
							'selectRow', index);
				},
				onEndEdit : function(index, row) {
					$('#t24_process_param_tbl_id').datagrid(
							'selectRow', index);
					var dt = (row.process_date_time).split(" ");
					var proc_date = new Date(commonGridDtfmt(dt[0],
							dt[1]));
					var time = proc_date.getTime();
					row.process_date_time = time;
				},
			});
		}
		
		getProcParam(row.heat_no, row.heat_counter, unit_id, product);
		getHeatwiseProcParam(row.heat_no, row.heat_counter, unit_id, row.psnHdrMstrMdl.psn_no);
	} else {
		$.messager.alert('Caster Production ',
				'Please Select Heat ...!', 'info');
	}	
}
function getProcParam(heat_no, heat_counter, unit_id, product){
	$.ajax({
 		headers : {
 			'Accept' : 'application/json',
 			'Content-Type' : 'application/json'
 		},
 		type : 'GET',
 		// data: JSON.stringify(formData),
 		dataType : "json",
 		url : './casterProduction/getProcessParam?unit_id='+unit_id+'&heat_no='+heat_no+'&heat_counter='+heat_counter,
 		success : function(data) {
 			if(product.includes('SLB')){
 				$('#t24_process_param_tbl_id_2').datagrid('loadData', data);	 
 			}else{
 				$('#t24_process_param_tbl_id').datagrid('loadData', data);	 
 			}
 		}
 		,
 		error : function() {
 			$.messager.alert('Processing', 'Error while loading process param',
 					'error');
 		}
 	});
}
function getHeatwiseProcParam(heatid, heat_cntr, subunitid, psn_no){
	if (psn_no != null && psn_no != '') {	
		$.ajax({
     		headers: { 
     		'Accept': 'application/json',
     		'Content-Type': 'application/json' 
     		},
     		type: 'GET',
     		dataType: "json",
     		url: './heatProcessEvent/getHeatHeatProcParamDtls?heatid='+heatid+'&heat_cntr='+heat_cntr+'&subunitid='+subunitid+ '&psn_no=' + psn_no,
     		success: function(data) {
     			 $('#t24_heatwise_process_param_tbl_id').datagrid('loadData', data);
     		},
     		error:function(){      
     			$.messager.alert('Processing','Error while Processing Ajax...','error');
     		}
		});
	 }
}
function savett24ProcessParamDtls(){
	var row = $('#t23_caster_production_tbl_id').datagrid('getSelected');
	var product = row.productMasterMdl.prodSecMtrlLookupModel.lookup_value;
	var values;
	
	if(product.includes('SLB')){
		$("#t24_process_param_tbl_id_2").datagrid("acceptChanges"); 
		values=$('#t24_process_param_tbl_id_2').datagrid('getData');
	}else{
		$("#t24_process_param_tbl_id").datagrid("acceptChanges"); 
		values=$('#t24_process_param_tbl_id').datagrid('getData');
	}
	$.ajax({
 		headers : {
 			'Accept' : 'application/json',
 			'Content-Type' : 'application/json'
 		},
 		type : 'POST',
 		 data: JSON.stringify(values.rows),
 		dataType : "json",
 		url : './casterProduction/saveCCMProcParam?heatNo='+row.heat_no,
 		success : function(data) {
 			$.messager.alert('Process Parameters Details Info',
 					data.comment, 'info');
 			
 		},
 		error : function() {
 			$.messager.alert('Processing', 'Error while saving process param',
 					'error');
 		}
 	});
}

function formatDateStr(colName, value, row, index){
	if(eval("row."+colName) == null)
	{
	return "";
	}else{
		var col=eval("row."+colName);
	return formatDateTimeStr(col);
	}

}

function formatDateTimeStr(d){
	if(d!=null && d!==''){
		var dpart=d.split(" ");
		var d1part=dpart[0].split("/");
		
		var date=new Date(d1part[2]+"-"+d1part[1]+"-"+d1part[0]+" "+dpart[1]);
		return addZero(date.getDate())+"/"+ addZero(date.getMonth()+1) + "/" + date.getFullYear() + " " + 
        addZero(date.getHours()) + ":" + addZero(date.getMinutes()) + ":" + addZero(date.getSeconds());
	}else{
		return '';
	}
}

function getT23CasterMouldDtls(){
	var row = $('#t23_caster_production_tbl_id').datagrid('getSelected');
	var mouldData = [];
	var v_url = "./CommonPool/getComboList?col1=ccm_mtube_trns_id&col2=jcktNoLookupMdl.lookup_value&classname=MtubeTrnsModel&status=1&wherecol=mtube_status in ('READY FOR USE','RUNNING') and record_status=";
	$.ajax({
 		headers : {
 			'Accept' : 'application/json',
 			'Content-Type' : 'application/json'
 		},
 		type : 'GET',
 		dataType : "json",
 		url : v_url,
 		success : function(data) {
 			for(var i=0;i<data.length;i++){
 				mouldData.push(data[i]);
 			}
 		},
 		error : function() {
 			$.messager.alert('Processing', 'Error while getting Sample No',
 					'error');
 		}
 	});
	
	$('#t23_btn_save').linkbutton('disable');
	if (row) {
		$('#t23_mould_details_form_div_id').dialog({
			modal : true,
			cache : true
		});
		$('#t23_mould_details_form_div_id').dialog('open').dialog('center')
			.dialog('setTitle', 'Mould Details');
		
		var product = row.productMasterMdl.prodSecMtrlLookupModel.lookup_value;
		//alert(product);
		if(product.includes('SLB'))
			getMouldDtlsForSlabCast(mouldData);
		else
			getMouldDtlsForBltCast(mouldData);
	}else{
		$.messager.alert('Caster Production ',
				'Please Select Heat ...!', 'info');
	}
}

function getMouldDtlsForBltCast(mouldData){
	$("#t23_caster_mould_dtls_tbl_id").edatagrid({
		columns:[[
	        {field:'label',title:'',width:200},
	        {field:'std1',title:'',width:100,
                  editor:{
                	  type:'numberbox',options:{precision:1}
                  }	
	        },
	        {field:'std2',title:'',width:100,
	        	editor:{
              	  type:'numberbox',options:{precision:1}
                }	
	        },
	        {field:'std3',title:'',width:100,
	        	editor:{
              	  type:'numberbox',options:{precision:1}
                }	
	        },
	        {field:'std4',title:'',width:100,	        
	        	editor:{
              	  type:'numberbox',options:{precision:1}
                }	
	        },
	        {field:'std5',title:'',width:100,
	        	editor:{
              	  type:'numberbox',options:{precision:1}
                }	
	        },
	        {field:'std6',title:'',width:100,	        
	        	editor:{
              	  type:'numberbox',options:{precision:1}
                }	
	        }
	    ]],
	    onBeforeEdit: function(index,row){
	    	var col1 = $(this).datagrid('getColumnOption','std1');
			var col2 = $(this).datagrid('getColumnOption','std2');
			var col3 = $(this).datagrid('getColumnOption','std3');
			var col4 = $(this).datagrid('getColumnOption','std4');
			var col5 = $(this).datagrid('getColumnOption','std5');
			var col6 = $(this).datagrid('getColumnOption','std6');
			$('#t23_btn_save').linkbutton('enable');
			if(row.label=="Mould Tube"){
				col1.editor=null;
	    		col2.editor=null;
	    		col3.editor=null;
	    		col4.editor=null;
	    		col5.editor=null;
	    		col6.editor=null;
			}
			else if(row.label=="Mould Jacket No" ){
				col1.editor = {
						type: 'combobox',
						 formatter :function (v,r,i){return formatColumnData('jcktNoLookupMdl.lookup_value',v,r,i);},
						 options:{
								//url:"./CommonPool/getComboList?col1=ccm_mtube_trns_id&col2=jcktNoLookupMdl.lookup_value&classname=MtubeTrnsModel&status=1&wherecol=mtube_status in ('READY FOR USE','RUNNING') and record_status=",
								//method:'get',
								valueField:'keyval',
				                textField:'txtvalue',
				                data: mouldData,
				                onSelect:function(value){
				                        var tdata=$("#t23_caster_mould_dtls_tbl_id").datagrid("getData").rows;
				                        tdata[1].std1=value.keyval;
				                        tdata[1].std1=value.txtvalue;
				                        $("#mtube1").val(value.keyval);
				                        $.ajax({
				                	 		headers: { 
				                	 		'Accept': 'application/json',
				                	 		'Content-Type': 'application/json' 
				                	 		},
				                	 		type: 'GET',
				                	 		dataType: "json",
				                	 		url:'./casterProduction/getMouldTubeLife?ccm_mtube_trns_id='+value.keyval,
				                	 		success: function(data1) {
				                	 			tdata[2].std1=data1.mtubeMasterMdl.mould_tube_no;
				                	 			tdata[3].std1=data1.current_life;
				                	 			tdata[4].std1=data1.total_life;
				                	 			
							                    $("#t23_caster_mould_dtls_tbl_id").datagrid("loadData",tdata);	}
				                     	});				                       
				                }
							}
				};
				col2.editor = {
						type: 'combobox',
						formatter :function (v,r,i){return formatColumnData('jcktNoLookupMdl.lookup_value',v,r,i);},
						options:{
							//url:"./CommonPool/getComboList?col1=ccm_mtube_trns_id&col2=jcktNoLookupMdl.lookup_value&classname=MtubeTrnsModel&status=1&wherecol=mtube_status in ('READY FOR USE','RUNNING') and record_status=",
							//method:'get',
							valueField:'keyval',
			                textField:'txtvalue',
			                data: mouldData,
			                onSelect:function(value){
		                        var tdata=$("#t23_caster_mould_dtls_tbl_id").datagrid("getData").rows;
		                        tdata[1].std2=value.keyval;
		                        tdata[1].std2=value.txtvalue;
		                        $("#mtube2").val(value.keyval);
		                        $.ajax({
		                	 		headers: { 
		                	 		'Accept': 'application/json',
		                	 		'Content-Type': 'application/json' 
		                	 		},
		                	 		type: 'GET',
		                	 		dataType: "json",
		                	 		url:'./casterProduction/getMouldTubeLife?ccm_mtube_trns_id='+value.keyval,
		                	 		success: function(data1) {
		                	 			tdata[2].std2=data1.mtubeMasterMdl.mould_tube_no;
		                	 			tdata[3].std2=data1.cleaning_life;
		                	 			tdata[4].std2=data1.total_life;
					                    $("#t23_caster_mould_dtls_tbl_id").datagrid("loadData",tdata);	}
		                     	});	                        
		                }
						}
				};
				col3.editor = {
						type: 'combobox',
						formatter :function (v,r,i){return formatColumnData('jcktNoLookupMdl.lookup_value',v,r,i);},
						options:{
							//url:"./CommonPool/getComboList?col1=ccm_mtube_trns_id&col2=jcktNoLookupMdl.lookup_value&classname=MtubeTrnsModel&status=1&wherecol=mtube_status in ('READY FOR USE','RUNNING') and record_status=",
							//method:'get',
							valueField:'keyval',
			                  textField:'txtvalue',
			                  data: mouldData,
				                onSelect:function(value){
			                        var tdata=$("#t23_caster_mould_dtls_tbl_id").datagrid("getData").rows;
			                        tdata[1].std3=value.keyval;
			                        tdata[1].std3=value.txtvalue;
			                        $("#mtube3").val(value.keyval);
			                        $.ajax({
			                	 		headers: { 
			                	 		'Accept': 'application/json',
			                	 		'Content-Type': 'application/json' 
			                	 		},
			                	 		type: 'GET',
			                	 		dataType: "json",
			                	 		url:'./casterProduction/getMouldTubeLife?ccm_mtube_trns_id='+value.keyval,
			                	 		success: function(data1) {
			                	 			tdata[2].std3=data1.mtubeMasterMdl.mould_tube_no;
			                	 			tdata[3].std3=data1.cleaning_life;
			                	 			tdata[4].std3=data1.total_life; 
						                    $("#t23_caster_mould_dtls_tbl_id").datagrid("loadData",tdata);	}
			                     	});
			                }
						}
				};
				col4.editor = {
						type: 'combobox',
						formatter :function (v,r,i){return formatColumnData('jcktNoLookupMdl.lookup_value',v,r,i);},
						options:{
							//url:"./CommonPool/getComboList?col1=ccm_mtube_trns_id&col2=jcktNoLookupMdl.lookup_value&classname=MtubeTrnsModel&status=1&wherecol=mtube_status in ('READY FOR USE','RUNNING') and record_status=",
							//method:'get',
							valueField:'keyval',
			                textField:'txtvalue',
			                data: mouldData,
				                onSelect:function(value){
			                        var tdata=$("#t23_caster_mould_dtls_tbl_id").datagrid("getData").rows;
			                        tdata[1].std4=value.keyval;
			                        tdata[1].std4=value.txtvalue;
			                        $("#mtube4").val(value.keyval);
			                        $.ajax({
			                	 		headers: { 
			                	 		'Accept': 'application/json',
			                	 		'Content-Type': 'application/json' 
			                	 		},
			                	 		type: 'GET',
			                	 		dataType: "json",
			                	 		url:'./casterProduction/getMouldTubeLife?ccm_mtube_trns_id='+value.keyval,
			                	 		success: function(data1) {
			                	 			tdata[2].std4=data1.mtubeMasterMdl.mould_tube_no;
			                	 			tdata[3].std4=data1.cleaning_life;
			                	 			tdata[4].std4=data1.total_life; 
						                    $("#t23_caster_mould_dtls_tbl_id").datagrid("loadData",tdata);	}
			                     	});
			                }
						}
				};
				col5.editor = {
						type: 'combobox',
						formatter :function (v,r,i){return formatColumnData('jcktNoLookupMdl.lookup_value',v,r,i);},
						options:{
							//url:"./CommonPool/getComboList?col1=ccm_mtube_trns_id&col2=jcktNoLookupMdl.lookup_value&classname=MtubeTrnsModel&status=1&wherecol=mtube_status in ('READY FOR USE','RUNNING') and record_status=",
							//method:'get',
							valueField:'keyval',
			                  textField:'txtvalue',
			                  data: mouldData,
				                onSelect:function(value){
			                        var tdata=$("#t23_caster_mould_dtls_tbl_id").datagrid("getData").rows;
			                        tdata[1].std5=value.keyval;
			                        tdata[1].std5=value.txtvalue;
			                        $("#mtube5").val(value.keyval);
			                        $.ajax({
			                	 		headers: { 
			                	 		'Accept': 'application/json',
			                	 		'Content-Type': 'application/json' 
			                	 		},
			                	 		type: 'GET',
			                	 		dataType: "json",
			                	 		url:'./casterProduction/getMouldTubeLife?ccm_mtube_trns_id='+value.keyval,
			                	 		success: function(data1) {
			                	 			tdata[2].std5=data1.mtubeMasterMdl.mould_tube_no;
			                	 			tdata[3].std5=data1.cleaning_life;
			                	 			tdata[4].std5=data1.total_life; 
						                    $("#t23_caster_mould_dtls_tbl_id").datagrid("loadData",tdata);	}
			                     	});
			                }
						}
				};
				col6.editor = {
						type: 'combobox',
						formatter :function (v,r,i){return formatColumnData('jcktNoLookupMdl.lookup_value',v,r,i);},
						options:{
							//url:"./CommonPool/getComboList?col1=ccm_mtube_trns_id&col2=jcktNoLookupMdl.lookup_value&classname=MtubeTrnsModel&status=1&wherecol=mtube_status in ('READY FOR USE','RUNNING') and record_status=",
							//method:'get',
							valueField:'keyval',
			                  textField:'txtvalue',
			                  data: mouldData,
				                onSelect:function(value){
			                        var tdata=$("#t23_caster_mould_dtls_tbl_id").datagrid("getData").rows;
			                        tdata[1].std6=value.keyval;
			                        tdata[1].std6=value.txtvalue;
			                        $("#mtube6").val(value.keyval);
			                        $.ajax({
			                	 		headers: { 
			                	 		'Accept': 'application/json',
			                	 		'Content-Type': 'application/json' 
			                	 		},
			                	 		type: 'GET',
			                	 		dataType: "json",
			                	 		url:'./casterProduction/getMouldTubeLife?ccm_mtube_trns_id='+value.keyval,
			                	 		success: function(data1) {
			                	 			tdata[2].std6=data1.mtubeMasterMdl.mould_tube_no;
			                	 			tdata[3].std6=data1.cleaning_life;
			                	 			tdata[4].std6=data1.total_life; 
						                    $("#t23_caster_mould_dtls_tbl_id").datagrid("loadData",tdata);	}
			                     	});
			                }
						}
				};
			}
	    	else if(row.label=="Strand No" ){
	    		col1.editor=null;
	    		col2.editor=null;
	    		col3.editor=null;
	    		col4.editor=null;
	    		col5.editor=null;
	    		col6.editor=null;
	    	}else if(row.label=="Cleaning Life" ){
	    		col1.editor=null;
	    		col2.editor=null;
	    		col3.editor=null;
	    		col4.editor=null;
	    		col5.editor=null;
	    		col6.editor=null;
	    	}else if(row.label=="Mould Total Life" ){
	    		col1.editor=null;
	    		col2.editor=null;
	    		col3.editor=null;
	    		col4.editor=null;
	    		col5.editor=null;
	    		col6.editor=null;
	    	}
	    	else if(row.label=="select"){
	    		col1.editor={
	    			type:'checkbox',
	    			options:{
	    				on: 'Y',
	    				off: 'N'
	    			}
	    		};
	    		col2.editor={
	    				type:'checkbox',
		    			options:{
		    				on: 'Y',
		    				off: 'N'
		    			}
		    	};
	    		col3.editor={
		    			type:'checkbox',
		    			options:{
		    				on: 'Y',
		    				off: 'N'
		    			}
		    	};
	    		col4.editor={
	    				type:'checkbox',
		    			options:{
		    				on: 'Y',
		    				off: 'N'
		    			}
		    	};
	    		col5.editor={
		    			type:'checkbox',
		    			options:{
		    				on: 'Y',
		    				off: 'N'
		    			}
		    	};
	    		col6.editor={
		    			type:'checkbox',
		    			options:{
		    				on: 'Y',
		    				off: 'N'
		    			}
		    	};
	    	}
	    	else{
				col1.editor={
	              	  type:'numberbox',options:{precision:1}
	                };	
				col2.editor={
		              	  type:'numberbox',options:{precision:1}
		                };	
				col3.editor={
		              	  type:'numberbox',options:{precision:1}
		                };	
				col4.editor={
		              	  type:'numberbox',options:{precision:1}
		                };	
				col5.editor={
		              	  type:'numberbox',options:{precision:1}
		                };	
				col6.editor={
		              	  type:'numberbox',options:{precision:1}
		                };	
			}
	    }
	    });
	
		var hrow = $('#t23_caster_production_tbl_id').datagrid('getSelected');
	    loadModuldTabData(hrow);	
}

function loadModuldTabData(row){
	var trns_sl_no = row.trns_sl_no;
	var product = row.productMasterMdl.prodSecMtrlLookupModel.lookup_value;
	//alert(product);
	if(product.includes('SLB'))
		loadModuldTabDataForSlabCast(trns_sl_no);
	else
		loadModuldTabDataForBltCast(trns_sl_no);
}

function loadModuldTabDataForBltCast(trns_sl_no){
	var tableData=[];
	$.ajax({
	 		headers: { 
	 		'Accept': 'application/json',
	 		'Content-Type': 'application/json' 
	 		},
	 		type: 'GET',
	 		//data: JSON.stringify(formData),heatid,heat_cntr,subunitid
	 		dataType: "json",
	 		url: './casterProduction/getCCMProdDtls?trns_sl_no='+trns_sl_no,
	 		success: function(data) {
	 				var details={
 						label:"Strand No",
 						std1:data[0]!=null?data[0].standlkpMdl.lookup_value:"1",
 						std2:data[1]!=null?data[1].standlkpMdl.lookup_value:"2",
 						std3:data[2]!=null?data[2].standlkpMdl.lookup_value:"3",
 						std4:data[3]!=null?data[3].standlkpMdl.lookup_value:"4",
 						std5:data[4]!=null?data[4].standlkpMdl.lookup_value:"5",
 						std6:data[5]!=null?data[5].standlkpMdl.lookup_value:"6"
	 				};
	 				tableData.push(details);
	 				var details1={
	 						label:"Mould Jacket No",
	 						std1:data[0]!=null&&data[0].mtubeTrnsMdl!=null&&data[0].mtubeTrnsMdl.mtube_status!='DISCARD'?data[0].mtubeTrnsMdl.jcktNoLookupMdl.lookup_value:"",
	 						std2:data[1]!=null&&data[1].mtubeTrnsMdl!=null&&data[1].mtubeTrnsMdl.mtube_status!='DISCARD'?data[1].mtubeTrnsMdl.jcktNoLookupMdl.lookup_value:"",
	 						std3:data[2]!=null&&data[2].mtubeTrnsMdl!=null&&data[2].mtubeTrnsMdl.mtube_status!='DISCARD'?data[2].mtubeTrnsMdl.jcktNoLookupMdl.lookup_value:"",
	 						std4:data[3]!=null&&data[3].mtubeTrnsMdl!=null&&data[3].mtubeTrnsMdl.mtube_status!='DISCARD'?data[3].mtubeTrnsMdl.jcktNoLookupMdl.lookup_value:"",
	 						std5:data[4]!=null&&data[4].mtubeTrnsMdl!=null&&data[4].mtubeTrnsMdl.mtube_status!='DISCARD'?data[4].mtubeTrnsMdl.jcktNoLookupMdl.lookup_value:"",
	 						std6:data[5]!=null&&data[5].mtubeTrnsMdl!=null&&data[5].mtubeTrnsMdl.mtube_status!='DISCARD'?data[5].mtubeTrnsMdl.jcktNoLookupMdl.lookup_value:""
	 				};
	 				tableData.push(details1);//Mould Tube
	 				if(data[0]!=null && data[0].mtubeTrnsMdl!=null){
	 					$("#mtube1").val(data[0].mtubeTrnsMdl.ccm_mtube_trns_id);
	 				}
	 				if(data[1]!=null && data[1].mtubeTrnsMdl!=null){
	 					$("#mtube2").val(data[1].mtubeTrnsMdl.ccm_mtube_trns_id);
	 				}
	 				if(data[2]!=null && data[2].mtubeTrnsMdl!=null){
	 					$("#mtube3").val(data[2].mtubeTrnsMdl.ccm_mtube_trns_id);
	 				}
	 				if(data[3]!=null && data[3].mtubeTrnsMdl!=null){
	 					$("#mtube4").val(data[3].mtubeTrnsMdl.ccm_mtube_trns_id);
	 				}
	 				if(data[4]!=null && data[4].mtubeTrnsMdl!=null){
	 					$("#mtube5").val(data[4].mtubeTrnsMdl.ccm_mtube_trns_id);
	 				}
	 				if(data[5]!=null && data[5].mtubeTrnsMdl!=null){
	 					$("#mtube6").val(data[5].mtubeTrnsMdl.ccm_mtube_trns_id);
	 				}
	 				var details1={
	 						label:"Mould Tube",
	 						std1:data[0]!=null&&data[0].mtubeTrnsMdl!=null&&data[0].mtubeTrnsMdl.mtube_status!='DISCARD'?data[0].mtubeTrnsMdl.mtubeMasterMdl.mould_tube_no:"",
	 						std2:data[1]!=null&&data[1].mtubeTrnsMdl!=null&&data[1].mtubeTrnsMdl.mtube_status!='DISCARD'?data[1].mtubeTrnsMdl.mtubeMasterMdl.mould_tube_no:"",
	 						std3:data[2]!=null&&data[2].mtubeTrnsMdl!=null&&data[2].mtubeTrnsMdl.mtube_status!='DISCARD'?data[2].mtubeTrnsMdl.mtubeMasterMdl.mould_tube_no:"",
	 						std4:data[3]!=null&&data[3].mtubeTrnsMdl!=null&&data[3].mtubeTrnsMdl.mtube_status!='DISCARD'?data[3].mtubeTrnsMdl.mtubeMasterMdl.mould_tube_no:"",
	 						std5:data[4]!=null&&data[4].mtubeTrnsMdl!=null&&data[4].mtubeTrnsMdl.mtube_status!='DISCARD'?data[4].mtubeTrnsMdl.mtubeMasterMdl.mould_tube_no:"",
	 						std6:data[5]!=null&&data[5].mtubeTrnsMdl!=null&&data[5].mtubeTrnsMdl.mtube_status!='DISCARD'?data[5].mtubeTrnsMdl.mtubeMasterMdl.mould_tube_no:""
	 				};
	 				tableData.push(details1);
	 				
	 				var details0={
	 						label:"Cleaning Life",
	 						std1:data[0]!=null&&data[0].mtubeTrnsMdl!=null?data[0].mtubeTrnsMdl.cleaning_life:"",
	 		 				std2:data[1]!=null&&data[1].mtubeTrnsMdl!=null?data[1].mtubeTrnsMdl.cleaning_life:"",
	 		 				std3:data[2]!=null&&data[2].mtubeTrnsMdl!=null?data[2].mtubeTrnsMdl.cleaning_life:"",
	 		 				std4:data[3]!=null&&data[3].mtubeTrnsMdl!=null?data[3].mtubeTrnsMdl.cleaning_life:"",
	 		 				std5:data[4]!=null&&data[4].mtubeTrnsMdl!=null?data[4].mtubeTrnsMdl.cleaning_life:"",
	 		 				std6:data[5]!=null&&data[5].mtubeTrnsMdl!=null?data[5].mtubeTrnsMdl.cleaning_life:""
	 				};
	 				tableData.push(details0);
	 				
	 				var details0={
	 						label:"Mould Total Life",
	 						std1:data[0]!=null&&data[0].mtubeTrnsMdl!=null?data[0].mtubeTrnsMdl.total_life:"",
	 		 				std2:data[1]!=null&&data[1].mtubeTrnsMdl!=null?data[1].mtubeTrnsMdl.total_life:"",
	 		 				std3:data[2]!=null&&data[2].mtubeTrnsMdl!=null?data[2].mtubeTrnsMdl.total_life:"",
	 		 				std4:data[3]!=null&&data[3].mtubeTrnsMdl!=null?data[3].mtubeTrnsMdl.total_life:"",
	 		 				std5:data[4]!=null&&data[4].mtubeTrnsMdl!=null?data[4].mtubeTrnsMdl.total_life:"",
	 		 				std6:data[5]!=null&&data[5].mtubeTrnsMdl!=null?data[5].mtubeTrnsMdl.total_life:""
	 				};
	 				tableData.push(details0);
	 				$("#t23_caster_mould_dtls_tbl_id").datagrid("loadData",tableData); 	 				
	 		}
	});	
}

function savetT23MTube(){
	$("#t23_caster_mould_dtls_tbl_id").datagrid("acceptChanges");
	var dataV=$("#t23_caster_mould_dtls_tbl_id").datagrid("getData").rows;
	var row = $('#t23_caster_production_tbl_id').datagrid('getSelected');
	var product = row.productMasterMdl.prodSecMtrlLookupModel.lookup_value;
	var formData=[];
	var mtube_id_arr = new Array();
	var label=[];
	
	if(product.includes('SLB'))
		label=['std1'];
	else
		label=['std1','std2','std3', 'std4', 'std5', 'std6'];
	
	for(var k=0;k<label.length;k++){
		var stand_no;
		var ccm_mtube_trns_id;
	for(var i=0;i<dataV.length;i++){
		var datas =dataV;
		if(datas[i].label==="Strand No"){
			stand_no=datas[i][label[k]];	
		}
		if(datas[i].label==="Mould Jacket No"){
			ccm_mtube_trns_id=$("#mtube"+(k+1)).val();			
		}
	}
	var details={		
			stand_no:stand_no,
			ccm_mtube_trns_id:ccm_mtube_trns_id
	};
	formData.push(details);
	mtube_id_arr.push(ccm_mtube_trns_id);
	}
	
	for(var j=0;j<mtube_id_arr.length;j++){
		for(var k=j+1;k<mtube_id_arr.length;k++){
			if(mtube_id_arr[j] != null && mtube_id_arr[j] != 'null' && mtube_id_arr[j] == mtube_id_arr[k]){
				$.messager.alert('Info','Please check, Strand '+formData[j].stand_no+' Mould jacket is used in other strands','info');
				
				return false;
			}
		}
	}
	
	var casterHeat_pk=row.trns_sl_no;	
	$.ajax({
		headers: { 
		'Accept': 'application/json',
		'Content-Type': 'application/json' 
		},
		type: 'POST',
		data: JSON.stringify(formData),
		dataType: "json",
		url: './casterProduction/updateToCCMMould?trns_sl_no='+casterHeat_pk,
		success: function(data) {
			$.messager.alert('Mould Transaction Details Info',
					data.comment, 'info');
		}
	});
}

function T23MtubeSendCleaning(){
	$("#t23_jkt_no").combobox("setValue","");
	
	$('#t23_mould_selection_id').dialog({
		modal : true,
		cache : true
	});
	$('#t23_mould_selection_id').dialog('open').dialog('center').dialog(
			'setTitle', 'Mould Selection For Cleaning');
	
	var url1="./CommonPool/getComboList?col1=ccm_mtube_trns_id&col2=jcktNoLookupMdl.lookup_value&classname=MtubeTrnsModel&status=1&wherecol=mtube_status in ('READY FOR USE','RUNNING') and record_status=";
		getDropdownList(url1, '#t23_jkt_no');
}

function ProceedT23MtubeSendCleaning(){
	var row = $('#t23_caster_production_tbl_id').datagrid('getSelected');
	
	var formData=[];
	var strandid=1;
	var ccm_mtube_trns_id=$("#t23_jkt_no").combobox("getValue");
	var details={
			stand_no:strandid,
			ccm_mtube_trns_id:ccm_mtube_trns_id,
			clean:"Y"
			
	};
	
	formData.push(details);
	$.ajax({
		headers: { 
		'Accept': 'application/json',
		'Content-Type': 'application/json' 
		},
		type: 'POST',
		data: JSON.stringify(formData),
		dataType: "json",
		url: './casterProduction/updateToCCMMouldClean',
		success: function(data) {
			
			$.messager.alert('Mould Transaction Details Info',
					data.comment, 'info');
			 loadModuldTabData(row);	
			 $('#t23_mould_selection_id').dialog('close');
		}
});
}

function T23MtubeSendScrap(){
	$("#t23_Rjkt_no").combobox("setValue","");
	
	$('#t23_mould_R_selection_id').dialog({
		modal : true,
		cache : true
	});
	$('#t23_mould_R_selection_id').dialog('open').dialog('center').dialog(
			'setTitle', 'Mould Selection For Scrap');
//	var url1 = "./CommonPool/getComboList?col1=lookup_id&col2=lookup_value&classname=LookupMasterModel&status=1&wherecol=lookup_type='CCM_STAND_NO' and lookup_status=";
//	getDropdownList(url1, '#t23_std_no_c');//t23_strand_no
//	
	var url1="./CommonPool/getComboList?col1=ccm_mtube_trns_id&col2=jcktNoLookupMdl.lookup_value&classname=MtubeTrnsModel&status=1&wherecol=mtube_status in ('READY FOR USE','RUNNING') and record_status=";
		getDropdownList(url1, '#t23_Rjkt_no');//t23_strand_no
}

function ProceedT23MtubeSendScrap(){
	var row = $('#t23_caster_production_tbl_id').datagrid('getSelected');

	var formData=[];
	var strandid=1;
	var ccm_mtube_trns_id=$("#t23_Rjkt_no").combobox("getValue");
	var details={
			stand_no:strandid,
			ccm_mtube_trns_id:ccm_mtube_trns_id,
			clean:"Y"		
	};
	
	formData.push(details);
	
	$.ajax({
			headers: { 
			'Accept': 'application/json',
			'Content-Type': 'application/json' 
			},
			type: 'POST',
			data: JSON.stringify(formData),
			dataType: "json",
			url: './casterProduction/updateToCCMMouldScrap',
			success: function(data) {
				
				$.messager.alert('Mould Transaction Details Info',
						data.comment, 'info');
				$('#t23_mould_R_selection_id').dialog('close');
				 loadModuldTabData(row);	
			}
	});
}

function saveT23CasterHeatDetails(){
	$("#t23_caster_production_tbl_id").datagrid("acceptChanges");
	var row = $('#t23_caster_production_tbl_id').datagrid('getSelected');
	if(row){
		if(parseFloat(row.return_qty) <= parseFloat(row.steel_ladle_wgt)){
			$.ajax({
			headers: { 
			'Accept': 'application/json',
			'Content-Type': 'application/json' 
			},
			type: 'POST',
			data: JSON.stringify(row),
			dataType: "json",
			url: './casterProduction/updateCCMHeatRetQty?return_qty='+row.return_qty+"&heat_no="+row.heat_no,
			success: function(data) {
				$.messager.alert('Caster Return Heat Info',
						data.comment, 'info');
				getccmsavedheats();
				//loadModuldTabData(row);	
			}
			});
		}else{
			$.messager.alert('Heat Details', 'Please check!! Retrun Qty is more than Steel Qty', 'info');
		}
	} else {$.messager.alert('Heat Details', 'Select Heat Details', 'info');}
}

function T23ccmCompleteHeat(){
	var isSuccess=false;
	var confirmStr;
	var row = $('#t23_caster_production_tbl_id').datagrid('getSelected');
	if(row){
		checkEventsDtls(row,function(data){
			if(data){
				checkByProductDtls(row,function(dataByProd){
					if(dataByProd){	
						$.ajax({
						headers: { 
						'Accept': 'application/json',
						'Content-Type': 'application/json' 
						},
						type: 'GET',
						dataType: "json",
						url: './casterProduction/getCCMProdBatchDtlsByTrnsId?trns_sl_no='+row.trns_sl_no,
						success: function(data) {
							if(data.length>0){
								confirmStr = '<br>Do you want to complete heat?<br><br><b>Heat No : </b>'+row.heat_no+'<br><b>PSN : </b>'+row.psnHdrMstrMdl.psn_no+
								'<br><b>Grade : </b>'+row.psnGradeMasterMdl.psn_grade+'<br><b>Section : </b>'+row.sectionLookupModel.lookup_value+
								'<br><b>No of Batches : </b>'+data[0].totNoOfBatches+'<br><b>Total weight (Tons) : </b>'+data[0].totBatchWeight;
																
								$.messager.confirm('Confirm', confirmStr, 
									function(r){
									if (r){
										$.ajax({
										headers: { 
											'Accept': 'application/json',
											'Content-Type': 'application/json' 
										},
										type: 'POST',
										data: JSON.stringify(row),
										dataType: "json",
										url: './casterProduction/completeCCMHeatTrnsaction?heat_no='+row.heat_no+"&trns_sl_no="+row.trns_sl_no+"&heat_counter="+row.heat_counter,
										success: function(data) {
											$.messager.alert('Heat Process', data.comment, 'info');
											getccmsavedheats();
										}
										});
									}
								});		
							}else{
								$.messager.alert('Product Batch Details', "Product & Batch Details Missing", 'info');	
							}
						}});
					}else{
						$.messager.alert('ByProduct Details', "ByProduct Details Missing", 'info');
					}
				});			
			}else{
				$.messager.alert('Event Entry', "Event Details Missing", 'info');
			}
		});    
	}else{
		$.messager.alert('Heat Selection',
				"Select Heat Details", 'info');
	}
}
function checkHeatProdDtls(row,callback){
	 var isSuccess=false;
	$.ajax({
		headers: { 
		'Accept': 'application/json',
		'Content-Type': 'application/json' 
		},
		type: 'GET',
		//data: JSON.stringify(formData),heatid,heat_cntr,subunitid
		dataType: "json",
		url: './casterProduction/getCCMProdBatchDtlsByTrnsId?trns_sl_no='+row.trns_sl_no,
		success: function(data) {
			if(data.length>0){
				isSuccess=true;
			}else{
				isSuccess=false;
			}
			callback(isSuccess);
		}});
}
function checkCCMProductDtls(row,callback){
	 var isSuccess=false;
	$.ajax({
		headers: { 
		'Accept': 'application/json',
		'Content-Type': 'application/json' 
		},
		type: 'GET',
		//data: JSON.stringify(formData),heatid,heat_cntr,subunitid
		dataType: "json",
		url: './casterProduction/getCCMProdDtlsByTrnsId?trns_sl_no='+row.trns_sl_no,
		success: function(data) {
			if(data.length>0){
				isSuccess=true;
			}else{
				isSuccess=false;
			}
			callback(isSuccess);
		}});
}
function checkByProductDtls(row,callback){
	 var isSuccess=false;
	$.ajax({
		headers: { 
		'Accept': 'application/json',
		'Content-Type': 'application/json' 
		},
		type: 'GET',
		//data: JSON.stringify(formData),heatid,heat_cntr,subunitid
		dataType: "json",
		url: './casterProduction/getCCMByProductsByTrnsId?trns_sl_no='+row.trns_sl_no+'&mtrl_type=CCM_BY_PRODUCTS',
		success: function(data) {
			if(data.length>0){
				isSuccess=true;
			}else{
				isSuccess=false;
			}
			callback(isSuccess);
		},
		error : function() {
			callback(false);
			$.messager.alert('Processing', 'Error while Processing Ajax...', 'error');
		}
	});
}
function checkEventsDtls(row,callback){
    var isSuccess=true;
	$.ajax({
		headers : {
			'Accept' : 'application/json',
			'Content-Type' : 'application/json'
		},
		type : 'GET',
		// data: JSON.stringify(formData),
		dataType : "json",
		url : "./heatProcessEvent/getHeatProcessEventDtlsByUnit?heat_id="+row.heat_no+"&heat_counter="+row.heat_counter+"&sub_unit_id="+row.sub_unit_id,
		success : function(data) {
			for (var i = 0; i < data.length; i++) {
				if(data[i].event_date_time==null){
					isSuccess=false;
				    break;
				}
			}
			callback(isSuccess);
		},
		error : function() {
			callback(false);
			$.messager.alert('Processing', 'Error while Processing Ajax...',
					'error');
		}
	});
}
//need to regenerate prod table
function reRoladProdDetls(){
	$("#t23_caster_products_dtls_tbl_id").datagrid("acceptChanges");
	var tblData=$("#t23_caster_products_dtls_tbl_id").datagrid("getData").rows;
	var copyData=$("#t23_caster_products_dtls_tbl_id").datagrid("getData").rows;
	var checkedData=tblData[1];
	
	var count=0;
	if(checkedData.std1=="Y"){
		count=count+1;
	}
	if(checkedData.std2=="Y"){
		count=count+1;
	}
	if(checkedData.std3=="Y"){
		count=count+1;
	}
	if(checkedData.std4=="Y"){
		count=count+1;
	}
	if(checkedData.std5=="Y"){
		count=count+1;
	}
	if(checkedData.std6=="Y"){
		count=count+1;
	}

	var row = $('#t23_caster_production_tbl_id').datagrid('getSelected');
	$.ajax({
		headers : {
			'Accept' : 'application/json',
			'Content-Type' : 'application/json'
		},
		type : 'GET',
		dataType : "json",
		url : "./casterProduction/getCCMProdDtlsDisableEnable?trns_sl_no="+row.trns_sl_no+"&no_of_strands="+count,
		success : function(data) {		
					if(checkedData.std1=="Y"){
						copyData[2].std1=data.csSizeMdl.lookup_value;
						copyData[3].std1=data.cs_wgt;
						copyData[4].std1=data.tot_wgt_batches;
						copyData[5].std1=data.cut_length;
						copyData[6].std1=data.no_batches;
					}else{
						copyData[2].std1="";
						copyData[3].std1="";
						copyData[4].std1="";
						copyData[5].std1="";
						copyData[6].std1="";
					}
					if(checkedData.std2=="Y"){
						copyData[2].std2=data.csSizeMdl.lookup_value;
						copyData[3].std2=data.cs_wgt;
						copyData[4].std2=data.tot_wgt_batches;
						copyData[5].std2=data.cut_length;
						copyData[6].std2=data.no_batches;
					}
					else{
						copyData[2].std2="";
						copyData[3].std2="";
						copyData[4].std2="";
						copyData[5].std2="";
						copyData[6].std2="";
					}
					if(checkedData.std3=="Y"){
						copyData[2].std3=data.csSizeMdl.lookup_value;
						copyData[3].std3=data.cs_wgt;
						copyData[4].std3=data.tot_wgt_batches;
						copyData[5].std3=data.cut_length;
						copyData[6].std3=data.no_batches;
					}
					else{
						copyData[2].std3="";
						copyData[3].std3="";
						copyData[4].std3="";
						copyData[5].std3="";							
						copyData[6].std3="";
					}
					if(checkedData.std4=="Y"){
						copyData[2].std4=data.csSizeMdl.lookup_value;
						copyData[3].std4=data.cs_wgt;
						copyData[4].std4=data.tot_wgt_batches;
						copyData[5].std4=data.cut_length;
						copyData[6].std4=data.no_batches;
					}
					else{
						copyData[2].std4="";
						copyData[3].std4="";
						copyData[4].std4="";
						copyData[5].std4="";							
						copyData[6].std4="";
					}
					if(checkedData.std5=="Y"){
						copyData[2].std5=data.csSizeMdl.lookup_value;
						copyData[3].std5=data.cs_wgt;
						copyData[4].std5=data.tot_wgt_batches;
						copyData[5].std5=data.cut_length;
						copyData[6].std5=data.no_batches;
					}
					else{
						copyData[2].std5="";
						copyData[3].std5="";
						copyData[4].std5="";
						copyData[5].std5="";							
						copyData[6].std5="";
					}
					if(checkedData.std6=="Y"){
						copyData[2].std6=data.csSizeMdl.lookup_value;
						copyData[3].std6=data.cs_wgt;
						copyData[4].std6=data.tot_wgt_batches;
						copyData[5].std6=data.cut_length;
						copyData[6].std6=data.no_batches;
					}
					else{
						copyData[2].std6="";
						copyData[3].std6="";
						copyData[4].std6="";
						copyData[5].std6="";							
						copyData[6].std6="";
					}
					$("#t23_caster_products_dtls_tbl_id").datagrid("loadData",copyData);
		}
	});
}
$("#t23_strand_no").combobox({
   
    onSelect:function(record){
    	
    }
 });

function addBatchEntry(){
	
	$('#t23_strand_selection_id').dialog({
		modal : true,
		cache : true
	});
	$('#t23_strand_selection_id').dialog('open').dialog('center').dialog(
			'setTitle', 'Add Batch Entry');
	var url1 = "./CommonPool/getComboList?col1=lookup_id&col2=lookup_value&classname=LookupMasterModel&status=1&wherecol=lookup_type='CCM_STAND_NO' and lookup_status=";
	getDropdownList(url1, '#t23_strand_no');//t23_strand_no
}
function saveAndLoadBatchEntry(){
	var row = $('#t23_caster_production_tbl_id').datagrid('getSelected');
	$.ajax({
		headers : {
			'Accept' : 'application/json',
			'Content-Type' : 'application/json'
		},
		type : 'POST',
		// data: JSON.stringify(formData),
		dataType : "json",
		url : "./casterProduction/getStrandBatchSeq?trns_sl_no="+row.trns_sl_no+"&strand_id="+$("#t23_strand_no").combobox("getValue"),
		success : function(data) {
			if(data.length>0){
				T23ProduceBatches();
				getT23HeatProdDetails(row.trns_sl_no,row);
			}
			else{
				$.messager.alert('Heat Deatils ',"Can`t add batches since it extends steel quanity tolerance",'warning');
				return false;
			}
			
			$('#t23_strand_selection_id').dialog('close');
		}
	});
}

function removeBatchEntry(){
	var row = $('#t23_caster_batch_dtls_tbl_id1').datagrid('getSelected');
	var rowH = $('#t23_caster_production_tbl_id').datagrid('getSelected');
	$.ajax({
		headers : {
			'Accept' : 'application/json',
			'Content-Type' : 'application/json'
		},
		type : 'DELETE',
		data: JSON.stringify(row),
		dataType : "json",
		url : "./casterProduction/removeBatchEntry",
		success : function(data) {
			T23ProduceBatches();
			//alert(rowH.trns_sl_no);
			getT23HeatProdDetails(rowH.trns_sl_no,rowH);
			//$('#t23_strand_selection_id').dialog('close')
			//calculating footer row
			var batch_data=$("#t23_caster_batch_dtls_tbl_id1").datagrid("getRows");
			footer_BatchWght(batch_data);
		}
	});
}
$("#t23_group_seq_no").combobox({
	onSelect:function(record){
		$.ajax({
			headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json'
			},
			type : 'GET',
			//data: JSON.stringify(row),
			dataType : "json",
			url : "./casterProduction/getCCMSeqEvents?groupNo="+$("#t23_group_seq_no").combobox("getValue")+"&sub_unit_id="+$("#t23_casterUnit").combobox("getValue"),
			success : function(data) {
				$("#t23_seq_caster_events_tbl_id").datagrid("loadData",data);
			}
		});
	
		$.ajax({
			headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json'
			},
			type : 'GET',
			//data: JSON.stringify(row),
			dataType : "json",
			url : "./casterProduction/getSeqGroupNosHeats?unit_id="+$("#t23_casterUnit").combobox("getValue")+"&groupSeqNo="+$("#t23_group_seq_no").combobox("getText"),
			success : function(data) {
				$("#t23_heats").datagrid("loadData",data);			
			}
		});
	   }
});

function T23ccmSeqEvent(){
	var row = $('#t23_caster_production_tbl_id').datagrid('getSelected');
	$("#t23_group_seq_no").combobox("setValue","");
	//$("#t23_casterUnit").combobox("setText","")
	//loading seq combobox
	$("#t23_seq_caster_events_tbl_id").datagrid("loadData",[]);
	$("#t23_heats").datagrid("loadData",[]);
	$('#t23_caster_seq_events_form_div_id').dialog({
		modal : true,
		cache : true
	});
	$('#t23_caster_seq_events_form_div_id').dialog('open').dialog('center').dialog(
			'setTitle', 'Seq Event Entry');
	$("#t23_seq_unit").textbox("setText",$("#t23_casterUnit").combobox("getText"));
	
	$.ajax({
		headers : {
			'Accept' : 'application/json',
			'Content-Type' : 'application/json'
		},
		type : 'GET',
		//data: JSON.stringify(row),
		dataType : "json",
		url : "./casterProduction/getSeqGroupNos?unit_id="+$("#t23_casterUnit").combobox("getValue"),
		success : function(data) {
			var comboData=[];
			for (var k=0;k<data.length;k++){
				var details={
						id:data[k].seq_group_dtls_sl_no,
						value:data[k].seq_group_no
				};
				comboData.push(details);
			}
			$("#t23_group_seq_no").combobox("loadData",comboData);
		}
	});
	if(row){

	}	
}

$('#t23_seq_caster_events_tbl_id')
.edatagrid(
		{
			onBeforeEdit : function(index, row) {
				if(row.event_date_time==null){
				row.event_date_time = formatDate(new Date());
				}
				else{
				row.event_date_time = formatDate(row.event_date_time);
				}
				$('#t23_seq_caster_events_tbl_id').datagrid(
						'selectRow', index);
			},
			onEndEdit : function(index, row) {
				$('#t23_seq_caster_events_tbl_id').datagrid(
						'selectRow', index);
				var dt = (row.event_date_time).split(" ");
				var proc_date = new Date(commonGridDtfmt(dt[0],
						dt[1]));
				var time = proc_date.getTime();
				row.group_seq_no=$("#t23_group_seq_no").combobox("getValue");
				row.sub_unit_id=$("#t23_casterUnit").combobox("getValue");
				row.event_date_time = time;
			}
		});
function saveT23SeqEventDtls(){
	$("#t23_seq_caster_events_tbl_id").datagrid("acceptChanges");
	var tdata=$("#t23_seq_caster_events_tbl_id").datagrid("getData").rows;
	$.ajax({
		headers : {
			'Accept' : 'application/json',
			'Content-Type' : 'application/json'
		},
		type : 'POST',
		data: JSON.stringify(tdata),
		dataType : "json",
		url : "./casterProduction/saveOrUpdateCCMSeqEvents",
		success : function(data) {
			if(data.status=="SUCCESS"){
				//T23ccmSeqEvent();
 				$.messager.alert('SUCCESS',data.comment,'info');
 			}
		}
	});
}
function cancelT23SeqEventDtls(){
	
}
$('#t23_ccm_byProducts_tbl_id')
.edatagrid(
		{
			
			onBeforeEdit : function(index, row) {
				//row.event_date_time = formatDate(row.event_date_time);
				//$('#t23_caster_events_tbl_id').datagrid('selectRow', index);
				if(row.consumption_date==null){
					row.consumption_date = formatDate(new Date());
					}
					else{
						row.consumption_date = formatDate(row.consumption_date);
					}
					$('#t23_ccm_byProducts_tbl_id').datagrid('selectRow', index);
			},
			onEndEdit : function(index, row) {
				$('#t23_ccm_byProducts_tbl_id').datagrid(
						'selectRow', index);
				var dt = (row.consumption_date).split(" ");
				var cons_time = new Date(commonGridDtfmt(dt[0],
						dt[1]));
				var time = cons_time.getTime();
				row.consumption_date = time;
				var table_rows=$("#t23_ccm_byProducts_tbl_id").datagrid("getRows");
				byProducts_cal(table_rows);
			}
		});
function T23CCMByProducts(){
	var row = $('#t23_caster_production_tbl_id').datagrid('getSelected');
	if(row){
		$("#t23_ccm_heat_no").textbox("setText",row.heat_no);
		$("#t23_ccm_aim_psn").textbox("setText",row.psnHdrMstrMdl.psn_no);
	$('#t23_ccm_byProducts_form_div_id').dialog({
		modal : true,
		cache : true
	});
	$('#t23_ccm_byProducts_form_div_id').dialog('open').dialog('center').dialog(
				'setTitle', 'By Products');

	//var eof_trns_sno = document.getElementById('t4_trns_si_no').value;
	$.ajax({
				headers : {
					'Accept' : 'application/json',
					'Content-Type' : 'application/json'
				},
				type : 'GET',
				// data: JSON.stringify(formData),
				dataType : "json",
				url : "./casterProduction/getCCMByProducts?mtrlType=CCM_BY_PRODUCTS"
						+ "&ccm_trns_sno=" + row.trns_sl_no+"&heat_qty="+row.steel_ladle_wgt+"&psn_no=NA",
				success : function(data) {
					byProducts_cal(data);
					$("#t23_ccm_byProducts_tbl_id").datagrid("loadData",data);
					}
				});
	}
}

function saveT23CCMByProductsDtls() {
	var row = $('#t23_caster_production_tbl_id').datagrid('getSelected');
	if(row){
		$('#t23_ccm_byProducts_tbl_id').datagrid('acceptChanges');

	var ccm_trns_sno = row.trns_sl_no;
	var rows = $('#t23_ccm_byProducts_tbl_id').datagrid('getRows');
	$.ajax({
		headers : {
			'Accept' : 'application/json',
			'Content-Type' : 'application/json'
		},
		type : 'POST',
		data : JSON.stringify(rows),
		// data: rows,
		dataType : "json",
		url : './casterProduction/saveOrUpdateCCMByProd?trns_ccm_si_no=' + ccm_trns_sno,
		success : function(data) {
			if (data.status == 'SUCCESS') {
				$('#t23_ccm_byProducts_tbl_id').datagrid('reload');
				$.messager.alert('Ladle Additions Details Info', data.comment,
						'info');
				//cancelT17ByProductsDtls();
			} else {
				$.messager.alert('Ladle Additions Details Info', data.comment,
						'info');
			}
		},
		error : function() {
			$.messager.alert('Processing', 'Error while Processing Ajax...',
					'error');
		}
	});
	}

}
function viewT23LiftChem(value, row) {
	  /*return '<a href="#" onclick="viewT17ChemDtls(\''
		+ row.sample_si_no +','+row.sample_no+','+row.sample_temp+','+formatDate(row.sample_date_time)+','

		+row.sample_result+','+row.remarks+','+row.chem_validation+ '\')">View Detail</a>';*/
	  return '<a href="#" onclick="getT23LiftChemDtls(\''
		+ row.heat_no +','+row.heat_counter+','+row.psnHdrMstrMdl.psn_no+','+row.psnGradeMasterMdl.psn_grade+','+row.psn_no+  '\')">View Details</a>';
}
function getT23LiftChemDtls(heat_dtls){
	var h_dtl=heat_dtls.split(",");
	var heat_no = h_dtl[0]; 
	var heat_counter = h_dtl[1];
	var aim_psn = h_dtl[2];
	var steel_grade = h_dtl[3];
	var psn_id = h_dtl[4];
	
	$("#t23_lift_chem_heat_no").textbox("setText","");
	$("#t23_lift_chem_aim_psn").textbox("setText","");
	$("#t23_lift_chem_grade").textbox("setText","");
	var dummydata = new Array();
	$('#t23_liftChemDet_tbl_id').datagrid('loadData', dummydata);
	
	$('#t23_liftChemDet_form_div_id').dialog({
		modal : true,
		cache : true
	});
	$("#t23_lift_chem_heat_no").textbox("setText",heat_no);
	$("#t23_lift_chem_aim_psn").textbox("setText",aim_psn);
	$("#t23_lift_chem_grade").textbox("setText",steel_grade);
	$('#t23_liftChemDet_form_div_id').dialog('open').dialog('center').dialog(
			'setTitle', 'Lift Chemistry');
	$.ajax({
		headers : {
			'Accept' : 'application/json',
			'Content-Type' : 'application/json'
		},
		type : 'GET',
		// data: JSON.stringify(formData),
		dataType : "json",
		url : "./casterProduction/getLiftChem?heat_no="+heat_no+"&heat_counter="+heat_counter+"&aim_psn="+psn_id,
		success : function(data) {
			$("#t23_liftChemDet_tbl_id").datagrid("loadData",data);
		}
	});
}
function addT31DelayEntry(){
	var row= $('#t23_caster_production_tbl_id').datagrid('getSelected');
	if (row) {
		getT31DelayDetails();
		$("#t31_delay_entry_div_id").attr("data-rowchange","0");
		$('#t31_delay_entry_div_id').on('keyup change paste', 'input, select, textarea', function(){
			$("#t31_delay_entry_div_id").attr("data-rowchange","1");
		});
		$('#t31_delay_entry_div_id').dialog({
			modal : true,
			cache : true
		});
		$('#t31_delay_entry_div_id').dialog('open').dialog('center').dialog(
					'setTitle', 'Delay Details Form');
		$('#t31_delay_entry_form_id').form('clear');
		$("#t31_heat_no").textbox("setText",row.heat_no);
		$("#t31_aim_psn").textbox("setText",row.psnHdrMstrMdl.psn_no); 
		$('#t31_delay_entry_tbl_id').edatagrid({
			onBeginEdit :function(index,rowE){}});
	}else{
		$.messager.alert('Caster Production ', 'Please Select Heat ...!', 'info');
	}
}
function getT31DelayDetails(){
	var row= $('#t23_caster_production_tbl_id').datagrid('getSelected');
	var trns_si_no = row.trns_sl_no ;
	var heat_no = row.heat_no;
	var heat_counter = row.heat_counter;
	var sub_unit_id= $('#t23_casterUnit').combobox('getValue');
	
	$.ajax({
		headers : {
			'Accept' : 'application/json',
			'Content-Type' : 'application/json'
		},
		type : 'GET',
		dataType : "json",
		url : "./casterProduction/activityDelayMstrBySubunit?sub_unit_id="+sub_unit_id +"&trns_si_no="+trns_si_no+"&heat_counter="+heat_counter+"&heat_no="+heat_no,
		success : function(data) {
			$('#t31_delay_entry_tbl_id').datagrid('loadData', data);
		},
		error : function() {
			$.messager.alert('Processing', 'Error while Processing Ajax...',
					'error');
		}
	});
}
function t31addDelayDetails(){
	$('#t31_delay_agency_form_id').form('clear');
	var row = $('#t31_delay_entry_tbl_id').datagrid('getSelected');
		
	if(row!=null){
		if(row.transDelayEntryhdr!=null ){
			$('#t31_delay_agency_div_id').dialog({
				modal : true,
				cache : true
			});
			$('#t31_delay_agency_div_id').dialog('open').dialog('center').dialog(
				'setTitle', 'Delay Details Form');
			var heatId = $('#t31_heat_no').textbox('getText');
			var aimpsn = $('#t31_aim_psn').textbox('getText');
			$("#t31_2_heat_no").textbox("setText",heatId);
			$("#t31_2_aim_psn").textbox("setText",aimpsn);
			$("#t31_2_activity").textbox("setText",row.activity_master.activities);
			$("#t31_delay_agency_tbl_id").attr("data-delay","0");
			refreshT31DelayDetailsView(row.transDelayEntryhdr.trns_delay_entry_hdr_id);
			DelayT31Init(row);
			$("#t31activity_value").val(row.activity_master.activities);
			$("#t31delay_details").val(row.activity_master.delay_details);
			$("#trans_delay_dtl_id_value").val(row.transDelayEntryhdr.trns_delay_entry_hdr_id);
			$("#t31delay").val(row.delay);
			
			$('#t31_delay_agency_tbl_id').edatagrid({
				onBeginEdit :function(index,rowE){
					var editors = $('#t31_delay_agency_tbl_id').datagrid('getEditors', index);
					var actVal = $(editors[2].target);//delay entry field
					actVal.textbox({
						onChange : function(newV,oldV) {
							if(oldV==''){
								oldV=0;
							}
							if(parseInt(newV)<parseInt(oldV)){
								var neg=parseInt(newV)-parseInt(oldV);
								$("#t31_delay_agency_tbl_id").attr("data-delay",neg);
							}else{
								var neg=parseInt(newV)-parseInt(oldV);
								$("#t31_delay_agency_tbl_id").attr("data-delay",neg);
							}
						}
					});
				},
				onBeforeEdit : function(index, rowE) {						
				},
				onBeforeSave : function(index, rowE) {
					var data = $('#t31_delay_agency_tbl_id').edatagrid('getData');
					var rows = data.rows;
					var sum = 0;
					for (var i=0; i < rows.length; i++) {
						if(typeof rows[i].delay_dtl_duration !="undefined"){
							sum+=rows[i].delay_dtl_duration;
						}
					}
					sum = sum+parseInt($("#t31_delay_agency_tbl_id").attr("data-delay"));
					if(sum>row.delay){
						$.messager.alert('Information', "Sum : "+ sum +" Activity Delay : "+row.delay
											+ " Sum of delay minutes should not be greater than activity delay minutes ", 'info');
						refreshT31DelayDetailsView(row.transDelayEntryhdr.trns_delay_entry_hdr_id);
						$("#t31_delay_agency_tbl_id").attr("data-delay","0");
						return false;
					}
				},
				onSuccess : function(index, rowE) {
					refreshT31DelayDetailsView(row.transDelayEntryhdr.trns_delay_entry_hdr_id);
				},
				onError : function(index, row) {
					alert("Error! opertaion failed." );
				}
			});
		}else{
			$.messager.alert('Processing', 'Please Save Details to enter delay details!', 'info');
		}
	}else{
		$.messager.alert('Processing', 'No row selected...!', 'info');
	}
}
function refreshT31DelayDetailsView(trans_delay_entry_hdr_id){
	$.ajax({
		headers : {
			'Accept' : 'application/json',
			'Content-Type' : 'application/json'
		},
		type : 'GET',
		dataType : "json",
		url : "./EOFproduction/getDelayDtlsByDelayHdr?trans_delay_entry_hdr_id="+trans_delay_entry_hdr_id,			
		success : function(data) {
			$('#t31_delay_agency_tbl_id').datagrid('loadData', data);
		},
		error : function() {
			$.messager.alert('Processing', 'Error while Processing Ajax...',
					'error');
		}
	});
}
function formatT31ActivityColumnData(colName, value, row, index) {
	try {
		if(row.isNewRecord){
    		return $("#t31activity_value").val();
    	}else{
    		if(eval("row."+colName) === null){
    			return $("#t31activity_value").val();
    		}else{
    			return eval("row."+colName);
    		}
    	}
	}catch(e){
		return "";
   	}
}
function DelayT31Init(row){
	var seq_no = 0;
	var sub_unit= $('#t23_casterUnit').combobox('getValue');
	$('#t31_delay_agency_tbl_id').edatagrid({
		updateUrl : './casterProduction/TransDelayDtlsUpdate?trans_delay_entry_hdr_id='+row.transDelayEntryhdr.trns_delay_entry_hdr_id+'&seq_group_no='+seq_no+'&sub_unit='+sub_unit,
		saveUrl : './casterProduction/TransDelayDtlsSave?trans_delay_entry_hdr_id='+row.transDelayEntryhdr.trns_delay_entry_hdr_id+'&seq_group_no='+seq_no+'&sub_unit='+sub_unit
	});
}
function saveT31DelaytDtls(){
	var rows = $('#t31_delay_entry_tbl_id').datagrid('getRows');
	for ( var i = 0; i < rows.length; i++) {
		$('#t31_delay_entry_tbl_id').datagrid('endEdit', i);
	} 
	if( $("#t31_delay_entry_div_id").attr("data-rowchange")=="1" ){
		var sel_rows = $('#t31_delay_entry_tbl_id').edatagrid('getRows');
		var vrow= $('#t23_caster_production_tbl_id').datagrid('getSelected');
		var trns_si_no = vrow.trns_sl_no ;
		
		$.ajax({
			headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json'
			},
			type : 'POST',
			data : JSON.stringify(sel_rows),
			dataType : "json",
			url : './casterProduction/TransDelaySave?trns_si_no='+trns_si_no,
			success : function(data) {
				if (data.status == 'SUCCESS') {
					$.messager.alert('Info', data.comment, 'info');
					getT31DelayDetails();
					$("#t31_delay_entry_div_id").attr("data-rowchange","0");
				} else {
					$.messager.alert('Info', data.comment, 'info');
				}
			},
			error : function() {
				$.messager.alert('Processing', 'Error while Processing Ajax...', 'error');
			}
		});
	}else{
		$.messager.alert('Processing', 'No changes Present!', 'info');
	}
}
function t31RefreshData(){
	refreshT31DelayDetailsView(parseInt($("#trans_delay_dtl_id_value").val()));
}
function formatT31DlyDtlsColumnData(colName, value, row, index) {
	try {
		if(row.isNewRecord){
			return $("#t31delay_details").val();
    	}else{
    		if(eval("row."+colName) === null){   		
    			return $("#t31delay_details").val();
    		}else{
    			return eval("row."+colName);
    		}
    	}
	}catch(e){
		return "";
   	}
}
function addT31CastSeqDelayEntry(){
	//getT31CastSeqDelayDetails();
	$("#t31_seq_delay_entry_div_id").attr("data-rowchange","0");
	$('#t31_seq_delay_entry_div_id').on('keyup change paste', 'input, select, textarea', function(){
		$("#t31_seq_delay_entry_div_id").attr("data-rowchange","1");
	});
	$('#t31_seq_delay_entry_div_id').dialog({
		modal : true,
		cache : true
	});
	$('#t31_seq_delay_entry_div_id').dialog('open').dialog('center').dialog(
				'setTitle', 'Delay Details Form');
	$('#t31_seq_delay_entry_form_id').form('clear');
	//$("#t31_group_seq_no").textbox("setText",$("#t23_group_seq_no").combobox("getText"));
	$("#t31_group_seq_no").combobox("setValue","");
	
	$.ajax({
		headers : {
			'Accept' : 'application/json',
			'Content-Type' : 'application/json'
		},
		type : 'GET',
		dataType : "json",
		url : "./casterProduction/getCloseSeqGroupNos?unit_id="+$("#t23_casterUnit").combobox("getValue"),
		success : function(data) {
			var comboData=[];
			for (var k=0;k<data.length;k++){
				var details={
						id:data[k].seq_group_dtls_sl_no,
						value:data[k].seq_group_no
				};
				comboData.push(details);
			}
			$("#t31_group_seq_no").combobox("loadData",comboData);
		}
	});
	//$("#t31_group_seq_no").combobox("setValue",$("#t23_group_seq_no").combobox("getValue"));
	$("#t31_unit").textbox("setText",$("#t23_casterUnit").combobox("getText")); 
	$('#t31_seq_delay_entry_tbl_id').edatagrid({
		onBeginEdit :function(index,rowE){}});
}
$("#t31_group_seq_no").combobox({
	onSelect:function(record){
		getT31CastSeqDelayDetails();
	}
});
function getT31CastSeqDelayDetails(){
	var trns_si_no = $("#t31_group_seq_no").combobox("getValue");
	var sub_unit_id= $('#t23_casterUnit').combobox('getValue');
	var heat_no = 0;
	var heat_counter = 0;
	
	$.ajax({
		headers : {
			'Accept' : 'application/json',
			'Content-Type' : 'application/json'
		},
		type : 'GET',
		dataType : "json",
		url : "./casterProduction/activityDelayMstrBySubunit?sub_unit_id="+sub_unit_id +"&trns_si_no="+trns_si_no+"&heat_counter="+heat_counter+"&heat_no="+heat_no,
		success : function(data) {
			$('#t31_seq_delay_entry_tbl_id').datagrid('loadData', data);
		},
		error : function() {
			$.messager.alert('Processing', 'Error while Processing Ajax...',
					'error');
		}
	});
}
function saveT31CastSeqDelaytDtls(){
	var rows = $('#t31_seq_delay_entry_tbl_id').datagrid('getRows');
	for ( var i = 0; i < rows.length; i++) {
		$('#t31_seq_delay_entry_tbl_id').datagrid('endEdit', i);
	} 
	if( $("#t31_seq_delay_entry_div_id").attr("data-rowchange")=="1" ){
		var sel_rows = $('#t31_seq_delay_entry_tbl_id').edatagrid('getRows');
		var trns_si_no = $("#t31_group_seq_no").combobox("getValue");
		
		$.ajax({
			headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json'
			},
			type : 'POST',
			data : JSON.stringify(sel_rows),
			dataType : "json",
			url : './casterProduction/TransDelaySave?trns_si_no='+trns_si_no,
			success : function(data) {
				if (data.status == 'SUCCESS') {
					$.messager.alert('Info', data.comment, 'info');
					getT31DelayDetails();
					$("#t31_seq_delay_entry_div_id").attr("data-rowchange","0");
				} else {
					$.messager.alert('Info', data.comment, 'info');
				}
			},
			error : function() {
				$.messager.alert('Processing', 'Error while Processing Ajax...', 'error');
			}
		});
	}else{
		$.messager.alert('Processing', 'No changes Present!', 'info');
	}
}
function t31addCastSeqDelayDetails(){
	$('#t31_seq_delay_agency_form_id').form('clear');
	var row = $('#t31_seq_delay_entry_tbl_id').datagrid('getSelected');
		
	if(row!=null){
		if(row.transDelayEntryhdr!=null ){
			$('#t31_seq_delay_agency_div_id').dialog({
				modal : true,
				cache : true
			});
			$('#t31_seq_delay_agency_div_id').dialog('open').dialog('center').dialog(
				'setTitle', 'Delay Details Form');
			$("#t31_2_group_seq_no").textbox("setText",$("#t31_group_seq_no").combobox("getValue"));
			$("#t31_seq_2_activity").textbox("setText",row.activity_master.activities);
			$("#t31_seq_delay_agency_tbl_id").attr("data-delay","0");
			refreshT31CastSeqDelayDetailsView(row.transDelayEntryhdr.trns_delay_entry_hdr_id);
			DelayT31CastSeqInit(row);
			$("#t31seq_activity_value").val(row.activity_master.activities);
			$("#t31seq_delay_details").val(row.activity_master.delay_details);
			$("#seq_trans_delay_dtl_id_value").val(row.transDelayEntryhdr.trns_delay_entry_hdr_id);
			$("#t31seq_delay").val(row.delay);
			
			$('#t31_seq_delay_agency_tbl_id').edatagrid({
				onBeginEdit :function(index,rowE){
					var editors = $('#t31_seq_delay_agency_tbl_id').datagrid('getEditors', index);
					var actVal = $(editors[2].target);//delay entry field
					actVal.textbox({
						onChange : function(newV,oldV) {
							if(oldV==''){
								oldV=0;
							}
							if(parseInt(newV)<parseInt(oldV)){
								var neg=parseInt(newV)-parseInt(oldV);
								$("#t31_seq_delay_agency_tbl_id").attr("data-delay",neg);
							}else{
								var neg=parseInt(newV)-parseInt(oldV);
								$("#t31_seq_delay_agency_tbl_id").attr("data-delay",neg);
							}
						}
					});
				},
				onBeforeEdit : function(index, rowE) {						
				},
				onBeforeSave : function(index, rowE) {
					var data = $('#t31_seq_delay_agency_tbl_id').edatagrid('getData');
					var rows = data.rows;
					var sum = 0;
					for (var i=0; i < rows.length; i++) {
						if(typeof rows[i].delay_dtl_duration !="undefined"){
							sum+=rows[i].delay_dtl_duration;
						}
					}
					sum = sum+parseInt($("#t31_seq_delay_agency_tbl_id").attr("data-delay"));
					if(sum>row.delay){
						$.messager.alert('Information', "Sum : "+ sum +" Activity Delay : "+row.delay
											+ " Sum of delay minutes should not be greater than activity delay minutes ", 'info');
						refreshT31CastSeqDelayDetailsView(row.transDelayEntryhdr.trns_delay_entry_hdr_id);
						$("#t31_seq_delay_agency_tbl_id").attr("data-delay","0");
						return false;
					}
				},
				onSuccess : function(index, rowE) {
					refreshT31CastSeqDelayDetailsView(row.transDelayEntryhdr.trns_delay_entry_hdr_id);
				},
				onError : function(index, row) {
					alert("Error! opertaion failed." );
				}
			});
		}else{
			$.messager.alert('Processing', 'Please Save Details to enter delay details!', 'info');
		}
	}else{
		$.messager.alert('Processing', 'No row selected...!', 'info');
	}
}
function refreshT31CastSeqDelayDetailsView(trans_delay_entry_hdr_id){
	$.ajax({
		headers : {
			'Accept' : 'application/json',
			'Content-Type' : 'application/json'
		},
		type : 'GET',
		dataType : "json",
		url : "./EOFproduction/getDelayDtlsByDelayHdr?trans_delay_entry_hdr_id="+trans_delay_entry_hdr_id,			
		success : function(data) {
			$('#t31_seq_delay_agency_tbl_id').datagrid('loadData', data);
		},
		error : function() {
			$.messager.alert('Processing', 'Error while Processing Ajax...',
					'error');
		}
	});
}
function formatT31CastSeqActivityColumnData(colName, value, row, index) {
	try {
		if(row.isNewRecord){
    		return $("#t31seq_activity_value").val();
    	}else{
    		if(eval("row."+colName) === null){
    			return $("#t31seq_activity_value").val();
    		}else{
    			return eval("row."+colName);
    		}
    	}
	}catch(e){
		return "";
   	}
}
function formatT31CastSeqDlyDtlsColumnData(colName, value, row, index) {
	try {
		if(row.isNewRecord){
    		return $("#t31seq_delay_details").val();
    	}else{
    		if(eval("row."+colName) === null){
    			return $("#t31seq_delay_details").val();
    		}else{
    			return eval("row."+colName);
    		}
    	}
	}catch(e){
		return "";
   	}
}
function DelayT31CastSeqInit(row){
	var seq_no = $("#t31_group_seq_no").combobox("getValue");
	var sub_unit= $('#t23_casterUnit').combobox('getValue');
	$('#t31_seq_delay_agency_tbl_id').edatagrid({
		updateUrl : './casterProduction/TransDelayDtlsUpdate?trans_delay_entry_hdr_id='+row.transDelayEntryhdr.trns_delay_entry_hdr_id+'&seq_group_no='+seq_no+'&sub_unit='+sub_unit,
		saveUrl : './casterProduction/TransDelayDtlsSave?trans_delay_entry_hdr_id='+row.transDelayEntryhdr.trns_delay_entry_hdr_id+'&seq_group_no='+seq_no+'&sub_unit='+sub_unit
	});
}
function t31CastSeqRefreshData(){
	refreshT31CastSeqDelayDetailsView(parseInt($("#seq_trans_delay_dtl_id_value").val()));
}

$('#t24_heatwise_process_param_tbl_id').datagrid({
	onBeginEdit:function(index,row){
   		var editors = $('#t24_heatwise_process_param_tbl_id').datagrid('getEditors', index);
        var minval=row.param_value_min;
        var maxval=row.param_value_max;
        if((minval == null || minval =='')&& (maxval == null ||maxval =='')){
        	return false;
        }else{
        	var actVal=$(editors[0].target);
        	actVal.textbox({
        		onChange:function(){
       	        $("#t24_process_param_form_div_id").attr("data-change","1");
       	        var aVal = actVal.textbox('getText');
       	        if((aVal != null && aVal !='') && (minval != null && minval !='')&& (maxval != null && maxval !='')){
       	        	minmax_flag=validateMinMax(aVal,minval,maxval);
       	          	if(! minmax_flag){
       	          		$.messager.alert('Information','Actual value '+aVal+' should be in between Min '+minval+' & Max '+maxval+' Values...!','info');
           	          	actVal.textbox('setValue','');
           	        }
       	        }
        		}
        	});
        }
   	}
});
function savet24_1ProcessParamDtls(){
	var row = $('#t23_caster_production_tbl_id').datagrid('getSelected');
	var heatid=row.heat_no;
	var heat_cntr=row.heat_counter;
	var proc_param_rows = $('#t24_heatwise_process_param_tbl_id').datagrid('getRows');

    for(var i=0; i<proc_param_rows.length; i++){
    	$('#t24_heatwise_process_param_tbl_id').datagrid('endEdit', i);
    }
    
    var grid_arry = '';

    for(var i=0; i<proc_param_rows.length; i++){
    	if((proc_param_rows[i].param_value_actual !=null && proc_param_rows[i].param_value_actual !='')){
    		var proc_date= formatDate(proc_param_rows[i].process_date_time);
    		grid_arry +=  proc_param_rows[i].param_id+'@'+proc_param_rows[i].param_value_actual+'@'+proc_date+'@'+proc_param_rows[i].proc_param_event_id+'SIDS';
    	}
    }
	formData={"grid_arry": grid_arry,"heat_id":heatid,"heat_counter": heat_cntr};

	 $.ajax({
		headers: { 
		'Accept': 'application/json',
		'Content-Type': 'application/json' 
		},
		type: 'POST',
		data: JSON.stringify(formData),
		// data: rows,
		dataType: "json",
		url: './heatProcessEvent/procParamSaveOrUpdate',
		success: function(data) {
		    if(data.status == 'SUCCESS'){
		    	$.messager.alert('Process Parameters Details Info',data.comment,'info');
		    	cancelt19ProcParamDtls();
	    	}else {
	    		$.messager.alert('Process Parameters Details Info',data.comment,'info');
	    	}
		},
		error:function(){      
			$.messager.alert('Processing','Error while Processing Ajax...','error');
		}
		}) ;
	}
function cancelt24_1ProcParamDtls(){
	var row = $('#t23_caster_production_tbl_id').datagrid('getSelected');
	var unit_id=$("#t23_casterUnit").combobox("getValue");
	getHeatwiseProcParam(row.heat_no, row.heat_counter, unit_id, row.psnHdrMstrMdl.psn_no);
}
function cancelt24ProcParamDtls(){
	var row = $('#t23_caster_production_tbl_id').datagrid('getSelected');
	var unit_id=$("#t23_casterUnit").combobox("getValue");
	var product = row.productMasterMdl.prodSecMtrlLookupModel.lookup_value;
	getProcParam(row.heat_no, row.heat_counter, unit_id, product);
}
function footer_BatchWght(data){
		footer_act_wght=0;//reset previous values to 0
		for(i=0;i<data.length;i++){
			if(data[i].act_batch_wgt===null){
				$.messager.alert('Processing','NULL value in actaul weight.Please check','info');
			}
			footer_act_wght = footer_act_wght + parseFloat(data[i].act_batch_wgt);
			}
		$('#t23_caster_batch_dtls_tbl_id1').datagrid('reloadFooter',[
			{act_len: 'Total Weight', act_batch_wgt: Number(footer_act_wght).toFixed(3)}
		]);		
}

function byProducts_cal(data){
	byProduct_total=0;//reset previous values to 0
	for(i=0;i<data.length;i++){
		if(data[i].qty===null){
			data[i].qty=0;
		}
		byProduct_total = byProduct_total + parseFloat(data[i].qty);
	}
	$('#t23_ccm_byProducts_tbl_id').datagrid('reloadFooter',[
		{ qty: Number(byProduct_total).toFixed(3),uom:'TONNES'}
	]);
}

function reRoladProdDetlsForSlabCast(){
	$("#t23_caster_products_dtls_tbl_id").datagrid("acceptChanges");
	var tblData=$("#t23_caster_products_dtls_tbl_id").datagrid("getData").rows;
	var copyData=$("#t23_caster_products_dtls_tbl_id").datagrid("getData").rows;
	var checkedData=tblData[1];
	
	var count=0;
	if(checkedData.std1=="Y"){
		count=count+1;
	}

	var row = $('#t23_caster_production_tbl_id').datagrid('getSelected');
	$.ajax({
		headers : {
			'Accept' : 'application/json',
			'Content-Type' : 'application/json'
		},
		type : 'GET',
		dataType : "json",
		url : "./casterProduction/getCCMProdDtlsDisableEnable?trns_sl_no="+row.trns_sl_no+"&no_of_strands="+count,
		success : function(data) {		
					if(checkedData.std1=="Y"){
						copyData[2].std1=data.csSizeMdl.lookup_value;
						copyData[3].std1=data.cs_wgt;
						copyData[4].std1=data.tot_wgt_batches;
						copyData[5].std1=data.cut_length;
						copyData[6].std1=data.no_batches;
					}else{
						copyData[2].std1="";
						copyData[3].std1="";
						copyData[4].std1="";
						copyData[5].std1="";
						copyData[6].std1="";
					}
					$("#t23_caster_products_dtls_tbl_id").datagrid("loadData",copyData);
		}
	});
}

function getT23HeatProdDetailsForSlabCast(trns_sl_no,hrow){
	var tableData=[];
	var sub_unit = $('#t23_casterUnit').combobox('getValue');
	var heat_plan_hdr = hrow.heat_plan_id;
	var aim_psn = hrow.psn_no;
	var cs_wt = null;
	var p_row = $('#t23_caster_production_tbl_id').datagrid('getSelected');
	
	$("#t23_caster_products_dtls_tbl_id").edatagrid({
		columns:[[
	        {field:'label',title:'',width:110},
	        {field:'std1',title:'',width:110,
                  editor:{
                	  type:'numberbox',options:{precision:1}
                  }	
	        }
	    ]],
	    onBeginEdit:function(index,row){
	    	var ed = $(this).datagrid('getEditor',{index:index,field:'std1'});
	    	
	    	if(row.label=="Status" ){ 
	    		$(ed.target).bind('change',function(e){
	    			reRoladProdDetlsForSlabCast();
	    		});
	    	}
	    },
	    onBeforeEdit: function(index,row){
			var col1 = $(this).datagrid('getColumnOption','std1');
					
			if(row.label=="C.S Size(mm)" ){
				col1.editor = {
						type: 'combobox',
						formatter :function (v,r,i){return formatColumnData('std1',v,r,i);},
						options:{
							url:"./CommonPool/getComboList?col1=prodSecIdLookupModel.lookup_id&col2=prodSecIdLookupModel.lookup_value&classname=ProductSectionMasterModel&status=1&wherecol= ccm_subunit_id="+sub_unit+" and record_status=",
							method:'get',
							valueField:'keyval',
			                textField:'txtvalue'
						}
				};	
			}else if(row.label==="Cut to Length" ){
				col1.editor = {
						type: 'combobox',
						formatter :function (v,r,i){return formatColumnData('std1',v,r,i);},
						options:{
							url:"./CommonPool/getComboList?col1=heat_line_id&col2=plan_cut_length&classname=HeatPlanLinesDetails&status=1&wherecol=heat_plan_id="+heat_plan_hdr+" and aim_psn="+aim_psn+" and record_status=",
							method:'get',
							valueField:'keyval',
			                textField:'txtvalue',
			                onSelect:function(value){
			                	$("#cutlength_std1").val(value.keyval);
			                	
			                	var dataV=$("#t23_caster_products_dtls_tbl_id").datagrid("getData");
			                	if(cs_wt != null){
			                		var temp = (((p_row.steel_ladle_wgt / cs_wt) * 1000) / value.txtvalue);
			                		var no_batches = Math.round(parseFloat(temp) / 3);
			                		for(var i=0;i<dataV.rows.length;i++){
			                			var datas =dataV.rows;
			                			if(datas[i].label==="No of Batches"){
			                				datas[i]['std1'] = no_batches;
			                				break;
			                			}
			                		}
			                	}
			                }
						}
				};
			}else if(row.label=="Status"){
	    		col1.editor={
	    				type:'checkbox',
	    				options:{
	    					on: 'Y',
	    					off: 'N'
	    				}
	    		};
	    	}else if(row.label=="Wgt(Tons)"){
		   		col1.editor = {
		   				type:'numberbox',options:{
		   					onChange: function(value) {
		              			 if(value==0){
		              				$.messager.alert('Info','Wgt(Tons) cannot be 0 Please Disable" : 1','info');
		              				 return false;
		              			 }
		   					}}
		   		} ;
			}else if(row.label=="No of Batches"){
		   		col1.editor = {
		   				type:'numberbox',options:{
		   					onChange: function(value) {
		              			 if(value==0){
		              				$.messager.alert('Info','Please Disable Stand No : 1','info');
		              				 return false;
		              			 }
		   					}}
		   		};
		   	}else if(row.label=="C.S wgt(Kgs/Mtr)"){
		   		col1.editor = {
		   				type:'numberbox',options:{readonly:true}
		   		};
		   	}else{
				col1.editor = {
						type:'numberbox',options:{precision:1}
                };
			}
		}
	});
	
	$('input[type="checkbox"]').click(function(){
		alert("Clicked..........");
	});
	var hrow = $('#t23_caster_production_tbl_id').datagrid('getSelected');
	$.ajax({
 		headers: { 
 		'Accept': 'application/json',
 		'Content-Type': 'application/json' 
 		},
 		type: 'GET',
 		dataType: "json",
 		url: './casterProduction/getCCMProdDtls?trns_sl_no='+trns_sl_no,
 		success: function(data) {
 				var details={
 						label:"Stand No",
 						std1:data[0]!=null?data[0].standlkpMdl.lookup_value:"1"
 				};
 				tableData.push(details);
 				var details1={
 						label:"Status",
 						std1:data[0]!=null?"Y":"N"
 				};
 				tableData.push(details1);				
 				
 				var details6={
						label:"C.S Size(mm)",
						std1:data[0]!=null?data[0].csSizeMdl.lookup_value:hrow.heatPlanMdl.smsCapabilityMstrModel.lookupOutputSection.lookup_value
				};
 				tableData.push(details6);
				
 				if(data[0]!=null){
 					$("#cs_strand1").val(data[0].cs_size);
 				}else{
 					$("#cs_strand1").val(hrow.heatPlanMdl.smsCapabilityMstrModel.section);
 				}
 			
 				var details7={
						label:"C.S wgt(Kgs/Mtr)",
						std1:data[0]!=null?data[0].cs_wgt:""
				};	
 				if(data[0]!=null){
 					cs_wt = data[0].cs_wgt;
 				}
 				tableData.push(details7);
 				
 				var details2={
 						label:"Wgt(Tons)",
 						std1:data[0]!=null?data[0].tot_wgt_batches:""
 				};
 				tableData.push(details2);
 				
 				var details8={
						label:"Cut to Length",
						std1:data[0]!=null?data[0].cut_length:""
				};
 				tableData.push(details8);
 				if(data[0]!=null){
                    $("#cutlength_std1").val(data[0].heat_plan_line_no);
                }
           
                var details9={
						label:"No of Batches",
						std1:data[0]!=null?data[0].no_batches:""
				};
 				tableData.push(details9);
 				
 		        $("#t23_caster_products_dtls_tbl_id").datagrid("loadData",tableData);
 		  },
 		error:function(){      
 			$.messager.alert('Processing','Error while Processing Ajax...','error');
 		  }
 		});
	
}

function getMouldDtlsForSlabCast(mouldData){
	$("#t23_caster_mould_dtls_tbl_id").edatagrid({
		columns:[[
	        {field:'label',title:'',width:200},
	        {field:'std1',title:'',width:100,
                  editor:{
                	  type:'numberbox',options:{precision:1}
                  }	
	        }
	    ]],
	    onBeforeEdit: function(index,row){
	    	var col1 = $(this).datagrid('getColumnOption','std1');
			$('#t23_btn_save').linkbutton('enable');
			
			if(row.label=="Mould Tube"){
				col1.editor=null;
			}else if(row.label=="Mould Jacket No" ){
				col1.editor = {
						type: 'combobox',
						 formatter :function (v,r,i){return formatColumnData('jcktNoLookupMdl.lookup_value',v,r,i);},
						 options:{
								//url:"./CommonPool/getComboList?col1=ccm_mtube_trns_id&col2=jcktNoLookupMdl.lookup_value&classname=MtubeTrnsModel&status=1&wherecol=mtube_status in ('READY FOR USE','RUNNING') and record_status=",
								//method:'get',
								valueField:'keyval',
				                textField:'txtvalue',
				                data: mouldData,
				                onSelect:function(value){
				                        var tdata=$("#t23_caster_mould_dtls_tbl_id").datagrid("getData").rows;
				                        tdata[1].std1=value.keyval;
				                        tdata[1].std1=value.txtvalue;
				                        $("#mtube1").val(value.keyval);
				                        $.ajax({
				                	 		headers: { 
				                	 		'Accept': 'application/json',
				                	 		'Content-Type': 'application/json' 
				                	 		},
				                	 		type: 'GET',
				                	 		dataType: "json",
				                	 		url:'./casterProduction/getMouldTubeLife?ccm_mtube_trns_id='+value.keyval,
				                	 		success: function(data1) {
				                	 			tdata[2].std1=data1.mtubeMasterMdl.mould_tube_no;
				                	 			tdata[3].std1=data1.current_life;
				                	 			tdata[4].std1=data1.total_life;
				                	 			
							                    $("#t23_caster_mould_dtls_tbl_id").datagrid("loadData",tdata);	}
				                     	});				                       
				                }
							}
				};	
			}else if(row.label=="Strand No" ){
	    		col1.editor=null;
	    	}else if(row.label=="Cleaning Life" ){
	    		col1.editor=null;
	    	}else if(row.label=="Mould Total Life" ){
	    		col1.editor=null;
	    	}else if(row.label=="select"){
	    		col1.editor={
	    			type:'checkbox',
	    			options:{
	    				on: 'Y',
	    				off: 'N'
	    			}
	    		};
	    	}else{
				col1.editor={
	              	  type:'numberbox',options:{precision:1}
	                };	
			}
	    }
	    });
		var row = $('#t23_caster_production_tbl_id').datagrid('getSelected');
	    loadModuldTabData(row);	
}

function getCsWeigth(csSec){
	var dataV=$("#t23_caster_products_dtls_tbl_id").datagrid("getData");
	var csWt;
	$.ajax({
				                 		headers: { 
				                 		'Accept': 'application/json',
				                 		'Content-Type': 'application/json' 
				                 		},
				                 		type: 'GET',
				                 		dataType: "json",
				                 		url: './casterProduction/getCsWtg?section='+csSec,
				                 		success: function(data) {
				                 		 var csWt = data.section_wgt;
				                 		for(var i=0;i<dataV.rows.length;i++){
				                 			 var datas =dataV.rows;
				                 			 if(datas[i].label==="C.S wgt(Kgs/Mtr)"){
				                 				 datas[i]['std2'] =csWt;
				                 				break;
				                 			}
				                 		}
				                 		}
				                 		
				                	});
	}
function loadModuldTabDataForSlabCast(trns_sl_no){
	var tableData=[];
	$.ajax({
	 		headers: { 
	 		'Accept': 'application/json',
	 		'Content-Type': 'application/json' 
	 		},
	 		type: 'GET',
	 		//data: JSON.stringify(formData),heatid,heat_cntr,subunitid
	 		dataType: "json",
	 		url: './casterProduction/getCCMProdDtls?trns_sl_no='+trns_sl_no,
	 		success: function(data) {
	 				var details={
 						label:"Strand No",
 						std1:data[0]!=null?data[0].standlkpMdl.lookup_value:"1"
	 				};
	 				tableData.push(details);
					
	 				var details1={
	 						label:"Mould Jacket No",
	 						std1:data[0]!=null&&data[0].mtubeTrnsMdl!=null&&data[0].mtubeTrnsMdl.mtube_status!='DISCARD'?data[0].mtubeTrnsMdl.jcktNoLookupMdl.lookup_value:""
	 				};
	 				tableData.push(details1);// Mould Tube
	 				if(data[0]!=null && data[0].mtubeTrnsMdl!=null){
	 					$("#mtube1").val(data[0].mtubeTrnsMdl.ccm_mtube_trns_id);
	 				}
	 				
	 				var details1={
	 						label:"Mould Tube",
	 						std1:data[0]!=null&&data[0].mtubeTrnsMdl!=null&&data[0].mtubeTrnsMdl.mtube_status!='DISCARD'?data[0].mtubeTrnsMdl.mtubeMasterMdl.mould_tube_no:""
	 				};
	 				tableData.push(details1);
	 				
	 				var details0={
	 						label:"Cleaning Life",
	 						std1:data[0]!=null&&data[0].mtubeTrnsMdl!=null?data[0].mtubeTrnsMdl.cleaning_life:""
	 				};
	 				tableData.push(details0);
	 				
	 				var details0={
	 						label:"Mould Total Life",
	 						std1:data[0]!=null&&data[0].mtubeTrnsMdl!=null?data[0].mtubeTrnsMdl.total_life:"",
	 				};
	 				tableData.push(details0);
	 				$("#t23_caster_mould_dtls_tbl_id").datagrid("loadData",tableData); 	 				
	 		}
	});	
}