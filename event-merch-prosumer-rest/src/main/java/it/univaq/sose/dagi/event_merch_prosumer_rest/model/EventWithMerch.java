package it.univaq.sose.dagi.event_merch_prosumer_rest.model;

import java.util.List;

public class EventWithMerch {

	private Event event;
	private List<Merchandise> merchandise;
	
	public EventWithMerch(Event event, List<Merchandise> merchandise) {
		super();
		this.event = event;
		this.merchandise = merchandise;
	}

	public EventWithMerch() {
		super();
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public List<Merchandise> getMerchandise() {
		return merchandise;
	}

	public void setMerchandise(List<Merchandise> merchandise) {
		this.merchandise = merchandise;
	}
	
}
