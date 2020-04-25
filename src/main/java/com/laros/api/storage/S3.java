package com.laros.api.storage;

import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AccessControlList;
import com.amazonaws.services.s3.model.GroupGrantee;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.ObjectTagging;
import com.amazonaws.services.s3.model.Permission;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.Tag;
import com.laros.api.config.property.LarosProjectApiProperty;

/*
 * 22.32. Implementando o envio do arquivo para o S3
 * */
@Component
public class S3 {
	
	private static final Logger logger = LoggerFactory.getLogger(S3.class);
	
	@Autowired
	private LarosProjectApiProperty property;
	
	@Autowired
	private AmazonS3 amazonS3;
	
	public String salvarTemporariamente(MultipartFile fichero) {
		AccessControlList acl = new AccessControlList();
		acl.grantPermission(GroupGrantee.AllUsers, Permission.Read);
		
		ObjectMetadata objectMetadata = new ObjectMetadata();
		objectMetadata.setContentType(fichero.getContentType());
		objectMetadata.setContentLength(fichero.getSize());
		
		String nombreUnico = generarNombreUnico(fichero.getOriginalFilename());
		//String nomePrefixado = "lancamentos/" + nomeUnico; // se puede agregar directorio.
		
		try {
			PutObjectRequest putObjectRequest = new PutObjectRequest(
					property.getS3().getBucket(),
					nombreUnico,
					fichero.getInputStream(), 
					objectMetadata)
					.withAccessControlList(acl);
			
			putObjectRequest.setTagging(new ObjectTagging(
					Arrays.asList(new Tag("expirar", "true")))); // Tiene que ser igual al configurado en S3Config.
			
			amazonS3.putObject(putObjectRequest);
			
			if (logger.isDebugEnabled()) {
				logger.debug("Fichero {} enviado con éxito para S3.", 
						fichero.getOriginalFilename());
			}
			
			return nombreUnico;
		} catch (IOException e) {
			throw new RuntimeException("Problemas al intentar enviar el fichero para S3.", e);
		}
	}

	private String generarNombreUnico(String originalFilename) {
		return UUID.randomUUID().toString() + "_" + originalFilename;
	}
	
	/*
	 * 22.33. Enviando arquivos para o S3
	 * */
	public String configurarUrl(String objeto) {
		return "\\\\" + property.getS3().getBucket() +
				".s3.amazonaws.com/" + objeto;
	}

}