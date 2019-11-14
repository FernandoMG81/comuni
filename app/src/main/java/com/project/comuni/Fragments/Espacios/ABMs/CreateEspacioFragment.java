package com.project.comuni.Fragments.Espacios.ABMs;


import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.project.comuni.Activities.MainActivity;
import com.project.comuni.Fragments.Espacios.ConfigPlaceFragment;
import com.project.comuni.Models.Espacio;
import com.project.comuni.Models.Firebase.Go;
import com.project.comuni.Models.Usuario;
import com.project.comuni.R;
import com.project.comuni.Servicios.EspacioService;
import com.project.comuni.Utils.FireUrl;

import java.util.HashMap;
import java.util.Map;

public class CreateEspacioFragment extends Fragment {

    //Db
    private FirebaseStorage dbF;

    //Variables
    private Go<Usuario> usuario = new Go<>(new Usuario());
    private Go<Espacio> espacio = new Go<>(new Espacio());

    //Que Hacer
    // 1 -> Crear
    // 2 -> Editar
    private  int queHacer;

    //Layout
    private TextView titulo;
    private TextView descripcion;
    private Button submit;

    private void getData() {
        Bundle bundle = getArguments();
        this.queHacer = (int) bundle.getSerializable("queHacer");
        this.usuario = (Go<Usuario>) bundle.getSerializable("usuario");
        this.espacio = (Go<Espacio>) bundle.getSerializable("espacioActual");
    }

    private void cuestionarioAObjeto(){
        espacio.getObject().setNombre(titulo.getText().toString());
        espacio.getObject().setDescripcion(descripcion.getText().toString());

        if(queHacer == 1){
            Map<String,Usuario> admin = new HashMap<>();
            admin.put(usuario.getKey(),usuario.getObject());
            espacio.getObject().setAdministradores(admin);

            espacio.getObject().setMiembros(null);

            //Setear Url de espacio
            if(espacio.getObject().getEspacioUrl()!=null){
                if(!espacio.getObject().getEspacioUrl().isEmpty()){
                    FireUrl url = new FireUrl();
                    espacio.getObject().setEspacioUrl(url.AddKey(espacio.getObject().getEspacioUrl(),espacio.getKey()));
                }
                else{
                    espacio.getObject().setEspacioUrl(espacio.getKey());
                }
            }
            else{
                espacio.getObject().setEspacioUrl(espacio.getKey());
            }
        }
    }

    private void setLayoutReference(View view){
        titulo = view.findViewById(R.id.CreateEspacioTitulo);
        descripcion = view.findViewById(R.id.CreateEspacioDescripción);

        if(queHacer==1){
            view.findViewById(R.id.EditEspacioSubmit).setVisibility(View.GONE);
            submit = view.findViewById(R.id.CreateEspacioSubmit);
        }
        else{
            titulo.setText(espacio.getObject().getNombre());
            descripcion.setText(espacio.getObject().getDescripcion());
            view.findViewById(R.id.CreateEspacioSubmit).setVisibility(View.GONE);
            submit = view.findViewById(R.id.EditEspacioSubmit);
        }

    }

    private void setBotonAgregar(){
        submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                cuestionarioAObjeto();

                if(!espacio.getObject().validar().equals("Ok")){
                    Toast.makeText(getContext(),espacio.getObject().validar(),Toast.LENGTH_LONG).show();
                }
                else{
                    new EspacioService(espacio).create()
                            .addOnCompleteListener(getActivity(), new OnCompleteListener() {
                                @Override
                                public void onComplete(@NonNull Task task) {
                                    if (!task.isSuccessful()) {
                                        Toast.makeText(getContext(), "Ocurrio un error", Toast.LENGTH_LONG).show();
                                    } else {
                                        goToEspacios();
                                    }
                                }
                            });
                }
            }
        });
    }

    private void setBotonEditar(){
        submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                cuestionarioAObjeto();

                if(!espacio.getObject().validar().equals("Ok")){
                    Toast.makeText(getContext(),espacio.getObject().validar(),Toast.LENGTH_LONG).show();
                }
                else{
                    new EspacioService(espacio).update()
                            .addOnCompleteListener(getActivity(), new OnCompleteListener() {
                                @Override
                                public void onComplete(@NonNull Task task) {
                                    if (!task.isSuccessful()) {
                                        Toast.makeText(getContext(), "Ocurrio un error", Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(getContext(), "Se editó el espacio", Toast.LENGTH_LONG).show();
                                        goToEspacios();
                                    }
                                }
                            });
                }
            }
        });
    }

    private void goToEspacios(){
        AppCompatActivity activity = (MainActivity) this.getContext();
        Fragment myFragment = new ConfigPlaceFragment();
        Bundle args = new Bundle();
        args.putSerializable("usuario",usuario);
        args.putSerializable("espacioActual",espacio);
        myFragment.setArguments(args);
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, myFragment).addToBackStack(null).commit();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_espacio, container, false);

        getData();
        setLayoutReference(view);
        if(queHacer == 1){
            setBotonAgregar();
        }
        else{
            setBotonEditar();
        }

        return view;
    }



}
