package it.univaq.sose.dagi.feedback_prosumer_rest.model;

import java.util.List;
import java.util.Map;

public class EventFeedbackReport {
	private List<Feedback> eventFeedbacks;
	private float averageRating;
	private float averageCustomerAge;
	private Map<String,Integer> keywordsCount;
	
	public EventFeedbackReport(List<Feedback> eventFeedbacks, float averageRating, float averageCustomerAge,
			Map<String, Integer> keywordsCount) {
		super();
		this.eventFeedbacks = eventFeedbacks;
		this.averageRating = averageRating;
		this.averageCustomerAge = averageCustomerAge;
		this.keywordsCount = keywordsCount;
	}
	public EventFeedbackReport() {
		super();
	}
	public List<Feedback> getEventFeedbacks() {
		return eventFeedbacks;
	}
	public void setEventFeedbacks(List<Feedback> eventFeedbacks) {
		this.eventFeedbacks = eventFeedbacks;
	}
	public float getAverageRating() {
		return averageRating;
	}
	public void setAverageRating(float averageRating) {
		this.averageRating = averageRating;
	}
	public float getAverageCustomerAge() {
		return averageCustomerAge;
	}
	public void setAverageCustomerAge(float averageCustomerAge) {
		this.averageCustomerAge = averageCustomerAge;
	}
	public Map<String, Integer> getKeywordsCount() {
		return keywordsCount;
	}
	public void setKeywordsCount(Map<String, Integer> keywordsCount) {
		this.keywordsCount = keywordsCount;
	}
	
	
	
}
