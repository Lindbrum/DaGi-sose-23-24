package it.univaq.sose.dagi.event_management_soap.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.stereotype.Service;

import it.univaq.sose.dagi.event_management_soap.dao.FeedbackRepository;
import it.univaq.sose.dagi.event_management_soap.model.Feedback;

@Service
public class FeedbackServiceImpl implements FeedbackService {

	private final FeedbackRepository feedbackRepository;
	
	
	
	public FeedbackServiceImpl(FeedbackRepository feedbackRepository) {
		this.feedbackRepository = feedbackRepository;
	}

	@Override
	public Feedback create(Feedback newFeedback) {
		
		return this.feedbackRepository.save(newFeedback);
	}

	@Override
	public Feedback update(Feedback feedback) throws IllegalArgumentException, NoSuchElementException {
		if(feedback.getId() != null) {
			Optional<Feedback> found = this.feedbackRepository.findById(feedback.getId());
			if(found.isPresent()) {
				return this.feedbackRepository.save(feedback);
			}else {
				throw new NoSuchElementException("Feedback to update not found.");
			}
		}else {
			throw new IllegalArgumentException("ID cannot be null.");
		}
	}

	@Override
	public void delete(Feedback feedback) throws IllegalArgumentException, NoSuchElementException {
		this.feedbackRepository.delete(feedback);

	}

	@Override
	public Feedback findById(long id) throws NoSuchElementException {
		
		return this.feedbackRepository.findById(id).orElseThrow(()->{throw new NoSuchElementException("Feedback not found.");});
	}

	@Override
	public List<Feedback> getAll() {
		
		return this.feedbackRepository.findAll();
	}

	@Override
	public List<Feedback> findByEventId(long eventId) {
		
		return this.feedbackRepository.findByEventId(eventId);
	}

}
