package com.boly.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.boly.entity.Codification;
import com.boly.entity.CodificationSpec;
import com.boly.entity.Etudiant;
import com.boly.entity.Position;

public interface CodificationRepository extends JpaRepository<Codification, Long>{
	Optional<Codification> findTopByEtudiantAndCodificationSpecOrderByDateDeReservationDesc(Etudiant etudiant, CodificationSpec codifSpec);
	Optional<Codification> findTopByPositionAndCodificationSpecOrderByDateDeReservationDesc(Position position, CodificationSpec codifSpec);
}
