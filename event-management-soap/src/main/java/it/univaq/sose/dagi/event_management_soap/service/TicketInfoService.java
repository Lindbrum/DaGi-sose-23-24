package it.univaq.sose.dagi.event_management_soap.service;

import java.util.List;
import java.util.NoSuchElementException;

import it.univaq.sose.dagi.event_management_soap.model.TicketInfo;

public interface TicketInfoService {
	public TicketInfo create(TicketInfo newTicketInfo);
	public TicketInfo update(TicketInfo ticketInfo) throws IllegalArgumentException, NoSuchElementException;
	public void delete(TicketInfo ticketInfo) throws IllegalArgumentException, NoSuchElementException;
	public TicketInfo findById(long id) throws NoSuchElementException;
	public List<TicketInfo> getAll();
	public List<TicketInfo> findByEventId(long eventId);
}
