package com.aluracursos.literalura.repository;

import com.aluracursos.literalura.model.Idioma;
import com.aluracursos.literalura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RepositorioLibro extends JpaRepository<Libro, Long> {
    List<Libro> findByIdioma(Idioma idioma);
//    Optional<Libro> findByTituloContainsIgnoreCase(String nombreLibro);
}
