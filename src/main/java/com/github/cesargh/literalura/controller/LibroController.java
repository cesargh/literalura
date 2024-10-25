package com.github.cesargh.literalura.controller;

import com.github.cesargh.literalura.dto.AutorDTO;
import com.github.cesargh.literalura.dto.IdiomaDTO;
import com.github.cesargh.literalura.dto.LibroDTO;
import com.github.cesargh.literalura.model.Libro;
import com.github.cesargh.literalura.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;

@Controller
public class LibroController {

    @Autowired
    private LibroService libroService;

    public Optional<List<LibroDTO>> BuscarLibrosPorTitulo(String titulo) {
        Optional<List<Libro>> librosBuscados = libroService.BuscarLibrosPorTitulo(titulo);
        if (librosBuscados.isEmpty()) {
            return Optional.empty();
        } else {
            List<LibroDTO> libros = librosBuscados.get().stream().map(e -> new LibroDTO(
                    e.getId(),
                    e.getTitulo(),
                    e.getDescargas(),
                    e.getAutores().stream().map(x -> new AutorDTO(
                            x.getId(),
                            x.getNombre(),
                            x.getAnioNacimiento(),
                            x.getAnioMuerte()
                    )).toList(),
                    e.getIdiomas().stream().map(y -> new IdiomaDTO(
                            y.getId(),
                            y.getCodigo()
                    )).toList()
            )).toList();
            return Optional.of(libros);
        }
    }

}
