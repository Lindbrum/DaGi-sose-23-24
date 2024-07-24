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

	public AuthRestApiImpl() {
		this.customerService = new CustomerServiceDummyImpl();
		this.organizerService = new OrganizerServiceDummyImpl();
	}

	@Override
	public Long signUpCustomer(Customer customer) {
		Long id = customerService.save(customer);
		return id;
	}

	@Override
	public Long signUpOrganizer(Organizer organizer) {
		Long id = organizerService.save(organizer);
		return id;
	}

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

	private void cleanSensitiveInfo(Customer customer) {
		customer.setUsername("HIDDEN");
		customer.setPassword("HIDDEN");
		customer.setName("HIDDEN");
		customer.setSurname("HIDDEN");
	}

}
