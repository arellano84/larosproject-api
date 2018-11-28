package com.laros.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.laros.api.model.Lanzamiento;
import com.laros.api.repository.LanzamientoRepository;

@Service
public class LanzamientoService {

	@Autowired
	private LanzamientoRepository lanzamientoRepository;
	
	public Lanzamiento actualizar(Long codigo, Lanzamiento lanzamiento) {
		Lanzamiento lanzamientoGuardado = buscarLanzamientoGuardado(codigo);
		BeanUtils.copyProperties(lanzamiento, lanzamientoGuardado, "codigo");
		lanzamientoRepository.save(lanzamientoGuardado);
		return lanzamientoGuardado;
	}

//	public void actualizarPropiedadActivo(Long codigo, Boolean activo) {
//		Lanzamiento lanzamientoGuardado = buscarLanzamientoGuardado(codigo);
//		lanzamientoGuardado.setActivo(activo);
//		lanzamientoRepository.save(lanzamientoGuardado);
//	}
	
	private Lanzamiento buscarLanzamientoGuardado(Long codigo) {
		Lanzamiento lanzamientoGuardado = lanzamientoRepository.findOne(codigo);
		if(lanzamientoGuardado==null)
			throw new EmptyResultDataAccessException(1);
		return lanzamientoGuardado;
	}
}
