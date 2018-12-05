package com.laros.api.repository.lanzamiento;

import java.util.List;

import com.laros.api.model.Lanzamiento;
import com.laros.api.repository.filter.LanzamientoFilter;

public interface LanzamientoRepositoryQuery {
	
	public List<Lanzamiento> filtrar(LanzamientoFilter lanzamientoFilter);

}
