package it.univaq.sose.dagi.authentication_rest.service;

import it.univaq.sose.dagi.authentication_rest.model.Organizer;

public interface OrganizerService {

	public Long save (Organizer organizer); //signup
	public Organizer lookup (String username); //login
	
}
