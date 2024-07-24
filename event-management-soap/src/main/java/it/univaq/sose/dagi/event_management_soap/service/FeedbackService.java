package it.univaq.sose.dagi.event_management_soap.service;

import java.util.List;
import java.util.NoSuchElementException;

import it.univaq.sose.dagi.event_management_soap.model.Feedback;

public interface FeedbackService {
	//This method adds new feedback. Returns the newly created feedback.
	public Feedback create(Feedback newFeedback);
	//This method modifies existing feedback. If the feedback to update is not found, raise NoSuchElementException. If the feedback has invalid data, raise IllegalArgumentException. Returns updated feedback.
	public Feedback update(Feedback feedback) throws IllegalArgumentException, NoSuchElementException;
	//This method removes feedback from the collection. Raises IllegalArgumentException if the feedback has invalid data and NoSuchElementException if the feedback is not found.
	public void delete(Feedback feedback) throws IllegalArgumentException, NoSuchElementException;
	//This method searches for feedback by ID and returns it. If the feedback doesn't exist, raise NoSuchElementException.
	public Feedback findById(long id) throws NoSuchElementException;
	//This method returns a list of all available feedback.
	public List<Feedback> getAll();
	//This method returns a list of feedback associated with a specific event, identified by eventId.
	public List<Feedback> findByEventId(long eventId);
}
