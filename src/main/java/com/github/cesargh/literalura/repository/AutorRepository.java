package com.github.cesargh.literalura.repository;

import com.github.cesargh.literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AutorRepository extends JpaRepository<Autor,Long> {

    //region [Category: JPA Derived Queries]

    // Consultas generadas automáticamente a partir del nombre del método.

    boolean existsByNombre(String nombre);
    Optional<Autor> findByNombre(String nombre);
    List<Autor> findByNombreContainingIgnoreCaseOrderByNombre(String nombre);
    List<Autor> findByAnioNacimientoLessThanEqualAndAnioMuerteGreaterThanEqualOrderByNombreAsc(
            Integer anioNacimiento, Integer anioMuerte);

    //endregion [Category: JPA Derived Queries]

    //region [Category: JPQL Queries]

    // Consultas ejecutadas sobre el modelo de entidades (clases Java)

    @Query("""
            SELECT a 
            FROM Autor a 
            WHERE a.anioNacimiento <= :anio AND a.anioMuerte >= :anio 
            ORDER BY a.nombre ASC, a.anioNacimiento ASC, a.anioMuerte ASC
            """)
    List<Autor> obtenerAutoresVivos(@Param("anio") Integer anio);

    //endregion [Category: JPQL Queries]

}
