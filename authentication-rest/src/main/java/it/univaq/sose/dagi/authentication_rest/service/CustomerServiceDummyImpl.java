package it.univaq.sose.dagi.authentication_rest.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import it.univaq.sose.dagi.authentication_rest.model.Customer;

public class CustomerServiceDummyImpl implements CustomerService{

	private List<Customer> customerList = new ArrayList<Customer>();
	private static Long nextId = 0L;
	
	@Override
	public Long save(Customer customer) {
		Long id = nextId++;
		customer.setId(id);
		customerList.add(customer);
		return id;
	}

	@Override
	public Customer lookup(String username) {
		Customer found = null;
		for(Customer c: customerList) {
			if(c.getUsername().equals(username)) {
				found = c;
				break;
			}
		}
		return found;
	}

	@Override
	public Customer findById(long id) throws NoSuchElementException{
		for(Customer c: customerList) {
			if(c.getId() == id) {
				return c;
			}
		}
		throw new NoSuchElementException("User not found.");
	}

	@Override
	public List<Customer> findMultipleById(long[] ids) {
		List<Customer> result = new ArrayList<>();
		for(Customer c: customerList) {
			for(long id : ids) {
				if(c.getId() == id) {
					result.add(c);
					break;
				}
			}
		}
		return result;
	}
	
	

}
