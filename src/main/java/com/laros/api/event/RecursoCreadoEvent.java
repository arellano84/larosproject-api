package com.laros.api.event;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationEvent;

public class RecursoCreadoEvent extends ApplicationEvent{
	
	private static final long serialVersionUID = 1L;
	
	HttpServletResponse response;
	private Long codigo;
	
	public RecursoCreadoEvent(Object source, HttpServletResponse response, Long codigo) {
		super(source);
		this.response = response;
		this.codigo = codigo;
	}

	public HttpServletResponse getResponse() {
		return response;
	}
	
	public Long getCodigo() {
		return codigo;
	}

}
