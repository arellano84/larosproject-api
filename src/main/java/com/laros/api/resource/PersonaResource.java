package com.laros.api.resource;

import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.laros.api.model.Persona;
import com.laros.api.repository.PersonaRepository;

@RestController
@RequestMapping("/personas")
public class PersonaResource {

	@Autowired
	private PersonaRepository personaRepository;

	@GetMapping
	public ResponseEntity<?> listar() {
		List<Persona> personas = personaRepository.findAll();
		return !personas.isEmpty() 
				? ResponseEntity.ok(personas) 
				: ResponseEntity.noContent().build();
	}
	
	@PostMapping
	public ResponseEntity<Persona> crear(@Valid @RequestBody Persona persona, HttpServletResponse response) {
		
		Persona personaSalvada = personaRepository.save(persona);
		
		URI uri = ServletUriComponentsBuilder
		.fromCurrentRequestUri()
		.path("/{codigo}")
		.buildAndExpand(personaSalvada.getCodigo()).toUri();
		
		return ResponseEntity.created(uri).body(personaSalvada);
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<?> buscarPorCodigo(@PathVariable Long codigo) {
		Persona persona = personaRepository.findOne(codigo);
		return persona!=null 
				? ResponseEntity.ok(persona) 
				: ResponseEntity.notFound().build();
	}

}
