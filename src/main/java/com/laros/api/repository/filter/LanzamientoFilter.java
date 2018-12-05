package com.laros.api.repository.filter;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

public class LanzamientoFilter {
	
	private String descripcion;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private LocalDate fechaVencimientoDe;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private LocalDate fechaVencimientoHasta;
	
	
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public LocalDate getFechaVencimientoDe() {
		return fechaVencimientoDe;
	}
	public void setFechaVencimientoDe(LocalDate fechaVencimientoDe) {
		this.fechaVencimientoDe = fechaVencimientoDe;
	}
	public LocalDate getFechaVencimientoHasta() {
		return fechaVencimientoHasta;
	}
	public void setFechaVencimientoHasta(LocalDate fechaVencimientoHasta) {
		this.fechaVencimientoHasta = fechaVencimientoHasta;
	}
	
}
