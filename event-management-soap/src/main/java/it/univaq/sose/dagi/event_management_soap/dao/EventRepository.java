package it.univaq.sose.dagi.event_management_soap.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.ListCrudRepository;

import it.univaq.sose.dagi.event_management_soap.model.Event;

public interface EventRepository extends ListCrudRepository<Event, Long> {
	
	Page<Event> findAll(Pageable pageable);
	
	Page<Event> findAllByOrganizerId(long organizerId, Pageable pageable);
}
