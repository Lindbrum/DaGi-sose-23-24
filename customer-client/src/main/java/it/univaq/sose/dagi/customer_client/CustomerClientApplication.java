package it.univaq.sose.dagi.customer_client;

import java.util.NoSuchElementException;
import java.util.Scanner;

import it.univaq.sose.dagi.customer_client.ui.AuthCommands;
import it.univaq.sose.dagi.customer_client.ui.CatalogueCommands;
import it.univaq.sose.dagi.customer_client.ui.TicketHistoryCommands;

public class CustomerClientApplication {

	private static Long customerId = 0L;
	private static Scanner scanner = new Scanner(System.in); // Single Scanner instance

	public static void main(String[] args) {
		while (true) {
			welcomeAndAuthentication();
			homePage();
		}
	}

	public static long getCustomerId() {
		return customerId;
	}

	private static void welcomeAndAuthentication() {

		System.out.println("\n\nWelcome to the event exploration and ticket reservation platform!");
		while (true) {
			System.out.println("What do you want to do?");
			System.out.println("1) Sign-in");
			System.out.println("2) Sign-up");
			System.out.println("3) Exit the application");
			System.out.print("Your answer: ");
			try {
				int command = scanner.nextInt();
				scanner.nextLine(); // throw away the \n
				switch (command) {
				case 1:
					customerId = AuthCommands.SignInCustomer(scanner);
					break;

				case 2:
					customerId = AuthCommands.SignUpCustomer(scanner);
					break;

				case 3:
					System.out.println("Goodbye!");
					System.exit(0);

				default:
					continue;

				}
				//Check if we authenticated successfully (null is assigned on failure)
				if(customerId != null) {
					return; //proceed to home page
				}
			} catch (NoSuchElementException e) {
				scanner.nextLine();
				continue;
			}
		}
	}

	private static void homePage() {
		while (true) {
			System.out.println("\n\n===Home page===");
			System.out.println("What do you want to do?");
			System.out.println("1) Explore the event catalogue");
			System.out.println("2) Check your tickets");
			System.out.println("3) Log out");
			boolean ok = false;
			while (!ok) {
				System.out.print("Your answer: ");
				try {
					int command = scanner.nextInt();
					scanner.nextLine();
					switch (command) {
					case 1:
						CatalogueCommands.eventCatalogue(scanner);
						break;

					case 2:
						TicketHistoryCommands.showTicketHistory(scanner);
						break;

					case 3:
						System.out.println("\n\nSuccessfully logged out.\n\n");
						return; //Return to welcome page

					default:
						continue;

					}
					ok = true;
				} catch (NoSuchElementException e) {
					scanner.nextLine();
					continue;
				}
			}
		}
	}
}
