package com.laros.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.laros.api.model.Persona;
import com.laros.api.repository.persona.PersonaRepositoryQuery;

public interface PersonaRepository extends JpaRepository<Persona, Long>, PersonaRepositoryQuery {

}
