package com.withsw.snmp.worker;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.withsw.common.CommonWorker;
import com.withsw.snmp.vo.SnmpTrapResponseVo;
import com.withsw.util.SysUtils;

public class SnmpTrapReceiverMy extends CommonWorker {
	
	private static final Logger logger = LoggerFactory.getLogger(SnmpTrapReceiverMy.class);
	
	Queue<SnmpTrapResponseVo> queue; 
	
	public SnmpTrapReceiverMy() {
		super.setName(this.getClass().getName());
	}
	
	public SnmpTrapReceiverMy(String name) {
		super.setName(name);
	}
	
	public SnmpTrapReceiverMy(ThreadGroup threadGroup, String name) {
		super(threadGroup, name);
	}
	
	public void init() {
		queue = new ConcurrentLinkedQueue<SnmpTrapResponseVo>();
	}

	@Override
	public void run() {
		logger.info("Started " + this.getClass().getSimpleName() + "...");
		
		while(!stop) {
			
			if(work) {
				logger.debug(getName() + " working...");
				SysUtils.sleep(1000);
			}
			else {
				Thread.yield();
			}
		}		
		
	}	
}
