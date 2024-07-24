package it.univaq.sose.dagi.event_management_soap.service;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import it.univaq.sose.dagi.event_management_soap.model.Event;

@Service
public class EventServiceDummyImpl implements EventService {
	
	//How many events to show in a page of the catalogue
	private final int EVENTS_PER_PAGE;

	private static Long nextId = 0L;
	private static List<Event> events = new ArrayList<>();
	
	public EventServiceDummyImpl(@Value("${service.event.catalogue.items-per-page}") int eventsPerPage) {
		EVENTS_PER_PAGE = eventsPerPage;
		
		Event e0 = new Event(nextId++, "Evening with fans", "InnTale has organized an evening with fans.", 2L, "Lucca", null, null, 50);
		e0.setStartDate(LocalDateTime.of(2024, Month.JULY, 29, 18, 0));
		e0.setEndDate(LocalDateTime.of(2024, Month.JULY, 29, 22, 0));
		Event e1 = new Event(nextId++, "D&D ESO campaign", "InnTale D&D campaign based on Elder Scrolls Online.", 2L, "Lucca", null, null, 10);
		e1.setStartDate(LocalDateTime.of(2024, Month.JULY, 30, 20, 0));
		e1.setEndDate(LocalDateTime.of(2024, Month.JULY, 30, 23, 0));
		Event e2 = new Event(nextId++, "Antonello Venditti live", "Canta Antonello Venditti in live per la perdonanza.", 1L, "L'Aquila", null, null, 400);
		e2.setStartDate(LocalDateTime.of(2024, Month.AUGUST, 12, 17, 0));
		e2.setEndDate(LocalDateTime.of(2024, Month.AUGUST, 12, 21, 0));

		events.add(e0);
		events.add(e1);
		events.add(e2);
	}
	
	@Override
	public Event create(Event newEvent) {
		newEvent.setId(nextId++);
		events.add(newEvent);
		return newEvent;
	}

	@Override
	public Event update(Event event) throws IllegalArgumentException, NoSuchElementException {
		if(event.getId() == null) {
			throw new IllegalArgumentException("ID cannot be null.");
		}
		Event found = null;
		for(Event current : events) {
			if(current.equals(event)) {
				current.setName(event.getName());
				current.setDescription(event.getDescription());
				current.setOrganizerId(event.getOrganizerId());
				current.setLocation(event.getLocation());
				current.setStartDate(event.getStartDate());
				current.setEndDate(event.getEndDate());
				current.setNrTickets(event.getNrTickets());
				found = current;
				break;
			}
		}
		if(found == null) {
			throw new NoSuchElementException("Event to update not found.");
		}
		return found;
	}

	@Override
	public void delete(Event event) throws IllegalArgumentException, NoSuchElementException {
		if(event.getId() == null) {
			throw new IllegalArgumentException("ID cannot be null.");
		}
		for(Iterator<Event> itr = events.listIterator(); itr.hasNext();) {
			Event current = itr.next();
			if(current.equals(event)) {
				itr.remove();
				return;
			}
		}
		throw new NoSuchElementException("Event to delete not found.");
	}

	@Override
	public Event findById(long id) throws NoSuchElementException {
		for(Event current : events) {
			if(current.getId().equals(id)) {
				return current;
			}
		}
		throw new NoSuchElementException("Event not found.");
	}

	@Override
	public List<Event> getAll() {
		return List.copyOf(events);
	}

	@Override
	public List<Event> sortAndFindByPage(int page, String sortBy) {
		//Sort the list depending on the parameter
		if (sortBy.equals(SortingMode.ID_DESC.name())) {
			events.sort(Event.getIdDescComparator());
		} else if (sortBy.equals(SortingMode.ID_ASC.name())) {
			events.sort(null); //Ascending ID is natural order
		} else if (sortBy.equals(SortingMode.ALPHABETICAL_DESC.name())) {
			events.sort(Event.getNameDescComparator());
		} else if (sortBy.equals(SortingMode.ALPHABETICAL_ASC.name())){
			events.sort(Event.getNameAscComparator());
		}else {
			System.err.println("\n\n\nWARNING: An invalid sorting method was provided, defaulting to ID_DESC.\n\n\n");
			events.sort(Event.getIdDescComparator());
		}
		
		//Fetch the sub list corresponding to the catalogue page
		List<Event> result;
		int firstIndex = (page - 1) * EVENTS_PER_PAGE;
		//Return empty if out of bounds otherwise sub list
		if(firstIndex < events.size()) {			
			int lastIndex = Math.min(EVENTS_PER_PAGE * page, events.size());
			result = events.subList(firstIndex, lastIndex);
		}else {
			result = new ArrayList<>(0);
		}
		return result;
	}

	@Override
	public List<Event> sortAndFindByPageAndOrganizer(long organizerId, int page, String sortBy) {
		//Sort the list depending on the parameter
				if (sortBy.equals(SortingMode.ID_DESC.name())) {
					events.sort(Event.getIdDescComparator());
				} else if (sortBy.equals(SortingMode.ID_ASC.name())) {
					events.sort(null); //Ascending ID is natural order
				} else if (sortBy.equals(SortingMode.ALPHABETICAL_DESC.name())) {
					events.sort(Event.getNameDescComparator());
				} else if (sortBy.equals(SortingMode.ALPHABETICAL_ASC.name())){
					events.sort(Event.getNameAscComparator());
				}else {
					System.err.println("\n\n\nWARNING: An invalid sorting method was provided, defaulting to ID_DESC.\n\n\n");
					events.sort(Event.getIdDescComparator());
				}
				
				//Fetch the sub list corresponding to the organizer catalogue page
				List<Event> result = new ArrayList<>();
				for(Event e : events) {
					if(e.getOrganizerId() == organizerId) {
						result.add(e);
					}
				}
				int firstIndex = (page - 1) * EVENTS_PER_PAGE;
				//Return empty if out of bounds otherwise sub list
				if(firstIndex < events.size()) {			
					int lastIndex = Math.min(EVENTS_PER_PAGE * page, result.size());
					result = result.subList(firstIndex, lastIndex);
				}else {
					result = new ArrayList<>(0);
				}
				return result;
	}
	
	

}
