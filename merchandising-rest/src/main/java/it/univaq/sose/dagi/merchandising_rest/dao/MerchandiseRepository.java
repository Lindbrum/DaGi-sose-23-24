package it.univaq.sose.dagi.merchandising_rest.dao;

import org.springframework.data.repository.ListCrudRepository;

import it.univaq.sose.dagi.merchandising_rest.model.Merchandise;

//This is an interface that extends ListCrudRepository<Merchandise, Long>. It represents a repository
//for managing Merchandise entities with a primary key of type Long.
public interface MerchandiseRepository extends ListCrudRepository<Merchandise, Long> {

}
