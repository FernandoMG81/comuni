package com.project.comuni.Models.Firebase;
import java.lang.reflect.ParameterizedType;
import java.util.Dictionary;
import java.util.Map;

// Clase generica que se genera para que los objetos tengan una clave asociada por fuera de ellos.
//Go = Generic Object
public class Go <TObject> {

    private String Key;
    public String getKey() { return Key; }
    public void setKey(String key) { Key = key; }

    private TObject Object;
    public TObject getObject() { return Object; }
    public void setObject(TObject object) { Object = object; }

    public Go(){
    }

    public Go(TObject Object)
    {
        this.Object = Object;
    }

    public Go(String Key)
    {
        this.Key = Key;
    }

    public Go(String Key, TObject Object)
    {
        this.Key = Key;
        this.Object = Object;

    }

    public Go(Go<TObject> GObject)
    {
        this.Key = GObject.Key;
        this.Object = GObject.Object;
    }

    public Go(Map.Entry<String,TObject> GObject)
    {
        this.Key = GObject.getKey();
        this.Object = GObject.getValue();
    }

/*        public Go(FirebaseObject<TObject> Object)
    {
        Key = Object.Key;
        this.Object = new TObject();
        this.Object = Object.Object;
    }*/

    public Dictionary<String, TObject> ToDictionary(Dictionary<String, TObject> Diction) {
        Diction.put(Key, Object);
        return Diction;
    }
}
