package com.laros.api.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.laros.api.repository.listener.LanzamientoAnexoListener;

@EntityListeners(LanzamientoAnexoListener.class) // 22.36. Configurando URL do anexo
@Entity
@Table(name="lanzamiento")
public class Lanzamiento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;
	
	@NotNull
	private String descripcion;
	
	@NotNull
	@Column(name="fecha_vencimiento")
	private LocalDate fechaVencimiento;
	
	@Column(name="fecha_pago")
	private LocalDate fechaPago;
	
	@NotNull
	private BigDecimal valor;
	
	private String observacion;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private TipoLanzamiento tipo;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name="codigo_categoria")
	private Categoria categoria;
	
	@JsonIgnoreProperties({"contactos"}) //22.27. Ignorando contatos da pessoa na pesquisa de lançamento
	@NotNull
	@ManyToOne
	@JoinColumn(name="codigo_persona")
	private Persona persona;
	
	
	// 22.34. Anexando arquivo no lançamento
	private String anexo;
	
	@Transient
	private String urlAnexo;
	
	// 22.19. Processando o template e enviando o e-mail
	@JsonIgnore
	public boolean isIngreso() {
		return TipoLanzamiento.INGRESO.equals(this.tipo);
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

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public TipoLanzamiento getTipo() {
		return tipo;
	}

	public void setTipo(TipoLanzamiento tipo) {
		this.tipo = tipo;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}
	
	

	public String getAnexo() {
		return anexo;
	}

	public void setAnexo(String anexo) {
		this.anexo = anexo;
	}

	public String getUrlAnexo() {
		return urlAnexo;
	}

	public void setUrlAnexo(String urlAnexo) {
		this.urlAnexo = urlAnexo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Lanzamiento other = (Lanzamiento) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}
	
	
}
