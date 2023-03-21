/**
 * PSN  operations details
 */

/** PSN Chemistry Screen Start * */

function getNewPSNNo(ordType) {
	var reqPram = ordType.slice(-10);
	$("#m10_psn_status_display").textbox("setText","NEW_PSN");
    $.ajax({
 		headers: { 
 		'Accept': 'application/json',
 		'Content-Type': 'application/json' 
 		},
 		type: 'GET',
 		dataType: "json",	
 		url: "./psnHdrMstr/getNextPSNSlNo?numCode="+reqPram,
 		success: function(data) {
 			var i = parseInt(data);
 			var psnNo;
			if (i <= 9) {
				psnNo = ordType+"000"+i; 
			} else if (i > 9 && i <= 99) {
				psnNo = ordType+"00"+i; 
			} else if (i > 99 && i <= 999) {
				psnNo = ordType+"0"+i; 
			}else {
				psnNo = ordType+""+i; 
			}
 			$('#m30_txt_psnNewNo_id').textbox('setText', psnNo);
 			$('#m10_psn_no').textbox('setText',psnNo);
 			
 		  },error:function(){    
 			$.messager.alert('Processing','Error while getting PSN No...','error');
 		  }
 		})
}

function validatePSNNo(psnNo) {
	$.ajax({
 		headers: { 
 		'Accept': 'application/json',
 		'Content-Type': 'application/json' 
 		},
 		type: 'GET',
 		dataType: "json",
 		url: "./psnHdrMstr/validatePSNNo?psnNo="+psnNo,
 		success: function(data) {
 			var cnt = parseInt(data);
 			return cnt;
 		  },error:function(){    
 			$.messager.alert('Processing','Error while validating PSN No...','error');
 			return -1;
 		  }
 		})
}

function PSNChemistryCreate() {
	PSNChemInit();
	PSNChemCRUD();
	var psn_hdr_sl_no = document.getElementById('m10_psn_hdr_sl_no').value;
	var psn_no = $('#m10_psn_no').textbox('getText');
	if (psn_hdr_sl_no != null && psn_hdr_sl_no != '') {
		$('#m11_PSN_Chemistry_form_div_id').dialog({
			modal : true,
			cache : true
		});
		$('#m11_PSN_Chemistry_form_div_id').dialog('open').dialog('center')
				.dialog('setTitle', 'PSN Chemistry Details Form');
		$('#psn_no_chem').textbox('setText', psn_no);
		$('#m11_chem_level').combobox('setValue', '');

		// refreshM11PSNChemistry();
	} else {
		$.messager.alert('PSN Details Info',
				'Please Enter PSN No and Save details', 'info');
	}

}

function PSNChemInit() {
	callM11Dropdown();

	// var psn_hdr_sl_no=document.getElementById('m10_psn_hdr_sl_no').value;

	/*
	 * $('#m11_PSN_Chemistry_tbl_id').edatagrid({ updateUrl:
	 * './psnChemMstr/PSNChemMstrUpdate', saveUrl:
	 * './psnChemMstr/PSNChemMstrSave?psn_hdr_sl_no='+psn_hdr_sl_no
	 * 
	 * });
	 */
}

function callM11Dropdown() {
	var url1 = "./CommonPool/getComboList?col1=lookup_id&col2=lookup_value&classname=LookupMasterModel&status=1&wherecol=lookup_type='CHEM_LEVEL' " +
			" and attribute1='Y' and lookup_status=";
	getDropdownList(url1, '#m11_chem_level');
}

function PSNChemCRUD() {

	$('#m11_PSN_Chemistry_tbl_id').edatagrid(
			{

				onBeforeEdit : function(index, row) {

					var chemlevel = $('#m11_chem_level').combobox('getValue');

					if (chemlevel = 0 || chemlevel == null || chemlevel == '') {
						$.messager.alert('PSN Details Info',
								'Please Select Chemistry Level', 'info');
						// refreshM17PSNQACharVal();
					}

					// record_status default 1
					if (row.record_status == null || row.record_status == '') {
						if (row.record_status != 0) {
							row.record_status = row.record_status || 1;
						}
					}

				},

				onBeforeSave : function(index, row) {

					var chemlevel = $('#m11_chem_level').combobox('getValue');

					if (chemlevel = 0 || chemlevel == null || chemlevel == '') {
						$.messager.alert('PSN Details Info',
								'Please Select Chemistry Level', 'info');

					}

				},
				onBeginEdit: function (index, row) {
					var editors = $('#m11_PSN_Chemistry_tbl_id').edatagrid('getEditors', index);
					var te_val = $(editors[10].target).combobox('getText');
					if (te_val == "" || te_val == null) {
						$.ajax({
					 		headers: { 
					 		'Accept': 'application/json',
					 		'Content-Type': 'application/json' 
					 		},
					 		type: 'GET',
					 		dataType: "json",
					 		url: "./psnHdrMstr/getdefaultTraceElement",
					 		success: function(data) {
					 			var teid = parseInt(data);
					 			$(editors[10].target).combobox('setValue',teid);
					 		  },error:function(){    
					 			$.messager.alert('Processing','Error while getting Default TraceElement...','error');
					 		  }
					 	})
					}
					
					var te_cmb_val = $(editors[10].target).combobox('getText');
				},
				onSave : function(index, row) {
					// refreshM11PSNChemistry();
				},
				onSuccess : function(index, row) {
					refreshM11PSNChemistry();
					M10enableSentToReview();
				},
				onError : function(index, row) {
					alert("Error In " + row.chem_si_no);
				}
			});

	$("#m11_chem_level")
			.combobox(
					{
						onSelect : function(record) {
							var chemlevel = record.keyval;
							var psn_hdr_sl_no = document
									.getElementById('m10_psn_hdr_sl_no').value;

							refreshM11PSNChemistry();
							if ((chemlevel != null && chemlevel != 0 && chemlevel != '')) {

								$('#m11_PSN_Chemistry_tbl_id')    
										.edatagrid(
												{
													
													updateUrl : './psnChemMstr/PSNChemMstrUpdate?psn_hdr_sl_no='
														+ psn_hdr_sl_no
														+ '&chemlevel='
														+ chemlevel,
													saveUrl : './psnChemMstr/PSNChemMstrSave?psn_hdr_sl_no='
															+ psn_hdr_sl_no
															+ '&chemlevel='
															+ chemlevel
															

												});

							}

						}
					});

}// end PSNChemCRUD()

function refreshM11PSNChemistry() {
	var psn_hdr_sl_no = document.getElementById('m10_psn_hdr_sl_no').value;
	var psn_no = $('#m10_psn_no').textbox('getText');
	var chemlevel = $('#m11_chem_level').combobox('getValue');

	if (chemlevel != 0) {
		$.ajax({
			headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json'
			},
			type : 'GET',
			// data: JSON.stringify(formData),
			dataType : "json",
			url : './psnChemMstr/getPsnChemMstrByPSN?psn_no=' + psn_no
					+ '&psn_hdr_sl_no=' + psn_hdr_sl_no + '&chemlevel='
					+ chemlevel,
			success : function(data) {
				$('#m11_PSN_Chemistry_tbl_id').edatagrid('loadData', data);
			},
			error : function() {
				$.messager.alert('Processing',
						'Error while Processing Ajax...', 'error');
			}
		})

	}// end if
}

$(window).load(setTimeout(applyM11Filter, 1)); // 1000 ms = 1 second.

function applyM11Filter() {

	$('#m11_PSN_Chemistry_tbl_id').datagrid('enableFilter');

}

function deleteChemistryDetails(){
	 $.messager.confirm('Confirm', 'Do you want to delete the selected Element ?', function(r){
			if (r){	 
	 var row = $('#m11_PSN_Chemistry_tbl_id').datagrid('getSelected');
	 var chemlevel = $('#m11_chem_level').combobox('getValue');
	 var psn_hdr_sl_no = document.getElementById('m10_psn_hdr_sl_no').value;
	 
	 if (row){
		 $.ajax({
  		headers: { 
  		'Accept': 'application/json',
  		'Content-Type': 'application/json' 
  		},
  		type: 'POST',
  		//data: JSON.stringify(formData),
  		dataType: "json",
  		url: './psnChemMstr/getPSNChemistryDelete?chem_si_no='+row.chem_si_no+ '&psn_hdr_sl_no=' + psn_hdr_sl_no + ' &chemlevel='
		+ chemlevel+'&element_id='+row.element_id,
		
  		success: function(data) {
  		    if(data.status == 'SUCCESS') 
  		    	{
  		    	$.messager.alert('PSN Chemistry Details Info',data.comment,'info');
  		    	refreshM11PSNChemistry();
  		    	}else {
  		    		$.messager.alert('PSN Chemistry Details Info',data.comment,'info');
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

/** PSN Chemistry Screen End * */

/** PSN Grade details start * */

function PSNGradeCreate() {
	PSNGradeInit();
	PSNGradeCRUD();
	var psn_hdr_sl_no = document.getElementById('m10_psn_hdr_sl_no').value;
	var psn_no = $('#m10_psn_no').textbox('getText');
	if (psn_hdr_sl_no != null && psn_hdr_sl_no != '') {
		$('#m12_PSN_Grade_form_div_id').dialog({
			modal : true,
			cache : true
		});
		$('#m12_PSN_Grade_form_div_id').dialog('open').dialog('center').dialog(
				'setTitle', 'PSN Grade Form');
		$('#psn_no_grade').textbox('setText', psn_no);
		refreshM12PSNGrade();
	} else {
		$.messager.alert('PSN Details Info',
				'Please Enter PSN No and Save details', 'info');
	}

}

function PSNGradeInit() {
	var psn_hdr_sl_no = document.getElementById('m10_psn_hdr_sl_no').value;

	$('#m12_PSN_Grade_tbl_id').edatagrid({
		updateUrl : './psnGradeMstr/PSNGradeUpdate',
		saveUrl : './psnGradeMstr/PSNGradeSave?psn_hdr_sl_no=' + psn_hdr_sl_no

	// destroyUrl: './HeatPlan/LineDelete'
	});

}

function PSNGradeCRUD() {
	$('#m12_PSN_Grade_tbl_id').edatagrid({

		onBeforeEdit : function(index, row) {

			// record_status default 1
			if (row.record_status == null || row.record_status == '') {
				if (row.record_status != 0) {
					row.record_status = row.record_status || 1;
				}
			}

		},
		onSave : function(index, row) {
			// SaveM12SavePSNGrade();

			refreshM12PSNGrade();
		},
		onSuccess : function(index, row) {

			refreshM12PSNGrade();
		},
		onDestroy : function(index, row) {

			deleteM12PSNGrade(row.psn_grade_sl_no);
		}
	});
}

function refreshM12PSNGrade() {
	var psn_hdr_sl_no = document.getElementById('m10_psn_hdr_sl_no').value;
	var psn_no = $('#m10_psn_no').textbox('getText');
	$.ajax({
		headers : {
			'Accept' : 'application/json',
			'Content-Type' : 'application/json'
		},
		type : 'GET',
		// data: JSON.stringify(formData),
		dataType : "json",
		url : './psnGradeMstr/getPSNGradeByPSN?psn_no=' + psn_no
				+ '&psn_hdr_sl_no=' + psn_hdr_sl_no,
		success : function(data) {
			$('#m12_PSN_Grade_tbl_id').edatagrid('loadData', data);
		},
		error : function() {
			$.messager.alert('Processing', 'Error while Processing Ajax...',
					'error');
		}
	})
}
function deleteGradeDetails(){
	 $.messager.confirm('Confirm', 'Do you want to delete the selected Element ?', function(r){
			if (r){	 
	 var row = $('#m12_PSN_Grade_tbl_id').datagrid('getSelected');
	 if (row){
		 $.ajax({
  		headers: { 
  		'Accept': 'application/json',
  		'Content-Type': 'application/json' 
  		},
  		type: 'POST',
  		//data: JSON.stringify(formData),
  		dataType: "json",
  		url: './psnGradeMstr/getPSNGradeDelete?psn_grade_sl_no='+row.psn_grade_sl_no,
  		success: function(data) {
  		    if(data.status == 'SUCCESS') 
  		    	{
  		    	$.messager.alert('PSN Grade Details Info',data.comment,'info');
  		    	refreshM12PSNGrade();
  		    	}else {
  		    		$.messager.alert('PSN Grade Details Info',data.comment,'info');
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

function deleteM12PSNGrade(grade_sl_no) {
	$.ajax({
		headers : {
			'Accept' : 'application/json',
			'Content-Type' : 'application/json'
		},
		type : 'GET',
		// data: JSON.stringify(formData),
		dataType : "json",
		url : './psnGradeMstr/PSNGradeDelete?grade_sl_no=' + grade_sl_no,
		success : function(data) {
			refreshT2HeatPlan();
		},
		error : function() {
			$.messager.alert('Processing', 'Error while Processing Ajax...',
					'error');
		}
	})
}

$(window).load(setTimeout(applyM12Filter, 1)); // 1000 ms = 1 second.

function applyM12Filter() {

	$('#m12_PSN_Grade_tbl_id').datagrid('enableFilter');

}
/** PSN Grade details End * */

/** PSN Product details start * */

function PSNProductCreate() {
	PSNProductInit();
	PSNProductCRUD();
	var psn_hdr_sl_no = document.getElementById('m10_psn_hdr_sl_no').value;
	var psn_no = $('#m10_psn_no').textbox('getText');
	if (psn_hdr_sl_no != null && psn_hdr_sl_no != '') {
		$('#m13_PSN_Product_form_div_id').dialog({
			modal : true,
			cache : true
		});
		$('#m13_PSN_Product_form_div_id').dialog('open').dialog('center')
				.dialog('setTitle', 'PSN Product Form');
		$('#psn_no_prod').textbox('setText', psn_no);
		refreshM13PSNProduct();
	} else {
		$.messager.alert('PSN Details Info', 'Please Enter PSN No and Save details',
				'info');
	}

}

function PSNProductInit() {
	var psn_hdr_sl_no = document.getElementById('m10_psn_hdr_sl_no').value;

	$('#m13_PSN_Product_tbl_id').edatagrid({
		updateUrl : './PSNProductMstr/PSNProdUpdate',
		saveUrl : './PSNProductMstr/PSNProdSave?psn_hdr_sl_no=' + psn_hdr_sl_no

	});

}

function PSNProductCRUD() {
	$('#m13_PSN_Product_tbl_id').edatagrid({
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
			refreshM13PSNProduct();
		},
		onSuccess : function(index, row) {
			refreshM13PSNProduct();
			M10enableSentToReview();
		},
		onDestroy : function(index, row) {
			deleteM13PSNProduct(row.product_sl_no);
		}
	});

	var lastIndex;
	$('#m13_PSN_Product_tbl_id').datagrid(
			{
				onClickRow : function(rowIndex) {
					if (lastIndex != rowIndex) {
						$(this).datagrid('endEdit', lastIndex);
						$(this).datagrid('beginEdit', rowIndex);
					}
					lastIndex = rowIndex;

				},

				onBeginEdit : function(rowIndex) {
					var prow = $('#m13_PSN_Product_tbl_id').datagrid(
							'getSelected');
					// alert('prow.prod_width----'+prow.prod_width);
					var editors = $('#m13_PSN_Product_tbl_id').datagrid(
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

function refreshM13PSNProduct() {
	var psn_hdr_sl_no = document.getElementById('m10_psn_hdr_sl_no').value;
	var psn_no = $('#m10_psn_no').textbox('getText');

	$.ajax({
		headers : {
			'Accept' : 'application/json',
			'Content-Type' : 'application/json'
		},
		type : 'GET',
		// data: JSON.stringify(formData),
		dataType : "json",
		url : './PSNProductMstr/getPSNProductByPSN?psn_no=' + psn_no
				+ '&psn_hdr_sl_no=' + psn_hdr_sl_no,
		success : function(data) {
			$('#m13_PSN_Product_tbl_id').edatagrid('loadData', data);

		},
		error : function() {
			$.messager.alert('Processing', 'Error while Processing Ajax...',
					'error');
		}
	})
}

function deleteM13PSNProduct(grade_sl_no) {
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

$(window).load(setTimeout(applyM13Filter, 1)); // 1000 ms = 1 second.

function applyM13Filter() {

	$('#m13_PSN_Product_tbl_id').datagrid('enableFilter');

}

/** PSN Product details End * */
/** PSN Unit SOP details start * */
function PSNUnitSopCreate() {	
	PSNUnitSopInit();
	PSNUnitSopCRUD();
	$('#m16_subunit_id').combobox('clear');
	$('#m16_unit_id').combobox('clear');
	var psn_hdr_sl_no = document.getElementById('m10_psn_hdr_sl_no').value;
	var psn_no = $('#m10_psn_no').textbox('getText');
	if (psn_hdr_sl_no != null && psn_hdr_sl_no != '') {
		$('#m16_PSN_Unit_Sop_form_div_id').dialog({
			modal : true,
			cache : true
		});
		$('#m16_PSN_Unit_Sop_form_div_id').dialog('open').dialog('center')
				.dialog('setTitle', 'PSN Unit SOP Form');
		$('#m16_psn_no_Unit_SOP').textbox('setText',psn_no);
		psn_no=$('#m16_psn_no_Unit_SOP').textbox('getText');
		refreshM16PSNUnitSop();
		psn_no=$('#psn_no_Unit_Sop').textbox('getText');
	} else {
		$.messager.alert('PSN Details Info',
				'Please Enter PSN No and Save details', 'info');
	}

}

function PSNUnitSopInit() {
	var psn_hdr_sl_no = document.getElementById('m10_psn_hdr_sl_no').value;
	var psn_no = $('#m10_psn_no').textbox('getText');
	var sub_unit_id = $('#m16_subunit_id').combobox('getValue');
	 $('#m16_PSN_Unit_Sop_tbl_id').datagrid({		
			  columns:[[
			        {field:'psn_sop_sl_no',title:'PSN SOP ID',width:0,hidden:true},
			        {field:'psn_hdr_sl_no',title:'PSN HDR ID',width:0,hidden:true},
					{field:'sop_sl_no',title:'SOP ID',width:0,hidden:true},
					{field:'unit_id',title:'UNIT ID',width:0,hidden:true},
					{field:'sub_unit_id',title:'SUB UNIT ID',width:0,hidden:true},
					{field:'sop_instruction',title:'SOP Instruction',width:250,sortable:true,editor:{
									type:'textbox',								
										}},
					{field:'record_status',
					title:'Status',width:40,
					editor : {
						type : 'combobox',
						editable : false,
						options : {
							required : true,
							editable : false,
							valueField : 'keyval',
							textField : 'txtvalue',
							panelHeight : 'auto',
							method : 'get',
							url : './CommonPool/getComboList?col1=lookup_code&col2=lookup_value&classname=LookupMasterModel&status=1&wherecol=lookup_type=\'RECORD_STATUS\' and lookup_status='
						}
					},
					formatter:(function(v,r,i){return formatColumnStatus('record_status',v,r,i);})},
				    {field:'record_version',title:'record_version',hidden:true},
			    ]]			 
		 });
}

function PSNUnitSopCRUD() {
	$('#m16_PSN_Unit_Sop_tbl_id').edatagrid({

		onBeforeEdit : function(index, row) {

			// record_status default 1
			if (row.record_status == null || row.record_status == '') {
				if (row.record_status != 0) {
					row.record_status = row.record_status || 1;
				}
			}

		},
		onSave : function(index, row) {

			refreshM16PSNUnitSop();
		},
		onSuccess : function(index, row) {

			refreshM16PSNUnitSop();
		}
	});
}

function refreshM16PSNUnitSop() {
	var psn_hdr_sl_no = document.getElementById('m10_psn_hdr_sl_no').value;
	var sub_unit_id=$('#m16_subunit_id').combobox('getValue');
	var unit_id=$('#m16_unit_id').combobox('getValue');
	$('#m16_PSN_Unit_Sop_tbl_id').edatagrid(
			{
				updateUrl : './PSNUnitSopMstr/PSNUnitSopUpdate',
				saveUrl : './PSNUnitSopMstr/PSNUnitSopSave?psn_hdr_sl_no='+ psn_hdr_sl_no + '&sub_unit_id=' + sub_unit_id + '&unit_id=' + unit_id
			});
	$.ajax({
		headers : {
			'Accept' : 'application/json',
			'Content-Type' : 'application/json'
		},
		type : 'GET',
		// data: JSON.stringify(formData),
		dataType : "json",
		url : './PSNUnitSopMstr/getPSNUnitSopByPSN?sub_unit_id=' + sub_unit_id
				+ '&psn_hdr_sl_no=' + psn_hdr_sl_no,
		success : function(data) {
			$('#m16_PSN_Unit_Sop_tbl_id').edatagrid('loadData', data);
		},
		error : function() {
			$.messager.alert('Processing', 'Error while Processing Ajax...',
					'error');
		}
	})
}

$(window).load(setTimeout(applyM16Filter, 1)); // 1000 ms = 1 second.

function applyM16Filter() {
	$('#m16_PSN_Unit_Sop_tbl_id').datagrid('enableFilter');

}

/** PSN Unit SOP End * */
/** PSN Quality Char Values start * */

function PSNQACharsCreate() {
	PSNQACharInit();
	PSNQACharCRUD();
	var psn_hdr_sl_no = document.getElementById('m10_psn_hdr_sl_no').value;
	var psn_no = $('#m10_psn_no').textbox('getText');
	if (psn_hdr_sl_no != null && psn_hdr_sl_no != '') {
		$('#m17_PSN_QA_Char_form_div_id').dialog({
			modal : true,
			cache : true
		});
		$('#m17_PSN_QA_Char_form_div_id').dialog('open').dialog('center')
				.dialog('setTitle', 'PSN Quality Char Values Form');
		$('#psn_no_Qa_char').textbox('setText', psn_no);
		$('#m17_unit').combobox('setValue', '');
		$('#m17_rqType').combobox('setValue', '');
		// refreshM17PSNQACharVal();
	} else {
		$.messager.alert('PSN Details Info',
				'Please Enter PSN No and Save details', 'info');
	}

}

function PSNQACharInit() {

	callM17Dropdown();
	/*
	 * var psn_hdr_sl_no=document.getElementById('m10_psn_hdr_sl_no').value;
	 * $('#m17_PSN_QA_Char_tbl_id').edatagrid({ updateUrl:
	 * './psnQACharValMstr/PSNQACharValUpdate', saveUrl:
	 * './psnQACharValMstr/PSNQACharValSave?psn_hdr_sl_no='+psn_hdr_sl_no+'&unit_id='+unit_id
	 * 
	 * //destroyUrl: './HeatPlan/LineDelete' });
	 */

}

function PSNQACharCRUD() {
	$('#m17_PSN_QA_Char_tbl_id').edatagrid(
			{

				onBeforeEdit : function(index, row) {

					var unit_id = $('#m17_unit').combobox('getValue');
					var rqType = $('#m17_rqType').combobox('getValue');
					if (unit_id = 0 || unit_id == null || unit_id == '') {
						$.messager.alert('PSN Details Info',
								'Please Select Unit', 'info');
						// refreshM17PSNQACharVal();
					}
					if (rqType = 0 || rqType == null || rqType == '') {
						$.messager.alert('PSN Details Info',
								'Please Select Requirement Type', 'info');
						// refreshM17PSNQACharVal();
					}
					row.min_value='NA';
					row.max_value='NA';
					// record_status default 1
					if (row.record_status == null || row.record_status == '') {
						if (row.record_status != 0) {
							row.record_status = row.record_status || 1;
						}
					}

				},

				onBeforeSave : function(index, row) {
					
					var unit_id = $('#m17_unit').combobox('getValue');
					var rqType = $('#m17_rqType').combobox('getValue');
					if (unit_id = 0 || unit_id == null || unit_id == '') {
						$.messager.alert('PSN Details Info',
								'Please Select Unit', 'info');

					}
					if (rqType = 0 || rqType == null || rqType == '') {
						$.messager.alert('PSN Details Info',
								'Please Select Requirement Type', 'info');

					}

				},
				onSave : function(index, row) {
					// refreshM17PSNQACharVal();
				},
				onSuccess : function(index, row) {

					refreshM17PSNQACharVal();
					M10enableSentToReview();
				},
				onError : function(index, row) {
					alert("Error In " + row.qa_char_si_no);
				}
			});

	$("#m17_unit").combobox({
		onSelect : function(record) {

			$('#m17_rqType').combobox('setValue', '');

		}
	});

	$("#m17_rqType")
			.combobox(
					{
						onSelect : function(record) {
							var rqType = record.keyval;
							var psn_hdr_sl_no = document
									.getElementById('m10_psn_hdr_sl_no').value;
							var unit_id = $('#m17_unit').combobox('getValue');

							refreshM17PSNQACharVal();
							if ((unit_id != null && unit_id != 0 && unit_id != '')
									&& (rqType != null && rqType != 0 && rqType != '')) {
								$('#m17_PSN_QA_Char_tbl_id')
										.edatagrid(
												{
													updateUrl : './psnQACharValMstr/PSNQACharValUpdate?psn_hdr_sl_no='
														+ psn_hdr_sl_no
														+ '&unit_id='
														+ unit_id
														+ '&rqType='
														+ rqType
													/*saveUrl : './psnQACharValMstr/PSNQACharValSave?psn_hdr_sl_no='
															+ psn_hdr_sl_no
															+ '&unit_id='
															+ unit_id
															+ '&rqType='
															+ rqType*/

												});
							}
						}
					});

}// end PSNQACharCRUD()

function refreshM17PSNQACharVal() {
	var psn_hdr_sl_no = document.getElementById('m10_psn_hdr_sl_no').value;
	var psn_no = $('#m10_psn_no').textbox('getText');

	var unit_id = $('#m17_unit').combobox('getValue');
	var rqType = $('#m17_rqType').combobox('getValue');

	if (unit_id != 0 || rqType != 0) {
		$.ajax({
			headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json'
			},
			type : 'GET',
			// data: JSON.stringify(formData),
			dataType : "json",
			url : './psnQACharValMstr/getPsnQACharValByPSN?psn_no=' + psn_no
					+ '&psn_hdr_sl_no=' + psn_hdr_sl_no + '&unit_id=' + unit_id
					+ '&rqType=' + rqType,
			success : function(data) {
				$('#m17_PSN_QA_Char_tbl_id').edatagrid('loadData', data);
			},
			error : function() {
				$.messager.alert('Processing',
						'Error while Processing Ajax...', 'error');
			}
		});

	}// end if

}
$(window).load(setTimeout(applyM17Filter, 1)); // 1000 ms = 1 second.

function applyM17Filter() {

	$('#m17_PSN_QA_Char_tbl_id').datagrid('enableFilter');

}

function callM17Dropdown() {
	var url1 = "./CommonPool/getComboList?col1=unit_id&col2=unit_name&classname=UnitMasterModel&status=1&wherecol=record_status=";
	var url2 = "./CommonPool/getComboList?col1=lookup_id&col2=lookup_value&classname=LookupMasterModel&status=1&wherecol=lookup_type='PSN_REQUIREMENT_TYPE' and lookup_status=";
	getDropdownList(url1, '#m17_unit');
	getDropdownList(url2, '#m17_rqType');
}

/** PSN Quality Char Values End * */
/** PSN Route Screen Start * */

function PSNRouteCreate() {
	PSNRouteInit();
	PSNRouteCRUD();
	var psn_hdr_sl_no = document.getElementById('m10_psn_hdr_sl_no').value;
	var psn_no = $('#m10_psn_no').textbox('getText');
	if (psn_hdr_sl_no != null && psn_hdr_sl_no != '') {
		$('#m18_PSN_Route_form_div_id').dialog({
			modal : true,
			cache : true
		});
		$('#m18_PSN_Route_form_div_id').dialog('open').dialog('center').dialog(
				'setTitle', 'PSN Route Details Form');
		$('#psn_no_route').textbox('setText', psn_no);
		refreshM18PSNRoute();
	} else {
		$.messager.alert('PSN Details Info',
				'Please Enter PSN No and Save details', 'info');
	}

}

function PSNRouteInit() {
	var psn_hdr_sl_no = document.getElementById('m10_psn_hdr_sl_no').value;

	$('#m18_PSN_Route_tbl_id').edatagrid(
			{
				updateUrl: './psnRouteMstr/PSNRouteMstrUpdate',
				saveUrl : './psnRouteMstr/PSNRouteMstrSave?psn_hdr_sl_no='
						+ psn_hdr_sl_no

			});

}

function PSNRouteCRUD() {
	$('#m18_PSN_Route_tbl_id').edatagrid({
		// record_status default 1
		onBeforeEdit : function(index, row) {

			// record_status default 1
			if (row.record_status == null || row.record_status == '') {
				if (row.record_status != 0) {
					row.record_status = row.record_status || 1;
				}
			}

		},
		onSave : function(index, row) {
			// refreshM18PSNRoute();
		},
		onSuccess : function(index, row) {

			refreshM18PSNRoute();
		},
		onError : function(index, row) {
			alert("Error In " + row.route_sl_no);
		}
	});
}

function refreshM18PSNRoute() {
	var psn_hdr_sl_no = document.getElementById('m10_psn_hdr_sl_no').value;
	var psn_no = $('#m10_psn_no').textbox('getText');
	$.ajax({
		headers : {
			'Accept' : 'application/json',
			'Content-Type' : 'application/json'
		},
		type : 'GET',
		// data: JSON.stringify(formData),
		dataType : "json",
		url : './psnRouteMstr/getPsnRouteMstrByPSN?psn_no=' + psn_no
				+ '&psn_hdr_sl_no=' + psn_hdr_sl_no,
		success : function(data) {
			$('#m18_PSN_Route_tbl_id').edatagrid('loadData', data);
		},
		error : function() {
			$.messager.alert('Processing', 'Error while Processing Ajax...',
					'error');
		}
	})
}

$(window).load(setTimeout(applyM18Filter, 1)); // 1000 ms = 1 second.

function applyM18Filter() {

	$('#m18_PSN_Route_tbl_id').datagrid('enableFilter');

}

/** PSN Route Screen End * */

/** PSN Document Start * */

function PSNDocsCreate() {
	PSNDocsInit();
	PSNDocsCRUD();
	var psn_hdr_sl_no = document.getElementById('m10_psn_hdr_sl_no').value;
	var psn_no = $('#m10_psn_no').textbox('getText');
	if (psn_hdr_sl_no != null && psn_hdr_sl_no != '') {
		$('#m19_PSN_Docs_form_div_id').dialog({
			modal : true,
			cache : true
		});
		$('#m19_PSN_Docs_form_div_id').dialog('open').dialog('center').dialog(
				'setTitle', 'PSN Document Details Form');
		$('#psn_no_docs_id').textbox('setText', psn_no);
		refreshM19PSNDocs();
	} else {
		$.messager.alert('PSN Details Info',
				'Please Enter PSN No and Save details', 'info');
	}

}

function PSNDocsInit() {
	var psn_hdr_sl_no = document.getElementById('m10_psn_hdr_sl_no').value;
	$('#m19_PSN_Docs_tbl_id').edatagrid({
		updateUrl : './psnDocsMstr/PSNDocsUpdate',
		saveUrl : './psnDocsMstr/PSNDocsSave?psn_hdr_sl_no=' + psn_hdr_sl_no

	});
}

function PSNDocsCRUD() {
	$('#m19_PSN_Docs_tbl_id').edatagrid({
		onSave : function(index, row) {
			refreshM19PSNDocs();
		},
		onSuccess : function(index, row) {

			refreshM19PSNDocs();
		},
		onError : function(index, row) {
			alert("Error In " + row.doc_sl_no);
		},
		onDestroy : function(index, row) {
			// alert("hai index success" +row.heat_line_id);
			deleteM19PSNDocs(row.doc_sl_no);
		}
	});
}

function refreshM19PSNDocs() {
	var psn_hdr_sl_no = document.getElementById('m10_psn_hdr_sl_no').value;
	var psn_no = $('#m10_psn_no').textbox('getText');
	$.ajax({
		headers : {
			'Accept' : 'application/json',
			'Content-Type' : 'application/json'
		},
		type : 'GET',
		// data: JSON.stringify(formData),
		dataType : "json",
		url : './psnDocsMstr/getPSNDocsByPSN?psn_no=' + psn_no
				+ '&psn_hdr_sl_no=' + psn_hdr_sl_no,
		success : function(data) {
			// $('#m19_PSN_Docs_tbl_id').edatagrid('loadData', data);
			$('#m19_PSN_Docs_tbl_id').edatagrid('loadData', data);
		},
		error : function() {
			$.messager.alert('Processing', 'Error while Processing Ajax...',
					'error');
		}
	})
}

// $(window).load(setTimeout(applyM19Filter,1)); //1000 ms = 1 second.

/*
 * function applyM19Filter() {
 * 
 * $('#m19_PSN_Docs_tbl_id').datagrid('enableFilter'); }
 */
function validateM19MstrPSNDocsform() {
	return $('#m19_PSN_Docs_form_div_id').form('validate');
}

function SaveM19PSNDocsMaster() {
	if (validateM19MstrPSNDocsform()) {
		var description = $('#m19_description').combobox('getText');
		var psn_hdr_sl_no = document.getElementById('m10_psn_hdr_sl_no').value;
		// var f = $('#m19_file_data').filebox('getValue');
		var f = $('#m19_file_data').next().find('.textbox-value');
		var file = $("#m19_file_data")[0].files[0];
		var formData = new FormData();
		formData.append("file", file);

		$.ajax({
			type : 'POST',
			data : formData,
			contentType : false,
			dataType : "json",
			processData : false,
			enctype : 'multipart/form-data',
			url : './psnDocsMstr/PSNDocsSave?psn_hdr_sl_no=' + psn_hdr_sl_no
					+ '&description=' + description,
			success : function(data) {
				if (data.status == 'SUCCESS') {
					$.messager.alert('PSN Docs Info', data.comment, 'info');
					refreshM19PSNDocs();

				} else {
					$.messager.alert('PSN Docs Info error', data.comment,
							'info');
				}
			},
			error : function() {
				$.messager.alert('Processing',
						'Error while Processing Ajax...', 'error');
			}
		})
	}

}
function DownloadFile() {
	var row = $('#m19_PSN_Docs_tbl_id').datagrid('getSelected');
	var url = './psnDocsMstr/PSNDocsDownload?doc_sl_no=' + row.doc_sl_no;
	window.open(url);
}

function deleteM19PSNDocs(lineno) {
	// alert("delete "+lineno);
	$.ajax({
		headers : {
			'Accept' : 'application/json',
			'Content-Type' : 'application/json'
		},
		type : 'GET',
		// data: JSON.stringify(formData),
		dataType : "json",
		url : './psnDocsMstr/PSNDocsDelete?doc_sl_no=' + lineno,
		success : function(data) {
			refreshM19PSNDocs();
		},
		error : function() {
			$.messager.alert('Processing', 'Error while Processing Ajax...',
					'error');
		}
	})
}

/** PSN Document Screen End * */
/** PSN Customer Master Map details start * */

function PSNCustomerMapCreate() {
	PSNCustomerMapInit();
	PSNCustomerMapCRUD();
	var psn_hdr_sl_no = document.getElementById('m10_psn_hdr_sl_no').value;
	var psn_no = $('#m10_psn_no').textbox('getText');
	if (psn_hdr_sl_no != null && psn_hdr_sl_no != '') {
		$('#m21_PSN_Customer_form_div_id').dialog({
			modal : true,
			cache : true
		});
		$('#m21_PSN_Customer_form_div_id').dialog('open').dialog('center')
				.dialog('setTitle', 'PSN Customer Map Details Form');
		$('#psn_no_customer').textbox('setText', psn_no);
		refreshM21PSNCustomerMap();
	} else {
		$.messager.alert('PSN Details Info',
				'Please Enter PSN No and Save details', 'info');
	}

}

function PSNCustomerMapInit() {
	var psn_hdr_sl_no = document.getElementById('m10_psn_hdr_sl_no').value;

	$('#m21_PSN_Customer_tbl_id').edatagrid(
			{
				updateUrl : './PSNCustomerMstr/PSNCustomerUpdate',
				saveUrl : './PSNCustomerMstr/PSNCustomerSave?psn_hdr_sl_no='
						+ psn_hdr_sl_no

			// destroyUrl: './HeatPlan/LineDelete'
			});

}

function PSNCustomerMapCRUD() {
	$('#m21_PSN_Customer_tbl_id').edatagrid({

		onBeforeEdit : function(index, row) {

			// record_status default 1
			if (row.record_status == null || row.record_status == '') {
				if (row.record_status != 0) {
					row.record_status = row.record_status || 1;
				}
			}

		},
		onSave : function(index, row) {

			refreshM21PSNCustomerMap();
		},
		onSuccess : function(index, row) {

			refreshM21PSNCustomerMap();
		}
	});
}

function refreshM21PSNCustomerMap() {
	var psn_hdr_sl_no = document.getElementById('m10_psn_hdr_sl_no').value;
	var psn_no = $('#m10_psn_no').textbox('getText');
	$.ajax({
		headers : {
			'Accept' : 'application/json',
			'Content-Type' : 'application/json'
		},
		type : 'GET',
		// data: JSON.stringify(formData),
		dataType : "json",
		url : './PSNCustomerMstr/getPSNCustomerDtlsByPSN?psn_no=' + psn_no
				+ '&psn_hdr_sl_no=' + psn_hdr_sl_no,
		success : function(data) {
			$('#m21_PSN_Customer_tbl_id').edatagrid('loadData', data);
		},
		error : function() {
			$.messager.alert('Processing', 'Error while Processing Ajax...',
					'error');
		}
	})
}

$(window).load(setTimeout(applyM21Filter, 1)); // 1000 ms = 1 second.

function applyM21Filter() {

	$('#m21_PSN_Customer_tbl_id').datagrid('enableFilter');

}

/** PSN Customer Master Map End * */

/** PSN Inclusion Rate Master details start * */

function PSNInclusionCreate() {
	PSNInclusionInit();
	PSNInclusionCRUD();
	var psn_hdr_sl_no = document.getElementById('m10_psn_hdr_sl_no').value;
	var psn_no = $('#m10_psn_no').textbox('getText');
	if (psn_hdr_sl_no != null && psn_hdr_sl_no != '') {
		$('#m22_PSN_Inclusion_form_div_id').dialog({
			modal : true,
			cache : true
		});
		$('#m22_PSN_Inclusion_form_div_id').dialog('open').dialog('center')
				.dialog('setTitle', 'PSN Inclusion Rate Details Form');
		$('#psn_no_inclusion').textbox('setText', psn_no);
		refreshM22PSNInclusion();
	} else {
		$.messager.alert('PSN Details Info',
				'Please Enter PSN No and Save details', 'info');
	}

}

function PSNInclusionInit() {
	var psn_hdr_sl_no = document.getElementById('m10_psn_hdr_sl_no').value;

	$('#m22_PSN_Inclusion_tbl_id').edatagrid(
			{
				updateUrl : './PSNInclusionMstr/PSNInclusionUpdate',
				saveUrl : './PSNInclusionMstr/PSNInclusionSave?psn_hdr_sl_no='
						+ psn_hdr_sl_no

			// destroyUrl: './HeatPlan/LineDelete'
			});

}

function PSNInclusionCRUD() {
	$('#m22_PSN_Inclusion_tbl_id').edatagrid({

		onBeforeEdit : function(index, row) {

			// record_status default 1
			if (row.record_status == null || row.record_status == '') {
				if (row.record_status != 0) {
					row.record_status = row.record_status || 1;
				}
			}

		},
		onSave : function(index, row) {

			refreshM22PSNInclusion();
		},
		onSuccess : function(index, row) {

			refreshM22PSNInclusion();
		}
	});
}

function refreshM22PSNInclusion() {
	var psn_hdr_sl_no = document.getElementById('m10_psn_hdr_sl_no').value;
	var psn_no = $('#m10_psn_no').textbox('getText');
	$.ajax({
		headers : {
			'Accept' : 'application/json',
			'Content-Type' : 'application/json'
		},
		type : 'GET',
		// data: JSON.stringify(formData),
		dataType : "json",
		url : './PSNInclusionMstr/getPSNInclusionDtlsByPSN?psn_no=' + psn_no
				+ '&psn_hdr_sl_no=' + psn_hdr_sl_no,
		success : function(data) {
			$('#m22_PSN_Inclusion_tbl_id').edatagrid('loadData', data);
		},
		error : function() {
			$.messager.alert('Processing', 'Error while Processing Ajax...',
					'error');
		}
	});
}

$(window).load(setTimeout(applyM22Filter, 1)); // 1000 ms = 1 second.

function applyM22Filter() {

	$('#m22_PSN_Inclusion_tbl_id').datagrid('enableFilter');

}
/** PSN Inclusion Rate Master Map End * */
/**PSN Process Parameters Begin**/
function m29PSNProcParams(){
	m29PSNProcParamsCRUD();
// 	  m29PSNProcParamsInit();
	  $('#m25_subunit_id').combobox('clear');
	  $('#m25_unit_id').combobox('clear');
	var psn_hdr_sl_no=document.getElementById('m10_psn_hdr_sl_no').value;
	var psn_no=$('#m10_psn_no').textbox('getText');
	console.log(psn_no+"___"+psn_hdr_sl_no);
	if(psn_hdr_sl_no!=null && psn_hdr_sl_no!=''){
		$('#m29_PSN_processParams_form_div_id').dialog({modal:true,cache: true});
	    $('#m29_PSN_processParams_form_div_id').dialog('open').dialog('center').dialog('setTitle','PSNProcess Parameters Details Form');
	$('#psn_no_processParams').textbox('setText',psn_no);
	   refreshM29PSNprocessParams();
	}else{
		$.messager.alert('PSN Details Info','Please Enter PSN No and Save details','info');
	}
}

/**
 * @returns
 */
function m29PSNProcParamsInit(){
	var psn_hdr_sl_no=document.getElementById('m10_psn_hdr_sl_no').value;
	var sub_unit_id=$('#m25_subunit_id').combobox('getValue');
	//alert("addded");
//	refreshM29PSNprocessParams();
	var avail_params;
	collectM29PsnProcessAddedParam(avail_params,function(data){
    avail_params=data;
	var param_query="";
	var temp='';
	if(avail_params!=''){
	 temp='and param_sl_no NOT IN ('+avail_params+')';
	}
	 $('#m29_mstr_processParams_tbl_id').datagrid({
		
		  columns:[[
		        {field:'proc_param_sl_no',title:'ID',width:0,hidden:true},
		        {field:'param_id',title:'Parameter',width:100,
		        	formatter:function(v,r,i){return formatColumnData('psnprocessParamMdl.param_desc',v,r,i);},
		        	editor:{
					type:'combobox', 
					options:{
						method:'GET',
						url:"./CommonPool/getComboList?col1=param_sl_no&col2=param_desc&classname=ProcessParametersMasterModel&status=1&wherecol=sub_unit_id="+sub_unit_id+""+param_query+ temp+" and record_status=",
						valueField:'keyval',
						textField:'txtvalue',
						required:true,
						editable:true,
						
						onSelect:function(value){
							//alert('value--'+value.keyval);
							setTimeout(function(){
							
							  var opts = $('#m29_mstr_processParams_tbl_id').edatagrid('options');
		                         var ed = $('#m29_mstr_processParams_tbl_id').edatagrid('getEditor',{
		                             index:opts.editIndex,
		                             field:'uom'
		                         });
		                     	$.ajax({
		                	 		headers: { 
		                	 		'Accept': 'application/json',
		                	 		'Content-Type': 'application/json' 
		                	 		},
		                	 		type: 'GET',
		                	 		dataType: "json",
		                	 		url:'./CommonPool/getComboList?col1=uomMdl.lookup_id&col2=uomMdl.lookup_value&classname=ProcessParametersMasterModel&status=1&wherecol=param_sl_no='+value.keyval+' and record_status=',
		                	 		success: function(data) {
		                	 			$(ed.target).textbox('setText',data[0].txtvalue);
		                	 			//$(ed.target).textbox('setValue',data[0].keyvalue);
		                	 			
		                	 			
		                	}
		                     	});
						},0);
							
						
						
							}
						}
		        	}
		        },
		        {field:'uom',title:'UOM',width:80,align:'right',sortable:true,formatter:function(v,r,i){return formatColumnData('psnprocessParamMdl.uomMdl.lookup_value',v,r,i);},
					editor:'textbox'
				
						},
		        {field:'value_min',title:'Val Min',width:80,align:'right',sortable:true,editor:{
					type:'numberbox',
					options:{
						precision:2,
						required:true
							}
						}},
		        {field:'value_max',title:'Val Max',width:80,sortable:true,editor:{
					type:'numberbox',
					options:{
						precision:2,
						required:true
							}
						}},
				{field:'value_aim',title:'Val Aim',width:80,sortable:true,editor:{
								type:'numberbox',
								options:{
									precision:2,
									required:true
										}
									}},
				{field:'record_status',
				title:'Status',width:40,
				editor : {
					type : 'combobox',
					editable : false,
					options : {
						required : true,
						editable : false,
						valueField : 'keyval',
						textField : 'txtvalue',
						panelHeight : 'auto',
						method : 'get',
						url : './CommonPool/getComboList?col1=lookup_code&col2=lookup_value&classname=LookupMasterModel&status=1&wherecol=lookup_type=\'RECORD_STATUS\' and lookup_status='
					}
				},
				formatter:(function(v,r,i){return formatColumnStatus('record_status',v,r,i);})},
			    {field:'record_version',title:'record_version',hidden:true},
		    ]]
		 
	 });
	});
//	 m29PSNProcParamsCRUD();
	
    $('#m29_mstr_processParams_tbl_id').edatagrid({
    	updateUrl: './PSNprocessParamsMstr/PSNProcessParamUpdate',
        saveUrl: './PSNprocessParamsMstr/PSNProcessParamSave?psn_hdr_sl_no='+psn_hdr_sl_no+'&sub_unit_id='+sub_unit_id
        
        //destroyUrl: './HeatPlan/LineDelete'
    });

}
    
function m29PSNProcParamsCRUD(){    
$('#m29_mstr_processParams_tbl_id').edatagrid({
	
	onBeforeEdit: function(index,row){
		//record_status default 1
		if(row.record_status == null||row.record_status == ''){
	       if(row.record_status!= 0){
	    	   row.record_status = row.record_status||1;
	       }
		}
		if(row.psn_proc_param_sl_no == null){
			//alert('psn_proc_param_sl_no is == null');

			 /* var dg = $('#m29_mstr_processParams_tbl_id');
			   var col = dg.edatagrid('getColumnOption', 'param_id');
			   col.editor1 = col.editor;
			   col.editor = 'combobox';
			   
			   var col2 = dg.edatagrid('getColumnOption', 'uom');
			   col2.editor1 = col2.editor;
			   col2.editor = 'text';*/
			//refreshM30PSNAdditions();

			}else{
			// alert('psn_proc_param_sl_no is not eq null');
			 var dg = $('#m29_mstr_processParams_tbl_id');
			   var col = dg.edatagrid('getColumnOption', 'param_id');
			   col.editor1 = col.editor;
			   col.editor = null;
			  var col2 = dg.edatagrid('getColumnOption', 'uom');
			   col2.editor1 = col2.editor;
			   col2.editor = null;
			}
			
		},
onSave: function(index,row){

	refreshM29PSNprocessParams();
},onSuccess: function(index,row){

	refreshM29PSNprocessParams();
}
});
}
  function refreshM29PSNprocessParams()
	{
	  m29PSNProcParamsInit();
		var psn_hdr_sl_no=document.getElementById('m10_psn_hdr_sl_no').value;
		var psn_no=$('#m25_subunit_id').combobox('getValue');//$('#m10_psn_no').textbox('getText');
//		alert("sub unit id="+psn_no);
		if(psn_no!=''){
	 	$.ajax({
	 		headers: { 
	 		'Accept': 'application/json',
	 		'Content-Type': 'application/json' 
	 		},
	 		type: 'GET',
	 		//data: JSON.stringify(formData),
	 		dataType: "json",
	 		url:'./PSNprocessParamsMstr/getPSNprocessParamsDtlsByPSN?sub_unit_id='+psn_no+'&psn_hdr_sl_no='+psn_hdr_sl_no,
	 		success: function(data) {
	 			 $('#m29_mstr_processParams_tbl_id').edatagrid('loadData', data); 
	 		  },
	 		error:function(){      
	 			$.messager.alert('Processing','Error while Processing Ajax...','error');
	 		  }
	 		});
	}
}
  function collectM29PsnProcessAddedParam(avail_params,callback){
	 
	  var psn_hdr_sl_no=document.getElementById('m10_psn_hdr_sl_no').value;
	  var psn_no=$('#m25_subunit_id').combobox('getValue');
	  
	  if(psn_no!=''){
		 	$.ajax({
		 		headers: { 
		 		'Accept': 'application/json',
		 		'Content-Type': 'application/json' 
		 		},
		 		type: 'GET',
		 		//data: JSON.stringify(formData),
		 		dataType: "json",
		 		url:'./PSNprocessParamsMstr/getPSNprocessParamsDtlsByPSN?sub_unit_id='+psn_no+'&psn_hdr_sl_no='+psn_hdr_sl_no,
		 		success: function(data) {
		 			console.log(data);
		 			var params=new Array();
		 			for(var i=0;i<=data.length;i++){
		 				if(data[i]!=undefined){
		 					params[i]=(data[i]["param_id"]);
		 				}
		 				
		 			}
		 			
		 			avail_params=params.toString();
		 			callback(avail_params);
		 			return avail_params;
		 			// $('#m29_mstr_processParams_tbl_id').edatagrid('loadData', data); 
		 		  },
		 		error:function(){      
		 			$.messager.alert('Processing','Error while Processing Ajax...','error');
		 		  }
		 		});
		}
	
  }
		  
		  
		        /**PSN Process Parameters END */
			  
			  function m30PSNAdditions(){
				  $('#m30_subunit_id').combobox('clear');
				  $('#m30_unit_id').combobox('clear');
				  $('#m30_additions_type').combobox('clear');
				  
					var psn_hdr_sl_no=document.getElementById('m10_psn_hdr_sl_no').value;
					var psn_no=$('#m10_psn_no').textbox('getText');
					if(psn_hdr_sl_no!=null && psn_hdr_sl_no!=''){
			 		$('#m30_PSN_additions_form_div_id').dialog({modal:true,cache: true});
			 	    $('#m30_PSN_additions_form_div_id').dialog('open').dialog('center').dialog('setTitle','PSN Additions Details Form');
					$('#psn_no_additions').textbox('setText',psn_no);
					//refreshM30PSNAdditions();
					}else{
						$.messager.alert('PSN Details Info','Please Enter PSN No and Save details','info');
					}
			    }
			    
			    /**
			     * @returns
			     */
			    function m30PSNAdditionsInit(){
			    	//alert('in  m30PSNAdditionsInit');
			    	  $('#m30_PSN_mstr_additions_tbl_id').datagrid({
						
						    columns:[[
						        {field:'psn_additions_sl_no',title:'ID',width:0,hidden:true},
						        {field:'mat_id',title:'MaterialName',width:100,formatter:function(v,r,i){return formatColumnData('mtrlMstrodel.material_desc',v,r,i);},
						        	editor:{
									type:'combobox', 
									options:{
										method:'GET',
										url:"./CommonPool/getComboList?col1=material_id&col2=material_desc&classname=MtrlProcessConsumableMstrModel&status=1&wherecol=material_type="+$('#m30_additions_type').combobox('getValue')+"  AND material_id not in (select mat_id from PSNAdditionsModel a  where a.addn_type_id="+$('#m30_additions_type').combobox('getValue')+" and a.sub_unit_id="+$('#m30_subunit_id').combobox('getValue')+" and psn_hdr_sl_no="+document.getElementById('m10_psn_hdr_sl_no').value +" and record_status=1) and record_status=",
										valueField:'keyval',
										textField:'txtvalue',
										required:true,
										editable:true,
										
										onSelect:function(value){
											//alert('value--'+value.keyval);
											setTimeout(function(){
											
											  var opts = $('#m30_PSN_mstr_additions_tbl_id').edatagrid('options');
						                         var ed = $('#m30_PSN_mstr_additions_tbl_id').edatagrid('getEditor',{
						                             index:opts.editIndex,
						                             field:'uom'
						                         });
						                     	$.ajax({
						                	 		headers: { 
						                	 		'Accept': 'application/json',
						                	 		'Content-Type': 'application/json' 
						                	 		},
						                	 		type: 'GET',
						                	 		dataType: "json",
						                	 		url:'./CommonPool/getComboList?col1=uomLkpModel.lookup_id&col2=uomLkpModel.lookup_value&classname=MtrlProcessConsumableMstrModel&status=1&wherecol=material_id='+value.keyval+' and record_status=',
						                	 		success: function(data) {
						                	 			/////alert('editor type--'+ed.type);
						                	 			//alert('uom value---'+data[0].txtvalue);
						                	 			
						                	 			//alert('uom Text---'+data[0].keyval);
						                	 			// document.getElementById('m30_uom_val').value=data[0].txtvalue;
						                	 			// alert('uom---'+document.getElementById('m30_uom_val').value);
						                	 			$(ed.target).textbox('setText',data[0].txtvalue);
						                	 			//$(ed.target).textbox('setValue',data[0].keyvalue);
						                	 			// $(ed.target).text('setValue',data[0]);
						                		 		//ed.text('setValue',data[0].txtvalue);
						                		 		//ed.textbox('setText',data[0].keyval);
						                	 			
						                	}
						                     	});
										},0);
											
										
										
											}
										}
									}
								},
								{
									field : 'uom',
									title : 'UOM',
									width : 80,
									align : 'right',
									sortable : true,
									formatter : function(v, r, i) {
										return formatColumnData(
												'mtrlMstrodel.uomLkpModel.lookup_value',
												v, r, i);
									},
									editor : 'textbox'

								},
								{
									field : 'value_min',
									title : 'Val Min',
									width : 80,
									align : 'right',
									sortable : true,
									editor : {
										type : 'numberbox',
										options : {
											precision : 2,
											required : true
										}
									}
								},
								{
									field : 'value_max',
									title : 'Val Max',
									width : 80,
									sortable : true,
									editor : {
										type : 'numberbox',
										options : {
											precision : 2,
											required : true
										}
									}
								},
								{
									field : 'value_aim',
									title : 'Val Aim',
									width : 80,
									sortable : true,
									editor : {
										type : 'numberbox',
										options : {
											precision : 2,
											required : true
										}
									}
								},
								{
									field : 'record_status',
									title : 'Status',
									width : 40,
									formatter : (function(v, r, i) {
										return formatColumnStatus(
												'record_status', v, r, i);
									})
								}, {
									field : 'record_version',
									title : 'record_version',
									hidden : true
								}, ] ]

					});
	var psn_hdr_sl_no = document.getElementById('m10_psn_hdr_sl_no').value;
	var sub_unit_id = $('#m30_subunit_id').combobox('getValue');
	var addn_type = $('#m30_additions_type').combobox('getValue');

	$('#m30_PSN_mstr_additions_tbl_id').edatagrid(
			{
				updateUrl : './PSNadditionsMstr/PSNAdditionsUpdate',
				saveUrl : './PSNadditionsMstr/PSNAdditionsSave?psn_hdr_sl_no='
						+ psn_hdr_sl_no + '&sub_unit_id=' + sub_unit_id
						+ '&addn_type_id=' + addn_type

			});
	m30PSNAdditionsCRUD();
	refreshM30PSNAdditions();

}

function getAdditionValues(addn_index, callbackToOriginal) {
	// alert('in getAdditionValues');
	$
			.ajax({
				headers : {
					'Accept' : 'application/json',
					'Content-Type' : 'application/json'
				},
				type : 'GET',
				dataType : "json",
				url : "./CommonPool/getComboList?col1=material_id&col2=material_desc&classname=MtrlProcessConsumableMstrModel&status=1&wherecol=material_type="
						+ $('#m30_additions_type').combobox('getValue')
						+ "  AND material_id not in (select mat_id from PSNAdditionsModel a  where a.addn_type_id="
						+ $('#m30_additions_type').combobox('getValue')
						+ " and a.sub_unit_id="
						+ $('#m30_subunit_id').combobox('getValue')
						+ " and record_status=1) and record_status=",
				success : function(data) {
					callbackToOriginal(data);
				},
				error : function() {
					$.messager.alert('Processing',
							'Error while Processing Ajax...', 'error');
				}

			});
}
function m30PSNAdditionsCRUD() {
	$('#m30_PSN_mstr_additions_tbl_id').edatagrid({

		onBeforeEdit : function(index, row) {
			// alert('on before edit');
			if (row.record_status == null || row.record_status == '') {
				if (row.record_status != 0) {
					row.record_status = row.record_status || 1;
				}
			}

			if (row.psn_additions_sl_no == null) {
				// alert('psn_additions_sl_no is == null');

				/*
				 * var dg = $('#m30_PSN_mstr_additions_tbl_id'); var col =
				 * dg.edatagrid('getColumnOption', 'mat_id'); col.editor1 =
				 * col.editor; col.editor = 'combobox';
				 * 
				 * var col2 = dg.edatagrid('getColumnOption', 'uom');
				 * col2.editor1 = col2.editor; col2.editor = 'text';
				 */
				// refreshM30PSNAdditions();
			} else {
				// alert('psn_additions_sl_no is not eq null');
				var dg = $('#m30_PSN_mstr_additions_tbl_id');
				var col = dg.edatagrid('getColumnOption', 'mat_id');
				col.editor1 = col.editor;
				col.editor = null;
				var col2 = dg.edatagrid('getColumnOption', 'uom');
				col2.editor1 = col2.editor;
				col2.editor = null;
			}

		},
		onAdd : function(index, row) {
			// alert("Add Button clicked");

			// var editors =
			// $('#m30_PSN_mstr_additions_tbl_id').edatagrid('getEditors',
			// index);
			// var addn_cmb = $(editors[0].target);
			// var
			// additions_url="./CommonPool/getComboList?col1=material_id&col2=material_desc&classname=MtrlProcessConsumableMstrModel&status=1&wherecol=material_type="+$('#m30_additions_type').combobox('getValue')+"
			// AND material_id not in (select mat_id from PSNAdditionsModel a
			// where
			// a.addn_type_id="+$('#m30_additions_type').combobox('getValue')+"
			// and a.sub_unit_id="+$('#m30_subunit_id').combobox('getValue')+"
			// and record_status=1) and record_status=";
			// addn_cmb.combobox('reload',additions_url);

			// var uom_cmb = $(editors[1].target);
			// var addn_val = $(editors[0].target).combobox('getText');
			// on change of Addition combo
			/*
			 * addn_cmb.combobox({ value:addn_val, onSelect: function(){ var
			 * aVal = addn_cmb.combobox('getValue'); var
			 * m30_uom_url='./CommonPool/getComboList?col1=uomLkpModel.lookup_id&col2=uomLkpModel.lookup_value&classname=MtrlProcessConsumableMstrModel&status=1&wherecol=material_id='+aVal+'
			 * and record_status='; getUomValue(editors,aVal,m30_uom_url); } });
			 */

		},
		onSave : function(index, row) {
			m30PSNAdditionsInit();
		},
		onSuccess : function(index, row) {
			m30PSNAdditionsInit();
		}
	});

}

function refreshM30PSNAdditions() {
	// alert('in refreshM30PSNAdditions');
	var psn_hdr_sl_no = document.getElementById('m10_psn_hdr_sl_no').value;
	var m30_subUnitId = $('#m30_subunit_id').combobox('getValue');
	var m30_addn_type = $('#m30_additions_type').combobox('getValue');
	if ($('#m30_subunit_id').combobox('getValue') != '') {
		$
				.ajax({
					headers : {
						'Accept' : 'application/json',
						'Content-Type' : 'application/json'
					},
					type : 'GET',
					dataType : "json",
					url : './PSNadditionsMstr/getPSNAdditionsDtlsBySubUnit?subUnitId='
							+ m30_subUnitId
							+ '&psn_hdr_sl_no='
							+ psn_hdr_sl_no
							+ '&addn_type=' + m30_addn_type,
					success : function(data) {
						$('#m30_PSN_mstr_additions_tbl_id').edatagrid(
								'loadData', data);
						// m30PSNAdditionsCRUD();
					},
					error : function() {
						$.messager.alert('Processing',
								'Error while Processing Ajax...', 'error');
					}


				});
	}
}
$("#m30_unit_id").combobox({
	onSelect : function(record) {
		getSubUnits('m30_unit_id', 'm30_subunit_id');
	},
	onChange : function() {
		$('#m30_subunit_id').combobox('clear');
	}
});
$("#m30_additions_type").combobox({
	onSelect : function() {
		m30PSNAdditionsInit();
	},
	onChange : function() {
		$('#m30_PSN_mstr_additions_tbl_id').datagrid('loadData', []);
	}
});

			
				  function refreshM30PSNAdditions()
					{
					  //alert('in  refreshM30PSNAdditions');
						 $('#m30_PSN_mstr_additions_tbl_id').edatagrid('loadData', []);
						var psn_hdr_sl_no=document.getElementById('m10_psn_hdr_sl_no').value;
						var m30_subUnitId=$('#m30_subunit_id').combobox('getValue');
						var m30_addn_type=$('#m30_additions_type').combobox('getValue');
						if($('#m30_subunit_id').combobox('getValue')!=''){
					 	$.ajax({
					 		headers: { 
					 		'Accept': 'application/json',
					 		'Content-Type': 'application/json' 
					 		},
					 		type: 'GET',
					 		dataType: "json",
					 		url:'./PSNadditionsMstr/getPSNAdditionsDtlsBySubUnit?subUnitId='+m30_subUnitId+'&psn_hdr_sl_no='+psn_hdr_sl_no+'&addn_type='+m30_addn_type,
					 		success: function(data) {
					 			 $('#m30_PSN_mstr_additions_tbl_id').edatagrid('loadData', data); 
					 			//m30PSNAdditionsCRUD();
					 		  },
					 		error:function(){      
					 			$.messager.alert('Processing','Error while Processing Ajax...','error');
					 		  }
					 		  
					 		});
					}
			}
				  $("#m30_unit_id").combobox({onSelect:function(record){getSubUnits('m30_unit_id','m30_subunit_id');},onChange:function(){$('#m30_subunit_id').combobox('clear');}});
				  $("#m30_additions_type").combobox({onSelect:function(){m30PSNAdditionsInit();},onChange:function(){$('#m30_PSN_mstr_additions_tbl_id').datagrid('loadData',[]);}});
				  
				  
				  function M10enableSentToReview(){
						var psn_status= $("#m10_psn_status_display").textbox("getText");
						isChemistryEntriesPresent(function(dataC){
							if(dataC.length>0){
								isProductEntriesPresent(function(dataP){
									if(dataP.length>0){
										isQualityCharValuesPresent(function(dataQC){
											if(dataQC.length>0){
												if(psn_status=="NEW_PSN"){
													$("#m10psn_send_to_review").linkbutton("enable");
												}
												
											}
										});
									}
								});
							}
						});
						
					}
					
					function isQualityCharValuesPresent(callback){
						var psn_hdr_sl_no=document.getElementById('m10_psn_hdr_sl_no').value;
						$.ajax({
							headers : {
								'Accept' : 'application/json',
								'Content-Type' : 'application/json'
							},
							type : 'GET',
							//data: JSON.stringify(formData),
							dataType : "json",
							url : './psnQACharValMstr/getPsnQACharValOnlyByPSN?psn_hdr_sl_no=' + psn_hdr_sl_no,
							success : function(data) {
								console.log("Qualitychrs Details...: "+data.length);
								//console.log(data)
								callback(data);
								
								//console.log($("#m13_PSN_Product_tbl_id").datagrid("getData"))
								
							},
							error: function(){
								console.log("EROOR : Qualitychrs Details...:"+data.length);
								}
							});
						
					}
					
					function isProductEntriesPresent(callback){
						var psn_hdr_sl_no=document.getElementById('m10_psn_hdr_sl_no').value;
						var psn_no = $('#m10_psn_no').textbox('getText');
						
						$
						.ajax({
							headers : {
								'Accept' : 'application/json',
								'Content-Type' : 'application/json'
							},
							type : 'GET',
							//data: JSON.stringify(formData),
							dataType : "json",
							url : './PSNProductMstr/getPSNProductByPSN?psn_hdr_sl_no=' + psn_hdr_sl_no+"&psn_no="+psn_no,
							success : function(data) {
								console.log("Product Details...:"+data.length);
								//console.log(data)
								callback(data)
								
								
							},
							error: function(){
								console.log("EROOR : Product Details...");
								}
							});
					}
					
					function isChemistryEntriesPresent(callback){
						var psn_hdr_sl_no=document.getElementById('m10_psn_hdr_sl_no').value;
						$
						.ajax({
							headers : {
								'Accept' : 'application/json',
								'Content-Type' : 'application/json'
							},
							type : 'GET',
							//data: JSON.stringify(formData),
							dataType : "json",
							url : './psnChemMstr/getPsnChemMstrOnlyByPSN?psn_hdr_sl_no=' + psn_hdr_sl_no,
							success : function(data) {
								console.log("CHEM Details...:"+data.length);
								//console.log(data)
								callback(data)
								
							},
							error: function(){
								console.log("EROOR : CHEM Details...");
								}
							});
					}
				  
				  
				  
				    	

	