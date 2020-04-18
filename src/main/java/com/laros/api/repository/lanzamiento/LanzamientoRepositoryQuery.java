package com.laros.api.repository.lanzamiento;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.laros.api.dto.MovimientoEstadisticaCategoria;
import com.laros.api.dto.MovimientoEstadisticaDia;
import com.laros.api.model.Lanzamiento;
import com.laros.api.repository.filter.LanzamientoFilter;
import com.laros.api.repository.projection.ResumenLanzamiento;

public interface LanzamientoRepositoryQuery {
		
	/*
	 * 22.2. Criando consulta para dados por categoria
	 * */
	public List<MovimientoEstadisticaCategoria> porCategoria(LocalDate mesReferencia);
	
	/*
	 * 22.4. Criando consulta para dados por dia
	 * */
	public List<MovimientoEstadisticaDia> porDia(LocalDate mesReferencia);	
	
	/*
	 * 
	 * */
	public Page<Lanzamiento> filtrar(LanzamientoFilter lanzamientoFilter, Pageable pageable);
	
	/*
	 * Proyeccion
	 * */
	public Page<ResumenLanzamiento> resumir(LanzamientoFilter lanzamientoFilter, Pageable pageable);
	
}
