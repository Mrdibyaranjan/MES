package main;

import java.io.BufferedReader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContextEvent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.manipal.deduplication.ConstantConfiguration;
import com.manipal.deduplication.Dedupe;
import com.saraansh.database.DatabaseAdaptor;
import com.saraansh.enrich.EnrichTransaction;

import clueDetectorFramework.RuleBasedClueDetectorRemote;
import dao.TransactionInformation;

import jmsRA.TransactionInfoConverter;
//import processingFramework.TransactionProcessorRemote;
import processingFramework.TransactionProcessorRemote;

public class CFRTAdapter {
	public static long lastWSCall = 0;
	public static Context context = null;
	// public static TransactionBeanRemote txnBean = null;
	public static RuleBasedClueDetectorRemote rbdBean = null;
	public static Logger logger = LogManager.getLogger(CFRTAdapter.class);

	public static InitialContext initCtx = null;
	public static TransactionInfoConverter converterObj = new TransactionInfoConverter();

	public static HashMap<String, Integer> TRANSACTION_TYPE_MAP = null;
	public static HashMap<String, Integer> TRANSACTION_CHANNEL_MAP = null;
	public static HashMap<String, Integer> TRANSACTION_MODE_MAP = null;
	public static HashMap<String, String> PRODUCT_GROUP_MAP = null;

	public static ExecutorService executor = null;
	public static HashMap<String, Integer> ENTITY_MASTER = null;
	public static Dedupe nameScreener = null; // new object created for screening against sanctionlists

	/**
	 * Changes made by Prathyusha M for namescreening addition Adding HashMap
	 * WHITE_LIST<>
	 */
	public static HashMap<Integer, ArrayList<String>> WHITE_WATCHLIST_DATA = null;


	/*
	 * need to call this method on start-up initialize database connection and jboss
	 * initial context object
	 * 
	 */
	public static void init(ServletContextEvent arg0) {
		executor = Executors.newCachedThreadPool();
		try {
			ConstantConfiguration.readConstantConfiguration();
			versionPrinter(arg0);
			nameScreener = new Dedupe(true);

			/*
			 * HashMap<Integer,HashMap<String,String>> screeningResult =
			 * nameScreener.getScreeningInfo("sanketh", null, "vijay", null, null, null,
			 * null, null, null, null, null, null, null, 123+"",0, "2019-02-26", "1");
			 * logger.info("screeningResult: "+screeningResult);
			 * logger.info("Final score: "+nameScreener.getFinalScoreForHashMap(
			 * screeningResult, "SCORE"));
			 */

			Properties masterClientProperties = getJbossProperties("localhost");
			context = new InitialContext(masterClientProperties);
			// txnBean = (TransactionBeanRemote)
			// context.lookup("java:/cfrt-fde-3.0/TransactionBean!bean.TransactionBeanRemote");
			rbdBean = (RuleBasedClueDetectorRemote) context
					.lookup("java:/cfrt-fde-3.0/RuleBasedDetector!clueDetectorFramework.RuleBasedClueDetectorRemote");
			DatabaseAdaptor obj = new DatabaseAdaptor();
			TRANSACTION_TYPE_MAP = obj.getTransactionTypeMap();
			TRANSACTION_CHANNEL_MAP = obj.getTransactionChannelMap();
			TRANSACTION_MODE_MAP = obj.getTransactionModeMap();
			PRODUCT_GROUP_MAP = obj.getProductGroupMap();
			ENTITY_MASTER = obj.getEntityMasterMap();
			WHITE_WATCHLIST_DATA = obj.loadWhiteListData();
		} catch (NamingException e) {
			logger.error("", e);
		} catch (Exception e) {
			logger.error("", e);
		}
		logger.info("CFRTAdapter :: init() beans initialized successfully");
	}

	public static void destroy() {
		rbdBean = null;
	//	txnProcessor = null;
		// txnBean = null;
		context = null;
		logger.info("CFRTAdapter :: destroy() beans destroyed successfully");
	}

	public String getFraudInfo(String message) {
		long inTime = System.currentTimeMillis();
		lastWSCall = inTime;
		long enrichTime = System.currentTimeMillis();
		TransactionInformation enrichedTxnInfo = EnrichTransaction.getEnrichedMessage(message);
		String score = null;
		if (enrichedTxnInfo != null) { 
			logger.info("Received Message" + enrichedTxnInfo);
			// TransactionCreate tFact = null;
			try {
				if (enrichedTxnInfo != null) {
					long startTime = System.currentTimeMillis();
					if (enrichedTxnInfo.getCustomerID() == null)
						enrichedTxnInfo.setCustomerID(enrichedTxnInfo.getCustomerProfile().getCustomerId());
					// tFact = txnBean.getTransactionCreate(enrichedTxnInfo);
					long factTime = System.currentTimeMillis();
					score = rbdBean.fire(enrichedTxnInfo);
					long scoreTime = System.currentTimeMillis();

					if (scoreTime - enrichTime >= 300) {
						logger.debug("Total time-" + (scoreTime - enrichTime) + " Enrich time-"
								+ (startTime - enrichTime) + " Fire time-" + (scoreTime - factTime) + "-" + message);
					}
				}
			} catch (Exception e) {
				logger.error("", e);
				score = getResponse("0.0", enrichedTxnInfo.getTransactionReferenceNumber() + "", "N/A", "0", "N/A",
						"51", "Error in processing");
			}
		} else {
			score = getResponse("0.0", "N/A", "N/A", "0", "N/A", "50", "Account ID not available");
		}
		logger.info(score + ",ProcessedTime=" + (System.currentTimeMillis() - inTime));
		return score;
	}

	public static Properties getJbossProperties(String ipAddress) {
		Properties props = new Properties();

		props.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
		props.put(Context.PROVIDER_URL, "remote://" + ipAddress);
		props.put("jboss.naming.client.ejb.context", true);
		props.put(Context.SECURITY_PRINCIPAL, "");
		props.put(Context.SECURITY_CREDENTIALS, "");
		props.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");

		props.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
		props.put(Context.PROVIDER_URL, "http-remoting://" + ipAddress + ":8085");
		props.put("remote.connection.default.connect.timeout", "50");
		props.put("jboss.naming.client.ejb.context", true);
		props.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
		props.put(Context.SECURITY_PRINCIPAL, "");
		props.put(Context.SECURITY_CREDENTIALS, "");

		return props;
	}

	/**
	 * To Print Version Details From Manifest File
	 * 
	 * @author rajarajan.raghavan
	 * @param arg0
	 */
	private static void versionPrinter(ServletContextEvent arg0) {
		String relativeWARPath = "/META-INF/MANIFEST.MF";
		String absoluteDiskPath = arg0.getServletContext().getRealPath(relativeWARPath);
		File file = new File(absoluteDiskPath);
		try {
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			StringBuffer stringBuffer = new StringBuffer();
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				stringBuffer.append(line);
				stringBuffer.append("\n");
			}
			fileReader.close();
			logger.info("\n" + stringBuffer.toString());
		} catch (IOException e) {
			logger.error("", e);
		}
	}

	/**
	 * @param score
	 * @param refNum
	 * @param scenario_name
	 * @param action_code
	 * @param action_desc
	 * @param cf_response_code
	 * @param cf_response_desc
	 * @return
	 */
	private String getResponse(String score, String refNum, String scenario_name, String action_code,
			String action_desc, String cf_response_code, String cf_response_desc) {
		String response = "score=" + score;
		response += ",scenario_name=" + scenario_name;
		response += ",action_code=" + action_code;
		response += ",action_desc=" + action_desc;
		response += ",reference_number=" + refNum;
		response += ",cf_response_code=" + cf_response_code;
		response += ",cf_response_desc=" + cf_response_desc;
		return response;
	}

	//Added  new methods updateTransctionStatus and getTrxnInfo
	
 	public void updateTransctionStatus(String cbsResponse) {	
 		DatabaseAdaptor obj = new DatabaseAdaptor();
 		TransactionInformation enrichedTxnInfo = EnrichTransaction.getEnrichedMessage(cbsResponse);
 		logger.info("Trxn Status= "+enrichedTxnInfo.getResponseCode()+" Trxn Account ID= "+enrichedTxnInfo.getAccountID());
 	//	logger.info("getTransactionReferenceNumber="+enrichedTxnInfo.getTransactionReferenceNumber() );
 		logger.info("getAccountEntrySrNo="+enrichedTxnInfo.getAccountEntrySrNo() );
 		try {
			/*
			 * if(enrichedTxnInfo.getAccountEntrySrNo() != null) {
			 * enrichedTxnInfo.setTransactionReferenceNumber(Long.parseLong(enrichedTxnInfo.
			 * getAccountEntrySrNo())); }
			 */
 		//obj.updateTransactionStatus(enrichedTxnInfo.getResponseCode(), enrichedTxnInfo.getTransactionReferenceNumber(),enrichedTxnInfo.getTransactionChannel());
 			obj.updateTransactionStatus(enrichedTxnInfo.getResponseCode(),enrichedTxnInfo.getAccountEntrySrNo(),enrichedTxnInfo.getTransactionChannel());
 		 			
 		
 		if(enrichedTxnInfo.getResponseCode() !=null && 
 				(enrichedTxnInfo.getResponseCode().equalsIgnoreCase("00") || enrichedTxnInfo.getResponseCode().equalsIgnoreCase("SUCCESS"))) {
 			return ;
		}
 		//	if(enrichedTxnInfo.getTransactionReferenceNumber() > 0) {
 		if(enrichedTxnInfo.getAccountEntrySrNo() != null) {
 		rbdBean.deleteTrxnFromMemory(getTrxnInfo(enrichedTxnInfo));
 			}
 			
 		}catch(Exception e) {
 			logger.error("Exception while update");
 		}
 	}
 
 public TransactionInformation getTrxnInfo(TransactionInformation enrichedTxnInfo) {
	 if(enrichedTxnInfo.getCustomerID()==null || enrichedTxnInfo.getCustomerID().equals(""))
		 enrichedTxnInfo.setCustomerID(new DatabaseAdaptor().getCustomerId(enrichedTxnInfo.getAccountID()) );
		try {
		if (enrichedTxnInfo != null) {
			if (enrichedTxnInfo.getCustomerID() == null || enrichedTxnInfo.getCustomerID().equals(""))
				enrichedTxnInfo.setCustomerID(enrichedTxnInfo.getCustomerProfile().getCustomerId());
			
			if (enrichedTxnInfo.getAccountID() == null || enrichedTxnInfo.getAccountID().equals("")) {
                  enrichedTxnInfo.setAccountID(enrichedTxnInfo.getCustomerProfile().getAccountId());
               }
		}
		}catch (Exception e) {
			logger.error("enrichedTxnInfo delete :" + enrichedTxnInfo.toString());
			logger.error("",e);
		}
		return enrichedTxnInfo;
 }

}
