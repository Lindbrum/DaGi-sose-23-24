package it.univaq.sose.dagi.organizer_client.client;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;

import it.univaq.sose.dagi.organizer_client.Utility;
import it.univaq.sose.dagi.wsdltypes.CreateTicketInfoRequest;
import it.univaq.sose.dagi.wsdltypes.CreateTicketInfoResponse;
import it.univaq.sose.dagi.wsdltypes.EventManagementImplService;
import it.univaq.sose.dagi.wsdltypes.EventManagementPort;
import it.univaq.sose.dagi.wsdltypes.ObjectFactory;
import it.univaq.sose.dagi.wsdltypes.ServiceException_Exception;

public class TicketSOAPClient {
	// Singleton
		private static TicketSOAPClient instance = null;

		private ObjectFactory factory;
		private String wsdlUrl = "http://localhost:8080/api/event/?wsdl";

		private EventManagementPort port;

		private TicketSOAPClient() {
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

		public static TicketSOAPClient getInstance() {
			if (instance == null) {
				instance = new TicketSOAPClient();
			}
			return instance;
		}
		
		public Long createTicketInfo(long eventId, LocalDateTime referenceDate, int availableTickets) throws ServiceException_Exception {
			CreateTicketInfoRequest requestParams = factory.createCreateTicketInfoRequest();
			requestParams.setEventId(eventId);
			requestParams.setReferenceDate(Utility.toXMLCalendar(referenceDate));
			requestParams.setAvailableTickets(availableTickets);
			
			CreateTicketInfoResponse response = port.createTicketInfo(requestParams);
			return response.getTicketInfoId();
		}
}
