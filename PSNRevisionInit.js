/**
 * PSN Revision Initiation operations details
 */

function formatRevisedValue(val, row) {
	if (val != null) {
		return '<span style="color:red;font-weight:bold">' + val + '</span>';
	} else {
		return '<span >-</span>';
	}

}
function formatEmptyCurrentValue(val, row) {
	if (val == 0 || val == null) {
		return '<span >-</span>';
	} else {
		return val
	}

}

var accordion = $("#psnRevisionIntiatorDiv");
PSNChemInit();
PSNChemCRUD();

$("#m32PsnPsnCategory").combobox({
	onSelect : function(record) {
		$('#m32psn_rev_no_display').textbox("setText", "");
		$('#m32psn_rev_no_display_prod1').textbox("setText", "");
		$('#m32addPsnRevision').linkbutton('enable');
		clearChemProductCrudTables();
		gotoHome();
		callM32Dropdown(record.text)
		$("#m32_psn_status_display").html("<b></b>")
		if (record.text != "APPROVED") {
			$('#m32addPsnRevision').linkbutton('disable')
			// $("#m32_psn_status_display").html("RETURNED")
			$(".returnedReview").css('display', "block");
		} else {
			$(".returnedReview").css('display', "none");
			// $("#m32_psn_status_display").html("UNDER REVISION")
		}
		clearReviewTablesView();
		$("#m32PsnRevisionSummary").textbox("setText", "");

	}
});
$("#m32_psn_cancel").click(function() {
	clearChemProductCrudTables();
	clearReviewTablesView();
	gotoHome();
	clearallComboBoxes();
	$("#m32PsnRevisionSummary").textbox("setText", "");
});
$("#m32_psn_send_review_btn")
		.click(
				function() {
					var psn_hdr_sl_no = document
							.getElementById('m32_psn_hdr_sl_no').value;
					if ($("#m32PsnPsnCategory").combobox('getText') == "RETURNED") {
						psn_hdr_sl_no = $("#m32PsnRevNo").combobox('getValue');
					} else {
						psn_hdr_sl_no = $("#m32PsnRevNo").combobox('getValue');
					}
					var products_rows = $('#m32_PSN_rev_rProduct_tbl_id')
							.datagrid('getRows');
					var chem_rows = $('#m32_PSN_rev_Chemistry_tbl_id')
							.datagrid('getRows');
					if (products_rows.length > 0 || chem_rows.length > 0) {
						var summary = $("#m32PsnRevisionSummary").textbox(
								"getText");
						$.ajax({
							headers : {
								'Accept' : 'application/json',
								'Content-Type' : 'application/json'
							},
							type : 'POST',
							// data: JSON.stringify(formData),
							dataType : "json",
							url : './psnHdrMstr/psnSendToReview?psn_hdr_sl_no='
									+ psn_hdr_sl_no
									+ "&remarks="
									+ $("#m32PsnRevisionInitiatorRemarks")
											.textbox('getText')
									+ "&revision_summary=" + summary,
							success : function(data) {
								// alert("Revision Details Sent for Approval");
								$.messager.alert('Revision Details.',
										'Revision Details Sent for Review..',
										'info');
								clearChemProductCrudTables();
								clearReviewTablesView();
								$("#m32PsnRevisionSummary").textbox("setText",
										"");
								gotoHome();
								clearallComboBoxes();
							},
							error : function() {
								$.messager.alert('Processing',
										'Error while Processing Ajax...',
										'error');
							}
						})
					} else {
						$.messager.alert('Not saved',
								'No Changes Present in this revision...',
								'info');
						// alert("No Changes Present in this revision...");
					}

				});

function clearallComboBoxes() {
	$("#m32PsnRevisionSummary").textbox("setText", "")
	$('#m32PsnNo').combobox('clear');
	$('#m32PsnRevNo').combobox('clear');
	$('#m32_chem_level').combobox('clear');
	$("#m32PsnPsnCategory").combobox('clear');
	$("#m32psn_no_chem").textbox("setText", "")
	$("#m32psn_rev_no_display").textbox("setText", "")
	$("#m32psn_no_prod1").textbox("setText", "")
	$("#m32psn_rev_no_display_prod1").textbox("setText", "")
	$('#psnRevisionIntiatorDiv').accordion('unselect', 'PSN Revision')
}

$("#m32PsnNo").combobox({
	onSelect : function(record) {
		// console.log(record)
		$("#m32_psn_status_display").html("<b></b>")
		$('#m32psn_rev_no_display').textbox("setText", "");
		$('#m32psn_rev_no_display_prod1').textbox("setText", "");
		gotoHome();
		clearChemProductCrudTables();

		$("#m32PsnRevNo").combobox('loadData', []);

		if ($("#m32PsnPsnCategory").combobox('getText') != "RETURNED") {
			$("#m32_psn_hdr_sl_no").val(record.keyval);
		} else {
			setPsnHdrSlNowrtReturnedPsn(record.keyval)
		}
		$("#m32psn_no_chem").textbox('setText', record.txtvalue);
		// $("#m32psn_no_prod").textbox('setText', record.txtvalue);
		$("#m32psn_no_prod1").textbox('setText', record.txtvalue);
		// console.log("********");
		// console.log(record);
		callM32PsnRevDropdown(record.keyval);
		clearReviewTablesView();
		setPsnStatusDisplay();
		$("#m32PsnRevisionSummary").textbox("setText", "");

	}
});

function setPsnStatusDisplay() {
	if ($("#m32PsnPsnCategory").combobox('getText') == "APPROVED"
			&& $("#m32PsnRevNo").combobox("getText") == "") {
		$("#m32_psn_status_display").html("Status : APPROVED")
	} else if ($("#m32PsnPsnCategory").combobox('getText') != "RETURNED") {
		$("#m32_psn_status_display").html("Status : UNDER REVISION")
	} else {
		$("#m32_psn_status_display").html("Status : RETURNED")

	}
}
$("#m32PsnRevNo").combobox({
	onSelect : function(record) {
		$("#m32psn_no_chem").textbox('setText', record.txtvalue);
		$("#m32psn_no_prod1").textbox('setText', record.txtvalue);

		loadChemProductDetailsForEdit(record.txtvalue, record.keyval);
		$('#psnRevisionIntiatorDiv').accordion('select', 'PSN Revision')
	}
});

function loadChemProductDetailsForEdit(txt, key) {
	gotoHome();
	clearChemProductCrudTables();
	$('#m32psn_rev_no_display').textbox("setText", txt);
	$('#m32psn_rev_no_display_prod1').textbox("setText", txt);
	$('#m32psn_no_chem').textbox('setText', $("#m32PsnNo").combobox("getText"));
	// alert($("#m32PsnNo").combobox("getText"))
	$('#m32psn_no_prod1')
			.textbox('setText', $("#m32PsnNo").combobox("getText"));
	// alert($('#m32psn_rev_no_display').textbox('getText'))
	initPsnRelatedFun();
	refreshM32PSNChemistry()
	refreshM32PSNProduct()
	clearReviewTablesView()
	getPSNRevisionInfo(key)
	console.log("TEST : " + $("#m32PsnRevNo").combobox("getText"));
	//		
	// if( $("#m32PsnPsnCategory").combobox('getText')=="APPROVED" && ){
	//			
	// }
	//	 
	if ($("#m32PsnPsnCategory").combobox('getText') != "RETURNED") {
		$("#m32_psn_status_display").html("Status : UNDER REVISION")
	} else {
		$("#m32_psn_status_display").html("Status : RETURNED")

	}

	refreshReviewPane()

	$('#m32_pen_rev_tabs').tabs({
		border : false,
		onSelect : function(title) {
			var tab = $('#m32_pen_rev_tabs').tabs('getSelected');
			var index = $('#m32_pen_rev_tabs').tabs('getTabIndex', tab);
			// alert(index);
			if (index == 0) {
				refreshReviewPane()
			} else if (index == 1) {
				refreshReviewPane()
			} else if (index == 2) {
				refreshReviewPane()
			}
		}
	});

	var data;
	returnPsnDetailsByPsnHdrSlNo($("#m32PsnRevNo").combobox("getValue"),
			function(data) {
				data = data;
				// console.log("Revision PSN Entry")
				// console.log(data);
				// console.log(data.summary_of_revision)
				// if(){
				$("#m32PsnRevisionSummary").textbox("setText",
						data.summary_of_revision);
				// }
			});

}
function refreshReviewPane() {
	var revised_psn_hdr_sl_no = $('#m32PsnRevNo').combobox('getValue');
	if (revised_psn_hdr_sl_no != null && revised_psn_hdr_sl_no != '') {
		loadRevisedChemDetailsPreview();
		loadRevisedProductsDetailsPreview();
	} else {
		$.messager.alert('Revision No.', 'Revision Number Required', 'info');
		// alert("Create Revision to Proceed.");
	}
}

function setPsnHdrSlNowrtReturnedPsn(returned_psn_sl_no) {
	// alert("RETURNED PSN Loading...");
	$.ajax({
		headers : {
			'Accept' : 'application/json',
			'Content-Type' : 'application/json'
		},
		type : 'GET',
		// data: JSON.stringify(formData),
		dataType : "json",
		url : './psnHdrMstr/getPSNDetailsBySlNo?psn_hdr_sl_no='
				+ returned_psn_sl_no,
		success : function(data) {
			// console.log(data);
			$("#m32_psn_hdr_sl_no").val(data.psn_no_before_rev);
			$("#m32PsnRevisionInitiatorRemarks").textbox("setText",
					data.remarks);
			$("#m32PsnRevisionInitiatorReviewerRemarks").textbox("setText",
					data.review_remarks);
			$("#m32PsnRevisionInitiatorApproverRemarks").textbox("setText",
					data.approver_remarks);

		},
		error : function() {
			$.messager.alert('Processing', 'Error while Processing Ajax...',
					'error');
		}
	})
}

function clearChemProductCrudTables() {

	$('#m32_PSN_Chemistry_tbl_id').edatagrid('loadData', []);
	$('#m32_PSN_Product_tbl_id').edatagrid('loadData', []);

}

function clearReviewTablesView() {

	$('#m32_PSN_rev_rProduct_tbl_id,#m32_PSN_rev_Chemistry_tbl_id').datagrid(
			'loadData', []);

}

function getPSNRevisionInfo(psn_hdr_sl_no) {

	$.ajax({
		headers : {
			'Accept' : 'application/json',
			'Content-Type' : 'application/json'
		},
		type : 'GET',
		// data: JSON.stringify(formData),
		dataType : "json",
		url : './psnHdrMstr/getPSNRevisionDetails?psn_hdr_sl_no='
				+ psn_hdr_sl_no,
		success : function(data) {

			// console.log(data);

		},
		error : function() {
			$.messager.alert('Processing', 'Error while Processing Ajax...',
					'error');
		}
	})
}

$('#m32PsnNo').combobox({
	filter : function(q, row) {
		var opts = $(this).combobox('options');
		return row[opts.textField].toLowerCase().indexOf(q.toLowerCase()) >= 0;
	}
});

function initPsnRelatedFun() {

	PSNProductInit();
	PSNProductCRUD();

}
function callM32Dropdown(status) {
	$('#m32PsnNo').combobox('clear');
	$('#m32PsnRevNo').combobox('clear');
	$('#m32_chem_level').combobox('clear');
	if (status == "APPROVED") {
		calApprovedPsnLoadingComboAction();
	} else {
		var url1 = "./CommonPool/getComboList?col1=psn_hdr_sl_no&col2=psn_no&classname=PSNHdrMasterModel&status=1&wherecol=psn_status ='"
				+ status + "' and record_status=";
		getDropdownList(url1, '#m32PsnNo');
	}
}

function calApprovedPsnLoadingComboAction() {
	$.ajax({
		headers : {
			'Accept' : 'application/json',
			'Content-Type' : 'application/json'
		},
		type : 'GET',
		// data: JSON.stringify(formData),
		dataType : "json",
		url : './psnHdrMstr/getApprovedPsnForRevision',

		success : function(data) {
			// console.log(data);
			var dataReceived = new Array();
			// console.log(data.length);
			for (var i = 0; i < data.length; i++) {
				// console.log(data[i])

				if (typeof (data[i]) != "undefined") {
					var temp = {
						"keyval" : data[i][0],
						"txtvalue" : data[i][1]
					}
					dataReceived.push(temp);
				}
			}
			$("#m32PsnNo").combobox('loadData', dataReceived)

		},
		error : function() {
			$.messager.alert('Processing', 'Error while Processing Ajax...',
					'error');
		}
	});
}

function callM32PsnRevDropdown(psn_hrd_sl_no) {
	$('#m32PsnRevNo').combobox('clear');
	var url1;
	// alert(psn_hrd_sl_no);
	if ($('#m32PsnPsnCategory').combobox('getValue') != "RETURNED") {

		url1 = "./CommonPool/getComboList?col1=psn_hdr_sl_no&col2=psn_revn_no&classname=PSNHdrMasterModel&status=1&wherecol=psn_no_before_rev='"
				+ psn_hrd_sl_no
				+ "' and psn_status='UNDER_REVISION' and record_status=";
	} else {
		url1 = "./CommonPool/getComboList?col1=psn_hdr_sl_no&col2=psn_revn_no&classname=PSNHdrMasterModel&status=1&wherecol=psn_hdr_sl_no='"
				+ psn_hrd_sl_no + "' and record_status=";
	}
	getDropdownList(url1, '#m32PsnRevNo');

	$("#m32PsnRevNo").combobox(
			{
				onLoadSuccess : function() {
					var items = $("#m32PsnRevNo").combobox('getData')
					if (items.length > 0) {
						$(this).combobox('select', items[0].keyval);
						loadChemProductDetailsForEdit($(this).combobox(
								'getText'), $(this).combobox('getValue'));
						$('#m32addPsnRevision').linkbutton('disable')

					} else {
						$('#m32addPsnRevision').linkbutton('enable')
					}
				},
				editable : false

			});
}

function PSNChemInit() {
	callM11Dropdown();
}

function callM11Dropdown() {
	var url1 = "./CommonPool/getComboList?col1=lookup_id&col2=lookup_value&classname=LookupMasterModel&status=1&wherecol=lookup_type='CHEM_LEVEL' and lookup_status=";
	getDropdownList(url1, '#m32_chem_level');
}
var minmax_flag = false;

function PSNChemCRUD() {
	// alert($("#m32psn_no_chem").val());

	var expamle;
	$('#m32_PSN_Chemistry_tbl_id')
			.edatagrid(
					{
						onBeforeEdit : function(index, row) {
							// console.log("****Edit*****");
							// console.log(row);
							var dg = $('#m32_PSN_Chemistry_tbl_id');
							var col = dg.edatagrid('getColumnOption',
									'element_id');
							var col2 = dg.edatagrid('getColumnOption', 'uom');
							var chemlevel = $('#m32_chem_level').combobox(
									'getValue');

							if (chemlevel = 0 || chemlevel == null
									|| chemlevel == '') {
								$.messager
										.alert(
												'PSN Details Info',
												'Please Select Chemistry Level',
												'info');
								// refreshM17PSNQACharVal();
							}

							// record_status default 1
							if (row.record_status == null
									|| row.record_status == '') {
								if (row.record_status != 0) {
									row.record_status = row.record_status || 1;
								}
							}
							// console.log("New Row");
							// console.log(row);

							// col2.editor1 = col2.editor;
							// col2.editor = null;
							if (row.isNewRecord) {
								if (col.editor == null) {
									var avail_params;
									getAvailedM32PSNChemistryValues(
											avail_params,
											function(data) {
												avail_params = data;
												var param_query = "";
												var temp = '';
												if (avail_params != '') {
													temp = 'and lookup_id NOT IN ('
															+ avail_params
															+ ')';
												}
												var edit = {
													type : 'combobox',
													options : {
														required : true,
														editable : true,
														valueField : 'keyval',
														textField : 'txtvalue',
														method : 'get',
														url : './CommonPool/getComboList?col1=lookup_id&col2=lookup_value&classname=LookupMasterModel&status=1&wherecol= lookup_type=\'ELEMENT\' '
																+ temp
																+ ' and lookup_status='

													}
												}
												col.editor = edit;
											})
								}

								if (col2.editor == null) {
									col2.editor = {
										type : 'combobox',
										options : {
											required : true,
											editable : true,
											valueField : 'keyval',
											textField : 'txtvalue',
											method : 'get',
											url : './CommonPool/getComboList?col1=lookup_id&col2=lookup_value&classname=LookupMasterModel&status=1&wherecol= lookup_type=\'UOM\' and lookup_status='
										}
									};
								}

							} else {
								// expamle=col.editor;
								col.editor1 = col.editor;
								col.editor = null;
								col2.editor1 = col2.editor;
								col2.editor = null;

							}
						},
						onBeginEdit : function(index, row) {

							var editors = $('#m32_PSN_Chemistry_tbl_id')
									.datagrid('getEditors', index);
							var value_min = row.value_min;
							var value_max = row.value_max;
							var value_aim = row.value_aim;
							// alert(value_min +":"+value_max);
							if ((value_min == null || value_min == '')
									&& (value_max == null || value_max == '')) {
								return false;
							} else {								
								var minactVal = $(editors[2].target);// min field
								var maxactVal = $(editors[3].target);// max field
								var actVal = $(editors[6].target);// aim field
								var firstEnabler;
								var secondEnabler;
								var thirdEnabler;
								for (var i = 0; i < editors.length; i++) {
									if (editors[i]['field'] == 'value_aim') {
										actVal = $(editors[i].target);
										firstEnabler = editors[i]['oldHtml']
									}
									if (editors[i]['field'] == 'value_max') {
										maxactVal = $(editors[i].target);
										secondEnabler = editors[i]['oldHtml']
									}
									if (editors[i]['field'] == 'value_min') {
										minactVal = $(editors[i].target);
										thirdEnabler = editors[i]['oldHtml']
									}
								}
																
							actVal
										.textbox({
											onChange : function() {
												
												var aVal = actVal.textbox('getText');
												var minVal = minactVal.textbox('getText');
												var maxVal = maxactVal.textbox('getText');
												
												if ((aVal != null && aVal != '')
														&& (minVal != null && minVal != '')
														&& (maxVal != null && maxVal != '')) {
												//	alert("value_min="+minVal+",value_max="+maxVal+",aVal="+aVal);
													minmax_flag = validateMinMax(aVal, minVal,maxVal);
												//	alert('minmax_flag--'+minmax_flag);//---sun
													
													if (!minmax_flag) {
														$.messager
																.alert(
																		'Information -1',
																		'Aim value '
																				+ aVal
																				+ ' should be in between Min '
																				+ minVal
																				+ ' & Max '
																				+ maxVal
																				+ ' Values...!',
																		'info');
														actVal.textbox(
																'setValue',
																firstEnabler);
														// refreshM32PSNChemistry();
													}
												}
											}
										});

								maxactVal
										.textbox({
											onChange : function() {
												var aValMax = maxactVal
														.textbox('getText');
												var minVal = minactVal.textbox('getText');
												var maxVal = maxactVal.textbox('getText');
												if ((aValMax != null && aValMax != '')
														&& (minVal != null && minVal != '')
														&& (maxVal != null && maxVal != '')) {
													if (parseFloat(aValMax) < parseFloat(minVal)) {
														$.messager
																.alert(
																		'Information -2',
																		'Max value '
																				+ aValMax
																				+ ' should not be less than Min '
																				+ minVal
																				+ ' Value...!',
																		'info');
														maxactVal.textbox(
																'setValue',
																secondEnabler);
													}
												}
											}
										});

							}
						},
						onBeforeSave : function(index, row) {

							var chemlevel = $('#m32_chem_level').combobox(
									'getValue');

							if (chemlevel = 0 || chemlevel == null
									|| chemlevel == '') {
								$.messager
										.alert(
												'PSN Details Info',
												'Please Select Chemistry Level',
												'info');

							}

						},

						onSave : function(index, row) {
							// refreshM11PSNChemistry();

							console.log(" index: " + index + " row : " + row);

						},
						onSuccess : function(index, row) {

							refreshM32PSNChemistry();
						},

						onError : function(index, row) {
							alert("Error In " + row.chem_si_no);
						}
					});

	$("#m32_chem_level")
			.combobox(
					{
						onSelect : function(record) {
							// alert($("#m32psn_no_chem").val());
							initChemistryDataGrid();
							var chemlevel = record.keyval;
							var psn_hdr_sl_no = document
									.getElementById('m32_psn_hdr_sl_no').value;
							var revised_psn_hdr_sl_no = $('#m32PsnRevNo')
									.combobox('getValue');

							console.log("Revised PSN Sl no : "
									+ revised_psn_hdr_sl_no
									+ " Current Psn Sl No : " + psn_hdr_sl_no)
							if (revised_psn_hdr_sl_no != null
									&& revised_psn_hdr_sl_no != '') {
								refreshM32PSNChemistry();
								if ((chemlevel != null && chemlevel != 0 && chemlevel != '')) {

									$('#m32_PSN_Chemistry_tbl_id')
											.edatagrid(
													{
														updateUrl : './psnChemMstr/PSNChemRevMstrUpdate?current_psn_hdr_sl_no='
																+ psn_hdr_sl_no
																+ '&revised_psn_hdr_sl_no='
																+ revised_psn_hdr_sl_no,

														saveUrl : './psnChemMstr/PSNChemRevMstrSave?psn_hdr_sl_no='
																+ psn_hdr_sl_no
																+ '&chemlevel='
																+ chemlevel
																+ '&revised_psn_hdr_sl_no='
																+ revised_psn_hdr_sl_no
													// /*
													// onEndEdit:function(index,row){
													// console.log("Row :"+row
													// +" Index : "+index);
													// console.log(row);
													// } */

													});

								}
							} else {
								$.messager.alert('Revision No.',
										'PSN Revision Number Missing..',
										'warning');

							}

						}
					});

}// end PSNChemCRUD()

// psn product
function PSNProductInit() {
	var psn_hdr_sl_no = document.getElementById('m32_psn_hdr_sl_no').value;
	var revised_psn_hdr_sl_no = $('#m32PsnRevNo').combobox('getValue');
	// console.log("psn : "+psn_hdr_sl_no+" revised : "+revised_psn_hdr_sl_no);
	$('#m32_PSN_Product_tbl_id')
			.edatagrid(
					{
						updateUrl : './PSNProductMstr/PSNRevProdUpdate?current_psn_hdr_sl_no='
								+ psn_hdr_sl_no
								+ "&revised_psn_hdr_sl_no="
								+ revised_psn_hdr_sl_no,
						saveUrl : './PSNProductMstr/PSNRevProdSave?psn_hdr_sl_no='
								+ psn_hdr_sl_no
								+ "&revised_psn_hdr_sl_no="
								+ revised_psn_hdr_sl_no
					});

}

function PSNProductCRUD() {
	$('#m32_PSN_Product_tbl_id').edatagrid({
		onBeforeEdit : function(index, row) {

			// record_status default 1
			if (row.record_status == null || row.record_status == '') {
				if (row.record_status != 0) {
					row.record_status = row.record_status || 1;
				}
			}

		},
		onSave : function(index, row) {
			// SaveM13PSNProd();
			refreshM32PSNProduct();
		},
		onSuccess : function(index, row) {
			refreshM32PSNProduct();
		},
		onDestroy : function(index, row) {
			deleteM32PSNProduct(row.product_sl_no);
		}
	});

	var lastIndex;
	$('#m32_PSN_Product_tbl_id').datagrid(
			{
				onClickRow : function(rowIndex) {
					if (lastIndex != rowIndex) {
						$(this).datagrid('endEdit', lastIndex);
						$(this).datagrid('beginEdit', rowIndex);
					}
					lastIndex = rowIndex;

				},

				onBeginEdit : function(rowIndex) {
					// alert('on begin edit');
					var prow = $('#m32_PSN_Product_tbl_id').datagrid(
							'getSelected');
					// alert('prow.prod_width----'+prow.prod_width);
					var editors = $('#m32_PSN_Product_tbl_id').datagrid(
							'getEditors', rowIndex);
					var prod = $(editors[2].target);
					var dia = $(editors[3].target);
					var width = $(editors[4].target);
					var thickness = $(editors[5].target);
					var section = $(editors[6].target);
					/*
					 * var min_length= $(editors[7].target); var max_length=
					 * $(editors[8].target);
					 */
					var widthValue = $(editors[4].target).numberbox('getText');
					var thickValue = $(editors[5].target).numberbox('getText');

					/*
					 * width.add(thickness).numberbox({ onSelect:function(){
					 * alert('on select in width'); var svalue=
					 * widthValue.numberbox('getValue')+"MM_"+thickValue.numberbox('getValue')+"MM";
					 * section.numberbox('setValue',svalue); } });
					 */
					var text = $(editors[2].target).combobox('getText');

					prod.combobox({
						value : text,
						onSelect : function() {
							var product = prod.combobox('getText');

							if (product == 'BILLET') {
								dia.numberbox({
									disabled : true,
									value : ''
								});
								width.numberbox({
									disabled : false
								});
								thickness.numberbox({
									disabled : false
								});
							}
							if (product == 'BLOOM') {
								dia.numberbox({
									disabled : true,
									value : ''
								});
								width.numberbox({
									disabled : false
								});
								thickness.numberbox({
									disabled : false
								});
							}
							if (product == 'ROUNDS') {
								dia.numberbox({
									disabled : false
								});
								width.numberbox({
									disabled : true,
									value : ''
								});
								thickness.numberbox({
									disabled : true,
									value : ''
								});
							}
							if (product == 'BARS') {
								dia.numberbox({
									disabled : true,
									value : ''
								});
								width.numberbox({
									disabled : false
								});
								thickness.numberbox({
									disabled : false
								});
							}
							if (product == 'RCS') {
								dia.numberbox({
									disabled : true,
									value : ''
								});
								width.numberbox({
									disabled : false
								});
								thickness.numberbox({
									disabled : false
								});
							}
						}
					});

				}

			});

}
function deleteM32PSNProduct(product_sl_no) {
	$.ajax({
		headers : {
			'Accept' : 'application/json',
			'Content-Type' : 'application/json'
		},
		type : 'GET',
		// data: JSON.stringify(formData),
		dataType : "json",
		url : './PSNProductMstr/PSNProdDelete?product_sl_no=' + product_sl_no,
		success : function(data) {
			refreshT2HeatPlan();
		},
		error : function() {
			$.messager.alert('Processing', 'Error while Processing Ajax...',
					'error');
		}
	})
}

$('#m32addPsnRevision')
		.bind(
				'click',
				function() {
					var summary = $("#m32PsnRevisionSummary")
							.textbox("getText");
					if (summary != '') {
						console.log("Revised Psn Dtls : "
								+ $('#m32PsnRevNo').combobox('getValue'))
						$.messager
								.confirm(
										'New Revision',
										'Are you sure want to create new revision?',
										function(r) {
											if (r) {
												var psn_hdr_sl_no = document
														.getElementById('m32_psn_hdr_sl_no').value;

												$
														.ajax({
															headers : {
																'Accept' : 'application/json',
																'Content-Type' : 'application/json'
															},
															type : 'POST',
															// data:
															// JSON.stringify(formData),
															dataType : "json",
															url : './psnHdrMstr/savePsnRevisionEntry?psn_hdr_sl_no='
																	+ psn_hdr_sl_no
																	+ "&revision_summary="
																	+ summary,

															success : function(
																	data) {
																if (data.status == "FAILURE") {
																	$.messager
																			.alert(
																					'Unable to process',
																					data.comment,
																					'error');
																} else {
																	$.messager
																			.alert(
																					'Processing',
																					'Revision Added....',
																					'info');
																}
																callM32PsnRevDropdown(psn_hdr_sl_no);

															},
															error : function() {
																$.messager
																		.alert(
																				'Processing',
																				'Error while Processing Ajax...',
																				'error');
															}
														})
											}
										});
					} else {
						$.messager
								.alert('Required',
										'Revision Summary Must be Added....',
										'warning');

					}

				});

function gotoHome() {

}

function loadRevisedChemDetailsPreview() {
	var revised_psn_hdr_sl_no = $('#m32PsnRevNo').combobox('getValue');

	var psn_hdr_sl_no = document.getElementById('m32_psn_hdr_sl_no').value;

	if ($("#m32PsnPsnCategory").combobox('getText') == "RETURNED") {
		psn_hdr_sl_no = $("#m32PsnRevNo").combobox('getValue');
	}
	$.ajax({
		headers : {
			'Accept' : 'application/json',
			'Content-Type' : 'application/json'
		},
		type : 'GET',
		// data: JSON.stringify(formData),
		dataType : "json",
		url : './psnChemMstr/getPsnRevChemDetails?psn_hdr_sl_no='
				+ psn_hdr_sl_no,

		success : function(data) {
			$("#m32_PSN_rev_Chemistry_tbl_id").datagrid('loadData', data);

		},
		error : function() {
			$.messager.alert('Processing', 'Error while Processing Ajax...',
					'error');
		}
	})

}

function loadRevisedProductsDetailsPreview() {

	var psn_no = $('#m32PsnNo').combobox('getText');
	var psn_hdr_sl_no = document.getElementById('m32_psn_hdr_sl_no').value;
	if ($("#m32PsnPsnCategory").combobox('getText') == "RETURNED") {
		psn_hdr_sl_no = $("#m32PsnRevNo").combobox('getValue');
	} // dont change this

	$.ajax({
		headers : {
			'Accept' : 'application/json',
			'Content-Type' : 'application/json'
		},
		type : 'GET',
		// data: JSON.stringify(formData),
		dataType : "json",
		url : './PSNProductMstr/getPsnRevProdDetails?psn_hdr_sl_no='
				+ psn_hdr_sl_no,

		success : function(data) {
			$('#m32_PSN_rev_rProduct_tbl_id').datagrid('loadData', data);
		},
		error : function() {
			$.messager.alert('Processing', 'Error while Processing Ajax...',
					'error');
		}
	})
}

// row styler
$('#m32_PSN_rev_rProduct_tbl_id')
		.datagrid(
				{
					rowStyler : function(index, row) {

						if (row.psn_hdr_sl_no != document
								.getElementById('m32_psn_hdr_sl_no').value
								&& $("#m32PsnPsnCategory").combobox('getText') != "RETURNED") {
							return 'background-color: pink; color: red; font-weight: bold;';
						}
					}
				});

$('#m32_PSN_Chemistry_tbl_id,#m32_PSN_Product_tbl_id')
		.edatagrid(
				{
					rowStyler : function(index, row) {
						// console.log(row.psn_hdr_sl_no+" - -
						// "+document.getElementById('m32_psn_hdr_sl_no').value)
						if (row.psn_hdr_sl_no != document
								.getElementById('m32_psn_hdr_sl_no').value) {
							return 'background-color: pink; color: red; font-weight: bold;';
						}
					}
				});
function deleterivprodDetails(){
	 $.messager.confirm('Confirm', 'Do you want to delete the selected Element ?', function(r){
			if (r){	 
	 var row = $('#m32_PSN_Product_tbl_id').datagrid('getSelected');
	 if (row){
		
		 $.ajax({
			 headers: { 
		'Accept': 'application/json',
		'Content-Type': 'application/json' 
		},
		type: 'POST',
		//data: JSON.stringify(formData),
		dataType: "json",
		url: './PSNProductMstr/getPsnRevProdDelete?product_sl_no='+row.product_sl_no,
			
		success: function(data) {
		    if(data.status == 'SUCCESS') 
		    	{
		    	$.messager.alert('PSN Prod Details Info',data.comment,'info');
		    	refreshM32PSNProduct();
		    	}else {
		    		$.messager.alert('PSN Prod Details Info',data.comment,'info');
		    	}
		  },
		error:function(){   
			$.messager.alert('Processing','Error while Processing Ajax...','error');
		  }
		}) 
}else{
	$.messager.alert('Information','Please Select Record...!','info');
}

}});
	
}
function refreshM32PSNProduct() {
	var psn_hdr_sl_no = document.getElementById('m32_psn_hdr_sl_no').value;
	var psn_no = $('#m32PsnNo').combobox('getText');
	if ($("#m32PsnPsnCategory").combobox('getText') == "RETURNED") {
		psn_hdr_sl_no = $("#m32PsnRevNo").combobox('getValue');
	}
	// console.log("Modified DATA -- >"+psn_hdr_sl_no);
	$.ajax({
		headers : {
			'Accept' : 'application/json',
			'Content-Type' : 'application/json'
		},
		type : 'GET',
		// data: JSON.stringify(formData),
		dataType : "json",
		url : './PSNProductMstr/getPSNIncludeRevisionsProductByPSN?psn_no='
				+ psn_no + '&psn_hdr_sl_no=' + psn_hdr_sl_no,
		success : function(data) {
			$('#m32_PSN_Product_tbl_id').edatagrid('loadData', data);

		},
		error : function() {
			$.messager.alert('Processing', 'Error while Processing Ajax...',
					'error');
		}
	})
}

function getAvailedM32PSNChemistryValues(avail_params, callback) {
	var psn_hdr_sl_no = document.getElementById('m32_psn_hdr_sl_no').value;
	var psn_no = $('#m32PsnNo').combobox('getText');
	var chemlevel = $('#m32_chem_level').combobox('getValue');
	$('#m32_PSN_Chemistry_tbl_id').edatagrid('loadData', []);
	// console.log("chemlevel" +" : "+chemlevel);
	if ($("#m32PsnPsnCategory").combobox('getText') == "RETURNED") {
		psn_hdr_sl_no = $("#m32PsnRevNo").combobox('getValue');
	}

	if (chemlevel != 0) {
		$.ajax({
			headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json'
			},
			type : 'GET',
			// data: JSON.stringify(formData),
			dataType : "json",
			url : './psnChemMstr/getPsnRevisionsChemMstrByPSN?psn_no=' + psn_no
					+ '&psn_hdr_sl_no=' + psn_hdr_sl_no + '&chemlevel='
					+ chemlevel,
			success : function(data) {
				$('#m32_PSN_Chemistry_tbl_id').edatagrid('loadData', data);

				var params = new Array();
				for (var i = 0; i <= data.length; i++) {
					if (data[i] != undefined) {
						params[i] = (data[i]["element_id"]);
					}

				}

				avail_params = params.toString();
				callback(avail_params);
				return avail_params;

			},
			error : function() {
				$.messager.alert('Processing',
						'Error while Processing Ajax...', 'error');
			}
		})

	}// end if
}

function refreshM32PSNChemistry() {
	var psn_hdr_sl_no = document.getElementById('m32_psn_hdr_sl_no').value;
	var psn_no = $('#m32PsnNo').combobox('getText');
	var chemlevel = $('#m32_chem_level').combobox('getValue');
	$('#m32_PSN_Chemistry_tbl_id').edatagrid('loadData', []);
	// console.log("chemlevel" +" : "+chemlevel);
	if ($("#m32PsnPsnCategory").combobox('getText') == "RETURNED") {
		psn_hdr_sl_no = $("#m32PsnRevNo").combobox('getValue');
	}

	if (chemlevel != 0) {
		$.ajax({
			headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json'
			},
			type : 'GET',
			// data: JSON.stringify(formData),
			dataType : "json",
			url : './psnChemMstr/getPsnRevisionsChemMstrByPSN?psn_no=' + psn_no
					+ '&psn_hdr_sl_no=' + psn_hdr_sl_no + '&chemlevel='
					+ chemlevel,
			success : function(data) {
				$('#m32_PSN_Chemistry_tbl_id').edatagrid('loadData', data);
			},
			error : function() {
				$.messager.alert('Processing',
						'Error while Processing Ajax...', 'error');
			}
		})

	}// end if
}

function returnPsnDetailsByPsnHdrSlNo(psn_hdr_sl_no, callback) {
	$
			.ajax({
				headers : {
					'Accept' : 'application/json',
					'Content-Type' : 'application/json'
				},
				type : 'GET',
				// data: JSON.stringify(formData),
				dataType : "json",
				url : './psnHdrMstr/getPSNDetailsBySlNo?psn_hdr_sl_no='
						+ psn_hdr_sl_no,

				success : function(data) {
					callback(data);
					// return data;
				},
				error : function() {
					$.messager.alert('Processing',
							'Error while Processing Ajax...', 'error');
				}
			})
}

function initChemistryDataGrid() {
	var avail_params;
	getAvailedM32PSNChemistryValues(
			avail_params,
			function(data) {

				avail_params = data;
				var param_query = "";
				var temp = '';
				if (avail_params != '') {
					temp = 'and lookup_id NOT IN (' + avail_params + ')';
				}
				expamle = {
					required : true,
					editable : true,
					valueField : 'keyval',
					textField : 'txtvalue',
					method : 'get',
					url : './CommonPool/getComboList?col1=lookup_id&col2=lookup_value&classname=LookupMasterModel&status=1&wherecol= lookup_type=\'ELEMENT\' '
							+ temp + ' and lookup_status='

				}

				$('#m32_PSN_Chemistry_tbl_id')
						.datagrid(
								{
									columns : [ [
											{
												field : 'psn_hdr_sl_no',
												title : 'ID',
												width : 0,
												hidden : true,
												editor : {
													type : 'validatebox'
												}
											},
											{
												field : 'element_id',
												title : 'ELEMENT',
												width : 100,
												formatter : function(v, r, i) {
													// console.log(r['elementLkpMstrModel']['lookup_value']);
													return formatColumnData(
															'elementLkpMstrModel.lookup_value',
															v, r, i);
												},
												editor : {
													type : 'combobox',
													options : {
														required : true,
														editable : true,
														valueField : 'keyval',
														textField : 'txtvalue',
														method : 'get',
														url : './CommonPool/getComboList?col1=lookup_id&col2=lookup_value&classname=LookupMasterModel&status=1&wherecol= lookup_type=\'ELEMENT\' '
																+ temp
																+ ' and lookup_status='

													}
												},

											},

											{
												field : 'uom',
												title : 'UOM',
												width : 100,
												formatter : function(v, r, i) {
													return formatColumnData(
															'uomLkpMstrModel.lookup_value',
															v, r, i);
												},
												editor : {
													type : 'combobox',
													options : {
														required : true,
														editable : true,
														valueField : 'keyval',
														textField : 'txtvalue',
														method : 'get',
														url : './CommonPool/getComboList?col1=lookup_id&col2=lookup_value&classname=LookupMasterModel&status=1&wherecol= lookup_type=\'UOM\' and lookup_status='
													}
												}
											},
											{
												field : 'value_min',
												title : 'VALUE_MIN',
												width : 100,
												editor : {
													type : 'numberbox',
													options : {
														precision : 4
													}
												},
												align : "right",
											},
											{
												field : 'value_max',
												title : 'VALUE_MAX',
												width : 100,
												editor : {
													type : 'numberbox',
													options : {
														precision : 4
													}
												},
												align : "right",
											},
											{
												field : 'jom_value_min',
												title : 'JOM_MIN',
												width : 100,
												editor : {
													type : 'numberbox',
													options : {
														precision : 4
													}
												},
												align : "right",
											},
											{
												field : 'jom_value_max',
												title : 'JOM_MAX',
												width : 100,
												editor : {
													type : 'numberbox',
													options : {
														precision : 4
													}
												},
												align : "right",
											},
											{
												field : 'value_aim',
												title : 'AIM',
												width : 100,
												editor : {
													type : 'numberbox',
													options : {
														precision : 4,
														required : true
													}
												},
												align : "right",
											},
											{
												field : 'chem_level',
												hidden : true,
												editor : {
													type : 'validatebox'
												}
											},
											{
												field : 'is_trace_element',
												width : 100,
												title : 'TRACE_ELEMENT',
												formatter : function(v, r, i) {
													return formatColumnData(
															'traceElementLkpMstrModel.lookup_value',
															v, r, i);
												},
												editor : {
													type : 'combobox',
													options : {
														required : true,
														editable : true,
														valueField : 'keyval',
														textField : 'txtvalue',
														method : 'get',
														url : './CommonPool/getComboList?col1=lookup_id&col2=lookup_value&classname=LookupMasterModel&status=1&wherecol= lookup_type=\'IS_TRACE_ELEMENT\' and lookup_status='
													}
												}
											},
											{
												field : 'remarks',
												width : 120,
												title : 'REMARKS',
												editor : {
													type : 'validatebox',
													options : {
														required : false,
														editable : true
													}
												}
											},
											{
												field : 'record_status',
												width : 120,
												title : 'RECORD STATUS',
												formatter : function(v, r, i) {
													return formatColumnData(
															'lookupMstrModel.lookup_value',
															v, r, i);
												},
												editor : {
													type : 'combobox',
													options : {
														required : true,
														editable : false,
														valueField : 'keyval',
														textField : 'txtvalue',
														method : 'get',
														url : './CommonPool/getComboList?col1=lookup_code&col2=lookup_value&classname=LookupMasterModel&status=1&wherecol=lookup_type=\'RECORD_STATUS\' and lookup_status='
													}
												}
											} ] ]

								})
			})

}
