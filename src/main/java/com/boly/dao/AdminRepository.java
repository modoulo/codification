package com.boly.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository; 

import com.boly.entity.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long> {
	public Optional<Admin> findTopByEmail(String email);
}
