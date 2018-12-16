package com.laros.api.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.laros.api.model.Usuario;
import com.laros.api.repository.UsuarioRepository;

@Service
public class AppUserDetailsService implements UserDetailsService{

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		Optional<Usuario> usuarioOpcional = usuarioRepository.findByEmail(email);
		Usuario usuario = usuarioOpcional.orElseThrow(()->new UsernameNotFoundException("Usuario y/o Contraseña Incorrectos"));
		
		return new User(email, usuario.getSenha(), getPermisos(usuario));
	}

	private Collection<? extends GrantedAuthority> getPermisos(Usuario usuario) {
		Set<SimpleGrantedAuthority> authorities = new HashSet<>();
		usuario.getPermisos().forEach(p->authorities.add(new SimpleGrantedAuthority(p.getDescipcion().toUpperCase())));
		return authorities;
	}
}
