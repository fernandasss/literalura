package com.aluracursos.literalura.principal;

import com.aluracursos.literalura.model.DatosLibro;
import com.aluracursos.literalura.model.DatosResultados;
import com.aluracursos.literalura.model.Libro;
import com.aluracursos.literalura.repository.Repositorio;
import com.aluracursos.literalura.service.ConsumoAPI;
import com.aluracursos.literalura.service.ConvierteDatos;

import java.util.Optional;
import java.util.Scanner;

public class Principal {
    private static final String URL_BASE = "https://gutendex.com/books/";
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    private Repositorio repositorio;
    Scanner teclado = new Scanner(System.in);

    public Principal(Repositorio repositorio) {
        this.repositorio = repositorio;
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
                    5 - Exhibir cantidad de libros en un determinado idioma.


                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibro();
                    break;
////                case 2:
////                    mostrarLibrosBuscados();
////                    break;
////                case 3:
////                    mostrarAutoresBuscados();
////                    break;
////                case 4:
////                    mostrarAutoresVivos();
////                    break;
////                case 5:
////                    mostrarLibrosPorIdioma();
////                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }
    }
    private DatosResultados getResultados() {
        System.out.println("Escriba el nombre del libro que desea buscar:");
        var nombreLibro = teclado.nextLine();
        var json = consumoAPI.obtenerDatos(URL_BASE + "?search=" + nombreLibro.replace(" ", "+"));
        DatosResultados datosBusqueda = conversor.obtenerDatos(json, DatosResultados.class);
        System.out.println(datosBusqueda.resultados());
        return datosBusqueda;
//        Optional<DatosLibro> libroBuscado = datosBusqueda.resultados().stream()
//                .filter(l -> l.titulo().toUpperCase().contains(nombreLibro.toUpperCase()))
//                .findFirst();
//        if (libroBuscado.isPresent()) {
//            System.out.println("Libro encontrado.");
//            System.out.println(libroBuscado.get());
//        } else {
//            System.out.println("Libro no encontrado.");
//        }
    }
        private void buscarLibro() {
            DatosResultados datos = getResultados();
            Libro libro = new Libro();
            repositorio.save(libro);
            System.out.println(datos);
        }


        }

