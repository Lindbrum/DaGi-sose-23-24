package it.univaq.sose.dagi.soap_proxy_prosumer_rest.client;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

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

@Component
public class EventSOAPClient {
	
	private ObjectFactory factory;
	
	//@Value("${client.soap.wsdl}")
	private String wsdlUrl = "http://localhost:8080/api/soap/?wsdl";
	
	private EventManagementPort port;
	
	//It initializes the EventSOAPClient with an ObjectFactory instance.
	//It sets up the necessary configurations to interact with the SOAP service.
	public EventSOAPClient() {
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

	//Fetches event information from the SOAP service using the specified eventId.
	//It constructs a request, sends it to the web service, and processes the response to create an Event object with
	//details such as name, location, description, start and end dates, and the number of tickets available.
	//If any error occurs, it logs the issue and returns null.
	public Event requestEventInfo(Long eventId) throws ServiceException_Exception {
		FetchEventInfoRequest requestParams = factory.createFetchEventInfoRequest();
		requestParams.setEventId(eventId);
		
		FetchEventInfoResponse response = this.port.fetchEventInfo(requestParams);
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

			EventCatalogueResponse response = this.port.eventCatalogue(requestParams);

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

	public Long createEvent(String name, String description, long organizerId, String location
			, LocalDateTime startDate, LocalDateTime endDate, int nrTickets) throws ServiceException_Exception {
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
		
		CreateEventResponse response = this.port.createEvent(requestParams);
		return response.getEventId();
	}
	
	public List<Event> requestOrganizerEventsPage(long organizerId, int page, String sortBy) throws ServiceException_Exception{
		OrganizerCatalogueRequest requestParams = factory.createOrganizerCatalogueRequest();
		requestParams.setOrganizerId(organizerId);
		requestParams.setPage(page);
		requestParams.setSortBy(sortBy);
		
		OrganizerCatalogueResponse response = this.port.organizerCatalogue(requestParams);
		List<Event> events = new ArrayList<>();
		for(EventData xmlEvent : response.getEventList().getEventData()) {
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