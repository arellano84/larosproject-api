package com.laros.api.resource;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.laros.api.model.Estado;
import com.laros.api.repository.EstadoRepository;

/*
 * 24.2. Criando pesquisa de estados e cidades
 * */
@RestController
@RequestMapping("/estados")
public class EstadoResource {

	private static final Logger logger = LoggerFactory.getLogger(EstadoResource.class);
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@GetMapping
//	@PreAuthorize("isAuthenticated()")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_PESSOA') and #oauth2.hasScope('read')") 
	public List<Estado> listar() {
		logger.debug("[listar] Inicio...");
		
		return estadoRepository.findAll();
	}
}