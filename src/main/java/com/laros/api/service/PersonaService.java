package com.laros.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.laros.api.model.Persona;
import com.laros.api.repository.PersonaRepository;

@Service
public class PersonaService {

	@Autowired
	private PersonaRepository personaRepository;
	
	public Persona actualizar(Long codigo, Persona persona) {
		Persona personaSalvada = buscarPersonaGuardada(codigo);
		BeanUtils.copyProperties(persona, personaSalvada, "codigo");
		personaRepository.save(personaSalvada);
		return personaSalvada;
	}

	public void actualizarPropiedadActivo(Long codigo, Boolean activo) {
		Persona personaSalvada = buscarPersonaGuardada(codigo);
		personaSalvada.setActivo(activo);
		personaRepository.save(personaSalvada);
	}
	
	private Persona buscarPersonaGuardada(Long codigo) {
		Persona personaSalvada = personaRepository.findOne(codigo);
		if(personaSalvada==null)
			throw new EmptyResultDataAccessException(1);
		return personaSalvada;
	}
}
