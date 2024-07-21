package it.univaq.sose.dagi.merchandising_rest.service;

import java.util.List;
import java.util.NoSuchElementException;

import it.univaq.sose.dagi.merchandising_rest.model.Merchandise;

public interface MerchandiseService {
	/**
	 * return all merchandise in database
	 * @return a list of merchandise
	 */
	List<Merchandise> getAll();
	/**
	 * return the merchandise for an event.
	 * @param eventId the event ID.
	 * @return a list of merchandise.
	 */
	List<Merchandise> getByEvent(long eventId);
	
	/**
	 * Save a new merchandise article in the database.
	 * @param newMerch the merchandise object to save.
	 * @return the merchandise object with the newly generated ID.
	 */
	Merchandise save(Merchandise newMerch);
	
	/**
	 * Update a merchandise article in the database.
	 * @param updatedMerch the udpated merchandise object.
	 * @return the updated merchandise object.
	 * @throws IllegalArgumentException if the ID is missing.
	 * @throws NoSuchElementException if the ID has no match in the database.
	 * 
	 */
	Merchandise update(Merchandise updatedMerch) throws IllegalArgumentException, NoSuchElementException;
	
	/**
	 * 
	 * @param eventId the id of the event to associate
	 * @param merchId the id of the merchandise article to associate
	 * @return the updated merchandise object from the database
	 * @throws IllegalArgumentException if either argument is null
	 * @throws NoSuchElementException if the merch article cannot be found
	 */
	Merchandise addEventToMerch(Long eventId, Long merchId) throws IllegalArgumentException, NoSuchElementException;
}
