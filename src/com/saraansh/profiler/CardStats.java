package com.saraansh.profiler;

import java.sql.ResultSet;
import java.util.HashMap;

import dao.ProfilerData;

public class CardStats {
	private HashMap<String, ProfilerData> cardProfileMap = new HashMap<String, ProfilerData>();
	private ProfilerData cardProfiler = new ProfilerData();

	public ProfilerData getCardProfilerData (String entityNumber, Integer channel, Integer mode, ResultSet rs) {
		String entityNumberForStats = "";
		// across all channels
		int channelFromStats;
		try {
			entityNumberForStats = rs.getString("ENTITY_NUMBER").trim();
			channelFromStats = rs.getInt("CHANNEL");

			if (channelFromStats == channel && rs.getInt("TRANSACTION_MODE") == mode) {
				cardProfiler.setNoOfDebitTransactedDaysInHistoryByCard(rs.getInt("NO_OF_DR_TXTD_DAYS"));
				cardProfiler.setTotalNoOfDebitTransactionsInHistoryByCard(rs.getInt("TOTAL_NO_OF_DR_TXNS"));
				cardProfiler.setSumOfDebitTranscationAmountInHistoryByCard(rs.getFloat("SUM_OF_DR_TXN_AMT"));
				cardProfiler.setHourSlotByCard(rs.getString("TIME_OCCURRENCE"));
				cardProfiler.setCurrencyOccurrenceByCard(rs.getString("CURRENCY_OCCURRENCE"));
				cardProfiler.setCountryOccurrenceByCard(rs.getString("COUNTRY_OCCURRENCE"));
				cardProfiler.setNodeOccurrenceByCard(rs.getString("NODE_OCCURRENCE"));
				cardProfiler.setIpOccurrenceByCard(rs.getString("IP_OCCURRENCE"));
				cardProfiler.setBeneficiaryOccurrenceByCard(rs.getString("BENEFICIARY_OCCURRENCE"));
				cardProfiler.setLocationOccurrenceByCard(rs.getString("LOCATION_OCCURRENCE"));
			}

			if (cardProfileMap.get(entityNumberForStats) == null) {
				cardProfiler.setNoOfDebitTransactedDaysInHistoryByCardAcrossChannel(rs.getInt("NO_OF_DR_TXTD_DAYS"));
				cardProfiler.setTotalNoOfDebitTransactionsInHistoryByCardAcrossChannel(rs.getInt("TOTAL_NO_OF_DR_TXNS"));
				cardProfiler.setSumOfDebitTranscationAmountInHistoryByCardAcrossChannel(rs.getFloat("SUM_OF_DR_TXN_AMT"));
				cardProfiler.setHourSlotByCardAcrossChannel(rs.getString("TIME_OCCURRENCE"));
				cardProfiler.setCurrencyOccurrenceByCardAcrossChannel(rs.getString("CURRENCY_OCCURRENCE"));
				cardProfiler.setCountryOccurrenceByCardAcrossChannel(rs.getString("COUNTRY_OCCURRENCE"));
				cardProfiler.setNodeOccurrenceByCardAcrossChannel(rs.getString("NODE_OCCURRENCE"));
				cardProfiler.setIpOccurrenceByCardAcrossChannel(rs.getString("IP_OCCURRENCE"));
				cardProfiler.setLocationOccurrenceByCardAcrossChannel(rs.getString("LOCATION_OCCURRENCE"));

				//Added for NSDL By Abdul Hadi
				cardProfiler.setAvgMonthlyTransactionAmountAcrossChannel(rs.getFloat("AVG_MONTHLY_AMOUNT"));
				cardProfiler.setAvgMonthlyTransactionCountAcrossChannel(rs.getInt("AVG_MONTHLY_COUNT"));
				cardProfiler.setMccOccurrenceAcrossChannel(rs.getString("MCC_OCCURRENCE"));
				
				if (channelFromStats == 1) {
					cardProfiler.setNoOfDebitTransactedDaysInHistoryByCardFromATM(rs.getInt("NO_OF_DR_TXTD_DAYS"));
					cardProfiler.setTotalNoOfDebitTransactionsInHistoryByCardFromATM(rs.getInt("TOTAL_NO_OF_DR_TXNS"));
					cardProfiler.setSumOfDebitTranscationAmountInHistoryByCardFromATM(rs.getFloat("SUM_OF_DR_TXN_AMT"));
					cardProfiler.setHourSlotByCardFromATM(rs.getString("TIME_OCCURRENCE"));
					cardProfiler.setCurrencyOccurrenceByCardFromATM(rs.getString("CURRENCY_OCCURRENCE"));
					cardProfiler.setCountryOccurrenceByCardFromATM(rs.getString("COUNTRY_OCCURRENCE"));
					cardProfiler.setNodeOccurrenceByCardFromATM(rs.getString("NODE_OCCURRENCE"));
					cardProfiler.setBeneficiaryOccurrenceByCardFromATM(rs.getString("BENEFICIARY_OCCURRENCE"));
					cardProfiler.setLocationOccurrenceByCardFromATM(rs.getString("LOCATION_OCCURRENCE"));
				} else if (channelFromStats == 2) {
					cardProfiler.setNoOfDebitTransactedDaysInHistoryByCardFromEDC(rs.getInt("NO_OF_DR_TXTD_DAYS"));
					cardProfiler.setTotalNoOfDebitTransactionsInHistoryByCardFromEDC(rs.getInt("TOTAL_NO_OF_DR_TXNS"));
					cardProfiler.setSumOfDebitTranscationAmountInHistoryByCardFromEDC(rs.getFloat("SUM_OF_DR_TXN_AMT"));
					cardProfiler.setHourSlotByCardFromEDC(rs.getString("TIME_OCCURRENCE"));
					cardProfiler.setCurrencyOccurrenceByCardFromEDC(rs.getString("CURRENCY_OCCURRENCE"));
					cardProfiler.setCountryOccurrenceByCardFromEDC(rs.getString("COUNTRY_OCCURRENCE"));
					cardProfiler.setNodeOccurrenceByCardFromEDC(rs.getString("NODE_OCCURRENCE"));
					cardProfiler.setLocationOccurrenceByCardFromEDC(rs.getString("LOCATION_OCCURRENCE"));
				} else if (channelFromStats == 3) {
					cardProfiler.setNoOfDebitTransactedDaysInHistoryByCardFromEcommerce(rs.getInt("NO_OF_DR_TXTD_DAYS"));
					cardProfiler.setTotalNoOfDebitTransactionsInHistoryByCardFromEcommerce(rs.getInt("TOTAL_NO_OF_DR_TXNS"));
					cardProfiler.setSumOfDebitTranscationAmountInHistoryByCardFromEcommerce(rs.getFloat("SUM_OF_DR_TXN_AMT"));
					cardProfiler.setHourSlotByCardFromEcommerce(rs.getString("TIME_OCCURRENCE"));
					cardProfiler.setCurrencyOccurrenceByCardFromEcommerce(rs.getString("CURRENCY_OCCURRENCE"));
					cardProfiler.setCountryOccurrenceByCardFromEcommerce(rs.getString("COUNTRY_OCCURRENCE"));
					cardProfiler.setNodeOccurrenceByCardFromEcommerce(rs.getString("NODE_OCCURRENCE"));
					cardProfiler.setIpOccurrenceByCardFromEcommerce(rs.getString("IP_OCCURRENCE"));
				}
				cardProfileMap.put(entityNumberForStats, cardProfiler);
			} else {
				cardProfiler = cardProfileMap.get(entityNumberForStats);

				cardProfiler.setNoOfDebitTransactedDaysInHistoryByCardAcrossChannel(getStaticsValueFortheSameAccount(
						cardProfiler.getNoOfDebitTransactedDaysInHistoryByCardAcrossChannel(),
						rs.getInt("NO_OF_DR_TXTD_DAYS")));
				cardProfiler.setTotalNoOfDebitTransactionsInHistoryByCardAcrossChannel(getStaticsValueFortheSameAccount(
						cardProfiler.getTotalNoOfDebitTransactionsInHistoryByCardAcrossChannel(),
						rs.getInt("TOTAL_NO_OF_DR_TXNS")));
				cardProfiler
						.setSumOfDebitTranscationAmountInHistoryByCardAcrossChannel(getStaticsAmountFortheSameAccount(
								cardProfiler.getSumOfDebitTranscationAmountInHistoryByCardAcrossChannel(),
								rs.getInt("SUM_OF_DR_TXN_AMT")));
				cardProfiler.setHourSlotByCardAcrossChannel(getAcctStatsOccurrenceValue(
						cardProfiler.getHourSlotByCardAcrossChannel(), rs.getString("TIME_OCCURRENCE")));
				cardProfiler.setCurrencyOccurrenceByCardAcrossChannel(getAcctStatsOccurrenceValue(
						cardProfiler.getCurrencyOccurrenceByCardAcrossChannel(), rs.getString("CURRENCY_OCCURRENCE")));
				cardProfiler.setCountryOccurrenceByCardAcrossChannel(getAcctStatsOccurrenceValue(
						cardProfiler.getCountryOccurrenceByCardAcrossChannel(), rs.getString("COUNTRY_OCCURRENCE")));
				cardProfiler.setNodeOccurrenceByCardAcrossChannel(getAcctStatsOccurrenceValue(
						cardProfiler.getNodeOccurrenceByCardAcrossChannel(), rs.getString("NODE_OCCURRENCE")));
				cardProfiler.setIpOccurrenceByCardAcrossChannel(getAcctStatsOccurrenceValue(
						cardProfiler.getIpOccurrenceByCardAcrossChannel(), rs.getString("IP_OCCURRENCE")));
				cardProfiler.setLocationOccurrenceByCardAcrossChannel(
						getAcctStatsOccurrenceValue(cardProfiler.getLocationOccurrenceByCardAcrossChannel(),
								rs.getString("LOCATION_OCCURRENCE")));
				//Added for NSDL By Abdul Hadi
				cardProfiler.setAvgMonthlyTransactionAmountAcrossChannel(
						getStaticsAmountFortheSameAccount(cardProfiler.getAvgMonthlyTransactionAmountAcrossChannel(),
								rs.getFloat("AVG_MONTHLY_AMOUNT")));
				cardProfiler.setAvgMonthlyTransactionCountAcrossChannel(
						getStaticsValueFortheSameAccount(cardProfiler.getAvgMonthlyTransactionCountAcrossChannel(),
								rs.getInt("AVG_MONTHLY_COUNT")));
				cardProfiler.setMccOccurrenceAcrossChannel(getAcctStatsOccurrenceValue(
						cardProfiler.getMccOccurrenceAcrossChannel(), rs.getString("MCC_OCCURRENCE")));
				//Ends here
				
				if (channelFromStats == 1) {
					// For only ATM specific variables
					cardProfiler.setNoOfDebitTransactedDaysInHistoryByCardFromATM(getStaticsValueFortheSameAccount(
							cardProfiler.getNoOfDebitTransactedDaysInHistoryByCardFromATM(),
							rs.getInt("NO_OF_DR_TXTD_DAYS")));
					cardProfiler.setTotalNoOfDebitTransactionsInHistoryByCardFromATM(getStaticsValueFortheSameAccount(
							cardProfiler.getTotalNoOfDebitTransactionsInHistoryByCardFromATM(),
							rs.getInt("TOTAL_NO_OF_DR_TXNS")));
					cardProfiler.setSumOfDebitTranscationAmountInHistoryByCardFromATM(getStaticsAmountFortheSameAccount(
							cardProfiler.getSumOfDebitTranscationAmountInHistoryByCardFromATM(),
							rs.getInt("SUM_OF_DR_TXN_AMT")));
					cardProfiler.setHourSlotByCardFromATM(getAcctStatsOccurrenceValue(
							cardProfiler.getHourSlotByCardFromATM(), rs.getString("TIME_OCCURRENCE")));
					cardProfiler.setCurrencyOccurrenceByCardFromATM(getAcctStatsOccurrenceValue(
							cardProfiler.getCurrencyOccurrenceByCardFromATM(), rs.getString("CURRENCY_OCCURRENCE")));
					cardProfiler.setCountryOccurrenceByCardFromATM(getAcctStatsOccurrenceValue(
							cardProfiler.getCountryOccurrenceByCardFromATM(), rs.getString("COUNTRY_OCCURRENCE")));
					cardProfiler.setNodeOccurrenceByCardFromATM(getAcctStatsOccurrenceValue(
							cardProfiler.getNodeOccurrenceByCardFromATM(), rs.getString("NODE_OCCURRENCE")));
					cardProfiler.setBeneficiaryOccurrenceByCardFromATM(
							getAcctStatsOccurrenceValue(cardProfiler.getBeneficiaryOccurrenceByCardFromATM(),
									rs.getString("BENEFICIARY_OCCURRENCE")));
					cardProfiler.setLocationOccurrenceByCardFromATM(getAcctStatsOccurrenceValue(
							cardProfiler.getLocationOccurrenceByCardFromATM(), rs.getString("LOCATION_OCCURRENCE")));
				} else if (channelFromStats == 2) {
					// For only EDC specific variables
					cardProfiler.setNoOfDebitTransactedDaysInHistoryByCardFromEDC(getStaticsValueFortheSameAccount(
							cardProfiler.getNoOfDebitTransactedDaysInHistoryByCardFromEDC(),
							rs.getInt("NO_OF_DR_TXTD_DAYS")));
					cardProfiler.setTotalNoOfDebitTransactionsInHistoryByCardFromEDC(getStaticsValueFortheSameAccount(
							cardProfiler.getTotalNoOfDebitTransactionsInHistoryByCardFromEDC(),
							rs.getInt("TOTAL_NO_OF_DR_TXNS")));
					cardProfiler.setSumOfDebitTranscationAmountInHistoryByCardFromEDC(getStaticsAmountFortheSameAccount(
							cardProfiler.getSumOfDebitTranscationAmountInHistoryByCardFromEDC(),
							rs.getInt("SUM_OF_DR_TXN_AMT")));
					cardProfiler.setHourSlotByCardFromEDC(getAcctStatsOccurrenceValue(
							cardProfiler.getHourSlotByCardFromEDC(), rs.getString("TIME_OCCURRENCE")));
					cardProfiler.setCurrencyOccurrenceByCardFromEDC(getAcctStatsOccurrenceValue(
							cardProfiler.getCurrencyOccurrenceByCardFromEDC(), rs.getString("CURRENCY_OCCURRENCE")));
					cardProfiler.setCountryOccurrenceByCardFromEDC(getAcctStatsOccurrenceValue(
							cardProfiler.getCountryOccurrenceByCardFromEDC(), rs.getString("COUNTRY_OCCURRENCE")));
					cardProfiler.setNodeOccurrenceByCardFromEDC(getAcctStatsOccurrenceValue(
							cardProfiler.getNodeOccurrenceByCardFromEDC(), rs.getString("NODE_OCCURRENCE")));
					cardProfiler.setLocationOccurrenceByCardFromEDC(getAcctStatsOccurrenceValue(
							cardProfiler.getLocationOccurrenceByCardFromEDC(), rs.getString("LOCATION_OCCURRENCE")));

				} else if (channelFromStats == 3) {
					// For only Ecommerce specific variables
					cardProfiler
							.setNoOfDebitTransactedDaysInHistoryByCardFromEcommerce(getStaticsValueFortheSameAccount(
									cardProfiler.getNoOfDebitTransactedDaysInHistoryByCardFromEcommerce(),
									rs.getInt("NO_OF_DR_TXTD_DAYS")));
					cardProfiler
							.setTotalNoOfDebitTransactionsInHistoryByCardFromEcommerce(getStaticsValueFortheSameAccount(
									cardProfiler.getTotalNoOfDebitTransactionsInHistoryByCardFromEcommerce(),
									rs.getInt("TOTAL_NO_OF_DR_TXNS")));
					cardProfiler.setSumOfDebitTranscationAmountInHistoryByCardFromEcommerce(
							getStaticsAmountFortheSameAccount(
									cardProfiler.getSumOfDebitTranscationAmountInHistoryByCardFromEcommerce(),
									rs.getInt("SUM_OF_DR_TXN_AMT")));
					cardProfiler.setHourSlotByCardFromEcommerce(getAcctStatsOccurrenceValue(
							cardProfiler.getHourSlotByCardFromEcommerce(), rs.getString("TIME_OCCURRENCE")));
					cardProfiler.setCurrencyOccurrenceByCardFromEcommerce(
							getAcctStatsOccurrenceValue(cardProfiler.getCurrencyOccurrenceByCardFromEcommerce(),
									rs.getString("CURRENCY_OCCURRENCE")));
					cardProfiler.setCountryOccurrenceByCardFromEcommerce(
							getAcctStatsOccurrenceValue(cardProfiler.getCountryOccurrenceByCardFromEcommerce(),
									rs.getString("COUNTRY_OCCURRENCE")));
					cardProfiler.setNodeOccurrenceByCardFromEcommerce(getAcctStatsOccurrenceValue(
							cardProfiler.getNodeOccurrenceByCardFromEcommerce(), rs.getString("NODE_OCCURRENCE")));
					cardProfiler.setIpOccurrenceByCardFromEcommerce(getAcctStatsOccurrenceValue(
							cardProfiler.getIpOccurrenceByCardFromEcommerce(), rs.getString("IP_OCCURRENCE")));
				}
				cardProfileMap.put(entityNumberForStats, cardProfiler);
			}
			channelFromStats = 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cardProfiler;
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
