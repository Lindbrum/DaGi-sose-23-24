package it.univaq.sose.dagi.organizer_client.client;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxrs.client.ClientConfiguration;
import org.apache.cxf.jaxrs.client.JAXRSClientFactory;
import org.apache.cxf.jaxrs.client.WebClient;

import io.swagger.api.SoapProxyApi;
import io.swagger.model.soap_proxy.CustomerHistory;
import io.swagger.model.soap_proxy.Event;
import io.swagger.model.soap_proxy.Feedback;
import io.swagger.model.soap_proxy.SoldTicket;
import io.swagger.model.soap_proxy.TicketInfo;
import it.univaq.sose.dagi.organizer_client.jackson.DateCompatibleJacksonJsonProvider;

public class SOAPProxyRESTClient {
	// Singleton
	private static SOAPProxyRESTClient instance = null;

	private SoapProxyApi api;

	private SOAPProxyRESTClient() {
			setup();
		}

	public static SOAPProxyRESTClient getInstance() {
		if (instance == null) {
			instance = new SOAPProxyRESTClient();
		}
		return instance;
	}

	private void setup() {
		DateCompatibleJacksonJsonProvider provider = new DateCompatibleJacksonJsonProvider();
		List providers = new ArrayList();
		providers.add(provider);

		api = JAXRSClientFactory.create("http://localhost:8080/api/proxy", SoapProxyApi.class, providers);
		org.apache.cxf.jaxrs.client.Client client = WebClient.client(api);

		ClientConfiguration config = WebClient.getConfig(client);
		config.getOutInterceptors().add(new LoggingOutInterceptor());
		config.getInInterceptors().add(new LoggingInInterceptor());
	}
	
	public Event requestEventInfo(Long eventId) {

		Event event = api.fetchEventInfo1(eventId);
		return event;
	}
	
	public List<Event> requestEventCataloguePage(int page, String sortBy) {
		return api.eventCatalogue1(page, sortBy);
	}
	
	public String submitFeedback(long userId, long eventId, int rating, String body) {
		Feedback feedback = new Feedback();
		feedback.setUserId(userId);
		feedback.setEventId(eventId);
		feedback.setRating(rating);
		feedback.setBody(body);
		return api.submitFeedback1(feedback);
	}
	
	public List<TicketInfo> fetchEventAvailableTickets(long eventId) {
		List<TicketInfo> tickets = api.fetchEventAvailableTickets1(eventId);
		return tickets;
	}
	
	public Long purchaseTicket(long eventId, long userId, LocalDateTime date) {
		SoldTicket data = new SoldTicket();
		data.setEventId(eventId);
		data.setUserId(userId);
		data.setReferenceDate(date);

		Long id = api.purchaseTicket1(data);
		return id;
	}
	
	public CustomerHistory fetchCustomerTicketHistory(long userId) {
		return api.fetchCustomerTicketHistory1(userId);
	}
	
	public Long createEvent(String name, String description, long organizerId, String location
			, LocalDateTime startDate, LocalDateTime endDate, int nrTickets) {
		Event event = new Event();
		event.setDescription(description);
		event.setStartDate(startDate);
		event.setEndDate(endDate);
		event.setLocation(location);
		event.setName(name);
		event.setNrTickets(nrTickets);
		event.setOrganizerId(organizerId);
		
		return api.createEvent1(event);
	}
	
	public List<Event> requestOrganizerEventsPage(long organizerId, int page, String sortBy) {
		return api.requestOrganizerEventsPage1(organizerId, page, sortBy);
	}
	
	public Long createTicketInfo(long eventId, LocalDateTime referenceDate, int availableTickets) {
		TicketInfo ticket = new TicketInfo();
		ticket.setAvailableTickets(availableTickets);
		ticket.setEventId(eventId);
		ticket.setReferenceDate(referenceDate);
		
		return api.createTicketInfo1(ticket);
	}
}
