package com.withsw;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

public class AppShutdown extends Thread {

	Logger logger = LoggerFactory.getLogger(AppShutdown.class);
	
	public AppShutdown() {
		this.setName("main_end");
	}
	
	@Override
	public void run() {
		
		endMsg();
	}
	
	public void endMsg() {
		MDC.remove("logFileName");

		logger.info("[END] MY APPLICATION");
		logger.info("------------------------------------------------------------------//");		
	}
}
