package it.univaq.sose.dagi.customer_client.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment;
import io.swagger.model.soap_proxy.CustomerHistory;
import io.swagger.model.soap_proxy.Event;
import io.swagger.model.soap_proxy.SoldTicket;
import it.univaq.sose.dagi.customer_client.CustomerClientApplication;
import it.univaq.sose.dagi.customer_client.client.SOAPProxyRESTClient;
import it.univaq.sose.dagi.customer_client.Utility;

public class TicketHistoryCommands {
	
	public static void showTicketHistory(Scanner scanner) {
		//Fetch the data
		CustomerHistory data;
		data = SOAPProxyRESTClient.getInstance().fetchCustomerTicketHistory(CustomerClientApplication.getCustomerId());
		List<SoldTicket> tickets = data.getBoughtTickets();
		List<Event> events = data.getTicketRelatedEvents();
		List<String> historyRows = new ArrayList<>(tickets.size()); //used to display in option menù the selected row
		
		//Print the menù
		while(true) {
			//Print the data
			System.out.println("");
			System.out.println("=================YOUR TICKET HISTORY================");
			int count = 1;
			AsciiTable at = Utility.createAsciiTable(29, 20);
			at.addRule();
			at.addRow("Name", "Reference date");
			for(SoldTicket currTicket : tickets) {
				//Fetch event name
				Event e = null;
				for(Event currEvent : events) {
					if(currEvent.getId() == currTicket.getEventId()) {
						e = currEvent;
						break;
					}
				}
				String eventHistory = String.format("%d) %s", count++, e.getName());
				historyRows.add(eventHistory);
				at.addRule();
				at.addRow(eventHistory, currTicket.getReferenceDate());
			}
			at.addRule();
			at.setTextAlignment(TextAlignment.CENTER);
			String rend = at.render();
			System.out.println(rend);
			System.out.println("");
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
						System.out.println("");
						System.out.println("What do you wish to do?");
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
		System.out.println("");
		System.out.println("=====================SUBMIT FEEDBACK======================");
		int rating = -1;
		while(true) {
			System.out.println("");
			System.out.print("From 1 to 5, how much would you rate this event? ");
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
		System.out.println("");
		System.out.println("Anything in particular you want to let the organizer know?");
		String body = scanner.nextLine();
		System.out.println("");
		System.out.println("Submitting your feedback, please wait...");
		String message = SOAPProxyRESTClient.getInstance().submitFeedback(CustomerClientApplication.getCustomerId(), eventId, rating, body);
		System.out.println("");
		System.out.println("=====================================");
		System.out.println(message);
		System.out.println("=====================================");
	}
}
