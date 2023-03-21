/**
 * PSN Review and Approve Script
 */

$('#m35_psn_review_tbl_id').datagrid({
		onDblClickRow : function() {			
			$('#m35_PSN_Review_Dtls_form_id').form('clear');
			m35ReviewDtls();
		}
	});	

$("#m35PsnPsnCategory").combobox({
	onSelect : function(record) {
		reset();
		if(record.text!="WAITING_FOR_REVIEW"){
			status="NEW_PSN_FOR_REVIEW"
		}
		else{
			status="WAITING_FOR_REVIEW"
		}
		
		var urlstatus="./psnReviewApprove/getPSNDtlsWaitingforReview?psn_status="+status;		
		$
		.ajax({
			headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json'
			},
			type : 'GET',
			dataType : "json",
			url : urlstatus,
			success : function(data) {
				$('#m35_psn_review_tbl_id').datagrid('loadData', data);

			},
			error : function() {
				$.messager
						.alert(
								'Processing',
								'CHemistry Revision Display Error while Processing Ajax...',
								'error');
			}
		});		
	}
});


function reset()
{
	$('#m35_psn_review_tbl_id').datagrid('loadData', []);
}

function getWaitingPSNDtls() {
		var psn_status=$('#m35PsnPsnCategory').combobox('getValue');
		$
				.ajax({
					headers : {
						'Accept' : 'application/json',
						'Content-Type' : 'application/json'
					},
					type : 'GET',
					dataType : "json",
					url : './psnReviewApprove/getPSNDtlsWaitingforReview?psn_status='
							+ psn_status,
					success : function(data) {
						$('#m35_psn_review_tbl_id').datagrid('loadData', data);
					},
					error : function() {
						$.messager
								.alert(
										'Processing',
										'CHemistry Revision Display Error while Processing Ajax...',
										'error');
					}
				});
	}

    function reviewDtlsforReturn(psn_hdr_sl_no,psn_no)
    {
    	$.ajax({
			headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json'
			},
			type : 'GET',
			// data: JSON.stringify(formData),
			dataType : "json",
			url : './psnChemMstr/getPsnRevChemDetails?psn_hdr_sl_no='
					+ psn_hdr_sl_no,

			success : function(data) {
				$("#m35_PSN_rev_Chemistry_tbl_id").datagrid('loadData',
						data);

			},
			error : function() {
				$.messager.alert('Processing',
						'Error while Processing Ajax...', 'error');
			}
		});
    	$
		.ajax({
			headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json'
			},
			type : 'GET',
			// data: JSON.stringify(formData),
			dataType : "json",
			url : './PSNProductMstr/getPSNProductByPSN?psn_no='
					+ psn_no + '&psn_hdr_sl_no='
					+ psn_hdr_sl_no,
			success : function(data) {
				$('#m35_psn_review_prod_tbl_id').datagrid(
						'loadData', data);
			},
			error : function() {
				$.messager.alert('Processing',
						'Error while Processing Ajax...', 'error');
			}
		});
    	$
		.ajax({
			headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json'
			},
			type : 'GET',
			// data: JSON.stringify(formData),
			dataType : "json",
			url : './PSNProductMstr/getPsnRevProdDetails?psn_hdr_sl_no='
					+ psn_hdr_sl_no,
			success : function(data) {
				$('#m35_psn_review_rev_prod_tbl_id').datagrid(
						'loadData', data);
			},
			error : function() {
				$.messager
						.alert(
								'Processing',
								'Product Revision Display Error while Processing Ajax...',
								'error');
			}
		});
    }

	function m35ReviewDtls() {
		var row = $('#m35_psn_review_tbl_id').datagrid('getSelected');
		if (row) {
		 if(row.psn_status=="WAITING_FOR_REVIEW"){	
			document.getElementById('m35_psn_hdr_sl_no').value=row.psn_hdr_sl_no;
			$('#m35_psn_rev_no').textbox('setText', row.psn_revn_no);
			$('#m35_psn_desc').textbox('setText', row.psn_no);
			$('#m35_psn_status').textbox('setText', row.psn_status);
			$('#m35_revision_summary').textbox('setText', row.summary_of_revision);			
			$('#m35_PSN_Review_Dtls_form_div_id').dialog({
				modal : true,
				cache : true
			});
			$('#m35_PSN_Review_Dtls_form_div_id').dialog('open').dialog(
					'center').dialog('setTitle', 'PSN Revision Details Form');
			var psn_hdr_sl_no = row.psn_hdr_sl_no;
			var old_psn_hdr_sl_no = row.psn_no_before_rev;
			var psn_no = row.psn_no;
			if(old_psn_hdr_sl_no==null || old_psn_hdr_sl_no=='null' || old_psn_hdr_sl_no=='')
				{
				reviewDtlsforReturn(psn_hdr_sl_no,psn_no);
				}
			else
				{
			$.ajax({
				headers : {
					'Accept' : 'application/json',
					'Content-Type' : 'application/json'
				},
				type : 'GET',
				// data: JSON.stringify(formData),
				dataType : "json",
				url : './psnChemMstr/getPsnRevChemDetails?psn_hdr_sl_no='
						+ old_psn_hdr_sl_no,

				success : function(data) {
					$("#m35_PSN_rev_Chemistry_tbl_id").datagrid('loadData',
							data);

				},
				error : function() {
					$.messager.alert('Processing',
							'Error while Processing Ajax...', 'error');
				}
			});
			$
					.ajax({
						headers : {
							'Accept' : 'application/json',
							'Content-Type' : 'application/json'
						},
						type : 'GET',
						// data: JSON.stringify(formData),
						dataType : "json",
						url : './PSNProductMstr/getPSNProductByPSN?psn_no='
								+ psn_no + '&psn_hdr_sl_no='
								+ old_psn_hdr_sl_no,
						success : function(data) {
							$('#m35_psn_review_prod_tbl_id').datagrid(
									'loadData', data);
						},
						error : function() {
							$.messager.alert('Processing',
									'Error while Processing Ajax...', 'error');
						}
					});
			$
					.ajax({
						headers : {
							'Accept' : 'application/json',
							'Content-Type' : 'application/json'
						},
						type : 'GET',
						// data: JSON.stringify(formData),
						dataType : "json",
						url : './PSNProductMstr/getPsnRevProdDetails?psn_hdr_sl_no='
								+ old_psn_hdr_sl_no,
						success : function(data) {
							$('#m35_psn_review_rev_prod_tbl_id').datagrid(
									'loadData', data);
						},
						error : function() {
							$.messager
									.alert(
											'Processing',
											'Product Revision Display Error while Processing Ajax...',
											'error');
						}
					});
				}
		} else {
			 if(row.psn_status=="NEW_PSN_FOR_REVIEW"){
				 document.getElementById('m35_psn_no').value=row.psn_hdr_sl_no;
				 document.getElementById('m35_psn_hdr_sl_no').value=row.psn_hdr_sl_no;
				 $('#m35_rep_psn_desc').textbox('setText', row.psn_no);				 
				 $('#m35_PSN_Review_Report_div_id').dialog({
						modal : true,
						cache : true
					});
					$('#m35_PSN_Review_Report_div_id').dialog('open').dialog(
							'center').dialog('setTitle', 'New PSN Revision Details Form');				
					executeReportDisplay();
			 }
			 else
				 {
			$.messager.alert('PSN Review Details Info',
					'Please Select PSN ...!', 'info');
				 }
		}
	   }		
	}
	function SendForApproval() {
		var psn_hdr_sl_no = document.getElementById('m35_psn_hdr_sl_no').value;
		var remarks = $('#m35_psn_rem').textbox('getText');
		if (psn_hdr_sl_no != null) {
			$
					.ajax({
						headers : {
							'Accept' : 'application/json',
							'Content-Type' : 'application/json'
						},
						type : 'POST',
						dataType : "json",
						url : './psnReviewApprove/PSNSendToApprove?psn_hdr_sl_no='
								+ psn_hdr_sl_no + '&remarks=' + remarks,
						success : function(data) {
							if (data.comment != "" && data.status == "SUCCESS") {
								console.log("data from response "
										+ JSON.stringify(data));
								$.messager.alert(' PSN Status Info',
										'PSN Sent for Approval');
								getWaitingPSNDtls();
							} else {
								if ((data.status == "FAILURE" || data.status == "SUCCESS")
										&& data.comment == "") {
									console.log("data from response "
											+ JSON.stringify(data));
									$.messager.alert(' PSN Status Info',
											'PSN Review failed');
									getWaitingPSNDtls();
								} else {
									$.messager.alert(data.status, "Error:"
											+ data.comment);
								}
							}
							$('#m35_PSN_Review_Dtls_form_div_id').dialog('close');
						},
						error : function() {
							$.messager.alert('Processing',
									'Error while Processing Ajax...', 'error');
						}
					});
		} else {
			$.messager.alert('Information', 'PSN No is not selected', 'error');
		}
	}
	function ReturnPSN() {
		var psn_hdr_sl_no = document.getElementById('m35_psn_hdr_sl_no').value;
		var remarks = $('#m35_psn_rem').textbox('getText');
		if (psn_hdr_sl_no != null) {
			$
					.ajax({
						headers : {
							'Accept' : 'application/json',
							'Content-Type' : 'application/json'
						},
						type : 'POST',
						dataType : "json",
						url : './psnReviewApprove/PSNReturn?psn_hdr_sl_no='
								+ psn_hdr_sl_no + '&remarks=' + remarks,
						success : function(data) {
							if (data.comment != "" && data.status == "SUCCESS") {
								console.log("data from response "
										+ JSON.stringify(data));
								$.messager.alert(' PSN Status Info',
										'PSN Returned to Initiator');
								getWaitingPSNDtls();
							} else {
								if ((data.status == "FAILURE" || data.status == "SUCCESS")
										&& data.comment == "") {
									console.log("data from response "
											+ JSON.stringify(data));
									$.messager.alert(' PSN Status Info',
											'PSN Returning failed');
									getWaitingPSNDtls();
								} else {
									$.messager.alert(' Info ', 'Error:'
											+ data.comment);
								}
							}
							$('#m35_PSN_Review_Dtls_form_div_id').dialog('close');
						},
						error : function() {
							$.messager.alert('Processing',
									'Error while Processing Ajax...', 'error');
						}
					});
		} else {
			$.messager.alert('Information', 'PSN No is not selected ', 'error');
		}
	}

	function RejectPSN() {
		var psn_hdr_sl_no = document.getElementById('m35_psn_hdr_sl_no').value;
		var remarks = $('#m35_psn_rem').textbox('getText');

		if (psn_hdr_sl_no != null) {
			$
					.ajax({
						headers : {
							'Accept' : 'application/json',
							'Content-Type' : 'application/json'
						},
						type : 'POST',
						dataType : "json",
						url : './psnReviewApprove/PSNReject?psn_hdr_sl_no='
								+ psn_hdr_sl_no + '&remarks=' + remarks,
						success : function(data) {
							if (data.comment != "" && data.status == "SUCCESS") {
								console.log("data from response "
										+ JSON.stringify(data));
								$.messager.alert(' PSN Status Info',
										'PSN Rejected');
								getWaitingPSNDtls();
							} else {
								if ((data.status == "FAILURE" || data.status == "SUCCESS")
										&& data.comment == "") {
									console.log("data from response "
											+ JSON.stringify(data));
									$.messager.alert(' PSN Status Info',
											'PSN Rejection failed');
									getWaitingPSNDtls();
								} else {
									$.messager.alert(' Info ', data.comment);
								}
							}
							$('#m35_PSN_Review_Dtls_form_div_id').dialog('close');
						},
						error : function() {
							$.messager.alert('Processing',
									'Error while Processing Ajax...', 'error');
						}
					});
		} else {
			$.messager.alert('Information', 'PSN No is not selected', 'error');
		}
	}
	
	function formatRevisedValue(val, row) {
		if (val != null) {
			return '<span style="color:red;font-weight:bold">' + val
					+ '</span>';
		}
	}		
	