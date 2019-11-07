package com.project.comuni.Servicios;

import android.view.View;

import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.comuni.Models.Firebase.Go;
import com.project.comuni.Models.Usuario;

public class Db<Tobject> {

    private FirebaseDatabase firebase;
    private DatabaseReference DbRef;

    public Db(View v){
        FirebaseApp.initializeApp(v.getContext());
        firebase = FirebaseDatabase.getInstance();
        DbRef = firebase.getReference();
    }

    //Devuelve DbRef que es el punto de comienzo para hacer queries
    public DatabaseReference DbRef (){return  DbRef;}

    //Busca una url dada y crea una key y su objeto
    public Boolean create(Go<Tobject> obj, String url){
        return DbRef.child(url)
                .push()
                .setValue(obj.getObject())
                .isSuccessful();
    }

    //Busca en una url y key dada y sobreescribe el objeto
    public Boolean update(Go<Tobject> obj, String url){
        return DbRef
                .child(url)
                .child(obj.getKey())
                .setValue(obj.getObject())
                .isSuccessful();
    }

    //Busca en una url y key dada y borra el objeto
    public Boolean delete(Go<Tobject> obj, String url){
        return DbRef.child(url)
                .child(obj.getKey())
                .removeValue()
                .isSuccessful();
    }

}
