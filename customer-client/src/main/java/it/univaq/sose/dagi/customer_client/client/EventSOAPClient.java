package it.univaq.sose.dagi.customer_client.client;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import io.swagger.model.event_merch_prosumer.Event;
import it.univaq.sose.dagi.customer_client.Utility;
import it.univaq.sose.dagi.wsdltypes.EventCatalogueRequest;
import it.univaq.sose.dagi.wsdltypes.EventCatalogueResponse;
import it.univaq.sose.dagi.wsdltypes.EventData;
import it.univaq.sose.dagi.wsdltypes.EventManagementImplService;
import it.univaq.sose.dagi.wsdltypes.EventManagementPort;
import it.univaq.sose.dagi.wsdltypes.FetchEventInfoRequest;
import it.univaq.sose.dagi.wsdltypes.FetchEventInfoResponse;
import it.univaq.sose.dagi.wsdltypes.ObjectFactory;
import it.univaq.sose.dagi.wsdltypes.ServiceException_Exception;

public class EventSOAPClient {

	// Singleton
	private static EventSOAPClient instance = null;

	private ObjectFactory factory;
	private String wsdlUrl = "http://localhost:8080/api/event/?wsdl";

	private EventManagementPort port;

	private EventSOAPClient() {
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

	public static EventSOAPClient getInstance() {
		if (instance == null) {
			instance = new EventSOAPClient();
		}
		return instance;
	}

	public Event requestEventInfo(Long eventId) throws ServiceException_Exception {

		FetchEventInfoRequest requestParams = factory.createFetchEventInfoRequest();
		requestParams.setEventId(eventId);

		FetchEventInfoResponse response = port.fetchEventInfo(requestParams);
		Event eventInfo = new Event();
		EventData eventData = response.getEventData();
		eventInfo.setId(eventId);
		eventInfo.setName(eventData.getName());
		eventInfo.setLocation(eventData.getLocation());
		eventInfo.setDescription(eventData.getDescription());
		eventInfo.setStartDate(Utility.toLocalDateTime(eventData.getStartDate()));
		eventInfo.setEndDate(Utility.toLocalDateTime(eventData.getEndDate()));
		eventInfo.setNrTickets(eventData.getNrTickets());

		System.out.println("\nThe response is " + response);
		return eventInfo;
	}

	public List<Event> requestEventCataloguePage(int page, String sortBy) {
		try {
			URL url = new URL(wsdlUrl);
			EventManagementImplService service = new EventManagementImplService(url);
			EventManagementPort port = service.getEventManagementImplPort();

			EventCatalogueRequest requestParams = factory.createEventCatalogueRequest();
			requestParams.setPage(page);
			requestParams.setSortBy(sortBy);

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
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ServiceException_Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
