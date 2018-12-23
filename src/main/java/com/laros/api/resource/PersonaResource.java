package com.laros.api.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.laros.api.event.RecursoCreadoEvent;
import com.laros.api.model.Persona;
import com.laros.api.repository.PersonaRepository;
import com.laros.api.service.PersonaService;

@RestController
@RequestMapping("/personas")
public class PersonaResource {

	@Autowired
	private PersonaRepository personaRepository;

	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private PersonaService personaService;
	
	
	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA') and #oauth2.hasScope('read')")
	public ResponseEntity<?> listar() {
		List<Persona> personas = personaRepository.findAll();
		return !personas.isEmpty() 
				? ResponseEntity.ok(personas) 
				: ResponseEntity.noContent().build();
	}
	
	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_CATEGORIA') and #oauth2.hasScope('write')")
	public ResponseEntity<Persona> crear(@Valid @RequestBody Persona persona, HttpServletResponse response) {
		Persona personaSalvada = personaRepository.save(persona);
		publisher.publishEvent(new RecursoCreadoEvent(this, response, personaSalvada.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(personaSalvada);
	}
	
	@GetMapping("/{codigo}")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA') and #oauth2.hasScope('read')")
	public ResponseEntity<?> buscarPorCodigo(@PathVariable Long codigo) {
		Persona persona = personaRepository.findOne(codigo);
		return persona!=null 
				? ResponseEntity.ok(persona) 
				: ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_CATEGORIA') and #oauth2.hasScope('write')")
	public void eliminar(@PathVariable Long codigo) {
		personaRepository.delete(codigo);
	}

	@PutMapping("/{codigo}")
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_CATEGORIA') and #oauth2.hasScope('write')")
	public ResponseEntity<Persona> actualizar(@PathVariable Long codigo, @Valid @RequestBody(required=true) Persona persona) {
		
		return ResponseEntity.ok(personaService.actualizar(codigo, persona));
	}
	
	@PutMapping("/{codigo}/activo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_CATEGORIA') and #oauth2.hasScope('write')")
	public void actualizar(@PathVariable Long codigo, @RequestBody Boolean activo) {
		personaService.actualizarPropiedadActivo(codigo, activo);
	}
	
}
