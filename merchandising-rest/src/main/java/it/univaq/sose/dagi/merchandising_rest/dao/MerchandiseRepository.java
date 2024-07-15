package it.univaq.sose.dagi.merchandising_rest.dao;

import org.springframework.data.repository.ListCrudRepository;

import it.univaq.sose.dagi.merchandising_rest.model.Merchandise;

public interface MerchandiseRepository extends ListCrudRepository<Merchandise, Long> {

}
