package com.github.cesargh.literalura.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "libros",
        uniqueConstraints = {@UniqueConstraint(name = "uc_libros_id", columnNames = {"id"})}
)
public class Libro {

    @Id
    @Column(nullable=false)
    private Long id;

    @Column(nullable=false, length=1024)
    private String titulo;

    @Column(nullable=false)
    private Long descargas;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(
            name = "libros_autores",
            joinColumns = @JoinColumn(name = "libro_id", foreignKey = @ForeignKey(name = "fk_libros_autores_libro_id")),
            inverseJoinColumns = @JoinColumn(name = "autor_id", foreignKey = @ForeignKey(name = "fk_libros_autores_autor_id")))
    private List<Autor> autores = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "libros_idiomas",
            joinColumns = @JoinColumn(name = "libro_id", foreignKey = @ForeignKey(name = "fk_libros_idiomas_libro_id")),
            inverseJoinColumns = @JoinColumn(name = "idioma_id", foreignKey = @ForeignKey(name = "fk_libros_idiomas_idioma_id")))
    private List<Idioma> idiomas = new ArrayList<>();

    public Libro() {}

    public Libro(Long id, String titulo, Long descargas) {
        this.id = id;
        this.titulo = titulo;
        this.descargas = descargas;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Long getDescargas() {
        return descargas;
    }

    public void setDescargas(Long descargas) {
        this.descargas = descargas;
    }

    public List<Autor> getAutores() {
        return autores;
    }

    public void setAutores(List<Autor> autores) {
        this.autores = autores;
    }

    public List<Idioma> getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(List<Idioma> idiomas) {
        this.idiomas = idiomas;
    }

}
