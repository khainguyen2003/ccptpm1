package com.khai.admin.util;

import java.text.DecimalFormat;

//Thư viện xử lí 
public class Utilities_text {
	public static boolean checkValidPass (String pass1, String pass2) {
		if (pass1!= null && pass2!=null) {
			if(!pass1.equalsIgnoreCase("") && !pass2.equalsIgnoreCase("")) {
				if (pass1.length()>6 && pass1.equals(pass2)) {
					return true;
				} 
			}
		}
		
		return false;
	}
	
	public static String convertNullValue(Object value) {
		return (value != null) ? String.valueOf(value) : "";
	}
	
	public static String formatNumber(int number) {
		DecimalFormat decimalFormat = new DecimalFormat("#,###");
		return decimalFormat.format(number);
	}
	public static String formatNumber(double number) {
		DecimalFormat decimalFormat = new DecimalFormat("#,###");
		return decimalFormat.format(number);
	}
	public static String formatNumber(float number) {
		DecimalFormat decimalFormat = new DecimalFormat("#,###");
		return decimalFormat.format(number);
	}
	public static String formatNumber(long number) {
		DecimalFormat decimalFormat = new DecimalFormat("#,###");
		return decimalFormat.format(number);
	}
	public static String formatNumber(short number) {
		DecimalFormat decimalFormat = new DecimalFormat("#,###");
		return decimalFormat.format(number);
	}
	public static String formatNumber(byte number) {
		DecimalFormat decimalFormat = new DecimalFormat("#,###");
		return decimalFormat.format(number);
	}

	public static boolean isTrue(String value) {
		if(value.equalsIgnoreCase("Có") && value.equalsIgnoreCase("1")) {
			return true;
		}

		return false;
	}
}
