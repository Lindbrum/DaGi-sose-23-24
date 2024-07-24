package it.univaq.sose.dagi.wsdltypes;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import it.univaq.sose.dagi.event_management_soap.Utility;
import it.univaq.sose.dagi.event_management_soap.model.Event;
import it.univaq.sose.dagi.event_management_soap.model.Feedback;
import it.univaq.sose.dagi.event_management_soap.model.SoldTicket;
import it.univaq.sose.dagi.event_management_soap.model.TicketInfo;
import it.univaq.sose.dagi.event_management_soap.service.EventService;
import it.univaq.sose.dagi.event_management_soap.service.EventServiceDummyImpl;
import it.univaq.sose.dagi.event_management_soap.service.FeedbackService;
import it.univaq.sose.dagi.event_management_soap.service.FeedbackServiceDummyImpl;
import it.univaq.sose.dagi.event_management_soap.service.SoldTicketService;
import it.univaq.sose.dagi.event_management_soap.service.SoldTicketServiceDummyImpl;
import it.univaq.sose.dagi.event_management_soap.service.TicketInfoService;
import it.univaq.sose.dagi.event_management_soap.service.TicketInfoServiceDummyImpl;
import it.univaq.sose.dagi.wsdltypes.EventCatalogueResponse.EventList;
import it.univaq.sose.dagi.wsdltypes.FetchCustomerBoughtTicketsResponse.EventsList;
import it.univaq.sose.dagi.wsdltypes.FetchEventFeedbackResponse.FeedbackList;
import it.univaq.sose.dagi.wsdltypes.FetchEventSoldTicketsResponse.SoldTicketsList;
import it.univaq.sose.dagi.wsdltypes.PurchaseMenuResponse.AvailabilitiesList;

public class EventManagementImpl implements EventManagementPort {

	// Services (injected by Spring DI)
	private EventService eventService;
	private FeedbackService feedbackService;
	private SoldTicketService soldTicketService;
	private TicketInfoService ticketInfoService;

	private ObjectFactory factory;

	public EventManagementImpl(EventServiceDummyImpl eventService, FeedbackServiceDummyImpl feedbackService,
			SoldTicketServiceDummyImpl soldTicketService, TicketInfoServiceDummyImpl ticketInfoService) {
		factory = new ObjectFactory();
		this.eventService = eventService;
		this.feedbackService = feedbackService;
		this.soldTicketService = soldTicketService;
		this.ticketInfoService = ticketInfoService;
	}

	/*************************************
	 * Event endpoints
	 *************************************/
	@Override
	public CreateEventResponse createEvent(CreateEventRequest parameters) throws ServiceException_Exception {
		// read the data from the unmarshalled object into POJO
		EventData xmlEvent = parameters.getEventData();
		Event newEvent = new Event(xmlEvent.getName(), xmlEvent.getDescription(), xmlEvent.getOrganizerId(),
				xmlEvent.getLocation(), Utility.toLocalDateTime(xmlEvent.getStartDate()),
				Utility.toLocalDateTime(xmlEvent.getEndDate()), xmlEvent.getNrTickets());
		// Add the event to the data source
		Event newEventWithId = eventService.create(newEvent);
		// Return the newly generated event id in the response
		CreateEventResponse response = factory.createCreateEventResponse();
		response.setEventId(newEventWithId.getId());
		return response;
	}

	@Override
	public UpdateEventResponse updateEvent(UpdateEventRequest parameters) throws ServiceException_Exception {
		// read the data from the unmarshalled object into POJO
		EventData xmlEvent = parameters.getEventData();
		Event updatedEvent = new Event(xmlEvent.getEventId(), xmlEvent.getName(), xmlEvent.getDescription(),
				xmlEvent.getOrganizerId(), xmlEvent.getLocation(), Utility.toLocalDateTime(xmlEvent.getStartDate()),
				Utility.toLocalDateTime(xmlEvent.getEndDate()), xmlEvent.getNrTickets());
		// Update the event in the data source
		try {
			eventService.update(updatedEvent);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException_Exception("An error has occured while updating the event.", e);
		}
		// Build the response
		UpdateEventResponse response = factory.createUpdateEventResponse();
		response.setEventId(updatedEvent.getId());
		return response;
	}

	@Override
	public DeleteEventResponse deleteEvent(DeleteEventRequest parameters) throws ServiceException_Exception {
		// Delete the event in the data source
		Event toDelete = new Event();
		toDelete.setId(parameters.getEventId());
		try {
			eventService.delete(toDelete);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException_Exception("An error has occured while deleting the event.", e);
		}
		// Build the response
		DeleteEventResponse response = factory.createDeleteEventResponse();
		response.setMessage("Event info deleted successfully!");
		return response;
	}

	@Override
	public FetchEventInfoResponse fetchEventInfo(FetchEventInfoRequest parameters) throws ServiceException_Exception {
		// Attempt to retrieve the event information
		Event selectedEvent = null;
		try {
			selectedEvent = eventService.findById(parameters.getEventId());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException_Exception("An error has occured while fetching the event information.", e);
		}
		// Build the response
		FetchEventInfoResponse response = factory.createFetchEventInfoResponse();
		EventData data = createEventData(selectedEvent); // Build JAXB object for the event data
		response.setEventData(data);
		return response;
	}

	@Override
	public EventCatalogueResponse eventCatalogue(EventCatalogueRequest parameters) throws ServiceException_Exception {
		System.out.print("=====================eventCatalogue");
		// Get the catalogue page using the selected sorting method
		List<Event> pageEvents = eventService.sortAndFindByPage(parameters.getPage(), parameters.getSortBy());
		// Build the response
		EventCatalogueResponse response = factory.createEventCatalogueResponse();
		EventList eventList = factory.createEventCatalogueResponseEventList();
		List<EventData> data = eventList.getEventData();
		for (Event e : pageEvents) {
			data.add(createEventData(e)); // Build JAXB object for the event data
		}
		response.setEventList(eventList);
		return response;
	}

	@Override
	public OrganizerCatalogueResponse organizerCatalogue(OrganizerCatalogueRequest parameters)
			throws ServiceException_Exception {
		// Get the organizer catalogue page using the selected sorting method
		List<Event> pageEvents = eventService.sortAndFindByPageAndOrganizer(parameters.getOrganizerId(), parameters.getPage(), parameters.getSortBy());
		// Build the response
		OrganizerCatalogueResponse response = factory.createOrganizerCatalogueResponse();
		it.univaq.sose.dagi.wsdltypes.OrganizerCatalogueResponse.EventList eventList = factory.createOrganizerCatalogueResponseEventList();
		List<EventData> data = eventList.getEventData();
		for (Event e : pageEvents) {
			data.add(createEventData(e)); // Build JAXB object for the event data
		}
		response.setEventList(eventList);
		return response;
	}

	/**
	 * Create the JAXB marshallable event data object from an event POJO.
	 * 
	 * @param selectedEvent the event POJO
	 * @return an instance of JAXB marshallable event data
	 */
	private EventData createEventData(Event selectedEvent) {
		EventData data = factory.createEventData();
		data.setEventId(selectedEvent.getId());
		data.setName(selectedEvent.getName());
		data.setDescription(selectedEvent.getDescription());
		data.setOrganizerId(selectedEvent.getOrganizerId());
		data.setLocation(selectedEvent.getLocation());
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
		// read the data from the unmarshalled object into POJO
		TicketInfo ticket = new TicketInfo(parameters.getEventId(),
				Utility.toLocalDateTime(parameters.getReferenceDate()), parameters.getAvailableTickets());
		// Add the event to the data source
		TicketInfo newTicketWithId = ticketInfoService.create(ticket);
		// Return the newly generated event id in the response
		CreateTicketInfoResponse response = factory.createCreateTicketInfoResponse();
		response.setTicketInfoId(newTicketWithId.getId());
		return response;
	}

	@Override
	public UpdateTicketInfoResponse updateTicketInfo(UpdateTicketInfoRequest parameters)
			throws ServiceException_Exception {

		// read the data from the unmarshalled object into POJO
		TicketInfo ticket = new TicketInfo(parameters.getTicketInfoId(), parameters.getEventId(),
				Utility.toLocalDateTime(parameters.getReferenceDate()), parameters.getAvailableTickets());
		// Update the event in the data source
		try {
			ticketInfoService.update(ticket);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException_Exception("An error has occured while updating the ticket info.", e);
		}
		// Build the response
		UpdateTicketInfoResponse response = factory.createUpdateTicketInfoResponse();
		response.setTicketInfoId(parameters.getTicketInfoId());
		return response;
	}

	@Override
	public DeleteTicketInfoResponse deleteTicketInfo(DeleteTicketInfoRequest parameters)
			throws ServiceException_Exception {
		// Delete the event in the data source
		TicketInfo toDelete = new TicketInfo();
		toDelete.setId(parameters.getTicketInfoId());
		try {
			ticketInfoService.delete(toDelete);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException_Exception("An error has occured while deleting the ticket info.", e);
		}
		// Build the response
		DeleteTicketInfoResponse response = factory.createDeleteTicketInfoResponse();
		response.setMessage("Ticket info deleted successfully!");
		return response;
	}

	@Override
	public PurchaseMenuResponse purchaseMenu(PurchaseMenuRequest parameters) throws ServiceException_Exception {
		// Retrieve the feedbacks for this event in the data source
		List<TicketInfo> eventTickets = ticketInfoService.findByEventId(parameters.getEventId());

		// Build the reponse
		PurchaseMenuResponse response = factory.createPurchaseMenuResponse();
		// Access and create the JAXB list object
		AvailabilitiesList availabilitiesWrapper = factory.createPurchaseMenuResponseAvailabilitiesList();
		List<TicketAvailability> availabilities = availabilitiesWrapper.getTicketAvailability();
		// Create the JAXB Marshallable objects from the POJOs
		for (TicketInfo ticket : eventTickets) {
			TicketAvailability xmlTicket = factory.createTicketAvailability();
			xmlTicket.setTicketInfoId(ticket.getId());
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
		// read the data from the unmarshalled object into POJO
		SoldTicketData xmlTicket = parameters.getSoldTicketData();
		SoldTicket newSoldTicket = new SoldTicket(xmlTicket.getUserId(), xmlTicket.getEventId(),
				Utility.toLocalDateTime(xmlTicket.getReferenceDate()));
		// Add the ticket sale info to the data source
		SoldTicket newTicketWithId = soldTicketService.create(newSoldTicket);
		// Decrease available tickets in event and ticket info
		decreaseAvailableTickets(newTicketWithId.getEventId(), newTicketWithId.getReferenceDate());
		// Return the newly generated ticket sale info id in the response
		CreateSoldTicketResponse response = factory.createCreateSoldTicketResponse();
		response.setSoldTicketId(newTicketWithId.getId());
		return response;
	}

	@Override
	public UpdateSoldTicketResponse updateSoldTicket(UpdateSoldTicketRequest parameters)
			throws ServiceException_Exception {
		// read the data from the unmarshalled object into POJO
		SoldTicketData xmlTicket = parameters.getSoldTicketData();
		SoldTicket newSoldTicket = new SoldTicket(xmlTicket.getSoldTicketId(), xmlTicket.getUserId(),
				xmlTicket.getEventId(), Utility.toLocalDateTime(xmlTicket.getReferenceDate()));
		// Attempt to update the ticket sale info in the data source
		try {
			soldTicketService.update(newSoldTicket);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException_Exception("An error has occured while updating the ticket sale info.", e);
		}
		// Return the newly generated ticket sale info id in the response
		UpdateSoldTicketResponse response = factory.createUpdateSoldTicketResponse();
		response.setSoldTicketId(newSoldTicket.getId());
		return response;
	}

	@Override
	public DeleteSoldTicketResponse deleteSoldTicket(DeleteSoldTicketRequest parameters)
			throws ServiceException_Exception {

		// Delete the event in the data source
		SoldTicket toDelete = new SoldTicket();
		toDelete.setId(parameters.getSoldTicketId());
		try {
			soldTicketService.delete(toDelete);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException_Exception("An error has occured while deleting the ticket sale info.", e);
		}
		// Build the response
		DeleteSoldTicketResponse response = factory.createDeleteSoldTicketResponse();
		response.setMessage("Ticket sale info deleted successfully!");
		return response;
	}

	@Override
	public FetchEventSoldTicketsResponse fetchEventSoldTickets(FetchEventSoldTicketsRequest parameters)
			throws ServiceException_Exception {
		// Retrieve the tickets sold for this event in the data source
		List<SoldTicket> eventSoldTickets = soldTicketService.findByEventId(parameters.getEventId());
		// Build the response
		FetchEventSoldTicketsResponse response = factory.createFetchEventSoldTicketsResponse();
		// Access and create the JAXB list object
		SoldTicketsList ticketsList = response.getSoldTicketsList();
		ticketsList = factory.createFetchEventSoldTicketsResponseSoldTicketsList();
		List<SoldTicketData> xmlTickets = ticketsList.getSoldTicketData();
		// Create the JAXB Marshallable objects from the POJOs

		for (SoldTicket st : eventSoldTickets) {
			SoldTicketData ticketData = factory.createSoldTicketData();
			ticketData.setSoldTicketId(st.getId());
			ticketData.setUserId(st.getUserId());
			ticketData.setEventId(st.getEventId());
			ticketData.setReferenceDate(Utility.toXMLCalendar(st.getReferenceDate()));
			xmlTickets.add(ticketData);
		}

		response.setSoldTicketsList(ticketsList);
		return response;
	}

	@Override
	public FetchCustomerBoughtTicketsResponse fetchCustomerBoughtTickets(FetchCustomerBoughtTicketsRequest parameters)
			throws ServiceException_Exception {
		// Retrieve the tickets bought by this customer in the data source
		List<SoldTicket> customerTickets = soldTicketService.findByCustomerId(parameters.getUserId());
		// Build the response
		FetchCustomerBoughtTicketsResponse response = factory.createFetchCustomerBoughtTicketsResponse();
		// Access and create the JAXB list object
		it.univaq.sose.dagi.wsdltypes.FetchCustomerBoughtTicketsResponse.SoldTicketsList ticketsList = response
				.getSoldTicketsList();
		ticketsList = factory.createFetchCustomerBoughtTicketsResponseSoldTicketsList();
		EventsList eventsList = factory.createFetchCustomerBoughtTicketsResponseEventsList();
		List<SoldTicketData> xmlTickets = ticketsList.getSoldTicketData();
		List<EventData> xmlEvents = eventsList.getEventData();
		// Create the JAXB Marshallable objects from the POJOs
		Set<Long> eventIds = new HashSet<>();
		for (SoldTicket st : customerTickets) {
			SoldTicketData ticketData = factory.createSoldTicketData();
			ticketData.setSoldTicketId(st.getId());
			ticketData.setUserId(st.getUserId());
			ticketData.setEventId(st.getEventId());
			eventIds.add(st.getEventId()); // add to the set of ids to fetch
			ticketData.setReferenceDate(Utility.toXMLCalendar(st.getReferenceDate()));
			xmlTickets.add(ticketData);
		}

		// Fetch events info
		for (Long id : eventIds) {
			Event e = eventService.findById(id);
			EventData event = factory.createEventData();
			event.setEventId(e.getId());
			event.setName(e.getName());
			event.setDescription(e.getDescription());
			event.setOrganizerId(e.getOrganizerId());
			event.setLocation(e.getLocation());
			event.setStartDate(Utility.toXMLCalendar(e.getStartDate()));
			event.setEndDate(Utility.toXMLCalendar(e.getEndDate()));
			event.setNrTickets(e.getNrTickets());
			xmlEvents.add(event);
		}

		response.setSoldTicketsList(ticketsList);
		response.setEventsList(eventsList);
		return response;
	}

	/*************************************
	 * Feedback endpoints
	 *************************************/

	@Override
	public CreateFeedbackResponse createFeedback(CreateFeedbackRequest parameters) throws ServiceException_Exception {
		// Create the feedback POJO
		FeedbackData newFeedback = parameters.getFeedbackData();
		Feedback feedback = new Feedback(newFeedback.getUserId(), newFeedback.getEventId(), newFeedback.getRating(),
				newFeedback.getBody());
		// Save the feedback in the data source
		feedbackService.create(feedback);
		// Build the response
		CreateFeedbackResponse response = factory.createCreateFeedbackResponse();
		response.setMessage("We received your feedback, thank you!");
		return response;
	}

	@Override
	public UpdateFeedbackResponse updateFeedback(UpdateFeedbackRequest parameters) throws ServiceException_Exception {
		// Create the feedback POJO
		FeedbackData xmlFeedback = parameters.getFeedbackData();
		Feedback feedback = new Feedback(xmlFeedback.getFeedbackId(), xmlFeedback.getUserId(), xmlFeedback.getEventId(),
				xmlFeedback.getRating(), xmlFeedback.getBody());
		// Attempt to update the feedback in the data source
		try {
			feedbackService.update(feedback);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException_Exception("An error has occured while updating the feedback.", e);
		}
		// Build the response
		UpdateFeedbackResponse response = factory.createUpdateFeedbackResponse();
		response.setMessage("Feedback updated successfully!");
		return response;
	}

	@Override
	public DeleteFeedbackResponse deleteFeedback(DeleteFeedbackRequest parameters) throws ServiceException_Exception {
		// Attempt to delete the feedback in the data source
		Feedback toDelete = new Feedback();
		toDelete.setId(parameters.getFeedbackId());
		try {
			feedbackService.delete(toDelete);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException_Exception("An error has occured while deleting the feedback.", e);
		}
		// Build the response
		DeleteFeedbackResponse response = factory.createDeleteFeedbackResponse();
		response.setMessage("Feedback deleted successfully!");
		return response;
	}

	@Override
	public FetchEventFeedbackResponse fetchEventFeedback(FetchEventFeedbackRequest parameters)
			throws ServiceException_Exception {

		// Retrieve the feedbacks for this event in the data source
		List<Feedback> eventFeedback = feedbackService.findByEventId(parameters.getEventId());
		// Build the reponse
		FetchEventFeedbackResponse response = factory.createFetchEventFeedbackResponse();
		// Access and create the JAXB list object
		FeedbackList feedbackList = response.getFeedbackList();
		feedbackList = factory.createFetchEventFeedbackResponseFeedbackList();
		List<FeedbackData> xmlEventFeedback = feedbackList.getFeedbackData();
		// Create the JAXB Marshallable objects from the POJOs
		for (Feedback f : eventFeedback) {
			if (f.getEventId() == parameters.getEventId()) {
				FeedbackData feedback = factory.createFeedbackData();
				feedback.setFeedbackId(f.getId());
				feedback.setUserId(f.getUserId());
				feedback.setEventId(f.getEventId());
				feedback.setRating(f.getRating());
				feedback.setBody(f.getBody());

				xmlEventFeedback.add(feedback);
			}
		}
		response.setFeedbackList(feedbackList);
		return response;
	}

	/**********************************************
	 * Private methods
	 ********************************************/
	private void decreaseAvailableTickets(long eventId, LocalDateTime ticketDate) {
		// Fetch the event to use in update
		Event event = eventService.findById(eventId);
		event.setNrTickets(event.getNrTickets() - 1);
		eventService.update(event);

		// Fetch the ticket info to use in update
		List<TicketInfo> eventTickets = ticketInfoService.findByEventId(eventId);
		for (TicketInfo ticket : eventTickets) {
			if (ticket.getReferenceDate().equals(ticketDate)) {
				ticket.setAvailableTickets(ticket.getAvailableTickets() - 1);
				ticketInfoService.update(ticket);
				break;
			}
		}
	}
}
