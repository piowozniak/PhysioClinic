package pl.fizjogabinet.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pl.fizjogabinet.model.entity.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
	
	@Query(value="select * from patient where fullName like %:search%", nativeQuery = true)
	public List<Patient> findByName(@Param("search") String search);

}
