package com.example.a21752434.appprimersqlite.model;

import java.io.Serializable;

public class Contacto implements Serializable {

    private int id;
    private String name;
    private String email;

    public Contacto(String name, String email) {
        id = -1;
        this.name = name;
        this.email = email;
    }

    public Contacto(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    /*********************                     SETTERS                     ************************/
    public void setId(int id) {
        this.id = id;
    }

    /*********************                     GETTERS                     ************************/

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
