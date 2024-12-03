package service;

import model.Libro;
import model.Autor;
import org.json.*;

public class DatosConverter {

    public static Libro convertirAFormatoLibro(JSONObject json) {
        // Obtener el título del libro
        String titulo = json.getString("title");

        // Obtener los autores (el primer autor)
        JSONArray authors = json.getJSONArray("authors");
        String autorNombre = authors.getJSONObject(0).getString("name");

        // Obtener el idioma del libro
        String idioma = json.getString("language");

        // Obtener el número de descargas
        int numDescargas = json.getInt("download_count");

        // Obtener la fecha de nacimiento y de fallecimiento del autor.
        // Asignar valores por defecto (null) si no están disponibles.
        Integer fechaNacimiento = null; // Si la API no devuelve estas fechas, asignar null o un valor predeterminado
        Integer fechaFallecimiento = null; // Lo mismo para la fecha de fallecimiento

        // Crear el autor con los datos obtenidos
        Autor autor = new Autor(autorNombre, fechaNacimiento, fechaFallecimiento);

        // Crear y devolver el libro con el autor y los datos obtenidos
        return new Libro(titulo, autor, idioma, numDescargas);
    }
}








