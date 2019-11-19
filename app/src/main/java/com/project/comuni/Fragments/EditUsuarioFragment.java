package com.project.comuni.Fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.storage.FirebaseStorage;
import com.project.comuni.Activities.MainActivity;
import com.project.comuni.Fragments.Espacios.ConfigPlaceFragment;
import com.project.comuni.Models.Espacio;
import com.project.comuni.Models.Firebase.Go;
import com.project.comuni.Models.Usuario;
import com.project.comuni.R;
import com.project.comuni.Servicios.EspacioService;
import com.project.comuni.Servicios.LoginService;
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
    private String emailViejo;

    //Variables
    // 1 -> Modificar Datos
    // 2 -> Modificar Email
    // 3 -> Modificar Contrasena
    private int queHacer;

    //Layout
    //Hints
    private TextInputLayout NombreHint;
    private TextInputLayout ApellidoHint;
    private TextInputLayout FechaHint;
    private TextInputLayout EmailHint;
    private TextInputLayout ContrasenaHint;
    private TextInputLayout Contrasena2Hint;
    //Edit Texts
    private TextInputEditText Nombre;
    private TextInputEditText Apellido;
    private TextInputEditText Fecha;
    private TextInputEditText Email;
    private TextInputEditText Contrasena;
    private TextInputEditText Contrasena2;
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

        NombreHint = view.findViewById(R.id.EditTextHintUsuario);
        ApellidoHint = view.findViewById(R.id.EditTextHintApellido);
        FechaHint = view.findViewById(R.id.EditTextHintUsuarioFecha);
        EmailHint = view.findViewById(R.id.EditTextHintUsuarioEmail);
        ContrasenaHint = view.findViewById(R.id.EditTextHintUsuarioContrasena);
        Contrasena2Hint = view.findViewById(R.id.EditTextHintUsuarioContrasena2);

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

        //Fecha Desactivada
        Fecha.setVisibility(View.GONE);
        FechaHint.setVisibility(View.GONE);

        //Borrar de pantala lo que no corresponde al formulario actual
        if(queHacer != 1){
            Nombre.setVisibility(View.GONE);
            Apellido.setVisibility(View.GONE);
            submitDatos.setVisibility(View.GONE);

            NombreHint.setVisibility(View.GONE);
            ApellidoHint.setVisibility(View.GONE);
        }
        if(queHacer == 1){
            Contrasena.setVisibility(View.GONE);
            ContrasenaHint.setVisibility(View.GONE);
        }
        if(queHacer != 2){
            Email.setVisibility(View.GONE);
            EmailHint.setVisibility(View.GONE);
            submitEmail.setVisibility(View.GONE);
        }
        if(queHacer!= 3){

            Contrasena2.setVisibility(View.GONE);
            Contrasena2Hint.setVisibility(View.GONE);
            submitContrasena.setVisibility(View.GONE);
        }

    }

    private void cuestionarioAObjeto(){
        usuarioAGuardar = usuario;
        //Modificar Datos Personales
        if(queHacer == 1) {
            usuarioAGuardar = new Go<>(usuario.getKey(),usuario.getObject());
            usuarioAGuardar.getObject().setNombre(Nombre.getText().toString());
            usuarioAGuardar.getObject().setApellido(Apellido.getText().toString());
        }

        //Modificar Email
        else if(queHacer == 2){
            emailViejo = usuario.getObject().getEmail();
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
                                        Toast.makeText(getContext(), "Datos Actualizados", Toast.LENGTH_LONG).show();
                                        goToPerfil();
                                    }
                                }
                            });
                }
            }
        });
    }

    private void setBotonEditarEmail(){
        submitEmail.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                cuestionarioAObjeto();
                if (!usuarioAGuardar.getObject().validarEmail().equals("Ok")){
                    Toast.makeText(getContext(), usuarioAGuardar.getObject().validarEmail(), Toast.LENGTH_SHORT).show();
                }
                else if(Contrasena.getText().toString().length() < 6)
                    {
                        Toast.makeText(getContext(), "Contraseña incorrecta.", Toast.LENGTH_SHORT).show();
                    }
                else{
                    new LoginService().changeEmail(usuarioAGuardar,emailViejo, Contrasena.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (!task.isSuccessful()){
                                        Toast.makeText(getContext(), task.getException().toString(), Toast.LENGTH_SHORT).show();
                                    }
                                    else{
                                        Toast.makeText(getContext(), "Email modificado.", Toast.LENGTH_SHORT).show();
                                        usuario = usuarioAGuardar;
                                        goToPerfil();
                                    }
                                }
                            });
                }
            }
        });
    }

    private void setBotonEditarContraseña(){
        submitContrasena.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                cuestionarioAObjeto();
                String validacion = usuario.getObject().validarContrasenas(Contrasena.getText().toString(), Contrasena2.getText().toString());

                if (!validacion.equals("Ok"))
                {
                    Toast.makeText(getContext(), validacion, Toast.LENGTH_SHORT).show();
                }
                else{
                    new LoginService().changePassword(Contrasena.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (!task.isSuccessful()){
                                        Toast.makeText(getContext(), task.getException().toString(), Toast.LENGTH_SHORT).show();
                                    }
                                    else{
                                        Toast.makeText(getContext(), "Contraseña modificada.", Toast.LENGTH_SHORT).show();
                                        usuario = usuarioAGuardar;
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
        Fragment myFragment = new PerfilFragment();
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
