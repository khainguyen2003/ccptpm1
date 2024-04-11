package com.khai.admin.util;

import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

//Lấy ngày
public class DateService {
	private static String getCurrentDate(String format) {
		DateFormat dateFormat = new SimpleDateFormat(format);
		
		Date date = new Date();
		
		return dateFormat.format(date);
 	}

	 private static Date convertDateFormattedToDate(String format, String dateFormated) {
		 DateFormat dateFormat = new SimpleDateFormat(format);
		 Date date = null;
         try {
			 date = dateFormat.parse(dateFormated);
         } catch (ParseException e) {
             throw new RuntimeException(e);
         }

		 return date;
     }
	
	public static String getCurrentDate() {
		return getCurrentDate("dd/MM/yyyy");
 	}
	 public static Date converCurrentDateFormated(String formatedDate) {
		return convertDateFormattedToDate("dd/MM/yyyy", formatedDate);
	 }
	 public static Date convertCurrentDateProfiles(String formatedDate) {
		return convertDateFormattedToDate("HH:mm:ss dd/MM/yyyy", formatedDate);
	 }
	 public static Date converDateProfiles(String formatedDate) {
		 return convertDateFormattedToDate("ddMMyyHHmmss", formatedDate);
	 }
	
	public static String getCurrentDateTime() {
		return getCurrentDate("HH:mm:ss dd/MM/yyyy");
 	}
	
	public static String getDateProfiles() {
		return getCurrentDate("ddMMyyHHmmss");
 	}
	
	public static String getDate(String format, double numericDate) {
		DateFormat dateFormat = new SimpleDateFormat(format);
		
		Date date = new Date((long) (numericDate * 1000));

		return dateFormat.format(date);
 	}
	
	public static String getDate(double numericDate) {
		return getDate("dd/MM/yyyy", numericDate);
 	}
	
	public static Date getDate(String date) {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date d = new Date();
		try {
			d = dateFormat.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return d;
	}
	
	

}
