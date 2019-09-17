package com.project.comuni.Models.Firebase;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.ServerValue;

public class User {

    private String fotoPerfilURL;
    private String nombre;
    private String email;




    public User(){


    }

    public String getFotoPerfilURL() {
        return fotoPerfilURL;
    }

    public void setFotoPerfilURL(String fotoPerfilURL) {
        this.fotoPerfilURL = fotoPerfilURL;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
