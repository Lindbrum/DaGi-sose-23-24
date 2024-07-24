package it.univaq.sose.dagi.feedback_prosumer_rest.client;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;

@Component
//@RefreshScope
public class CustomerRESTClient {

	public static final String FIELD_ID = "id";
	public static final String FIELD_AGE = "age";
	public static final String FIELD_GENDER = "gender";
	
	@Value("${client.authentication.customer.uri}")
	private String baseUri;

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
