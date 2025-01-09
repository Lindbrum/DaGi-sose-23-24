package it.univaq.sose.dagi.soap_proxy_prosumer_rest.client;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
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

import it.univaq.sose.dagi.soap_proxy_prosumer_rest.Utility;
import it.univaq.sose.dagi.soap_proxy_prosumer_rest.model.Event;
import it.univaq.sose.dagi.wsdltypes.CreateEventRequest;
import it.univaq.sose.dagi.wsdltypes.CreateEventResponse;
import it.univaq.sose.dagi.wsdltypes.EventCatalogueRequest;
import it.univaq.sose.dagi.wsdltypes.EventCatalogueResponse;
import it.univaq.sose.dagi.wsdltypes.EventData;
import it.univaq.sose.dagi.wsdltypes.EventManagementImplService;
import it.univaq.sose.dagi.wsdltypes.EventManagementPort;
import it.univaq.sose.dagi.wsdltypes.FetchEventInfoRequest;
import it.univaq.sose.dagi.wsdltypes.FetchEventInfoResponse;
import it.univaq.sose.dagi.wsdltypes.ObjectFactory;
import it.univaq.sose.dagi.wsdltypes.OrganizerCatalogueRequest;
import it.univaq.sose.dagi.wsdltypes.OrganizerCatalogueResponse;
import it.univaq.sose.dagi.wsdltypes.ServiceException_Exception;
import jakarta.ws.rs.ServiceUnavailableException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EventSOAPClient {

	private static final Logger log = LoggerFactory.getLogger(EventSOAPClient.class);
	private ObjectFactory factory;

	private String wsdlUri = "event-management-soap/event-management-soap/?wsdl";

	private URL lastUrl;
	private EurekaClient eurekaClient;
	private EventManagementImplService service;
	private final List<InstanceInfo> lastInstancesCache = Collections.synchronizedList(new ArrayList<>());

	// It initializes the EventSOAPClient with an ObjectFactory instance.
	// It sets up the necessary configurations to interact with the SOAP service.
	public EventSOAPClient(EurekaClient eurekaClient) {
		// super();
		this.eurekaClient = eurekaClient;
		this.service = null;
		this.factory = new ObjectFactory();
	}

	
	//Returns an instance of the SOAP service among the ones registered to the discovery server
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
			log.info("New Retrieved BancomatService URL: {}", url);
			lastUrl = url;

			return service.getEventManagementImplPort();
		} catch (MalformedURLException e) {
			log.error("Malformed URL: {}", e.getMessage(), e);
			throw new ServiceUnavailableException("Malformed URL: " + e.getMessage());
		} catch (Exception e) {
			log.error("Failed to retrieve BancomatService URL: {}", e.getMessage(), e);
			throw new ServiceUnavailableException("Failed to retrieve BancomatService URL: " + e.getMessage());
		}
	}

	private List<InstanceInfo> deepCopyInstanceInfoList(List<InstanceInfo> instances) {
		return instances.stream().map(InstanceInfo::new) // Use the copy constructor
				.collect(Collectors.toList());
	}

	// Fetches event information from the SOAP service using the specified eventId.
	// It constructs a request, sends it to the web service, and processes the
	// response to create an Event object with
	// details such as name, location, description, start and end dates, and the
	// number of tickets available.
	// If any error occurs, it logs the issue and returns null.
	public Event requestEventInfo(Long eventId) throws ServiceException_Exception {
		FetchEventInfoRequest requestParams = factory.createFetchEventInfoRequest();
		requestParams.setEventId(eventId);

		EventManagementPort port = getService(); //load balancing
		FetchEventInfoResponse response = port.fetchEventInfo(requestParams);
		Event eventInfo = new Event();
		EventData eventData = response.getEventData();
		eventInfo.setId(eventId);
		eventInfo.setName(eventData.getName());
		eventInfo.setLocation(eventData.getLocation());
		eventInfo.setOrganizerId(eventData.getOrganizerId());
		eventInfo.setDescription(eventData.getDescription());
		eventInfo.setStartDate(Utility.toLocalDateTime(eventData.getStartDate()));
		eventInfo.setEndDate(Utility.toLocalDateTime(eventData.getEndDate()));
		eventInfo.setNrTickets(eventData.getNrTickets());

		System.out.println("\nThe response is " + response);
		return eventInfo;
	}

	public List<Event> requestEventCataloguePage(int page, String sortBy) {
		try {
			EventCatalogueRequest requestParams = factory.createEventCatalogueRequest();
			requestParams.setPage(page);
			requestParams.setSortBy(sortBy);

			EventManagementPort port = getService(); //load balancing
			EventCatalogueResponse response = port.eventCatalogue(requestParams);

			List<Event> cataloguePage = new ArrayList<>();
			List<EventData> xmlCatalogue = response.getEventList().getEventData();

			for (EventData xmlEvent : xmlCatalogue) {
				Event eventInfo = new Event();
				eventInfo.setId(xmlEvent.getEventId());
				eventInfo.setName(xmlEvent.getName());
				eventInfo.setLocation(xmlEvent.getLocation());
				eventInfo.setDescription(xmlEvent.getDescription());
				eventInfo.setStartDate(Utility.toLocalDateTime(xmlEvent.getStartDate()));
				eventInfo.setEndDate(Utility.toLocalDateTime(xmlEvent.getEndDate()));
				eventInfo.setNrTickets(xmlEvent.getNrTickets());
				cataloguePage.add(eventInfo);
			}

			return cataloguePage;
		} catch (ServiceException_Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Long createEvent(String name, String description, long organizerId, String location, LocalDateTime startDate,
			LocalDateTime endDate, int nrTickets) throws ServiceException_Exception {
		CreateEventRequest requestParams = factory.createCreateEventRequest();
		EventData event = factory.createEventData();
		event.setName(name);
		event.setDescription(description);
		event.setOrganizerId(organizerId);
		event.setLocation(location);
		event.setStartDate(Utility.toXMLCalendar(startDate));
		event.setEndDate(Utility.toXMLCalendar(endDate));
		event.setNrTickets(nrTickets);
		requestParams.setEventData(event);

		EventManagementPort port = getService(); //load balancing
		CreateEventResponse response = port.createEvent(requestParams);
		return response.getEventId();
	}

	public List<Event> requestOrganizerEventsPage(long organizerId, int page, String sortBy)
			throws ServiceException_Exception {
		OrganizerCatalogueRequest requestParams = factory.createOrganizerCatalogueRequest();
		requestParams.setOrganizerId(organizerId);
		requestParams.setPage(page);
		requestParams.setSortBy(sortBy);

		EventManagementPort port = getService(); //load balancing
		OrganizerCatalogueResponse response = port.organizerCatalogue(requestParams);
		List<Event> events = new ArrayList<>();
		for (EventData xmlEvent : response.getEventList().getEventData()) {
			Event event = new Event();
			event.setId(xmlEvent.getEventId());
			event.setName(xmlEvent.getName());
			event.setDescription(xmlEvent.getDescription());
			event.setOrganizerId(xmlEvent.getOrganizerId());
			event.setLocation(xmlEvent.getLocation());
			event.setStartDate(Utility.toLocalDateTime(xmlEvent.getStartDate()));
			event.setEndDate(Utility.toLocalDateTime(xmlEvent.getEndDate()));
			event.setNrTickets(xmlEvent.getNrTickets());

			events.add(event);

		}
		return events;
	}
}