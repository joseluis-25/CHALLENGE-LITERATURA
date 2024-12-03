package model;

public class Libro {
    private String titulo;
    private Autor autor;
    private String idioma;
    private int numDescargas;

    // Constructor
    public Libro(String titulo, Autor autor, String idioma, int numDescargas) {
        this.titulo = titulo;
        this.autor = autor;
        this.idioma = idioma;
        this.numDescargas = numDescargas;
    }

    // Getter para el título
    public String getTitulo() {
        return titulo;
    }

    // Getter para el autor
    public Autor getAutor() {
        return autor;
    }

    // Getter para el idioma
    public String getIdioma() {
        return idioma;
    }

    // Getter para el número de descargas
    public int getNumDescargas() {
        return numDescargas;
    }

    // Setter para el título
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    // Setter para el autor
    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    // Setter para el idioma
    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    // Setter para el número de descargas
    public void setNumDescargas(int numDescargas) {
        this.numDescargas = numDescargas;
    }


}













