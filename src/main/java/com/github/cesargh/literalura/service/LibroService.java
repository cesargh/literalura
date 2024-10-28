package com.github.cesargh.literalura.service;

import com.github.cesargh.literalura.model.Autor;
import com.github.cesargh.literalura.model.DatoAutor;
import com.github.cesargh.literalura.model.DatoLibro;
import com.github.cesargh.literalura.model.Idioma;
import com.github.cesargh.literalura.model.Libro;
import com.github.cesargh.literalura.repository.AutorRepository;
import com.github.cesargh.literalura.repository.IdiomaRepository;
import com.github.cesargh.literalura.repository.LibroRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Tuple;
import org.hibernate.Transaction;
import org.hibernate.engine.spi.SessionImplementor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class LibroService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private IdiomaRepository idiomaRepository;

    private void Persistir(List<DatoLibro> librosBuscados) {
        System.out.println("DEBUG : LibroService.Persistir --> INICIO");
        SessionImplementor sessionImp = (SessionImplementor) entityManager.getDelegate();
        Transaction transaction = sessionImp.getTransaction();
        try {
            transaction.begin();
            System.out.println("DEBUG : LibroService.Persistir --> TRANSACTION BEGIN");
            for(String codigo : librosBuscados.stream().flatMap(e -> e.idiomas().stream()).distinct().toList()) {
                if (! idiomaRepository.existsByCodigo(codigo)) {
                    System.out.printf("DEBUG : Guardando idioma con Código = %s\n", codigo);
                    idiomaRepository.save(new Idioma(codigo));
                 }
            }
            for(String nombre : librosBuscados.stream().flatMap(e -> e.autores().stream()).map(DatoAutor::nombre).distinct().toList()) {
                if (! autorRepository.existsByNombre(nombre)) {
                    System.out.printf("DEBUG : Guardando autor con Nombre = %s\n", nombre);
                    var optDatoAutor = librosBuscados.stream().flatMap(e -> e.autores().stream()).filter(e -> e.nombre().equals(nombre)).findFirst();
                    if(optDatoAutor.isPresent()) {
                        DatoAutor datoAutor = optDatoAutor.get();
                        autorRepository.save(new Autor(nombre,datoAutor.anioNacimiento(),datoAutor.anioMuerte()));
                    }
                }
            }
            for(DatoLibro datoLibro : librosBuscados) {
                if (! libroRepository.existsById(datoLibro.id())) {
                    System.out.printf("DEBUG : Guardando libro con Id = %s\n", datoLibro.id());
                    Libro libro = new Libro(datoLibro.id(), datoLibro.titulo(), datoLibro.descargas());
                    for(String codigo : datoLibro.idiomas()) {
                        var optIdioma = idiomaRepository.findByCodigo(codigo);
                        optIdioma.ifPresent(idioma -> libro.getIdiomas().add(idioma));
                    }
                    for(DatoAutor datoAutor : datoLibro.autores()) {
                        var optAutor = autorRepository.findByNombre(datoAutor.nombre());
                        optAutor.ifPresent(autor -> libro.getAutores().add(autor));
                    }
                    libroRepository.save(libro);
                }
            }
            transaction.commit();
            System.out.println("DEBUG : LibroService.Persistir --> TRANSACTION COMMIT");
        } catch (Exception e) {
            transaction.rollback();
            System.out.println("DEBUG : LibroService.Persistir --> TRANSACTION ROLLBACK");
            throw new LibroServiceException(e);
        } finally {
            System.out.println("DEBUG : LibroService.Persistir --> FIN");
        }
    }

    public List<Libro> BuscarPorTitulo(String titulo) {
        List<DatoLibro> librosBuscados = Buscador.BuscarLibrosPorTitulo(titulo);
        if (librosBuscados.isEmpty()) {
            return new ArrayList<>();
        } else {
            Persistir(librosBuscados);
            return libroRepository.findByIdInOrderByTitulo(librosBuscados.stream().map(DatoLibro::id).toList());
        }
    }

    public List<Tuple> ObtenerCantidadesPorIdiomaConSQLNativo() {
        return libroRepository.obtenerCantidadesPorIdioma();
    }

    public List<Map.Entry<String,Long>> ObtenerCantidadesPorIdiomaConStreams() {
        var resultado = ObtenerTodos();
        if (resultado.isEmpty()) {
            return new ArrayList<>();
        } else {
            return resultado.stream()
                .flatMap(libro -> libro.getIdiomas().stream().map(Idioma::getCodigo))
                .collect(Collectors.groupingBy(idioma -> idioma, Collectors.counting()))
                .entrySet().stream()
                .sorted((entry1, entry2) -> {
                    int valueComparison = entry2.getValue().compareTo(entry1.getValue());
                    if (valueComparison != 0) {
                        return valueComparison;
                    } else {
                        return entry1.getKey().compareTo(entry2.getKey());
                    }
                })
                .toList();
        }
    }

    public List<Libro> ObtenerPorTitulo(String titulo) {
        return libroRepository.findByTituloContainingIgnoreCaseOrderByTitulo(titulo);
    }

    public List<Libro> ObtenerTodos() {
        return libroRepository.findAll();
    }

}
