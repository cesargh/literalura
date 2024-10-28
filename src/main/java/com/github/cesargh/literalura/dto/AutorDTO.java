package com.github.cesargh.literalura.dto;

import java.util.List;

public record AutorDTO(
        Long id,
        String nombre,
        Integer anioNacimiento,
        Integer anioMuerte,
        List<LibroDTO> libros
) {
}
