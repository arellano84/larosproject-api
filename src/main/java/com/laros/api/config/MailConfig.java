package com.laros.api.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.laros.api.config.property.LarosProjectApiProperty;

/*
 * 22.16. Configurando o envio de e-mail
 * */
@Configuration
public class MailConfig {

	@Autowired
	private LarosProjectApiProperty propiedadesMail;
	
	@Bean
	public JavaMailSender javaMailSender() {
		Properties props = new Properties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.auth", true);
		props.put("mail.smtp.starttls.enable", true);
		props.put("mail.smtp.connectiontimeout", 1000 * 10);// 10 segs
		
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setJavaMailProperties(props);
		mailSender.setHost(propiedadesMail.getMail().getHost());
		mailSender.setPort(propiedadesMail.getMail().getPort());
		mailSender.setUsername(propiedadesMail.getMail().getUsername());
		mailSender.setPassword(propiedadesMail.getMail().getPassword());
		
		return mailSender;
	}
}
