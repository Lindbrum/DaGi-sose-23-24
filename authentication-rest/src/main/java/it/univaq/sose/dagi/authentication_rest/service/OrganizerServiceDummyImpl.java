package it.univaq.sose.dagi.authentication_rest.service;

import java.util.ArrayList;
import java.util.List;

import it.univaq.sose.dagi.authentication_rest.model.Organizer;

public class OrganizerServiceDummyImpl implements OrganizerService {

	private List<Organizer> organizerList = new ArrayList<>();
	private static Long nextId = 0L;
	
	public OrganizerServiceDummyImpl() {
		
		Organizer o0 = new Organizer(nextId++, "admin", "admin", "Administrator");
		Organizer o1 = new Organizer(nextId++, "assoret", "amammt", "Hit It Up");
		Organizer o2 = new Organizer(nextId++, "asso", "ama", "InnTales");
		
		organizerList.add(o0);
		organizerList.add(o1);
		organizerList.add(o2);
	}
	
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
