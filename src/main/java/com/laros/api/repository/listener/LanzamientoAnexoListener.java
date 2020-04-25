package com.laros.api.repository.listener;

import javax.persistence.PostLoad;

import org.springframework.util.StringUtils;

import com.laros.api.LarosApiApplication;
import com.laros.api.model.Lanzamiento;
import com.laros.api.storage.S3;

/*
 * 22.36. Configurando URL do anexo
 * */
public class LanzamientoAnexoListener {
	
	@PostLoad
	public void postLoad(Lanzamiento lanzamiento) {
		if (StringUtils.hasText(lanzamiento.getAnexo())) {
			S3 s3 = LarosApiApplication.getBean(S3.class);
			lanzamiento.setUrlAnexo(s3.configurarUrl(lanzamiento.getAnexo()));
		}
	}

}