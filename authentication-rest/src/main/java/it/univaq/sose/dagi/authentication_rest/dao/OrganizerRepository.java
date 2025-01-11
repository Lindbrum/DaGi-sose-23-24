package it.univaq.sose.dagi.authentication_rest.dao;

import java.util.Optional;

import org.springframework.data.repository.ListCrudRepository;

import it.univaq.sose.dagi.authentication_rest.model.Organizer;

public interface OrganizerRepository extends ListCrudRepository<Organizer, Long> {
	
	Optional<Organizer> findByUsername(String username);
}
