package com.example.appdenunciagenero.plantillas;

public class Publicacion {

    private String noControl, autor, comentario, fecha;
    int contadorApoyos, contadorComentarios;

    public Publicacion(String noControl, String autor, String comentario, String fecha, int contadorApoyos, int contadorComentarios) {
        this.noControl = noControl;
        this.autor = autor;
        this.comentario = comentario;
        this.fecha = fecha;
        this.contadorApoyos = contadorApoyos;
        this.contadorComentarios = contadorComentarios;
    }

    public String getNoControl() {
        return noControl;
    }

    public void setNoControl(String noControl) {
        this.noControl = noControl;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getContadorApoyos() {
        return contadorApoyos;
    }

    public void setContadorApoyos(int contadorApoyos) {
        this.contadorApoyos = contadorApoyos;
    }


    public int getContadorComentarios() {
        return contadorComentarios;
    }

    public void setContadorComentarios(int contadorComentarios) {
        this.contadorComentarios = contadorComentarios;
    }
}
