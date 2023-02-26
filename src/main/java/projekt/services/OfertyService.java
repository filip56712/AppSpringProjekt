package projekt.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import projekt.models.Oferty;
import projekt.repositories.OfertyRepository;

@Service
public class OfertyService {
	@Autowired
	private OfertyRepository repository;
	
	public <S extends Oferty> S save(S entity) {
		return repository.save(entity);
	}

	public Optional<Oferty> findById(Long id) {
		return repository.findById(id);
	}
	
	public List<Oferty> findAll() {
		return repository.findAllSQL();
	}
	
	public List<Oferty> findLast(){
		return repository.findLast();
	}
	
	public List<Oferty> findAktualne(){
		return repository.findAllWhereData_zakIsNull();
	}
	
	public List<Oferty> findZakonczonePoIdKupujacy(Long id){
		return repository.findAllWhereData_zakIsNotNullAndByIdKupujacy(id);
	}
	
	public List<Oferty> findByName(String name){
		return repository.findAllWhereNazwa_ksiazkiLikeName(name);
	}

	public void deleteById(Long id) {
		repository.deleteById(id);
	}

	public OfertyService() {
	super();
	}

	public List<Oferty> findZakonczonePoIdSprzedajacy(Long id) {
		return repository.findAllWhereData_zakIsNotNullAndByIdSprzedajacy(id);
	}

	public List<Oferty> findAllByIdSprzedajacy(Long currentUserId) {
		return repository.findAllByIdSprzedajacy(currentUserId);
	}
}
