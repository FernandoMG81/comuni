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
    private ArrayList<String> urlUsuarios = new ArrayList<>();
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

    public EspacioService(){
        db = new Db();
        espacio = new Go<>();
        espacio.setObject(new Espacio());
        if(espacio.getObject().getMiembros()!= null) {
            urlUsuariosMiembros = setUrlUsuarios(espacio.getObject().getMiembros());
            urlUsuarios.addAll(urlUsuariosMiembros);
        }
        if(espacio.getObject().getAdministradores()!= null) {
            urlUsuariosAdministradores = setUrlUsuarios(espacio.getObject().getAdministradores());
            urlUsuarios.addAll(urlUsuariosAdministradores);
        }
    }

    public EspacioService(Go<Espacio> usuariox){
        db = new Db();
        espacio = usuariox;
        urlUsuariosAdministradores = setUrlUsuarios(espacio.getObject().getAdministradores());
        urlUsuariosMiembros = setUrlUsuarios(espacio.getObject().getMiembros());
        urlUsuarios = urlUsuariosMiembros;
        urlUsuarios.addAll(urlUsuariosAdministradores);
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

    private ArrayList<Go<Espacio>> getAllFrom(String url) {
        ArrayList<Go<Espacio>> espacios = new ArrayList<>();
        db.DbRef().child(url)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        for (DataSnapshot x : snapshot.getChildren()) {
                            espacio.setKey(snapshot.getKey());
                            espacio.setObject(snapshot.getValue(espacio.getObject().getClass()));
                            espacios.add(espacio);
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        espacio = null;
                    }
                });
        return espacios;
    }

    public ArrayList<Go<Espacio>> getAll(){
        return getAllFrom(urlEspacio);
    }

    public ArrayList<Go<Espacio>> getAllFromUsuario(Go<Usuario> usuario){
        ArrayList<Go<Espacio>> espacios = new ArrayList<>();
        espacios = getAllFrom(url.AddKey(url.getUsuarios(),
                           url.AddKey(usuario.getKey(),
                             url.getAdministradores())));
        espacios.addAll(getAllFrom(url.AddKey(url.getUsuarios(),
                url.AddKey(usuario.getKey(),
                        url.getMiembros()))));
        return espacios;
    }
}
