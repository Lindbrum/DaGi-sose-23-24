package it.univaq.sose.dagi.customer_client.ui;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import io.swagger.model.authentication_provider.Credentials;
import io.swagger.model.authentication_provider.Customer;
import it.univaq.sose.dagi.customer_client.client.AuthenticationRESTClient;

public class AuthCommands {

	public static Long SignUpCustomer(Scanner scanner) {
		Customer customer = new Customer();
		System.out.println();
		System.out.print("Insert your username: ");
		customer.setUsername(scanner.nextLine());
		System.out.print("Insert your password: ");
		customer.setPassword(scanner.nextLine());
		System.out.print("Insert your name: ");
		customer.setName(scanner.nextLine());
		System.out.print("Insert your surname: ");
		customer.setSurname(scanner.nextLine());
		boolean ok = false;
		int age = 0;
		while (!ok) {
			System.out.print("Insert your age: ");
			try {
				age = scanner.nextInt();
				scanner.nextLine();
				System.out.println(age);
				if (age < 0) {
					continue;
				}
				customer.setAge(age);
				ok = true;
			} catch (InputMismatchException e) {
				scanner.next();
				continue;
			} catch (NoSuchElementException e) {
				scanner.next();
				continue;
			}
		}
		ok = false;
		String gender = null;
		while (!ok) {
			System.out.println("What's your gender? ");
			System.out.println("1)male\n2)female\n3)other\n4)i don't want to declare it");
			try {
				int genderSelected = scanner.nextInt();
				scanner.nextLine();
				switch (genderSelected) {
				case 1: {
					gender = "male";
					break;
				}
				case 2: {
					gender = "female";
					break;
				}
				case 3: {
					gender = "other";
					break;
				}
				case 4: {
					gender = "not declared";
					break;
				}
				default: {
					continue;
				}
				}
				customer.setGender(gender);
				ok = true;
			} catch (NoSuchElementException e) {
				continue;
			}
		}
		// Register the new customer
		Long id = AuthenticationRESTClient.getInstance().signupCustomer(customer);
		// If null is returned, the username was already taken
		if (id == null) {
			System.out.println("");
			System.out.print("ERROR: The username is already taken. Please try with a new username.");
		}
		return id;
	}

	public static Long SignInCustomer(Scanner scanner) {
		Credentials credentials = new Credentials();
		System.out.println();
		System.out.print("Insert your username: ");
		credentials.setUsername(scanner.nextLine());
		System.out.print("Insert your password: ");
		credentials.setPassword(scanner.nextLine());
		// Sign in the customer
		Long id = AuthenticationRESTClient.getInstance().signinCustomer(credentials);
		// If null is returned, credentials were incorrect
		if (id == null) {
			System.out.println("");
			System.out.print("ERROR: The credentials were incorrect.");
		}
		return id;

	}
}
