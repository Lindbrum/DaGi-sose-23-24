/**
 * 
 */
package it.univaq.sose.dagi.authentication_rest.dao;

import org.springframework.data.repository.ListCrudRepository;

import it.univaq.sose.dagi.authentication_rest.model.Customer;
import java.util.List;
import java.util.Optional;


/**
 * 
 */
public interface CustomerRepository extends ListCrudRepository<Customer, Long> {

	Optional<Customer> findByUsername(String username);
	List<Customer> findAllByIdIn(long[] ids);
}
