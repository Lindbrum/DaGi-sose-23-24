package it.univaq.sose.dagi.event_management_soap.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.stereotype.Service;

import it.univaq.sose.dagi.event_management_soap.dao.TicketInfoRepository;
import it.univaq.sose.dagi.event_management_soap.model.TicketInfo;

@Service
public class TicketInfoServiceImpl implements TicketInfoService {

	private final TicketInfoRepository ticketInfoRepository;
	
	public TicketInfoServiceImpl(TicketInfoRepository ticketInfoRepository) {
		this.ticketInfoRepository = ticketInfoRepository;
	}

	@Override
	public TicketInfo create(TicketInfo newTicketInfo) {
		
		return this.ticketInfoRepository.save(newTicketInfo);
	}

	@Override
	public TicketInfo update(TicketInfo ticketInfo) throws IllegalArgumentException, NoSuchElementException {
		if(ticketInfo.getId() != null) {
			Optional<TicketInfo> found = this.ticketInfoRepository.findById(ticketInfo.getId());
			if(found.isPresent()) {
				return this.ticketInfoRepository.save(ticketInfo);
			}else {
				throw new NoSuchElementException("Ticket's info to update not found.");
			}
		}else {
			throw new IllegalArgumentException("ID cannot be null.");
		}
	}

	@Override
	public void delete(TicketInfo ticketInfo) throws IllegalArgumentException, NoSuchElementException {
		this.ticketInfoRepository.delete(ticketInfo);

	}

	@Override
	public TicketInfo findById(long id) throws NoSuchElementException {
		return this.ticketInfoRepository.findById(id).orElseThrow(()->{throw new NoSuchElementException("Ticket's info not found.");});
	}

	@Override
	public List<TicketInfo> getAll() {
		return this.ticketInfoRepository.findAll();
	}

	@Override
	public List<TicketInfo> findByEventId(long eventId) {
		return this.ticketInfoRepository.findByEventId(eventId);
	}

}
