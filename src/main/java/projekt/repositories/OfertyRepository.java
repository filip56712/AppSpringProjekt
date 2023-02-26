package projekt.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import projekt.models.Oferty;

@Repository
public interface OfertyRepository extends JpaRepository<Oferty, Long> {
	
	List<Oferty> findAllSQL();
	List<Oferty> findLast();

	@Query("SELECT oferty FROM Oferty oferty WHERE oferty.data_zak IS NULL")
	List<Oferty> findAllWhereData_zakIsNull();
	
	@Query("SELECT oferty FROM Oferty oferty WHERE oferty.data_zak IS NOT NULL AND oferty.kupujacy=:id_podane")
	List<Oferty> findAllWhereData_zakIsNotNullAndByIdKupujacy(@Param("id_podane") Long id_podane);
	
	@Query("SELECT oferty FROM Oferty oferty WHERE oferty.data_zak IS NOT NULL AND oferty.sprzedajacy=:id_podane")
	List<Oferty> findAllWhereData_zakIsNotNullAndByIdSprzedajacy(@Param("id_podane") Long id_podane);
	
	@Query("SELECT oferty FROM Oferty oferty WHERE oferty.sprzedajacy=:id_podane")
	List<Oferty> findAllByIdSprzedajacy(@Param("id_podane") Long id_podane);
	
	@Query("SELECT oferty FROM Oferty oferty WHERE oferty.Nazwa_ksiazki ILIKE %:name% AND oferty.data_zak IS NULL")
	List<Oferty> findAllWhereNazwa_ksiazkiLikeName(@Param("name") String name);

}
