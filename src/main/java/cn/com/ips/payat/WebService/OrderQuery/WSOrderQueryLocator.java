/**
 * WSOrderQueryLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package cn.com.ips.payat.WebService.OrderQuery;

public class WSOrderQueryLocator extends org.apache.axis.client.Service implements WSOrderQuery {

    public WSOrderQueryLocator() {
    }


    public WSOrderQueryLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public WSOrderQueryLocator(String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for WSOrderQuerySoap
    private String WSOrderQuerySoap_address = "http://newpay.ips.com.cn:80/psfp-entry/services/order";

    public String getWSOrderQuerySoapAddress() {
        return WSOrderQuerySoap_address;
    }

    // The WSDD service name defaults to the port name.
    private String WSOrderQuerySoapWSDDServiceName = "WSOrderQuerySoap";

    public String getWSOrderQuerySoapWSDDServiceName() {
        return WSOrderQuerySoapWSDDServiceName;
    }

    public void setWSOrderQuerySoapWSDDServiceName(String name) {
        WSOrderQuerySoapWSDDServiceName = name;
    }

    public OrderQueryService getWSOrderQuerySoap() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(WSOrderQuerySoap_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getWSOrderQuerySoap(endpoint);
    }

    public OrderQueryService getWSOrderQuerySoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            cn.com.ips.payat.WebService.OrderQuery.WSOrderQuerySoapBindingStub _stub = new cn.com.ips.payat.WebService.OrderQuery.WSOrderQuerySoapBindingStub(portAddress, this);
            _stub.setPortName(getWSOrderQuerySoapWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setWSOrderQuerySoapEndpointAddress(String address) {
        WSOrderQuerySoap_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (OrderQueryService.class.isAssignableFrom(serviceEndpointInterface)) {
                cn.com.ips.payat.WebService.OrderQuery.WSOrderQuerySoapBindingStub _stub = new cn.com.ips.payat.WebService.OrderQuery.WSOrderQuerySoapBindingStub(new java.net.URL(WSOrderQuerySoap_address), this);
                _stub.setPortName(getWSOrderQuerySoapWSDDServiceName());
                return _stub;
            }
        }
        catch (Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        String inputPortName = portName.getLocalPart();
        if ("WSOrderQuerySoap".equals(inputPortName)) {
            return getWSOrderQuerySoap();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://payat.ips.com.cn/WebService/OrderQuery", "WSOrderQuery");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://payat.ips.com.cn/WebService/OrderQuery", "WSOrderQuerySoap"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(String portName, String address) throws javax.xml.rpc.ServiceException {
        
if ("WSOrderQuerySoap".equals(portName)) {
            setWSOrderQuerySoapEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
