package com.boly.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.boly.entity.Position;

public interface PositionRepository extends JpaRepository<Position, Long>{

}
