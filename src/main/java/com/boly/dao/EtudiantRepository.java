package com.boly.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.boly.entity.Etudiant;

public interface EtudiantRepository extends JpaRepository<Etudiant, Long>{
	public Optional<Etudiant> findTopByEmail(String email);
}
