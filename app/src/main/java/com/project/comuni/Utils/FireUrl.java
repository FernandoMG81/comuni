package com.project.comuni.Utils;

import com.project.comuni.Models.Espacio;
import com.project.comuni.Models.Firebase.Go;
import com.project.comuni.Models.Usuario;

import java.util.ArrayList;

public class FireUrl {

    private String root = "";
    private String usuarios = "Usuarios";
    private String administradores = "administradores";
    private String miembros = "miembros";
    private String espacios = "Espacios";
    private String posts = "Posts";
    private String datos = "Datos";

    public FireUrl(String root){this.root = root;}

    public FireUrl(){}

    public String AddKey(String url, String key){
        return url + "/" + key;
    }

    public ArrayList<String> UrlToList(String url){
        ArrayList<String> keys = new ArrayList<>();
        String aux[] = url.split("/");
        for (String x:aux)
        {
            if (x.contains("/")){ x.replaceAll("/",""); }
            keys.add(x);
        }
        return keys;
    }

    public String ListToUrl(ArrayList<String> keys){
        String url = "";
        if (keys.size()>0)
        {
            url = keys.get(0);
            keys.remove(0);
        }
        for (String x: keys)
        {
        url = url + "/" + keys;
        }
        return url;
    }

    public String ToUrlEspacios (String url){
        return url.replace("/","");
    }

    public String FromUrlEspacios (String url){
        return ListToUrl(UrlToList(url))
                .replace("[","")
                .replace("]","");
    }

    public String getRootInEspacios(Go<Espacio> espacio){
        if(espacio.getObject().getEspacioUrl() != null){
            return (AddKey(getEspacios(),
                    AddKey(espacio.getObject().getEspacioUrl(),
                            espacio.getKey())));
        }
        else {
            return (AddKey(getEspacios(),espacio.getKey()));
        }
    }

    public String getRootInUsuarios(Go<Usuario> usuario){
        return AddKey(getUsuarios(),usuario.getKey());
    }


    //Getters de propiedades

    public String getRoot() {
        return root;
    }

    public String getUsuarios() {
        return usuarios;
    }

    public String getAdministradores() {
        return administradores;
    }

    public String getMiembros() {
        return miembros;
    }

    public String getEspacios() {
        return espacios;
    }

    public String getDatos() {
        return datos;
    }

    public String getPosts() {
        return posts;
    }
}
