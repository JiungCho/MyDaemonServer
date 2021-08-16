package com.withsw.util;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

public class SysUtils {

	public static String getPid() {
		RuntimeMXBean bean = ManagementFactory.getRuntimeMXBean();
		
		String pid = "";
		String jvmName = bean.getName();
		
		if(jvmName != null && jvmName.contains("@")) {
			pid = jvmName.split("@")[0];
		}
		
		return pid;
	}
}
