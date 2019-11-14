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
import java.util.HashMap;
import java.util.Map;

public class EspacioService {

    private Db db = new Db();

    private FireUrl url = new FireUrl("Espacios");
    private ArrayList<String> urlUsuarios = new ArrayList<>();
    private ArrayList<String> urlUsuariosAdministradores;
    private ArrayList<String> urlUsuariosMiembros;
    private String urlEspacio = url.getRoot();

    private Go<Espacio> espacio = new Go<>();

    private ArrayList<String> setUrlUsuarios(Map<String,Usuario> usuarios){
        ArrayList<String> Arrayx = new ArrayList<>();
        if (usuarios != null) {
            for (Map.Entry<String, Usuario> x : usuarios.entrySet()) {
                url.getRootInUsuarios(new Go<Usuario>(x));

            }
        }
        return Arrayx;
    }

    public EspacioService(){}

    public EspacioService(Go<Espacio> espaciox){
        espacio = espaciox;
        urlUsuariosAdministradores = setUrlUsuarios(espacio.getObject().getAdministradores());
        urlUsuariosMiembros = setUrlUsuarios(espacio.getObject().getMiembros());
        urlUsuarios = urlUsuariosMiembros;
        urlUsuarios.addAll(urlUsuariosAdministradores);
        if(espacio.getObject().getEspacioUrl() != null){
            urlEspacio = url.AddKey(url.getRoot(),espacio.getObject().getEspacioUrl());
        }
    }

    public Task create (){
        espacio.setKey(db.createKey(urlEspacio));
       return db.updateWithDatos(new Go<>(espacio.getKey(),espacio.getObject().returnSmallerMaps())
               ,urlEspacio).addOnCompleteListener(new OnCompleteListener<Transaction.Result>() {
            @Override
            public void onComplete(@NonNull Task<Transaction.Result> task) {
                if (task.isSuccessful()) {
                    if (espacio.getObject().getAdministradores()!= null) {
                        for (Map.Entry<String, Usuario> x : espacio.getObject().getAdministradores().entrySet()) {
                            Go<Usuario> aux = new Go<>(x.getKey(), x.getValue().returnSmall());
                            db.update(new Go<>(espacio.getKey(), espacio.getObject().returnSmall()),
                                    url.AddKey(url.getUsuarios(), url.AddKey(aux.getKey(),url.getAdministradores())));
                        }
                    }
                    if (espacio.getObject().getMiembros()!= null) {
                        for (Map.Entry<String, Usuario> x : espacio.getObject().getMiembros().entrySet()) {
                            Go<Usuario> aux = new Go<>(x.getKey(), x.getValue().returnSmall());
                            db.update(new Go<>(espacio.getKey(), espacio.getObject().returnSmall()),
                                    url.AddKey(url.getUsuarios(), url.AddKey(aux.getKey(),url.getMiembros())));
                        }
                    }
                }
            }
        });
    }

    public Task updateSoloEspacio(){
        return db.updateWithDatos(new Go<>(espacio.getKey(), espacio.getObject().returnSmallerMaps()),
                urlEspacio);
    }

    public Task update () {
        return db.updateWithDatos(new Go<>(espacio.getKey(), espacio.getObject().returnSmallerMaps()),
                urlEspacio)
                .addOnCompleteListener(new OnCompleteListener<Transaction.Result>() {
                    @Override
                    public void onComplete(@NonNull Task<Transaction.Result> task) {
                        if (task.isSuccessful()) {
                            if (espacio.getObject().getAdministradores() != null) {
                                for (Map.Entry<String, Usuario> x : espacio.getObject().getAdministradores().entrySet()) {
                                    Go<Usuario> aux = new Go<>(x.getKey(), x.getValue());
                                    db.update(new Go<>(espacio.getKey(), espacio.getObject().returnSmall()),
                                            url.AddKey(url.getUsuarios(), url.AddKey(aux.getKey(), url.getAdministradores())));
                                }
                            }
                            if (espacio.getObject().getMiembros() != null) {
                                for (Map.Entry<String, Usuario> x : espacio.getObject().getMiembros().entrySet()) {
                                    Go<Usuario> aux = new Go<>(x.getKey(), x.getValue());
                                    db.update(new Go<>(espacio.getKey(), espacio.getObject().returnSmall()),
                                            url.AddKey(url.getUsuarios(), url.AddKey(aux.getKey(), url.getMiembros())));
                                }
                            }
                        }
                    }
                });
    }

    public Task updateEspacioUsuario(Go<Usuario> usuario){

        return db.updateWithDatos(new Go<>(espacio.getKey(), espacio.getObject().returnSmallerMaps()),
                urlEspacio)
                .addOnCompleteListener(new OnCompleteListener<Transaction.Result>() {
                    @Override
                    public void onComplete(@NonNull Task<Transaction.Result> task) {
                        if (task.isSuccessful()) {
                            new UsuarioService(usuario).updateSoloUsuario();
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

    public Task deleteUsuario (Go<Usuario> usuario){

        for (Map.Entry<String,Espacio> espaciox:usuario.getObject().getAdministradores().entrySet())
        {
            if (espacio.getKey().equals(espaciox.getKey())){
                return db.delete(usuario,
                        url.AddKey(urlEspacio,
                                url.AddKey(espacio.getKey(),
                                        url.AddKey(url.getDatos(),
                                            url.getAdministradores()))))
                        .addOnCompleteListener(new OnCompleteListener<Transaction.Result>() {
                            @Override
                            public void onComplete(@NonNull Task<Transaction.Result> task) {
                                if (task.isSuccessful()) {
                                    db.delete(espacio,
                                            url.AddKey(url.getUsuarios(),
                                                    url.AddKey(usuario.getKey(),
                                                            url.getAdministradores())));
                                }
                            }
                        });
            }
        }
        return db.delete(usuario,
                        url.AddKey(urlEspacio,
                            url.AddKey(espacio.getKey(),
                                    url.AddKey(url.getDatos(),
                                        url.getMiembros()))))
                .addOnCompleteListener(new OnCompleteListener<Transaction.Result>() {
                    @Override
                    public void onComplete(@NonNull Task<Transaction.Result> task) {
                        if (task.isSuccessful()) {
                            db.delete(espacio,
                                    url.AddKey(url.getUsuarios(),
                                            url.AddKey(usuario.getKey(),
                                                    url.getMiembros())));
                        }
                    }
        });
    }

    public Query getObject(){
        return db.getObject(url.getDatos(),url.AddKey(urlEspacio,espacio.getKey()));
    }

    private Query getAllFrom(String url) {
        return db.DbRef().child(url);
    }
    public Query getAllFromUsuario(Go<Usuario> usuario)
    {
        return getAllFrom(
                url.AddKey(url.getUsuarios(),
                        url.AddKey(usuario.getKey(),
                                url.getAdministradores())));
    }

}
