package com.withsw;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

public class AppShutdown extends Thread {

	Logger logger = LoggerFactory.getLogger(AppShutdown.class);
	
	App app;
	
	public AppShutdown(App app) {
		this.app = app;
	}
	
	@Override
	public void run() {
		logger.info("Shutdown...");
		
		if(app != null) {
			app.shutdown();			
		}
	}
	
	public void endMsg() {
		MDC.remove("logFileName");

		logger.info("[END] MY APPLICATION");
		logger.info("------------------------------------------------------------------//");		
	}
}
