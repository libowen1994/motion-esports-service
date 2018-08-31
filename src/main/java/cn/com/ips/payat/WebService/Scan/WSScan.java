/**
 * WSScan.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package cn.com.ips.payat.WebService.Scan;

public interface WSScan extends javax.xml.rpc.Service {
    public String getWSScanSoapAddress();

    public cn.com.ips.payat.WebService.Scan.ScanService getWSScanSoap() throws javax.xml.rpc.ServiceException;

    public cn.com.ips.payat.WebService.Scan.ScanService getWSScanSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
