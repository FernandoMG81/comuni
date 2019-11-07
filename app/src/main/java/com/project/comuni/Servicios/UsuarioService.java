package com.project.comuni.Servicios;

import android.graphics.drawable.Drawable;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.project.comuni.Models.Espacio;
import com.project.comuni.Models.Firebase.Go;
import com.project.comuni.Models.Usuario;
import com.project.comuni.R;
import com.project.comuni.Utils.FireUrl;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UsuarioService {

    private Db db;

    private FireUrl url = new FireUrl("Usuarios");
    private ArrayList<String> urlEspacios;
    private ArrayList<String> urlEspaciosAdministradores;
    private ArrayList<String> urlEspaciosMiembros;

    private Go<Usuario> usuario;

    private ArrayList<String> setUrlEspacios(Map<String,Espacio> espacios){
        ArrayList<String> Arrayx = new ArrayList<>();
        for (Map.Entry<String,Espacio> x:espacios.entrySet()) {
            url.getRootInEspacios(new Go<Espacio>(x));
        }
        return Arrayx;
    }

    private void setUrlEspaciosAmbos(){

        urlEspacios = urlEspaciosMiembros;
        for (String x: urlEspaciosAdministradores){
            urlEspacios.add(x);
        }
    }

    public UsuarioService(View v){
        db = new Db(v);
        usuario = new Go<>();
        urlEspaciosAdministradores = setUrlEspacios(usuario.getObject().getAdministradores());
        urlEspaciosMiembros = setUrlEspacios(usuario.getObject().getMiembros());
        setUrlEspaciosAmbos();
    }

    public UsuarioService(View v, Go<Usuario> usuariox){
        db = new Db(v);
        usuario = usuariox;
    }

    public boolean create (){
        return db.create(usuario,url.getRoot());
    }

    public boolean update () {
        boolean TodoOk, b = true;
        TodoOk = db.update(usuario, url.getRoot());
        if (TodoOk == true) {
            for (String x : urlEspacios) {
                b = db.update(usuario, x);
                if (b == false) {
                    TodoOk = false;
                }
            }
        }
        return TodoOk;
    }

    public boolean delete (){
        boolean TodoOk, b = true;
        TodoOk = db.delete(usuario, url.getRoot());
        if (TodoOk == true) {
            for (String x : urlEspacios) {
                b = db.delete(usuario, x);
                if (b == false) {
                    TodoOk = false;
                }
            }
        }
        return TodoOk;
    }

    public Go<Usuario> getObject(){
        db.DbRef().child(url.getRoot())
                .orderByKey()
                .equalTo(usuario.getKey())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        usuario.setKey(snapshot.getKey());
                        usuario.setObject(snapshot.getValue(usuario.getObject().getClass()));
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        usuario = null;
                    }
                });
        return usuario;
    }

    public Go<Usuario> getAll(){
        db.DbRef().child(url.getRoot())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        usuario.setKey(snapshot.getKey());
                        usuario.setObject(snapshot.getValue(usuario.getObject().getClass()));
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        usuario = null;
                    }
                });
        return usuario;
    }

}

