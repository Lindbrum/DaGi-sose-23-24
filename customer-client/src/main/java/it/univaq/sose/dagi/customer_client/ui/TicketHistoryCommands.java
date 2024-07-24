package it.univaq.sose.dagi.customer_client.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import io.swagger.model.soap_proxy.CustomerHistory;
import io.swagger.model.soap_proxy.Event;
import io.swagger.model.soap_proxy.SoldTicket;
import it.univaq.sose.dagi.customer_client.CustomerClientApplication;
import it.univaq.sose.dagi.customer_client.client.SOAPProxyRESTClient;

public class TicketHistoryCommands {
	
	public static void showTicketHistory(Scanner scanner) {
		//Fetch the data
		CustomerHistory data;
		data = SOAPProxyRESTClient.getInstance().fetchCustomerTicketHistory(CustomerClientApplication.getCustomerId());
		List<SoldTicket> tickets = data.getBoughtTickets();
		List<Event> events = data.getTicketRelatedEvents();
		List<String> historyRows = new ArrayList<>(tickets.size()); //used to display in option menù the selected row
		for(Event e : events) {
			System.out.print(e.getId()+",");
		}
		System.out.println();
		
		//Print the menù
		while(true) {
			//Print the data
			System.out.println("\n\n==============YOUR TICKET HISTORY==============");
			System.out.println("\nEvent\t\t\tTicket date");
			int count = 1;
			for(SoldTicket currTicket : tickets) {
				//Fetch event name
				Event e = null;
				for(Event currEvent : events) {
					if(currEvent.getId() == currTicket.getEventId()) {
						e = currEvent;
						break;
					}
				}
				String row = String.format("%d) %s\t\t\t%s", count++, e.getName(), currTicket.getReferenceDate());
				historyRows.add(row);
				System.out.println(row);
				System.out.println("---------------------------------------------------------");
			}
			System.out.print("Select any ticket for options or press Q to return to home page: ");
			String selection = scanner.nextLine();
			
			switch (selection) {
			case "q":
			case "Q":
				return;
			default:
				// Check if we are selecting a ticket
				try {
					int parsedSelection = Integer.parseInt(selection);
					if (parsedSelection > 0 && parsedSelection <= tickets.size()) {
						// We are selecting a ticket
						System.out.println(String.format("\nSelected '%s'", historyRows.get(parsedSelection - 1)));
						System.out.println("\nWhat do you wish to do?");
						System.out.println("1) Write a feedback on this event");
						System.out.println("2) Back to history");
						boolean exitLoop = false;
						while(!exitLoop) {
							try {
								System.out.print("Your answer: ");
								int optionSelection = scanner.nextInt();
								scanner.nextLine();
								switch(optionSelection) {
								case 1:
									writeFeedback(scanner, tickets.get(parsedSelection - 1).getEventId());
								case 2:
									exitLoop = true;
									break;
								default:
									continue;
								}	
							}catch(NoSuchElementException e) {
								scanner.nextLine();
								continue;
							}
						}
					}
					continue;
				} catch (NumberFormatException e) {
					continue;
				}
			}
		}
	}
	
	private static void writeFeedback(Scanner scanner, long eventId) {
		System.out.println("\n\n==============SUBMIT FEEDBACK==============");
		int rating = -1;
		while(true) {			
			System.out.print("\nFrom 1 to 5, how much would you rate this event? ");
			try{
				rating = scanner.nextInt();
				scanner.nextLine();
				if(rating >= 1 && rating <= 5) {
					break;
				}
			}catch(NoSuchElementException e) {
				scanner.nextLine();
				continue;
			}
		}
		System.out.println("\nAnything in particular you want to let the organizer know?");
		String body = scanner.nextLine();
		System.out.println("\nSubmitting your feedback, please wait...");
		String message = SOAPProxyRESTClient.getInstance().submitFeedback(CustomerClientApplication.getCustomerId(), eventId, rating, body);
		System.out.println("\n=======================================================");
		System.out.println(message);
		System.out.println("\n=======================================================");
	}
}
