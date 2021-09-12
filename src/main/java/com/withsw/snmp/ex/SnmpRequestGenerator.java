package com.withsw.snmp.ex;

import java.net.InetAddress;
import java.util.Vector;

import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.UdpAddress;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;

import com.withsw.util.SysUtils;

public class SnmpRequestGenerator {

    static int defaultPort = 161;
    //static String defaultIP = "175.112.229.9";
    static String defaultIP = "www.withsw.com";

    static void testGetNext() throws java.io.IOException {
        //1. Make Protocol Data Unit
        PDU pdu = new PDU();
        pdu.add(new VariableBinding(new OID("1.3.6.1.2.1.1.1.0"))); // sysDescr
        //pdu.add(new VariableBinding(new OID("1.3.6.1.2.1.2.1"))); // ifNumber
        pdu.setType(PDU.GETNEXT);

        //2. Make target
        CommunityTarget target = new CommunityTarget();
        UdpAddress targetAddress = new UdpAddress();
        targetAddress.setInetAddress(InetAddress.getByName(defaultIP));
        targetAddress.setPort(defaultPort);
        target.setAddress(targetAddress);
        target.setCommunity(new OctetString("kerusia"));
        target.setVersion(SnmpConstants.version1);

        //3. Make SNMP Message. Simple!
        Snmp snmp = new Snmp(new DefaultUdpTransportMapping());

        //4. Send Message and Recieve Response
        snmp.listen();
        ResponseEvent response = snmp.send(pdu, target);
        SysUtils.sleep(5000);
        if (response.getResponse() == null) {
            System.out.println("Error: There is some problems.");
        } else {
            Vector<? extends VariableBinding> variableBindings = response.getResponse().getVariableBindings();
                for( int i = 0; i < variableBindings.size(); i++){
                    System.out.println(variableBindings.get(i));
                }
        }
        snmp.close();
    }

    public static void main(String[] args) throws java.io.IOException {
        try {
            testGetNext();
        } catch (Exception ex) {
            System.out.println ("ex *** : " + ex);
            ex.printStackTrace ();
        }
    }	
}
