package com.withsw;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import com.withsw.common.CommonWorker;
import com.withsw.conf.Conf;
import com.withsw.snmp.worker.SnmpJobScheduler;
import com.withsw.snmp.worker.SnmpTrapReceiver;
import com.withsw.util.SysUtils;

public class App {

	private static final Logger logger = LoggerFactory.getLogger(App.class);

	CommonWorker snmpJobScheduler;
	SnmpTrapReceiver snmpTrapReceiver;
	
	
	public void shutdown() {
		if(snmpTrapReceiver != null) {
			snmpTrapReceiver.shutdown();
			SysUtils.sleep(100);
		}
		
		if(snmpJobScheduler != null) {
			snmpJobScheduler.setWork(false);
			SysUtils.sleep(100);
		}
	}
	
	private void init() {
		
		initSnmp();
	}
	
	
	public void initSnmp() {
		snmpTrapReceiver = new SnmpTrapReceiver();
		snmpTrapReceiver.startup();	
		
		snmpJobScheduler = new SnmpJobScheduler("SnmpJobScheduler");
		snmpJobScheduler.setDaemon(true);
		snmpJobScheduler.start();

		try {
			snmpJobScheduler.join();
		} catch (InterruptedException e) {
			logger.error("Ocuured InterruptedException in SnmpTrapReceiver");
		}		
				
	}
	
	public void run() {
		init();
	}
	
	public static void main(String[] args) {
		MDC.put("logFileName", App.class.getSimpleName());
		
		logger.info("//------------------------------------------------------------------");
		logger.info("[START] MY APPLICATION");
		
		logger.info("load configuration.");
		Conf.getInstance();		
		
		App app = new App();
		app.run();
		
		
		Runtime.getRuntime().addShutdownHook(new AppShutdown(app));
		logger.info("Added ShutdownHook.");
	}
}
