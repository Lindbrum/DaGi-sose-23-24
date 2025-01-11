package it.univaq.sose.dagi.authentication_rest.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.stereotype.Service;

import it.univaq.sose.dagi.authentication_rest.dao.CustomerRepository;
import it.univaq.sose.dagi.authentication_rest.model.Customer;

@Service
public class CustomerServiceImpl implements CustomerService {

	private final CustomerRepository customerRepository;
	
	public CustomerServiceImpl(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}
	
	@Override
	public Long save(Customer customer) {
		
		return this.customerRepository.save(customer).getId();
	}

	@Override
	public Customer lookup(String username) {
		
		return this.customerRepository.findByUsername(username).get();
	}

	@Override
	public Customer findById(long id) throws NoSuchElementException {
		
		Optional<Customer> found = this.customerRepository.findById(id);
		if(found.isPresent()) {			
			return found.get();
		}else {
			throw new NoSuchElementException("User not found.");
		}
	}

	@Override
	public List<Customer> findMultipleById(long[] ids) {
		return this.customerRepository.findAllByIdIn(ids);
	}

}
