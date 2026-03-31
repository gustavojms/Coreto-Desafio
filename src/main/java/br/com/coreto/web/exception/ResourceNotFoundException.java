package br.com.coreto.web.exception;

import java.util.UUID;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String entity, UUID id) {
        super(entity + " nao encontrado(a) com id: " + id);
    }
}
