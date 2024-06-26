package com.ford.utils;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DTDateUtils {

	protected final static Log logger = LogFactory.getLog(DTDateUtils.class);

	public static Date getTodayDate() throws ParseException {
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date todayDate = (Date) formatter.parse(formatter.format(currentDate.getTime()));
		return todayDate;
	}

	public static Date getTodayDateWithTime() throws ParseException {
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date todayDate = (Date) formatter.parse(formatter.format(currentDate.getTime()));
		Date currentDateWithTime = new Date(todayDate.getTime() - (1 * 1000));

		return currentDateWithTime;
	}

	public static Date getCurrentDate() throws ParseException {
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		currentDate.set(Calendar.HOUR_OF_DAY, 0);
		currentDate.set(Calendar.MINUTE, 0);
		currentDate.set(Calendar.SECOND, 0);
		Date todayDate = (Date) formatter.parse(formatter.format(currentDate.getTime()));
		return todayDate;
	}

	public static Date getDateWithFormat(Date date) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date givenDate = (Date) formatter.parse(formatter.format(date));
		return givenDate;
	}

	public static Date getMonthAndYearFormat(Date date) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("MM-yyyy");
		SimpleDateFormat formatterObj = new SimpleDateFormat("dd-MM-yyyy");
		String dateString = formatter.format(date);
		dateString = "05-" + dateString;
		Date formattedDate = (Date) formatterObj.parse(dateString);
		return formattedDate;
	}

	public static Date getDateFromStringDate(String scheduleDate) throws ParseException {
		DateFormat formatter = null;
		Date convertedDate = null;
		formatter = new SimpleDateFormat("dd-MM-yyyy");
		convertedDate = (Date) formatter.parse(scheduleDate);
		logger.info("Date from dd-MM-yyyy String in Java : " + convertedDate);
		return convertedDate;
	}

	public static Date getDateFromStringDateFormat(String scheduleDate) throws ParseException {
		DateFormat formatter = null;
		Date convertedDate = null;
		formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		convertedDate = (Date) formatter.parse(scheduleDate);
		logger.info("Date from dd-MM-yyyy String in Java : " + convertedDate);
		return convertedDate;
	}

	public static Date getDateFormat() throws ParseException {

		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
		return (Date) formatter.parse(formatter.format(currentDate.getTime()));
	}

	public static Date getDob(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, 1);
		Date dob = c.getTime();
		return dob;
	}

	public static boolean sameDate(Date fromDate, Date toDate) {
		SimpleDateFormat fmt = new SimpleDateFormat("dd-MM-yyyy");
		if (fmt.format(fromDate).equals(fmt.format(toDate))) {
			return true;
		}
		return false;
	}

	public static boolean sameDayWithTime(Date fromDate, Date toDate) {
		long diff = toDate.getTime() - fromDate.getTime();
		long diffHours = diff / (60 * 60 * 1000);
		if (diffHours <= 6) {
			return true;
		}
		return false;
	}

	public static boolean isNotEmpty(List list) {

		boolean isNotEmpty = false;
		if (list != null && !list.isEmpty()) {
			isNotEmpty = true;
		}
		return isNotEmpty;
	}

	public static boolean isValidString(String stringValue) {
		boolean isValidString = false;
		if (stringValue != null && !stringValue.isEmpty()) {
			isValidString = true;
		}
		return isValidString;
	}

	public static String generateRandomString() {
		String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		StringBuilder salt = new StringBuilder();
		Random rnd = new Random();
		while (salt.length() < 4) { // length of the random string.
			int index = (int) (rnd.nextFloat() * SALTCHARS.length());
			salt.append(SALTCHARS.charAt(index));
		}
		String saltStr = salt.toString();
		return saltStr;

	}

	public static Date addDays(Date date, int days) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.add(Calendar.DATE, days);
		return cal.getTime();
	}

	public static Date getLastModifiledFileDate(String filePath) {
		File file = new File(filePath);
		// Getting the last modified time in epoch
		long lastModified = file.lastModified();
		Date date = new Date(lastModified);
		return date;
	}

}
