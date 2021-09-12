package com.withsw.snmp.ex;

import java.util.Iterator;
import java.util.Vector;

import org.snmp4j.CommandResponder;
import org.snmp4j.CommandResponderEvent;
import org.snmp4j.PDU;
import org.snmp4j.PDUv1;
import org.snmp4j.smi.VariableBinding;

public class SnmpTrapReceiver implements CommandResponder {

	@Override
	public void processPdu(CommandResponderEvent event) {
	    PDU pdu = event.getPDU();
	    if (pdu.getType() == PDU.V1TRAP) {

	      PDUv1 pduV1 = (PDUv1) pdu;
	      System.out.println("");
	      System.out.println("===== NEW SNMP 1 TRAP RECEIVED ====");
	      System.out.println("agentAddr " + pduV1.getAgentAddress().toString());
	      System.out.println("enterprise " + pduV1.getEnterprise().toString());
	      System.out.println("timeStam" + String.valueOf(pduV1.getTimestamp()));
	      System.out.println("genericTrap"+ String.valueOf(pduV1.getGenericTrap()));
	      System.out.println("specificTrap " + String.valueOf(pduV1.getSpecificTrap()));
	      System.out.println("snmpVersion " + String.valueOf(PDU.V1TRAP));
	      System.out.println("communityString " + new String(event.getSecurityName()));

	    } else if (pdu.getType() == PDU.TRAP) {
		      System.out.println("");
		      System.out.println("===== NEW SNMP 2/3 TRAP RECEIVED ====");

		   System.out.println("errorStatus " + String.valueOf(pdu.getErrorStatus()));
		   System.out.println("errorIndex "+ String.valueOf(pdu.getErrorIndex()));
	       System.out.println("requestID " +String.valueOf(pdu.getRequestID()));
	       System.out.println("snmpVersion " + String.valueOf(PDU.TRAP));
	       System.out.println("communityString " + new String(event.getSecurityName()));

	    }

	    Vector<? extends VariableBinding> varBinds = pdu.getVariableBindings();
	    if (varBinds != null && !varBinds.isEmpty()) {
	      Iterator<? extends VariableBinding> varIter = varBinds.iterator();

	      StringBuilder resultset = new StringBuilder();
	      resultset.append("-----");
	      while (varIter.hasNext()) {
	        VariableBinding vb = varIter.next();

	        String syntaxstr = vb.getVariable().getSyntaxString();
	        int syntax = vb.getVariable().getSyntax();
	        System.out.println( "OID: " + vb.getOid());
	        System.out.println("Value: " +vb.getVariable());	        
	        System.out.println("syntaxstring: " + syntaxstr );
	        System.out.println("syntax: " + syntax);
	        System.out.println("------");
	      }

	      
	    }
	    System.out.println("==== TRAP END ===");
	    System.out.println("");
	}

}
