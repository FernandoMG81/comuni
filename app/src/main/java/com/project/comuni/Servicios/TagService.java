package com.project.comuni.Servicios;

import android.view.View;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
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

    public TagService(){
        db = new Db();
        tag = new Go<>();

    }

    public TagService(Go<Tag> tagx){
        db = new Db();
        tag = tagx;
        setUrlEspacios();
    }

    public Task create (){
        return db.create(tag,urlEspacios);
    }

    public Task update (){
        return db.update(tag,urlEspacios);
    }

    public Task delete (){
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

    public ArrayList<Go<Tag>> getAll(){
        ArrayList<Go<Tag>> tags = new ArrayList<>();
        db.DbRef().child(urlEspacios)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        for (DataSnapshot x: snapshot.getChildren())
                        {
                            tag.setKey(x.getKey());
                            tag.setObject(x.getValue(tag.getObject().getClass()));
                            tags.add(tag);
                        }

                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        tag = null;
                    }
                });
        return tags;
    }

    public Query getAllFromEspacios(Go<Espacio> espacio) {
        return db.getAll(url.AddKey(url.getRootInEspacios(espacio),url.getRoot()));
    }

}
