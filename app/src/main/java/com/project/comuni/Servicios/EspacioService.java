package com.project.comuni.Servicios;

import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.project.comuni.Models.Espacio;
import com.project.comuni.Models.Firebase.Go;
import com.project.comuni.Models.Usuario;
import com.project.comuni.Utils.FireUrl;

import java.util.ArrayList;
import java.util.Map;

public class EspacioService {

    private Db db;

    private FireUrl url = new FireUrl("Espacios");
    private ArrayList<String> urlUsuarios;
    private ArrayList<String> urlUsuariosAdministradores;
    private ArrayList<String> urlUsuariosMiembros;
    private String urlEspacio = url.getRoot();

    private Go<Espacio> espacio;

    private ArrayList<String> setUrlUsuarios(Map<String,Usuario> usuarios){
        ArrayList<String> Arrayx = new ArrayList<>();
        for (Map.Entry<String,Usuario> x:usuarios.entrySet()) {
            url.getRootInUsuarios(new Go<Usuario>(x));
        }
        return Arrayx;
    }

    private void setUrlUsuariosAmbos(){

        urlUsuarios = urlUsuariosMiembros;
        for (String x: urlUsuariosAdministradores){
            urlUsuarios.add(x);
        }
    }

    public EspacioService(View v){
        db = new Db(v);
        espacio = new Go<>();
        urlUsuariosAdministradores = setUrlUsuarios(espacio.getObject().getAdministradores());
        urlUsuariosMiembros = setUrlUsuarios(espacio.getObject().getMiembros());
        setUrlUsuariosAmbos();
    }

    public EspacioService(View v, Go<Espacio> usuariox){
        db = new Db(v);
        espacio = usuariox;
        urlUsuariosAdministradores = setUrlUsuarios(espacio.getObject().getAdministradores());
        urlUsuariosMiembros = setUrlUsuarios(espacio.getObject().getMiembros());
        setUrlUsuariosAmbos();
        if(espacio.getObject().getEspacioUrl() != null & !espacio.getObject().getEspacioUrl().isEmpty()){
            urlEspacio = url.AddKey(url.getRoot(),url.FromUrlEspacios(espacio.getObject().getEspacioUrl()));
        }
    }

    public boolean create (){
        return db.create(espacio,urlEspacio);
    }

    public boolean update () {
        boolean TodoOk, b = true;
        TodoOk = db.update(espacio, urlEspacio);
        if (TodoOk == true) {
            for (String x : urlUsuarios) {
                b = db.update(espacio, x);
                if (b == false) {
                    TodoOk = false;
                }
            }
        }
        return TodoOk;
    }

    public boolean delete (){
        boolean TodoOk, b = true;
        TodoOk = db.delete(espacio, urlEspacio);
        if (TodoOk == true) {
            for (String x : urlUsuarios) {
                b = db.delete(espacio, x);
                if (b == false) {
                    TodoOk = false;
                }
            }
        }
        return TodoOk;
    }

    public Go<Espacio> getObject(){
        db.DbRef().child(urlEspacio)
                .orderByKey()
                .equalTo(espacio.getKey())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        espacio.setKey(snapshot.getKey());
                        espacio.setObject(snapshot.getValue(espacio.getObject().getClass()));
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        espacio = null;
                    }
                });
        return espacio;
    }

    public Go<Espacio> getAll(){
        db.DbRef().child(urlEspacio)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        espacio.setKey(snapshot.getKey());
                        espacio.setObject(snapshot.getValue(espacio.getObject().getClass()));
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        espacio = null;
                    }
                });
        return espacio;
    }
}
