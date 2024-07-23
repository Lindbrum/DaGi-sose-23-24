package it.univaq.sose.dagi.authentication_rest.service;

import java.util.List;

import it.univaq.sose.dagi.authentication_rest.model.Organizer;

public class OrganizerServiceDummyImpl implements OrganizerService {

	private List<Organizer> organizerList;
	private static Long nextId = 0L;
	
	public OrganizerServiceDummyImpl() {
		//The constructor creates an initial list of organizers.
		//Each organizer is instantiated with a unique ID (incremented by the nextId variable),a username, a password, and a name.
		Organizer o0 = new Organizer(nextId++, "admin", "admin", "Administrator");
		Organizer o1 = new Organizer(nextId++, "assoret", "amammt", "Hit It Up");
		Organizer o2 = new Organizer(nextId++, "asso", "ama", "InnTales");
		organizerList = List.of(o0,o1,o2);
	}
	
	//This method saves a new organizer:
	//Generate a new ID by incrementing nextId.
	//Assign the ID to the organizer.
	//Adds the organizer to the organizerList list.
	//Returns the new organizer's ID.
	@Override
	public Long save(Organizer organizer) {
		Long id = nextId++;
		organizer.setId(id);
		organizerList.add(organizer);
		return id;
	}

	//This method searches for a host by username:
	//Initialize the found variable to null.
	//Scrolls through the organizerList.
	//If it finds an organizer with the matching username, it assigns that organizer to found and breaks the loop.
	//Returns the organizer found or null if no match is found.
	@Override
	public Organizer lookup(String username) {
		Organizer found = null;
		for(Organizer o: organizerList) {
			if(o.getUsername().equals(username)) {
				found = o;
				break;
			}
		}
		return found;
	}
	
}
