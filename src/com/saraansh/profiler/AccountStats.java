package com.saraansh.profiler;

import java.sql.ResultSet;
import java.util.HashMap;

import dao.ProfilerData;

/* Added by Ganesh
 * AccountStats defined the statistics and behavior values of a Account
 * As per Design, Required to Define 65 variables for Account Stats
 * 07th August 2018
*/

public class AccountStats {
	private HashMap<String, ProfilerData> acctProfileMap = new HashMap<String, ProfilerData>();
	private ProfilerData accountProfiler = new ProfilerData();

	public ProfilerData getAccountProfilerData(String entityNumber, Integer channel, Integer mode, ResultSet rs) {
		String entityNumberForStats = "";
		// across all channels
		int channelFromStats;
		try {
			entityNumberForStats = rs.getString("ENTITY_NUMBER").trim();
			channelFromStats = rs.getInt("CHANNEL");

			if (channelFromStats == channel && rs.getInt("TRANSACTION_MODE") == mode) {
				accountProfiler.setNoOfDebitTransactedDaysInHistoryByAcct(rs.getInt("NO_OF_DR_TXTD_DAYS"));
				accountProfiler.setTotalNoOfDebitTransactionsInHistoryByAcct(rs.getInt("TOTAL_NO_OF_DR_TXNS"));
				accountProfiler.setSumOfDebitTranscationAmountInHistoryByAcct(rs.getFloat("SUM_OF_DR_TXN_AMT"));
				
				/*if (rs.getString("MAX_DR_TXN_AMT_INFO") != null && !rs.getString("MAX_DR_TXN_AMT_INFO").equals(""))
					accountProfiler.setMaxDebitTransactionAmountInfoByAcct(
							Double.parseDouble(rs.getString("MAX_DR_TXN_AMT_INFO")
									.substring(rs.getString("MAX_DR_TXN_AMT_INFO").length() - 6)));
				*/
				 
				accountProfiler.setLastDeclinedtxnsOccurrenceByAcct(rs.getString("DECLINED_TXN_OCCURRENCE"));
				accountProfiler.setHourSlotByAcct(rs.getString("TIME_OCCURRENCE"));
				accountProfiler.setCurrencyOccurrenceByAcct(rs.getString("CURRENCY_OCCURRENCE"));
				accountProfiler.setCountryOccurrenceByAcct(rs.getString("COUNTRY_OCCURRENCE"));
				accountProfiler.setNodeOccurrenceByAcct(rs.getString("NODE_OCCURRENCE"));
				accountProfiler.setIpOccurrenceByAcct(rs.getString("IP_OCCURRENCE"));
				accountProfiler.setBeneficiaryOccurrenceByAcct(rs.getString("BENEFICIARY_OCCURRENCE"));
			}

			if (acctProfileMap.get(entityNumberForStats) == null) {
				accountProfiler.setNoOfDebitTransactedDaysInHistoryByAcctAcrossChannel(rs.getInt("NO_OF_DR_TXTD_DAYS"));
				accountProfiler.setTotalNoOfDebitTransactionsInHistoryByAcctAcrossChannel(rs.getInt("TOTAL_NO_OF_DR_TXNS"));
				accountProfiler.setSumOfDebitTranscationAmountInHistoryByAcctAcrossChannel(rs.getFloat("SUM_OF_DR_TXN_AMT"));
				
				/*if (rs.getString("MAX_DR_TXN_AMT_INFO") != null && !rs.getString("MAX_DR_TXN_AMT_INFO").equals(""))
					accountProfiler.setMaxDebitTransactionAmountInfoByAcctAcrossChannel(
							Double.parseDouble(rs.getString("MAX_DR_TXN_AMT_INFO")
									.substring(rs.getString("MAX_DR_TXN_AMT_INFO").length() - 6)));

				if (rs.getString("LAST_DR_TXN_INFO") != null && !rs.getString("LAST_DR_TXN_INFO").equals("")) {
					accountProfiler.setLastDebitTransactionAmountInfoByAcctAcrossChannel(Double.parseDouble(
							rs.getString("LAST_DR_TXN_INFO").substring(rs.getString("LAST_DR_TXN_INFO").length() - 6)));
				}*/

				accountProfiler.setLastDeclinedtxnsOccurrenceByAcctAcrossChannel(rs.getString("DECLINED_TXN_OCCURRENCE"));
				accountProfiler.setHourSlotByAcctAcrossChannel(rs.getString("TIME_OCCURRENCE"));
				accountProfiler.setCurrencyOccurrenceByAcctAcrossChannel(rs.getString("CURRENCY_OCCURRENCE"));
				accountProfiler.setCountryOccurrenceByAcctAcrossChannel(rs.getString("COUNTRY_OCCURRENCE"));
				accountProfiler.setNodeOccurrenceByAcctAcrossChannel(rs.getString("NODE_OCCURRENCE"));
				accountProfiler.setIpOccurrenceByAcctAcrossChannel(rs.getString("IP_OCCURRENCE"));
				accountProfiler.setBeneficiaryOccurrenceByAcctAcrossChannel(rs.getString("BENEFICIARY_OCCURRENCE"));

				//Added for NSDL By Abdul Hadi
				accountProfiler.setAvgMonthlyTransactionAmountAcrossChannel(rs.getFloat("AVG_MONTHLY_AMOUNT"));
				accountProfiler.setAvgMonthlyTransactionCountAcrossChannel(rs.getInt("AVG_MONTHLY_COUNT"));
				accountProfiler.setMccOccurrenceAcrossChannel(rs.getString("MCC_OCCURRENCE"));
				
				if (channelFromStats == 4) {
					accountProfiler
							.setNoOfDebitTransactedDaysInHistoryByAcctFromInternet(rs.getInt("NO_OF_DR_TXTD_DAYS"));
					accountProfiler
							.setTotalNoOfDebitTransactionsInHistoryByAcctFromInternet(rs.getInt("TOTAL_NO_OF_DR_TXNS"));
					accountProfiler.setSumOfDebitTranscationAmountInHistoryByAcctFromInternet(
							rs.getFloat("SUM_OF_DR_TXN_AMT"));

					/*
					 * if (rs.getString("MAX_DR_TXN_AMT_INFO") != null &&
					 * !rs.getString("MAX_DR_TXN_AMT_INFO").equals(""))
					 * accountProfiler.setMaxDebitTransactionAmountInHistoryByAcctFromInternet(
					 * Double.parseDouble(rs.getString("MAX_DR_TXN_AMT_INFO")
					 * .substring(rs.getString("MAX_DR_TXN_AMT_INFO").length() - 6)));
					 */

					accountProfiler
							.setLastDeclinedtxnsOccurrenceByAcctFromInternet(rs.getString("DECLINED_TXN_OCCURRENCE"));
					accountProfiler.setHourSlotByAcctFromInternet(rs.getString("TIME_OCCURRENCE"));
					accountProfiler.setCurrencyOccurrenceByAcctFromInternet(rs.getString("CURRENCY_OCCURRENCE"));
					accountProfiler.setCountryOccurrenceByAcctFromInternet(rs.getString("COUNTRY_OCCURRENCE"));
					accountProfiler.setNodeOccurrenceByAcctFromInternet(rs.getString("NODE_OCCURRENCE"));
					accountProfiler.setIpOccurrenceByAcctFromInternet(rs.getString("IP_OCCURRENCE"));
					accountProfiler.setBeneficiaryOccurrenceByAcctFromInternet(rs.getString("BENEFICIARY_OCCURRENCE"));

				} else if (channelFromStats == 5) {
					accountProfiler
							.setNoOfDebitTransactedDaysInHistoryByAcctFromTeller(rs.getInt("NO_OF_DR_TXTD_DAYS"));
					accountProfiler
							.setTotalNoOfDebitTransactionsInHistoryByAcctFromTeller(rs.getInt("TOTAL_NO_OF_DR_TXNS"));
					accountProfiler
							.setSumOfDebitTranscationAmountInHistoryByAcctFromTeller(rs.getFloat("SUM_OF_DR_TXN_AMT"));

					/*
					 * if (rs.getString("MAX_DR_TXN_AMT_INFO") != null &&
					 * !rs.getString("MAX_DR_TXN_AMT_INFO").equals(""))
					 * accountProfiler.setMaxDebitTransactionAmountInHistoryByAcctFromTeller(
					 * Double.parseDouble(rs.getString("MAX_DR_TXN_AMT_INFO")
					 * .substring(rs.getString("MAX_DR_TXN_AMT_INFO").length() - 6)));
					 */

					accountProfiler.setLastDeclinedtxnsOccurrenceByAcctFromTeller(rs.getString("DECLINED_TXN_OCCURRENCE"));
					accountProfiler.setHourSlotByAcctFromTeller(rs.getString("TIME_OCCURRENCE"));
					accountProfiler.setCurrencyOccurrenceByAcctFromTeller(rs.getString("CURRENCY_OCCURRENCE"));
					accountProfiler.setCountryOccurrenceByAcctFromTeller(rs.getString("COUNTRY_OCCURRENCE"));
					accountProfiler.setNodeOccurrenceByAcctFromTeller(rs.getString("NODE_OCCURRENCE"));
					accountProfiler.setIpOccurrenceByAcctFromTeller(rs.getString("IP_OCCURRENCE"));
					accountProfiler.setBeneficiaryOccurrenceByAcctFromTeller(rs.getString("BENEFICIARY_OCCURRENCE"));

				} else if (channelFromStats == 7) {
					accountProfiler
							.setNoOfDebitTransactedDaysInHistoryByAcctFromMobile(rs.getInt("NO_OF_DR_TXTD_DAYS"));
					accountProfiler
							.setTotalNoOfDebitTransactionsInHistoryByAcctFromMobile(rs.getInt("TOTAL_NO_OF_DR_TXNS"));
					accountProfiler
							.setSumOfDebitTranscationAmountInHistoryByAcctFromMobile(rs.getFloat("SUM_OF_DR_TXN_AMT"));

					/*
					 * if (rs.getString("MAX_DR_TXN_AMT_INFO") != null &&
					 * !rs.getString("MAX_DR_TXN_AMT_INFO").equals(""))
					 * accountProfiler.setMaxDebitTransactionAmountInHistoryByAcctFromMobile(
					 * Double.parseDouble(rs.getString("MAX_DR_TXN_AMT_INFO")
					 * .substring(rs.getString("MAX_DR_TXN_AMT_INFO").length() - 6)));
					 */

					accountProfiler
							.setLastDeclinedtxnsOccurrenceByAcctFromMobile(rs.getString("DECLINED_TXN_OCCURRENCE"));
					accountProfiler.setHourSlotByAcctFromMobile(rs.getString("TIME_OCCURRENCE"));
					accountProfiler.setCurrencyOccurrenceByAcctFromMobile(rs.getString("CURRENCY_OCCURRENCE"));
					accountProfiler.setCountryOccurrenceByAcctFromMobile(rs.getString("COUNTRY_OCCURRENCE"));
					accountProfiler.setNodeOccurrenceByAcctFromMobile(rs.getString("NODE_OCCURRENCE"));
					accountProfiler.setIpOccurrenceByAcctFromMobile(rs.getString("IP_OCCURRENCE"));
					accountProfiler.setBeneficiaryOccurrenceByAcctFromMobile(rs.getString("BENEFICIARY_OCCURRENCE"));

				}
				acctProfileMap.put(entityNumberForStats, accountProfiler);
			} else {
				accountProfiler = acctProfileMap.get(entityNumberForStats);

				accountProfiler.setNoOfDebitTransactedDaysInHistoryByAcctAcrossChannel(getStaticsValueFortheSameAccount(
						accountProfiler.getNoOfDebitTransactedDaysInHistoryByAcctAcrossChannel(),
						rs.getInt("NO_OF_DR_TXTD_DAYS")));

				accountProfiler
						.setTotalNoOfDebitTransactionsInHistoryByAcctAcrossChannel(getStaticsValueFortheSameAccount(
								accountProfiler.getTotalNoOfDebitTransactionsInHistoryByAcctAcrossChannel(),
								rs.getInt("TOTAL_NO_OF_DR_TXNS")));

				accountProfiler
						.setSumOfDebitTranscationAmountInHistoryByAcctAcrossChannel(getStaticsAmountFortheSameAccount(
								accountProfiler.getSumOfDebitTranscationAmountInHistoryByAcctAcrossChannel(),
								rs.getInt("SUM_OF_DR_TXN_AMT")));

				//Added for NSDL By Abdul Hadi
				accountProfiler.setAvgMonthlyTransactionAmountAcrossChannel(getStaticsAmountFortheSameAccount(
						accountProfiler.getAvgMonthlyTransactionAmountAcrossChannel(),
						rs.getFloat("AVG_MONTHLY_AMOUNT")));
				accountProfiler.setAvgMonthlyTransactionCountAcrossChannel(getStaticsValueFortheSameAccount(
						accountProfiler.getAvgMonthlyTransactionCountAcrossChannel(),
						rs.getInt("AVG_MONTHLY_COUNT")));
				accountProfiler.setMccOccurrenceAcrossChannel(getAcctStatsOccurrenceValue(
						accountProfiler.getMccOccurrenceAcrossChannel(), rs.getString("MCC_OCCURRENCE")));
				
				if (channelFromStats == 4) {
					// For only Internet specific variables
					accountProfiler
							.setNoOfDebitTransactedDaysInHistoryByAcctFromInternet(getStaticsValueFortheSameAccount(
									accountProfiler.getNoOfDebitTransactedDaysInHistoryByAcctFromInternet(),
									rs.getInt("NO_OF_DR_TXTD_DAYS")));

					accountProfiler
							.setTotalNoOfDebitTransactionsInHistoryByAcctFromInternet(getStaticsValueFortheSameAccount(
									accountProfiler.getTotalNoOfDebitTransactionsInHistoryByAcctFromInternet(),
									rs.getInt("TOTAL_NO_OF_DR_TXNS")));

					accountProfiler.setSumOfDebitTranscationAmountInHistoryByAcctFromInternet(
							getStaticsAmountFortheSameAccount(
									accountProfiler.getSumOfDebitTranscationAmountInHistoryByAcctFromInternet(),
									rs.getInt("SUM_OF_DR_TXN_AMT")));

					accountProfiler.setCountryOccurrenceByAcctFromInternet(
							getAcctStatsOccurrenceValue(accountProfiler.getCountryOccurrenceByAcctFromInternet(),
									rs.getString("COUNTRY_OCCURRENCE")));

					accountProfiler.setLastDeclinedtxnsOccurrenceByAcctFromInternet(
							getAcctStatsOccurrenceValue(accountProfiler.getLastDeclinedtxnsOccurrenceByAcctFromInternet(),
									rs.getString("DECLINED_TXN_OCCURRENCE")));

					accountProfiler.setHourSlotByAcctFromInternet(getAcctStatsOccurrenceValue(
							accountProfiler.getHourSlotByAcctFromInternet(), rs.getString("TIME_OCCURRENCE")));

					accountProfiler.setCurrencyOccurrenceByAcctFromInternet(
							getAcctStatsOccurrenceValue(accountProfiler.getCurrencyOccurrenceByAcctFromInternet(),
									rs.getString("CURRENCY_OCCURRENCE")));

					accountProfiler.setNodeOccurrenceByAcctFromInternet(getAcctStatsOccurrenceValue(
							accountProfiler.getNodeOccurrenceByAcctFromInternet(), rs.getString("NODE_OCCURRENCE")));

					accountProfiler.setIpOccurrenceByAcctFromInternet(getAcctStatsOccurrenceValue(
							accountProfiler.getIpOccurrenceByAcctFromInternet(), rs.getString("IP_OCCURRENCE")));

					accountProfiler.setBeneficiaryOccurrenceByAcctFromInternet(
							getAcctStatsOccurrenceValue(accountProfiler.getBeneficiaryOccurrenceByAcctFromInternet(),
									rs.getString("BENEFICIARY_OCCURRENCE")));

				} else if (channelFromStats == 5) {
					// For only Teller specific variables
					accountProfiler
							.setNoOfDebitTransactedDaysInHistoryByAcctFromTeller(getStaticsValueFortheSameAccount(
									accountProfiler.getNoOfDebitTransactedDaysInHistoryByAcctFromTeller(),
									rs.getInt("NO_OF_DR_TXTD_DAYS")));

					accountProfiler
							.setTotalNoOfDebitTransactionsInHistoryByAcctFromTeller(getStaticsValueFortheSameAccount(
									accountProfiler.getTotalNoOfDebitTransactionsInHistoryByAcctFromTeller(),
									rs.getInt("TOTAL_NO_OF_DR_TXNS")));

					accountProfiler
							.setSumOfDebitTranscationAmountInHistoryByAcctFromTeller(getStaticsAmountFortheSameAccount(
									accountProfiler.getSumOfDebitTranscationAmountInHistoryByAcctFromTeller(),
									rs.getInt("SUM_OF_DR_TXN_AMT")));

					accountProfiler.setCountryOccurrenceByAcctFromTeller(getAcctStatsOccurrenceValue(
							accountProfiler.getCountryOccurrenceByAcctFromTeller(), rs.getString("COUNTRY_OCCURRENCE")));

					accountProfiler.setLastDeclinedtxnsOccurrenceByAcctFromTeller(
							getAcctStatsOccurrenceValue(accountProfiler.getLastDeclinedtxnsOccurrenceByAcctFromTeller(),
									rs.getString("DECLINED_TXN_OCCURRENCE")));

					accountProfiler.setHourSlotByAcctFromTeller(getAcctStatsOccurrenceValue(
							accountProfiler.getHourSlotByAcctFromTeller(), rs.getString("TIME_OCCURRENCE")));

					accountProfiler.setCurrencyOccurrenceByAcctFromTeller(
							getAcctStatsOccurrenceValue(accountProfiler.getCurrencyOccurrenceByAcctFromTeller(),
									rs.getString("CURRENCY_OCCURRENCE")));

					accountProfiler.setNodeOccurrenceByAcctFromTeller(getAcctStatsOccurrenceValue(
							accountProfiler.getNodeOccurrenceByAcctFromTeller(), rs.getString("NODE_OCCURRENCE")));

					accountProfiler.setIpOccurrenceByAcctFromTeller(getAcctStatsOccurrenceValue(
							accountProfiler.getIpOccurrenceByAcctFromTeller(), rs.getString("IP_OCCURRENCE")));

					accountProfiler.setBeneficiaryOccurrenceByAcctFromTeller(
							getAcctStatsOccurrenceValue(accountProfiler.getBeneficiaryOccurrenceByAcctFromTeller(),
									rs.getString("BENEFICIARY_OCCURRENCE")));

				} else if (channelFromStats == 7) {
					// For only Mobile specific variables
					accountProfiler
							.setNoOfDebitTransactedDaysInHistoryByAcctFromMobile(getStaticsValueFortheSameAccount(
									accountProfiler.getNoOfDebitTransactedDaysInHistoryByAcctFromMobile(),
									rs.getInt("NO_OF_DR_TXTD_DAYS")));

					accountProfiler
							.setTotalNoOfDebitTransactionsInHistoryByAcctFromMobile(getStaticsValueFortheSameAccount(
									accountProfiler.getTotalNoOfDebitTransactionsInHistoryByAcctFromMobile(),
									rs.getInt("TOTAL_NO_OF_DR_TXNS")));

					accountProfiler
							.setSumOfDebitTranscationAmountInHistoryByAcctFromMobile(getStaticsAmountFortheSameAccount(
									accountProfiler.getSumOfDebitTranscationAmountInHistoryByAcctFromMobile(),
									rs.getInt("SUM_OF_DR_TXN_AMT")));

					accountProfiler.setCountryOccurrenceByAcctFromMobile(getAcctStatsOccurrenceValue(
							accountProfiler.getCountryOccurrenceByAcctFromMobile(), rs.getString("COUNTRY_OCCURRENCE")));

					accountProfiler.setLastDeclinedtxnsOccurrenceByAcctFromMobile(
							getAcctStatsOccurrenceValue(accountProfiler.getLastDeclinedtxnsOccurrenceByAcctFromMobile(),
									rs.getString("DECLINED_TXN_OCCURRENCE")));

					accountProfiler.setHourSlotByAcctFromMobile(getAcctStatsOccurrenceValue(
							accountProfiler.getHourSlotByAcctFromMobile(), rs.getString("TIME_OCCURRENCE")));

					accountProfiler.setCurrencyOccurrenceByAcctFromMobile(
							getAcctStatsOccurrenceValue(accountProfiler.getCurrencyOccurrenceByAcctFromMobile(),
									rs.getString("CURRENCY_OCCURRENCE")));

					accountProfiler.setNodeOccurrenceByAcctFromMobile(getAcctStatsOccurrenceValue(
							accountProfiler.getNodeOccurrenceByAcctFromMobile(), rs.getString("NODE_OCCURRENCE")));

					accountProfiler.setIpOccurrenceByAcctFromMobile(getAcctStatsOccurrenceValue(
							accountProfiler.getIpOccurrenceByAcctFromMobile(), rs.getString("IP_OCCURRENCE")));

					accountProfiler.setBeneficiaryOccurrenceByAcctFromMobile(
							getAcctStatsOccurrenceValue(accountProfiler.getBeneficiaryOccurrenceByAcctFromMobile(),
									rs.getString("BENEFICIARY_OCCURRENCE")));

				}

				accountProfiler.setCountryOccurrenceByAcctAcrossChannel(getAcctStatsOccurrenceValue(
						accountProfiler.getCountryOccurrenceByAcctAcrossChannel(), rs.getString("COUNTRY_OCCURRENCE")));

				accountProfiler.setLastDeclinedtxnsOccurrenceByAcctAcrossChannel(
						getAcctStatsOccurrenceValue(accountProfiler.getLastDeclinedtxnsOccurrenceByAcctAcrossChannel(),
								rs.getString("DECLINED_TXN_OCCURRENCE")));

				accountProfiler.setHourSlotByAcctAcrossChannel(getAcctStatsOccurrenceValue(
						accountProfiler.getHourSlotByAcctAcrossChannel(), rs.getString("TIME_OCCURRENCE")));

				accountProfiler.setCurrencyOccurrenceByAcctAcrossChannel(
						getAcctStatsOccurrenceValue(accountProfiler.getCurrencyOccurrenceByAcctAcrossChannel(),
								rs.getString("CURRENCY_OCCURRENCE")));

				accountProfiler.setNodeOccurrenceByAcctAcrossChannel(getAcctStatsOccurrenceValue(
						accountProfiler.getNodeOccurrenceByAcctAcrossChannel(), rs.getString("NODE_OCCURRENCE")));

				accountProfiler.setIpOccurrenceByAcctAcrossChannel(getAcctStatsOccurrenceValue(
						accountProfiler.getIpOccurrenceByAcctAcrossChannel(), rs.getString("IP_OCCURRENCE")));

				accountProfiler.setBeneficiaryOccurrenceByAcctAcrossChannel(
						getAcctStatsOccurrenceValue(accountProfiler.getBeneficiaryOccurrenceByAcctAcrossChannel(),
								rs.getString("BENEFICIARY_OCCURRENCE")));

				acctProfileMap.put(entityNumberForStats, accountProfiler);
			}

			channelFromStats = 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return accountProfiler;

	}
	
	private int getStaticsValueFortheSameAccount(int hashMapValue, int resultsetValue) {
		int StaticsValueFortheSameAccount = hashMapValue;
		StaticsValueFortheSameAccount = StaticsValueFortheSameAccount + resultsetValue;
		return StaticsValueFortheSameAccount;
	}
	
	private double getStaticsAmountFortheSameAccount(double hashMapValue, double resultsetValue) {
		double StaticsValueFortheSameAccountForAmount = hashMapValue;
		StaticsValueFortheSameAccountForAmount = StaticsValueFortheSameAccountForAmount + resultsetValue;
		return StaticsValueFortheSameAccountForAmount;
	}

	/* Added by Ganesh
	 * getAcctStatsOccurrenceValue method for adding each occurrence value split by comma
	 * 07th August 2018
	*/
	private String getAcctStatsOccurrenceValue(String profilervalue, String resultsetValue) {
		if (profilervalue != null && !profilervalue.equals(""))
			return profilervalue = profilervalue + "," + resultsetValue;
		else
			return profilervalue = resultsetValue;
	}

}
