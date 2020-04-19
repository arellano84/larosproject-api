package com.laros.api.model;

public enum TipoLanzamiento {
	
	INGRESO("Ingreso"),
	GASTO("Gasto")
	;
	
	// 22.8. Criando o DTO do relatório
	private final String descripcion;
	
	TipoLanzamiento(String descripcion) {
		this.descripcion= descripcion;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	
}
