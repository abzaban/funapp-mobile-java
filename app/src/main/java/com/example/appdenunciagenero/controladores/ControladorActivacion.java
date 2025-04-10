package com.example.appdenunciagenero.controladores;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.appdenunciagenero.R;

public class ControladorActivacion extends AppCompatActivity implements View.OnClickListener {

    private Button btnActivarNoControl;
    private Button btnVolver;
    private EditText txtActivarNoControl;
    private EditText txtActivarContrasenia;
    private EditText txtConfirmarContrasenia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_activacion);

        txtActivarNoControl = (EditText) findViewById(R.id.txtActivarNoControl);
        txtActivarContrasenia = (EditText) findViewById(R.id.txtActivarContrasenia);
        txtConfirmarContrasenia = (EditText) findViewById(R.id.txtConfirmarContrasenia);

        btnActivarNoControl = (Button) findViewById(R.id.btnActivarNoControl);
        btnVolver = (Button) findViewById(R.id.btnVolver);
        btnActivarNoControl.setOnClickListener(this);
        btnVolver.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btnActivarNoControl) {
            if (txtActivarNoControl.getText().length() < 8) {
                Toast.makeText(this.getApplicationContext(), "Ingrese un no. de control válido", Toast.LENGTH_SHORT).show();
                return;
            }
            if ((txtActivarContrasenia.getText() + "").length() < 8 || (txtConfirmarContrasenia.getText() + "").length() < 8) {
                Toast.makeText(this.getApplicationContext(), "La contraseña debe ser de mínimo 8 carácteres", Toast.LENGTH_SHORT).show();
                return;
            }
            if ((txtActivarContrasenia.getText() + "").compareTo(txtConfirmarContrasenia.getText() + "") != 0) {
                Toast.makeText(this.getApplicationContext(), "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                return;
            }
            return;
        }
        if (v == btnVolver) {
            finish();
            return;
        }
    }
}
