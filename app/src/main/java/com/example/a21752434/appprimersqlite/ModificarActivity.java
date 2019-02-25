package com.example.a21752434.appprimersqlite;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a21752434.appprimersqlite.db.ContactosDatasource;
import com.example.a21752434.appprimersqlite.model.Contacto;

public class ModificarActivity extends AppCompatActivity {

    private EditText etId;
    private EditText etNombre;
    private EditText etEmail;

    private Button btnBuscar;
    private Button btnGuardar;

    private ContactosDatasource cds;
    private Contacto contacto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar);

        etId = findViewById(R.id.etId);
        etNombre = findViewById(R.id.etNombreMod);
        etEmail = findViewById(R.id.etEmailMod);

        btnBuscar = findViewById(R.id.btnBuscarMod);
        btnGuardar = findViewById(R.id.btnGuardarMod);

        cds = new ContactosDatasource(this);
    }


    public void buscar(View v) {
        String id = etId.getText().toString().trim();

        if(id.isEmpty()) {
            Toast.makeText(this, "Debe introducir un id", Toast.LENGTH_LONG).show();
        } else {
            contacto = cds.consultarContacto(Integer.parseInt(id));

            if(contacto != null) {
                etId.setEnabled(false);
                btnBuscar.setEnabled(false);

                etNombre.setEnabled(true);
                etEmail.setEnabled(true);
                btnGuardar.setEnabled(true);

                etNombre.setText(contacto.getName());
                etEmail.setText(contacto.getEmail());

            } else {
                Toast.makeText(this, "NO se ha encontrado un contacto con ese id", Toast.LENGTH_LONG).show();
            }

        }

    }

    public void modificarContacto(View v) {
        String nombre = etNombre.getText().toString().trim();
        String email = etEmail.getText().toString().trim();

        if(nombre.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, "Los campos no pueden estar vacios", Toast.LENGTH_LONG).show();
        } else {
            contacto.setName(nombre);
            contacto.setEmail(email);
            cds.modificarContactos(contacto);

            Toast.makeText(this, "Se ha realizado la modificación correctamente", Toast.LENGTH_LONG).show();


            // Reinicializar valores
            etId.setText("");
            etNombre.setText("");
            etEmail.setText("");

            etId.setEnabled(true);
            btnBuscar.setEnabled(true);

            etNombre.setEnabled(false);
            etEmail.setEnabled(false);
            btnGuardar.setEnabled(false);

        }
    }

    public void cancelarMod(View v) {
        finish();
    }
}
