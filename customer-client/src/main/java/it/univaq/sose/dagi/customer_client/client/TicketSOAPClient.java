package it.univaq.sose.dagi.customer_client.client;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

import it.univaq.sose.dagi.wsdltypes.CreateSoldTicketRequest;
import it.univaq.sose.dagi.wsdltypes.CreateSoldTicketResponse;
import it.univaq.sose.dagi.wsdltypes.EventManagementImplService;
import it.univaq.sose.dagi.wsdltypes.EventManagementPort;
import it.univaq.sose.dagi.wsdltypes.FetchCustomerBoughtTicketsRequest;
import it.univaq.sose.dagi.wsdltypes.FetchCustomerBoughtTicketsResponse;
import it.univaq.sose.dagi.wsdltypes.ObjectFactory;
import it.univaq.sose.dagi.wsdltypes.PurchaseMenuRequest;
import it.univaq.sose.dagi.wsdltypes.PurchaseMenuResponse;
import it.univaq.sose.dagi.wsdltypes.ServiceException_Exception;
import it.univaq.sose.dagi.wsdltypes.SoldTicketData;
import it.univaq.sose.dagi.wsdltypes.TicketAvailability;

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

	public List<TicketAvailability> fetchEventAvailableTickets(long eventId) throws ServiceException_Exception {
		PurchaseMenuRequest requestParams = factory.createPurchaseMenuRequest();
		requestParams.setEventId(eventId);

		PurchaseMenuResponse response = port.purchaseMenu(requestParams);
		// Access the list
		return response.getAvailabilitiesList().getTicketAvailability();
	}

	public CreateSoldTicketResponse purchaseTicket(long eventId, long userId, XMLGregorianCalendar date)
			throws ServiceException_Exception {
		CreateSoldTicketRequest requestParams = factory.createCreateSoldTicketRequest();
		SoldTicketData data = factory.createSoldTicketData();
		data.setEventId(eventId);
		data.setUserId(userId);
		data.setReferenceDate(date);
		requestParams.setSoldTicketData(data);

		CreateSoldTicketResponse response = port.createSoldTicket(requestParams);

		return response;
	}
	
	public FetchCustomerBoughtTicketsResponse fetchCustomerTicketHistory(long userId) throws ServiceException_Exception {
		FetchCustomerBoughtTicketsRequest requestParams = factory.createFetchCustomerBoughtTicketsRequest();
		requestParams.setUserId(userId);
		
		FetchCustomerBoughtTicketsResponse response = port.fetchCustomerBoughtTickets(requestParams);
		
		return response;
	}
}
