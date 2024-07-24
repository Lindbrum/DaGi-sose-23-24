package it.univaq.sose.dagi.sales_analysis_prosumer_rest.client;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;


//This class is designed to interact with a RESTful service to fetch user information.
//It uses Spring's dependency injection to get the base URI for the service and constructs requests to retrieve user details.
@Component
//@RefreshScope
public class CustomerRESTClient {

	public static final String FIELD_ID = "id";
	public static final String FIELD_AGE = "age";
	public static final String FIELD_GENDER = "gender";
	
	@Value("${client.authentication.customer.uri}")
	private String baseUri;

	//This method constructs a URI to fetch user details for a given list of user IDs. It starts by concatenating the IDs into
	//a comma-separated string, appends this to the base URI, and then creates a URI object. The method then uses RestTemplate
	//to send a GET request to this URI and returns the response as a JsonNode object. If URI creation fails, it prints the stack trace.
	public JsonNode fetchUsersInfo(Long[] userIds){
		URI uri = null;
		StringBuilder urlArray = new StringBuilder();
		for(Long id : userIds) {
			urlArray.append(id);
			urlArray.append(',');
		}
		urlArray.deleteCharAt(urlArray.length() - 1);
		String builtString = urlArray.toString();
		System.out.println("================"+baseUri + builtString);
		try {
			uri = new URI(baseUri + builtString);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.getForObject(uri, JsonNode.class);
	}

}
