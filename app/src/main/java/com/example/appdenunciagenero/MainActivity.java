package com.example.appdenunciagenero;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.appdenunciagenero.controladores.ControladorActivacion;
import com.example.appdenunciagenero.controladores.ControladorHub;
import com.example.appdenunciagenero.modelo.Modelo;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnIngresar;
    private Button btnActivarCta;
    private EditText txtNoControl;
    private EditText txtContrasenia;
    private Modelo modelo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        modelo = new Modelo();
        if (!modelo.conectarse()) {
            Toast.makeText(this.getApplicationContext(), "No se pudo conectar a la base de datos", Toast.LENGTH_SHORT).show();
            finish();
        }
        txtNoControl = (EditText) findViewById(R.id.txtNoControl);
        txtContrasenia = (EditText) findViewById(R.id.txtContrasenia);
        btnIngresar = (Button) findViewById(R.id.btnIngresar);
        btnActivarCta = (Button) findViewById(R.id.btnActivarCta);
        btnIngresar.setOnClickListener(this);
        btnActivarCta.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btnIngresar) {
            if ((txtNoControl.getText() + "").compareTo("") == 0 || (txtContrasenia.getText() + "").compareTo("") == 0) {
                Toast.makeText(this.getApplicationContext(), "Favor de llenar ambos campos", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!modelo.validarCredenciales(txtNoControl.getText() + "", txtContrasenia.getText() + "")) {
                Toast.makeText(this.getApplicationContext(), "Credenciales no válidas", Toast.LENGTH_SHORT).show();
                return;
            }
            Intent i = new Intent(this, ControladorHub.class);
            i.putExtra("modelo", modelo);
            i.putExtra("noControl", txtNoControl.getText() + "");
            startActivity(i);
            return;
        }
        if (v == btnActivarCta) {
            Intent i = new Intent(this, ControladorActivacion.class);
            startActivity(i);
        }
    }
}