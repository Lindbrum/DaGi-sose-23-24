package it.univaq.sose.dagi.event_management_soap.model;

import java.time.LocalDateTime;
import java.util.Comparator;

public class Event implements Comparable<Event>{
	
	private Long id;
	private String name;
	private String description;
	private String location;
	private LocalDateTime startDate;
	private LocalDateTime endDate;
	private int nrTickets;
	public Event(Long id, String name, String description, String location, LocalDateTime startDate,
			LocalDateTime endDate, int nrTickets) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.location = location;
		this.startDate = startDate;
		this.endDate = endDate;
		this.nrTickets = nrTickets;
	}
	
	public Event(String name, String description, String location, LocalDateTime startDate, LocalDateTime endDate,
			int nrTickets) {
		super();
		this.name = name;
		this.description = description;
		this.location = location;
		this.startDate = startDate;
		this.endDate = endDate;
		this.nrTickets = nrTickets;
	}

	public Event() {
		super();
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public LocalDateTime getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}
	public LocalDateTime getEndDate() {
		return endDate;
	}
	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}
	public int getNrTickets() {
		return nrTickets;
	}
	public void setNrTickets(int nrTickets) {
		this.nrTickets = nrTickets;
	}

	@Override
	public int compareTo(Event o) {
		if(this.id > o.id) return 1;
		else if(this.id < o.id) return -1;
		else return 0;
	}
	
	public static EventIdDescendingComparator getIdDescComparator() {
		return new EventIdDescendingComparator();
	}
	
	public static EventNameAscendingComparator getNameAscComparator() {
		return new EventNameAscendingComparator();
	}
	
	public static EventNameDescendingComparator getNameDescComparator() {
		return new EventNameDescendingComparator();
	}
	
}

class EventIdDescendingComparator implements Comparator<Event>{

	@Override
	public int compare(Event o1, Event o2) {
		if(o1.getId() > o2.getId()) return -1;
		else if(o1.getId() < o2.getId()) return 1;
		else return 0;
	}
	
}

class EventNameAscendingComparator implements Comparator<Event>{

	@Override
	public int compare(Event o1, Event o2) {
		return o1.getName().compareTo(o2.getName());
	}
	
}

class EventNameDescendingComparator implements Comparator<Event>{

	@Override
	public int compare(Event o1, Event o2) {
		return o1.getName().compareTo(o2.getName()) * -1;
	}
	
}
