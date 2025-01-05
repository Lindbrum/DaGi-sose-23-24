package it.univaq.sose.dagi.organizer_client;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Scanner;

import it.univaq.sose.dagi.organizer_client.ui.AuthCommands;
import it.univaq.sose.dagi.organizer_client.ui.EventCommands;
import it.univaq.sose.dagi.organizer_client.ui.MerchCommands;
import it.univaq.sose.dagi.organizer_client.ui.ReportCommands;

public class OrganizerClientApplication {

	private static Long organizerId = 0L;
	private static Scanner scanner = new Scanner(System.in); // Single Scanner instance
	
	public static void main(String[] args) {
		while (true) {
			welcomeAndAuthentication();
			homePage();
		}
	}
	
	public static Long getOrganizerId() {
		return organizerId;
	}

	private static void welcomeAndAuthentication() {
		System.out.println(LocalDateTime.now().toString());
		System.out.println("\n\nWelcome, organizer, to the event exploration and ticket reservation platform!");
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
					organizerId = AuthCommands.SignInOrganizer(scanner);
					break;

				case 2:
					organizerId = AuthCommands.SignUpOrganizer(scanner);
					break;

				case 3:
					System.out.println("Goodbye!");
					System.exit(0);

				default:
					continue;

				}
				//Check if we authenticated successfully (null is assigned on failure)
				if(organizerId != null) {
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
			System.out.println("\n\n============HOME PAGE============");
			System.out.println("What do you want to do?");
			System.out.println("1) Create an event");
			System.out.println("2) List a new merchandise article");
			System.out.println("3) Manage merchandise");
			System.out.println("4) View reports");
			System.out.println("5) Log out");
			boolean ok = false;
			while (!ok) {
				System.out.print("Your answer: ");
				try {
					int command = scanner.nextInt();
					scanner.nextLine();
					switch (command) {
					case 1:
						EventCommands.createEvent(scanner);
						break;
					case 2:
						MerchCommands.createMerch(scanner);
						break;
					
					case 3:
						MerchCommands.merchCatalogue(scanner);
						break;

					case 4:
						ReportCommands.organizerEventCatalogue(scanner);
						break;

					case 5:
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
