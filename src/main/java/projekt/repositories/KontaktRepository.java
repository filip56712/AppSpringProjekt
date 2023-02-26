package projekt.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import projekt.models.Kontakt;

@Repository
public interface KontaktRepository extends JpaRepository<Kontakt, Long> {
	
}
