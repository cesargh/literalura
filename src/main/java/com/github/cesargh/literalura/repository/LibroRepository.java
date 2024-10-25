package com.github.cesargh.literalura.repository;

import com.github.cesargh.literalura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LibroRepository extends JpaRepository<Libro,Long> {

    // JPA Consultas Derivadas
    boolean existsById(Long id);
    Optional<List<Libro>> findByIdIn(List<Long> ids);

}
