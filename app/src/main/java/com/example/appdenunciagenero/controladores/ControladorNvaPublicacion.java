package com.example.appdenunciagenero.controladores;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.appdenunciagenero.R;
import com.example.appdenunciagenero.modelo.Modelo;
import com.example.appdenunciagenero.plantillas.Publicacion;

public class ControladorNvaPublicacion extends AppCompatActivity implements View.OnClickListener {

    private ImageButton btnCancelar;
    private ImageButton btnPublicar;
    private EditText txtComentario;
    private Modelo modelo;
    private String noControl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_nva_publicacion);

        modelo = (Modelo) getIntent().getSerializableExtra("modelo");
        noControl = (String) getIntent().getExtras().get("noControl");

        txtComentario = (EditText) findViewById(R.id.txtComentario);

        btnCancelar = (ImageButton) findViewById(R.id.btnCancelar);
        btnPublicar = (ImageButton) findViewById(R.id.btnPublicar);
        btnCancelar.setOnClickListener(this);
        btnPublicar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btnCancelar) {
            finish();
            return;
        }
        if ((txtComentario.getText() + "").compareTo("") == 0) {
            Toast.makeText(this.getApplicationContext(), "No se puede hacer una publicación vacía", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!modelo.registrarPublicacion(new Publicacion(noControl, null, txtComentario.getText() + "", null, 0, 0))) {
            Toast.makeText(this.getApplicationContext(), "Hubo un error", Toast.LENGTH_SHORT).show();
            finish();
        }
        Toast.makeText(this.getApplicationContext(), "Comentario publicado con éxito", Toast.LENGTH_LONG).show();
        finish();
    }
}
