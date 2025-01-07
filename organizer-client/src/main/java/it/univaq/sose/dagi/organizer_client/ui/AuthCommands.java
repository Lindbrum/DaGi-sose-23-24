package it.univaq.sose.dagi.organizer_client.ui;

import java.util.Scanner;

import io.swagger.model.authentication_provider.Credentials;
import io.swagger.model.authentication_provider.Organizer;
import it.univaq.sose.dagi.organizer_client.client.AuthenticationRESTClient;

public class AuthCommands {

	public static Long SignUpOrganizer(Scanner scanner) {
		Organizer organizer = new Organizer();
		System.out.println();
		System.out.print("Insert your username: ");
		organizer.setUsername(scanner.nextLine());
		System.out.print("Insert your password: ");
		organizer.setPassword(scanner.nextLine());
		System.out.print("Insert your name: ");
		organizer.setName(scanner.nextLine());
		// Register the new customer
		Long id = AuthenticationRESTClient.getInstance().signupOrganizer(organizer);
		// If null is returned, the username was already taken
		if (id == null) {
			System.out.print("");
			System.out.print("ERROR: The username is already taken. Please try with a new username.");
		}
		return id;
	}

	public static Long SignInOrganizer(Scanner scanner) {
		Credentials credentials = new Credentials();
		System.out.println();
		System.out.print("Insert your username: ");
		credentials.setUsername(scanner.nextLine());
		System.out.print("Insert your password: ");
		credentials.setPassword(scanner.nextLine());
		// Sign in the customer
		Long id = AuthenticationRESTClient.getInstance().signinOrganizer(credentials);
		// If null is returned, credentials were incorrect
		if (id == null) {
			System.out.print("");
			System.out.print("ERROR: The credentials were incorrect.");
		}
		return id;

	}
}
