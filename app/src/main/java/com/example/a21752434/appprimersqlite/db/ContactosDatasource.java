package com.example.a21752434.appprimersqlite.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.a21752434.appprimersqlite.model.Contacto;

import java.util.ArrayList;

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
    public Contacto consultarContacto(int idContacto) {

        /*1º PREPARAR RECURSOS*/
        SQLiteDatabase sdb = openReadable();
        Contacto contacto = null;
        String select = "SELECT " + ContactosContract.ContactoEntry.COLUMN_ID +
                ", " + ContactosContract.ContactoEntry.COLUMN_NAME +
                ", " + ContactosContract.ContactoEntry.COLUMN_MAIL +
                " FROM " + ContactosContract.ContactoEntry.TABLE_NAME +
                " WHERE " + ContactosContract.ContactoEntry.COLUMN_ID + " = ?";
        String[] argsWhere = {String.valueOf(idContacto)};

        /* 2º BUSQUEDA*/
        Cursor cursor = sdb.rawQuery(select, argsWhere);

        int id;
        String nombre;
        String email;
        if(cursor.moveToFirst()) {

            id = cursor.getInt(cursor.getColumnIndex(ContactosContract.ContactoEntry.COLUMN_ID));
            nombre = cursor.getString(cursor.getColumnIndex(ContactosContract.ContactoEntry.COLUMN_NAME ));
            email = cursor.getString(cursor.getColumnIndex(ContactosContract.ContactoEntry.COLUMN_MAIL ));
            contacto = new Contacto(id, nombre, email);
        }

        /*3º CERRAR RECURSOS*/
        cursor.close();
        sdb.close();

        return contacto;
    }

    public ArrayList<Contacto> consultarContactos() {

        /*1º PREPARAR RECURSOS*/
        SQLiteDatabase sdb = openReadable();
        ArrayList<Contacto> listaContacto = new ArrayList<Contacto>();

        /*String[] columnas = {ContactosContract.ContactoEntry.COLUMN_ID,
                ContactosContract.ContactoEntry.COLUMN_NAME,
                ContactosContract.ContactoEntry.COLUMN_MAIL};*/

        /* 2º BUSQUEDA*/
        Cursor cursor = sdb.query(ContactosContract.ContactoEntry.TABLE_NAME,
                new String[]{ContactosContract.ContactoEntry.COLUMN_ID,
                        ContactosContract.ContactoEntry.COLUMN_NAME,
                        ContactosContract.ContactoEntry.COLUMN_MAIL}, // se puede poner a null
                null,
                null,
                null,
                null,
                ContactosContract.ContactoEntry.COLUMN_NAME + " DESC", //order by
                null // se puede quitar
                );

        int id;
        String nombre;
        String email;
        if(cursor.moveToFirst()) {

            do {

                id = cursor.getInt(cursor.getColumnIndex(ContactosContract.ContactoEntry.COLUMN_ID));
                nombre = cursor.getString(cursor.getColumnIndex(ContactosContract.ContactoEntry.COLUMN_NAME ));
                email = cursor.getString(cursor.getColumnIndex(ContactosContract.ContactoEntry.COLUMN_MAIL ));
                listaContacto.add(new Contacto(id, nombre, email));

            } while (cursor.moveToNext());
            // se puede utilizar solo un while(cursor.moveToNext())
        }

        /*3º CERRAR RECURSOS*/
        cursor.close();
        sdb.close();

        return listaContacto;


    }

    /*********************                   INSERTAR                      ************************/
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

    /*********************                   MODIFICAR                     ************************/
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
/*        sdb.update(ContactosContract.ContactoEntry.TABLE_NAME,
                cv,
                String.format("%s=%d", ContactosContract.ContactoEntry.COLUMN_ID, c.getId()),
                null);*/

        sdb.setTransactionSuccessful();
        sdb.endTransaction();
        close(sdb);

    }

    /*********************                   BORRAR                     ************************/
    public void borrarContacto(int idContacto) {
        SQLiteDatabase sdb = openWriteable();
        sdb.beginTransaction();

        // Opción C
        String clausulaWhere = ContactosContract.ContactoEntry.COLUMN_ID+" = " +idContacto;

        sdb.delete(ContactosContract.ContactoEntry.TABLE_NAME, clausulaWhere,null);

        sdb.setTransactionSuccessful();
        sdb.endTransaction();
        close(sdb);

    }

}
