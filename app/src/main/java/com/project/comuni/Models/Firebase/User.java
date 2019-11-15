package com.project.comuni.Models.Firebase;


public class User {

    private String fotoPerfilURL;
    private String nombre;
    private String email;
    private String token;


    public User(){
    }

    public String getToken() {return token;}

    public void setToken(String token) {this.token = token;}

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
