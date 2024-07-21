package it.univaq.sose.dagi.event_merch_prosumer_rest.clients;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Value;
//import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;

@Component
//@RefreshScope
public class MerchandiseRESTClient {

	public static final String FIELD_ID = "id";
	public static final String FIELD_EVENTID = "eventId";
	public static final String FIELD_BARCODE = "barCode";
	public static final String FIELD_NAME = "name";
	public static final String FIELD_DESCRIPTION = "description";

	@Value("${client.merchandise.uri}")
	private String baseUri;

	public JsonNode findEventMerch(Long eventId){
		URI uri = null;
		try {
			uri = new URI(baseUri + eventId);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.getForObject(uri, JsonNode.class);
	}

}
