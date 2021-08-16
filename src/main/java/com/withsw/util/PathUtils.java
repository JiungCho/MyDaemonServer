package com.withsw.util;

import java.security.InvalidParameterException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PathUtils {
	public static String tillToday(String baseDir) {
		if(baseDir == null || "".equals(baseDir)) {
			throw new InvalidParameterException("baseDir is null or empty");
		}
		
		String dir = "";
		String today = "", year = "", month = "", day = "";
		String sep = System.getProperty("file.separator");
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		today = sdf.format(new Date());
		if(today != null) {
			year = today.substring(0, 4);
			month = today.substring(4, 6);
			day = today.substring(6, 8);
		}
		
		dir = baseDir + year + sep + month + sep + day;
		
		return dir;
	}
}
