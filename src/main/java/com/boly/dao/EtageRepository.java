package com.boly.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.boly.entity.Etage;

public interface EtageRepository extends JpaRepository<Etage, Long> {
	
}
