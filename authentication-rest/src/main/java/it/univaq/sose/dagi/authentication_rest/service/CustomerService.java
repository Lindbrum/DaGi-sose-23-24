package it.univaq.sose.dagi.authentication_rest.service;

import java.util.List;
import java.util.NoSuchElementException;

import it.univaq.sose.dagi.authentication_rest.model.Customer;

public interface CustomerService {
	
	public Long save (Customer customer); //signup
	public Customer lookup (String username); //login
	public Customer findById(long id) throws NoSuchElementException; //customer info
	public List<Customer> findMultipleById(long[] ids); //list of customers info
	
}
