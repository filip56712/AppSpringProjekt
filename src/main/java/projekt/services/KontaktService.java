package projekt.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import projekt.models.Kontakt;
import projekt.repositories.KontaktRepository;

@Service
public class KontaktService {
	@Autowired
	private KontaktRepository repository;
	
	public <S extends Kontakt> S save(S entity) {
		return repository.save(entity);
	}

	public Optional<Kontakt> findById(Long id) {
		return repository.findById(id);
	}
	
	public List<Kontakt> findAll() {
		return repository.findAll();
	}

	public void deleteById(Long id) {
		repository.deleteById(id);
	}

	public KontaktService() {
	super();
	}


}
