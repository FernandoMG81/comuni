package com.project.comuni.Servicios;

import android.graphics.drawable.Drawable;
import android.renderscript.Sampler;
import android.view.View;

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

    public UsuarioService(){
        db = new Db();
        usuario = new Go<>();
        urlEspaciosAdministradores = setUrlEspacios(usuario.getObject().getAdministradores());
        urlEspaciosMiembros = setUrlEspacios(usuario.getObject().getMiembros());
        urlEspacios = urlEspaciosMiembros;
        urlEspacios.addAll(urlEspaciosAdministradores);
    }

    public UsuarioService(Go<Usuario> usuariox){
        db = new Db();
        usuario = usuariox;
    }

    public Task create (){
        return db.create(usuario,url.getRoot());
    }

    public Task update () {
        return db.update(usuario, url.getRoot())
                .addOnCompleteListener(new OnCompleteListener<Transaction.Result>() {
                    @Override
                    public void onComplete(@NonNull Task<Transaction.Result> task) {
                        if (task.isSuccessful()) {
                            for (String x : urlEspacios) {
                                db.update(usuario, x);
                            }
                        }
                    }
                });
    }

    public Task delete (){
         return db.delete(usuario, url.getRoot())
                .addOnCompleteListener(new OnCompleteListener<Transaction.Result>() {
                    @Override
                    public void onComplete(@NonNull Task<Transaction.Result> task) {
                        if (task.isSuccessful()) {
                            for (String x : urlEspacios) {
                                db.delete(usuario, x);
                            }
                        }
                    }
                });
    }

    public Query getObject(){
       return db.getObject(usuario.getKey(),url.getRoot());
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

