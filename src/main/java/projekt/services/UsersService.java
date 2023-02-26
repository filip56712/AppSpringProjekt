package projekt.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import projekt.models.Users;
import projekt.repositories.UsersRepository;

@Service
public class UsersService {
	@Autowired
	private UsersRepository repository;
	
	public <S extends Users> S save(S entity) {
		return repository.save(entity);
	}

	public Optional<Users> findById(Long id) {
		return repository.findById(id);
	}
	
	public List<Users> findAll() {
		return repository.findAll();
	}

	public void deleteById(Long id) {
		repository.deleteById(id);
	}

	public UsersService() {
	super();
	}

	public Users findByName(String name) {
		return repository.findByName(name);
	}
	
}
