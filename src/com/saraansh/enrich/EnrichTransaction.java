/**
 * 
 */
package com.saraansh.enrich;

import static main.CFRTAdapter.ENTITY_MASTER;
import static main.CFRTAdapter.TRANSACTION_CHANNEL_MAP;
import static main.CFRTAdapter.TRANSACTION_MODE_MAP;
import static main.CFRTAdapter.TRANSACTION_TYPE_MAP;
import static main.CFRTAdapter.WHITE_WATCHLIST_DATA;
import static main.CFRTAdapter.converterObj;
import static main.CFRTAdapter.executor;
import static main.CFRTAdapter.logger;
import static main.CFRTAdapter.nameScreener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.apache.commons.lang3.StringUtils;

import com.manipal.deduplication.ConstantConfiguration;
import com.saraansh.database.LoadProfilerData;
import com.saraansh.profiler.LoadCustomerProfile;

import dao.CustomerProfile;
import dao.ProfilerData;
import dao.TransactionInformation;
import main.CFRTAdapter;

/**
 * @author sanketh.v
 *
 */
public class EnrichTransaction {

	public static TransactionInformation getEnrichedMessage(String message) {
		long startTime = System.currentTimeMillis();
		ProfilerData profilerData = new ProfilerData();
		Map<String, CustomerProfile> customerProfileMap = new HashMap<>();
		List<String> account_id_list = new ArrayList<String>();
		message = "<TRANSACTION_INFO xmlns=\"http://testXmlParse\">" + message + "</TRANSACTION_INFO>";
		StringBuffer txnAsXML = new StringBuffer(message);
		TransactionInformation transactionInformation = converterObj.convertToTransactionInformation(txnAsXML);

		String typeKey = transactionInformation.getTransactionType();
		logger.info("typeKey" + typeKey);
		String modeKey = transactionInformation.getTransactionMode();
		logger.info("modeKey" + modeKey);
		if (modeKey.equals(""))
			modeKey = "NA";
		String channelKey = transactionInformation.getTransactionChannel();
		logger.info("channelKey" + channelKey);
		String txnDateTime = StringUtils.substringBetween(txnAsXML.toString(), "<TRANSACTION_DATE_TIME>",
				"</TRANSACTION_DATE_TIME>");
		try {
			logger.info(TRANSACTION_MODE_MAP.get(modeKey));
			if (TRANSACTION_TYPE_MAP.get(typeKey) == null || TRANSACTION_CHANNEL_MAP.get(channelKey) == null) {
				logger.warn("Unknown channel or type -" + typeKey + "-" + channelKey);
				return null;
			}
		} catch (Exception e1) {
			logger.info("exception line" + e1);
			logger.fatal("", e1);
			return null;
		}
		// TransactionInformation obj = txnInfo;
		/*
		 * DatabaseAdaptor dbObj = new DatabaseAdaptor(); Thread t1 = new Thread(new
		 * Runnable() { public void run() { dbObj.insertIntoFTTable(obj); } });
		 * t1.start();
		 */

		account_id_list.add(transactionInformation.getAccountID());
		if (transactionInformation.getCounterpartAccountID() != null) {
			account_id_list.add(transactionInformation.getCounterpartAccountID());
		}
		/*
		 * Added by Ganesh used the executor to span thread for lookup CP and Profiler
		 * 07th August 2018
		 */
		@SuppressWarnings("unchecked")
		Future<Map<String, CustomerProfile>> cpTask = executor.submit(new LoadCustomerProfile(account_id_list));
		String entityNumber = "";
		int entityId;
		if (channelKey.equalsIgnoreCase("ATM") || channelKey.equalsIgnoreCase("EDC")
				|| channelKey.equalsIgnoreCase("Ecommerce")) {
			entityNumber = transactionInformation.getCardNumber();
			entityId = ENTITY_MASTER.get("Card_number");
		} else {
			entityNumber = transactionInformation.getAccountID();
			entityId = ENTITY_MASTER.get("Account_id");
		}
		@SuppressWarnings("unchecked")
		Future<ProfilerData> profilerTask = executor.submit(new LoadProfilerData(entityNumber,
				TRANSACTION_CHANNEL_MAP.get(channelKey), TRANSACTION_MODE_MAP.get(modeKey), entityId));
		/*
		 * @SuppressWarnings("unchecked") Future ftTask = executor.submit(new
		 * InsertIntoFTRT(obj)); executor.submit(dbObj.insertIntoFTTable(obj));
		 */

		try {
			customerProfileMap = cpTask.get();
			logger.info("customerProfileMap: " + customerProfileMap);
			profilerData = profilerTask.get();
			logger.info("profilerData: " + profilerData);
		} catch (InterruptedException | ExecutionException e) {
			logger.error(e);
		}
		CustomerProfile counterpartCustomerProfile = new CustomerProfile();
		if (transactionInformation.getCounterpartAccountID() != null
				&& !transactionInformation.getCounterpartAccountID().equals("")) {
			/*********************
			 * COUNTERPART CUSTOMER INFORMATION STARTS HERE
			 **************************/
			int counterpartAccountAgeInDays = 0;
			int counterpartAccountHolderAge = 0;
			if (transactionInformation.getCounterpartAccountID() != null) {
				counterpartCustomerProfile = customerProfileMap.get(transactionInformation.getCounterpartAccountID());
			}
			logger.info("counterpartCustomerProfile:" + counterpartCustomerProfile);
			if (counterpartCustomerProfile != null) {
				counterpartAccountAgeInDays = Utility.getDateDiff(txnDateTime,
						counterpartCustomerProfile.getProfileCreationDate());
				counterpartCustomerProfile.setAccountAgeInDays(counterpartAccountAgeInDays);

				if (counterpartCustomerProfile.getDOB() != null && !counterpartCustomerProfile.getDOB().equals("")) {
					counterpartAccountHolderAge = Utility.calculateAgeOfCustomer(counterpartCustomerProfile.getDOB(),
							txnDateTime);
					counterpartCustomerProfile.setAccountHolderAge(counterpartAccountHolderAge);
				}
			}
			/*********************
			 * COUNTERPART CUSTOMER INFORMATION ENDS HERE
			 **************************/
		}
		transactionInformation.setCounterpartCustomerProfile(counterpartCustomerProfile);

		/*********************
		 * CURRENT TRANSACTION CUSTOMER INFORMATION STARTS HERE
		 **************************/
		CustomerProfile customerProfile = customerProfileMap.get(transactionInformation.getAccountID().trim());
		logger.info("customerProfile:" + customerProfile.toString());
		int accountAgeInDays = 0;
		String dob;
		int customerAge = 0;

		if (customerProfile != null) {
			accountAgeInDays = Utility.getDateDiff(txnDateTime, customerProfile.getProfileCreationDate());
			customerProfile.setAccountAgeInDays(accountAgeInDays);
			dob = customerProfile.getDOB();
			if (dob != null && !dob.equals(""))
				customerAge = Utility.calculateAgeOfCustomer(dob, txnDateTime);
			customerProfile.setAccountHolderAge(customerAge);
		} else {
			logger.trace("Account ID is not available in DB-" + account_id_list);
			return null;
		}
		transactionInformation.setCustomerProfile(customerProfile);
		/*********************
		 * CURRENT TRANSACTION CUSTOMER INFORMATION ENDS HERE
		 **************************/

		/*********************
		 * PROFILER INFORMATION STARTS HERE
		 **************************/
		if (profilerData != null) {
			if (entityId == CFRTAdapter.ENTITY_MASTER.get("Account_id")) {
				String MaxDateFromList = Utility
						.checkMaxDateFromTransactedDateList(profilerData.getCountryOccurrenceByAcct());
				String MaxIpOccuranceDateFromList = Utility
						.checkMaxDateFromTransactedDateList(profilerData.getIpOccurrenceByAcct());
				int lastDebitTransactedDay = Utility.getDateDiff(txnDateTime, MaxDateFromList);
				int lastIPAddressUsageInDays = Utility.getDateDiff(txnDateTime, MaxIpOccuranceDateFromList);
				profilerData.setLastDebitTransactedDay(lastDebitTransactedDay);
				profilerData.setLastIPAddressUsageInDays(lastIPAddressUsageInDays);
			}
		}
		transactionInformation.setProfilerData(profilerData);

		// setting sanction's list screen information
		setSanctionListInfoForCustomerName(transactionInformation,
				getFinalScoreFromIndividualList(transactionInformation, transactionInformation.getCustomerProfile()));
		setSanctionListInfoForCounterpartName(transactionInformation, getFinalScoreFromIndividualList(
				transactionInformation, transactionInformation.getCounterpartCustomerProfile()));
		logger.trace("Enrich time -" + (System.currentTimeMillis() - startTime));
		logger.info("transactionInformation:" + transactionInformation);
		return transactionInformation;
	}

	private static HashMap<String, Double> getFinalScoreFromIndividualList(
			TransactionInformation enrichedTransactionInformation, CustomerProfile custProfile) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (custProfile != null && custProfile.getCustomerName() != null && WHITE_WATCHLIST_DATA != null
				&& !WHITE_WATCHLIST_DATA.get(8).contains(custProfile.getCustomerName())) {
			long start = System.currentTimeMillis();
			HashMap<Integer, HashMap<String, String>> screeningResult = nameScreener.getScreeningInfo(
					custProfile.getCustomerName().split("#")[0], null, custProfile.getCustomerName().split("#")[1],
					null, null, null, null, null, null, null, null, null, null,
					enrichedTransactionInformation.getTransactionReferenceNumber() + "", 0,
					sdf.format(enrichedTransactionInformation.getTransactionDateTime()), custProfile.getCustomerType());
			logger.info("screeningResult :" + screeningResult);
			if (nameScreener.getFinalScoreForHashMap(screeningResult,
					"SCORE") > ConstantConfiguration.FinalScoreThresholdIndividual)
				enrichedTransactionInformation.setCustomerNameInSanctionList(true);
			logger.info("Time taken to screen across sanction list:" + (System.currentTimeMillis() - start));
			logger.info("nameScreener.getFinalScoreFromIndividualList(screeningResult) :" + nameScreener.getFinalScoreFromIndividualList(screeningResult));
			return nameScreener.getFinalScoreFromIndividualList(screeningResult);
		}
		return null;
	}

	private static void setSanctionListInfoForCustomerName(TransactionInformation enrichedTransactionInformation,
			HashMap<String, Double> individualScoreMap) {
		logger.info("individualScoreMap-" + individualScoreMap);
		if (individualScoreMap != null) {
			if (individualScoreMap.get("OFAC") != null
					&& individualScoreMap.get("OFAC") > ConstantConfiguration.FinalScoreThresholdIndividual)
				enrichedTransactionInformation.setCustomerNameInOFACList(true);
			if (individualScoreMap.get("UN") != null
					&& individualScoreMap.get("UN") > ConstantConfiguration.FinalScoreThresholdIndividual)
				enrichedTransactionInformation.setCustomerNameInUNList(true);
			if (individualScoreMap.get("PEP") != null
					&& individualScoreMap.get("PEP") > ConstantConfiguration.FinalScoreThresholdIndividual)
				enrichedTransactionInformation.setCustomerNameInPEPList(true);
		}
	}

	private static void setSanctionListInfoForCounterpartName(TransactionInformation enrichedTransactionInformation,
			HashMap<String, Double> individualScoreMap) {
		if (individualScoreMap != null) {
			if (individualScoreMap.get("OFAC") != null
					&& individualScoreMap.get("OFAC") > ConstantConfiguration.FinalScoreThresholdIndividual)
				enrichedTransactionInformation.setCounterpartNameInOFACList(true);
			if (individualScoreMap.get("UN") != null
					&& individualScoreMap.get("UN") > ConstantConfiguration.FinalScoreThresholdIndividual)
				enrichedTransactionInformation.setCounterpartNameInUNList(true);
			if (individualScoreMap.get("PEP") != null
					&& individualScoreMap.get("PEP") > ConstantConfiguration.FinalScoreThresholdIndividual)
				enrichedTransactionInformation.setCounterpartNameInPEPList(true);
		}
	}

	/*
	 * public static TransactionInformation getEnrichedMessage(Transaction txn) {
	 * long startTime = System.currentTimeMillis(); ProfilerData profilerData = new
	 * ProfilerData(); Map<String, CustomerProfile> customerProfileMap = new
	 * HashMap<>(); List<String> account_id_list = new ArrayList<String>();
	 * List<String> customer_id_list = new ArrayList<String>();
	 * TransactionInformation transactionInformation = null;
	 * 
	 * try { transactionInformation =
	 * converterObj.convertToTransactionInformation(txn); if (transactionInformation
	 * == null) return null; } catch (Exception e2) {
	 * logger.error("Unable to convert TransactionInformation-"+txn); return null; }
	 * 
	 * String typeKey = transactionInformation.getTransactionType(); String modeKey
	 * = transactionInformation.getTransactionMode(); if (modeKey==null ||
	 * modeKey.equals("")) modeKey = "NA"; String channelKey =
	 * transactionInformation.getTransactionChannel(); String txnDateTime =
	 * txn.getTxnDateTime();
	 * 
	 * try { logger.info(TRANSACTION_MODE_MAP.get(modeKey)); if
	 * (TRANSACTION_TYPE_MAP.get(typeKey) == null ||
	 * TRANSACTION_CHANNEL_MAP.get(channelKey) == null) {
	 * logger.warn("Unknown channel or type -"+typeKey+"-"+channelKey); return null;
	 * } } catch (Exception e1) { logger.fatal("", e1); return null; }
	 * 
	 * //TransactionInformation obj = txnInfo; DatabaseAdaptor dbObj = new
	 * DatabaseAdaptor(); Thread t1 = new Thread(new Runnable() { public void run()
	 * { dbObj.insertIntoFTTable(obj); } }); t1.start();
	 * 
	 * 
	 * account_id_list.add(transactionInformation.getAccountID());
	 * if(transactionInformation.getCounterpartAccountID() != null) {
	 * account_id_list.add(transactionInformation.getCounterpartAccountID()); }
	 * customer_id_list.add(transactionInformation.getCustomerID());
	 * 
	 * Added by Ganesh used the executor to span thread for lookup CP and Profiler
	 * 07th August 2018
	 * 
	 * @SuppressWarnings("unchecked") Future<Map<String, CustomerProfile>> cpTask =
	 * executor.submit(new LoadCustomerProfile(account_id_list,customer_id_list));
	 * //Future<Map<String, CustomerProfile>> cpTask = executor.submit(new
	 * LoadCustomerProfile(account_id_list)); NormalizationDTO normDto = new
	 * NormalizationDTO(); try { normDto = executor.submit(new
	 * LoadNormalizationFactor(transactionInformation.getAccountID())).get(); }
	 * catch (InterruptedException | ExecutionException e) { logger.error("",e); }
	 * 
	 * transactionInformation.setNormDto(normDto); String entityNumber = ""; int
	 * entityId; if (channelKey.equalsIgnoreCase("ATM") ||
	 * channelKey.equalsIgnoreCase("EDC") ||
	 * channelKey.equalsIgnoreCase("Ecommerce")) { entityNumber =
	 * transactionInformation.getCardNumber(); entityId =
	 * ENTITY_MASTER.get("Card_number"); } else { entityNumber =
	 * transactionInformation.getAccountID(); entityId =
	 * ENTITY_MASTER.get("Account_id"); }
	 * 
	 * @SuppressWarnings("unchecked") Future<ProfilerData> profilerTask =
	 * executor.submit(new LoadProfilerData(entityNumber,
	 * TRANSACTION_CHANNEL_MAP.get(channelKey), TRANSACTION_MODE_MAP.get(modeKey),
	 * entityId));
	 * 
	 * @SuppressWarnings("unchecked") Future ftTask = executor.submit(new
	 * InsertIntoFTRT(obj)); executor.submit(dbObj.insertIntoFTTable(obj));
	 * 
	 * try { customerProfileMap = cpTask.get(); profilerData = profilerTask.get(); }
	 * catch (InterruptedException | ExecutionException e) { logger.error(e); }
	 * CustomerProfile counterpartCustomerProfile = new CustomerProfile();
	 * if(transactionInformation.getCounterpartAccountID()!=null &&
	 * !transactionInformation.getCounterpartAccountID().equals("")) {
	 *//*********************
		 * COUNTERPART CUSTOMER INFORMATION STARTS HERE
		 **************************/
	/*
	 * int counterpartAccountAgeInDays = 0; int counterpartAccountHolderAge = 0;
	 * if(transactionInformation.getCounterpartAccountID() != null) {
	 * counterpartCustomerProfile =
	 * customerProfileMap.get(transactionInformation.getCounterpartAccountID()); }
	 * 
	 * if (counterpartCustomerProfile != null) { counterpartAccountAgeInDays =
	 * Utility.getDateDiff(txnDateTime,
	 * counterpartCustomerProfile.getProfileCreationDate());
	 * counterpartCustomerProfile.setAccountAgeInDays(counterpartAccountAgeInDays);
	 * 
	 * if (counterpartCustomerProfile.getDOB() != null &&
	 * !counterpartCustomerProfile.getDOB().equals("")) {
	 * counterpartAccountHolderAge =
	 * Utility.calculateAgeOfCustomer(counterpartCustomerProfile.getDOB(),
	 * txnDateTime);
	 * counterpartCustomerProfile.setAccountHolderAge(counterpartAccountHolderAge);
	 * } }
	 * 
	 *//*********************
		 * COUNTERPART CUSTOMER INFORMATION ENDS HERE
		 **************************/
	/*
	 * } transactionInformation.setCounterpartCustomerProfile(
	 * counterpartCustomerProfile);
	 * 
	 *//*********************
		 * CURRENT TRANSACTION CUSTOMER INFORMATION STARTS HERE
		 **************************/
	/*
	 * CustomerProfile customerProfile =
	 * customerProfileMap.get(transactionInformation.getAccountID().trim()); if
	 * (customerProfile == null) { customerProfile =
	 * (CustomerProfile)((Map)customerProfileMap).get(transactionInformation.
	 * getCustomerID().trim()); } int accountAgeInDays = 0; String dob; int
	 * customerAge = 0;
	 * 
	 * if (customerProfile != null) { accountAgeInDays =
	 * Utility.getDateDiff(txnDateTime, customerProfile.getProfileCreationDate());
	 * customerProfile.setAccountAgeInDays(accountAgeInDays);
	 * 
	 * dob = customerProfile.getDOB(); if (dob != null && !dob.equals(""))
	 * customerAge = Utility.calculateAgeOfCustomer(dob, txnDateTime);
	 * customerProfile.setAccountHolderAge(customerAge); } else {
	 * logger.trace("Account ID is not available in DB-"+account_id_list); return
	 * null; } transactionInformation.setCustomerProfile(customerProfile);
	 *//*********************
		 * CURRENT TRANSACTION CUSTOMER INFORMATION ENDS HERE
		 **************************/
	/*
	
	*//*********************
		 * PROFILER INFORMATION STARTS HERE
		 **************************//*
									 * if (profilerData != null) {
									 * 
									 * if (entityId == CFRTAdapter.ENTITY_MASTER.get("Account_id")) { //String
									 * MaxDateFromList = Utility.checkMaxDateFromTransactedDateList(profilerData.
									 * getCountryOccurrenceByAcct()); String
									 * internetOccurence="",mobileoccurence="",MaxIpOccuranceDateFromList="",
									 * MaxLastdebitTxnList="",MaxLastDebitTxnListFromEcommerce="";
									 * if(profilerData.getHourSlotByAcctFromInternet()!=null &&
									 * !profilerData.getHourSlotByAcctFromInternet().equals("") &&
									 * !profilerData.getHourSlotByAcctFromInternet().equals("null"))
									 * internetOccurence=profilerData.getHourSlotByAcctFromInternet();
									 * if(profilerData.getHourSlotByAcctFromMobile()!=null &&
									 * !profilerData.getHourSlotByAcctFromMobile().equals("") &&
									 * !profilerData.getHourSlotByAcctFromMobile().equals("null"))
									 * mobileoccurence=profilerData.getHourSlotByAcctFromMobile();
									 * 
									 * if(!internetOccurence.equals("") && !mobileoccurence.equals(""))
									 * MaxIpOccuranceDateFromList =
									 * Utility.checkMaxDateFromTransactedDateList(internetOccurence+","+
									 * mobileoccurence); else if(internetOccurence.equals(""))
									 * MaxIpOccuranceDateFromList =
									 * Utility.checkMaxDateFromTransactedDateList(mobileoccurence); else
									 * if(mobileoccurence.equals("")) MaxIpOccuranceDateFromList =
									 * Utility.checkMaxDateFromTransactedDateList(internetOccurence);
									 * 
									 * 
									 * MaxLastdebitTxnList =
									 * Utility.checkMaxDateFromTransactedDateList(profilerData.
									 * getHourSlotByAcctAcrossChannel()); //int lastDebitTransactedDay =
									 * Utility.getDateDiff(txnDateTime, MaxDateFromList); int
									 * lastIPAddressUsageInDays = Utility.getDateDiff(txnDateTime,
									 * MaxIpOccuranceDateFromList); int lastDebitTransactedDaysInHistoryByAccount =
									 * Utility.getDateDiff(txnDateTime, MaxLastdebitTxnList);
									 * //profilerData.setLastDebitTransactedDay(lastDebitTransactedDay);
									 * profilerData.setLastIPAddressUsageInDays(lastIPAddressUsageInDays);
									 * profilerData.setLastDebitTransactedDaysInHistoryByAccount(
									 * lastDebitTransactedDaysInHistoryByAccount);
									 * 
									 * profilerData.setNoOfDebitTransactedMonthsInHistoryByAcctAcrossChannel(
									 * getTxtdValuesInaMonthOrWeek(profilerData.
									 * getNoOfDebitTxtdMonthsInHistoryByAcctAcrossChannel()));
									 * profilerData.setNoOfCreditTransactedMonthsInHistoryByAcctAcrossChannel(
									 * getTxtdValuesInaMonthOrWeek(profilerData.
									 * getNoOfCreditTxtdMonthsInHistoryByAcctAcrossChannel()));
									 * profilerData.setNoOfDebitTransactedMonthsInHistoryByAcctFromInternet(
									 * getTxtdValuesInaMonthOrWeek(profilerData.
									 * getNoOfDebitTxtdMonthsInHistoryByAcctFromInternet()));
									 * profilerData.setNoOfDebitTransactedMonthsInHistoryByAcctFromMobile(
									 * getTxtdValuesInaMonthOrWeek(profilerData.
									 * getNoOfDebitTxtdMonthsInHistoryByAcctFromMobile()));
									 * profilerData.setNoOfDebitTransactedMonthsInHistoryByAcctFromTeller(
									 * getTxtdValuesInaMonthOrWeek(profilerData.
									 * getNoOfDebitTxtdMonthsInHistoryByAcctFromTeller()));
									 * profilerData.setNoOfCreditTransactedMonthsInHistoryByAcctFromInternet(
									 * getTxtdValuesInaMonthOrWeek(profilerData.
									 * getNoOfCreditTxtdMonthsInHistoryByAcctFromInternet()));
									 * profilerData.setNoOfCreditTransactedMonthsInHistoryByAcctFromMobile(
									 * getTxtdValuesInaMonthOrWeek(profilerData.
									 * getNoOfCreditTxtdMonthsInHistoryByAcctFromMobile()));
									 * profilerData.setNoOfCreditTransactedMonthsInHistoryByAcctFromTeller(
									 * getTxtdValuesInaMonthOrWeek(profilerData.
									 * getNoOfCreditTxtdMonthsInHistoryByAcctFromTeller()));
									 * 
									 * 
									 * profilerData.setNoOfDebitTransactedWeeklyInHistoryByAcctAcrossChannel(
									 * getTxtdValuesInaMonthOrWeek(profilerData.
									 * getNoOfDebitTxtdWeeklyInHistoryByAcctAcrossChannel()));
									 * profilerData.setNoOfCreditTransactedWeeklyInHistoryByAcctAcrossChannel(
									 * getTxtdValuesInaMonthOrWeek(profilerData.
									 * getNoOfCreditTxtdWeeklyInHistoryByAcctAcrossChannel()));
									 * profilerData.setNoOfDebitTransactedWeeklyInHistoryByAcctFromInternet(
									 * getTxtdValuesInaMonthOrWeek(profilerData.
									 * getNoOfDebitTxtdWeeklyInHistoryByAcctFromInternet()));
									 * profilerData.setNoOfDebitTransactedWeeklyInHistoryByAcctFromMobile(
									 * getTxtdValuesInaMonthOrWeek(profilerData.
									 * getNoOfDebitTxtdWeeklyInHistoryByAcctFromMobile()));
									 * profilerData.setNoOfDebitTransactedWeeklyInHistoryByAcctFromTeller(
									 * getTxtdValuesInaMonthOrWeek(profilerData.
									 * getNoOfDebitTxtdWeeklyInHistoryByAcctFromTeller()));
									 * profilerData.setNoOfCreditTransactedWeeklyInHistoryByAcctFromInternet(
									 * getTxtdValuesInaMonthOrWeek(profilerData.
									 * getNoOfCreditTxtdWeeklyInHistoryByAcctFromInternet()));
									 * profilerData.setNoOfCreditTransactedWeeklyInHistoryByAcctFromMobile(
									 * getTxtdValuesInaMonthOrWeek(profilerData.
									 * getNoOfCreditTxtdWeeklyInHistoryByAcctFromMobile()));
									 * profilerData.setNoOfCreditTransactedWeeklyInHistoryByAcctFromTeller(
									 * getTxtdValuesInaMonthOrWeek(profilerData.
									 * getNoOfCreditTxtdWeeklyInHistoryByAcctFromTeller()));
									 * 
									 * }
									 * 
									 * if (entityId == CFRTAdapter.ENTITY_MASTER.get("Card_number")) { String
									 * MaxLastDebitTxnListFromEcommerce =
									 * Utility.checkMaxDateFromTransactedDateList(profilerData.
									 * getHourSlotByCardFromEcommerce()); String MaxLastdebitTxnList =
									 * Utility.checkMaxDateFromTransactedDateList(profilerData.
									 * getHourSlotByCardAcrossChannel()); int
									 * lastDebitTransactedDaysInHistoryBycardFromEcom =
									 * Utility.getDateDiff(txnDateTime, MaxLastDebitTxnListFromEcommerce); int
									 * lastDebitTransactedDaysInHistoryByCard = Utility.getDateDiff(txnDateTime,
									 * MaxLastdebitTxnList);
									 * profilerData.setNoOfDebitTransactedDaysInHistoryByCardFromEcommerce(
									 * lastDebitTransactedDaysInHistoryBycardFromEcom);
									 * profilerData.setLastDebitTransactedDaysInHistoryByCard(
									 * lastDebitTransactedDaysInHistoryByCard);
									 * 
									 * profilerData.setNoOfDebitTransactedMonthsInHistoryByCardAcrossChannel(
									 * getTxtdValuesInaMonthOrWeek(profilerData.
									 * getNoOfDebitTxtdMonthsInHistoryByCardAcrossChannel()));
									 * profilerData.setNoOfCreditTransactedMonthsInHistoryByCardAcrossChannel(
									 * getTxtdValuesInaMonthOrWeek(profilerData.
									 * getNoOfCreditTxtdMonthsInHistoryByCardAcrossChannel()));
									 * profilerData.setNoOfDebitTransactedMonthsInHistoryByCardFromATM(
									 * getTxtdValuesInaMonthOrWeek(profilerData.
									 * getNoOfDebitTxtdMonthsInHistoryByCardFromATM()));
									 * profilerData.setNoOfDebitTransactedMonthsInHistoryByCardFromEDC(
									 * getTxtdValuesInaMonthOrWeek(profilerData.
									 * getNoOfDebitTxtdMonthsInHistoryByCardFromEDC()));
									 * profilerData.setNoOfDebitTransactedMonthsInHistoryByCardFromEcommerce(
									 * getTxtdValuesInaMonthOrWeek(profilerData.
									 * getNoOfDebitTxtdMonthsInHistoryByCardFromEcommerce()));
									 * profilerData.setNoOfCreditTransactedMonthsInHistoryByCardFromATM(
									 * getTxtdValuesInaMonthOrWeek(profilerData.
									 * getNoOfCreditTxtdMonthsInHistoryByCardFromATM()));
									 * profilerData.setNoOfCreditTransactedMonthsInHistoryByCardFromEDC(
									 * getTxtdValuesInaMonthOrWeek(profilerData.
									 * getNoOfCreditTxtdMonthsInHistoryByCardFromEDC()));
									 * profilerData.setNoOfCreditTransactedMonthsInHistoryByCardFromEcommerce(
									 * getTxtdValuesInaMonthOrWeek(profilerData.
									 * getNoOfCreditTxtdMonthsInHistoryByCardFromEcommerce()));
									 * 
									 * 
									 * profilerData.setNoOfDebitTransactedWeeklyInHistoryByCardAcrossChannel(
									 * getTxtdValuesInaMonthOrWeek(profilerData.
									 * getNoOfDebitTxtdWeeklyInHistoryByCardAcrossChannel()));
									 * profilerData.setNoOfCreditTransactedWeeklyInHistoryByCardAcrossChannel(
									 * getTxtdValuesInaMonthOrWeek(profilerData.
									 * getNoOfCreditTxtdWeeklyInHistoryByCardAcrossChannel()));
									 * profilerData.setNoOfDebitTransactedWeeklyInHistoryByCardFromATM(
									 * getTxtdValuesInaMonthOrWeek(profilerData.
									 * getNoOfDebitTxtdWeeklyInHistoryByCardFromATM()));
									 * profilerData.setNoOfDebitTransactedWeeklyInHistoryByCardFromEDC(
									 * getTxtdValuesInaMonthOrWeek(profilerData.
									 * getNoOfDebitTxtdWeeklyInHistoryByCardFromEDC()));
									 * profilerData.setNoOfDebitTransactedWeeklyInHistoryByCardFromEcommerce(
									 * getTxtdValuesInaMonthOrWeek(profilerData.
									 * getNoOfDebitTxtdWeeklyInHistoryByCardFromEcommerce()));
									 * profilerData.setNoOfCreditTransactedWeeklyInHistoryByCardFromATM(
									 * getTxtdValuesInaMonthOrWeek(profilerData.
									 * getNoOfCreditTxtdWeeklyInHistoryByCardFromATM()));
									 * profilerData.setNoOfCreditTransactedWeeklyInHistoryByCardFromEDC(
									 * getTxtdValuesInaMonthOrWeek(profilerData.
									 * getNoOfCreditTxtdWeeklyInHistoryByCardFromEDC()));
									 * profilerData.setNoOfCreditTransactedWeeklyInHistoryByCardFromEcommerce(
									 * getTxtdValuesInaMonthOrWeek(profilerData.
									 * getNoOfCreditTxtdWeeklyInHistoryByCardFromEcommerce()));
									 * 
									 * }
									 * 
									 * } transactionInformation.setProfilerData(profilerData);
									 * 
									 * //setting sanction's list screen information
									 * setSanctionListInfoForCustomerName(transactionInformation,
									 * getFinalScoreFromIndividualList(transactionInformation,
									 * transactionInformation.getCustomerProfile()));
									 * setSanctionListInfoForCounterpartName(transactionInformation,
									 * getFinalScoreFromIndividualList(transactionInformation,
									 * transactionInformation.getCounterpartCustomerProfile()));
									 * 
									 * logger.trace("Enrich time -"+(System.currentTimeMillis()-startTime)); return
									 * transactionInformation; }
									 */

}
