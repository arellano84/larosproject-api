package com.laros.api.security.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class GeneradorSenha {
	
	public static void main(String[] args) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		System.out.println(encoder.encode("simon")); //25.00.00 Mejoras: externalización de datos de Authorization.
	}
}
