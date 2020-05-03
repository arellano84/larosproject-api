package com.laros.api.service;

import java.io.InputStream;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.laros.api.dto.MovimientoEstadisticaPersona;
import com.laros.api.mail.Mailer;
import com.laros.api.model.Lanzamiento;
import com.laros.api.model.Persona;
import com.laros.api.model.Usuario;
import com.laros.api.repository.LanzamientoRepository;
import com.laros.api.repository.PersonaRepository;
import com.laros.api.repository.UsuarioRepository;
import com.laros.api.service.exception.PersonaInexistenteOInactivaException;
import com.laros.api.storage.S3;
import com.laros.api.util.Constantes;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class LanzamientoService {

	//22.22. Incluindo logs no agendamento do envio de e-mail
	private static final Logger logger = LoggerFactory.getLogger(LanzamientoService.class);	
	
	private static final String ROLE_DESTINATARIOS = "ROLE_PESQUISAR_LANCAMENTO";
	
	@Autowired
	private PersonaRepository personaRepository;
	
	@Autowired
	private LanzamientoRepository lanzamientoRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private Mailer mailer;
	
	// 22.34. Anexando arquivo no lançamento
	@Autowired
	private S3 s3;
	
	/*
	 * 22.15. Criando um agendamento de tarefa (Scheduler)
	 * */
	// @Scheduled(fixedDelay = 1000 * 60 * 60) //cada hora //TODO: email: activar envio
	public void notificarSobreMovimientosVencidos() {
		
		if(logger.isDebugEnabled()) {
			logger.debug("[notificarSobreMovimientosVencidos] INICIO...");
		}
		
		//22.21. Agendando o envio de e-mail
		
		//Consultado Movimientos Vencidos
		List<Lanzamiento> lanzVencidos = lanzamientoRepository.findByFechaVencimientoLessThanEqualAndFechaPagoIsNull(LocalDate.now());
		//Consultado Destinatarios
		List<Usuario> destinatarios = usuarioRepository.findByPermisosDescipcion(ROLE_DESTINATARIOS);
		
		
		if(lanzVencidos.isEmpty()) {
			logger.debug("[notificarSobreMovimientosVencidos] Sin lanzamientos vencidos.");
			return ;
		}
		
		logger.debug("[notificarSobreMovimientosVencidos] Existen {} lanzamientos vencidos.", lanzVencidos.size());
		
		if(destinatarios.isEmpty()) {
			logger.warn("[notificarSobreMovimientosVencidos] Sin destinatarios.");
			return ;
		}
		
		//Envia email con template.
		mailer.avisarSobreLanzamentosVencidos(lanzVencidos, destinatarios);
				
		logger.info("[notificarSobreMovimientosVencidos] FIN.");
	}
	
	/*
	 * 22.15. Criando um agendamento de tarefa (Scheduler) -> chon
	 * */
	//@Scheduled(cron = "0 05 13 * * *") // Todos los dias a las 13:05, * indica siempre OjO. //TODO: desactivado
	public void notificarSobreMovimientosVencidosPlanificado() {
		System.out.println("[LanzamientoService.notificarSobreMovimientosVencidosPlanificado]----------> Ejecutado...");
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
		
		// 22.34. Anexando arquivo no lançamento
		if(StringUtils.hasText(lanzamiento.getAnexo())) {
			// Si  hay anexo lo enviará a S3
			s3.salvar(lanzamiento.getAnexo());
		}
		
		return lanzamientoRepository.save(lanzamiento);
	}
	
	public Lanzamiento actualizar(Long codigo, Lanzamiento lanzamiento) throws PersonaInexistenteOInactivaException {
		
		logger.debug("[actualizar] INICIO...");
		
		Lanzamiento lanzamientoGuardado = buscarLanzamientoExistente(codigo);
		if(!lanzamiento.getPersona().equals(lanzamientoGuardado.getPersona())) {
			validarPersona(lanzamiento);
		}
		
		// 22.35. Atualizando e removendo anexo
		if (StringUtils.isEmpty(lanzamiento.getAnexo())
				&& StringUtils.hasText(lanzamientoGuardado.getAnexo())) {
			s3.remover(lanzamientoGuardado.getAnexo());
		} else if (StringUtils.hasLength(lanzamiento.getAnexo())
				&& !lanzamiento.getAnexo().equals(lanzamientoGuardado.getAnexo())) {
			s3.substituir(lanzamientoGuardado.getAnexo(), lanzamiento.getAnexo());
		}
		
		
		//Copiar propiedades de un objeto a otro, excepto el código.
		BeanUtils.copyProperties(lanzamiento, lanzamientoGuardado, "codigo");
		
		logger.debug("[actualizar] FIN.");
		
		return lanzamientoRepository.save(lanzamientoGuardado);
	}
	
	private void validarPersona(Lanzamiento lanzamiento) throws PersonaInexistenteOInactivaException {
		Persona persona = null;
		if(lanzamiento.getPersona().getCodigo() != null) {
			persona = personaRepository.getOne(lanzamiento.getPersona().getCodigo());
		}
		
		if (persona == null || persona.isInactivo()) {
			throw new PersonaInexistenteOInactivaException();
		}
	}

	private Lanzamiento buscarLanzamientoExistente(Long codigo) {
		// 25.2. Novas assinaturas do Spring Data JPA
		Optional<Lanzamiento> lanzamientoGuardado = lanzamientoRepository.findById(codigo);
		if(!lanzamientoGuardado.isPresent())
			throw new IllegalArgumentException();
		return lanzamientoGuardado.get();
	}
	
}
