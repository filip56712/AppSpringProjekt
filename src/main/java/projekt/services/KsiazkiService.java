package projekt.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import projekt.models.Ksiazki;
import projekt.repositories.KsiazkiRepository;

@Service
public class KsiazkiService {
	@Autowired
	private KsiazkiRepository repository;
	
	public <S extends Ksiazki> S save(S entity) {
		return repository.save(entity);
	}

	public Optional<Ksiazki> findById(Long id) {
		return repository.findById(id);
	}
	
	public List<Ksiazki> findAll() {
		return repository.findAllSQL();
	}
	
	public List<Ksiazki> findLast() {
		return repository.findLast();
	}

	public void deleteById(Long id) {
		repository.deleteById(id);
	}

	public KsiazkiService() {
	super();
	}

	public List<Ksiazki> findByName(String query) {
		return repository.findAllWhereNazwaLikeName(query);
	}


}
