package com.laros.api.repository.lanzamiento;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.laros.api.model.Lanzamiento;
import com.laros.api.repository.filter.LanzamientoFilter;

public interface LanzamientoRepositoryQuery {
	
	public Page<Lanzamiento> filtrar(LanzamientoFilter lanzamientoFilter, Pageable pageable);

}
