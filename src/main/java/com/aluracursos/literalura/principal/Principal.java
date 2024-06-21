package com.aluracursos.literalura.principal;

import com.aluracursos.literalura.model.*;
import com.aluracursos.literalura.repository.RepositorioAutor;
import com.aluracursos.literalura.repository.RepositorioLibro;
import com.aluracursos.literalura.service.ConsumoAPI;
import com.aluracursos.literalura.service.ConvierteDatos;

import java.util.*;

public class Principal {
    private static final String URL_BASE = "https://gutendex.com/books/";
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    private RepositorioLibro repositorio;
    private RepositorioAutor repositorioAutor;
    Scanner teclado = new Scanner(System.in);
    private List<Libro> libro = new ArrayList<>();
    private List<Autor> autor = new ArrayList<>();



    public Principal(RepositorioLibro repositorio, RepositorioAutor repositorioAutor) {
        this.repositorio = repositorio;
        this.repositorioAutor = repositorioAutor;
    }

    public void muestraMenu() {
        var json = consumoAPI.obtenerDatos(URL_BASE);
        System.out.println(json);
        var datos = conversor.obtenerDatos(json, DatosResultados.class);
        System.out.println(datos);
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    1 - Buscar libro por título.
                    2 - Mostrar libros buscados.
                    3 - Mostrar autores buscados.
                    4 - Mostrar autores vivos en determinado año.
                    5 - Mostrar libros en un determinado idioma.


                    0 - Salir
                    """;

            while (opcion != 0) {
                System.out.println(menu);
                try {
                    opcion = teclado.nextInt();
                    teclado.nextLine();
                } catch (InputMismatchException e) {
                    System.out.println("Entrada inválida, intente nuevamente.");
                    teclado.nextLine();
                    continue;
                }
                switch (opcion) {
                    case 1:
                        buscarLibro();
                        break;
                    case 2:
                        mostrarLibrosBuscados();
                        break;
                    case 3:
                        mostrarAutoresBuscados();
                        break;
                    case 4:
                        mostrarAutoresVivos();
                        break;
                    case 5:
                        mostrarLibrosPorIdioma();
                        break;
                    case 0:
                        System.out.println("Cerrando la aplicación...");
                        break;
                    default:
                        System.out.println("Opción inválida");
                }
            }
        }
    }

    private Libro convertirDatosLibroAEntidad(DatosLibro datosLibro) {
        Libro libro = new Libro();
        if (datosLibro != null) {
            libro.setTitulo(datosLibro.titulo());
            libro.setNumeroDescargas(datosLibro.numeroDescargas());
            libro.setId(datosLibro.id());
            Idioma idioma;
            if (datosLibro.idioma() != null) {
                for (String idiomaAPI : datosLibro.idioma()) {
                    idioma = Idioma.fromString(idiomaAPI);
                    libro.setIdioma(idioma);
                }
            } else {
                libro.setIdioma(Idioma.OTRO);
            }
            if (datosLibro.autor() != null) {
                DatosAutor datosAutor = datosLibro.autor().stream().findFirst().orElse(null);
                Autor existente = repositorioAutor.findByNombreAndFechaDeNacimientoAndFechaDeFallecimiento(
                        datosAutor.nombre(),
                        datosAutor.fechaDeNacimiento(),
                        datosAutor.fechaDeFallecimiento());
                if (existente == null) {
                    Autor autor = new Autor();
                    autor.setNombre(datosAutor.nombre());
                    autor.setFechaDeNacimiento(datosAutor.fechaDeNacimiento());
                    autor.setFechaDeFallecimiento(datosAutor.fechaDeFallecimiento());
                    repositorioAutor.save(autor);
                    libro.setAutor(autor);
                } else {
                    libro.setAutor(existente);
                }
            }
            repositorio.save(libro);
        }
        return libro;
    }

    private DatosLibro getDatosLibro() {
        System.out.println("Escriba el nombre del libro que desea buscar:");
        var nombreLibro = teclado.nextLine();
        var json = consumoAPI.obtenerDatos(URL_BASE + "?search=" + nombreLibro.replace(" ", "+"));
        DatosResultados datosBusqueda = conversor.obtenerDatos(json, DatosResultados.class);
        System.out.println(datosBusqueda.resultados());
        return datosBusqueda.resultados().stream().findFirst().orElse(null);
    }

    private void buscarLibro() {
        DatosLibro datosLibro = getDatosLibro();
        Libro libro = convertirDatosLibroAEntidad(datosLibro);
        System.out.println(libro);
    }

    private void mostrarLibrosBuscados() {
        libro = repositorio.findAll();
        libro.stream()
                .forEach(System.out::println);
    }

    private void mostrarAutoresBuscados() {
        autor = repositorioAutor.findAll();
        autor.stream().forEach(System.out::println);
    }

    private void mostrarAutoresVivos() {
        System.out.println("Escriba el año que desea buscar:");
        try {
            var fechaBuscada = teclado.nextInt();
            teclado.nextLine();
            List<Autor> autoresVivos = repositorioAutor.autoresVivos(fechaBuscada);
            if (autoresVivos.isEmpty()) {
                System.out.println("No hay autores vivos registrados ese año.");
            } else {
                System.out.println("Los autores vivos son:");
                autoresVivos.forEach(System.out::println);
            }
        }catch (InputMismatchException e) {
                System.out.println("Entrada no válida, intente nuevamente.");
                teclado.nextLine();
            }

    }

    private void mostrarLibrosPorIdioma() {
        System.out.println("""
                  Ingrese el idioma para buscar libros:
                  es - Español
                  en - Inglés
                  fr - Francés
                  pt - Portugués
                """);
        try {
            var idiomaUsuario = teclado.nextLine();
            var lenguaje = Idioma.fromString(idiomaUsuario);
            List<Libro> librosPorIdioma = repositorio.findByIdioma(lenguaje);
            if(librosPorIdioma.isEmpty()){
                System.out.println("No hay libros registrados en ese idioma.");
            }else {
                System.out.println("Los libros en el idioma elegido son los siguientes:");
                librosPorIdioma.forEach(System.out::println);
            }
        } catch (IllegalArgumentException e) {
            System.out.println("No encontrado, intente nuevamente.");
            mostrarLibrosPorIdioma();
        }
    }
}









