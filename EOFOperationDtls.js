/** FurnaceAdditions Screen Start * */
function T5furnaceEntryOpen() {
	var minmax_flag = false;
	$('#t5_eof_furnace_entry_tbl_id3')
	.edatagrid(
			{
				onBeforeEdit : function(index, row) {
					row.consumption_date = formatDate(row.consumption_date);
					$('#t5_eof_furnace_entry_tbl_id3').datagrid(
							'selectRow', index);
				},
				onEndEdit : function(index, row) {
					$('#t5_eof_furnace_entry_tbl_id3').datagrid(
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
					var editors = $('#t5_eof_furnace_entry_tbl_id3')
					.datagrid('getEditors', index);
					var minval = row.val_min;
					var maxval = row.val_max;
					var actVal = $(editors[0].target);
					var actdate = $(editors[1].target);

					if ((minval == null || minval == '')
							&& (maxval == null || maxval == '')) {
						return false;
					} else {
						actVal
						.textbox({
							onChange : function() {

								var aVal = actVal
								.textbox('getText');
								if ((aVal != null && aVal != '')
										&& (minval != null && minval != '')
										&& (maxval != null && maxval != '')) 
								{
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
	var minmax_flag = false, color_flag = false;

	$('#t5_eof_furnace_entry_tbl_id1')
	.edatagrid(
			{
				onBeforeEdit : function(index, row) {
					row.consumption_date = formatDate(row.consumption_date);
					$('#t5_eof_furnace_entry_tbl_id1').datagrid(
							'selectRow', index);
				},
				onEndEdit : function(index, row) {
					$('#t5_eof_furnace_entry_tbl_id1').datagrid(
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
					var editors = $('#t5_eof_furnace_entry_tbl_id1')
					.datagrid('getEditors', index);
					var minval = row.val_min;
					var maxval = row.val_max;
					var actVal = $(editors[0].target);
					if ((minval == null || minval == '')
							&& (maxval == null || maxval == '')) {

						return false;
					} else {
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
}
function T5FurnaceAdditions() {
	var t4_trns_si_no = document.getElementById('t4_trns_si_no').value;
	var scrap_charge_at = $('#t4_scrap_charge_at').datetimebox('getText');
	if ((t4_trns_si_no != '') && (scrap_charge_at != '')) {
		$('#t5_FurnaceAdditions_form_div_id').dialog({
			modal : true,
			cache : true
		});
		$('#t5_FurnaceAdditions_form_div_id').dialog('open').dialog('center')
		.dialog('setTitle', 'Furnace Addition Details Form');

		T5furnaceEntryOpen();

		//for unsaved data detection and user confirmation related validation
		$('#t5_FurnaceAdditions_form').attr("data-change","0");
		assignRowChangeDetectionListener('t5_FurnaceAdditions_form_div_id','t5_eof_furnace_entry_tbl_id1');
		assignRowChangeDetectionListener('t5_FurnaceAdditions_form_div_id','t5_eof_furnace_entry_tbl_id2');
		assignRowChangeDetectionListener('t5_FurnaceAdditions_form_div_id','t5_eof_furnace_entry_tbl_id3');
		assignChangesDetectListenerWithTabs('t5_FurnaceAdditions_form_div_id');
		closeActionBindEvt('t5_FurnaceAdditions_form_div_id');

		var heatId = $('#t4_heat_id').textbox('getText');
		var heatcnt = document.getElementById('t4_heat_cnt').value;
		var aimpsn = $('#t4_aim_psn').textbox('getText');
		$('#t5_heat_no1').textbox('setText', heatId);
		$('#t5_aim_psn1').textbox('setText', aimpsn);
		$('#t5_heat_no2').textbox('setText', heatId);
		$('#t5_aim_psn2').textbox('setText', aimpsn);
		$('#t5_heat_no3').textbox('setText', heatId);
		$('#t5_aim_psn3').textbox('setText', aimpsn);

		$('#t5_cons_date2').datetimebox({
			value : scrap_charge_at
			// value: (formatDate(new Date()))

		});
		$('#t5_combo_scrapBktId').combobox('setValue', '');
		document.getElementById('t5_scrapBkt_hId').value = '0';
		getScrapBucketNo();
		getT5ScrapDtlsGrid();

		$('#t5_FurnaceAdditions_tabs_div_id').tabs('select', 0);

		$('#t5_FurnaceAdditions_tabs_div_id').tabs({
			border : false,
			onSelect : function(title, index) {
				if (index == 0) {
					$('#t5_combo_scrapBktId').combobox('setValue', '');
					document.getElementById('t5_scrapBkt_hId').value = '0';
					getT5ScrapDtlsGrid();
				}
				if (index == 1) {
					getT5FurnaceAddDtlsGrid();
				}
				if (index == 2) {
					getT5GasConsDtlsGrid();
				}

				if($('#t5_FurnaceAdditions_form_div_id').attr("data-change")==1){
					doSwitchingofTabs('t5_FurnaceAdditions_form_div_id');
				}else{
					return;
				}
			},
			onUnselect:function(title,index){
				//alert("????"+title +": "+index);
			}
		});

	} else {
		var msg = '';
		if (t4_trns_si_no == '') {
			msg = "Heat";
		} else {
			msg = "Scrap Charge At date";
		}
		$.messager
		.alert('Information', 'Please Select ' + msg + '...!', 'info');
	}
}// end

$(window).load(setTimeout(applyT5Filter, 1)); // 1000 ms = 1 second.

function applyT5Filter() {
	document.getElementById('t5_scrapBkt_hId').value = '0';
}

function getT5ScrapBktNo() {
	var url1 = "./CommonPool/getComboList?col1=scrap_bkt_header_id&col2=scrapBucketStatusModel.scrap_bucket_no&classname=ScrapBucketHdr&status=1&"
		+ "wherecol=scrapBucketLoadedStatusModel.lookup_code='LOADED' and scrapBucketLoadedStatusModel.lookup_id=scrap_bkt_load_status and "
		+ "scrapBucketStatusModel.scrap_bucket_id = scrap_bkt_id and scrapBucketStatusModel.scrap_bucket_status = scrap_bkt_load_status and "
		+ "scrapBucketStatusModel.record_status=";
	getDropdownList(url1, '#t5_combo_scrapBktId');
}
function getT5ConsumedScrapBktNo() {
	var trns_sl_no = document.getElementById('t4_trns_si_no').value;
	$.ajax({
		headers : {
			'Accept' : 'application/json',
			'Content-Type' : 'application/json'
		},
		type : 'GET',
		// data: JSON.stringify(formData),
		dataType : "json",
		url : "./EOFproduction/getConsumedBucketsByHeat?heat_transId="
			+ parseInt(trns_sl_no),
			success : function(data) {
				$('#t5_combo_scrapBktId').combobox('loadData', data);

			},
			error : function() {
				$.messager.alert('Processing', 'Error while Processing Ajax...',
				'error');
			}
	});
}

$("#t5_combo_scrapBktId")
.combobox(
		{
			onSelect : function(record) {
				var bucket_header_id = record.keyval;
				$.ajax({
					headers : {
						'Accept' : 'application/json',
						'Content-Type' : 'application/json'
					},
					type : 'GET',
					// data: JSON.stringify(formData),
					dataType : "json",
					url : "./scrapEntry/getLoadedScrapBktsByHeaderId?scrap_bkt_hrd_id="
						+ bucket_header_id,
						success : function(data) {
							document.getElementById('t5_scrapBkt_hId').value = bucket_header_id;
							getT5ScrapDtlsGrid();
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

function getT5ConsumedScrapDtls() {
	var t5_trns_si_no = document.getElementById('t4_trns_si_no').value;
	$.ajax({
		headers : {
			'Accept' : 'application/json',
			'Content-Type' : 'application/json'
		},
		type : 'GET',
		// data: JSON.stringify(formData),
		dataType : "json",
		url : "./EOFproduction/getConsumedBucketsDtlsByHeat?heat_transId="
			+ t5_trns_si_no,
			success : function(data) {
				var cost = 0;
				for (var i = 0; i < data.length; i++) {
					cost += data[i].qty;
				}
				$('#t5_eof_furnace_entry_tab2_tbl_id3').datagrid('loadData', {
					"total" : '',
					"rows" : data,
					"footer" : [ {
						"scrap_bkt_no" : "<b>Total Quantity(T)</b>",
						"qty" : parseFloat(cost).toFixed(2)
					} ]
				});

			},
			error : function() {
				$.messager.alert('Processing', 'Error while Processing Ajax...',
				'error');
			}
	});
}
function getT5ScrapDtlsGrid() {
	var bucket_header_id = document.getElementById('t5_scrapBkt_hId').value;

	$.ajax({
		headers : {
			'Accept' : 'application/json',
			'Content-Type' : 'application/json'
		},
		type : 'GET',
		dataType : "json",
		url : "./scrapEntry/getScrapEntryDetails?bucket_header_id=" + bucket_header_id+"&scrap_bucket_id=0&scrap_pattern_id=0",
		success : function(data) {
			var cost = 0;
			for (var i = 0; i < data.length; i++) {
				cost += data[i].material_qty;
			}
			$('#t5_eof_furnace_entry_tbl_id2').datagrid('loadData', {
				"total" : '',
				"rows" : data,
				"footer" : [ {
					"mtrlName" : "<b>Total Quantity(T)</b>",
					"material_qty" : parseFloat(cost).toFixed(2)
				} ]
			});
			getT5ConsumedScrapDtls();
		},
		error : function() {
			$.messager.alert('Processing', 'Error while Processing Ajax...',
			'error');
		}
	});
}

function getT5FurnaceAddDtlsGrid() {
	var eof_trns_sno = document.getElementById('t4_trns_si_no').value;
	var psn_no = document.getElementById('t4_aim_psn').value;

	$.ajax({
		headers : {
			'Accept' : 'application/json',
			'Content-Type' : 'application/json'
		},
		type : 'GET',
		// data: JSON.stringify(formData),
		dataType : "json",
		url : "./heatProcessEvent/getMtrlDetailsByType?mtrlType=FURNACE_ADDITIONS"
			+ "&eof_trns_sno=" + eof_trns_sno +"&psn_no="+psn_no,
			success : function(data) {
				var cost = 0;
				for (var i = 0; i < data.length; i++) {
					cost += data[i].qty;
				}

				$('#t5_eof_furnace_entry_tbl_id1').edatagrid('loadData', {
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

function getT5GasConsDtlsGrid() {
	var eof_trns_sno = document.getElementById('t4_trns_si_no').value;
	var psn_no = document.getElementById('t4_aim_psn').value;
	$
	.ajax({
		headers : {
			'Accept' : 'application/json',
			'Content-Type' : 'application/json'
		},
		type : 'GET',
		// data: JSON.stringify(formData),
		dataType : "json",
		url : "./heatProcessEvent/getMtrlDetailsByType?mtrlType=PROCESS_CONSUMPTIONS"
			+ "&eof_trns_sno=" + eof_trns_sno+"&psn_no="+ psn_no,
			success : function(data) {
				$('#t5_eof_furnace_entry_tbl_id3').datagrid('loadData',
						data);
			},
			error : function() {
				$.messager.alert('Processing',
						'Error while Processing Ajax...', 'error');
			}
	});
}

function cancelT5FurnaceDtls() {
	getT5FurnaceAddDtlsGrid();
}

function cancelT5ScrapDtls() {
	resetT5ScrapDtlsGrid();
	getT5ScrapDtlsGrid();
}
function resetT5ScrapDtlsGrid() {
	getT5ScrapBktNo();
	$('#t5_combo_scrapBktId').combobox('setValue', '');
	$('#t5_cons_date2').combobox('enable');
	$('#t5_save_scrap_btn_id').linkbutton('enable');
	document.getElementById('t5_scrapBkt_hId').value = 0;
}
function cancelT5GasConsDtls() {
	getT5GasConsDtlsGrid();
}
function saveT5FurnaceDtls() {
	var eof_trns_sno = document.getElementById('t4_trns_si_no').value;
	var sub_unit = document.getElementById('t4_sub_unit_id').value;
	var eventname = 'FURNACE_ADDITION';

	var tab1_rows = $('#t5_eof_furnace_entry_tbl_id1').datagrid('getRows');

	for (var i = 0; i < tab1_rows.length; i++) {
		$('#t5_eof_furnace_entry_tbl_id1').datagrid('endEdit', i);
	}
	var mtr_cons_si_id = 0;
	var grid_arry = '';
	for (var i = 0; i < tab1_rows.length; i++) {
		if ((tab1_rows[i].qty != null && tab1_rows[i].qty != '')
				&& (tab1_rows[i].consumption_date != null && tab1_rows[i].consumption_date != '')) {
			var consdate = formatDate(tab1_rows[i].consumption_date);
			grid_arry += tab1_rows[i].material_id + '@' + tab1_rows[i].qty
			+ '@' + consdate + '@' + tab1_rows[i].mtr_cons_si_no + '@'
			+ tab1_rows[i].version + '@' + tab1_rows[i].sap_matl_id + '@' + tab1_rows[i].valuation_type + 'SIDS';
			$('#t5_eof_furnace_entry_tbl_id1').datagrid('endEdit', i);
		}
	}

	formData = {
			"grid_arry" : grid_arry,
			"trns_eof_si_no" : eof_trns_sno
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
		url : './heatProcessEvent/SaveOrUpdate?sub_unit=' + sub_unit
		+ '&eventname=' + eventname,
		success : function(data) {
			if (data.status == 'SUCCESS') {
				$.messager.alert('Furnace Additions Details Info',
						data.comment, 'info');
				cancelT5FurnaceDtls();
			} else {
				$.messager.alert('Furnace Additions Details Info',
						data.comment, 'info');
			}
		},
		error : function() {
			$.messager.alert('Processing', 'Error while Processing Ajax...',
			'error');
		}
	});
}

function saveT5GasConsDtls() {
	var eof_trns_sno = document.getElementById('t4_trns_si_no').value;
	var sub_unit = document.getElementById('t4_sub_unit_id').value;
	var eventname = 'GAS_CONSUMPTION';

	var tab3_rows = $('#t5_eof_furnace_entry_tbl_id3').datagrid('getRows');
	for (var i = 0; i < tab3_rows.length; i++) {
		$('#t5_eof_furnace_entry_tbl_id3').datagrid('endEdit', i);
	}
	var mtr_cons_si_id = 0;
	var grid_arry = '';

	for (var i = 0; i < tab3_rows.length; i++) {
		if ((tab3_rows[i].qty != null && tab3_rows[i].qty != '')
				&& (tab3_rows[i].consumption_date != null && tab3_rows[i].consumption_date != '')) {
			var consdate = formatDate(tab3_rows[i].consumption_date);
			grid_arry += tab3_rows[i].material_id + '@' + tab3_rows[i].qty
			+ '@' + consdate + '@' + tab3_rows[i].mtr_cons_si_no + '@'
			+ tab3_rows[i].version + 'SIDS';
		}
	}

	formData = {
			"grid_arry" : grid_arry,
			"trns_eof_si_no" : eof_trns_sno
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
		url : './heatProcessEvent/SaveOrUpdate?sub_unit=' + sub_unit
		+ '&eventname=' + eventname,
		success : function(data) {
			if (data.status == 'SUCCESS') {
				$.messager.alert('Gas Consumption Details Info', data.comment,
				'info');
				getT5GasConsDtlsGrid();
			} else {
				$.messager.alert('Gas Consumption Details Info', data.comment,
				'info');
			}
		},
		error : function() {
			$.messager.alert('Processing', 'Error while Processing Ajax...',
			'error');
		}
	});

}

function saveT5ScrapDtls() {
	var cons_date = $('#t5_cons_date2').datetimebox('getValue');
	var eof_trns_sno = document.getElementById('t4_trns_si_no').value;
	var bucket_header_id = document.getElementById('t5_scrapBkt_hId').value;
	var sub_unit = document.getElementById('t4_sub_unit_id').value;
	var eventname = 'SCRAP_CHARGE_START';

	var scrapbktCbId = $('#t5_combo_scrapBktId').combobox('getValue');
	var tab2_rows = $('#t5_eof_furnace_entry_tbl_id2').datagrid('getRows');
	var cons_si_no = 0;
	var grid_arry = '';

	var totQty = 0;
	if ((bucket_header_id != null && bucket_header_id > 0)
			&& (scrapbktCbId != null || scrapbktCbId > 0) ) { //&& (actionCb != null || actionCb != '')
		for (var i = 0; i < tab2_rows.length; i++) {
			if (tab2_rows[i].material_qty != null
					&& tab2_rows[i].material_qty != '') {
				$('#t5_eof_furnace_entry_tbl_id2').datagrid('endEdit', i);
				totQty = totQty + parseFloat(tab2_rows[i].material_qty);
			}
		}
		formData = {
				"trns_eof_si_no" : eof_trns_sno,
				"scrap_bkt_header_id" : bucket_header_id,
				"qty" : totQty
		};

		$.ajax({
			headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json'
			},
			type : 'POST',
			data : JSON.stringify(formData),
			dataType : "json",
			url : './heatProcessEvent/ScrapSaveOrUpdate?cons_date=' + cons_date
			+ '&sub_unit=' + sub_unit + '&eventname=' + eventname,
			success : function(data) {
				if (data.status == 'SUCCESS') {
					$.messager.alert('Scrap Details Info', data.comment, 'info');
					$('#t5_combo_scrapBktId').combobox('setValue', '');
					document.getElementById('t5_scrapBkt_hId').value = '0';
					getT5ScrapBktNo();
					cancelT5ScrapDtls();
					getT5ScrapDtlsGrid();
				} else {
					$.messager
					.alert('Scrap Details Info', data.comment, 'info');
				}
			},
			error : function() {
				$.messager.alert('Processing',
						'Error while Processing Ajax...', 'error');
			}
		});
	}// end if
	else {
		$.messager.alert('Message', 'Please Select Scrap Bucket', 'info');
	}

}
/* will get scrapBuketNo based on action selected */
function getScrapBucketNo() {
	getT5ScrapBktNo();
	$('#t5_cons_date2').combobox('enable');
	$('#t5_save_scrap_btn_id').linkbutton('enable');

	document.getElementById('t5_scrapBkt_hId').value = 0;
	getT5ScrapDtlsGrid();
	$('#t5_combo_scrapBktId').combobox('setValue', ' ');
	//var actionVal = $('#t5_actionCombo').combobox('getText');
	$('#t5_combo_scrapBktId').combobox('setValue', '');
	var data = new Array();
	$('#t5_combo_scrapBktId').combobox('loadData', data);
}

/** FurnaceAdditions Screen End * */

/** Ladle Addition Screen Start * */
function T6LadleAdditionOpen() {
	var minmax_flag = false, color_flag = false;
	$('#t6_eof_Ladle_Addition_tbl_id')
	.edatagrid(
			{
				onBeforeEdit : function(index, row) {
					row.consumption_date = formatDate(row.consumption_date);
					$('#t6_eof_Ladle_Addition_tbl_id').datagrid(
							'selectRow', index);
				},
				onEndEdit : function(index, row) {
					$('#t6_eof_Ladle_Addition_tbl_id').datagrid(
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
					var editors = $('#t6_eof_Ladle_Addition_tbl_id')
					.datagrid('getEditors', index);
					var minval = row.val_min;
					var maxval = row.val_max;
					var actVal = $(editors[0].target);
					if ((minval == null || minval == '')
							&& (maxval == null || maxval == '')) {
						return false;
					} else {
						$('#t6_eof_Ladle_Addition_form_div_id').attr("data-change","1")
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
	assignRowChangeDetectionListener('t6_eof_Ladle_Addition_form_div_id','t6_eof_Ladle_Addition_tbl_id');
	closeActionBindEvt('t6_eof_Ladle_Addition_form_div_id');
}

function T6LadleAddition() {
	var t4_trns_si_no = document.getElementById('t4_trns_si_no').value;
	if (t4_trns_si_no != '') {
		$('#t6_eof_Ladle_Addition_form_div_id').dialog({
			modal : true,
			cache : true
		});
		$('#t6_eof_Ladle_Addition_form_div_id').dialog('open').dialog('center')
		.dialog('setTitle', 'Ladle Addition Details Form');

		var heatId = $('#t4_heat_id').textbox('getText');
		var heatcnt = document.getElementById('t4_heat_cnt').value;
		var aimpsn = $('#t4_aim_psn').textbox('getText');

		T6LadleAdditionOpen();
		$('#t6_heat_no').textbox('setText', heatId);
		$('#t6_aim_psn').textbox('setText', aimpsn);
		getT6LadleAdditionDtlsGrid();

		$('#t6_eof_Ladle_Addition_tabs_div_id').tabs({
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
	var eof_trns_sno = document.getElementById('t4_trns_si_no').value;
	var psn_no = document.getElementById('t4_aim_psn').value;
	$
	.ajax({
		headers : {
			'Accept' : 'application/json',
			'Content-Type' : 'application/json'
		},
		type : 'GET',
		// data: JSON.stringify(formData),
		dataType : "json",
		url : "./heatProcessEvent/getMtrlDetailsByType?mtrlType=LADLE_ADDITIONS"
			+ "&eof_trns_sno=" + eof_trns_sno +"&psn_no="+psn_no,
			success : function(data) {
				var cost = 0;
				for (var i = 0; i < data.length; i++) {
					cost += data[i].qty;
				}

				$('#t6_eof_Ladle_Addition_tbl_id').datagrid('loadData', {
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

function cancelT6LadleDtls() {
	getT6LadleAdditionDtlsGrid();

}
function saveT6LadleDtls() {
	var eof_trns_sno = document.getElementById('t4_trns_si_no').value;
	var sub_unit = document.getElementById('t4_sub_unit_id').value;
	var eventname = 'LADLE_ADDITIONS';

	var ladle_rows = $('#t6_eof_Ladle_Addition_tbl_id').datagrid('getRows');

	for (var i = 0; i < ladle_rows.length; i++) {
		$('#t6_eof_Ladle_Addition_tbl_id').datagrid('endEdit', i);
	}

	var mtr_cons_si_id = 0;
	var grid_arry = '';
	for (var i = 0; i < ladle_rows.length; i++) {
		$('#t6_eof_Ladle_Addition_tbl_id').datagrid('endEdit', i);

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
			"trns_eof_si_no" : eof_trns_sno
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
		url : './heatProcessEvent/SaveOrUpdate?sub_unit=' + sub_unit
		+ '&eventname=' + eventname,
		success : function(data) {
			if (data.status == 'SUCCESS') {
				$.messager.alert('Ladle Additions Details Info', data.comment,
				'info');
				cancelT6LadleDtls();
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

$(window).load(setTimeout(applyT6Filter, 1)); // 1000 ms = 1 second.

function applyT6Filter() {
	$('#t6_eof_Ladle_Addition_tbl_id').datagrid('enableFilter');
}

/** Ladle Addition Screen End * */

/** Chemitry Details Screen Start * */

function T7ChemistryDtls() {

	var t4_trns_si_no = document.getElementById('t4_trns_si_no').value;
	if (t4_trns_si_no != '') {
		$('#t7_eof_Chemistry_form_div_id').dialog({
			modal : true,
			cache : true
		});
		$('#t7_eof_Chemistry_form_div_id').dialog('open').dialog('center')
		.dialog('setTitle', 'Chemical Details Entry Form');
		$('#t7_eof_Chemistry_form_id').form('clear');

		var dummydata = new Array();
		$('#t7_eof_Chemistry_tbl_id').datagrid('loadData', dummydata);
		$('#t7_eof_chem_samp_tbl_id').datagrid('loadData', dummydata);

		var heatId = $('#t4_heat_id').textbox('getText');
		var heatcnt = document.getElementById('t4_heat_cnt').value;
		var aimpsn = $('#t4_aim_psn').textbox('getText');

		T7ChemistryDtlsOpen();
		$('#t7_heat_no').textbox('setText', heatId);
		$('#t7_aim_psn').textbox('setText', aimpsn);
		$('#t7_sample_date_time').datetimebox({
			value : (formatDate(new Date()))
		});
		getT7Dropdowns();
	} else {
		$.messager.alert('Information', 'Please Select Heat...!', 'info');
	}
	$('#t7_spectro').hide();
}

function T7ChemistryDtlsOpen() {

	var minmax_flag = false, color_flag = false, rej_flag = '';
	$('#t7_eof_Chemistry_tbl_id').edatagrid({
		// saveUrl: './scrapEntry/DtlsSaveOrUpdate',
	});

	$('#t7_eof_Chemistry_tbl_id').datagrid({
		onBeforeEdit : function(index, row) {
			$('#t7_eof_Chemistry_tbl_id').datagrid('selectRow', index);
		},

		onBeginEdit : function(index, row) {
			color_flag = false;
			var editors = $('#t7_eof_Chemistry_tbl_id').datagrid('getEditors', index);
			var minval = row.min_value;
			var maxval = row.max_value;
			var actVal = $(editors[0].target);

			if (maxval == null ) 
			{
				$.messager.alert('Information', 'Actual value should be set after Max and Min value !','info');
				color_flag = false;
				setDefaultCustomComboValues('t7_sample_result', 'REJECT', $('#t7_sample_result').combobox('getData'));
				return false;
			}
			else
			{
				actVal.textbox({onChange : function()
					{
					var aVal = actVal.textbox('getText');

					if ((aVal != null && aVal != '') && (minval != null ) && (maxval != null )) {
						minmax_flag = validateMinMax(aVal, minval,maxval);

						if (!minmax_flag) {
							$.messager.alert('Information', 'Actual value ' + aVal + ' should be in between Min '
									+ minval + ' & Max '+ maxval+ ' Values...!','info');
							color_flag = true;
							rej_flag = index;
							setDefaultCustomComboValues('t7_sample_result', 'REJECT', $('#t7_sample_result').combobox('getData'));
						}
						else
						{
							color_flag = false;
							if(rej_flag != '' && rej_flag == index)
							{
								setDefaultCustomComboValues('t7_sample_result', 'OK', $('#t7_sample_result').combobox('getData'));
							}
						}
					}

					}
				});
			}
		},
		rowStyler:function(index, row) {

			if (color_flag) {
				return 'background-color:#ffee00;color:red;';
			}
		},
		onEndEdit : function(index, row) {
			$('#t7_eof_Chemistry_tbl_id').datagrid('selectRow', index);	
		}
	});
}

function getT7Dropdowns() {
	var url1 = "./CommonPool/getComboList?col1=lookup_id&col2=lookup_value&classname=LookupMasterModel&status=1&"
		+ "wherecol=lookup_type='CHEM_LEVEL' and lookup_code = 'EAF' and lookup_value in ('HM_CHEM','EAF_TAP_CHEM','EAF_QCA_CHEM') and lookup_status=";
	var url2 = "./CommonPool/getComboList?col1=lookup_id&col2=lookup_value&classname=LookupMasterModel&status=1&"
		+ "wherecol=lookup_type='CHEM_TEST_RESULT' and lookup_status=";

	getDropdownList(url1, '#t7_analysis_type');
	getDropdownList(url2, '#t7_sample_result');
}

$("#t7_analysis_type")
.combobox(
		{
			onSelect : function(record) {
				$("#t7_sample_no").combobox('enable');
				document.getElementById('t7_sample_si_no').value = '0';
				$('#t7_sample_no').combobox('setValue', '');
				setDefaultCustomComboValues('t7_sample_result',
						'OK', $('#t7_sample_result').combobox(
						'getData'));
				//$("#t7_sample_result").combobox('enable');
				$('#t7_final_result_btn_id').linkbutton('enable');
				$('#t7_save_chem_btn_id').linkbutton('enable');
				$('#t7_close_chem_btn_id').linkbutton('enable');
				$('#t7_getSample_btn').linkbutton('enable');

				getT7ChemistryDtlsGrid();
				getT7ChemSampleDtls();
				//GetSample(); //generating sample number without generate sample number button...
			}
		});

function getT7ChemistryDtlsGrid() {
	var heat_id = $('#t4_heat_id').combobox('getText');
	var heat_counter = document.getElementById('t4_heat_cnt').value;
	var analysis_id = $('#t7_analysis_type').combobox('getValue');
	var analysis_type = $('#t7_analysis_type').combobox('getText');
	var sample_no = $('#t7_sample_no').combobox('getText');
	var actual_sample_no=sample_no.substring(sample_no.indexOf("L") + 0);
	var sub_unit_id = document.getElementById('t4_sub_unit_id').value;
	var psn_id = document.getElementById('t4_aim_psn_id').value;
	var hm_recv_id = document.getElementById('t4_hmRecvId').value;
	var url_spectrocheck = $('#t7_analysis_type').combobox('getText');

	if (heat_id != null && heat_id != '' && heat_counter != null
			&& heat_counter != '' && url_spectrocheck=='EAF_TAP_CHEM') {
		$('#t7_spectro').show();

		$.ajax({
			headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json'
			},
			type : 'GET',
			// data: JSON.stringify(formData),
			dataType : "json",
			url : "./heatProcessEvent/getChemDtlsByAnalysis?analysis_id="
				+ analysis_id + "&heat_id=" + heat_id + "&heat_counter="
				+ heat_counter + "&sub_unit_id=" + sub_unit_id
				+ "&sample_no=" + sample_no + "&psn_id=" + psn_id
				+ "&hm_recv_id=" + hm_recv_id+"&analysis_type="+analysis_type,
				success : function(data) {
					$('#t7_eof_Chemistry_tbl_id').datagrid('loadData', data);
				},
				error : function() {
					$.messager.alert('Processing',
							'Error while Processing Ajax...', 'error');
				}
		});

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
			url : "./heatProcessEvent/getChemDtlsByAnalysis?analysis_id="
				+ analysis_id + "&heat_id=" + heat_id + "&heat_counter="
				+ heat_counter + "&sub_unit_id=" + sub_unit_id
				+ "&sample_no=" + sample_no + "&psn_id=" + psn_id
				+ "&hm_recv_id=" + hm_recv_id+"&analysis_type="+analysis_type,
				success : function(data) {
					$('#t7_eof_Chemistry_tbl_id').datagrid('loadData', data);
				},
				error : function() {
					$.messager.alert('Processing',
							'Error while Processing Ajax...', 'error');
				}
		});

	}
}

//Final Result Datagrid t7_eof_chem_samp_tbl_id
function getT7ChemSampleDtls() {
	var heat_id = $('#t4_heat_id').combobox('getText');
	var heat_counter = document.getElementById('t4_heat_cnt').value;
	var analysis_id = $('#t7_analysis_type').combobox('getValue');
	var sub_unit_id = document.getElementById('t4_sub_unit_id').value;

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
					$('#t7_eof_chem_samp_tbl_id').datagrid('loadData', data);
				},
				error : function() {
					$.messager.alert('Processing',
							'Error while Processing Ajax...', 'error');
				}
		});

	}
}

function validateT7HeatChemForm() {
	return $('#t7_eof_Chemistry_form_id').form('validate');
}

/*
function enableSave()
{
    var rejection_counter=0;
	var child_rows = $('#t7_eof_Chemistry_tbl_id').datagrid('getRows');
				
	for (var i = 0; i < child_rows.length; i++) {
				
		if ((child_rows[i].aim_value != null && child_rows[i].max_value != null))
		{
			if( child_rows[i].aim_value > child_rows[i].max_value)
				{
			    	rejection_counter = rejection_counter+1;
			    	break;
				}
		   }
	   }
	if(rejection_counter > 0)
	{
		$.messager.alert('Processing', 'Please enter correct data?','info');
		return false;
	}
	else
	{
		$('#t7_ok_chem_btn_id').linkbutton('disable');
		$('#t7_save_chem_btn_id').linkbutton('enable');	
	}
	
	
}*/

function saveT7ChemistryDtls() { 
	var gridArray = $('#t7_eof_Chemistry_tbl_id').datagrid('getSelections');
			
		if (validateT7HeatChemForm())
		{
			var sample_date_time = $('#t7_sample_date_time').datetimebox('getValue');
			var sample_no = $('#t7_sample_no').combobox('getText');
			var sample_temp = $('#t7_sample_temp').numberbox('getValue');
			var sub_unit = document.getElementById('t4_sub_unit_id').value;
			var analysis_type = $('#t7_analysis_type').combobox('getText');
			var analysis_id = $('#t7_analysis_type').combobox('getValue');
			var final_result = $('#t7_sample_result').combobox('getValue');
			var remarks = $('#t7_remarks').textbox('getValue');
			var sample_si_no = document.getElementById('t7_sample_si_no').value;

			var heat_id = $('#t4_heat_id').combobox('getText');
			var heat_counter = document.getElementById('t4_heat_cnt').value;

			if (sample_si_no == null || sample_si_no == '') {
				sample_si_no = 0;
			}
			var eventname = 'EAF_CHEM_DETAILS';
			var rows = $('#t7_eof_Chemistry_tbl_id').datagrid('getRows');

			for (var i = 0; i < rows.length; i++) {
				$('#t7_eof_Chemistry_tbl_id').datagrid('endEdit', i);
			}
			var child_rows = $('#t7_eof_Chemistry_tbl_id').datagrid('getRows');// $('#t7_eof_Chemistry_tbl_id').datagrid('getSelections');
			var grid_arry = '';

			for (var i = 0; i < child_rows.length; i++) {
				if ((child_rows[i].aim_value != null && child_rows[i].aim_value != '')) {
					grid_arry += child_rows[i].element + '@'
					+ child_rows[i].aim_value + '@'
					+ child_rows[i].min_value + '@'
					+ child_rows[i].max_value + '@'
					+ child_rows[i].dtls_si_no + 'SIDS';	
				}
			}
			if(grid_arry == '' || grid_arry == null)
			{
				$.messager.alert('Processing',
						' Chemistry elements data not found...', 'error');
				return;
			}

			formData = {
					"heat_id" : heat_id,
					"heat_counter" : heat_counter,
					"sample_no" : sample_no,
					"sample_temp" : sample_temp,
					"sub_unit_id" : sub_unit,
					"sub_unit_id" : sub_unit,
					"analysisType" : analysis_type,
					"analysis_type":analysis_id,
					"sample_result" : final_result,
					"remarks" : remarks,
					"sample_si_no" : sample_si_no,
					"grid_arry" : grid_arry
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
				url : './heatProcessEvent/ChemDtlsSaveOrUpdate?eventname='
					+ eventname + '&sample_date=' + sample_date_time,
					success : function(data) {
						if (data.status == 'SUCCESS') {

							$.messager.alert('Chemical Details Info', data.comment,
							'info');
							//saveFinalResult(data.msg); 
							//getT7ChemSampleDtls();
							clearAllData();
						} else {
							$.messager.alert('Chemical Details Info', data.comment,
							'info');
						}
					},
					error : function() {
						$.messager.alert('Processing',
								'Error while Processing Ajax...', 'error');
					}
			});

		}/// end valid if
		

}

function clearAllData() {
	$('#t7_sample_date_time').datetimebox({
		value : (formatDate(new Date()))
	});
	$('#t7_sample_no').combobox('setValue', '');
	$('#t7_sample_temp').numberbox('setValue', '');
	$('#t7_analysis_type').combobox('setValue', '');
	$('#t7_sample_result').combobox('setValue', '');
	$('#t7_remarks').textbox('setText', '');
	document.getElementById('t7_sample_si_no').value = '0';
	var dummydata = new Array();
	$('#t7_sample_no').combobox('loadData', dummydata);
	$('#t7_eof_Chemistry_tbl_id').datagrid('loadData', dummydata);
	$('#t7_eof_chem_samp_tbl_id').datagrid('loadData', dummydata);
}

$(window).load(setTimeout(applyT7Filter, 1)); // 1000 ms = 1 second.

function applyT7Filter() {
	document.getElementById('t7_sample_si_no').value = '0';
}

function cancelT7ChemistryDtls() {
	getT7ChemistryDtlsGrid();
}



$('#t7_eof_chem_samp_tbl_id').datagrid({
	rowStyler:function(index,row){
		if (row.final_result == 1){
			var analysis_id = $('#t7_analysis_type').combobox('getValue');
			var psn_id = document.getElementById('t4_aim_psn_id').value;
			var chem_hdr_id = row.sample_si_no; 

			$('#t7_final_result_btn_id').linkbutton('disable');
			$('#t7_save_chem_btn_id').linkbutton('disable');
			$('#t7_close_chem_btn_id').linkbutton('disable');
			$('#t7_getSample_btn').linkbutton('disable');
			$('#t7_spectro').hide();

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

						$('#t7_eof_Chemistry_tbl_id').datagrid('loadData', data);

						$('#t7_sample_no').combobox('setValue', row.sample_no);
						$('#t7_sample_date_time').datetimebox({	value : formatDate(row.sample_date_time)});
						$('#t7_sample_temp').numberbox('setValue', row.sample_temp);
						$('#t7_sample_result').combobox('setValue', row.sample_result);
						if(row.remarks != 'null'){
							$('#t7_remarks').textbox('setText', row.remarks);}
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

function viewT7SampleDtls(value, row) {
	return '<a href="#" onclick="viewT7ChemDtls(\''
	+ row.sample_si_no +','+row.sample_no+','+row.sample_temp+','+formatDate(row.sample_date_time)+','
	+row.sample_result+','+row.remarks+ '\')">View Detail</a>';
}

function viewT7ChemDtls(chem_dtls){

	var analysis_id = $('#t7_analysis_type').combobox('getValue');
	var psn_id = document.getElementById('t4_aim_psn_id').value;

	var c_dtl=chem_dtls.split(",");
	chem_hdr_id = c_dtl[0]; samp_no = c_dtl[1]; samp_temp = c_dtl[2];
	samp_date = c_dtl[3]; samp_res = c_dtl[4]; remarks = c_dtl[5];
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

				$('#t7_eof_Chemistry_tbl_id').datagrid('loadData', data);

				$('#t7_sample_no').combobox('setValue', samp_no);
				$('#t7_sample_date_time').datetimebox({	value : samp_date});
				$('#t7_sample_temp').numberbox('setValue', samp_temp);
				$('#t7_sample_result').combobox('setValue', samp_res);
				if(remarks != 'null'){
					$('#t7_remarks').textbox('setText', remarks);}
			},
			error : function() {
				$.messager.alert('Processing',
						'Error while Processing Ajax...', 'error');
			}
	});

}

function saveFinalResult(sample_no){
	var row = $('#t7_eof_chem_samp_tbl_id').datagrid('getSelected');
	var samp_hdr_id = row.sample_si_no;//sample_no;
	var sub_unit_id = document.getElementById('t4_sub_unit_id').value;

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
				getT7ChemSampleDtls();
			} else {
				$.messager.alert('Result Info', data.comment, 'info');
			};
		},
		error : function() {
			$.messager.alert('Processing', 'Error while Processing Ajax...',
			'error');
		}
	});
}


/** Chemical Details Screen End * */

/** Events Screen Start * */
function T8EventsDtls() {
	assignRowChangeDetectionListener('t8_eof_events_form_div_id','t8_eof_events_tbl_id');
	closeActionBindEvt('t8_eof_events_form_div_id');
	$('#t8_eof_events_tbl_id').edatagrid({
		onBeginEdit:function(index,row){
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
		},
		onBeforeEdit : function(index, row) {		
			if(row.event_date_time==null){
				row.event_date_time = formatDate(new Date());
			}
			else{
				row.event_date_time = formatDate(row.event_date_time);
			}
			$('#t8_eof_events_tbl_id').datagrid('selectRow', index);
		},
		onEndEdit : function(index, row) {
			$('#t8_eof_events_form_div_id').attr("data-change","1")
			$('#t8_eof_events_tbl_id').datagrid('selectRow', index);
			var dt = (row.event_date_time).split(" ");

			var cons_time = new Date(commonGridDtfmt(dt[0], dt[1]));

			var time = cons_time.getTime();
			$('#t8_eof_events_form_div_id').attr("data-change","1")
			row.event_date_time = time;
		},
		onAfterEdit:function(index,row){
			row.editing = false;

			$('#t8_eof_events_form_div_id').attr("data-change","1")
		}
	});
	//$("#t8_tare_weight").textbox({"editable":false});

}

function T8EventsDtlsOpen() {
	var t4_trns_si_no = document.getElementById('t4_trns_si_no').value;
	var hmRecid=document.getElementById('t4_heat_receieve_id').value;
	//$("#t8_tare_weight").textbox("setText","")
	if(hmRecid!=''&& hmRecid!=null){
		$.ajax({
			headers: { 
				'Accept': 'application/json',
				'Content-Type': 'application/json' 
			},
			type: 'GET',
			//data: JSON.stringify(formData),
			dataType: "json",
			url: './HMReceive/getHMDetailsbyId?recvid='+hmRecid,
			success: function(data) {
			}
		})
	}

	if (t4_trns_si_no != '') {
		$('#t8_eof_events_form_div_id').dialog({
			modal : true,
			cache : true
		});
		$('#t8_eof_events_form_div_id').dialog('open').dialog('center').dialog(
				'setTitle', 'Events Details Form');
		$('#t8_eof_events_form_id').form('clear');

		var heatId = $('#t4_heat_id').textbox('getText');
		var heatcnt = document.getElementById('t4_heat_cnt').value;
		var aimpsn = $('#t4_aim_psn').textbox('getText');
		T8EventsDtls();
		$('#t8_heat_no').textbox('setText', heatId);
		$('#t8_aim_psn').textbox('setText', aimpsn);
		getT8EventDtlsGrid();

	} else {
		$.messager.alert('Information', 'Please Select Heat...!', 'info');
	}
}// end

function getT8EventDtlsGrid() {
	var eof_trns_sno = document.getElementById('t4_trns_si_no').value;

	$.ajax({
		headers : {
			'Accept' : 'application/json',
			'Content-Type' : 'application/json'
		},
		type : 'GET',
		// data: JSON.stringify(formData),
		dataType : "json",
		url : "./heatProcessEvent/getHeatProcessEventDtls?eof_trns_sno="
			+ eof_trns_sno+"&unit=EAF",
			success : function(data) {
				$('#t8_eof_events_tbl_id').datagrid('loadData', data);
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

/*function checkHMPourEnd(value){
	 var dg = $("#t8_eof_events_tbl_id");
	    var row = dg.datagrid('getSelected');
	    if(row.eventName=="HM_CHARGE_END"){    	
	    	$("#t8_tare_weight").textbox({"editable":true});
	    }
}*/

function cancelT8EventDtls() {
	getT8EventDtlsGrid();
}
function updateT8TareWeight(heat_receieve_id,tareWight,eof_trns_sno){
	$.ajax({
		headers : {
			'Accept' : 'application/json',
			'Content-Type' : 'application/json'
		},
		type : 'POST',
		// data: JSON.stringify(formData),
		dataType : "json",
		url : "./HMReceive/hmMetalReceiptUpdate?hmRevId="+heat_receieve_id+"&tareWeight="+tareWight+"&trans_si_no="+eof_trns_sno,
		success : function(data) {
			$('#t4_hm_wt').numberbox('setValue',parseFloat(data.comment));
		},
		error : function() {
			$.messager.alert('Processing', 'Error while Processing Ajax...',
			'error');
		}
	});
}

function saveT8EventDtls() {
	var heat_receieve_id = document.getElementById('t4_heat_receieve_id').value;
	//var tareWight=$('#t8_tare_weight').textbox('getText');
	var eof_trns_sno = document.getElementById('t4_trns_si_no').value;
	var sub_unit = document.getElementById('t4_sub_unit_id').value;
	var sel_rows = $('#t8_eof_events_tbl_id').datagrid('getRows');
	var grid_arry = '';
	var date_prev_val='';
	var isDateValid ='NO';
	var isDateNull ='YES';
	var eventdate = '';

	for (var i = 0; i < sel_rows.length; i++) {
		$('#t8_eof_events_tbl_id').datagrid('endEdit', i);
		if (i == 0){
			if (sel_rows[i].event_date_time == '' || sel_rows[i].event_date_time== null || sel_rows[i].event_date_time== ' '){
				isDateValid ='NO';
				break;
			}else{			
				date_prev_val = formatDate(sel_rows[i].event_date_time);
				// alert('row 000--'+sel_rows[i].event_date_time);
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
		/*
		if(sel_rows[i].eventName == "HM_CHARGE_END" &&sel_rows[i].event_date_time!=null ){
			if(tareWight!='' && parseFloat(tareWight)>0){
				updateT8TareWeight(heat_receieve_id,tareWight,eof_trns_sno);
				}else{
					$.messager.alert('Tare Weight','Tare Weight is required of HM_CHARGE_END','warning');
				}
		}
		if(sel_rows[i].eventName == "HM_CHARGE_END" &&sel_rows[i].event_date_time==null ){
			if(tareWight!=null){			
					$.messager.alert('Tare Weight','HM_CHARGE_END value should be entered to calculate HM weight','warning');
			}			
		}
		 */
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
				+ eof_trns_sno+'&unit=EAF',
				success : function(data) {
					if (data.status == 'SUCCESS') {
						$.messager.alert('Event Details Info', data.comment, 'info');
						$('#t8_eof_events_form_div_id').attr("data-change","0")
						cancelT8EventDtls();
						$('#t4_heat_id').combobox('setValue',$('#t4_heat_id').combobox('getValue'));//setting heat id in the heat details table.
						setT4HeatFormData();//resetting all data in table against the heat id(function present in-->>EOFProduction.jsp).
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

$(window).load(setTimeout(applyT8Filter, 1)); // 1000 ms = 1 second.

function applyT8Filter() {
	$('#t8_eof_events_tbl_id').datagrid('enableFilter');
}

/** Events Screen End * */

/**Delay Details Screen Start**/
function addT9DelayEntry(){
	//alert("alert....");
	var heatId = $('#t4_heat_id').textbox('getText');
	if (heatId!=null && heatId!=''){
		openT9DelayEntryScreen();
	}else{
		$.messager.alert('Warning', 'Select Heat Number',
		'info');
	}
}

function t91ReferenshData(){
	refreshT9DelayDetailsView(parseInt($("#trans_delay_dtl_id_value").val()));
}


function t9addDelayDetails(){
	$('#t91_delay_entry_form_id').form('clear');
	var row = $('#t9_delay_entry_tbl_id').datagrid('getSelected');

	if(row!=null){
		var heatId = $('#t4_heat_id').textbox('getText');
		var aimpsn = $('#t4_aim_psn').textbox('getText');
		if(row.transDelayEntryhdr!=null ){
			$("#t91_heat_no").textbox("setText",heatId);
			$("#t91_aim_psn").textbox("setText",aimpsn);
			$("#t91_activity").textbox("setText",row.activity_master.activities);

			$('#t91_delay_entry_form_div_id').dialog({
				modal : true,
				cache : true
			});
			$('#t91_delay_entry_form_div_id').dialog('open').dialog('center').dialog(
					'setTitle', 'Delay Details Form');

			$("#t91_delay_entry_tbl_id").attr("data-delay","0")

			refreshT9DelayDetailsView(row.transDelayEntryhdr.trns_delay_entry_hdr_id);
			DelayT9Init(row);
			$("#t91activity_value").val(row.activity_master.activities);
			$("#t91delay_details").val(row.activity_master.delay_details);
			$("#trans_delay_dtl_id_value").val(row.transDelayEntryhdr.trns_delay_entry_hdr_id);
			$("#t91delay").val(row.delay);
			$('#t91_delay_entry_tbl_id').edatagrid(
					{				
						onBeginEdit :function(index,rowE){
							var editors = $('#t91_delay_entry_tbl_id')
							.datagrid('getEditors', index);
							var actVal = $(editors[2].target);//delay entry field
							actVal
							.textbox({
								onChange : function(newV,oldV) {
									var aVal = actVal
									.textbox('getText');
									if(oldV==''){
										oldV=0;
									}
									if(parseInt(newV)<parseInt(oldV)){						
										var neg=parseInt(newV)-parseInt(oldV)
										$("#t91_delay_entry_tbl_id").attr("data-delay",neg)
									}else{
										var neg=parseInt(newV)-parseInt(oldV)
										$("#t91_delay_entry_tbl_id").attr("data-delay",neg)
									}							
								}})

						},
						onBeforeEdit : function(index, rowE) {

						},
						onBeforeSave : function(index, rowE) {
							var data = $('#t91_delay_entry_tbl_id').edatagrid('getData');
							var rows = data.rows;
							var sum = 0;

							for (i=0; i < rows.length; i++) {
								if(typeof rows[i].delay_dtl_duration !="undefined"){
									sum+=rows[i].delay_dtl_duration;
								}
							}

							sum = sum+parseInt($("#t91_delay_entry_tbl_id").attr("data-delay"));

							if(sum>row.delay){
								$.messager
								.alert(
										'Information',
										"Sum : "+ sum +" Activity Delay : "+row.delay
										+ " Sum of delay minutes should not be grater than activity delay minutes ",
								'info');
								$("#t91_delay_entry_tbl_id").attr("data-delay","0")
								return false;
							}
						},
						onSuccess : function(index, rowE) {				
							refreshT9DelayDetailsView(row.transDelayEntryhdr.trns_delay_entry_hdr_id)
						},
						onError : function(index, row) {
							alert("Error! opertaion failed." );
						}
					});
		}else{
			$.messager.alert('Processing', 'Please Save Details to enter delay details!',
			'info');
		}
	}else{
		$.messager.alert('Processing', 'No row selected...!',
		'info');
	}
}

function refreshT9DelayDetailsView(trans_delay_entry_hdr_id){
	$.ajax({
		headers : {
			'Accept' : 'application/json',
			'Content-Type' : 'application/json'
		},
		type : 'GET',
		// data: JSON.stringify(formData),
		dataType : "json",
		url : "./EOFproduction/getDelayDtlsByDelayHdr?trans_delay_entry_hdr_id="+trans_delay_entry_hdr_id,

		success : function(data) {	
			$('#t91_delay_entry_tbl_id').datagrid('loadData', data);
		},
		error : function() {
			$.messager.alert('Processing', 'Error while Processing Ajax...',
			'error');
		}
	});
}


function formatT9ActivityColumnData(colName, value, row, index) {
	try {
		if(row.isNewRecord){
			return $("#t91activity_value").val();
		}else{
			if(eval("row."+colName) === null)
			{
				return $("#t91activity_value").val();
			}else{
				return eval("row."+colName);
			}
		}
	}catch(e)
	{
		return "";
	}
}
function formatT9DlyDtlsColumnData(colName, value, row, index) {
	try {
		if(row.isNewRecord){
			return $("#t91delay_details").val();
		}else{
			if(eval("row."+colName) === null)
			{   		
				return $("#t91delay_details").val();
			}else{
				return eval("row."+colName);
			}
		}
	}catch(e)
	{
		return "";
	}
}

function DelayT9Init(row){
	$('#t91_delay_entry_tbl_id')
	.edatagrid(
			{
				updateUrl : './EOFproduction/TransDelayDtlsUpdate?trans_delay_entry_hdr_id='+row.transDelayEntryhdr.trns_delay_entry_hdr_id,
				saveUrl : './EOFproduction/TransDelayDtlsSave?trans_delay_entry_hdr_id='+row.transDelayEntryhdr.trns_delay_entry_hdr_id					
			});
}

function saveT9DelaytDtls(){
	var rowsCh = $('#t9_delay_entry_tbl_id').edatagrid('getChanges');
	var rows = $('#t9_delay_entry_tbl_id').datagrid('getRows');
	for ( var i = 0; i < rows.length; i++) {
		$('#t9_delay_entry_tbl_id').datagrid('endEdit', i);
	} 
	var sel_rows = $('#t9_delay_entry_tbl_id').edatagrid('getRows');
	var isReadyToSave=false;
	for(var k=0;k<sel_rows.length;k++){
		if(sel_rows[k].transDelayEntryhdr==null){
			isReadyToSave=true;
		}
	}
	if( $("#t9_delay_entry_form_div_id").attr("data-rowchange")=="1" || isReadyToSave){

		var heatId = $('#t4_heat_id').textbox('getText');
		$.ajax({
			headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json'
			},
			type : 'POST',
			data : JSON.stringify(sel_rows),
			// data: rows,
			dataType : "json",
			url : './EOFproduction/TransDelaySave?heat_id='+heatId,
			success : function(data) {
				if (data.status == 'SUCESS') {					
					$.messager.alert('Info', data.comment, 'info');
					getT9DelayDetails();
					$("#t9_delay_entry_form_div_id").attr("data-rowchange","0");
				} else {
					$.messager.alert('Info', data.comment, 'info');
				}
			},
			error : function() {
				$.messager.alert('Processing', 'Error while Processing Ajax...',
				'error');
			}
		});
	}else{
		$.messager.alert('Processing', 'No changes Present!',
		'info');
	}
}


function openT9DelayEntryScreen(){
	getT9DelayDetails();
	$("#t9_delay_entry_form_div_id").attr("data-rowchange","0");
	$('#t9_delay_entry_form_div_id').on('keyup change paste', 'input, select, textarea', function(){
		$("#t9_delay_entry_form_div_id").attr("data-rowchange","1");
	});
	$('#t9_delay_entry_form_div_id').dialog({
		modal : true,
		cache : true
	});
	$('#t9_delay_entry_form_div_id').dialog('open').dialog('center').dialog(
			'setTitle', 'Delay Details Form');
	$('#t9_delay_entry_form_id').form('clear');

	var heatId = $('#t4_heat_id').textbox('getText');

	var aimpsn = $('#t4_aim_psn').textbox('getText');
	$("#t9_heat_no").textbox("setText",heatId);
	$("#t9_aim_psn").textbox("setText",aimpsn);

	$('#t9_delay_entry_tbl_id').edatagrid(
			{				
				onBeginEdit :function(index,rowE){}});
}
function getT9DelayDetails(){
	var t4_trns_si_no2 = document.getElementById('t4_trns_si_no').value;
	var sub_unit_id=$('#t4_unit').combobox('getValue');
	$.ajax({
		headers : {
			'Accept' : 'application/json',
			'Content-Type' : 'application/json'
		},
		type : 'GET',
		// data: JSON.stringify(formData),
		dataType : "json",
		url : "./EOFproduction/activityDelayMstrBySubunit?sub_unit_id="+sub_unit_id +"&trns_si_no="+t4_trns_si_no2,

		success : function(data) {
			$('#t9_delay_entry_tbl_id').datagrid('loadData', data);			
		},
		error : function() {
			$.messager.alert('Processing', 'Error while Processing Ajax...',
			'error');
		}
	});
}

/**Delay Details Screen End**/

/** PSN Document Start * */

function T10PSNDocsView() {
	var psn_hdr_sl_no = document.getElementById('t4_aim_psn_id').value;

	var psn_no = $('#t4_aim_psn').combobox('getText');
	if (psn_hdr_sl_no != null && psn_hdr_sl_no != '') {
		$('#t10_PSN_Docs_form_div_id').dialog({
			modal : true,
			cache : true
		});
		$('#t10_PSN_Docs_form_div_id').dialog('open').dialog('center').dialog(
				'setTitle', 'PSN Document Details Form');
		$('#t7_eof_Chemistry_form_id').form('clear');
		T10PSNDocsInit();
		$('#t10_rep_psn_desc').textbox('setText', psn_no);		
		document.getElementById('t10_psn_no').value=psn_hdr_sl_no;
		refreshT10PSNDocs(); 		
	} else {
		$.messager.alert('PSN DOC Details Info',
				'Please Select Heat & Check PSN ...!', 'info');
	}

}

function T10PSNDocsInit() {
	$('#t10_PSN_Docs_tbl_id').edatagrid({
	});

	$('#t10_PSN_Docs_tbl_id').edatagrid({
		onSuccess : function(index, row) {		
			refreshT10PSNDocs();
		},
		onError : function(index, row) {
			alert("Error In " + row.doc_sl_no);
		}
	});
}

function refreshT10PSNDocs() {
	var psn_hdr_sl_no = document.getElementById('t4_aim_psn_id').value;
	var psn_no = $('#t4_aim_psn').combobox('getText');
	clearAllDivs();
	executeReportDisplay();
	$.ajax({
		headers : {
			'Accept' : 'application/json',
			'Content-Type' : 'application/json'
		},
		type : 'GET',
		// data: JSON.stringify(formData),
		dataType : "json",
		url : './heatProcessEvent/getPSNDocsByPSN?psn_no=' + psn_no
		+ '&psn_hdr_sl_no=' + psn_hdr_sl_no,
		success : function(data) {

			$('#t10_PSN_Docs_tbl_id').edatagrid('loadData', data);
		},
		error : function() {
			$.messager.alert('Processing', 'Error while Processing Ajax...',
			'error');
		}
	});
}

function viewT10PSNFile(value, row) {
	var href = './heatProcessEvent/PSNDocsView?doc_sl_no=' + row.doc_sl_no;
	return '<a target="_blank" href="' + href + '">View Detail</a>';
}

$(window).load(setTimeout(applyT10Filter, 1)); // 1000 ms = 1 second.

function applyT10Filter() {
	$('#t10_PSN_Docs_tbl_id').datagrid('enableFilter');
}

function cancelT10PSNDocs() {
	refreshT10PSNDocs();	
}
/** PSN Document Screen End * */
/** Dispatch Screen End * */
function checkScrapEntry() {
	var t4_trns_si_no2 = document.getElementById('t4_trns_si_no').value;
	$.ajax({
		headers : {
			'Accept' : 'application/json',
			'Content-Type' : 'application/json'
		},
		type : 'GET',
		// data: JSON.stringify(formData),
		dataType : "json",
		url : './EOFproduction/checkScrapEntryByTransId?transId='
			+ parseInt(t4_trns_si_no2),
			success : function(data) {
				return data;
			},
			error : function() {
				$.messager.alert('Processing', 'Error while Processing Ajax...',
				'error');
			}
	});
}
function checkHeatDetails() {
	var vflag = false;

	var scrap_charge_at = $('#t4_scrap_charge_at').datetimebox('getText');
	if (scrap_charge_at == null || scrap_charge_at == '') {
		if (!checkScrapEntry()) {
			$.messager.alert('Information',
					'Scrap Charge Date cannot be Null..!', 'info');
		}
	}

	var hm_charge_at = $('#t4_hm_charge_at').datetimebox('getText');
	var tap_start_at = $('#t4_tap_start_at').datetimebox('getText');
	var tap_close_at = $('#t4_tap_close_at').datetimebox('getText');
	if (hm_charge_at == null || hm_charge_at == '') {
		$.messager.alert('Information', 'HM Charge Date cannot be Null !',
		'info');
		return vflag;
	}
	if (tap_start_at == null || tap_start_at == '') {
		$.messager.alert('Information', 'Tap start Date cannot be Null !',
		'info');
		return vflag;
	}
	if (tap_close_at == null || tap_close_at == '') {
		$.messager.alert('Information', 'Tap close Date cannot be Null !',
		'info');
		return vflag;
	}
	return true;
}

function checkEventsDtls(callback){
	var eof_trns_sno = document.getElementById('t4_trns_si_no').value;
	var isSuccess=true;
	$.ajax({
		headers : {
			'Accept' : 'application/json',
			'Content-Type' : 'application/json'
		},
		type : 'GET',
		// data: JSON.stringify(formData),
		dataType : "json",
		url : "./heatProcessEvent/getHeatProcessEventDtls?eof_trns_sno="
			+ eof_trns_sno+"&unit=EAF",
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

function checkDelayDtlsPresent(callback){
	var isSuccess=false;
	var t4_trns_si_no2 = document.getElementById('t4_trns_si_no').value;
	var sub_unit_id=$('#t4_unit').combobox('getValue');
	$.ajax({
		headers : {
			'Accept' : 'application/json',
			'Content-Type' : 'application/json'
		},
		type : 'GET',
		// data: JSON.stringify(formData),
		dataType : "json",
		url : "./EOFproduction/presentActivityDelayMstrBySubunit?sub_unit_id="+sub_unit_id +"&trns_si_no="+t4_trns_si_no2,

		success : function(data) {
			if(data.length>0){
				isSuccess=true;
			}
			callback(isSuccess);
		},
		error : function() {
			$.messager.alert('Processing', 'Error while Processing Ajax...',
			'error');
		}
	});
}

function T11DispatchDtls() {
	var t4_trns_si_no1 = document.getElementById('t4_trns_si_no').value;
	if (t4_trns_si_no1 != '') {
		if (checkHeatDetails()) {
			checkEventsDtls(function(isEventPresent){
				if(isEventPresent){					
					checkDelayDtlsPresent(function(isEventPresent){
						if(isEventPresent){							
							//scrap bucket check...
							$.ajax({
								headers : {
									'Accept' : 'application/json',
									'Content-Type' : 'application/json'
								},
								type : 'GET',
								// data: JSON.stringify(formData),
								dataType : "json",
								url : "./EOFproduction/getConsumedBucketsDtlsByHeat?heat_transId="
									+ t4_trns_si_no1,
									success : function(data) {
										if(data.length==0){
											$.messager.alert('Missing', 'Scrap Bucket Not Loaded!!',
											'warning');
										}
									},
									error : function() {
										$.messager.alert('Processing', 'Error while Processing Ajax...',
										'error');
									}
							});

							$('#t11_eof_dispatch_win').window('open');
							$('#t11_eof_dispatch_form_id').form('clear');	
							$("#T4ladleDetailsId").show();
							$('T4ladleDetailsId').datagrid('reload');	
							getT11Dropdowns();
							//getLaddleLifeDet();
							var heatId = $('#t4_heat_id').textbox('getText');
							var heatcnt = document.getElementById('t4_heat_cnt').value;
							var aimpsn = $('#t4_aim_psn').textbox('getText');

							totalWtValidation(function(addwt){		
								$('#t11_tap_weight').textbox('setText',parseFloat(addwt));
							});												
						}else{
							$.messager.alert('Missing', 'some of delay entries are missing please check it',
							'warning');
						}
					})
				}else{
					$.messager.alert('warning', 'Some of Event details missing please check events',
					'warning');
				}
			})
		}
	} else {
		$.messager.alert('Information', 'Please Select Heat...!', 'info');
	}
}
/*
function getLaddleLifeDet() {
	var ladleNo = $('#t11_steel_ladle').combobox('getValue');
	if( ladleNo!=null && ladleNo!='')
	{
		$.ajax({
			headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json'
			},
			type : 'GET',
			// data: JSON.stringify(formData),
			dataType : "json",
			url : './EOFproduction/getLaddlePartsLifeDet?steelLadleNo=' + ladleNo,
			success : function(data) {
				$('#t11_parts_life_det_tbl_id').datagrid('loadData', data);
			},
			error : function() {
				$.messager.alert('Processing', 'Error while Processing Ajax...',
				'error');
			}
		});
	}
}*/
/*function restLaddleLifeDet() {
	var ladleNo = $('#t11_steel_ladle').combobox('getValue');
	if(ladleNo!='' && ladleNo!=null){
		$.ajax({
			headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json'
			},
			type : 'GET',
			// data: JSON.stringify(formData),
			dataType : "json",
			url : './EOFproduction/getLaddlePartsLifeDet?steelLadleNo=' + ladleNo,
			success : function(data) {
				for (var i = 0; i < data.length; i++) {
					data[i].trns_life = '0';
				}
				$('#t11_parts_life_det_tbl_id').datagrid('loadData', data);
			},
			error : function() {
				$.messager.alert('Processing', 'Error while Processing Ajax...',
				'error');
			}
		});
	}
}*/
/*
function incrementLaddleLifeDet() {
	var ladleNo = $('#t11_steel_ladle').combobox('getValue');
	if(ladleNo!=null && ladleNo!=''){
		$
		.ajax({
			headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json'
			},
			type : 'GET',
			// data: JSON.stringify(formData),
			dataType : "json",
			url : './EOFproduction/getLaddlePartsLifeDet?steelLadleNo='
				+ ladleNo,
				success : function(data) {
					for (var i = 0; i < data.length; i++) {
						data[i].trns_life = parseInt(data[i].trns_life + 1);
					}

					$('#t11_parts_life_det_tbl_id').datagrid('loadData', data);
					for (var i = 0; i < data.length; i++) {
						if (parseInt((data[i].max_life_value)
								- (data[i].trns_life)) <= 10) {
							$.messager
							.alert(
									'Alert',
									'This Ladle\'s Part <b>'
									+ data[i].part_name
									+ '</b> Life is about to Expire...',
							'info');
						}
					}
				},
				error : function() {
					$.messager.alert('Processing',
							'3.Error while Processing Ajax...', 'error');
				}
		});
	}
}*/

function totalWtValidation(callback) {
	var heatId = $('#t4_heat_id').textbox('getText');
	var heatcnt = document.getElementById('t4_heat_cnt').value;
	var sub_unit = $('#t4_unit').combobox('getValue');
	console.log(heatId+","+sub_unit+","+heatcnt);
	var tap_wt = $('#t11_tap_weight').textbox('getValue');
	var flag = false;

	$.ajax({
		headers : {
			'Accept' : 'application/json',
			'Content-Type' : 'application/json'
		},
		type : 'GET',
		// data: JSON.stringify(formData),
		dataType : "json",
		url : './heatProcessEvent/totalWtValidation?heat_id=' + heatId
		+ '&sub_unit_id=' + sub_unit + '&counter=' + heatcnt,
		success : function(data) {
			callback(data.comment);
		},
		error : function() {
			$.messager.alert('Processing', '4.Error while Checking Total Weight',
			'error');
		}
	});
	return flag;
}

/*$("#t11_steel_ladle").combobox({
	onSelect : function(record) {
		getLaddleLifeDet();
	},
	onChange : function() {
	}
});*/

/*
$("#t11_ladle_campaign_status").combobox({
	onSelect : function(record) {
		var sids = record.keyval;
		if (sids == 'YES') {
			restLaddleLifeDet();
		} else {
			incrementLaddleLifeDet();
		}
	}
});
*/
$("#t11_campaign_status").combobox({
	onSelect:function(record)
	{
		var sids=record.keyval;
		if(sids == 'YES'){		
			$('#t11_refractory_status').combobox('setValue','YES');
			$('#t11_hm_launder_status').combobox('setValue','YES');
			$('#t11_steel_launder_status').combobox('setValue','YES');
			$('#t11_tuyer_status').combobox('setValue','YES');
			$('#t11_tap_hole_status').combobox('setValue','YES');
		}else{
			$('#t11_refractory_status').combobox('setValue','NO');
			$('#t11_hm_launder_status').combobox('setValue','NO');
			$('#t11_steel_launder_status').combobox('setValue','NO');
			$('#t11_tuyer_status').combobox('setValue','NO');
			$('#t11_tap_hole_status').combobox('setValue','NO');
		}
	}
});

$("#t11_dispatch_to").combobox({
	onSelect : function(record) {
		var sids = record.txtvalue;
		if (sids == 'DRY PIT') {
			//Heat moving to dry pit user confirmation..
			$.messager.confirm('Information', 'Heat will be moved to DRY PIT and ABORTED!!', function(r){
				if (r==false){
					$("#t11_dispatch_to").combobox('clear');
					return false;
				}
			});
			$('#t11_send_btn_id').linkbutton('enable');
		} else {
			getT11ValidationDtlsGrid();
		}
	}
});
function validateT11EofDispatchForm() {
	return $('#t11_eof_dispatch_form_id').form('validate');
}
function saveT11EofDispatch(){
	if(validateT11EofDispatchForm())
	{
		var tap_wt = $('#t11_tap_weight').textbox('getText');
		var tap_temp = $('#t11_tap_temp').textbox('getValue');

		var campaign_status = $('#t11_campaign_status').combobox('getValue');
		var refractory_status = $('#t11_refractory_status').combobox('getValue');
		var hm_launder_status = $('#t11_hm_launder_status').combobox('getValue');
		var steel_launder_status =  $('#t11_steel_launder_status').combobox('getValue');
		var tuyer_status = $('#t11_tuyer_status').combobox('getValue');
		var tap_hole_status = $('#t11_tap_hole_status').combobox('getValue');
		var campaign_id = document.getElementById('t11_campaign_id').value;
		var eof_trns_sno =$('#t4_heat_id').combobox('getValue');
		var remarks = $('#t11_campaign_remarks').textbox('getValue');

		var steel_ladle_no = $('#t11_steel_ladle').combobox('getValue');

		var tap_temp = $('#t11_tap_temp').textbox('getValue');
		var dispatch_to_unit = $('#t11_dispatch_to').combobox('getValue');
		var dispatch_to_unit_name = $('#t11_dispatch_to').combobox('getText');
		var heatId = $('#t4_heat_id').textbox('getText');
		var heatcnt = document.getElementById('t4_heat_cnt').value;
		var sub_unit_name = $('#t4_unit').combobox('getText');
		var grid1_arry='';
		var ladleCampStatus = $('#t11_ladle_campaign_status').textbox('getText');
		var ladleCampRemarks = $('#t11_ladle_campaign_remarks').textbox('getText');

		/*var rows = $('#t11_parts_life_det_tbl_id').datagrid('getRows');	

		for(var i=0; i<rows.length; i++){
			grid1_arry += rows[i].ladle_life_sl_no+'@'+steel_ladle_no+'@'+rows[i].equipment_id+'@'+rows[i].part_id+'@'+rows[i].trns_life+'@'+'SIDS';
		}*/
		if (campaign_id == null || campaign_id == '') {
			campaign_id = 0;
		}	

		var formData = {						
				eofDispatchList: [{		
					"campaign_id" : campaign_id,	
					"campaign_remarks" : remarks,	
					"eof_trns_sno" : eof_trns_sno,	
					"campaign_status" : campaign_status,	
					"sub_unit_name" : sub_unit_name,	
					"lifeParameterName":"FURNACE LIFE"	
				},{	
					"campaign_id" : campaign_id,	
					"campaign_remarks" : remarks,	
					"eof_trns_sno" : eof_trns_sno,	
					"campaign_status" : hm_launder_status,	
					"sub_unit_name" : sub_unit_name,	
					"lifeParameterName":"HM LAUNDER LIFE"	
				},
				{	   				"campaign_id" : campaign_id,	
					"campaign_remarks" : remarks,	
					"eof_trns_sno" : eof_trns_sno,	
					"campaign_status" : refractory_status,	
					"sub_unit_name" : sub_unit_name,	
					"lifeParameterName":"WALL LIFE"	
				},	
				{	
					"campaign_id" : campaign_id,	
					"campaign_remarks" : remarks,	
					"eof_trns_sno" : eof_trns_sno,	
					"campaign_status" : tuyer_status,	
					"sub_unit_name" : sub_unit_name,	
					"lifeParameterName":"BOTTOM LIFE"	
				},	
				{	
					"campaign_id" : campaign_id,	
					"campaign_remarks" : remarks,	
					"eof_trns_sno" : eof_trns_sno,	
					"campaign_status" : tap_hole_status,	
					"sub_unit_name" : sub_unit_name,	
					"lifeParameterName":"EBT LIFE"	
				},
				{	
					"campaign_id" : campaign_id,	
					"campaign_remarks" : remarks,	
					"eof_trns_sno" : eof_trns_sno,	
					"campaign_status" : steel_launder_status,	
					"sub_unit_name" : sub_unit_name,	
					"lifeParameterName":"DELTA LIFE"	
				}],
				"eofHeatDetails" : {			
					"heat_id":heatId,"heat_counter":heatcnt,"sub_unit_name" : sub_unit_name,		
					"steel_ladle_no" : steel_ladle_no,"tap_wt" : tap_wt,		
					"tap_temp" : tap_temp,"dispatch_to_unit" : dispatch_to_unit,"dispatch_to_unit_name":dispatch_to_unit_name		
				},			
				"ladleLifeDetails" :{			
					"grid1_arry":grid1_arry,"ladleCampStatus":ladleCampStatus,"ladleCampRemarks":ladleCampRemarks,		
					"steel_ladle_no":steel_ladle_no		
				}    			
		};	
		var tapWeightMaxUrl = "./CommonPool/getComboList?col1=lookup_id&col2=lookup_value&classname=LookupMasterModel&status=1"
			+ "&wherecol=lookup_type='EOF_DISPATCH_MIN_WEIGHT' and lookup_status=";
		/*var tapWeightProcessMsg = "Getting the EOF Dispatch Tempurature range.";
		var tapWeightErrorMsg = "Error while Processing EOF Tap weight.";*/
		getResult(tapWeightMaxUrl, function(isRecordPresent, value){
			if (parseFloat(value[0].txtvalue) <= tap_wt) {
		$.ajax({
			headers: { 
				'Accept': 'application/json',
				'Content-Type': 'application/json' 
			},
			type: 'POST',
			data: JSON.stringify(formData),
			dataType: "json",
			url : './heatProcessEvent/eofDispatchSave',
			success: function(data) {
				if(data.status == 'SUCCESS') 
				{
					$.messager.alert('Eaf Dispatch Details Info',data.comment,'info');
					cancelT11EofDispatch();
					getEOFCampaignLife();
					clearT4HeatHdrform();
					GetT4UpdatedHeatList();
					//getLaddleLifeDet();
				}else {
					$.messager.alert('Eaf Dispatch Details Info',data.comment,'info');
				}
			},
			error:function(){      
				$.messager.alert('Processing','5.Error while Processing Ajax...','error');
			}
		});
			}
			else {
				$.messager.alert('Warning', 'Its does not satisfy the steel quantity tolerance');
				return false;
			}
		});
	}
}

function cancelT11EofDispatch() {
	$('#t11_eof_dispatch_win').window('close');
}

function getT11ValidationDtlsGrid() {
	var t4_trns_si_no3 = document.getElementById('t4_trns_si_no').value;
	$.ajax({
		headers : {
			'Accept' : 'application/json',
			'Content-Type' : 'application/json'
		},
		type : 'GET',
		// data: JSON.stringify(formData),heatid,heat_cntr,subunitid
		dataType : "json",
		url : "./EOFproduction/checkEOFValidations?eof_tranId="
			+ t4_trns_si_no3,
			success : function(data) {
				$('#t11_eof_dispatch_validation').datagrid('loadData', data);
				var vcount = 0;
				for (var i = 0; i < data.length; i++) {
					if (parseInt(data[i].validation_result) > 0) {
						vcount = vcount + 1;
					}
				}
				if (parseInt(data.length) == parseInt(vcount)) {
					$('#t11_send_btn_id').linkbutton('enable');
				} else {
					$('#t11_send_btn_id').linkbutton('disable');
				}
			},
			error : function() {
				$.messager.alert('Processing', 'Error while Processing Ajax...',
				'error');
			}
	});
}

function viewT11DispatchImg(value, row) {
	if (row.validation_result > 0) {
		return '<center><img alt="MES" src="./images/ok.png"></center>';
	} else {
		return '<center><img alt="MES" src="./images/cancel.png"></center>';
	}
}

function getT11Dropdowns(){
	var url1="./CommonPool/getComboList?col1=lookup_code&col2=lookup_value&classname=LookupMasterModel&status=1&wherecol=lookup_type='DISPATCH_STATUS' and lookup_status=";
	getDropdownList(url1,'#t11_campaign_status');
	getDropdownList(url1,'#t11_ladle_campaign_status');
	getDropdownList(url1,'#t11_tap_hole_status');
	getDropdownList(url1,'#t11_refractory_status');
	getDropdownList(url1,'#t11_hm_launder_status');
	getDropdownList(url1,'#t11_steel_launder_status');
	getDropdownList(url1,'#t11_tuyer_status');

	var psn_route = document.getElementById('t4_psn_route').value;
	var subQry;
	/*if(psn_route != null && psn_route != ""){
		if(psn_route.includes('VD'))
			subQry = " and ladle_type = 'VD' "
				else
					subQry = " and ladle_type <> 'VD' "
	}else{
		subQry = "";
	}*/

	var url2="./CommonPool/getComboList?col1=st_ladle_track_id&col2=steelLadleObj.steel_ladle_no&classname=SteelLadleTrackingModel&status=1&" +
	"wherecol=steelLadleObj.steel_ladle_si_no =st_ladle_si_no and ladleStatusObj.steel_ladle_status_id=ladle_status" +
	" and ladleStatusObj.steel_ladle_status='AVAILABLE' and recordStatus=";
	getDropdownList(url2,'#t11_steel_ladle');

	var url3="./CommonPool/getComboList?col1=lookup_id&col2=lookup_value&classname=LookupMasterModel&status=1&" +
	"wherecol=lookup_type='EOF_DISPATCH_TO' and lookup_status=";
	getDropdownList(url3,'#t11_dispatch_to');

	getT11ValidationDtlsGrid();
}

/** Dispatch Screen End * */

/** Process Parameter Screen Start * */
function T12ProcessParamOpen() {
	$('#t12_process_param_tbl_id').edatagrid({

	});
	assignRowChangeDetectionListener('t12_process_param_form_div_id','t12_process_param_tbl_id');
	closeActionBindEvt('t12_process_param_form_div_id');
	$('#t12_process_param_tbl_id')
	.datagrid(
			{
				onBeforeEdit : function(index, row) {

					row.process_date_time = formatDate(row.process_date_time);
					$('#t12_process_param_tbl_id').datagrid(
							'selectRow', index);
				},
				onEndEdit : function(index, row) {
					$('#t12_process_param_tbl_id').datagrid(
							'selectRow', index);
					var dt = (row.process_date_time).split(" ");

					var proc_date = new Date(commonGridDtfmt(dt[0],
							dt[1]));
					var time = proc_date.getTime();
					row.process_date_time = time;
				},
				onBeginEdit : function(index, row) {
					var editors = $('#t12_process_param_tbl_id')
					.datagrid('getEditors', index);
					var minval = row.param_value_min;
					var maxval = row.param_value_max;

					if ((minval == null || minval == '')
							&& (maxval == null || maxval == '')) {

						return false;
					} else {
						var actVal = $(editors[0].target);
						actVal
						.textbox({
							onChange : function() {
								$("#t12_process_param_tabs_div_id").attr("data-change","1");
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
										actVal.textbox(
												'setValue', '');
									}
								}
							}
						});
					}
				}
			});


	var subunitid = document.getElementById('t4_sub_unit_id').value;
	var t4_trns_si_no = document.getElementById('t4_trns_si_no').value;
	setEofElectrodeTime(t4_trns_si_no)

	$('#t12_electrode_usage').edatagrid({
		columns:[[
			{field:'electrodeId',title:'Electrode',width:100,hidden:false,
				formatter:function(v,r,i){return formatColumnData('electrodeLkpMstrModel.lookup_value',v,r,i)}},
				{field:'isAdded',title:'Changed',width:100,hidden:false,
					formatter:function(v,r,i){return formatColumnData('isAdded',v,r,i)},
					editor:{
						type:"combobox",
						options:{
							editable:true,valueField:"keyval",textField:"txtvalue",
							method:"get",
							url:"./CommonPool/getComboList?col1=lookup_value&col2=lookup_value&classname=LookupMasterModel&status=&wherecol= lookup_type='ACTIVE_STATUS' and lookup_status= 1"}
					}
				},
				{field:'isAdjusted',title:'Adjusted',width:100,hidden:false,
					formatter:function(v,r,i){return formatColumnData('isAdjusted',v,r,i)},
					editor:{
						type:"combobox",
						options:{
							editable:true,valueField:"keyval",textField:"txtvalue",
							method:"get",
							url:"./CommonPool/getComboList?col1=lookup_value&col2=lookup_value&classname=LookupMasterModel&status=&wherecol= lookup_type='ACTIVE_STATUS' and lookup_status= 1"}
					}
				},
				{field:'electrodeTransId',title:'electrodeTransId',width:0,hidden:true}
				]],
				updateUrl : './EOFproduction/EOFelectrodeSaveOrUpdate?sub_unit_id='+subunitid+" &trans_si_no="+t4_trns_si_no+"&redirect=1",
				onSuccess : function(index, row) {
					refreshEofElectrodeData();
				},

				onBeforeEdit:function(index,row){ 

				},onEndEdit:function(index,row){
					var starttime=$('#t12_e_start_time').textbox('getText')
					var endtime=$('#t12_e_end_time').textbox('getText')
					if(endtime!=null && endtime!='' &&starttime!=null&&starttime!='' ){
						$.ajax({
							headers: { 
								'Accept': 'application/json',
								'Content-Type': 'application/json' 
							},
							type: 'POST',
							dataType: "json",
							url: './EOFproduction/EOFelectrodeSaveOrUpdate?sub_unit_id='+subunitid+" &trans_si_no="+t4_trns_si_no+"&redirect=0&electrode_start_time="+starttime+"&electrode_end_time="+endtime,
							success: function(data) {
							},
							error:function(){      
								$.messager.alert('Processing','Error while Processing Ajax...','error');
							}
						});
					}else{
						$.messager.alert('Processing','Start & End time must be entered....','info');
					}
				},
				onAfterEdit:function(index,row){
				}
	});	
}

function setEofElectrodeTime(trans_si_no){
	$.ajax({
		headers: { 
			'Accept': 'application/json',
			'Content-Type': 'application/json' 
		},
		type: 'GET',
		// data: JSON.stringify(formData),heatid,heat_cntr,subunitid
		dataType: "json",
		url: './EOFproduction/getEOFHeatDetails?trns_si_no='+trans_si_no,
		success: function(data) {
			$('#t12_e_start_time').datetimebox('setText',formatDate(data.electrodeStartTime));
			$('#t12_e_end_time').datetimebox('setText',formatDate(data.electrodeEndTime));
		},
		error:function(){      
			$.messager.alert('Processing','Error while Processing Ajax...','error');
		}
	});
}

function refreshEofElectrodeData(){
	var sub_unit=$("#t4_unit").combobox("getValue");
	if(sub_unit!=null && sub_unit!=""){
		var t4_trns_si_no=document.getElementById('t4_trns_si_no').value;
		setEofElectrodeTime(t4_trns_si_no);
		$.ajax({
			headers: { 
				'Accept': 'application/json',
				'Content-Type': 'application/json' 
			},
			type: 'GET',
			// data: JSON.stringify(formData),heatid,heat_cntr,subunitid
			dataType: "json",
			url: './EOFproduction/getElectrodeTrans?sub_unit_id='+sub_unit +"&trans_si_no="+t4_trns_si_no,
			success: function(data) {
				$('#t12_electrode_usage').datagrid('loadData', data);     			
			},
			error:function(){      
				$.messager.alert('Processing','Error while Processing Ajax...','error');
			}
		});
	} 
}

function T12ProcessParam() {
	var t4_trns_si_no = document.getElementById('t4_trns_si_no').value;

	if (t4_trns_si_no != '') {
		$('#t12_process_param_form_div_id').dialog({
			modal : true,
			cache : true
		});
		$('#t12_process_param_form_div_id').dialog('open').dialog('center')
		.dialog('setTitle', 'Process Parameters Details Form');

		var heatId = $('#t4_heat_id').textbox('getText');
		var heatcnt = document.getElementById('t4_heat_cnt').value;
		var aimpsn = $('#t4_aim_psn').textbox('getText');

		T12ProcessParamOpen();
		$('#t12_heat_no').textbox('setText', heatId);
		$('#t12_aim_psn').textbox('setText', aimpsn);
		$('#t12_e_heat_no').textbox('setText',heatId);
		$('#t12_e_aim_psn').textbox('setText',aimpsn);
		getT12ProcParamDtlsGrid();

	} else {
		$.messager.alert('Information', 'Please Select Heat...!', 'info');
	}
}// end


function getT12ProcParamDtlsGrid() {
	var heatid = $('#t4_heat_id').combobox('getText');
	var heat_cntr = document.getElementById('t4_heat_cnt').value;
	var subunitid = document.getElementById('t4_sub_unit_id').value;
	var psn_no = document.getElementById('t4_aim_psn').value;

	if (psn_no != null && psn_no != '') {
		$.ajax({
			headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json'
			},
			type : 'GET',
			// data: JSON.stringify(formData),heatid,heat_cntr,subunitid
			dataType : "json",
			url : './heatProcessEvent/getHeatHeatProcParamDtls?heatid='
				+ heatid + '&heat_cntr=' + heat_cntr + '&subunitid='
				+ subunitid + '&psn_no=' + psn_no,
				success : function(data) {
					$('#t12_process_param_tbl_id').datagrid('loadData', data);

				},
				error : function() {
					$.messager.alert('Processing',
							'Error while Processing Ajax...', 'error');
				}
		});
	}
	refreshEofElectrodeData();
}

function cancelT12ProcParamDtls() {
	getT12ProcParamDtlsGrid();
}
function saveT12ProcessParamDtls() {
	var heatid = $('#t4_heat_id').combobox('getText');
	var heat_cntr = document.getElementById('t4_heat_cnt').value;
	var rows = $('#t12_process_param_tbl_id').datagrid('getRows');

	for (var i = 0; i < rows.length; i++) {
		$('#t12_process_param_tbl_id').datagrid('endEdit', i);
	}
	var proc_param_rows = $('#t12_process_param_tbl_id').datagrid('getRows');
	var grid_arry = '';

	for (var i = 0; i < proc_param_rows.length; i++) {
		if ((proc_param_rows[i].param_value_actual != null && proc_param_rows[i].param_value_actual != '')) {
			var proc_date = formatDate(proc_param_rows[i].process_date_time);
			//alert('Event ID'+i+': '+proc_param_rows[i].proc_param_event_id );
			grid_arry += proc_param_rows[i].param_id + '@'
			+ proc_param_rows[i].param_value_actual + '@' + proc_date
			+ '@' + proc_param_rows[i].proc_param_event_id + 'SIDS';
		}
	}
	formData = {
			"grid_arry" : grid_arry,
			"heat_id" : heatid,
			"heat_counter" : heat_cntr
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
		url : './heatProcessEvent/procParamSaveOrUpdate',
		success : function(data) {
			if (data.status == 'SUCCESS') {
				$.messager.alert('Process Parameters Details Info',
						data.comment, 'info');
				cancelT12ProcParamDtls();
			} else {
				$.messager.alert('Process Parameters Details Info',
						data.status, 'info');
			}
		},
		error : function() {
			$.messager.alert('Processing', 'Error while Processing Ajax...',
			'error');
		}
	});

}

function generateHeatNo(vunit, retMix) {
	$.ajax({
		headers : {
			'Accept' : 'application/json',
			'Content-Type' : 'application/json'
		},
		type : 'GET',
		dataType : "json",
		url : "./EOFproduction/getNewHeatNo?sub_unit=" + vunit,
		success : function(data) {
			$('#t4_heat_id').combobox('setValue', 0);
			$('#t4_heat_id').combobox('setText', data.heat_id);
			doDisableEnableButtons('t4_heat_hdr_btn_div_id','disable');
			//return 1;
		},
		error : function() {
			$.messager.alert('Processing', 'Error while retrieving HeatSeq...',
			'error');
		}
	});
}

function generateBilletId(heatNo, strand, bltseq) {
	var retVal = "";

	if (heatNo == "") {
		$.messager.alert('Information', 'HeatNo Can Not Be Blank...!', 'info');
	} else if (strand == "") {
		$.messager.alert('Information', 'Strand Can Not Be Blank...!', 'info');
	} else if (bltseq == "") {
		$.messager.alert('Information', 'BilletSeq Can Not Be Blank...!',
		'info');
	} else {
		retVal = heatNo + strand + bltseq;
	}

	return retVal;
}

function Padder(len, pad) {
	if (len == undefined) {
		len = 1;
	} else if (pad == undefined) {
		//alert('pad: ' + pad);
		pad = '0';
	}
	var pads = '';
	while (pads.length < len) {
		pads += pad;
	}
	this.pad = function(pdnm) {
		var s = pdnm.toString();
		return pads.substring(0, pads.length - s.length) + s;
	};
}


function GetSample(){
	$('#t7_sample_date_time').datetimebox({
		value : (formatDate(new Date()))
	});
	$('#t7_sample_temp').numberbox('setValue', '');
	setDefaultCustomComboValues('t7_sample_result',
			'OK', $('#t7_sample_result').combobox(
			'getData'));
	$('#t7_remarks').textbox('setText', '');
	document.getElementById('t7_sample_si_no').value = '0';
	var rows = $('#t7_eof_Chemistry_tbl_id').datagrid('getRows');

	for (var i = 0; i < rows.length; i++) {
		if (rows[i].dtls_si_no != null
				&& rows[i].dtls_si_no != '') {
			getT7ChemistryDtlsGrid();
			break;
		}
	}

	GenerateSampleNo();
}

function GenerateSampleNo() {
	var heatId = String($('#t4_heat_id').textbox('getText'));
	var heatcnt = document.getElementById('t4_heat_cnt').value;
	var sub_unit = document.getElementById('t4_sub_unit_id').value;
	var analysis_id = $('#t7_analysis_type').combobox('getValue');

	var flag = false;
	var v_url = './heatProcessEvent/getSampleNo?heat_id='+heatId+'&heat_counter='+heatcnt
	+'&sub_unit_id=' + sub_unit +  '&analysis_type='+ analysis_id;

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
			$('#t7_sample_no').textbox('setText',data.comment);
			$("#t7_sample_no").combobox('disable');	
		},
		error : function() {
			$.messager.alert('Processing', 'Error while getting Sample No',
			'error');
		}
	});
}

function T13EOFByProducts(){
	var t4_trns_si_no = document.getElementById('t4_trns_si_no').value;

	if (t4_trns_si_no != '') {
		$('#t17_eof_byProducts_form_div_id').dialog({
			modal : true,
			cache : true
		});
		$('#t17_eof_byProducts_form_div_id').dialog('open').dialog('center').dialog(
				'setTitle', 'EAF By Products');

		var heatId = $('#t4_heat_id').textbox('getText');
		var heatcnt = document.getElementById('t4_heat_cnt').value;
		var aimpsn = $('#t4_aim_psn').textbox('getText');
		T17ByProductsDtls();
		$('#t17_heat_no').textbox('setText', heatId);
		$('#t17_aim_psn').textbox('setText', aimpsn);
		getT17ByProductsDtlsGrid();

	} else {
		$.messager.alert('Information', 'Please Select Heat...!', 'info');
	}
}
function T17ByProductsDtls() {
	$('#t17_eof_byProducts_tbl_id')
	.edatagrid(
			{
				onBeforeEdit : function(index, row) {
					row.consumption_date = formatDate(row.consumption_date);
					$('#t17_eof_byProducts_tbl_id').datagrid(
							'selectRow', index);
					var heatId = $('#t4_heat_id').textbox('getText');
					var heatcnt = document.getElementById('t4_heat_cnt').value;
					var aimpsn = $('#t4_aim_psn').textbox('getText');
					var t4_trns_si_no = document.getElementById('t4_trns_si_no').value;
					if(row.mtrlName=='Eaf Slag'){
					}
				},
				onEndEdit : function(index, row) {
					$('#t17_eof_byProducts_tbl_id').datagrid(
							'selectRow', index);
					var dt = (row.consumption_date).split(" ");
					var cons_time = new Date(commonGridDtfmt(dt[0],
							dt[1]));
					var time = cons_time.getTime();

					row.consumption_date = time;
				}							
			});
}
function getT17ByProductsDtlsGrid() {
	var eof_trns_sno = document.getElementById('t4_trns_si_no').value;
	$.ajax({
		headers : {
			'Accept' : 'application/json',
			'Content-Type' : 'application/json'
		},
		type : 'GET',
		// data: JSON.stringify(formData),
		dataType : "json",
		url : "./heatProcessEvent/getMtrlDetailsByType?mtrlType=EAF_BY_PRODUCT"
			+ "&eof_trns_sno=" + eof_trns_sno+"&psn_no=NA",
			success : function(data) {

				$.ajax({
					headers : {
						'Accept' : 'application/json',
						'Content-Type' : 'application/json'
					},
					type : 'GET',
					// data: JSON.stringify(formData),
					dataType : "json",
					url : "./EOFproduction/getLiquidSteelValue?trans_si_no="+eof_trns_sno,

					success : function(res) {
						var hmWt=$("#t4_hm_wt").numberbox('getValue');
						for(var i=0;i<data.length;i++){
							if(data[i].mtrlName=='Eaf Slag'){
								data[i].qty=res.SLAG;
							}else if(data[i].mtrlName=='Eaf Sludge'){
								data[i].qty=res.SLUDGE;
							}
						}
						$('#t17_eof_byProducts_tbl_id').datagrid('loadData',
								data);
					},
					error : function() {
						$.messager.alert('Processing',
								'Error while Processing Ajax...', 'error');
					}
				});					
			},
			error : function() {
				$.messager.alert('Processing',
						'Error while Processing Ajax...', 'error');
			}
	});
}

function saveT17ByProductsDtls() {
	var eof_trns_sno = document.getElementById('t4_trns_si_no').value;
	var sub_unit = document.getElementById('t4_sub_unit_id').value;
	var eventname = 'EAF_BY_PRODUCTS';

	var rows = $('#t17_eof_byProducts_tbl_id').datagrid('getRows');

	for (var i = 0; i < rows.length; i++) {
		$('#t17_eof_byProducts_tbl_id').datagrid('endEdit', i);
	}

	var mtr_cons_si_id = 0;
	var grid_arry = '';
	for (var i = 0; i < rows.length; i++) {
		$('#t17_eof_byProducts_tbl_id').datagrid('endEdit', i);

		if ((rows[i].qty != null && rows[i].qty != '')
				&& (rows[i].consumption_date != null && rows[i].consumption_date != '')) {
			var consdate = formatDate(rows[i].consumption_date);
			grid_arry += rows[i].material_id + '@' + rows[i].qty
			+ '@' + consdate + '@' + rows[i].mtr_cons_si_no + '@'
			+ rows[i].version+  'SIDS';
		}
	}
	formData = {
			"grid_arry" : grid_arry,
			"trns_eof_si_no" : eof_trns_sno
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
		url : './heatProcessEvent/SaveOrUpdate?sub_unit=' + sub_unit
		+ '&eventname=' + eventname,
		success : function(data) {
			if (data.status == 'SUCCESS') {
				$.messager.alert('Ladle Additions Details Info', data.comment,
				'info');
				cancelT17ByProductsDtls();
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
function cancelT17ByProductsDtls() {
	getT17ByProductsDtlsGrid();
}

function getTareWeightDetails(){//interface
	var hmRecid=document.getElementById('t4_heat_receieve_id').value;
	//$("#t8_tare_weight").textbox("setValue","")
	$.ajax({
		headers: { 
			'Accept': 'application/json',
			'Content-Type': 'application/json' 
		},
		type: 'GET',
		//data: JSON.stringify(formData),
		dataType: "json",
		url: './HMReceive/getHMDetailsbyId?recvid='+hmRecid,
		success: function(data) {		
			var laddleNo =data.hmLadleNo;
			var castNo=data.castNo;
			var unit=data.lookupHmSource.lookup_value;

			weighRecvDirectory(laddleNo,castNo,unit,'TARE_WEIGHT',function(data){
				//$("#t8_tare_weight").textbox("setValue",data.weight)
			})
		},
		error:function(){     
			$.messager.alert('Processing', 'Error while Processing Ajax...',
			'error');
		}
	})
}

function weighRecvDirectory(laddleNo,castNo,unit,wb_type,callback){
	if(castNo!='' &&castNo!=''&&unit!='' ){
		$.ajax({
			headers: { 
				'Accept': 'application/json',
				'Content-Type': 'application/json' 
			},
			type: 'GET',
			//data: JSON.stringify(formData),
			dataType: "json",
			url: './WBIntf/getWBweight?laddleNo='+laddleNo+'&castNo='+castNo+'&weighmentType='+wb_type+'&unit='+unit,
			success: function(data) {
				callback(data)
			},
			error:function(){             			
				$.messager.alert('Processing','Data not available for selected criteria','warning');
			}
		})
	}else{
		$.messager.alert('Processing','Select Required Fields','warning');
	}

}

//-----CONTAINER LIFE EOF FUNCTIONS ENDS----------
//Container life change EOF
function T12ContainerLife(){
	$('#t17_eof_container_change').dialog('open').dialog('center')
	.dialog('setTitle', 'Shell Change');
	$('#t12_mstr_eof_container_life_tbl_id').datagrid(
	'reload');
}

function T12ContainerChange(){
	var url1 = "./CommonPool/getComboList?col1=lookup_code&col2=lookup_value&classname=LookupMasterModel&status=1&wherecol=lookup_type='CONTAINER_STATUS' and lookup_status=";
	getDropdownList(url1, '#t12_status');

	var URL2 = "./CommonPool/getComboList?col1=sub_unit_id&col2=sub_unit_name&classname=SubUnitMasterModel&status=1&wherecol=unitDetailsMstrMdl.unit_name LIKE 'EAF%25' AND record_status=";
	getDropdownList(URL2, '#t12_unit');

	var url3 = "./CommonPool/getComboList?col1=lookup_code&col2=lookup_value&classname=LookupMasterModel&status=1&wherecol=lookup_type='CONTAINER_SUPPLIER_NAME' and lookup_status=";
	getDropdownList(url3, '#t12_supplier_id');

	var url4 = "./CommonPool/getComboList?col1=lookup_code&col2=lookup_value&classname=LookupMasterModel&status=1&wherecol=lookup_type='CONTAINER_STATUS' and lookup_value NOT IN ('RUNNING') and lookup_status=";
	//getDropdownList(url4, '#t12_running_container_status');

	var row = $('#t12_mstr_eof_container_life_tbl_id').datagrid(
	'getSelected');
	if (row.status!="RUNNING") {
		$('#t12_mstr_eof_container_life_form_div_id').form('clear');
		$('#t12_mstr_eof_container_life_form_div_id').dialog({
			modal : true,
			cache : true
		});
		$('#t12_mstr_eof_container_life_form_div_id').dialog('open')
		.dialog('center').dialog('setTitle',
		'Edit Shell Master Details');
		$('#t12_mstr_eof_container_life_form_div_id').form('load', row);
		$('#t12_container_name').textbox('setText', row.container_name);
		$('#t12_container_name').textbox('readonly', true);
		$('#t12_supplier_id').combobox('setText', row.supplier_id);
		$('#t12_supplier_id').textbox('readonly', true);
		$('#t12_container_life').numberbox('setValue', row.container_life);
		$('#t12_container_life').numberbox('readonly', true);
		$('#t12_unit').combobox('setValue', row.unit);
		$('#t12_unit').textbox('readonly', true);
		$('#t12_status').textbox('readonly', false);
		$('#running_container_details').hide();
		$('#t12_status').combobox({
			onChange: function (value) {
				if(value=="RUNNING"){
					$.ajax({
						headers : {
							'Accept' : 'application/json',
							'Content-Type' : 'application/json'
						},
						type : 'POST',
						//data : JSON.stringify(formData),
						dataType : "json",
						url : './MstrContainerLife/getCurrentContainer?Unit='+row.unit,
						success : function(data) {
							if(data.length==0){
								$('#running_container_details').hide();
								$('#t12_current_container_id').val(null);
							}
							else{
								$('#running_container_details').hide();
								$('#t12_current_container_id').val(data[0].container_id);
								$('#t12_running_container_name').textbox('setText',data[0].container_name);
								$('#t12_running_container_name').textbox('readonly',true);
								$('#t12_running_container_status').combobox('setValue',"UNINSTALL");
								$('#t12_running_supplier_id').combobox('setValue',data[0].supplier_id);
								$('#t12_running_supplier_id').combobox('readonly',true);
								$('#t12_running_container_life').numberbox('setValue',data[0].container_life);
								$('#t12_running_container_life').numberbox('readonly',true);
								$('#t12_running_unit').textbox('setValue',data[0].status);
								$('#t12_running_unit').combobox('setValue',data[0].unit);
								$('#t12_running_unit').combobox('readonly',true);
							}   					
						},
						error : function() {
							$.messager.alert('Processing',
									'Error while Processing Ajax...', 'error');
						}
					})
				}
			}
		});
	} else {
		$.messager.alert('Information', 'Please Select Record Other Than Running Status', 'info');
	}
	//-----CONTAINER LIFE EOF FUNCTIONS ENDS----------
}