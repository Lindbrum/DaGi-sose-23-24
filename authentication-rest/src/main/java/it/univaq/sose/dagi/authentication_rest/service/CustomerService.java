package it.univaq.sose.dagi.authentication_rest.service;

import it.univaq.sose.dagi.authentication_rest.model.Customer;

public interface CustomerService {
	
	public Long save (Customer customer); //signup
	public Customer lookup (String username); //login
	
}
