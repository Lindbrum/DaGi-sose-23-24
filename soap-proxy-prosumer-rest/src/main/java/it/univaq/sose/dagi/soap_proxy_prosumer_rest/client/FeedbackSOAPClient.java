package it.univaq.sose.dagi.soap_proxy_prosumer_rest.client;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import it.univaq.sose.dagi.soap_proxy_prosumer_rest.model.Feedback;
import it.univaq.sose.dagi.wsdltypes.CreateFeedbackRequest;
import it.univaq.sose.dagi.wsdltypes.CreateFeedbackResponse;
import it.univaq.sose.dagi.wsdltypes.EventManagementImplService;
import it.univaq.sose.dagi.wsdltypes.EventManagementPort;
import it.univaq.sose.dagi.wsdltypes.FeedbackData;
import it.univaq.sose.dagi.wsdltypes.FetchEventFeedbackRequest;
import it.univaq.sose.dagi.wsdltypes.FetchEventFeedbackResponse;
import it.univaq.sose.dagi.wsdltypes.FetchEventFeedbackResponse.FeedbackList;
import it.univaq.sose.dagi.wsdltypes.ObjectFactory;
import it.univaq.sose.dagi.wsdltypes.ServiceException_Exception;

//This class facilitates communication with a SOAP web service to retrieve feedback data for a specified event.
//It uses an ObjectFactory to construct SOAP request objects and process responses.
@Component
public class FeedbackSOAPClient {

	private ObjectFactory factory;
	
	//@Value("${client.soap.wsdl}")
		private String wsdlUrl = "http://localhost:8080/api/soap/?wsdl";
	
	private EventManagementPort port;
	
	public FeedbackSOAPClient() {
		//super();
		this.factory = new ObjectFactory();
		URL url;
		try {
			url = new URL(wsdlUrl);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		EventManagementImplService service = new EventManagementImplService(url);
		this.port = service.getEventManagementImplPort();
	}

	//This method retrieves feedback data for a given event ID. It constructs a SOAP request, sends it to the web service, and
	//processes the response to extract feedback details. It maps the response data into a list of Feedback objects and
	//returns this list. If the URL is malformed, it catches and prints any MalformedURLException that occurs.
	public List<Feedback> requestEventFeedbacks(Long eventId) throws ServiceException_Exception {
		FetchEventFeedbackRequest requestParams = factory.createFetchEventFeedbackRequest();
		requestParams.setEventId(eventId);
		FetchEventFeedbackResponse response = this.port.fetchEventFeedback(requestParams);
		FeedbackList feedbackList = response.getFeedbackList();
		List<FeedbackData> xmlFeedbacks = feedbackList.getFeedbackData();
		List<Feedback> feedbacks = new ArrayList<>();
		for(FeedbackData xmlFeedback : xmlFeedbacks) {
			Feedback feedback = new Feedback();
			feedback.setId(xmlFeedback.getFeedbackId());
			feedback.setUserId(xmlFeedback.getUserId());
			feedback.setEventId(xmlFeedback.getEventId());
			feedback.setRating(xmlFeedback.getRating());
			feedback.setBody(xmlFeedback.getBody());
			feedbacks.add(feedback);
		}
		return feedbacks;
	}
	
	public String submitFeedback(long userId, long eventId, int rating, String body) throws ServiceException_Exception {
		CreateFeedbackRequest requestParams = factory.createCreateFeedbackRequest();
		FeedbackData feedback = factory.createFeedbackData();
		feedback.setUserId(userId);
		feedback.setEventId(eventId);
		feedback.setRating(rating);
		feedback.setBody(body);
		requestParams.setFeedbackData(feedback);
		
		CreateFeedbackResponse response = this.port.createFeedback(requestParams);
		return response.getMessage();
	}
}
