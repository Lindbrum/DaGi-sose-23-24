package it.univaq.sose.dagi.authentication_rest.service;

import java.util.ArrayList;
import java.util.List;

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

}
