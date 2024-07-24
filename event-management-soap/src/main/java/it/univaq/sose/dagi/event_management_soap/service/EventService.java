package it.univaq.sose.dagi.event_management_soap.service;

import java.util.List;
import java.util.NoSuchElementException;

import it.univaq.sose.dagi.event_management_soap.model.Event;

public interface EventService {
	enum SortingMode {
		ID_DESC, ID_ASC, ALPHABETICAL_ASC, ALPHABETICAL_DESC
	}
	
	public Event create(Event event);
	public Event update(Event event) throws IllegalArgumentException, NoSuchElementException;
	public void delete(Event event) throws IllegalArgumentException, NoSuchElementException;
	public Event findById(long id) throws NoSuchElementException;
	public List<Event> getAll();
	public List<Event> sortAndFindByPage(int page, String sortBy);
	public List<Event> sortAndFindByPageAndOrganizer(long organizerId, int page, String sortBy);
}
