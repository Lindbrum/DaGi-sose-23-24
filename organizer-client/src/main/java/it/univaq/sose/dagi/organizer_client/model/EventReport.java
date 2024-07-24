package it.univaq.sose.dagi.organizer_client.model;

import io.swagger.model.feedback_prosumer.EventFeedbackReport;
import io.swagger.model.sales_prosumer.EventSalesReport;

public class EventReport {
	private EventFeedbackReport feedbackReport;
	private EventSalesReport salesReport;
	public EventReport(EventFeedbackReport feedbackReport, EventSalesReport salesReport) {
		super();
		this.feedbackReport = feedbackReport;
		this.salesReport = salesReport;
	}
	public EventReport() {
		super();
	}
	public EventFeedbackReport getFeedbackReport() {
		return feedbackReport;
	}
	public void setFeedbackReport(EventFeedbackReport feedbackReport) {
		this.feedbackReport = feedbackReport;
	}
	public EventSalesReport getSalesReport() {
		return salesReport;
	}
	public void setSalesReport(EventSalesReport salesReport) {
		this.salesReport = salesReport;
	}
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("\n=========EVENT REPORT==========");
		sb.append("\nFeedbacks: " + this.feedbackReport.getEventFeedbacks().size());
		sb.append("\nSales: " + this.salesReport.getEventSoldTickets().size());
		return sb.toString();
	}
	
	
}
