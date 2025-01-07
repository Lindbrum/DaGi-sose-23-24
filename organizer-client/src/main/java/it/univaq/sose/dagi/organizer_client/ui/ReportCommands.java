package it.univaq.sose.dagi.organizer_client.ui;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment;
import io.swagger.model.feedback_prosumer.EventFeedbackReport;
import io.swagger.model.feedback_prosumer.Feedback;
import io.swagger.model.sales_prosumer.EventSalesReport;
import io.swagger.model.soap_proxy.Event;
import it.univaq.sose.dagi.organizer_client.OrganizerClientApplication;
import it.univaq.sose.dagi.organizer_client.Utility;
import it.univaq.sose.dagi.organizer_client.client.ReportAsyncRESTClient;
import it.univaq.sose.dagi.organizer_client.client.SOAPProxyRESTClient;
import it.univaq.sose.dagi.organizer_client.model.EventReport;
import it.univaq.sose.dagi.organizer_client.model.SortingMode;

public class ReportCommands {

	private static boolean exitCatalogue;
	private static boolean isDirty;
	private static int currentPage;
	private static SortingMode currentSortBy;
	private static List<Event> currentPageEvents;

	public static void organizerEventCatalogue(Scanner scanner) {
		// initialize state
		exitCatalogue = false;
		isDirty = true;
		currentPage = 1;
		currentSortBy = SortingMode.ID_DESC;

		// Keep loading until we exit to the home page
		while (!exitCatalogue) {
			loadNewPage(scanner);
		}
	}

	private static void loadNewPage(Scanner scanner) {
		System.out.println("");
		System.out.println(String.format("==============Your events page %s, sort method '%s'=============", currentPage,
				currentSortBy.toString()));
		// Load the catalogue page content
		populateCataloguePage();
		System.out.println("===================================================================");
		
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
		// Keep asking until the input makes sense
		while (!ok) {
			System.out.print("");
			System.out.print("Select an event to generate a report for or use the navigation commands: ");
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
				isDirty = true; // flag to request new content from the api
				return;
			case "n":
			case "N":
				// Do nothing if the page is empty
				if (currentPageEvents.size() == 0) {
					continue;
				}
				currentPage++;
				ok = true;
				isDirty = true; // flag to request new content from the api
				return;
			case "s":
			case "S":
				SortingMode oldSortBy = currentSortBy;
				boolean newSortOk;
				do {
					newSortOk = true;
					System.out.print(
							"Select new sort method: 1) By newest, 2) By oldest, 3) From A to Z, 4) From Z to A: ");
					int sortMethodSelected = scanner.nextInt();
					scanner.nextLine();
					if (sortMethodSelected == 1) {
						currentSortBy = SortingMode.ID_DESC;
					} else if (sortMethodSelected == 2) {
						currentSortBy = SortingMode.ID_ASC;
					} else if (sortMethodSelected == 3) {
						currentSortBy = SortingMode.ALPHABETICAL_ASC;
					} else if (sortMethodSelected == 4) {
						currentSortBy = SortingMode.ALPHABETICAL_DESC;
					} else {
						newSortOk = false;
					}
				} while (!newSortOk);
				// Check if we changed the sorting method and only request the catalogue page if
				// so
				if (currentSortBy != oldSortBy) {
					isDirty = true; // flag to request new content from the api
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
						System.out.println("");
						System.out.println("=======Selected event " + parsedSelection + "========");
						System.out.println("");
						System.out.print("Specify keywords to count in feedbacks (eventually empty): ");
						String keywords = scanner.nextLine();
						showEventReport(currentPageEvents.get(parsedSelection - 1), keywords, scanner);
						ok = true;
					}
					continue;
				} catch (NumberFormatException e) {
					continue;
				}

			}
		}
		
	}
	
	private static void showEventReport(Event event, String keywords, Scanner scanner) {
		
		//used to format data in tables
		AsciiTable at;
		String rend;
		
		//Fetch the report data
		EventReport report = ReportAsyncRESTClient.getInstance().fetchEventReport(event.getId(), keywords);
		EventFeedbackReport feedbacksReport = report.getFeedbackReport();
		EventSalesReport salesReport = report.getSalesReport();
		//System.out.println("DEBUG: " +feedbacksReport.toString());
		
		//Format the document
		System.out.println("==========================================================");
		System.out.println(String.format("==============Report for '%s'==============", event.getName()));
		System.out.println("==========================================================");
		
		//Feedback report
		System.out.println("");
		System.out.println("=========================Feedbacks========================");
		
		if(feedbacksReport.getEventFeedbacks() == null){
			System.out.println("");
			System.out.println("****************************");
			System.out.println("*No feedbacks to report yet*");
			System.out.println("****************************");
		}else{
			//Create a tabular view of each feedback
			at = Utility.createAsciiTable(10, 45);
			for (Feedback feedback : feedbacksReport.getEventFeedbacks()) {
				at.addRule();
				at.addRow("ID", feedback.getId());
				at.addRule();
				at.addRow("User ID", feedback.getUserId());
				at.addRule();
				at.addRow("Rating", feedback.getRating());
				at.addRule();
				at.addRow("Body", feedback.getBody());
				at.addRule();
			}
			at.setTextAlignment(TextAlignment.CENTER);
			rend = at.render();
			System.out.println(rend);
			
			//Average rating and customer age
			System.out.println(String.format("Average rating: %f", feedbacksReport.getAverageRating()));
			System.out.println(String.format("Average customer age: %f", feedbacksReport.getAverageCustomerAge()));
			
			//Create a tabular view of each keyword count
			if(feedbacksReport.getKeywordsCount() != null) {
				at = Utility.createAsciiTable(20, 9);
				Map<String,Integer> counts = feedbacksReport.getKeywordsCount();
				System.out.println("");
				System.out.println("Keyword counts:");
				for(Entry<String,Integer> current : counts.entrySet()) {
					at.addRule();
					at.addRow(current.getKey(), current.getValue());
				}
				at.addRule();
				
				at.setTextAlignment(TextAlignment.CENTER);
				rend = at.render();
				System.out.println(rend);
			}
		}
		
		//Report on ticket sales
		System.out.println("");
		System.out.println("==========Ticket Sales==========");
		
		if(salesReport.getEventSoldTickets() == null || salesReport.getEventSoldTickets().isEmpty()) {
			System.out.println("");
			System.out.println("*******************************");
			System.out.println("*No ticket sales to report yet*");
			System.out.println("*******************************");
		}else {
			System.out.println(String.format("Average customer age: %f", salesReport.getAverageCustomerAge()));
			
			//Age counts
			Map<String,Integer> ageCounts = salesReport.getAgeCounts();
			System.out.println("");
			System.out.println("Age counts:");
			
			//Create a tabular view of each age count
			at = Utility.createAsciiTable(14, 9);
			for(Entry<String,Integer> current : ageCounts.entrySet()) {
				at.addRule();
				at.addRow(current.getKey(), current.getValue());
			}
			at.addRule();
			
			at.setTextAlignment(TextAlignment.CENTER);
			rend = at.render();
			System.out.println(rend);
			
			//Gender counts
			Map<String,Integer> genderCounts = salesReport.getGenderCounts();
			System.out.println("");
			System.out.println("Gender counts:");
			
			//Create a tabular view of each gender count
			at = Utility.createAsciiTable(14, 9);
			for(Entry<String,Integer> current : genderCounts.entrySet()) {
				at.addRule();
				at.addRow(current.getKey(), current.getValue());
			}
			at.addRule();
			
			at.setTextAlignment(TextAlignment.CENTER);
			rend = at.render();
			System.out.println(rend);
			
			//Date counts
			Map<String,Integer> dateCounts = salesReport.getDateCounts();
			System.out.println("");
			System.out.println("Date counts:");
			
			//Create a tabular view of each age count
			at = Utility.createAsciiTable(25, 9);
			for(Entry<String,Integer> current : dateCounts.entrySet()) {
				at.addRule();
				at.addRow(current.getKey(), current.getValue());
			}
			at.addRule();
			
			at.setTextAlignment(TextAlignment.CENTER);
			rend = at.render();
			System.out.println(rend);
		}
		
		System.out.println("==========================================================");
		System.out.println("=======================End of report======================");
		System.out.println("==========================================================");
	}

	private static void populateCataloguePage() {
		if(isDirty) {
			currentPageEvents = SOAPProxyRESTClient.getInstance().requestOrganizerEventsPage(OrganizerClientApplication.getOrganizerId(), currentPage, currentSortBy.name());
			isDirty = false;
		}
		int count = 1;
        
		//Create a tabular view
		AsciiTable at = Utility.createAsciiTable(25, 40);
		
        at.addRule();
	    at.addRow("Event title", "Running time");
		for (Event event : currentPageEvents) {
			String eventName = String.format("%d) %s", count++, event.getName());
			String runningTime = event.getStartDate().toString() + " - " + event.getEndDate().toString();
			at.addRule();
		    at.addRow(eventName, runningTime);
		}
		at.addRule();
		
		at.setTextAlignment(TextAlignment.CENTER);
		String rend = at.render();
		System.out.println(rend);
	}
}
