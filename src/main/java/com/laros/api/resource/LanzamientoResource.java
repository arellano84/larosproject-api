package com.laros.api.resource;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.laros.api.dto.Anexo;
import com.laros.api.dto.MovimientoEstadisticaCategoria;
import com.laros.api.dto.MovimientoEstadisticaDia;
import com.laros.api.event.RecursoCreadoEvent;
import com.laros.api.exceptionhandler.LarosExceptionHandler.Error;
import com.laros.api.model.Lanzamiento;
import com.laros.api.repository.LanzamientoRepository;
import com.laros.api.repository.filter.LanzamientoFilter;
import com.laros.api.repository.projection.ResumenLanzamiento;
import com.laros.api.service.LanzamientoService;
import com.laros.api.service.exception.PersonaInexistenteOInactivaException;
import com.laros.api.storage.S3;
import com.laros.api.util.Constantes;

@RestController
@RequestMapping(value = {"/lanzamientos", "/movimientos"}) // ("/lanzamientos")
public class LanzamientoResource {

	private static final Logger logger = LoggerFactory.getLogger(PersonaResource.class);
	
	@Autowired
	private LanzamientoRepository lanzamientoRepository;

	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private LanzamientoService lanzamientoService;
	
	@Autowired
	private MessageSource messageSource;
	
	// 22.33. Enviando arquivos para o S3
	@Autowired
	private S3 s3;

	// 22.28. Upload de arquivos para API
	@PostMapping("/anexo")
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_LANCAMENTO') and #oauth2.hasScope('read')") 
	public Anexo uploadAnexo(@RequestParam("nomanexo") MultipartFile anexo) throws IOException {

		logger.debug("[uploadAnexo] Inicio...");
		/*
		FileOutputStream out = new FileOutputStream("G:\\temp\\anexo-" + anexo.getOriginalFilename());
		out.write(anexo.getBytes());
		out.close();*/
		
		String nombre = s3.salvarTemporariamente(anexo, Constantes.DIR_ANEXO_TIPO_LANZAMIENTO);

		logger.debug("[uploadAnexo] Fin.");
		
		return new Anexo(nombre, s3.configurarUrl(nombre));
	}
	
	
	/*
	 * 22.14. Retornando os bytes do relatório na requisição
	 * */
	@GetMapping("/informes/por-persona")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')") 
	public ResponseEntity<byte[]> informePorPersona(
			@RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate inicio, 
			@RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate fin) throws Exception {
		
		byte[] informePorPersona= lanzamientoService.informePorPersona(inicio, fin);
		
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE)
				.body(informePorPersona);
	}
	
	
	/*
	 * 22.5. Retornando os dados estatísticos de lançamento por dia
	 * */
	@GetMapping("/estadisticas/por-dia")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')") 
	public List<MovimientoEstadisticaDia> porDia() {
		
		List<MovimientoEstadisticaDia> porDia = lanzamientoRepository.porDia(LocalDate.now());
		
		return porDia;
	}
	
	/*
	 * 22.3. Retornando os dados estatísticos de lançamento por categoria
	 * */
	@GetMapping("/estadisticas/por-categoria")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')") 
	public List<MovimientoEstadisticaCategoria> porCategoria() {
		
		List<MovimientoEstadisticaCategoria> porCategoria = lanzamientoRepository.porCategoria(LocalDate.now());
		
		return porCategoria;
	}
	
	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA') and #oauth2.hasScope('read')") //TODO: Revisar rol, es lanzamiento.
	public Page<Lanzamiento> buscar(LanzamientoFilter lanzamientoFilter, Pageable pageable) {
		
//		List<Lanzamiento> lanzamientos = lanzamientoRepository.findAll();
		return lanzamientoRepository.filtrar(lanzamientoFilter, pageable);
		
//		return !lanzamientos.isEmpty() 
//				? ResponseEntity.ok(lanzamientos) 
//				: ResponseEntity.noContent().build();
	}
	
	
	@GetMapping(params="resumo")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA') and #oauth2.hasScope('read')")
	public Page<ResumenLanzamiento> resumir(LanzamientoFilter lanzamientoFilter, Pageable pageable) {
		return lanzamientoRepository.resumir(lanzamientoFilter, pageable);
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
	public ResponseEntity<Lanzamiento> actualizar(@PathVariable Long codigo, @Valid @RequestBody(required=true) Lanzamiento lanzamiento) throws PersonaInexistenteOInactivaException {
		try {
			return ResponseEntity.ok(lanzamientoService.actualizar(codigo, lanzamiento));
		} catch (IllegalArgumentException e){
			return ResponseEntity.notFound().build();
		} 
	}
	
	@ExceptionHandler({PersonaInexistenteOInactivaException.class})
	public ResponseEntity<Object> handlePersonaInexistenteOInactivaException(PersonaInexistenteOInactivaException ex) {
		String mensajeUsuario =  messageSource.getMessage("persona.inexistenteoinactiva", null, LocaleContextHolder.getLocale());
		String mensajeDesarrollador =  ex.toString();
		List<Error> errores = Arrays.asList(new Error(mensajeUsuario, mensajeDesarrollador));
		return ResponseEntity.badRequest().body(errores);
	}
	
	
}
