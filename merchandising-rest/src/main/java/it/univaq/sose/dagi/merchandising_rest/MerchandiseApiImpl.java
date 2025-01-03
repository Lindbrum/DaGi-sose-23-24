package it.univaq.sose.dagi.merchandising_rest;

import java.util.List;

import it.univaq.sose.dagi.merchandising_rest.model.Merchandise;
import it.univaq.sose.dagi.merchandising_rest.service.MerchandiseService;
import it.univaq.sose.dagi.merchandising_rest.service.MerchandiseServiceDummyImpl;

public class MerchandiseApiImpl implements MerchandiseApi {

	// Service (injected)
	private final MerchandiseService merchandiseService;

	// @Autowired is inferred by Spring Boot when there is a single public
	// constructor
	//@Autowired
	public MerchandiseApiImpl(MerchandiseServiceDummyImpl merchandiseServiceImpl) {
			this.merchandiseService = merchandiseServiceImpl;
		}

	//This method retrieves the entire list of merchandise items from the service. It returns a ResponseEntity containing the list
	//of Merchandise objects with an HTTP status of 200 (OK). This indicates that the merchandise list has been successfully
	//fetched and is being returned in JSON format.
	@Override
	public List<Merchandise> getAll() {
		List<Merchandise> merchList = merchandiseService.getAll();
		return merchList;
	}

	//This method returns a page of merchandise available in the database. A successful response (HTTP 200)
	//contains a list of merchandise items in JSON format located at a certain page and sorted by either creating date or alphabetically. The response code 400 indicates invalid request parameters were provided.
	//The response code 500 indicates an unexpected error occurred on the server.
	@Override
	public List<Merchandise> getMerchandisePage(int page, String sortBy) {
		List<Merchandise> merchList = merchandiseService.getPage(page, sortBy);
		return merchList;
	}
	
	//This method fetches the merchandise associated with a specific event identified by eventId. It returns a ResponseEntity
	//with the list of Merchandise objects relevant to the event and an HTTP status of 200 (OK), indicating that
	//the requested merchandise data is successfully returned in JSON format.
	@Override
	public List<Merchandise> getEventMerchandise(Long eventId) {
		List<Merchandise> merchList = merchandiseService.getByEvent(eventId);
		return merchList;
	}

	//This method is used to add a new merchandise item to the database. It returns a ResponseEntity containing the newly created
	//Merchandise object, which includes the newly generated ID, and an HTTP status of 200 (OK). This means the merchandise has
	//been successfully created and is returned in JSON format.
	@Override
	public Merchandise create(Merchandise newMerch) {
		Merchandise merchWithId = merchandiseService.save(newMerch);
		return merchWithId;
	}

	//This method associates an existing merchandise item with a specified event. It returns a ResponseEntity containing the
	//updated Merchandise object reflecting the new event association, with an HTTP status of 200 (OK). This indicates that
	//the merchandise has been successfully linked to the event and is returned in JSON format.
	@Override
	public Merchandise addToEvent(String eventId, Long merchId) {
		Long parsedEventId = null;
			if(eventId != null && !eventId.equalsIgnoreCase("null")) {
			try{
				parsedEventId = Long.parseLong(eventId);
			}catch (NumberFormatException e) {
				throw e;
			}
		}
		Merchandise updatedMerch = merchandiseService.addEventToMerch(parsedEventId, merchId);
		return updatedMerch;
	}


}
