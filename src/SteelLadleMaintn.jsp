<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<div  style="padding-top: 10px;padding-left: 10px;padding-right: 10px;">
 <table id="t20_steel_ladle_det_tbl_id" toolbar="#t20_steel_ladle_det_tbl_toolbar_div_id"   title="<spring:message code="label.t20.ladlehisthdr"/>" class="easyui-datagrid" style="width:100%;height: 500px;"
            url="./SteelLadleStatus/getSteelLadleStatusDetails" method="get" iconCls='icon-ok'
             pagination="true" maximizable="true"  resizable="true" remoteSort="false" pageSize="20"
            rownumbers="true" fitColumns="true" singleSelect="true">
        <thead>
            <tr>
                <th field="steel_ladle_si_no" sortable="true" hidden="true" width="40"><b><spring:message code="label.m26.steelLadleMstrId"/></b></th>
                <th field="steel_ladle_no" sortable="true" width="80"><b><spring:message code="label.m26.steelLadleMstrLadleNo"/></b></th>
                <th field="steel_ladle_status" sortable="true" width="120"><b><spring:message code="label.m26.steelLadleMstrLadleStatus"/></b></th>
                <th field="steel_ladle_condition" sortable="true" width="120" formatter="(function(v,r,i){return formatColumnData('lkpMstrMdl.lookup_value',v,r,i);})"><b><spring:message code="label.m26.steelLadleMstrLadleCondition"/></b></th>
                <th field="recordStatus"  sortable="true" formatter="(function(v,r,i){return formatColumnStatus('recordStatus',v,r,i);})" width="40"><b><spring:message code="label.m26.steelLadleMstrStatus"/></b></th>  
            </tr>
        </thead>
    </table>

    <div id="t20_steel_ladle_det_tbl_toolbar_div_id" style="padding-top: 5px;">
    <%@ page import="java.util.*" %>
    
 <%--Display Buttons  --%>
   
   	<%
    request.setAttribute("scrn_id", Integer.parseInt(request.getParameter("scrn_id")));
    %>
    
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
   
    <%--    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:100px" iconCls="icon-add" plain="false" onclick="addA1MstrRole()"><spring:message code="label.a1.roledetailsbtnadd"/></a>
       
        <a href="javascript:void(0)" class="easyui-linkbutton" style="width:100px" iconCls="icon-edit" plain="false" onclick="editM1MstrLookup()"><spring:message code="label.a1.roledetailsbtnedit"/></a>
 --%>
    </div>
    </div>

<div id="t20_steel_ladle_det_form_div_id" class="easyui-dialog" style="width:430px;height:auto;padding:10px 10px"
            closed="true" buttons="#t20_mstr_steel_ladle_form_btn_div_id">
        <div class="ftitle"><spring:message code="label.m26.steelLadleMstrHeader"/></div>       
        <form id="t20_steel_ladle_det_form_id" method="post" novalidate>
          <input name="steel_ladle_si_no" type="hidden"  id="t20_steel_ladle_si_no" value=null>
         <div class="fitem">
              <label><spring:message code="label.m26.steelLadleMstrLadleNo"/></label>
                <input name="steel_ladle_no"  type="text" id="t20_steel_ladle_no"  class="easyui-combobox" data-options="required:true,panelHeight: 'auto',editable:false,selected:true,
                  valueField:'keyval',                    
                    textField:'txtvalue'">
            </div>
        
            
           <div class="fitem" > 
              <label><spring:message code="label.m26.steelLadleMstrLadleStatus"/></label>
                 <input name="steel_ladle_status"  type="text" id="t20_steel_ladle_status"  class="easyui-combobox" data-options="required:true,readonly:true,panelHeight: 'auto',editable:false,selected:true, 
                   valueField:'keyval',                     
                    textField:'txtvalue'"> 
            </div> 
            
            <div class="fitem">
              <label><spring:message code="label.m26.steelLadleMstrLadleCondition"/></label>
                <input name="steel_ladle_condition"  type="text" id="t20_steel_ladle_condition"  class="easyui-combobox" data-options="required:true,readonly:true,panelHeight: 'auto',editable:false,selected:true,
                  valueField:'keyval',                    
                    textField:'txtvalue'">
            </div>
            
            <div class="fitem" id="t20_repairdate_div_id" > 
        	<label ><spring:message code="label.t20.repairdate"/></label>
         	<input name="repairdate"  type="text" id="t20_repairDate"  class="easyui-datetimebox" data-options="required:true,showSeconds:false">
        	</div>
          
             <div class="fitem" id="t20_cirndate_div_id" > 
        	<label><spring:message code="label.t20.incircundate"/></label>
         	<input name="cirndate"  type="text" id="t20_cirndate"  class="easyui-datetimebox" data-options="required:true,showSeconds:false">
        	</div>
        </form>
    </div>
    
    <div id="t20_mstr_steel_ladle_form_btn_div_id">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="savet20SteelLadle()" style="width:90px">Save</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="cancelt20SteelLadle()" style="width:90px">Cancel</a>
    </div>


 <script type="text/javascript">
    
 $(window).load(setTimeout(applyT20Filter,1));  //1000 ms = 1 second.
    
    function applyT20Filter()
    {
    	
		 $('#t20_steel_ladle_det_tbl_id').datagrid('enableFilter');
		
    }
 

    function callt20Dropdowns()
    {
    	//var url1="./CommonPool/getComboList?col1=lookup_code&col2=lookup_value&classname=LookupMasterModel&status=1&wherecol=lookup_type='RECORD_STATUS' and lookup_status=";
    	var url2="./CommonPool/getComboList?col1=lookup_id&col2=lookup_value&classname=LookupMasterModel&status=1&wherecol=lookup_type='STEEL_LADLE_CONDITION' and lookup_status=";
    	var url3="./CommonPool/getComboList?col1=lookup_code&col2=lookup_value&classname=LookupMasterModel&status=1&wherecol=lookup_type='STEEL_LADLE_STATUS' and lookup_status=";
    	//getDropdownList(url1,'#t20_recordStatus');
    	getDropdownList(url2,'#t20_steel_ladle_condition');
    	getDropdownList(url3,'#t20_steel_ladle_status');
    	
    	  var url1="./CommonPool/getComboList?col1=steel_ladle_si_no&col2=steel_ladle_no&classname=SteelLadleMasterModel&status=1&" +
  	    "wherecol=lkpMstrMdl.lookup_type='STEEL_LADLE_CONDITION' and recordStatus=";
  	getDropdownList(url1,'#t20_steel_ladle_no');
          
    }
    function callt20EditDropdowns()
    {
    	var url2="./CommonPool/getComboList?col1=lookup_id&col2=lookup_value&classname=LookupMasterModel&status=1&wherecol=lookup_type='STEEL_LADLE_CONDITION' and lookup_status=";
    	var url3="./CommonPool/getComboList?col1=lookup_code&col2=lookup_value&classname=LookupMasterModel&status=1&wherecol=lookup_type='STEEL_LADLE_STATUS' and lookup_status=";
    	getDropdownList(url2,'#t20_steel_ladle_condition');
    	getDropdownList(url3,'#t20_steel_ladle_status');
    	var data=new Array();
    	$('#t20_steel_ladle_no').combobox('loadData', data);
    }
    	
    
       function refresht20SteelLadle()
        {
        	
         	$.ajax({
         		headers: { 
         		'Accept': 'application/json',
         		'Content-Type': 'application/json' 
         		},
         		type: 'GET',
         		//data: JSON.stringify(formData),
         		dataType: "json",
         		url: './SteelLadleStatus/getSteelLadleStatusDetails',
         		success: function(data) {
         			 $('#t20_steel_ladle_det_tbl_id').datagrid('loadData', data); 
         		  },
         		error:function(){      
         			$.messager.alert('Processing','Error while Processing Ajax...','error');
         		  }
         		});
      
        }
      
        function addT20StLadleStatus(){
        	callt20Dropdowns();
        	$("#t20_repairdate_div_id").show();
        	$("#t20_cirndate_div_id").hide();
        	
        	$('#t20_steel_ladle_det_form_div_id').dialog({modal:true,cache: true});
            $('#t20_steel_ladle_det_form_div_id').dialog('open').dialog('center').dialog('setTitle','New Steel Ladle Entry');
            $('#t20_steel_ladle_det_form_id').form('clear');
            document.getElementById('t20_steel_ladle_si_no').value='0';
            $('#t20_recordStatus').combobox('setText',"Active");
            $('#t20_recordStatus').combobox('setValue',"1");
     		$('#t20_steel_ladle_status').combobox({
     			 onLoadSuccess: function(){
               		setDefaultCustomComboValues('t20_steel_ladle_status','NOT_AVAILABLE',$('#t20_steel_ladle_status').combobox('getData'));
               		}
               	}); 
            $('#t20_steel_ladle_condition').combobox({
              	 onLoadSuccess: function(){
              		setDefaultCustomComboValues('t20_steel_ladle_condition','IN_REPAIR',$('#t20_steel_ladle_condition').combobox('getData'));
              		}
              	}); 
        } 
        
        function validatet20SteelLadleform(){
            return $('#t20_steel_ladle_det_form_div_id').form('validate');
        }
        
        
        function cancelt20SteelLadle()
        {
        	$('#t20_steel_ladle_det_form_div_id').dialog('close');
        	refresht20SteelLadle();
        }
        
        
        function savet20SteelLadle()
        {
        	if(validatet20SteelLadleform()){
        	var ladle_si_no = $('#t20_steel_ladle_no').textbox('getValue');;
        	var ladle_no =$('#t20_steel_ladle_no').textbox('getText');
       		var ladle_status=$('#t20_steel_ladle_status').combobox('getValue');
       		var ladle_condition=$('#t20_steel_ladle_condition').combobox('getValue');
       		var ladle_conditionVal=$('#t20_steel_ladle_condition').combobox('getText');
       	
       		if(ladle_conditionVal == 'IN_REPAIR'){
       			var repair_date =$('#t20_repairDate').datebox('getValue');
       		
       			changed_date=repair_date;
       		}else{
       			var cirn_date =$('#t20_cirndate').datebox('getValue');
       			
       			changed_date=cirn_date;
       		}
     		
           var formData = {"steel_ladle_si_no":ladle_si_no,"steel_ladle_no":ladle_no,
        		   	"steel_ladle_status":ladle_status,"steel_ladle_condition":ladle_condition};

           	$.ajax({
           		headers: { 
           		'Accept': 'application/json',
           		'Content-Type': 'application/json' 
           		},
           		type: 'POST',
           		data: JSON.stringify(formData),
           		dataType: "json",
           		url: './laddleMaintainance/saveOrUpdate?changed_date='+changed_date,
           		success: function(data) {
           		    if(data.status == 'SUCCESS') 
           		    	{
           		    	$.messager.alert('Steel Ladle Status Details Info',data.comment,'info');
           		    	cancelt20SteelLadle();
           		    	
           		    	}else {
           		    		$.messager.alert('Steel Ladle Status Details Info',data.comment,'info');
           		    	}
           		  },
           		error:function(){      
           			$.messager.alert('Processing','Error while Processing Ajax...','error');
           		  }
           		});
        }
        }
           
        

        function editT20StLaldeStatus(){
        	$("#t20_repairdate_div_id").hide();
        	$("#t20_cirndate_div_id").show();
            var row = $('#t20_steel_ladle_det_tbl_id').datagrid('getSelected');
            if (row){
            	if(row.steel_ladle_status=='NOT_AVAILABLE'){
            		callt20EditDropdowns();
            	$('#t20_steel_ladle_det_form_id').form('clear');
            	$('#t20_steel_ladle_det_form_div_id').dialog({modal:true,cache: true});
                $('#t20_steel_ladle_det_form_div_id').dialog('open').dialog('center').dialog('setTitle','Change Ladle Status');
               // $('#t20_steel_ladle_det_form_id').form('load',row);
               
                document.getElementById('t20_steel_ladle_si_no').value = row.steel_ladle_si_no;
                $('#t20_steel_ladle_no').combobox('setValue',row.steel_ladle_si_no);
                $('#t20_steel_ladle_no').combobox('setText',row.steel_ladle_no);
                
//             	$('#t20_steel_ladle_status').combobox('setValue','AVAILABLE');
//             	$('#t20_steel_ladle_condition').combobox('setValue','CIRCULATION');
            	 $('#t20_steel_ladle_status').combobox({
                  	 onLoadSuccess: function(){
                  		setDefaultCustomComboValues('t20_steel_ladle_status','AVAILABLE',$('#t20_steel_ladle_status').combobox('getData'));
                  		}
                  	});  
            	  $('#t20_steel_ladle_condition').combobox({
                  	 onLoadSuccess: function(){
                  		setDefaultCustomComboValues('t20_steel_ladle_condition','CIRCULATION',$('#t20_steel_ladle_condition').combobox('getData'));
                  		}
                  	});  
            	}else{
            		$.messager.alert('Information','Select Ladle whose Condition is IN_REPAIR...!','info');
            	}
            }else{
            	$.messager.alert('Information','Please Select Record...!','info');
            }
            
         
        }
        </script>

        <style type="text/css">
        #t20_steel_ladle_det_form_id{
            margin:0;
            padding:10px 30px;
        }
        
    </style>