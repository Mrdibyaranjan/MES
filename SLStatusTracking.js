function addNewCircRow() {			
			//alert("vjhhsddhvb");
		/*	$('#t20_circ_steel_ladle_det_tbl_id').edatagrid('insertRow', {
				index: 0,
				row:{part_id:''}
				});*/
			
			$('#t20_circ_steel_ladle_det_tbl_id').edatagrid('insertRow', {
				index: 0,
				row:{parts_si_no:''}
				});
		}

function deleteT20CircRow() {
    var row = $('#t20_circ_steel_ladle_det_tbl_id').datagrid('getSelected');
    var index = $('#t20_circ_steel_ladle_det_tbl_id').datagrid('getRowIndex', row); // get the row index
    //var partMaintLogId = row.stladle_parts_maint_log_id;
    //var stladleMaintStsId = row.stladle_maint_status_id;
    
   // if(partMaintLogId > 0 && stladleMaintStsId > 0){
           $('#t20_circ_steel_ladle_det_tbl_id').datagrid('deleteRow',index);
           //$('#t2_sav_btn_id').linkbutton('disable');               
     /*      $.ajax({
               headers : {
                   'Accept' : 'application/json',
                   'Content-Type' : 'application/json'
               },
               type : 'GET',
               dataType : "json",
               url : './laddleMaintainance/partLogDelete?partMaintLogId='+partMaintLogId+'&stladleMaintStsId='+stladleMaintStsId,
               success : function(data) {
                   if (data.status == 'SUCCESS') {
                       $.messager.alert('Result Info', data.comment, 'info');
                       refreshT20CircPartGrid();
                   } else {
                       $.messager.alert('Result Info', data.comment, 'info');
                   };
               },
               error : function() {
                   $.messager.alert('Processing', 'Error while Deleting Parts...', 'error');
               }
           });*/
      /* }else{
           refreshT20CircPartGrid();
     } */
}

$('#t20_circ_steel_ladle_det_tbl_id').edatagrid(
		{	
			onBeforeEdit : function(index, row) {
				$('#t20_circ_steel_ladle_det_tbl_id').datagrid(
						'selectRow', index);
			},
			onAfterEdit:function(index,row,changes){
				//alert("AFTEREDIT")
				console.log("AFTEREDIT");
				console.log(index,row,changes);
			},
			onEndEdit : function(index, row) {
				$('#t20_circ_steel_ladle_det_tbl_id').datagrid(
						'selectRow', index);
				var editors = $('#t20_circ_steel_ladle_det_tbl_id').datagrid('getEditors', index);
				 
				console.log(editors);
				var chdt=(row.change_date).split(" ");
		    	var ch_time=new Date(commonGridDtfmt(chdt[0],chdt[1]));
		    	var chtime=ch_time.getTime();
		    	row.change_date =chtime;
		    	
		    	
			},

			onBeforeSave : function(index, row) {

			//alert("SAVE");

			},
			onBeginEdit: function (index, row) {
				var editors = $('#t20_circ_steel_ladle_det_tbl_id').datagrid('getEditors', index);
				 var partcmb = $(editors[1].target);
				 var partSuppcmb = $(editors[2].target);   
				 var partType = $(editors[3].target);
				 var partStatus = $(editors[4].target);
				    partcmb.combobox('options').onSelect = function(rec){                       
	                       row.lkpPartId={
	                    		 "lookup_id" : parseInt(rec.keyval),
	   	                        "lookup_value" : rec.txtvalue
	                    };
	                       row.part_id = parseInt(rec.keyval);
	                   }
				    partSuppcmb.combobox('options').onSelect = function(rec){
				    	console.log("Row"+JSON.stringify(row));
	                       row.lkpSuppId={
	                        "lookup_id" : parseInt(rec.keyval),
	                        "lookup_value" : rec.txtvalue
	                    };
	                       row.part_supp_id = parseInt(rec.keyval);
	                   }
				    partType.combobox('options').onSelect = function(rec){
				    	console.log("Row"+JSON.stringify(rec));
	                       row.lkpTypeId={
	                        "lookup_id" : parseInt(rec.keyval),
	                        "lookup_value" : rec.txtvalue
	                    };
	                       row.part_type_id = parseInt(rec.keyval);
	                   }
				    partStatus.combobox('options').onSelect = function(rec){
				    	console.log("Row"+JSON.stringify(rec));
	                       row.lkpSuppId={
	                        "lookup_id" : parseInt(rec.keyval),
	                        "lookup_value" : rec.txtvalue
	                    };
	                       row.parts_status = parseInt(rec.keyval);
	                   }
				
				
			},
			onSave : function(index, row) {
				//$(this).datagrid('appendRow',row);
				// refreshM11PSNChemistry();
			},
			onSuccess : function(index, row) {
			},
			onError : function(index, row) {
			}
		});
		