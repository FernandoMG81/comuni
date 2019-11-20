package com.project.comuni.Fragments.Espacios;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

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
import com.project.comuni.Adapters.Espacios.RecyclerAdapterAgregarUsuarios;
import com.project.comuni.Adapters.Espacios.RecyclerAdapterTags;
import com.project.comuni.Adapters.Espacios.RecyclerAdapterUsuarios;
import com.project.comuni.Models.Espacio;
import com.project.comuni.Models.Firebase.Go;
import com.project.comuni.Models.Tag;
import com.project.comuni.Models.Usuario;
import com.project.comuni.R;
import com.project.comuni.Servicios.EspacioService;
import com.project.comuni.Servicios.LoginService;
import com.project.comuni.Servicios.TagService;
import com.project.comuni.Servicios.UsuarioService;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;

import static com.project.comuni.Utils.Util.filtrarString;

public class ListadoUsuariosFragment extends Fragment {

    //Variables Datos
    private Go<Espacio> espacio = new Go<>();
    private Go<Usuario> usuario = new Go<>(new Usuario());
    private ArrayList<Go<Usuario>> usuariosNoListar = new ArrayList<>();
    private ArrayList<Go<Usuario>> usuarios = new ArrayList<>();
    private ArrayList<Go<Usuario>> usuariosAMostrar = new ArrayList<>();

    //Variables Filtrado
    private String searchText = "";

    //Que Hacer
    // 1 -> Agregar Admins
    // 2 -> Agregar Miembros
    private  int queHacer;

    // Layout
    private TextView vacio;
    private EditText search;
    private RecyclerView recyclerViewUsuarios;
   // private ProgressBar progressBar;

    private void getData() {
        Bundle bundle = getArguments();
        this.usuario = (Go<Usuario>) bundle.getSerializable("usuario");
        this.espacio = (Go<Espacio>) bundle.getSerializable("espacioActual");
        this.usuariosNoListar = (ArrayList<Go<Usuario>>) bundle.getSerializable("listadoUsuarios");
        this.queHacer = (int) bundle.getSerializable("queHacer");
    }

    public void setLayoutReferences(View v){
        vacio = v.findViewById(R.id.ContactosTextoVacio);
        vacio.setVisibility(View.GONE);
        search = v.findViewById(R.id.ContactosSearch);
        recyclerViewUsuarios = v.findViewById(R.id.RVContactos);
        //progressBar = v.findViewById(R.id.listadoUsuarios);
     //   progressBar.setVisibility(View.VISIBLE);
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
            RecyclerAdapterAgregarUsuarios adapter = new RecyclerAdapterAgregarUsuarios(
                    getContext(), espacio, usuariosAMostrar, queHacer);
            recyclerViewUsuarios.setAdapter(adapter);
    }

    private void setUsuariosNoListar(){
        Go<Usuario> aux;
        if(queHacer == 1){
            for ( Map.Entry <String,Usuario> x : espacio.getObject().getAdministradores().entrySet())
            {
                aux = new Go<>(x.getKey(), x.getValue());
                usuariosNoListar.add(aux);
            }
        }
        else{
            for ( Map.Entry <String,Usuario> x : espacio.getObject().getMiembros().entrySet())
            {
                aux = new Go<>(x.getKey(), x.getValue());
                usuariosNoListar.add(aux);
            }
        }
        for (Go<Usuario> x : usuariosNoListar) {
            for (Go<Usuario> y : usuarios)
                if (x.getKey().equals(y.getKey())) {
                    usuarios.remove(y);
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

                        new EspacioService(espacio).getObject()
                                .addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }

                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        usuariosNoListar.clear();
                                        for (DataSnapshot x : dataSnapshot.getChildren()) {
                                            espacio.setObject(x.getValue(espacio.getObject().getClass()));
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
                                      //  progressBar.setVisibility(View.INVISIBLE);
                                    }

                                });
                    }
                });

        return view;
    }

}
