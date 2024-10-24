package com.github.cesargh.literalura.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

// La omisión del modificador de acceso implica Package-Private.
final class Conversor {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private Conversor() {
    }

    public static <T> T Convertir(String json, Class<T> clase) throws JsonProcessingException {
        return objectMapper.readValue(json, clase);
    }

}
