package jmsRA;

import static main.CFRTAdapter.logger;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import dao.TransactionInformation;

public class TransactionInfoConverter {

	public TransactionInformation convertToTransactionInformation(StringBuffer txnAsXML) {
		TransactionInformation txn_obj = null;
		txn_obj = new TransactionInformation();
		try {
			String xmlString = txnAsXML.toString();
			xmlString = removeSpecialCharacters(xmlString);
			
			txn_obj.setAccountID(StringUtils.substringBetween(xmlString,"<ACCOUNT_ID>", "</ACCOUNT_ID>"));
			txn_obj.setCounterpartAccountID(StringUtils.substringBetween(xmlString,"<COUNTERPART_ACCOUNT_ID>", "</COUNTERPART_ACCOUNT_ID>"));
			txn_obj.setCardNumber(StringUtils.substringBetween(xmlString,"<CARD_NUMBER>", "</CARD_NUMBER>"));
			txn_obj.setCustomerID(StringUtils.substringBetween(xmlString,"<CUSTOMER_ID>", "</CUSTOMER_ID>"));
			txn_obj.setTransactionAmount(Float.parseFloat(StringUtils.substringBetween(xmlString,"<TRANSACTION_AMOUNT>", "</TRANSACTION_AMOUNT>")));
			txn_obj.setCurrentBalance(Float.parseFloat(StringUtils.substringBetween(xmlString,"<CURRENTBALANCE>", "</CURRENTBALANCE>")));
			txn_obj.setTransactionType(StringUtils.substringBetween(xmlString,"<TRANSACTION_TYPE>", "</TRANSACTION_TYPE>"));
			txn_obj.setTransactionMode(StringUtils.substringBetween(xmlString,"<TRANSACTION_MODE>", "</TRANSACTION_MODE>"));
			txn_obj.setTransactionChannel(StringUtils.substringBetween(xmlString,"<CHANNEL>", "</CHANNEL>"));
			txn_obj.setTransactionCountry(StringUtils.substringBetween(xmlString,"<COUNTRY>", "</COUNTRY>"));
			txn_obj.setTransactionProvince(StringUtils.substringBetween(xmlString,"<TRANSACTION_PROVINCE>", "</TRANSACTION_PROVINCE>"));
			txn_obj.setTransactionBranch(StringUtils.substringBetween(xmlString,"<TRANSACTION_BRANCH>", "</TRANSACTION_BRANCH>"));
			txn_obj.setCounterpartBank(StringUtils.substringBetween(xmlString,"<COUNTERPART_BANK>", "</COUNTERPART_BANK>"));
			txn_obj.setCounterpartName(StringUtils.substringBetween(xmlString,"<COUNTERPART_NAME>", "</COUNTERPART_NAME>"));
			txn_obj.setAcquirerID(StringUtils.substringBetween(xmlString,"<ACQUIRER_ID>", "</ACQUIRER_ID>"));
			txn_obj.setIssuerID(StringUtils.substringBetween(xmlString,"<ISSUER_ID>", "</ISSUER_ID>"));
			txn_obj.setNode(StringUtils.substringBetween(xmlString,"<NODE>", "</NODE>"));
			txn_obj.setTransactionLocation(StringUtils.substringBetween(xmlString,"<TXN_LOCATION>", "</TXN_LOCATION>"));
			txn_obj.setTransactionCode(StringUtils.substringBetween(xmlString,"<TXN_CODE>", "</TXN_CODE>"));
			txn_obj.setTransactionDescription(StringUtils.substringBetween(xmlString,"<TXN_DESC>", "</TXN_DESC>"));
			txn_obj.setMakerID(StringUtils.substringBetween(xmlString,"<MAKER_ID>", "</MAKER_ID>"));
			txn_obj.setCheckerID(StringUtils.substringBetween(xmlString,"<CHECKER_ID>", "</CHECKER_ID>"));
/*			txn_obj.setFeeFlag(Integer.parseInt(StringUtils.substringBetween(xmlString,"<FEE_FLG>", "</FEE_FLG>")));
			txn_obj.setPaymentFlag(Integer.parseInt(StringUtils.substringBetween(xmlString,"<PAYMENT_FLG>", "</PAYMENT_FLG>")));
			txn_obj.setErrorCorrectedFlag(Integer.parseInt(StringUtils.substringBetween(xmlString,"<ERROR_CORRECTED_FLAG>", "</ERROR_CORRECTED_FLAG>")));
			txn_obj.setReversalFlag(Integer.parseInt(StringUtils.substringBetween(xmlString,"<REVERSAL_FLG>", "</REVERSAL_FLG>")));
*/			txn_obj.setResponseCode(StringUtils.substringBetween(xmlString,"<RESPONSE_CODE>", "</RESPONSE_CODE>"));
			txn_obj.setReasonCode(StringUtils.substringBetween(xmlString,"<REASON_CODE>", "</REASON_CODE>"));
			txn_obj.setPosEntryMode(StringUtils.substringBetween(xmlString,"<POS_ENTRY_MODE>", "</POS_ENTRY_MODE>"));
			txn_obj.setMcc(StringUtils.substringBetween(xmlString,"<MCC>", "</MCC>"));
			txn_obj.setEciCode(StringUtils.substringBetween(xmlString,"<ECI_CODE>", "</ECI_CODE>"));
			txn_obj.setTransactionSourceOfFunds(StringUtils.substringBetween(xmlString,"<TXN_SOURCE_OF_FUNDS>", "</TXN_SOURCE_OF_FUNDS>"));
			txn_obj.setEventSeqNo(StringUtils.substringBetween(xmlString,"<EVENT_SEQ_NO>", "</EVENT_SEQ_NO>"));
			txn_obj.setAccountEntrySrNo(StringUtils.substringBetween(xmlString,"<AC_ENTRY_SR_NO>", "</AC_ENTRY_SR_NO>"));
			txn_obj.setMiscellaneous(StringUtils.substringBetween(xmlString,"<MISCELLANEOUS>", "</MISCELLANEOUS>"));
			txn_obj.setReserveField1(StringUtils.substringBetween(xmlString,"<RESERVE_FIELD1>", "</RESERVE_FIELD1>"));
			txn_obj.setReserveField2(StringUtils.substringBetween(xmlString,"<RESERVE_FIELD2>", "</RESERVE_FIELD2>"));
			txn_obj.setReserveField3(StringUtils.substringBetween(xmlString,"<RESERVE_FIELD3>", "</RESERVE_FIELD3>"));
			//txn_obj.setTransactionReferenceNumber(Long.parseLong(StringUtils.substringBetween(xmlString,"<TRANSACTION_REFERENCE_NUMBER>", "</TRANSACTION_REFERENCE_NUMBER>")));
			
			if (txn_obj.getTransactionType().equalsIgnoreCase("Withdrawal") && txn_obj.getCounterpartAccountID()!=null && !txn_obj.getCounterpartAccountID().equalsIgnoreCase("")) {
				txn_obj.setBeneficiaryAccountID(txn_obj.getCounterpartAccountID());
				txn_obj.setCounterpartAccountID(txn_obj.getBeneficiaryAccountID());
			}
			else if (txn_obj.getTransactionType().equalsIgnoreCase("Deposit") && txn_obj.getCounterpartAccountID()!=null && !txn_obj.getCounterpartAccountID().equalsIgnoreCase("")) {
				txn_obj.setDraweeAccountID(txn_obj.getCounterpartAccountID());
				txn_obj.setCounterpartAccountID(txn_obj.getDraweeAccountID());
			}
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			Date transactionDateTime = sdf.parse(StringUtils.substringBetween(xmlString,"<TRANSACTION_DATE_TIME>", "</TRANSACTION_DATE_TIME>"));
			txn_obj.setTransactionDateTime(transactionDateTime);

		} catch (Exception e) {
			logger.error("",  e);
		}
		return txn_obj;
	}
	
	private String removeSpecialCharacters(String xmlString) {
		if (xmlString.contains("'"))
			xmlString = xmlString.replace("'", "&apos;");
		if (xmlString.contains("&"))
			xmlString = xmlString.replace("&", "&amp;");
		return xmlString;
	}
}
