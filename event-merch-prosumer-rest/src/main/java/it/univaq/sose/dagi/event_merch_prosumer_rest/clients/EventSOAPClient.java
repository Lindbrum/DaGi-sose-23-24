package it.univaq.sose.dagi.event_merch_prosumer_rest.clients;

import java.net.MalformedURLException;
import java.net.URL;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import it.univaq.sose.dagi.event_merch_prosumer_rest.Utility;
import it.univaq.sose.dagi.event_merch_prosumer_rest.model.Event;
import it.univaq.sose.dagi.wsdltypes.EventData;
import it.univaq.sose.dagi.wsdltypes.EventManagementImplService;
import it.univaq.sose.dagi.wsdltypes.EventManagementPort;
import it.univaq.sose.dagi.wsdltypes.FetchEventInfoRequest;
import it.univaq.sose.dagi.wsdltypes.FetchEventInfoResponse;
import it.univaq.sose.dagi.wsdltypes.ObjectFactory;
import it.univaq.sose.dagi.wsdltypes.ServiceException_Exception;

@Component
public class EventSOAPClient {
	
	private ObjectFactory factory;
	@Value("${client.event.wsdl}")
	private String wsdlUrl;
	
	public EventSOAPClient() {
		//super();
		this.factory = new ObjectFactory();
	}

	public Event requestEventInfo(Long eventId) throws ServiceException_Exception {
		try {
			URL url = new URL(wsdlUrl);
			EventManagementImplService service = new EventManagementImplService(url);
			EventManagementPort port = service.getEventManagementImplPort();
			
			FetchEventInfoRequest requestParams = factory.createFetchEventInfoRequest();
			requestParams.setEventId(eventId);
			
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
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return null;
	}

}