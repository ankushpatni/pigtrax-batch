package com.pigtrax.batch.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;

public class DateUtil {

	private static final String DEFAULT_FORMATTER = "yyyy-MM-dd";

	private static final Logger logger = Logger.getLogger(DateUtil.class);

	public static Date getCurrentDate() {
		return Calendar.getInstance().getTime();
	}

	public static String getCurrentDateAsString() {
		return new SimpleDateFormat(DEFAULT_FORMATTER).format(getCurrentDate());
	}

	public static String getDateAsString(final Date date) {
		return new SimpleDateFormat(DEFAULT_FORMATTER).format(date);
	}

	public static Date getDateFromString(final String date) {
		try {
			return new SimpleDateFormat(DEFAULT_FORMATTER).parse(date);
		} catch (Exception e) {
			logger.error("Exception in DateUtil.getDateFromString" + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	public static Integer compareStringDate(String date1, String date2) {
		try {
			return getDateFromString(date1).compareTo(getDateFromString(date2));
		} catch (Exception e) {
			logger.error("Exception in DateUtil.compareStringDate" + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

}
