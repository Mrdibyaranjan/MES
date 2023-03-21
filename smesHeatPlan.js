/**
 * Developed by Somashekhar Heat Plan Display and subgrid display functinality
 */
//First table retrieval for Edit
$(function() {
	$('#t2_heatplan_tbl_id')
			.datagrid(
					{	view : detailview,
						detailFormatter : function(index, row) {
							return '<div style="padding:2px" id="t2_heatplan_tbl2_id"><table class="t2_heatplan_tbl2_id"></table></div>';
						},
						onExpandRow : function(index, row) {
							var t2_heatplan_tbl2_id = $(this).datagrid(
									'getRowDetail', index).find(
									'table.t2_heatplan_tbl2_id');
							t2_heatplan_tbl2_id
									.datagrid({
										url : './HeatPlan/getHeatPlanLineDetailsByStatus?HEAT_PLAN_ID='
												+ row.heat_plan_id,
										method : 'get',
										fitColumns : true,
										singleSelect : true,
										rownumbers : true,
										loadMsg : 'Loading...',
										height : 'auto',
										columns : [ [
												{	field : 'heat_plan_line_no',
													title : 'Line No'
												},
												{	field : 'aim_psn_id',
													title : 'Aim PSN',
													formatter:(function(v, r, i) {return formatColumnData('psnHdrModel.psn_no',v, r, i);})
												},
												
												{	field : 'grade',
													title : 'Grade'
													
												},
												
												/*{	field : 'tundish_type',
													title : 'Tundish Type',
													formatter:(function(v, r, i) {return formatColumnData('lookupTundishType.lookup_value',v, r, i);})
												},*/
												{	field : 'soHeaderId',
													title : 'Sales Order',
													formatter:(function(v, r, i) {return formatColumnData('soHeaderModel.soHeaderItem',v, r, i);})
												},
												{	field : 'plan_cut_length',
													title : 'Cut Length (Mtr)'
												},
												{	field : 'alter_cut_length_min',
													title : 'Alternative Cut Length min (Mtr)'
												},
												{	field : 'alter_cut_length_max',
													title : 'Alternative Cut Length max (Mtr)'
												},
												{	field : 'plan_heat_qty',
													title : 'Production Quantity (Tons)'
												},
												{	field : 'casting_order_req',
													title : 'Order Type'
												},
												{	field : 'line_remarks',
													title : 'Remarks'
												},
												/*{	field : 'line_status',
													title : 'Status',
													formatter : (function(v, r,
															i) {
														return formatColumnData(
																'statusMstrModel.main_status_desc',
																v, r, i);
													})
												}, */
												{	field : 'heat_plan_line_id',
													title : 'Heat Plan Line ID',
													hidden : true
												}, 
												{	field : 'heat_plan_id',
													title : 'Heat Plan ID',
													hidden : true
												}] ],
										onResize : function() {
											$('#t2_heatplan_tbl_id')
													.datagrid(
															'fixDetailRowHeight',
															index);
										},
										onLoadSuccess : function() {
											setTimeout(
													function() {
														$('#t2_heatplan_tbl_id')
																.datagrid(
																		'fixDetailRowHeight',
																		index);
													}, 0);
										}
									});
							$('#t2_heatplan_tbl_id').datagrid(
									'fixDetailRowHeight', index);
						}
					});
});

//Get Dropdown data to CasterType, SectionType, Target EAF, PSN
function callt2Dropdowns() {
	var url1 = "./CommonPool/getComboList?col1=lookup_id&col2=lookup_value&classname=LookupMasterModel&status=1&wherecol=lookup_type='CASTER_TYPE' and lookup_status=";
	getDropdownList(url1, '#t2_caster_type');
	
	var url4 = "./CommonPool/getComboList?col1=sub_unit_id&col2=sub_unit_name&classname=SubUnitMasterModel&status=1&wherecol=unitDetailsMstrMdl.unit_name = 'EAF' and record_status=";
	getDropdownList(url4, '#t2_target_eof');
	
	/*var url5 = "./CommonPool/getComboList?col1=psn_hdr_sl_no&col2=psn_no&classname=PSNHdrMasterModel&status=1&wherecol=psn_status='APPROVED' and record_status=";
	getDropdownList(url5, '#t2_aim_psn_id');*/
		
	$('#t2_plan_create_date').datetimebox({
		value : (formatDate(new Date()))
	});
}

//On change of Caster Type
$("#t2_caster_type").combobox({
	onSelect : function(record) {
		setSectionType(record.txtvalue);
	}
});

function setSectionType(caster_type){
	var dummydata = new Array();
	$('#t2_section_type').combobox('setValue', '');
	$('#t2_section_planned').combobox('loadData', dummydata);
	$('#t2_section_planned').combobox('setValue', '');
	
	var product = ''; 
	
	/*if (caster_type == 'CCM1'){
		product = "'BLM' and 'BLT'";
	}else if(caster_type == 'CCM2'){
		product = "'SLB'";
	}*/
	
	var url2 = "./CommonPool/getComboList?col1=lookup_id&col2=lookup_value&classname=LookupMasterModel&status=1&wherecol=lookup_type='SECTION_TYPE' " +
	" and lookup_status=";
	//"and lookup_code between "+product+" and lookup_status=";
	
	getDropdownList(url2, '#t2_section_type');
}
//On change of Section Type
$("#t2_section_type").combobox({
	onSelect : function(record) {
		setSection(record.keyval);
	}
});

function setSection(sec_type){
	$('#t2_section_planned').combobox('setValue', '');
	
	var caster = $('#t2_caster_type').combobox('getValue');
	var url3 = "./CommonPool/getComboList?col1=capability_mstr_id&col2=lookupOutputSection.lookup_value&classname=SMSCapabilityMasterModel&status=1&wherecol=section_type="+sec_type+" and caster_type="+caster+" and recordStatus=";
	getDropdownList(url3, '#t2_section_planned');
}

//On change of PSN
/*
$("#t2_aim_psn_id").combobox({
	onSelect : function(record) {
		setTundishType(record.keyval);
	}
});
*/

function setTundishType(psn){
	$('#t2_tundish_type').combobox('setValue', '');
	var url6 = "./CommonPool/getComboList?col1=tundish_type&col2=lookupTundishType.lookup_value&classname=TundishSelectionMasterModel&status=1&wherecol=psn_no="+psn+" and recordStatus=";
	$.ajax({
		headers: { 
			'Accept': 'application/json',
	     	'Content-Type': 'application/json' 
	    },
	    type: 'GET',
	    url: url6,
	    dataType: 'json',
	    success: function(data){
	    if(data.length == 0){
	    	var url7 = "./CommonPool/getComboList?col1=lookup_id&col2=lookup_value&classname=LookupMasterModel&status=1&wherecol=lookup_type='TUNDISH_TYPE' and lookup_code = 'NORMAL' and lookup_status=";
	 		getDropdownList(url7, '#t2_tundish_type');
	    }else{
	    	$("#t2_tundish_type").combobox('loadData', data);
	    }
	    },
	    error: function(){
	    	$.messager.alert('Processing','Error while Processing Ajax...','error');
	    }
	});
}
//Refresh Plan header
function refreshT2HeatPlanHeader() {
	$.ajax({
				headers : {
					'Accept' : 'application/json',
					'Content-Type' : 'application/json'
				},
				type : 'GET',
				// data: JSON.stringify(formData),
				dataType : "json",
				url : './HeatPlan/getHeatPlanHeaderDetailsByStatus?PLAN_STATUS=WIP,RELEASED',
				success : function(data) {
					$('#t2_heatplan_tbl_id').datagrid('loadData', data);
				},
				error : function() {
					$.messager.alert('Processing',
							'Error while Processing Ajax...', 'error');
				}
			});
}

//Refresh Heat Plan Line
function refreshT2HeatPlan() {
	var heatplanid = document.getElementById('t2_heat_plan_id').value;
	$.ajax({
		headers : {
			'Accept' : 'application/json',
			'Content-Type' : 'application/json'
		},
		type : 'GET',
		// data: JSON.stringify(formData),
		dataType : "json",
		url : './HeatPlan/getHeatPlanLineDetailsByStatus?HEAT_PLAN_ID='
				+ heatplanid,
		success : function(data) {
			$('#t2_heat_line_tbl_id').edatagrid('loadData', data);
		},
		error : function() {
			$.messager.alert('Processing', 'Error while Processing Ajax...',
					'error');
		}
	});
}

//Refresh Plan header
function refreshT2PrevPlanDtls() {
	$.ajax({
				headers : {
					'Accept' : 'application/json',
					'Content-Type' : 'application/json'
				},
				type : 'GET',
				// data: JSON.stringify(formData),
				dataType : "json",
				url : './HeatPlan/getAllPrevHeatPlanDetails',
				success : function(data) {
					$('#t2_prev_heatplan_tbl_id').datagrid('loadData', data);
				
				},
				error : function() {
					$.messager.alert('Processing',
							'Error while Processing Ajax...', 'error');
				}
			});
}

//On Click of Create Heat Plan button
function createT2HeatPlan() {

	$('#t2_heatplan_form_div_id').dialog({
		modal : true,
		cache : true,
		onClose : function() {
			refreshT2HeatPlanHeader();
		}
	});
	$('#t2_heatplan_form_div_id').dialog('open').dialog('center').dialog(
			'setTitle', 'Heat Plan Entry Form');
	$('#t2_heatplan_form_id').form('clear');
	$('#t2_heat_line_tbl_id').datagrid('loadData', []);
	//T2HeatPlanLineGrid();
	
	$('#t2_caster_type').combobox('enable');
	$('#t2_section_type').combobox('enable');
	$('#t2_section_planned').combobox('enable');
	$('#t2_target_eof').combobox('enable');
	//$('#t2_aim_psn_id').combobox('enable');
	//$('#t2_tundish_type').combobox('enable');	
	$('#t2_prod_start_date').datebox('enable');
	
	$('#t2_save_heat_plan_btn_id').linkbutton('disable');
	getLookupValues();
	callt2Dropdowns();
}

//edit pending heat plan
function editT5LinePlan(){
   var row = $('#t5_prev_heatplan_tbl_id').datagrid('getSelected');
   if (row) {
	p_hdr_id = document.getElementById('t2_heat_plan_id').value;
	row = $('#t5_prev_heatplan_tbl_id').datagrid('getSelected');
	//console.log(row);
	heatplanid = row.heat_plan_id;
	refreshT5PrevPlanLines(heatplanid);
	}else{
		$.messager.alert('Information', 'Please Select Record...!', 'info');
	}
}

//Refresh Heat Plan Line  for edit
function refreshT5PrevPlanLines(heatplanid) {
	row = $('#t5_prev_heatplan_tbl_id').datagrid('getSelected');
	heat_plan_id=row.heat_plan_id;
	$.ajax({
		headers : {
			'Accept' : 'application/json',
			'Content-Type' : 'application/json'
		},
		type : 'GET',
		dataType : "json",
		url : './HeatPlan/getHeatPlanLineByStatus?HEAT_PLAN_ID='+heat_plan_id,
		success : function(data)
		{
			linecount=data.length;
			$('#t5_heat_line_tbl_id').datagrid('loadData', data);
		},
		error : function() {
			$.messager.alert('Processing',
					'Error while Processing Ajax...', 'error');
		}
	});
}
function t5_closeheatplanLine(){
	var rows=$('#t5_heat_line_tbl_id').datagrid('getChecked');
	
	if(rows=="" || rows==null){
		 $.messager.alert('Alert','Please select checkboxes');
		 return false;
	}
	var grid_arry = '';
	for (var z = 0; z < rows.length;z++) {
		  if ( rows[z].main_status_desc== "RELEASED"){
	      grid_arry += rows[z].heat_plan_dtl_id+"@"+'SIDS';
	     }else{
	     		$.messager.alert('INFO','Cannot close, Heat Plan is in WIP...','info');
	     		refreshT2HeatPlan();
	     		return false;
	     	}
	  }
     formData = {
    			"grid_arry" : grid_arry,
				};
     //console.log(JSON.stringify(formData));
    	  $.messager.confirm('Confirm', 'Do you want to close selected Heat plan?', function(r){
              if(r){
	        $.ajax({
                  headers : {
			    				'Accept' : 'application/json',
			    				'Content-Type' : 'application/json'
			    			},
                            type : 'POST',
			    			data: JSON.stringify(formData),
			    			dataType : "json",
			    			url: './HeatPlan/closeHeatPlanDetails?heat_plan_id='+heat_plan_id,
                            success : function(data) {
			    				if (data.status == 'SUCCESS') {
			    					$.messager.alert('Heat Plan Details Info',
											'Heat Plan Closed Successfully', 'info');
			    					refreshT5PrevPlanLines();
			    					refreshT2PrevPlanDtls();
			    					refreshT2HeatPlanHeader();
			    				} else {
			    					$.messager.alert('Result Info', data.comment, 'info');
			    				};
			    			},
                                error : function() {
			    				$.messager.alert('Processing', 'Error while Processing Ajax...',
			    				'error');
			    			}

            });
		};
		});
		}

function getLookupValues(){
	var lkp_type = 'HEAT_SIZE';
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
				if(data[i].lookup_code == 'AVG'){
					document.getElementById('t2_avg_heat_size').value = data[i].lookup_value;
				}else if(data[i].lookup_code == 'MAX'){
					document.getElementById('t2_max_heat_size').value = data[i].lookup_value;
				}
			}
		},
		error : function() {
			$.messager.alert('Processing', 'Error while Processing Ajax...',
					'error');
		}
	});
}

function validateCutLength(){	
	var len_min, len_max, cut_len;
	var alt_len_min, alt_len_max;
	var vr = $('#t2_heat_line_tbl_id').datagrid('getRows');
	
	len_min = parseFloat(document.getElementById('t2_length_min').value);
	len_max = parseFloat(document.getElementById('t2_length_max').value);
	
	for (var i = 0; i < vr.length; i++) {
		cut_len = parseFloat(vr[i].plan_cut_length);
		alt_len_min = vr[i].alter_cut_length_min;
		alt_len_max = vr[i].alter_cut_length_max;
				
		if(cut_len < len_min || cut_len > len_max){
			$.messager.alert('INFO', 'Please check Cut Length defined in master ('+len_min+'-'+len_max+')', 'info');
			return false;
		}
		if((alt_len_min != '' && alt_len_min != null)
				&& (alt_len_max != '' && alt_len_max != null)
				&& (alt_len_min > alt_len_max)){
			$.messager.alert('INFO', 'Alternate Cut Length Min should be less than Alternate Cut Length Max', 'info');
			return false;
		}
	}

	var heat_plan_hdr_id = document.getElementById('t2_heat_plan_id').value;
	if(heat_plan_hdr_id == null || heat_plan_hdr_id == ''){
		var plan_seq;
		var caster = $('#t2_caster_type').combobox('getValue');
		var prodStartDt = $('#t2_prod_start_date').datebox('getText');
		$.ajax({
		headers : {
		'Accept' : 'application/json',
		'Content-Type' : 'application/json'
		},
		type : 'GET',
		dataType : "json",
		url : './HeatPlan/getHeatPlanSequence?prod_start_date='+prodStartDt+'&caster='+caster,
		success : function(data) {
			plan_seq = data + 1;
			$('#t2_plan_sequence').numberbox('setValue', plan_seq);
		},
		error : function() {
			$.messager.alert('Processing', 'Error while Processing Ajax...',
				'error');
		}
		});
	}
	calcNoOfHeats();
}

//Calculates No of heats
function calcNoOfHeats(){
	var vr = $('#t2_heat_line_tbl_id').datagrid('getRows');
	
	for ( var z = 0; z < vr.length; z++) {
	   $('#t2_heat_line_tbl_id').datagrid('endEdit', z);
	}   
	var total = 0, calc_val = 0, trunc_val;
	var prod_qty = 0;
	var heat_size = parseInt(document.getElementById('t2_avg_heat_size').value);
	var add_qty = parseInt(document.getElementById('t2_max_heat_size').value) - heat_size;
	
	/* Commented on 27/03/2019
	for (var m = 0; m < vr.length; m++) {
		var psn_total = 0, psn_heats = 0;
		for (var n = 0; n < vr.length; n++) {
			if(parseInt(vr[m].aim_psn) == parseInt(vr[n].aim_psn)){
				prod_qty = parseFloat(vr[n].plan_heat_qty);
				psn_total = psn_total + prod_qty;
			}
		}
		psn_heats =  Math.round((psn_total / heat_size));
		total = total + psn_total;
		calc_val = calc_val + psn_heats;
	}
	*/
	
	for (var i = 0; i < vr.length; i++) {
		prod_qty = parseFloat(vr[i].plan_heat_qty);
		total = total + prod_qty;
	}
	document.getElementById('t2_planned_qty').value = total;
	calc_val = total / heat_size;
	
	calc_val = Math.round((total / heat_size));
	/*trunc_val = Math.trunc(calc_val);
	var msg = 'Please change Plan Qty to '+(trunc_val*heat_size)+'tons - '+trunc_val+' heats and '+((trunc_val+1)*heat_size)+'tons - '+(trunc_val+1)+' heats';
	if (calc_val % 1 != 0){
		calc_val = (total + add_qty) / heat_size;
		if (calc_val % 1 != 0){
			calc_val = (total - add_qty) / heat_size;
			if(calc_val % 1 != 0){
				$.messager.alert('INFO', msg, 'info');
			}
		}
	}*/
	
	if(calc_val % 1 == 0){
		$('#t2_no_of_heats_planned').numberbox('setValue', calc_val);		
		$('#t2_save_heat_plan_btn_id').linkbutton('enable');
	}
}

//Add rows
function addT2LinePlan() {
	if (validateT2HPlanForm()) {
	$('#t2_save_heat_plan_btn_id').linkbutton('disable');
	var vr = $('#t2_heat_line_tbl_id').datagrid('getRows');
	var vlen = vr.length;
	var t2_line_no = 10;
	if (!(vlen == 0)) {
		t2_line_no = parseInt(vr[vlen - 1].heat_plan_line_no) + 10;
	}
	$('#t2_heat_line_tbl_id').edatagrid('addRow', {
		row : {
			heat_plan_line_no : t2_line_no
		}
	});
	}
}

//Delete row destroyRow
function deleteT2HeatLinePlan() {
	var row = $('#t2_heat_line_tbl_id').datagrid('getSelected');
	var index = $('#t2_heat_line_tbl_id').datagrid('getRowIndex',row);  // get the row index
	p_line_id = row.heat_line_id;
	p_hdr_id = document.getElementById('t2_heat_plan_id').value;
	var heats_planned = 0;
	var heat_size, planned_qty;
	
	if(p_hdr_id > 0){
		l_status = row.statusMstrModel.main_status_desc;
	if ( l_status == 'RELEASED'){
		$('#t2_heat_line_tbl_id').datagrid('deleteRow',index);
		$('#t2_save_heat_plan_btn_id').linkbutton('disable');
		$('#t2_no_of_heats_planned').numberbox('setValue', '');
		
		calcNoOfHeats();
		heats_planned = $('#t2_no_of_heats_planned').textbox('getText');
		heat_size = parseInt(document.getElementById('t2_avg_heat_size').value);
		planned_qty = document.getElementById('t2_planned_qty').value;
		
		if(heats_planned != null && heats_planned != ''){
			$.ajax({
				headers : {
					'Accept' : 'application/json',
					'Content-Type' : 'application/json'
				},
				type : 'GET',
				// data: JSON.stringify(formData),
				dataType : "json",
				url : './HeatPlan/LineDelete?HEAT_LINE_ID='+p_line_id+'&HEAT_PLAN_ID='+p_hdr_id+'&NO_OF_HEATS_PLANNED='+heats_planned
				+'&heat_size='+heat_size+'&heat_qty='+planned_qty,
				success : function(data) {
					if (data.status == 'SUCCESS') {
						$.messager.alert('Result Info', data.comment, 'info');
						refreshT2HeatPlan();
					} else {
						$.messager.alert('Result Info', data.comment, 'info');
					};
				},
				error : function() {
					$.messager.alert('Processing', 'Error while Processing Ajax...',
					'error');
				}
			});
		}else{
			refreshT2HeatPlan();
		}
	}else{
		$.messager.alert('INFO','Cannot delete, Please check Line Status ...','info');
		refreshT2HeatPlan();
	}
	}else{
		$('#t2_heat_line_tbl_id').datagrid('deleteRow',index);
		$('#t2_save_heat_plan_btn_id').linkbutton('disable');
		$('#t2_no_of_heats_planned').numberbox('setValue', '');
		calcNoOfHeats();
		/*var t2_line_no = 10;
		for (var j=0; j<vr.length; j++){
			vr[j].heat_plan_line_no = '';
			vr[j].heat_plan_line_no = parseInt(t2_line_no);
			t2_line_no = t2_line_no + 10;
		}
		*/
	}
}

//On Click of Edit Heat Plan button
function editT2HeatPlan() {
	var row = $('#t2_heatplan_tbl_id').datagrid('getSelected');

	if (row) {
		$('#t2_heatplan_form_div_id').dialog({
			modal : true,
			cache : true,
			onClose : function() {
				refreshT2HeatPlanHeader();
			}
		});
		$('#t2_heatplan_form_div_id').dialog('open').dialog('center').dialog(
				'setTitle', 'Heat Plan Edit Form');
		$('#t2_heatplan_form_id').form('clear');
		
		$('#t2_caster_type').combobox('disable');
		$('#t2_section_type').combobox('disable');
		$('#t2_section_planned').combobox('disable');
		$('#t2_target_eof').combobox('disable');
		//$('#t2_aim_psn_id').combobox('disable');
		//$('#t2_tundish_type').combobox('disable');	
		$('#t2_prod_start_date').datebox('disable');
		
		$('#t2_save_heat_plan_btn_id').linkbutton('disable');
		
		getLookupValues();
		callt2Dropdowns();

		$('#t2_caster_type').combobox('setValue', row.caster_type);
		$('#t2_target_eof').combobox('setValue', row.target_eof);
		//$('#t2_aim_psn_id').combobox('setValue', row.aim_psn_id);
		$('#t2_no_of_heats_planned').numberbox('setValue', row.no_of_heats_planned);
		$('#t2_plan_sequence').numberbox('setValue', row.plan_sequence);
		$('#t2_plan_create_date').datetimebox('setText', formatDate(row.plan_create_date));
		$('#t2_prod_start_date').datebox('setText', formatDate(row.prod_start_date));
		$('#t2_plan_remarks').textbox('setText', row.plan_remarks);
		document.getElementById('t2_heat_plan_id').value = row.heat_plan_id;
		document.getElementById('t2_plan_hdr_version').value = row.version;
		
		setSectionType(row.lookupCasterType.lookup_value);
		setSection(row.section_type);
		//setTundishType(row.aim_psn_id);
		$('#t2_section_type').combobox('setValue', row.section_type);
		$('#t2_section_planned').combobox('setValue', row.section_planned);
		//$('#t2_tundish_type').combobox('setValue', row.tundish_type);
		refreshT2HeatPlan();
	} else {
		$.messager.alert('Information', 'Please Select Record...!', 'info');
	}
}

function validateT2HPlanForm() {
	return $('#t2_heatplan_form_id').form('validate');
}

//Save Heat Plan Header & Lines
function saveHeatPlan() {
	if (validateT2HPlanForm()) {
		var heatplanid = document.getElementById('t2_heat_plan_id').value;
		var caster_type = $('#t2_caster_type').combobox('getValue');
		var sec_typ_id = $('#t2_section_type').combobox('getValue');
		var section = $('#t2_section_planned').combobox('getValue');
		var tgt_eof = $('#t2_target_eof').combobox('getValue');
		//var psn = $('#t2_aim_psn_id').combobox('getValue');
		//var tundish_type = $('#t2_tundish_type').combobox('getValue');
		var no_of_heats_planned = $('#t2_no_of_heats_planned').textbox(
				'getText');
		var planned_qty = document.getElementById('t2_planned_qty').value;
		var sequence = $('#t2_plan_sequence').textbox('getText');
		var plan_create_date = $('#t2_plan_create_date').datetimebox('getText');
		var prod_start_date = $('#t2_prod_start_date').datebox('getText');
		var plan_remarks = $('#t2_plan_remarks').textbox('getText');
		var heat_size = parseInt(document.getElementById('t2_avg_heat_size').value);
		var tbl_rows = $('#t2_heat_line_tbl_id').datagrid('getRows');	
		//console.log(tbl_rows);
		var formData, c_url;
		if (heatplanid != null && heatplanid > 0) {
			c_url = './HeatPlan/UpdateHeatPlanDetails?no_of_heats_planned='+no_of_heats_planned+'&planned_qty='+planned_qty+'&heat_size='+heat_size;
			formData = {
					"heat_plan_id" : heatplanid,
					"heatPlanLine" : tbl_rows
			};
		}else{
			c_url = './HeatPlan/SaveHeatPlanDetails?prod_start_date='+ prod_start_date+'&heat_size='+heat_size;
			formData = {
					"caster_type" : caster_type,
					"section_type" : sec_typ_id,
					"section_planned" : section,
					"target_eof" : tgt_eof,
					//"aim_psn_id" : psn,
					//"tundish_type" : tundish_type,
					"no_of_heats_planned" : no_of_heats_planned,
					"plan_sequence" : sequence,
					"plan_remarks" : plan_remarks,
					"planned_qty" : planned_qty,
					"heatPlanLine" : tbl_rows
			};
		}
		
		$.ajax({
				headers : {
					'Accept' : 'application/json',
					'Content-Type' : 'application/json'
				},
				type : 'POST',
				data : JSON.stringify(formData),
				dataType : "json",
				url : c_url,
				success : function(data) {
				if (data.status == 'SUCCESS') {
					$.messager.alert('Heat Plan Details Info',
							'Heat Plan Saved Successfully', 'info');
					refreshT2PrevPlanDtls();
					//clearAll();
					clearAllOnlyLineDetails();
					//document.getElementById('t2_heat_plan_id').value = data.comment;
					//refreshT2HeatPlan();
					refreshT2PrevPlanDtls();
				} else {
					$.messager.alert('Heat Plan HDR Details Info',
									data.comment, 'info');
				}
				},
				error : function() {
					$.messager.alert('Processing',
								'Error while Processing Ajax...', 'error');
				}
		});
	}
}

//On Click of Update Heat Plan
function updateT2HeatPlanHeader() {
	if (validateT2HPlanForm()) {
		var no_of_heats_planned = $('#t2_no_of_heats_planned').textbox(
				'getText');
		var vr = $('#t2_heat_line_tbl_id').datagrid('getRows');
		var vlen = vr.length;
		if (no_of_heats_planned >= vlen) {
			var plan_create_date = $('#t2_plan_create_date').datetimebox(
					'getText');
			var plan_remarks = $('#t2_plan_remarks').textbox('getText');
			var heat_plan_id = document.getElementById('t2_heat_plan_id').value;
			var caster_type = $('#t2_caster_type').combobox('getValue');
			var version = document.getElementById('t2_plan_hdr_version').value;
			var sec_typ_id = $('#t2_sec_type').combobox('getValue');
			var dia = $('#t2_dia').textbox('getValue');
			var thickness = $('#t2_thickness').textbox('getValue');
			var width = $('#t2_width').textbox('getValue');

			var formData = {
				"no_of_heats_planned" : no_of_heats_planned,
				"plan_remarks" : plan_remarks,
				"heat_plan_id" : heat_plan_id,
				"caster_type" : caster_type,
				"section_type" : sec_typ_id,
				"plan_dia" : dia,
				"plan_thickness" : thickness,
				"plan_width" : width,
				"version" : version
			};

			$.ajax({
				headers : {
					'Accept' : 'application/json',
					'Content-Type' : 'application/json'
				},
				type : 'POST',
				data : JSON.stringify(formData),
				dataType : "json",
				url : './HeatPlan/HDRUpdate?plan_create_date='
						+ plan_create_date,
				success : function(data) {
					if (data.status == 'SUCCESS') {
						$.messager.alert('Heat Plan HDR Details Info',
								'HEAT HEADER INFO ' + data.comment, 'info');
						refreshT2HeatPlan();
					} else {
						$.messager.alert('Heat Plan HDR Details Info',
								data.comment, 'info');
					}
				},
				error : function() {
					$.messager.alert('Processing',
							'Error while Processing Ajax...', 'error');
				}
			});

		} else {
			$.messager
					.alert(
							'INFO',
							'Please Update No Of Planned Heats is >= No of Line Elements ...',
							'info');

		}
	}
}

function exportExcel(){
	//window.open("./HeatPlan/exportDailyHeatPlan");
	var caster = $('#t2_caster').combobox('getValue');
	if(caster != null && caster != "")
		window.open("./HeatPlan/displayDailyHeatPlan?caster_type="+caster);
	else
		$.messager.alert('INFO', 'Please select Caster then Download', 'info');
}

function T2DayPlanReport() {
	var url1 = "./CommonPool/getComboList?col1=lookup_id&col2=lookup_value&classname=LookupMasterModel&status=1&wherecol=lookup_type='CASTER_TYPE' and lookup_status=";
	getDropdownList(url1, '#t2_caster');
	
	$('#t2_heat_plan_day_rpt_div_id').dialog({
		modal : true,
		cache : true
	});
	$('#t2_heat_plan_day_rpt_div_id').dialog('open').dialog('center').dialog(
			'setTitle', 'Heat Plan Report');
	$('#t2_heat_plan_day_rpt_form_id').form('clear');
}

function T2HeatPlanDetailReport() {
	var url1 = "./CommonPool/getComboList?col1=lookup_id&col2=lookup_value&classname=LookupMasterModel&status=1&wherecol=lookup_type='CASTER_TYPE' and lookup_status=";
	getDropdownList(url1, '#t2_rpt_caster');
	
	$('#t2_heat_plan_det_rpt_div_id').dialog({
		modal : true,
		cache : true
	});
	$('#t2_heat_plan_det_rpt_div_id').dialog('open').dialog('center').dialog(
			'setTitle', 'Heat Plan Report');
	$('#t2_heat_plan_det_rpt_form_id').form('clear');
}

function expHeatPlanDetRpt(){
	var caster = $('#t2_rpt_caster').combobox('getValue');
	var rpt_date = $('#t2_rpt_date').datebox('getText');
	
	if(caster != null && caster != ""){
		if(rpt_date != null && rpt_date != "")
			window.open("./HeatPlan/displayHeatPlanDetailRpt?caster_type="+caster+"&report_date="+rpt_date);
		else
			$.messager.alert('INFO', 'Please select Report date and Download', 'info');
	}else{
		$.messager.alert('INFO', 'Please select Caster and Download', 'info');
	}
}

function T2DisplayDayPlanRpt(){
	var caster = $('#t2_caster').combobox('getValue');
	
	$('#t2_heat_plan_day_rpt_id').panel('open');
	$('#t2_heat_plan_day_rpt_id').empty();
	$('#t2_export').linkbutton('enable');
	  
	$.ajax({
   		headers: { 
   		'Accept': 'application/json',
   		'Content-Type': 'application/json' 
   		},
   		type: 'GET',
   		dataType: "json",
   		url: './HeatPlan/displayDailyHeatPlan?caster_type='+caster,
   		success: function(data) {
   		 clearAllDivs(psn_rep_scrn_type);
   		},
   		error:function(){      
			$.messager.alert('Processing','Error while Processing Ajax...','error');
		}
	});
}

function T2DayPlanRptExportWord(){
	var caster = $('#t2_caster').combobox('getValue');
	if (caster != null && caster != '') {
		T10PSNDocsInit();
		//$('#psn_desc_docs').textbox('setText', psn_no);
		//$('#psn_no_docs').textbox('setText', psn_hdr_sl_no);
		$('#t10_rep_psn_desc').textbox('setText', psn_no);		
		document.getElementById('t10_psn_no').value=psn_hdr_sl_no;
 		refreshT10PSNDocs(); 		
	} else {
		$.messager.alert('PSN DOC Details Info',
				'Please Select Heat & Check PSN ...!', 'info');
	}

}

function clearAll(){
	$('#t2_caster_type').combobox('setValue', '');
	$('#t2_section_type').combobox('setValue', '');
	$('#t2_section_planned').combobox('setValue', '');
	$('#t2_target_eof').combobox('setValue', '');
	//$('#t2_aim_psn_id').combobox('setValue', '');
	//$('#t2_tundish_type').combobox('setValue', '');
	$('#t2_no_of_heats_planned').numberbox('setValue', '');
	$('#t2_plan_sequence').numberbox('setValue', '');
	/*$('#t2_plan_create_date').datetimebox({
		value : (formatDate(new Date()))
	});*/
	//$('#t2_prod_start_date').datetimebox('setValue','');
	$('#t2_prod_start_date').datebox({value : ''});
	$('#t2_plan_remarks').textbox('setText', '');
	$('#t2_save_heat_plan_btn_id').linkbutton('disable');
	var dummydata = new Array();
	$('#t2_heat_line_tbl_id').datagrid('loadData', dummydata);

}
function clearAllOnlyLineDetails(){
//	$('#t2_caster_type').combobox('setValue', '');
//	$('#t2_section_type').combobox('setValue', '');
//	$('#t2_section_planned').combobox('setValue', '');
//	$('#t2_target_eof').combobox('setValue', '');
	//$('#t2_aim_psn_id').combobox('setValue', '');
//	$('#t2_tundish_type').combobox('setValue', '');
	$('#t2_no_of_heats_planned').numberbox('setValue', '');
	$('#t2_plan_sequence').numberbox('setValue', '');
	/*$('#t2_plan_create_date').datetimebox({
		value : (formatDate(new Date()))
	});*/
	//$('#t2_prod_start_date').datetimebox('setValue','');
	$('#t2_prod_start_date').datebox({value : ''});
	$('#t2_plan_remarks').textbox('setText', '');
	$('#t2_save_heat_plan_btn_id').linkbutton('disable');
	var dummydata = new Array();
	$('#t2_heat_line_tbl_id').datagrid('loadData', dummydata);

}

//On change of Section
$("#t2_section_planned").combobox({
	onSelect : function(record) {
		$.ajax({
			headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json'
			},
			type : 'GET',
			dataType : "json",
			url : './HeatPlan/getSmsCapabilityDtls?master_id='+record.keyval,
			success : function(data) {
				document.getElementById('t2_length_min').value = data.length_min;
				document.getElementById('t2_length_max').value = data.length_max;
			},
			error : function() {
				$.messager.alert('Processing', 'Error while Processing Ajax...',
				'error');
			}
		});
	}
});

$(function() {
	$('#t2_heat_line_tbl_id').edatagrid({
		onBeginEdit : function(index, row) {	
			var editors = $('#t2_heat_line_tbl_id').datagrid('getEditors', index);
			var psncmb = $(editors[3].target);
			//var tundcmb = $(editors[4].target);
			var socmb = $(editors[4].target);
			var cutLength = $(editors[5].target);
			var altLenMin = $(editors[6].target);
			var altLenMax = $(editors[7].target);
			var planHeatQty = $(editors[8].target);
			var castingOrder=$(editors[9].target);
			psncmb.combobox('options').onSelect = function(rec){
				row.psnHdrModel={
					"psn_hdr_sl_no" : parseInt(rec.keyval),
					"psn_no" : rec.txtvalue
				};
				/*
				var url2="./CommonPool/getComboList?col1=tundish_type&col2=lookupTundishType.lookup_value&classname=TundishSelectionMasterModel&status=1&wherecol=psn_no="+$(editors[3].target).combobox('getValue')+" and recordStatus=";
             	$.ajax({
        	 		headers: { 
        	 		'Accept': 'application/json',
        	 		'Content-Type': 'application/json' 
        	 		},
        	 		type: 'GET',
        	 		dataType: "json",
        	 		url: url2,
        	 		success: function(data) {	
        	 			if(data.length == 0){
							var url7 = "./CommonPool/getComboList?col1=lookup_id&col2=lookup_value&classname=LookupMasterModel&status=1&wherecol=lookup_type='TUNDISH_TYPE' and lookup_code = 'NORMAL' and lookup_status=";
								
							tundcmb.combobox({
								url:url7,
							    valueField:'keyval',
							    textField:'txtvalue',
							    method:'get'
							});
						}else{
							//tundcmb.combobox('loadData', data);
							tundcmb.combobox({
								data:data,
							    valueField:'keyval',
							    textField:'txtvalue',
							});
						}
					}
				});*/
				
				
				var url2="./HeatPlan/getSalesOrderList?psn_hdr_id="+$(editors[3].target).combobox('getValue')+"&section_type="+ $('#t2_section_type').combobox('getText');
             	$.ajax({
        	 		headers: { 
        	 		'Accept': 'application/json',
        	 		'Content-Type': 'application/json' 
        	 		},
        	 		type: 'GET',
        	 		dataType: "json",
        	 		url: url2,
        	 		success: function(data) {	
        	 			//socmb.combobox('loadData', data);
        	 			socmb.combobox({
        	 				data:data,
        	 				valueField:'soId',
        	 				textField:'soHeaderItem',
						});
					}
				});
			};
			socmb.combobox({
				onSelect: function(rec){
					row.soHeaderModel={
						"soId" : parseInt(rec.soId),
						"soHeaderItem" : rec.soHeaderItem
					};
				}
			});
			/*tundcmb.combobox({
	  			onSelect: function(rec){
	  				row.lookupTundishType={
	  					"lookup_id" : parseInt(rec.keyval),
	  					"lookup_value" : rec.txtvalue
	  				};
	  			}
			});*/
			
			castingOrder.combobox({
	  			onSelect: function(rec){
	  				row.lookupCastingOrder={
	  					"lookup_id" : parseInt(rec.keyval),
	  					"lookup_value" : rec.txtvalue
	  				};
	  				//row.casting_order_req=rec.txtvalue;
	  			}
			});
			
			/*for (var i = 0; i < (data.total - 1); i++) {
				data.rows[i].aim_psn = data.rows[i].psn_id;
				data.rows[i].tundish_type = data.rows[i].tundish_id;
				
			}*/
			cutLength.textbox({onChange : function() {
				var len_min = parseFloat(document.getElementById('t2_length_min').value);
				var len_max = parseFloat(document.getElementById('t2_length_max').value);
				var cut_len = parseFloat(cutLength.textbox('getText'));
				
				if(cut_len < len_min || cut_len > len_max){
					$('#t2_ok_heat_plan_btn_id').linkbutton('disable');
					$.messager.alert('INFO', 'Please check, Cut Length defined in master is ('+len_min+'-'+len_max+')', 'info');
				}else{
					$('#t2_ok_heat_plan_btn_id').linkbutton('enable');
				}
			}});
			altLenMin.textbox({onChange : function() {
				var alt_len_min = parseFloat(altLenMin.textbox('getText'));
				var altMaxLen = parseFloat(altLenMax.textbox('getText'));
				if(( (altMaxLen != '' && altMaxLen != null) && (alt_len_min > altMaxLen))){
					$('#t2_ok_heat_plan_btn_id').linkbutton('disable');
					$.messager.alert('INFO', 'Alternate Cut Length Min should be less than Alternate Cut Length Max', 'info');
				}else{
					$('#t2_ok_heat_plan_btn_id').linkbutton('enable');
				}
			}});
			altLenMax.textbox({onChange : function() {
				var alt_len_max = parseFloat(altLenMax.textbox('getText'));
				var altMinLen = parseFloat(altLenMin.textbox('getText'));
				if(( (altMinLen != '' && altMinLen != null) && (alt_len_max < altMinLen))){
					$('#t2_ok_heat_plan_btn_id').linkbutton('disable');
					$.messager.alert('INFO', 'Alternate Cut Length Max should be more than Alternate Cut Length Min', 'info');
				}else{
					$('#t2_ok_heat_plan_btn_id').linkbutton('enable');
				}
			}});
			planHeatQty.textbox({onChange : function() {
				$('#t2_no_of_heats_planned').numberbox('setValue', '');
				$('#t2_save_heat_plan_btn_id').linkbutton('disable');
			}});
		},
		/*rowStyler:function(index, row) {
			if (document.getElementById('t2_length_min').value == 'Y') {
				$('#t2_ok_heat_plan_btn_id').linkbutton('disable');
				return 'background-color:pink;color:blue;font-weight:bold;';
			}else{
				$('#t2_ok_heat_plan_btn_id').linkbutton('enable');
			}
		},*/
	});
});

function closeT2HeatPlan(){
	var row = $('#t2_heatplan_tbl_id').datagrid('getSelected');
	if (row) {
		$.messager.confirm('Confirm', 'Do you want to close selected Heat plan?', function(r){
			if (r){
				var l_status = row.mainHeatStatusMasterModel.main_status_desc;
				if (l_status == 'RELEASED') {
					c_url = './HeatPlan/CancelHeatPlanDetails?plan_hdr_id='+row.heat_plan_id;
					$.ajax({
						headers : {
							'Accept' : 'application/json',
							'Content-Type' : 'application/json'
						},
						type : 'POST',
						dataType : "json",
						url : c_url,
						success : function(data) {
							if (data.status == 'SUCCESS') {
								$.messager.alert('Heat Plan Details Info',
										'Heat Plan Closed Successfully', 'info');
								refreshT2HeatPlanHeader();
							} else {
								$.messager.alert('Heat Plan HDR Details Info',
								data.comment, 'info');
							}
						},
						error : function() {
							$.messager.alert('Processing',
							'Error while Processing Ajax...', 'error');
						}
					});
				}else{
					$.messager.alert('INFO','Cannot close, Heat Plan is in WIP...','info');
				}
			}
		});
	}else{
		$.messager.alert('Information', 'Please Select Record...!', 'info');	
	}
}

function T2HeatPlanLineGrid(){
	 $('#t2_heat_line_tbl_id').datagrid({
		  columns:[[
		        {field:'heat_plan_id',title:'Heat Plan Id',width:0,hidden:true},
				{field:'heat_line_id',title:'Heat Plan Line Id',width:0,hidden:true},
				{field:'heat_plan_line_no',title:'Indent ID',width:40,editor:{
					type:'numberbox',
					options:{
						required:true
					}
				}},
		        {field:'aim_psn',title:'PSN',width:70,
		        	formatter:function(v,r,i){return formatColumnData('psnHdrModel.psn_no',v,r,i);},
		        	editor:{
					type:'combobox', 
					options:{
						method:'GET',
						url:"./CommonPool/getComboList?col1=psn_hdr_sl_no&col2=psn_no&classname=PSNHdrMasterModel&status=1&wherecol=psn_status='APPROVED' and record_status=",
						valueField:'keyval',
						textField:'txtvalue',
						required:true,
						editable:true,
						/*
						onSelect:function(value){
							setTimeout(function(){
								var opts = $('#t2_heat_line_tbl_id').edatagrid('options');
								//var tr = $(this).closest('tr.datagrid-row');
								//var ind = parseInt(tr.attr('datagrid-row-index'));
		                        var ed = $('#t2_heat_line_tbl_id').edatagrid('getEditor',{
									index:opts.editIndex,
		                            field:'tundish_type'
								});
		                     	$.ajax({
		                	 		headers: { 
		                	 		'Accept': 'application/json',
		                	 		'Content-Type': 'application/json' 
		                	 		},
		                	 		type: 'GET',
		                	 		dataType: "json",
		                	 		url:'./CommonPool/getComboList?col1=tundish_type&col2=lookupTundishType.lookup_value&classname=TundishSelectionMasterModel&status=1&wherecol=psn_no='+value.keyval+' and recordStatus=',
		                	 		success: function(data) {	
		                	 			if(data.length == 0){
	    									var url7 = "./CommonPool/getComboList?col1=lookup_id&col2=lookup_value&classname=LookupMasterModel&status=1&wherecol=lookup_type='TUNDISH_TYPE' and lookup_code = 'NORMAL' and lookup_status=";
	 										
	 										$.ajax({
	 				                	 		headers: { 
	 				                	 		'Accept': 'application/json',
	 				                	 		'Content-Type': 'application/json' 
	 				                	 		},
	 				                	 		type: 'GET',
	 				                	 		dataType: "json",
	 				                	 		url:url7,
	 				                	 		success: function(data) {		
	 				                	 			var tundish_def = parseInt(data);
	 				                	 			$(ed.target).combobox('setValue', tundish_def);
	 											}
	 										});
	    								}else{
	    									//var tundish = parseInt(data);
											$(ed.target).combobox('loadData', data);
	    								}
									}
								});
							},0);
						}*/
					}
		        	}
		        },
				/*{field:'tundish_type', title:'Tundish Type',width:70,
		        	formatter:(function(v,r,i){return formatColumnStatus('lookupTundishType.lookup_value',v,r,i);}),
					editor : {
						type : 'combobox',
						options : {
							required : true,
							editable : true,
							valueField : 'keyval',
							textField : 'txtvalue',
							panelHeight : 'auto',
						}
					},
				},*/				
		        {field:'plan_cut_length',title:'Cut Length (Mtr)',width:55,editor:{
					type:'numberbox',
					options:{
						precision:2,
						required:true
					}
				}},
		        {field:'alter_cut_length_min',title:'Alternative Cut Length min (Mtr)',width:100,editor:{
					type:'numberbox',
					options:{
						precision:2,
					}
				}},
				{field:'alter_cut_length_max',title:'Alternative Cut Length max (Mtr)',width:100,editor:{
					type:'numberbox',
					options:{
						precision:2,
					}
				}},
				{field:'plan_heat_qty',title:'Plan Qty (Tons)',width:55,editor:{
					type:'numberbox',
					options:{
						precision:2,
						required:true
					}
				}},
				{field:'line_remarks',title:'Remarks',width:100, editor:{type:'textbox'}},
			    {field:'record_version',title:'record_version',hidden:true},
				{field:'line_status',title:'Line Status',hidden:true},
		    ]]
		 
	 });
}