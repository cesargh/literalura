package com.github.cesargh.literalura.service;

import com.github.cesargh.literalura.model.Autor;
import com.github.cesargh.literalura.repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AutorService {

    @Autowired
    private AutorRepository autorRepository;

    public List<Autor> ObtenerPorNombre(String nombre) {
        return autorRepository.findByNombreContainingIgnoreCaseOrderByNombre(nombre);
    }

    public List<Autor> InformarVivosPorAnio(int anio) {
        // JPA Derived Query
        return autorRepository.findByAnioNacimientoLessThanEqualAndAnioMuerteGreaterThanEqualOrderByNombreAsc(anio,anio);
        // Usando JPQL Query
        //return autorRepository.obtenerAutoresVivos(anio);
    }

}
