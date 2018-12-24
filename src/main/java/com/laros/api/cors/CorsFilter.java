package com.laros.api.cors;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.laros.api.config.property.LarosProjectApiProperty;

/*
 * Se implementa esto porque cors todavia funciona con spring security.
 * */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsFilter implements Filter{
	
	@Autowired
	private LarosProjectApiProperty larosProjectApiProperty; 
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		
		//Se envia en todas las solcitudes.
		res.setHeader("Access-Control-Allow-Origin", larosProjectApiProperty.getOrigenPermitido());
		res.setHeader("Access-Control-Allow-Credentials", "true"); // refresh
		
		if("OPTIONS".equals(req.getMethod()) && larosProjectApiProperty.getOrigenPermitido().equals(req.getHeader("Origin"))) {
			
			res.setHeader("Access-Control-Allow-Methods", "POST, GET, DELETE, PUT, OPTIONS");
			res.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type, Accept");
			res.setHeader("Access-Control-Max-Age", "3600");//1 hora tiempo que el buscador hace proxima solicitud.
			
			res.setStatus(HttpServletResponse.SC_OK);
		} else {
			chain.doFilter(req, res);//Sigue flujo normal.
		}
	}
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}


	@Override
	public void destroy() {
	}
	
}
