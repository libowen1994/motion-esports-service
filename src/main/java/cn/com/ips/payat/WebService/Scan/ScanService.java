/**
 * ScanService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package cn.com.ips.payat.WebService.Scan;

public interface ScanService extends java.rmi.Remote {
    public String scanPay(String scanPayReq) throws java.rmi.RemoteException;
    public String barCodeScanPay(String barCodeScanPay) throws java.rmi.RemoteException;
}
