package it.univaq.sose.dagi.event_management_soap.service;

import java.util.List;
import java.util.NoSuchElementException;

import it.univaq.sose.dagi.event_management_soap.model.Event;

//The EventService interface provides methods for handling events.
//It includes operations to create, update and delete events, find events by ID, get all events and retrieve them sorted and paginated.
//The SortingMode enum defines sorting modes for events, such as sorting by ID ascending or descending, and alphabetical sorting.
public interface EventService {
	enum SortingMode {
		ID_DESC, ID_ASC, ALPHABETICAL_ASC, ALPHABETICAL_DESC
	}
	
	//Creates a new event and returns the created event.
	public Event create(Event event);
	//Update an existing event. It can raise IllegalArgumentException if the data is invalid, or NoSuchElementException if the event does not exist.
	public Event update(Event event) throws IllegalArgumentException, NoSuchElementException;
	//Removes an event. Raises IllegalArgumentException if the event is invalid, or NoSuchElementException if it does not exist.
	public void delete(Event event) throws IllegalArgumentException, NoSuchElementException;
	//Retrieve an event by ID. Returns the event or raises NoSuchElementException if not found.
	public Event findById(long id) throws NoSuchElementException;
	//Returns a list of all events.
	public List<Event> getAll();
	//Returns sorted and paginated events. Parameters include the page to display and the sort order.
	public List<Event> sortAndFindByPage(int page, String sortBy);
}
