package it.univaq.sose.dagi.feedback_prosumer_rest.client;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import it.univaq.sose.dagi.feedback_prosumer_rest.model.Feedback;
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
	
	public FeedbackSOAPClient() {
		//super();
		this.factory = new ObjectFactory();
	}

	//This method retrieves feedback data for a given event ID. It constructs a SOAP request, sends it to the web service, and
	//processes the response to extract feedback details. It maps the response data into a list of Feedback objects and
	//returns this list. If the URL is malformed, it catches and prints any MalformedURLException that occurs.
	public List<Feedback> requestEventFeedbacks(Long eventId) throws ServiceException_Exception {
		try {
			//@Value("${service.feedback.soap.wsdl}")
			//String wsdlUrl;
			URL url = new URL("http://localhost:8081/event-management-soap/event-management-soap?wsdl");
			EventManagementImplService service = new EventManagementImplService(url);
			EventManagementPort port = service.getEventManagementImplPort();
			FetchEventFeedbackRequest requestParams = factory.createFetchEventFeedbackRequest();
			requestParams.setEventId(eventId);
			FetchEventFeedbackResponse response = port.fetchEventFeedback(requestParams);
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
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
