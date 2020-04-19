package com.laros.api.mail;

import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.context.event.ApplicationReadyEvent;
//import org.springframework.context.event.EventListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

/*
 * 22.17. Enviando um e-mail simples
 * */
@Component
public class Mailer {

	@Autowired
	private JavaMailSender javaMailSender;
	
	/*
	 * Escuchador para enviar.
	 * */
	/*@EventListener
	public void test(ApplicationReadyEvent event) {
		enviarEmail("", //TODO: agregar email
				Arrays.asList(""), //TODO: agregar email
				"[larosproject] Probando envio",
				"<br>Hola, ¿cómo estas?</br>");
		
		System.out.println("[Mailer.test]---------->Enviando Email...");
	}*/
	
	public void enviarEmail(String remitente, List<String> destinatarios, String asunto ,String mensaje) {
		try {
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
			helper.setFrom(remitente);
			helper.setTo(destinatarios.toArray(new String[destinatarios.size()]));
			helper.setSubject(asunto);
			helper.setText(mensaje, true);			
			
			javaMailSender.send(mimeMessage);
		} catch (MessagingException e) {
			throw new RuntimeException("Problemas al enviar el email!", e);
		}
	}
}
