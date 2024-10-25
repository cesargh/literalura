package com.github.cesargh.literalura.dto;

import java.util.List;

public record LibroDTO(
        Long id,
        String titulo,
        Long descargas,
        List<AutorDTO> autores,
        List<IdiomaDTO> idiomas
) {
}
