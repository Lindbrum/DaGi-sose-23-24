package it.univaq.sose.dagi.authentication_rest.service;

import org.springframework.stereotype.Service;

import it.univaq.sose.dagi.authentication_rest.dao.OrganizerRepository;
import it.univaq.sose.dagi.authentication_rest.model.Organizer;

@Service
public class OrganizerServiceImpl implements OrganizerService {

	private final OrganizerRepository organizerRepository;
	
	
	
	public OrganizerServiceImpl(OrganizerRepository organizerRepository) {
		this.organizerRepository = organizerRepository;
	}

	@Override
	public Long save(Organizer organizer) {
		return this.organizerRepository.save(organizer).getId();
	}

	@Override
	public Organizer lookup(String username) {
		return this.organizerRepository.findByUsername(username).get();
	}

}
