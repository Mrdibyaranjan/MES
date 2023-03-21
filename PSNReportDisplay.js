/**
 * PSN Report Script
 */
/// PSN Report Display for New PSNs wating for Review

  function printPsn(psn_rep_scrn_type)
  {
	 //var psn_no=document.getElementById('m35_rep_psn_hdr_sl_no').value;
	  var psn_no=0;
	  if(psn_rep_scrn_type=="r1")
		 {
		  psn_no=$('#'+psn_rep_scrn_type+'_psn_no').combobox('getValue');
		 }
	 else{
		  psn_no=document.getElementById(psn_rep_scrn_type+'_psn_no').value;
	 }	 
	  window.open("./psnReport/printPsnReport?"+psn_no,'CHARTVIEW','left=40,top=20,width=1100,height=550,toolbar=0,resizable=1');
  }
  
  function formatDateWithoutTime(d){ 
		if(d!=null && d!==''){
		var date=new Date(d);
		 	    return addZero(date.getDate())+"/"+ addZero(date.getMonth()+1) + "/" + date.getFullYear();
		}else{
			return '';
		}
	}
  
  function exportPsnDoc(psn_rep_scrn_type)
  {
	 var psn_no=0;
	 if(psn_rep_scrn_type=="r1")
		 {
		  psn_no=$('#'+psn_rep_scrn_type+'_psn_no').combobox('getValue');
		 }
	 else{
		  psn_no=document.getElementById(psn_rep_scrn_type+'_psn_no').value;
	 }	 
	 var urldata=psn_no+"&"+"Word";
      window.open("./psnReport/exportPsnReport?"+urldata,'CHARTVIEW','left=40,top=20,width=1100,height=550,toolbar=0,resizable=1');
  }
  
  function reportDisplay(psn_rep_scrn_type,imageUrl){
	  $('#'+psn_rep_scrn_type+'_psn_report_id').panel('open');
	  $('#'+psn_rep_scrn_type+'_psn_report_id').empty();
	  $('#'+psn_rep_scrn_type+'_print').linkbutton('enable');
	  $('#'+psn_rep_scrn_type+'_export').linkbutton('enable');
	  
	  var sinum="12";
	  var psn_no=0;
	  	if(psn_rep_scrn_type=="r1")
		 {
	  		 psn_no=$('#'+psn_rep_scrn_type+'_psn_no').combobox('getValue');
		 }
		 else{
			  psn_no=document.getElementById(psn_rep_scrn_type+'_psn_no').value;
		 }	 
	  $.ajax({
   		headers: { 
   		'Accept': 'application/json',
   		'Content-Type': 'application/json' 
   		},
   		type: 'GET',
   		dataType: "json",
   		url: './psnReport/getPsnReport?psn_no='+psn_no,
   		success: function(data) {
   		clearAllDivs(psn_rep_scrn_type);
   		var table = $("<table width='100%' bordercolor='white' bgcolor='white' align='center' id="+psn_rep_scrn_type+"_psnDocTable cellpadding='0' cellspacing='0'></table>").appendTo("#"+psn_rep_scrn_type+"_psn_report_id");
   		var cmnln = $("<tr></tr>").appendTo(table);
   		var cmntmp = $("<td colspan='2'></td>").appendTo(cmnln);
   		var pageNo="<span id='pge_no'>SHEET 01 OF 01</span>";
   		var cmntbl = $("<table width='100%'  id="+psn_rep_scrn_type+"_psncmntbl cellpadding='0' border=1 cellspacing='0'></table>").appendTo(cmntmp);
   		$("<tr height='40' border=1 > <td width='20%' rowspan='2' align='center'><b></td> <td colspan='3' rowspan='1' width='60%' align='center'><b><div style='text-transform: uppercase' id='"+psn_rep_scrn_type+"_PLANTLOCATION'></div></b></td> <td width='20%' align='center'> <div style='border-bottom: solid 2px #808080; padding-bottom: 3px;' id='"+psn_rep_scrn_type+"_PSNFNO'></div><div id='"+psn_rep_scrn_type+"_PSNNO'></div></td> </tr>").appendTo(cmntbl);
   		//$("<tr height='22'><td width='20%' rowspan='2' align='center'></td> <td colspan='2' width='60%' align='center'>MONNET ISPAT AND ENERGY LTD.</td> <td width='20%' align='center'> <div id='"+psn_rep_scrn_type+"_PSNNO'></div></td> </tr>").appendTo(cmntbl);
   		$("<tr height='40'><td colspan='3' width='60%' align='center'><b>(Formerly known as Monnet Ispat & Energy Limited)</b></td><td width='20%' align='center'>"+pageNo+"</div></td> </tr>").appendTo(cmntbl);
   		//$("<tr height='40' border=1 > <td width='20%' align='center'>"+pageNo+"</div></td> </tr>").appendTo(cmntbl);
   		$("<tr border=1 > <td width='20%' align='center'>ISSUE NO: <div style='display: inline;' id='"+psn_rep_scrn_type+"_ISSUE_NO'></div></td> <td width='30%' colspan='2' align='center'>ISSUE DATE: <div style='display: inline;' id='"+psn_rep_scrn_type+"_ISSUE_DATE'></div></td> <td width='30%' align='center'>REVISION NO: <div style='display: inline;' id='"+psn_rep_scrn_type+"_REVN_NO'></div> </td> <td width='20%' align='center'>REVISION DATE: <div style='display: inline;' id='"+psn_rep_scrn_type+"_REVN_DATE'></div></td> </tr>").appendTo(cmntbl);
   		$("<tr border=1 > <td colspan='5'> <div style='display: inline;font-size: medium;'> TITLE :</div><div style='display: inline;font-size: medium;' id='"+psn_rep_scrn_type+"_TITLE'><b>"+data.TITLE+"</b></div></td></tr>").appendTo(cmntbl);
   		$("<tr> <td colspan='5'> <div style='display: inline;font-size: small;'>1: Grade :</div> <div style='display: inline;font-size: small;' id='"+psn_rep_scrn_type+"_GRADE'>"+data.GRADE+"</div> </td> </tr>").appendTo(table);
   		/*$("<tr height='30'><td width='20%'><div style='display: inline;font-size: small;'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 2. Size :</div></td> <td align='left'> <div style='display: inline;font-size: small;' id='psn_cast_size'>"+data.CAST_SIZE+"</div> </td> </tr>").appendTo(table);   (Joint Venture Company by AION & JSW Steel Limited.)
   		$("<tr height='30'><td width='20%'><div style='display: inline;font-size: small;'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 3. Process Route :</div></td> <td align='left'> <div style='display: inline;font-size: small;' id='psn_process_route'>"+data.PROCESS_ROUTE+"</div> </td> </tr>").appendTo(table);
   		$("<tr height='30'><td width='20%'><div style='display: inline;font-size: small;'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 4. Product Form :</div></td> <td align='left'> <div style='display: inline;font-size: small;' id='psn_prod_form'>"+data.PROD_FORM+"</div> </td> </tr>").appendTo(table);
   		$("<tr height='30'><td width='20%'><div style='display: inline;font-size: small;'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 5. Customer Name :</div></td> <td align='left'> <div style='display: inline;font-size: small;' id='psn_cust_name'>"+data.CUST_NAME+"</div> </td> </tr>").appendTo(table);
   		$("<tr height='30'><td width='20%'><div style='display: inline;font-size: small;'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 6. Customer Reference :</div></td> <td align='left'> <div style='display: inline;font-size: small;' id='psn_cust_ref'>"+data.CUST_REF+"</div> </td> </tr>").appendTo(table);
   		$("<tr height='30'><td width='20%'><div style='display: inline;font-size: small;'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 7. End Application :</div></td> <td align='left'> <div style='display: inline;font-size: small;' id='psn_end_app'>"+data.END_APP+"</div> </td> </tr>").appendTo(table);
   		$("<tr height='30'><td width='20%'><div style='display: inline;font-size: small;'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 8. Hot Metal to be used :</div></td> <td align='left'> <div style='display: inline;font-size: small;' id='psn_hm_used'>"+data.HM_S_TOBE_USED+"</div> </td> </tr>").appendTo(table);
   		$("<tr height='30'><td width='20%'><div style='display: inline;font-size: small;'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 9. Melt Range :</div></td> <td align='left'> <div style='display: inline;font-size: small;' id='psn_melt_range'></div> </td> </tr>").appendTo(table);*/
        if (data.PROD_LI.length > 0) { 
	   		var prdln = $("<tr></tr>").appendTo(table);
	   		var prodtmp = $("<td colspan='5'></br> 2: Product Details : </td>").appendTo(prdln);
	   		var prodtbl = $("<table width='100%' id="+psn_rep_scrn_type+"_psnprdtbl border=1 cellpadding='0' cellspacing='0'></table>").appendTo(prodtmp);
	   		$("<tr height='10' align='center'><td>I/P Product</td><td>I/P Section</td><td>O/P Product</td><td>O/P Section</td><td>Reduction Ratio</td><td>Process Route</td></tr>").appendTo(prodtbl);
	   	 	var rowProd;
	   	 	$.each(data.PROD_LI, function (i, value) {
	   	 		//$("<tr height='10' align='left'><td width='20%' colspan='5'>2 Product Details</td></tr>").appendTo(prodtbl); //Product List
	   	 		rowProd = $("<tr height='10' align='center'>...</tr>").appendTo(prodtbl); 
	   	 			if (value.lkpIPProdMstrMdl.lookup_code != null) { $("<td></td>").text(value.lkpIPProdMstrMdl.lookup_code).appendTo(rowProd); }
	   					else { $("<td></td>").text("").appendTo(rowProd); }
	   				if (value.lkpIPSectMstrMdl.lookup_code != null) { $("<td></td>").text(value.lkpIPSectMstrMdl.lookup_code).appendTo(rowProd); }
	   					else { $("<td></td>").text("").appendTo(rowProd); }
	   				if (value.lkpOPProdMstrMdl.lookup_code != null) { $("<td width='20%'></td>").text(value.lkpOPProdMstrMdl.lookup_code).appendTo(rowProd); }
	   					else { $("<td width='20%'></td>").text("").appendTo(rowProd); }
	   				if (value.lkpOPSectMstrMdl.lookup_code != null) { $("<td width='20%'></td>").text(value.lkpOPSectMstrMdl.lookup_value).appendTo(rowProd); }
	   					else { $("<td width='20%'></td>").text("").appendTo(rowProd); }
	   				if (value.lkpRedRatMstrMdl.lookup_code != null) { $("<td width='20%'></td>").text(value.lkpRedRatMstrMdl.lookup_code).appendTo(rowProd); }
	   			  else { $("<td width='20%'></td>").text("").appendTo(rowProd); }
	   				if (value.lkpProcRoutMstrMdl.lookup_code != null) { $("<td width='20%'></td>").text(value.lkpProcRoutMstrMdl.lookup_value).appendTo(rowProd); }
	   					else { $("<td width='20%'></td>").text("").appendTo(rowProd); }
	   		})
   		}
   		
 		if (data.CUST_REF_LI.length > 0) {
	   		var custln = $("<tr align='left'> </tr>").appendTo(table);
	   	 	var custtmp = $("<td width='100%' colspan='5'></br> 3: Customer Details : </td>").appendTo(custln);
			var custtbl = $("<table width='100%' align='center' id="+psn_rep_scrn_type+"_psncusttbl border=1 cellpadding='0' cellspacing='0'></table>").appendTo(custtmp);
			$("<tr height='10' align='center'><td width='25%'>Customer Name</td><td width='50%'>Customer Ref</td><td width='25%'>End Application</td></tr>").appendTo(custtbl);
	 		var rowCust;
	 		$.each(data.CUST_REF_LI, function (i, value) {
				rowCust = $("<tr height='10' align='center'>...</tr>").appendTo(custtbl); 
	 			if (value.customerMstr.customer_name != null) { $("<td width='25%'></td>").text(value.customerMstr.customer_name).appendTo(rowCust); }
					else { $("<td width='25%'></td>").text("").appendTo(rowCust); }
				if (value.cust_psn_ref != null) { $("<td align='left' width='50%'></td>").text(value.cust_psn_ref).appendTo(rowCust); }
					else { $("<td align='left' width='50%'></td>").text("").appendTo(rowCust); }
				if (value.end_application != null) { $("<td width='25%'></td>").text(value.end_application).appendTo(rowCust); }
					else { $("<td width='25%'></td>").text("").appendTo(rowCust); }
			})
 		}
   		
   		$("<tr> <td colspan='5'></br> 4 Hot Metal to be Used :<div style='display: inline;font-size: small;' id='hms_tobe_used'>"+data.HM_S_TOBE_USED+"</div> </td> </tr>").appendTo(table);
   		
 		$("<tr> <td colspan='5'><div style='display: inline;font-size: medium;' id='ChemCompo'></div> </td> </tr>").appendTo(table);
 		 
 		var chemln = $("<tr> </tr>").appendTo(table);
   	 	var chemtmp = $("<td width='100%' colspan='5'></br></br>5: chemistry composition</td>").appendTo(chemln);
   	
   	 	var chemtbl = $("<table width='100%' border='1' id="+psn_rep_scrn_type+"_psnchemtbl cellpadding='0' cellspacing='0'></table>").appendTo(chemtmp);
   	  /*  var row7=  $("<tr height='20' border=1 > <td width='20%' rowspan='1' align='center'><b></td></tr>" ).appendTo(chemtbl);*/
   	 
   	 	var row1 = $("<tr><th colspan='1' height='20' align='center'></th><td></td></tr>").appendTo(chemtbl);
   	    var row5 = $("<tr> <th rowspan='2'height='20' align='center'>Customer Spec</th><td align='center'>Min</td></tr>").appendTo(chemtbl);
		var row6 = $("<tr height='20' align='center'><td>Max</td></tr>").appendTo(chemtbl);
		var row2 = $("<tr> <th rowspan='3' height='20' align='center'>Internal Spec</th><td align='center'>Min</td></tr>").appendTo(chemtbl);
		var row3 = $("<tr height='20' align='center'><td>Max</td></tr>").appendTo(chemtbl);
		var row4 = $("<tr height='20' align='center'><td>Aim</td></tr>").appendTo(chemtbl);
		
		
		if (data.CHEMISTRY_LI.length > 0) {
	 		$.each(data.CHEMISTRY_LI, function (i, value) {
	 			$("<td></td>").text(value.elementLkpMstrModel.lookup_code+"("+value.uomLkpMstrModel.lookup_value+")").appendTo(row1);
	 			if(value.value_min != null){
	 				if(value.remarks=="*"){
	 	            $("<td align='center'></td>").text(value.remarks+""+value.value_min+"").appendTo(row2);
	 	           $("<tr> <td colspan='5'></div></br><div style='display: inline;font-size: small;' id='"+psn_rep_scrn_type+"_PSNCUSTOMERREQUIREMENT'></div> </td> </tr>").appendTo(table);}
	 		        else 
	 				$("<td align='center'></td>").text(value.value_min).appendTo(row2); 
	 			}
	 			else $("<td></td>").text("").appendTo(row2);
	 			if(value.value_max != null){
	 				if(value.remarks=="*")
	 	            $("<td></td>").text(value.remarks+""+value.value_max+"").appendTo(row3);
	 		        else 
	 				$("<td></td>").text(value.value_max).appendTo(row3); 
	 			}
	 			else $("<td></td>").text("").appendTo(row3);
	 			if(value.value_aim != null){
	 				if(value.remarks=="<" || value.remarks==">" )
	 	            $("<td></td>").text(value.remarks+""+value.value_aim+"").appendTo(row4);
	 		        else 
	 				$("<td></td>").text(value.value_aim).appendTo(row4); 
	 			}
	 			else $("<td></td>").text("").appendTo(row4);
	 			
	 			if(value.cust_limit_vlaue_min != null){
	 				
	 				$("<td align='center'></td>").text(value.cust_limit_vlaue_min).appendTo(row5); 
	 			}
	 			else $("<td align='center'></td>").text("-").appendTo(row5);
	 			if(value.cust_limit_vlaue_max != null){
	 				
	 				$("<td></td>").text(value.cust_limit_vlaue_max).appendTo(row6); 
	 			}
	 			else $("<td></td>").text("-").appendTo(row6);
	 	    })
		}
		
		
/*		$("<tr> <td colspan='5'></br> * Customer Limit :<div style='display: inline;font-size: small;' id='customer_limit'>"+data.CUSTOMER_LIMIT+"</div> </td> </tr>").appendTo(table);*/
		
		if (data.TRACE_LI.length > 0) {
			var traceln = $("<tr align='left'> </tr>").appendTo(table);
	   	 	var tracetbl = $("<td width='100%' colspan='5'></br> 6: Traces (Max) : </td>").appendTo(traceln);
	 		var rowTrace;
	 		$.each(data.TRACE_LI, function (i, value) {
 				if (i == data.TRACE_LI.length-1) { tracetbl.append(value.elementLkpMstrModel.lookup_code+"("+value.uomLkpMstrModel.lookup_value+")"+"-"+ value.value_max); }
				else { tracetbl.append(value.elementLkpMstrModel.lookup_code+"("+value.uomLkpMstrModel.lookup_value+")"+"-"+ value.value_max+", "); }
		})
   		} else {
   			var traceln = $("<tr align='left'> </tr>").appendTo(table);
	   	 	var tracetbl = $("<td width='100%' colspan='5'></br> 6: Traces (Max) : No Trace Elements </td>").appendTo(traceln);
   		}
		//tracetbl.append("</br>*External");
		
		if (data.EOF_G_LI.length > 0) {
	 		var eofln = $("<tr align='left'> </tr>").appendTo(table);
	 		var eoftmp1 = $("<td align='left'></br>7: EAF : </td><td align='left'></td>").appendTo(eofln);
	 		var eofln2 = $("<tr align='left'><td align='left' width='20%' style='vertical-align:top'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; General Requirements : </td></tr>").appendTo(table);
	 		var eoftmp2 = $("<td align='left'></td>").appendTo(eofln2);
	   	 	var eoftbl = $("<table width='100%' align='left' id='"+psn_rep_scrn_type+"_psneoftbl' cellpadding='0' cellspacing='0'></table>").appendTo(eoftmp2);
			$.each(data.EOF_G_LI, function (i, value) {
	 		 	var rowEof = $("<tr align='center'>...</tr>").appendTo(eoftbl);
	 			if (value.qa_char_value != null) { 
	 				var uom = '';
	 				if( value.uomLkpMstrModel != null)
	 					
	 				uom = '('+value.uomLkpMstrModel.lookup_value+')';
	 					
	 				$("<td width='20%' align='left'></td>").text(value.psnQualityMstrModel.qa_char_desc).appendTo(rowEof); 
	 											   $("<td width='80%' align='left'></td>").text(' : '+value.qa_char_value+uom).appendTo(rowEof); }
					else { $("<td></td>").text("").appendTo(rowEof); }
			})
   		}
 		
		if (data.LRF_G_LI.length > 0) {
			var lrfln = $("<tr align='left'> </tr>").appendTo(table);
	 		var lrftmp1 = $("<td align='left'></br>8: LRF : </td><td align='left'></td>").appendTo(lrfln);
	 		var lrfln2 = $("<tr align='left'><td align='left' width='20%' style='vertical-align:top'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; General Requirements : </td></tr>").appendTo(table);
	 		var lrftmp2 = $("<td align='left'></td>").appendTo(lrfln2);
	   	 	var lrftbl = $("<table width='100%' align='left' id='"+psn_rep_scrn_type+"_psnlrftbl' cellpadding='0' cellspacing='0'></table>").appendTo(lrftmp2);
			$.each(data.LRF_G_LI, function (i, value) {
	 		 	var rowLrf = $("<tr align='center'>...</tr>").appendTo(lrftbl);
	 			if (value.qa_char_value != null) {
	 				var uom = '';
	 				if( value.uomLkpMstrModel != null)
	 					
	 				uom = '('+value.uomLkpMstrModel.lookup_value+')';
	 				$("<td width='20%' align='left'></td>").text(value.psnQualityMstrModel.qa_char_desc).appendTo(rowLrf); 
	 											   $("<td width='80%' align='left'></td>").text(' : '+value.qa_char_value+ uom).appendTo(rowLrf); }
					else { $("<td></td>").text("").appendTo(rowLrf); }
			})
		}
		
		if (data.VD_G_LI.length > 0) {
			var vdln = $("<tr align='left'> </tr>").appendTo(table);
	 		var vdtmp1 = $("<td align='left'></br> 9: VD : </td><td align='left'></td>").appendTo(vdln);
	 		var vdln2 = $("<tr align='left'><td align='left' width='20%' style='vertical-align:top'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; General Requirements : </td></tr>").appendTo(table);
	 		var vdtmp2 = $("<td align='left'></td>").appendTo(vdln2);
	   	 	var vdtbl = $("<table width='100%' align='left' id='"+psn_rep_scrn_type+"_psnvdtbl' cellpadding='0' cellspacing='0'></table>").appendTo(vdtmp2);
			$.each(data.VD_G_LI, function (i, value) {
	 		 	var rowVd = $("<tr align='center'>...</tr>").appendTo(vdtbl);
	 			if (value.qa_char_value != null) {
	 				var uom = '';
	 				if( value.uomLkpMstrModel != null)
	 					
	 				uom = '('+value.uomLkpMstrModel.lookup_value+')';
	 				$("<td width='20%' align='left'></td>").text(value.psnQualityMstrModel.qa_char_desc).appendTo(rowVd); 
	 											   $("<td width='80%' align='left'></td>").text(' : '+value.qa_char_value+uom).appendTo(rowVd); }
					else { $("<td></td>").text("").appendTo(rowVd); }
			})
   		}
		
		/*var vdln = $("<tr align='left'> </tr>").appendTo(table);
   	 	var vdhdr = $("<td width='20%' align='left' style='vertical-align:top'> 9 VD : </br> &nbsp;&nbsp;&nbsp; General Requirements : </td>").appendTo(vdln);
	 	var vdtbl = $("<td width='100%' colspan='4'> </td>").appendTo(vdln);
   	 	vdtbl.append("</br>");
 		$.each(data.VD_G_LI, function (i, value) {
 			var rowVd = $("<tr height='10' align='center'>...</tr>").appendTo(vdtbl);
 			if (value.qa_char_value != null) { $("<td width='20%'></td>").text(value.psnQualityMstrModel.qa_char_desc).appendTo(rowVd);
 												$("<td width='20%'></td>").text(value.qa_char_value).appendTo(rowVd); }
				else { $("<td width='20%'></td>").text("").appendTo(rowVd); }
		})*/
		
		if (data.CCM_G_LI.length > 0) {
		    var ccmln = $("<tr align='left'> </tr>").appendTo(table);
	 		var ccmtmp1 = $("<td align='left'></br>10: CCM : </td><td align='left'></td>").appendTo(ccmln);
	 		var ccmln2 = $("<tr align='left'><td align='left' width='20%' style='vertical-align:top'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; General Requirements : </td></tr>").appendTo(table);
	 		var ccmtmp2 = $("<td align='left'></td>").appendTo(ccmln2);
	   	 	var ccmtbl = $("<table width='100%' align='left' id='"+psn_rep_scrn_type+"_psnccmgtbl' cellpadding='0' cellspacing='0'></table>").appendTo(ccmtmp2);
			$.each(data.CCM_G_LI, function (i, value) {
	 		 	var rowCcm = $("<tr align='center'>...</tr>").appendTo(ccmtbl);
	 			if (value.qa_char_value != null) { 
	 				var uom = '';
	 				if( value.uomLkpMstrModel != null)
	 					
	 				uom = '('+value.uomLkpMstrModel.lookup_value+')';
	 				$("<td width='20%' align='left'></td>").text(value.psnQualityMstrModel.qa_char_desc).appendTo(rowCcm); 
	 					$("<td width='80%' align='left'></td>").text(' : '+value.qa_char_value+ uom).appendTo(rowCcm); }
					else { $("<td></td>").text("").appendTo(rowCcm); }
			})
		}
		if (data.CCM_RM_LI.length > 0) {
			var ccmln = $("<tr align='left'> </tr>").appendTo(table);
			if (data.CCM_RM_LI.length > 0) {
				var ccmtmp1 = $("<td align='left'></br></td><td align='left'></td>").appendTo(ccmln);
			} else {
				var ccmtmp1 = $("<td align='left'></br>10: CCM : </td><td align='left'></td>").appendTo(ccmln);
			}
	 		var ccmln2 = $("<tr align='left'><td align='left' width='20%' style='vertical-align:top'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Rolling Mill Requirements : </td></tr>").appendTo(table);
	 		var ccmtmp2 = $("<td align='left'></td>").appendTo(ccmln2);
	   	 	var ccmtbl = $("<table width='100%' align='left' id='"+psn_rep_scrn_type+"_psnccmtsttbl' cellpadding='0' cellspacing='0'></table>").appendTo(ccmtmp2);
			$.each(data.CCM_RM_LI, function (i, value) {
	 		 	var rowCcm = $("<tr align='center'>...</tr>").appendTo(ccmtbl);
	 			if (value.qa_char_value != null) {
	 				var uom = '';
	 				if( value.uomLkpMstrModel != null)
	 					
	 				uom = '('+value.uomLkpMstrModel.lookup_value+')';
	 				$("<td width='20%' align='left'></td>").text(value.psnQualityMstrModel.qa_char_desc).appendTo(rowCcm); 
	 											   $("<td width='80%' align='left'></td>").text(' : '+value.qa_char_value+uom).appendTo(rowCcm); }
					else { $("<td></td>").text("").appendTo(rowCcm); }
			})
   		}
		
		if (data.CCM_TST_LI.length > 0) {
			var ccmln = $("<tr align='left'> </tr>").appendTo(table);
			if (data.CCM_G_LI.length > 0) {
				var ccmtmp1 = $("<td align='left'></br></td><td align='left'></td>").appendTo(ccmln);
			} else {
				var ccmtmp1 = $("<td align='left'></br>10: CCM : </td><td align='left'></td>").appendTo(ccmln);
			}
	 		var ccmln2 = $("<tr align='left'><td align='left' width='20%' style='vertical-align:top'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Testing Requirements : </td></tr>").appendTo(table);
	 		var ccmtmp2 = $("<td align='left'></td>").appendTo(ccmln2);
	   	 	var ccmtbl = $("<table width='100%' align='left' id='"+psn_rep_scrn_type+"_psnccmtsttbl' cellpadding='0' cellspacing='0'></table>").appendTo(ccmtmp2);
			$.each(data.CCM_TST_LI, function (i, value) {
	 		 	var rowCcm = $("<tr align='center'>...</tr>").appendTo(ccmtbl);
	 			if (value.qa_char_value != null) { 
	 				var uom = '';
	 				if( value.uomLkpMstrModel != null)
	 					
	 				uom = '('+value.uomLkpMstrModel.lookup_value+')';
	 				$("<td width='20%' align='left'></td>").text(value.psnQualityMstrModel.qa_char_desc).appendTo(rowCcm); 
	 											   $("<td width='80%' align='left'></td>").text(' : '+value.qa_char_value+uom).appendTo(rowCcm); }
					else { $("<td></td>").text("").appendTo(rowCcm); }
			})
   		}
		
		if (data.CCM_AS_CAST_REQ.length > 0) {
			var ccmln = $("<tr align='left'> </tr>").appendTo(table);
			if (data.CCM_G_LI.length > 0) {
				var ccmtmp1 = $("<td align='left'></br></td><td align='left'></td>").appendTo(ccmln);
			} else {
				var ccmtmp1 = $("<td align='left'></br>10: CCM : </td><td align='left'></td>").appendTo(ccmln);
			}
	 		var ccmln2 = $("<tr align='left'><td align='left' width='20%' style='vertical-align:top'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; As Cast Requirements : </td></tr>").appendTo(table);
	 		var ccmtmp2 = $("<td align='left'></td>").appendTo(ccmln2);
	   	 	var ccmtbl = $("<table width='100%' align='left' id='"+psn_rep_scrn_type+"_psnccmtsttbl' cellpadding='0' cellspacing='0'></table>").appendTo(ccmtmp2);
			$.each(data.CCM_AS_CAST_REQ, function (i, value) {
	 		 	var rowCcm = $("<tr align='center'>...</tr>").appendTo(ccmtbl);
	 			if (value.qa_char_value != null) { 
	 				var uom = '';
	 				if( value.uomLkpMstrModel != null)
	 					
	 				uom = '('+value.uomLkpMstrModel.lookup_value+')';
	 				$("<td width='20%' align='left'></td>").text(value.psnQualityMstrModel.qa_char_desc).appendTo(rowCcm); 
	 											   $("<td width='80%' align='left'></td>").text(' : '+value.qa_char_value+ uom ).appendTo(rowCcm); }
					else { $("<td></td>").text("").appendTo(rowCcm); }
			})
   		}

	    if (data.BLM_G_LI.length > 0) {
	   		var blmln = $("<tr align='left'> </tr>").appendTo(table);
	 		var blmtmp1 = $("<td align='left'></br>11: BLM : </td><td align='left'></td>").appendTo(blmln);
	 		var blmln2 = $("<tr align='left'><td align='left' width='20%' style='vertical-align:top'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; General Requirements : </td></tr>").appendTo(table);
	 		var blmtmp2 = $("<td align='left'></td>").appendTo(blmln2);
	   	 	var blmtbl = $("<table width='100%' align='left' id='"+psn_rep_scrn_type+"_psnblmgtbl' cellpadding='0' cellspacing='0'></table>").appendTo(blmtmp2);
			$.each(data.BLM_G_LI, function (i, value) {
	 		 	var rowBlm = $("<tr align='center'>...</tr>").appendTo(blmtbl);
	 			if (value.qa_char_value != null) { 
	 				var uom = '';
	 				if( value.uomLkpMstrModel != null)
	 					
	 				uom = '('+value.uomLkpMstrModel.lookup_value+')';
	 				$("<td width='20%' align='left'></td>").text(value.psnQualityMstrModel.qa_char_desc).appendTo(rowBlm); 
	 											   $("<td width='80%' align='left'></td>").text(' : '+value.qa_char_value+ uom).appendTo(rowBlm); }
					else { $("<td></td>").text("").appendTo(rowBlm); }
			})
		}
		
		if (data.BLM_RM_LI.length > 0) {
			var blmln = $("<tr align='left'> </tr>").appendTo(table);
			if (data.BLM_G_LI.length > 0) {
	 			var blmtmp1 = $("<td align='left'></br></td><td align='left'></td>").appendTo(blmln);
			} else {
				var blmtmp1 = $("<td align='left'></br>11: BLM : </td><td align='left'></td>").appendTo(blmln);
			}
	 		var blmln2 = $("<tr align='left'><td align='left' width='20%' style='vertical-align:top'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Rolling Mill Requirements : </td></tr>").appendTo(table);
	 		var blmtmp2 = $("<td align='left'></td>").appendTo(blmln2);
	   	 	var blmtbl = $("<table width='100%' align='left' id='"+psn_rep_scrn_type+"_psnblmrmtbl' cellpadding='0' cellspacing='0'></table>").appendTo(blmtmp2);
			$.each(data.BLM_RM_LI, function (i, value) {
	 		 	var rowBlm = $("<tr align='center'>...</tr>").appendTo(blmtbl);
	 			if (value.qa_char_value != null) {
	 				var uom = '';
	 				if( value.uomLkpMstrModel != null)
	 					
	 				uom = '('+value.uomLkpMstrModel.lookup_value+')';
	 				$("<td width='20%' align='left'></td>").text(value.psnQualityMstrModel.qa_char_desc).appendTo(rowBlm); 
	 											   $("<td width='80%' align='left'></td>").text(' : '+value.qa_char_value+ uom).appendTo(rowBlm); }
					else { $("<td></td>").text("").appendTo(rowBlm); }
			})
   		}
		
		if (data.BLM_TST_LI.length > 0) {
			var blmln = $("<tr align='left'> </tr>").appendTo(table);
			if (data.BLM_G_LI.length > 0 || data.BLM_RM_LI.length > 0) {
	 			var blmtmp1 = $("<td align='left'></br></td><td align='left' colspan='4'></td>").appendTo(blmln);
			} else {
				var blmtmp1 = $("<td align='left'></br>11: BLM : </td><td align='left'></td>").appendTo(blmln);
			}
	 		var blmln2 = $("<tr align='left'><td align='left' width='20%' style='vertical-align:top'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Testing Requirements : </td></tr>").appendTo(table);
	 		var blmtmp2 = $("<td align='left'></td>").appendTo(blmln2);
	   	 	var blmtbl = $("<table width='100%' align='left' id='"+psn_rep_scrn_type+"_psnblmtsttbl' cellpadding='0' cellspacing='0'></table>").appendTo(blmtmp2);
			$.each(data.BLM_TST_LI, function (i, value) {
	 		 	var rowBlm = $("<tr align='center'>...</tr>").appendTo(blmtbl);
	 			if (value.qa_char_value != null) {
	 				var uom = '';
	 				if( value.uomLkpMstrModel != null)
	 					
	 				uom = '('+value.uomLkpMstrModel.lookup_value+')';
	 				$("<td width='20%' align='left'></td>").text(value.psnQualityMstrModel.qa_char_desc).appendTo(rowBlm); 
	 											   $("<td width='80%' align='left'></td>").text(' : '+value.qa_char_value+uom).appendTo(rowBlm); }
					else { $("<td></td>").text("").appendTo(rowBlm); }
			})
		}
		
 		if (data.WRM_G_LI.length > 0) {
	   		var wrmln = $("<tr align='left'> </tr>").appendTo(table);
	 		var wrmtmp1 = $("<td align='left'></br>12: WRM : </td><td align='left'></td>").appendTo(wrmln);
	 		var wrmln2 = $("<tr align='left'><td align='left' width='20%' style='vertical-align:top'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; General Requirements : </td></tr>").appendTo(table);
	 		var wrmtmp2 = $("<td align='left'></td>").appendTo(wrmln2);
	   	 	var wrmtbl = $("<table width='100%' align='left' id='"+psn_rep_scrn_type+"_psnwrmgtbl' cellpadding='0' cellspacing='0'></table>").appendTo(wrmtmp2);
			$.each(data.WRM_G_LI, function (i, value) {
	 		 	var rowWrm = $("<tr align='center'>...</tr>").appendTo(wrmtbl);
	 			if (value.qa_char_value != null) {
	 				var uom = '';
	 				if( value.uomLkpMstrModel != null)
	 					
	 				uom = '('+value.uomLkpMstrModel.lookup_value+')';
	 				$("<td width='20%' align='left'></td>").text(value.psnQualityMstrModel.qa_char_desc).appendTo(rowWrm); 
	 											   $("<td width='80%' align='left'></td>").text(' : '+value.qa_char_value+ uom).appendTo(rowWrm); }
					else { $("<td></td>").text("").appendTo(rowWrm); }
			})
   		}
		
		if (data.WRM_RM_LI.length > 0) {
			var wrmln = $("<tr align='left'> </tr>").appendTo(table);
			if (data.WRM_G_LI.length > 0) {
				var wrmtmp1 = $("<td align='left'></br> </td><td align='left'></td>").appendTo(wrmln);
			} else {
				var wrmtmp1 = $("<td align='left'></br>12: WRM : </td><td align='left'></td>").appendTo(wrmln);
			}
	 		var wrmln2 = $("<tr align='left'><td align='left' width='20%' style='vertical-align:top'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Rolling Mill Requirements : </td></tr>").appendTo(table);
	 		var wrmtmp2 = $("<td align='left'></td>").appendTo(wrmln2);
	   	 	var wrmtbl = $("<table width='100%' align='left' id='"+psn_rep_scrn_type+"_psnwrmrmtbl' cellpadding='0' cellspacing='0'></table>").appendTo(wrmtmp2);
			$.each(data.WRM_RM_LI, function (i, value) {
	 		 	var rowWrm = $("<tr align='center'>...</tr>").appendTo(wrmtbl);
	 			if (value.qa_char_value != null) { 
	 				var uom = '';
	 				if( value.uomLkpMstrModel != null)
	 					
	 				uom = '('+value.uomLkpMstrModel.lookup_value+')';
	 				$("<td width='20%' align='left'></td>").text(value.psnQualityMstrModel.qa_char_desc).appendTo(rowWrm); 
	 											   $("<td width='80%' align='left'></td>").text(' : '+value.qa_char_value+ uom).appendTo(rowWrm); }
					else { $("<td></td>").text("").appendTo(rowWrm); }
			})
   		}
		
		if (data.WRM_TST_LI.length > 0) {
			var wrmln = $("<tr align='left'> </tr>").appendTo(table);
			if (data.WRM_G_LI.length > 0 || data.WRM_RM_LI.length > 0) {
				var wrmtmp1 = $("<td align='left'></br> </td><td align='left' colspan='4'></td>").appendTo(wrmln);
			} else {
				var wrmtmp1 = $("<td align='left'></br> 15 WRM : </td><td align='left' colspan='4'></td>").appendTo(wrmln);
			}
	 		var wrmln2 = $("<tr align='left'><td align='left' width='20%' style='vertical-align:top'>&nbsp;&nbsp;&nbsp; Testing Requirements : </td></tr>").appendTo(table);
	 		var wrmtmp2 = $("<td align='left' colspan='4'></td>").appendTo(wrmln2);
	   	 	var wrmtbl = $("<table width='100%' align='left' id='"+psn_rep_scrn_type+"_psnwrmtsttbl' cellpadding='0' cellspacing='0'></table>").appendTo(wrmtmp2);
			$.each(data.WRM_TST_LI, function (i, value) {
	 		 	var rowWrm = $("<tr align='center'>...</tr>").appendTo(wrmtbl);
	 			if (value.qa_char_value != null) { 
	 				var uom = '';
	 				if( value.uomLkpMstrModel != null)
	 					
	 				uom = '('+value.uomLkpMstrModel.lookup_value+')';
	 				$("<td width='20%' align='left'></td>").text(value.psnQualityMstrModel.qa_char_desc).appendTo(rowWrm); 
	 											   $("<td width='80%' align='left'></td>").text(' : '+value.qa_char_value+ uom).appendTo(rowWrm); }
					else { $("<td></td>").text("").appendTo(rowWrm); }
			})
		}
		
		if (data.RLM_ACR_LI.length > 0) {
			var wrmln = $("<tr align='left'> </tr>").appendTo(table);
			var wrmtmp1 = $("<td align='left'></br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 13 Rolling Mill : </td><td align='left'></td>").appendTo(wrmln);
	 		var wrmln2 = $("<tr align='left'><td align='left' width='20%' style='vertical-align:top'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; As Cast Requirements : </td></tr>").appendTo(table);
	 		var wrmtmp2 = $("<td align='left'></td>").appendTo(wrmln2);
	   	 	var wrmtbl = $("<table width='100%' align='left' id='"+psn_rep_scrn_type+"_psnwrmtsttbl' cellpadding='0' cellspacing='0'></table>").appendTo(wrmtmp2);
			$.each(data.RLM_ACR_LI, function (i, value) {
	 		 	var rowWrm = $("<tr align='center'>...</tr>").appendTo(wrmtbl);
	 			if (value.qa_char_value != null) { 
	 				var uom = '';
	 				if( value.uomLkpMstrModel != null)
	 					
	 				uom = '('+value.uomLkpMstrModel.lookup_value+')';
	 				$("<td width='20%' align='left'></td>").text(value.psnQualityMstrModel.qa_char_desc).appendTo(rowWrm); 
	 											   $("<td width='80%' align='left'></td>").text(' : '+value.qa_char_value+uom).appendTo(rowWrm); }
					else { $("<td></td>").text("").appendTo(rowWrm); }
			})
		}
		
		if (data.RLM_GR_LI.length > 0) {
			var wrmln = $("<tr align='left'> </tr>").appendTo(table);
			if(data.RLM_ACR_LI.length > 0){
				var wrmtmp1 = $("<td align='left'></br> </td><td align='left'></td>").appendTo(wrmln);
			}
			else{
				var wrmtmp1 = $("<td align='left'></br>13: Rolling Mill : </td><td align='left' colspan='4'></td>").appendTo(wrmln);
		 		
			}
			var wrmln2 = $("<tr align='left'><td align='left' width='20%' style='vertical-align:top'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; General Requirements : </td></tr>").appendTo(table);
	 		var wrmtmp2 = $("<td align='left'></td>").appendTo(wrmln2);
	   	 	var wrmtbl = $("<table width='100%' align='left' id='"+psn_rep_scrn_type+"_psnwrmtsttbl' cellpadding='0' cellspacing='0'></table>").appendTo(wrmtmp2);
			$.each(data.RLM_GR_LI, function (i, value) {
	 		 	var rowWrm = $("<tr align='center'>...</tr>").appendTo(wrmtbl);
	 			if (value.qa_char_value != null) {
	 				var uom = '';
	 				if( value.uomLkpMstrModel != null)
	 					
	 				uom = '('+value.uomLkpMstrModel.lookup_value+')';
	 				$("<td width='20%' align='left'></td>").text(value.psnQualityMstrModel.qa_char_desc).appendTo(rowWrm); 
	 											   $("<td width='80%' align='left'></td>").text(' : '+value.qa_char_value+ uom).appendTo(rowWrm); }
					else { $("<td></td>").text("").appendTo(rowWrm); }
			})
		}
		
		if (data.RLM_RM_LI.length > 0) {
			var wrmln = $("<tr align='left'> </tr>").appendTo(table);
			if(data.RLM_ACR_LI.length > 0 ||data.RLM_GR_LI.length > 0 ){
				var wrmtmp1 = $("<td align='left'></br> </td><td align='left'></td>").appendTo(wrmln);
			}
			else{
				var wrmtmp1 = $("<td align='left'></br>13: Rolling Mill : </td><td align='left' colspan='4'></td>").appendTo(wrmln);
		 		
			}
			var wrmln2 = $("<tr align='left'><td align='left' width='20%' style='vertical-align:top'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Rolling Mill Requirements : </td></tr>").appendTo(table);
	 		var wrmtmp2 = $("<td align='left'></td>").appendTo(wrmln2);
	   	 	var wrmtbl = $("<table width='100%' align='left' id='"+psn_rep_scrn_type+"_psnwrmtsttbl' cellpadding='0' cellspacing='0'></table>").appendTo(wrmtmp2);
			$.each(data.RLM_RM_LI, function (i, value) {
	 		 	var rowWrm = $("<tr align='center'>...</tr>").appendTo(wrmtbl);
	 			if (value.qa_char_value != null) { 
	 				var uom = '';
	 				if( value.uomLkpMstrModel != null)
	 					
	 				uom = '('+value.uomLkpMstrModel.lookup_value+')';
	 				$("<td width='20%' align='left'></td>").text(value.psnQualityMstrModel.qa_char_desc).appendTo(rowWrm); 
	 											   $("<td width='80%' align='left'></td>").text(' : '+value.qa_char_value+ uom).appendTo(rowWrm); }
					else { $("<td></td>").text("").appendTo(rowWrm); }
			})
		}
		
		if (data.RLM_TR_LI.length > 0) {
			var wrmln = $("<tr align='left'> </tr>").appendTo(table);
			if(data.RLM_ACR_LI.length > 0 ||data.RLM_GR_LI.length > 0 || data.RLM_RM_LI.length > 0 ){
				var wrmtmp1 = $("<td align='left'></br> </td><td align='left'></td>").appendTo(wrmln);
			}
			else{
				var wrmtmp1 = $("<td align='left'></br>13: Rolling Mill : </td><td align='left'></td>").appendTo(wrmln);
		 		
			}
			var wrmln2 = $("<tr align='left'><td align='left' width='20%' style='vertical-align:top'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Testing Requirements : </td></tr>").appendTo(table);
	 		var wrmtmp2 = $("<td align='left'></td>").appendTo(wrmln2);
	   	 	var wrmtbl = $("<table width='100%' align='left' id='"+psn_rep_scrn_type+"_psnwrmtsttbl' cellpadding='0' cellspacing='0'></table>").appendTo(wrmtmp2);
			$.each(data.RLM_TR_LI, function (i, value) {
	 		 	var rowWrm = $("<tr align='center'>...</tr>").appendTo(wrmtbl);
	 			if (value.qa_char_value != null) {
	 				var uom = '';
	 				if( value.uomLkpMstrModel != null)
	 					
	 				uom = '('+value.uomLkpMstrModel.lookup_value+')';
	 				$("<td width='20%' align='left'></td>").text(value.psnQualityMstrModel.qa_char_desc).appendTo(rowWrm); 
	 											   $("<td width='80%' align='left'></td>").text(' : '+value.qa_char_value+uom).appendTo(rowWrm); }
					else { $("<td></td>").text("").appendTo(rowWrm); }
			})
		}
		
	
		var rvnln = $("</br><tr align='center'> </tr>").appendTo(table);
		var rvntmp = $("<td width='100%' colspan='5'> </br></td>").appendTo(rvnln);
		var rvntbl = $("<table width='100%' align='center' border='1' id="+psn_rep_scrn_type+"_psnrvntbl cellpadding='0' cellspacing='0'></table>").appendTo(rvntmp);
		$("<tr height='10' align='center'><td width='20%'>Rev No</td><td width='20%'>Date</td><td width='20%'>Summary of Changes</td><td width='20%'>Name</td></tr>").appendTo(rvntbl);
		
 		$.each(data.REVN_LI, function (i, value) {
 			rowRevn = $("<tr height='10' align='center'></tr>").appendTo(rvntbl);  
 			if (value.psn_revn_no != null) { $("<td width='20%'></td>").text(value.psn_revn_no).appendTo(rowRevn); }
				else { $("<td width='20%'></td>").text("").appendTo(rowRevn); }
 			if(value.psn_revn_no != null)
 				{
 				if(value.psn_revn_no==0)
 				{$("<td width='20%'></td>").text(data.ISSUE_DATE).appendTo(rowRevn);}
 				else
 				{$("<td width='20%'></td>").text(data.REVN_DATE).appendTo(rowRevn);}
 				}
 			else { $("<td width='20%'></td>").text("").appendTo(rowRevn); }
 		    if (value.summary_of_revision != null) { $("<td width='20%'></td>").text(value.summary_of_revision).appendTo(rowRevn); }
			else { $("<td width='20%'></td>").text("").appendTo(rowRevn); }
			if (value.approved_by != null) { $("<td width='20%'></td>").text(value.approved_by).appendTo(rowRevn); }
				else { $("<td width='20%'></td>").text("").appendTo(rowRevn); }
		})
		
	 		
 		var cmnln = $("<tr></tr>").appendTo(table);
   		var cmntmp = $("<td colspan='2'></td>").appendTo(cmnln);
   		var cmntbl = $("</br><table width='100%' id="+psn_rep_scrn_type+"_psncmntbl border=1 cellpadding='0' cellspacing='0'></table>").appendTo(cmntmp);
   		$("<tr height='40'> <td align='center' width='20%'>Prepared By <br/>("+data.PREPARED_BY+")</td> <td align='center' width='40%'>Reviewed By <br/>("+data.REVIEWED_BY+")</td> <td align='center' colspan='2'>Approved By <br/>("+data.APPROVED_BY+")</td></tr>").appendTo(cmntbl);
 		//$("<tr height='40'> <td align='center' width='20%'> </td> <td align='center' width='20%' rowspan='3'>MANAGEMENT REPRESENTATIVE</td> <td align='center' width='20%' rowspan='3'></td> <td align='center' width='20%' rowspan='3'>HOD (QA)</td> <td align='center' width='20%' rowspan='3'></td></tr>").appendTo(cmntbl);
 		//$("<tr height='40'> <td align='center' width='20%'>VERIFIED BY <br/>("+data.VERIFIED_BY+")</td> </tr>").appendTo(cmntbl);
 		$("<tr height='40'> <td align='center' width='20%'> </td><td align='center' width='20%'> </td><td align='center' width='20%'> </td> </tr>").appendTo(cmntbl);
   	 	
 		/*var ftrtbl = $("<td width='100%' colspan='5' align='left'> <table width='100%' align='center' id="+psn_rep_scrn_type+"_psnftrtbl cellpadding='0' cellspacing='0'> </table></td>").appendTo(ftrln);
   	 		$("<tr height='40'> <td align='center' width='20%'>PREPARED BY</td> <td align='center' width='40%' colspan='2'>ISSUED AND CONTROLLED BY</td> <td align='center' colspan='2'>REVIEWED AND APPROVED BY</td></tr>").appendTo(ftrtbl);
   	 		$("<tr height='40'> <td align='center' width='20%'> </td> <td align='center' width='20%' rowspan='3'>MANAGEMENT REPRESENTATIVE</td> <td align='center' width='20%' rowspan='3'></td> <td align='center' width='20%' rowspan='3'>HOD (QA)</td> <td align='center' width='20%' rowspan='3'></td></tr>").appendTo(ftrtbl);
 			$("<tr height='40'> <td align='center' width='20%'> </td> </tr>").appendTo(ftrtbl);
   	 		$("<tr height='40'> <td align='center' width='20%'>VERIFIED BY</td> </tr>").appendTo(ftrtbl);*/
 		    $('#'+psn_rep_scrn_type+'_PLANTLOCATION').append(data.PLANT_LOCATION);
 		    $('#'+psn_rep_scrn_type+'_PSNFNO').append(data.PSN_FORMAT_NUMBER);
 		   	$('#'+psn_rep_scrn_type+'_PSNNO').append(data.PSN_NO);
			$('#'+psn_rep_scrn_type+'_ISSUE_NO').append(data.ISSUE_NO);
			$('#'+psn_rep_scrn_type+'_ISSUE_DATE').append(data.ISSUE_DATE);
			$('#'+psn_rep_scrn_type+'_REVN_NO').append(data.REVN_NO);
			$('#'+psn_rep_scrn_type+'_REVN_DATE').append(data.REVN_DATE);
			$('#'+psn_rep_scrn_type+'_PREPARED_BY').append(data.PREPARED_BY);
			$('#'+psn_rep_scrn_type+'_VERIFIED_BY').append(data.VERIFIED_BY);
			$('#'+psn_rep_scrn_type+'_PSNCUSTOMERREQUIREMENT').append(data.PSN_CUSTOMER_REQUIREMENT);
			//$('#'+psn_rep_scrn_type+'_REVIEWED_BY').append(data.REVIEWED_BY);
			
			//$('#'+psn_rep_scrn_type+'_TITLE').append(data.TITLE);  PLANT_LOCATION
			//$('#'+psn_rep_scrn_type+'_GRADE').append(data.GRADE);
			$('#'+psn_rep_scrn_type+'_APPROVED_BY').append(data.APPROVED_BY);
			//$('#'+psn_rep_scrn_type+'_REVN_DATE').append(formatDate(data.REVN_DATE));
			/*$('#SIZE').append(data.SIZE);
			$('#PROCESSROUTE').append(data.ROUTE);
			$('#CUSTNAME').append(data.CUST_NAME);
			$('#CUSTREF').append(data.CUST_REF);
			$('#ENDAPP').append(data.END_APP);
			$('#HOTMETALUSED').append(data.HM_USED);
			
			$('#TAPPING').append(data.TAPPING);
			$('#LADLE_COND').append(data.LADLE_COND);
			$('#PURGING_MED').append(data.PURGING_MED);
			$('#SUPER_HEAT').append(data.SUPER_HEAT);
			$('#VD_TREAT').append(data.VD_TREAT);
			$('#TYPE_CAST').append(data.TYPE_CAST);
			$('#BILLET_MACRO').append(data.BILLET_MACRO);
			$('#BILLET_SURFACE').append(data.BILLET_SURFACE);*/
			//alert($("#r1_psn_report_id").height())
			var hg=$("#r1_psn_report_id").height();
			if(hg<600){
				$("#pge_no").html("SHEET 01 OF 01")
			}else{
				$("#pge_no").html("SHEET 01 OF 02")
			}
			
   		  },
   		error:function(){      
   			$.messager.alert('Processing','Error while Processing Ajax...','error');
   		  }
   		})
  } 
   
  function clearAllDivs(psn_rep_scrn_type)
  {
	  $('#'+psn_rep_scrn_type+'_PLANTLOCATION').empty();
	  $('#'+psn_rep_scrn_type+'_PSNFNO').empty();
	  $('#'+psn_rep_scrn_type+'_PSNNO').empty();
      $('#'+psn_rep_scrn_type+'_ISSUE_NO').empty();
      $('#'+psn_rep_scrn_type+'_ISSUE_DATE').empty();
      $('#'+psn_rep_scrn_type+'_REVN_NO').empty();
      $('#'+psn_rep_scrn_type+'_REVN_DATE').empty();
      $('#'+psn_rep_scrn_type+'_GRADE').empty();
      $('#'+psn_rep_scrn_type+'_PREPARED_BY').empty();
	  $('#'+psn_rep_scrn_type+'_VERIFIED_BY').empty();
	  $('#'+psn_rep_scrn_type+'_TITLE').empty();
	  $('#'+psn_rep_scrn_type+'_PSNCUSTOMERREQUIREMENT').empty();
	  /* $('#SIZE').empty();
      $('#PROCESSROUTE').empty();
      $('#CUSTNAME').empty();
	  $('#CUSTREF').empty();
	  $('#ENDAPP').empty();
	  $('#HOTMETALUSED').empty();      
     $('#TAPPING').empty();
   	 $('#LADLE_COND').empty();
   	 $('#PURGING_MED').empty();
   	 $('#SUPER_HEAT').empty();
   	 $('#VD_TREAT').empty();
	 $('#TYPE_CAST').empty();
	 $('#BILLET_MACRO').empty();
	 $('#BILLET_SURFACE').empty();*/	
  }
  


