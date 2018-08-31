/**
 * OrderQueryService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package cn.com.ips.payat.WebService.OrderQuery;

public interface OrderQueryService extends java.rmi.Remote {
    public String getOrderByMerBillNo(String orderQuery) throws java.rmi.RemoteException;
    public String getOrderByBankNo(String orderQuery) throws java.rmi.RemoteException;
    public String getOrderByTime(String orderQuery) throws java.rmi.RemoteException;
}
