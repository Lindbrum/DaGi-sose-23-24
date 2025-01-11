package it.univaq.sose.dagi.event_management_soap.dao;

import org.springframework.data.repository.ListCrudRepository;

import it.univaq.sose.dagi.event_management_soap.model.TicketInfo;
import java.util.List;


public interface TicketInfoRepository extends ListCrudRepository<TicketInfo, Long> {
	List<TicketInfo> findByEventId(Long eventId);
}
