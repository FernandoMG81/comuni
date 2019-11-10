package com.project.comuni.Servicios;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.Transaction;
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
        if (usuarios != null) {
            for (Map.Entry<String, Usuario> x : usuarios.entrySet()) {
                url.getRootInUsuarios(new Go<Usuario>(x));
            }
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

    public EspacioService(Go<Espacio> espaciox){
        db = new Db();
        espacio = espaciox;
        urlUsuariosAdministradores = setUrlUsuarios(espacio.getObject().getAdministradores());
        urlUsuariosMiembros = setUrlUsuarios(espacio.getObject().getMiembros());
        urlUsuarios = urlUsuariosMiembros;
        urlUsuarios.addAll(urlUsuariosAdministradores);
        if(espacio.getObject().getEspacioUrl() != null){
            urlEspacio = url.AddKey(url.getRoot(),url.FromUrlEspacios(espacio.getObject().getEspacioUrl()));
        }
    }

    public Task create (){
        espacio.setKey(db.createKey(urlEspacio));
       return db.update(espacio,urlEspacio).addOnCompleteListener(new OnCompleteListener<Transaction.Result>() {
            @Override
            public void onComplete(@NonNull Task<Transaction.Result> task) {
                if (task.isSuccessful()) {
                    if (espacio.getObject().getAdministradores()!= null) {
                        for (Map.Entry<String, Usuario> x : espacio.getObject().getAdministradores().entrySet()) {
                            Go<Usuario> aux = new Go<>(x.getKey(), x.getValue());
                            db.update(espacio, url.AddKey(url.getUsuarios(), url.AddKey(aux.getKey(),url.getAdministradores())));
                        }
                    }
                    if (espacio.getObject().getMiembros()!= null) {
                        for (Map.Entry<String, Usuario> x : espacio.getObject().getMiembros().entrySet()) {
                            Go<Usuario> aux = new Go<>(x.getKey(), x.getValue());
                            db.update(espacio, url.AddKey(url.getUsuarios(), url.AddKey(aux.getKey(),url.getMiembros())));
                        }
                    }
                }
            }
        });
    }

    public Task update () {
        return db.update(espacio, urlEspacio).addOnCompleteListener(new OnCompleteListener<Transaction.Result>() {
            @Override
            public void onComplete(@NonNull Task<Transaction.Result> task) {
                if (task.isSuccessful()) {
                    for (String x : urlUsuarios) {
                        db.update(espacio, x);
                    }
                }
            }
        });
    }

    public Task delete (){

        return db.delete(espacio, urlEspacio).addOnCompleteListener(new OnCompleteListener<Transaction.Result>() {
            @Override
            public void onComplete(@NonNull Task<Transaction.Result> task) {
                if (task.isSuccessful()) {
                    for (String x : urlUsuarios) {
                        db.delete(espacio, x);
                    }
                }
            }
        });
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

    private Query getAllFrom(String url) {
        return db.DbRef().child(url);
    }
    public Query getAllFromUsuario(Go<Usuario> usuario){return getAllFrom(url.AddKey(url.getUsuarios(),url.AddKey(usuario.getKey(),url.getAdministradores())));}
    public Query getAll(){
        return getAllFrom(urlEspacio);
    }

/*    public ArrayList<Go<Espacio>> getAllFromUsuario(Go<Usuario> usuario){
        ArrayList<Go<Espacio>> espacios = new ArrayList<>();
        espacios = getAllFrom(url.AddKey(url.getUsuarios(),
                           url.AddKey(usuario.getKey(),
                             url.getAdministradores())));
        espacios.addAll(getAllFrom(url.AddKey(url.getUsuarios(),
                url.AddKey(usuario.getKey(),
                        url.getMiembros()))));
        return espacios;
    }*/
}
