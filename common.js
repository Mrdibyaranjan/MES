/**
 * Developed by Somashekhar
 * formatColumnData--This method used to extract sub model data or sub json array
 * formatColumnStatus--This method used to convert raw data ie y-Active and N-InActive
 */
function formatColumnData(colName, value, row, index) {
	try {
    	if(eval("row."+colName) === null)
    		{
    		return "";
    		}else{
    			return eval("row."+colName);
    		}
        }catch(e)
        {
        	return "";
    	}
}



function doDisableEnableButtons(divId,mode){
	var div="#"+divId;
	var i=1;
	$(div).find('a').each(function(e) {
		//console.log($(this).attr('id'));
		if(i>1){
			 $(this).linkbutton(mode);
		}
		i++;
	})
}
	

function formatColumnStatus(colName, value, row, index) {
	try {
	if(eval("row."+colName) =='Y' || eval("row."+colName) =='1')
		{
		return "Active";
		}else{
			return "InActive";
		}
    }catch(e)
    {
    	return "";
	}
}



function defaultPagination(tableid){
    var dg = $('#'+tableid);
    dg.datagrid('loadData',[]);
    dg.datagrid({pagePosition:'bottom'});
    dg.datagrid('getPager').pagination({
        layout:['list','sep','first','prev','sep','manual','sep','next','last','sep','refresh']
    });
}

/*Get the date object by passing string data mm/dd/yyyy*/

function getDateObject(s){
	var t = Date.parse(s);
	if (!isNaN(t)){
		return new Date(t);
	} else {
		return new Date();
	}
}

/* Common Dropdown values retrival process method by passing url and elementid*/
function getDropdownList(url,elementid)
{
	 $.ajax({
   	 headers: { 
     		'Accept': 'application/json',
     		'Content-Type': 'application/json' 
     		},
     		type: 'GET',
        url: url,
        dataType: 'json',
        success: function(data){
       	 $(elementid).combobox('loadData', data);
        },
        error: function(){
        	 $.messager.alert('Processing','Error while Processing getDropdownList('+url+','+elementid+')...','error');
        }
    });
}


/* Common Date format mm/dd/yyyy hh:mm:ss*/


function addZero(n){
    return n < 10 ? '0' + n : '' + n;
 }

function formatDate(d){
	if(d!=null && d!==''){
	var date=new Date(d);
	 	    return addZero(date.getDate())+"/"+ addZero(date.getMonth()+1) + "/" + date.getFullYear() + " " + 
	           addZero(date.getHours()) + ":" + addZero(date.getMinutes());
	 	  // + ":" + addZero(date.getSeconds()
	}else{
		return '';
	}
}



function formatDateTime(colName, value, row, index) {
	
	if(eval("row."+colName) == null)
	{
	return "";
	}else{
		var col=eval("row."+colName);
		return formatDate(col);
	}
}  


/*Standard date format dd/mm/yyyy hh:mm:ss*/
  
$.extend($.fn.datebox.defaults,{
	formatter:function(date){
		var y = date.getFullYear();
		var m = date.getMonth()+1;
		var d = date.getDate();
		return (d<10?('0'+d):d)+'/'+(m<10?('0'+m):m)+'/'+y;
	},
	parser:function(s){
		if (!s) return new Date();
		var ss = s.split('/');
		var d = parseInt(ss[0],10);
		var m = parseInt(ss[1],10);
		var y = parseInt(ss[2],10);
		if (!isNaN(y) && !isNaN(m) && !isNaN(d)){
			return new Date(y,m-1,d);
		} else {
			return new Date();
		}
	}
});
/* Shifts depending on time */

function autoShift(datetime,comboid,resultset){

	var splitdate=datetime.split(" ");
 	
 	var sdate=splitdate[0].split("/").reverse().join("/");
 	var newdate=sdate.concat(" "+splitdate[1]);	
	var date=new Date(newdate);
	var hours=date.getHours();
	if(hours>=6&&hours<14){
		 setDefaultCustomComboValues(comboid,'A',resultset);
	}else if(hours>=14&&hours<22){
		 setDefaultCustomComboValues(comboid,'B',resultset);
	}else{
		 setDefaultCustomComboValues(comboid,'C',resultset);;
	}
	
}

function assignChangesDetectLister(lookupDivId){
$('#'+lookupDivId).attr("data-change","0")
$('#'+lookupDivId).on('keyup change paste', 'input, select, textarea', function(){
console.log("init.....");
			$('#'+lookupDivId).attr("data-change","1")
});
}

//row changes detector and updates parent div data-change
function assignRowChangeDetectionListener(lookupDivId,lookupTableDivId){
	
	$('#'+lookupTableDivId).datagrid({
		onAfterEdit:function(index,row){
			row.editing = false;
			console.log("Row Updating...")
			//$(this).datagrid('updateRow',{index:index, row:{}});
			$('#'+lookupDivId).attr("data-change","1")
		}
		
		
	})
	
}



function assignChangesDetectListenerWithTabs(lookupDivId){
	var requestedFormId="#"+lookupDivId;
//	$(".tabs-inner").click(function(){
//		if($(requestedFormId).attr("data-change")=="1"){
//			doSwitchingofTabs(lookupDivId);
//		}
//	});
//	$(requestedFormId).find('a').each(function(e) {
//	    //e.preventDefault();
//	    $(this).click(function(){
//    		$(this).css("color","green")
//    		$(requestedFormId).attr("data-change","1")
//    		
//    	});
//	});
	$(requestedFormId+' input,'+requestedFormId+' select').each(
		    function(index){
//		    	$(this).click(function(){
//		    		$(this).css("color","red")
//		    		$(requestedFormId).attr("data-change","1")
//		    		
//		    	});
//		    	$(this).keyup(function(){
//	    			$(requestedFormId).attr("data-change","1")
//	    		});
//	    		$(this).change(function(){
//	    			$(requestedFormId).attr("data-change","1")
//	    		});
	    		
		    	
		    });
}

/*Dialog box close */

function dialogBoxClose(gridid)
{
	$('#'+gridid).dialog('close');
}
/*Dialog box close action with confirmation*/

function closeActionBindEvt(gridid){
	 $(".panel-tool-close").click(function(){
		
		if($('#'+gridid).attr("data-change")==1){
			doCloseDialogAction(gridid);
		}else{
			return;
		}
		 
	 }); 

}
function dialogBoxCloseAfterConfirmation(gridid){
	assignChangesDetectLister(gridid)

}

function closeBtnOfAllDialogue(gridid){
	if($('#'+gridid).attr("data-change")==1){
		doCloseDialogAction(gridid);
	}else{
		$('#'+gridid).dialog('close');
	}
}
function doCloseDialogAction(gridid){
	
	if (confirm("Unsaved data is present are you sure want to close?")) {
		$('#'+gridid).attr("data-change","0")   
		$('#'+gridid).dialog('close');
		}
		else{
			 $('#'+gridid).dialog ("open"); //dialog stay alive
			 return;
		}
}
function doSwitchingofTabs(gridid){
	
	if (confirm("Unsaved data is present are you sure want to switch next tab?")) {
		$('#'+gridid).attr("data-change","0")   
//		$('#'+gridid).dialog('close');
		}
	else{
			 $('#'+gridid).dialog ("open"); //dialog stay alive
			 return;
		}
}
/*Default combo value set */

function setDefaultComboValues(comboid,val,id)
{
	$('#'+comboid).combobox('setText',val);
	$('#'+comboid).combobox('setValue',id);
}

/*Default custom combo value set */

function setDefaultCustomComboValues(comboid,val,resultset)
{
	for(c=0;c<resultset.length;c++)
	{
		if(resultset[c].txtvalue==val)
		{
			 setDefaultComboValues(comboid,val,resultset[c].keyval);
			 break;
		}
	}
}


//2011-01-26T13:51:50.417 common date format for grid
	
	function commonGridDtfmt(dt,tms)
	{
		
		var dts=dt.split("/");
		
		    return (dts[2])+'-'+(dts[1])+'-'+(dts[0]) + ' ' + tms;
		    
		
	}

	// converting date to ISO format yyyy/mm/dd hh:mm:ss
	function commonDateISOformat(date){
		var splitdate=date.split(" ");
     	
     	var sdate=splitdate[0].split("/").reverse().join("/");
     	var datetime=sdate.concat(" "+splitdate[1]);	
    	return new Date(datetime);
	}
	/*function  to validate value for min and max values.*/
    function validateMinMax(value, min_v, max_v) {
		var in_value;
		var in_min;
		var in_max;
		if ((max_v != null) && (min_v != null)) {
						try {
				in_value = parseFloat(value);
				in_min = parseFloat(min_v);
				in_max = parseFloat(max_v);
				//alert('in_value---'+in_value);
				//alert('in_min---'+in_min);
				//alert('in_max---'+in_max);
				if ((in_value <= in_max) && (in_value >= in_min)) {
					return true;
				}
				return false;
			} catch (e) {
				return true;
			}
		} else {
			return true;
		}

	}
 function   roundOffVal(colName, value, row, index){
	 if(value != null){
		 return parseFloat(value).toFixed(2);
	 }
	 else{
		 return 0.00;
		 }
 
 }
    
 /*Restric number of days between from and todate */
 
 function restrictDateRange(fdate1,tdate,noOfdays){
     $('#'+tdate).datebox().datebox('calendar').calendar({
         validator: function(date){
      	  
      	   var fdate=$('#'+fdate1).datebox('getText');
      	   
      	   var ss = fdate.split('/');
      	   var d = parseInt(ss[0],10);
     		   var m = parseInt(ss[1],10);
     		   var y = parseInt(ss[2],10);
         
             var d1 = new Date(y, m-1, d);
             var d2 = new Date(y, m-1, d+parseInt(noOfdays));
             return d1<=date && date<=d2;
         }
     });
 }
 
 
/*restrictDateAndHourRange number of days between from and todate */
 
 function restrictDateAndHourRange(fdate1,tdate1,noOfHr,msg){
        	
        	var fms=0,tms=0;
        	
        	 var fdate=$('#'+fdate1).datetimebox('getValue');
        	
        	 var splitfdate=fdate.split(" ");
        	 	
        	 var shr=splitfdate[1].split(":");
        	 
        	 var ss = splitfdate[0].split('/');
        	   
    	   	   var d = parseInt(ss[0],10);
	   		   var m = parseInt(ss[1],10);
	   		   var y = parseInt(ss[2],10);
	   		   
	   		 var fd = new Date(y, m-1, d, parseInt(shr[0]), parseInt(shr[1]), parseInt(shr[2]),0);
             
             fms = fd.getTime();
	   		   
        	 
             try{
     
          var tdate=$('#'+tdate1).datetimebox('getValue');
        	
       	  var splittdate=tdate.split(" ");
       	 	
       	  var ehr=splittdate[1].split(":");
       	 	
     	     var ts = splittdate[0].split('/');
         	   
     	   	   var d1 = parseInt(ts[0],10);
    		   var m1 = parseInt(ts[1],10);
    		   var y1 = parseInt(ts[2],10);
         
             
             var td = new Date(y1, m1-1, d1, parseInt(ehr[0]), parseInt(ehr[1]), parseInt(ehr[2]),0);
             
              tms = td.getTime();
              
             }catch(e)
             {
             }
          
             var ftime=parseInt(tms)-parseInt(fms);
             
             var minutes = ftime / (1000*60);
             
             if((parseInt(minutes)>=0) && ((parseInt(minutes))<=(parseInt(noOfHr*60))))
            	 {
            	 return true;
            	 }else{
                $.messager.alert('info',msg,'info');
            	$('#'+tdate1).datetimebox('setValue','');
            		 return false;
            	 }
           
 }
 
 /*Default Load Message... */

 function loadIngStart(tblid,loading_msg)
 {
 	$('#'+tblid).datagrid('options').loadMsg = loading_msg;
 	$('#'+tblid).datagrid('loading');
 	
 }
 
 /*Default End Message... */

 function loadIngEnd(tblid)
 {
 	$('#'+tblid).datagrid('loaded');
 	
 }
 /* Get SubUnits based on Unit */
 function getSubUnits(unit_cdId,subunit_cbId){
		 $(subunit_cbId).combobox('setValue', '');
		var unitId = $('#'+unit_cdId).combobox('getValue');
		//$('#'+comboid).combobox('setValue',id);
		var url1='./CommonPool/getComboList?col1=sub_unit_id&col2=sub_unit_name&classname=SubUnitMasterModel&status=1&wherecol=unit_id='+unitId+' and record_status=';
		getDropdownList(url1,'#'+subunit_cbId);
	}
 /**/
 function getUomValue(editors,matId,uom_url)
	{
	 	$.ajax({
	 		headers: { 
	 		'Accept': 'application/json',
	 		'Content-Type': 'application/json' 
	 		},
	 		type: 'GET',
	 		dataType: "json",
	 		url:uom_url,
	 		success: function(data) {
	 			var uom_ed= $(editors[1].target);
	 			/*uom_ed.combobox('setValue',data[0].txtvalue);
	 			uom_ed.combobox('setText',data[0].keyval);*/
	 			 //var additions_url="./CommonPool/getComboList?col1=material_id&col2=material_desc&classname=MtrlProcessConsumableMstrModel&status=1&wherecol=mtrlTypeLkpModel.lookup_id=material_type and mtrlTypeLkpModel.lookup_code=\'"+additions_type+"\' AND record_status=";
				 //$(ed.target).combobox('reload',uom_url);
				//uom_ed.combobox('reload',uom_url);
		 		uom_ed.text('setValue',data[0].keyval);
	 			/*uom_ed.combobox({
	          		editable:false,
	          		value:data[0].txtvalue
	          	});*/
	}
	 	});
	}
function restrictDays(div){
	   $('#'+div).datebox().datebox('calendar').calendar({
           validator: function(date){
               var now = new Date();
               var d1 = new Date(now.getFullYear(), now.getMonth(), now.getDate());
               var d2 = new Date(now.getFullYear(), now.getMonth(), now.getDate()+10);
               return d1<=date && date<=d2;
           }
       });
	   
	   }

function getResult(url,callback) {
	$.ajax({
		headers : {
			'Accept' : 'application/json',
			'Content-Type' : 'application/json'
		},
		type : 'GET',
		/*beforeSend:function(){
			$.messager.progress({title: 'Please wait', text:processMsg});
		},*/
		/*complete: function(){
			$.messager.progress('close');
		},*/
		dataType : "json",
		url :  url,
		success : function(data) {	
			if(data.length > 0){
				callback(true, data);
			} else {
				callback(false, 0);
			}
		},
		error : function() {
			callback(false);
			$.messager.alert('Processing','error while processing ajax', 'error');			
		}
	});		   
}


	