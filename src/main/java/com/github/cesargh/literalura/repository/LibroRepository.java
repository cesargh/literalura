package com.github.cesargh.literalura.repository;

import com.github.cesargh.literalura.model.Libro;
import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LibroRepository extends JpaRepository<Libro,Long> {

    //region [Category: JPA Derived Queries]

    // Consultas generadas automáticamente a partir del nombre del método.

    List<Libro> findByIdInOrderByTitulo(List<Long> ids);
    List<Libro> findByTituloContainingIgnoreCaseOrderByTitulo(String titulo);
    List<Libro> findTop10ByOrderByDescargasDesc();

    //endregion [Category: JPA Derived Queries]

    //region [Category: SQL Native Queries]

    // Consultas SQL puras ejecutadas sobre el modelo de objetos del repositorio.

    @Query( nativeQuery = true,
            value = """
                    SELECT COUNT(a.idioma_id) AS valor, b.codigo AS descripcion
                    FROM libros_idiomas a
                    INNER JOIN idiomas b ON b.id = a.idioma_id
                    GROUP BY b.codigo
                    ORDER BY valor DESC, descripcion ASC
                    """)
    List<Tuple> obtenerCantidadesPorIdioma();

    @Query( nativeQuery = true,
            value = """
                    SELECT a.*
                    FROM libros a
                    INNER JOIN libros_idiomas b on b.libro_id = a.id
                    INNER JOIN idiomas c on c.id = b.idioma_id
                    WHERE c.codigo = :codigoIdioma
                    ORDER BY a.titulo
                    """)
    List<Libro> obtenerPorIdioma(@Param("codigoIdioma") String codigoIdioma);

    //endregion [Category: SQL Native Queries]

}
