package com.withsw.conf;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

public class ReloadChecker implements Runnable {

	private Logger logger = LoggerFactory.getLogger(ReloadChecker.class);
	
	public static boolean isStop;
	private int checkPeriod;
	private String confPath;
	private File confFile;
	private long fileModified;
	
	public ReloadChecker() {
		ReloadChecker.isStop = false;
	}

	@Override
	public void run() {
		MDC.put("logFileName", ReloadChecker.class.getSimpleName());
		
		logger.info("//------------------------------------------------------------------");
		logger.info("[START] ReloadChecker Thread");

		checkPeriod = Conf.getInstance().getReloadCheckPeriod();
		confPath = Conf.getInstance().getConfPath();
		
		try {
			confFile = new File(confPath);	
		} catch(NullPointerException e) {
			logger.error("confPath is null.");
		}
		
		fileModified = confFile.lastModified();
		while(!isStop) {
			
			if(fileModified != confFile.lastModified()) {
				logger.info("Changed config file.");
				Conf.getInstance().loadConfig();
				fileModified = confFile.lastModified();
			}
			
			try {
				Thread.sleep(checkPeriod * 1000);
			} catch (InterruptedException e) {
				logger.warn("Interrupted sleep of thread.");
			}
		}
		
		
		logger.info("[END] Receiver Thread");
		logger.info("------------------------------------------------------------------//");
		
		MDC.remove("logFileName");
	}
	
}
