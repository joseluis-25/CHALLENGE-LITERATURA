package application;

import java.util.InputMismatchException;
import java.util.Scanner;
import model.Libro;
import model.Autor;
import repository.LibroRepository;
import repository.AutorRepository;
import service.ConsumoAPI;
import org.json.JSONArray;
import org.json.JSONObject;

public class LibreriaApplication {

    private static Scanner scanner = new Scanner(System.in);
    private static LibroRepository libroRepository = new LibroRepository();
    private static AutorRepository autorRepository = new AutorRepository();
    private static ConsumoAPI consumoAPI = new ConsumoAPI();

    public static void main(String[] args) {
        int opcion;
        do {
            mostrarMenu();
            opcion = obtenerOpcionMenu();
            if (opcion != 0) {
                procesarOpcion(opcion);  // Solo procesar la opción si no es 0
            }
        } while (opcion != 0);
    }

    private static void mostrarMenu() {
        System.out.println("Elija la opción a través de un número:");
        System.out.println("1- Buscar libro por título");
        System.out.println("2- Listar libros registrados");
        System.out.println("3- Listar autores registrados");
        System.out.println("4- Listar autores vivos en un determinado año");
        System.out.println("5- Listar libros por idioma");
        System.out.println("0- Salir");
    }

    // Método para obtener la opción del menú de forma segura
    private static int obtenerOpcionMenu() {
        int opcion = -1;
        boolean opcionValida = false;
        while (!opcionValida) {
            try {
                System.out.println("opción:");
                opcion = scanner.nextInt();
                scanner.nextLine(); // Limpiar el buffer
                if (opcion < 0 || opcion > 5) {
                    System.out.println("OPCIÓN NO VÁLIDA. Elija una opción válida.");
                } else {
                    opcionValida = true;  // Opción válida
                }
            } catch (InputMismatchException e) {
                System.out.println("OPCIÓN NO VÁLIDA. Debe ingresar un número.");
                scanner.nextLine();  // Limpiar el buffer
            }
        }
        return opcion;
    }

    private static void procesarOpcion(int opcion) {
        switch (opcion) {
            case 1:
                buscarYGrabarLibroPorTitulo();
                break;
            case 2:
                listarLibrosRegistrados();
                break;
            case 3:
                listarAutoresRegistrados();
                break;
            case 4:
                listarAutoresVivosEnAno();
                break;
            case 5:
                listarLibrosPorIdioma();
                break;
            default:
                System.out.println("Opción no válida.");
                break;
        }
    }

    private static void buscarYGrabarLibroPorTitulo() {
        System.out.print("Ingrese el título del libro: ");
        String titulo = scanner.nextLine().trim(); // Eliminar espacios

        // Consultar la API para obtener los detalles del libro
        System.out.println("Buscando en la API...");
        JSONObject libroJson = consumoAPI.obtenerDatosDesdeAPI(titulo);

        if (libroJson != null) {
            System.out.println("Datos obtenidos desde la API:");

            // Obtener título del libro
            String libroTitulo = libroJson.optString("title", "No disponible");

            // Obtener el autor (que es un arreglo de autores)
            String autorNombre = "Desconocido";
            Integer autorFechaNacimiento = null;
            Integer autorFechaFallecimiento = null;

            if (libroJson.has("authors")) {
                JSONArray autores = libroJson.getJSONArray("authors");
                if (autores.length() > 0) {
                    autorNombre = autores.getJSONObject(0).optString("name", "Desconocido");

                    // Obtener las fechas de nacimiento y fallecimiento, convertidas a Integer
                    autorFechaNacimiento = autores.getJSONObject(0).optInt("birth_year", 0);
                    autorFechaFallecimiento = autores.getJSONObject(0).optInt("death_year", 0);
                }
            }

            // Crear el objeto Autor con los parámetros obtenidos
            Autor autor = new Autor(autorNombre, autorFechaNacimiento, autorFechaFallecimiento);

            // Obtener el idioma
            String idioma = "No disponible";
            if (libroJson.has("languages")) {
                JSONArray idiomas = libroJson.getJSONArray("languages");
                if (idiomas.length() > 0) {
                    idioma = idiomas.getString(0);  // Se asume que solo hay un idioma
                }
            }

            // Obtener el número de descargas, convertido a Integer
            int descargas = libroJson.optInt("download_count", 0);

            // Verificar si el libro ya está registrado antes de guardarlo
            if (libroRepository.buscarPorTitulo(libroTitulo) != null) {
                System.out.println("LIBRO YA REGISTRADO");
                System.out.println("\n");
                return;
            }

            // Crear el objeto libro con el autor y los detalles obtenidos
            Libro libro = new Libro(libroTitulo, autor, idioma, descargas);

            // Grabar el libro en la base de datos
            libroRepository.guardar(libro);

            // Mostrar los datos obtenidos de la API y grabados en la base de datos
            System.out.println("***** LIBRO *****");
            System.out.println("Título: " + libroTitulo);
            System.out.println("Autor: " + autorNombre);
            System.out.println("Idioma: " + idioma);
            System.out.println("Número de descargas: " + (descargas > 0 ? descargas : "No disponible"));
            System.out.println("\n");
        } else {
            System.out.println("No se encontraron resultados en la API.");
        }
    }

    private static void listarLibrosRegistrados() {
        libroRepository.listarLibros().forEach(LibreriaApplication::mostrarLibro);
    }

    private static void listarAutoresRegistrados() {
        autorRepository.listarAutores().forEach(LibreriaApplication::mostrarAutor);
    }

    private static void listarAutoresVivosEnAno() {
        System.out.print("Ingrese el año que desea saber qué autores están vivos: ");
        int ano = scanner.nextInt();
        scanner.nextLine(); // Limpiar el buffer
        autorRepository.listarAutoresVivos(ano).forEach(LibreriaApplication::mostrarAutor);
    }

    private static void listarLibrosPorIdioma() {
        System.out.print("Ingrese el idioma de los libros a buscar (es/en/fr/pt): ");
        String idioma = scanner.nextLine().trim().toLowerCase();

        if (idioma.equals("es") || idioma.equals("en") || idioma.equals("fr") || idioma.equals("pt")) {
            libroRepository.listarLibrosPorIdioma(idioma).forEach(LibreriaApplication::mostrarLibro);
        } else {
            System.out.println("Idioma no válido. Los idiomas válidos son: es (español), en (inglés), fr (francés), pt (portugués).");
        }
    }

    private static void mostrarLibro(Libro libro) {
        System.out.println("***** LIBRO *****");
        System.out.println("Título: " + libro.getTitulo());
        System.out.println("Autor: " + libro.getAutor().getNombre());
        System.out.println("Idioma: " + libro.getIdioma());
        System.out.println("Número de descargas: " + (libro.getNumDescargas() > 0 ? libro.getNumDescargas() : "No disponible"));
        System.out.println("\n");
    }

    private static void mostrarAutor(Autor autor) {
        System.out.println("Autor: " + autor.getNombre());
        System.out.println("Fecha de nacimiento: " + autor.getFechaNacimientoString());  // Usar el método getFechaNacimientoString()
        System.out.println("Fecha de fallecimiento: " + autor.getFechaFallecimientoString());  // Usar el método getFechaFallecimientoString()
        System.out.println("\n");
    }
}




