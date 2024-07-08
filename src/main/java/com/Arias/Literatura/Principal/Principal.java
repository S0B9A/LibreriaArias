package com.Arias.Literatura.Principal;

import com.Arias.Literatura.Modelo.Autor;
import com.Arias.Literatura.Modelo.DatosLibreria;
import com.Arias.Literatura.Modelo.DatosLibro;
import com.Arias.Literatura.Modelo.Libro;
import com.Arias.Literatura.Repositorio.AutorRepository;
import com.Arias.Literatura.Repositorio.LibroRepository;
import com.Arias.Literatura.Services.ConsumoAPI;
import com.Arias.Literatura.Services.ConvierteDatos;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {

    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private final String URL_BASE = "http://gutendex.com/books/?search=";
    private ConvierteDatos conversor = new ConvierteDatos();
    private AutorRepository autorRepository;
    private LibroRepository libroRepository;

    public Principal(AutorRepository autorRepository, LibroRepository libroRepository) {
        this.autorRepository = autorRepository;
        this.libroRepository = libroRepository;
    }

    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    1 - Buscar libro por titulo
                    2 - Buscar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos en un determinado año
                    5 - Listar de libros por idioma
                                        
                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibro();
                    break;
                case 2:
                    buscarLibroPorTituloRegistrado();
                    break;
                case 3:
                    listarAutoresRegistrados();
                    break;
                case 4:
                    listarAutoresVivosEnDeterminadoAnno();
                    break;
                case 5:
                    listarLibrosPorIdioma();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }
    }

    public void buscarLibro() {
        try {
            System.out.println("Escribe el nombre del libro que deseas buscar");
            var nombreLibro = teclado.nextLine();
            var json = consumoApi.obtenerDatos(URL_BASE + nombreLibro.replace(" ", "%20"));

            DatosLibreria datosLibreria = conversor.obtenerDatos(json, DatosLibreria.class);
            DatosLibro datosLibro = datosLibreria.libros().get(0);

            Libro libroBuscado = new Libro(datosLibro);
            Autor autorDelLibroBuscado = new Autor(datosLibro.autor().get(0));
            libroBuscado.setAutor(autorDelLibroBuscado);

            Guardarlibro(autorDelLibroBuscado, libroBuscado);
            monstrarLibro(libroBuscado);
        } catch (Exception ex) {
            System.out.println("No se encontro el libro mediante la busqueda");
        }
    }

    public void Guardarlibro(Autor autor, Libro libro) {
        autorRepository.save(autor);
        libroRepository.save(libro);
    }

    public void monstrarLibro(Libro libro) {
        System.out.println(libro.toString());
    }

    private void buscarLibroPorTituloRegistrado() {
        System.out.println("Escribe el nombre del titulo que deseas buscar");
        var tituloDelLibro = teclado.nextLine();
        Optional<Libro> libroRegistrado = libroRepository.findByTituloContainsIgnoreCase(tituloDelLibro);

        if (libroRegistrado.isPresent()) {
            System.out.println("La serie buscada es: " + libroRegistrado.get());
        } else {
            System.out.println("Serie no encontrada");
        }
    }

    public void listarAutoresRegistrados() {
        List<Autor> autors = autorRepository.findAll();
        autors.forEach(a -> System.out.println(a.toString()));
    }

    public void listarAutoresVivosEnDeterminadoAnno() {
        System.out.println("Escribe el año que deseas saber que autores estaban con vida");
        long anno = teclado.nextLong();
        List<Autor> autoresConVida = autorRepository.ListarAutoresVivosDuranteUnAnno(anno);
        autoresConVida.forEach(a -> System.out.println(a.toString()));
    }

    public void listarLibrosPorIdioma() {

        System.out.println("Puedes buscar libros por idioma\n" +
                "es - Español\n" +
                "en - Ingles\n" +
                "fr - Frances\n" +
                "pt - Portugues\n");

        System.out.println("Escribe el idioma (asignatura) que deseas buscar");
        var idiomaDelLibro = teclado.nextLine();

        List<Libro> libroListPorIdioma = libroRepository.findByIdioma(idiomaDelLibro);

        if (!libroListPorIdioma.isEmpty()) {
            libroListPorIdioma.forEach(l -> System.out.println(l.toString()));
        } else {
            System.out.println("No se encontro ningun libro registrado con ese idioma");
        }
    }
}