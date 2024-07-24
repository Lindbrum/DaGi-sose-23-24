package it.univaq.sose.dagi.event_management_soap.service;

import java.util.List;
import java.util.NoSuchElementException;

import it.univaq.sose.dagi.event_management_soap.model.SoldTicket;

public interface SoldTicketService {
	public SoldTicket create(SoldTicket newSoldTicket);
	public SoldTicket update(SoldTicket soldTicket) throws IllegalArgumentException, NoSuchElementException;
	public void delete(SoldTicket soldTicket) throws IllegalArgumentException, NoSuchElementException;
	public SoldTicket findById(long id) throws NoSuchElementException;
	public List<SoldTicket> getAll();
	public List<SoldTicket> findByEventId(long eventId);
	public List<SoldTicket> findByCustomerId(long customerId);
}
