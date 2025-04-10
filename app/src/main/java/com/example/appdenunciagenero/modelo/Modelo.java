package com.example.appdenunciagenero.modelo;

import com.example.appdenunciagenero.persistencia.BD;
import com.example.appdenunciagenero.plantillas.Comentario;
import com.example.appdenunciagenero.plantillas.Publicacion;
import java.io.Serializable;
import java.util.ArrayList;

public class Modelo implements Serializable {

    private BD baseDatos;

    public Modelo() {
        baseDatos = new BD();
    }

    public boolean conectarse() {
        return baseDatos.iniciarConexion();
    }

    public boolean registrarPublicacion(Publicacion publicacion) {
        return baseDatos.procInsertarPublicacion(publicacion);
    }

    public boolean validarCredenciales(String noControl, String contrasenia) {
        return baseDatos.procValidarUsuario(noControl, contrasenia);
    }

    public ArrayList<Publicacion> obtenerPublicaciones() {
        return baseDatos.selectPublicacionesRecientes();
    }

    public String obtenerNombre(String noControl) {
        return baseDatos.selectPseudonimo(noControl);
    }

    public boolean actualizarNombre(String noControl, String pseudonimo) {
        return baseDatos.actualizarPseudonimo(noControl, pseudonimo);
    }

    public ArrayList<Comentario> obtenerComentarios(String noControl, String fecha) {
        return baseDatos.selectComentariosEspecificosRecientes(noControl, fecha);
    }

    public boolean registrarComentario(Publicacion publicacion, Comentario comentario) {
        return baseDatos.procInsertarComentario(publicacion, comentario);
    }
}
