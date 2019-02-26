package com.example.a21752434.appprimersqlite;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a21752434.appprimersqlite.db.ContactosDatasource;
import com.example.a21752434.appprimersqlite.model.Contacto;

public class BorrarActivity extends AppCompatActivity {

    private EditText etId;
    private EditText etNombre;
    private EditText etEmail;

    private Button btnBuscar;
    private Button btnBorrar;

    private ContactosDatasource cds;
    private Contacto contacto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrar);

        etId = findViewById(R.id.etIdBorrar);
        etNombre = findViewById(R.id.etNombreBorrar);
        etEmail = findViewById(R.id.etEmailBorrar);

        btnBuscar = findViewById(R.id.btnBuscarBorrar);
        btnBorrar = findViewById(R.id.btnGuardarBorrar);

        cds = new ContactosDatasource(this);
    }

    public void buscarBorrar(View v) {
        String id = etId.getText().toString().trim();

        if(id.isEmpty()) {
            Toast.makeText(this, "Debe introducir un id", Toast.LENGTH_LONG).show();
        } else {
            contacto = cds.consultarContacto(Integer.parseInt(id));

            if(contacto != null) {
                etId.setEnabled(false);
                btnBuscar.setEnabled(false);


                btnBorrar.setEnabled(true);

                etNombre.setText(contacto.getName());
                etEmail.setText(contacto.getEmail());

            } else {
                Toast.makeText(this, "NO se ha encontrado un contacto con ese id", Toast.LENGTH_LONG).show();
            }

        }
    }

    public void borrar(View v) {
        cds.borrarContacto(contacto.getId());

        Toast.makeText(this, "Se ha borrado correctamente", Toast.LENGTH_LONG).show();

        // Reinicializar valores
        etId.setText("");
        etNombre.setText("");
        etEmail.setText("");

        etId.setEnabled(true);
        btnBuscar.setEnabled(true);

        btnBorrar.setEnabled(false);

    }

    public void cancelarMod(View v) {
        finish();
    }
}
