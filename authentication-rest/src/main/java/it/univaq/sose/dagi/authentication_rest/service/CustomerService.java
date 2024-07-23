package it.univaq.sose.dagi.authentication_rest.service;

import java.util.List;
import java.util.NoSuchElementException;

import it.univaq.sose.dagi.authentication_rest.model.Customer;

public interface CustomerService {
	
	//Saves a new customer and returns the created customer ID.
	public Long save (Customer customer); //signup
	//Searches for a customer by username and returns the corresponding Customer object.
	public Customer lookup (String username); //login
	//Retrieves a customer's information given their ID, throwing an exception if the customer is not found.
	public Customer findById(long id) throws NoSuchElementException; //customer info
	//Retrieves information for a list of customers given their IDs.
	public List<Customer> findMultipleById(long[] ids); //list of customers info
	
}
