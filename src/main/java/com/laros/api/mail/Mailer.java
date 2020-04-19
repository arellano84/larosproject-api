package com.laros.api.mail;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.laros.api.model.Lanzamiento;
import com.laros.api.model.Usuario;
import com.laros.api.repository.LanzamientoRepository;

/*
 * 22.17. Enviando um e-mail simples
 * */
@Component
public class Mailer {

	@Autowired
	private JavaMailSender javaMailSender;
	
	// 22.19. Processando o template e enviando o e-mail
	@Autowired
	private TemplateEngine thymeleaf;
	
	
	
	/*
	 * Escuchador para enviar.
	 * */
	
	/* //TODO: email: Test para probar email
	
	@Autowired
	private LanzamientoRepository lanzamientosRepository;
	
	@EventListener
	public void test(ApplicationReadyEvent event) {
		
		System.out.println("[Mailer.test]---------->Enviando Email...");
		
//		enviarEmail("",
//				Arrays.asList(""),
//				"[larosproject] Probando envio",
//				"<br>Hola, ¿cómo estas?</br>");
		
		// Para que funcione en local activar: https://myaccount.google.com/lesssecureapps?pli=1
		
		String remitente = "..@gmail.com"; // TODO: para prueba agregar email
		List<String> destinatarios = Arrays.asList("...@gmail.com"); // TODO: para prueba agregar email
		String asunto = "Movimientos Vencidos";
		String template = "mail/notificacion-movimientos-vencidos";
		
		//Consulta Movimientos
		List<Lanzamiento> lanzamientos = lanzamientosRepository.findAll();
		//Agrega Lista
		Map<String, Object> variables = new HashMap<>();
		variables.put("movimientos", lanzamientos);
		
		//Envia email con template.
		enviarEmail(remitente, destinatarios, asunto, template, variables);
		
		System.out.println("[Mailer.test]---------->Enviado Email.");
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
	
	
	/*
	 * 22.19. Processando o template e enviando o e-mail
	 * */
	public void enviarEmail(String remitente, 
			List<String> destinatarios, String asunto, String template, 
			Map<String, Object> variables) {
		Context context = new Context(new Locale("es", "ES"));
		
		variables.entrySet().forEach(
				e -> context.setVariable(e.getKey(), e.getValue()));
		
		String mensaje = thymeleaf.process(template, context);
		
		this.enviarEmail(remitente, destinatarios, asunto, mensaje);
	}
	
	
	/*
	 * 22.21. Agendando o envio de e-mail
	 * */
	public void avisarSobreLanzamentosVencidos(List<Lanzamiento> lanzVencidos, List<Usuario> destinatarios) {
		
		Map<String, Object> variables = new HashMap<>();
		variables.put("movimientos", lanzVencidos);
		
		List<String> emails = destinatarios.stream()
				.map(u -> u.getEmail())
				.collect(Collectors.toList());
		
		emails = Arrays.asList("...@gmail.com"); // TODO: email: agregar email de prueba
		
		this.enviarEmail("larosproject20@gmail.com", 
				emails, 
				"Movimientos vencidos", 
				"mail/notificacion-movimientos-vencidos", 
				variables);
	}
	
}
