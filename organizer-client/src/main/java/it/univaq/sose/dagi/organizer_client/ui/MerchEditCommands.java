package it.univaq.sose.dagi.organizer_client.ui;

import java.util.List;
import java.util.Scanner;

import io.swagger.model.merchandise_provider.Merchandise;
import io.swagger.model.soap_proxy.Event;
import it.univaq.sose.dagi.organizer_client.client.MerchandiseRESTClient;
import it.univaq.sose.dagi.organizer_client.client.SOAPProxyRESTClient;

public class MerchEditCommands {
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
	private static boolean merchModified;
	private static int currentPage;
	private static SortingMode currentSortBy;
	private static List<Event> currentPageEvents;
	private static Merchandise merchandise;

	public static boolean modifyAssociatedEvent(Scanner scanner, Merchandise merch) {
		//initialize state
		merchandise = merch;
		merchModified = false;
		exitCatalogue = false;
		isDirty = true;
		currentPage = 1;
		currentSortBy = SortingMode.ID_DESC;
		
		//Keep loading until we are done
		while (!exitCatalogue) {
			loadNewPage(scanner);
		}
		
		return merchModified; //this is used to check wether the user is exiting using the menù or automatically after completing the modification
	}

	private static void loadNewPage(Scanner scanner) {
		System.out.println(String.format("===Event catalogue page %s, sort method '%s'===", currentPage,
				currentSortBy.toString()));
		System.out.println("Event title\t\t\t\tRunning time");
		// Load the catalogue page content
		populateCataloguePage();
		
		System.out.println("Select the new event to associate using its list number or digit \"R\" to disassociate the merchandise article from the event currently set");
		// Check if to allow page navigation
		if (currentPage > 1) {
			System.out.println("B) Previous page");
		}
		if (currentPageEvents.size() > 0) {
			System.out.println("N) Next page");
		}
		System.out.println("S) Change sort method");
		System.out.println("Q) Return to previous menù");
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
			case "r":
			case "R":
				//remove the event ID from the merch
				System.out.println("Disassociating the merch from the event, please wait.");
				boolean result = changeAssociatedEvent(null, scanner);
				merchModified = true; // go back to the merchandise list (so two levels of menù above)
				exitCatalogue = true;
				ok = true;
				break;
			default:
				// Check if we are selecting an event in the current page
				try {
					int parsedSelection = Integer.parseInt(selection);
					if (parsedSelection > 0 && parsedSelection <= currentPageEvents.size()) {
						// We are selecting an event in the page
						System.out.println("Selected event " + parsedSelection + ", please wait.");
						result = changeAssociatedEvent(parsedSelection, scanner);
						if(result) {
							System.out.println("===========================");
							System.out.println("Event successfully updated.");
							System.out.println("===========================");
						}else {
							System.out.println("=========================================================================");
							System.out.println("There was an error while changing the associated event. Please try again.");
							System.out.println("=========================================================================");
						}
						merchModified = true; // go back to the merchandise list (so two levels of menù above)
						exitCatalogue = true;
						ok = true;
					}
					continue;
				} catch (NumberFormatException e) {
					continue;
				}

			}
		}
	}
	
	private static boolean changeAssociatedEvent(Integer selectedEvent, Scanner scanner) {
		Long eventId = selectedEvent != null ? currentPageEvents.get(selectedEvent - 1).getId() : null;
		return MerchandiseRESTClient.getInstance().modifyRelatedEvent(merchandise.getId(), eventId);
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
