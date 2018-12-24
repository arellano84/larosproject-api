package com.laros.api.repository.projection;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.laros.api.model.TipoLanzamiento;

public class ResumenLanzamiento {
	
	private Long codigo;
	private String descripcion;
	private LocalDate fechaVencimiento;
	private LocalDate fechaPago;
	private BigDecimal valor;
	private TipoLanzamiento tipo;
	private String categoria;
	private String persona;
		
	
	public ResumenLanzamiento(Long codigo, String descripcion, LocalDate fechaVencimiento, LocalDate fechaPago,
			BigDecimal valor, TipoLanzamiento tipo, String categoria, String persona) {
		this.codigo = codigo;
		this.descripcion = descripcion;
		this.fechaVencimiento = fechaVencimiento;
		this.fechaPago = fechaPago;
		this.valor = valor;
		this.tipo = tipo;
		this.categoria = categoria;
		this.persona = persona;
	}
	
	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public LocalDate getFechaVencimiento() {
		return fechaVencimiento;
	}
	public void setFechaVencimiento(LocalDate fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}
	public LocalDate getFechaPago() {
		return fechaPago;
	}
	public void setFechaPago(LocalDate fechaPago) {
		this.fechaPago = fechaPago;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	public TipoLanzamiento getTipo() {
		return tipo;
	}
	public void setTipo(TipoLanzamiento tipo) {
		this.tipo = tipo;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public String getPersona() {
		return persona;
	}
	public void setPersona(String persona) {
		this.persona = persona;
	}
}
