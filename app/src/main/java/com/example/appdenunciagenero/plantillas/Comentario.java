package com.example.appdenunciagenero.plantillas;

public class Comentario {

    private String noControl, pseudonimo, comentario, fecha;

    public Comentario(String noControl, String pseudonimo, String comentario, String fecha) {
        this.noControl = noControl;
        this.pseudonimo = pseudonimo;
        this.comentario = comentario;
        this.fecha = fecha;
    }

    public String getNoControl() {
        return noControl;
    }

    public String getPseudonimo() {
        return pseudonimo;
    }

    public String getComentario() {
        return comentario;
    }

    public String getFecha() {
        return fecha;
    }

    public void setNoControl(String noControl) {
        this.noControl = noControl;
    }

    public void setPseudonimo(String pseudonimo) {
        this.pseudonimo = pseudonimo;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
