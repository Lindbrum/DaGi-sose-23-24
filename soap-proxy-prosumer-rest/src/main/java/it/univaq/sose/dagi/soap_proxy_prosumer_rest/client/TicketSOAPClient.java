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

import javax.xml.datatype.XMLGregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;

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
import jakarta.ws.rs.ServiceUnavailableException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TicketSOAPClient {

	private ObjectFactory factory;
	private URL lastUrl;
	private EurekaClient eurekaClient;
	private EventManagementImplService service;
	private final List<InstanceInfo> lastInstancesCache = Collections.synchronizedList(new ArrayList<>());
	private static final Logger log = LoggerFactory.getLogger(EventSOAPClient.class);

	private String wsdlUri = "event-management-soap/event-management-soap/?wsdl";

	// It initializes the EventSOAPClient with an ObjectFactory instance.
	// It sets up the necessary configurations to interact with the SOAP service.
	public TicketSOAPClient(EurekaClient eurekaClient) {
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
		EventManagementPort port = getService(); //load balancing
		requestParams.setEventId(eventId);
		FetchEventSoldTicketsResponse response = port.fetchEventSoldTickets(requestParams);
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
		EventManagementPort port = getService(); //load balancing
		requestParams.setEventId(eventId);

		PurchaseMenuResponse response = port.purchaseMenu(requestParams);
		// Access the list
		List<TicketInfo> tickets = new ArrayList<>();
		for (TicketAvailability xmlTicket : response.getAvailabilitiesList().getTicketAvailability()) {
			TicketInfo ticket = new TicketInfo();
			ticket.setId(xmlTicket.getTicketInfoId());
			ticket.setEventId(eventId);
			ticket.setReferenceDate(Utility.toLocalDateTime(xmlTicket.getReferenceDate()));
			ticket.setAvailableTickets(xmlTicket.getRemainingTickets());

			tickets.add(ticket);
		}
		return tickets;
	}

	public Long purchaseTicket(long eventId, long userId, XMLGregorianCalendar date) throws ServiceException_Exception {
		CreateSoldTicketRequest requestParams = factory.createCreateSoldTicketRequest();
		EventManagementPort port = getService(); //load balancing
		SoldTicketData data = factory.createSoldTicketData();
		data.setEventId(eventId);
		data.setUserId(userId);
		data.setReferenceDate(date);
		requestParams.setSoldTicketData(data);

		CreateSoldTicketResponse response = port.createSoldTicket(requestParams);

		return response.getSoldTicketId();
	}

	public CustomerHistory fetchCustomerTicketHistory(long userId) throws ServiceException_Exception {
		FetchCustomerBoughtTicketsRequest requestParams = factory.createFetchCustomerBoughtTicketsRequest();
		EventManagementPort port = getService(); //load balancing
		requestParams.setUserId(userId);

		FetchCustomerBoughtTicketsResponse response = port.fetchCustomerBoughtTickets(requestParams);

		// tickets
		List<SoldTicket> soldTicketsInfo = new ArrayList<>();
		for (SoldTicketData xmlTicket : response.getSoldTicketsList().getSoldTicketData()) {
			SoldTicket ticket = new SoldTicket();
			ticket.setEventId(xmlTicket.getEventId());
			ticket.setId(xmlTicket.getSoldTicketId());
			ticket.setReferenceDate(Utility.toLocalDateTime(xmlTicket.getReferenceDate()));
			ticket.setUserId(xmlTicket.getUserId());
			soldTicketsInfo.add(ticket);
		}

		// events
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

		return new CustomerHistory(soldTicketsInfo, ticketEvents);
	}

	public Long createTicketInfo(long eventId, LocalDateTime referenceDate, int availableTickets)
			throws ServiceException_Exception {
		CreateTicketInfoRequest requestParams = factory.createCreateTicketInfoRequest();
		EventManagementPort port = getService(); //load balancing
		requestParams.setEventId(eventId);
		requestParams.setReferenceDate(Utility.toXMLCalendar(referenceDate));
		requestParams.setAvailableTickets(availableTickets);

		CreateTicketInfoResponse response = port.createTicketInfo(requestParams);
		return response.getTicketInfoId();
	}
}