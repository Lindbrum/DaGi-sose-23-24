package it.univaq.sose.dagi.authentication_rest.service;

import it.univaq.sose.dagi.authentication_rest.model.Organizer;

public interface OrganizerService {

	//This method generates a new ID for the organizer, assigns it to the organizer, adds the organizer to the list, and returns the ID.
	public Long save (Organizer organizer); //signup
	//This method searches the list of organizers for an organizer with the specified username and, if found, returns it.
	public Organizer lookup (String username); //login
	
}
