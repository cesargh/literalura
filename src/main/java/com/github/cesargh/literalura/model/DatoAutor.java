package com.github.cesargh.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatoAutor(
        @JsonAlias("name") String nombre,
        @JsonAlias("birth_year") int anioNacimiento,
        @JsonAlias("death_year") int anioMuerte
) {
}
