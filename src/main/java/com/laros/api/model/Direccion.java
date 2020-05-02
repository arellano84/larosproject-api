package com.laros.api.model;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class Direccion {
	
//	@Size(min=1, max = 20)
	private String tipo;
	
//	@Size(min=1, max = 200)
	private String calle;
	
//	@Size(min=1, max = 10)
	private String numero;
	
//	@Size(min=1, max = 100)
	private String complemento;
	
//	@Size(min=1, max = 10)
	private String cp;
	
//	@Size(min=1, max = 50)
//	private String municipio;
	
//	@Size(min=1, max = 50)
//	private String ciudad;
	
//	@Size(min=1, max = 50)
	private String pais;
	
	@ManyToOne
	@JoinColumn(name = "codigo_ciudad")
	private Ciudad ciudad;

	
	public Ciudad getCiudad() {
		return ciudad;
	}
	
	public void setCiudad(Ciudad ciudad) {
		this.ciudad = ciudad;
	}
	
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getCalle() {
		return calle;
	}
	public void setCalle(String calle) {
		this.calle = calle;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getComplemento() {
		return complemento;
	}
	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}
	public String getCp() {
		return cp;
	}
	public void setCp(String cp) {
		this.cp = cp;
	}
//	public String getMunicipio() {
//		return municipio;
//	}
//	public void setMunicipio(String municipio) {
//		this.municipio = municipio;
//	}
//	public String getCiudad() {
//		return ciudad;
//	}
//	public void setCiudad(String ciudad) {
//		this.ciudad = ciudad;
//	}
	public String getPais() {
		return pais;
	}
	public void setPais(String pais) {
		this.pais = pais;
	}
	
	@Override
	public String toString() {
		return "Direccion [tipo=" + tipo + ", calle=" + calle + ", numero=" + numero + ", complemento=" + complemento
				+ ", cp=" + cp + ", ciudad=" + ciudad + ", pais=" + pais + "]";
	}
}
