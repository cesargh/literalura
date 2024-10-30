package com.github.cesargh.literalura.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "idiomas",
        uniqueConstraints = { @UniqueConstraint(name = "uc_idiomas_codigo", columnNames = {"codigo"}) }
)
public class Idioma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, length=5)
    private String codigo;

    @ManyToMany(mappedBy = "idiomas", fetch = FetchType.LAZY)
    @OrderBy("titulo ASC")
    private List<Libro> libros = new ArrayList<>();

    public Idioma() {}

    public Idioma(String codigo) {
        this.codigo = codigo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        this.libros = libros;
    }

}
