package it.univaq.sose.dagi.customer_client.ui;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment;
import io.swagger.model.event_merch_prosumer.EventWithMerch;
import io.swagger.model.event_merch_prosumer.Merchandise;
import io.swagger.model.soap_proxy.Event;
import it.univaq.sose.dagi.customer_client.client.EventMerchProsumerRESTClient;
import it.univaq.sose.dagi.customer_client.client.SOAPProxyRESTClient;
import it.univaq.sose.dagi.customer_client.Utility;

public class CatalogueCommands {

	enum SortingMode {
		ID_DESC, ID_ASC, ALPHABETICAL_ASC, ALPHABETICAL_DESC;

		@Override
		public String toString() {
			switch (this) {
			default:
			case ID_DESC:
				return "newest";
			case ID_ASC:
				return "oldest";
			case ALPHABETICAL_DESC:
				return "A to Z";
			case ALPHABETICAL_ASC:
				return "Z to A";
			}
		}
	}

	private static boolean exitCatalogue;
	private static boolean isDirty;
	private static int currentPage;
	private static SortingMode currentSortBy;
	private static List<Event> currentPageEvents;

	public static void eventCatalogue(Scanner scanner) {
		//initialize state
		exitCatalogue = false;
		isDirty = true;
		currentPage = 1;
		currentSortBy = SortingMode.ID_DESC;
		
		//Keep loading until we exit to the home page
		while (!exitCatalogue) {
			loadNewPage(scanner);
		}
	}

	private static void loadNewPage(Scanner scanner) {
		System.out.println(String.format("=================Event catalogue page %s, sort method '%s'================", currentPage,
				currentSortBy.toString()));
		// Load the catalogue page content
		populateCataloguePage();
		
		// Check if to allow page navigation
		if (currentPage > 1) {
			System.out.println("B) Previous page");
		}
		if (currentPageEvents.size() > 0) {
			System.out.println("N) Next page");
		}
		System.out.println("S) Change sort method");
		System.out.println("Q) Return to home");
		boolean ok = false;
		//Keep asking until the input makes sense
		while (!ok) {
			System.out.print("Your answer: ");
			String selection = scanner.nextLine();
			switch (selection) {
			case "b":
			case "B":
				// Do nothing if on page 1
				if (currentPage < 2) {
					continue;
				}
				currentPage--;
				ok = true;
				isDirty = true; //flag to request new content from the api
				return;
			case "n":
			case "N":
				// Do nothing if the page is empty
				if (currentPageEvents.size() == 0) {
					continue;
				}
				currentPage++;
				ok = true;
				isDirty = true; //flag to request new content from the api
				return;
			case "s":
			case "S":
				SortingMode oldSortBy = currentSortBy;
				boolean newSortOk;
				do{
					newSortOk = true;
					System.out.print("Select new sort method: 1) By newest, 2) By oldest, 3) From A to Z, 4) From Z to A: ");
					int sortMethodSelected = scanner.nextInt();
					scanner.nextLine();
					if(sortMethodSelected == 1) {
						currentSortBy = SortingMode.ID_DESC;
					}else if(sortMethodSelected == 2) {
						currentSortBy = SortingMode.ID_ASC;
					}else if(sortMethodSelected == 3) {
						currentSortBy = SortingMode.ALPHABETICAL_ASC;
					}else if(sortMethodSelected == 4) {
						currentSortBy = SortingMode.ALPHABETICAL_DESC;
					}else {	
						newSortOk = false;
					}
				}while(!newSortOk);
				//Check if we changed the sorting method and only request the catalogue page if so
				if(currentSortBy != oldSortBy) {
					isDirty = true; //flag to request new content from the api
				}
				return;
			case "q":
			case "Q":
				exitCatalogue = true;
				ok = true;
				return;
			default:
				// Check if we are selecting an event in the current page
				try {
					int parsedSelection = Integer.parseInt(selection);
					if (parsedSelection > 0 && parsedSelection <= currentPageEvents.size()) {
						// We are selecting an event in the page
						System.out.println("Selected event " + parsedSelection);
						showEventInfo(parsedSelection, scanner);
						ok = true;
					}
					continue;
				} catch (NumberFormatException e) {
					continue;
				}

			}
		}
	}

	private static void showEventInfo(int eventMenuPosition, Scanner scanner) {
		while(true) {	//We exit with a return statement		
			//Fetch all the info required (event and merchandise)
			Event event = currentPageEvents.get(eventMenuPosition - 1);
			EventWithMerch eventWithMerch = EventMerchProsumerRESTClient.getInstance().fetchEventInfoAndMerchandise(event.getId());
			
			//Print the event info and menù
			printEventInfo(eventWithMerch);
			boolean ok = false;
			boolean reprintInfo = false;
			while(!ok) {
				if(reprintInfo) {
					printEventInfo(eventWithMerch);
					reprintInfo = false;
				}
				System.out.print("Your answer: ");
				try{
					int selected = scanner.nextInt();
					scanner.nextLine();
					switch(selected) {
					case 1:
						boolean ticketPurchased = TicketPurchaseCommands.openTicketPurchase(scanner, event);
						if(ticketPurchased) {
							return; //Go back to catalogue if the customer purchased the ticket
						}else {
							//we are coming back from the ticket purchase menù, reprint the info and menù
							reprintInfo = true;
						}
						break;
					case 2:
						ok = true;
						return; //Exit the event details
					default:
						break;
					}
				}catch(NoSuchElementException e) {
					scanner.nextLine();
					continue;
				}
			}
		}
	}

	private static void populateCataloguePage() {
		if(isDirty) {
			currentPageEvents = SOAPProxyRESTClient.getInstance().requestEventCataloguePage(currentPage, currentSortBy.name());
			isDirty = false;
		}
		int count = 1;
		AsciiTable at = Utility.createAsciiTable(32, 42);
		at.addRule();
		at.addRow("Event title", "Running time");
		for (Event event : currentPageEvents) {
			String eventTitle= String.format("%d) %s", count++, event.getName());
			String runningTime = event.getStartDate().toString() + " - " + event.getEndDate().toString();
			at.addRule();
			at.addRow(eventTitle, runningTime);
		}
		at.addRule();
		at.setTextAlignment(TextAlignment.CENTER);
		String rend = at.render();
		System.out.println(rend);
	}
	
	private static void printEventInfo(EventWithMerch eventWithMerch) {
		//Print the info
		System.out.println("\n======================================EVENT DETAILS=====================================");
		AsciiTable at = Utility.createAsciiTable(30, 55);
		at.addRule();
	    at.addRow("Name", eventWithMerch.getEvent().getName());
	    at.addRule();
	    at.addRow("Organizer", eventWithMerch.getEvent().getOrganizerId());
	    at.addRule();
	    at.addRow("Description of the event", eventWithMerch.getEvent().getDescription());
	    at.addRule();
	    at.addRow("Start", eventWithMerch.getEvent().getStartDate());
	    at.addRule();
	    at.addRow("End", eventWithMerch.getEvent().getEndDate());
	    at.addRule();
	    at.addRow("Tickets remaining", eventWithMerch.getEvent().getNrTickets());
	    at.addRule();
		at.setTextAlignment(TextAlignment.CENTER);
		String rend = at.render();
		System.out.println(rend);
	    System.out.println("");
		System.out.println("=======================================Merchandise list========================================");
		int count = 1;
		at = Utility.createAsciiTable(30, 17, 45);
		at.addRule();
	    at.addRow("Article", "Barcode", "Description");
		for(Merchandise merch : eventWithMerch.getMerchandise()) {
			at.addRule();
		    at.addRow(merch.getName(), merch.getBarCode(), merch.getDescription());
		}
		at.addRule();
		at.setTextAlignment(TextAlignment.CENTER);
		rend = at.render();
		System.out.println(rend);
		//Print the menù
		System.out.println("===============================================================================================");
		System.out.println("1) Buy a ticket");
		System.out.println("2) Return to the catalogue");
	}
}
