package it.univaq.sose.dagi.sales_analysis_prosumer_rest.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class EventSalesReport {
	private List<SoldTicket> eventSoldTickets;
	private Map<Integer,Integer> ageCounts;
	private float averageCustomerAge;
	private Map<String,Integer> genderCounts;
	private Map<LocalDateTime,Integer> dateCounts;
	

	public EventSalesReport(List<SoldTicket> eventSoldTickets, Map<Integer, Integer> ageCounts,
			float averageCustomerAge, Map<String, Integer> genderCounts, Map<LocalDateTime, Integer> dateCounts) {
		super();
		this.eventSoldTickets = eventSoldTickets;
		this.ageCounts = ageCounts;
		this.averageCustomerAge = averageCustomerAge;
		this.genderCounts = genderCounts;
		this.dateCounts = dateCounts;
	}

	public EventSalesReport() {
		super();
	}

	public List<SoldTicket> getEventSoldTickets() {
		return eventSoldTickets;
	}

	public void setEventSoldTickets(List<SoldTicket> eventSoldTickets) {
		this.eventSoldTickets = eventSoldTickets;
	}

	public Map<LocalDateTime, Integer> getDateCounts() {
		return dateCounts;
	}

	public void setDateCounts(Map<LocalDateTime, Integer> dateCounts) {
		this.dateCounts = dateCounts;
	}

	public Map<Integer, Integer> getAgeCounts() {
		return ageCounts;
	}

	public void setAgeCounts(Map<Integer, Integer> ageCounts) {
		this.ageCounts = ageCounts;
	}

	public float getAverageCustomerAge() {
		return averageCustomerAge;
	}

	public void setAverageCustomerAge(float averageCustomerAge) {
		this.averageCustomerAge = averageCustomerAge;
	}

	public Map<String, Integer> getGenderCounts() {
		return genderCounts;
	}

	public void setGenderCounts(Map<String, Integer> genderCounts) {
		this.genderCounts = genderCounts;
	}
	
	
}
