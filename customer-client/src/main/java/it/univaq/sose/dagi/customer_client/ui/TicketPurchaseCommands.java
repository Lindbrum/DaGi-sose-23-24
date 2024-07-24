package it.univaq.sose.dagi.customer_client.ui;

import java.util.List;
import java.util.Scanner;

import io.swagger.model.soap_proxy.Event;
import io.swagger.model.soap_proxy.TicketInfo;
import it.univaq.sose.dagi.customer_client.CustomerClientApplication;
import it.univaq.sose.dagi.customer_client.client.SOAPProxyRESTClient;

public class TicketPurchaseCommands {

	//Return true if the customer purchased a ticket
	public static boolean openTicketPurchase(Scanner scanner, Event event) {
		
		// Print the available tickets
		System.out.println("============AVAILABLE TICKETS============");
		List<TicketInfo> tickets;
		tickets = SOAPProxyRESTClient.getInstance().fetchEventAvailableTickets(event.getId());
		int count = 1;
		System.out.println("Ticket date/time\t\t\tRemaining");
		for (TicketInfo current : tickets) {
			System.out.println(String.format("%d) %s\t\t\t %d", count++,
					current.getReferenceDate().toString(), current.getAvailableTickets()));
			System.out.println("-----------------------------------------");
		}
		// Print the menù
		System.out.println("Select the ticket to purchase or press Q to go back to the event info.");
		while (true) {
			System.out.print("Your answer: ");
			String selection = scanner.nextLine();

			switch (selection) {
			case "q":
			case "Q":
				return false;
			default:
				// Check if we are selecting a ticket
				try {
					int parsedSelection = Integer.parseInt(selection);
					if (parsedSelection > 0 && parsedSelection <= tickets.size()) {
						// We are selecting a ticket
						System.out.println("\nSelected ticket " + parsedSelection);
						TicketInfo ticket = tickets.get(parsedSelection - 1);
						SOAPProxyRESTClient.getInstance().purchaseTicket(event.getId(), CustomerClientApplication.getCustomerId(), ticket.getReferenceDate());
						System.out.println("===============================================================================================================");
						System.out.println("Ticket purchased successfully! You can review the information in the ticket purchase history section of the app.");
						System.out.println("===============================================================================================================");
						return true;
					}
					continue;
				} catch (NumberFormatException e) {
					continue;
				}
			}
		}
	}
}
