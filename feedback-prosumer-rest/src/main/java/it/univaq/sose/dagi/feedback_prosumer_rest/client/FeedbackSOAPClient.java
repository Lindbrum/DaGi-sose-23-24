package it.univaq.sose.dagi.feedback_prosumer_rest.client;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;

import it.univaq.sose.dagi.feedback_prosumer_rest.model.Feedback;
import it.univaq.sose.dagi.wsdltypes.EventManagementImplService;
import it.univaq.sose.dagi.wsdltypes.EventManagementPort;
import it.univaq.sose.dagi.wsdltypes.FeedbackData;
import it.univaq.sose.dagi.wsdltypes.FetchEventFeedbackRequest;
import it.univaq.sose.dagi.wsdltypes.FetchEventFeedbackResponse;
import it.univaq.sose.dagi.wsdltypes.FetchEventFeedbackResponse.FeedbackList;
import it.univaq.sose.dagi.wsdltypes.ObjectFactory;
import it.univaq.sose.dagi.wsdltypes.ServiceException_Exception;
import jakarta.ws.rs.ServiceUnavailableException;
import lombok.extern.slf4j.Slf4j;

//This class facilitates communication with a SOAP web service to retrieve feedback data for a specified event.
//It uses an ObjectFactory to construct SOAP request objects and process responses.
@Slf4j
@Service
public class FeedbackSOAPClient {
	private ObjectFactory factory;
	private URL lastUrl;
	private EurekaClient eurekaClient;
	private EventManagementImplService service;
	private final List<InstanceInfo> lastInstancesCache = Collections.synchronizedList(new ArrayList<>());
	private static final Logger log = LoggerFactory.getLogger(FeedbackSOAPClient.class);

	private String wsdlUri = "event-management-soap/event-management-soap/?wsdl";

	public FeedbackSOAPClient(EurekaClient eurekaClient) {
		// super();
		this.eurekaClient = eurekaClient;
		this.service = null;
		this.factory = new ObjectFactory();
	}

	// Returns an instance of the SOAP service among the ones registered to the
	// discovery server
	private EventManagementPort getService() {
		try {
			List<InstanceInfo> instances = Optional
					.ofNullable(eurekaClient.getInstancesByVipAddress("event-management-soap", false))
					.filter(list -> !list.isEmpty()).orElseGet(() -> {

						log.warn("Using cached copy of the event management SOAP service");
						log.warn("lastInstancesCache {}", lastInstancesCache);
						synchronized (lastInstancesCache) {
							return new ArrayList<>(lastInstancesCache); // Return a copy of the cached instances
						}
					});

			// Update the instance cache synchronously
			synchronized (lastInstancesCache) {
				lastInstancesCache.clear();
				lastInstancesCache.addAll(deepCopyInstanceInfoList(instances));
			}

			// Remove the last used instance from the list
			if (lastUrl != null) {
				instances.removeIf(instance -> {
					try {
						return Objects.equals(new URL(instance.getHomePageUrl() + wsdlUri), lastUrl);
					} catch (MalformedURLException e) {
						log.error("Malformed URL while filtering instances: {}", e.getMessage(), e);
						return false;
					}
				});
			}

			// If no alternative instances are available, use the last used instance
			if (instances.isEmpty()) {
				log.warn("No alternative instances available for event-management-soap, using the last used instance");
				if (service != null) {
					return service.getEventManagementImplPort();
				} else {
					throw new ServiceUnavailableException(
							"event-management-soap: No alternative instances available and no previously used instance available");
				}
			}

			// Shuffle the list to select a random instance
			Collections.shuffle(instances);
			InstanceInfo instance = instances.get(0);
			String eurekaUrl = instance.getHomePageUrl() + wsdlUri;
			URL url = new URL(eurekaUrl);
			service = new EventManagementImplService(url);
			log.info("New Retrieved Event Management SOAP URL: {}", url);
			lastUrl = url;

			return service.getEventManagementImplPort();
		} catch (MalformedURLException e) {
			log.error("Malformed URL: {}", e.getMessage(), e);
			throw new ServiceUnavailableException("Malformed URL: " + e.getMessage());
		} catch (Exception e) {
			log.error("Failed to retrieve Event Management SOAP URL: {}", e.getMessage(), e);
			throw new ServiceUnavailableException("Failed to retrieve Event Management SOAP URL: " + e.getMessage());
		}
	}

	private List<InstanceInfo> deepCopyInstanceInfoList(List<InstanceInfo> instances) {
		return instances.stream().map(InstanceInfo::new) // Use the copy constructor
				.collect(Collectors.toList());
	}

	// This method retrieves feedback data for a given event ID. It constructs a
	// SOAP request, sends it to the web service, and
	// processes the response to extract feedback details. It maps the response data
	// into a list of Feedback objects and
	// returns this list. If the URL is malformed, it catches and prints any
	// MalformedURLException that occurs.
	public List<Feedback> requestEventFeedbacks(Long eventId) throws ServiceException_Exception {

		EventManagementPort port = getService(); // load balancing
		FetchEventFeedbackRequest requestParams = factory.createFetchEventFeedbackRequest();
		requestParams.setEventId(eventId);
		FetchEventFeedbackResponse response = port.fetchEventFeedback(requestParams);
		FeedbackList feedbackList = response.getFeedbackList();
		List<FeedbackData> xmlFeedbacks = feedbackList.getFeedbackData();
		List<Feedback> feedbacks = new ArrayList<>();
		for (FeedbackData xmlFeedback : xmlFeedbacks) {
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
}
