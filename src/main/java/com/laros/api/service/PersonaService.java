package com.laros.api.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.laros.api.model.Persona;
import com.laros.api.repository.PersonaRepository;

@Service
public class PersonaService {

	private static final Logger logger = LoggerFactory.getLogger(PersonaService.class);
	
	@Autowired
	private PersonaRepository personaRepository;
	
	/*
	 * 22.25. Inserindo uma pessoa com contato
	 * */
	public Persona guardar(Persona persona) {
		logger.debug("[guardar] Inicio...");
		
		// 22.25. Inserindo uma pessoa com contato
//		if(persona.getContactos().isEmpty()) // TODO: validar cuando no envia Contacto...
		persona.getContactos().forEach(c->c.setPersona(persona));
		
		Persona personaSalvada= personaRepository.save(persona);
		
		logger.debug("[guardar] Fin.");
		return personaSalvada;
	}
	
	public Persona actualizar(Long codigo, Persona persona) {
		logger.debug("[actualizar] Inicio...");
		Persona personaSalvada = buscarPersonaGuardada(codigo);
		
		//22.26. Usando a propriedade orphanRemoval
		personaSalvada.getContactos().clear();
		personaSalvada.getContactos().addAll(persona.getContactos());
		
		// 22.25. Inserindo uma pessoa com contato
		personaSalvada.getContactos().forEach(c->c.setPersona(personaSalvada)); // TODO: validar cuando no envia Contactos en el  Json...
		
		BeanUtils.copyProperties(persona, personaSalvada, "codigo", "contactos"); //Ignoramos código y contactos en la copia.
		personaRepository.save(personaSalvada);
		
		logger.debug("[actualizar] Inicio...");
		return personaSalvada;
	}

	public void actualizarPropiedadActivo(Long codigo, Boolean activo) {
		Persona personaSalvada = buscarPersonaGuardada(codigo);
		personaSalvada.setActivo(activo);
		personaRepository.save(personaSalvada);
	}
	
	public Persona buscarPersonaGuardada(Long codigo) {
		Persona personaSalvada = personaRepository.findOne(codigo);
		if(personaSalvada==null)
			throw new EmptyResultDataAccessException(1);
		return personaSalvada;
	}
}
