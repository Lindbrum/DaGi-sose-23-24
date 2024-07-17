package it.univaq.sose.dagi.event_merch_prosumer_rest.clients;

import org.springframework.stereotype.Component;

import it.univaq.sose.dagi.event_merch_prosumer_rest.Utility;
import it.univaq.sose.dagi.event_merch_prosumer_rest.model.Event;
import it.univaq.sose.dagi.wsdltypes.*;

@Component
public class EventSOAPClient {

	private ObjectFactory factory;
	
	public EventSOAPClient() {
		//super();
		this.factory = new ObjectFactory();
	}

	public Event requestEventInfo(Long eventId) throws ServiceException_Exception {
		EventManagementImplService service = new EventManagementImplService();
		EventManagementPort port = service.getEventManagementImplPort();
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

}