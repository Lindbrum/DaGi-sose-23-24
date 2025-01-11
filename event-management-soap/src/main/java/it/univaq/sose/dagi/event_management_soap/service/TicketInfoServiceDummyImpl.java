package it.univaq.sose.dagi.event_management_soap.service;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import it.univaq.sose.dagi.event_management_soap.model.TicketInfo;

@Service
public class TicketInfoServiceDummyImpl implements TicketInfoService {

	private static Long nextId = 0L;
	private static List<TicketInfo> ticketInfos = new ArrayList<>();
	
	//This is the constructor of the TicketInfoServiceDummyImpl class. Initializes some TicketInfo objects with sample values ​​and adds them to a list of ticketInfos.
	//Each TicketInfo is created with a unique ID and reference date, then added to the list.
	public TicketInfoServiceDummyImpl() {
		TicketInfo t0 = new TicketInfo(nextId++, 0L, null, 50);
		t0.setReferenceDate(LocalDateTime.of(2025, Month.JULY, 29, 18, 0));
		TicketInfo t1 = new TicketInfo(nextId++, 1L, null, 10);
		t1.setReferenceDate(LocalDateTime.of(2025, Month.JULY, 30, 20, 0));
		TicketInfo t2 = new TicketInfo(nextId++, 2L, null, 400);
		t2.setReferenceDate(LocalDateTime.of(2025, Month.AUGUST, 12, 17, 0));
		TicketInfo t3 = new TicketInfo(nextId++, 3L, null, 400);
		t3.setReferenceDate(LocalDateTime.of(2025, Month.JUNE, 24, 17, 0));
		
		ticketInfos.add(t0);
		ticketInfos.add(t1);
		ticketInfos.add(t2);
		ticketInfos.add(t3);
	}
	
	//This method takes a TicketInfo object as a parameter, assigns a new ID to the ticket, adds it to the ticketInfos list
	//and returns the updated object. It is used to create a new ticket and add it to the system.
	@Override
	public TicketInfo create(TicketInfo newTicketInfo) {
		newTicketInfo.setId(nextId++);
		ticketInfos.add(newTicketInfo);
		return newTicketInfo;
	}

	//This method updates an existing ticket. If the ticket ID is null, throw IllegalArgumentException.
	//It searches for the ticket in the list, updates its details if found, and returns the updated ticket.
	//If the ticket is not found, throw NoSuchElementException.
	@Override
	public TicketInfo update(TicketInfo ticketInfo) throws IllegalArgumentException, NoSuchElementException {
		if(ticketInfo.getId() == null) {
			throw new IllegalArgumentException("ID cannot be null.");
		}
		TicketInfo found = null;
		for(TicketInfo current : ticketInfos) {
			if(current.equals(ticketInfo)) {
				current.setEventId(ticketInfo.getEventId());
				current.setReferenceDate(ticketInfo.getReferenceDate());
				current.setAvailableTickets(ticketInfo.getAvailableTickets());
				
				found = current;
				break;
			}
		}
		if(found == null) {
			throw new NoSuchElementException("Ticket info to update not found.");
		}
		return found;
	}

	//This method deletes a ticket from the list. If the ticket ID is null, throw IllegalArgumentException.
	//It searches for and removes the ticket from the list, throwing NoSuchElementException if the ticket is not found.
	@Override
	public void delete(TicketInfo ticketInfo) throws IllegalArgumentException, NoSuchElementException {
		if(ticketInfo.getId() == null) {
			throw new IllegalArgumentException("ID cannot be null.");
		}
		for(Iterator<TicketInfo> itr = ticketInfos.listIterator(); itr.hasNext();) {
			TicketInfo current = itr.next();
			if(current.equals(ticketInfo)) {
				itr.remove();
				return;
			}
		}
		throw new NoSuchElementException("Ticket info to delete not found.");
	}

	//This method searches for a ticket by ID. If the ticket is found, he returns it; otherwise, throw NoSuchElementException.
	@Override
	public TicketInfo findById(long id) throws NoSuchElementException {
		for(TicketInfo current : ticketInfos) {
			if(current.getId().equals(id)) {
				return current;
			}
		}
		throw new NoSuchElementException("Ticket info not found.");
	}

	//Returns an immutable copy of the list of all available ticketInfos tickets.
	@Override
	public List<TicketInfo> getAll() {
		return List.copyOf(ticketInfos);
	}

	//This method searches for all tickets associated with a given event, identified by the ID provided. It returns a list of matching tickets.
	@Override
	public List<TicketInfo> findByEventId(long eventId) {
		List<TicketInfo> result = new ArrayList<>();
		for(TicketInfo current : ticketInfos) {
			if(current.getEventId().equals(eventId)) {
				result.add(current);
			}
		}
		return result;
	}
	
}
