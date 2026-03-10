package com.laros.api.storage;

import org.springframework.web.multipart.MultipartFile;

/*
 * 10/03/2026
 * Mejora: Uso de Storage para local o para producción (S3).
 * */
public interface StorageService {

    String salvarTemporariamente(MultipartFile fichero, String directorio);

    void salvar(String objeto);

    void remover(String objeto);

    void substituir(String objetoAntigo, String objetoNovo);

    String configurarUrl(String objeto);
}