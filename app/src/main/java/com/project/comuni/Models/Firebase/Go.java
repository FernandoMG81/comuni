package com.project.comuni.Models.Firebase;
import java.util.Dictionary;
// Clase generica que se genera para que los objetos tengan una clave asociada por fuera de ellos.
//Go = Generic Object
public class Go <TObject extends Object> {

        private String Key;
        private TObject Object;

        public Go()
        {
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
