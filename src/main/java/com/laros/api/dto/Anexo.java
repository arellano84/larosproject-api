package com.laros.api.dto;

/*
 * 22.33. Enviando arquivos para o S3
 * */
public class Anexo {
	
	private String nombre;
	
	private String url;

	public Anexo(String nombre, String url) {
		this.nombre = nombre;
		this.url = url;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public String getUrl() {
		return url;
	}

}
