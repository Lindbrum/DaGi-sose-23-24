package it.univaq.sose.dagi.soap_proxy_prosumer_rest;

import java.util.List;

import org.springframework.stereotype.Service;

import it.univaq.sose.dagi.soap_proxy_prosumer_rest.client.EventSOAPClient;
import it.univaq.sose.dagi.soap_proxy_prosumer_rest.client.FeedbackSOAPClient;
import it.univaq.sose.dagi.soap_proxy_prosumer_rest.client.TicketSOAPClient;
import it.univaq.sose.dagi.soap_proxy_prosumer_rest.model.CustomerHistory;
import it.univaq.sose.dagi.soap_proxy_prosumer_rest.model.Event;
import it.univaq.sose.dagi.soap_proxy_prosumer_rest.model.Feedback;
import it.univaq.sose.dagi.soap_proxy_prosumer_rest.model.SoldTicket;
import it.univaq.sose.dagi.soap_proxy_prosumer_rest.model.TicketInfo;
import it.univaq.sose.dagi.wsdltypes.ServiceException_Exception;

@Service
public class SoapProxyProsumerApiImpl implements SoapProxyProsumerApi {
	
	
	private EventSOAPClient eventClient;
	
	private FeedbackSOAPClient feedbackClient;
	
	private TicketSOAPClient ticketClient;

	public SoapProxyProsumerApiImpl(EventSOAPClient eventClient, FeedbackSOAPClient feedbackClient, TicketSOAPClient ticketClient) {
		this.eventClient = eventClient;
		this.feedbackClient = feedbackClient;
		this.ticketClient = ticketClient;
	}
	
	@Override
	public Event fetchEventInfo(Long eventId) {
		try {
			return eventClient.requestEventInfo(eventId);
		} catch (ServiceException_Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Event> eventCatalogue(int page, String sortBy) {
		return eventClient.requestEventCataloguePage(page, sortBy);
	}

	@Override
	public Long createEvent(Event newEvent) {
		try {
			return eventClient.createEvent(newEvent.getName(), newEvent.getDescription(), newEvent.getOrganizerId()
					, newEvent.getLocation(), newEvent.getStartDate(), newEvent.getEndDate(), newEvent.getNrTickets());
		} catch (ServiceException_Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Event> requestOrganizerEventsPage(Long organizerId, int page, String sortBy)
			throws ServiceException_Exception {
		return eventClient.requestOrganizerEventsPage(organizerId, page, sortBy);
	}

	@Override
	public String submitFeedback(Feedback newFeedback) throws ServiceException_Exception {
		return feedbackClient.submitFeedback(newFeedback.getUserId(), newFeedback.getEventId(), newFeedback.getRating(), newFeedback.getBody());
	}

	@Override
	public List<TicketInfo> fetchEventAvailableTickets(Long eventId) throws ServiceException_Exception {
		return ticketClient.fetchEventAvailableTickets(eventId);
	}

	@Override
	public Long purchaseTicket(SoldTicket newSoldTicket) throws ServiceException_Exception {
		return ticketClient.purchaseTicket(newSoldTicket.getEventId(), newSoldTicket.getUserId(), Utility.toXMLCalendar(newSoldTicket.getReferenceDate()));
	}

	@Override
	public CustomerHistory fetchCustomerTicketHistory(Long userId)
			throws ServiceException_Exception {
		return ticketClient.fetchCustomerTicketHistory(userId);
	}

	@Override
	public Long createTicketInfo(TicketInfo newTicketInfo) throws ServiceException_Exception {
		
		return ticketClient.createTicketInfo(newTicketInfo.getEventId(), newTicketInfo.getReferenceDate(), newTicketInfo.getAvailableTickets());
	}

}
