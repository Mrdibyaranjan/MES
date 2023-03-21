<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<script  src="${pageContext.request.contextPath}/js/common.js"></script>

<div title="EAF HM & Scrap details"
	style="padding-top: 5px; padding-left: 5px; padding-right: 5px;"
	data-options="iconCls:'icon-ok'">
<table id="t32_eof_hm_scrap_tbl_id"
		title="EAF HM & Scrap details"
		class="easyui-datagrid" style="width: 100%; height: 500px;"
		url="./EOFproduction/displayT32EofHMScrapDtls"
		method="get" iconCls='icon-ok' pagination="true" maximizable="true"
		resizable="true" fitColumns="true" remoteSort="false" pageSize="20"
		rownumbers="true" singleSelect="true">
	<thead>
		<tr>
			<th field="heat_no"  width="120">Heat No</th>
            <th field="aim_psn"  width="150">PSN</th>  
			<th field="grade"  width="150">Grade</th> 
            <th field="hm_wt"  width="120" >Hotmetal Qty</th> 
            <th field="total_scrap"  width="120" >Total Scrap</th>
            <th field="op_wt"  width="120" >BLT/BLM Weight</th>
            <th field="yield"  width="120">Yield</th>
            <th field="scrap_details" width="140" formatter="viewT32ScrapDetails">View Details</th>
            <th field="trns_si_no" width="0" hidden='true'>Trns Id</th>
            <th field="scrap_hdr_id" width="0" hidden='true'>Scrap Hdr Id</th>
            <th field="isConsPosted" width="0" hidden='true'>Cons Posting</th>
         </tr>
	</thead>
</table>
</div>
<!-- Scrap details -->

<div id="t32_eof_scrap_dtls_div_id" class="easyui-dialog" style="width:80%;height:600px;padding:5px 5px;" closed="true">
	<form id="t32_eof_scrap_dtls_form_id" method="post" novalidate>
		<input name="scrap_bkt_hdr_id" type="hidden"  id="scrap_bkt_hdr_id"/>
		<table style="width: 90%;padding-right: 20px">
			<tr height="40">
				<td style="width: 100px;"> 
					<label style="padding-right: 5px;display:block; width:x; height:y; text-align:right;"><b><spring:message code="label.t17.lrfChemHeatNo"/></b></label></td><td>
					<input name="heat_no"  type="text" id="t32_scrap_cons_heat_no"  class="easyui-textbox" data-options="editable:false" style="width:100px">
				</td>
				<td style="width: 100px;"> 
					<label style="padding-right: 5px;display:block; width:x; height:y; text-align:right;"><b><spring:message code="label.t17.lrfChemAimPsn"/></b></label></td><td>
					<input name="aim_psn"  type="text" id="t32_scrap_cons_aim_psn"  class="easyui-textbox" data-options="editable:false" style="width:150px">
				</td>
				<td style="width: 100px;"> 
					<label style="padding-right: 5px;display:block; width:x; height:y; text-align:right;"><b>Grade</b></label></td><td>
					<input name="grade"  type="text" id="t32_scrap_cons_grade"  class="easyui-textbox" data-options="editable:false" style="width:150px">
				</td>
				<td style="width: 200px;"> 
					<label style="padding-right: 5px;display:block; width:x; height:y; text-align:right;"><b>Total Scrap(In Tons)</b></label></td><td>
					<input name="total_scrap" type="text" id="t32_scrap_cons_tot_scrap" class="easyui-numberbox" 
					data-options="required:true, editor:{type:'numberbox',options:{precision:2,min:0}}" style="width:110px">				
				</td>
			</tr> 
		</table>
		
		<br>
		<div  style="padding-top: 10px;padding-left: 50px;padding-right: 20px;">
		<!-- <div id="t32_eof_scrap_dtls_div_id" title="EAF Scrap Details" class="easyui-dialog" style="width:85%;height:600px;padding:5px 5px;" closed="true"> -->
			<table width="70%" align="center" id="t32_eof_scrap_tbl_id" toolbar="#t32_eof_scrap_btn_div_id" class="easyui-datagrid" style="height:auto;"
           	iconCls='icon-ok' maximizable="true"  resizable="true" remoteSort="false" rownumbers="true" singleSelect="false" showFooter="true"> 
	        <thead>
	            <tr>
	              <th field="mtrlName" sortable="false" width="230px"><b><spring:message code="label.t3.materialName"/></b></th>
	              <th field="matPercent" sortable="true" align="right" width="130px"><b><spring:message code="label.t3.matPercent"/></b></th>
	              <th field="material_qty" sortable="false" align="right" width="130px" data-options="editable:false"><b><spring:message code="label.t3.Qty"/></b></th>
	              <th field="uom" formatter="(function(v,r,i){return formatColumnData('uom',v,r,i);})" sortable="false" width="110"><b><spring:message code="label.t3.uom"/></b></th>
	              <th field=scrap_bkt_detail_id sortable="true" hidden="true" width="1"></th>
	              <th field=material_id sortable="true" hidden="true" width="1"></th>
	              <th field=record_version sortable="true" hidden="true" width="1"></th>
	         	</tr>
			</thead>
	    	</table>
		</div>		
	</form>
	<div id="t32_eof_scrap_btn_div_id">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" id="btnT32Save" onclick="saveT32EofScrapDtls()" style="width:90px">Save</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload" id="btnT32Refresh" onclick="cancelT32EofScrapDtls()" style="width:90px">Refresh</a>
	</div>
</div>
<!-- Scrap Details End -->
  
<script>
	function viewT32ScrapDetails(value, row) {
		$('#btnT32Save').linkbutton('disable');
		return '<a href="#" onclick="getT32EofScrapDtls(\''
			+ row.heat_no +','+row.total_scrap+','+row.aim_psn+','+row.grade+','+row.scrap_hdr_id+','+row.isConsPosted+  '\')">View Details</a>';
	}
	function getT32EofScrapDtls(heat_dtls){
		var h_dtl=heat_dtls.split(",");
		var heat_no = h_dtl[0]; 
		var total_scrap = h_dtl[1];
		var aim_psn = h_dtl[2];
		var steel_grade = h_dtl[3];
		var scrap_hdr_id = h_dtl[4];
		var cons_posting = h_dtl[5];
		
		$("#t32_scrap_cons_heat_no").textbox("setText","");
		$("#t32_scrap_cons_aim_psn").textbox("setText","");
		$("#t32_scrap_cons_grade").textbox("setText","");
		$("#t32_scrap_cons_tot_scrap").numberbox("setValue","");
		document.getElementById('scrap_bkt_hdr_id').value= '';
		var dummydata = new Array();
		$('#t32_eof_scrap_tbl_id').datagrid('loadData', dummydata);
		
		$('#t32_eof_scrap_dtls_div_id').dialog({
			modal : true,
			cache : true
		});
		$("#t32_scrap_cons_heat_no").textbox("setText", heat_no);
		$("#t32_scrap_cons_aim_psn").textbox("setText", aim_psn);
		$("#t32_scrap_cons_grade").textbox("setText", steel_grade);
		$("#t32_scrap_cons_tot_scrap").numberbox("setValue", total_scrap);
		document.getElementById('scrap_bkt_hdr_id').value = scrap_hdr_id;
		if(cons_posting == '1'){
			$('#btnT32Save').linkbutton('disable');
			$('#t32_scrap_cons_tot_scrap').numberbox({disabled: true});
		}else{
			$('#t32_scrap_cons_tot_scrap').numberbox({disabled: false});
		}
		
		$('#t32_eof_scrap_dtls_div_id').dialog('open').dialog('center').dialog(
				'setTitle', 'EAF Scrap Details');
		loadScrapTable(scrap_hdr_id);
	}
	function loadScrapTable(sc_hdr_id){
		$.ajax({
			headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json'
			},
			type : 'GET',
			dataType : "json",
			url : "./EOFproduction/getEofScrapDtls?scrap_hdr_id="+sc_hdr_id,
			success : function(data) {
				$("#t32_eof_scrap_tbl_id").datagrid("loadData",data);
			}
		});
	}
	function cancelT32EofScrapDtls(){
		loadScrapTable(document.getElementById('scrap_bkt_hdr_id').value);
	}
	$('#t32_scrap_cons_tot_scrap').numberbox({
		min:0,
        precision:2,
        onChange: function(totScrap){
			if (totScrap > 0) {
				getT32ScrapDtlsGrid(totScrap);
				$('#btnT32Save').linkbutton('enable');
			}
         }
	});
	function getT32ScrapDtlsGrid(totQty){
		var rows = $('#t32_eof_scrap_tbl_id').datagrid('getRows');
		var tableData=[];
		var calcQty;
        if (rows != null) {
			for(var i=0; i<rows.length; i++){
				calcQty = rows[i].material_qty;
                if(rows[i].matPercent!=null && rows[i].matPercent!=""){
                	calcQty = ((parseFloat(rows[i].matPercent) * parseFloat(totQty))/100).toFixed(2);
				} 
                var details={
                	mtrlName:rows[i].mtrlName,
					matPercent:rows[i].matPercent,
					material_qty:calcQty,
 					uom:rows[i].uom,
 					scrap_bkt_detail_id:rows[i].scrap_bkt_detail_id,
 					material_id:rows[i].material_id,
 					record_version:rows[i].record_version
 				};
 				tableData.push(details);
			}
        }
        $("#t32_eof_scrap_tbl_id").datagrid("loadData",tableData);
    }
	function saveT32EofScrapDtls(){
		var d_rows = $('#t32_eof_scrap_tbl_id').datagrid('getRows');
		var total_scrap =$('#t32_scrap_cons_tot_scrap').textbox('getText');
		
		formData = {
			"scrap_bkt_header_id" : document.getElementById('scrap_bkt_hdr_id').value,
			"scrap_bkt_qty" : total_scrap,
			"scrapBktDtls" : d_rows
		};
		
	 	$.ajax({
	 		headers: { 
	 		'Accept': 'application/json',
	 		'Content-Type': 'application/json' 
	 		},
	 		type: 'POST',
	 		data: JSON.stringify(formData),
	 		dataType: "json",
	 		url: './EOFproduction/UpdateScrapBucketDtls',
	 		success: function(data) {
				if(data.status == 'SUCCESS'){
   		    		$.messager.alert('EAF HM & Scrap Consumption',data.comment,'info');
	   		    	refreshT32MainGrid();
   		    	}else {
   		    		$.messager.alert('EAF HM & Scrap Consumption',data.comment,'info');
   		    	}
	 		},
	 		error:function(){      
	 			$.messager.alert('Processing','Error while Processing Ajax...','error');
	 		}
		});
	}
	function refreshT32MainGrid(){
		$.ajax({
			headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json'
			},
			type : 'GET',
			dataType : "json",
			url : "./EOFproduction/displayT32EofHMScrapDtls",
			success : function(data) {
				$("#t32_eof_hm_scrap_tbl_id").datagrid("loadData",data);
			}
		});
	}
</script>