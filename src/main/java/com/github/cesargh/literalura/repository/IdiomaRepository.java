package com.github.cesargh.literalura.repository;

import com.github.cesargh.literalura.model.Idioma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IdiomaRepository extends JpaRepository<Idioma,Long> {

    //region [Category: JPA Derived Queries]

    // Consultas generadas automáticamente a partir del nombre del método.

    boolean existsByCodigo(String codigo);
    Optional<Idioma> findByCodigo(String codigo);

    //endregion [Category: JPA Derived Queries]

}
