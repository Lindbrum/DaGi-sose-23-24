package it.univaq.sose.dagi.event_management_soap.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import it.univaq.sose.dagi.event_management_soap.dao.EventRepository;
import it.univaq.sose.dagi.event_management_soap.model.Event;

@Service
public class EventServiceImpl implements EventService {

	private final EventRepository eventRepository;
	private final int EVENTS_PER_PAGE;
	
	
	
	public EventServiceImpl(EventRepository eventRepository,
			@Value("${service.event.catalogue.items-per-page}") int eventsPerPage) {
		
		this.eventRepository = eventRepository;
		this.EVENTS_PER_PAGE = eventsPerPage;
	}

	@Override
	public Event create(Event event) {
		
		return this.eventRepository.save(event);
	}

	@Override
	public Event update(Event event) throws IllegalArgumentException, NoSuchElementException {
		if(event.getId() != null) {
			Optional<Event> found = this.eventRepository.findById(event.getId());
			if(found.isPresent()) {
				return this.eventRepository.save(event);
			}else {
				throw new NoSuchElementException("Event to update not found.");
			}
		}else {
			throw new IllegalArgumentException("ID cannot be null.");
		}
	}

	@Override
	public void delete(Event event) throws IllegalArgumentException, NoSuchElementException {
		this.eventRepository.delete(event);
	}

	@Override
	public Event findById(long id) throws NoSuchElementException {
		
		return this.eventRepository.findById(id).orElseThrow(() -> {throw new NoSuchElementException("Event not found.");});
	}

	@Override
	public List<Event> getAll() {
		
		return this.eventRepository.findAll();
	}

	@Override
	public List<Event> sortAndFindByPage(int page, String sortBy) {
		
		Pageable pageable = setupPageable(page, sortBy);
		
		return this.eventRepository.findAll(pageable).getContent();
	}


	@Override
	public List<Event> sortAndFindByPageAndOrganizer(long organizerId, int page, String sortBy) {

		Pageable pageable = setupPageable(page, sortBy);
		
		return this.eventRepository.findAllByOrganizerId(organizerId, pageable).getContent();
	}
	
	private Pageable setupPageable(int page, String sortBy) {
		Sort sortingMethod;
		if (sortBy.equals(SortingMode.ID_DESC.name())) {
			sortingMethod = Sort.by("id").descending();
		} else if (sortBy.equals(SortingMode.ID_ASC.name())) {
			sortingMethod = Sort.by("id").ascending(); //Ascending ID is natural order.
		} else if (sortBy.equals(SortingMode.ALPHABETICAL_DESC.name())) {
			sortingMethod = Sort.by("name").descending();
		} else if (sortBy.equals(SortingMode.ALPHABETICAL_ASC.name())){
			sortingMethod = Sort.by("name").ascending();
		}else {
			System.err.println("\n\n\nWARNING: An invalid sorting method was provided, defaulting to ID_DESC.\n\n\n");
			sortingMethod = Sort.by("id").descending();
		}
		
		Pageable pageable = PageRequest.of(page - 1, EVENTS_PER_PAGE, sortingMethod); //page is zero indexed
		return pageable;
	}

}
