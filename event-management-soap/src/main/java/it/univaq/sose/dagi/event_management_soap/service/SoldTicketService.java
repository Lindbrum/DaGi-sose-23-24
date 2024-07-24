package it.univaq.sose.dagi.event_management_soap.service;

import java.util.List;
import java.util.NoSuchElementException;

import it.univaq.sose.dagi.event_management_soap.model.SoldTicket;

public interface SoldTicketService {
	//This method is used to create a new sold ticket. It takes a SoldTicket object as a parameter, stores it, and returns the created object.
	public SoldTicket create(SoldTicket newSoldTicket);
	//This method updates an existing sold ticket. It accepts a SoldTicket object, and if the object to update is not found or the ID is null, it throws
	//NoSuchElementException or IllegalArgumentException, respectively. It returns the updated object.
	public SoldTicket update(SoldTicket soldTicket) throws IllegalArgumentException, NoSuchElementException;
	//This method deletes a sold ticket from memory. It requires a SoldTicket object to identify the ticket to remove.
	//It throws IllegalArgumentException if the ID is null and NoSuchElementException if the ticket is not found.
	public void delete(SoldTicket soldTicket) throws IllegalArgumentException, NoSuchElementException;
	//This method searches for a ticket sold by ID and returns the corresponding object. If the ticket is not found, throw NoSuchElementException.
	public SoldTicket findById(long id) throws NoSuchElementException;
	//Returns a list of all available sold tickets. The list is a copy of the stored tickets.
	public List<SoldTicket> getAll();
	//This method returns a list of tickets sold associated with a specific event, identified by eventId.
	public List<SoldTicket> findByEventId(long eventId);
	public List<SoldTicket> findByCustomerId(long customerId);
}
