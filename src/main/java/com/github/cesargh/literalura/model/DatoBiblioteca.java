package com.github.cesargh.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatoBiblioteca(
        @JsonAlias("next") String siguienteURL,
        @JsonAlias("results") List<DatoLibro> libros
) {
}