/*package com.saraansh.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.Callable;

import jmsRA.TransactionInformation;
import main.CFRTAdapter;

public  class InsertIntoFTRT implements Runnable {
	
	TransactionInformation obj = new TransactionInformation();
	public InsertIntoFTRT(TransactionInformation obj) {
		this.obj=obj;
	}

	@Override
	public void run() {
		insertIntoFTTable(this.obj);
	}

	private void insertIntoFTTable(TransactionInformation tFact) {
     DatabaseAdaptor dbObj = new DatabaseAdaptor();
		String strQry = null;
			strQry = "insert into FINANCIAL_TRANSACTION_RT(CUSTOMER_ID,ACCOUNT_ID,TRANSACTION_REFERENC"
					+ "E_NUMBER,TRANSACTION_TYPE,TRANSACTION_DATE_TIME,TRANSACTION_AMOUNT,CURRENTBALANC"
					+ "E,TRANSACTION_BRANCH,"
					+ "TRANSACTION_MODE, COUNTRY, CHANNEL, CARD_NUMBER, NODE, maker_id,TXN_LOCATION, MCC, RESPONSE_CODE,POS_ENTRY_MODE,COUNTERPART_NAME,COUNTERPART_ACCOUNT_ID,COUNTERPART_BANK,IN_TIME) VALUES (?,?,?,?,?"
					+ ",?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,GETDATE())";
			try (Connection conn = dbObj.getConnection("jdbc/EnterpriseDB");
					 PreparedStatement st = conn.prepareStatement(strQry);
					 ResultSet rs = st.executeQuery();)
			{
			//System.out.println("CustomerId is :::"+tFact.getCustomerId());
			//BigDecimal d = new BigDecimal(tFact.getCustomerId());
			//BigDecimal CUSTOMER_ID = d;
			String CUSTOMER_ID = tFact.getCustomerId();
			String ACCOUNT_ID = tFact.getAccountID();
			String TRANSACTION_REFERENCE_NUMBER = tFact.getTransaction_Reference_Number() + "";
			String TRANSACTION_TYPE = tFact.getTransaction_Type();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			Date TRANSACTION_DATE_TIME = new java.sql.Date(sdf.parse(tFact.getTransactionValueDate()).getTime());
			double TRANSACTION_AMOUNT = tFact.getTransaction_Amount();
			double CURRENTBALANCE = tFact.getCurrentBalance();
			String LOCATION = tFact.getTxn_Loc();
			String TRANSACTION_BRANCH = tFact.getTransactionBranch();
			String BENEFICIARY_ACCOUNT_ID = tFact.getBeneficiary_Account_Id();
			String DRAWEE_BANK = tFact.getDrawee_Bank();

			String DRAWEE_ACCOUNT_ID = tFact.getDrawee_Account_Id();
			String CARD_NUMBER = tFact.getCard_Number();
			String TRANSACTION_MODE = tFact.getTransaction_Mode();
			String COUNTRY = tFact.getCountry();
			String CHANNEL = tFact.getChannel();
			String NODE = tFact.getNode();
			String makerID = tFact.getMakerId();

			int REQUEST_TYPE = tFact.getRequest_Type();
			String TXN_LOCATION = tFact.getMerchantName();
			//String MCC_CODE=tFact.getMccCode();
			String MCC_CODE="";
			String RESPONSE_CODE=tFact.getResponseCode();
			String POS_ENTRY_MODE=tFact.getPosEntryMode();
			String counterpartName = "";
			String counterpartAccID = "";
			String counterpartBank = "";
			
			counterpartName = tFact.getCounterpartName();
			counterpartAccID = tFact.getCounterpartAccID();
			//counterpartBank = RuleBasedDetector.BRANCH_MAP.get(tFact.getBeneficiary_Bank());		
			

			try {

				//pstmt.setBigDecimal(1, CUSTOMER_ID);
				st.setString(1, CUSTOMER_ID);
				st.setString(2, ACCOUNT_ID);
				st.setLong(3, Long.parseLong(TRANSACTION_REFERENCE_NUMBER));
				st.setInt(4, CFRTAdapter.TRANSACTION_TYPE_MAP.get(TRANSACTION_TYPE));
				st.setTimestamp(5, new Timestamp(TRANSACTION_DATE_TIME.getTime()));
				st.setDouble(6, (new Double(TRANSACTION_AMOUNT)).doubleValue());
				st.setDouble(7, (new Double(CURRENTBALANCE)).doubleValue());
				st.setString(8, TRANSACTION_BRANCH);
				st.setInt(9, CFRTAdapter.TRANSACTION_MODE_MAP.get(TRANSACTION_MODE));
				st.setString(10, COUNTRY);
				st.setInt(11, CFRTAdapter.TRANSACTION_CHANNEL_MAP.get(CHANNEL));
				st.setString(12, CARD_NUMBER);
				st.setString(13, NODE);
				st.setString(14, makerID);
				st.setString(15, TXN_LOCATION);
				st.setString(16, MCC_CODE);
				st.setString(17, RESPONSE_CODE);
				st.setString(18, POS_ENTRY_MODE);
				st.setString(19, counterpartName);
				st.setString(20, counterpartAccID);
				st.setString(21, counterpartBank);
				st.addBatch();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			tFact = null;
			st.executeBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
	}
} */