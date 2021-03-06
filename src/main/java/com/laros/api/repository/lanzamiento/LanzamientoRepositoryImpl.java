package com.laros.api.repository.lanzamiento;

import java.time.LocalDate;
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

import com.laros.api.dto.MovimientoEstadisticaCategoria;
import com.laros.api.dto.MovimientoEstadisticaDia;
import com.laros.api.dto.MovimientoEstadisticaPersona;
import com.laros.api.model.Categoria_;
import com.laros.api.model.Lanzamiento;
import com.laros.api.model.Lanzamiento_;
import com.laros.api.model.Persona_;
import com.laros.api.repository.filter.LanzamientoFilter;
import com.laros.api.repository.projection.ResumenLanzamiento;

public class LanzamientoRepositoryImpl implements LanzamientoRepositoryQuery{

	@PersistenceContext
	private EntityManager manager;  
	

	/*
	 * 22.12. Criando a consulta do relatório
	 * */
	@Override
	public List<MovimientoEstadisticaPersona> porPersona(LocalDate inicio, LocalDate fin) {
		
		CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
		CriteriaQuery<MovimientoEstadisticaPersona> criteriaQuery = criteriaBuilder.createQuery(MovimientoEstadisticaPersona.class);
		
		//Buscamos los datos en Lanzamiento.
		Root<Lanzamiento> root = criteriaQuery.from(Lanzamiento.class);
		
		//Construimos objeto
		criteriaQuery.select(
				criteriaBuilder.construct(MovimientoEstadisticaPersona.class,
						root.get(Lanzamiento_.tipo),
						root.get(Lanzamiento_.persona),
						criteriaBuilder.sum(root.get(Lanzamiento_.valor)))
				);
		
		//consulta por rango de fechas.
		criteriaQuery.where(
					criteriaBuilder.greaterThanOrEqualTo(root.get(Lanzamiento_.fechaVencimiento), inicio),
					criteriaBuilder.lessThanOrEqualTo(root.get(Lanzamiento_.fechaVencimiento), fin)
				);
		
		//Agrupamos por tipo y fecha vencimiento
		criteriaQuery.groupBy(root.get(Lanzamiento_.tipo), root.get(Lanzamiento_.fechaVencimiento));
		
		//Genera query 
		TypedQuery<MovimientoEstadisticaPersona> typedQuery = manager.createQuery(criteriaQuery);
		
		//Devolvemos resultado
		return typedQuery.getResultList();
	}
	
	/*
	 * 22.4. Criando consulta para dados por dia
	 * */
	@Override
	public List<MovimientoEstadisticaDia> porDia(LocalDate mesReferencia) {
		
		CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
		CriteriaQuery<MovimientoEstadisticaDia> criteriaQuery = criteriaBuilder.createQuery(MovimientoEstadisticaDia.class);
		
		//Buscamos los datos en Lanzamiento.
		Root<Lanzamiento> root = criteriaQuery.from(Lanzamiento.class);
		
		//Construimos objeto
		criteriaQuery.select(
				criteriaBuilder.construct(MovimientoEstadisticaDia.class,
						root.get(Lanzamiento_.tipo),
						root.get(Lanzamiento_.fechaVencimiento),
						criteriaBuilder.sum(root.get(Lanzamiento_.valor)))
				);
		
		//Obtenemos primer y ultimo día del mes
		LocalDate primerDia = mesReferencia.withDayOfMonth(1);
		LocalDate ultimoDia = mesReferencia.withDayOfMonth(mesReferencia.lengthOfMonth());
		
		//Fecha mayor o igual a ultimo y primer día.
		criteriaQuery.where(
					criteriaBuilder.greaterThanOrEqualTo(root.get(Lanzamiento_.fechaVencimiento), primerDia),
					criteriaBuilder.lessThanOrEqualTo(root.get(Lanzamiento_.fechaVencimiento), ultimoDia)
				);
		
		//Agrupamos por tipo y fecha vencimiento
		criteriaQuery.groupBy(root.get(Lanzamiento_.tipo), root.get(Lanzamiento_.fechaVencimiento));
		
		//Genera query 
		TypedQuery<MovimientoEstadisticaDia> typedQuery = manager.createQuery(criteriaQuery);
		
		//Devolvemos resultado
		return typedQuery.getResultList();
	}
	
	/*
	 * 22.2. Criando consulta para dados por categoria
	 * */
	@Override
	public List<MovimientoEstadisticaCategoria> porCategoria(LocalDate mesReferencia) {
		
		CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
		CriteriaQuery<MovimientoEstadisticaCategoria> criteriaQuery = criteriaBuilder.createQuery(MovimientoEstadisticaCategoria.class);
		
		//Buscamos los datos en Lanzamiento.
		Root<Lanzamiento> root = criteriaQuery.from(Lanzamiento.class);
		
		//Construimos objeto
		criteriaQuery.select(
				criteriaBuilder.construct(MovimientoEstadisticaCategoria.class,
						root.get(Lanzamiento_.categoria), 
						criteriaBuilder.sum(root.get(Lanzamiento_.valor)))
				);
		
		//Obtenemos primer y ultimo día del mes
		LocalDate primerDia = mesReferencia.withDayOfMonth(1);
		LocalDate ultimoDia = mesReferencia.withDayOfMonth(mesReferencia.lengthOfMonth());
		
		//Fecha mayor o igual a ultimo y primer día.
		criteriaQuery.where(
					criteriaBuilder.greaterThanOrEqualTo(root.get(Lanzamiento_.fechaVencimiento), primerDia),
					criteriaBuilder.lessThanOrEqualTo(root.get(Lanzamiento_.fechaVencimiento), ultimoDia)
				);
		
		//Agrupamos por categoria
		criteriaQuery.groupBy(root.get(Lanzamiento_.categoria));
		
		//Genera query 
		TypedQuery<MovimientoEstadisticaCategoria> typedQuery = manager.createQuery(criteriaQuery);
		
		//Devolvemos resultado
		return typedQuery.getResultList();
	}
	
	@Override
	public Page<Lanzamiento> filtrar(LanzamientoFilter lanzamientoFilter, Pageable pageable) {
		
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Lanzamiento> criteria = builder.createQuery(Lanzamiento.class);
		
		Root<Lanzamiento> root = criteria.from(Lanzamiento.class);
		
		//Crear las Restricciones
		Predicate[] predicates = crearRestriciones(lanzamientoFilter, builder, root);
		criteria.where(predicates);
		
		TypedQuery<Lanzamiento> query = manager.createQuery(criteria);
		adicionarRestriccionesDePaginacion(query, pageable);
		Long total = totalRegistros(lanzamientoFilter);
		
//		return query.getResultList();
		return new PageImpl<>(query.getResultList(), pageable, total);
	}
	
	@Override
	public Page<ResumenLanzamiento> resumir(LanzamientoFilter lanzamientoFilter, Pageable pageable) {
		
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<ResumenLanzamiento> criteria = builder.createQuery(ResumenLanzamiento.class);
		Root<Lanzamiento> root = criteria.from(Lanzamiento.class);
		
		criteria.select(builder.construct(ResumenLanzamiento.class, 
				root.get(Lanzamiento_.codigo), 
				root.get(Lanzamiento_.descripcion), 
				root.get(Lanzamiento_.fechaVencimiento), 
				root.get(Lanzamiento_.fechaPago), 
				root.get(Lanzamiento_.valor), 
				root.get(Lanzamiento_.tipo), 
				root.get(Lanzamiento_.categoria).get(Categoria_.nombre), 
				root.get(Lanzamiento_.persona).get(Persona_.nombre)
				));
		
		//Crear las Restricciones
		Predicate[] predicates = crearRestriciones(lanzamientoFilter, builder, root);
		//Filtrar
		criteria.where(predicates);
		
		//Agregar Restricciones
		TypedQuery<ResumenLanzamiento> query = manager.createQuery(criteria);
		adicionarRestriccionesDePaginacion(query, pageable);
		Long total = totalRegistros(lanzamientoFilter);
		
		return new PageImpl<>(query.getResultList(), pageable, total);

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
	

	private void adicionarRestriccionesDePaginacion(TypedQuery<?> query, Pageable pageable) {
		int paginaActual = pageable.getPageNumber();
		int totalRegistrosPorPagina = pageable.getPageSize();
		int primerRegistroDePagina = paginaActual * totalRegistrosPorPagina;
		query.setFirstResult(primerRegistroDePagina);
		query.setMaxResults(totalRegistrosPorPagina);
	}
	
	private Long totalRegistros(LanzamientoFilter lanzamientoFilter) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Lanzamiento> root = criteria.from(Lanzamiento.class);
		
		//Crear las Restricciones
		Predicate[] predicates = crearRestriciones(lanzamientoFilter, builder, root);
		criteria.where(predicates);
		
		criteria.select(builder.count(root));
		return manager.createQuery(criteria).getSingleResult();
	}

}
