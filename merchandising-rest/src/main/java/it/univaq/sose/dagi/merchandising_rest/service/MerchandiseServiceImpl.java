package it.univaq.sose.dagi.merchandising_rest.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import it.univaq.sose.dagi.merchandising_rest.dao.MerchandiseRepository;
import it.univaq.sose.dagi.merchandising_rest.model.Merchandise;

@Service
public class MerchandiseServiceImpl implements MerchandiseService {

	private final int MERCHS_PER_PAGE;
	
	private final MerchandiseRepository merchandiseRepository;

	// @Autowired
	public MerchandiseServiceImpl(MerchandiseRepository merchandiseRepository
			, @Value("${service.merchandise.catalogue.items-per-page}") int merchPerPage) {
		
		this.merchandiseRepository = merchandiseRepository;
		this.MERCHS_PER_PAGE = merchPerPage;
	}

	@Override
	public List<Merchandise> getAll() {
		return this.merchandiseRepository.findAll();
	}

	@Override
	public List<Merchandise> getByEvent(long eventId) {

		return this.merchandiseRepository.findAllByEventId(eventId);
	}

	@Override
	public Merchandise save(Merchandise newMerch) {

		return this.merchandiseRepository.save(newMerch);
	}

	@Override
	public Merchandise update(Merchandise updatedMerch) throws IllegalArgumentException, NoSuchElementException {

		if (updatedMerch.getId() != null) {
			Optional<Merchandise> found = this.merchandiseRepository.findById(updatedMerch.getId());
			if (found.isPresent()) {
				return this.merchandiseRepository.save(updatedMerch);
			} else {
				throw new NoSuchElementException("No record with this ID exists."); //id not found
			}
		} else {
			throw new IllegalArgumentException("ID is null."); //id was null
		}
	}

	@Override
	public Merchandise addEventToMerch(Long eventId, Long merchId)
			throws IllegalArgumentException, NoSuchElementException {
		
		if (merchId != null) {
			Optional<Merchandise> found = this.merchandiseRepository.findById(merchId);
			if (found.isPresent()) {
				Merchandise updatedMerch = found.get();
				updatedMerch.setEventId(eventId);
				return this.merchandiseRepository.save(updatedMerch);
			} else {
				throw new NoSuchElementException("No record with this ID exists."); //id not found
			}
		} else {
			throw new IllegalArgumentException("ID is null."); //id was null
		}
	}

	@Override
	public List<Merchandise> getPage(int page, String sortBy) {
		Sort sortingMethod;
		if (sortBy.equals(SortingMode.ID_DESC.name())) {
			sortingMethod = Sort.by("id").descending();
		} else if (sortBy.equals(SortingMode.ID_ASC.name())) {
			sortingMethod = Sort.by("id").ascending(); //Ascending ID is natural order.
		} else if (sortBy.equals(SortingMode.ALPHABETICAL_DESC.name())) {
			sortingMethod = Sort.by("name").descending();
		} else if (sortBy.equals(SortingMode.ALPHABETICAL_ASC.name())){
			sortingMethod = Sort.by("name").ascending();
		}else {
			System.err.println("\n\n\nWARNING: An invalid sorting method was provided, defaulting to ID_DESC.\n\n\n");
			sortingMethod = Sort.by("id").descending();
		}
		
		Pageable pageable = PageRequest.of(page - 1, MERCHS_PER_PAGE, sortingMethod); //page is zero indexed
		Page<Merchandise> result = this.merchandiseRepository.findAll(pageable);
		return result.getContent();
	}

}
