package com.laros.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.laros.api.model.Lanzamiento;
import com.laros.api.model.Persona;
import com.laros.api.repository.LanzamientoRepository;
import com.laros.api.repository.PersonaRepository;
import com.laros.api.service.exception.PersonaInexistenteOInactivaException;

@Service
public class LanzamientoService {

	@Autowired
	private PersonaRepository personaRepository;
	
	@Autowired
	private LanzamientoRepository lanzamientoRepository;


	public Lanzamiento guardar(Lanzamiento lanzamiento) throws PersonaInexistenteOInactivaException {
		validarPersona(lanzamiento);
		
		return lanzamientoRepository.save(lanzamiento);
	}
	
	public Lanzamiento actualizar(Long codigo, Lanzamiento lanzamiento) throws PersonaInexistenteOInactivaException {
		Lanzamiento lanzamientoGuardado = buscarLanzamientoExistente(codigo);
		if(!lanzamiento.getPersona().equals(lanzamientoGuardado.getPersona())) {
			validarPersona(lanzamiento);
		}
		
		//Copiar propiedades de un objeto a otro, excepto el código.
		BeanUtils.copyProperties(lanzamiento, lanzamientoGuardado, "codigo");
		
		return lanzamientoRepository.save(lanzamientoGuardado);
	}
	
	private void validarPersona(Lanzamiento lanzamiento) throws PersonaInexistenteOInactivaException {
		Persona persona = null;
		if(lanzamiento.getPersona().getCodigo() != null) {
			persona = personaRepository.findOne(lanzamiento.getPersona().getCodigo());
		}
		
		if (persona == null || persona.isInactivo()) {
			throw new PersonaInexistenteOInactivaException();
		}
	}

	private Lanzamiento buscarLanzamientoExistente(Long codigo) {
		Lanzamiento lanzamientoGuardado = lanzamientoRepository.findOne(codigo);
		if(lanzamientoGuardado==null)
			throw new IllegalArgumentException();
		return lanzamientoGuardado;
	}

}
