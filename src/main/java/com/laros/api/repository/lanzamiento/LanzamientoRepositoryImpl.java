package com.laros.api.repository.lanzamiento;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.util.StringUtils;

import com.laros.api.model.Lanzamiento;
import com.laros.api.model.Lanzamiento_;
import com.laros.api.repository.filter.LanzamientoFilter;

public class LanzamientoRepositoryImpl implements LanzamientoRepositoryQuery{

	@PersistenceContext
	private EntityManager manager;  
	
	@Override
	public List<Lanzamiento> filtrar(LanzamientoFilter lanzamientoFilter) {
		
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Lanzamiento> criteria = builder.createQuery(Lanzamiento.class);
		
		Root<Lanzamiento> root = criteria.from(Lanzamiento.class);
		
		//Crear las Restricciones
		Predicate[] predicates = crearRestriciones(lanzamientoFilter, builder, root);
		criteria.where(predicates);
		
		TypedQuery<Lanzamiento> query = manager.createQuery(criteria);
		
		return query.getResultList();
	}

	private Predicate[] crearRestriciones(LanzamientoFilter lanzamientoFilter, CriteriaBuilder builder,
			Root<Lanzamiento> root) {
		
		List<Predicate> predicates = new ArrayList<>();
		
		if(!StringUtils.isEmpty(lanzamientoFilter.getDescripcion())) {
			predicates.add(
					builder.like(
							builder.lower(root.get(Lanzamiento_.descripcion)), 
							"%" + lanzamientoFilter.getDescripcion().toLowerCase() + "%")
					);
		}
		
		if(lanzamientoFilter.getFechaVencimientoDe() != null) {
			predicates.add(
					builder.greaterThanOrEqualTo(
							root.get(Lanzamiento_.fechaVencimiento), 
							lanzamientoFilter.getFechaVencimientoDe()));
		}
		
		if(lanzamientoFilter.getFechaVencimientoHasta() != null) {
			predicates.add(
					builder.lessThanOrEqualTo(
							root.get(Lanzamiento_.fechaVencimiento), 
							lanzamientoFilter.getFechaVencimientoHasta()));
		}
		
		return predicates.toArray(new Predicate[predicates.size()]);
	}

}
