package com.Arias.Literatura.Modelo;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "autores")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private Long annoDeNacimiento;
    private Long annoDeFallecimiento;
    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Libro> Libros;

    public Autor() {
    }

    public Autor(DatosAutor datosAutor) {
        this.nombre = datosAutor.nombre();
        this.annoDeFallecimiento = datosAutor.annoDeFallecimiento();
        this.annoDeNacimiento = datosAutor.annoDeNacimiento();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getAnnoDeFallecimiento() {
        return annoDeFallecimiento;
    }

    public void setAnnoDeFallecimiento(Long annoDeFallecimiento) {
        this.annoDeFallecimiento = annoDeFallecimiento;
    }

    public Long getAnnoDeNacimiento() {
        return annoDeNacimiento;
    }

    public void setAnnoDeNacimiento(Long annoDeNacimiento) {
        this.annoDeNacimiento = annoDeNacimiento;
    }

    public List<Libro> getLibros() {
        return Libros;
    }

    public void setLibros(List<Libro> libros) {
        libros.forEach(e -> e.setAutor(this));
        Libros = libros;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("----- AUTOR -----\n");
        sb.append("Autor: ").append(nombre).append("\n");
        sb.append("Año de nacimiento: ").append(annoDeNacimiento).append("\n");
        sb.append("Año de fallecimiento: ").append(annoDeFallecimiento).append("\n");
        sb.append("Libros:\n");

        // Iterar sobre los libros y construir la cadena
        getLibros().forEach(l -> sb.append(l.getTitulo()).append("\n"));

        sb.append("-------------------\n");
        return sb.toString();
    }
}
