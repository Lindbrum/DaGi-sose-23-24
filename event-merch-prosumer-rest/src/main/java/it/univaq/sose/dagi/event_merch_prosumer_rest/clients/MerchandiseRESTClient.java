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

	//These constants represent the keys used in the JSON payload for identifying and accessing merchandise details.
	public static final String FIELD_ID = "id";
	public static final String FIELD_EVENTID = "eventId";
	public static final String FIELD_BARCODE = "barCode";
	public static final String FIELD_NAME = "name";
	public static final String FIELD_DESCRIPTION = "description";

	//It holds the base URI for the merchandise service endpoint, which is injected from the application properties.
	@Value("${client.merchandise.uri}")
	private String baseUri;

	//This method constructs a URI by appending the eventId to the base URI and makes a GET request to fetch merchandise information for the specified event.
	//It returns the JSON node containing the merchandise details, specifically extracting the "body" part of the response payload.
	public JsonNode findEventMerch(Long eventId){
		URI uri = null;
		try {
			uri = new URI(baseUri + eventId);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		RestTemplate restTemplate = new RestTemplate();
		JsonNode responseEntityJson = restTemplate.getForObject(uri, JsonNode.class);
		return responseEntityJson.findValue("body"); //Access the payload
	}

}
