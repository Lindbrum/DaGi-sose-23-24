package it.univaq.sose.dagi.wsdltypes;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import jakarta.xml.ws.WebEndpoint;
import jakarta.xml.ws.WebServiceClient;
import jakarta.xml.ws.WebServiceFeature;
import jakarta.xml.ws.Service;

/**
 * This class was generated by Apache CXF 4.0.4
 * 2024-07-21T11:47:40.856+02:00
 * Generated source version: 4.0.4
 *
 */
@WebServiceClient(name = "EventManagementImplService",
                  wsdlLocation = "file:/C:/Users/Roger/woskspace/Spring_tool_suit_4.23.1/DaGi-sose-23-24/event-merch-prosumer-rest/src/main/resources/wsdl/service.wsdl",
                  targetNamespace = "http://wsdltypes.dagi.sose.univaq.it/")
public class EventManagementImplService extends Service {

    public static final URL WSDL_LOCATION;

    public static final QName SERVICE = new QName("http://wsdltypes.dagi.sose.univaq.it/", "EventManagementImplService");
    public static final QName EventManagementImplPort = new QName("http://wsdltypes.dagi.sose.univaq.it/", "EventManagementImplPort");
    static {
        URL url = null;
        try {
            url = new URL("file:/C:/Users/Roger/woskspace/Spring_tool_suit_4.23.1/DaGi-sose-23-24/event-merch-prosumer-rest/src/main/resources/wsdl/service.wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(EventManagementImplService.class.getName())
                .log(java.util.logging.Level.INFO,
                     "Can not initialize the default wsdl from {0}", "file:/C:/Users/Roger/woskspace/Spring_tool_suit_4.23.1/DaGi-sose-23-24/event-merch-prosumer-rest/src/main/resources/wsdl/service.wsdl");
        }
        WSDL_LOCATION = url;
    }

    public EventManagementImplService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public EventManagementImplService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public EventManagementImplService() {
        super(WSDL_LOCATION, SERVICE);
    }

    public EventManagementImplService(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    public EventManagementImplService(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    public EventManagementImplService(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }




    /**
     *
     * @return
     *     returns EventManagementPort
     */
    @WebEndpoint(name = "EventManagementImplPort")
    public EventManagementPort getEventManagementImplPort() {
        return super.getPort(EventManagementImplPort, EventManagementPort.class);
    }

    /**
     *
     * @param features
     *     A list of {@link jakarta.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns EventManagementPort
     */
    @WebEndpoint(name = "EventManagementImplPort")
    public EventManagementPort getEventManagementImplPort(WebServiceFeature... features) {
        return super.getPort(EventManagementImplPort, EventManagementPort.class, features);
    }

}