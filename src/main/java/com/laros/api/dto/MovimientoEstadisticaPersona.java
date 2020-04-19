package com.laros.api.dto;

import java.math.BigDecimal;

import com.laros.api.model.Persona;
import com.laros.api.model.TipoLanzamiento;

/*
 * 22.8. Criando o DTO do relatório
 * */
public class MovimientoEstadisticaPersona {
	
	private TipoLanzamiento tipoLanzamiento;
	
	private Persona persona;
	
	private BigDecimal total;

	public MovimientoEstadisticaPersona(TipoLanzamiento tipoLanzamiento, Persona persona, BigDecimal total) {
		this.tipoLanzamiento = tipoLanzamiento;
		this.persona = persona;
		this.total = total;
	}

	public TipoLanzamiento getTipoLanzamiento() {
		return tipoLanzamiento;
	}

	public void setTipoLanzamiento(TipoLanzamiento tipoLanzamiento) {
		this.tipoLanzamiento = tipoLanzamiento;
	}

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	
}
