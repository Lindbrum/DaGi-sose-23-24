package it.univaq.sose.dagi.authentication_rest;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import it.univaq.sose.dagi.authentication_rest.model.Credentials;
import it.univaq.sose.dagi.authentication_rest.model.Customer;
import it.univaq.sose.dagi.authentication_rest.model.Organizer;
import it.univaq.sose.dagi.authentication_rest.service.CustomerService;
import it.univaq.sose.dagi.authentication_rest.service.CustomerServiceDummyImpl;
import it.univaq.sose.dagi.authentication_rest.service.OrganizerService;
import it.univaq.sose.dagi.authentication_rest.service.OrganizerServiceDummyImpl;

public class AuthRestApiImpl implements AuthRestApi {

	private CustomerService customerService;
	private OrganizerService organizerService;

	//The AuthRestApiImpl implementation constructor initializes the customerService and organizerService services with dummy implementations
	//(CustomerServiceDummyImpl and OrganizerServiceDummyImpl). These services are responsible for managing customer and organizer data.
	public AuthRestApiImpl() {
		this.customerService = new CustomerServiceDummyImpl();
		this.organizerService = new OrganizerServiceDummyImpl();
	}

	//This method handles a customer's registration. It calls the save method of the customerService service to save the customer
	//and returns a response entity containing the ID of the created customer and the HTTP status CREATED.
	@Override
	public Long signUpCustomer(Customer customer) {
		Long id = customerService.save(customer);
		return id;
	}

	//Similar to the previous method, this one handles registering an organizer. It calls the organizerService service's save method
	//and returns a response entity containing the ID of the created organizer and the HTTP status CREATED.
	@Override
	public Long signUpOrganizer(Organizer organizer) {
		Long id = organizerService.save(organizer);
		return id;
	}

	//This method handles a customer's login. It calls the lookup method of the customerService service to search for the customer by username.
	//If the customer is found and the password matches, it returns the customer ID and HTTP OK status. Otherwise, it returns null.
	@Override
	public Long signInCustomer(Credentials credentials) {
		Customer customer = customerService.lookup(credentials.getUsername());
		System.out.println(String.format("Credentials: %s, %s", credentials.getUsername(), credentials.getPassword()));
		System.out.println(customer != null);
		if(customer != null) {			
			System.out.println(String.format("Customer: %s, %s", customer.getUsername(), customer.getPassword()));
			System.out.println(customer.getPassword().equals(credentials.getPassword()));
		}
		if (customer != null && customer.getPassword().equals(credentials.getPassword())) {
			return customer.getId();
		}
		return null;
	}

	//Similar to the previous method, this one handles an organizer's login. It calls the organizerService service's lookup method to search for the organizer by username.
	//If the organizer is found and the password matches, it returns the organizer ID and HTTP status OK. Otherwise, it returns null.
	@Override
	public Long signInOrganizer(Credentials credentials) {
		Organizer organizer = organizerService.lookup(credentials.getUsername());
		if (organizer != null && organizer.getPassword().equals(credentials.getPassword())) {
			System.out.print("DENTRO");
			return organizer.getId();
		}
		System.err.print("FUUUUUUUUUUUUUUUUUUUUUUUUUORI");
		return null;
	}

	//This method retrieves a customer's information given their ID. It calls the customerService service's findById method to find the customer.
	//If the client is found, it removes sensitive information by calling cleanSensitiveInfo and returns the client and HTTP status OK.
	//If the customer is not found, it catches the NoSuchElementException exception and returns a response entity with the exception details.
	@Override
	public Customer fetchCustomerInfo(long userId) {
		try {
			Customer customer = customerService.findById(userId);
			cleanSensitiveInfo(customer);
			return customer;
		} catch (NoSuchElementException e) {
			e.printStackTrace();
			return null;
		}
	}

	//This method is similar to the previous, but in this case it retrieves information for a list of customers given a string of comma-separated IDs.
	//It converts the ID string to an array of longs and calls the findMultipleById method of the customerService service to find the customers.
	//Removes sensitive information from each client by calling cleanSensitiveInfo and returns the list of clients and HTTP OK status.
	@Override
	public List<Customer> fetchCustomerInfoList(String userIds) {
		
		// Convert the array of strings into an array of longs
		String[] userIdArray = userIds.split(",");
		long[] userIdsLongArray = Arrays.stream(userIdArray).mapToLong(Long::parseLong).toArray();
		List<Customer> customers = customerService.findMultipleById(userIdsLongArray);
		customers.forEach(customer -> {
			cleanSensitiveInfo(customer);
		});
		return customers;
	}

	//This private method removes sensitive information from a Customer object
	//by setting the username, password, name, and surname fields to "HIDDEN".
	private void cleanSensitiveInfo(Customer customer) {
		customer.setUsername("HIDDEN");
		customer.setPassword("HIDDEN");
		customer.setName("HIDDEN");
		customer.setSurname("HIDDEN");
	}

}
