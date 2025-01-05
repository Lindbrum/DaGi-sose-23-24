package it.univaq.sose.dagi.organizer_client.ui;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import io.swagger.model.merchandise_provider.Merchandise;
import io.swagger.model.soap_proxy.Event;
import it.univaq.sose.dagi.organizer_client.client.MerchandiseRESTClient;
import it.univaq.sose.dagi.organizer_client.client.SOAPProxyRESTClient;
import it.univaq.sose.dagi.organizer_client.model.SortingMode;

public class MerchCommands {

	private static boolean exitCatalogue;
	private static boolean isDirty;
	private static int currentPage;
	private static SortingMode currentSortBy;
	private static List<Merchandise> currentPageMerchs;

	public static void createMerch(Scanner scanner) {
		System.out.println("\n\n===========LIST NEW MERCHANDISE===========");
		
		String name = null;
		String description = null;
		Long associatedEventId = null;
		Long barcode = null;
		
		System.out.print("\nName of the article: ");
		name = scanner.nextLine().trim();

		System.out.println("\nA description of the article:");
		description = scanner.nextLine().trim();
		
		boolean ok = false;
		while(!ok) {			
			System.out.print("\nID of the event to associated with this article (leave blank if you intend to set this later): ");
			String eventId = scanner.nextLine().trim(); //remove whitespaces
			if(eventId != null && !eventId.isEmpty()) {			
				try{
					associatedEventId = Long.parseLong(eventId);
					ok = true;
				}catch (NumberFormatException e) {
					ok = false;
					System.out.println("==========\nPlease input a valid event ID or leave blank if you don't have one yet.\n=========");
				}
			}else { //we are leaving the input blank
				ok = true;
			}
		}
		
		ok = false;
		while(!ok) {
			System.out.println("\nProvide the article barcode:");
			String barcodeInput = scanner.nextLine().trim(); //remove whitespaces
			if(barcodeInput != null && !barcodeInput.isEmpty()) {			
				try{
					barcode = Long.parseLong(barcodeInput);
					ok = true;
				}catch (NumberFormatException e) {
					ok = false;
					System.out.println("==========\nPlease input a valid barcode (only numbers allowed).\n=========");
				}
			}
		}			
		
		//Save on the merchandise service
		Merchandise newMerch = new Merchandise();
		newMerch.setName(name);
		newMerch.setDescription(description);
		newMerch.setEventId(associatedEventId);
		newMerch.setBarCode(barcode);
		MerchandiseRESTClient.getInstance().createMerchandise(newMerch);
		
		System.out.println("\n==========================================");
		System.out.println("The merchandise article has been registered");
		System.out.println("============================================");
	}
	
	public static void merchCatalogue(Scanner scanner) {
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
		System.out.println(String.format("===Merchandise catalogue page %s, sort method '%s'===", currentPage,
				currentSortBy.toString()));
		System.out.println("Name\t\t\t\tDescription\t\t\t\tAssociated event\t\t\t\tBarcode");
		// Load the catalogue page content
		populateCataloguePage();
		
		// Check if to allow page navigation
		if (currentPage > 1) {
			System.out.println("B) Previous page");
		}
		if (currentPageMerchs.size() > 0) {
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
				if (currentPageMerchs.size() == 0) {
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
					if (parsedSelection > 0 && parsedSelection <= currentPageMerchs.size()) {
						// We are selecting an event in the page
						System.out.println("Selected event " + parsedSelection);
						openMerchDetails(parsedSelection, scanner);
						ok = true;
					}
					continue;
				} catch (NumberFormatException e) {
					continue;
				}

			}
		}
	}
	
	private static void openMerchDetails(int merchMenuPosition, Scanner scanner) {
		while(true) {	//We exit with a return statement		
			//Fetch all the info required
			Merchandise merch = currentPageMerchs.get(merchMenuPosition - 1);
			
			//Print the info and menù
			printMerchDetailsAndMenu(merch);
			boolean ok = false;
			boolean reprintInfo = false;
			while(!ok) {
				if(reprintInfo) {
					printMerchDetailsAndMenu(merch);
					reprintInfo = false;
				}
				System.out.print("Your answer: ");
				try{
					int selected = scanner.nextInt();
					scanner.nextLine();
					switch(selected) {
					case 1:
						boolean merchModified = MerchEditCommands.modifyAssociatedEvent(scanner, merch);
						if(merchModified) {
							isDirty = true;
							return; //Go back to catalogue if the organizer has modified
						}else {
							//the organizer is coming back without changes, reprint the info and menù
							reprintInfo = true;
						}
						break;
					case 2:
						ok = true;
						return; //Exit the merch details
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
			currentPageMerchs = MerchandiseRESTClient.getInstance().fetchMerchandisePage(currentPage, currentSortBy.name());
			isDirty = false;
		}
		int count = 1;
		//N) NAME - DESCRIPTION - EVENT - BARCODE
		for (Merchandise merch : currentPageMerchs) {
			System.out.println("-------------------------------------------------------------------");
			System.out.println(String.format("%d) %s\t\t%s\t\t%s\t\t%s", count++, merch.getName(), merch.getDescription(), merch.getEventId(), merch.getBarCode()));
		}
		System.out.println("-------------------------------------------------------------------");
	}
	
	private static void printMerchDetailsAndMenu(Merchandise merch) {
		//Fetch the associated event info, if defined
		Event associatedEvent = null;
		if(merch.getEventId() != null) {			
			associatedEvent = SOAPProxyRESTClient.getInstance().requestEventInfo(merch.getEventId());
		}
		
		//Print the info
		System.out.println("\n=============MERCHANDISE DETAILS=============");
		System.out.println(String.format("Name:\t %s", merch.getName()));
		System.out.println(String.format("Description:\n\n%s", merch.getDescription()));
		System.out.println(String.format("Associated event:\t %s", associatedEvent != null ? associatedEvent.getName() : "None"));
		System.out.println(String.format("Barcode:\t %s", merch.getBarCode()));
		
		//Print the menù
		System.out.println("=========================================");
		System.out.println("1) Change the associated event");
		System.out.println("2) Return to the catalogue");
	}

}
