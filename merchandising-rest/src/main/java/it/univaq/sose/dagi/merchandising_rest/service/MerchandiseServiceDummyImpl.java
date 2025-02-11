package it.univaq.sose.dagi.merchandising_rest.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import it.univaq.sose.dagi.merchandising_rest.model.Merchandise;


@Service
public class MerchandiseServiceDummyImpl implements MerchandiseService {

	//How many merchs to show in a page of the catalogue.
	private final int MERCHS_PER_PAGE;
	
	//This static field holds the list of Merchandise objects.
	private static List<Merchandise> merchandiseRepository = new ArrayList<>();
	//This static field keeps track of the next available ID for new Merchandise objects. It is used to generate unique IDs for each merchandise item.
	private static long nextID = 0;
	
	//This is a class class with a predefined list of Merchandise items.
	//It creates several Merchandise objects with unique IDs, event IDs, bar codes, names, and descriptions.
	//These objects are added to an in-memory list, merchandiseRepository, which serves as the data source for this service.
	public MerchandiseServiceDummyImpl(@Value("${service.merchandise.catalogue.items-per-page}") int merchssPerPage) {
		
		MERCHS_PER_PAGE = merchssPerPage;
		
		Merchandise m0 = new  Merchandise(nextID++, 0L, 90930312919L, "Lily plush", "A miniature plush one of Kurolily's cats.");
		Merchandise m1 = new  Merchandise(nextID++, 1L, 90930312920L, "Lily plush", "A miniature plush one of Kurolily's cats.");
		Merchandise m2 = new  Merchandise(nextID++, 2L, 90930312921L, "Santino di San Celestino", "A postcard with the image of S. Celestino.");
		Merchandise m3 = new  Merchandise(nextID++, 2L, 90930312922L, "Venditti's album", "The latest album from Antonello Venditti.");
		
		merchandiseRepository.add(m0);
		merchandiseRepository.add(m1);
		merchandiseRepository.add(m2);
		merchandiseRepository.add(m3);
	}
	
	//This method retrieves a copy of the entire list of merchandise items stored in merchandiseRepository.
	//It returns this list as an immutable List of Merchandise objects. This method provides a way to access all
	//merchandise records currently held in the repository.
	@Override
	public List<Merchandise> getAll() {
		return List.copyOf(merchandiseRepository);
	}
	
	//This method retrieves a copy of a page of merchandise items stored in merchandiseRepository.
	//It returns this list as an immutable List of Merchandise objects. This method provides a way to access a paginated view
	//of merchandise records currently held in the repository.
	@Override
	public List<Merchandise> getPage(int page, String sortBy) {
		//Sort the list depending on the parameter.
		if (sortBy.equals(SortingMode.ID_DESC.name())) {
			merchandiseRepository.sort(Merchandise.getIdDescComparator());
		} else if (sortBy.equals(SortingMode.ID_ASC.name())) {
			merchandiseRepository.sort(null); //Ascending ID is natural order.
		} else if (sortBy.equals(SortingMode.ALPHABETICAL_DESC.name())) {
			merchandiseRepository.sort(Merchandise.getNameDescComparator());
		} else if (sortBy.equals(SortingMode.ALPHABETICAL_ASC.name())){
			merchandiseRepository.sort(Merchandise.getNameAscComparator());
		}else {
			System.err.println("\n\n\nWARNING: An invalid sorting method was provided, defaulting to ID_DESC.\n\n\n");
			merchandiseRepository.sort(Merchandise.getIdDescComparator());
		}
		
		//Fetch the sub list corresponding to the catalogue page.
		List<Merchandise> result;
		int firstIndex = (page - 1) * MERCHS_PER_PAGE;
		//Return empty if out of bounds otherwise sub list.
		if(firstIndex < merchandiseRepository.size()) {			
			int lastIndex = Math.min(MERCHS_PER_PAGE * page, merchandiseRepository.size());
			result = merchandiseRepository.subList(firstIndex, lastIndex);
		}else {
			result = new ArrayList<>(0);
		}
		return result;
	}

	//This method method returns a list of merchandise items that are associated with a specified event.
	//It iterates through merchandiseRepository and collects items whose event ID matches the provided eventId.
	//The result is a List of Merchandise objects that correspond to the given event.
	@Override
	public List<Merchandise> getByEvent(long eventId) {
		List<Merchandise> eventMerchandise = new ArrayList<>();
		for(Merchandise m : merchandiseRepository) {
			if(m.getEventId() == eventId) {
				eventMerchandise.add(m);
			}
		}
		return eventMerchandise;
	}

	//This method adds a new Merchandise item to the repository. It first generates a new ID for the item, assigns this
	//ID to the newMerch object, and then adds it to merchandiseRepository. The method returns the updated Merchandise object, which
	//now includes the newly assigned ID.
	@Override
	public Merchandise save(Merchandise newMerch) {
		long id = nextID++;
		newMerch.setId(id);
		merchandiseRepository.add(newMerch);
		Merchandise updated = newMerch;
		return updated;
	}

	//This method updates an existing Merchandise item in the repository. It requires that the updatedMerch object contains
	//a non-null ID. The method searches for the item with the matching ID in merchandiseRepository and, if found, updates
	//its properties with those from the updatedMerch object. If no matching item is found, an exception is thrown.
	//The method returns the updated Merchandise object.
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

	//This method associates a merchandise item with a specific event by updating its event ID or disassociates from the currently set event if null was passed as the event ID.
	//It requires merchId to be non-null. The method searches for the merchandise item with the given merchId in the repository.
	//If found, it updates the item's event ID with the provided eventId or set it to null if disassociating. If no matching item is found, an exception is thrown.
	//The method returns the updated Merchandise object.
	@Override
	public Merchandise addEventToMerch(Long eventId, Long merchId)
			throws IllegalArgumentException, NoSuchElementException {
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
