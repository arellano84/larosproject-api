package com.laros.api.event.listener;

import java.net.URI;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.laros.api.event.RecursoCreadoEvent;

@Component
public class RecursoCreadoListener implements ApplicationListener<RecursoCreadoEvent>{

	@Override
	public void onApplicationEvent(RecursoCreadoEvent recursoCreadoEvent) {
		HttpServletResponse response = recursoCreadoEvent.getResponse();
		Long codigo = recursoCreadoEvent.getCodigo();
		
		agregarHeaderLocation(response, codigo);
		
	}

	private void agregarHeaderLocation(HttpServletResponse response, Long codigo) {
		URI uri = ServletUriComponentsBuilder
		.fromCurrentRequestUri()
		.path("/{codigo}")
		.buildAndExpand(codigo).toUri();
		response.setHeader("Location", uri.toASCIIString());
	}
}
