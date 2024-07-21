package it.univaq.sose.dagi.merchandising_rest;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import it.univaq.sose.dagi.merchandising_rest.model.Merchandise;
import it.univaq.sose.dagi.merchandising_rest.service.MerchandiseService;
import it.univaq.sose.dagi.merchandising_rest.service.MerchandiseServiceDummyImpl;

public class MerchandiseApiImpl implements MerchandiseApi {

	// Service (injected)
	private final MerchandiseService merchandiseService;

	// @Autowired is inferred by Spring Boot when there is a single public
	// constructor
	//@Autowired
	public MerchandiseApiImpl(MerchandiseServiceDummyImpl merchandiseServiceImpl) {
			this.merchandiseService = merchandiseServiceImpl;
		}

	@Override
	public ResponseEntity<List<Merchandise>> getAll() {
		List<Merchandise> merchList = merchandiseService.getAll();
		return new ResponseEntity<List<Merchandise>>(merchList, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<List<Merchandise>> getEventMerchandise(Long eventId) {
		List<Merchandise> merchList = merchandiseService.getByEvent(eventId);
		return new ResponseEntity<List<Merchandise>>(merchList, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Merchandise> create(Merchandise newMerch) {
		Merchandise merchWithId = merchandiseService.save(newMerch);
		return new ResponseEntity<Merchandise>(merchWithId, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Merchandise> addToEvent(Long eventId, Long merchId) {
		Merchandise updatedMerch = merchandiseService.addEventToMerch(eventId, merchId);
		return new ResponseEntity<Merchandise>(updatedMerch, HttpStatus.OK);
	}

}
