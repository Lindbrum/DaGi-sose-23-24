package it.univaq.sose.dagi.event_management_soap.service;

import java.util.List;
import java.util.NoSuchElementException;

import it.univaq.sose.dagi.event_management_soap.model.TicketInfo;

public interface TicketInfoService {
	//This method is used to create a new TicketInfo object.
	//It accepts a TicketInfo instance as a parameter and returns the created TicketInfo with an assigned ID.
	public TicketInfo create(TicketInfo newTicketInfo);
	//This method updates an existing TicketInfo object. It requires the TicketInfo to have a non-null ID.
	//If the ID is null, it throws IllegalArgumentException. If the TicketInfo is not found, it throws NoSuchElementException. It returns the updated TicketInfo.
	public TicketInfo update(TicketInfo ticketInfo) throws IllegalArgumentException, NoSuchElementException;
	//This method deletes a TicketInfo object. It requires the TicketInfo to have a non-null ID. If the ID is null, it throws IllegalArgumentException.
	//If the TicketInfo to delete is not found, it throws NoSuchElementException.
	public void delete(TicketInfo ticketInfo) throws IllegalArgumentException, NoSuchElementException;
	//This method retrieves a TicketInfo object by its ID. If the TicketInfo is found, it returns it; otherwise, it throws NoSuchElementException.
	public TicketInfo findById(long id) throws NoSuchElementException;
	//This method returns a list of all TicketInfo objects. The list is a copy of the current collection of ticket information.
	public List<TicketInfo> getAll();
	//This method retrieves a list of TicketInfo objects associated with a specific event, identified by eventId. It returns a list of ticket information related to that event.
	public List<TicketInfo> findByEventId(long eventId);
}
