package com.laros.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.laros.api.model.Estado;


/*
 * 24.2. Criando pesquisa de estados e cidades
 * */
public interface EstadoRepository extends JpaRepository<Estado, Long> {

}