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
	
	//This method compares the current Feedback object with another object to determine if they are equal.
	//It first checks if the other object is null or not an instance of Feedback. If so, it returns false.
	//If the object is of type Feedback, it casts it and compares the id fields of both Feedback instances.
	//It returns true if the IDs are equal; otherwise, it returns false.
	@Override
	public boolean equals(Object obj) {
		if(obj == null || !(obj instanceof Feedback)) return false;
		Feedback casted = (Feedback) obj;
		return this.getId().equals(casted.getId());
	}
}
