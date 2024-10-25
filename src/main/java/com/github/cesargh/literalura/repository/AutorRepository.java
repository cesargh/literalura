package com.github.cesargh.literalura.repository;

import com.github.cesargh.literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AutorRepository extends JpaRepository<Autor,Long> {

    // JPA Consultas Derivadas
    boolean existsByNombre(String nombre);
    Optional<Autor> findByNombre(String nombre);

}
