package it.univaq.sose.dagi.authentication_rest.service;

import java.util.ArrayList;
import java.util.List;

import it.univaq.sose.dagi.authentication_rest.model.Organizer;

public class OrganizerServiceDummyImpl implements OrganizerService {

	private List<Organizer> organizerList = new ArrayList<Organizer>();
	private static Long nextId = 0L;
	
	@Override
	public Long save(Organizer organizer) {
		Long id = nextId++;
		organizer.setId(id);
		organizerList.add(organizer);
		return id;
	}

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
