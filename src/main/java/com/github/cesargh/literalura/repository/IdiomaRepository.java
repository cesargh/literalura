package com.github.cesargh.literalura.repository;

import com.github.cesargh.literalura.model.Idioma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IdiomaRepository extends JpaRepository<Idioma,Long> {

    // JPA Consultas Derivadas
    boolean existsByCodigo(String codigo);
    Optional<Idioma> findByCodigo(String codigo);

}
