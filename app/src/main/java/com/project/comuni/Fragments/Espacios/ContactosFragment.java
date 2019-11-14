package com.project.comuni.Fragments.Espacios;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

import java.util.ArrayList;
import java.util.Map;

public class ContactosFragment extends Fragment {

    //Variables Datos
    private Boolean administrador = false;
    private Go<Espacio> espacio = new Go<>();

    private Go<Usuario> usuario = new Go<>(new Usuario());
    private ArrayList<Go<Usuario>> usuarios = new ArrayList<>();
    private ArrayList<Go<Usuario>> usuariosNoListar = new ArrayList<>();
    private ArrayList<Go<Tag>> tags = new ArrayList<>();

    //Variable que hacer
    // 1 Guardar en Administradores
    // 2 Guardar en Miembros
    private int queHacer;


    // Layout
    //Espacio
    private TextView Nombre;
    private TextView Descripcion;
    //Recyclers
    private RecyclerView recyclerUsuarios;

    private void getData() {
        Bundle bundle = getArguments();
        this.queHacer = (int) bundle.getSerializable("queHacer");
        this.usuario = (Go<Usuario>) bundle.getSerializable("usuario");
        this.usuariosNoListar = (ArrayList<Go<Usuario>>) bundle.getSerializable("usuariosNoListar");
        this.espacio = (Go<Espacio>) bundle.getSerializable("espacioActual");
    }

    public void setLayoutReferences(View v){
        Nombre = v.findViewById(R.id.ConfigPlaceNombre);
        Descripcion = v.findViewById(R.id.ConfigPlaceDescripcion);

        recyclerUsuarios = v.findViewById(R.id.RVAdministradores);


        Nombre.setText(espacio.getObject().getNombre());
        Descripcion.setText(espacio.getObject().getDescripcion());
    }

    private void setRecyclerUsuarios() {
        ArrayList<Go<Usuario>> usuarios = new ArrayList<>();
        if (espacio.getObject().getAdministradores() != null) {
            for (Map.Entry<String, Usuario> x : espacio.getObject().getAdministradores().entrySet()) {
                usuarios.add(new Go<>(x));
            }
            recyclerUsuarios.setLayoutManager(new LinearLayoutManager(getContext()));
            RecyclerAdapterUsuarios adapter = new RecyclerAdapterUsuarios(getContext(), espacio, usuarios, administrador);
            recyclerUsuarios.setAdapter(adapter);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_usuarios, container, false);

        getData();
        setLayoutReferences(view);

        setRecyclerUsuarios();
        new UsuarioService().getAll()
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        usuarios.clear();
                        for (DataSnapshot x: snapshot.getChildren())
                        {
                            for (Go<Usuario> y:usuariosNoListar) {
                                if(y.getKey() != x.getKey()){
                                    usuarios.add(new Go<>(
                                            x.getKey(),
                                            x.getValue(usuario.getObject().getClass())));
                                    break;
                                }
                            }
                            if (usuarios.size()>0){
                                setRecyclerUsuarios();
                            }
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
