package com.project.comuni.Servicios;

import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.project.comuni.Models.Comentario;
import com.project.comuni.Models.Firebase.Go;
import com.project.comuni.Utils.FireUrl;

import java.util.ArrayList;

public class ComentarioService {
    private Db db;

    private FireUrl url = new FireUrl("Comentarios");
    private String urlEspacios = "";

    private Go<Comentario> comentario;

    private void setUrlEspacios(){
        urlEspacios = url.AddKey(url.getRootInEspacios(comentario.getObject().getPost().getObject().getEspacio()),
                        url.AddKey("Posts",
                          url.AddKey(comentario.getObject().getPost().getKey(),
                            url.getRoot())));
    }

    public ComentarioService(View v){
        db = new Db(v);
        comentario = new Go<>();

    }

    public ComentarioService(View v, Go<Comentario> comentariox){
        db = new Db(v);
        comentario = comentariox;
        setUrlEspacios();
    }

    public boolean create (){
        return db.create(comentario,urlEspacios);
    }

    public boolean update (){
        return db.update(comentario,urlEspacios);
    }

    public boolean delete (){
        return db.delete(comentario,urlEspacios);
    }

    public Go<Comentario> getObject(){
        db.DbRef().child(urlEspacios)
                .orderByKey()
                .equalTo(comentario.getKey())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        comentario.setKey(snapshot.getKey());
                        comentario.setObject(snapshot.getValue(comentario.getObject().getClass()));
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        comentario = null;
                    }
                });
        return comentario;
    }

    public Go<Comentario> getAll(){
        db.DbRef().child(urlEspacios)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        comentario.setKey(snapshot.getKey());
                        comentario.setObject(snapshot.getValue(comentario.getObject().getClass()));
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        comentario = null;
                    }
                });
        return comentario;
    }

}
