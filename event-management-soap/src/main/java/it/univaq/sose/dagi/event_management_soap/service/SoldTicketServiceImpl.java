package it.univaq.sose.dagi.event_management_soap.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.stereotype.Service;

import it.univaq.sose.dagi.event_management_soap.dao.SoldTicketRepository;
import it.univaq.sose.dagi.event_management_soap.model.SoldTicket;

@Service
public class SoldTicketServiceImpl implements SoldTicketService {

	private final SoldTicketRepository soldTicketRepository;
	
	public SoldTicketServiceImpl(SoldTicketRepository soldTicketRepository) {
		this.soldTicketRepository = soldTicketRepository;
	}

	@Override
	public SoldTicket create(SoldTicket newSoldTicket) {
		
		return this.soldTicketRepository.save(newSoldTicket);
	}

	@Override
	public SoldTicket update(SoldTicket soldTicket) throws IllegalArgumentException, NoSuchElementException {
		if(soldTicket.getId() != null) {
			Optional<SoldTicket> found = this.soldTicketRepository.findById(soldTicket.getId());
			if(found.isPresent()) {
				return this.soldTicketRepository.save(soldTicket);
			}else {
				throw new NoSuchElementException("Sold ticket's info to update not found.");
			}
		}else {
			throw new IllegalArgumentException("ID cannot be null.");
		}
	}

	@Override
	public void delete(SoldTicket soldTicket) throws IllegalArgumentException, NoSuchElementException {
		this.soldTicketRepository.delete(soldTicket);

	}

	@Override
	public SoldTicket findById(long id) throws NoSuchElementException {
		return this.soldTicketRepository.findById(id).orElseThrow(()->{throw new NoSuchElementException("Sold ticket's info not found.");});
	}

	@Override
	public List<SoldTicket> getAll() {
		
		return this.soldTicketRepository.findAll();
	}

	@Override
	public List<SoldTicket> findByEventId(long eventId) {
		
		return this.soldTicketRepository.findByEventId(eventId);
	}

	@Override
	public List<SoldTicket> findByCustomerId(long customerId) {
		// TODO Auto-generated method stub
		return this.soldTicketRepository.findByUserId(customerId);
	}

}
