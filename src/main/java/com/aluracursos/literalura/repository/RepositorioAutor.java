package com.aluracursos.literalura.repository;

import com.aluracursos.literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RepositorioAutor extends JpaRepository<Autor, Long> {
    Autor findByNombreAndFechaDeNacimientoAndFechaDeFallecimiento(String nombre, Integer fechaDeNacimiento, Integer fechaDeFallecimiento );


    @Query("SELECT a FROM Autor a WHERE a.fechaDeNacimiento < :fechaBuscada AND a.fechaDeFallecimiento > :fechaBuscada")
    List<Autor> autoresVivos(int fechaBuscada);

}
