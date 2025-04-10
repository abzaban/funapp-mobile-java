package com.example.appdenunciagenero.persistencia;

import android.os.StrictMode;
import com.example.appdenunciagenero.plantillas.Comentario;
import com.example.appdenunciagenero.plantillas.Publicacion;
import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class BD implements Serializable {

    private static Connection conexion;
    private static Statement consulta;

    public BD() {
        conexion = null;
    }

    public boolean iniciarConexion() {
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            conexion = DriverManager.getConnection("jdbc:jtds:sqlserver://192.168.68.108;databaseName=DENUNCIAGENERO;user=sa;password=Uchiha99;");
            consulta = conexion.createStatement();
            return true;
        } catch(Exception e) {
            return false;
        }
    }

    public boolean procInsertarPublicacion(Publicacion publicacion) {
        try {
            consulta.execute("exec PA_InsertarPublicacion '" + publicacion.getNoControl() + "', '" + publicacion.getComentario() + "'");
            return true;
        } catch(SQLException sqle) {
            return false;
        }
    }

    public boolean procValidarUsuario(String noControl, String contrasenia) {
        try {
            CallableStatement cs = conexion.prepareCall("{call PA_ValidarUsuario (?,?)}");
            cs.setString(1, noControl);
            cs.setString(2, contrasenia);
            ResultSet rs = cs.executeQuery();
            rs.next();
            String resultado = rs.getString(1);
            System.out.println(resultado);
            return resultado == null ? false : true;
        } catch(SQLException sqle) {
            return false;
        }
    }

    public ArrayList<Publicacion> selectPublicacionesRecientes() {
        ArrayList<Publicacion> publicaciones;
        try {
            publicaciones = new ArrayList<Publicacion>();
            ResultSet rs = consulta.executeQuery("select pub_fecha, usu_nombre, pub_comentario, pub_contadorApoyos, pub_contadorComentarios, P.usu_noControl from PUBLICACIONES P inner join USUARIOS U\n" +
                    "    on P.usu_noControl = U.usu_noControl\n" +
                    "    order by pub_fecha desc");
            while(rs.next())
                publicaciones.add(new Publicacion(rs.getString(6), rs.getString(2), rs.getString(3), rs.getString(1), rs.getInt(4), rs.getInt(5)));
            return publicaciones;
        } catch(SQLException sqle) {
            return null;
        }
    }

    public String selectPseudonimo(String noControl) {
        try {
            ResultSet rs = consulta.executeQuery("select usu_nombre from USUARIOS where usu_noControl = '" + noControl + "'");
            rs.next();
            return rs.getString(1);
        } catch(SQLException sqle) {
            return null;
        }
    }

    public boolean actualizarPseudonimo(String noControl, String pseudonimo) {
        try {
            consulta.execute("update USUARIOS set usu_nombre = '" + pseudonimo + "' where usu_noControl = '" + noControl + "'");
            return true;
        } catch(SQLException sqle) {
            return false;
        }
    }

    public ArrayList<Comentario> selectComentariosEspecificosRecientes(String noControl, String fecha) {
        ArrayList<Comentario> comentarios;
        try {
            comentarios = new ArrayList<Comentario>();
            ResultSet rs = consulta.executeQuery("select usu_nombre, com_fechaPublicacionComentario, com_comentarioRetro from COMENTARIOS C inner join USUARIOS U \n" +
                    "on C.com_noControlAutorComentario = U.usu_noControl \n" +
                    "where C.pub_noControlAutorPrincipal = '" + noControl + "' and C.pub_fechaPublicacionPrincipal = '" + fecha + "'\n" +
                    "order by com_fechaPublicacionComentario desc");
            while(rs.next()) {
                comentarios.add(new Comentario(null, rs.getString(1), rs.getString(3), rs.getString(2)));
            }
            return comentarios;
        } catch(SQLException sqle) {
            return null;
        }
    }

    public boolean procInsertarComentario(Publicacion publicacion, Comentario comentario) {
        try {
            consulta.execute("exec PA_InsertarComentario '" + publicacion.getNoControl() + "', '" + publicacion.getFecha() + "', '" + comentario.getNoControl() + "', '" + comentario.getComentario() + "'");
            return true;
        } catch(SQLException sqle) {
            return false;
        }
    }
}
