package it.univaq.sose.dagi.wsdltypes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import it.univaq.sose.dagi.event_management_soap.Utility;
import it.univaq.sose.dagi.event_management_soap.model.Event;
import it.univaq.sose.dagi.event_management_soap.model.Feedback;
import it.univaq.sose.dagi.event_management_soap.model.SoldTicket;
import it.univaq.sose.dagi.event_management_soap.model.TicketInfo;
import it.univaq.sose.dagi.wsdltypes.EventCatalogueResponse.EventList;
import it.univaq.sose.dagi.wsdltypes.FetchEventFeedbackResponse.FeedbackList;
import it.univaq.sose.dagi.wsdltypes.FetchEventSoldTicketsResponse.SoldTicketsList;
import it.univaq.sose.dagi.wsdltypes.PurchaseMenuResponse.AvailabilitiesList;

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
	
	/*************************************
	 * Event endpoints
	 *************************************/
	@Override
	public CreateEventResponse createEvent(CreateEventRequest parameters) throws ServiceException_Exception {
		// TODO Add persistence
		CreateEventResponse response = factory.createCreateEventResponse();
		Long eventId = eventIdCount++;
		EventData newEvent = parameters.getEventData();
		events.add(new Event(eventId, newEvent.getName(), newEvent.getDescription()
				, newEvent.getLocation(), Utility.toLocalDateTime(newEvent.getStartDate())
				, Utility.toLocalDateTime(newEvent.getEndDate()), newEvent.getNrTickets()));
		response.setEventId(eventId);
		return response;
	}
	
	@Override
	public UpdateEventResponse updateEvent(UpdateEventRequest parameters) throws ServiceException_Exception {
		EventData xmlEvent = parameters.getEventData();
		for (int i = 0; i < soldTickets.size(); i++) {
			Event e = events.get(i);
			if (e.getId() == xmlEvent.getEventId()) {
				e.setName(xmlEvent.getName());
				e.setDescription(xmlEvent.getDescription());
				e.setLocation(xmlEvent.getLocation());
				e.setStartDate(Utility.toLocalDateTime(xmlEvent.getStartDate()));
				e.setEndDate(Utility.toLocalDateTime(xmlEvent.getEndDate()));
				e.setNrTickets(xmlEvent.getNrTickets());
				break;
			}
		}
		UpdateEventResponse response = factory.createUpdateEventResponse();
		response.setEventId(xmlEvent.getEventId());
		return response;
	}
	
	@Override
	public DeleteEventResponse deleteEvent(DeleteEventRequest parameters) throws ServiceException_Exception {
		for (Iterator<Event> iterator = events.listIterator(); iterator.hasNext();) {
			Event e = iterator.next();
			if (parameters.getEventId() == e.getId()) {
				iterator.remove();
				break;
			}
		}
		DeleteEventResponse response = factory.createDeleteEventResponse();
		response.setMessage("Event info deleted successfully!");
		return response;
	}
	
	@Override
	public FetchEventInfoResponse fetchEventInfo(FetchEventInfoRequest parameters) throws ServiceException_Exception {
		// TODO Add persistence
		Event selectedEvent = null;
		for (Event e : events) {
			if (e.getId() == parameters.getEventId()) {
				selectedEvent = e;
				break;
			}
		}
		if (selectedEvent == null) {
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
		if (parameters.getSortBy().equals(SortingMode.ID_DESC.name())) {
			events.sort(Event.getIdDescComparator());
		} else if (parameters.getSortBy().equals(SortingMode.ID_ASC.name())) {
			events.sort(null);
		} else if (parameters.getSortBy().equals(SortingMode.ALPHABETICAL_DESC.name())) {
			events.sort(Event.getNameDescComparator());
		} else {
			events.sort(Event.getNameAscComparator());
		}

		List<Event> result = new ArrayList<>();
		int firstIndex = (parameters.getPage() - 1) * EVENTS_PER_PAGE;
		int lastIndex = Math.min(EVENTS_PER_PAGE * parameters.getPage(), events.size());
		result = events.subList(firstIndex, lastIndex);

		EventCatalogueResponse response = factory.createEventCatalogueResponse();
		EventList eventList = factory.createEventCatalogueResponseEventList();
		List<EventData> data = eventList.getEventData();
		for (Event e : result) {
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

	/*************************************
	 * Ticket info endpoints
	 *************************************/
	
	@Override
	public CreateTicketInfoResponse createTicketInfo(CreateTicketInfoRequest parameters)
			throws ServiceException_Exception {
		TicketInfo newTicketInfo = new TicketInfo();
		Long id = ticketInfoIdCount++;
		newTicketInfo.setId(id);
		newTicketInfo.setEventId(parameters.getEventId());
		newTicketInfo.setReferenceDate(Utility.toLocalDateTime(parameters.getReferenceDate()));
		newTicketInfo.setAvailableTickets(parameters.getAvailableTickets());
		ticketInfos.add(newTicketInfo);
		CreateTicketInfoResponse response = factory.createCreateTicketInfoResponse();
		response.setTicketInfoId(id);
		return response;
	}

	@Override
	public UpdateTicketInfoResponse updateTicketInfo(UpdateTicketInfoRequest parameters)
			throws ServiceException_Exception {

		for (int i = 0; i < ticketInfos.size(); i++) {
			TicketInfo ti = ticketInfos.get(i);
			if (ti.getId() == parameters.getTicketInfoId()) {
				ti.setEventId(parameters.getEventId());
				ti.setReferenceDate(Utility.toLocalDateTime(parameters.getReferenceDate()));
				ti.setAvailableTickets(parameters.getAvailableTickets());
				break;
			}
		}
		UpdateTicketInfoResponse response = factory.createUpdateTicketInfoResponse();
		response.setTicketInfoId(parameters.getTicketInfoId());
		return response;
	}
	
	@Override
	public DeleteTicketInfoResponse deleteTicketInfo(DeleteTicketInfoRequest parameters)
			throws ServiceException_Exception {
		for (Iterator<TicketInfo> iterator = ticketInfos.listIterator(); iterator.hasNext();) {
			TicketInfo ti = iterator.next();
			if (parameters.getTicketInfoId() == ti.getId()) {
				iterator.remove();
				break;
			}
		}
		DeleteTicketInfoResponse response = factory.createDeleteTicketInfoResponse();
		response.setMessage("Ticket info deleted successfully!");
		return response;
	}
	
	@Override
	public PurchaseMenuResponse purchaseMenu(PurchaseMenuRequest parameters) throws ServiceException_Exception {
		// TODO Add persistence
		List<TicketInfo> result = new ArrayList<>();
		for (TicketInfo t : ticketInfos) {
			if (t.getEventId() == parameters.getEventId()) {
				result.add(t);
			}
		}
		PurchaseMenuResponse response = factory.createPurchaseMenuResponse();
		AvailabilitiesList availabilitiesWrapper = factory.createPurchaseMenuResponseAvailabilitiesList();
		List<TicketAvailability> availabilities = availabilitiesWrapper.getTicketAvailability();
		for (TicketInfo ticket : result) {
			TicketAvailability xmlTicket = factory.createTicketAvailability();
			xmlTicket.setReferenceDate(Utility.toXMLCalendar(ticket.getReferenceDate()));
			xmlTicket.setRemainingTickets(ticket.getAvailableTickets());
			availabilities.add(xmlTicket);
		}
		response.setAvailabilitiesList(availabilitiesWrapper);
		return response;
	}
	
	/*************************************
	 * Sold ticket endpoints
	 *************************************/
	
	@Override
	public CreateSoldTicketResponse createSoldTicket(CreateSoldTicketRequest parameters)
			throws ServiceException_Exception {
		SoldTicketData xmlTicket = parameters.getSoldTicketData();
		SoldTicket newSoldTicket = new SoldTicket();
		Long id = soldTicketIdCount++;
		newSoldTicket.setId(id);
		newSoldTicket.setUserId(xmlTicket.getUserId());
		newSoldTicket.setEventId(xmlTicket.getEventId());
		newSoldTicket.setReferenceDate(Utility.toLocalDateTime(xmlTicket.getReferenceDate()));
		soldTickets.add(newSoldTicket);
		CreateSoldTicketResponse response = factory.createCreateSoldTicketResponse();
		response.setSoldTicketId(id);
		return response;
	}

	@Override
	public UpdateSoldTicketResponse updateSoldTicket(UpdateSoldTicketRequest parameters)
			throws ServiceException_Exception {

		SoldTicketData xmlTicket = parameters.getSoldTicketData();
		for (int i = 0; i < soldTickets.size(); i++) {
			SoldTicket st = soldTickets.get(i);
			if (st.getId() == xmlTicket.getSoldTicketId()) {
				st.setUserId(xmlTicket.getUserId());
				st.setEventId(xmlTicket.getEventId());
				st.setReferenceDate(Utility.toLocalDateTime(xmlTicket.getReferenceDate()));
				break;
			}
		}
		UpdateSoldTicketResponse response = factory.createUpdateSoldTicketResponse();
		response.setSoldTicketId(xmlTicket.getSoldTicketId());
		return response;
	}

	@Override
	public DeleteSoldTicketResponse deleteSoldTicket(DeleteSoldTicketRequest parameters)
			throws ServiceException_Exception {
		
		for (Iterator<SoldTicket> iterator = soldTickets.listIterator(); iterator.hasNext();) {
			SoldTicket st = iterator.next();
			if (parameters.getSoldTicketId() == st.getId()) {
				iterator.remove();
				break;
			}
		}
		DeleteSoldTicketResponse response = factory.createDeleteSoldTicketResponse();
		response.setMessage("Ticket sale info deleted successfully!");
		return response;
	}
	
	@Override
	public FetchEventSoldTicketsResponse fetchEventSoldTickets(FetchEventSoldTicketsRequest parameters)
			throws ServiceException_Exception {
		FetchEventSoldTicketsResponse response = factory.createFetchEventSoldTicketsResponse();
		SoldTicketsList ticketsList = response.getSoldTicketsList();
		ticketsList = factory.createFetchEventSoldTicketsResponseSoldTicketsList();
		List<SoldTicketData> eventSoldTickets = ticketsList.getSoldTicketData();;
		for(SoldTicket st : soldTickets) {
			if(st.getEventId() == parameters.getEventId()) {
				SoldTicketData ticketData = factory.createSoldTicketData();
				ticketData.setSoldTicketId(st.getId());
				ticketData.setUserId(st.getUserId());
				ticketData.setEventId(st.getEventId());
				ticketData.setReferenceDate(Utility.toXMLCalendar(st.getReferenceDate()));
				eventSoldTickets.add(ticketData);
			}
		}
		response.setSoldTicketsList(ticketsList);
		return response;
	}

	/*************************************
	 * Feedback endpoints
	 *************************************/
	
	@Override
	public CreateFeedbackResponse createFeedback(CreateFeedbackRequest parameters) throws ServiceException_Exception {
		// TODO Add persistence
		FeedbackData newFeedback = parameters.getFeedbackData();
		Feedback feedback = new Feedback(feedbackIdCount++, newFeedback.getUserId(), newFeedback.getEventId(),
				newFeedback.getRating(), newFeedback.getBody());
		feedbacks.add(feedback);
		CreateFeedbackResponse response = factory.createCreateFeedbackResponse();
		response.setMessage("We received your feedback, thank you!");
		return response;
	}

	@Override
	public UpdateFeedbackResponse updateFeedback(UpdateFeedbackRequest parameters) throws ServiceException_Exception {
		FeedbackData xmlFeedback = parameters.getFeedbackData();
		for (int i = 0; i < feedbacks.size(); i++) {
			Feedback f = feedbacks.get(i);
			if (f.getId() == xmlFeedback.getFeedbackId()) {
				f.setUserId(xmlFeedback.getUserId());
				f.setEventId(xmlFeedback.getEventId());
				f.setRating(xmlFeedback.getRating());
				f.setBody(xmlFeedback.getBody());
				break;
			}
		}
		UpdateFeedbackResponse response = factory.createUpdateFeedbackResponse();
		response.setMessage("Feedback updated successfully!");
		return response;
	}

	@Override
	public DeleteFeedbackResponse deleteFeedback(DeleteFeedbackRequest parameters) throws ServiceException_Exception {
		for (Iterator<Feedback> iterator = feedbacks.listIterator(); iterator.hasNext();) {
			Feedback f = iterator.next();
			if (parameters.getFeedbackId() == f.getId()) {
				iterator.remove();
				break;
			}
		}
		DeleteFeedbackResponse response = factory.createDeleteFeedbackResponse();
		response.setMessage("Feedback deleted successfully!");
		return response;
	}
	



	@Override
	public FetchEventFeedbackResponse fetchEventFeedback(FetchEventFeedbackRequest parameters)
			throws ServiceException_Exception {
		FetchEventFeedbackResponse response = factory.createFetchEventFeedbackResponse();
		FeedbackList feedbackList = response.getFeedbackList();
		feedbackList = factory.createFetchEventFeedbackResponseFeedbackList();
		List<FeedbackData> eventFeedback = feedbackList.getFeedbackData();
		for(Feedback f : feedbacks) {
			if(f.getEventId() == parameters.getEventId()) {
				System.out.println("SI, SONO ENTRATO.");
				FeedbackData feedback = factory.createFeedbackData();
				feedback.setFeedbackId(f.getId());
				feedback.setUserId(f.getUserId());
				feedback.setEventId(f.getEventId());
				feedback.setRating(f.getRating());
				feedback.setBody(f.getBody());
				eventFeedback.add(feedback);
			}
		}
		response.setFeedbackList(feedbackList);
		return response;
	}
}
