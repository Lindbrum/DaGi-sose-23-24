package it.univaq.sose.dagi.soap_proxy_prosumer_rest.model;

import java.util.List;

public class CustomerHistory {

	private List<SoldTicket> boughtTickets;
	
	private List<Event> ticketRelatedEvents;

	public CustomerHistory(List<SoldTicket> boughtTickets, List<Event> ticketRelatedEvents) {
		super();
		this.boughtTickets = boughtTickets;
		this.ticketRelatedEvents = ticketRelatedEvents;
	}

	public CustomerHistory() {
		super();
	}

	public List<SoldTicket> getBoughtTickets() {
		return boughtTickets;
	}

	public void setBoughtTickets(List<SoldTicket> boughtTickets) {
		this.boughtTickets = boughtTickets;
	}

	public List<Event> getTicketRelatedEvents() {
		return ticketRelatedEvents;
	}

	public void setTicketRelatedEvents(List<Event> ticketRelatedEvents) {
		this.ticketRelatedEvents = ticketRelatedEvents;
	}
	
	
}
