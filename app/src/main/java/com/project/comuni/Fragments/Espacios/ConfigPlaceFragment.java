package com.project.comuni.Fragments.Espacios;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.project.comuni.Fragments.Espacios.ABMs.CreateEspacioFragment;
import com.project.comuni.Fragments.Espacios.ABMs.CreateTagFragment;
import com.project.comuni.Models.Espacio;
import com.project.comuni.Models.Firebase.Go;
import com.project.comuni.Models.Tag;
import com.project.comuni.Models.Usuario;
import com.project.comuni.R;
import com.project.comuni.Servicios.EspacioService;
import com.project.comuni.Servicios.LoginService;
import com.project.comuni.Servicios.TagService;
import com.project.comuni.Servicios.UsuarioService;

import java.util.ArrayList;
import java.util.Map;

public class ConfigPlaceFragment extends Fragment {

    //Variables Datos
    private Boolean administrador = false;
    private Go<Espacio> espacio = new Go<>();
    private Go<Usuario> usuario = new Go<>(new Usuario());
    private ArrayList<Go<Tag>> tags = new ArrayList<>();

    // Layout
    //Espacio
    private TextView Nombre;
    private TextView Descripcion;
    //Desplegar
    private LinearLayout LLAdmins;
    private LinearLayout LLMiembros;
    private LinearLayout LLTags;
    //TextBox Mensaje vacio
    private TextView AdminsVacio;
    private TextView MiembrosVacio;
    private TextView TagsVacio;
    //Recyclers
    private RecyclerView recyclerViewAdmins;
    private RecyclerView recyclerViewMiembros;
    private RecyclerView recyclerViewTags;
    //Buttons
    private LinearLayout LLBotones;
    private Button AgregarEspacioButton;
    private Button EditarEspacioButton;
    private Button AgregarAdminButton;
    private Button AgregarMiembroButton;
    private Button AgregarTagButton;

    private void getData() {
        Bundle bundle = getArguments();
        this.usuario = (Go<Usuario>) bundle.getSerializable("usuario");
        this.espacio = (Go<Espacio>) bundle.getSerializable("espacioActual");
    }

    public void setLayoutReferences(View v){
        Nombre = v.findViewById(R.id.ConfigPlaceNombre);
        Descripcion = v.findViewById(R.id.ConfigPlaceDescripcion);

        AdminsVacio =v.findViewById(R.id.ConfigAdminVacio);
        AdminsVacio.setVisibility(View.GONE);
        MiembrosVacio =v.findViewById(R.id.configMiembrosVacio);
        MiembrosVacio.setVisibility(View.GONE);
        TagsVacio =v.findViewById(R.id.configTagsVacio);
        TagsVacio.setVisibility(View.GONE);

        LLAdmins =v.findViewById(R.id.LLConfigAdmin);
        LLMiembros =v.findViewById(R.id.LLConfigMiembros);
        LLTags =v.findViewById(R.id.LLConfigTags);

        recyclerViewAdmins = v.findViewById(R.id.RVAdministradores);
        recyclerViewAdmins.setVisibility(View.GONE);
        recyclerViewMiembros = v.findViewById(R.id.RVMiembros);
        recyclerViewMiembros.setVisibility(View.GONE);
        recyclerViewTags = v.findViewById(R.id.RVTags);
        recyclerViewTags.setVisibility(View.GONE);

        LLBotones = v.findViewById(R.id.ConfigPlaceBotonesEspacio);
        LLBotones.setVisibility(View.GONE);
        AgregarEspacioButton = v.findViewById(R.id.AgregarEspacio);
        AgregarEspacioButton.setVisibility(View.GONE);
        EditarEspacioButton = v.findViewById(R.id.EditarEspacio);
        EditarEspacioButton.setVisibility(View.GONE);
        AgregarAdminButton = v.findViewById(R.id.AgregarAdministrador);
        AgregarAdminButton.setVisibility(View.INVISIBLE);
        AgregarMiembroButton = v.findViewById(R.id.AgregarMiembro);
        AgregarMiembroButton.setVisibility(View.INVISIBLE);
        AgregarTagButton = v.findViewById(R.id.AgregarTag);
        AgregarTagButton.setVisibility(View.INVISIBLE);

        Nombre.setText(espacio.getObject().getNombre());
        Descripcion.setText(espacio.getObject().getDescripcion());
    }

    private void setDesplegarAdmins(){
        LLAdmins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (recyclerViewAdmins.getVisibility() == View.GONE) {
                    recyclerViewAdmins.setVisibility(View.VISIBLE);
                    if(espacio.getObject().getAdministradores() != null){
                        if(espacio.getObject().getAdministradores().size()==0){
                            AdminsVacio.setVisibility(View.VISIBLE);
                        }
                        else{
                            AdminsVacio.setVisibility(View.GONE);
                        }
                    }
                    else{
                        AdminsVacio.setVisibility(View.VISIBLE);
                    }

                }
                else
                {
                    recyclerViewAdmins.setVisibility(View.GONE);
                    AdminsVacio.setVisibility(View.GONE);
                }
            }
        });
    }

    private void setDesplegarMiembros(){
        LLMiembros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (recyclerViewMiembros.getVisibility() == View.GONE) {
                    recyclerViewMiembros.setVisibility(View.VISIBLE);
                    if(espacio.getObject().getMiembros() != null){
                        if(espacio.getObject().getMiembros().size()==0){
                            MiembrosVacio.setVisibility(View.VISIBLE);
                        }
                        else{
                            MiembrosVacio.setVisibility(View.GONE);
                        }
                    }
                    else{
                        MiembrosVacio.setVisibility(View.VISIBLE);
                    }
                }
                else
                {
                    recyclerViewMiembros.setVisibility(View.GONE);
                    MiembrosVacio.setVisibility(View.GONE);
                }
            }
        });
    }

    private void setDesplegarTags(){
        LLTags.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (recyclerViewTags.getVisibility() == View.GONE) {
                    recyclerViewTags.setVisibility(View.VISIBLE);
                    if(tags.size() == 0){
                        TagsVacio.setVisibility(View.VISIBLE);
                    }
                }
                else
                {
                    recyclerViewTags.setVisibility(View.GONE);
                    TagsVacio.setVisibility(View.GONE);
                }
            }
        });
    }

    private void setRecyclerAdmin() {
        ArrayList<Go<Usuario>> usuarios = new ArrayList<>();
        if (espacio.getObject().getAdministradores() != null) {
            for (Map.Entry<String, Usuario> x : espacio.getObject().getAdministradores().entrySet()) {
                usuarios.add(new Go<>(x));
            }
            recyclerViewAdmins.setLayoutManager(new LinearLayoutManager(getContext()));
            RecyclerAdapterUsuarios adapter = new RecyclerAdapterUsuarios(getContext(), espacio, usuarios, administrador,1);
            recyclerViewAdmins.setAdapter(adapter);
        }
    }

    private void setRecyclerMiembros(){
        ArrayList<Go<Usuario>> usuarios = new ArrayList<>();
        if(espacio.getObject().getMiembros()!= null) {
            for (Map.Entry<String, Usuario> x : espacio.getObject().getMiembros().entrySet()) {
                usuarios.add(new Go<>(x));
            }
            recyclerViewMiembros.setLayoutManager(new LinearLayoutManager(getContext()));
            RecyclerAdapterUsuarios adapter = new RecyclerAdapterUsuarios(getContext(), espacio, usuarios, administrador,2);
            recyclerViewMiembros.setAdapter(adapter);
        }
    }

    private void setRecyclerTags(){
        recyclerViewTags.setLayoutManager(new LinearLayoutManager(getContext()));
        RecyclerAdapterTags adapter = new RecyclerAdapterTags(getContext(),espacio, usuario, tags,administrador);
        recyclerViewTags.setAdapter(adapter);
    }

    private void setAgregarEspacioButton(){
        AgregarEspacioButton.setVisibility(View.VISIBLE);
        AgregarEspacioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (MainActivity) view.getContext();
                Fragment myFragment = new CreateEspacioFragment();
                Bundle args = new Bundle();
                args.putSerializable("queHacer", 1);
                args.putSerializable("espacioActual", espacio);
                args.putSerializable("usuario",usuario);
                myFragment.setArguments(args);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, myFragment).addToBackStack(null).commit();
            }
        });
    }

    private void setEditEspacioButton(){
        EditarEspacioButton.setVisibility(View.VISIBLE);
        EditarEspacioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (MainActivity) view.getContext();
                Fragment myFragment = new CreateEspacioFragment();
                Bundle args = new Bundle();
                args.putSerializable("queHacer", 2);
                args.putSerializable("espacioActual", espacio);
                args.putSerializable("usuario",usuario);
                myFragment.setArguments(args);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, myFragment).addToBackStack(null).commit();
            }
        });
    }

    private void setAddAdminButton(){
        AgregarAdminButton.setVisibility(View.VISIBLE);
        AgregarAdminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Go<Usuario>> usuarios = new ArrayList<>();
                if(espacio.getObject().getAdministradores()!=null) {
                    for (Map.Entry<String, Usuario> x : espacio.getObject().getAdministradores().entrySet()) {
                        usuarios.add(new Go<>(x.getKey(), x.getValue()));
                    }

                    AppCompatActivity activity = (MainActivity) view.getContext();
                    Fragment myFragment = new ListadoUsuariosFragment();
                    Bundle args = new Bundle();
                    args.putSerializable("espacioActual", espacio);
                    args.putSerializable("usuario", usuario);
                    args.putSerializable("listadoUsuarios", usuarios);
                    args.putSerializable("queHacer", 1);
                    myFragment.setArguments(args);
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, myFragment).addToBackStack(null).commit();
                }
                else{
                    Toast.makeText(getContext(), "Ocurrió un error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setAddMiembroButton(){
        AgregarMiembroButton.setVisibility(View.VISIBLE);
        AgregarMiembroButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Go<Usuario>> usuarios = new ArrayList<>();
                if(espacio.getObject().getMiembros()!=null) {
                    for (Map.Entry<String, Usuario> x : espacio.getObject().getMiembros().entrySet()) {
                        usuarios.add(new Go<>(x.getKey(), x.getValue()));
                    }
                }

                    AppCompatActivity activity = (MainActivity) view.getContext();
                    Fragment myFragment = new ListadoUsuariosFragment();
                    Bundle args = new Bundle();
                    args.putSerializable("espacioActual", espacio);
                    args.putSerializable("usuario", usuario);
                    args.putSerializable("listadoUsuarios", usuarios);
                    args.putSerializable("queHacer", 2);
                    myFragment.setArguments(args);
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, myFragment).addToBackStack(null).commit();


            }
        });
    }

    private void setAddTagButton(){
        AgregarTagButton.setVisibility(View.VISIBLE);
        AgregarTagButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (MainActivity) view.getContext();
                Fragment myFragment = new CreateTagFragment();
                Bundle args = new Bundle();
                args.putSerializable("espacioActual", espacio);
                args.putSerializable("usuario",usuario);
                myFragment.setArguments(args);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, myFragment).addToBackStack(null).commit();
            }
        });
    }

    private void getTags(){
        new TagService().getAllFromEspacios(espacio)
                .addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }

                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        tags.clear();
                        for (DataSnapshot x : snapshot.getChildren()) {
                            Go<Tag> tagx = new Go<>(new Tag());
                            tagx.setKey(x.getKey());
                            tagx.setObject(x.getValue(tagx.getObject().getClass()));
                            tags.add(tagx);
                        }
                        if (tags.size()>0) {
                            setRecyclerTags();
                        }
                    }

                });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_config_espacio, container, false);

        getData();
        setLayoutReferences(view);
        setDesplegarAdmins();
        setDesplegarMiembros();
        setDesplegarTags();

        for (Map.Entry<String,Espacio> x :usuario.getObject().getAdministradores().entrySet())
        {
            if(x.getKey().equals(espacio.getKey())){
                administrador = true;
                LLBotones.setVisibility(View.VISIBLE);
                setAgregarEspacioButton();
                setEditEspacioButton();
                setAddAdminButton();
                setAddMiembroButton();
                setAddTagButton();

                break;
            }
        }

        setRecyclerAdmin();
        setRecyclerMiembros();

        usuario = new LoginService().getGoUser();
        new UsuarioService(usuario).getObject()
                .addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot x: dataSnapshot.getChildren()) {
                            usuario.setObject((x.getValue(usuario.getObject().getClass())));
                        }
                            for (Map.Entry<String,Espacio> x :usuario.getObject().getAdministradores().entrySet())
                            {
                                if(x.getKey().equals(espacio.getKey())){
                                    LLBotones.setVisibility(View.VISIBLE);
                                    administrador = true;
                                    setAgregarEspacioButton();
                                    setEditEspacioButton();
                                    setAddAdminButton();
                                    setAddMiembroButton();
                                    setAddTagButton();

                                    break;
                                }
                                getTags();
                                setRecyclerAdmin();
                                setRecyclerMiembros();
                            }

                    }
                });
        return view;
    }

}
