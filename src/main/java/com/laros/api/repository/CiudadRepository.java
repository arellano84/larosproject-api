package com.laros.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.laros.api.model.Ciudad;


/*
 * 24.2. Criando pesquisa de estados e cidades
 * */
public interface CiudadRepository extends JpaRepository<Ciudad, Long> {
	
	List<Ciudad> findByEstadoCodigo(Long estadoCodigo);

}