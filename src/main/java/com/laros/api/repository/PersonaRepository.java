package com.laros.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.laros.api.model.Persona;

public interface PersonaRepository extends JpaRepository<Persona, Long>{

}
