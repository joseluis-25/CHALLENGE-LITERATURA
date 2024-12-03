package model;

public class Autor {
    private String nombre;
    private Integer fechaNacimiento;
    private Integer fechaFallecimiento;

    // Constructor
    public Autor(String nombre, Integer fechaNacimiento, Integer fechaFallecimiento) {
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.fechaFallecimiento = fechaFallecimiento;
    }

    // Getters
    public String getNombre() {
        return nombre;
    }

    public Integer getFechaNacimiento() {
        return fechaNacimiento;
    }

    public Integer getFechaFallecimiento() {
        return fechaFallecimiento;
    }

    // Métodos para convertir las fechas a String
    public String getFechaNacimientoString() {
        return (fechaNacimiento != null) ? fechaNacimiento.toString() : "No disponible";
    }

    public String getFechaFallecimientoString() {
        return (fechaFallecimiento != null) ? fechaFallecimiento.toString() : "No disponible";
    }
}




















