package com.saraansh.database;

import static main.CFRTAdapter.logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.saraansh.profiler.AccountStats;
import com.saraansh.profiler.CardStats;

import dao.CustomerProfile;
import dao.ProfilerData;
import main.CFRTAdapter;

public class DatabaseAdaptor {

	public Map<String, CustomerProfile> getCustomerProfileInfo(List<String> account_id_list) {
		String strQry = null;
		String customer_id = "";
		String accounts = "";
		
		Map<String, CustomerProfile> result = new HashMap<>();
		
		for (String acc: account_id_list) {
			accounts += "'" + acc + "',";
		}
		try {
			accounts = accounts.substring(0, accounts.lastIndexOf(","));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		strQry =  " select CUSTOMER_ID, FLG_STAFF, DATE_OF_BIRTH, ACCOUNT_ID, PROFILE_CREATIONDATE, MOBILE_NUMBER, APP, PRODUCT_GROUP, "+ 
		          " ACCOUNT_OPEN_BALANCE, account_status, CUST_TYPE, CUST_SUB_TYPE, COST_CENTER, PREVIOUS_DAY_BALANCE, "
		          + "FIRST_NAME ,LAST_NAME from CUSTOMER_PROFILE where ACCOUNT_ID in($1) " ;
				
		strQry = strQry.replace("$1", accounts);
		
		try (Connection con = getConnection("jdbc/EnterpriseDB");
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery(strQry);) {
			String accountID = "";
			CustomerProfile customerProfile = null;
			while (rs.next()) {
				customerProfile = new CustomerProfile();
                customerProfile.setCustomerId(rs.getString(1));
                customer_id = rs.getString(1);
                
				if (customer_id !=null && !customer_id.equals("")){
					if (rs.getString(2)!=null)
						customerProfile.setFlagStaff(rs.getString(2));
					else
						customerProfile.setFlagStaff("N");
					customerProfile.setDOB(rs.getString(3));
					accountID = rs.getString(4);
					customerProfile.setAccountId(accountID);
					customerProfile.setProfileCreationDate(rs.getString(5));
					customerProfile.setMobileNumber(rs.getString(6));
					customerProfile.setProductGroup(rs.getString(8));
					customerProfile.setAccountOpenBalance(rs.getFloat(9));
					customerProfile.setAccountStatus(rs.getString(10));
					if (rs.getString(11)!=null)
						customerProfile.setCustomerType(rs.getString(11));
					else
						customerProfile.setCustomerType("0");
					
					if (rs.getString(11) != null)
						customerProfile.setCustomerSubType(rs.getString(12));
					else
						customerProfile.setCustomerSubType("0");

					customerProfile.setBranchID(rs.getString(13));
					customerProfile.setPreviousDayBalance(rs.getFloat(14));
					customerProfile.setCustomerName(rs.getString(15)+"#"+rs.getString(16));//namescreening implementation in RT

			}
				result.put(accountID.trim(), customerProfile);
		  }
		} catch (StringIndexOutOfBoundsException e) {
			e.printStackTrace();
			CFRTAdapter.logger.error("", e+"-"+account_id_list);
		} catch (Exception e) {
			e.printStackTrace();
			CFRTAdapter.logger.error("", e+"-"+account_id_list);
		} 
		return result;
	}
	
	public static long getDiffYears(Date first, Date last) {
	    Calendar a = getCalendar(first);
	    Calendar b = getCalendar(last);
	    long end = a.getTimeInMillis();
	    long start = b.getTimeInMillis();
	    return TimeUnit.MILLISECONDS.toDays(Math.abs(end - start));
	}
	
	public static Calendar getCalendar(Date date) {
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
	    return cal;
	}

	
	/*public Connection getConnection() {
		Connection conn = null;
		try {
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:/comp/env");
			DataSource ds = (DataSource) envContext.lookup("jdbc/EnterpriseDB");
			conn = ds.getConnection();
		} catch (NamingException ne) {
			ne.printStackTrace();
		}
		catch (SQLException sqle) {
			sqle.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}*/
	
	public Connection getConnection(String dbContext) {
		Connection conn = null;
		try {
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:/comp/env");
			DataSource ds = (DataSource) envContext.lookup(dbContext);
			conn = ds.getConnection();
		} catch (NamingException ne) {
			ne.printStackTrace();
		}

		catch (SQLException sqle) {
			sqle.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

	public HashMap<String, Integer> getTransactionChannelMap() {
		HashMap<String, Integer> channelMap = new HashMap<String, Integer>();
		String cNo_query = "SELECT TXN_CHANNEL, TXN_CHANNEL_ID FROM TRANSACTION_CHANNEL";
		try (Connection conn = getConnection("jdbc/EnterpriseDB");
				Statement stmt = conn.createStatement();
				ResultSet rs_cNo = stmt.executeQuery(cNo_query);) {

			if (rs_cNo != null) {
				while (rs_cNo.next())
					channelMap.put(rs_cNo.getString(1), rs_cNo.getInt(2));
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return channelMap;
	}

	public HashMap<String, Integer> getTransactionModeMap() {
		HashMap<String, Integer> modeMap = new HashMap<String, Integer>();
		String cNo_query = "SELECT TRXN_MODE_DSC, TRXN_MODE_ID FROM TRANSACTION_MODE";
		try (Connection conn = getConnection("jdbc/EnterpriseDB");
				Statement stmt = conn.createStatement();
				ResultSet rs_cNo = stmt.executeQuery(cNo_query);) {
			if (rs_cNo != null) {
				while (rs_cNo.next()) {
					modeMap.put(rs_cNo.getString(1), rs_cNo.getInt(2));
				}
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return modeMap;
	}
	
	public HashMap<String, String> getProductGroupMap() {
		HashMap<String, String> modeMap = new HashMap<String, String>();
		String cNo_query = "SELECT GRP, DES FROM product_group";
		try (Connection conn = getConnection("jdbc/EnterpriseDB");
				Statement stmt = conn.createStatement();
				ResultSet rs_cNo = stmt.executeQuery(cNo_query);) {
			if (rs_cNo != null) {
				while (rs_cNo.next()) {
					modeMap.put(rs_cNo.getString(1), rs_cNo.getString(2));
				}
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return modeMap;
	}

	public HashMap<String, Integer> getTransactionTypeMap() {
		HashMap<String, Integer> TRXN_TYPE_DSC = new HashMap<String, Integer>();
		String cNo_query = "SELECT TRXN_TYPE_DSC, TRXN_TYPE_ID FROM TRANSACTION_TYPE";
		try (Connection conn = getConnection("jdbc/EnterpriseDB");
				Statement stmt = conn.createStatement();
				ResultSet rs_cNo = stmt.executeQuery(cNo_query);) {
			if (rs_cNo != null) {
				while (rs_cNo.next()) {
					TRXN_TYPE_DSC.put(rs_cNo.getString(1), rs_cNo.getInt(2));
				}
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return TRXN_TYPE_DSC;
	}
	
	/*Added by Ganesh
     * The getProfilerData method used for getting Daily Statistics and behavior of an account and card
     * 07th August 2018
     */
	public ProfilerData getProfilerData(String entityNumber, Integer channel,Integer mode,Integer entityID) {
		String strQry = null;
		AccountStats acctStats = new AccountStats();
		CardStats cardStats = new CardStats();
		ProfilerData profilerData = new ProfilerData();
		
		strQry = "SELECT ENTITY_ID,ENTITY_NUMBER,CHANNEL, TRANSACTION_MODE, NO_OF_DR_TXTD_DAYS,TOTAL_NO_OF_DR_TXNS,SUM_OF_DR_TXN_AMT,MAX_DR_TXN_AMT_INFO,LAST_DR_TXN_INFO,"
				+ " NO_OF_CR_TXTD_DAYS,TOTAL_NO_OF_CR_TXNS,SUM_OF_CR_TXN_AMT,MAX_CR_TXN_AMT_INFO,LAST_CR_TXN_INFO,"
				+ " DECLINED_TXN_OCCURRENCE,PIN_CHANGE_OCCURRENCE,TIME_OCCURRENCE,CURRENCY_OCCURRENCE,COUNTRY_OCCURRENCE,"
				+ " LOCATION_OCCURRENCE,NODE_OCCURRENCE,IP_OCCURRENCE,MCC_OCCURRENCE,BENEFICIARY_OCCURRENCE,AVG_INTER_TXN_GAP,AVG_MONTHLY_COUNT,AVG_MONTHLY_AMOUNT from ENTITY_STATS where "
				+ " ENTITY_NUMBER = '$1' and ENTITY_ID=$2 ";
		
		strQry = strQry.replace("$1", entityNumber);
		strQry = strQry.replace("$2", entityID+"");
		try (Connection conn = getConnection("jdbc/SDGProd");
			 PreparedStatement st = conn.prepareStatement(strQry);
			 ResultSet rs = st.executeQuery();) {
			while (rs.next()) {
				if (rs.getInt("ENTITY_ID") == CFRTAdapter.ENTITY_MASTER.get("Card_number"))
					profilerData = cardStats.getCardProfilerData(rs.getString("ENTITY_NUMBER"), channel, mode, rs);
				else if (rs.getInt("ENTITY_ID") == CFRTAdapter.ENTITY_MASTER.get("Account_id"))
					profilerData = acctStats.getAccountProfilerData(rs.getString("ENTITY_NUMBER"), channel, mode, rs);
			}
		} catch (Exception e) {
			CFRTAdapter.logger.error(e);
		}
		acctStats=null;
		cardStats=null;
		return profilerData;
	}
	
	public HashMap<String, Integer> getEntityMasterMap() {
		HashMap<String, Integer> entityMasterMap = new HashMap<String, Integer>();
		String cNo_query = "SELECT ENTITY_ID, ENTITY_DESCRIPTION FROM ENTITY_MASTER";
		try (Connection conn = getConnection("jdbc/SDGProd");
				Statement stmt = conn.createStatement();
				ResultSet rs_cNo = stmt.executeQuery(cNo_query);) {
			if (rs_cNo != null) {
				while (rs_cNo.next()) {
					entityMasterMap.put(rs_cNo.getString(2).trim(), rs_cNo.getInt(1));
				}
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return entityMasterMap;
	}
	
	/**
	 * load the watchlist data for account id and cutomer id,
	 * Added by Prathyusha Mallela from tmb msr code
	 * */
	public HashMap<Integer, ArrayList<String>> loadWhiteListData() {
		String strQry = "SELECT WATCHLIST_ID, WATCHLIST_ENTITY_ID FROM " 
				+ " WATCHLIST WL, WATCHLIST_DATA WLD "
				+ "WHERE WL.LIST_STATUS='White' AND WL.TYPE=1 AND WL.LIST_ID=WLD.LIST_ID "
				+ "and WLD.WATCHLIST_ENTITY_ID is not null order by WL.WATCHLIST_ID";
		ArrayList<String> wList = new ArrayList<String>();
		HashMap<Integer, ArrayList<String>> whiteWatchListData = new HashMap<Integer, ArrayList<String>>();
		
		try (Connection conn = getConnection("jdbc/EnterpriseDB");
				PreparedStatement st = conn.prepareStatement(strQry);
				ResultSet rs = st.executeQuery(strQry);) {
			while (rs.next()) {
				if (whiteWatchListData.get(rs.getInt(1)) == null) {
					wList = new ArrayList<String>();
					wList.add(rs.getString("WATCHLIST_ENTITY_ID"));
				} else {
					wList = whiteWatchListData.get(rs.getInt(1));
					wList.add(rs.getString("WATCHLIST_ENTITY_ID"));
				}
				whiteWatchListData.put(rs.getInt(1), wList);
			}
		} catch (Exception e) {
			logger.error("", e);
		}
		return whiteWatchListData;
	}
	
	//added new  method updateTransactionStatus by abhilash
	
	public void updateTransactionStatus(String status, long refNo,String channel) {
		logger.info("status" +  status);
		logger.info("refNo" +  refNo);
		logger.info("channel" +  channel);
		String query = "UPDATE FINANCIAL_TRANSACTION_RT SET RESPONSE_CODE= "+"'" + status+ "'" +" WHERE TRANSACTION_REFERENCE_NUMBER= "+ "'" +refNo +"'" +" AND CHANNEL= " + "'" + channel +"'" ;
		logger.info("query" + query);	
		//	+ "AND TRANSACTION_DATE_TIME='$3'";
			/*
			 * query = query.replace("$1", status); query = query.replace("$2", refNo);
			 */
		//query = query.replace("$3", trxn_date_time);

		try (Connection conn = getConnection("jdbc/EnterpriseDB"); PreparedStatement stmt = conn.prepareStatement(query);) {
			stmt.setString(1, status); 
			stmt.setLong(2, refNo); 
			stmt.setString(3, channel); 
			int result = stmt.executeUpdate();
			System.out.println(result + " row updated successfully");
			logger.info(result + " row updated successfully");

		} catch (SQLException e1) {
			logger.info(e1 + " e1");
			logger.error("" + e1);
		} catch (Exception e) {
			logger.info(e + " e");
			logger.error("" + e);
		}
	}
	
	public void updateTransactionStatus(String status, String refNo,String channel) {
		logger.info("status" +  status);
		logger.info("refNo" +  refNo);
		logger.info("channel" +  channel);
		String query = "UPDATE FINANCIAL_TRANSACTION_RT SET RESPONSE_CODE= "+"'" + status+ "'" +" WHERE AC_ENTRY_SR_NO= "+ "'" +refNo +"'" +" AND CHANNEL= " + "'" + channel +"'" ;
		logger.info("query" + query);	
		//	+ "AND TRANSACTION_DATE_TIME='$3'";
			/*
			 * query = query.replace("$1", status); query = query.replace("$2", refNo);
			 */
		//query = query.replace("$3", trxn_date_time);

		try (Connection conn = getConnection("jdbc/EnterpriseDB"); PreparedStatement stmt = conn.prepareStatement(query);) {
			stmt.setString(1, status); 
			stmt.setString(2, refNo); 
			stmt.setString(3, channel); 
			int result = stmt.executeUpdate();
			System.out.println(result + " row updated successfully");
			logger.info(result + " row updated successfully");

		} catch (SQLException e1) {
			logger.info(e1 + " e1");
			logger.error("" + e1);
		} catch (Exception e) {
			logger.info(e + " e");
			logger.error("" + e);
		}
	}



	//Added for CustomerId look up for Switch card trxns 

	public String getCustomerId(String accountID) { String customerId = "";
	String query =	"select CUSTOMER_ID from ENTPROD.CUSTOMER_PROFILE where ACCOUNT_ID=?"; 
	try	(Connection con = getConnection("jdbc/EnterpriseDB");PreparedStatement stmt	= con.prepareStatement(query);){
			stmt.setString(1, accountID); 
			try(ResultSet rs = stmt.executeQuery();)
				{ 
						while(rs.next()) {
						customerId=rs.getString("CUSTOMER_ID"); 
				}
		}catch (Exception e) {
							logger.error("", e);
							}
			} catch (Exception e) { 
				logger.error("", e);
			}
	return	customerId; }
		 
}
