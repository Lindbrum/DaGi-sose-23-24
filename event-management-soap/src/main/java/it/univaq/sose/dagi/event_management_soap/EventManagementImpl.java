package it.univaq.sose.dagi.event_management_soap;

import java.util.ArrayList;
import java.util.List;

import it.univaq.sose.dagi.event_management_soap.model.Event;
import it.univaq.sose.dagi.event_management_soap.model.Feedback;
import it.univaq.sose.dagi.event_management_soap.model.SoldTicket;
import it.univaq.sose.dagi.event_management_soap.model.TicketInfo;
import it.univaq.sose.dagi.wsdltypes.CreateEventRequest;
import it.univaq.sose.dagi.wsdltypes.CreateEventResponse;
import it.univaq.sose.dagi.wsdltypes.CreateFeedbackRequest;
import it.univaq.sose.dagi.wsdltypes.CreateFeedbackResponse;
import it.univaq.sose.dagi.wsdltypes.EventCatalogueRequest;
import it.univaq.sose.dagi.wsdltypes.EventCatalogueResponse;
import it.univaq.sose.dagi.wsdltypes.EventCatalogueResponse.EventList;
import it.univaq.sose.dagi.wsdltypes.EventData;
import it.univaq.sose.dagi.wsdltypes.EventManagementPort;
import it.univaq.sose.dagi.wsdltypes.FetchEventInfoRequest;
import it.univaq.sose.dagi.wsdltypes.FetchEventInfoResponse;
import it.univaq.sose.dagi.wsdltypes.ObjectFactory;
import it.univaq.sose.dagi.wsdltypes.PurchaseMenuRequest;
import it.univaq.sose.dagi.wsdltypes.PurchaseMenuResponse;
import it.univaq.sose.dagi.wsdltypes.ServiceException_Exception;
import it.univaq.sose.dagi.wsdltypes.TicketAvailability;

public class EventManagementImpl implements EventManagementPort {

	enum SortingMode {
		ID_DESC, ID_ASC, ALPHABETICAL_ASC, ALPHABETICAL_DESC
	}
	
	private static Long eventIdCount = 0L;
	private static Long soldTicketIdCount = 0L;
	private static Long ticketInfoIdCount = 0L;
	private static Long feedbackIdCount = 0L;
	private List<Event> events = new ArrayList<>();
	private List<SoldTicket> soldTickets = new ArrayList<>();
	private List<TicketInfo> ticketInfos = new ArrayList<>();
	private List<Feedback> feedbacks = new ArrayList<>();
	private ObjectFactory factory;
	
	private final int EVENTS_PER_PAGE = 15;
	
	
	public EventManagementImpl() {
		factory = new ObjectFactory();
	}
	
	@Override
	public CreateEventResponse createEvent(CreateEventRequest parameters) throws ServiceException_Exception {
		// TODO Add persistence
		CreateEventResponse response = factory.createCreateEventResponse();
		Long eventId = eventIdCount++;
		events.add(new Event(eventId, parameters.getName(), parameters.getDescription()
				, parameters.getLocation(), Utility.toLocalDateTime(parameters.getStartDate())
				, Utility.toLocalDateTime(parameters.getEndDate()), parameters.getNrTickets()));
		response.setEventId(eventId);
		return response;
	}

	@Override
	public PurchaseMenuResponse purchaseMenu(PurchaseMenuRequest parameters) throws ServiceException_Exception {
		// TODO Add persistence
		List<TicketInfo> result = new ArrayList<>();
		for(TicketInfo t : ticketInfos) {
			if(t.getEventId() == parameters.getEventId()) {
				result.add(t);
			}
		}
		PurchaseMenuResponse response = factory.createPurchaseMenuResponse();
		List<TicketAvailability> availabilities = response.getTicketAvailability();
		for(TicketInfo ticket : result) {
			TicketAvailability xmlTicket = factory.createTicketAvailability();
			xmlTicket.setReferenceDate(Utility.toXMLCalendar(ticket.getReferenceDate()));
			xmlTicket.setRemainingTickets(ticket.getAvailableTickets());
			availabilities.add(xmlTicket);
		}
		return response;
	}

	@Override
	public CreateFeedbackResponse createFeedback(CreateFeedbackRequest parameters) throws ServiceException_Exception {
		// TODO Add persistence
		
		Feedback feedback = new Feedback(feedbackIdCount++, parameters.getUserId(), parameters.getEventId(), parameters.getRating(), parameters.getBody());
		feedbacks.add(feedback);
		CreateFeedbackResponse response = factory.createCreateFeedbackResponse();
		response.setMessage("We received your feedback, thank you!");
		return response;
	}

	@Override
	public FetchEventInfoResponse fetchEventInfo(FetchEventInfoRequest parameters) throws ServiceException_Exception {
		// TODO Add persistence
		Event selectedEvent = null;
		for(Event e : events) {
			if(e.getId() == parameters.getEventId()) {
				selectedEvent = e;
				break;
			}
		}
		if(selectedEvent == null) {
			throw new ServiceException_Exception("Event not found.");
		}
		FetchEventInfoResponse response = factory.createFetchEventInfoResponse();
		EventData data = createEventData(selectedEvent);
		response.setEventData(data);
		return response;
	}


	@Override
	public EventCatalogueResponse eventCatalogue(EventCatalogueRequest parameters) throws ServiceException_Exception {
		// TODO Add persistence
		if(parameters.getSortBy().equals(SortingMode.ID_DESC.name())) {
			events.sort(Event.getIdDescComparator());
		}
		else if (parameters.getSortBy().equals(SortingMode.ID_ASC.name())) {
			events.sort(null);
		}
		else if (parameters.getSortBy().equals(SortingMode.ALPHABETICAL_DESC.name())) {
			events.sort(Event.getNameDescComparator());
		}
		else {
			events.sort(Event.getNameAscComparator());
		}
		
		List<Event> result = new ArrayList<>();
		int firstIndex = (parameters.getPage() - 1) * EVENTS_PER_PAGE;
		int lastIndex = Math.min(EVENTS_PER_PAGE * parameters.getPage(), events.size());
		result = events.subList(firstIndex, lastIndex);
		
		EventCatalogueResponse response = factory.createEventCatalogueResponse();
		EventList eventList = factory.createEventCatalogueResponseEventList();
		List<EventData> data = eventList.getEventData();
		for(Event e : result) {
			data.add(createEventData(e));
		}
		response.setEventList(eventList);
		return response;
	}

	private EventData createEventData(Event selectedEvent) {
		EventData data = factory.createEventData();
		data.setName(selectedEvent.getName());
		data.setLocation(selectedEvent.getLocation());
		data.setDescription(selectedEvent.getDescription());
		data.setStartDate(Utility.toXMLCalendar(selectedEvent.getStartDate()));
		data.setEndDate(Utility.toXMLCalendar(selectedEvent.getEndDate()));
		data.setNrTickets(selectedEvent.getNrTickets());
		return data;
	}
}

