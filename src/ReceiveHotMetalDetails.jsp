<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<div  style="padding-top: 10px;padding-left: 10px;padding-right: 10px;">
      <table id="t1_hm_recv_tbl_id" toolbar="#t1_hm_recv_tbl_toolbar_div_id"  title="<spring:message code="label.t1.hmRecevHeader"/>" class="easyui-datagrid" style="width:100%;height: 500px;"
            url="./HMReceive/getHMDetailsbyStatus?STAGE=AVAILABLE&LADLE_STATUS=RECVD" method="get" iconCls='icon-ok'
             pagination="true" maximizable="true"  resizable="true" remoteSort="false" pageSize="20"
            rownumbers="true"  singleSelect="true"> 
        <thead data-options="frozen:true">
            <tr>
                <th field="hmRecvId" sortable="false" hidden="true" width="40"><spring:message code="label.t1.hmRecvId"/></th>
                <!-- <th field="castNo" sortable="true" width="70"><spring:message code="label.t1.castNo"/></th>  -->
                <th field="hmLadleNo" sortable="true" width="70"><spring:message code="label.t1.hmLadleNo"/></th>
                <th field="hmLadleProdDt" sortable="true" formatter="(function(v,r,i){return formatDateTime('hmLadleProdDt',v,r,i)})"  width="150"><spring:message code="label.t1.prodDate"/></th> 
                <th field="lookupHmSource.lookup_id" sortable="true" width="75" formatter="(function(v,r,i){return formatColumnData('lookupHmSource.lookup_value',v,r,i);})"><spring:message code="label.t1.hmSource"/></th>
         </tr>
         </thead>
         <thead>
           <tr>
           <!-- <th field="hmLadleTempBf" sortable="false" width="80"><spring:message code="label.t1.hmLadleTempAtBf"/></th>  -->
           <th field="hmLadleGrossWt" sortable="false" width="80"><spring:message code="label.t1.hmGrossWt"/></th>
      <%--      <th field="hmLadleTareWt" sortable="false" width="80"><spring:message code="label.t1.hmTareWt"/></th>
           
           <th field="hmLadleNetWt" sortable="false" width="80"><spring:message code="label.t1.hmNetWt"/></th> --%>
           <th field="hmAvlblWt" sortable="false" hidden="true" width="0"><spring:message code="label.t1.hmAvlblWt"/></th>
           
            <th field="hmLadleC" sortable="false" width="50"><spring:message code="label.t1.c"/></th>
             <th field="hmLadleSi" sortable="false" width="50"><spring:message code="label.t1.si"/></th>
           <th field="hmLadleMn" sortable="false" width="50"><spring:message code="label.t1.mn"/></th>
           <th field="hmLadleP" sortable="false" width="50"><spring:message code="label.t1.p"/></th>
           <th field="hmLadleS" sortable="false" width="50"><spring:message code="label.t1.s"/></th>
           
           
          
           <th field="hmLadleTi" sortable="false" width="50"><spring:message code="label.t1.ti"/></th>
           <th field="remarks" sortable="false" width="150"><spring:message code="label.t1.remarks"/></th>
           </tr>
        </thead>
    </table>
    <input type="hidden" id="t51_hm_min_length">
    <input type="hidden" id="t51_hm_max_length">
     <div id="t1_hm_recv_tbl_toolbar_div_id" style="padding-top: 5px;">
      <%@ page import="java.util.*" %>
    
 <%--Display Buttons  --%>
   
   	<%
    request.setAttribute("scrn_id", Integer.parseInt(request.getParameter("scrn_id")));
    %>
    <table>
    <tr>
    <td width="80%">
    <c:forEach var="disbtn" items="${display_btn}">
   <c:forEach var="btn_view" items="${disbtn.value}">
     <c:if test='${disbtn.key == scrn_id}'>
     <%--Setting Button Position  --%>
      <c:set var="btn_position" value="${fn:split(btn_view, '$$$')}"/>
	   <c:if test="${btn_position[1] eq 'PORTION1'}">
	   ${btn_position[0]}
	   </c:if>
     
     </c:if>
   </c:forEach>
   </c:forEach>
    </td>
   <td align="center" width="20%">
      <div id="divImage" style="height: 30px; width: 30px" onclick="javascript:void(0)"  class="easyui-tooltip" data-options="
                    hideEvent: 'none',
                    content: function(){
                        return gridT1LadleValidation();
                    },
                    onShow: function(){
                        var t = $(this);
                        t.tooltip('tip').focus().unbind().bind('blur',function(){
                            t.tooltip('hide');
                        });
                    }
                "></div>
                </td>
                </tr>
                </table>
     </div>
    </div>
    
    <div id="t1_hm_receive_form_div_id" class="easyui-dialog" style="width:900px;height:450px;padding:10px 10px"
            closed="true" buttons="#t1_hm_receive_form_btn_div_id">
             
        <form id="t1_hm_receive_form_id" method="post" novalidate>
        
        <input name="hmRecvId" type="hidden" class="textboxhidden"  id="t1_hmRecvId">
      
      	
        <table style="width: 100%">
        <tr>
       <td colspan="6">  <div class="ftitle"><spring:message code="label.t1.hmRecevformHeader"/></div>   </td>
       </tr>
        <!-- first row -->
        <tr style="height: 30px;">
        <td> 
        <label><spring:message code="label.t1.hmLadleNo"/></label>
        </td>
        <td>
            <input name="hmLadleNo"  type="text" id="t1_hmLadleNo"  class="easyui-combobox" data-options="required:true,editable:false,validType:{length:[1,4]},valueField:'keyval',                    
                    textField:'txtvalue'">
                <!-- <input name="hmLadleNo"  type="text" id="t1_hmLadleNo"  class="easyui-textbox" data-options="required:true,validType:{length:[1,4]}"> -->
            
        </td>
        <!-- 
        <td> 
        <label><spring:message code="label.t1.castNo"/></label>
        </td>
        <td>
                <input name="castNo"  type="text" id="t1_castNo"  class="easyui-textbox" data-options="required:true,validType:{length:[1,10]}">                 
        </td>   -->      
        <td> 
        <label><spring:message code="label.t1.hmSource"/></label>
        </td>
        
         <td>
   <input name="hmSource"  type="text" id="t1_hmSource"  class="easyui-combobox" data-options="required:true,panelHeight: 'auto',editable:false,selected:true,
                    valueField:'keyval',                    
                    textField:'txtvalue'
            ">
        </td>
        <td> 
        <label><spring:message code="label.t1.prodDate"/></label>
        </td>
        <td>
                <input name="hmLadleProdDt"  type="text" id="t1_hmLadleProdDt"  class="easyui-datetimebox" data-options="required:false,showSeconds:false,onChange:function(){formEntryValidations();}">
        </td>
        
        
        </tr>
        
         <!-- 3rd row -->
       
       
       <tr style="height: 30px;">
       <!--  
        <td> 
        <label><spring:message code="label.t1.tempAtBf"/></label>
        </td>
        <td>
            
                <input name="hmLadleTempBf"  type="text" id="t1_hmLadleTempBf"  class="easyui-numberbox" data-options="required:false,validType:{length:[1,4]},onChange:function(){formBFTempValidation();}">
            
        </td>
        -->
        <td> 
       <label><spring:message code="label.t1.hmGrossWt"/></label>
        </td>
        
         <td>
          <input name="hmLadleGrossWt"  type="text" id="t1_hmLadleGrossWt"  class="easyui-numberbox" data-options="required:true,precision:2,onChange:function(){netWeightCalculation();}" >
          <!-- <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="getgrossWeight()" style="width:30px"></a> -->
   
        </td>
        <td> 
        <label><spring:message code="label.t1.hmReceiveDt"/></label>
        </td>
        
         <td>
                 <input name="hmRecvDate"  type="text" id="t1_hmRecvDate"  class="easyui-datetimebox" data-options="required:true,showSeconds:false">
   
        </td>
        
        </tr>
       <tr>
       <td colspan="6"> <div class="ftitle"><spring:message code="label.t1.hmRecevChemHeader"/></div>  </td>
       </tr>
          <!-- 5th row -->
       <tr style="height: 30px;">
        <td> 
        <label><spring:message code="label.t1.c"/></label>
        </td>
        <td>
                <input name="hmLadleC"  type="text" id="t1_hmLadleC"  class="easyui-numberbox" data-options="required:false,precision:3">
        </td>
        
        <td> 
        <label><spring:message code="label.t1.si"/></label>
        </td>
        <td>
            
                <input name="hmLadleSi"  type="text" id="t1_hmLadleSi"  class="easyui-numberbox" data-options="required:false,precision:3">
            
        </td>
        
        <td> 
        <label><spring:message code="label.t1.mn"/></label>
        </td>
        <td>
            
                <input name="hmLadleMn"  type="text" id="t1_hmLadleMn"  class="easyui-numberbox" data-options="required:false,precision:3">
            
        </td>
        
        </tr>
       
       
           <!-- 6th row -->
       <tr style="height: 30px;">
        <td> 
        <label><spring:message code="label.t1.p"/></label>
        </td>
        <td>
                <input name="hmLadleP"  type="text" id="t1_hmLadleP"  class="easyui-numberbox" data-options="required:false,precision:3,onChange:function(){formchemPValidation();}">
        </td>
        
        <td> 
        <label><spring:message code="label.t1.s"/></label>
        </td>
        
         <td>
                 <input name="hmLadleS"  type="text" id="t1_hmLadleS" class="easyui-numberbox" data-options="required:false,precision:3,onChange:function(){formchemSValidation();}">
   
        </td>
        
        <td> 
        <label><spring:message code="label.t1.ti"/></label>
        </td>
        
         <td>
                 <input name="hmLadleTi"  type="text" id="t1_hmLadleTi"  class="easyui-numberbox" data-options="precision:3">
   
        </td>
        
        </tr>
        
        <!-- 7th row -->
        <tr style="height: 50px;">
          <td> 
        <label><spring:message code="label.t1.remarks"/></label>
        </td>
       <td colspan="5">
         <input name="remarks"  type="text" id="t1_remarks"  class="easyui-textbox" data-options="multiline:true" style="height:50px;width: 97%">
        </td>
       </tr>
       
        
        </table>
 		 <span>
        <input name="hmLadleStatus" type="hidden" class="textboxhidden"    id="t1_hmLadleStatus">
        <input name="dataSource" type="hidden" class="textboxhidden"    id="t1_dataSource">
        <input name="hmLadleStageStatus" type="hidden" class="textboxhidden"   id="t1_hmLadleStageStatus">
         <input name="version" type="hidden" class="textboxhidden"   id="t1_version">
         <input name="ladle_no" type="hidden" class="textboxhidden"  id="t1_ladle_no">
       </span>
        </form>
    </div>
    
     <div id="t1_hm_receive_form_btn_div_id">
     
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveT1HMReceive()" style="width:90px">Save</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="cancelT1HMReceive()" style="width:90px">Cancel</a>
    </div>
    
    
    <div id="t1_hm_receive_next_process_form_div_id" class="easyui-dialog" style="width:430px;height:auto;padding:10px 10px"
            closed="true" buttons="#t1_hm_receive_next_process_form_btn_div_id">
        <div class="ftitle"><spring:message code="label.t1.hmNextProcessHeader"/></div>       
        <form id="t1_hm_receive_next_process_form_id" method="post" novalidate>
         <input name="hmRecvId" type="hidden" class="textboxhidden"   id="t1_NextProcesshmRecvId">
         <div class="fitem">
              <label><spring:message code="label.t1.hmNextProcess"/></label>
                <input name="hmNextProcess"  type="text" id="t1_hmNextProcess"  class="easyui-combobox" data-options="required:true,panelHeight: 'auto',editable:false,
                    valueField:'keyval',                    
                    textField:'txtvalue'">
                    <label style="padding-top:10px">Gross Weight</label> <%-- <spring:message code="label.t1.hmMeasuredWeight"/> --%>
                <input name="hmMeasuredWeight"  type="text" id="t1_hmMeasuredWeight"  class="easyui-numberbox" data-options="editable:true"  >
                <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="getProcessHeatgrossWeight()" style="width:30px"></a>
                     <label style="padding-top:10px"><spring:message code="label.t1.tempAtEof"/></label>
                <input name="hmMeasuredWeight"  type="text" id="t1_tempAtEof"  class="easyui-numberbox"  data-options="required:true,onChange:function(){prodRecvDateValidation();}" >
            </div>
        </form>
    </div>
    
    <div id="t1_hm_receive_next_process_form_btn_div_id" align="center">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveT1HmReceiveNextProcess()" style="width:90px">Process</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="cancelT1NextProcess()" style="width:90px">Cancel</a>
    </div>
    
     <div id="t1_get_hm_det_jpod_div_id" class="easyui-dialog" style="height:600px;padding:10px 10px;width: 90%;"   closed="true">
      <table id="t1_get_hm_det_jpod_tbl_id" toolbar="#t1_get_hm_det_jpod_tbl_toolbar_div_id"  title="HM Details Available in Jpod Interface" class="easyui-datagrid" style="width:100%;height: 500px;"
            method="get" iconCls='icon-ok'
             pagination="true" maximizable="true"  resizable="true" remoteSort="false" pageSize="20"
            rownumbers="true"  singleSelect="false"> 
        <thead data-options="frozen:true">
            <tr>
              	<th field="ck" checkbox="true"></th>
                <th field="hmSeqNo" sortable="false" hidden="false" width="40">HmSeqNo</th>
                <!-- <th field="castNo" sortable="true" width="70"><spring:message code="label.t1.castNo"/></th>  -->
                <th field="hmLadleNo" sortable="true" width="70"><spring:message code="label.t1.hmLadleNo"/></th>
                <th field="hmLadleProdDt" sortable="true" formatter="(function(v,r,i){return formatDateTime('hmLadleProdDt',v,r,i)})"  width="150"><spring:message code="label.t1.prodDate"/></th> 
                <th field="sourceOfHm" sortable="true" width="70" ><spring:message code="label.t1.hmSource"/></th>
         </tr>
         </thead>
         <thead>
           <tr>
           <th field="hmLadleTempBf" sortable="false" width="80"><spring:message code="label.t1.hmLadleTempAtBf"/></th>
           <th field="hmLadleGrossWt" sortable="false" width="80"><spring:message code="label.t1.hmGrossWt"/></th>
           <th field="hmLadleTareWt" sortable="false" width="80"><spring:message code="label.t1.hmTareWt"/></th>
           
           <th field="hmLadleNetWt" sortable="false" width="80"><spring:message code="label.t1.hmNetWt"/></th>
           <th field="hmAvlblWt" sortable="false" hidden="true" width="0"><spring:message code="label.t1.hmAvlblWt"/></th>
           
            <th field="hmLadleC" sortable="false" width="50"><spring:message code="label.t1.c"/></th>
           <th field="hmLadleMn" sortable="false" width="50"><spring:message code="label.t1.mn"/></th>
           <th field="hmLadleS" sortable="false" width="50"><spring:message code="label.t1.s"/></th>
           
           <th field="hmLadleP" sortable="false" width="50"><spring:message code="label.t1.p"/></th>
           <th field="hmLadleSi" sortable="false" width="50"><spring:message code="label.t1.si"/></th>
           <th field="hmLadleTi" sortable="false" width="50"><spring:message code="label.t1.ti"/></th>
           <th field="remarks" sortable="false" width="150"><spring:message code="label.t1.remarks"/></th>
           </tr>
        </thead>
    </table>
      <div id="t1_get_hm_det_jpod_tbl_toolbar_div_id" align="left" >
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="saveT1HMData()" style="width:160px">Receive Hot Metal</a>
       
    </div>
    
    
    </div>
    
     <script type="text/javascript">
    
     $(window).load(setTimeout(applyT1Filter,1));  //1000 ms = 1 second.
    
    function applyT1Filter()
    {
    	
		 $('#t1_hm_recv_tbl_id').datagrid('enableFilter');
		
    }
     
     
    function cancelT1HMReceive()
    {
    	$('#t1_hm_receive_form_div_id').dialog('close');
    	refreshT1HmReceive();
    }
    
    
    function refreshT1HmReceive()
    {
    	
     	$.ajax({
     		headers: { 
     		'Accept': 'application/json',
     		'Content-Type': 'application/json' 
     		},
     		type: 'GET',
     		//data: JSON.stringify(formData),
     		dataType: "json",
     		url: "./HMReceive/getHMDetailsbyStatus?STAGE=AVAILABLE&LADLE_STATUS=RECVD",
     		success: function(data) {
     			 $('#t1_hm_recv_tbl_id').datagrid('loadData', data); 
     		  },
     		error:function(){      
     			$.messager.alert('Processing','Error while Processing Ajax...','error');
     		  }
     		})
    }
    
    
    function callT1Dropdowns()
    {
    	var url1="./CommonPool/getComboList?col1=lookup_id&col2=lookup_value&classname=LookupMasterModel&status=1&wherecol=lookup_type='HM_SOURCE' and lookup_status=";
    	getDropdownList(url1,'#t1_hmSource');
    	
    }
    
    function callLadleNoDropDownBox(){
    	var urlLadleQ="./CommonPool/getComboList?col1=hm_ladle_no&col2=hm_ladle_no&classname=HmLadleMasterModel&status=&wherecol=record_status=1";
    	getDropdownList(urlLadleQ,'#t1_hmLadleNo');
    }
    
   
    
    function getT1HMInterfaceData()
    {
//   
    	$.messager.progress(); 
    	callT1Dropdowns();
    	$('#t1_get_hm_det_jpod_div_id').dialog({modal:true,cache: true});
        $('#t1_get_hm_det_jpod_div_id').dialog('open').dialog('center').dialog('setTitle','Hot Metal in JPOD');
     
         
        //$('#t1_get_hm_det_jpod_tbl_id').datagrid('loadData', new Array()); 
    	//$('#t1_get_hm_det_jpod_tbl_id').datagrid('enableFilter');
    		
    	 $.ajax({
     		headers: { 
     		'Accept': 'application/json',
     		'Content-Type': 'application/json' 
     		},
     		type: 'GET',
     		//data: JSON.stringify(formData),
     		dataType: "json",
     		url: './HMReceive/getHMDetailsFromJPOD', // getHMDetailsbyInterface
     		success: function(data) {
     			console.log(data)
     			$('#t1_get_hm_det_jpod_tbl_id').datagrid('loadData', data); 
     			if(data.length>0){
     				 $.messager.progress('close');
     				$.messager.alert('Hot Metal Interface Details Info',"HM Data loaded from JPOD");
     				
     			}else{
     				 $.messager.progress('close');
     				$.messager.alert('Hot Metal Interface Details Info',"No New HM Details available in JPOD");
     				dialogBoxClose('t1_get_hm_det_jpod_div_id');
     				
     			}
     			/* if(data.status == 'SUCCESS') 
   		    	{
     				
   		    	refreshT1HmReceive();
   		    	$.messager.alert('Hot Metal Interface Details Info',data.comment,'info');
   		    	}else {
   		    		$.messager.alert('Hot Metal Interface Details Info',data.comment,'info');
   		    	} */
     		  },
     		error:function(){      
     			$.messager.alert('Processing','Error while Processing Ajax...','error');
     		  }
     		}) 
    }
    
    function addT1HmReceive(){
    	callT1Dropdowns();
    	callLadleNoDropDownBox();
    	$('#t1_hm_receive_form_div_id').dialog({modal:true,cache: true});
        $('#t1_hm_receive_form_div_id').dialog('open').dialog('center').dialog('setTitle','Hot Metal Receive Entry Form');
        $('#t1_hm_receive_form_id').form('clear');
       // $('#t1_hmRecvId').textbox('setValue',"0");
       var current_date=new Date();
      // $('#t1_hmLadleProdDt').datetimebox('setValue', current_date.getDate()+"/"+(current_date.getMonth()+1)+"/"+current_date.getFullYear());
       $('#t1_hmRecvDate').datetimebox('setValue', current_date.getDate()+"/"+(current_date.getMonth()+1)+"/"+current_date.getFullYear() +" "+current_date.getHours()+":"+current_date.getMinutes());
        document.getElementById('t1_hmRecvId').value='0';
       // $('#t1_hmLadleGrossWt').numberbox('disable');
       
    }
    
    
    function validateT1HMReceiveForm(){
        return $('#t1_hm_receive_form_id').form('validate');
    }
    
 
    
    function saveT1HMReceive()
    {
    	
    	if(validateT1HMReceiveForm())
		{
    		
    		var hmLadleNo=$('#t1_hmLadleNo').textbox('getText');
    		//var castNo=$('#t1_castNo').textbox('getText');
    		var castNo="";
    		var hmSource =$('#t1_hmSource').combobox('getValue');
    		
    		var hmLadleTareWt=0;//$('#t1_hmLadleTareWt').numberbox('getValue');
    		
    		var hmLadleGrossWt=$('#t1_hmLadleGrossWt').numberbox('getValue');
    		var hmLadleNetWt=0;//$('#t1_hmLadleNetWt').numberbox('getValue');
    		var hmAvlblWt=hmLadleGrossWt;//$('#t1_hmLadleNetWt').numberbox('getValue');
    		
    		var hmLadleProdDt=$('#t1_hmLadleProdDt').datetimebox('getText');
    		var hmLadleTempBf=0//$('#t1_hmLadleTempBf').numberbox('getValue');
    		var hmLadleTempEof=0;//$('#t1_hmLadleTempEof').numberbox('getValue');
    		var hmRecvDate=$('#t1_hmRecvDate').datetimebox('getText');
    		
    		var hmLadleC=$('#t1_hmLadleC').numberbox('getValue');
    		var hmLadleMn=$('#t1_hmLadleMn').numberbox('getValue');
    		var hmLadleP=$('#t1_hmLadleP').numberbox('getValue');
    		var hmLadleS=$('#t1_hmLadleS').numberbox('getValue');
    		var hmLadleSi=$('#t1_hmLadleSi').numberbox('getValue');
    		var hmLadleTi=$('#t1_hmLadleTi').numberbox('getValue');
    		var remarks=$('#t1_remarks').textbox('getText');
    		//var hmRecvId=$('#t1_hmRecvId').textbox('getText');
    		var hmRecvId=document.getElementById('t1_hmRecvId').value;
    		//alert("ok");
    		var dataSource=document.getElementById('t1_dataSource').value;//$('#t1_dataSource').textbox('getText');
    		//var dataSource=$('#t1_dataSource').textbox('getText');
    		//var hmLadleStageStatus=$('#t1_hmLadleStageStatus').textbox('getText'); 
    		//var hmLadleStatus=$('#t1_hmLadleStatus').textbox('getText');
			var hmLadleStageStatus=document.getElementById('t1_hmLadleStageStatus').value;
			var hmLadleStatus=document.getElementById('t1_hmLadleStatus').value;
			var version=document.getElementById('t1_version').value;
			
			var ladle_no=document.getElementById('t1_ladle_no').value;
			if(hmLadleTempBf==null  ||hmLadleTempBf =='' ){
				hmLadleTempBf=0;
			}
           
      var formData = {"hmLadleNo":hmLadleNo,
           				"castNo":castNo,"hmSource":hmSource,
           				"hmLadleTareWt":hmLadleTareWt,"hmLadleGrossWt":hmLadleGrossWt,"hmLadleNetWt":hmLadleNetWt,"hmAvlblWt":hmAvlblWt,"hmLadleProdDt_s":hmLadleProdDt
           				,"hmLadleTempBf":hmLadleTempBf,"hmLadleTempEof":hmLadleTempEof,"hmRecvDate_s":hmRecvDate,"hmLadleC":hmLadleC,"hmLadleMn":hmLadleMn
           				,"hmLadleP":hmLadleP,"hmLadleS":hmLadleS,"hmLadleSi":hmLadleSi,"hmLadleTi":hmLadleTi,"remarks":remarks,"hmRecvId":hmRecvId
           				 ,"hmLadleStatus":hmLadleStatus,"dataSource":dataSource,"hmLadleStageStatus":hmLadleStageStatus,"version":version };
     
      	$.ajax({
           		headers: { 
           		'Accept': 'application/json',
           		'Content-Type': 'application/json' 
           		},
           		type: 'POST',
           		data: JSON.stringify(formData),
           		dataType: "json",
           		url: './HMReceive/SaveOrUpdate?ladle_no='+ladle_no,
           		success: function(data) {
           		    if(data.status == 'SUCCESS') 
           		    	{
           		    	$.messager.alert('Hot Metal Receive Info',data.comment,'info');
           		    	cancelT1HMReceive();
           		    	
           		    	}else {
           		    		$.messager.alert('Hot Metal Receive Info',data.comment,'info');
           		    	}
           		  },
           		error:function(){      
           			$.messager.alert('Processing','Error while Processing Ajax...','error');
           		  }
           		}) 
    		
		}
    }
    
    
     function editT1HmReceive(){
    	 var row = $('#t1_hm_recv_tbl_id').datagrid('getSelected');
         if (row){
        	 callT1Dropdowns();
        	// var date1=new Date(row.hmLadleProdDt);
         	//var date2=new Date(row.hmRecvDate);
         	 
         	$('#t1_hm_receive_form_id').form('clear');
           	$('#t1_hm_receive_form_div_id').dialog({modal:true,cache: true});            	           	
         	 $('#t1_hm_receive_form_div_id').dialog('open').dialog('center').dialog('setTitle','Edit Hot Metal Receive Edit Form');
         	
         	document.getElementById('t1_ladle_no').value=row.hmLadleNo;
              //$('#t1_hmLadleTareWt').numberbox('setValue',row.hmLadleTareWt);
             $('#t1_hmLadleGrossWt').numberbox('setValue',row.hmLadleGrossWt);
             //$('#t1_hmLadleNetWt').numberbox('setValue',row.hmLadleNetWt);
             
             $('#t1_hmLadleProdDt').datetimebox('setText',formatDate(row.hmLadleProdDt));
             //$('#t1_hmLadleTempBf').numberbox('setValue',row.hmLadleTempBf);
             $('#t1_hmLadleTempEof').numberbox('setValue',row.hmLadleTempEof);
             
             $('#t1_hmRecvDate').datetimebox('setText',formatDate(row.hmRecvDate));
             
             $('#t1_hmLadleC').numberbox('setValue',row.hmLadleC);
             $('#t1_hmLadleMn').numberbox('setValue',row.hmLadleMn);
             $('#t1_hmLadleS').numberbox('setValue',row.hmLadleS);
             
             $('#t1_hmLadleP').numberbox('setValue',row.hmLadleP); 
             $('#t1_hmLadleSi').numberbox('setValue',row.hmLadleSi);
             $('#t1_hmLadleTi').numberbox('setValue',row.hmLadleTi); 
             
             $('#t1_hm_receive_form_id').form('load',row);    
            
         }else{
         	$.messager.alert('Information','Please Select Record...!','info');
         }
         
    }
     
        
    
    function cancelT1NextProcess()
    {
   	 $('#t1_hm_receive_next_process_form_div_id').dialog('close');
   	refreshT1HmReceive();
    }
    

    
    function nextProcessT1HmReceive(){
   	 var row = $('#t1_hm_recv_tbl_id').datagrid('getSelected');
        if (row){
        	
        	var t1_url="./CommonPool/getComboList?col1=lookup_code&col2=lookup_value&classname=LookupMasterModel&status=1&wherecol=lookup_type='HM_NEXT_PROCESS' and lookup_status=";
        	getDropdownList(t1_url,'#t1_hmNextProcess');
        	
       	$('#t1_hm_receive_next_process_form_div_id').dialog({modal:true,cache: true});
        	$('#t1_hm_receive_next_process_form_id').form('clear');
        	  $('#t1_hm_receive_next_process_form_div_id').dialog('open').dialog('center').dialog('setTitle','Hot Metal Next Process');
            $('#t1_hm_receive_next_process_form_id').form('load',row);
            
           // $('#t1_NextProcesshmRecvId').textbox('setValue',row.hmRecvId);
            
            document.getElementById('t1_NextProcesshmRecvId').value=row.hmRecvId;
            
        }else{
        	$.messager.alert('Information','Please Select Record...!','info');
        }
        
   }
    
    function validateT1NextProcessHMReceiveForm(){
    	 return $('#t1_hm_receive_next_process_form_id').form('validate');
    }
    
    function saveT1HmReceiveNextProcess()
    {
    	if(validateT1NextProcessHMReceiveForm())
		{

    		var nextProcessId =$('#t1_hmNextProcess').combobox('getValue');
    		
    		var hmRecvId= document.getElementById('t1_NextProcesshmRecvId').value;//$('#t1_NextProcesshmRecvId').textbox('getText');
    		
    		var hmSmsMwt= $('#t1_hmMeasuredWeight').textbox('getText');
    		var t1_tempAtEof=$('#t1_tempAtEof').textbox('getText');
       var formData = {"hmRecvId":hmRecvId,
           				"hmNextProcess":nextProcessId,
           				"hmSmsMeasuredWt":hmSmsMwt,
           				"hmLadleTempEof":t1_tempAtEof};
      
           	$.ajax({
           		headers: { 
           		'Accept': 'application/json',
           		'Content-Type': 'application/json' 
           		},
           		type: 'POST',
           		data: JSON.stringify(formData),
           		dataType: "json",
           		url: './HMReceive/HMNextProcessUnit',
           		success: function(data) {
           		    if(data.status == 'SUCCESS') 
           		    	{
           		    	$.messager.alert('Hot Metal Sent Info',data.comment,'info');
           		    	cancelT1NextProcess();
           		    	
           		    	}else {
           		    		$.messager.alert('Hot Metal Sent Info',data.comment,'info');
           		    	}
           		  },
           		error:function(){      
           			$.messager.alert('Processing','Error while Processing Ajax...','error');
           		  }
           		})
		}
    }

    function getHMMinWgt(){
    	var lkp_type = 'HM_WGT_MIN';
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
    				document.getElementById('t51_hm_min_length').value = data[i].lookup_value;
    			}
    		}
    	});
    }

    function getHMMaxWgt(){
    	var lkp_type = 'HM_WGT_MAX';
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
    				document.getElementById('t51_hm_max_length').value = data[i].lookup_value;
    			}
    		}
    	});
    }
    getHMMinWgt();
	getHMMaxWgt();
    function netWeightCalculation(){
        var HMWeightMin = document.getElementById('t51_hm_min_length').value ;
        var HMWeightMax = document.getElementById('t51_hm_max_length').value;
    	var hmLadleTareWt=0//$('#t1_hmLadleTareWt').numberbox('getValue');
		var hmLadleGrossWt=$('#t1_hmLadleGrossWt').numberbox('getValue');
		/* if((hmLadleGrossWt<10 && hmLadleGrossWt>70)){
			$.messager.alert('Alert','Gross weight should be B/W 10 & 70','warning');
			} */
			
    	  if(hmLadleGrossWt<parseFloat(HMWeightMin) || hmLadleGrossWt>parseFloat(HMWeightMax)){
			$.messager.alert('Alert','HM weight should be B/W '+HMWeightMin+'&'+HMWeightMax,'warning');
			$('#t1_hmLadleGrossWt').numberbox('setValue','');
			}
			
		if(hmLadleTareWt!=''&& hmLadleTareWt!=null){
			if(hmLadleTareWt<hmLadleGrossWt)	{
			var hmLadleNetWt= hmLadleGrossWt - hmLadleTareWt;
			$//('#t1_hmLadleNetWt').numberbox('setValue',hmLadleNetWt);
			}else{
				$.messager.alert('Alert','Tare weight should be less than gross weight','info');
				
				$('#t1_hmLadleGrossWt').numberbox('setValue','');
				}
			}else if(hmLadleGrossWt!=null && hmLadleGrossWt!=''){
				//$.messager.alert('Alert','Please enter HM Tare Wt','info');
				//$('#t1_hmLadleGrossWt').numberbox('setValue','');
			}
			
			
		}
    
    function resetHmLadleGrossWt(){
    	// var tareWt= $('#t1_hmLadleTareWt').numberbox('getValue');
    	
         /* if(tareWt!=''){
        	 $('#t1_hmLadleGrossWt').numberbox('enable');
         }else{
        	 $('#t1_hmLadleGrossWt').numberbox('disable');
         }
    	$('#t1_hmLadleGrossWt').numberbox('setValue','');
    	$('#t1_hmLadleNetWt').numberbox('setValue',''); */
         
    }
  
    function formEntryValidations(){
    	var p_date= $('#t1_hmLadleProdDt').datetimebox('getValue');
    	var prod_Date= commonDateISOformat(p_date);
    	var sys_date=new Date();
    	    	
		
		if(((sys_date.getTime()-prod_Date.getTime())/(1000*60*60))>4){
			$.messager.alert('Warning','Production Date is more than 4 hours','warning');
		}
    	
    }
    
    function formBFTempValidation(){
    	var bf_temp= $('#t1_hmLadleTempBf').numberbox('getText');
    	if(!(bf_temp>1450 && bf_temp<1600 )){
    		$.messager.alert('Warning','Temp at BF should be B/W 1450 & 1600','warning');
    	}
    }

    function formEOFTempValidation(){
    	var bf_temp= $('#t1_hmLadleTempBf').numberbox('getText');
    	var eof_temp= $('#t1_hmLadleTempEof').numberbox('getText');
    	if(bf_temp<eof_temp ){
    		$.messager.alert('Warning','Temp at BF should be greater than Temp at EAF','warning');
    		$('#t1_hmLadleTempEof').numberbox('setValue','');
    	}
    }
      
    function formchemSValidation(){
    	var chem_s= $('#t1_hmLadleS').numberbox('getText');
    	if(chem_s > 1 ){
    		$.messager.alert('Warning','S% should be less than 1','warning');
    	}
    }
    
    function formchemPValidation(){
    	var chem_p= $('#t1_hmLadleP').numberbox('getText');
    	if(chem_p > 1 ){
    		$.messager.alert('Warning','P% should be less than 1','warning');
    	}
    }
    
   /*  $(document).ready(function(){
        setInterval(gridT1imgChange,10000)}); */
    	 
    	 
    	function gridT1imgChange()
   	 {
    		var images = ["warningSign.jpg","success.jpg","warning.jpg"];
    		
   		 $("#divImage").css("background-image", "");
   		
   		
   	        	//var rettxtmsg=gridT1LadleValidationBreak();
   	        	
   	        	if(rettxtmsg!="")
   	        		{
   	        		 $("#divImage").css("background-image", "url(./images/" + images[0] + ")");
   	        		 $("#divImage").css("background-size", "30px 30px");
   	        		 $("#divImage").css("background-color", "red");
   	        		}
   	      
   	   
   	 }
    	function prodRecvDateValidation(){
    		var p_date= $('#t1_hmLadleProdDt').datetimebox('getValue');
    		var rec_date=$('#t1_hmRecvDate').datetimebox('getValue');
        	var prod_Date= commonDateISOformat(p_date);
        	var recv_Date= commonDateISOformat(rec_date);
        	
        	if(prod_Date.getTime()>=recv_Date.getTime()){
        		if(rec_date!=''){
        			//$('#t1_hmRecvDate').datetimebox('setValue','');
    				$.messager.alert('Warning','HM Recieved Date Should be greater than or equal to Production Date','warning');
    		}
        	}
    	}
    	
    	
    	function gridT1LadleValidation(){
   		var rows=$('#t1_hm_recv_tbl_id').datagrid('getRows');  		
    		var sys_date=new Date();
    		var txtmsg="";
    		var msg="";
    		if(rows.length>8){
    			msg= '<p style="color: red;"><b> More than 8 Ladles pending for process in Hot metal receive </b><p> <br>';
    			txtmsg=txtmsg+ msg;
    		}
    		for(i=0;i<rows.length;i++){
    		
    		if(((sys_date-rows[i].hmLadleProdDt)/(1000*60*60))>4){
    			
    			msg= '<b>('+parseInt(i+1)+') Ladle'+rows[i].hmLadleNo+' waiting for process more than 4 hours </b> <br>';
    	         txtmsg=txtmsg+ msg;
    		}
    		
    	}
    	return txtmsg; 
    	} 
    	
    	function gridT1LadleValidationBreak(){
    		var rows=$('#t1_hm_recv_tbl_id').datagrid('getRows');
    		var sys_date=new Date();
    		var txtmsg="";
    		var msg="";
    		for(i=0;i<rows.length;i++){
    			if(rows.length>8){
    	 			msg= '<p style="color: red;"><b> More than 8 Ladles pending for process in Hot metal receive </b><p> <br>';
    	 			txtmsg=txtmsg+ msg;
    	 		}
    			
    		if(((sys_date-rows[i].hmLadleProdDt)/(1000*60*60))>4){
    			
    			msg= '<b>('+parseInt(i+1)+') Ladle'+rows[i].hmLadleNo+' waiting for process more than 4 hours </b> <br>';
    	         txtmsg=txtmsg+ msg;
    		}
    		break;
    		
    	}
    	return txtmsg;
    	} 
    	
    	 function saveT1HMData(){
         	
         var checked_rows = $('#t1_get_hm_det_jpod_tbl_id').datagrid('getChecked');
          if(checked_rows.length>0){
        	  $.messager.progress();
            		//var checked_rows = $('#t7_eof_Chemistry_tbl_id').datagrid('getRows');// $('#t7_eof_Chemistry_tbl_id').datagrid('getSelections');
         		var hm_arry = '';
         		for (var i = 0; i < checked_rows.length; i++) {
         			if ((checked_rows[i].hmSeqNo != null && checked_rows[i].hmSeqNo != '')) {
         				hm_arry += 
         				checked_rows[i].hmLadleTareWt + '@'
         				+ checked_rows[i].hmLadleGrossWt + '@'
         				+ checked_rows[i].hmLadleNetWt + '@'
         				+ checked_rows[i].hmLadleC + '@'
         				+ checked_rows[i].hmLadleMn + '@'
         				+ checked_rows[i].hmLadleS + '@'
         				+ checked_rows[i].hmLadleP + '@'
         				+ checked_rows[i].hmLadleSi + '@'     						
         				+ checked_rows[i].hmLadleTi + '@'
         				+ checked_rows[i].hmLadleTempBf + '@'
         				+ formatDate(checked_rows[i].hmLadleProdDt) + '@'
         				+ checked_rows[i].hmAvlblWt + '@'
         				+ checked_rows[i].hmLadleNo + '@'
         				+ checked_rows[i].sourceOfHm + '@'
         				+ checked_rows[i].remarks+ '@'
         				+ checked_rows[i].hmSeqNo + 'SIDS';
         			}
         		}
         		formData = {
             			"hm_arry" : hm_arry
             		};
                    	$.ajax({
                    		headers: { 
                    		'Accept': 'application/json',
                    		'Content-Type': 'application/json' 
                    		},
                    		type: 'POST',
                    		data: JSON.stringify(formData),
                    		dataType: "json",
                    		url: './HMReceive/SaveHMDataArray',
                    		success: function(data) {
                    		    if(data.status == 'SUCCESS') 
                    		    	{
                    		    	$.messager.progress('close');
                    		    	$.messager.alert('Saved HM Data in SMS',data.comment,'info');
                    		    	
                    		    	dialogBoxClose('t1_get_hm_det_jpod_div_id');
                    		    	refreshT1HmReceive();
                    		    	}else {
                    		    		$.messager.progress('close');
                    		    		$.messager.alert('HM Data Recevied in SMS Info',data.comment,'info');
                    		    	}
                    		  },
                    		error:function(){      
                    			$.messager.alert('Processing','Error while Processing Ajax...','error');
                    		  }
                    		})
          }else{
        	  $.messager.alert('HM Data Recevied in SMS Info','No Records Selected','info');
          }
      	
        		 
            
         }
    	 
    	 function getgrossWeight(){
    		 var laddleNo =$("#t1_hmLadleNo").combobox("getText");
    		 //var castNo=$("#t1_castNo").textbox("getValue");
    		 var castNo = "";
    		 var unit=$("#t1_hmSource").textbox("getText");
    		 $("#t1_hmLadleGrossWt").textbox("setValue",'')
    		 weighRecvDirectory(laddleNo,castNo,unit,'GROSS_WEIGHT',function(data){
    			 $("#t1_hmLadleGrossWt").textbox("setValue",data.weight)
    		 })
    	 }
    	 function getProcessHeatgrossWeight(){
    		 var row = $('#t1_hm_recv_tbl_id').datagrid('getSelected');
    		 $("#t1_hmMeasuredWeight").textbox("setValue","")
    		 
    		 var laddleNo =row.hmLadleNo;
    		 //var castNo=row.castNo;
    		 var castNo="";
    		 var unit=row.lookupHmSource.lookup_value;
    		 weighRecvDirectory(laddleNo,castNo,unit,'BEFORE_POURING',function(data){
    			 $("#t1_hmMeasuredWeight").textbox("setValue",data.weight)
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
             		    //
             		  },
             		error:function(){     
             			
             			$.messager.alert('Processing','Data not available for selected criteria','warning');
             		  }
             		})
        		 }else{
        			 $.messager.alert('Processing','Select Required Fields','warning');
        		 }
    		 
    	 }

    	 function deleteT1HmReceive(){
    		 $.messager.confirm('Confirm', 'Do you want to delete the selected ladle ?', function(r){
    				if (r){	 
    		 var row = $('#t1_hm_recv_tbl_id').datagrid('getSelected');
    		 if (row){
    			 $.ajax({
            		headers: { 
            		'Accept': 'application/json',
            		'Content-Type': 'application/json' 
            		},
            		type: 'POST',
            		//data: JSON.stringify(formData),
            		dataType: "json",
            		url: './HMReceive/hotMetalReceiveStatusUpdate?hmRecvId='+row.hmRecvId,
            		success: function(data) {
            		    if(data.status == 'SUCCESS') 
            		    	{
            		    	$.messager.alert('Hot Metal Receive Info',data.comment,'info');
            		    	cancelT1HMReceive();
            		    	
            		    	}else {
            		    		$.messager.alert('Hot Metal Receive Info',data.comment,'info');
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

    </script>
       <style type="text/css">
        #t1_hm_receive_form_id{
            margin:0;
            padding:10px 30px;
        }       
    </style>