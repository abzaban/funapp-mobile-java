package com.example.appdenunciagenero.controladores;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.appdenunciagenero.R;
import com.example.appdenunciagenero.modelo.Modelo;
import com.example.appdenunciagenero.plantillas.Publicacion;
import java.util.ArrayList;

public class ControladorHub extends AppCompatActivity implements View.OnClickListener {

    private ImageButton btnIrPublicar;
    private ImageButton btnPerfil;
    private ImageButton btnRecargar;
    private ImageButton btnSalir;
    private ArrayList<Publicacion> arrayPublicaciones;
    private MyListAdapterPublicaciones mlap;
    private ListView listaPublicaciones;
    private ArrayList<ImageButton> arrayBotonesApoyos;
    private ArrayList<ImageButton> arrayBotonesComentarios;
    private Modelo modelo;
    private String noControl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_hub);

        noControl = (String) getIntent().getExtras().get("noControl");
        modelo = (Modelo) getIntent().getSerializableExtra("modelo");

        arrayPublicaciones = modelo.obtenerPublicaciones();
        mlap = new MyListAdapterPublicaciones(this);
        arrayBotonesApoyos = mlap.getBotonesApoyos();
        arrayBotonesComentarios = mlap.getBotonesComentarios();
        listaPublicaciones = (ListView) findViewById(R.id.listaPublicaciones);
        listaPublicaciones.setAdapter(mlap);

        btnIrPublicar = (ImageButton) findViewById(R.id.btnIrPublicar);
        btnPerfil = (ImageButton) findViewById(R.id.btnPerfil);
        btnRecargar = (ImageButton) findViewById(R.id.btnRecargar);
        btnSalir = (ImageButton) findViewById(R.id.btnSalir);
        btnIrPublicar.setOnClickListener(this);
        btnPerfil.setOnClickListener(this);
        btnRecargar.setOnClickListener(this);
        btnSalir.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        arrayPublicaciones = modelo.obtenerPublicaciones();
        mlap = new MyListAdapterPublicaciones(this);
        arrayBotonesApoyos = mlap.getBotonesApoyos();
        arrayBotonesComentarios = mlap.getBotonesComentarios();
        listaPublicaciones.setAdapter(mlap);
    }

    @Override
    public void onClick(View v) {
        if (v == btnIrPublicar) {
            Intent i = new Intent(this, ControladorNvaPublicacion.class);
            i.putExtra("modelo", modelo);
            i.putExtra("noControl", noControl);
            startActivity(i);
            return;
        }
        if (v == btnPerfil) {
            Intent i = new Intent(this, ControladorPerfil.class);
            i.putExtra("modelo", modelo);
            i.putExtra("noControl", noControl);
            startActivity(i);
            return;
        }
        if (v == btnRecargar) {
            onResume();
            return;
        }
        if (v == btnSalir) {
            finish();
            return;
        }
        if (arrayBotonesComentarios.contains(v)) {
            arrayBotonesComentarios = mlap.getBotonesComentarios();
            System.out.println(arrayBotonesComentarios.size());
            int pos = -1;
            for (int i = 0 ; i < arrayPublicaciones.size() ; i++)
                if (v == arrayBotonesComentarios.get(i)) {
                    pos = i;
                    break;
                }
            if (pos == -1) {
                Toast.makeText(this.getApplicationContext(), "Hubo un error", Toast.LENGTH_SHORT).show();
                return;
            }
            Intent i = new Intent(this, ControladorComentarios.class);
            i.putExtra("modelo", modelo);
            i.putExtra("noControl", arrayPublicaciones.get(pos).getNoControl());
            i.putExtra("autor", arrayPublicaciones.get(pos).getAutor());
            i.putExtra("publicacion", arrayPublicaciones.get(pos).getComentario());
            i.putExtra("fecha", arrayPublicaciones.get(pos).getFecha());
            i.putExtra("noControlAutorComentario", noControl);
            startActivity(i);
            return;
        }
        if (arrayBotonesApoyos.contains(v)) {
            for (int i = 0 ; i < arrayBotonesApoyos.size() ; i++)
                arrayBotonesApoyos.get(i).setId(i);
            return;
        }
    }

    class MyListAdapterPublicaciones extends ArrayAdapter<Publicacion> {

        private ControladorHub ch;
        private ArrayList<ImageButton> botonesApoyos;
        private ArrayList<ImageButton> botonesComentarios;

        public MyListAdapterPublicaciones(ControladorHub ch) {
            super(ControladorHub.this, R.layout.item_publicaciones, arrayPublicaciones);
            this.ch = ch;
            botonesApoyos = new ArrayList<ImageButton>();
            botonesComentarios = new ArrayList<ImageButton>();
        }

        public View getView(int Position, View convertView, ViewGroup parent) {
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.item_publicaciones, parent, false);
            }
            Publicacion currentPublicacion = arrayPublicaciones.get(Position);
            TextView autor = (TextView) itemView.findViewById(R.id.lblAutor);
            autor.setText(currentPublicacion.getAutor());
            TextView comentario = (TextView) itemView.findViewById(R.id.lblComentario);
            comentario.setText(currentPublicacion.getComentario());
            botonesApoyos.add((ImageButton) itemView.findViewById(R.id.btnApoyo));
            botonesApoyos.get(Position).setOnClickListener(ch);
            TextView lblContadorApoyos = (TextView) itemView.findViewById(R.id.lblContadorApoyos);
            lblContadorApoyos.setText(currentPublicacion.getContadorApoyos() + "");
            botonesComentarios.add((ImageButton) itemView.findViewById(R.id.btnComentar));
            botonesComentarios.get(Position).setOnClickListener(ch);
            TextView lblContadorComentarios = (TextView) itemView.findViewById(R.id.lblContadorComentarios);
            lblContadorComentarios.setText(currentPublicacion.getContadorComentarios() + "");
            TextView lblFecha = (TextView) itemView.findViewById(R.id.lblFecha);
            lblFecha.setText(currentPublicacion.getFecha());
            return itemView;
        }

        public ArrayList<ImageButton> getBotonesApoyos() {
            return botonesApoyos;
        }

        public ArrayList<ImageButton> getBotonesComentarios() {
            return botonesComentarios;
        }
    }
}


