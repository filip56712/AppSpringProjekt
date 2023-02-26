package projekt.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import projekt.models.Wysylka;
import projekt.repositories.WysylkaRepository;

@Service
public class WysylkaService {
	@Autowired
	private WysylkaRepository repository;
	
	public <S extends Wysylka> S save(S entity) {
		return repository.save(entity);
	}

	public Optional<Wysylka> findById(Long id) {
		return repository.findById(id);
	}
	
	public List<Wysylka> findByIdKupujacy(Long id) {
		return repository.findAllByIdKupujacy(id);
	}
	
	public List<Wysylka> findAll() {
		return repository.findAll();
	}

	public void deleteById(Long id) {
		repository.deleteById(id);
	}

	public WysylkaService() {
	super();
	}

	public List<Wysylka> findByIdSprzedajacy(Long l) {
		return repository.findAllByIdSprzedajacy(l);
	}

	public Wysylka findByIdOferty(Long id) {
		return repository.findAllByIdOferty(id);
	}


}
