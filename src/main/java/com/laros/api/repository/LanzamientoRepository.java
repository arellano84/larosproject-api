package com.laros.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.laros.api.model.Lanzamiento;
import com.laros.api.repository.lanzamiento.LanzamientoRepositoryQuery;

public interface LanzamientoRepository extends JpaRepository<Lanzamiento, Long>, LanzamientoRepositoryQuery {

}
