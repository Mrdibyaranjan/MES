package com.saraansh.enrich;

import static main.CFRTAdapter.logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;

public class Utility {

	static String checkMaxDateFromTransactedDateList(String OccurrenceDateValues) {
		SimpleDateFormat formatter = new SimpleDateFormat("ddMMyy");
		ArrayList<Date> dateList = new ArrayList<Date>();
		String maxDateValue="";
		if(OccurrenceDateValues==null || OccurrenceDateValues.equals("")) return maxDateValue;
		HashSet<String> tempSet = new HashSet<>();
		String[] loc = OccurrenceDateValues.split(",");
		for (String val: loc)
			tempSet.add(val.substring(val.length()-6,val.length()));
		try {
			for(String val: tempSet)
				dateList.add(formatter.parse(val));
		} catch (ParseException e) {
			logger.error(e);
		}
		Date maxDate=Collections.max(dateList);
		formatter = new SimpleDateFormat("yyyy-MM-dd");
	    maxDateValue= formatter.format(maxDate);
		tempSet.clear();
		return maxDateValue;
	}
	
	public static int getDiffYears(Date first, Date last) {
	    Calendar a = getCalendar(first);
	    Calendar b = getCalendar(last);
	    int diff = b.get(Calendar.YEAR) - a.get(Calendar.YEAR);
	    if (a.get(Calendar.DAY_OF_YEAR) > b.get(Calendar.DAY_OF_YEAR)) {
	        diff--;
	    }
	    return diff;
	}
	
	static int calculateAgeOfCustomer(String dob, String transactionDateTime) {
		int age = 0;
		SimpleDateFormat formaterForDOBAndAOD = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat formaterForTxnDateTime = new SimpleDateFormat("yyyyMMddHHmmss");
		if (dob != null && !dob.equals("")) {
			try {
				Date dobForDate = formaterForDOBAndAOD.parse(dob);
				Date currTxnDateTime = formaterForTxnDateTime.parse(transactionDateTime);
				age = getDiffYears(dobForDate, currTxnDateTime);// age_in_years
			} catch (Exception e1) {
				age = 0;
				logger.error(e1);
			}
		} else
			age = 0;

		return age;
	}
	
	/*Added by Ganesh
     * The getDateDiff method used for calculating account Age in Days
     * Current Txns Date minus profile Creation Date
     * 07th August 2018
     */
	static int getDateDiff(String transactionDateTime, String profileCreationDate) {
		int days = 0;
		if (transactionDateTime != null && !transactionDateTime.equals("") && profileCreationDate != null
				&& !profileCreationDate.equals("")) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			try {
				Date date = formatter.parse(profileCreationDate);
				long txnDateTime = ((sdf.parse(transactionDateTime)).getTime());
				days = (int) ((txnDateTime - date.getTime()) / ((24 * 60 * 60 * 1000)));
			} catch (Exception e) {
				logger.error(e);
			}
		}
		return days;
	}
	
	public static Calendar getCalendar(Date date) {
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
	    return cal;
	}
}
