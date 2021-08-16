package com.withsw.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.security.InvalidParameterException;

public class FileUtils {
	public static void copy(String src, String dest, String charset) throws IOException {
		if(src == null || "".equals(src)) {
			throw new InvalidParameterException("src is null or empty");
		}
		
		if(dest == null || "".equals(dest)) {
			throw new InvalidParameterException("dest is null or empty");
		}
		
		if(charset == null || "".equals(charset)) {
			charset = "utf-8";
		}
		
		File srcFile = new File(src);
		File destFile = new File(dest);
		File destParentPath = null;

		if(!srcFile.exists()) {
			throw new FileNotFoundException("not found the src file.");
		}		
		
		if(!srcFile.isFile()) {
			throw new InvalidParameterException("src is not a file.");
		}
		
		if(destFile.exists()) {
			destFile.delete();
		}
		else {
			destParentPath = new File(destFile.getParent());
			destParentPath.mkdirs();
		}
		
		if(!destFile.createNewFile()) {
			throw new RuntimeException("fail to create the dest file.");
		}
		
		FileInputStream srcFis = new FileInputStream(srcFile);
		FileOutputStream destFos = new FileOutputStream(destFile);
		
		InputStreamReader srcIsr = new InputStreamReader(srcFis, charset);
		OutputStreamWriter destOsw = new OutputStreamWriter(destFos, charset);
		
		char[] buf = new char[4096];
		int readSize = 0, totalSize = 0;
		
		if(srcFile.length() > 0) {
			while((readSize = srcIsr.read(buf)) != -1) {
				destOsw.write(buf, 0, readSize);
				totalSize += readSize;
			}
			
			if(totalSize == srcFile.length()) {
				destFos.flush();
			}
			
			if(srcIsr != null) { srcIsr.close(); }
			if(destOsw != null) { destOsw.close(); }
			if(srcFis != null) { srcFis.close(); }
			if(destFos != null) { destFos.close(); }
		}
	}
}
