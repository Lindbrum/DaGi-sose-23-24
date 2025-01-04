package it.univaq.sose.dagi.organizer_client.client;

import java.util.concurrent.Future;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import io.swagger.model.feedback_prosumer.EventFeedbackReport;
import io.swagger.model.sales_prosumer.EventSalesReport;
import it.univaq.sose.dagi.organizer_client.client.callback.FeedbackCallback;
import it.univaq.sose.dagi.organizer_client.client.callback.SalesCallback;
import it.univaq.sose.dagi.organizer_client.jackson.DateCompatibleJacksonJsonProvider;
import it.univaq.sose.dagi.organizer_client.model.EventReport;

public class ReportAsyncRESTClient {
	//Singleton
		private static ReportAsyncRESTClient instance = null;
		
		private static final String endpointFeedback = "http://localhost:8080/api/reports/feed/feedback-report/";
		private static final String endpointSales = "http://localhost:8080/api/reports/sales/sales/";
		private Client feedbackClient;
		private Client salesClient;
		
		private ReportAsyncRESTClient() {
			setup();
		}
		
		public static ReportAsyncRESTClient getInstance() {
			if(instance == null) {
				instance = new ReportAsyncRESTClient();
			}
			return instance;
		}
		
		private void setup() {
			feedbackClient = ClientBuilder.newClient();
			feedbackClient.register(new DateCompatibleJacksonJsonProvider());
			salesClient = ClientBuilder.newClient();
			salesClient.register(new DateCompatibleJacksonJsonProvider());
	    }
		
		public EventReport fetchEventReport(long eventId, String keywords) {
			//Build callbacks
			FeedbackCallback feedbackCallback = new FeedbackCallback();
			SalesCallback salesCallback = new SalesCallback();
			
			System.out.print("\nRequesting feedback and sales reports, please wait...");
			
			//Build future objects and make the async requests
			String feedbackUrl = endpointFeedback + eventId;
			if(keywords != null && !keywords.isEmpty()) {
				feedbackUrl += "?keywords=" + keywords;
			}
			Future<EventFeedbackReport> futureFeedbackResponse = feedbackClient.target(feedbackUrl).request().async().get(feedbackCallback);
			Future<EventSalesReport> futureSalesResponse = salesClient.target(endpointSales + 
					eventId).request().async().get(salesCallback);
			
			while(!futureFeedbackResponse.isDone() || !futureSalesResponse.isDone()) {
				//System.out.print(".");
			}
			System.out.println("\nCompleted.");
			EventReport report = new EventReport(feedbackCallback.getResponse(), salesCallback.getResponse());
			return report;
		}
}
