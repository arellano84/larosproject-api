package com.laros.api.storage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final String BASE_DIR = "uploads/";

    @Override
    public String salvarTemporariamente(MultipartFile fichero, String directorio) {

        try {
            String nombre = UUID.randomUUID() + "_" + fichero.getOriginalFilename();
            Path path = Paths.get(BASE_DIR + directorio + nombre);

            Files.createDirectories(path.getParent());
            Files.copy(fichero.getInputStream(), path);

            logger.debug("Fichero {} guardado temporalmente.",
                    path.toAbsolutePath());

            return path.toString();

        } catch (IOException e) {
            throw new RuntimeException("Error guardando archivo local", e);
        }
    }

    @Override
    public void salvar(String objeto) {
        // no hacer nada o mover archivo si quieres
    }

    @Override
    public void remover(String objeto) {
        try {
            Files.deleteIfExists(Paths.get(objeto));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void substituir(String objetoAntigo, String objetoNovo) {
        if (objetoAntigo != null) {
            remover(objetoAntigo);
        }
    }

    @Override
    public String configurarUrl(String objeto) {
        return "/uploads/" + objeto;
    }
}
