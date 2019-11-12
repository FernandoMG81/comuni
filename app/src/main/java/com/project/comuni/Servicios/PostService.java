package com.project.comuni.Servicios;

import android.view.View;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.project.comuni.Models.Espacio;
import com.project.comuni.Models.Firebase.Go;
import com.project.comuni.Models.Post;
import com.project.comuni.Utils.FireUrl;

import java.util.ArrayList;

public class PostService {

    private Db db;

    private FireUrl url = new FireUrl("Posts");
    private String urlEspacios = "";

    private Go<Post> post;

    private void setUrlEspacios(){
        urlEspacios = url.AddKey(url.getRootInEspacios(post.getObject().getEspacio()),
                url.getRoot());
    }

    public PostService(){
        db = new Db();
        post = new Go<>();
    }

    public PostService(Go<Post> postx){
        db = new Db();
        post = postx;
        setUrlEspacios();
    }

    public Task create (){
        post.setKey(db.createKey(urlEspacios));
        if (!(post.getObject().getTag().getKey() != null)) {
        post.getObject().getTag().setObject(null);
        }
        return db.update(post,urlEspacios);
    }

    public Task update (){
        return db.update(post,urlEspacios);
    }

    public Task delete (){
        return db.deleteWithDatos(post,urlEspacios);
    }

    public Query getObject(){
      return db.DbRef().child(urlEspacios)
                .orderByKey()
                .equalTo(post.getKey());
    }

    public Query getAllFromEspacios(Go<Espacio> espacio) {
        return db.getAll(url.AddKey(url.getRootInEspacios(espacio),url.getRoot()));
    }

}
