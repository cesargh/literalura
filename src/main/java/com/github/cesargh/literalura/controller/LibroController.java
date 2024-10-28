package com.github.cesargh.literalura.controller;

import com.github.cesargh.literalura.dto.AutorDTO;
import com.github.cesargh.literalura.dto.EstadisticaDTO;
import com.github.cesargh.literalura.dto.IdiomaDTO;
import com.github.cesargh.literalura.dto.LibroDTO;
import com.github.cesargh.literalura.model.Libro;
import com.github.cesargh.literalura.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@Controller
public class LibroController {

    @Autowired
    private LibroService libroService;

    private List<LibroDTO> TransformarLibroDTO(List<Libro> libros) {
        if (libros.isEmpty()) {
            return new ArrayList<>();
        } else {
            return libros.stream()
                .map(e -> new LibroDTO
                    (
                    e.getId(),
                    e.getTitulo(),
                    e.getDescargas(),
                    e.getAutores().stream()
                        .map(x -> new AutorDTO
                            (
                            x.getId(),
                            x.getNombre(),
                            x.getAnioNacimiento(),
                            x.getAnioMuerte(),
                            new ArrayList<>()
                            )
                        ).toList(),
                    e.getIdiomas().stream()
                        .map(y -> new IdiomaDTO
                            (
                            y.getId(),
                            y.getCodigo()
                            )
                        ).toList()
                    )
                ).toList();
        }
    }

    public List<LibroDTO> BuscarPorTitulo(String titulo) {
        return TransformarLibroDTO(libroService.BuscarPorTitulo(titulo));
    }

    public List<EstadisticaDTO> InformarCantidadesPorIdioma() {
        return libroService.ObtenerCantidadesPorIdiomaConSQLNativo()
            .stream()
            .map(e -> new EstadisticaDTO
                (
                e.get("valor", Long.class),
                e.get("descripcion", String.class)
                )
            )
            .toList();

        // IMPORTANTE:
        // El objetivo del Challenge era utilizar Streams + JPA Derived Queries,
        // pero cuando tenemos muchos libros se vuelve ineficiente.
        // Aquí dejo la funcionalidad para cumplir con el objetivo,
        // pero queda activa la que utiliza directamente JPQL Native Queries.

//        return libroService.ObtenerCantidadesPorIdiomaConStreams().stream()
//            .map(e -> new EstadisticaDTO
//                (
//                e.getValue(),
//                e.getKey()
//                )
//            )
//            .toList();

    }

    public List<LibroDTO> InformarPorTitulo(String titulo) {
        return TransformarLibroDTO(libroService.ObtenerPorTitulo(titulo));
    }

}
