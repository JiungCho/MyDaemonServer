package com.withsw.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.security.InvalidParameterException;

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
	
	
	public static void generatePidFile(String filePath) {
		if(filePath == null || "".equals(filePath)) {
			throw new InvalidParameterException("path parameter is invalid.");
		}
		
		File file = new File(filePath);
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				throw new RuntimeException("failed to create new file.");
			}
		}
		
		String pid = getPid();

		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
			fos.write(pid.getBytes());
		} catch (FileNotFoundException e) {
			throw new RuntimeException("not found the file.");
		} catch (IOException e) {
			throw new RuntimeException("occured exception for writing bytes.");
		}
		finally {
			if(fos != null) {
				try { fos.close(); } catch (IOException e) {  }
			}
		}
	}
	
	public static void sleep(long millis) {
		try { Thread.sleep(millis); } catch (InterruptedException e) { }
	}
}
