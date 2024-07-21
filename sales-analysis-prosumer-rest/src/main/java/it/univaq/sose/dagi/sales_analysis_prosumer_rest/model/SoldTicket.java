package it.univaq.sose.dagi.sales_analysis_prosumer_rest.model;

import java.time.LocalDateTime;

public class SoldTicket {
	
	private Long id;
	private Long userId;
	private Long eventId;
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
	
	
}