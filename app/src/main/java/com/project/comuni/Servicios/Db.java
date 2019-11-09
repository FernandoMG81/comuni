package com.project.comuni.Servicios;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.project.comuni.Models.Firebase.Go;

public class Db<Tobject> {

    private FirebaseDatabase fDatabase;
    private DatabaseReference DbRef;

    private FirebaseStorage fStorage;
    private StorageReference StRef;

    public Db(){
        fDatabase = FirebaseDatabase.getInstance();
        DbRef = fDatabase.getReference();
    }

    public StorageReference Storage(String url){
        fStorage = FirebaseStorage.getInstance();
        StRef = fStorage.getReference(url);
        return StRef;
    }

    //Devuelve DbRef que es el punto de comienzo para hacer queries
    public DatabaseReference DbRef (){return  DbRef;}

    //Busca una url dada y crea una key y su objeto
    public Task<Void> create(Go<Tobject> obj, String url){
        return DbRef.child(url)
                .push()
                .setValue(obj.getObject());
    }

    //Busca en una url y key dada y sobreescribe el objeto
    public Task<Void> update(Go<Tobject> obj, String url){
        return DbRef
                .child(url)
                .child(obj.getKey())
                .setValue(obj.getObject());
    }

    //Busca en una url y key dada y borra el objeto
    public Task<Void> delete(Go<Tobject> obj, String url){
        return DbRef.child(url)
                .child(obj.getKey())
                .removeValue();
    }

}
