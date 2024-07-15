package it.univaq.sose.dagi.merchandising_rest.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import it.univaq.sose.dagi.merchandising_rest.model.Merchandise;

@Service
public class MerchandiseServiceDummyImpl implements MerchandiseService {

	private List<Merchandise> merchandiseRepository = new ArrayList<>();
	private static long nextID = 0;
	
	
	@Override
	public List<Merchandise> getAll() {
		return List.copyOf(merchandiseRepository);
	}

	@Override
	public List<Merchandise> getByEvent(long eventId) {
		List<Merchandise> eventMerchandise = new ArrayList<>();
		for(Merchandise m : merchandiseRepository) {
			if(m.getId() == eventId) {
				eventMerchandise.add(m);
			}
		}
		return eventMerchandise;
	}

	@Override
	public Merchandise save(Merchandise newMerch) {
		long id = nextID++;
		newMerch.setId(id);
		merchandiseRepository.add(newMerch);
		Merchandise updated = newMerch;
		return updated;
	}

	@Override
	public Merchandise update(Merchandise updatedMerch) throws IllegalArgumentException, NoSuchElementException {
		if(updatedMerch.getId() == null) {
			throw new IllegalArgumentException("ID is null.");
		}
		
		boolean found = false;
		for(Merchandise m : merchandiseRepository) {
			if(m.getId().equals(updatedMerch.getId())) {
				
				found = true;
				
				m.setEventId(updatedMerch.getEventId());
				m.setName(updatedMerch.getName());
				m.setDescription(updatedMerch.getDescription());
				m.setBarCode(updatedMerch.getBarCode());
				break;
			}
		}
		if(!found) {
			throw new NoSuchElementException("No record with this ID exists.");
		}
		return updatedMerch;
	}

	@Override
	public Merchandise addEventToMerch(Long eventId, Long merchId)
			throws IllegalArgumentException, NoSuchElementException {
		if(eventId == null) {
			throw new IllegalArgumentException("Event ID is null.");
		}
		if(merchId == null) {
			throw new IllegalArgumentException("Merchandise article ID is null.");
		}
		
		Merchandise found = null;
		for(Merchandise m : merchandiseRepository) {
			if(m.getId().equals(merchId)) {
				m.setEventId(eventId);
				found = m;
				break;
			}
		}
		if(found == null) {
			throw new NoSuchElementException("No merchandise article with this ID exists.");
		}
		return found;
	}

	
}
