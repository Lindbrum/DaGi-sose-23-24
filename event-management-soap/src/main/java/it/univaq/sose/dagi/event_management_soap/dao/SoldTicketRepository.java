package it.univaq.sose.dagi.event_management_soap.dao;

import java.util.List;

import org.springframework.data.repository.ListCrudRepository;

import it.univaq.sose.dagi.event_management_soap.model.SoldTicket;

public interface SoldTicketRepository extends ListCrudRepository<SoldTicket, Long> {

	List<SoldTicket> findByEventId(long eventId);

	List<SoldTicket> findByUserId(long userId);
	
}
