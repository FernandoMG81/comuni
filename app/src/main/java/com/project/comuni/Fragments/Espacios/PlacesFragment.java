package com.project.comuni.Fragments.Espacios;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.project.comuni.Adapters.Espacios.RecyclerAdapterPlaces;
import com.project.comuni.Models.Espacio;
import com.project.comuni.Models.Firebase.Go;
import com.project.comuni.Models.Usuario;
import com.project.comuni.R;
import com.project.comuni.Servicios.EspacioService;
import com.project.comuni.Servicios.LoginService;
import com.project.comuni.Servicios.UsuarioService;

import java.util.ArrayList;

import static com.project.comuni.Utils.Util.filtrarString;

public class PlacesFragment extends Fragment {

    //Variables Datos
    private ArrayList<Go<Espacio>> espacios = new ArrayList<>();
    private ArrayList<Go<Espacio>> espaciosAMostrar = new ArrayList<>();
    private Go<Usuario> usuario = new Go<>(new Usuario());

    //Variables Filtrado
    private String searchText = "";

    // Layout
    private TextView vacio;
    private EditText search;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;


    public void setLayoutReferences(View v){
        vacio = v.findViewById(R.id.placesTextVacio);
        search = v.findViewById(R.id.postNewsSearch);
        recyclerView = v.findViewById(R.id.placesRV);
        progressBar = v.findViewById(R.id.placesProgressBar);
        progressBar.setVisibility(View.VISIBLE);

        vacio.setVisibility(View.GONE);
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
                        espacios.clear();


                        for (DataSnapshot x: dataSnapshot.getChildren()) {
                            usuario.setObject((x.getValue(usuario.getObject().getClass())));
                        }

                        //salvameJebus();

                        espacios = usuario.getObject().returnAllEspacios();
                        progressBar.setVisibility(View.INVISIBLE);
                        setSearch();
                        if (espacios.size() > 0) {
                            filterData();
                            setRecycler();
                            vacio.setVisibility(View.GONE);

                        }
                        else{
                            vacio.setVisibility(View.VISIBLE);
                        }

                    }
                });
        return view;
    }


    //Aaaaaleluya Aleluuya aleluuuuuuuyaaa!
    //Cantada en Do mayor
    public void salvameJebus(){
        Go<Espacio> espacio = new Go<>("-RQ1IqFaJlgbJhRdOuXqumwn3EAv2",new Espacio());
        espacio.getObject().getAdministradores().put(usuario.getKey(),usuario.getObject());
        espacio.getObject().setNombre("TSP");
        espacio.getObject().setDescripcion("Tecnicatura Superior en Programacion");
        new EspacioService(espacio).create();
    }

    @Override
    public void onResume() {
        super.onResume();
        espacios.clear();
        espaciosAMostrar.clear();
    }

}
