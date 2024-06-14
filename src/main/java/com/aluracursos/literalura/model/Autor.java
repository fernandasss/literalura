package com.aluracursos.literalura.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "autor")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(unique = true)
    private String nombre;
    private String fechaNacimiento;
    private String fechaFallecimiento;
    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Libro> libro;

    public Autor(){}

    public Autor(DatosAutor datosAutor) {
        this.nombre = datosAutor.nombre();
        this.fechaNacimiento = datosAutor.fechaDeNacimiento();
        this.fechaFallecimiento = datosAutor.fechaDefallecimiento();
    }

    @Override
    public String toString() {
        return "Autor{" +
                "Id=" + Id +
                ", nombre='" + nombre + '\'' +
                ", añoNacimiento='" + fechaNacimiento + '\'' +
                ", añoFallecimiento='" + fechaFallecimiento + '\'' +
                '}';
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAñoNacimiento() {
        return fechaNacimiento;
    }

    public void setAñoNacimiento(String añoNacimiento) {
        this.fechaNacimiento = añoNacimiento;
    }

    public String getAñoFallecimiento() {
        return fechaFallecimiento;
    }

    public void setAñoFallecimiento(String añoFallecimiento) {
        this.fechaFallecimiento = añoFallecimiento;
    }

    public List<Libro> getLibro() {
        return libro;
    }

    public void setLibro(List<Libro> libro) {
        this.libro = libro;
    }
}
