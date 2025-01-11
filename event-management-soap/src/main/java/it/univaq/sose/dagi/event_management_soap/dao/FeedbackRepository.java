package it.univaq.sose.dagi.event_management_soap.dao;

import org.springframework.data.repository.ListCrudRepository;

import it.univaq.sose.dagi.event_management_soap.model.Feedback;
import java.util.List;


public interface FeedbackRepository extends ListCrudRepository<Feedback, Long> {
	List<Feedback> findByEventId(Long eventId);
}
