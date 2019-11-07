package com.project.comuni.Servicios;

import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.project.comuni.Models.Espacio;
import com.project.comuni.Models.Firebase.Go;
import com.project.comuni.Models.Tag;
import com.project.comuni.Utils.FireUrl;

import java.util.ArrayList;

public class TagService {

    private Db db;

    private FireUrl url = new FireUrl("Tags");
    private String urlEspacios = "";

    private Go<Tag> tag;

    private void setUrlEspacios(){
        urlEspacios = url.AddKey(url.getRootInEspacios(tag.getObject().getEspacio()),
                url.getRoot());
    }

    public TagService(View v){
        db = new Db(v);
        tag = new Go<>();

    }

    public TagService(View v, Go<Tag> tagx){
        db = new Db(v);
        tag = tagx;
        setUrlEspacios();
    }

    public boolean create (){
        return db.create(tag,urlEspacios);
    }

    public boolean update (){
        return db.update(tag,urlEspacios);
    }

    public boolean delete (){
        return db.delete(tag,urlEspacios);
    }

    public Go<Tag> getObject(){
        db.DbRef().child(urlEspacios)
                .orderByKey()
                .equalTo(tag.getKey())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        tag.setKey(snapshot.getKey());
                        tag.setObject(snapshot.getValue(tag.getObject().getClass()));
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        tag = null;
                    }
                });
        return tag;
    }

    public Go<Tag> getAll(){
        db.DbRef().child(urlEspacios)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        tag.setKey(snapshot.getKey());
                        tag.setObject(snapshot.getValue(tag.getObject().getClass()));
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        tag = null;
                    }
                });
        return tag;
    }

}
