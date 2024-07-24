package it.univaq.sose.dagi.authentication_rest.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import it.univaq.sose.dagi.authentication_rest.model.Customer;

public class CustomerServiceDummyImpl implements CustomerService{

	private List<Customer> customerList = new ArrayList<>();
	private static Long nextId = 0L;
	
	//The constructor initializes the service by creating a default customer list.
	//Each customer is instantiated with a unique ID, username, password, first name, last name, age and gender.
	//The nextId variable is incremented to ensure that each customer has a unique ID.
	public CustomerServiceDummyImpl() {
		Customer c0 = new Customer(nextId++, "lindbrum", "abc123", "Dario", "D'Ercole", 28, "male");
		Customer c1 = new Customer(nextId++, "giospaz", "def456", "Giovanni", "Spaziani", 25, "male");
		Customer c2 = new Customer(nextId++, "bacco", "sanguemisto", "Loris", "D'Ercole", 26, "male");
		Customer c3 = new Customer(nextId++, "londibelodi", "aueguardlu", "Linda", "D'Ercole", 24, "female");
		Customer c4 = new Customer(nextId++, "smari67", "mammapia", "Marina", "Stefanucci", 57, "female");
		
		customerList.add(c0);
		customerList.add(c1);
		customerList.add(c2);
		customerList.add(c3);
		customerList.add(c4);
	}
	
	//This method saves a new customer. A unique ID is assigned to the customer, the ID is incremented, and the customer
	//is added to the customerList. The method returns the newly created customer ID.
	@Override
	public Long save(Customer customer) {
		Long id = nextId++;
		customer.setId(id);
		customerList.add(customer);
		return id;
	}

	//This method searches for a customer by username. It iterates through the customerList and, if it finds a customer
	//with the matching username, returns the found Customer object. If no customers are found, it returns null.
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

	//This method retrieves a customer's information given their ID. It iterates through the customerList and, if it finds a customer
	//with the matching ID, returns the found Customer object. If it doesn't find any customers, it throws a NoSuchElementException with the message "User not found.".
	@Override
	public Customer findById(long id) throws NoSuchElementException{
		for(Customer c: customerList) {
			if(c.getId() == id) {
				return c;
			}
		}
		throw new NoSuchElementException("User not found.");
	}

	//This method retrieves the information of a list of customers given their IDs. Creates an empty result list and iterates
	//through the customerList. For each customer, check if the ID is present in the ids array. If it finds a match, it
	//adds the customer to the result list. Returns the result list containing the found customers.
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
