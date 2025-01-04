package it.univaq.sose.dagi.organizer_client.client;

import java.util.ArrayList;
import java.util.List;

import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxrs.client.ClientConfiguration;
import org.apache.cxf.jaxrs.client.JAXRSClientFactory;
import org.apache.cxf.jaxrs.client.WebClient;

import io.swagger.api.AuthenticationApi;
import io.swagger.api.MerchandiseApi;
import io.swagger.model.merchandise_provider.Merchandise;
import it.univaq.sose.dagi.organizer_client.jackson.DateCompatibleJacksonJsonProvider;

public class MerchandiseRESTClient {
	//Singleton
		private static MerchandiseRESTClient instance = null;
		
		private MerchandiseApi api;
		
		private MerchandiseRESTClient() {
			setup();
		}
		
		public static MerchandiseRESTClient getInstance() {
			if(instance == null) {
				instance = new MerchandiseRESTClient();
			}
			return instance;
		}
		
		private void setup() {
	        DateCompatibleJacksonJsonProvider provider = new DateCompatibleJacksonJsonProvider();
	        List providers = new ArrayList();
	        providers.add(provider);
	        
	        api = JAXRSClientFactory.create("http://localhost:8080/api/merch", MerchandiseApi.class, providers);
	        org.apache.cxf.jaxrs.client.Client client = WebClient.client(api);
	        
	        ClientConfiguration config = WebClient.getConfig(client);
	        //config.getOutInterceptors().add(new LoggingOutInterceptor());
	        //config.getInInterceptors().add(new LoggingInInterceptor());
	    }
		
		public long createMerchandise(Merchandise newMerch) {
			Merchandise merchWithId = api.create1(newMerch);
			return merchWithId.getId();
		}
		
		public boolean modifyRelatedEvent(long merchId, Long eventId) {
			Merchandise updated = api.addToEvent1(eventId != null ? eventId.toString() : "null", merchId);
			return updated.getEventId() == eventId; //check if the event id was successfully updated
		}
		
		public List<Merchandise> fetchMerchandisePage(int page, String sortBy){
			List<Merchandise> result = api.getMerchandisePage1(page, sortBy);
			return result;
		}
}
