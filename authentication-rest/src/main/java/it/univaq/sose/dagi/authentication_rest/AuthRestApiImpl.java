package it.univaq.sose.dagi.authentication_rest;

import it.univaq.sose.dagi.authentication_rest.model.Credentials;
import it.univaq.sose.dagi.authentication_rest.model.Customer;
import it.univaq.sose.dagi.authentication_rest.model.Organizer;
import it.univaq.sose.dagi.authentication_rest.service.CustomerService;
import it.univaq.sose.dagi.authentication_rest.service.OrganizerService;

public class AuthRestApiImpl implements AuthRestApi{

	private CustomerService customerService;
	private OrganizerService organizerService;
	
	public AuthRestApiImpl(CustomerService customerService, OrganizerService organizerService) {
		super();
		this.customerService = customerService;
		this.organizerService = organizerService;
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
		if(customer != null && customer.getPassword().equals(credentials.getPassword())) {
			return customer.getId();
		}
		return null;
	}

	@Override
	public Long signInOrganizer(Credentials credentials) {
		Organizer organizer = organizerService.lookup(credentials.getUsername());
		if(organizer != null && organizer.getPassword().equals(credentials.getPassword())) {
			return organizer.getId();
		}
		return null;
	}

}
