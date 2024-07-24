package it.univaq.sose.dagi.organizer_client.ui;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

import io.swagger.model.feedback_prosumer.EventFeedbackReport;
import io.swagger.model.feedback_prosumer.Feedback;
import io.swagger.model.sales_prosumer.EventSalesReport;
import it.univaq.sose.dagi.organizer_client.OrganizerClientApplication;
import it.univaq.sose.dagi.organizer_client.client.EventSOAPClient;
import it.univaq.sose.dagi.organizer_client.client.ReportAsyncRESTClient;
import it.univaq.sose.dagi.organizer_client.model.EventReport;
import it.univaq.sose.dagi.wsdltypes.EventData;
import it.univaq.sose.dagi.wsdltypes.ServiceException_Exception;

public class ReportCommands {

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
	private static List<EventData> currentPageEvents;

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
		System.out.println(String.format("\n\n===Your events page %s, sort method '%s'===", currentPage,
				currentSortBy.toString()));
		System.out.println("Event title\t\t\t\tRunning time");
		// Load the catalogue page content
		populateCataloguePage();
		System.out.println("======================================================");
		
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
						System.out.println("\n=======Selected event " + parsedSelection + "========");
						System.out.print("\nSpecify keywords to count in feedbacks (eventually empty): ");
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
	
	private static void showEventReport(EventData event, String keywords, Scanner scanner) {
		//Fetch the report data
		EventReport report = ReportAsyncRESTClient.getInstance().fetchEventReport(event.getEventId(), keywords);
		EventFeedbackReport feedbacksReport = report.getFeedbackReport();
		EventSalesReport salesReport = report.getSalesReport();
		
		//Format the document
		System.out.println("===================================================================");
		System.out.println(String.format("\n\n==========Report for '%s'==========", event.getName()));
		System.out.println("===================================================================");
		//Feedback report
		System.out.println("\n==========Feedbacks==========");
		for(Feedback feedback : feedbacksReport.getEventFeedbacks()) {
			System.out.println(String.format("ID: %d", feedback.getId()));
			System.out.println(String.format("User ID: %d", feedback.getUserId()));
			System.out.println(String.format("Rating: %d", feedback.getRating()));
			System.out.println(String.format("Body:\n%s", feedback.getBody()));
			System.out.println("------------------------------------------------------");
		}
		System.out.println(String.format("Average rating: %f", feedbacksReport.getAverageRating()));
		System.out.println(String.format("Average customer age: %f", feedbacksReport.getAverageCustomerAge()));
		if(feedbacksReport.getKeywordsCount() != null) {
			Map<String,Integer> counts = feedbacksReport.getKeywordsCount();
			System.out.println("\nKeyword counts:");
			int count = 1;
			for(Entry<String,Integer> current : counts.entrySet()) {
				System.out.println(String.format("%d) %s:\t%d", count++, current.getKey(), current.getValue()));
			}
		}
		System.out.println("\n==========Ticket Sales==========");
		System.out.println(String.format("Average customer age: %f", salesReport.getAverageCustomerAge()));
		Map<String,Integer> ageCounts = salesReport.getAgeCounts();
		System.out.println("\nAge counts:");
		int count = 1;
		for(Entry<String,Integer> current : ageCounts.entrySet()) {
			System.out.println(String.format("%d) %s:\t%d", count++, current.getKey(), current.getValue()));
		}
		Map<String,Integer> genderCounts = salesReport.getGenderCounts();
		System.out.println("\nGender counts:");
		count = 1;
		for(Entry<String,Integer> current : genderCounts.entrySet()) {
			System.out.println(String.format("%d) %s:\t%d", count++, current.getKey(), current.getValue()));
		}
		Map<String,Integer> dateCounts = salesReport.getDateCounts();
		System.out.println("\nDate counts:");
		count = 1;
		for(Entry<String,Integer> current : dateCounts.entrySet()) {
			System.out.println(String.format("%d) %s:\t%d", count++, current.getKey(), current.getValue()));
		}
		System.out.println("===================================================================");
		System.out.println("===========================End of report===========================");
		System.out.println("===================================================================");
	}

	private static void populateCataloguePage() {
		if(isDirty) {
			try {
				currentPageEvents = EventSOAPClient.getInstance().requestOrganizerEventsPage(OrganizerClientApplication.getOrganizerId(), currentPage, currentSortBy.name());
			} catch (ServiceException_Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			}
			isDirty = false;
		}
		int count = 1;
		for (EventData event : currentPageEvents) {
			String runningTime = event.getStartDate().toString() + " - " + event.getEndDate().toString();

			System.out.println("-------------------------------------------------------------------");
			System.out.println(String.format("%d) %s\t\t%s", count++, event.getName(), runningTime));
		}
		System.out.println("-------------------------------------------------------------------");
	}
}
