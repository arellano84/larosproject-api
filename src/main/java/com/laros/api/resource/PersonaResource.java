package com.laros.api.resource;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import com.laros.api.repository.filter.PersonaFilter;
import com.laros.api.service.PersonaService;

@RestController
@RequestMapping("/personas")
public class PersonaResource {

	private static final Logger logger = LoggerFactory.getLogger(PersonaResource.class);
	
	@Autowired
	private PersonaRepository personaRepository;

	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private PersonaService personaService;
	
	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_PESSOA') and #oauth2.hasScope('read')")
	public Page<Persona> buscar(PersonaFilter personaFilter, Pageable pageable) {
		
		return personaRepository.filtrarPaginando(personaFilter, pageable);
	}
	
	@GetMapping("/personas/listar") //20200403
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_PESSOA') and #oauth2.hasScope('read')")
	public ResponseEntity<?> listar(PersonaFilter personaFilter) {
		
//		List<Persona> personas = personaRepository.findAll();
		
		//7.7. Desafio: Pesquisa de pessoa
		List<Persona> personas = personaRepository.filtrar(personaFilter);
		return !personas.isEmpty() 
				? ResponseEntity.ok(personas) 
				: ResponseEntity.noContent().build();
	}
	
	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_PESSOA') and #oauth2.hasScope('write')")
	public ResponseEntity<Persona> crear(@Valid @RequestBody Persona persona, HttpServletResponse response) {
		
		logger.debug("[crear] Inicio...");
		
		// 22.25. Inserindo uma pessoa com contato
//		Persona personaSalvada = personaRepository.save(persona);
		Persona personaSalvada = personaService.guardar(persona);
		
		publisher.publishEvent(new RecursoCreadoEvent(this, response, personaSalvada.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(personaSalvada);
	}
	
	@GetMapping("/{codigo}")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_PESSOA') and #oauth2.hasScope('read')")
	public ResponseEntity<?> buscarPorCodigo(@PathVariable Long codigo) {
		// 25.2. Novas assinaturas do Spring Data JPA
		Optional<Persona> persona = personaRepository.findById(codigo);
		return persona.isPresent() 
				? ResponseEntity.ok(persona) 
				: ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_PESSOA') and #oauth2.hasScope('write')")
	public void eliminar(@PathVariable Long codigo) {
		personaRepository.deleteById(codigo);
	}

	@PutMapping("/{codigo}")
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_PESSOA') and #oauth2.hasScope('write')")
	public ResponseEntity<Persona> actualizar(@PathVariable Long codigo, @Valid @RequestBody(required=true) Persona persona) {
		
		return ResponseEntity.ok(personaService.actualizar(codigo, persona));
	}
	
	@PutMapping("/{codigo}/activo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_PESSOA') and #oauth2.hasScope('write')")
	public void actualizar(@PathVariable Long codigo, @RequestBody Boolean activo) {
		personaService.actualizarPropiedadActivo(codigo, activo);
	}
	
}
