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
	
	public TicketInfoServiceDummyImpl() {
		TicketInfo t0 = new TicketInfo(nextId++, 0L, null, 50);
		t0.setReferenceDate(LocalDateTime.of(2024, Month.JULY, 29, 18, 0));
		TicketInfo t1 = new TicketInfo(nextId++, 1L, null, 10);
		t1.setReferenceDate(LocalDateTime.of(2024, Month.JULY, 30, 20, 0));
		TicketInfo t2 = new TicketInfo(nextId++, 2L, null, 400);
		t2.setReferenceDate(LocalDateTime.of(2024, Month.AUGUST, 12, 17, 0));
		
		ticketInfos.add(t0);
		ticketInfos.add(t1);
		ticketInfos.add(t2);
	}
	
	@Override
	public TicketInfo create(TicketInfo newTicketInfo) {
		newTicketInfo.setId(nextId++);
		ticketInfos.add(newTicketInfo);
		return newTicketInfo;
	}

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

	@Override
	public TicketInfo findById(long id) throws NoSuchElementException {
		for(TicketInfo current : ticketInfos) {
			if(current.getId().equals(id)) {
				return current;
			}
		}
		throw new NoSuchElementException("Ticket info not found.");
	}

	@Override
	public List<TicketInfo> getAll() {
		return List.copyOf(ticketInfos);
	}

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
