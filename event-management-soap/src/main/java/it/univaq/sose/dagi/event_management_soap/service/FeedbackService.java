package it.univaq.sose.dagi.event_management_soap.service;

import java.util.List;
import java.util.NoSuchElementException;

import it.univaq.sose.dagi.event_management_soap.model.Feedback;

public interface FeedbackService {
	public Feedback create(Feedback newFeedback);
	public Feedback update(Feedback feedback) throws IllegalArgumentException, NoSuchElementException;
	public void delete(Feedback feedback) throws IllegalArgumentException, NoSuchElementException;
	public Feedback findById(long id) throws NoSuchElementException;
	public List<Feedback> getAll();
	public List<Feedback> findByEventId(long eventId);
}
