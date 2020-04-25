package com.laros.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;

import com.laros.api.config.property.LarosProjectApiProperty;

@SpringBootApplication
@EnableConfigurationProperties(LarosProjectApiProperty.class)
public class LarosApiApplication {

	// 22.36. Configurando URL do anexo
	private static ApplicationContext APPLICATION_CONTEXT;
	
	
	public static void main(String[] args) {
		APPLICATION_CONTEXT = SpringApplication.run(LarosApiApplication.class, args);
	}
	
	// 22.36. Configurando URL do anexo
	public static <T> T getBean(Class<T> type) {
		return APPLICATION_CONTEXT.getBean(type);
	}
}
