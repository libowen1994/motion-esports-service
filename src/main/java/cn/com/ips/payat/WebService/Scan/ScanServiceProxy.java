package cn.com.ips.payat.WebService.Scan;

public class ScanServiceProxy implements ScanService {
  private String _endpoint = null;
  private ScanService scanService = null;
  
  public ScanServiceProxy() {
    _initScanServiceProxy();
  }
  
  public ScanServiceProxy(String endpoint) {
    _endpoint = endpoint;
    _initScanServiceProxy();
  }
  
  private void _initScanServiceProxy() {
    try {
      scanService = (new WSScanLocator()).getWSScanSoap();
      if (scanService != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)scanService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)scanService)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (scanService != null)
      ((javax.xml.rpc.Stub)scanService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public ScanService getScanService() {
    if (scanService == null)
      _initScanServiceProxy();
    return scanService;
  }
  
  public String scanPay(String scanPayReq) throws java.rmi.RemoteException{
    if (scanService == null)
      _initScanServiceProxy();
    return scanService.scanPay(scanPayReq);
  }
  
  public String barCodeScanPay(String barCodeScanPay) throws java.rmi.RemoteException{
    if (scanService == null)
      _initScanServiceProxy();
    return scanService.barCodeScanPay(barCodeScanPay);
  }
  
  
}