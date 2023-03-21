<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<script  src="${pageContext.request.contextPath}/js/common.js"></script>

<div  style="padding-top: 20px;padding-left: 10px;padding-right: 10px;">
     
     <input name="t3_scrapBkt_hId"  type="hidden" id="t3_scrapBkt_hId">
     <input name="record_version" type="hidden"  id="t3_record_version">
    <table style="width: 100%" class="easyui-panel" >
       
        <!-- first row -->
        <tr style="height: 30px;">
        <td> 
        <label style="padding-right: 1px"><b><spring:message code="label.t3.scrapBucket"/></b></label>
       <input name="t3_combo_scrapBktId" type="text" id="t3_combo_scrapBktId" 
       		class="easyui-combobox" style="width:100px;" 
        	data-options="required:true,panelHeight: 'auto',editable:false,
        	url:'./CommonPool/getComboList?col1=scrap_bucket_id&col2=scrap_bucket_no&classname=ScrapBucketStatusModel&status=1&wherecol=lookupMstrMdl.lookup_id=scrap_bucket_status and lookupMstrMdl.lookup_code in (\'EMPTY\') and record_status=',
        	method:'get',valueField:'keyval',textField:'txtvalue'" />
        </td>
        <td> 
        <label style="padding-right: 1px"><b><spring:message code="label.t3.scrapPattern"/></b></label>
        <input class="easyui-combobox" id="t3_combo_scrapPtrnId" name="t3_combo_scrapPatternId" style="width:100px;" 
        	data-options="required:true,panelHeight: 'auto',editable:false,
        	url:'./CommonPool/getComboList?col1=scrap_pattern_id&col2=scrap_pattern_no&classname=ScrapPatternMasterModel&status=1&wherecol=record_status=',
        	method:'get',valueField:'keyval',textField:'txtvalue'" />       
        </td>
        <td> 
        <label style="padding-right: 1px"><b><spring:message code="label.m31.description"/></b></label>
         <input name="t3_scrap_ptrn_desc" id="t3_scrap_ptrn_desc" readonly="readonly" type="text" class="easyui-textbox" style="width:250px;" >
        </td> 
         <td> 
        <label style="padding-right: 1px"><b>Work Center</b></label>         
         	<select id="t3_work_center" class="easyui-combobox" name="dept" style="width:200px;">
         	   <option value=""></option>
    			<option value="EAF1">EAF1</option>
    			<option value="EAF2">EAF2</option>
			</select>
        </td> 
         <td> 
        <label style="padding-right: 1px"><b><spring:message code="label.t3.prodDate"/></b></label>
         <input name="t3_scrap_prod_date" id="t3_scrap_prod_date" type="text" formatter="(function(v,r,i){return formatDateTime('t3_scrap_prod_date',v,r,i)})"  class="easyui-datetimebox"   data-options="required:true,showSeconds:false" >
         
        </td> 
         
        </tr>
        </table>
     </div>


    <div align="center" style="padding-top: 10px;padding-left: 10px;padding-right: 10px;height: 100%">
      <table width="100%">
      <tr> <td> <div id="t3_scrap_entry_form_btn_div_id">
			  <%@ page import="java.util.*" %>
		 	<%--Display Buttons  --%>
		   <table><tr>
		        <td colspan="6" align="center">
		           <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" id="btnT3Save" onclick="saveT3ScrapHdrEntry()" style="width:90px">Save</a>
		           </td><td>
		         <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload" id="btnT3Refresh" onclick="cancelT3ScrapDtlsEntry()" style="width:90px">Refresh</a>
		        </td><td>&nbsp;</td>
		        <td><label><b>BucketNo :</b> </label>
		        <input name="t3_bkt_no"  class="easyui-textbox" type="text" id="t3_bkt_no" style="width:80px;"
		        	data-options="editable:false">
		        </td>
		        <td><label><b>PatternNo :</b> </label>
		        <input name="t3_ptrn_no"  class="easyui-textbox" type="text" id="t3_ptrn_no" style="width:80px;"
		        	data-options="editable:false">
		        </td>
		        
		        </tr></table>		         
		        <label style="padding-right: 10px;padding-left: 10px;"><b>Total Scrap Quantity :</b> </label>
		        <input name="t3_qty"   class="easyui-textbox" type="text" id="t3_qty" data-options="editable:false"
		        style="width:15%;vertical-align: middle;color: blue;text-align: center;"> <b>TONNES</b>
		        <!--  
		         <label style="padding-right: 2px;padding-left: 10px;"><b>weighbridge :</b> </label>
		        <input    class="easyui-combobox"  data-options="valueField:'keyval',                    
                    textField:'formatter'" type="text" id="t3_qty_frm_intf" 
		        style="height:30px;font-size: 40px;width:200px;vertical-align: middle;color: blue;text-align: center;">
		        -->
		    </div>
		    </td> 
		</tr>
        <tr>
        <td valign="top" align="left" width="50%">
        <table width="100%" id="t3_scrap_entry_tbl_id" toolbar="#t3_scrap_entry_form_btn_div_id"  title="<spring:message code="label.t3.scrapEntryHeader"/>" class="easyui-datagrid" style="height:auto;"
           iconCls='icon-ok' maximizable="true"  resizable="true" remoteSort="false" rownumbers="true" singleSelect="false"> 
	        <thead>
	            <tr>
	              <th field="material_id" formatter="(function(v,r,i){return formatColumnData('mtrlName',v,r,i);})" sortable="false" width="200px"><b><spring:message code="label.t3.materialName"/></b></th>
	             <th field="sap_matl_desc" formatter="(function(v,r,i){return formatColumnData('sapMtlid',v,r,i);})" sortable="false" align="left" width="150px"><b>SAP Matl ID</b></th>
	              <!--   <th field="sap_matl_desc" align="right" width="150">SAP Matl ID</th> -->
	              <!--  <th field="matPercent" sortable="true" align="right" width="120px"><b><spring:message code="label.t3.matPercent"/></b></th>-->
	              <th field="material_qty" sortable="false" align="right" width="120px"   data-options="editor:{type:'numberbox',options:{precision:2,min:0}}"><b><spring:message code="label.t3.Qty"/></b></th>
	              <th field="uom" formatter="(function(v,r,i){return formatColumnData('uom',v,r,i);})" sortable="false" width="80"><b><spring:message code="label.t3.uom"/></b></th>
	              <th field=scrap_bkt_detail_id sortable="true" hidden="true" width="1"></th>
	              <th field="record_version" sortable="true" hidden="true" width="1"></th>
	         </tr>
	         </thead>
	    </table>
	    </td>
	    <td valign="top" align="left" width="50%">
        <table width="100%" id="t3_loaded_bkt_tbl_id" title="<spring:message code="label.t3.loadedScrapBuckets"/>" 
        	class="easyui-datagrid" style="height:auto;" url="./scrapEntry/getLoadedScrapBktNos" method="get" iconCls='icon-ok' 
        	resizable="true" remoteSort="false" rownumbers="true" singleSelect="true"
        	fitColumns="true" onLoadSuccess="afterLoad()"> 
	        <thead>
	            <tr>
	            <th field="scrap_bucket_no" sortable="true" width="50" formatter="(function(v,r,i){return formatColumnData('scrapBucketStatusModel.scrap_bucket_no',v,r,i);})">
	            	<b><spring:message code="label.t3.scrapBucket"/></b></th>
	            <th field="scrap_pattern_no" sortable="true" width="50" formatter="(function(v,r,i){return formatColumnData('scrapPatternModel.scrap_pattern_no',v,r,i);})">
	            	<b><spring:message code="label.t3.scrapPattern"/></b></th>
	            <th field="scrap_bkt_qty" sortable="true" width="40" type="text" formatter="formatPrice"><b><spring:message code="label.t3.Qty"/></b></th>
	            <th field="scrap_bkt_id" hidden="true" sortable="true" width="40"></th>
	            <th field="scrap_bkt_header_id" hidden="true" sortable="true" width="40"></th>
	            <th field="scrap_pattern_id" hidden="true" sortable="true" width="40"></th>
	            <th field="description" sortable="true" width="40" formatter="(function(v,r,i){return formatColumnData('scrapPatternModel.description',v,r,i);})">
	            	<b><spring:message code="label.m31.description"/></b></th>
	         </tr>
	         </thead>
	    </table>
	    </td>
	    </tr>
	    </table></div>

<div>

</div>
     <script type="text/javascript">
     
     function getScrapWeifhtsByIntf(work_center) {
    	 $('#t3_qty_frm_intf').combobox('clear');
    		//var url1 = "./CommonPool/getComboList?col1=trans_no&col2=net_weight&classname=ScrapWeightDetails&status=&wherecol=work_center='"+work_center+"' and record_status=0 and trunc(trans_date)=trunc(sysdate) ";
    		//getDropdownList(url1, '#t3_qty_frm_intf');
    		
    		$.ajax({
    			headers: { 
    			'Accept': 'application/json',
    			'Content-Type': 'application/json' 
    			},
    			type: 'GET',
    			//data: JSON.stringify(formData),
    			dataType: "json",
    			url: './scrapEntry/getScrapWeightfrmIntf?work_center='+work_center,
    			success: function(data) {
    				var comboData=[];
    				 for(var i=0;i<data.length;i++){
    					var d=new Date(data[i].created_time);
    					var datestring = d.getDate()  + "-" + (d.getMonth()+1) + "-" + d.getFullYear() + " " +
    					d.getHours() + ":" + d.getMinutes();

    					var details={
        						id:data[i].trans_no,
        						keyval:data[i].trans_no,
        						txtvalue:data[i].net_weight,
        						formatter:data[i].net_weight +" ("+formatDate(d)+")"
        				}
    					comboData.push(details);
    				}
    				$("#t3_qty_frm_intf").combobox("loadData",comboData); 
    				 
    			}
    	})
    	}
     /*
     function formatDate(date) {
    	  var hours = date.getHours();
    	  var minutes = date.getMinutes();
    	  var ampm = hours >= 12 ? 'pm' : 'am';
    	  hours = hours % 12;
    	  hours = hours ? hours : 12; // the hour '0' should be '12'
    	  minutes = minutes < 10 ? '0'+minutes : minutes;
    	  var strTime = hours + ':' + minutes + ' ' + ampm;
    	  var retDate = date.getMonth()+1 + "/" +  date.getDate() + "/" + date.getFullYear() + "  " + strTime
    	  return retDate;
    	}
*/
     /*
     $('#t3_qty_frm_intf').combobox({
    	 onSelect: function(row){
    		 $("#t3_qty").numberbox('setValue',row.txtvalue);	     		
    	 }
    	 
     })*/
     $('#t3_work_center').combobox({
    		onSelect: function(row){
    			getT3ScrapDtlsGrid(0);
    			//getScrapWeifhtsByIntf(row.value);
    		}
    	})
     
     function formatPrice(val,row){
    	 return parseFloat(val).toFixed(2);
     }
     
     $('#t3_scrap_prod_date').datetimebox({
 	    value: (formatDate(new Date())) 
 	});
     
	$('#t3_scrap_entry_tbl_id').edatagrid({
		onBeginEdit: function(index,field,changes){
			var dg = $(this);
			var editors = dg.edatagrid('getEditors', index);
			var qty = $(editors[0].target);
			var qtytotal = 0;
    		var totalQty = $('#t3_qty').textbox('getValue');
    					
    		if(totalQty != null && totalQty != ""){
    			qtytotal = parseFloat(totalQty);
    		}
			qty.numberbox({
				onChange: function(newValue, oldValue) {
					var oldVal = 0;
					var newVal = 0;
					
					if(newValue != null && newValue != ""){
						newVal = parseFloat(newValue);
					}
					if(oldValue != null && oldValue != ""){
						oldVal = parseFloat(oldValue);
					}
					
					var diff = newVal - oldVal;
					qtytotal = qtytotal + diff;
					$('#t3_qty').textbox('setValue', (Math.round(qtytotal * 100) / 100));
				}
			})
			
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
     
    
    /*$("#t3_combo_scrapPtrnId").combobox({
     	onSelect:function(record)
     	{
     		setScrapPatternDesc(record.keyval);
     		$('#t3_loaded_bkt_tbl_id').datagrid('unselectAll');
     		getT3ScrapDtlsGrid(0);
     		$("#t3_qty").numberbox('setValue','');			
    		$('#t3_ptrn_no').textbox('setText', record.txtvalue);
     	}
    })*/
     
    $("#t3_combo_scrapBktId").combobox({
		onSelect:function(record)
     	{
     		$('#t3_loaded_bkt_tbl_id').datagrid('unselectAll');
     		$("#t3_qty").textbox('enable');
     		$("#t3_qty").textbox('setValue','');
     		$('#t3_bkt_no').textbox('setText', record.txtvalue);
     		$("#t3_combo_scrapPtrnId").combobox('setValue','1');
		    $('#t3_scrap_ptrn_desc').textbox('setValue', 'P01');
		    $('#t3_ptrn_no').textbox('setValue', 'P01');
     		/*$("#t3_combo_scrapPtrnId").combobox('enable');
     		$("#t3_combo_scrapPtrnId").combobox('setValue','');*/    		
    		/*var nodata=new Array();
        	$('#t3_scrap_entry_tbl_id').datagrid('loadData', {"rows":nodata,"footer":[{"mtrlName":"<b>Total Quantity</b>","matPercent": 0,"material_qty": 0}]});
        	$('#t3_loaded_bkt_tbl_id').datagrid('resize');*/
     		//getT3ScrapDtlsGrid(0);
     	}
	})
     /*
     $('#t3_qty').numberbox({
    	 min:0,
    	    precision:2,
		  onChange: function(totScrap){
		    if ($("#t3_combo_scrapPtrnId").combobox('getValue') == "") {
		    	$.messager.alert('Alert','Please Select the Pattern');
		    	return false;
		    }
		    if (totScrap > 0) {
		    	getT3ScrapDtlsGrid(totScrap);
		    	$('#btnT3Save').linkbutton('enable');
		    }
		    getT3QuantitySum();
		  }
		});
     */
     $('#t3_loaded_bkt_tbl_id').datagrid({
    	 fit: true,
    	 onSelect: function(record){
    		 var row = $('#t3_loaded_bkt_tbl_id').datagrid('getSelected');
    		 document.getElementById('t3_scrapBkt_hId').value = row.scrap_bkt_header_id;
    		 	$('#t3_combo_scrapBktId').combobox('setValue', '');
    		 	$('#t3_combo_scrapPtrnId').combobox('setValue', '');
    		 	
    		 	getT3ScrapDtlsGrid(row.scrap_bkt_qty.toFixed(2));
    		 	$('#btnT3Save').linkbutton('disable');
    		}
     
    		});
     function getT3QuantitySum(){  
			var t3dg = $('#t3_scrap_entry_tbl_id'); 		
			var qtytotal = 0; var percent = 0;
			var rows = $('#t3_scrap_entry_tbl_id').datagrid('getRows');  
			/*for(var i=0; i<rows.length; i++){  
				if(rows[i].material_qty!=null && rows[i].material_qty!="")
			  	{
					qtytotal = qtytotal  + parseFloat(rows[i].material_qty);
			  	}
				if(rows[i].matPercent!=null && rows[i].matPercent!="")
				{
					percent = percent  + parseFloat(rows[i].matPercent);
				}
			}*/  
			
			/*if(qtytotal>10){
				$.messager.alert('Warning','Total quantity should be less than 10','warning');
			}*/
			//t3dg.datagrid('reloadFooter', [{mtrlName:"Total Quantity:",matPercent:percent,material_qty:qtytotal}]);
			//document.getElementById('t3_qty').value=parseFloat(qtytotal).toFixed(2);
			
		}
     
     function setScrapPatternDesc(sp_id) {
 		$.ajax({
 	      		headers: {
 	      		'Accept': 'application/json',
 	      		'Content-Type': 'application/json' 
 	      		},
 	      		type: 'GET',
 	      		//data: JSON.stringify(formData),
 	      		dataType: "json",
 	      		url: "./scrapPatternMstr/getScrapPatternDesc?sp_id="+sp_id,
 	      		success: function(data) {
 	      			var desc = data.description;
 	      			$('#t3_scrap_ptrn_desc').textbox('setText', desc);
 	      		},
 	      		error:function(XMLHttpRequest, textStatus, errorThrown){      
 	      			$.messager.alert('Processing','Error while Loading Details...','error');
 	      		  }
	      	})
     }
     
     function getT3ScrapDtlsGrid(totQty)
     {
     	var bucket_header_id = 0; 
     	var scrap_bucket_id = 0;
     	var scrap_pattern_id = 0;
     	
     	var row = $('#t3_loaded_bkt_tbl_id').datagrid('getSelected');
     	
     	if (row != null) {
     		var row = $('#t3_loaded_bkt_tbl_id').datagrid('getSelected');
     		scrap_bucket_id = row.scrap_bkt_id;
    		scrap_pattern_id = row.scrap_pattern_id;
    		
    		$('#t3_bkt_no').textbox('setText', row.scrapBucketStatusModel.scrap_bucket_no);
    		$('#t3_ptrn_no').textbox('setText', row.scrapPatternModel.scrap_pattern_no);
    		$('#t3_combo_scrapPtrnId').combobox('disable');
    	    $('#t3_qty').textbox('disable');
        	
     	} else {
         	scrap_bucket_id = $('#t3_combo_scrapBktId').combobox('getValue');
         	scrap_pattern_id = $('#t3_combo_scrapPtrnId').combobox('getValue');
         	//$('#t3_combo_scrapPtrnId').combobox('enable');
    	    //$('#t3_qty').numberbox('enable');
     	}
     	
     	/*var nodata=new Array();
    	$('#t3_scrap_entry_tbl_id').datagrid('loadData', {"rows":nodata,"footer":[{"mtrlName":"<b>Total Quantity</b>","matPercent": 0,"material_qty": 0}]});
    	$('#t3_loaded_bkt_tbl_id').datagrid('resize');*/
     	$.ajax({
      		headers: { 
      		'Accept': 'application/json',
      		'Content-Type': 'application/json' 
      		},
      		type: 'GET',
      		//data: JSON.stringify(formData),
      		dataType: "json",
      		url: "./scrapEntry/getScrapEntryDetails?scrap_bucket_id="+scrap_bucket_id+"&scrap_pattern_id="+scrap_pattern_id+"&bucket_header_id="+bucket_header_id,
      		success: function(data) {
      			if (totQty != null && totQty > 0) {
      				$('#t3_qty').textbox('setText',totQty);
      				/*var matqty = 0; var percent = 0;
      				for(var i=0; i<data.length; i++){
      				if(data[i].matPercent!=null && data[i].matPercent!="")
      				  {
      					data[i].material_qty = ((parseFloat(data[i].matPercent)*parseFloat(totQty))/100).toFixed(3);
      					matqty += parseFloat(data[i].material_qty);      					
      					percent += parseFloat(data[i].matPercent);
      				  } else {
      					matqty += parseFloat(data[i].material_qty);
      					percent += 0;
      				  }
         	        }*/
      				/*$('#t3_scrap_entry_tbl_id').datagrid('loadData', {"total":'',"rows":data,"footer":[{"mtrlName":"<b>Total Quantity</b>","matPercent": percent.toFixed(2),"material_qty": matqty.toFixed(2)}]});
      				$('#t3_loaded_bkt_tbl_id').datagrid('resize');*/
      				//$('#t3_scrap_ptrn_desc').textbox('setText', row.scrap_ptrn_desc);
      				
         			$('#btnT3Refresh').linkbutton('enable');
      			} else if (data.length > 0 ) {
      				// if the scrap pattern dtls available
      				var matqty = 0; var percent = 0;
      				
         	    	$('#t3_qty').textbox('enable');
         	    	$('#btnT3Save').linkbutton('enable');
    				$('#btnT3Refresh').linkbutton('enable');
    				
      				/*for(var i=0; i<data.length; i++){
      					if (data[i].material_qty != null && data[i].material_qty != "") {
              				matqty += data[i].material_qty.toFixed(2);
      					}
      					if (data[i].matPercent != null && data[i].matPercent != "") {
      						percent += parseFloat(data[i].matPercent);
      					}
  					}*/
             	    /*$('#t3_scrap_entry_tbl_id').datagrid('loadData', {"total":'',"rows":data,"footer":[{"mtrlName":"<b>Total Quantity</b>","matPercent":percent.toFixed(2),"material_qty": matqty.toFixed(2)}]});
             	    $('#t3_loaded_bkt_tbl_id').datagrid('resize');*/
      			} else {
      				// if the scrap pattern dtls not available
      				$('#t3_qty').textbox('disable');
      				$('#btnT3Save').linkbutton('disable');
    				$('#btnT3Refresh').linkbutton('enable');
      				$('#t3_combo_scrapPtrnId').combobox('enable');
      				$('#t3_loaded_bkt_tbl_id').datagrid('resize');
      			}
      			$('#t3_scrap_entry_tbl_id').datagrid('loadData', data);
      		  },
      		error:function(XMLHttpRequest, textStatus, errorThrown){      
      			//$.messager.alert('Processing','Error while Loading Details...','error');
      		  }
      		})
     }
     
     function saveT3ScrapHdrEntry(){
    	var scrap_prod_date = $('#t3_scrap_prod_date').datetimebox('getValue');	
 	    var scrap_bkt_id =$('#t3_combo_scrapBktId').combobox('getValue');
     	var scrap_patrn_id =$('#t3_combo_scrapPtrnId').combobox('getValue');
     	var t3_qty =$('#t3_qty').textbox('getText');
     	if(scrap_prod_date == "" || scrap_prod_date == null){
  			 $.messager.alert('Alert','Please Select Production Date');
     		 return false;
       	}
     	if(scrap_bkt_id == "" || scrap_bkt_id == null){
  			 $.messager.alert('Alert','Please Select Scrap Bucket');
     		 return false;
       	}
     	if(scrap_patrn_id == "" || scrap_patrn_id == null){
 			 $.messager.alert('Alert','Please Select Scrap Pattern');
    		 return false;
      	}
     	if(t3_qty == "" || t3_qty == null){
 			 $.messager.alert('Alert','Please Enter Total Scrap Quanity.');
    		 return false;
      	}
     	
    	//var chldrows = $('#t3_scrap_entry_tbl_id').datagrid('getRows'); 
    	
    	var totQty = $('#t3_qty').textbox('getText');		
 		/*for(var i=0; i<chldrows.length; i++){  
 			 $('#t3_scrap_entry_tbl_id').datagrid('endEdit', i);
 			 if(chldrows[i].material_qty!=null && chldrows[i].material_qty!=""){
 				 totQty = totQty  + parseFloat(chldrows[i].material_qty);
 			 }
 			}*/
 		
 			var select_rows = $('#t3_scrap_entry_tbl_id').datagrid('getRows');
 		for(var i=0; i<select_rows.length; i++){
 	    	   $('#t3_scrap_entry_tbl_id').datagrid('endEdit', i);
     	}
     	
 		   var bkt_dtl_id = 0;
 		   var grid_arry = '';
 	       for(var i=0; i<select_rows.length; i++){
 	    	   
 	    	   if(select_rows[i].material_qty !=null && select_rows[i].material_qty !=''){
 	    		   
 	    		   grid_arry += select_rows[i].material_id+'@'+select_rows[i].material_qty+'@'+select_rows[i].scrap_bkt_detail_id+'@'+select_rows[i].record_version+'&&';
 	    		   
 	    	   }else{
 	    		   grid_arry += select_rows[i].material_id+'@0@'+select_rows[i].scrap_bkt_detail_id+'@0@'+select_rows[i].record_version+'&&';
 	    	   }
 	           
 	       }
 	       var bucket_header_id=0; 
 	
   		formData={"grid_arry": grid_arry,"scrap_bkt_header_id":bucket_header_id};
   		
   		$.ajax({
	   		headers: { 
	   		'Accept': 'application/json',
	   		'Content-Type': 'application/json' 
	   		},
	   		type: 'POST',
	   		data: JSON.stringify(formData),
	   		//data: rows,
	   		dataType: "json",
	   		url: './scrapEntry/getScrapQty',
	   		success: function(qty) {
   				if (qty > 0) {
   				var url1 = './scrapEntry/SaveOrUpdate?totQty='+totQty+'&scrap_bkt_id='+scrap_bkt_id+'&scrap_prod_dt='+scrap_prod_date+'&scrap_patrn_id='+parseInt(scrap_patrn_id);
		   		if(parseFloat(totQty)>qty){
		   			 $.messager.confirm('Confirm','Total quantity more than '+qty+' ton.. Do you want Process ?',function(r){
		   				if (r){
		   				 $.ajax({
					   		headers: { 
					   		'Accept': 'application/json',
					   		'Content-Type': 'application/json' 
					   		},
					   		type: 'POST',
					   		data: JSON.stringify(formData),
					   		//data: rows,
					   		dataType: "json",
					   		url: url1,
					   		success: function(data) {
					   			//var trans_no=$("#t3_qty_frm_intf").combobox("getValue");
			 		  			
					   		    if(data.status == 'SUCCESS') 
					   		    	{
					   		    	$.messager.alert('Scrap HDR Entry Details Info',data.comment,'info');
					   		    	//updateSrapWeightIntf(trans_no);
					   		    	getT3ScrapDtlsGrid(totQty);
					   		    	reloadCombos();
					   		    	}else {
					   		    		$.messager.alert('Scrap HDR Entry Details Info',data.comment,'info');
					   		    	}
					   		  },
					   		error:function(){      
					   			$.messager.alert('Processing','Error while Processing Ajax...','error');
					   		  }
					   		}) 
		   			  	}
		   			});
				    }else{
				    	 $.ajax({
				 		  		headers: { 
				 		  		'Accept': 'application/json',
				 		  		'Content-Type': 'application/json' 
				 		  		},
				 		  		type: 'POST',
				 		  		data: JSON.stringify(formData),
				 		  		//data: rows,
				 		  		dataType: "json",
				 		  		url: url1,
				 		  		success: function(data) {
				 		  			
				 		  		    if(data.status == 'SUCCESS') 
				 		  		    	{
				 		  		    	$.messager.alert('Scrap HDR Entry Details Info123',data.comment,'info');
				 		  		    	//cancelT3ScrapDtlsEntry();	
				 		  		    	//getT3ScrapDtlsGrid(totQty);
				 		  		    	clearOnSave();
				 		  		    	}else {
				 		  		    		$.messager.alert('Scrap HDR Entry Details Info',data.comment,'info');
				 		  		    	}
				 		  		  },
				 		  		error:function(){      
				 		  			$.messager.alert('Processing','Error while Processing Ajax...','error');
				 		  		  }
				 		  		})
				    	}
   					} else {
   						$.messager.alert('Alert!','Define ScrapQuantity with LookupType:SCRAP_QUANTITY and LookupCode:TOTAL_SCRAP_QTY in Lookups','info');
   					}
	   			},
 		  		error:function(){      
 		  			$.messager.alert('Processing','Error while Processing Ajax...','error');
 		  		}
   		})
     }
     function clearOnSave(){
    	reloadCombos();
    	$('#t3_scrap_ptrn_desc').textbox('setValue', '');
    	$('#t3_work_center').combobox('setValue', '');
    	$('#t3_bkt_no').textbox('setValue', '');
    	$('#t3_ptrn_no').textbox('setValue', '');
		$("#t3_qty").textbox('setValue', '');
		
		var dummydata = new Array();
		$('#t3_scrap_entry_tbl_id').datagrid('loadData', dummydata);
		
		$.ajax({
      		headers: { 
      		'Accept': 'application/json',
      		'Content-Type': 'application/json' 
      		},
      		type: 'GET',
      		dataType: "json",
      		url: "./scrapEntry/getLoadedScrapBktNos",
      		success: function(data) {
      			$('#t3_loaded_bkt_tbl_id').datagrid('loadData', data);
    		}
		})
     }
     function updateSrapWeightIntf(trans_no){
    	 var value1=$("#t3_qty").textbox("getText");
    	 var value2=$("#t3_qty_frm_intf").combobox("getText");
    	// if(parseFloat(value1)==parseFloat(value2)){
    		 $.ajax({
 		  		headers: { 
 		  		'Accept': 'application/json',
 		  		'Content-Type': 'application/json' 
 		  		},
 		  		type: 'POST',
 		  		data: JSON.stringify(formData),
 		  		//data: rows,
 		  		dataType: "json",
 		  		url: './scrapEntry/updateScrapIntfEntry?trans_no='+trans_no,
 		  		success: function(data) {
 		  		    if(data.status == 'SUCCESS') 
 		  		    	{
 		  		    	getScrapWeifhtsByIntf($('#t3_work_center').combobox("getValue"));
 		  		    	}else {
 		  		    		$.messager.alert('Scrap HDR Entry Details Info',data.comment,'info');
 		  		    	}
 		  		  },
 		  		error:function(){      
 		  			$.messager.alert('Processing','Error while Processing Ajax...','error');
 		  		  }
 		  		})
    	// }
    	 
     }
     
     function reloadCombos() {
    	 $('#t3_combo_scrapBktId').combobox('reload', './CommonPool/getComboList?col1=scrap_bucket_id&col2=scrap_bucket_no&classname=ScrapBucketStatusModel&status=1&wherecol=lookupMstrMdl.lookup_id=scrap_bucket_status and lookupMstrMdl.lookup_code in (\'EMPTY\') and record_status=');
    	 $('#t3_combo_scrapBktId').combobox('setValue', '');
    	 $('#t3_combo_scrapPtrnId').combobox('reload', './CommonPool/getComboList?col1=scrap_pattern_id&col2=scrap_pattern_no&classname=ScrapPatternMasterModel&status=1&wherecol=record_status=');
    	 $('#t3_combo_scrapPtrnId').combobox('setValue', '');
     }
     
     function cancelT3ScrapDtlsEntry(){
 	    document.getElementById('t3_scrapBkt_hId').value='0';
 	   	$('#t3_combo_scrapBktId').combobox('setValue','');
 	    $('#t3_combo_scrapPtrnId').combobox('setValue','');
 	    $('#t3_scrap_ptrn_desc').textbox('setText','');
 	    $('#t3_bkt_no').textbox('setText','');
   		$('#t3_ptrn_no').textbox('setText','');
		$('#t3_qty').textbox('setText','');
		/*var data=new Array();
 	 	$('#t3_scrap_entry_tbl_id').datagrid('loadData', {"rows":data,"footer":[{"mtrlName":"<b>Total Quantity</b>","matPercent": 0,"material_qty": 0}]});
 	 	$('#t3_loaded_bkt_tbl_id').datagrid('resize');*/
 	 	//getT3ScrapDtlsGrid(0);
    }
    
    </script>
    
      