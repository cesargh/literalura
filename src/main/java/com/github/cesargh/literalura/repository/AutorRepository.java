package com.github.cesargh.literalura.repository;

import com.github.cesargh.literalura.model.Autor;
import jakarta.persistence.Tuple;
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

    //region [Category: SQL Native Queries]

    // Consultas SQL puras ejecutadas sobre el modelo de objetos del repositorio.

    @Query( nativeQuery = true,
            value = """
                    SELECT a.*
                    FROM autores a
                    WHERE NOT ((a.anio_nacimiento is null) or (a.anio_muerte is null))
                    ORDER BY (a.anio_muerte - a.anio_nacimiento) ASC
                    LIMIT 10
                    """)
    List<Autor> obtenerTop10Jovenes();

    @Query( nativeQuery = true,
            value = """
                    SELECT COUNT(DISTINCT a.nombre) AS valor, d.codigo AS descripcion
                    FROM autores a
                    INNER JOIN libros_autores b ON b.autor_id = a.id
                    INNER JOIN libros_idiomas c ON c.libro_id = b.libro_id
                    INNER JOIN idiomas d ON d.id = c.idioma_id
                    GROUP BY d.codigo
                    ORDER BY valor DESC, descripcion ASC
                    """)
    List<Tuple> obtenerCantidadesPorIdioma();

    //endregion [Category: SQL Native Queries]
}
