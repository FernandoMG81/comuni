package com.project.comuni.Fragments;


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
import com.project.comuni.Servicios.UsuarioService;
import com.project.comuni.Utils.FireUrl;

import java.util.HashMap;
import java.util.Map;

public class EditUsuarioFragment extends Fragment {

    //Db
    private FirebaseStorage dbF;

    //Variables
    private Go<Usuario> usuario = new Go<>(new Usuario());
    private Go<Usuario> usuarioAGuardar = new Go<>(new Usuario());

    //Variables
    // 1 -> Modificar Datos
    // 2 -> Modificar Email
    // 3 -> Modificar Contrasena
    private int queHacer;

    //Layout
    //Edit Texts
    private TextView Nombre;
    private TextView Apellido;
    private TextView Fecha;
    private TextView Email;
    private TextView Contrasena;
    private TextView Contrasena2;
    //Buttons
    private Button submitDatos;
    private Button submitEmail;
    private Button submitContrasena;

    private void getData() {
        Bundle bundle = getArguments();
        this.usuario = (Go<Usuario>) bundle.getSerializable("usuario");
        this.queHacer = (int) bundle.getSerializable("queHacer");
    }

    private void setLayoutReference(View view){
        Nombre = view.findViewById(R.id.EditUsuarioNombre);
        Apellido = view.findViewById(R.id.EditUsuarioApellido);
        Fecha = view.findViewById(R.id.EditUsuarioFecha);
        Email = view.findViewById(R.id.EditUsuarioEmail);
        Contrasena = view.findViewById(R.id.EditUsuarioContrasena);
        Contrasena2 = view.findViewById(R.id.EditUsuarioContrasena2);

        submitDatos = view.findViewById(R.id.EditUsuarioSubmitDatos);
        submitEmail = view.findViewById(R.id.EditUsuarioSubmitEmail);
        submitContrasena = view.findViewById(R.id.EditUsuarioSubmitContrasena);

        Nombre.setText(usuario.getObject().getNombre());
        Apellido.setText(usuario.getObject().getApellido());
        //Fecha.setText(usuario.getObject().getApellido());
        Email.setText(usuario.getObject().getEmail());

        //Borrar de pantala lo que no corresponde al formulario actual
        if(queHacer != 1){
            Nombre.setVisibility(View.GONE);
            Apellido.setVisibility(View.GONE);
            Fecha.setVisibility(View.GONE);
            submitDatos.setVisibility(View.GONE);
        }
        if(queHacer != 2){
            Email.setVisibility(View.GONE);
            submitEmail.setVisibility(View.GONE);
        }
        if(queHacer!= 3){
            Contrasena.setVisibility(View.GONE);
            Contrasena2.setVisibility(View.GONE);
            submitContrasena.setVisibility(View.GONE);
        }

    }

    private void cuestionarioAObjeto(){

        //Modificar Datos Personales
        if(queHacer == 1) {
            usuarioAGuardar = new Go<>(usuario.getKey(),usuario.getObject());
            usuarioAGuardar.getObject().setNombre(Nombre.getText().toString());
            usuarioAGuardar.getObject().setApellido(Apellido.getText().toString());
        }

        //Modificar Email
        else if(queHacer == 2){
            usuarioAGuardar.getObject().setEmail(Email.getText().toString());
        }
    }

    private void setBotonEditarDatos(){
        submitDatos.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                cuestionarioAObjeto();
                if(!usuarioAGuardar.getObject().validarDatos().equals("Ok")){
                    Toast.makeText(getContext(), usuarioAGuardar.getObject().validarDatos(), Toast.LENGTH_SHORT).show();
                }
                else{
                    new UsuarioService(usuarioAGuardar).update()
                            .addOnCompleteListener(getActivity(), new OnCompleteListener() {
                                @Override
                                public void onComplete(@NonNull Task task) {
                                    if (!task.isSuccessful()) {
                                        Toast.makeText(getContext(), "Ocurrio un error", Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(getContext(), "Contraseña Actualizada", Toast.LENGTH_LONG).show();
                                        goToPerfil();
                                    }
                                }
                            });
                }
            }
        });
    }

    private void setBotonEditarEmail(){
        submitDatos.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                cuestionarioAObjeto();
                if (usuarioAGuardar.getObject().validarEmail().equals("Ok")){
                    Toast.makeText(getContext(), usuarioAGuardar.getObject().validarEmail(), Toast.LENGTH_SHORT).show();
                }
                else{
                    new UsuarioService(usuarioAGuardar).update()
                            .addOnCompleteListener(getActivity(), new OnCompleteListener() {
                                @Override
                                public void onComplete(@NonNull Task task) {
                                    if (!task.isSuccessful()) {
                                        Toast.makeText(getContext(), "Ocurrio un error", Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(getContext(), "Contraseña Actualizada", Toast.LENGTH_LONG).show();
                                        goToPerfil();
                                    }
                                }
                            });
                }
            }
        });
    }

    private void setBotonEditarContraseña(){
        submitDatos.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                cuestionarioAObjeto();
                String validacion = usuario.getObject().validarContrasenas(Contrasena.getText().toString(), Contrasena2.getText().toString());

                if (validacion.equals("Ok"))
                {
                    Toast.makeText(getContext(), validacion, Toast.LENGTH_SHORT).show();
                }
                else{
                    new UsuarioService(usuarioAGuardar).update()
                            .addOnCompleteListener(getActivity(), new OnCompleteListener() {
                                @Override
                                public void onComplete(@NonNull Task task) {
                                    if (!task.isSuccessful()) {
                                        Toast.makeText(getContext(), "Ocurrio un error", Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(getContext(), "Contraseña Actualizada", Toast.LENGTH_LONG).show();
                                        goToPerfil();
                                    }
                                }
                            });
                }
            }
        });
    }

    private void goToPerfil(){
        AppCompatActivity activity = (MainActivity) this.getContext();
        Fragment myFragment = new ConfigPlaceFragment();
        Bundle args = new Bundle();
        args.putSerializable("usuario",usuario);
        myFragment.setArguments(args);
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, myFragment).addToBackStack(null).commit();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_usuario, container, false);

        getData();
        setLayoutReference(view);
        setBotonEditarDatos();
        setBotonEditarEmail();
        setBotonEditarContraseña();

        return view;
    }

}
