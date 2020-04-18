package com.laros.api.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.laros.api.model.TipoLanzamiento;

/*
	22.1. Preparação do retorno dos dados para os gráficos
*/
public class MovimientoEstadisticaDia {
	
	private TipoLanzamiento tipoLanzamiento;
	
	private LocalDate dia;
	
	private BigDecimal total;

	public MovimientoEstadisticaDia(TipoLanzamiento tipoLanzamiento, LocalDate dia, BigDecimal total) {
		this.tipoLanzamiento = tipoLanzamiento;
		this.dia = dia;
		this.total = total;
	}

	public TipoLanzamiento getTipoLanzamiento() {
		return tipoLanzamiento;
	}

	public void setTipoLanzamiento(TipoLanzamiento tipoLanzamiento) {
		this.tipoLanzamiento = tipoLanzamiento;
	}

	public LocalDate getDia() {
		return dia;
	}

	public void setDia(LocalDate dia) {
		this.dia = dia;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	
}
