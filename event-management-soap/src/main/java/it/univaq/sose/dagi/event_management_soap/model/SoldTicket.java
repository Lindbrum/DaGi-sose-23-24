package it.univaq.sose.dagi.event_management_soap.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "sold_ticket")
public class SoldTicket {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private Long userId;
	@Column(nullable = false)
	private Long eventId;
	@Column(nullable = false)
	private LocalDateTime referenceDate;
	
	public SoldTicket(Long id, Long userId, Long eventId, LocalDateTime referenceDate) {
		super();
		this.id = id;
		this.userId = userId;
		this.eventId = eventId;
		this.referenceDate = referenceDate;
	}
	
	public SoldTicket(Long userId, Long eventId, LocalDateTime referenceDate) {
		super();
		this.userId = userId;
		this.eventId = eventId;
		this.referenceDate = referenceDate;
	}

	public SoldTicket() {
		super();
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getEventId() {
		return eventId;
	}
	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}
	public LocalDateTime getReferenceDate() {
		return referenceDate;
	}
	public void setReferenceDate(LocalDateTime referenceDate) {
		this.referenceDate = referenceDate;
	}
	
	//This method compares whether two SoldTicket objects are equal.
	//Checks whether the passed object is null or not of type SoldTicket; in that case, it returns false.
	//If it is of the correct type, it compares the IDs of the two objects and returns true if they are equal, false otherwise.
	@Override
	public boolean equals(Object obj) {
		if(obj == null || !(obj instanceof SoldTicket)) return false;
		SoldTicket casted = (SoldTicket) obj;
		return this.getId().equals(casted.getId());
	}
}
