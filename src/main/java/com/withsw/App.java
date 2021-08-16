package com.withsw;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import com.withsw.conf.Conf;

public class App {

	private static final Logger logger = LoggerFactory.getLogger(App.class);
	
	public static void main(String[] args) {
		MDC.put("logFileName", App.class.getSimpleName());
		
		logger.info("//------------------------------------------------------------------");
		logger.info("[START] MY APPLICATION");
		
		Runtime.getRuntime().addShutdownHook(new AppShutdown());
		logger.info("Added ShutdownHook.");
	
		logger.info("Load configuration.");
		Conf.getInstance();
	}
}
