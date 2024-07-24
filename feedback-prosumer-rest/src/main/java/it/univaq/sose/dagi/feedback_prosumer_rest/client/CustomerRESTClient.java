package it.univaq.sose.dagi.feedback_prosumer_rest.client;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;

@Component
//@RefreshScope

//This class is designed to interact with a RESTful service to fetch information about users based on their IDs, ages and genders.
//It constructs a URI for the request, sends the request using RestTemplate, and processes the response.
public class CustomerRESTClient {

	public static final String FIELD_ID = "id";
	public static final String FIELD_AGE = "age";
	public static final String FIELD_GENDER = "gender";
	
	@Value("${client.authentication.customer.uri}")
	private String baseUri;

	//This method fetches user information for the given array of user IDs. It builds a URI by appending each user ID
	//to the base URI, then sends an HTTP GET request using RestTemplate. The method returns the JSON payload containing
	//user information from the response. If the URI is malformed, it catches and prints any URISyntaxException that arises.
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
		JsonNode responseEntityJson = restTemplate.getForObject(uri, JsonNode.class);
		return responseEntityJson;
	}

}
