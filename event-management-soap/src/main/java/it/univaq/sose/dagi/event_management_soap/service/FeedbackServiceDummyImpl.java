package it.univaq.sose.dagi.event_management_soap.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import it.univaq.sose.dagi.event_management_soap.model.Feedback;

@Service
public class FeedbackServiceDummyImpl implements FeedbackService {

	private static List<Feedback> feedbacks = new ArrayList<>();
	private static long nextId = 0L;
	
	//This constructor initializes the class by creating a series of Feedback objects with predefined data and adds them to the feedbacks list.
	public FeedbackServiceDummyImpl() {
		Feedback f0 = new Feedback(nextId++, 0L, 0L, 5, "Ho conosciuto Kurolily, 5/5!");
		Feedback f1 = new Feedback(nextId++, 2L, 0L, 4, "Li seguo su twitch da tempo, ve li consiglio.");
		Feedback f2 = new Feedback(nextId++, 1L, 2L, 5, "Ne è valsa la pensa prenotare mesi in anticipo, mi so beccato la seconda fila.");
		Feedback f3 = new Feedback(nextId++, 4L, 2L, 5, "Bellissimo, bravo Antonello e agli organizzatori.");
		Feedback f4 = new Feedback(nextId++, 0L, 1L, 5, "Prima volta che vedo una campagna di D&D dal vivo e anche da spettatore mi so divertito un mondo.");
		Feedback f5 = new Feedback(nextId++, 2L, 1L, 5, "Ovviamente il barbaro di Simone Fancazzista il mio preferito.");
		Feedback f6 = new Feedback(nextId++, 3L, 2L, 3, "Molto bello, solo che sono rimasta in piedi le prime ore.");
		
		feedbacks.add(f0);
		feedbacks.add(f1);
		feedbacks.add(f2);
		feedbacks.add(f3);
		feedbacks.add(f4);
		feedbacks.add(f5);
		feedbacks.add(f6);
	}
	
	//This method creates a new feedback, assigning it a unique ID and adding it to the feedbacks list. Returns the created feedback.
	@Override
	public Feedback create(Feedback newFeedback) {
		newFeedback.setId(nextId++);
		feedbacks.add(newFeedback);
		return newFeedback;
	}

	//This method updates existing feedback. If the feedback ID is null, throw an exception.
	//If the feedback to be updated is not found in the list, throw NoSuchElementException. It returns updated feedback.
	@Override
	public Feedback update(Feedback feedback) throws IllegalArgumentException, NoSuchElementException {
		if(feedback.getId() == null) {
			throw new IllegalArgumentException("ID cannot be null.");
		}
		Feedback found = null;
		for(Feedback current : feedbacks) {
			if(current.equals(feedback)) {
				current.setUserId(feedback.getUserId());
				current.setEventId(feedback.getEventId());
				current.setRating(feedback.getRating());
				current.setBody(feedback.getBody());
				
				found = current;
				break;
			}
		}
		if(found == null) {
			throw new NoSuchElementException("Feedback to update not found.");
		}
		return found;
	}

	//This method removes a feedback from the list. If the feedback ID is null, throw an exception.
	//If feedback is not found, throw NoSuchElementException.
	@Override
	public void delete(Feedback feedback) throws IllegalArgumentException, NoSuchElementException {
		if(feedback.getId() == null) {
			throw new IllegalArgumentException("ID cannot be null.");
		}
		for(Iterator<Feedback> itr = feedbacks.listIterator(); itr.hasNext();) {
			Feedback current = itr.next();
			if(current.equals(feedback)) {
				itr.remove();
				return;
			}
		}
		throw new NoSuchElementException("Feedback to delete not found.");
	}

	//This method searches for and returns feedback by ID. If feedback is not found, throw NoSuchElementException.
	@Override
	public Feedback findById(long id) throws NoSuchElementException {
		for(Feedback current : feedbacks) {
			if(current.getId().equals(id)) {
				return current;
			}
		}
		throw new NoSuchElementException("Feedback not found.");
	}

	//This method returns a copy of the list of all available feedback.
	@Override
	public List<Feedback> getAll() {
		return List.copyOf(feedbacks);
	}

	//This method returns a list of feedback associated with a specific event, identified by eventId.
	@Override
	public List<Feedback> findByEventId(long eventId) {
		List<Feedback> result = new ArrayList<>();
		for(Feedback current : feedbacks) {
			if(current.getEventId().equals(eventId)) {
				result.add(current);
			}
		}
		return result;
	}

}
