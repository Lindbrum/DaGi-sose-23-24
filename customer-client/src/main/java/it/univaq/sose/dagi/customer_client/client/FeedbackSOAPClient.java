package it.univaq.sose.dagi.customer_client.client;

import java.net.MalformedURLException;
import java.net.URL;

import it.univaq.sose.dagi.wsdltypes.CreateFeedbackRequest;
import it.univaq.sose.dagi.wsdltypes.CreateFeedbackResponse;
import it.univaq.sose.dagi.wsdltypes.EventManagementImplService;
import it.univaq.sose.dagi.wsdltypes.EventManagementPort;
import it.univaq.sose.dagi.wsdltypes.FeedbackData;
import it.univaq.sose.dagi.wsdltypes.ObjectFactory;
import it.univaq.sose.dagi.wsdltypes.ServiceException_Exception;

public class FeedbackSOAPClient {

	// Singleton
	private static FeedbackSOAPClient instance = null;

	private ObjectFactory factory;
	private String wsdlUrl = "http://localhost:8080/api/event/?wsdl";

	private EventManagementPort port;

	private FeedbackSOAPClient() {
		// super();
		this.factory = new ObjectFactory();
		try {
			URL url = new URL(wsdlUrl);
			EventManagementImplService service = new EventManagementImplService(url);
			this.port = service.getEventManagementImplPort();
		} catch (MalformedURLException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public static FeedbackSOAPClient getInstance() {
		if (instance == null) {
			instance = new FeedbackSOAPClient();
		}
		return instance;
	}
	
	public String submitFeedback(long userId, long eventId, int rating, String body) throws ServiceException_Exception {
		CreateFeedbackRequest requestParams = factory.createCreateFeedbackRequest();
		FeedbackData feedback = factory.createFeedbackData();
		feedback.setUserId(userId);
		feedback.setEventId(eventId);
		feedback.setRating(rating);
		feedback.setBody(body);
		requestParams.setFeedbackData(feedback);
		
		CreateFeedbackResponse response = port.createFeedback(requestParams);
		return response.getMessage();
	}
}
