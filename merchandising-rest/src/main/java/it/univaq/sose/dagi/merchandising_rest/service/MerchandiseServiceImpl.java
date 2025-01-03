package it.univaq.sose.dagi.merchandising_rest.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import it.univaq.sose.dagi.merchandising_rest.dao.MerchandiseRepository;
import it.univaq.sose.dagi.merchandising_rest.model.Merchandise;

@Service
public class MerchandiseServiceImpl implements MerchandiseService {

	private final MerchandiseRepository merchandiseRepository;
	
	//@Autowired
	public MerchandiseServiceImpl(MerchandiseRepository merchandiseRepository) {
		this.merchandiseRepository = merchandiseRepository;
	}

	@Override
	public List<Merchandise> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Merchandise> getByEvent(long eventId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Merchandise save(Merchandise newMerch) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Merchandise update(Merchandise updatedMerch) throws IllegalArgumentException, NoSuchElementException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Merchandise addEventToMerch(Long eventId, Long merchId)
			throws IllegalArgumentException, NoSuchElementException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Merchandise> getPage(int page, String sortBy) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}

