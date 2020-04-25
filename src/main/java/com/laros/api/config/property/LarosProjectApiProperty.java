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

	/*
	 * 22.30. Configurando o serviço S3
	 * */
	private final S3 s3 = new S3();
	
	public S3 getS3() {
		return s3;
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
		
		// (Angular) 19.00.2 Refactorizando: Inyectando tiempo valido de tokens en propiedades
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
	
	
	
	
	
	/*
	 * 22.16. Configurando o envio de e-mail
	 * */
	private final Mail mail = new Mail();
	
	public Mail getMail() {
		return mail;
	}
	
	public static class Mail {
		
		private String host;
		private Integer port;
		private String username;
		private String password;

		public String getHost() {
			return host;
		}

		public void setHost(String host) {
			this.host = host;
		}

		public Integer getPort() {
			return port;
		}

		public void setPort(Integer port) {
			this.port = port;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}
	}
	
	
	/*
	 * 22.30. Configurando o serviço S3
	 * */
	public static class S3 {
		
		private String accessKeyId;
		
		private String secretAccessKey;
		
		private String bucket;
		

		public String getBucket() {
			return bucket;
		}

		public void setBucket(String bucket) {
			this.bucket = bucket;
		}

		public String getAccessKeyId() {
			return accessKeyId;
		}

		public void setAccessKeyId(String accessKeyId) {
			this.accessKeyId = accessKeyId;
		}

		public String getSecretAccessKey() {
			return secretAccessKey;
		}

		public void setSecretAccessKey(String secretAccessKey) {
			this.secretAccessKey = secretAccessKey;
		}
	}
	
}
