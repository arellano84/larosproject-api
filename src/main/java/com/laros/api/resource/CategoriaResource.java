package com.laros.api.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.laros.api.event.RecursoCreadoEvent;
import com.laros.api.model.Categoria;
import com.laros.api.repository.CategoriaRepository;

@RestController
@RequestMapping("/categorias")
public class CategoriaResource {

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@GetMapping
	public ResponseEntity<?> listar() {
		List<Categoria> categorias = categoriaRepository.findAll();
		return !categorias.isEmpty() 
				? ResponseEntity.ok(categorias) 
				: ResponseEntity.noContent().build();
	}
	
	@PostMapping
//	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Categoria> crear(@Valid @RequestBody Categoria categoria, HttpServletResponse response) {
		
		Categoria categoriaSalvada = categoriaRepository.save(categoria);
		
		publisher.publishEvent(new RecursoCreadoEvent(this, response, categoriaSalvada.getCodigo()));
		
//		return ResponseEntity.created(uri).body(categoriaSalvada);
		return ResponseEntity.status(HttpStatus.CREATED).body(categoriaSalvada);
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<?> buscarPorCodigo(@PathVariable Long codigo) {
		Categoria categoria = categoriaRepository.findOne(codigo);
		return categoria!=null 
				? ResponseEntity.ok(categoria) 
				: ResponseEntity.notFound().build();
	}

}
