package com.laros.api.dto;

import java.math.BigDecimal;

import com.laros.api.model.Categoria;

/*
	22.1. Preparação do retorno dos dados para os gráficos
*/
public class MovimientoEstadisticaCategoria {
	
	private Categoria categoria;
	
	private BigDecimal total;
	
	public MovimientoEstadisticaCategoria(Categoria categoria, BigDecimal total) {
		this.categoria = categoria;
		this.total = total;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}
}
