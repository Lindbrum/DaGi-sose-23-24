package it.univaq.sose.dagi.organizer_client.client;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;

import it.univaq.sose.dagi.organizer_client.Utility;
import it.univaq.sose.dagi.wsdltypes.CreateEventRequest;
import it.univaq.sose.dagi.wsdltypes.CreateEventResponse;
import it.univaq.sose.dagi.wsdltypes.EventData;
import it.univaq.sose.dagi.wsdltypes.EventManagementImplService;
import it.univaq.sose.dagi.wsdltypes.EventManagementPort;
import it.univaq.sose.dagi.wsdltypes.ObjectFactory;
import it.univaq.sose.dagi.wsdltypes.OrganizerCatalogueRequest;
import it.univaq.sose.dagi.wsdltypes.OrganizerCatalogueResponse;
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
			
			CreateEventResponse response = port.createEvent(requestParams);
			return response.getEventId();
		}
		
		public List<EventData> requestOrganizerEventsPage(long organizerId, int page, String sortBy) throws ServiceException_Exception{
			OrganizerCatalogueRequest requestParams = factory.createOrganizerCatalogueRequest();
			requestParams.setOrganizerId(organizerId);
			requestParams.setPage(page);
			requestParams.setSortBy(sortBy);
			
			OrganizerCatalogueResponse response = port.organizerCatalogue(requestParams);
			
			return response.getEventList().getEventData();
		}
}
