package projekt.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import projekt.models.Ksiazki;
@Repository
public interface KsiazkiRepository extends JpaRepository<Ksiazki, Long> {
	List<Ksiazki> findAllSQL();
	List<Ksiazki> findLast();
	
	@Query("SELECT ksiazki FROM Ksiazki ksiazki WHERE ksiazki.nazwa ILIKE %:query%")
	List<Ksiazki> findAllWhereNazwaLikeName(@Param("query") String query);
}
