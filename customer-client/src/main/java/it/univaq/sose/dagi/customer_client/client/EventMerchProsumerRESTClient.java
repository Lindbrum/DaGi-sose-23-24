package it.univaq.sose.dagi.customer_client.client;

import java.util.ArrayList;
import java.util.List;

import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxrs.client.ClientConfiguration;
import org.apache.cxf.jaxrs.client.JAXRSClientFactory;
import org.apache.cxf.jaxrs.client.WebClient;

import io.swagger.api.EventMerchProsumerApi;
import io.swagger.model.event_merch_prosumer.EventWithMerch;
import it.univaq.sose.dagi.customer_client.jackson.DateCompatibleJacksonJsonProvider;

public class EventMerchProsumerRESTClient {
	//Singleton
	private static EventMerchProsumerRESTClient instance = null;
	
	private EventMerchProsumerApi api;
	
	public static EventMerchProsumerRESTClient getInstance() {
		if(instance == null) {
			instance = new EventMerchProsumerRESTClient();
		}
		
		return instance;
	}
	
	private EventMerchProsumerRESTClient() {
		setup();
	}
	
	private void setup() {
        DateCompatibleJacksonJsonProvider provider = new DateCompatibleJacksonJsonProvider();
        List providers = new ArrayList();
        providers.add(provider);
        
        api = JAXRSClientFactory.create("http://localhost:8080/api/event-merch", EventMerchProsumerApi.class, providers);
        org.apache.cxf.jaxrs.client.Client client = WebClient.client(api);
        
        ClientConfiguration config = WebClient.getConfig(client);
        config.getOutInterceptors().add(new LoggingOutInterceptor());
        config.getInInterceptors().add(new LoggingInInterceptor());
    }
	
	public EventWithMerch fetchEventInfoAndMerchandise(Long eventId) {
		EventWithMerch result = api.getEventInfo1(eventId);
		return result;
	}
}
