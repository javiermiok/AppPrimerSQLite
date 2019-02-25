package com.example.a21752434.appprimersqlite;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a21752434.appprimersqlite.db.ContactosDatasource;
import com.example.a21752434.appprimersqlite.model.Contacto;

public class AltaActivity extends AppCompatActivity {

    private EditText etNombre;
    private EditText etEmail;
    private ContactosDatasource cds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alta);

        etNombre = findViewById(R.id.etNombreAlta);
        etEmail = findViewById(R.id.etEmailAlta);

        cds = new ContactosDatasource(this);
    }

    public void guardar(View v) {
        String nombre = etNombre.getText().toString().trim();
        String email = etEmail.getText().toString().trim();

        if(nombre.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, "Los campos no pueden estar vacios", Toast.LENGTH_LONG).show();
        } else {
            Contacto c = new Contacto(nombre, email);
            long id = cds.insertarContacto(c);

            if(id != -1) {
                Toast.makeText(this, "Se ha insertado el nuevo contacto", Toast.LENGTH_LONG).show();
                c.setId((int) id);

                etNombre.setText("");
                etEmail.setText("");
            } else {
                Toast.makeText(this, "No se ha insertado el nuevo contacto", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void cancelar(View v) {
        finish();
    }
}
