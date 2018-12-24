package com.laros.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

import com.laros.api.config.property.LarosProjectApiProperty;

@SpringBootApplication
@EnableConfigurationProperties(LarosProjectApiProperty.class)
public class LarosApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(LarosApiApplication.class, args);
	}
}
