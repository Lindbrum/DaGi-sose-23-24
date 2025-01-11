package it.univaq.sose.dagi.merchandising_rest.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.ListCrudRepository;

import it.univaq.sose.dagi.merchandising_rest.model.Merchandise;

//This is an interface that extends ListCrudRepository<Merchandise, Long>. It represents a repository
//for managing Merchandise entities with a primary key of type Long.
public interface MerchandiseRepository extends ListCrudRepository<Merchandise, Long> {
	//Find all merchandises for an event
	List<Merchandise> findAllByEventId(Long eventId);
	//Return a page of merchandise with the provided sorting method
	Page<Merchandise> findAll(Pageable pageable);
}
