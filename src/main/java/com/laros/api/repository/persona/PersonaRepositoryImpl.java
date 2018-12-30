package com.laros.api.repository.persona;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import com.laros.api.model.Persona;
import com.laros.api.model.Persona_;
import com.laros.api.repository.filter.PersonaFilter;

/*
 * 7.7. Desafio: Pesquisa de pessoa
 * */
public class PersonaRepositoryImpl implements PersonaRepositoryQuery{

	@PersistenceContext
	private EntityManager manager;  
	
	@Override
	public List<Persona> filtrar(PersonaFilter personaFilter) {
		
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Persona> criteria = builder.createQuery(Persona.class);
		
		Root<Persona> root = criteria.from(Persona.class);
		
		//Crear las Restricciones
		Predicate[] predicates = crearRestriciones(personaFilter, builder, root);
		criteria.where(predicates);
		
		TypedQuery<Persona> query = manager.createQuery(criteria);
//		adicionarRestriccionesDePaginacion(query, pageable);
//		Long total = totalRegistros(personaFilter);
		
		return query.getResultList();
//		return new PageImpl<>(query.getResultList(), pageable, total);
	}	
	
	private Predicate[] crearRestriciones(PersonaFilter personaFilter, CriteriaBuilder builder,
			Root<Persona> root) {
		
		List<Predicate> predicates = new ArrayList<>();
		
		if(!StringUtils.isEmpty(personaFilter.getNombre())) {
			predicates.add(
					builder.like(
							builder.lower(root.get(Persona_.nombre)), 
							"%" + personaFilter.getNombre().toLowerCase() + "%")
					);
		}
		
		if(!StringUtils.isEmpty(personaFilter.getDocumento())) {
			predicates.add(
					builder.like(
							builder.lower(root.get(Persona_.documento)), 
							"%" + personaFilter.getDocumento().toLowerCase() + "%")
					);
		}
		
		return predicates.toArray(new Predicate[predicates.size()]);
	}
	

//	private void adicionarRestriccionesDePaginacion(TypedQuery<?> query, Pageable pageable) {
//		int paginaActual = pageable.getPageNumber();
//		int totalRegistrosPorPagina = pageable.getPageSize();
//		int primerRegistroDePagina = paginaActual * totalRegistrosPorPagina;
//		query.setFirstResult(primerRegistroDePagina);
//		query.setMaxResults(totalRegistrosPorPagina);
//	}
//	
//	private Long totalRegistros(PersonaFilter personaFilter) {
//		CriteriaBuilder builder = manager.getCriteriaBuilder();
//		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
//		Root<Persona> root = criteria.from(Persona.class);
//		
//		//Crear las Restricciones
//		Predicate[] predicates = crearRestriciones(personaFilter, builder, root);
//		criteria.where(predicates);
//		
//		criteria.select(builder.count(root));
//		return manager.createQuery(criteria).getSingleResult();
//	}

}
