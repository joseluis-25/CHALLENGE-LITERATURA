package repository;

import model.Autor;
import java.sql.*;
import java.util.*;

public class AutorRepository {

    private static Connection connection;

    static {
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/alura_literatura", "postgres", "CHATPOSTGRES2525");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Autor> listarAutores() {
        List<Autor> autores = new ArrayList<>();
        try {
            String query = "SELECT DISTINCT autor, fecha_nacimiento, fecha_fallecimiento FROM libros";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                String nombre = rs.getString("autor");

                // Obtener fechas como Integer
                Integer fechaNacimientoInt = rs.getInt("fecha_nacimiento");
                Integer fechaFallecimientoInt = rs.getInt("fecha_fallecimiento");

                // Si el valor es 0, lo dejamos como null
                if (fechaNacimientoInt == 0) fechaNacimientoInt = null;
                if (fechaFallecimientoInt == 0) fechaFallecimientoInt = null;

                // Crear el objeto Autor con las fechas como Integer
                autores.add(new Autor(nombre, fechaNacimientoInt, fechaFallecimientoInt));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return autores;
    }

    // Método para listar autores vivos en un año específico
    public List<Autor> listarAutoresVivos(int ano) {
        List<Autor> autoresVivos = new ArrayList<>();
        try {
            // Usamos la función EXTRACT para comparar el año, pero ahora las fechas están en formato Integer
            String query = "SELECT autor, fecha_nacimiento, fecha_fallecimiento " +
                    "FROM libros WHERE (fecha_fallecimiento IS NULL OR fecha_fallecimiento >= ?) " +
                    "AND fecha_nacimiento <= ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, ano);  // Comparar fecha_fallecimiento con el año
            stmt.setInt(2, ano);  // Comparar fecha_nacimiento con el año

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String nombre = rs.getString("autor");

                // Obtener fechas como Integer
                Integer fechaNacimientoInt = rs.getInt("fecha_nacimiento");
                Integer fechaFallecimientoInt = rs.getInt("fecha_fallecimiento");

                // Si el valor es 0, lo dejamos como null
                if (fechaNacimientoInt == 0) fechaNacimientoInt = null;
                if (fechaFallecimientoInt == 0) fechaFallecimientoInt = null;

                // Crear el objeto Autor con las fechas como Integer
                autoresVivos.add(new Autor(nombre, fechaNacimientoInt, fechaFallecimientoInt));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return autoresVivos;
    }

    public List<Autor> listarAutoresPorIdioma(String idioma) {
        List<Autor> autores = new ArrayList<>();
        try {
            String query = "SELECT autor, fecha_nacimiento, fecha_fallecimiento FROM libros WHERE idioma = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, idioma);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String nombre = rs.getString("autor");

                // Obtener fechas como Integer
                Integer fechaNacimientoInt = rs.getInt("fecha_nacimiento");
                Integer fechaFallecimientoInt = rs.getInt("fecha_fallecimiento");

                // Si el valor es 0, lo dejamos como null
                if (fechaNacimientoInt == 0) fechaNacimientoInt = null;
                if (fechaFallecimientoInt == 0) fechaFallecimientoInt = null;

                // Crear el objeto Autor con las fechas como Integer
                autores.add(new Autor(nombre, fechaNacimientoInt, fechaFallecimientoInt));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return autores;
    }
}
