package com.laros.api.storage;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
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
import com.amazonaws.services.s3.model.SetObjectTaggingRequest;
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
	
	public String salvarTemporariamente(MultipartFile fichero, String directorio) {
		AccessControlList acl = new AccessControlList();
		acl.grantPermission(GroupGrantee.AllUsers, Permission.Read);
		
		ObjectMetadata objectMetadata = new ObjectMetadata();
		objectMetadata.setContentType(fichero.getContentType());
		objectMetadata.setContentLength(fichero.getSize());
		
		String nombreUnico = generarNombreUnico(fichero.getOriginalFilename());
		String nomePrefixado = directorio + nombreUnico; // se puede agregar directorio.
		
		try {
			PutObjectRequest putObjectRequest = new PutObjectRequest(
					property.getS3().getBucket(),
					nomePrefixado,
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
			
			return nomePrefixado;
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

	/*
	 * 22.34. Anexando arquivo no lançamento
	 * */
	public void salvar(String objeto) {
		
		SetObjectTaggingRequest setObjectTaggingRequest = new SetObjectTaggingRequest(
				property.getS3().getBucket(), 
				objeto, 
				new ObjectTagging(Collections.emptyList()));
		
		amazonS3.setObjectTagging(setObjectTaggingRequest);
	}

}