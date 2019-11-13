package com.project.comuni.Servicios;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.project.comuni.Models.Firebase.Go;
import com.project.comuni.Utils.FireUrl;

import java.util.HashMap;
import java.util.Map;

public class Db<Tobject> {

    private FirebaseDatabase fDatabase;
    private DatabaseReference DbRef;

    private FirebaseStorage fStorage;
    private StorageReference StRef;

    private FireUrl fireUrl = new FireUrl();

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

    //Busca una url dada y crea una key
    public String createKey(String url){
        return DbRef.child(url)
                .push().getKey();
    }


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

    //Busca en una url y key dada y sobreescribe el objeto
    public Task<Void> updateWithDatos(Go<Tobject> obj, String url){
        return DbRef
                .child(url)
                .child(obj.getKey())
                .child(fireUrl.getDatos())
                .setValue(obj.getObject());
    }

    //Busca en una url y key dada y sobreescribe el objeto
    public Task<Void> updateWithChildren(Go<Tobject> obj, String url){
        Map<String, Tobject> mapObj = new HashMap<>();
        mapObj.put(obj.getKey(), obj.getObject());
        return DbRef
                .child(url)
                .child(obj.getKey())
                .updateChildren((Map<String, Object>) mapObj);
    }

    //Busca en una url y key dada y borra el objeto
    public Task<Void> delete(Go<Tobject> obj, String url){
        return DbRef.child(url)
                .child(obj.getKey())
                .removeValue();
    }

    public Task<Void> deleteWithDatos(Go<Tobject> obj, String url){
        return DbRef.child(url)
                .child(obj.getKey())
                .removeValue();
    }

    public Query getObject(String key, String url){
        return DbRef.child(url)
                .orderByKey()
                .equalTo(key);
    }

    public Query getAll(String url){
        return DbRef.child(url);
    }

    public Query getAllWithDatos(String url){
        return DbRef.child(fireUrl.AddKey(url,fireUrl.getDatos()));
    }

}
