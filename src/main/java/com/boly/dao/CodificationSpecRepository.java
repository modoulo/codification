package com.boly.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.boly.entity.CodificationSpec;

public interface CodificationSpecRepository extends JpaRepository<CodificationSpec, Long>{
	@Query("SELECT c FROM CodificationSpec c	WHERE c.dateDeCreation IN (SELECT max(dateDeCreation) FROM CodificationSpec)")	
	Optional<CodificationSpec> getLastCodificationSpec();
}
