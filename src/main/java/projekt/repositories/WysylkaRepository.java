package projekt.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import projekt.models.Wysylka;

@Repository
public interface WysylkaRepository extends JpaRepository<Wysylka, Long> {
	
	@Query("SELECT wysylka FROM Wysylka wysylka WHERE wysylka.kupujacy=:id_podane")
	List<Wysylka> findAllByIdKupujacy(@Param("id_podane") Long id_podane);
	
	@Query("SELECT wysylka FROM Wysylka wysylka WHERE wysylka.sprzedajacy=:id_podane")
	List<Wysylka> findAllByIdSprzedajacy(@Param("id_podane") Long id_podane);

	@Query("SELECT wysylka FROM Wysylka wysylka WHERE wysylka.oferta_id=:id_podane")
	Wysylka findAllByIdOferty(@Param("id_podane") Long id_podane);

}
