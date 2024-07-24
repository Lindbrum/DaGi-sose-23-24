package it.univaq.sose.dagi.soap_proxy_prosumer_rest.client;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

import org.springframework.stereotype.Component;

import it.univaq.sose.dagi.soap_proxy_prosumer_rest.Utility;
import it.univaq.sose.dagi.soap_proxy_prosumer_rest.model.CustomerHistory;
import it.univaq.sose.dagi.soap_proxy_prosumer_rest.model.Event;
import it.univaq.sose.dagi.soap_proxy_prosumer_rest.model.SoldTicket;
import it.univaq.sose.dagi.soap_proxy_prosumer_rest.model.TicketInfo;
import it.univaq.sose.dagi.wsdltypes.CreateSoldTicketRequest;
import it.univaq.sose.dagi.wsdltypes.CreateSoldTicketResponse;
import it.univaq.sose.dagi.wsdltypes.CreateTicketInfoRequest;
import it.univaq.sose.dagi.wsdltypes.CreateTicketInfoResponse;
import it.univaq.sose.dagi.wsdltypes.EventData;
import it.univaq.sose.dagi.wsdltypes.EventManagementImplService;
import it.univaq.sose.dagi.wsdltypes.EventManagementPort;
import it.univaq.sose.dagi.wsdltypes.FetchCustomerBoughtTicketsRequest;
import it.univaq.sose.dagi.wsdltypes.FetchCustomerBoughtTicketsResponse;
import it.univaq.sose.dagi.wsdltypes.FetchEventSoldTicketsRequest;
import it.univaq.sose.dagi.wsdltypes.FetchEventSoldTicketsResponse;
import it.univaq.sose.dagi.wsdltypes.FetchEventSoldTicketsResponse.SoldTicketsList;
import it.univaq.sose.dagi.wsdltypes.ObjectFactory;
import it.univaq.sose.dagi.wsdltypes.PurchaseMenuRequest;
import it.univaq.sose.dagi.wsdltypes.PurchaseMenuResponse;
import it.univaq.sose.dagi.wsdltypes.ServiceException_Exception;
import it.univaq.sose.dagi.wsdltypes.SoldTicketData;
import it.univaq.sose.dagi.wsdltypes.TicketAvailability;

@Component
public class TicketSOAPClient {

	private ObjectFactory factory;

	private EventManagementPort port;

	//@Value("${client.soap.wsdl}")
		private String wsdlUrl = "http://localhost:8080/api/soap/?wsdl";

	public TicketSOAPClient() {
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

	// This method in the SoldTicketsSOAPClient class retrieves sold ticket details
	// for a specified event.
	// It takes an eventId as input, constructs a SOAP request using this ID, and
	// communicates with a web service to
	// fetch the ticket data. The response is parsed to create a list of SoldTicket
	// objects, which includes details such
	// as ticket ID, event ID, reference date, and user ID. This list is then
	// returned. If an error occurs in creating the URL
	// for the web service, the method catches the exception and prints the stack
	// trace, returning null if the process fails.
	public List<SoldTicket> fetchEventSoldTicketsInfo(Long eventId) throws ServiceException_Exception {
		FetchEventSoldTicketsRequest requestParams = factory.createFetchEventSoldTicketsRequest();
		requestParams.setEventId(eventId);
		FetchEventSoldTicketsResponse response = this.port.fetchEventSoldTickets(requestParams);
		SoldTicketsList xmlList = response.getSoldTicketsList();
		List<SoldTicket> soldTicketsInfo = new ArrayList<>();
		for (SoldTicketData xmlTicket : xmlList.getSoldTicketData()) {
			SoldTicket ticket = new SoldTicket();
			ticket.setEventId(eventId);
			ticket.setId(xmlTicket.getSoldTicketId());
			ticket.setReferenceDate(Utility.toLocalDateTime(xmlTicket.getReferenceDate()));
			ticket.setUserId(xmlTicket.getUserId());
			soldTicketsInfo.add(ticket);
		}

		System.out.println("\nThe response is " + response);
		return soldTicketsInfo;
	}

	public List<TicketInfo> fetchEventAvailableTickets(long eventId) throws ServiceException_Exception {
		PurchaseMenuRequest requestParams = factory.createPurchaseMenuRequest();
		requestParams.setEventId(eventId);

		PurchaseMenuResponse response = this.port.purchaseMenu(requestParams);
		// Access the list
		List<TicketInfo> tickets = new ArrayList<>();
		for(TicketAvailability xmlTicket : response.getAvailabilitiesList().getTicketAvailability()) {
			TicketInfo ticket = new TicketInfo();
			ticket.setId(xmlTicket.getTicketInfoId());
			ticket.setEventId(eventId);
			ticket.setReferenceDate(Utility.toLocalDateTime(xmlTicket.getReferenceDate()));
			ticket.setAvailableTickets(xmlTicket.getRemainingTickets());
			
			tickets.add(ticket);
		}
		return tickets;
	}

	public Long purchaseTicket(long eventId, long userId, XMLGregorianCalendar date)
			throws ServiceException_Exception {
		CreateSoldTicketRequest requestParams = factory.createCreateSoldTicketRequest();
		SoldTicketData data = factory.createSoldTicketData();
		data.setEventId(eventId);
		data.setUserId(userId);
		data.setReferenceDate(date);
		requestParams.setSoldTicketData(data);

		CreateSoldTicketResponse response = this.port.createSoldTicket(requestParams);

		return response.getSoldTicketId();
	}

	public CustomerHistory fetchCustomerTicketHistory(long userId)
			throws ServiceException_Exception {
		FetchCustomerBoughtTicketsRequest requestParams = factory.createFetchCustomerBoughtTicketsRequest();
		requestParams.setUserId(userId);

		FetchCustomerBoughtTicketsResponse response = this.port.fetchCustomerBoughtTickets(requestParams);

		//tickets
		List<SoldTicket> soldTicketsInfo = new ArrayList<>();
		for (SoldTicketData xmlTicket : response.getSoldTicketsList().getSoldTicketData()) {
			SoldTicket ticket = new SoldTicket();
			ticket.setEventId(xmlTicket.getEventId());
			ticket.setId(xmlTicket.getSoldTicketId());
			ticket.setReferenceDate(Utility.toLocalDateTime(xmlTicket.getReferenceDate()));
			ticket.setUserId(xmlTicket.getUserId());
			soldTicketsInfo.add(ticket);
		}
		
		//events
		List<Event> ticketEvents = new ArrayList<>();
		for (EventData xmlEvent : response.getEventsList().getEventData()) {
			Event eventInfo = new Event();
			eventInfo.setId(xmlEvent.getEventId());
			eventInfo.setName(xmlEvent.getName());
			eventInfo.setLocation(xmlEvent.getLocation());
			eventInfo.setDescription(xmlEvent.getDescription());
			eventInfo.setStartDate(Utility.toLocalDateTime(xmlEvent.getStartDate()));
			eventInfo.setEndDate(Utility.toLocalDateTime(xmlEvent.getEndDate()));
			eventInfo.setNrTickets(xmlEvent.getNrTickets());
			ticketEvents.add(eventInfo);
		}
		
		
		return new CustomerHistory(soldTicketsInfo,ticketEvents);
	}
	
	public Long createTicketInfo(long eventId, LocalDateTime referenceDate, int availableTickets) throws ServiceException_Exception {
		CreateTicketInfoRequest requestParams = factory.createCreateTicketInfoRequest();
		requestParams.setEventId(eventId);
		requestParams.setReferenceDate(Utility.toXMLCalendar(referenceDate));
		requestParams.setAvailableTickets(availableTickets);
		
		CreateTicketInfoResponse response = this.port.createTicketInfo(requestParams);
		return response.getTicketInfoId();
	}
}