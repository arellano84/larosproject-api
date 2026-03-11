package com.laros.api.storage;

import com.laros.api.config.property.LarosProjectApiProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

/*
 * 10/03/2026: Local Storage.
 * */
@Component
@Profile("local")
public class LocalStorageService implements StorageService {

    private static final Logger logger = LoggerFactory.getLogger(LocalStorageService.class);

    @Autowired
    private LarosProjectApiProperty propiedades;

    @Override
    public String salvarTemporariamente(MultipartFile fichero, String directorio) {

        try {
            String nombre = UUID.randomUUID() + "_" + fichero.getOriginalFilename();
            Path path = Paths.get(propiedades.getStorage().getBaseDir() + directorio + nombre);

            Files.createDirectories(path.getParent());
            Files.copy(fichero.getInputStream(), path);

            logger.debug("Fichero {} guardado temporalmente.",
                    path.toAbsolutePath());

            return path.toAbsolutePath().toString();

        } catch (IOException e) {
            throw new RuntimeException("Error guardando archivo local", e);
        }
    }

    @Override
    public void salvar(String objeto) {
        logger.debug("Fichero {} guardado.", objeto);
        // no hacer nada o mover archivo si quieres
    }

    @Override
    public void remover(String objeto) {
        try {
            logger.debug("Fichero {} eliminado.", objeto);
            Files.deleteIfExists(Paths.get(objeto));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void substituir(String objetoAntigo, String objetoNovo) {
        if (objetoAntigo != null) {
            logger.debug("Fichero {} sustituido por {}.", objetoAntigo, objetoNovo);
            remover(objetoAntigo);
        }
    }

    @Override
    public String configurarUrl(String objeto) {
        return propiedades.getStorage().getBaseDir() + objeto;
    }
}
