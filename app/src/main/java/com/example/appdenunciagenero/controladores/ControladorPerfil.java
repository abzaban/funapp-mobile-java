package com.example.appdenunciagenero.controladores;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.appdenunciagenero.R;
import com.example.appdenunciagenero.modelo.Modelo;

public class ControladorPerfil extends AppCompatActivity implements View.OnClickListener {

    private Modelo modelo;
    private Button btnGuardar;
    private Button btnVolver;
    private EditText txtPseudonimo;
    private EditText txtNoControl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_perfil);

        modelo = (Modelo) getIntent().getSerializableExtra("modelo");

        txtPseudonimo = (EditText) findViewById(R.id.txtPseudonimo);
        txtNoControl = (EditText) findViewById(R.id.txtNoControl);

        String noControl = getIntent().getExtras().get("noControl") + "";
        txtNoControl.setText(noControl);
        txtPseudonimo.setText(modelo.obtenerNombre(noControl));

        btnGuardar = (Button) findViewById(R.id.btnGuardar);
        btnVolver = (Button) findViewById(R.id.btnVolver);
        btnGuardar.setOnClickListener(this);
        btnVolver.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btnGuardar) {
            if (!modelo.actualizarNombre(txtNoControl.getText() + "", txtPseudonimo.getText() + "")) {
                Toast.makeText(this.getApplicationContext(), "Ocurrió un error", Toast.LENGTH_SHORT).show();
                return;
            }
            Toast.makeText(this.getApplicationContext(), "Pseudónimo cambiado con éxito", Toast.LENGTH_SHORT).show();
            return;
        }
        if (v == btnVolver) {
            finish();
            return;
        }
    }
}
