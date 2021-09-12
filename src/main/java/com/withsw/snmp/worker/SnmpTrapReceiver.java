package com.withsw.snmp.worker;

import java.io.IOException;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snmp4j.MessageDispatcherImpl;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.mp.MPv1;
import org.snmp4j.mp.MPv2c;
import org.snmp4j.mp.MPv3;
import org.snmp4j.security.AuthMD5;
import org.snmp4j.security.Priv3DES;
import org.snmp4j.security.PrivAES128;
import org.snmp4j.security.PrivAES192;
import org.snmp4j.security.PrivAES256;
import org.snmp4j.security.SecurityModels;
import org.snmp4j.security.SecurityProtocols;
import org.snmp4j.security.USM;
import org.snmp4j.security.UsmUser;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.GenericAddress;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.TcpAddress;
import org.snmp4j.smi.UdpAddress;
import org.snmp4j.transport.DefaultTcpTransportMapping;
import org.snmp4j.transport.DefaultUdpTransportMapping;
import org.snmp4j.util.MultiThreadedMessageDispatcher;
import org.snmp4j.util.ThreadPool;

public class SnmpTrapReceiver {
	private static final Logger logger = LoggerFactory.getLogger(SnmpTrapReceiver.class);
	private static final int DEFAULT_THREAD_POOL_CNT = 2;
	private static final String DEFAULT_THREAD_POOL_NM = "SnmpTrap";
	
	private MultiThreadedMessageDispatcher dispatcher;
	private ThreadPool threadPool;
	private Snmp snmp = null;
	private Address listenAddress;
	private TransportMapping<?> transport;
	
	private String threadPoolNm;
	private int threadPoolCnt;
	
	public SnmpTrapReceiver() {
		init();
	}
	
	public SnmpTrapReceiver(int threadPoolCnt) {
		this.threadPoolCnt = threadPoolCnt;
		init();
	}
	
	public SnmpTrapReceiver(int threadPoolCnt, String threadPoolNm) {
		this.threadPoolCnt = threadPoolCnt;
		this.threadPoolNm = threadPoolNm;
		init();
	}
	
	private void init() {
		if(threadPoolCnt == 0) {
			threadPoolCnt = DEFAULT_THREAD_POOL_CNT;
		}
		
		if(threadPoolNm == null || threadPoolNm.length() == 0) {
			threadPoolNm = DEFAULT_THREAD_POOL_NM;
		}		
	}
	
	private void listen() throws UnknownHostException, IOException {		
		threadPool = ThreadPool.create(threadPoolNm, threadPoolCnt);
		dispatcher = new MultiThreadedMessageDispatcher(threadPool, new MessageDispatcherImpl());
		listenAddress = GenericAddress.parse(System.getProperty("snmp4j.listenAddress", "udp:0.0.0.0/162"));
		
		if(listenAddress instanceof UdpAddress) {
			transport = new DefaultUdpTransportMapping((UdpAddress) listenAddress);
		}
		else {
			transport = new DefaultTcpTransportMapping((TcpAddress) listenAddress);
		}

		// v3 SECURITY
		USM usm = new USM(SecurityProtocols.getInstance().addDefaultProtocols(), new OctetString(MPv3.createLocalEngineID()), 0);
		SecurityProtocols.getInstance().addPrivacyProtocol(new PrivAES192());
		SecurityProtocols.getInstance().addPrivacyProtocol(new PrivAES256());
		SecurityProtocols.getInstance().addPrivacyProtocol(new Priv3DES());
		
		usm.setEngineDiscoveryEnabled(true);
		
		SecurityModels.getInstance().addSecurityModel(usm);
		
		snmp = new Snmp(dispatcher, transport);
		snmp.getMessageDispatcher().addMessageProcessingModel(new MPv1());
		snmp.getMessageDispatcher().addMessageProcessingModel(new MPv2c());
		snmp.getMessageDispatcher().addMessageProcessingModel(new MPv3(usm));
		
		String username = "username";         // SET THIS
		String authpassphrase = "authpassphrase";   // SET THIS
		String privacypassphrase = "privacypassphrase";   // SET THIS
      
		// SET THE SECURITY PROTOCOLS HERE
		snmp.getUSM().addUser(new OctetString(username)
    	        			  , new UsmUser(new OctetString(username)
    	        					  		, AuthMD5.ID, new OctetString(authpassphrase)
    	        					  		, PrivAES128.ID, new OctetString(privacypassphrase)));
		
		
		snmp.listen();
	}
	
	public void startup() {
		try {
			logger.info("ready to listen SnmpTrap...");
			
			listen();
			snmp.addCommandResponder(new SnmpTrapReceiverWorker());
			
			logger.info("started to listen SnmpTrap...");
		} catch (UnknownHostException e) {
			logger.error("occured UnknownHostException: " + e.getMessage());
		} catch (IOException e) {
			logger.error("occured IOException: " + e.getMessage());
		}
	}
	
	public void shutdown() {
		logger.info("start to shut down SnmpTrapReceiver...");
		
		threadPool.cancel();
		
		try {
			snmp.close();
		} catch (IOException e) {
			logger.error("occured IOException: " + e.getMessage());
		}
		
		logger.info("finished shutdown SnmpTrapReceiver.");
	}
}
