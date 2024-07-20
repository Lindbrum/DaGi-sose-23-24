package it.univaq.sose.dagi.feedback_prosumer_rest.model;

public class Feedback {

	private Long id;
	private Long userId;
	private Long eventId;
	private int rating;
	private String body;
	
	public Feedback(Long id, Long userId, Long eventId, int rating, String body) {
		super();
		this.id = id;
		this.userId = userId;
		this.eventId = eventId;
		this.rating = rating;
		this.body = body;
	}
	
	public Feedback(Long userId, Long eventId, int rating, String body) {
		super();
		this.userId = userId;
		this.eventId = eventId;
		this.rating = rating;
		this.body = body;
	}
	
	public Feedback() {
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
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	
	
}
