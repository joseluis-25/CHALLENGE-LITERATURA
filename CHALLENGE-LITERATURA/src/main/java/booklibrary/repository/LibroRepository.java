package repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Libro;
import model.Autor;

public class LibroRepository {

    private Connection connection;

    public LibroRepository() {
        try {
            this.connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/alura_literatura", "postgres", "CHATPOSTGRES2525");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void guardar(Libro libro) {
        try {
            String query = "INSERT INTO libros (titulo, autor, fecha_nacimiento, fecha_fallecimiento, idioma, numero_descargas) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, libro.getTitulo());
            stmt.setString(2, libro.getAutor().getNombre());
            stmt.setInt(3, libro.getAutor().getFechaNacimiento());
            stmt.setInt(4, libro.getAutor().getFechaFallecimiento());
            stmt.setString(5, libro.getIdioma());
            stmt.setInt(6, libro.getNumDescargas());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Libro> listarLibros() {
        List<Libro> libros = new ArrayList<>();
        try {
            String query = "SELECT * FROM libros";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                String titulo = rs.getString("titulo");
                String autorNombre = rs.getString("autor");
                Integer autorFechaNacimiento = rs.getInt("fecha_nacimiento");
                Integer autorFechaFallecimiento = rs.getInt("fecha_fallecimiento");
                String idioma = rs.getString("idioma");
                int numDescargas = rs.getInt("numero_descargas");

                Autor autor = new Autor(autorNombre, autorFechaNacimiento, autorFechaFallecimiento);
                Libro libro = new Libro(titulo, autor, idioma, numDescargas);
                libros.add(libro);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return libros;
    }

    public Libro buscarPorTitulo(String titulo) {
        try {
            String query = "SELECT * FROM libros WHERE titulo = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, titulo);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String libroTitulo = rs.getString("titulo");
                String autorNombre = rs.getString("autor");
                Integer autorFechaNacimiento = rs.getInt("fecha_nacimiento");
                Integer autorFechaFallecimiento = rs.getInt("fecha_fallecimiento");
                String idioma = rs.getString("idioma");
                int numDescargas = rs.getInt("numero_descargas");

                Autor autor = new Autor(autorNombre, autorFechaNacimiento, autorFechaFallecimiento);
                return new Libro(libroTitulo, autor, idioma, numDescargas);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Libro> listarLibrosPorIdioma(String idioma) {
        List<Libro> librosPorIdioma = new ArrayList<>();
        try {
            String query = "SELECT * FROM libros WHERE idioma = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, idioma);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String titulo = rs.getString("titulo");
                String autorNombre = rs.getString("autor");
                Integer autorFechaNacimiento = rs.getInt("fecha_nacimiento");
                Integer autorFechaFallecimiento = rs.getInt("fecha_fallecimiento");
                String idiomaLibro = rs.getString("idioma");
                int numDescargas = rs.getInt("numero_descargas");

                Autor autor = new Autor(autorNombre, autorFechaNacimiento, autorFechaFallecimiento);
                Libro libro = new Libro(titulo, autor, idiomaLibro, numDescargas);
                librosPorIdioma.add(libro);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return librosPorIdioma;
    }
}
















