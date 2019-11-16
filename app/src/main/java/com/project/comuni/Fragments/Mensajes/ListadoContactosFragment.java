package com.project.comuni.Fragments.Mensajes;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.project.comuni.Adapters.Espacios.RecyclerAdapterAgregarUsuarios;
import com.project.comuni.Adapters.Mensajes.RecyclerAdapterContactos;
import com.project.comuni.Models.Espacio;
import com.project.comuni.Models.Firebase.Go;
import com.project.comuni.Models.Usuario;
import com.project.comuni.R;
import com.project.comuni.Servicios.EspacioService;
import com.project.comuni.Servicios.UsuarioService;

import java.util.ArrayList;
import java.util.Map;

import static com.project.comuni.Utils.Util.filtrarString;

public class ListadoContactosFragment extends Fragment {

    //Variables Datos
    private Go<Usuario> usuario = new Go<>(new Usuario());
    private ArrayList<Go<Usuario>> usuarios = new ArrayList<>();
    private ArrayList<Go<Usuario>> usuariosAMostrar = new ArrayList<>();

    //Variables Filtrado
    private String searchText = "";

    // Layout
    private TextView vacio;
    private EditText search;
    private RecyclerView recyclerViewUsuarios;

    private void getData() {
        Bundle bundle = getArguments();
        this.usuario = (Go<Usuario>) bundle.getSerializable("usuario");
    }

    public void setLayoutReferences(View v){
        vacio = v.findViewById(R.id.ContactosTextoVacio);
        vacio.setVisibility(View.GONE);
        search = v.findViewById(R.id.ContactosSearch);
        recyclerViewUsuarios = v.findViewById(R.id.RVContactos);
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
                setRecyclerUsuarios();
            }
        });
    }

    private void filterData(){

        usuariosAMostrar.clear();
        for (Go<Usuario> x: usuarios){
            //||
            //                    filtrarString (x.getObject().getApellido(), searchText)
            if (filtrarString(x.getObject().getNombre(), searchText) )
            {
                usuariosAMostrar.add(x);
            }
        }
    }

    private void setRecyclerUsuarios() {
            recyclerViewUsuarios.setLayoutManager(new LinearLayoutManager(getContext()));
            RecyclerAdapterContactos adapter = new RecyclerAdapterContactos(
                    getContext(), usuariosAMostrar);
            recyclerViewUsuarios.setAdapter(adapter);
    }

    private void setUsuariosNoListar() {
        for (Go<Usuario> x : usuarios) {
            if (x.getKey().equals(usuario.getKey())) {
                usuarios.remove(x);
                break;
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contactos, container, false);

        getData();
        setLayoutReferences(view);

        new UsuarioService().getAll()
                .addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        usuario = null;
                    }

                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        usuarios.clear();
                        for (DataSnapshot x : snapshot.getChildren()) {
                            Go<Usuario> usuariox = new Go<>(
                                    x.getKey(),
                                    x.getValue(usuario.getObject().getClass()
                                    ));
                            usuarios.add(usuariox);
                        }

                        setUsuariosNoListar();

                        if (usuarios.size() > 0) {
                            setSearch();
                            filterData();
                            setRecyclerUsuarios();
                            vacio.setVisibility(View.GONE);
                        } else {
                            vacio.setVisibility(View.VISIBLE);
                        }
                    }
                });

        return view;
    }

}
