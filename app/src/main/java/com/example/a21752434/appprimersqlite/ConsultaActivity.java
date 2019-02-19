package com.example.a21752434.appprimersqlite;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.a21752434.appprimersqlite.db.ContactosDatasource;
import com.example.a21752434.appprimersqlite.model.Contacto;
import com.example.a21752434.appprimersqlite.recycler.ContactoAdaptador;

import java.util.ArrayList;

public class ConsultaActivity extends AppCompatActivity {

    private RecyclerView rv;
    private ContactoAdaptador adaptador;
    private LinearLayoutManager llm;
    private ArrayList<Contacto> datos;
    ContactosDatasource cds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta);

        rv = findViewById(R.id.rvContactos);

        cds = new ContactosDatasource(this);
        datos = cds.consultarContactos();

        if(datos.isEmpty()) {
            Toast.makeText(this,"No se han encontrado contactos", Toast.LENGTH_LONG).show();
        } else {
            adaptador = new ContactoAdaptador(datos);
            llm = new LinearLayoutManager(this);

            rv.setLayoutManager(llm);
            rv.setAdapter(adaptador);
        }
    }
}
