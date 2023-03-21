/**. Begin load data into HeatDetails grid ## Button:Process Heat Plan * */
function t22LoadHeatDetailsData(){
	 $('#t22_vd_save_btn_id').linkbutton('enable');
	if(validateT22VDCrewDetailsform()){
	var row = $('#t22_vd_production_tbl_id').datagrid('getSelected');

	if(row){
		$.messager.confirm('Confirm', 'Do you want to process selected laddle ?', function(r){
			if (r){
				 var heatno =row.heat_id;
				 var laddlename =row.steel_ladle_name;
				 var lrftemp=row.lrf_dispatch_temp;
				 var steelwgt=row.steel_wgt;
				 var mbar_achieved_time= row.lowest_m_bar;
				 var mbar_retention_time=row.holding_time;
			   	 var tarcaster=row.target_caster_name;
			  	 var prevunit=row.prev_unit;
			  	 var aimpsn=row.aim_psn_char;
			 	
			   	  
			   	  	$('#t22_heat_id').textbox('setText',heatno);
			 	    $('#t22_aim_psn').textbox('setText',aimpsn);
			 	    $('#t22_tar_caster').textbox('setText',tarcaster);
				    $('#t22_prev_unit').textbox('setText',prevunit);
				    $('#t22_laddle_no').textbox('setText',laddlename);
			 	    $('#t22_lrf_temp').textbox('setText',lrftemp);			 	  
			 	    $('#t22_steel_wgt').textbox('setText',steelwgt);
			 	    $('#t22_lowest_mbar').textbox('setText',mbar_achieved_time);
			 	    $('#t22_holding_mbar').textbox('setText',mbar_retention_time);
			 	    $('#t22_initial_temp').textbox('setText','');			 	   
			 	   /* $('#t22_plan_no').textbox('setText',planno);
			 	    $('#t22_plan_line_no').textbox('setText',planlineno);*/

			 	   	document.getElementById('t22_heat_cnt').value=row.heat_counter;
			 	    document.getElementById('t22_caster_id').value=row.target_caster_id;
			 	    document.getElementById('t22_sub_unit_id').value=row.sub_unit_id;
			 	    document.getElementById('t22_act_path').value=row.act_proc_path;			 	   
			 	    document.getElementById('t22_heat_track_id').value=row.heat_track_id;
			 	    document.getElementById('t22_aim_psn_id').value=row.aim_psn;
			 	    document.getElementById('t22_steel_ladleno_id').value=row.steel_ladle_no;
			 	    document.getElementById('t22_plan_no').value=row.heat_plan_id;
			 	    document.getElementById('t22_plan_line_no').value=row.heat_plan_line_no;  
			}
		});
		 
    } else{
    		$.messager.alert('VD Production','Please Select One Record to Process Heat ...!','info');
    	}
}
	else{
		$.messager.alert('VD Production','Please select all Crew Details  !','info');
	}
}


/** 1.End load data into HeatDetails grid * */

/**2. Begin savet22VDHeatDetails() ## Button:Save * */
function saveT22VDHeatDetails(){
		 if(validateT22VDHeatDetailsform()){
		 if(validateT22VDCrewDetailsform()){
			 var cunitId=$('#t22_unit').combobox('getValue');
			 var cunitName=$('#t22_unit').combobox('getText');
			 var heat_id= $('#t22_heat_id').combobox('getText'); 
			 var heatCounter= document.getElementById('t22_heat_cnt').value;
			 var steelwgt=$('#t22_steel_wgt').textbox('getText'); 
			 var mbar_achieved_time=$('#t22_lowest_mbar').textbox('getText'); 
			 var mbar_retention_time=$('#t22_holding_mbar').textbox('getText'); 
			 var stLadleTrackId=document.getElementById('t22_steel_ladleno_id').value;
			 var lrfTemp=$('#t22_lrf_temp').textbox('getText'); 
			 //var vdInitialTemp=$('#t22_initial_temp').textbox('getText'); 
			 var prevUnit=$('#t22_prev_unit').textbox('getText');
			 var aimPsn=document.getElementById('t22_aim_psn_id').value;
			 var prodShift=$('#t22_shift').combobox('getValue');
			 var heat_plan_id=document.getElementById('t22_plan_no').value;
			 var heat_plan_line_id= document.getElementById('t22_plan_line_no').value;
			 var targetCasterId= document.getElementById('t22_caster_id').value;
			 var trns_si_no=0;
			 var shift_mgr=$('#t22_shiftMgr').combobox('getValue');
			 //var vd_mgr=$('#t22_vdInCharge').combobox('getValue');
			 var vd_optr=$('#t22_vdOperator').combobox('getValue');
			 
			 var main_status="WIP";
			 var proc_path=document.getElementById('t22_act_path').value;
			 var act_proc_path = proc_path.concat('-'+cunitName);
			 var unit_status="PROCESSING";
			 var vd_status="PROCESSING";
			 var heatTrackId= document.getElementById('t22_heat_track_id').value;
			 var user_role_id_1= shift_mgr;
			 //var user_role_id_2= vd_mgr;
			 var user_role_id_3=vd_optr;
			 var formData = {
				  "vdHeatDetails":{
					  "trns_si_no":trns_si_no,"sub_unit_id":cunitId,"heat_id":heat_id,"heat_counter":heatCounter,
					  "heat_plan_id":heat_plan_id,"heat_plan_line_id":heat_plan_line_id,
					  "steel_wgt":steelwgt,"lowest_m_bar":mbar_achieved_time,"holding_time":mbar_retention_time,"target_caster":targetCasterId,"production_shift":prodShift,
					  "aim_psn":aimPsn,"dispatch_temp":lrfTemp,"prev_unit":prevUnit,"steel_ladle_no":stLadleTrackId
					  //"vd_initial_temp":vdInitialTemp
				  },
				  "vdCrewDetList":[{
					  "user_role_id":user_role_id_1,"heat_id":heat_id,"heat_counter":heatCounter,"sub_unit_id":cunitId
				  },{
					  "user_role_id":user_role_id_3,"heat_id":heat_id,"heat_counter":heatCounter,"sub_unit_id":cunitId 
				  }
				  ],
				  "vdHeatStatus":{
					  "heat_track_id":heatTrackId,"main_status":main_status,"act_proc_path":act_proc_path,
					  "current_unit":cunitName,"unit_process_status":unit_status,
					  "vd_status":vd_status
				  }
				};
			 
				$.ajax({
			 		headers: { 
			 		'Accept': 'application/json',
			 		'Content-Type': 'application/json' 
			 		},
			 		type: 'POST',
			 		data: JSON.stringify(formData),
			 		// data: JSON.stringify({ VDHeatDetails: formData1, VDCrewDet: formData2,VDHeatStatus:formData3}),
			 		
			 		dataType: "json",
			 		url: './VDproduction/VDHeatSave',
			 		success: function(data) {
			 		    if(data.status == 'SUCCESS') 
			 		    	{
			 		    	
			 		    	$.messager.alert('VD Heat Details Info',data.comment,'info');
			 		    	getT22RefreshHotmetalVDGrid();
			 		    	//clearT22HeatHdrform();
			 		    	getT22UpdatedHeatList();			 		    	
			 		    	}else {
			 		    		$.messager.alert('VD Heat Details Info',data.comment,'info');
			 		    	}
			 		  },
			 		error:function(){      
			 			$.messager.alert('Processing','Error while Processing Ajax...','error');
			 		  }
			 		});
				 
				 
		}
		 //alert('validation failed');
		 }
}
/** 2.End savet22VDHeatDetails() * */


function getT22UpdatedHeatList()
{
	var unit_id=$('#t22_unit').combobox('getValue');
//	alert("id--> "+unit_id);
	var url6="./CommonPool/getComboList?col1=trns_si_no&col2=heat_id&classname=VDHeatDetailsModel&status=1&wherecol=sub_unit_id="+unit_id+"  and DISPATCH_TO_UNIT=NULL and record_status="
	getDropdownList(url6,'#t22_heat_id');
	
}
function setT22HeatFormData()
{
	
	 var t22_trns_si_no=$('#t22_heat_id').combobox('getValue');
		if(t22_trns_si_no!=null && t22_trns_si_no!=''){
			//clearT4HeatHdrform();
		$.ajax({
		headers: { 
		'Accept': 'application/json',
		'Content-Type': 'application/json' 
		},
		type: 'GET',
		//data: JSON.stringify(formData),
		dataType: "json",
		url: './VDproduction/getVDHeatFormDtlsById?trns_sl_no='+t22_trns_si_no,
		success: function(data) {
			 document.getElementById('t22_trns_si_no').value=data.trns_si_no;
			 document.getElementById('t22_heat_cnt').value=data.heat_counter;
			 document.getElementById('t22_aim_psn_id').value=data.aim_psn;
			 document.getElementById('t22_sub_unit_id').value=data.sub_unit_id;
			 document.getElementById('t22_heat_track_id').value=data.heat_trackingId;
			 document.getElementById('t22_steel_ladleno_id').value=data.steel_ladle_no;
			 document.getElementById('t22_plan_no').value=data.heat_plan_id;
			 document.getElementById('t22_plan_line_no').value=data.heat_plan_line_id;
			 document.getElementById('t22_caster_id').value=data.target_caster;
			 $('#t22_recDate').datetimebox('setText',formatDate(data.production_date));
			 $('#t22_shift').combobox('setValue',data.production_shift);
			$('#t22_unit').combobox('setValue',data.sub_unit_id);
			$('#t22_unit').textbox('setText',data.sub_unit_name);
			
			 $('#t22_aim_psn').textbox('setValue',data.psn_no);
			 $('#t22_tar_caster').textbox('setValue',data.target_caster_name);
			 $('#t22_prev_unit').textbox('setValue',data.prev_unit);
			 $('#t22_prev_unit_id').combobox('setValue',data.prev_unit);
			 $('#t22_laddle_no').textbox('setValue',data.steel_ladle_name);
			 $('#t22_lrf_temp').textbox('setValue',data.dispatch_temp);
			 $('#t22_steel_wgt').textbox('setValue',data.steel_wgt);
			 $('#t22_lowest_mbar').textbox('setValue',data.lowest_m_bar);
			 $('#t22_holding_mbar').textbox('setValue',data.holding_time);
			 
			
			
			 setT22CrewDetails();
			
		  },
		error:function(){ 
			$.messager.alert('Info','Heat Number does not exists','Info');
		  }
		});
		}else{
			$.messager.alert('Info','Please Enter Heat Number','Info');
		}
		
	 
}


$('#t22_heat_id').combobox({
		onSelect: function(){
			setT22HeatFormData();
			$("#T22ladleDetailsId").hide();
			//setAimPsnFormData();
},onChange: function(){
	var t22_trns_sl_no=$('#t22_heat_id').combobox('getValue');
	var t22_sub_unit_id=$('#t22_unit').combobox('getValue');
	 if((t22_sub_unit_id!=null && t22_sub_unit_id!='')){
		 getT22UpdatedHeatList();
	if(!(t22_trns_sl_no!=null && t22_trns_sl_no!='')){
	  	document.getElementById('t22_trns_si_no').value='';
		document.getElementById('t22_aim_psn_id').value=''; 
		document.getElementById('t22_heat_cnt').value='';
		document.getElementById('t22_caster_id').value='';
		document.getElementById('t22_sub_unit_id').value='';
	
		 
		 $('#t22_aim_psn').textbox('setValue','');
		 $('#t22_unit').textbox('setValue','');
		 $('#t22_laddle_no').textbox('setValue','');
	     $('#t22_steel_wgt').numberbox('setValue','');
	     $('#t22_lowest_mbar').numberbox('setValue','');
	     $('#t22_holding_mbar').numberbox('setValue','');
		 $('#t22_lrf_temp').numberbox('setValue','');
		 
}
	 }else{
		 $('#t22_heat_id').textbox('setValue','');
		 $('#t22_heat_id').textbox('setText','');
	$.messager.alert('Info','Please select Unit in Crew Detail Grid','Info');
	
	 }
}
});

function setT22CrewDetails(){
	 var heat_no=$('#t22_heat_id').combobox('getText');
	 var heat_counter=document.getElementById('t22_heat_cnt').value;
	 var unit_id=$('#t22_unit').combobox('getValue');
		if(heat_no!=null && heat_no!=''){
			//clearT4HeatHdrform();
		$.ajax({
		headers: { 
		'Accept': 'application/json',
		'Content-Type': 'application/json' 
		},
		type: 'GET',
		//data: JSON.stringify(formData),
		dataType: "json",
		url: './EOFproduction/getEOFCrewDtlsbyHeatNo?Heat_no='+heat_no+'&unit_id='+unit_id+'&heat_counter='+heat_counter,
		success: function(data) {
			for(var i=0;i<data.length;i++){
				if(data[i].userRoleMapMstrMdl.lookupMasterModel.lookup_code=='VD_SHIFT_INCHARGE'){
					 $('#t22_shiftMgr').combobox('setText',data[i].userRoleMapMstrMdl.appUserAccountDetails.user_name);
					 $('#t22_shiftMgr').combobox('setValue',data[i].user_role_id);
				}
				/*if(data[i].userRoleMapMstrMdl.lookupMasterModel.lookup_code=='VD_SECTION_INCHARGE'){
					$('#t22_vdInCharge').combobox('setText',data[i].userRoleMapMstrMdl.appUserAccountDetails.user_name);
					$('#t22_vdInCharge').combobox('setValue',data[i].user_role_id);
				}*/
				if(data[i].userRoleMapMstrMdl.lookupMasterModel.lookup_code=='VD_OPERATOR'){
					$('#t22_vdOperator').combobox('setText',data[i].userRoleMapMstrMdl.appUserAccountDetails.user_name);
					$('#t22_vdOperator').combobox('setValue',data[i].user_role_id);
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


function clearT22HeatHdrform()
	{
	 $('#t22_heat_hdr_form_id').form('clear');
	}

 function getT22RefreshHotmetalVDGrid()
 {
 	$.ajax({
  		headers: { 
  		'Accept': 'application/json',
  		'Content-Type': 'application/json' 
  		},
  		type: 'GET',
  		//data: JSON.stringify(formData),
  		dataType: "json",
  		url:"./LRFproduction/getHeatsWaitingForVDProcess?CURRENT_UNIT=VD&UNIT_PROCESS_STATUS=WAITING FOR PROCESSING",
  		success: function(data) {
  			 $('#t22_vd_production_tbl_id').datagrid('loadData', data);
  		  },
  		error:function(){      
  			$.messager.alert('Processing','Error while Processing Ajax...','error');
  		  }
  		});
 	
 }
function validateT22VDCrewDetailsform(){
	    return $('#t22_crew_form_id').form('validate');
}

function validateT22VDHeatDetailsform(){
    return $('#t22_heat_hdr_form_id').form('validate');

}




/**3.Begin  PSN Document Start * */
    
    function t22PSNDocsView(){
		var psn_hdr_sl_no=document.getElementById('t22_aim_psn_id').value;
		//psn_hdr_sl_no='2';;
		var psn_no=$('#t22_aim_psn').combobox('getText');
		if(psn_hdr_sl_no!=null && psn_hdr_sl_no!=''){
 		$('#t22_PSN_Docs_form_div_id').dialog({modal:true,cache: true});
 	    $('#t22_PSN_Docs_form_div_id').dialog('open').dialog('center').dialog('setTitle','PSN Document Details Form');
 	    t22PSNDocsInit();
 	    $('#t22_psn_no_docs').textbox('setText',psn_no);
 	  
 	   refreshT22PSNDocs();
		}else{
			$.messager.alert('PSN DOC Details Info','Please Select Heat & Check PSN ...!','info');
		}
 	   
 	}
	
	function t22PSNDocsInit(){
		// var psn_hdr_sl_no=document.getElementById('m10_psn_hdr_sl_no').value;
	    $('#t22_PSN_Docs_tbl_id').edatagrid({
	        
	    });
	
	$('#t22_PSN_Docs_tbl_id').edatagrid({
	onSuccess: function(index,row){
	
		refreshT22PSNDocs();
	},onError: function(index,row){
    		alert("Error In "+row.doc_sl_no);
	}
	});
	
	}
	
	function refreshT22PSNDocs()
	{
		var psn_hdr_sl_no=document.getElementById('t22_aim_psn_id').value;
		var psn_no=$('#t22_aim_psn').combobox('getText');
	 	$.ajax({
	 		headers: { 
	 		'Accept': 'application/json',
	 		'Content-Type': 'application/json' 
	 		},
	 		type: 'GET',
	 		// data: JSON.stringify(formData),
	 		dataType: "json",
	 		url:'./heatProcessEvent/getPSNDocsByPSN?psn_no='+psn_no+'&psn_hdr_sl_no='+psn_hdr_sl_no,
	 		success: function(data) {
	 			// $('#m19_PSN_Docs_tbl_id').edatagrid('loadData', data);
	 			$('#t22_PSN_Docs_tbl_id').edatagrid('loadData', data);
	 		  },
	 		error:function(){      
	 			$.messager.alert('Processing','Error while Processing Ajax...','error');
	 		  }
	 		});
	}
	
    
    
    function t22viewPSNFile(value,row){
    	
    	var href = './heatProcessEvent/PSNDocsView?doc_sl_no='+row.doc_sl_no;
    	return '<a target="_blank" href="' + href + '">View Detail</a>';
    	

    }
    
    function t22docsView(){
    	
    	
    	var row = $('#t22_PSN_Docs_tbl_id').datagrid('getSelected');
    	// alert("row="+row);
    	if(row){
    		var url='./heatProcessEvent/PSNDocsView?doc_sl_no='+row.doc_sl_no;
       	
        	window.open(url);
        	// refreshT10PSNDocs();
        } else{
        		$.messager.alert('PSN Doc Details Info','Please Select One Record to View PSN Doc ...!','info');
        	}
    }
  
    $(window).load(setTimeout(applyT22Filter,1));  // 1000 ms = 1 second.
    
    function applyT22Filter()
    {
		 $('#t22_PSN_Docs_tbl_id').datagrid('enableFilter');
		 
    } 
    
    function cancelT22PSNDocs(){
    	refreshT22PSNDocs(); 
    }
    
    /** PSN Document Screen End * */
    
    /** Chemical Details Screen Start * */
    function T22ChemistryDtls(){
  	  var heat_counter=document.getElementById('t22_heat_cnt').value;
    	  var t22_trns_sl_no=$('#t22_heat_id').combobox('getValue');
        if(t22_trns_sl_no!='' && t22_trns_sl_no!=null && heat_counter>0){
           	$('#t22_vd_Chemistry_form_div_id').dialog({modal:true,cache: true});
       	    $('#t22_vd_Chemistry_form_div_id').dialog('open').dialog('center').dialog('setTitle','Chemical Details Entry Form');
       	    $('#t22_vd_Chemistry_form_id').form('clear');
       	    var heatId = $('#t22_heat_id').combobox('getText'); //$('#t22_heat_id').textbox('getText');
       	    var heatcnt = document.getElementById('t22_heat_cnt').value;
      	    var aimpsn =$('#t22_aim_psn').textbox('getText');

      	    T22ChemistryDtlsOpen();
      	    $('#t22_spectro').hide();
      	    $('#t22_heat_no').textbox('setValue',heatId);
      	    $('#t22_che_aim_psn').textbox('setValue',aimpsn);
      	    $('#t22_sample_date_time').datetimebox({
           	    value: (formatDate(new Date()))      	   
           	});
      	    getT22Dropdowns();   	    
      	    $.ajax({
      	   		headers: { 
      	   		'Accept': 'application/json',
      	   		'Content-Type': 'application/json' 
      	   		},
      	   		type: 'GET',
      	   		// data: JSON.stringify(formData),
      	   		dataType: "json",
      	   		url: "./VDproduction/VDLiftChem",
      	   		success: function(data) {
      	   			document.getElementById('t22_chem_level').value = data.comment;
      	   			refreshT22Chem();
      	   		},
      	   		error:function(){      
      	   			$.messager.alert('Processing','Error while Processing Ajax...','error');
     	   		  	}
      	   	});
    		}else{
    			$.messager.alert('Information','Please Select Heat...!','info');
    		}
        $('#t22_vd_Chemistry_tbl_id').datagrid('hideColumn', 'remarks'); 	// hide spectro lab remarks
  																			// column initially @ t22_lrf_Chemistry_tbl_id
    }
    
    function T22ChemistryDtlsOpen(){ 
  	  	var minmax_flag = false, color_flag = false, rej_flag = '';
  	  	$('#t22_vd_Chemistry_tbl_id').edatagrid({
  	  		// saveUrl: './scrapEntry/DtlsSaveOrUpdate',
  	  	});
    	    $('#t22_vd_Chemistry_tbl_id').datagrid({  	      	 
    	    	  onBeforeEdit:function(index,row){ 
    	    		  $('#t22_vd_Chemistry_tbl_id').datagrid('selectRow', index);
    	          },
    	          onBeginEdit:function(index,row){
    	        	color_flag = false;
    	        	var editors = $('#t22_vd_Chemistry_tbl_id').datagrid('getEditors', index); 	        	
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
    	          				setDefaultCustomComboValues('t22_sample_result', 'REJECT', $('#t22_sample_result').combobox('getData'));
    	          			}else{
    	          				color_flag = false;
    	          				if(rej_flag != '' && rej_flag == index){
    	          					setDefaultCustomComboValues('t22_sample_result', 'OK', $('#t22_sample_result').combobox('getData'));
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
    				$('#t22_vd_Chemistry_tbl_id').datagrid('selectRow', index);	
    			 }
    	    });  	    
    }

    function getT22Dropdowns(){
        var url1="./CommonPool/getComboList?col1=lookup_id&col2=lookup_value&classname=LookupMasterModel&status=1&" +
  	    "wherecol=lookup_type='CHEM_LEVEL' and lookup_value in ('LRF_INITIAL_CHEM','LRF_AVD_CHEM','LRF_CHEM','LRF_LIFT_CHEM') and lookup_status=";
        var url2="./CommonPool/getComboList?col1=lookup_id&col2=lookup_value&classname=LookupMasterModel&status=1&" +
    	    "wherecol=lookup_type='CHEM_TEST_RESULT' and lookup_status=";
        // getDropdownList(url1,'#t22_analysis_type');
        getDropdownList(url2,'#t22_sample_result');
    }

    $("#t22_analysis_type").combobox({
      	onSelect:function(record) {
      		$('#t22_sample_no').combobox('setValue','');
      		refreshT22Chem();
      	}
    });

    function refreshT22Chem(){
  	  $('#t22_sample_temp').numberbox('setValue', '');
  	  $('#t22_remarks').textbox('setText', '');
  	  document.getElementById('t22_sample_si_no').value = '0';
  	  setDefaultCustomComboValues('t22_sample_result','OK', $('#t22_sample_result').combobox('getData'));
  	  getT22ChemistryDtlsGrid();
  	  getT22ChemSampleDtls();
  	  $('#t22_final_result_btn_id').linkbutton('enable');
  	  $('#t22_save_chem_btn_id').linkbutton('enable');
  	  $('#t22_close_chem_btn_id').linkbutton('enable');
  	  $('#t22_getSample_btn').linkbutton('enable');
    }

    // Final Result Datagrid t22_lrf_chem_samp_tbl_id
    function getT22ChemSampleDtls() {
  	  var heat_id = $('#t22_heat_id').combobox('getText');
  	  // var analysis_id = $('#t22_analysis_type').combobox('getValue');
  	  var analysis_id = 0;
  	  var sub_unit_id = document.getElementById('t22_sub_unit_id').value;
  	  var heat_counter=	document.getElementById('t22_heat_cnt').value;
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
    					if(sub_unit.match(/VD.*/)){
    						tableData.push(data[i]); 
    					}
    				}
    				$('#t22_vd_chem_samp_tbl_id').datagrid('loadData', tableData);
    			},
    			error : function() {
    				$.messager.alert('Processing',
    						'Error while Processing Ajax...', 'error');
    			}
    		});
    	}
    }

    function viewT22SampleDtls(value, row) {
  	  /*
  		 * return '<a href="#" onclick="viewT22ChemDtls(\'' + row.sample_si_no
  		 * +','+row.sample_no+','+row.sample_temp+','+formatDate(row.sample_date_time)+','
  		 * 
  		 * +row.sample_result+','+row.remarks+','+row.chem_validation+
  		 * '\')">View Detail</a>';
  		 */
  	  return '<a href="#" onclick="viewT22ChemDtls(\''
  		+ row.sample_si_no +','+row.sample_no+','+row.sample_temp+','+formatDate(row.sample_date_time)+','
  		+row.sample_result+','+row.remarks+ '\')">View Detail</a>';
    }
    function viewT22ChemDtls(chem_dtls){
  		var analysis_id = document.getElementById('t22_chem_level').value;
  		var psn_id = document.getElementById('t22_aim_psn_id').value;
  		var c_dtl=chem_dtls.split(",");
  		chem_hdr_id = c_dtl[0]; samp_no = c_dtl[1]; samp_temp = c_dtl[2];
  		samp_date = c_dtl[3]; samp_res = c_dtl[4]; remarks = c_dtl[5];
  		document.getElementById('t22_sample_si_no').value=chem_hdr_id;
  		// document.getElementById('t22_chem_validation').value=c_dtl[6];
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
  				$('#t22_vd_Chemistry_tbl_id').datagrid('loadData', data);
  				$('#t22_sample_no').combobox('setValue', samp_no);
  				$('#t22_sample_date_time').datetimebox({	value : samp_date});
  				$('#t22_sample_temp').numberbox('setValue', samp_temp);
  				$('#t22_sample_result').combobox('setValue', samp_res);
  				$('#t22_getSample_btn').linkbutton('disable');
  				if(remarks != 'null'){
  					$('#t22_remarks').textbox('setText', remarks);}				
  			},
  			error : function() {
  				$.messager.alert('Processing',
  						'Error while Processing Ajax...', 'error');
  			}
  		});
  	}
    function getT22ChemistryDtlsGrid() {
    	var heat_id=$('#t22_heat_id').combobox('getText');
    	var heat_counter=	document.getElementById('t22_heat_cnt').value;
    	var analysis_id = document.getElementById('t22_chem_level').value;
    	var sample_no = $('#t22_sample_no').combobox('getText');
    	var sub_unit_id=document.getElementById('t22_sub_unit_id').value;
    	var aim_psnId=document.getElementById('t22_aim_psn_id').value;
    	if(heat_id!=null && heat_id!='' && heat_counter!=null && heat_counter!=''){		
    	$.ajax({
     		headers: { 
     		'Accept': 'application/json',
     		'Content-Type': 'application/json' 
     		},
     		type: 'GET',
     		// data: JSON.stringify(formData),
     		dataType: "json",
     		url: "./VDproduction/getChemDtlsByAnalysis?analysis_id="+analysis_id+"&heat_id="+heat_id+"&heat_counter="+heat_counter+"&sub_unit_id="+sub_unit_id+"&sample_no="+sample_no+"&aim_psn_id="+aim_psnId,
     		success: function(data) {
     			 $('#t22_vd_Chemistry_tbl_id').datagrid('loadData', data);
     		},
     		error:function(){      
     			$.messager.alert('Processing','Error while Processing Ajax...','error');
     		}
     		});
    	}
    }
    function GetSample(){  	 
  	  GenerateSampleNo();
  	 refreshT22Chem();
    }
    function GenerateSampleNo() {
   	var heatId = String($('#t22_heat_id').textbox('getText'));
   	var heatcnt = document.getElementById('t22_heat_cnt').value;
   	var sub_unit = document.getElementById('t22_sub_unit_id').value;
   	// var analysis_id = $('#t22_analysis_type').combobox('getValue');
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
   			//$('#t22_sample_no').textbox('setText',data.comment); Get From Procedure
   			$('#t22_sample_no').textbox('setText',(heatId+'/VP1')); // HardCode
   			$("#t22_sample_no").combobox('disable');	
   			$('#t22_spectro').show();
   		},
   		error : function() {
   			$.messager.alert('Processing', 'Error while getting Sample No',
   					'error');
   		}
   	});
    }
    function validateT22HeatChemForm(){
    	    return $('#t22_vd_Chemistry_form_id').form('validate');
    }
    function saveT22ChemistryDtls(){
  	  if(validateT22HeatChemForm()){ 	
  		  var prevUnit=$('#t22_prev_unit').textbox('getText');
  		  var sample_date_time = $('#t22_sample_date_time').datetimebox('getValue');
  		  var sample_no=$('#t22_sample_no').combobox('getText');	
  		  var sample_temp=$('#t22_sample_temp').numberbox('getValue'); 
  		  var sub_unit =document.getElementById('t22_sub_unit_id').value;
  		  // var eof_trns_sno =
  			// document.getElementById('t4_trns_si_no').value;
  		  /*
  			 * var analysis_type =$('#t22_analysis_type').combobox('getValue');
  			 * var analysis_type_txt
  			 * =$('#t22_analysis_type').combobox('getText');
  			 */
  		  var final_result =$('#t22_sample_result').combobox('getValue');
  		  var remarks = $('#t22_remarks').textbox('getValue');
  		  var sample_si_no = document.getElementById('t22_sample_si_no').value;
  		  var heat_id=$('#t22_heat_id').combobox('getText');
  		  var heat_counter=	document.getElementById('t22_heat_cnt').value;
  		  if(sample_si_no == null || sample_si_no == ''){
  			  sample_si_no = 0;
  		  }
        	  var eventname = 'LRF_CHEM_DETAILS';
        	  var rows = $('#t22_vd_Chemistry_tbl_id').datagrid('getRows');
        	  for(var i=0; i<rows.length; i++){
    	    	   $('#t22_vd_Chemistry_tbl_id').datagrid('endEdit', i);
        	  }
    	      var child_rows = $('#t22_vd_Chemistry_tbl_id').datagrid('getRows');
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
    function applyT22Filter() {
    	 $('#t22_vd_Chemistry_tbl_id').datagrid('enableFilter');
    	 document.getElementById('t22_sample_si_no').value='0';
    	 getT22ChemistryDtlsGrid();
    } 
    function cancelT22ChemistryDtls(){
    	 getT22ChemistryDtlsGrid(); 
    }
    function clearAllData() {
  	  $('#t22_sample_date_time').datetimebox({
  			value : (formatDate(new Date()))
  	  });
  	  $('#t22_sample_no').combobox('setValue', '');
  	  $('#t22_sample_temp').numberbox('setValue', '');
  	  // $('#t22_analysis_type').combobox('setValue', '');
  	  $('#t22_sample_result').combobox('setValue', '');
  	  $('#t22_remarks').textbox('setText', '');
  	  document.getElementById('t22_sample_si_no').value = '0';

  	  var dummydata = new Array();
  	  $('#t22_sample_no').combobox('loadData', dummydata);
  	  refreshT22Chem();
  	  // $('#t22_lrf_Chemistry_tbl_id').datagrid('loadData', dummydata);
  	  // $('#t22_lrf_chem_samp_tbl_id').datagrid('loadData', dummydata);
    }
    function saveFinalResult(){
  	  var row = $('#t22_vd_chem_samp_tbl_id').datagrid('getSelected');
  	  var samp_hdr_id = row.sample_si_no;
  	  if(row.sample_si_no==null || row.sample_si_no==''){
  		$.messager.alert('Sample Info', 'Select Sample Number...', 'info');
  		return false;
  	  }
  	  var sub_unit_id = document.getElementById('t22_sub_unit_id').value;
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
  				  getT22ChemSampleDtls();
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
    $('#t22_vd_chem_samp_tbl_id').datagrid({
  	  rowStyler:function(index,row){
  		  if (row.final_result == 1){
  	        	var analysis_id = document.getElementById('t22_chem_level').value;
  	        	var psn_id = document.getElementById('t22_aim_psn_id').value;
  	        	var chem_hdr_id = row.sample_si_no;
  	        	document.getElementById('t22_sample_si_no').value=chem_hdr_id;
  	    		// document.getElementById('t22_chem_validation').value=row.chem_validation;
  	        	$('#t22_final_result_btn_id').linkbutton('disable');
  	        	$('#t22_save_chem_btn_id').linkbutton('disable');
  	        	$('#t22_close_chem_btn_id').linkbutton('disable');
  	        	$('#t22_getSample_btn').linkbutton('disable');
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
  	        					$('#t22_vd_Chemistry_tbl_id').datagrid('loadData', data);
  	        					$('#t22_sample_no').combobox('setValue', row.sample_no);
  	        					$('#t22_sample_date_time').datetimebox({	value : formatDate(row.sample_date_time)});
  	        					$('#t22_sample_temp').numberbox('setValue', row.sample_temp);
  	        					$('#t22_sample_result').combobox('setValue', row.sample_result);
  	        					if(row.remarks != 'null'){
  	        						$('#t22_remarks').textbox('setText', row.remarks);}
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
    
    

    /** Button Events Screen Start **/
    
    function t22EventsDtlsOpen(){
     	 var t22_trns_si_no=document.getElementById('t22_trns_si_no').value;
        
     	 if(t22_trns_si_no!='')
  		{
  		$('#t22_vd_events_form_div_id').dialog({modal:true,cache: true});
  	    $('#t22_vd_events_form_div_id').dialog('open').dialog('center').dialog('setTitle','Events Details Form');
  	    $('#t22_vd_events_form_id').form('clear');
  	 
  	    var heatId =$('#t22_heat_id').textbox('getText');
        //var heatcnt = document.getElementById('t22_heat_cnt').value;
        var sub_unit_id=document.getElementById('t22_sub_unit_id').value;
        var aimpsn =$('#t22_aim_psn').textbox('getText');
    	    
  	    $('#t22_event_heat_no').textbox('setText',heatId);
  	    $('#t22_event_aim_psn').textbox('setText',aimpsn);
  	   
  	    var url1="./CommonPool/getComboList?col1=event_si_no&col2=event_desc&classname=EventMasterModel&status=1&wherecol=sub_unit_id="+sub_unit_id+" and record_status=";
        	getDropdownList(url1,'#t22_event_id');
        	 
         $('#t22_event_date_time').datetimebox({
     		
     	    value: (formatDate(new Date())) 
     	   
     	});
        t22EventsDtls();
  	    getT22EventDtlsGrid();
  	
  		}else{
  			$.messager.alert('Information','Please Select Heat...!','info');
  		}
  	}//end
    
    function t22EventsDtls(){ 
    
    $('#t22_vd_events_tbl_id').edatagrid({
   	 
  	  onBeforeEdit:function(index,row){ 
  		if(row.event_date_time==null){
			row.event_date_time = formatDate(new Date());
  		}
  		else{
  		          row.event_date_time = formatDate(row.event_date_time);
  	  }
  		         $('#t22_vd_events_tbl_id').datagrid('selectRow', index);
        },onEndEdit:function(index,row){
        	$('#t22_vd_events_tbl_id').datagrid('selectRow', index);
  	    	  var dt=(row.event_date_time).split(" ");
  	    	
  	    	  var cons_time=new Date(commonGridDtfmt(dt[0],dt[1]));
  	    	
  	    	  var time=cons_time.getTime();
  	    	 
  	    	  row.event_date_time =time;
  	      
    }
    });
    
    
    }
    
       
       
    
       function getT22EventDtlsGrid()
       {
      		var heat_id=$('#t22_heat_id').combobox('getText');
      	  	var heat_counter=	document.getElementById('t22_heat_cnt').value;
      	  	var sub_unit_id=document.getElementById('t22_sub_unit_id').value;

       	$.ajax({
        		headers: { 
        		'Accept': 'application/json',
        		'Content-Type': 'application/json' 
        		},
        		type: 'GET',
        		//data: JSON.stringify(formData),
        		dataType: "json",
        		url: "./heatProcessEvent/getHeatProcessEventDtlsByUnit?heat_id="+heat_id+"&heat_counter="+heat_counter+"&sub_unit_id="+sub_unit_id,
        		success: function(data) {
        			 $('#t22_vd_events_tbl_id').datagrid('loadData', data);
        			 
        			 $("#T22SaveBtn").linkbutton('disable');
        				for (var i = 0; i < data.length; i++) {
        					if(data[i].event_date_time==null){
        						$("#T22SaveBtn").linkbutton('enable');
        					}

        				}
        			
        		  },
        		error:function(){      
        			$.messager.alert('Processing','Error while Processing Ajax...','error');
        		  }
        		});
       }
       
       function cancelT22EventDtls(){
       	getT22EventDtlsGrid();
       
       }
       function validateT22VdDispatchForm(){
  	   	 return $('#t22_vd_events_form_id').form('validate');
  	   }
       function saveT22EventDtls(){
       //	alert("in save");
          	 var heat_proc_event_id=0;
           	/*var heat_id = $('#t22_event_heat_no').combobox('getText');
           	var heat_counter =document.getElementById('t22_heat_cnt').value;
           	var event_id= $('#t22_event_id').combobox('getValue');
           	var event_date_time=$('#t22_event_date_time').datetimebox('getText');*/        	

       	   //    formData={"heat_proc_event_id": heat_proc_event_id,"heat_id": heat_id,"heat_counter": heat_counter,"event_id": event_id,"event_date": event_date_time};
       	   	var sel_rows = $('#t22_vd_events_tbl_id').datagrid('getRows');
       	    var trns_si_no=document.getElementById('t22_trns_si_no').value;
       	   	
       	   	var grid_arry = '';
       	   	   var date_prev_val='';
       	   	   var isDateValid ='NO';
       	   	   var isDateNull ='YES';
       	   	   var eventdate = '';

       	   	for (var i = 0; i < sel_rows.length; i++) {
       	   		$('#t22_vd_events_tbl_id').datagrid('endEdit', i);
       	   		if (i == 0){
       	   			if (sel_rows[i].event_date_time == '' || sel_rows[i].event_date_time== null || sel_rows[i].event_date_time== ' '){
       	   				isDateValid ='NO';
       	   				break;
       	   			}else{			
       	   				date_prev_val = formatDate(sel_rows[i].event_date_time);
       	   				   //alert('row 000--'+sel_rows[i].event_date_time);
       	   				   eventdate = formatDate(sel_rows[i].event_date_time);
       	   				   grid_arry += sel_rows[i].event_id + '@' + eventdate + '@'
       	   					+ sel_rows[i].heat_proc_event_id + 'SIDS';
       	   				   isDateValid ='YES';
       	   			  }
       	   		} 
       	   		else{
       	   			//alert('row i--'+i+sel_rows[i].event_date_time);
       	   			if (sel_rows[i].event_date_time=='' || sel_rows[i].event_date_time==null || sel_rows[i].event_date_time==' '){
       	   				//alert('before j loop--'+sel_rows[i].event_date_time);
       	   				//alert('i and toal'+ i+sel_rows.length-1);
       	   				if(i == sel_rows.length-1 ){
       	   					isDateValid ='YES';
       	   					isDateNull ='NO';
       	   					//alert('i equals last row');
       	   				}else{
       	   				for (var j = i+1; j < sel_rows.length; j++) {
       	   					if (sel_rows[j].event_date_time !='' && sel_rows[j].event_date_time != null && sel_rows[j].event_date_time !=null){
       	   						isDateNull ='YES';
       	   						//alert('row j loop--'+j+sel_rows[j].event_date_time);
       	   						break;
       	   					}
       	   					else {
       	   						isDateValid ='YES';
       	   						isDateNull ='NO';
       	   					}
       	   				}
       	   				}
       	   				if (isDateNull =='YES'){
       	   					//alert('date is null');
       	   					isDateNull ='NO';
       	   					isDateValid ='NO';
       	   					break;
       	   				}
       	   				else{
       	   					//alert('data is not null')
       	   					eventdate = formatDate(sel_rows[i].event_date_time);
       	   					/*grid_arry += sel_rows[i].event_id + '@' + eventdate + '@'
       	   					+ sel_rows[i].heat_proc_event_id + 'SIDS';*/
       	   					isDateValid ='YES';
       	   				}
       	   			}
       	   			else{
       	   				 eventdate = formatDate(sel_rows[i].event_date_time);
       	   				//alert('event date 000--'+eventdate+'<'+date_prev_val);
       	   				if (eventdate < date_prev_val){
       	   					isDateValid ='NO';
       	   					//alert('event date less--'+eventdate+'<'+date_prev_val);
       	   					break;
       	   				}
       	   				else{
       	   					eventdate = formatDate(sel_rows[i].event_date_time);
       	   					date_prev_val =formatDate(sel_rows[i].event_date_time);
       	   					grid_arry += sel_rows[i].event_id + '@' + eventdate + '@'
       	   					+ sel_rows[i].heat_proc_event_id + 'SIDS';
       	   					//alert('event date more than--'+eventdate+'<'+date_prev_val);
       	   					isDateValid ='YES';
       	   				}
       	   			}
       	   		}
       	   	}
       	   			formData = {
       	   		"grid_arry" : grid_arry
       	   	};
       	   	// alert(formData);
       	   	if(isDateValid == 'YES'){
    $.ajax({
       		headers: { 
       		'Accept': 'application/json',
       		'Content-Type': 'application/json' 
       		},
       		type: 'POST',
       		data: JSON.stringify(formData),
       		//data: rows,
       		dataType: "json",
       		url : './heatProcessEvent/EventSaveOrUpdate?trns_sno='
   				+ trns_si_no+'&unit=VD',
       		success: function(data) {
       		    if(data.status == 'SUCCESS') 
       		    	{
       		    	$.messager.alert('Event Details Info',data.comment,'info');
       		    	cancelT22EventDtls();
       		    	getT22EventDtlsGrid();
       		    	
       		    	}else {
       		    		$.messager.alert('Event Details Info',data.comment,'info');
       		    	}
       		  },
       		error:function(){      
       			$.messager.alert('Processing','Error while Processing Ajax...','error');
       		  }
       		});

          	}
         
       	
           else{
           	$.messager.alert('Event Details Info','Date entered is not valid','error');
           }
     	   
        }   
          /** Button Events Screen End **/

       
       /** Delay Entry Start**/
     
       function addT22DelayEntry(){
    	   var heatId = $('#t22_heat_id').textbox('getText');
    		 if (heatId!=null && heatId!=''){
    			 openT22DelayEntryScreen();
    		 }
    		 else{
    			 $.messager.alert('Warning', 'Select Heat Number',
    				'info');
    		 }
       }
       
       function openT22DelayEntryScreen(){
    	   getT22DelayDetails();
    	   $("#t22_delay_entry_form_div_id").attr("data-rowchange","0");
    	   $('#t22_delay_entry_form_div_id').on('keyup change paste', 'input, select, textarea', function(){
    			 console.log("init.....changes detected...");
    			 $("#t22_delay_entry_form_div_id").attr("data-rowchange","1");
    			 });
    	   $('#t22_delay_entry_form_div_id').dialog({
    			modal : true,
    			cache : true
    		});
    		$('#t22_delay_entry_form_div_id').dialog('open').dialog('center').dialog(
    				'setTitle', 'Delay Details Form');
    		$('#t22_delay_entry_form_id').form('clear');
    		
    		 var heatId = $('#t22_heat_id').textbox('getText');

    		 var aimpsn = $('#t22_aim_psn').textbox('getText');
    		// console.log("Heat : "+heatId+"AIM :"+aimpsn);
    		 $("#t22_delay_heat_no").textbox("setText",heatId);
    		 $("#t22_delay_aim_psn").textbox("setText",aimpsn);  
    		 $('#t22_delay_entry_tbl_id').edatagrid(
    					{
    						
    					onBeginEdit :function(index,rowE){}});
       }
       
       function getT22DelayDetails(){
   		var t4_trns_si_no2 =document.getElementById('t22_trns_si_no').value;
   		var heat_counter=	document.getElementById('t22_heat_cnt').value;
   		//console.log("Trans ID : "+t4_trns_si_no2);
   		 var sub_unit_id= document.getElementById('t22_sub_unit_id').value; //$('#t4_unit').combobox('getValue')
   		$.ajax({
   			headers : {
   				'Accept' : 'application/json',
   				'Content-Type' : 'application/json'
   			},
   			type : 'GET',
   			// data: JSON.stringify(formData),
   			dataType : "json",
   			url : "./VDproduction/activityDelayMstrBySubunit?sub_unit_id="+sub_unit_id +"&trns_si_no="+t4_trns_si_no2+"&heat_counter="+parseInt(heat_counter),//
   					
   			success : function(data) {
   				console.log("VD- Delay")
   				console.log(data);
   				$('#t22_delay_entry_tbl_id').datagrid('loadData', data);

   			},
   			error : function() {
   				$.messager.alert('Processing', 'Error while Processing Ajax...',
   						'error');
   			}
   		});
   	}
       
       function saveT22DelaytDtls(){
    	   //alert("Saving data");
    	   var rowsCh = $('#t22_delay_entry_tbl_id').edatagrid('getChanges');
    	   var rows = $('#t22_delay_entry_tbl_id').datagrid('getRows');
    	   
    		 for ( var i = 0; i < rows.length; i++) {
    		    $('#t22_delay_entry_tbl_id').datagrid('endEdit', i);
    		 } 
    		 //console.log("TEST : "+$("#t24_delay_entry_form_div_id").attr("data-rowchange"));
    		if( $("#t22_delay_entry_form_div_id").attr("data-rowchange")=="1" ){
    		 var sel_rows = $('#t22_delay_entry_tbl_id').edatagrid('getRows');
    		 var heatId = $('#t22_heat_id').textbox('getText');
//    		for (var k=0;k<=sel_rows.length;k++){
    		  //$('#dgP').datagrid('beginEdit', indx);
    			

    				$.ajax({
    					headers : {
    						'Accept' : 'application/json',
    						'Content-Type' : 'application/json'
    					},
    					type : 'POST',
    					data : JSON.stringify(sel_rows),
    					// data: rows,
    					dataType : "json",
    					url : './VDproduction/TransDelaySave?heat_id='+heatId,
    					success : function(data) {
    						if (data.status == 'SUCESS') {
    							$.messager.alert('Info', data.comment, 'info');
    							getT22DelayDetails();
    							 $("#t22_delay_entry_form_div_id").attr("data-rowchange","0");
    							//cancelT8EventDtls();
    						} else {
    							$.messager.alert('Info', data.comment, 'info');
    						}
    					},
    					error : function() {
    						$.messager.alert('Processing', 'Error while Processing Ajax...',
    								'error');
    					}
    				});
    		
    			//sel_rows[i]
//    			if(sel_rows[i].corrective_action!=''){
    				
    				
//    			}else{
//    				alert("Empty field are available....");
//    			}
//    		}
    		}else{
    			$.messager.alert('Processing', 'No changes Present!',
    			'info');
    		}
       }
       
       function t221ReferenshData(){
    	   refreshT22DelayDetailsView(parseInt($("#trans_delay_dtl_id_value").val()));
   	}
       
       function t221addDelayDetails(){
    		  // alert("Open");
    			 $('#t221_delay_entry_form_id').form('clear');
    				var row = $('#t22_delay_entry_tbl_id').datagrid('getSelected');
    				//console.log("Selected Row");
    				//console.log(row);
    				if(row!=null){
    				 var heatId = $('#t22_heat_id').textbox('getText');

    				 var aimpsn = $('#t22_aim_psn').textbox('getText');
    				 if(row.transDelayEntryhdr!=null ){
    				 $("#t221_delay_dtls_heat_no").textbox("setText",heatId);
    				 $("#t221_delay_dtls_aim_psn").textbox("setText",aimpsn);
    				 $("#t221_delay_dtls_activity").textbox("setText",row.activity_master.activities);
    				
    				$('#t221_delay_entry_form_div_id').dialog({
    					modal : true,
    					cache : true
    				});
    				$('#t221_delay_entry_form_div_id').dialog('open').dialog('center').dialog(
    						'setTitle', 'Delay Details Form');
    				//$("#data-delay").val("0");
    				$("#t221_delay_entry_tbl_id").attr("data-delay","0")
    				//loading delay entries against delay hdr
    				
    				refreshT22DelayDetailsView(row.transDelayEntryhdr.trns_delay_entry_hdr_id);
    				DelayT22Init(row);
    				$("#t221activity_value").val(row.activity_master.activities);
    				$("#t221delay_details").val(row.activity_master.delay_details);
    				$("#trans_delay_dtl_id_value").val(row.transDelayEntryhdr.trns_delay_entry_hdr_id);
    				$("#t221delay").val(row.delay);
    				$('#t221_delay_entry_tbl_id').edatagrid(
    						{
    							
    						onBeginEdit :function(index,rowE){
    							var editors = $('#t221_delay_entry_tbl_id')
    							.datagrid('getEditors', index);
    							//console.log("Begin Edit");
    							//console.log(rowE);
    							//console.log(editors)
    							var actVal = $(editors[2].target);//delay entry field
    							actVal
    							.textbox({
    								onChange : function(newV,oldV) {
    									var aVal = actVal
    									.textbox('getText');
    									console.log("NEW : "+newV+"OLD :"+oldV);
    									if(oldV==''){
    										oldV=0;
    									}
    									if(parseInt(newV)<parseInt(oldV)){
    									
    										var neg=parseInt(newV)-parseInt(oldV)
    										$("#t221_delay_entry_tbl_id").attr("data-delay",neg)
    									}else{
    										var neg=parseInt(newV)-parseInt(oldV)
    										$("#t221_delay_entry_tbl_id").attr("data-delay",neg)
    									}
    									
    									//console.log("value : "+$("#t241_delay_entry_tbl_id").attr("data-delay"));
//    									console.log("Value Entered..."+aVal +"OLD : "+oldV +"NEW :"+newV);
//    									
//    									  var data = $('#t91_delay_entry_tbl_id').edatagrid('getData');
//    									  var rows = data.rows;
//    									  var sum = 0;
//    									 
//    									  for (i=0; i < rows.length; i++) {
//    										  console.log(sum +": "+rows[i].delay_dtl_duration);
//    										  if(typeof rows[i].delay_dtl_duration !="undefined"){
//    									        sum+=rows[i].delay_dtl_duration;
//    									  }
//    										 sum=sum+parseInt(aVal);
//    										  console.log("SUM  : "+sum +" delay : "+row.delay)
//    										  if(sum>row.delay){
//    										 if(typeof editors[2]['oldHtml']!="undefined"){
//    											 actVal.textbox(
//    														'setValue',editors[2]['oldHtml']);
//    										 }else{
//    											 actVal.textbox(
//    														'setValue',0);
//    										 }
//    										 refreshT24DelayDetailsView(row.transDelayEntryhdr.trns_delay_entry_hdr_id)
//    										  $.messager
//    											.alert(
//    													'Information',
//    															"Sum : "+ aVal +" Activity Delay : "+row.delay
//    															+ " Sum of delay minutes should not be grater than activity delay minutes ",
//    													'info');
//    										  return;
//    									  }
//    									  }
    								}})
    							
    							},
    						onBeforeEdit : function(index, rowE) {
    								
    							},
    						onBeforeSave : function(index, rowE) {
    							
//    							 
    							  var data = $('#t221_delay_entry_tbl_id').edatagrid('getData');
    							  var rows = data.rows;
    							  var sum = 0;
    							 
    							  for (i=0; i < rows.length; i++) {
    								 // console.log(rows[i].delay_dtl_duration);
    								  if(typeof rows[i].delay_dtl_duration !="undefined"){
    							        sum+=rows[i].delay_dtl_duration;
    							  }
    							  }
    							  //console.log("value edited : "+$("#t241_delay_entry_tbl_id").attr("data-delay"));
    							 
    							  sum = sum+parseInt($("#t221_delay_entry_tbl_id").attr("data-delay"));
//    							  if(isNaN(sum)){
//    								  sum=0
//    							  }
    							  //console.log("SUM : "+sum +"Delay : "+row.delay);
    							  if(sum>row.delay){
    								  $.messager
    									.alert(
    											'Information',
    													"Sum : "+ sum +" Activity Delay : "+row.delay
    													+ " Sum of delay minutes should not be grater than activity delay minutes ",
    											'info');
    								  refreshT22DelayDetailsView(row.transDelayEntryhdr.trns_delay_entry_hdr_id)
    								  $("#t221_delay_entry_tbl_id").attr("data-delay","0")
    								  return false;
    							  }
    							},
    						onSuccess : function(index, rowE) {
    							
    							refreshT22DelayDetailsView(row.transDelayEntryhdr.trns_delay_entry_hdr_id)
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
       
       function refreshT22DelayDetailsView(trans_delay_entry_hdr_id){
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
   			
   			
   				$('#t221_delay_entry_tbl_id').datagrid('loadData', data);

   			},
   			error : function() {
   				$.messager.alert('Processing', 'Error while Processing Ajax...',
   						'error');
   			}
   		});
   	}
       
       function formatT22ActivityColumnData(colName, value, row, index) {
   		try {
   			if(row.isNewRecord){
   	    		return $("#t221activity_value").val();
   	    	}else{
   	    	if(eval("row."+colName) === null)
   	    		{
   	    		return $("#t221activity_value").val();
   	    		}else{
   	    			return eval("row."+colName);
   	    		}
   	    	}
   	        }catch(e)
   	        {
   	        	return "";
   	    	}
   	}
   	function formatT22DlyDtlsColumnData(colName, value, row, index) {
   		try {
   			
   	    	if(row.isNewRecord){
   	    		return $("#t221delay_details").val();
   	    	}else{
   			if(eval("row."+colName) === null)
   	    		{
   	    		
   	    		return $("#t221delay_details").val();
   	    		}else{
   	    			return eval("row."+colName);
   	    		}
   	    	}
   	    	
   	        }catch(e)
   	        {
   	        	return "";
   	    	}
   	}
   	
   	function DelayT22Init(row){
		$('#t221_delay_entry_tbl_id')
		.edatagrid(
				{
					updateUrl : './LRFproduction/TransDelayDtlsUpdate?trans_delay_entry_hdr_id='+row.transDelayEntryhdr.trns_delay_entry_hdr_id,
					saveUrl : './LRFproduction/TransDelayDtlsSave?trans_delay_entry_hdr_id='+row.transDelayEntryhdr.trns_delay_entry_hdr_id
							

				});
	}
    
    /** Process Parameter Screen Start **/
    function vdProcessParamOpen(){ 
        $('#t22_process_param_tbl_id').edatagrid({
               
               //saveUrl: './scrapEntry/DtlsSaveOrUpdate',
            });
        
        $('#t22_process_param_tbl_id').datagrid({
          	 
       	 onBeforeEdit:function(index,row){ 
     		           row.process_date_time = formatDate(row.process_date_time);
     		         $('#t22_process_param_tbl_id').datagrid('selectRow', index);
           },onEndEdit:function(index,row){
           	$('#t22_process_param_tbl_id').datagrid('selectRow', index);
     	    	 var dt=(row.process_date_time).split(" ");
     	    	 var proc_date=new Date(commonGridDtfmt(dt[0],dt[1]));
     	    	 var time=proc_date.getTime();
     	    	 row.process_date_time =time;
     	      }, onBeginEdit:function(index,row){
   	          	var editors = $('#t22_process_param_tbl_id').datagrid('getEditors', index);

   	          	var minval=row.param_value_min;
   	          	var maxval=row.param_value_max;
   	          
   	          	if((minval == null || minval =='')&& (maxval == null ||maxval ==''))
   	          	{
   	          		return false;
   	          	}else{
   	          		var actVal=$(editors[0].target);
       	          	actVal.textbox({
       	          		onChange:function(){
       	          			var aVal = actVal.textbox('getText');
       	          			//alert("123 actVal="+aVal+" Min="+minval+" Max="+maxval);
       	          			if((aVal != null && aVal !='') && (minval != null && minval !='')&& (maxval != null && maxval !='')){
       	          				minmax_flag=validateMinMax(aVal,minval,maxval);
       	          				//alert('minmax_flag--'+minmax_flag);
       	          				if(! minmax_flag){
           	          				$.messager.alert('Information','Actual value '+aVal+' should be in between Min '+minval+' & Max '+maxval+' Values...!','info');
           	          				actVal.textbox('setValue','');
           	          			}
       	          			}
       	          		}
       	          	});
   	          	}//else
   	          	
   	           }
       });
       
        
        }
    
    function t22ProcessParam(){
       	
     	 var t22_trns_si_no=document.getElementById('t22_trns_si_no').value;

         if(t22_trns_si_no!='')
  		{
  		$('#t22_process_param_form_div_id').dialog({modal:true,cache: true});
  	    $('#t22_process_param_form_div_id').dialog('open').dialog('center').dialog('setTitle','Process Parameters Details Form');
  	    
  	 
  	   var heatId =$('#t22_heat_id').textbox('getText');
       //var heatcnt = document.getElementById('t22_heat_cnt').value;
       var aimpsn =$('#t22_aim_psn').textbox('getText');
   	
    	vdProcessParamOpen();
    	
  	    $('#t22_proc_heat_no').textbox('setText',heatId);
  	    $('#t22_proc_aim_psn').textbox('setText',aimpsn);
  	    getT22ProcParamDtlsGrid();
  		}else{
  			$.messager.alert('Information','Please Select Heat...!','info');
  		}
  	}//end
    
    function getT22ProcParamDtlsGrid()
    {
    	
    	var heatid=$('#t22_heat_id').combobox('getText');
    	var heat_cntr=document.getElementById('t22_heat_cnt').value;
    	var subunitid=document.getElementById('t22_sub_unit_id').value;
    	 var psn_no = document.getElementById('t22_aim_psn').value;
    	
    	$.ajax({
     		headers: { 
     		'Accept': 'application/json',
     		'Content-Type': 'application/json' 
     		},
     		type: 'GET',
     		//data: JSON.stringify(formData),heatid,heat_cntr,subunitid
     		dataType: "json",
     		url: './heatProcessEvent/getHeatHeatProcParamDtls?heatid='+heatid
     		+'&heat_cntr='+heat_cntr
     		+'&subunitid='+subunitid
     		+ '&psn_no=' + psn_no,
     		success: function(data) {
     			 $('#t22_process_param_tbl_id').datagrid('loadData', data);
     			
     		  },
     		error:function(){      
     			$.messager.alert('Processing','Error while Processing Ajax...','error');
     		  }
     		});
    }
    
    function cancelT22ProcParamDtls(){
 	   getT22ProcParamDtlsGrid();
    
    }
  function saveT22ProcessParamDtls(){
	  var erows=$('#t22_process_param_tbl_id').datagrid('getSelected');
	  if(erows){
		  var heatid=$('#t22_heat_id').combobox('getText');
		  var heat_cntr=document.getElementById('t22_heat_cnt').value;
		  var rows = $('#t22_process_param_tbl_id').datagrid('getRows');
	    	
		  for(var i=0; i<rows.length; i++){
		    	   $('#t22_process_param_tbl_id').datagrid('endEdit', i);
		    }
		   var proc_param_rows = $('#t22_process_param_tbl_id').datagrid('getRows');
		   var grid_arry = '';
		   for(var i=0; i<proc_param_rows.length; i++){
		       	if((proc_param_rows[i].param_value_actual !=null && proc_param_rows[i].param_value_actual !='')){
			    		   var proc_date= formatDate(proc_param_rows[i].process_date_time);
			    		   grid_arry +=  proc_param_rows[i].param_id+'@'+proc_param_rows[i].param_value_actual+'@'+proc_date+'@'+proc_param_rows[i].proc_param_event_id+'SIDS';
			    	   }
		       }
		       formData={"grid_arry": grid_arry,"heat_id":heatid,"heat_counter": heat_cntr};
			//alert(formData);
		 $.ajax({
			headers: { 
			'Accept': 'application/json',
			'Content-Type': 'application/json' 
			},
			type: 'POST',
			data: JSON.stringify(formData),
			//data: rows,
			dataType: "json",
			url: './heatProcessEvent/procParamSaveOrUpdate',
			success: function(data) {
			    if(data.status == 'SUCCESS') 
			    	{
			    	$.messager.alert('Process Parameters Details Info',data.comment,'info');
			    	cancelT22ProcParamDtls();
			    	}else {
			    		$.messager.alert('Process Parameters Details Info',data.comment,'info');
			    	}
			  },
			error:function(){      
				$.messager.alert('Processing','Error while Processing Ajax...','error');
			  }
			}) ;

	  }else{
  		$.messager.alert('Process Parameters Details','Data not changed to save ...!','info');
  	}
	  
	  
    	
    }
          
    /** Process Param Screen End **/
  
  	/**VD Additions start  */
  function loadDataDummy(){
	 	// document.getElementById('t15_lrf_arc_disp_div_id').style.display =
		// 'none';
	  document.getElementById('t30_vd_disp_div_id').style.visibility="visible";
	  var unit_id=$('#t22_unit').combobox('getValue');
	  var heatId=$('#t22_heat_id').combobox('getText');
	  var heatCnt=document.getElementById('t22_heat_cnt').value;
	$.ajax({
	   		headers: { 
	   		'Accept': 'application/json',
	   		'Content-Type': 'application/json' 
	   		},
	   		type: 'GET',
	   		// data: JSON.stringify(formData),
	   		dataType: "json",
	   		url: "./VDproduction/getVDAdditionsTemp?unit_id="+unit_id+"&heatId="+heatId+"&heatCnt="+heatCnt,
	   		success: function(data) {
	   		  $('#t30_vd_arc_disp').datagrid('loadData', data);
	   		
	   		  },
	   		error:function(){      
	   			$.messager.alert('Processing','Error while Processing Ajax...','error');
	   		  }
	   		});  
}
	function viewT30VDAdditions () {
		document.getElementById('t30_vd_disp_div_id').style.visibility="hidden";
		var hdrCnt=$('#t30_vd_add_tbl_id').datagrid('getData').total;
		var heatId=$('#t22_heat_id').combobox('getText');
		var heatCnt=document.getElementById('t22_heat_cnt').value;
		var unit_id=$('#t22_unit').combobox('getValue');
	  
		$.ajax({
		headers: { 
	   	'Accept': 'application/json',
	   	'Content-Type': 'application/json' 
	   	},
	   	type: 'GET',
	   	//data: JSON.stringify(formData),
	   	dataType: "json",
	   	url: "./VDproduction/getVDAdditionsByHeat?heat_id="+heatId+"&heat_cnt="+heatCnt+"&unit_id="+unit_id,
	   	success: function(data) {
			var $n1 = {};
	   		var columns = new Array();
	   		 /*var colarray=new Array(
	   					{"test_id":'A1'},
	   					{"test_id":'N1'},
	   					{"test_id":'O1'}
	   					
	   			
	   			);*/
	   		var v=$('#t30_vd_add_tbl_id').datagrid('getRows')[0].material_name;
	   		var arr = data.headerdis;
	   		columns.push({ "field": data.ar_si_no, title:data.ar_si_no, "width": 100, "sortable": true });
	   		//columns.push({ "field": data.bath_sample_no, title:data.bath_sample_no, "width": 100, "sortable": true });
	   		//columns.push({ "field": data.ar_si_no, title:data.bath_sample_no, "width": 100, "sortable": true });
	   		/*columns.push({ "field": data.bath_temp, title:data.bath_temp, "width": 100, "sortable": true });
	   		columns.push({ "field": data.arc_start_date_time, title:data.arc_start_date_time,
	   		"formatter":function(v,r,i){return formatDateTime(data.arc_start_date_time,v,r,i)}, "width": 100, "sortable": true });
	   		columns.push({ "field": data.arc_end_date_time, title:data.arc_end_date_time,
	   		"formatter":function(v,r,i){return formatDateTime(data.arc_end_date_time,v,r,i)}, "width": 100, "sortable": true });
	   		columns.push({ "field": data.power_consumption, title:data.power_consumption, "width": 100, "sortable": true });*/
	   		for(var i=0;i<hdrCnt;i++){
				var str =  data.headerdis[i];
    			var resField = str.split("(");
    			columns.push({ "field": resField[0], "title": data.headerdis[i], "width": 120, "sortable": true });
	   		}
			/* $.each(arr, function (i, item) {
	   		 columns.push({ "field": item.test_id, "title": item.test_id, "width": 100, "sortable": true });
	   		 });*/
	   	 	$n1.columns = new Array(columns);
	   	 	$('#t30_vd_arc_disp').datagrid($n1);
		   	 loadDataDummy();
		},
	   	error:function(){      
			$.messager.alert('Processing','Error while Processing Ajax...','error');
		}
		});
 	}
	function T30VDAdditions(){
		  $('#t30_vd_add_tbl_id').edatagrid({
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
				  $('#t30_vd_add_tbl_id').datagrid('selectRow', index);
			  },onEndEdit:function(index,row){
				  $('#t30_vd_add_tbl_id').datagrid('selectRow', index);
		    	  var dt=(row.addition_date_time).split(" ");
		    	  var cons_time=new Date(commonGridDtfmt(dt[0],dt[1]));
		    	  var time=cons_time.getTime();
		    	  row.addition_date_time =time;
			  }
			});
		    //clearT30AdditionsDet();
		    //resetArcDates();
			var heat_no=$('#t22_heat_id').combobox('getText');
			var heat_cnt=document.getElementById('t22_heat_cnt').value;
			var unit=$('#t22_unit').combobox('getText');
			var unit_id=$('#t22_unit').combobox('getValue');
			var psn_no=$('#t22_aim_psn').textbox('getText');
			if((heat_no!=null && heat_no!='') && (heat_cnt!=null && heat_cnt!='')){
	 		$('#t30_arc_det_form_div_id').dialog({modal:true,cache: true});
	 	    $('#t30_arc_det_form_div_id').dialog('open').dialog('center').dialog('setTitle','VD Additions Entry Form');
	 	    $('#t30_heat_no').textbox('setText',heat_no);
	 	    $('#t30_unit').textbox('setText',unit);
	 	    $('#t30_psn_no').textbox('setText',psn_no);
	 	    getT30AddDetGrid();
	 	    //callT15DropDowns();
	 	    $('#t30_arc_det_form_div_id').attr("data-change","0")
	 	    document.getElementById('t30_vd_disp_div_id').style.visibility="hidden";
	 	    closeActionBindEvt('t30_arc_det_form_div_id' );
		}else{
			$.messager.alert('Additions Details Info','Please Select Heat no','info');
		}
	  }
	  function getT30AddDetGrid(){
		  	var heatId=document.getElementById('t22_trns_si_no').value;
			var heatCnt=document.getElementById('t22_heat_cnt').value;
			var unit_id=$('#t22_unit').combobox('getValue');
		  	$.ajax({
		   		headers: { 
		   		'Accept': 'application/json',
		   		'Content-Type': 'application/json' 
		   		},
		   		type: 'GET',
		   		// data: JSON.stringify(formData),
		   		dataType: "json",
		   		url: "./VDproduction/getVdAdditions?lookup_code=VD_ADDITIONS&sub_unit_id="+unit_id,
		   		success: function(data) {
		   			 $('#t30_vd_add_tbl_id').datagrid('loadData', data);
		   		  },
		   		error:function(){      
		   			$.messager.alert('Processing','Error while Processing Ajax...','error');
		   		  }
		   		});
		  }
	
	  function clearT30AdditionsDet(){
		  /*getT30AddDetGrid();
		  var data1=new Array();
		  document.getElementById('t30_arc_sl_no').value='';
		  // $('#t15_temp').numberbox('setValue','');
		  $('#t15_consumption').numberbox('setValue','');
		  $('#t15_arc_start_date').datetimebox('setValue','');
		  $('#t15_arc_end_date').datetimebox('setValue','');
		  $('#t15_lrf_arc_disp').datagrid('loadData', data1);*/
		  document.getElementById('t30_vd_disp_div_id').style.visibility="hidden";
	  }
	  
	  function saveT30VDAdditions(){
			var heatId=$('#t22_heat_id').combobox('getText');
			var heatCnt=document.getElementById('t22_heat_cnt').value;
			var arc_sl_no=document.getElementById('t30_arc_sl_no').value;
			var heatId=$('#t22_heat_id').combobox('getText');
		 	/*var arc_start_date = $('#t15_arc_start_date').datetimebox('getValue');
		 	var arc_end_date = $('#t15_arc_end_date').datetimebox('getValue');*/
		 	var arc_start_date =new Date();
		 	var arc_end_date = new Date();
			// var temp=$('#t15_temp').numberbox('getValue');
			//var consumption=$('#t15_consumption').numberbox('getValue');
			var consumption=0;
			// var addition_type=$('#t15_addition_type').combobox('getValue');
			//var prevUnit=$('#t13_prev_unit').textbox('getText');	

			if(arc_sl_no == null || arc_sl_no == ''){
				arc_sl_no = 0;
			}

		  	var tab1_rows = $('#t30_vd_add_tbl_id').datagrid('getRows');
		  	for(var i=0; i<tab1_rows.length; i++){
		  	   $('#t30_vd_add_tbl_id').datagrid('endEdit', i);
		  	}

		  	// var tab1_rows = $('#t30_vd_add_tbl_id').datagrid('getSelections');

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
		   				"heat_id": heatId,"heat_counter":heatCnt ,"arc_sl_no":arc_sl_no,"power_consumption":consumption
		   				//,"prev_unit":prevUnit
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
				url: './VDproduction/SaveOrUpdate?arc_start_date='+arc_start_date+'&arc_end_date='+arc_end_date,

				success: function(data) {
				    if(data.status == 'SUCCESS')	{
				    	$.messager.alert('VD Additions Details Info',data.comment,'info');
				    	cancelT15ArcDet();
				    	// getT15ArcDetGridBySampleNo(arc_sl_no);
				    	// getT15SampleNos();
				    	}else {

				    		$.messager.alert('VD Additions Details Info(E)',data.comment,'info');

				    	}

				  },

				error:function(){      

					$.messager.alert('Processing','Error while Processing Ajax...','error');

				  }

				});

		  	}else{

		  		$.messager.alert('VD Additions Details Info','No Additions Done','info');

		  	}

		  	

		  }
	  
	  
	/**VD Additions end  */
  function checkEventsDtls(callback){
		//var eof_trns_sno = document.getElementById('t22_trns_si_no').value;
		var heat_id=$('#t22_heat_id').combobox('getText');
  	  	var heat_counter=	document.getElementById('t22_heat_cnt').value;
  	  	var sub_unit_id=document.getElementById('t22_sub_unit_id').value;
	    var isSuccess=true;
	    
		$.ajax({
			headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json'
			},
			type : 'GET',
			// data: JSON.stringify(formData),
			dataType : "json",
			url: "./heatProcessEvent/getHeatProcessEventDtlsByUnit?heat_id="+heat_id+"&heat_counter="+heat_counter+"&sub_unit_id="+sub_unit_id,
			success : function(data) {
//				$('#t8_eof_events_tbl_id').datagrid('loadData', data);
//				$("#T8SaveBtn").linkbutton('disable');
				for (var i = 0; i < data.length; i++) {
					if(data[i].event_date_time==null){
						//$("#T8SaveBtn").linkbutton('enable');
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
		var t4_trns_si_no2 = document.getElementById('t22_trns_si_no').value;
		//console.log("Trans ID : "+t4_trns_si_no2);
		  var sub_unit_id=$('#t22_unit').combobox('getValue');
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
				console.log("Delay details")
				console.log(data);
				if(data.length>0){
					 isSuccess=true;
				}
				//$('#t9_delay_entry_tbl_id').datagrid('loadData', data);
				callback(isSuccess);

			},
			error : function() {
				$.messager.alert('Processing', 'Error while Processing Ajax...',
						'error');
			}
		});
	}
  
  
   /** VD Dispatch Begin**/
	function t22VdDispatch(){
   	 var t22_trns_si_no=document.getElementById('t22_trns_si_no').value;
   	 if(t22_trns_si_no!=''){
   		checkEventsDtls(function(isEventPresent){
   			if(isEventPresent){
   				checkDelayDtlsPresent(function(isEventPresent){
					//console.log("DELAY DETAILS : "+isEventPresent);
					if(true){//isEventPresent
						$('#t22_vd_dispatch_div_id').dialog({modal:true,cache: true});
		   			    $('#t22_vd_dispatch_div_id').dialog('open').dialog('center').dialog('setTitle','VD Dispatch Screen');
		   			    $('#t22_vd_dispatch_form_id').form('clear');
		   			    var dipwd=$('#t22_steel_wgt').textbox('getValue');
		   			   // alert(dipwd);
		   			    $('#t22_vdDispatchWgt').numberbox('setValue',dipwd);
		   				var vdUnit = $('#t22_unit').combobox('getText');
		   				var url1='';
		   				//alert('vdUNit value-----'+vdUnit);
//		   				if(vdUnit == 'VD1'){
//		   					 url1="./CommonPool/getComboList?col1=lookup_id&col2=lookup_value&classname=LookupMasterModel&status=1&" +
//		   					    "wherecol=lookup_type='VD_DISPATCH_TO' and lookup_value in ('LRF1','LRF3')and lookup_status=";
//		   				}else{
		   					 url1="./CommonPool/getComboList?col1=lookup_id&col2=lookup_value&classname=LookupMasterModel&status=1&" +
		   					    "wherecol=lookup_type='VD_DISPATCH_TO' and lookup_status=";
//		   				}
		   		      		getDropdownList(url1,'#t22_dispatchTo');
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
		
		
	}else{
		$.messager.alert('Alert','Select Heat to dispatch...!','info');
	}
	}
   
   
   /** VD Dispatch End**/
	function resetForm(){
		 $('#t22_remarks').textbox('setValue','');
  		 $('#t22_vdDispatchWgt').numberbox('setValue','');
  		 $('#t22_vdDispatchTemp').numberbox('setValue','');
  		 $('#t22_dispatchTo').combobox('setValue','Select');
		
	}
	
	 function validateT22VdDispatchForm(){
	    	
	   	 return $('#t22_vd_dispatch_form_id').form('validate');
	   }
	 function cancelT22VdDispatch()
	    {
	    	$('#t22_vd_dispatch_div_id').dialog('close');
	  
	    }
	function saveT22VdDispatch(){
		if(validateT22VdDispatchForm()){
			var vdDispatchRemarks = $('#t22_remarks').textbox('getValue');
    		var vdDispatchWgt = $('#t22_vdDispatchWgt').numberbox('getValue');
    		var vdDispatchTemp = $('#t22_vdDispatchTemp').numberbox('getValue');
    		var vdDispatchStartTemp = $('#t22_vdDispatchStartTemp').numberbox('getValue');
    		var vdDispatchTo = $('#t22_dispatchTo').combobox('getValue');
    		var vdDispUnit = $('#t22_dispatchTo').combobox('getText');
    		var vdDispatchTo_unit_name = $('#t22_dispatchTo').combobox('getText');
    		//alert('@748');
    		var t22_trans_si_no= document.getElementById('t22_trns_si_no').value;
    		var t22_heatTrackId= document.getElementById('t22_heat_track_id').value;
    		var t22_main_status="WIP";
    		//In service impl class this t22_act_proc_path will be concatenated to existing act process path
			var t22_act_proc_path = $('#t22_unit').combobox('getText');
			var t22_unit_status;
			if(vdDispUnit.match(/LRF.*/)){
				t22_unit_status="WAITING FOR DISPATCH";
			}else if(vdDispUnit.match(/CCM.*/)){
				t22_unit_status="WAITING FOR PROCESSING";
			}
			var t22_vd_status="DISPATCHED";	
			var t22_eof_status="DISPATCHED";
			var t22_lrf_status="DISPATCHED";
			var t22_blt_caster_status='';
			var t22_blm_caster_status='';
			var t22_ar_n2_consumption='0.0';
		//alert('t22_trans_si_no-------'+t22_trans_si_no);
		//alert('vd_process_remarks-----'+vdDispatchRemarks);
		//alert('dispatch_temp------'+parseInt(vdDispatchTemp));
		//alert('dispatch_to_unit------'+parseInt(vdDispatchTo));
		//alert('vd_dispatch_wgt-------'+parseInt(vdDispatchWgt));
    		var formData = {
  				  "vdHeatDetails":{
  					 "trns_si_no":t22_trans_si_no,"vd_process_remarks":vdDispatchRemarks,"before_vd_temp":parseInt(vdDispatchStartTemp),"dispatch_temp":parseInt(vdDispatchTemp),
  					"dispatch_to_unit":parseInt(vdDispatchTo),"vd_dispatch_wgt":parseInt(vdDispatchWgt)  
  				  },
  				
  				  "vdHeatStatus":{
  					  "heat_track_id":t22_heatTrackId,"main_status":t22_main_status,"act_proc_path":t22_act_proc_path,
  					  "current_unit":vdDispatchTo_unit_name,"unit_process_status":t22_unit_status,"vd_status":t22_vd_status,
  					  "eof_status":t22_eof_status,"lrf_status":t22_lrf_status,"vd_status":t22_vd_status,"blt_cas_status":t22_blt_caster_status,
  					  "blm_cas_status":t22_blm_caster_status
  				  },
  				//"trns_si_no":t22_trans_si_no,
  				//"AR_N2_CONSUMPTION":t22_ar_n2_consumption			
  				};
    		var tapWeightMaxUrl = "./CommonPool/getComboList?col1=lookup_id&col2=lookup_value&classname=LookupMasterModel&status=1"
    			+ "&wherecol=lookup_type='VD_DISPATCH_MIN_WEIGHT' and lookup_status=";
    		/*var tapWeightProcessMsg = "Getting the VD Dispatch Tempurature range.";
    		var tapWeightErrorMsg = "Error while Processing VD Tap weight.";*/
    		getResult(tapWeightMaxUrl, function(isRecordPresent, value){
    			if (parseFloat(value[0].txtvalue) <= vdDispatchWgt) {
           	$.ajax({
           		headers: { 
           		'Accept': 'application/json',
           		'Content-Type': 'application/json' 
           		},
           		type: 'POST',
           		data: JSON.stringify(formData),
           		dataType: "json",
           		url : "./VDproduction/vdDispatchSave?trns_si_no="+t22_trans_si_no +"&AR_N2_CONSUMPTION="+t22_ar_n2_consumption, 
           		success: function(data) {
           		    if(data.status == 'SUCCESS') 
           		    	{
           		    	$.messager.alert('VD Dispatch Details Info',data.comment,'info');
           		    	clearT22HeatHdrform();
           		    	cancelT22VdDispatch();
           		    	getT22UpdatedHeatList();
           		    	}else {
           		    		$.messager.alert('VD Dispatch Details Info',data.comment,'info');
           		    	}
           		  },
           		error:function(){      
           			$.messager.alert('Processing','Error while Processing Ajax...','error');
           		  }
           		});
    			}else{
    				$.messager.alert('Warning', 'Its does not satisfy the dispatch weight');
    				return false;
    				}
    		});
           
    		
		}
    		
     			
    	
		
		
    
	}
	function viewT30LRFDtls(){		
		$('#t30_reuse_arc_det_div').dialog({modal:true,cache: false});
		$('#t30_reuse_arc_det_div').dialog('open').dialog('center')
		.dialog('setTitle', 'LRF Arching Details');
		var prev_unit=$('#t22_prev_unit').textbox('getText');
		var heatId=$('#t22_heat_id').combobox('getText');
	  	var heatCnt=document.getElementById('t22_heat_cnt').value;
	  	var sub_unit_id=null;
	  	//fetching previous unit ID...
	  	$.ajax({
       		headers: { 
       		'Accept': 'application/json',
       		'Content-Type': 'application/json' 
       		},
       		type: 'GET',
       		beforeSend:function(){
				$.messager.progress({ text:'Fetching Data'});
				},
				complete: function(){
				$.messager.progress('close');
				},
       		//data: JSON.stringify(formData),
       		dataType: "json",
       		url : "./MstrSubUnit/getSubUnitIdByName?sub_unitName="+prev_unit, 
       		success: function(data11) {
       			sub_unit_id = data11.sub_unit_id; 
       		  	$.ajax({
       		   		headers: { 
       		   		'Accept': 'application/json',
       		   		'Content-Type': 'application/json' 
       		   		},
       		   		type: 'GET',
       		   		// data: JSON.stringify(formData),
       		   		dataType: "json",
       		   		url: "./LRFproduction/getLRFArcAdditions?lookup_code=LRF_ARC_ADDITIONS&sub_unit_id="+data11.sub_unit_id,
       		   		success: function(data1) {
       		   			 $('#t15_reuse_lrf_arc_add_tbl_id').datagrid('loadData', data1);
       			//1st prgrm loading header row..
       		   		$.ajax({
       		 		headers: { 
       		 	   	'Accept': 'application/json',
       		 	   	'Content-Type': 'application/json' 
       		 	   	},
       		 	   	type: 'GET',
       		 	   	//data: JSON.stringify(formData),
       		 	   	dataType: "json",
       		 	   	url: "./LRFproduction/getLRFArcAdditionsByHeat?heat_id="+heatId+"&heat_cnt="+heatCnt+"&unit_id="+data11.sub_unit_id,
       		 	   	success: function(data) {
       		 	   	var hdrCnt=$('#t15_reuse_lrf_arc_add_tbl_id').datagrid('getData').total;
       		 			var $n1 = {};
       		 	   		var columns = new Array();
       		 	   		 /*var colarray=new Array(
       		 	   					{"test_id":'A1'},
       		 	   					{"test_id":'N1'},
       		 	   					{"test_id":'O1'}
       		 	   					
       		 	   			
       		 	   			);*/
       		 	   		var v=$('#t15_reuse_lrf_arc_add_tbl_id').datagrid('getRows')[0].material_name;
       		 	   		var arr = data.headerdis;
       		 	   		//columns.push({ "field": data.ar_si_no, title:data.ar_si_no, "width": 100, "sortable": true });
       		 	   		columns.push({ "field": data.bath_sample_no, title:data.bath_sample_no, "width": 100, "sortable": true });
       		 	   		columns.push({ "field": data.bath_temp, title:data.bath_temp, "width": 100, "sortable": true });
       		 	   		columns.push({ "field": data.arc_start_date_time, title:data.arc_start_date_time,
       		 	   		"formatter":function(v,r,i){return formatDateTime(data.arc_start_date_time,v,r,i)}, "width": 100, "sortable": true });
       		 	   		columns.push({ "field": data.arc_end_date_time, title:data.arc_end_date_time,
       		 	   		"formatter":function(v,r,i){return formatDateTime(data.arc_end_date_time,v,r,i)}, "width": 100, "sortable": true });
       		 	   		columns.push({ "field": data.power_consumption, title:data.power_consumption, "width": 100, "sortable": true });
       		 	   		for(var i=0;i<hdrCnt;i++){
       		 				var str =  data.headerdis[i];
       		     			var resField = str.split("(");
       		     			columns.push({ "field": resField[0], "title": data.headerdis[i], "width": 120, "sortable": true });
       		 	   		}
       		 			/* $.each(arr, function (i, item) {
       		 	   		 columns.push({ "field": item.test_id, "title": item.test_id, "width": 100, "sortable": true });
       		 	   		 });*/
       		 	   	 	$n1.columns = new Array(columns);
       		 	   	 	$('#t15_reuse_lrf_arc_disp').datagrid($n1);
       		 		   	 loadFinalData(data11.sub_unit_id,heatId,heatCnt);
       		 		},
       		 	   	error:function(){      
       		 			$.messager.alert('Processing','Error while getting LRF data...','error');
       		 		}
       		 		});
       		   		  },
       		   		error:function(){      
       		   			$.messager.alert('Processing','Error while fetching previous unit ID...','error');
       		   		  }
       		   		});
       	 },
	   		error:function(){      
	   			$.messager.alert('Processing','Error while Processing Ajax...','error');
	   		  }
	   		});
       			
       		 
		
	 
	}

	
	function loadFinalData(subUnit,heatId,heatCnt){
		$.ajax({
  		   		headers: { 
  		   		'Accept': 'application/json',
  		   		'Content-Type': 'application/json' 
  		   		},
  		   		type: 'GET',
  		   		// data: JSON.stringify(formData),
  		   		dataType: "json",
  		   		url: "./LRFproduction/getLRFArcAdditionsTemp?unit_id="+subUnit+"&heatId="+heatId+"&heatCnt="+heatCnt,
  		   		success: function(data3) {
  		   		  $('#t15_reuse_lrf_arc_disp').datagrid('loadData', data3);
  			
   			},
       		error:function(){      
       			$.messager.alert('Processing','Error while loading data...','error');
       		  }
       		}); 
	}