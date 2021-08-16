package com.withsw.conf;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.withsw.util.StrUtils;

public class Conf {

	private Logger logger = LoggerFactory.getLogger(Conf.class);
	
	private volatile static Conf instance;

	private Properties prop;
	
	private String mode;
	private String userDir;
	private String confPath;
	private int reloadCheckPeriod;

	public static Conf getInstance() {
		if(instance == null) {
			synchronized(Conf.class) {
				if(instance == null) {
					instance = new Conf();
					instance.loadConfig();
				}
			}
		}
		
		return instance;
	}
	
	public void loadConfig() {
		userDir = System.getProperties().getProperty("user.dir");
		
		confPath = System.getProperties().getProperty("conf.path");
		if(confPath == null) {
			confPath = userDir + "/conf/conf.properties";
		}
				
		try {
			prop = new Properties();
			prop.load(new FileInputStream(new File(confPath)));
		} catch(IOException e) {
			logger.error("Failed to load config.");
		} catch(Exception e) {
			logger.error("Failed to load config. (" + e.getMessage() + ")");
		}
		
		mode = StrUtils.nvl(prop.getProperty("mode"), "dev");
		reloadCheckPeriod = StrUtils.toNumber(prop.getProperty("reload.checker.period"));
		
		printConfig();
	}
	
	private void printConfig() {
		logger.info("**********************************************");
		logger.info("[CONFIG]");
		logger.info("mode: " + mode);
		logger.info("user.dir: " + userDir);
		logger.info("**********************************************");
	}

	public String getUserDir() {
		return userDir;
	}

	public String getConfPath() {
		return confPath;
	}

	public int getReloadCheckPeriod() {
		return reloadCheckPeriod;
	}
}
