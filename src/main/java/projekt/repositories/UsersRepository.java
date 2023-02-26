package projekt.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import projekt.models.Users;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
	Users findByName(String name);
}
