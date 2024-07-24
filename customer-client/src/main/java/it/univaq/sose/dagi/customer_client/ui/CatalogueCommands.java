package it.univaq.sose.dagi.customer_client.ui;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import io.swagger.model.event_merch_prosumer.EventWithMerch;
import io.swagger.model.event_merch_prosumer.Merchandise;
import io.swagger.model.soap_proxy.Event;
import it.univaq.sose.dagi.customer_client.client.EventMerchProsumerRESTClient;
import it.univaq.sose.dagi.customer_client.client.SOAPProxyRESTClient;

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
		System.out.println(String.format("===Event catalogue page %s, sort method '%s'===", currentPage,
				currentSortBy.toString()));
		System.out.println("Event title\t\t\t\tRunning time");
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
			
			//Print the info
			System.out.println("\n=============EVENT DETAILS=============");
			System.out.println(String.format("Name:\t %s", eventWithMerch.getEvent().getName()));
			System.out.println(String.format("Organizer:\t %s", eventWithMerch.getEvent().getOrganizerId()));
			System.out.println(String.format("Description of the event:\n\n%s", eventWithMerch.getEvent().getDescription()));
			System.out.println(String.format("Start:\t %s", eventWithMerch.getEvent().getStartDate()));
			System.out.println(String.format("End:\t %s", eventWithMerch.getEvent().getEndDate()));
			System.out.println(String.format("Tickets remaining:\t %d", eventWithMerch.getEvent().getNrTickets()));
			System.out.println("\n=============Merchandise list=============");
			System.out.println("article\t\t\t\t\tbarcode\t\t\t\t\t\tdescription");
			int count = 1;
			for(Merchandise merch : eventWithMerch.getMerchandise()) {
				System.out.println(String.format("%d) %s\t\t\t%d\t\t%s", count++, merch.getName(), merch.getBarCode(), merch.getDescription()));
				System.out.println("------------------------------------------");
			}
			
			//Print the menù
			System.out.println("=========================================");
			System.out.println("1) Buy a ticket");
			System.out.println("2) Return to the catalogue");
			boolean ok = false;
			while(!ok) {
				System.out.print("Your answer: ");
				try{
					int selected = scanner.nextInt();
					scanner.nextLine();
					switch(selected) {
					case 1:
						boolean ticketPurchased = TicketPurchaseCommands.openTicketPurchase(scanner, event);
						if(ticketPurchased) {
							return; //Go back to catalogue if the customer purchased the ticket
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
		for (Event event : currentPageEvents) {
			String runningTime = event.getStartDate().toString() + " - " + event.getEndDate().toString();

			System.out.println("-------------------------------------------------------------------");
			System.out.println(String.format("%d) %s\t\t%s", count++, event.getName(), runningTime));
		}
		System.out.println("-------------------------------------------------------------------");
	}
}
