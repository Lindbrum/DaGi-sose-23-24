package it.univaq.sose.dagi.organizer_client.ui;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import it.univaq.sose.dagi.organizer_client.OrganizerClientApplication;
import it.univaq.sose.dagi.organizer_client.client.SOAPProxyRESTClient;

public class EventCommands {

	private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy kk:mm");

	public static void createEvent(Scanner scanner) {
		System.out.println("");
		System.out.println("===========CREATE EVENT===========");
		// Step 1: Collect event data
		String name;
		String description;
		String location;
		LocalDateTime startDate = null;
		LocalDateTime endDate = null;
		int nrTickets = 0;

		System.out.println("");
		System.out.print("Name of the event: ");
		name = scanner.nextLine();

		System.out.println("");
		System.out.println("A short description of the event:");
		description = scanner.nextLine();

		System.out.println("");
		System.out.print("Location of the event: ");
		location = scanner.nextLine();

		while (true) {
			System.out.println("");
			System.out.print("How many tickets to sell: ");
			try {
				nrTickets = scanner.nextInt();
				scanner.nextLine();
				break;
			} catch (NoSuchElementException e) {
				scanner.nextLine();
				continue;
			}
		}

		// Keep trying until date can be parsed.
		boolean isDone = false;
		String dateString;
		while (!isDone) {
			while (true) {
				System.out.println("");
				System.out.print("Start date and time of the event (accepted format 'dd/mm/yyyy hh:mm'): ");
				try {
					dateString = scanner.nextLine();
					startDate = LocalDateTime.parse(dateString, DATE_FORMAT);
					break;
				} catch (DateTimeParseException e) {
					System.out.println("");
					System.out.println("Wrong date format, try again.");
				}
			}
			while (true) {
				System.out.println("");
				System.out.print("End date and time of the event (accepted format 'dd/mm/yyyy hh:mm'): ");
				try {
					dateString = scanner.nextLine();
					endDate = LocalDateTime.parse(dateString, DATE_FORMAT);
					break;
				} catch (DateTimeParseException e) {
					System.out.println("");
					System.out.println("Wrong date format, try again.");
				}
			}

			if (startDate.isBefore(endDate)) {
				isDone = true;
			} else {
				System.out.println("");
				System.out.println("An error has occured: the starting date has to be before the ending date");
			}
		}

		// Step 2: Collect tickets info
		int dates = 0;
		while (true) {
			System.out.print("");
			System.out.print("How many separate dates will you sell tickets for? ");
			try {
				dates = scanner.nextInt();
				scanner.nextLine();
				if (dates >= 1) {
					break;
				}
			} catch (NoSuchElementException e) {
				scanner.nextLine();
				continue;
			}
		}
		int[] availabilities = new int[dates];
		LocalDateTime[] referenceDates = new LocalDateTime[dates];
		if (dates >= 2) {
			for (int i = 0; i < dates; i++) {
				while (true) {
					System.out.print("");
					System.out
							.print(String.format("%d° reference date (accepted format 'dd/mm/yyyy hh:mm'): ", i + 1));
					try {
						dateString = scanner.nextLine();
						referenceDates[i] = LocalDateTime.parse(dateString, DATE_FORMAT);
						break;
					} catch (DateTimeParseException e) {
						System.out.println("");
						System.out.println("Wrong date format, try again.");
					}
				}
				while (true) {
					System.out.println("");
					System.out.print(String.format("How many tickets to sell in the %d° reference date? ", i + 1));
					;
					try {
						availabilities[i] = scanner.nextInt();
						scanner.nextLine();
						break;
					} catch (NoSuchElementException e) {
						scanner.nextLine();
						continue;
					}
				}
			}
		}else { //dates < 2
			referenceDates[0] = startDate;
			availabilities[0] = nrTickets;
		}
		
		// Step 3: save everything on the services
		long eventDbId;
		eventDbId = SOAPProxyRESTClient.getInstance().createEvent(name, description,
				OrganizerClientApplication.getOrganizerId(), location, startDate, endDate, nrTickets);
		long[] ticketsIds = new long[dates];
		for (int i = 0; i < dates; i++) {
			ticketsIds[i] = SOAPProxyRESTClient.getInstance().createTicketInfo(eventDbId, referenceDates[i],
					availabilities[i]);
		}
		System.out.println("");
		System.out.println("=================================================================================");
		System.out.println("Event has been successfully created, along with all the tickets availabilities");
		System.out.println("=================================================================================");
	}
}
