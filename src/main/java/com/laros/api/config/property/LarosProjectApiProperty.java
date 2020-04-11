package com.laros.api.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("larosproject")
public class LarosProjectApiProperty {
	
	private String origenPermitido = "http://localhost";
	
	public String getOrigenPermitido() {
		return origenPermitido;
	}
	public void setOrigenPermitido(String origenPermitido) {
		this.origenPermitido = origenPermitido;
	}

	
	private final Seguridad seguridad = new Seguridad();
	
	public Seguridad getSeguridad() {
		return seguridad;
	}

	public static class Seguridad {
		
		private boolean enableHttps;
		private Integer accessTokenValiditySeconds;
		private Integer refreshTokenValiditySeconds;

		public boolean isEnableHttps() {
			return enableHttps;
		}
		public void setEnableHttps(boolean enableHttps) {
			this.enableHttps = enableHttps;
		}
		
		public Integer getAccessTokenValiditySeconds() {
			return accessTokenValiditySeconds;
		}
		public void setAccessTokenValiditySeconds(Integer accessTokenValiditySeconds) {
			this.accessTokenValiditySeconds = accessTokenValiditySeconds;
		}

		public Integer getRefreshTokenValiditySeconds() {
			return refreshTokenValiditySeconds;
		}
		public void setRefreshTokenValiditySeconds(Integer refreshTokenValiditySeconds) {
			this.refreshTokenValiditySeconds = refreshTokenValiditySeconds;
		}
	}
	
	
	
	
}
