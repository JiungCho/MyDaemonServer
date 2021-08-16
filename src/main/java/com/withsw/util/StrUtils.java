package com.withsw.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StrUtils {

	private static Logger logger = LoggerFactory.getLogger(StrUtils.class);
	
	public StrUtils() {
	}
	
	public static String nvl(String str, String def) {
		if(str == null) {
			str = def;
		}
		
		return str;
	}

	public static int toNumber(String str) {
		int value = 0;
		
		try {
			value = Integer.parseInt(str);	
		} catch(NumberFormatException e) {
			logger.warn("Occured NumberFormatException. set to 0 of value");
			value = 0;
		}
		
		return value;
	}
	
	public static String fillSpace(String str, int length) {
		
		return "";
	}
}
