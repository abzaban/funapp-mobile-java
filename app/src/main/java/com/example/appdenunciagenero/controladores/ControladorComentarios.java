package com.example.appdenunciagenero.controladores;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.appdenunciagenero.R;
import com.example.appdenunciagenero.modelo.Modelo;
import com.example.appdenunciagenero.plantillas.Comentario;
import com.example.appdenunciagenero.plantillas.Publicacion;
import java.util.ArrayList;

public class ControladorComentarios extends AppCompatActivity implements View.OnClickListener {

    private Modelo modelo;
    private ArrayList<Comentario> arrayComentarios;
    private ImageButton btnCancelarComentario;
    private ImageButton btnPublicarComentario;
    private ImageButton btnActualizaComentarios;
    private TextView lblAutorPublicacion;
    private TextView lblPublicacionComentario;
    private EditText txtComentarioEspecifico;
    private ListView listaComentarios;
    private String noControlAutorPublicacion;
    private String fechaPublicacion;
    private MyListAdapterComentarios mylac;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_comentarios);

        modelo = (Modelo) getIntent().getSerializableExtra("modelo");

        noControlAutorPublicacion = getIntent().getExtras().get("noControl") + "";
        fechaPublicacion = getIntent().getExtras().get("fecha") + "";
        arrayComentarios = modelo.obtenerComentarios(noControlAutorPublicacion, fechaPublicacion);
        listaComentarios = (ListView) findViewById(R.id.listaComentarios);
        mylac = new MyListAdapterComentarios();
        listaComentarios.setAdapter(mylac);

        lblAutorPublicacion = (TextView) findViewById(R.id.lblAutorPublicacion);
        lblAutorPublicacion.setText(getIntent().getExtras().get("autor") + "");
        lblPublicacionComentario = (TextView) findViewById(R.id.lblPublicacionComentario);
        lblPublicacionComentario.setText(getIntent().getExtras().get("publicacion") + "");

        txtComentarioEspecifico = findViewById(R.id.txtComentarioEspecifico);

        btnCancelarComentario = (ImageButton) findViewById(R.id.btnCancelarComentario);
        btnPublicarComentario = (ImageButton) findViewById(R.id.btnPublicarComentario);
        btnActualizaComentarios = (ImageButton) findViewById(R.id.btnActualizarComentarios);
        btnCancelarComentario.setOnClickListener(this);
        btnPublicarComentario.setOnClickListener(this);
        btnActualizaComentarios.setOnClickListener(this);
    }

    public void actualizarFeed() {
        arrayComentarios = modelo.obtenerComentarios(noControlAutorPublicacion, fechaPublicacion);
        mylac = new MyListAdapterComentarios();
        listaComentarios.setAdapter(mylac);
    }

    @Override
    public void onClick(View v) {
        if (v == btnPublicarComentario) {
            if((txtComentarioEspecifico.getText() + "").compareTo("") == 0) {
                Toast.makeText(this.getApplicationContext(), "No se pueden hacer comentarios vacíos", Toast.LENGTH_SHORT).show();
                return;
            }
            if(!modelo.registrarComentario(
                    new Publicacion
                            (noControlAutorPublicacion, null, null, fechaPublicacion, 0, 0),
                    new Comentario
                            (getIntent().getExtras().get("noControlAutorComentario") + "", null, txtComentarioEspecifico.getText() + "", null))) {
                Toast.makeText(this.getApplicationContext(), "Hubo un error", Toast.LENGTH_SHORT).show();
                return;
            }
            Toast.makeText(this.getApplicationContext(), "Comentario publicado con éxito", Toast.LENGTH_SHORT).show();
            txtComentarioEspecifico.setText("");
            actualizarFeed();
            return;
        }
        if (v == btnCancelarComentario) {
            finish();
            return;
        }
        if (v == btnActualizaComentarios) {
            actualizarFeed();
            return;
        }
    }

    class MyListAdapterComentarios extends ArrayAdapter<Comentario> {

        public MyListAdapterComentarios() {
            super(ControladorComentarios.this, R.layout.item_comentarios, arrayComentarios);
        }

        public View getView(int Position, View convertView, ViewGroup parent) {
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.item_comentarios, parent, false);
            }
            Comentario currentComentario = arrayComentarios.get(Position);
            TextView lblAutorComentario = (TextView) itemView.findViewById(R.id.lblAutorComentario);
            lblAutorComentario.setText(currentComentario.getPseudonimo());
            TextView lblComentarioEspecifico = (TextView) itemView.findViewById(R.id.lblComentarioEspecifico);
            lblComentarioEspecifico.setText(currentComentario.getComentario());
            TextView lblFechaComentario = (TextView) itemView.findViewById(R.id.lblFechaComentario);
            lblFechaComentario.setText(currentComentario.getFecha());
            return itemView;
        }
    }
}
