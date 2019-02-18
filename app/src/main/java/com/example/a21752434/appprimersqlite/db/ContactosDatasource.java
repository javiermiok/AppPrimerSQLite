package com.example.a21752434.appprimersqlite.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.a21752434.appprimersqlite.model.Contacto;

/**
 * Métodos para manipular la base de datos
 */
public class ContactosDatasource {
    /*********************                                                 ************************/
    /*********************            ATRIBUTOS - CONSTRUCTOR              ************************/
    /*********************                                                 ************************/

    private ContactosSQLiteHelper csh;
    private Context contexto;

    public ContactosDatasource(Context contexto) {
        this.contexto = contexto;
        csh = new ContactosSQLiteHelper(contexto);
    }

    /*********************              LEER/ESCRIBIR/CERRAR               ************************/
    public SQLiteDatabase openReadable() {
        return csh.getReadableDatabase();
    }
    public SQLiteDatabase openWriteable() {
        return csh.getWritableDatabase();
    }
    public void close(SQLiteDatabase database) {
        database.close();
    }

    /*********************                   CONSULTA                      ************************/
    public void consultarContactos() {

    }

    public long insertarContacto(Contacto contacto) {
        SQLiteDatabase sdb = openWriteable();

        sdb.beginTransaction();

        ContentValues cv = new ContentValues();
        cv.put(ContactosContract.ContactoEntry.COLUMN_NAME, contacto.getName());
        cv.put(ContactosContract.ContactoEntry.COLUMN_MAIL, contacto.getEmail());

        long id = sdb.insert(ContactosContract.ContactoEntry.TABLE_NAME, null, cv);

        if (id != -1) {
            sdb.setTransactionSuccessful();
        }

        sdb.endTransaction();
        close(sdb);

        return id;
    }

    public void modificarContactos(Contacto c) {
        SQLiteDatabase sdb = openWriteable();

        sdb.beginTransaction();

        ContentValues cv = new ContentValues();
        cv.put(ContactosContract.ContactoEntry.COLUMN_NAME, c.getName());
        cv.put(ContactosContract.ContactoEntry.COLUMN_MAIL, c.getEmail());

        // Opcion A
        String clausulaWhere = ContactosContract.ContactoEntry.COLUMN_ID+" = ?";
        String[] args = {String.valueOf(c.getId())};

        sdb.update(ContactosContract.ContactoEntry.TABLE_NAME,
                cv, clausulaWhere,args);

        // Opcion B
        sdb.update(ContactosContract.ContactoEntry.TABLE_NAME,
                cv,
                String.format("%s=%d", ContactosContract.ContactoEntry.COLUMN_ID, c.getId()),
                null);


        sdb.endTransaction();
        close(sdb);

    }

    public void borrarContacto() {

    }

}
