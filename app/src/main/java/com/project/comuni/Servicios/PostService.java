package com.project.comuni.Servicios;

import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
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

    public boolean create (){
        return db.create(post,urlEspacios);
    }

    public boolean update (){
        return db.update(post,urlEspacios);
    }

    public boolean delete (){
        return db.delete(post,urlEspacios);
    }

    public Go<Post> getObject(){
        db.DbRef().child(urlEspacios)
                .orderByKey()
                .equalTo(post.getKey())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        post.setKey(snapshot.getKey());
                        post.setObject(snapshot.getValue(post.getObject().getClass()));
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        post = null;
                    }
                });
        return post;
    }

    private ArrayList<Go<Post>> getAllFrom(String url){
        ArrayList<Go<Post>> posts = new ArrayList<>();
        db.DbRef().child(url)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        for (DataSnapshot x: snapshot.getChildren()) {
                            post.setKey(snapshot.getKey());
                            post.setObject(snapshot.getValue(post.getObject().getClass()));
                            posts.add(post);
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        post = null;
                    }
                });
        return posts;
    }

    public ArrayList<Go<Post>> getAll(){
        return getAllFrom(urlEspacios);
    }


}
