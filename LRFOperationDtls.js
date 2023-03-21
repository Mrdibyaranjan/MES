/** 1. Begin load data into HeatDetails grid ## Button:Process Heat Plan * */

function T13LoadHeatDetailsData(){
	$('#t13_lrf_heat_save_btn').linkbutton('enable');
	if(t13ValidateCrewDetails()){
		var row = $('#t13_LRF_production_tbl_id').datagrid('getSelected');
		if(row){
			$.messager.confirm('Confirm', 'Do you want to process selected laddle ?', function(r){
				if (r){
					setHeatDetailsGrid(row);
					document.getElementById('t13_reladling').value='N';
					getOtherData();
					if(row.unit_process_status == 'WAITING FOR DISPATCH' && row.trns_si_no != 0 ){
						document.getElementById('t13_trns_si_no').value='';
						document.getElementById('t13_cstatus').value=row.unit_process_status;
						$.ajax({
			 			headers: { 
			 			'Accept': 'application/json',
			 			'Content-Type': 'application/json' 
			 			},
			 			type: 'GET',
			 			dataType: "json",
			 			url: './LRFproduction/getLRFHeatDetailsByHeatNo?heatno='+row.heat_id+'&heat_counter='+row.heat_counter,
			 			success: function(data) {		 				
			 				$('#t13_purging_medium').combobox('setValue',data.purge_medium);
			 				//$('#t13_process_control').combobox('setValue',data.process_control);
			 				//$('#t13_vessel_car_no').combobox('setValue',data.vessel_car_id);
			 			},
			 			error:function(){ 
			 				$.messager.alert('Info','Heat Number does not exists','Info');
			 			}
						});
			 	   }else{
			 		 document.getElementById('t13_trns_si_no').value='';
			 		 $('#t13_purging_medium').textbox('setValue','');
	 				 //$('#t13_process_control').textbox('setValue','');
	 				 //$('#t13_vessel_car_no').textbox('setValue','');
			 	   }
				}
			});
		} else{
			$.messager.alert('LRF Production','Please Select One Record to Process Heat ...!','info');
		}
	}
	else{
		$.messager.alert('LRF Production','Please select all Crew Details  !','info');
	}
}
/** 1.End load data into HeatDetails grid * */

function T13LoadReLadleHeatDetailsData(){
	$('#t13_lrf_heat_save_btn').linkbutton('enable');
	if(t13ValidateCrewDetails()){
		var row = $('#t13_LRF_production_tbl_id').datagrid('getSelected');
		if(row){
			$.messager.confirm('Confirm', 'Do you want to ReLadle selected Heat ?', function(r){
				if (r){
					setHeatDetailsGrid(row);
					getOtherData();
					document.getElementById('t13_reladling').value='Y';
			 		document.getElementById('t13_trns_si_no').value='';
			 		$('#t13_purging_medium').textbox('setValue','');
	 				//$('#t13_process_control').textbox('setValue','');
	 				//$('#t13_vessel_car_no').textbox('setValue','');
	 				
	 				var s_url="./CommonPool/getComboList?col1=sub_unit_id&col2=sub_unit_name&classname=SubUnitMasterModel&status=0&wherecol=sub_unit_name='RLS' and record_status=";
					$.ajax({
						headers: { 
						'Accept': 'application/json',
						'Content-Type': 'application/json' 
						},
						type: 'GET',
						dataType: "json",
						url: s_url,
						success: function(data) {
							document.getElementById('t13_lrf_disp_unit').value = data[0].keyval;
						},
						error:function(){ 
							$.messager.alert('Info','Subunit not exists','Info');
						}
					}); 				
				}
			});
		} else{
			$.messager.alert('LRF Production','Please Select One Record to Process Heat ...!','info');
		}
	}
	else{
		$.messager.alert('LRF Production','Please select all Crew Details  !','info');
	}
}

function setHeatDetailsGrid(row){
	var heatno =row.heat_id;
	var aimpsn = row.aim_psn_char;
   	var tarcaster =row.caster_type;
   	var prevunit=row.sub_unit_name;
   	var laddleName =row.steel_ladle_name;
   	var taptemp=row.tap_temp;
   	var tapwgt=row.tap_wt;
 	var cutlength=row.plan_cut_length;
   
   	 
	$('#t13_heat_id').textbox('setText',heatno);
 	$('#t13_aim_psn').textbox('setText',aimpsn);
 	$('#t13_tar_caster').textbox('setText',tarcaster);
	$('#t13_prev_unit').textbox('setText',prevunit);
	$('#t13_laddle_no').textbox('setText',laddleName);
 	$('#t13_tap_temp').textbox('setText',taptemp);
 	$('#t13_tap_wgt').textbox('setText',tapwgt);
	$('#t13_initial_temp').textbox('setText','');
	$('#t13_plan_cut_length').textbox('setText','');

	document.getElementById('t13_heat_cnt').value=row.heat_counter;
 	document.getElementById('t13_caster_id').value=row.caster_id;
	document.getElementById('t13_sub_unit_id').value=row.sub_unit_id;
 	document.getElementById('t13_act_path').value=row.act_proc_path;
 	document.getElementById('t13_heat_track_id').value=row.heat_track_id;
 	document.getElementById('t13_aim_psn_id').value=row.aim_psn;
 	document.getElementById('t13_steel_ladleno_id').value=row.steel_ladle_no;
 	document.getElementById('t13_plan_no').value=row.heat_plan_id;
 	document.getElementById('t13_plan_line_no').value=row.heat_plan_line_id;
 	document.getElementById('t13_lrf_entry').value=row.lrf_entry;
 	document.getElementById('t13_plan_cut_length').value=row.plan_cut_length;
}
function disableHeatButtons(){
	// $('#t13_lrf_heat_arc_btn').linkbutton('disable');
	// $('#t13_lrf_heat_chem_btn').linkbutton('disable');
	// $('#t13_lrf_heat_event_btn').linkbutton('disable');
	// $('#t13_lrf_heat_pparam_btn').linkbutton('disable');
	$('#t13_lrf_heat_save_btn').linkbutton('disable');
}

function enableHeatButtons(){
	$('#t13_lrf_heat_arc_btn').linkbutton('enable');
	$('#t13_lrf_heat_chem_btn').linkbutton('enable');
	$('#t13_lrf_heat_event_btn').linkbutton('enable');
	$('#t13_lrf_heat_pparam_btn').linkbutton('enable');
	// $('#t13_lrf_heat_save_btn').linkbutton('enable');
}

/** 2. Begin saveT13LRFHeatDetails() ## Button:Save * */
function saveT13LRFHeatDetails(){
	if(validateT13LRFCrewDetailsform()){
		var formValidate = validateT13LRFHeatDetailsform();
		var reladling = document.getElementById('t13_reladling').value;
		var purging_medium, process_control, vessel_car_no;
		if(reladling == 'Y'){
			formValidate = true;
			purging_medium = '';
			process_control = '';
			vessel_car_no = '';
		}else{
			formValidate = validateT13LRFHeatDetailsform();
			if(formValidate){
				purging_medium= $('#t13_purging_medium').combobox('getValue');
				//process_control= $('#t13_process_control').combobox('getValue');
				//vessel_car_no =$("#t13_vessel_car_no").combobox('getValue');
			}
		}
		if(formValidate){
			var cunitId=$('#t13_unit').combobox('getValue');
			var cunitName=$('#t13_unit').combobox('getText');
			var heat_id= $('#t13_heat_id').combobox('getText'); 
			var steelwgt=$('#t13_tap_wgt').textbox('getText'); 
			var stLadleTrackId=document.getElementById('t13_steel_ladleno_id').value;
			var tapTemp=$('#t13_tap_temp').textbox('getText'); 
			var lrfInitialTemp=$('#t13_initial_temp').textbox('getText'); 
			var prevUnit=$('#t13_prev_unit').textbox('getText');
			var aimPsn=document.getElementById('t13_aim_psn_id').value;
			var prodShift=$('#t13_shift').combobox('getValue');
			var heat_plan_id=document.getElementById('t13_plan_no').value;
			var heat_plan_line_id= document.getElementById('t13_plan_line_no').value;
			var targetCasterId= document.getElementById('t13_caster_id').value;
			var trns_si_no=document.getElementById('t13_trns_si_no').value;
			var shift_mgr=$('#t13_shiftMgr').combobox('getValue');
			var lrf_mgr=$('#t13_lrfInCharge').combobox('getValue');
			var main_status="WIP";
			var proc_path=document.getElementById('t13_act_path').value;
			var act_proc_path = proc_path.concat('-'+cunitName);
			var lrf_status;
			var lrf_status_af_vd;
			var unit_status;
			var heatCounter, disp_unit = null;
			if(trns_si_no == ''){
				trns_si_no=0;
			}
			var heatTrackId= document.getElementById('t13_heat_track_id').value;
			var prevHeatTrackId;
			if(prevUnit.match(/VD.*/)){
				heatCounter= parseInt(document.getElementById('t13_heat_cnt').value) + 1;
				unit_status = "WAITING FOR DISPATCH";
				lrf_status="DISPATCHED";
				lrf_status_af_vd = "PROCESSING";
			}else if(prevUnit.match(/CCM.*/) || prevUnit.match(/LRF.*/)){// reprocess
																			// after
																			// heat
																			// mix/transfer
				unit_status="PROCESSING";
				lrf_status="PROCESSING";
				lrf_status_af_vd = "";
				
				if(prevUnit.match(/CCM.*/) && document.getElementById('t13_lrf_entry').value == '0'){
					heatCounter= document.getElementById('t13_heat_cnt').value;
					heatTrackId =  document.getElementById('t13_heat_track_id').value;
				}else{
					heatCounter= parseInt(document.getElementById('t13_heat_cnt').value) + 1;
					prevHeatTrackId = document.getElementById('t13_heat_track_id').value;
					heatTrackId =  null;
				}
			}else if(reladling == 'Y' && trns_si_no == 0){// sent for
															// reladling from
															// EOF
				heatCounter= document.getElementById('t13_heat_cnt').value;
				unit_status="RELADLING";
				lrf_status="RELADLING";
				lrf_status_af_vd = "";
				disp_unit = document.getElementById('t13_lrf_disp_unit').value;
			}else{
				heatCounter= document.getElementById('t13_heat_cnt').value;
				unit_status="PROCESSING";
				lrf_status="PROCESSING";
				lrf_status_af_vd = "";
			}
			var user_role_id_1= shift_mgr;
			var user_role_id_2= lrf_mgr;
			var formData = {
				"lrfHeatDetails":{
				"trns_sl_no":trns_si_no,"sub_unit_id":cunitId,"heat_id":heat_id,"heat_counter":heatCounter,
					"heat_plan_id":heat_plan_id,"heat_plan_line_no":heat_plan_line_id,
					"steel_wgt":steelwgt,"target_caster_id":targetCasterId,"production_shift":prodShift,
					"aim_psn":aimPsn,"tap_temp":tapTemp,"prev_unit":prevUnit,"steel_ladle_no":stLadleTrackId,
					"lrf_initial_temp":lrfInitialTemp,"purge_medium":purging_medium,
					"reladling":reladling,"lrf_dispatch_unit":disp_unit
				},
				"lrfCrewDetList":[{
					"user_role_id":user_role_id_1,"heat_id":heat_id,"heat_counter":heatCounter,"sub_unit_id":cunitId
				},{
					"user_role_id":user_role_id_2,"heat_id":heat_id,"heat_counter":heatCounter,"sub_unit_id":cunitId 
				}],
				"lrfHeatStatus":{
					"heat_track_id":heatTrackId,"main_status":main_status,"act_proc_path":act_proc_path,
					"current_unit":cunitName,"unit_process_status":unit_status,
					"lrf_status":lrf_status,"lrf_status_af_vd":lrf_status_af_vd,"heat_counter":heatCounter,"inActiveHeatTrackId":prevHeatTrackId
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
			 		url: './LRFproduction/LRFHeatSave',
			 		success: function(data) {
			 		    if(data.status == 'SUCCESS') {
							$.messager.alert('LRF Heat Details Info',data.comment,'info');
			 		    	getT13RefreshHotmetalLRFGrid();
			 		    	clearT13HeatHdrform();
			 		    	getT13UpdatedHeatList();
			 		    	enableHeatButtons();
						}else {
							$.messager.alert('LRF Heat Details Info',data.comment,'info');
						}
					},
			 		error:function(){      
						$.messager.alert('Processing','Error while Processing Ajax...','error');
					}
			});
		}
	}
}

/** 2.End saveT13LRFHeatDetails() * */
function getT13UpdatedHeatList(){
	var unit_id=$('#t13_unit').combobox('getValue');
	var url6="./CommonPool/getComboList?col1=trns_sl_no&col2=heat_id&classname=LRFHeatDetailsModel&status=1&wherecol=sub_unit_id="+unit_id+" and lrf_dispatch_unit=NULL and record_status=";
	getDropdownList(url6,'#t13_heat_id');
}

function setT13HeatFormData(){
	var t13_trns_si_no=$('#t13_heat_id').combobox('getValue');
	if(t13_trns_si_no!=null && t13_trns_si_no!=''){
		$.ajax({
		headers: { 
		'Accept': 'application/json',
		'Content-Type': 'application/json' 
		},
		type: 'GET',
		// data: JSON.stringify(formData),
		dataType: "json",
		url: './LRFproduction/getLRFHeatFormDtlsById?trns_sl_no='+t13_trns_si_no,
		success: function(data) {
			 document.getElementById('t13_trns_si_no').value=data.trns_sl_no;
			 document.getElementById('t13_heat_cnt').value=data.heat_counter;
			 document.getElementById('t13_aim_psn_id').value=data.aim_psn;
			 document.getElementById('t13_sub_unit_id').value=data.sub_unit_id;
			 document.getElementById('t13_heat_track_id').value=data.heat_track_id;
			 document.getElementById('t13_steel_ladleno_id').value=data.steel_ladle_no;
			 document.getElementById('t13_plan_no').value=data.heat_plan_id;		
		 	 document.getElementById('t13_plan_line_no').value=data.heat_plan_line_no;	 	   
		 	 $('#t13_recDate').datetimebox('setText',formatDate(data.production_date));
		 	 $('#t13_shift').combobox('setValue',data.production_shift);
		 	 $('#t13_unit').combobox('setValue',data.sub_unit_id);
		 	 $('#t13_unit').textbox('setText',data.sub_unit_name);
			 // $('#t13_heat_id').combobox('setValue',data.trns_sl_no);
			 // $('#t13_heat_id').textbox('setText',data.heat_id);
			 $('#t13_aim_psn').textbox('setValue',data.psn_no);
			 $('#t13_tar_caster').textbox('setValue',data.target_caster_name);
			 $('#t13_prev_unit').textbox('setValue',data.prev_unit);
			 $('#t13_laddle_no').textbox('setValue',data.steel_ladle_name);
			 $('#t13_tap_temp').textbox('setValue',data.tap_temp);
			 $('#t13_tap_wgt').textbox('setValue',data.steel_wgt);
			 // $('#t13_plan_no').textbox('setValue',data.heat_plan_id);
			 // $('#t13_plan_line_no').textbox('setValue',data.heat_plan_line_no);
			 $('#t13_initial_temp').textbox('setValue',data.lrf_initial_temp);
			 $('#t13_purging_medium').textbox('setValue',data.purge_medium);
			 $('#t13_plan_cut_length').textbox('setValue',data.plan_cut_length);
			 //$('#t13_process_control').textbox('setValue',data.process_control);		  
			 //$('#t13_vessel_car_no').textbox('setValue',data.vessel_car_id);

			 setT13CrewDetails();
			 getOtherData();
		  },
		error:function(){ 
			$.messager.alert('Info','Heat Number does not exists','Info');
		}
		});
		}else{
			$.messager.alert('Info','Please Enter Heat Number','Info');
		}
}

function getOtherData(){
	var unit_id=$('#t13_unit').combobox('getText');
	var url5="./CommonPool/getComboList?col1=lookup_id&col2=lookup_value&classname=LookupMasterModel&status=1&wherecol=lookup_type='LRF_PURGING_MEDIUM' and lookup_status=";
	//var url6="./CommonPool/getComboList?col1=lookup_id&col2=lookup_value&classname=LookupMasterModel&status=1&wherecol=lookup_type='LRF_PROCESS_CONTROL' and lookup_status=";
	//var url7="./CommonPool/getComboList?col1=lookup_id&col2=lookup_value&classname=LookupMasterModel&status=1&wherecol=lookup_type='VESSEL_CAR'  and lookup_status=";
	getDropdownList(url5,'#t13_purging_medium');	
	//getDropdownList(url6,'#t13_process_control');	
	//getDropdownList(url7,'#t13_vessel_car_no');	
}

$('#t13_heat_id').combobox({
	onSelect: function(){
		setT13HeatFormData();
		// setAimPsnFormData();
		$("#T13ladleDetailsId").hide();
},onChange: function(){
	var t13_trns_sl_no=$('#t13_heat_id').combobox('getValue');
	if(t13_trns_sl_no>0){
		$('#t13_lrf_heat_save_btn').linkbutton("disable");
	}
	var t13_sub_unit_id=$('#t13_unit').combobox('getValue');
	if((t13_sub_unit_id!=null && t13_sub_unit_id!='')){
		getT13UpdatedHeatList();
		if(!(t13_trns_sl_no!=null && t13_trns_sl_no!='')){
			document.getElementById('t13_trns_si_no').value='';
			document.getElementById('t13_aim_psn_id').value=''; 
			document.getElementById('t13_heat_cnt').value='';
			document.getElementById('t13_caster_id').value='';
			document.getElementById('t13_sub_unit_id').value='';
			$('#t13_heat_plan_id').numberbox('setValue','');	 
			$('#t13_aim_psn').textbox('setValue','');
			$('#t13_unit').textbox('setValue','');
			//$('#t13_vessel_car_no').textbox('setValue','');
			$('#t13_heat_plan_line_no').textbox('setValue','');
			$('#t13_laddle_no').textbox('setValue','');
			$('#t13_tap_wt').numberbox('setValue','');
			$('#t13_tap_temp').numberbox('setValue','');
		}
	}else{
		 $('#t13_heat_id').textbox('setValue','');
		 $('#t13_heat_id').textbox('setText','');
		 $.messager.alert('Info','Please select Unit in Crew Detail Grid','info');
	 }
	}
});

function setT13CrewDetails(){
	var heat_no=$('#t13_heat_id').combobox('getText');
	var heat_counter=document.getElementById('t13_heat_cnt').value;
	var unit_id=$('#t13_unit').combobox('getValue');

	if(heat_no!=null && heat_no!=''){
		$.ajax({
		headers: { 
		'Accept': 'application/json',
		'Content-Type': 'application/json' 
		},
		type: 'GET',
		// data: JSON.stringify(formData),
		dataType: "json",
		url: './EOFproduction/getEOFCrewDtlsbyHeatNo?Heat_no='+heat_no+'&unit_id='+unit_id+'&heat_counter='+heat_counter,
		success: function(data) {
			for(var i=0;i<data.length;i++){
				if(data[i].userRoleMapMstrMdl.lookupMasterModel.lookup_code=='LRF_SHIFT_INCHARGE'){
					 $('#t13_shiftMgr').combobox('setText',data[i].userRoleMapMstrMdl.appUserAccountDetails.user_name);
					 $('#t13_shiftMgr').combobox('setValue',data[i].user_role_id);
				}
				if(data[i].userRoleMapMstrMdl.lookupMasterModel.lookup_code=='LRF_ENGINEER'){
					$('#t13_lrfInCharge').combobox('setText',data[i].userRoleMapMstrMdl.appUserAccountDetails.user_name);
					$('#t13_lrfInCharge').combobox('setValue',data[i].user_role_id);
				}		
			}
		},
		error:function(){ 
			$.messager.alert('Info','Heat Number does not exists','Info');
		}
		});
	}else{
		$.messager.alert('Info','Please Enter Heat Number','Info');
	}
}

function clearT13HeatHdrform(){
	$('#t13_heat_hdr_form_id').form('clear');
}

function getT13RefreshHotmetalLRFGrid(){
	$.ajax({
  		headers: { 
  		'Accept': 'application/json',
  		'Content-Type': 'application/json' 
  		},
  		type: 'GET',
  		// data: JSON.stringify(formData),
  		dataType: "json",
  		url:"./EOFproduction/getHeatsWaitingForLrfProcess?CURRENT_UNIT=LRF&UNIT_PROCESS_STATUS=WAITING FOR PROCESSING" ,
  		success: function(data) {
  			 $('#t13_LRF_production_tbl_id').datagrid('loadData', data);
  		 },
  		error:function(){      
  			$.messager.alert('Processing','Error while Processing Ajax...','error');
  		}
	});
}

function validateT13LRFCrewDetailsform(){
    return $('#t13_crew_form_id').form('validate');
}

function validateT13LRFHeatDetailsform(){
    return $('#t13_heat_hdr_form_id').form('validate');
}

function t13ValidateCrewDetails(){
	return $('#t13_crew_form_id').form('validate');	
}

/** 3.Begin PSN Document Start * */
function t14PSNDocsView(){
	// PSNDocsInit();
	var psn_hdr_sl_no = document.getElementById('t13_aim_psn_id').value;
	var psn_no = $('#t13_aim_psn').combobox('getText');
	if (psn_hdr_sl_no != null && psn_hdr_sl_no != '') {
		$('#t14_PSN_Docs_form_div_id').dialog({
			modal : true,
			cache : true
		});
		$('#t14_PSN_Docs_form_div_id').dialog('open').dialog('center').dialog(
					'setTitle', 'PSN Document Details Form');
		$('#t7_eof_Chemistry_form_id').form('clear');
		t14PSNDocsInit();
		// $('#psn_desc_docs').textbox('setText', psn_no);
		// $('#psn_no_docs').textbox('setText', psn_hdr_sl_no);
		$('#t14_rep_psn_desc').textbox('setText', psn_no);		
		document.getElementById('t14_psn_no').value=psn_hdr_sl_no;
		refreshT14PSNDocs(); 		
	} else {
			$.messager.alert('PSN DOC Details Info',
					'Please Select Heat & Check PSN ...!', 'info');
	}
}

function t14PSNDocsInit(){
	$('#t14_PSN_Docs_tbl_id').edatagrid({
	});
	$('#t14_PSN_Docs_tbl_id').edatagrid({
		onSuccess : function(index, row) {
		refreshT14PSNDocs();
	},
	onError : function(index, row) {
		alert("Error In " + row.doc_sl_no);
	}
	});
}

function refreshT14PSNDocs(){
	var psn_hdr_sl_no = document.getElementById('t13_aim_psn_id').value;
	var psn_no = $('#t13_aim_psn').combobox('getText');
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
		$('#t14_PSN_Docs_tbl_id').edatagrid('loadData', data);
	},
	error : function() {
		$.messager.alert('Processing', 'Error while Processing Ajax...',
						'error');
	}
	});
}

function t14viewPSNFile(value,row){   	
	var href = './heatProcessEvent/PSNDocsView?doc_sl_no='+row.doc_sl_no;

	return '<a target="_blank" href="' + href + '">View Detail</a>';
}

$(window).load(setTimeout(applyT14Filter,1));  // 1000 ms = 1 second.
   
function applyT14Filter(){
	$('#t14_PSN_Docs_tbl_id').datagrid('enableFilter');		 
} 
  
function cancelT14PSNDocs(){
	refreshT14PSNDocs(); 
}

/** PSN Document Screen End * */
/** Process Parameter Screen Start * */

function LRFProcessParamOpen(){ 
	var subunitid=document.getElementById('t13_sub_unit_id').value;
	var t13_trns_si_no=document.getElementById('t13_trns_si_no').value;
	setLrfElectrodeTime(t13_trns_si_no);
    $('#t19_process_param_tbl_id').edatagrid({
     // saveUrl: './scrapEntry/DtlsSaveOrUpdate',
    });
    $('#t19_electrode_usage').edatagrid({
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
        updateUrl : './LRFproduction/LRFelectrodeSaveOrUpdate?sub_unit_id='+subunitid+" &trans_si_no="+t13_trns_si_no+"&redirect=1",
        onSuccess : function(index, row) {
        	refresht19lrfElectrodeData();
		},

		onBeforeEdit:function(index,row){ 
// row.startDate = formatDate(row.startDate);
// row.endDate = formatDate(row.endDate);
// $('#t19_electrode_usage').datagrid('selectRow', index);
       },onEndEdit:function(index,row){
           var rows=$('#t19_electrode_usage').edatagrid('getRows');
           if(rows[index].isAdded=="Y" && rows[index].isAdjusted=="Y"){
    		   rows[index].isAdded="";
    		   rows[index].isAdjusted="";
    		   $.messager.alert('Processing','Electrode cannot be changed and adjusted at the same time','info');
    		   console.log("onEndEdit");
    		   $('#t19_electrode_usage').edatagrid('cancelRow')
    		   return false;
    	   }
           else{
        	   var starttime=$('#t191_electrode_start_time').textbox('getText')
               var endtime=$('#t191_electrode_end_time').textbox('getText')
               if(endtime!=null && endtime!='' &&starttime!=null&&starttime!='' ){
            	   $.ajax({
            		   headers: { 
            			   'Accept': 'application/json',
    	               		'Content-Type': 'application/json' 
            		   },
            		   type: 'POST',
                 		// data: JSON.stringify(formData),heatid,heat_cntr,subunitid
            		   dataType: "json",
            		   url: './LRFproduction/LRFelectrodeSaveOrUpdate?sub_unit_id='+subunitid+" &trans_si_no="+t13_trns_si_no+"&redirect=0&electrode_start_time="+starttime+"&electrode_end_time="+endtime,
            		   success: function(data) {
            			   $.messager.alert('LRF Electrode details info','Data Saved Successfully','info');
            			   // $('#t191_electrode_start_time').datetimebox('setText',formatDate(data.electrodeStartTime));
            			   // $('#t191_electrode_end_time').datetimebox('setText',formatDate(data.electrodeEndTime));
            		   },
            		   error:function(){      
            			   $.messager.alert('Processing','Error while Processing Ajax...','error');
            		   }
            	   });
               }else{
             	  $.messager.alert('Processing','Start & End time must be entered....','info');
               }
           }
    	   
       } ,
       onAfterEdit:function(index,row){
       }
		// saveUrl : './LRFproduction/LRFelectrodeSaveOrUpdate'
    });
    assignRowChangeDetectionListener('t19_process_param_form_div_id','t18_lrf_events_tbl_id');
   	closeActionBindEvt('t19_process_param_form_div_id');
 
   	$('#t19_process_param_tbl_id').datagrid({
   		onBeforeEdit:function(index,row){ 
   			row.process_date_time = formatDate(row.process_date_time);
   			$('#t19_process_param_tbl_id').datagrid('selectRow', index);
   		},onEndEdit:function(index,row){
   			$('#t19_process_param_tbl_id').datagrid('selectRow', index);
   			var dt=(row.process_date_time).split(" ");
   			var proc_date=new Date(commonGridDtfmt(dt[0],dt[1]));
   			var time=proc_date.getTime();
   			row.process_date_time =time;
   		}, onBeginEdit:function(index,row){
   			var editors = $('#t19_process_param_tbl_id').datagrid('getEditors', index);
    	    var minval=row.param_value_min;
    	    var maxval=row.param_value_max;
    	    if((minval == null || minval =='')&& (maxval == null ||maxval =='')){
    	    	return false;
    	    }else{
    	    	var actVal=$(editors[0].target);
    	    	actVal.textbox({
    	    		onChange:function(){
        	        $("#t19_process_param_form_div_id").attr("data-change","1");
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
    	    }// else
   		}
        });
}
    
function setLrfElectrodeTime(trans_si_no){
	$.ajax({
		headers: { 
      	'Accept': 'application/json',
      	'Content-Type': 'application/json' 
      	},
      	type: 'GET',
      	// data: JSON.stringify(formData),heatid,heat_cntr,subunitid
      	dataType: "json",
      	url: './LRFproduction/getLRFHeatDtlsById?trns_sl_no='+trans_si_no,
      	success: function(data) {
      		$('#t191_electrode_start_time').datetimebox('setText',formatDate(data.electrodeStartTime));
      		$('#t191_electrode_end_time').datetimebox('setText',formatDate(data.electrodeEndTime));
      	  },
      	error:function(){      
      		$.messager.alert('Processing','Error while Processing Ajax...','error');
      	  }
      	});
	}

function LRFt19ProcessParam(){
	var t13_trns_si_no=document.getElementById('t13_trns_si_no').value;
	if(t13_trns_si_no!=''){
  		$('#t19_process_param_form_div_id').dialog({modal:true,cache: true});
  	    $('#t19_process_param_form_div_id').dialog('open').dialog('center').dialog('setTitle','Process Parameters Details Form');
  	    var heatId =$('#t13_heat_id').textbox('getText');
  	    var heatcnt = document.getElementById('t13_heat_cnt').value;
    	var aimpsn =$('#t13_aim_psn').textbox('getText');
    	LRFProcessParamOpen();
  	    $('#t19_heat_no').textbox('setText',heatId);
  	    $('#t19_aim_psn').textbox('setText',aimpsn);
  	    $('#t191_heat_no').textbox('setText',heatId);
	    $('#t191_aim_psn').textbox('setText',aimpsn);
  	    gett19ProcParamDtlsGrid();
  		}else{
  			$.messager.alert('Information','Please Select Heat...!','info');
  		}
  	}// end

function gett19ProcParamDtlsGrid(){
	var heatid=$('#t13_heat_id').combobox('getText');
	var heat_cntr=document.getElementById('t13_heat_cnt').value;
	var subunitid=document.getElementById('t13_sub_unit_id').value;
	var psn_no = document.getElementById('t13_aim_psn').value;
	
	if (psn_no != null && psn_no != '') {	

	 $.ajax({
     		headers: { 
     		'Accept': 'application/json',
     		'Content-Type': 'application/json' 
     		},
     		type: 'GET',
     		// data: JSON.stringify(formData),heatid,heat_cntr,subunitid
     		dataType: "json",
     		url: './heatProcessEvent/getHeatHeatProcParamDtls?heatid='+heatid+'&heat_cntr='+heat_cntr+'&subunitid='+subunitid+ '&psn_no=' + psn_no,
     		success: function(data) {
     			 $('#t19_process_param_tbl_id').datagrid('loadData', data);
     		},

     		error:function(){      
     			$.messager.alert('Processing','Error while Processing Ajax...','error');
     		}
     		});
	 }
	 refresht19lrfElectrodeData();
}  
    
function refresht19lrfElectrodeData(){
	var sub_unit=$("#t13_unit").combobox("getValue");
	 // alert($("#t13_unit").combobox("getValue"));
	 if(sub_unit!=null && sub_unit!=""){
		 var t4_trns_si_no2 =document.getElementById('t13_trns_si_no').value;
    	 setLrfElectrodeTime(t4_trns_si_no2);
		 $.ajax({
	     		headers: { 
	     		'Accept': 'application/json',
	     		'Content-Type': 'application/json' 
	     		},
	     		type: 'GET',
	     		// data: JSON.stringify(formData),heatid,heat_cntr,subunitid
	     		dataType: "json",
	     		url: './LRFproduction/getElectrodeTrans?sub_unit_id='+sub_unit +"&trans_si_no="+t4_trns_si_no2,
	     		success: function(data) {
	     			 $('#t19_electrode_usage').datagrid('loadData', data);     			
	     		},
	     		error:function(){      
	     			$.messager.alert('Processing','Error while Processing Ajax...','error');
	     		}
	     		});
	 	} 
}

function cancelt19ProcParamDtls(){
	gett19ProcParamDtlsGrid();
}

function savet19ProcessParamDtls(){
	var heatid=$('#t13_heat_id').combobox('getText');
	var heat_cntr=document.getElementById('t13_heat_cnt').value;
	var rows = $('#t19_process_param_tbl_id').datagrid('getRows');

    for(var i=0; i<rows.length; i++){
    	$('#t19_process_param_tbl_id').datagrid('endEdit', i);
    }
    var proc_param_rows = $('#t19_process_param_tbl_id').datagrid('getRows');
    var grid_arry = '';
    for(var i=0; i<proc_param_rows.length; i++){
    	if(proc_param_rows[i].is_mandatory !=null && proc_param_rows[i].is_mandatory == 'Y' && (proc_param_rows[i].param_value_actual ==null || proc_param_rows[i].param_value_actual ==''))
    		{
    		$.messager.alert('Process Parameters Details Info','MANDATORY ELEMENTS MUST BE ENTERED','info');
    		return;
    		}
    	  if(proc_param_rows[i].param_value_actual !=null && proc_param_rows[i].param_value_actual !='' )
    			{
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
    /** Process Param Screen End * */

  /** Begin Arching Details ## Button:Arching Details * */

$('#t15_arc_end_date').datetimebox({
    // value: (formatDate(new Date()))
	onSelect:function(){
		// restrictDateRange('t15_arc_start_date','t15_arc_end_date',0);
		var dflag=restrictDateAndHourRange('t15_arc_start_date','t15_arc_end_date',2,'Arc end date should not be more than 2 Hours from Arc Start date.');
    	if(dflag == false){
	    		$('#'+tdate1).datetimebox('setValue','');
    	}
	}
});

	function T15OpenArchingDetails(){
	  $('#t15_lrf_arc_add_tbl_id').edatagrid({
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
			onBeforeEdit:function(index,row){ 
			  row.addition_date_time = formatDate(row.addition_date_time);
			  $('#t15_lrf_arc_add_tbl_id').datagrid('selectRow', index);
		  },onEndEdit:function(index,row){
			  $('#t15_lrf_arc_add_tbl_id').datagrid('selectRow', index);
	    	  var dt=(row.addition_date_time).split(" ");
	    	  var cons_time=new Date(commonGridDtfmt(dt[0],dt[1]));
	    	  var time=cons_time.getTime();
	    	  row.addition_date_time =time;
		  }
		});
	    clearT15ArcDet();
	    resetArcDates();
		var heat_no=$('#t13_heat_id').combobox('getText');
		var heat_cnt=document.getElementById('t13_heat_cnt').value;
		var unit=$('#t13_unit').combobox('getText');
		var unit_id=$('#t13_unit').combobox('getValue');
		// heat_cnt=1;
		if((heat_no!=null && heat_no!='') && (heat_cnt!=null && heat_cnt!='')){
 		$('#t15_arc_det_form_div_id').dialog({modal:true,cache: true});
 	    $('#t15_arc_det_form_div_id').dialog('open').dialog('center').dialog('setTitle','Arcing Details Entry Form ');
 	    $('#t15_heat_no').textbox('setText',heat_no);
 	    $('#t15_unit').textbox('setText',unit);
 	    // $('#t15_heat_cnt').textbox('setText',heat_cnt);
 		// getDropdownList(url1,'#t7_analysis_type');
 	    // getT15SampleNos(heat_no,heat_cnt);
 	    getT15ArcDetGrid();
 	    callT15DropDowns();
 	    $('#t15_arc_det_form_div_id').attr("data-change","0")
 	    closeActionBindEvt('t15_arc_det_form_div_id' );
 	    // viewT15ArcAdditions();
 	    // .setT15GridData();
 	    // refreshT15PSNDocs();
	}else{
		$.messager.alert('Arcing Details Info','Please Select Heat no','info');
	}
  }

  function resetArcDates(){
	  var current_date=new Date();
	  var constructed_datefrom=addZero(current_date.getDate())+"/"+addZero(current_date.getMonth()+1)+"/"+current_date.getFullYear()+" "+addZero(current_date.getHours())+":"+addZero(current_date.getMinutes());
	  var constructed_dateto=addZero(current_date.getDate())+"/"+addZero(current_date.getMonth()+1)+"/"+addZero(current_date.getFullYear())+" "+addZero(current_date.getHours())+":"+addZero((current_date.getMinutes()+10));

	  $('#t15_arc_start_date').datetimebox({
		  value: constructed_datefrom,
		  required: true,
		  showSeconds: false
		});
	  $('#t15_arc_end_date').datetimebox({
		  value: constructed_dateto,
		  required: true,
		  showSeconds: false
		});
  }

  function loadDataDummy(){
	 	// document.getElementById('t15_lrf_arc_disp_div_id').style.display =
		// 'none';
	  document.getElementById('t15_lrf_arc_disp_div_id').style.visibility="visible";
	  var unit_id=$('#t13_unit').combobox('getValue');
	  var heatId=$('#t13_heat_id').combobox('getText');
	  var heatCnt=document.getElementById('t13_heat_cnt').value;
  	$.ajax({
	   		headers: { 
	   		'Accept': 'application/json',
	   		'Content-Type': 'application/json' 
	   		},
	   		type: 'GET',
	   		// data: JSON.stringify(formData),
	   		dataType: "json",
	   		url: "./LRFproduction/getLRFArcAdditionsTemp?unit_id="+unit_id+"&heatId="+heatId+"&heatCnt="+heatCnt,
	   		success: function(data) {
	   		  $('#t15_lrf_arc_disp').datagrid('loadData', data);
	   		  },
	   		error:function(){      
	   			$.messager.alert('Processing','Error while Processing Ajax...','error');
	   		  }
	   		});  
  }
  function getT15ArcDetGrid(){
  	var heatId=document.getElementById('t13_trns_si_no').value;
	var heatCnt=document.getElementById('t13_heat_cnt').value;
	var unit_id=$('#t13_unit').combobox('getValue');
  	$.ajax({
   		headers: { 
   		'Accept': 'application/json',
   		'Content-Type': 'application/json' 
   		},
   		type: 'GET',
   		// data: JSON.stringify(formData),
   		dataType: "json",
   		url: "./LRFproduction/getLRFArcAdditions?lookup_code=LRF_ARC_ADDITIONS&sub_unit_id="+unit_id,
   		success: function(data) {
   			 $('#t15_lrf_arc_add_tbl_id').datagrid('loadData', data);
   		  },
   		error:function(){      
   			$.messager.alert('Processing','Error while Processing Ajax...','error');
   		  }
   		});
  }

  function clearT15ArcDet(){
	  getT15ArcDetGrid();
	  var data1=new Array();
	  document.getElementById('t15_arc_sl_no').value='';
	  // $('#t15_temp').numberbox('setValue','');
	  $('#t15_consumption').numberbox('setValue','');
	  $('#t15_arc_start_date').datetimebox('setValue','');
	  $('#t15_arc_end_date').datetimebox('setValue','');
	  $('#t15_lrf_arc_disp').datagrid('loadData', data1);
	  document.getElementById('t15_lrf_arc_disp_div_id').style.visibility="hidden";
  }
  var initialState;
  function setT15ArcHdrDetBySampleNo(arc_sl_no){
	  if(arc_sl_no!=null && arc_sl_no >0){
  		 $.ajax({
  		headers: { 
  		'Accept': 'application/json',
  		'Content-Type': 'application/json' 
 		},
  		type: 'GET',
  		// data: JSON.stringify(formData),
  		dataType: "json",
  		url: './LRFproduction/getArcAdditionsHdrDtlsBySample?arc_sl_no='+arc_sl_no,
  		success: function(data) {
  			document.getElementById('t15_arc_sl_no').value=data.arc_sl_no;
  			// document.getElementById('t15_addition_type').value=data.addition_type;
  			 // $('#t15_addition_type').combobox('setValue',data.addition_type);
  			 $('#t15_arc_start_date').datetimebox({ 	    	   		
  				 value: (formatDate(data.arc_start_date_time)) 
  			 });
  			$('#t15_arc_end_date').datetimebox({
        	    value: (formatDate(data.arc_end_date_time)) 
        	});
  			// $('#t15_temp').numberbox('setValue',data.bath_temp);
  			$('#t15_consumption').numberbox('setValue',data.power_consumption); 		
// formstate.init_state_value = $form.serialize();
     		assignChangesDetectLister('t15_arc_det_form_div_id');
  		  },
  		error:function(){ 
  			$.messager.alert('Error',data.error,'Info');
  		}
  		});
  	}else{
  		$('#t15_arc_start_date').datetimebox({
      	    value: (formatDate(new Date())) 
      	});
		$('#t15_arc_end_date').datetimebox({
    	    value: (formatDate(new Date())) 
    	});

		clearT15AllData();
  		}
  }
  
  var formstate = {
		  init_state : ""
  };

	Object.defineProperty( formstate, 'init_state', {
	    get : function(){ return init_state; },
	    set : function( value ){ this.init_state = value; }
	} );

  function getT15ArcDetGridBySampleNo(sampleNoKeyVal){
	var heatId=$('#t13_heat_id').combobox('getText');
	var heatCnt=document.getElementById('t13_heat_cnt').value;
  	$.ajax({
   		headers: { 
   		'Accept': 'application/json',
   		'Content-Type': 'application/json' 
   		},
   		type: 'GET',
   		// data: JSON.stringify(formData),
   		dataType: "json",
   		url: "./LRFproduction/getLRFArcAdditionsBySampleNo?arc_sl_no="+sampleNoKeyVal+"&heat_id="+heatId+"&heat_cnt="+heatCnt,
   		success: function(data) {
   			 $('#t15_lrf_arc_add_tbl_id').datagrid('loadData', data);
   		  },
   		error:function(){      
   			$.messager.alert('Processing','Error while Processing Ajax...','error');
   		  }
   		});
  }
  function saveT15ArcAdditions(){
	var heatId=$('#t13_heat_id').combobox('getText');
	var heatCnt=document.getElementById('t13_heat_cnt').value;
	var arc_sl_no=document.getElementById('t15_arc_sl_no').value;
	var heatId=$('#t13_heat_id').combobox('getText');
 	var arc_start_date = $('#t15_arc_start_date').datetimebox('getValue');
 	var arc_end_date = $('#t15_arc_end_date').datetimebox('getValue');
	// var temp=$('#t15_temp').numberbox('getValue');
	var consumption=$('#t15_consumption').numberbox('getValue');
	// var addition_type=$('#t15_addition_type').combobox('getValue');
	var prevUnit=$('#t13_prev_unit').textbox('getText');	

	if(arc_sl_no == null || arc_sl_no == ''){
		arc_sl_no = 0;
	}

  	var tab1_rows = $('#t15_lrf_arc_add_tbl_id').datagrid('getRows');
  	for(var i=0; i<tab1_rows.length; i++){
  	   $('#t15_lrf_arc_add_tbl_id').datagrid('endEdit', i);
  	}

  	// var tab1_rows = $('#t15_lrf_arc_add_tbl_id').datagrid('getSelections');

  	if(tab1_rows.length>0){
  		var cons_sl_no = 0;
  		var grid_arry = '';
  		
  		for(var i=0; i<tab1_rows.length; i++){
  			if((tab1_rows[i].consumption_qty !=null && tab1_rows[i].consumption_qty !='')&&
  					(tab1_rows[i].addition_date_time !=null && tab1_rows[i].addition_date_time !='')){
  				var consdate= formatDate(tab1_rows[i].addition_date_time);
  				grid_arry += tab1_rows[i].material_id+'@'+tab1_rows[i].sap_matl_id+'@'+tab1_rows[i].valuation_type+'@'+tab1_rows[i].consumption_qty+'@'+consdate+'@'+tab1_rows[i].cons_sl_no+'@'+tab1_rows[i].material_type+'SIDS';
  			}
  		}

  		var formData = {
   			"arcDetails" : {
   				"heat_id": heatId,"heat_counter":heatCnt ,"arc_sl_no":arc_sl_no,"power_consumption":consumption,"prev_unit":prevUnit
   			},
   			"conDetails" : {
   				"arc_grid_arry": grid_arry,"heat_id": heatId,"heat_counter":heatCnt 
   			}
   		};

  		$.ajax({
		headers: { 
		'Accept': 'application/json',
		'Content-Type': 'application/json' 
		},
		type: 'POST',
		data: JSON.stringify(formData),
		// data: rows,
		dataType: "json",
		url: './LRFproduction/SaveOrUpdate?arc_start_date='+arc_start_date+'&arc_end_date='+arc_end_date,

		success: function(data) {
		    if(data.status == 'SUCCESS')	{
		    	$.messager.alert('LRF Arc Additions Details Info',data.comment,'info');
		    	cancelT15ArcDet();
		    	// getT15ArcDetGridBySampleNo(arc_sl_no);
		    	// getT15SampleNos();
		    	}else {

		    		$.messager.alert('LRF Arc Additions Details Info(E)',data.comment,'info');

		    	}

		  },

		error:function(){      

			$.messager.alert('Processing','Error while Processing Ajax...','error');

		  }

		});

  	}else{

  		$.messager.alert('LRF Arc Additions Details Info','No Arcing Additions Done','info');

  	}

  	

  }

  function cancelT15ArcDet(){

	  getT15ArcDetGrid();

	  clearT15AllData();

	  

  }

  function clearT15AllData(){

	document.getElementById('t15_arc_sl_no').value='';

	// $('#t15_temp').numberbox('setValue','');

	$('#t15_consumption').numberbox('setValue','');

	$('#t15_arc_start_date').datetimebox('setValue','');

	$('#t15_arc_end_date').datetimebox('setValue','');

	

  }

  

  function callT15DropDowns()

  {

  	var url3="./CommonPool/getComboList?col1=lookup_id&col2=lookup_value&classname=LookupMasterModel&status=1&wherecol=lookup_type='LRF_ADDITION_TYPE' and lookup_status=";

  	// getDropdownList(url3,'#t15_addition_type');

  	// $('#t15_addition_type').combobox('setValue',201);

  }
  /** End Arching Details ## Button:Arching Details * */

  /** Chemical Details Screen Start * */
  function T17ChemistryDtls(){
	  var heat_counter=document.getElementById('t13_heat_cnt').value;
  	  var t13_trns_sl_no=$('#t13_heat_id').combobox('getValue');
      if(t13_trns_sl_no!='' && t13_trns_sl_no!=null && heat_counter>0){
         	$('#t17_lrf_Chemistry_form_div_id').dialog({modal:true,cache: true});
     	    $('#t17_lrf_Chemistry_form_div_id').dialog('open').dialog('center').dialog('setTitle','Chemical Details Entry Form');
     	    $('#t17_lrf_Chemistry_form_id').form('clear');
     	    var heatId = $('#t13_heat_id').combobox('getText'); //$('#t13_heat_id').textbox('getText');
     	    var heatcnt = document.getElementById('t13_heat_cnt').value;
    	    var aimpsn =$('#t13_aim_psn').textbox('getText');

    	    T17ChemistryDtlsOpen();
    	    $('#t17_spectro').hide();
    	    $('#t17_heat_no').textbox('setValue',heatId);
    	    $('#t17_aim_psn').textbox('setValue',aimpsn);
    	    $('#t17_sample_date_time').datetimebox({
         	    value: (formatDate(new Date()))      	   
         	});
    	    getT17Dropdowns();   	    
    	    $.ajax({
    	   		headers: { 
    	   		'Accept': 'application/json',
    	   		'Content-Type': 'application/json' 
    	   		},
    	   		type: 'GET',
    	   		// data: JSON.stringify(formData),
    	   		dataType: "json",
    	   		url: "./LRFproduction/LRFLiftChem",
    	   		success: function(data) {
    	   			document.getElementById('t17_chem_level').value = data.comment;
    	   			refreshT17Chem();
    	   		},
    	   		error:function(){      
    	   			$.messager.alert('Processing','Error while Processing Ajax...','error');
   	   		  	}
    	   	});
  		}else{
  			$.messager.alert('Information','Please Select Heat...!','info');
  		}
      $('#t17_lrf_Chemistry_tbl_id').datagrid('hideColumn', 'remarks'); // hide
																		// spectro
																		// lab
																		// remarks
																		// column
																		// initially
																		// @
																		// t17_lrf_Chemistry_tbl_id
  }
  
  function T17ChemistryDtlsOpen(){ 
	  	var minmax_flag = false, color_flag = false, rej_flag = '';
	  	$('#t17_lrf_Chemistry_tbl_id').edatagrid({
	  		// saveUrl: './scrapEntry/DtlsSaveOrUpdate',
	  	});
  	    $('#t17_lrf_Chemistry_tbl_id').datagrid({  	      	 
  	    	  onBeforeEdit:function(index,row){ 
  	    		  $('#t17_lrf_Chemistry_tbl_id').datagrid('selectRow', index);
  	          },
  	          onBeginEdit:function(index,row){
  	        	color_flag = false;
  	        	var editors = $('#t17_lrf_Chemistry_tbl_id').datagrid('getEditors', index); 	        	
  	          	var minval=row.min_value;
  	          	var maxval=row.max_value;

  	          	var actVal=$(editors[0].target);
  	          	if ((minval == null || minval == '')
						&& (maxval == null || maxval == '')) {
  	          		return false;
  	          	}else{
  	          		actVal.textbox({onChange : function() {
  	          		var aVal = actVal.textbox('getText');
  	          		if ((aVal != null && aVal != '') && (minval != null && minval != '') && (maxval != null && maxval != '')) {
  	          			minmax_flag = validateMinMax(aVal, minval, maxval); 	          			
  	          			if (!minmax_flag) {
  	          				$.messager.alert('Information', 'Actual value ' + aVal + ' should be in between Min '
								+ minval + ' & Max '+ maxval+ ' Values...!','info');
  	          				color_flag = true;
  	          				rej_flag = index;
  	          				setDefaultCustomComboValues('t17_sample_result', 'REJECT', $('#t17_sample_result').combobox('getData'));
  	          			}else{
  	          				color_flag = false;
  	          				if(rej_flag != '' && rej_flag == index){
  	          					setDefaultCustomComboValues('t17_sample_result', 'OK', $('#t17_sample_result').combobox('getData'));
  	          				}
  	          			}
  	          		}
  	          		}});
  	          	}
  	          },
  	          rowStyler:function(index, row) {
  				if (color_flag) {
  					return 'background-color:#ffee00;color:red;';
  				}
  			 },
  			 onEndEdit : function(index, row) {
  				$('#t17_lrf_Chemistry_tbl_id').datagrid('selectRow', index);	
  			 }
  	    });  	    
  }

  function getT17Dropdowns(){
      var url1="./CommonPool/getComboList?col1=lookup_id&col2=lookup_value&classname=LookupMasterModel&status=1&" +
	    "wherecol=lookup_type='CHEM_LEVEL' and lookup_value in ('LRF_INITIAL_CHEM','LRF_AVD_CHEM','LRF_CHEM','LRF_LIFT_CHEM') and lookup_status=";
      var url2="./CommonPool/getComboList?col1=lookup_id&col2=lookup_value&classname=LookupMasterModel&status=1&" +
  	    "wherecol=lookup_type='CHEM_TEST_RESULT' and lookup_status=";
      // getDropdownList(url1,'#t17_analysis_type');
      getDropdownList(url2,'#t17_sample_result');
  }

  $("#t17_analysis_type").combobox({
    	onSelect:function(record) {
    		$('#t17_sample_no').combobox('setValue','');
    		refreshT17Chem();
    	}
  });

  function refreshT17Chem(){
	  $('#t17_sample_temp').numberbox('setValue', '');
	  $('#t17_remarks').textbox('setText', '');
	  document.getElementById('t17_sample_si_no').value = '0';
	  setDefaultCustomComboValues('t17_sample_result',
				'OK', $('#t17_sample_result').combobox(
					'getData'));
	  getT17ChemistryDtlsGrid();
	  getT17ChemSampleDtls();
	  $('#t17_final_result_btn_id').linkbutton('enable');
	  $('#t17_save_chem_btn_id').linkbutton('enable');
	  $('#t17_close_chem_btn_id').linkbutton('enable');
	  $('#t17_getSample_btn').linkbutton('enable');
  }

  // Final Result Datagrid t17_lrf_chem_samp_tbl_id
  function getT17ChemSampleDtls() {
	  var heat_id = $('#t13_heat_id').combobox('getText');
	  // var analysis_id = $('#t17_analysis_type').combobox('getValue');
	  var analysis_id = 0;
	  var sub_unit_id = document.getElementById('t13_sub_unit_id').value;
	  var heat_counter=	document.getElementById('t13_heat_cnt').value;
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
  				var tableData=[];
  				for(var i=0;i<data.length;i++){
  					var sub_unit = data[i].subUnitMasterDtls.sub_unit_name;
  					if(sub_unit.match(/LRF.*/)){
  						tableData.push(data[i]); 
  					}
  				}
  				$('#t17_lrf_chem_samp_tbl_id').datagrid('loadData', tableData);
  			},
  			error : function() {
  				$.messager.alert('Processing',
  						'Error while Processing Ajax...', 'error');
  			}
  		});
  	}
  }

  function viewT17SampleDtls(value, row) {
	  /*
		 * return '<a href="#" onclick="viewT17ChemDtls(\'' + row.sample_si_no
		 * +','+row.sample_no+','+row.sample_temp+','+formatDate(row.sample_date_time)+','
		 * 
		 * +row.sample_result+','+row.remarks+','+row.chem_validation+
		 * '\')">View Detail</a>';
		 */
	  return '<a href="#" onclick="viewT17ChemDtls(\''
		+ row.sample_si_no +','+row.sample_no+','+row.sample_temp+','+formatDate(row.sample_date_time)+','
		+row.sample_result+','+row.remarks+ '\')">View Detail</a>';
  }
  function viewT17ChemDtls(chem_dtls){
		var analysis_id = document.getElementById('t17_chem_level').value;
		var psn_id = document.getElementById('t13_aim_psn_id').value;
		var c_dtl=chem_dtls.split(",");
		chem_hdr_id = c_dtl[0]; samp_no = c_dtl[1]; samp_temp = c_dtl[2];
		samp_date = c_dtl[3]; samp_res = c_dtl[4]; remarks = c_dtl[5];
		document.getElementById('t17_sample_si_no').value=chem_hdr_id;
		// document.getElementById('t17_chem_validation').value=c_dtl[6];
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
				$('#t17_lrf_Chemistry_tbl_id').datagrid('loadData', data);
				$('#t17_sample_no').combobox('setValue', samp_no);
				$('#t17_sample_date_time').datetimebox({	value : samp_date});
				$('#t17_sample_temp').numberbox('setValue', samp_temp);
				$('#t17_sample_result').combobox('setValue', samp_res);
				$('#t17_getSample_btn').linkbutton('disable');
				if(remarks != 'null'){
					$('#t17_remarks').textbox('setText', remarks);}				
			},
			error : function() {
				$.messager.alert('Processing',
						'Error while Processing Ajax...', 'error');
			}
		});
	}
  function getT17ChemistryDtlsGrid() {
  	var heat_id=$('#t13_heat_id').combobox('getText');
  	var heat_counter=	document.getElementById('t13_heat_cnt').value;
  	var analysis_id = document.getElementById('t17_chem_level').value;
  	var sample_no = $('#t17_sample_no').combobox('getText');
  	var sub_unit_id=document.getElementById('t13_sub_unit_id').value;
  	var aim_psnId=document.getElementById('t13_aim_psn_id').value;

  	if(heat_id!=null && heat_id!='' && heat_counter!=null && heat_counter!=''){		
  	$.ajax({
   		headers: { 
   		'Accept': 'application/json',
   		'Content-Type': 'application/json' 
   		},
   		type: 'GET',
   		// data: JSON.stringify(formData),
   		dataType: "json",
   		url: "./LRFproduction/getChemDtlsByAnalysis?analysis_id="+analysis_id+"&heat_id="+heat_id+"&heat_counter="+heat_counter+"&sub_unit_id="+sub_unit_id+"&sample_no="+sample_no+"&aim_psn_id="+aim_psnId,
   		success: function(data) {
   			 $('#t17_lrf_Chemistry_tbl_id').datagrid('loadData', data);
   		},
   		error:function(){      
   			$.messager.alert('Processing','Error while Processing Ajax...','error');
   		}
   		});
  	}
  }
  function GetSample(){
	  refreshT17Chem();
	  GenerateSampleNo();
  }
  function GenerateSampleNo() {
 	var heatId = String($('#t13_heat_id').textbox('getText'));
 	var heatcnt = document.getElementById('t13_heat_cnt').value;
 	var sub_unit = document.getElementById('t13_sub_unit_id').value;
 	// var analysis_id = $('#t17_analysis_type').combobox('getValue');
 	var analysis_id = 0;
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
 			$('#t17_sample_no').textbox('setText',data.comment);
 			$("#t17_sample_no").combobox('disable');	
 			$('#t17_spectro').show();
 		},
 		error : function() {
 			$.messager.alert('Processing', 'Error while getting Sample No',
 					'error');
 		}
 	});
  }
  function validateT17HeatChemForm(){
  	    return $('#t17_lrf_Chemistry_form_id').form('validate');
  }
  function saveT17ChemistryDtls(){
	  if(validateT17HeatChemForm()){ 	
		  var prevUnit=$('#t13_prev_unit').textbox('getText');
		  var sample_date_time = $('#t17_sample_date_time').datetimebox('getValue');
		  var sample_no=$('#t17_sample_no').combobox('getText');	
		  var sample_temp=$('#t17_sample_temp').numberbox('getValue'); 
		  var sub_unit =document.getElementById('t13_sub_unit_id').value;
		  // var eof_trns_sno =
			// document.getElementById('t4_trns_si_no').value;
		  /*
			 * var analysis_type =$('#t17_analysis_type').combobox('getValue');
			 * var analysis_type_txt
			 * =$('#t17_analysis_type').combobox('getText');
			 */
		  var final_result =$('#t17_sample_result').combobox('getValue');
		  var remarks = $('#t17_remarks').textbox('getValue');
		  var sample_si_no = document.getElementById('t17_sample_si_no').value;
		  var heat_id=$('#t13_heat_id').combobox('getText');
		  var heat_counter=	document.getElementById('t13_heat_cnt').value;
		  if(sample_si_no == null || sample_si_no == ''){
			  sample_si_no = 0;
		  }
      	  var eventname = 'LRF_CHEM_DETAILS';
      	  var rows = $('#t17_lrf_Chemistry_tbl_id').datagrid('getRows');
      	  for(var i=0; i<rows.length; i++){
  	    	   $('#t17_lrf_Chemistry_tbl_id').datagrid('endEdit', i);
      	  }
  	      var child_rows = $('#t17_lrf_Chemistry_tbl_id').datagrid('getRows');
    	  var grid_arry = '';
    	  for(var i=0; i<child_rows.length; i++){
    		  if((child_rows[i].aim_value !=null && child_rows[i].aim_value !='')){
    			  grid_arry += child_rows[i].element+'@'+child_rows[i].aim_value+'@'+child_rows[i].min_value+'@'+child_rows[i].max_value+'@'+child_rows[i].dtls_si_no+'SIDS';
  	    	  }
    	  }
    	  if(grid_arry == '' || grid_arry == null){
  			$.messager.alert('Processing',
  					' Chemistry elements data not found...', 'error');
  			return;
  		  }
          formData={"heat_id": heat_id,"heat_counter": heat_counter,"sample_no": sample_no,
      		    "sample_temp":sample_temp,"sub_unit_id":sub_unit,
      		    "sub_unit_id":sub_unit,"sample_result":final_result,"prev_unit":prevUnit,
      		    "remarks":remarks,"sample_si_no":sample_si_no,"grid_arry": grid_arry};
          $.ajax({
        	  headers: { 
        		  'Accept': 'application/json',
        		  'Content-Type': 'application/json' 
        	  },
        	  type: 'POST',
        	  data: JSON.stringify(formData),
        	  url: './heatProcessEvent/ChemDtlsSaveOrUpdate?eventname='+eventname+'&sample_date='+sample_date_time,
        	  success: function(data) {
        		  if(data.status == 'SUCCESS'){
        			  $.messager.alert('Chemical Details Info',data.comment,'info');
        			  clearAllData();
        		  }else {
        			  $.messager.alert('Chemical Details Info',data.comment,'info');
        		  }
        	  },
        	  error:function(){      
        		  $.messager.alert('Processing','Error while Processing Ajax...','error');
        	  }
          });
  		}// end valid if
  }
  function applyT17Filter() {
  	 $('#t17_lrf_Chemistry_tbl_id').datagrid('enableFilter');
  	 document.getElementById('t17_sample_si_no').value='0';
  	 getT17ChemistryDtlsGrid();
  } 
  function cancelT17ChemistryDtls(){
  	 getT17ChemistryDtlsGrid(); 
  }
  function clearAllData() {
	  $('#t17_sample_date_time').datetimebox({
			value : (formatDate(new Date()))
	  });
	  $('#t17_sample_no').combobox('setValue', '');
	  $('#t17_sample_temp').numberbox('setValue', '');
	  // $('#t17_analysis_type').combobox('setValue', '');
	  $('#t17_sample_result').combobox('setValue', '');
	  $('#t17_remarks').textbox('setText', '');
	  document.getElementById('t17_sample_si_no').value = '0';

	  var dummydata = new Array();
	  $('#t17_sample_no').combobox('loadData', dummydata);
	  refreshT17Chem();
	  // $('#t17_lrf_Chemistry_tbl_id').datagrid('loadData', dummydata);
	  // $('#t17_lrf_chem_samp_tbl_id').datagrid('loadData', dummydata);
  }
  function saveFinalResult(){
	  var row = $('#t17_lrf_chem_samp_tbl_id').datagrid('getSelected');
	  var samp_hdr_id = row.sample_si_no;
	  var sub_unit_id = document.getElementById('t13_sub_unit_id').value;
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
				  $.messager.alert('Result Info', data.comment, 'info');
				  getT17ChemSampleDtls();
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
  $('#t17_lrf_chem_samp_tbl_id').datagrid({
	  rowStyler:function(index,row){
		  if (row.final_result == 1){
	        	var analysis_id = document.getElementById('t17_chem_level').value;
	        	var psn_id = document.getElementById('t13_aim_psn_id').value;
	        	var chem_hdr_id = row.sample_si_no;
	        	document.getElementById('t17_sample_si_no').value=chem_hdr_id;
	    		// document.getElementById('t17_chem_validation').value=row.chem_validation;
	        	$('#t17_final_result_btn_id').linkbutton('disable');
	        	$('#t17_save_chem_btn_id').linkbutton('disable');
	        	$('#t17_close_chem_btn_id').linkbutton('disable');
	        	$('#t17_getSample_btn').linkbutton('disable');
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
	        					$('#t17_lrf_Chemistry_tbl_id').datagrid('loadData', data);
	        					$('#t17_sample_no').combobox('setValue', row.sample_no);
	        					$('#t17_sample_date_time').datetimebox({	value : formatDate(row.sample_date_time)});
	        					$('#t17_sample_temp').numberbox('setValue', row.sample_temp);
	        					$('#t17_sample_result').combobox('setValue', row.sample_result);
	        					if(row.remarks != 'null'){
	        						$('#t17_remarks').textbox('setText', row.remarks);}
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

  /** Chemical Details Screen End * */
  /** Events Screen Start * */

  function T18EventsDtls(){ 
	  assignRowChangeDetectionListener('t18_lrf_events_form_div_id','t18_lrf_events_tbl_id');
	  closeActionBindEvt('t18_lrf_events_tbl_id');
	  $('#t18_lrf_events_tbl_id').edatagrid({ 	 

	  onBeforeEdit:function(index,row){ 
		           // row.event_date_time = formatDate(row.event_date_time);
		  if(row.event_date_time==null){
				row.event_date_time = formatDate(new Date());
				}
				else{
					row.event_date_time = formatDate(row.event_date_time);
				}
		         $('#t18_lrf_events_tbl_id').datagrid('selectRow', index);

      },onEndEdit:function(index,row){
    	  $('#t18_lrf_events_form_div_id').attr("data-change","1")
    	  $('#t18_lrf_events_tbl_id').datagrid('selectRow', index);
    	  var dt=(row.event_date_time).split(" ");
    	  var cons_time=new Date(commonGridDtfmt(dt[0],dt[1]));
    	  var time=cons_time.getTime();
	    	  row.event_date_time =time;
	      }   ,
	      onAfterEdit:function(index,row){
			row.editing = false;
			$('#t18_lrf_events_form_div_id').attr("data-change","1")
		}
  });
  }
  function T18EventsDtlsOpen(){
	  var t13_trns_si_no=document.getElementById('t13_trns_si_no').value;
	  if(t13_trns_si_no!=''){
		  $('#t18_lrf_events_form_div_id').dialog({modal:true,cache: true});
		  $('#t18_lrf_events_form_div_id').dialog('open').dialog('center').dialog('setTitle','Events Details Form');
		  $('#t18_lrf_events_form_id').form('clear');
 	 
		  var heatId =$('#t13_heat_id').textbox('getText');
		  var heatcnt = document.getElementById('t13_heat_cnt').value;
		  var sub_unit_id=document.getElementById('t13_sub_unit_id').value;
		  var aimpsn =$('#t13_aim_psn').textbox('getText');
		  $('#t18_heat_no').textbox('setText',heatId);
		  $('#t18_aim_psn').textbox('setText',aimpsn);
		  var url1="./CommonPool/getComboList?col1=event_si_no&col2=event_desc&classname=EventMasterModel&status=1&wherecol=sub_unit_id="+sub_unit_id+" and record_status=";
		  getDropdownList(url1,'#t18_event_id');
		  $('#t18_event_date_time').datetimebox({
			  value: (formatDate(new Date())) 
		  });
		  T18EventsDtls();
		  getT18EventDtlsGrid();	
 		}else{
 			$.messager.alert('Information','Please Select Heat...!','info');
 		}
 	}// end

  function getT18EventDtlsGrid(){
	  var heat_id=$('#t13_heat_id').combobox('getText');
 	  var heat_counter=	document.getElementById('t13_heat_cnt').value;
 	  var sub_unit_id;
 	  var trns_si_no=document.getElementById('t13_trns_si_no').value;
 	  var prev_unit=$('#t13_prev_unit').textbox('getText');
 	  var psn_hdr_sl_no = document.getElementById('t13_aim_psn_id').value;
 	  if(prev_unit.match(/VD.*/)){
    		sub_unit_id = $('#t13_unit').combobox('getValue');
 	  }else{
   		sub_unit_id =  document.getElementById('t13_sub_unit_id').value;
 	  }
   	  $.ajax({
    		headers: { 
      		'Accept': 'application/json',
      		'Content-Type': 'application/json' 
      		},
      		type: 'GET',
      		// data: JSON.stringify(formData),
      		dataType: "json",
      		url: "./heatProcessEvent/getLrfHeatProcessEventDtls?heat_id="+heat_id+"&heat_counter="+heat_counter+"&sub_unit_id="+sub_unit_id+"&prev_unit="+prev_unit+"&psn_hdr_id="+psn_hdr_sl_no,
      		success: function(data) {
      			$("#T18SaveBtn").linkbutton('disable');
      			$('#t18_lrf_events_tbl_id').datagrid('loadData', data);

      			for (var i = 0; i < data.length; i++) {
      				if(data[i].event_date_time==null){
      					$("#T18SaveBtn").linkbutton('enable');
    				}
    			}     			
      		},
      		error:function(){      
      			$.messager.alert('Processing','Error while Processing Ajax...','error');
      		}
   	 });
  }
function cancelT18EventDtls(){
	getT18EventDtlsGrid();
}

   function saveT18EventDtls(){
	   var heat_id=$('#t13_heat_id').combobox('getText');
	   var heat_counter=	document.getElementById('t13_heat_cnt').value;
	   var sub_unit_id=document.getElementById('t13_sub_unit_id').value;
	   var lrf_trns_sno = document.getElementById('t13_trns_si_no').value
	   var sel_rows = $('#t18_lrf_events_tbl_id').datagrid('getRows');
	   var grid_arry = '';
	   var date_prev_val='';
	   var isDateValid ='NO';
	   var eventdate='';
	   var isBreakdown = false;
	   if ($('#t18_breakdown').is(":checked")){
		   isBreakdown = true;
	   }

	   for (var i = 0; i < sel_rows.length; i++) {
		   $('#t18_lrf_events_tbl_id').datagrid('endEdit', i);
		   var j;   
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
		   } else{
			   /*
				 * if (sel_rows[i].event_date_time == '' ||
				 * sel_rows[i].event_date_time == null ||
				 * sel_rows[i].event_date_time == ' '){ if(isBreakdown)
				 * isDateValid ='YES'; else isDateValid ='NO'; break; }else{
				 */
				   j = i+1;
				   if(j <= sel_rows.length){
					   if (sel_rows[i].event_date_time == '' || sel_rows[i].event_date_time == null || sel_rows[i].event_date_time == ' '){
						   isDateValid ='YES';
						   /*
							 * if(isBreakdown) isDateValid ='YES'; else
							 * isDateValid ='NO'; break;
							 */
					   }else{
						   eventdate = formatDate(sel_rows[i].event_date_time);
						   if (eventdate < date_prev_val){
							   isDateValid ='NO';
							   break;
						   }else{
							   date_prev_val =formatDate(sel_rows[i].event_date_time);
							   grid_arry += sel_rows[i].event_id + '@' + eventdate + '@'
							   + sel_rows[i].heat_proc_event_id + 'SIDS';						   

							   isDateValid ='YES';
						   }
					   }
				   }
			   // }
		   }
   		}
   	      // formData={"heat_proc_event_id": heat_proc_event_id,"heat_id":
			// heat_id,"heat_counter": heat_counter,"event_id":
			// event_id,"event_date": event_date_time};
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
   			url : './heatProcessEvent/EventSaveOrUpdate?trns_sno='+lrf_trns_sno+'&unit=LRF',
   			success : function(data) {
   				if (data.status == 'SUCCESS') {
   					$.messager.alert('Event Details Info', data.comment, 'info');
   					getT18EventDtlsGrid();
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
    /** Events Screen End * */  

   /**
	 * Delay Entry Start
	 */   
   function addT24DelayEntry(){
	   var heatId = $('#t13_heat_id').textbox('getText');
		 if (heatId!=null && heatId!=''){
			 openT24DelayEntryScreen();
		 }
		 else{
			 $.messager.alert('Warning', 'Select Heat Number',
				'info');
		 }
   }

   function openT24DelayEntryScreen(){
	   getT24DelayDetails();
	   $("#t24_delay_entry_form_div_id").attr("data-rowchange","0");
	   $('#t24_delay_entry_form_div_id').on('keyup change paste', 'input, select, textarea', function(){
		   $("#t24_delay_entry_form_div_id").attr("data-rowchange","1");
			 });
	   	$('#t24_delay_entry_form_div_id').dialog({
			modal : true,
			cache : true
		});

		$('#t24_delay_entry_form_div_id').dialog('open').dialog('center').dialog(
				'setTitle', 'Delay Details Form');
		$('#t24_delay_entry_form_id').form('clear');
		var heatId = $('#t13_heat_id').textbox('getText');
		var aimpsn = $('#t13_aim_psn').textbox('getText');
		$("#t24_heat_no").textbox("setText",heatId);
		$("#t24_aim_psn").textbox("setText",aimpsn);  
		$('#t24_delay_entry_tbl_id').edatagrid({
			onBeginEdit :function(index,rowE){}});
}
   
   function getT24DelayDetails(){
		var t4_trns_si_no2 =document.getElementById('t13_trns_si_no').value;
		var heat_counter=	document.getElementById('t13_heat_cnt').value;
		var sub_unit_id= document.getElementById('t13_sub_unit_id').value; // $('#t4_unit').combobox('getValue')
		var prev_unit=$('#t13_prev_unit').textbox('getText');

		$.ajax({
			headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json'
			},
			type : 'GET',
			// data: JSON.stringify(formData),
			dataType : "json",
			url : "./LRFproduction/activityDelayMstrBySubunit?sub_unit_id="+sub_unit_id +"&trns_si_no="+t4_trns_si_no2+"&heat_counter="+parseInt(heat_counter)+"&prev_unit="+prev_unit,
			success : function(data) {
				$('#t24_delay_entry_tbl_id').datagrid('loadData', data);
			},
			error : function() {
				$.messager.alert('Processing', 'Error while Processing Ajax...',
						'error');
			}
		});
   } 

   function saveT24DelaytDtls(){
	   var rowsCh = $('#t24_delay_entry_tbl_id').edatagrid('getChanges');
	   var rows = $('#t24_delay_entry_tbl_id').datagrid('getRows');
	   for ( var i = 0; i < rows.length; i++) {
	    $('#t24_delay_entry_tbl_id').datagrid('endEdit', i);
	   } 
	   if( $("#t24_delay_entry_form_div_id").attr("data-rowchange")=="1" ){
		   var sel_rows = $('#t24_delay_entry_tbl_id').edatagrid('getRows');
		   var heatId = $('#t13_heat_id').textbox('getText');
		   var heat_count=$('#t13_heat_cnt').val();
		   $.ajax({
			   	headers : {
					'Accept' : 'application/json',
					'Content-Type' : 'application/json'
			   	},
				type : 'POST',
				data : JSON.stringify(sel_rows),
				// data: rows,
				dataType : "json",
				url : './LRFproduction/TransDelaySave?heat_id='+heatId+'&heat_counter='+heat_count,
				success : function(data) {
					if (data.status == 'SUCESS') {
						$.messager.alert('Info', data.comment, 'info');
						getT24DelayDetails();
						 $("#t24_delay_entry_form_div_id").attr("data-rowchange","0");
							// cancelT8EventDtls();
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

   function t241ReferenshData(){
		refreshT24DelayDetailsView(parseInt($("#trans_delay_dtl_id_value").val()));
   }

   function t241addDelayDetails(){
		 $('#t241_delay_entry_form_id').form('clear');
		 var row = $('#t24_delay_entry_tbl_id').datagrid('getSelected');
		 if(row!=null){
			 var heatId = $('#t13_heat_id').textbox('getText');
			 var aimpsn = $('#t13_aim_psn').textbox('getText');
			 if(row.transDelayEntryhdr!=null ){
			 $("#t241_heat_no").textbox("setValue",heatId);
			 $("#t241_aim_psn").textbox("setValue",aimpsn);
			 $("#t241_activity").textbox("setText",row.activity_master.activities);
			 $('#t241_delay_entry_form_div_id').dialog({
				modal : true,
				cache : true
			 });
			 $('#t241_delay_entry_form_div_id').dialog('open').dialog('center').dialog(
					'setTitle', 'Delay Details Form');
			// $("#data-delay").val("0");
			$("#t241_delay_entry_tbl_id").attr("data-delay","0")
			// loading delay entries against delay hdr
			refreshT24DelayDetailsView(row.transDelayEntryhdr.trns_delay_entry_hdr_id);
			DelayT24Init(row);
			$("#t241activity_value").val(row.activity_master.activities);
			$("#t241delay_details").val(row.activity_master.delay_details);
			$("#trans_delay_dtl_id_value").val(row.transDelayEntryhdr.trns_delay_entry_hdr_id);
			$("#t241delay").val(row.delay);
			$('#t241_delay_entry_tbl_id').edatagrid({					
				onBeginEdit :function(index,rowE){
					var editors = $('#t241_delay_entry_tbl_id').datagrid('getEditors', index);
					var actVal = $(editors[2].target);// delay entry field
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
									$("#t241_delay_entry_tbl_id").attr("data-delay",neg)
								}else{
									var neg=parseInt(newV)-parseInt(oldV)
									$("#t241_delay_entry_tbl_id").attr("data-delay",neg)
								}
							}})
						},
					onBeforeEdit : function(index, rowE) {						
					},
					onBeforeSave : function(index, rowE) {
						var data = $('#t241_delay_entry_tbl_id').edatagrid('getData');
						var rows = data.rows;
						var sum = 0;
						for (i=0; i < rows.length; i++) {
							if(typeof rows[i].delay_dtl_duration !="undefined"){
							  sum+=rows[i].delay_dtl_duration;
							}
						}
						sum = sum+parseInt($("#t241_delay_entry_tbl_id").attr("data-delay"));
					  if(sum>row.delay){
						  $.messager
							.alert(
									'Information',
												"Sum : "+ sum +" Activity Delay : "+row.delay
											+ " Sum of delay minutes should not be grater than activity delay minutes ",
										'info');
							  refreshT24DelayDetailsView(row.transDelayEntryhdr.trns_delay_entry_hdr_id)
							  $("#t241_delay_entry_tbl_id").attr("data-delay","0")
							  return false;
						  }
						},
					onSuccess : function(index, rowE) {
						refreshT24DelayDetailsView(row.transDelayEntryhdr.trns_delay_entry_hdr_id)
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

   function refreshT24DelayDetailsView(trans_delay_entry_hdr_id){
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
				$('#t241_delay_entry_tbl_id').datagrid('loadData', data);
			},
			error : function() {
				$.messager.alert('Processing', 'Error while Processing Ajax...',
						'error');
			}
		});
	}

   function formatT24ActivityColumnData(colName, value, row, index) {
		try {
			if(row.isNewRecord){
	    		return $("#t241activity_value").val();
	    	}else{
	    	if(eval("row."+colName) === null)
	    		{
	    		return $("#t241activity_value").val();
	    		}else{
	    			return eval("row."+colName);
	    		}
	    	}
	        }catch(e)
	        {
	        	return "";
	    	}
	}

	function formatT24DlyDtlsColumnData(colName, value, row, index) {
		try {
			if(row.isNewRecord){
	    		return $("#t241delay_details").val();
	    	}else{
	    		if(eval("row."+colName) === null){
	    			return $("#t241delay_details").val();
	    		}else{
	    			return eval("row."+colName);
	    		}
	    	}
		}catch(e){
	        	return "";
	    	}
	}
	

	function DelayT24Init(row){
		$('#t241_delay_entry_tbl_id')
		.edatagrid(
				{
					updateUrl : './LRFproduction/TransDelayDtlsUpdate?trans_delay_entry_hdr_id='+row.transDelayEntryhdr.trns_delay_entry_hdr_id,
					saveUrl : './LRFproduction/TransDelayDtlsSave?trans_delay_entry_hdr_id='+row.transDelayEntryhdr.trns_delay_entry_hdr_id
				});
	}
   /** Delay Entry End * */

	function checkEventsDtls(callback){
		 var heat_id=$('#t13_heat_id').combobox('getText');
		 var heat_counter=	document.getElementById('t13_heat_cnt').value;
	 	 var sub_unit_id=document.getElementById('t13_sub_unit_id').value;
	 	 var trns_si_no=document.getElementById('t13_trns_si_no').value;
	 	 var prev_unit=$('#t13_prev_unit').textbox('getText');
	 	 var isSuccess=true;
	 	var psn_hdr_sl_no = document.getElementById('t13_aim_psn_id').value;

		$.ajax({
			headers: { 
	      		'Accept': 'application/json',
	      		'Content-Type': 'application/json' 
	      		},
	      		type: 'GET',
	      		// data: JSON.stringify(formData),
	      		dataType: "json",
	      		url: "./heatProcessEvent/getLrfHeatProcessEventDtls?heat_id="+heat_id+"&heat_counter="+heat_counter+"&sub_unit_id="+sub_unit_id+"&prev_unit="+prev_unit+"&psn_hdr_id="+psn_hdr_sl_no,
	      		success: function(data) {
// $('#t8_eof_events_tbl_id').datagrid('loadData', data);
// $("#T8SaveBtn").linkbutton('disable');
				for (var i = 0; i < data.length; i++) {
					if(data[i].event_date_time==null){
						// $("#T8SaveBtn").linkbutton('enable');
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
		var t4_trns_si_no2 =document.getElementById('t13_trns_si_no').value;
		var heat_counter=	document.getElementById('t13_heat_cnt').value;
		var sub_unit_id=$('#t13_unit').combobox('getValue');
		$.ajax({
			headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json'
			},
			type : 'GET',
			// data: JSON.stringify(formData),
			dataType : "json",
			url : "./LRFproduction/presentActivityDelayMstrBySubunit?sub_unit_id="+sub_unit_id +"&trns_si_no="+t4_trns_si_no2+"&heat_counter="+parseInt(heat_counter),//

			success : function(data) {
				if(data.length>0){
					 isSuccess=true;
				}
				// $('#t9_delay_entry_tbl_id').datagrid('loadData', data);
				callback(isSuccess);
			},

			error : function() {
				$.messager.alert('Processing', 'Error while Processing Ajax...',
						'error');
			}
		});
	}

	/** LRF Dispatch Begin* */

	function t21LrfDispatch(){
		var vPass = true;
   	 	var t21_trns_si_no=document.getElementById('t13_trns_si_no').value;
   	 	var prevUnit=$('#t13_prev_unit').textbox('getText');
   	 	var aimPsn=document.getElementById('t13_aim_psn_id').value;
   	 	var aimPsnNo=document.getElementById('t13_aim_psn').value;

   	 	if(t21_trns_si_no!=''){
			checkEventsDtls(function(isEventPresent){
				if(isEventPresent){
						if(isEventPresent){
							if(prevUnit.match(/VD.*/)){
								checkAVDChemDtlsPresent(function(isEventPresent){
									if(isEventPresent){									
										checkLiftTempPresent(function(liftTempCheck){
											if(liftTempCheck == 0){
												vPass = false;
												$.messager.alert('Missing', 'LRF Lift Temperature is missing please check',
												'warning');
											}else if(liftTempCheck == 1){
												vPass = false;
												$.messager.alert('Alert', 'LRF Lift Temperature is not matching with PSN master value',
												'warning');
											}else if(liftTempCheck == 2){
												vPass = true;
											}
										});
									}else{
										vPass = false;
										$.messager.alert('Missing', 'Chemistry entries after VD are missing please check',
										'warning');
									}
								});
							}// VD check end
							
							if(vPass){
								$('#t21_lrf_dispatch_div_id').dialog({modal:true,cache: true});
								$('#t21_lrf_dispatch_div_id').dialog('open').dialog('center').dialog('setTitle','LRF Dispatch Screen');
								$('#t21_lrf_dispatch_form_id').form('clear');
								var heatid=$('#t13_heat_id').combobox('getText');
								var heat_cntr = document.getElementById('t13_heat_cnt').value;
								var condition=$("#t13_tar_caster").textbox("getText");
								
						    	LRFDispatch();
								$.ajax({
									headers: { 
								    'Accept': 'application/json',
								    'Content-Type': 'application/json' 
									},
								    type: 'GET',
								    dataType: "json",
								    url: './LRFproduction/getHeatTrackStatus?heatno='+heatid+'&heatCounter='+heat_cntr+'&aimPSN='+aimPsn,
								    success: function(data) {
								    	var hstatus=data.unit_process_status;	
								    	document.getElementById('t13_cstatus').value=hstatus;
								    	if(hstatus == 'WAITING FOR DISPATCH'){
											url1="./CommonPool/getComboList?col1=sub_unit_id&col2=sub_unit_name&classname=SubUnitMasterModel&status=1&wherecol=unitDetailsMstrMdl.unit_name = 'CCM' and sub_unit_name = '"+condition+"' and record_status=";
											getDropdownList(url1,'#t21_dispatchTo');
										}else{
											var psnRoute = data.act_proc_path;
											if(psnRoute.match(/VD/)){
												url1="./CommonPool/getComboList?col1=sub_unit_id&col2=sub_unit_name&classname=SubUnitMasterModel&status=1&wherecol=unitDetailsMstrMdl.unit_name = 'VD' and record_status=";
											}else{
												url1="./CommonPool/getComboList?col1=sub_unit_id&col2=sub_unit_name&classname=SubUnitMasterModel&status=1&wherecol=unitDetailsMstrMdl.unit_name = 'VD' OR sub_unit_name = '"+condition+"' and record_status=";
											}
											getDropdownList(url1,'#t21_dispatchTo');
										}
								    },
								    error:function(){      
								    	$.messager.alert('Processing','Check the Process Route Of PSN No ( '+aimPsnNo+' )','info');
								    }
								});
								var heatStatus=document.getElementById('t13_cstatus').value;
								// alert("Status : "+heatStatus);
							}
						}else{
							$.messager.alert('Missing', 'some of delay entries are missing please check it',
							'warning');
						}
					// } );
				}else{
					$.messager.alert('warning', 'Some of Event details missing please check events',
					'warning');
				}
				}
			);
   	 }  
}
	function getTrackStatus(){
	}

    function checkLiftTempPresent(callback){
    	var vretVal = 0;
    	var heatid=$('#t13_heat_id').combobox('getText');
    	var heat_cntr = document.getElementById('t13_heat_cnt').value;
    	var subunitid = document.getElementById('t13_sub_unit_id').value;
    	var psn_no = document.getElementById('t13_aim_psn').value;
    	
    	$.ajax({
		headers: { 
	    'Accept': 'application/json',
	    'Content-Type': 'application/json' 
		},
	    type: 'GET',
	    dataType: "json",
	    url: './heatProcessEvent/getHeatHeatProcParamDtls?heatid='+heatid+'&heat_cntr='+heat_cntr+'&subunitid='+subunitid+ '&psn_no=' + psn_no,
	    success: function(data) {
	    	if(data.length == 0)
	    		vretVal = 0;
	    	else{
	    		for(var j=0;j<=data.length;j++){
	    			if(data[j].proc_para_name=='LRF_LIFTING_TEMP'){
	    				if(data[j].param_value_actual != data[j].param_value_aim)
	    					vretVal = 1;
	     				else
	     					vretVal = 2;
	    			}
	    		}
	    	}
	    	callback(vretVal);
	    },
	    error:function(){      
	    	$.messager.alert('Processing','Error while Processing Ajax...','error');
	    }
	});		
	return vretVal;
}	

function checkAVDChemDtlsPresent(callback){
	var isSuccess=false;
  	var heat_id = $('#t13_heat_id').combobox('getText');
  	var analysis_type = "LRF_AVD_CHEM";
  	var sub_unit_id = document.getElementById('t13_sub_unit_id').value;
  	var heat_counter=	document.getElementById('t13_heat_cnt').value;

  	if (heat_id != null && heat_id != '') {
  		$.ajax({
  			headers : {
  				'Accept' : 'application/json',
  				'Content-Type' : 'application/json'
  			},
  			type : 'GET',
  			dataType : "json",
  			url : "./heatProcessEvent/getSampleDtlsByAnalysisType?analysis_type="
  					+ analysis_type + "&heat_id=" + heat_id + "&sub_unit_id=" + sub_unit_id+"&heat_counter="+heat_counter,
  			success : function(data) {
  				if(data.length == 0)
  					isSuccess=false;
  				else
  					isSuccess=true;
  				callback(isSuccess);
  			},
  			error : function() {
  				$.messager.alert('Processing',
  						'Error while Processing Ajax...', 'error');
  			}
  		});
  	}
}
   /** LRF Dispatch End* */
	function resetForm(){
		 $('#t21_remarks').textbox('setValue','');
  		 $('#t21_lrfDispatchWgt').numberbox('setValue','');
  		 $('#t21_lrfDispatchTemp').numberbox('setValue','');
  		 $('#t21_dispatchTo').combobox('setValue','Select');
	}

	function validateT21LRFDispatchForm(){
		return $('#t21_lrf_dispatch_form_id').form('validate');
	}

	function cancelT21LrfDispatch(){
		$('#t21_lrf_dispatch_div_id').dialog('close');
    }

	function saveT21LRFDispatch(){
		var cstatus=document.getElementById('t13_cstatus').value;
		
		var lrfar_n2_consumption = $('#t21_ar_n2_consumption').numberbox('getValue');
		if(cstatus == 'WAITING FOR DISPATCH'){
			var lrfDispatchRemarks = $('#t21_remarks').textbox('getValue');
    		var lrfDispatchWgt = $('#t21_lrfDispatchWgt').numberbox('getValue');
    		var lrfDispatchTemp = $('#t21_lrfDispatchTemp').numberbox('getValue');
    		var lrfDispatchTo = $('#t21_dispatchTo').combobox('getValue');
    		var lrfDispatchTo_unit_name = $('#t21_dispatchTo').combobox('getText');
    		var t21_trans_si_no= document.getElementById('t13_trns_si_no').value;
    		var t21_heatTrackId= document.getElementById('t13_heat_track_id').value;
    		var t21_stLadleTrackId= document.getElementById('t13_steel_ladleno_id').value;
    		var t21_main_status="WIP";
    		var t21_act_proc_path = $('#t13_unit').combobox('getText');
			var t21_unit_status="WAITING FOR PROCESSING";
			var t21_lrf_status="DISPATCHED";	
			var t21_eof_status="DISPATCHED";
			var t21_vd_status='DISPATCHED';
			var t21_af_vd_status='DISPATCHED';
			
			var t21_blt_caster_status='';
			var t21_blm_caster_status='';
			if(lrfDispatchTo_unit_name == 'BILLET CASTER'){
				t21_blt_caster_status='PROCESSING';
			}
			else if(lrfDispatchTo_unit_name == 'BLOOM CASTER'){
				t21_blm_caster_status='PROCESSING';
			}else if(lrfDispatchTo_unit_name.substring(0, 3) != 'CCM'){
				$.messager.alert('Alert','Dispatch to caster is only allowed...!','info');
			}
			var formData = {
					"vdHeatDetails":{
	  					  "trns_si_no":t21_trans_si_no,"vd_process_remarks":lrfDispatchRemarks,"dispatch_temp":parseInt(lrfDispatchTemp),
	  					  "dispatch_to_unit":parseInt(lrfDispatchTo),"vd_dispatch_wgt":parseInt(lrfDispatchWgt),"steel_ladle_no":t21_stLadleTrackId,
	  				  },
	  				 "vdHeatStatus":{
	  					  "heat_track_id":t21_heatTrackId,"main_status":t21_main_status,"act_proc_path":t21_act_proc_path,
	  					  "current_unit":lrfDispatchTo_unit_name,"unit_process_status":t21_unit_status,"lrf_status":t21_lrf_status,
	  					  "eof_status":t21_eof_status,"vd_status":t21_vd_status,"lrf_status_af_vd":t21_af_vd_status,"blt_cas_status":t21_blt_caster_status,
	  					  "blm_cas_status":t21_blm_caster_status
	  				  }
	  				};
			var tapWeightMaxUrl = "./CommonPool/getComboList?col1=lookup_id&col2=lookup_value&classname=LookupMasterModel&status=1"
    			+ "&wherecol=lookup_type='LRF_DISPATCH_MIN_WEIGHT' and lookup_status=";
    		/*var tapWeightProcessMsg = "Getting the LRF Dispatch Temperature range.";
    		var tapWeightErrorMsg = "Error while Processing LRF Tap weight.";*/
			getResult(tapWeightMaxUrl, function(isRecordPresent, value){
				if (parseFloat(value[0].txtvalue) <= lrfDispatchWgt) {
    		$.ajax({
           		headers: { 
           		'Accept': 'application/json',
           		'Content-Type': 'application/json' 
           		},
           		type: 'POST',
           		data: JSON.stringify(formData),
           		dataType: "json",
           		url : './VDproduction/vdDispatchSave?trns_si_no='+t21_trans_si_no+"&AR_N2_CONSUMPTION="+lrfar_n2_consumption,
           		success: function(data) {
           		    if(data.status == 'SUCCESS')  	{
           		    	$.messager.alert('LRF Dispatch Details Info',data.comment,'info');
           		    	clearT13HeatHdrform();
           		    	cancelT21LrfDispatch();
           		    	getT13UpdatedHeatList();
           		    }else {
           		    	$.messager.alert('LRF Dispatch Details Info',data.comment,'info');
           		    }
           		  },
           		error:function(){      
           			$.messager.alert('Processing','Error while Processing Ajax...','error');
           		}
          		});
    			}else
    				{
    				$.messager.alert('Warning', 'Its does not satisfy the LRF dispatch weight');
    				return false;
    				}
    		});
		}else{
			if(validateT21LRFDispatchForm()){
				var lrfDispatchRemarks = $('#t21_remarks').textbox('getValue');
	    		var lrfDispatchWgt = $('#t21_lrfDispatchWgt').numberbox('getValue');
	    		var lrfDispatchTemp = $('#t21_lrfDispatchTemp').numberbox('getValue');
	    		var lrfDispatchTo = $('#t21_dispatchTo').combobox('getValue');
	    		var lrfDispatchTo_unit_name = $('#t21_dispatchTo').combobox('getText');
	    		var t21_trans_si_no= document.getElementById('t13_trns_si_no').value;
	    		var t21_heatTrackId= document.getElementById('t13_heat_track_id').value;
	    		var t21_stLadleTrackId= document.getElementById('t13_steel_ladleno_id').value;
	    		var t21_main_status="WIP";
	    		// In service impl class this t21_act_proc_path will be
				// concatenated to existing act process path
				var t21_act_proc_path = $('#t13_unit').combobox('getText');
				var t21_unit_status="WAITING FOR PROCESSING";
				var t21_lrf_status="DISPATCHED";	
				var t21_eof_status="DISPATCHED";
				var t21_vd_status='';
				var t21_blt_caster_status='';
				var t21_blm_caster_status='';

				if(lrfDispatchTo_unit_name.substring(0, 2) == 'VD'){
					t21_vd_status='PROCESSING';
				}
				else if(lrfDispatchTo_unit_name == 'BILLET CASTER'){
					t21_blt_caster_status='PROCESSING';
				}
				else if(lrfDispatchTo_unit_name == 'BLOOM CASTER'){
					t21_blm_caster_status='PROCESSING';
				}else if((lrfDispatchTo_unit_name == 'DRY PIT')){
					t21_unit_status='ABORTED';
					t21_lrf_status='ABORTED';
				}else if(lrfDispatchTo_unit_name == null || lrfDispatchTo_unit_name == ''){
					$.messager.alert('Alert','Select Dispatch Unit...!','info');
				}
					
	    		var formData = {
	  				  "lrfHeatDetails":{
	  					  "trns_sl_no":t21_trans_si_no,"lrf_process_remarks":lrfDispatchRemarks,"lrf_dispatch_temp":lrfDispatchTemp,
	  					  "lrf_dispatch_unit":lrfDispatchTo,"lrf_dispatch_wgt":	lrfDispatchWgt,"steel_ladle_no":t21_stLadleTrackId,"AR_N2_CONSUMPTION":lrfar_n2_consumption
	  				  },
	  				  "lrfHeatStatus":{
	  					  "heat_track_id":t21_heatTrackId,"main_status":t21_main_status,"act_proc_path":t21_act_proc_path,
	  					  "current_unit":lrfDispatchTo_unit_name,"unit_process_status":t21_unit_status,"lrf_status":t21_lrf_status,
	  					  "eof_status":t21_eof_status,"vd_status":t21_vd_status,"blt_cas_status":t21_blt_caster_status,
	  					  "blm_cas_status":t21_blm_caster_status
	  				  }
	  				};
	    		var tapWeightMaxUrl = "./CommonPool/getComboList?col1=lookup_id&col2=lookup_value&classname=LookupMasterModel&status=1"
	    			+ "&wherecol=lookup_type='LRF_DISPATCH_MIN_WEIGHT' and lookup_status=";
	    		/*var tapWeightProcessMsg = "Getting the LRF Dispatch Temperature range.";
	    		var tapWeightErrorMsg = "Error while Processing LRF Tap weight.";*/
	    		getResult(tapWeightMaxUrl, function(isRecordPresent, value){
					if (parseFloat(value[0].txtvalue) <= lrfDispatchWgt) {
	           $.ajax({
	           		headers: { 
	           		'Accept': 'application/json',
	           		'Content-Type': 'application/json' 
	           		},
	           		type: 'POST',
	           		data: JSON.stringify(formData),
	           		dataType: "json",
	           		url : './LRFproduction/lrfDispatchSave',
	           		success: function(data) {
	           		    if(data.status == 'SUCCESS') 		    	{
	           		    	$.messager.alert('LRF Dispatch Details Info',data.comment,'info');
	           		    	clearT13HeatHdrform();
	           		    	cancelT21LrfDispatch();
	           		    	getT13UpdatedHeatList();
	           		    }else {
           		    		$.messager.alert('LRF Dispatch Details Info',data.comment,'info');
           		    	}
	           		  },
	           		error:function(){      
	           			$.messager.alert('Processing','Error while Processing Ajax...','error');
	           		}
           		});
	    			}
	    			else{
	    				$.messager.alert('Warning', 'Its does not satisfy the LRF dispatch weight');
	    				return false;
	    				}
	    		});
	    			
			}
		}
	}
	
	function gradeChange(){ 	
		$.ajax({
			headers: { 
				'Accept': 'application/json',
		     	'Content-Type': 'application/json' 
			},
		    type: 'GET',
		    url: "./LRFproduction/getPlanGrades",
		    dataType: 'json',
		    success: function(data){ 		    	 
		    	$('#t17_lrf_GradeFinalize_form_div_id').dialog('open').dialog('center').dialog('setTitle','Grade Change Form');
	 		    $('#t17_lrf_GradeFinalize_form_id').form('clear');
	 		    $('#t17_lrf_heatplan_tbl_id').datagrid('loadData', []);
	 			 
	 			loadIngStart('t17_lrf_heatplan_tbl_id','Loading....Please Wait......');
	 			$('#t17_lrf_heatplan_tbl_id').datagrid('loadData', data);
	  			$('#t17_lrf_heatplan_tbl_id').datagrid('enableFilter');
	  			loadIngEnd('t17_lrf_heatplan_tbl_id');
	 		    /*
				 * var comboData=[]; for(var i=0;i<data.length;i++){ var
				 * details={ id:data[i].psnHdrId,
				 * //value:data[i].psnHdrModel.psn_no value:data[i].finalGrade };
				 * comboData.push(details); }
				 * $("#t17_final_psn").combobox('loadData', comboData);
				 */
		    },
		    error: function(){
		    	 $.messager.alert('Processing','Error while Processing Ajax...','error');
		    }
		});
	}

	function gradeFinalize(){ 	    
 	    var chem_hdr_id = document.getElementById('t17_sample_si_no').value;
 	    var analysis_id = document.getElementById('t17_chem_level').value;
 	    // var analysis_type = $('#t17_analysis_type').combobox('getText');
 	    var analysis_type = "LRF_LIFT_CHEM";
 	    // var chem_validation =
		// document.getElementById('t17_chem_validation').value;
 	    var chem_validation = 'Pass';
 	    var aim_psn_id = document.getElementById('t13_aim_psn_id').value;
 	    var rows = $('#t17_lrf_Chemistry_tbl_id').datagrid('getRows');
   	  	for(var i=0; i<rows.length; i++){
   	  		var aim_val = parseFloat(rows[i].aim_value);
   	  		var max_value = parseFloat(rows[i].max_value);
   	  		if(aim_val > max_value){
   	  			chem_validation = 'Fail';
   	  			break;
   	  		}
   	  	}

 	    if(chem_validation == 'Fail'){
 	    	$.ajax({
 		   	 headers: { 
 		     		'Accept': 'application/json',
 		     		'Content-Type': 'application/json' 
 		     		},
 		     type: 'GET',
 		     url: "./LRFproduction/getFinalGrades?sample_si_id="
				+ chem_hdr_id +"&analysis_id="+analysis_id+"&aim_psn_id="+aim_psn_id+"&analysis_type="+analysis_type,
 		     dataType: 'json',
 		     success: function(data){ 		    	 
 		    	 if(data.length != 0){
 		    		 $('#t17_lrf_GradeFinalize_form_div_id').dialog('open').dialog('center').dialog('setTitle','Grade Finalization Form');
 	 		    	 $('#t17_lrf_GradeFinalize_form_id').form('clear');
 	 		    	 // $('#t17_gradeFinalize_btn').linkbutton('disable');
 	 		    	 $("#t17_final_psn").combobox('loadData', data);
 		    	 }else if(data.length == 0){
 		    		// $('#t17_gradeFinalize_btn').linkbutton('disable');
 		    		$.messager.alert('Information','No matching PSNs are found !!!');
 		    		 // $('#t17_lrf_ladleChange_form_div_id').dialog('open').dialog('center').dialog('setTitle','Ladle
						// No Change Form');
 		    		 // $('#t17_lrf_ladleChange_form_id').form('clear');
 		    	 }
 		     },
 		     error: function(){
 		    	 $.messager.alert('Processing','Error while Processing Ajax...','error');
 		     }
 	    	});
 	    }else{
 	    	$.messager.alert('Information','Please check!! Chemistry values are within PSN range','info');
 	    }
	}

	function saveFinalGrade(){
		var row = $('#t17_lrf_heatplan_tbl_id').datagrid('getSelected');
		
	    if (row){
	    	var heat_no=$('#t17_heat_no').combobox('getText');
			var trns_si_no=document.getElementById('t13_trns_si_no').value;
			var aim_psn_id = row.aim_psn;
			var heat_counter= document.getElementById('t13_heat_cnt').value;
			var plan_dtl_id = row.heat_plan_dtl_id;
			
	    	$.ajax({
			   	 headers: { 
			     		'Accept': 'application/json',
			     		'Content-Type': 'application/json' 
			     		},
			     type: 'POST',
			     url: "./LRFproduction/updateLRFProdPSN?heat_no="
					+ heat_no +"&final_plan="+plan_dtl_id+"&trns_si_no="+trns_si_no+"&heat_counter="+heat_counter,
			     dataType: 'json',
			     success: function(data){
			    	 if(data.status == 'SUCCESS'){
			    		 $('#t17_aim_psn').textbox('setText',row.psnHdrModel.psn_no);
			    		 $('#t13_aim_psn').textbox('setText',row.psnHdrModel.psn_no);
			    		 $('#t13_tar_caster').textbox('setText', row.target_caster);
			    		 document.getElementById('t13_aim_psn_id').value=aim_psn_id;
			    		 $('#t17_lrf_heatplan_tbl_id').datagrid('loadData', []);
			    		 $.messager.alert('Grade change Info',data.comment,'info');
			    	 }else {
			    		 $.messager.alert('Grade change Info',data.comment,'info');
			    	 }
			     },
			     error: function(){
			    	 $.messager.alert('Processing','Error while Processing Ajax...','error');
			     }
		    });
	    }else{
	    	$.messager.alert('Information','Please Select Grade...!','info');
	    }
	}

	function closeFinalGrade() {
		var psn_no = $('#t17_final_psn').combobox('getText');
		var aim_psn_id = $('#t17_final_psn').combobox('getValue');
		dialogBoxClose('t17_lrf_GradeFinalize_form_div_id');
		clearAllData();
		$('#t17_aim_psn').textbox('setText',psn_no);
		$('#t13_aim_psn').textbox('setText',psn_no);
		document.getElementById('t13_aim_psn_id').value=aim_psn_id;
	}

	

	function T25LRFLadleMix(){
		var heat_counter=document.getElementById('t13_heat_cnt').value;
		var t13_trns_sl_no=$('#t13_heat_id').combobox('getValue');	

		$('#t25_lrf_ladle_mix_tbl_id').edatagrid({
	  		// saveUrl: './scrapEntry/DtlsSaveOrUpdate',
			onLoadSuccess: function(data){
				var opts = $(this).datagrid('options');
				for(var i=0; i<data.rows.length; i++){
					var row = data.rows[i];
					if (row.heat_id == $('#t13_heat_id').combobox('getText')){
						$(this).edatagrid('checkRow',i);
						var tr = opts.finder.getTr(this,i);
						tr.find('input[type=checkbox]').attr('disabled','disabled');
					}
				}
			},
	  	});
		if(t13_trns_sl_no!='' && t13_trns_sl_no!=null && heat_counter>0){
			$('#t25_lrf_ladle_mix_div_id').dialog({modal:true,cache: true});
	     	$('#t25_lrf_ladle_mix_div_id').dialog('open').dialog('center').dialog('setTitle','LRF Ladle Mix Form');
	     	$('#t25_lrf_ladle_mix_form_id').form('clear');
	     	T25LRFLadleMixTblDtls();
		}
	}

	function T25LRFLadleMixTblDtls(){
   		var unit_id = $('#t13_unit').combobox('getText');
 		var h_status = 'PROCESSING';
 		$.ajax({
 			headers : {
     		'Accept' : 'application/json',
     		'Content-Type' : 'application/json'
     		},
     		type : 'GET',
     		// data: JSON.stringify(formData),
     		dataType : "json",
     		url : "./LRFproduction/getHeatsForLadleMix?CURRENT_UNIT="
     			+ unit_id +"&UNIT_PROCESS_STATUS="+h_status,
     		success : function(data) {
     			$('#t25_lrf_ladle_mix_tbl_id').datagrid('loadData', data);
     			$('#t25_lrf_ladle_mix_save_btn').linkbutton('disable');
     			$('#t25_lrf_ladle_close_save_btn').linkbutton('disable');
     		},

     		error : function() {
     			$.messager.alert('Processing', 'Error while Processing Ajax...',
     						'error');
     		}
 		});
	}
	function T25MixSelectedLadles(){
		if(validateT25MixLadles()){
			var rows=$('#t25_lrf_ladle_mix_tbl_id').datagrid('getChecked');
			if(rows.length == 0)
				$.messager.alert('Information','Please select two Checkboxes','info');
			else{
				var sub_unit_id=$('#t13_unit').combobox('getValue');
				var unitCapacity = null;
				
				$.ajax({
					headers: { 
					'Accept': 'application/json',
					'Content-Type': 'application/json' 
					},
					type: 'GET',
					// data: JSON.stringify(formData),
					dataType: "json",
					url : "./MstrSubUnit/getSubUnitCapacity?sub_unitId="
			     		+ sub_unit_id,
					success: function(data) {
						unitCapacity = data.attribute1;
						var net_wt, tot_net_wt = 0, tot_mix_qty = 0;
						var ldl1, ldl2;
						for(var i=0;i<rows.length;i++){
							net_wt = parseFloat(rows[i].steel_wgt) - parseFloat(rows[i].mix_qty);
							tot_net_wt = tot_net_wt + net_wt;
							tot_mix_qty = tot_mix_qty + parseFloat(rows[i].mix_qty);
							var mix_heat_no = rows[i].heat_id + 'M';
							if(unitCapacity != null && rows[i].mix_qty > parseFloat(unitCapacity)){
								 $.messager.alert('Information','Mix Quanity should be less than Unit Capacity...!','info');
								 return false;
							}						
							if(i == 0){
								$('#t25_act_heat_no1').textbox('setValue', rows[i].heat_id);
								// $('#t25_mod_heat_no1').textbox('setValue',
								// mix_heat_no);
								ldl1 = rows[i].steel_ladle_name;
							}else if(i == 1){
								$('#t25_act_heat_no2').textbox('setValue', rows[i].heat_id);
								// $('#t25_mod_heat_no2').textbox('setValue',
								// mix_heat_no);
								ldl2 = rows[i].steel_ladle_name;
							}
						}

						if( unitCapacity != null && tot_mix_qty > parseFloat(unitCapacity)){
							 $.messager.alert('Information','Mix Quanity should be less than Unit Capacity...!','info');
							 return false;
						}
						$('#t25_steel_wt1').numberbox('setValue', tot_mix_qty);
						$('#t25_steel_wt2').numberbox('setValue', tot_net_wt);

						var url1="./CommonPool/getComboList?col1=steel_ladle_si_no&col2=steel_ladle_no&classname=SteelLadleMasterModel&status=1&" +
						"wherecol=recordStatus=";				
						var url2="./CommonPool/getComboList?col1=steel_ladle_si_no&col2=steel_ladle_no&classname=SteelLadleMasterModel&status=1&" +
						"wherecol= steel_ladle_no in ('"+ldl1+"','"+ldl2+"') and recordStatus=";
						getDropdownList(url1,'#t25_steel_ladle_no1');
						getDropdownList(url2,'#t25_steel_ladle_no2');
						$('#t25_lrf_ladle_mix_save_btn').linkbutton('enable');
						$('#t25_lrf_ladle_close_save_btn').linkbutton('enable');
					},
					error:function(){ 
						$.messager.alert('Info','Heat Number does not exists','Info');
					}
				});
			}
		}
	}
	 function validateT25MixLadles(){
		 var rows=$('#t25_lrf_ladle_mix_tbl_id').datagrid('getChecked');
		 var allrows=$('#t25_lrf_ladle_mix_tbl_id').datagrid('getRows');
		 if(rows.length==2){
			 for(var i=0;i<allrows.length;i++){
				 $('#t25_lrf_ladle_mix_tbl_id').datagrid('endEdit', i);
			 }
			 		  
			 for(var i=0;i<rows.length;i++){
				 if(!(rows[i].mix_qty!='' && rows[i].mix_qty!=null)){
					 $.messager.alert('Information','Please Enter valid Mix Quanity...!','info');
					 return false;
				 }
			 }
		 }else{
			  $.messager.alert('Information','Please Select Minimum 2 Ladles...!','info');
			  return false;
		 } 

		 return true;
	 }
	 function saveT25LadleMixDtls(){
		 var rows=$('#t25_lrf_ladle_mix_tbl_id').datagrid('getChecked'); 
		 var trans_id1, track_id1, trans_id2, track_id2, steel_wt1, steel_wt2, ladle_no1, ladle_no2;
		 for(var i=0;i<rows.length;i++){
			 if(i == 0){
				 trans_id1 = rows[i].trns_sl_no;
				 track_id1 =  rows[i].heat_track_id;
			 }else if(i == 1){
				 trans_id2 =  rows[i].trns_sl_no;
				 track_id2 =  rows[i].heat_track_id;
			 }
		 }

		 steel_wt1 = $('#t25_steel_wt1').numberbox('getValue');
		 steel_wt2 = $('#t25_steel_wt2').numberbox('getValue');
		 ladle_no1 = $('#t25_steel_ladle_no1').combobox('getValue');
		 ladle_no2 = $('#t25_steel_ladle_no2').combobox('getValue');

		 $.ajax({
		   	 headers: { 
		     		'Accept': 'application/json',
		     		'Content-Type': 'application/json' 
		     		},
		     type: 'POST',
		     url: "./LRFproduction/saveLRFLadleMix?trns_sl_no1="
				+ trans_id1 +"&trns_sl_no2="+trans_id2+"&heat_track_id1="+track_id1+"&heat_track_id2="+track_id2
				+"&steel_wt1="+steel_wt1+"&steel_wt2="+steel_wt2+"&steel_ladle_no1="+ladle_no1+"&steel_ladle_no2="+ladle_no2,
		     dataType: 'json',
		     success: function(data){
		    	 if(data.status == 'SUCCESS'){
		    		 $.messager.alert('LRF Ladle Mix Info',data.comment,'info');
		    		 if($('#t25_act_heat_no1').textbox('getText') == $('#t13_heat_id').combobox('getText')){
		    			 $('#t13_laddle_no').textbox('setText', $('#t25_steel_ladle_no1').combobox('getText'));
		    		 }else if($('#t25_act_heat_no2').textbox('getText') == $('#t13_heat_id').combobox('getText')){
		    			 $('#t13_laddle_no').textbox('setText', $('#t25_steel_ladle_no2').combobox('getText'));
		    		 }
		    		 clearT25Data();
		    		 T25LRFLadleMixTblDtls();
		    	 }else {
		    		 $.messager.alert('LRF Ladle Mix Info',data.comment,'info');
		    	 }
		     },
		     error: function(){
		    	 $.messager.alert('Processing','Error while Processing Ajax...','error');
		     }
	    });

	 }

	 function clearT25Data() {
		 $('#t25_act_heat_no1').textbox('setText', '');
		 $('#t25_act_heat_no2').textbox('setText', '');
		 $('#t25_mod_heat_no1').textbox('setText', '');
		 $('#t25_mod_heat_no2').textbox('setText', '');
		 $('#t25_steel_wt1').numberbox('setValue', '');
		 $('#t25_steel_wt2').numberbox('setValue', '');
		 $('#t25_steel_ladle_no1').combobox('setValue', '');
		 $('#t25_steel_ladle_no2').combobox('setValue', '');			 
	 }

/* Return Heat Begin */
function setT28Mix(){
	dispT28ReturnHeatsScreen("Mix");
}
function setT28Transfer(){
	dispT28ReturnHeatsScreen("Transfer");
}
function setT28Reladle(){
	var row = $('#t28_returnheat_tbl_id').datagrid('getSelected');
	if (row) {	
		$('#t28_return_heat_reladle_div_id').dialog({
			modal : true,
			cache : true,
			onClose : function() {
				refreshT28MainScreen();
			}
		});
		$('#t28_return_heat_reladle_div_id').dialog('open').dialog('center').dialog(
				'setTitle', "Return Heat Reladle Form");
		$('#t28_return_heat_reladle_form_id').form('clear');
		
		var psn_route = row.psn_route;
		var subQry;
		if(psn_route != null && psn_route != ""){
			  if(psn_route.includes('VD'))
				  subQry = " and ladle_type = 'VD' "
			  else
				  subQry = " and ladle_type <> 'VD' "
		  }else{
			  subQry = "";
		  }
		
		var url2="./CommonPool/getComboList?col1=st_ladle_si_no&col2=steelLadleObj.steel_ladle_no&classname=SteelLadleTrackingModel&status=1&" +
		    "wherecol=ladleStatusObj.steel_ladle_status='AVAILABLE' "+subQry+" and steelLadleObj.recordStatus=1 and recordStatus=";

		getDropdownList(url2,'#t28_steelLadle');
		
		document.getElementById('t28_trns_sl_no').value=row.trns_sl_no;
		document.getElementById('t28_version').value=row.version;
		document.getElementById('t28_trnsType').value="Reladle";
		$('#t28_reladle_save').linkbutton('enable');
	}else {
		$.messager.alert('Information', 'Please Select Record...!', 'info');
	}
}
function setT28Process(){
	document.getElementById('t28_trnsType').value="Process";
	
	var row = $('#t28_returnheat_tbl_id').datagrid('getSelected');
	if (row) {	
		
		$.messager
		.confirm(
				'Confirm',
				'Do you want to Process this Return Heat..?',
				function(r) {
					if (r) {
						$.ajax({
							headers: { 
							'Accept': 'application/json',
				      		'Content-Type': 'application/json' 
							},
							type: 'POST',
							dataType: "json",
							url: './LRFproduction/saveProcessReturnHeatDetails?trans_si_no='+row.trns_sl_no,
							success: function(data) {
								if (data.status == 'SUCCESS') {
									$.messager.alert('Return Heat Info', 'Data Saved Successfully', 'info');
									refreshT28MainScreen();
								} else {
									$.messager.alert('Return Heat Info', data.comment, 'info');
								}
							},
							error:function(){      
								$.messager.alert('Processing','Error while Processing Ajax...','error');
							}
						});	
					}
					});
	}else {
		$.messager.alert('Information', 'Please Select Record...!', 'info');
	}
}
function dispT28ReturnHeatsScreen(trns_type){
	var row = $('#t28_returnheat_tbl_id').datagrid('getSelected');
	if (row) {
		var title = trns_type+" "+"Return Heat Form";
		
		$('#t28_returnheat_form_div_id').dialog({
			modal : true,
			cache : true,
			onClose : function() {
				refreshT28MainScreen();
			}
		});
		$('#t28_returnheat_mix_form_div_id').dialog('open').dialog('center').dialog(
				'setTitle', title);
		$('#t28_returnheat_mix_form_id').form('clear');
		$(function() {
			$('#t28_returnheat_mix_tbl_id').edatagrid({
			});
		});
		$('#t28_mix').linkbutton('enable');
		$('#t28_clear').linkbutton('enable');
		var dummydata = new Array();
		$('#m28_sel_mix_returnheats_tbl_id').datagrid('loadData', dummydata);
		
		$('#t28_heat_id').textbox('setText', row.heat_id);
		$('#t28_aim_psn_no').textbox('setText', row.psnHdrModel.psn_no);
		$('#t28_sub_unit_id').textbox('setText', row.subUnitMstr.sub_unit_name);
		$('#t28_steelLadleNo').textbox('setText', row.steelLdlMstr.steel_ladle_no);
		$('#t28_return_type').textbox('setText', row.returnTypeMdl.lookup_value);
		$('#t28_act_qty').textbox('setText', row.act_qty);
		$('#t28_balance_qty').textbox('setText', row.balance_qty);
		document.getElementById('t28_trns_sl_no').value=row.trns_sl_no;
		document.getElementById('t28_heatRefId').value=row.heatRefId;
		document.getElementById('t28_aim_psn_id').value=row.aim_psn_no;
		document.getElementById('t28_steel_ladle_id').value=row.steelLadleNo;
		document.getElementById('t28_version').value=row.version;
		document.getElementById('t28_heat_counter').value=row.heat_counter;
		document.getElementById('t28_trnsType').value=trns_type;
		$.ajax({
	  		headers: { 
	  		'Accept': 'application/json',
	  		'Content-Type': 'application/json' 
	  		},
	  		type: 'GET',
	  		dataType: "json",
	  		url:"./LRFproduction/getMixReturnHeatDetails?trns_sl_no="+row.trns_sl_no,
	  		success: function(data) {
	  			$('#t28_returnheat_mix_tbl_id').datagrid('loadData', data);
	  		},
	  		error:function(){      
	  			$.messager.alert('Processing','Error while Processing Ajax...','error');
	  		}
		});
	}else {
		$.messager.alert('Information', 'Please Select Record...!', 'info');
	}
}
function refreshT28MainScreen(){
	$.ajax({
  		headers: { 
  		'Accept': 'application/json',
  		'Content-Type': 'application/json' 
  		},
  		type: 'GET',
  		dataType: "json",
  		url:"./LRFproduction/getReturnHeatDetails",
  		success: function(data) {
  			$('#t28_returnheat_tbl_id').datagrid('loadData', data);
  		},
  		error:function(){      
  			$.messager.alert('Processing','Error while Processing Ajax...','error');
  		}
	});
}
function T28MixSelectedHeats(){
	$(function() {
		$('#m28_sel_mix_returnheats_tbl_id').edatagrid({
		});
	});
	var rows=$('#t28_returnheat_mix_tbl_id').datagrid('getChecked');
	if(rows.length == 0)
		$.messager.alert('Information','Please select checkboxes','info');
	else{
		var vr = $('#t28_returnheat_mix_tbl_id').datagrid('getRows');
		for ( var z = 0; z < vr.length; z++) {
			$('#t28_returnheat_mix_tbl_id').datagrid('endEdit', z);
		};  
		for (var j = 0; j < rows.length; j++) {
			if(rows[j].pour_qty == null || rows[j].pour_qty == ''){
				$.messager.alert('Information','Please enter pour qty','info');
				
				return false;
			}
		}
		/*
		 * $('#m28_sel_mix_returnheats_tbl_id').datagrid({ columns:[[
		 * {field:'heat_id',title:'Heat No',width:100},
		 * {field:'aim_psn_no',title:'PSN',width:120},
		 * {field:'return_type',title:'Return Type',width:70},
		 * {field:'steelLadleNo',title:'Ladle No',width:80},
		 * {field:'sub_unit_id',title:'Unit',width:100},
		 * {field:'act_qty',title:'Actual Qty',width:100},
		 * {field:'balance_qty',title:'Balance/Total Qty',width:100},
		 * {field:'pour_qty',title:'Pour Qty',width:100},
		 * {field:'trns_sl_no',title:'Id',width:0,hidden:true},
		 * {field:'heatRefId',title:'Ref Id',width:0,hidden:true},
		 * {field:'version',title:'version',width:0,hidden:true}, ]] });
		 */
		var balance_qty = 0;
		var type = document.getElementById('t28_trnsType').value;
		for (var i = 0; i < rows.length; i++) {
			if(type == 'Mix')
				balance_qty = (parseFloat(rows[i].balance_qty) - parseFloat(rows[i].pour_qty));
			else if(type == 'Transfer')
				balance_qty = (parseFloat(rows[i].balance_qty) + parseFloat(rows[i].pour_qty));
			
			$('#m28_sel_mix_returnheats_tbl_id').edatagrid('addRow', {
				 row : {
					 heat_id : rows[i].heat_id,
					 psn_no : rows[i].psnHdrModel.psn_no,
					 // return_type : rows[i].returnTypeMdl.lookup_value,
					 ladle_no : rows[i].steelLdlMstr.steel_ladle_no,
					 sub_unit_id : rows[i].subUnitMstr.sub_unit_name,
					 actQty : rows[i].act_qty,
					 pourQty : rows[i].pour_qty,
					 balanceQty : balance_qty, 
					 reladleHeatDtlId : rows[i].trns_sl_no,
					 // heatRefId : rows[i].heatRefId,
					 aimPsn : rows[i].aim_psn_no,
					 steelLadleId : rows[i].steelLadleNo,
					 heat_counter : rows[i].heat_counter,
					 // version : rows[i].version
				 }
			});
		}
		setBalanceWeight(type);
		$('#t28_mix').linkbutton('disable');
		$('#t28_return_heat_mix_save_btn').linkbutton('enable');
	}
}
function setBalanceWeight(trns_type){
	var rows = $('#m28_sel_mix_returnheats_tbl_id').datagrid('getRows');
	var sub_unit_name = $('#t28_sub_unit_id').textbox('getText');
	
	var pour_qty, tot_pour_qty = 0, tot_qty = 0, new_heat_id;
	for(var i = 0; i < rows.length; i++){
		pour_qty = parseFloat(rows[i].pourQty);
		tot_pour_qty = tot_pour_qty + pour_qty;
	}
	if(trns_type == 'Mix'){
		tot_qty = parseFloat($('#t28_balance_qty').textbox('getText')) + tot_pour_qty;
		var heat_id = $('#t28_heat_id').textbox('getText');
		if(sub_unit_name.match(/CCM.*/) && $('#t28_return_type').textbox('getText') != "Full"){
			var last_char = heat_id.substr(heat_id.length - 1);
			var newHeatNo = heat_id.substr(0, heat_id.length - 1);
			
			if(last_char == "A" || last_char == "B"){
				new_heat_id = newHeatNo + "X";
			}else{
				new_heat_id = newHeatNo + String.fromCharCode(last_char.charCodeAt() + 1);
			}
		}else{
			new_heat_id = heat_id;
		}
		$('#t28_new_heat_id').textbox('setText',new_heat_id);
	}else if(trns_type == 'Transfer'){
		tot_qty = parseFloat($('#t28_balance_qty').textbox('getText')) - tot_pour_qty;
	}
	$('#t28_balance_qty').textbox('setText',tot_qty);
}
function T28ClearSelectedHeats(){
	// $('#t28_returnheat_mix_tbl_id').datagrid('unselectAll');
	var rows=$('#t28_returnheat_mix_tbl_id').datagrid('getRows');
	for(var i = 0; i < rows.length; i++){
		rows[i].pour_qty = '';
		$('#t28_returnheat_mix_tbl_id').datagrid('uncheckRow', i);
	}
	$('#t28_balance_qty').textbox('setText', $('#t28_act_qty').textbox('getText') );
	var dummydata = new Array();
	$('#m28_sel_mix_returnheats_tbl_id').datagrid('loadData', dummydata);
	$('#t28_mix').linkbutton('enable');
}
function T28ClearHeatDetail(){
	/*
	 * $('#t28_new_heat_id').textbox('setText', '');
	 * $('#t28_aim_psn_no').textbox('setText', '');
	 * $('#t28_sub_unit_id').textbox('setText', '');
	 * $('#t28_steelLadleNo').textbox('setText', '');
	 * $('#t28_return_type').textbox('setText', '');
	 * $('#t28_act_qty').textbox('setText', '');
	 * $('#t28_balance_qty').textbox('setText', '');
	 * $('#t28_new_heat_id').textbox('setText', '');
	 * document.getElementById('t28_trns_sl_no').value = '';
	 * document.getElementById('t28_heatRefId').value = '';
	 * document.getElementById('t28_aim_psn_id').value = '';
	 * document.getElementById('t28_steel_ladle_id').value = '';
	 * document.getElementById('t28_trnsType').value = '';
	 * document.getElementById('t28_version').value = '';
	 */
	$('#t28_return_heat_mix_save_btn').linkbutton('disable');
	var dummydata = new Array();
	$('#t28_returnheat_mix_tbl_id').datagrid('loadData', dummydata);
	$('#t28_mix').linkbutton('disable');
	$('#t28_clear').linkbutton('disable');
}
function saveT28ReturnHeatReladle(){
	var ladle_no = $('#t28_steelLadle').combobox('getValue');
	var trns_id = document.getElementById('t28_trns_sl_no').value;
	
	$.ajax({
		headers: { 
			'Accept': 'application/json',
      		'Content-Type': 'application/json' 
		},
      	type: 'POST',
      	dataType: "json",
      	url: './LRFproduction/saveReladleReturnHeatDetails?steel_ladle_id='+ladle_no+"&trans_si_no="+trns_id,
      	success: function(data) {
      		if (data.status == 'SUCCESS') {
      			$.messager.alert('Return Heat Info', 'Data Saved Successfully', 'info');
				refreshT28MainScreen();
				$('#t28_reladle_save').linkbutton('disable');
			} else {
				$.messager.alert('Return Heat Info', data.comment, 'info');
			}
      	},
      	error:function(){      
  			$.messager.alert('Processing','Error while Processing Ajax...','error');
  		}
	});
}
function saveT28ReturnHeatMix(){
	var tbl_rows = $('#m28_sel_mix_returnheats_tbl_id').datagrid('getRows');
	if(tbl_rows.length > 0){
	var heat_no = $('#t28_new_heat_id').textbox('getText');
	var psn_id = document.getElementById('t28_aim_psn_id').value;
	var ladle_id = document.getElementById('t28_steel_ladle_id').value;
	var trns_type = document.getElementById('t28_trnsType').value;
	var act_qty = $('#t28_act_qty').textbox('getText');
	var heat_qty = $('#t28_balance_qty').textbox('getText');
	var rec_version = document.getElementById('t28_version').value;
	var trns_id = document.getElementById('t28_trns_sl_no').value;
	
	formData = {
		"reladleHeatDtlId" : trns_id,
		"heatId" : heat_no,
		"steelLadleId" : ladle_id,
		"processType" : trns_type,
		"heatQty" : heat_qty,
		"actualQty" : act_qty,
		"aimPsn" : psn_id,
		"reladleProcessDtls" : tbl_rows
	};	
	
	$.ajax({
		headers : {
			'Accept' : 'application/json',
			'Content-Type' : 'application/json'
		},
		type : 'POST',
		data : JSON.stringify(formData),
		dataType : "json",
		url : './LRFproduction/saveCCMReturnHeatDetails',
		success : function(data) {
			if (data.status == 'SUCCESS') {
				$.messager.alert('Return Heat Info', 'Data Saved Successfully', 'info');
				// T28ClearSelectedHeats();
				T28ClearHeatDetail();
				refreshT28MainScreen();
			} else {
				$.messager.alert('Return Heat Info', data.comment, 'info');
			}
		},
		error : function() {
			$.messager.alert('Processing', 'Error while Processing Ajax...', 'error');
		}
	});
	}else{
		$.messager.alert('Information','Mixing details are missings','info');
		return false;
	}
}

function LRFDispatch(){
	// LRF Dispatch automated values...LRF Dispatch Wgt = Tap Wgt + Arching
	// details except (Lime + Spar+ Syn.Slag+ Al dross+ Al Cement),
	// LRF Dispatch Temp = sample temp @ finalized chemistry
	var unit_id=$('#t13_unit').combobox('getValue');
	var heatId=$('#t13_heat_id').combobox('getText');
	var heatCnt=document.getElementById('t13_heat_cnt').value;
	var eof_tap_wgt=$('#t13_tap_wgt').textbox('getValue')!=null?$('#t13_tap_wgt').textbox('getValue'):0; 
  	$.ajax({
   		headers: { 
   		'Accept': 'application/json',
   		'Content-Type': 'application/json' 
   		},
   		type: 'GET',
   		// data: JSON.stringify(formData),
   		dataType: "json",
   		url: "./LRFproduction/getLRFArcAdditionsTemp?unit_id="+unit_id+"&heatId="+heatId+"&heatCnt="+heatCnt,
   		success: function(data) {
  		var item_total=0;
  		var grand_total=0;
   		for(i=0;i<data.length;i++){
   			var total=0.00;
   			total+=data[i]["Al_Wire"]!=null?data[i]["Al_Wire"]:0.00;
   			total+=data[i]["CPC"]!=null?data[i]["CPC"]:0.00;
  			total+=data[i]["C_wire"]!=null?data[i]["C_wire"]:0.00;
   			total+=data[i]["CaSi_W"]!=null?data[i]["CaSi_W"]:0.00;
  			total+=data[i]["Cu"]!=null?data[i]["Cu"]:0.00;
   			total+=data[i]["FeAl"]!=null?data[i]["FeAl"]:0.00;
   			total+=data[i]["FeB"]!=null?data[i]["FeB"]:0.00;
   			total+=data[i]["FeMo"]!=null?data[i]["FeMo"]:0.00;
   			total+=data[i]["FeP"]!=null?data[i]["FeP"]:0.00;
	   		total+=data[i]["FeS"]!=null?data[i]["FeS"]:0.00;
	   		total+=data[i]["FeSi"]!=null?data[i]["FeSi"]:0.00;
	   		total+=data[i]["FeTi"]!=null?data[i]["FeTi"]:0.00;
	  		total+=data[i]["FeV"]!=null?data[i]["FeV"]:0.00;
	  		total+=data[i]["GF"]!=null?data[i]["GF"]:0.00;
	  		total+=data[i]["HCFeCr"]!=null?data[i]["HCFeCr"]:0.00;
	  		total+=data[i]["HCFeMn"]!=null?data[i]["HCFeMn"]:0.00;
	  		total+=data[i]["LCFeCr"]!=null?data[i]["LCFeCr"]:0.00;
	   		total+=data[i]["LPHCFeMn"]!=null?data[i]["LPHCFeMn"]:0.00;
			total+=data[i]["LPSiMn"]!=null?data[i]["LPSiMn"]:0.00;
	   		total+=data[i]["LTiHCFeCr"]!=null?data[i]["LTiHCFeCr"]:0.00;
	   		total+=data[i]["Mn Metal"]!=null?data[i]["Mn Metal"]:0.00;
	   		total+=data[i]["Ni "]!=null?data[i]["Ni "]:0.00;
	   		total+=data[i]["Pb wire"]!=null?data[i]["Pb wire"]:0.00;
	   		total+=data[i]["S_Wire "]!=null?data[i]["S_Wire "]:0.00;
	   		total+=data[i]["SiMn"]!=null?data[i]["SiMn"]:0.00;
	   		total+=data[i]["Si Metal"]!=null?data[i]["Si Metal"]:0.00;
	   			
	   		item_total+=total;// Material total is @ KGS .... it should be
									// converted to TONS....
   		}	
	   		     			grand_total=parseFloat(eof_tap_wgt) + parseFloat((item_total)*(1/1000));// Converting
																										// KGS
																										// material
																										// to
																										// TONS...EOF
																										// tap
																										// wght
																										// is
																										// already
																										// in
																										// TONS
	   			     		$('#t21_lrfDispatchWgt').numberbox('setValue',grand_total); // Value
																						// in
																						// Tons.......
	   		  },
	   		error:function(){      
	   			$.messager.alert('Processing','Error while Processing Ajax...','error');
	   		  }
	   		});  
}
function T29DispatchToLrf(){
	$('#t29_disp_to_lrf_div_id').dialog({
		modal : true,
		cache : true,
		onClose : function() {
			refreshT28MainScreen();
		}
	});
	$('#t29_disp_to_lrf_div_id').dialog('open').dialog('center').dialog(
			'setTitle', "Dispatch To LRF Form");
	$('#t29_disp_to_lrf_form_id').form('clear');
	// var heat_id= $('#t13_heat_id').combobox('getText');
	// $('#t29_heat_no').textbox('setText',heat_id);
	$('#t29_disp_to_lrf_save_btn').linkbutton('enable');
	var sub_unit = $('#t13_unit').combobox('getValue');
	
	var url2="./CommonPool/getComboList?col1=sub_unit_id&col2=sub_unit_name&classname=SubUnitMasterModel&status=1&wherecol= unitDetailsMstrMdl.unit_name='LRF' and sub_unit_id <> "+sub_unit+" or sub_unit_name = 'RLS' and record_status=";
	var url5="./CommonPool/getComboList?col1=lookup_id&col2=lookup_value&classname=LookupMasterModel&status=1&wherecol=lookup_type='LRF_PURGING_MEDIUM' and lookup_status=";
    //var url6="./CommonPool/getComboList?col1=lookup_id&col2=lookup_value&classname=LookupMasterModel&status=1&wherecol=lookup_type='LRF_PROCESS_CONTROL' and lookup_status=";    
    //var url7="./CommonPool/getComboList?col1=lookup_id&col2=lookup_value&classname=LookupMasterModel&status=1&wherecol=lookup_type='VESSEL_CAR'  and lookup_status=";
	     
    getDropdownList(url2,'#t29_unit');
    getDropdownList(url5,'#t29_purging_medium');    
	//getDropdownList(url6,'#t29_process_control');         
	//getDropdownList(url7,'#t29_vessel_car_no'); 
}
function SaveT29DispatchToLrf(){
	if($('#t29_disp_to_lrf_form_id').form('validate')){
		var validate = false;
		var unit_id=$('#t29_unit').combobox('getValue');
		var unit_name=$('#t29_unit').combobox('getText');
		var heat_id= $('#t13_heat_id').combobox('getText'); // $('#t29_heat_no').textbox('getValue');;
		var trns_id=$('#t13_heat_id').combobox('getValue');
		var heat_counter, purging_medium = null;
		if(unit_name == 'RLS'){
			heat_counter = document.getElementById('t13_heat_cnt').value;
			validate = true;
		}else{
			heat_counter=parseInt(document.getElementById('t13_heat_cnt').value) + 1;
			purging_medium= $('#t29_purging_medium').combobox('getValue');
			//process_control= $('#t29_process_control').combobox('getValue');
			//vessel_car =$("#t29_vessel_car_no").combobox('getValue');
			if(purging_medium != null)
				validate = true;
		}
		var remarks = $('#t29_remarks').textbox('getValue');
		var heat_track_id=document.getElementById('t13_heat_track_id').value;
		var shift;
		var datetime = new Date();
		var hours=datetime.getHours();
		if(hours>=6&&hours<14){
			shift = 'A';
		}else if(hours>=14&&hours<22){
			shift = 'B';
		}else{
			shift = 'C';
		}
		
		formData={"heat_id": heat_id,"heat_counter": heat_counter,"sub_unit_id":unit_id,"sub_unit_name":unit_name,
      		    "purge_medium":purging_medium,"returnRemarks":remarks,"heat_track_id":heat_track_id};
		if(validate){
			$.ajax({
				headers : {
					'Accept' : 'application/json',
					'Content-Type' : 'application/json'
				},
				type : 'POST',
				data : JSON.stringify(formData),
				dataType : "json",
				url : './LRFproduction/SaveDispatchToLrf?shift='+shift+'&trans_id='+trns_id,  
				success : function(data) {
					if (data.status == 'SUCCESS') {
						$.messager.alert('Dispatch to LRF/RLS', 'Data Saved Successfully', 'info');
						clearT13HdrDtls();
						$('#t29_disp_to_lrf_save_btn').linkbutton('disable');
					} else {
						$.messager.alert('Dispatch to LRF', data.comment, 'info');
					}
				},
				error : function() {
					$.messager.alert('Processing', 'Error while Processing Ajax...', 'error');
				}
			});
		}else{
			$.messager.alert('Dispatch to LRF/RLS', 'Purging Medium, Process Control, Vessel car fields are mandatory!!!', 'info');
		}
	}
}
function clearT13HdrDtls(){
	var dummydata = new Array();
	$('#t13_heat_id').combobox('loadData', dummydata);
	getT13UpdatedHeatList();
	$('#t13_aim_psn').textbox('setValue','');
	$('#t13_tar_caster').textbox('setValue','');
	$('#t13_prev_unit').textbox('setValue','');
	$('#t13_laddle_no').textbox('setValue','');
	$('#t13_tap_temp').textbox('setValue','');     
	$('#t13_tap_wgt').textbox('setValue','');
	$('#t13_initial_temp').textbox('setValue','');
	         
	$('#t13_purging_medium').combobox('setValue','');
	//$('#t13_process_control').combobox('setValue','');
	//$('#t13_vessel_car_no').combobox('setValue','');
	         
	document.getElementById('t13_trns_si_no').value='';
	document.getElementById('t13_aim_psn_id').value=''; 
	document.getElementById('t13_heat_cnt').value='';
	document.getElementById('t13_caster_id').value='';
	document.getElementById('t13_sub_unit_id').value='';
	document.getElementById('t13_heat_track_id').value='';
}

function viewT15PrevDtls(){
	var eof_trns_sno;
	var heat_no=$('#t13_heat_id').combobox('getText');
	var aim_psnId=$('#t13_aim_psn').textbox('getText');
	$.ajax({
		headers : {
			'Accept' : 'application/json',
			'Content-Type' : 'application/json'
		},
		type : 'GET',
		dataType : "json",
		url : "./EOFproduction/getEofTrnsNo?heat_no="+heat_no,
		success : function(data) {
			$.ajax({
				headers : {
					'Accept' : 'application/json',
					'Content-Type' : 'application/json'
				},
				type : 'GET',
				beforeSend:function(){
					$.messager.progress({ text:'Fetching Data'});
					},
					complete: function(){
					$.messager.progress('close');
					},
				// data: JSON.stringify(formData),
				dataType : "json",
				url : "./heatProcessEvent/getMtrlDetailsByType?mtrlType=LADLE_ADDITIONS"
						+ "&eof_trns_sno=" + data.trns_si_no +"&psn_no="+aim_psnId,
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
					$('#t6_reuse_eof_Ladle_Addition_div').dialog({modal:true,cache: false});
					$('#t6_reuse_eof_Ladle_Addition_div').dialog('open').dialog('center')
					.dialog('setTitle', 'EAF Ladle Addition Details');

				},
				error : function() {
					$.messager.alert('Processing',
							'Error while Processing Ajax...', 'error');
				}
			});

		},
		error : function() {
			$.messager.alert('Processing',
					'Error while Getting EOF trns_sl_no...', 'error');
		}
	})
}