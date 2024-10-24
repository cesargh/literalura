package com.github.cesargh.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatoLibro(
        @JsonAlias("id") Long id,
        @JsonAlias("title") String titulo,
        @JsonAlias("download_count") Long descargas,
        @JsonAlias("authors") List<DatoAutor> autores,
        @JsonAlias("languages") List<String> idiomas
) {
}
