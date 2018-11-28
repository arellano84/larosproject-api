package com.laros.api.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.laros.api.model.Lanzamiento;
import com.laros.api.repository.LanzamientoRepository;
import com.laros.api.service.LanzamientoService;

@RestController
@RequestMapping("/lanzamientos")
public class LanzamientoResource {

	@Autowired
	private LanzamientoRepository lanzamientoRepository;

	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private LanzamientoService lanzamientoService;
	
	
	@GetMapping
	public ResponseEntity<?> listar() {
		List<Lanzamiento> lanzamientos = lanzamientoRepository.findAll();
		return !lanzamientos.isEmpty() 
				? ResponseEntity.ok(lanzamientos) 
				: ResponseEntity.noContent().build();
	}
	
	@PostMapping
	public ResponseEntity<Lanzamiento> crear(@Valid @RequestBody Lanzamiento lanzamiento, HttpServletResponse response) {
		Lanzamiento lanzamientoGuardado = lanzamientoRepository.save(lanzamiento);
		publisher.publishEvent(new RecursoCreadoEvent(this, response, lanzamientoGuardado.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(lanzamientoGuardado);
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<?> buscarPorCodigo(@PathVariable Long codigo) {
		Lanzamiento lanzamiento = lanzamientoRepository.findOne(codigo);
		return lanzamiento!=null 
				? ResponseEntity.ok(lanzamiento) 
				: ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void eliminar(@PathVariable Long codigo) {
		lanzamientoRepository.delete(codigo);
	}

	@PutMapping("/{codigo}")
	public ResponseEntity<Lanzamiento> actualizar(@PathVariable Long codigo, @Valid @RequestBody(required=true) Lanzamiento lanzamiento) {
		return ResponseEntity.ok(lanzamientoService.actualizar(codigo, lanzamiento));
	}
	
//	@PutMapping("/{codigo}/activo")
//	@ResponseStatus(HttpStatus.NO_CONTENT)
//	public void actualizar(@PathVariable Long codigo, @RequestBody Boolean activo) {
//		lanzamientoService.actualizarPropiedadActivo(codigo, activo);
//	}
	
}
