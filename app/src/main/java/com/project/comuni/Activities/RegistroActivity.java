package com.project.comuni.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project.comuni.Models.User;
import com.project.comuni.Models.Usuario;
import com.project.comuni.R;

public class RegistroActivity extends AppCompatActivity {

    private EditText txtNombre, txtCorreo, txtContraseña, txtContraseñaRepetida;
    private Button btnRegistrar;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference referenceUsuarios;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        txtNombre = findViewById(R.id.idRegistroNombre);
        txtCorreo = findViewById(R.id.idRegistroCorreo);
        txtContraseña = findViewById(R.id.idRegistroContraseña);
        txtContraseñaRepetida = findViewById(R.id.idRegistroRepiteContraseña);
        btnRegistrar = findViewById(R.id.idBotonRegistro);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        referenceUsuarios = database.getReference("Usuarios");

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String correo = txtCorreo.getText().toString();
                String nombre = txtNombre.getText().toString();
                if(isValidEmail(correo) && validarContraseña() && validaNombre(nombre)){
                    String contraseña = txtContraseña.getText().toString();

                    mAuth.createUserWithEmailAndPassword(correo, contraseña)
                            .addOnCompleteListener(RegistroActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Toast.makeText(RegistroActivity.this, "Se registro correctamente", Toast.LENGTH_LONG).show();
                                        User usuario = new User();
                                        usuario.setEmail(correo);
                                        usuario.setNombre(nombre);
                                        referenceUsuarios.push().setValue(usuario);
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(RegistroActivity.this, "Error al registrarse", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });

                } else Toast.makeText(RegistroActivity.this, "Validacion funcionando", Toast.LENGTH_LONG).show();

            }
        });


    }

    private boolean isValidEmail(CharSequence target){
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public boolean validarContraseña(){
        String contraseña, contraseñaRepetida;
        contraseña = txtContraseña.getText().toString();
        contraseñaRepetida = txtContraseñaRepetida.getText().toString();
        if(contraseña.equals(contraseñaRepetida)){
            if(contraseña.length()>=6 && contraseña.length()<=16){
                return true;
            } else return false;
        }else return false;
    }

    public boolean validaNombre(String nombre){
        return !nombre.isEmpty();
    }
}
