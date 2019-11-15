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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.project.comuni.Activities.MainActivity;
import com.project.comuni.Fragments.Espacios.ConfigPlaceFragment;
import com.project.comuni.Models.Firebase.Go;
import com.project.comuni.Models.Usuario;
import com.project.comuni.R;
import com.project.comuni.Servicios.LoginService;
import com.project.comuni.Servicios.UsuarioService;

public class PerfilFragment extends Fragment {

    //Db
    private FirebaseStorage dbF;

    //Variables
    private Go<Usuario> usuario = new Go<>(new Usuario());

    //Que Hacer en Edit Usuario
    // 1 -> Modificar Datos
    // 2 -> Modificar Email
    // 3 -> Modificar Contrasena
    private int queHacer;

    //Layout
    //Edit Texts
    private TextView Nombre;
    private TextView Apellido;
    private TextView Fecha;
    private TextView Edad;
    private TextView Email;
    private TextView Created;
    //Buttons
    private Button EditDatos;
    private Button EditEmail;
    private Button EditContrasena;

    private void getData() {

    }

    private void setLayoutReference(View view){
        Nombre = view.findViewById(R.id.PerfilNombre);
        Apellido = view.findViewById(R.id.PerfilApellido);
        Edad = view.findViewById(R.id.PerfilEdad);
        Fecha = view.findViewById(R.id.PerfilFechaNacimiento);
        Created = view.findViewById(R.id.PerfilFechaCreated);
        Email = view.findViewById(R.id.PerfilEmail);

        EditDatos = view.findViewById(R.id.PerfilSubmitDatos);
        EditEmail = view.findViewById(R.id.PerfilSubmitEmail);
        EditContrasena = view.findViewById(R.id.PerfilSubmitContrasena);

        Nombre.setText(usuario.getObject().getNombre());
        Apellido.setText(usuario.getObject().getApellido());
        Fecha.setText(usuario.getObject().getApellido());
        Created.setText(usuario.getObject().getCreado());
        Email.setText(usuario.getObject().getEmail());

        Apellido.setVisibility(View.GONE);
        Fecha.setVisibility(View.GONE);
        Created.setVisibility(View.GONE);
        Edad.setVisibility(View.GONE);
    }

    private void setBotonEditarDatos(){
        EditDatos.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                queHacer = 1; //Para que modifique solo Datos Personales
                goToEditUsuarios();
            }
        });
    }

    private void setBotonEditarEmail(){
        EditEmail.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                queHacer = 2; //Para que modifique solo Datos Personales
                goToEditUsuarios();
            }
        });
    }

    private void setBotonEditarContraseña(){
        EditContrasena.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                queHacer = 3; //Para que modifique solo Datos Personales
                goToEditUsuarios();
            }
        });
    }

    private void goToEditUsuarios(){
        AppCompatActivity activity = (MainActivity) this.getContext();
        Fragment myFragment = new EditUsuarioFragment();
        Bundle args = new Bundle();
        args.putSerializable("queHacer",queHacer);
        args.putSerializable("usuario",usuario);
        myFragment.setArguments(args);
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, myFragment).addToBackStack(null).commit();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_perfil_usuario, container, false);

        getData();
        usuario = new LoginService().getGoUser();
        new UsuarioService(usuario).getObject()
                .addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot x:dataSnapshot.getChildren())
                        {
                            usuario.setObject(x.getValue(usuario.getObject().getClass()));
                        }
                        setLayoutReference(view);
                        setBotonEditarDatos();
                        setBotonEditarEmail();
                        setBotonEditarContraseña();
                    }
                });

        return view;
    }

}
