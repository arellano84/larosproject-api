package com.laros.api.service;

import java.io.InputStream;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.laros.api.dto.MovimientoEstadisticaPersona;
import com.laros.api.model.Lanzamiento;
import com.laros.api.model.Persona;
import com.laros.api.repository.LanzamientoRepository;
import com.laros.api.repository.PersonaRepository;
import com.laros.api.service.exception.PersonaInexistenteOInactivaException;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class LanzamientoService {

	@Autowired
	private PersonaRepository personaRepository;
	
	@Autowired
	private LanzamientoRepository lanzamientoRepository;

	
	/*
	 * 22.15. Criando um agendamento de tarefa (Scheduler)
	 * */
	// @Scheduled(fixedDelay = 1000 * 60 * 60) //cada hora //TODO: desactivado
	public void notificarSobreMovimientosVencidos() {
		System.out.println("[LanzamientoService.notificarSobreMovimientosVencidos]---------->Método siendo ejecutado...");
	}
	
	/*
	 * 22.15. Criando um agendamento de tarefa (Scheduler) -> chon
	 * */
	//@Scheduled(cron = "0 05 13 * * *") // Todos los dias a las 13:05, * indica siempre OjO. //TODO: desactivado
	public void notificarSobreMovimientosVencidosPlanificado() {
		System.out.println("[LanzamientoService.notificarSobreMovimientosVencidosPlanificado]---------->Método siendo ejecutado...");
	}
	
	/*
	 * 22.13. Gerando os bytes do relatório
	 * */
	public byte[] informePorPersona(LocalDate inicio, LocalDate fin) throws Exception {
		//Consulta de datos
		List<MovimientoEstadisticaPersona> datos = lanzamientoRepository.porPersona(inicio, fin);
		
		//Agregamos parámetros.
		Map<String, Object> parametros = new HashMap();
		parametros.put("DT_INICIO", Date.valueOf(inicio));
		parametros.put("DT_FIN", Date.valueOf(fin));
		parametros.put("REPORT_LOCALE", new Locale("es", "ES")); //Formato local
		
		//Seleccionando informe a generar
		InputStream inputStream = this.getClass().getResourceAsStream("/informes/movimientos-por-persona.jasper");

		//Generar informe
		JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, parametros, new JRBeanCollectionDataSource(datos));

		//Generar pdf
		return JasperExportManager.exportReportToPdf(jasperPrint);
		
	}
	
	public Lanzamiento guardar(Lanzamiento lanzamiento) throws PersonaInexistenteOInactivaException {
		validarPersona(lanzamiento);
		
		return lanzamientoRepository.save(lanzamiento);
	}
	
	public Lanzamiento actualizar(Long codigo, Lanzamiento lanzamiento) throws PersonaInexistenteOInactivaException {
		Lanzamiento lanzamientoGuardado = buscarLanzamientoExistente(codigo);
		if(!lanzamiento.getPersona().equals(lanzamientoGuardado.getPersona())) {
			validarPersona(lanzamiento);
		}
		
		//Copiar propiedades de un objeto a otro, excepto el código.
		BeanUtils.copyProperties(lanzamiento, lanzamientoGuardado, "codigo");
		
		return lanzamientoRepository.save(lanzamientoGuardado);
	}
	
	private void validarPersona(Lanzamiento lanzamiento) throws PersonaInexistenteOInactivaException {
		Persona persona = null;
		if(lanzamiento.getPersona().getCodigo() != null) {
			persona = personaRepository.findOne(lanzamiento.getPersona().getCodigo());
		}
		
		if (persona == null || persona.isInactivo()) {
			throw new PersonaInexistenteOInactivaException();
		}
	}

	private Lanzamiento buscarLanzamientoExistente(Long codigo) {
		Lanzamiento lanzamientoGuardado = lanzamientoRepository.findOne(codigo);
		if(lanzamientoGuardado==null)
			throw new IllegalArgumentException();
		return lanzamientoGuardado;
	}

}
