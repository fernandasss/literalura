package com.aluracursos.literalura.repository;

import com.aluracursos.literalura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface Repositorio extends JpaRepository<Libro, Long> {
//    Optional<Libro> findByTituloContainsIgnoreCase(String nombreLibro);
}
