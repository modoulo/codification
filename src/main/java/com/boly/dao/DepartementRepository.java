package com.boly.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.boly.entity.Departement;

public interface DepartementRepository extends JpaRepository<Departement, Long> {

}
