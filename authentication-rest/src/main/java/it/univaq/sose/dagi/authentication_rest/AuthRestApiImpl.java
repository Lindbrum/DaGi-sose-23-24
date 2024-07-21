package it.univaq.sose.dagi.authentication_rest;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import it.univaq.sose.dagi.authentication_rest.model.Credentials;
import it.univaq.sose.dagi.authentication_rest.model.Customer;
import it.univaq.sose.dagi.authentication_rest.model.ExceptionData;
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
	public ResponseEntity<Long> signUpCustomer(Customer customer) {
		Long id = customerService.save(customer);
		return new ResponseEntity<>(id, HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<Long> signUpOrganizer(Organizer organizer) {
		Long id = organizerService.save(organizer);
		return new ResponseEntity<>(id, HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<Long> signInCustomer(Credentials credentials) {
		Customer customer = customerService.lookup(credentials.getUsername());
		if (customer != null && customer.getPassword().equals(credentials.getPassword())) {
			return new ResponseEntity<>(customer.getId(), HttpStatus.OK);
		}
		return null;
	}

	@Override
	public ResponseEntity<Long> signInOrganizer(Credentials credentials) {
		Organizer organizer = organizerService.lookup(credentials.getUsername());
		if (organizer != null && organizer.getPassword().equals(credentials.getPassword())) {
			return new ResponseEntity<>(organizer.getId(), HttpStatus.OK);
		}
		return null;
	}

	@Override
	public ResponseEntity<?> fetchCustomerInfo(long userId) {
		try {
			Customer customer = customerService.findById(userId);
			cleanSensitiveInfo(customer);
			return new ResponseEntity<>(customer, HttpStatus.OK);
		} catch (NoSuchElementException e) {
			e.printStackTrace();
			return new ResponseEntity<>(
					new ExceptionData(HttpStatus.OK, HttpStatus.OK.value(), e.getLocalizedMessage()), HttpStatus.OK);
		}
	}

	@Override
	public ResponseEntity<List<Customer>> fetchCustomerInfoList(String userIds) {
		
		// Convert the array of strings into an array of longs
		String[] userIdArray = userIds.split(",");
		long[] userIdsLongArray = Arrays.stream(userIdArray).mapToLong(Long::parseLong).toArray();
		List<Customer> customers = customerService.findMultipleById(userIdsLongArray);
		customers.forEach(customer -> {
			cleanSensitiveInfo(customer);
		});
		return new ResponseEntity<>(customers, HttpStatus.OK);
	}

	private void cleanSensitiveInfo(Customer customer) {
		customer.setUsername("HIDDEN");
		customer.setPassword("HIDDEN");
		customer.setName("HIDDEN");
		customer.setSurname("HIDDEN");
	}

}
