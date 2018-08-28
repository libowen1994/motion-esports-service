package cn.com.ips.payat.WebService.OrderQuery;

public class OrderQueryServiceProxy implements OrderQueryService {
  private String _endpoint = null;
  private OrderQueryService orderQueryService = null;
  
  public OrderQueryServiceProxy() {
    _initOrderQueryServiceProxy();
  }
  
  public OrderQueryServiceProxy(String endpoint) {
    _endpoint = endpoint;
    _initOrderQueryServiceProxy();
  }
  
  private void _initOrderQueryServiceProxy() {
    try {
      orderQueryService = (new WSOrderQueryLocator()).getWSOrderQuerySoap();
      if (orderQueryService != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)orderQueryService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)orderQueryService)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (orderQueryService != null)
      ((javax.xml.rpc.Stub)orderQueryService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public OrderQueryService getOrderQueryService() {
    if (orderQueryService == null)
      _initOrderQueryServiceProxy();
    return orderQueryService;
  }
  
  public String getOrderByMerBillNo(String orderQuery) throws java.rmi.RemoteException{
    if (orderQueryService == null)
      _initOrderQueryServiceProxy();
    return orderQueryService.getOrderByMerBillNo(orderQuery);
  }
  
  public String getOrderByBankNo(String orderQuery) throws java.rmi.RemoteException{
    if (orderQueryService == null)
      _initOrderQueryServiceProxy();
    return orderQueryService.getOrderByBankNo(orderQuery);
  }
  
  public String getOrderByTime(String orderQuery) throws java.rmi.RemoteException{
    if (orderQueryService == null)
      _initOrderQueryServiceProxy();
    return orderQueryService.getOrderByTime(orderQuery);
  }
  
  
}