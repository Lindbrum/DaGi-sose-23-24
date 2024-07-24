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
		System.out.println("\n\n===========CREATE EVENT===========");
		//Step 1: Collect event data
		String name;
		String description;
		String location;
		LocalDateTime startDate;
		LocalDateTime endDate;
		int nrTickets = 0;
		
		System.out.print("\nName of the event: ");
		name = scanner.nextLine();

		System.out.println("\nA short description of the event:");
		description = scanner.nextLine();
		
		System.out.print("\nLocation of the event: ");
		location = scanner.nextLine();
		
		while(true) {			
			System.out.print("\nHow many tickets to sell: ");
			try{
				nrTickets = scanner.nextInt();
				scanner.nextLine();
				break;
			}catch(NoSuchElementException e) {
				scanner.nextLine();
				continue;
			}
		}
		
		//Keep trying until date can be parsed.
		String dateString;
		while(true) {			
			System.out.print("\nStart date and time of the event (accepted format 'dd/mm/yyyy hh:mm'): ");
			try {
				dateString = scanner.nextLine();
				startDate = LocalDateTime.parse(dateString, DATE_FORMAT);
				break;
			}catch (DateTimeParseException e) {
				System.out.println("\nWrong date format, try again.");
			}
		}
		while(true) {			
			System.out.print("\nEnd date and time of the event (accepted format 'dd/mm/yyyy hh:mm'): ");
			try {
				dateString = scanner.nextLine();
				endDate = LocalDateTime.parse(dateString, DATE_FORMAT);
				break;
			}catch (DateTimeParseException e) {
				System.out.println("\nWrong date format, try again.");
			}
		}
		
		
		//Step 2: Collect tickets info
		int dates = 0;
		while(true) {			
			System.out.print("\nHow many separate dates will you sell tickets for? ");
			try{
				dates = scanner.nextInt();
				scanner.nextLine();
				if(dates >= 1) {
					break;
				}
			}catch(NoSuchElementException e) {
				scanner.nextLine();
				continue;
			}
		}
		int[] availabilities = new int[dates];
		LocalDateTime[] referenceDates = new LocalDateTime[dates];
		for(int i = 0; i < dates; i++) {
			while(true) {			
				System.out.print(String.format("\n%d° reference date (accepted format 'dd/mm/yyyy hh:mm'): ", i+1));
				try {
					dateString = scanner.nextLine();
					referenceDates[i] = LocalDateTime.parse(dateString, DATE_FORMAT);
					break;
				}catch (DateTimeParseException e) {
					System.out.println("\nWrong date format, try again.");
				}
			}
			while(true) {			
				System.out.print(String.format("\nHow many tickets to sell in the %d° reference date? ", i+1));;
				try{
					availabilities[i] = scanner.nextInt();
					scanner.nextLine();
					break;
				}catch(NoSuchElementException e) {
					scanner.nextLine();
					continue;
				}
			}
		}
		//Step 3: save everything on the services
		long eventDbId;
		eventDbId = SOAPProxyRESTClient.getInstance().createEvent(name, description, OrganizerClientApplication.getOrganizerId(), location, startDate, endDate, nrTickets);
		long[] ticketsIds = new long[dates];
		for(int i = 0; i < dates; i++) {
			ticketsIds[i] = SOAPProxyRESTClient.getInstance().createTicketInfo(eventDbId, referenceDates[i], availabilities[i]);
		}
		System.out.println("\n=================================================================================");
		System.out.println("Event has been successfully created, along with all the tickets availabilities");
		System.out.println("=================================================================================");
	}
}
