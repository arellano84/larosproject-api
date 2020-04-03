package com.laros.api.repository.persona;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.laros.api.model.Persona;
import com.laros.api.repository.filter.PersonaFilter;

/*
 * 7.7. Desafio: Pesquisa de pessoa
 * */
public interface PersonaRepositoryQuery {
	
	public List<Persona> filtrar(PersonaFilter personaFilter);
	
	/*
	 * Proyeccion
	 * */
	public Page<Persona> filtrarPaginando(PersonaFilter personaFilter, Pageable pageable);
	
}
