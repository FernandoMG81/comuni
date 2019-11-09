package com.project.comuni.Servicios;

import android.view.View;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.project.comuni.Models.Firebase.Go;
import com.project.comuni.Models.Mensaje;
import com.project.comuni.Models.Usuario;
import com.project.comuni.Utils.FireUrl;

import java.util.ArrayList;

public class MensajeService {
    private Db db;

    private FireUrl url = new FireUrl("Mensajes");
    private String urlUsuarioEmisor = "";
    private String urlUsuarioReceptor = "";


    private Go<Mensaje> mensaje;

    private void setUrlUsuarios(){
        urlUsuarioEmisor = url.AddKey(url.getRootInUsuarios(mensaje.getObject().getEmisor()),
                            url.AddKey(url.getRoot(),
                             mensaje.getObject().getReceptor().getKey()));
        urlUsuarioReceptor = url.AddKey(url.getRootInUsuarios(mensaje.getObject().getReceptor()),
                url.AddKey(url.getRoot(),
                        mensaje.getObject().getEmisor().getKey()));
    }

    public MensajeService(){
        db = new Db();
        mensaje = new Go<>();

    }

    public MensajeService( Go<Mensaje> mensajex){
        db = new Db();
        mensaje = mensajex;
        setUrlUsuarios();
    }

    public Task create (){
        return db.create(mensaje,urlUsuarioEmisor)
                .addOnCompleteListener(new OnCompleteListener<Transaction.Result>() {
            @Override
            public void onComplete(@NonNull Task<Transaction.Result> task) {
                if (task.isSuccessful()) {
                    db.create(mensaje,urlUsuarioReceptor);
                }
            }
        });
    }

    public Task update (){
        return db.update(mensaje,urlUsuarioEmisor)
                .addOnCompleteListener(new OnCompleteListener<Transaction.Result>() {
            @Override
            public void onComplete(@NonNull Task<Transaction.Result> task) {
                if (task.isSuccessful()) {
                    db.update(mensaje,urlUsuarioReceptor);
                }
            }
        });

    }

    public Task delete(){
        return db.delete(mensaje,urlUsuarioEmisor)
                .addOnCompleteListener(new OnCompleteListener<Transaction.Result>() {
            @Override
            public void onComplete(@NonNull Task<Transaction.Result> task) {
                if (task.isSuccessful()) {
                    db.delete(mensaje,urlUsuarioReceptor);
                }
            }
        });
    }

    public Go<Mensaje> getObject(){
        db.DbRef().child(urlUsuarioEmisor)
                .orderByKey()
                .equalTo(mensaje.getKey())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        mensaje.setKey(snapshot.getKey());
                        mensaje.setObject(snapshot.getValue(mensaje.getObject().getClass()));
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        mensaje = null;
                    }
                });
        return mensaje;
    }

    public ArrayList<Go<Mensaje>> getAllFromXandY(Go<Usuario> X, Go<Usuario> Y){
        ArrayList<Go<Mensaje>> mensajes = new ArrayList<>();
        db.DbRef().child(url.AddKey(url.getRootInUsuarios(X),
                          url.AddKey(url.getRoot(),
                            Y.getKey())))
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        for (DataSnapshot x:snapshot.getChildren()) {
                            mensaje.setKey(snapshot.getKey());
                            mensaje.setObject(snapshot.getValue(mensaje.getObject().getClass()));
                            mensajes.add(mensaje);
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        mensaje = null;
                    }
                });
        return mensajes;
    }
}
