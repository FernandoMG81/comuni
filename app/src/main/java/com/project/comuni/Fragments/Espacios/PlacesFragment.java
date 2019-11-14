package com.project.comuni.Fragments.Espacios;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.project.comuni.Activities.MainActivity;
import com.project.comuni.Adapters.Espacios.RecyclerAdapterPlaces;
import com.project.comuni.Adapters.Espacios.RecyclerAdapterPosts;
import com.project.comuni.Models.Espacio;
import com.project.comuni.Models.Firebase.Go;
import com.project.comuni.Models.Post;
import com.project.comuni.Models.Usuario;
import com.project.comuni.R;
import com.project.comuni.Servicios.EspacioService;
import com.project.comuni.Servicios.LoginService;
import com.project.comuni.Servicios.PostService;
import com.project.comuni.Servicios.UsuarioService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.project.comuni.Utils.Util.filtrarString;

public class PlacesFragment extends Fragment {

    //Variables Datos
    private ArrayList<Go<Espacio>> espacios = new ArrayList<>();
    private ArrayList<Go<Espacio>> espaciosAMostrar = new ArrayList<>();
    private Go<Usuario> usuario = new Go<>(new Usuario());

    //Variables Filtrado
    private String searchText = "";

    // Layout
    private EditText search;
    private RecyclerView recyclerView;


    public void setLayoutReferences(View v){
        search = v.findViewById(R.id.NewsSearch);
        recyclerView = v.findViewById(R.id.RVPlaces);
    }

    private void setSearch(){
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                searchText = editable.toString();
                filterData();
                setRecycler();
            }
        });
    }

    private void filterData(){

        espaciosAMostrar.clear();
        for (Go<Espacio> x: espacios){
            if (filtrarString(x.getObject().getNombre(), searchText) ||
                    filtrarString (x.getObject().getDescripcion(), searchText))
            {
                espaciosAMostrar.add(x);

            }
        }
    }

    private void setRecycler(){
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        RecyclerAdapterPlaces adapter = new RecyclerAdapterPlaces(getContext(), espaciosAMostrar, usuario);
        recyclerView.setAdapter(adapter);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_places, container, false);

        setLayoutReferences(view);
        usuario = new LoginService().getGoUser();
        new UsuarioService(usuario).getObject()
                .addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot x: dataSnapshot.getChildren()) {
                            usuario.setObject((x.getValue(usuario.getObject().getClass())));
                        }

                        new EspacioService().getAllFromUsuario(usuario)
                                .addValueEventListener(
                                        new ValueEventListener() {

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {

                                            }

                                            @Override
                                            public void onDataChange(DataSnapshot snapshot) {
                                                for (DataSnapshot x : snapshot.getChildren()) {
                                                    Go<Espacio> aux = new Go<>(new Espacio());
                                                    aux.setKey(x.getKey());
                                                    aux.setObject(x.getValue(aux.getObject().getClass()));
                                                   espacios.add(aux);
                                                }

                                                setSearch();
                                                if (espacios.size() > 0) {
                                                    filterData();
                                                    setRecycler();
                                                }

                                            }
                                        });
                    }
                });
        return view;
    }


    //Aaaaaleluya Aleluuya aleluuuuuuuyaaa!
    //Cantada en Do mayor
    public void salvameJebus(){
        Go<Espacio> espacio = new Go<>("-LtMMd1njIO_i3WAAPr9",new Espacio());
        espacio.getObject().getAdministradores().put(usuario.getKey(),usuario.getObject());
        espacio.getObject().setNombre("TSP");
        espacio.getObject().setDescripcion("Tecnicatura Superior en Programacion");
        new EspacioService(espacio).update();
    }

    @Override
    public void onResume() {
        super.onResume();
        espacios.clear();
        espaciosAMostrar.clear();
    }

}
