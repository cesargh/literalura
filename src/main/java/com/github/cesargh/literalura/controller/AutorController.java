package com.github.cesargh.literalura.controller;

import com.github.cesargh.literalura.dto.AutorDTO;
import com.github.cesargh.literalura.dto.EstadisticaDTO;
import com.github.cesargh.literalura.dto.IdiomaDTO;
import com.github.cesargh.literalura.dto.LibroDTO;
import com.github.cesargh.literalura.model.Autor;
import com.github.cesargh.literalura.service.AutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AutorController {

    @Autowired
    private AutorService autorService;

    private List<AutorDTO> TransformarAutorDTO(List<Autor> autores) {
        if (autores.isEmpty()) {
            return new ArrayList<>();
        } else {
            return autores.stream()
                .map(e -> new AutorDTO
                    (
                    e.getId(),
                    e.getNombre(),
                    e.getAnioNacimiento(),
                    e.getAnioMuerte(),
                    e.getLibros().stream()
                        .map(x -> new LibroDTO
                            (
                            x.getId(),
                            x.getTitulo(),
                            x.getDescargas(),
                            new ArrayList<>(),
                            x.getIdiomas().stream()
                                .map(y -> new IdiomaDTO
                                    (
                                    y.getId(),
                                    y.getCodigo()
                                    )
                                ).toList()
                            )
                        )
                    .toList()
                    )
                ).toList();
        }
    }

    public List<EstadisticaDTO> InformarCantidadesPorIdioma() {
        return autorService.ObtenerCantidadesPorIdioma()
            .stream()
            .map(e -> new EstadisticaDTO
                (
                e.get("valor", Long.class),
                e.get("descripcion", String.class)
                )
            )
            .toList();
    }

    public List<AutorDTO> InformarPorNombre(String nombre) {
        return TransformarAutorDTO(autorService.ObtenerPorNombre(nombre));
    }

    public List<AutorDTO> InformarTop10Jovenes() {
        return TransformarAutorDTO(autorService.ObtenerTop10Jovenes());
    }

    public List<AutorDTO> InformarVivosPorAnio(int anio) {
        return TransformarAutorDTO(autorService.ObtenerVivosPorAnio(anio));
    }

}
