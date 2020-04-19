package com.laros.api.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.laros.api.model.Lanzamiento;
import com.laros.api.repository.lanzamiento.LanzamientoRepositoryQuery;

public interface LanzamientoRepository extends JpaRepository<Lanzamiento, Long>, LanzamientoRepositoryQuery {
	
	/*
	 * 22.20. Buscando lançamentos vencidos com Spring Data JPA
	 * */
	List<Lanzamiento> findByFechaVencimientoLessThanEqualAndFechaPagoIsNull(LocalDate fecha); //El nombre es como una query.

}
