package com.project.comuni.Servicios;

import android.media.Image;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.project.comuni.Models.Firebase.Go;
import com.project.comuni.Models.Noticia;
import com.project.comuni.Models.Usuario;
import com.project.comuni.R;
import com.project.comuni.Utils.FireUrl;

import java.util.ArrayList;
import java.util.Calendar;

import androidx.constraintlayout.solver.widgets.Snapshot;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.SnapHelper;

public class NoticiaService {

    private Db db;

    private FireUrl url = new FireUrl("Noticias");

    private Go<Noticia> noticia;

    public NoticiaService(){
        db = new Db();
        noticia = new Go<>();

    }

    public NoticiaService(Go<Noticia> noticiax){
        db = new Db();
        noticia = noticiax;
    }

    public Task create (){
        return db.create(noticia,url.AddKey(url.getEspacios(),url.getRoot()));
    }

    public Task update (){
        return db.update(noticia,url.AddKey(url.getEspacios(),url.getRoot()));
    }

    public Task delete (){
        return db.delete(noticia,url.AddKey(url.getEspacios(),url.getRoot()));
    }

    public Go<Noticia> getObject(){
        db.DbRef().child(url.AddKey(url.getEspacios(),url.getRoot()))
                .orderByKey()
                .equalTo(noticia.getKey())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        noticia.setKey(snapshot.getKey());
                        noticia.setObject(snapshot.getValue(noticia.getObject().getClass()));
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        noticia = null;
                    }
                });
        return noticia;
    }

    public ArrayList<Go<Noticia>> getAll(){
        ArrayList<Go<Noticia>> noticias = new ArrayList<>();
        db.DbRef().child(url.AddKey(url.getEspacios(),url.getRoot()))
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        for (DataSnapshot x: snapshot.getChildren())
                        {
                            noticia.setKey(snapshot.getKey());
                            noticia.setObject(snapshot.getValue(noticia.getObject().getClass()));
                            noticias.add(noticia);
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        noticia = null;
                    }
                });
        return noticias;
    }

}
