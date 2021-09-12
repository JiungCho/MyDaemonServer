package com.withsw.snmp.worker;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.withsw.common.CommonWorker;
import com.withsw.util.SysUtils;



public class SnmpJobScheduler extends CommonWorker {
	
	private static final Logger logger = LoggerFactory.getLogger(SnmpJobScheduler.class);
	
	public SnmpJobScheduler() {
		super.setName(this.getClass().getName());
	}
	
	public SnmpJobScheduler(String name) {
		super.setName(name);
	}
	
	public SnmpJobScheduler(ThreadGroup threadGroup, String name) {
		super(threadGroup, name);
	}	

	@Override
	public void run() {
		logger.info("Started " + this.getClass().getSimpleName() + "...");
		
		while(!stop) {
			
			if(work) {
				//logger.debug(getName() + " working...");
				SysUtils.sleep(1000);
			}
			else {
				Thread.yield();
			}
		}		
		
	}
	
}
