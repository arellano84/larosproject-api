package com.laros.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.laros.api.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

	public Optional<Usuario> findByEmail(String email);
	
	/*
	 * 22.20. Buscando lançamentos vencidos com Spring Data JPA
	 * */
	public List<Usuario> findByPermisosDescipcion(String permisosDescipcion);
}
