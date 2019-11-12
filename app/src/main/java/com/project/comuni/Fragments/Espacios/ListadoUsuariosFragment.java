package com.project.comuni.Fragments.Espacios;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
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
import com.project.comuni.Servicios.LoginService;
import com.project.comuni.Servicios.TagService;
import com.project.comuni.Servicios.UsuarioService;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;

public class ListadoUsuariosFragment extends Fragment {

    //Variables Datos
    private Go<Espacio> espacio = new Go<>();
    private Go<Usuario> usuario = new Go<>(new Usuario());
    private ArrayList<Go<Usuario>> usuariosNoListar = new ArrayList<>();
    private ArrayList<Go<Usuario>> usuarios = new ArrayList<>();

    //Que Hacer
    // 1 -> Agregar Admins
    // 2 -> Agregar Miembros
    private  int queHacer;

    // Layout
    private RecyclerView recyclerViewUsuarios;

    private void getData() {
        Bundle bundle = getArguments();
        this.usuario = (Go<Usuario>) bundle.getSerializable("usuario");
        this.espacio = (Go<Espacio>) bundle.getSerializable("espacioActual");
        this.usuariosNoListar = (ArrayList<Go<Usuario>>) bundle.getSerializable("listadoUsuarios");
        this.queHacer = (int) bundle.getSerializable("queHacer");
    }

    public void setLayoutReferences(View v){
        recyclerViewUsuarios = v.findViewById(R.id.RVContactos);
    }

    private void setRecyclerUsuarios() {
            recyclerViewUsuarios.setLayoutManager(new LinearLayoutManager(getContext()));
            RecyclerAdapterAgregarUsuarios adapter = new RecyclerAdapterAgregarUsuarios(
                    getContext(), espacio, usuarios, queHacer);
            recyclerViewUsuarios.setAdapter(adapter);
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
                    public void onDataChange(DataSnapshot snapshot) {
                        for (DataSnapshot x: snapshot.getChildren()) {
                            Go<Usuario> usuariox = new Go<>(
                                    x.getKey(),
                                    x.getValue(usuario.getObject().getClass()
                                    ));
                            usuarios.add(usuariox);
                        }

                        for (Go<Usuario> x:usuariosNoListar) {
                            for(Go<Usuario> y: usuarios)
                            if (x.getKey().equals(y.getKey())) {
                                usuarios.remove(y);
                                break;
                            }
                        }

                            if (usuarios.size()> 0){
                                setRecyclerUsuarios();
                            }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        usuario = null;
                    }
                });

        return view;
    }

}
