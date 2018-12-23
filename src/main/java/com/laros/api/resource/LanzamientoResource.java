package com.laros.api.resource;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.laros.api.event.RecursoCreadoEvent;
import com.laros.api.exceptionhandler.LarosExceptionHandler.Error;
import com.laros.api.model.Lanzamiento;
import com.laros.api.repository.LanzamientoRepository;
import com.laros.api.repository.filter.LanzamientoFilter;
import com.laros.api.service.LanzamientoService;
import com.laros.api.service.exception.PersonaInexistenteOInactivaException;

@RestController
@RequestMapping("/lanzamientos")
public class LanzamientoResource {

	@Autowired
	private LanzamientoRepository lanzamientoRepository;

	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private LanzamientoService lanzamientoService;
	
	@Autowired
	private MessageSource messageSource;
	
	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA') and #oauth2.hasScope('read')")
	public Page<Lanzamiento> buscar(LanzamientoFilter lanzamientoFilter, Pageable pageable) {
		
//		List<Lanzamiento> lanzamientos = lanzamientoRepository.findAll();
		return lanzamientoRepository.filtrar(lanzamientoFilter, pageable);
		
//		return !lanzamientos.isEmpty() 
//				? ResponseEntity.ok(lanzamientos) 
//				: ResponseEntity.noContent().build();
	}
	
	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_CATEGORIA') and #oauth2.hasScope('write')")
	public ResponseEntity<Lanzamiento> crear(@Valid @RequestBody Lanzamiento lanzamiento, HttpServletResponse response) throws PersonaInexistenteOInactivaException {
		
		//Validar que no sea modificación.
		lanzamiento.setCodigo(null);
		
		Lanzamiento lanzamientoGuardado = lanzamientoService.guardar(lanzamiento);
		publisher.publishEvent(new RecursoCreadoEvent(this, response, lanzamientoGuardado.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(lanzamientoGuardado);
	}
	
	@GetMapping("/{codigo}")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA') and #oauth2.hasScope('read')")
	public ResponseEntity<?> buscarPorCodigo(@PathVariable Long codigo) {
		Lanzamiento lanzamiento = lanzamientoRepository.findOne(codigo);
		return lanzamiento!=null 
				? ResponseEntity.ok(lanzamiento) 
				: ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_CATEGORIA') and #oauth2.hasScope('write')")
	public void eliminar(@PathVariable Long codigo) {
		lanzamientoRepository.delete(codigo);
	}

	@PutMapping("/{codigo}")
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_CATEGORIA') and #oauth2.hasScope('write')")
	public ResponseEntity<Lanzamiento> actualizar(@PathVariable Long codigo, @Valid @RequestBody(required=true) Lanzamiento lanzamiento) {
		return ResponseEntity.ok(lanzamientoService.actualizar(codigo, lanzamiento));
	}
	
	@ExceptionHandler({PersonaInexistenteOInactivaException.class})
	public ResponseEntity<Object> handlePersonaInexistenteOInactivaException(PersonaInexistenteOInactivaException ex) {
		String mensajeUsuario =  messageSource.getMessage("persona.inexistenteoinactiva", null, LocaleContextHolder.getLocale());
		String mensajeDesarrollador =  ex.toString();
		List<Error> errores = Arrays.asList(new Error(mensajeUsuario, mensajeDesarrollador));
		return ResponseEntity.badRequest().body(errores);
	}
	
	
}
