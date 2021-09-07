package com.withsw.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class FmtUtil {
	public static String now() {
		Calendar c = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd h:m:s");
		String dateTime = sdf.format(c.getTime());
		
		return dateTime;
	}
}
