package it.univaq.sose.dagi.soap_proxy_prosumer_rest.model;

import java.time.LocalDateTime;
import java.util.Comparator;

public class Event implements Comparable<Event>{
	
	private Long id;
	private String name;
	private String description;
	private Long organizerId;
	private String location;
	private LocalDateTime startDate;
	private LocalDateTime endDate;
	private int nrTickets;
	
	
	
	public Event(Long id, String name, String description, Long organizerId, String location, LocalDateTime startDate,
			LocalDateTime endDate, int nrTickets) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.organizerId = organizerId;
		this.location = location;
		this.startDate = startDate;
		this.endDate = endDate;
		this.nrTickets = nrTickets;
	}

	public Event(String name, String description, Long organizerId, String location, LocalDateTime startDate,
			LocalDateTime endDate, int nrTickets) {
		super();
		this.name = name;
		this.description = description;
		this.organizerId = organizerId;
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
	
	public Long getOrganizerId() {
		return organizerId;
	}

	public void setOrganizerId(Long organizerId) {
		this.organizerId = organizerId;
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
	
	//This method compares the current object to another object to determine whether they are equal.
	//If the object passed as a parameter is null or not an instance of the Event class, returns false.
	//Otherwise, cast the object to type Event and compare the event IDs.
	//If the IDs are equal, the method returns true, indicating that the events are considered equal.
	@Override
	public boolean equals(Object obj) {
		if(obj == null || !(obj instanceof Event)) return false;
		Event casted = (Event) obj;
		return this.getId().equals(casted.getId());
	}
}

//This represents a comparator that sorts events by their ID in descending order.
//The compare method compares the IDs of two events. If the ID of the first event (o1) is greater than that of the second (o2), it returns -1, indicating that o1 must precede o2.
//If the ID of o1 is less, returns 1, placing o1 after o2. If the IDs are equal, it returns 0, maintaining the original order between the two events.
class EventIdDescendingComparator implements Comparator<Event>{

	@Override
	public int compare(Event o1, Event o2) {
		if(o1.getId() > o2.getId()) return -1;
		else if(o1.getId() < o2.getId()) return 1;
		else return 0;
	}
	
}

//This instead sorts the events by their name in ascending order.
//The compare method uses the compareTo method of the String class to compare event names.
//If the name of the first event (o1) is alphabetically before that of the second event (o2), it returns a negative value.
//If the name of o1 is after that of o2, returns a positive value. If the names are identical, returns 0.
class EventNameAscendingComparator implements Comparator<Event>{

	@Override
	public int compare(Event o1, Event o2) {
		return o1.getName().compareTo(o2.getName());
	}
	
}

//Finally, this sorts events by their name in descending order.
//The compare method compares the event names using compareTo and then multiplies the result by -1.
//This reverses the natural order, so that an event with a name that would be first in an ascending sort will now come after an event with a name that would be after in an ascending sort.
//If the name of o1 comes before that of o2, it returns a positive value. If o1 comes after o2, it returns a negative value. If the names are equal, returns 0.
class EventNameDescendingComparator implements Comparator<Event>{

	@Override
	public int compare(Event o1, Event o2) {
		return o1.getName().compareTo(o2.getName()) * -1;
	}

	
	
	
}
