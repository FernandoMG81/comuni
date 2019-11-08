package com.project.comuni.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project.comuni.Models.Firebase.Go;
import com.project.comuni.Models.Usuario;
import com.project.comuni.R;
import com.project.comuni.Servicios.Db;
import com.project.comuni.Servicios.LoginService;
import com.project.comuni.Servicios.UsuarioService;


public class LoginActivity extends AppCompatActivity {

    //private SharedPreferences prefs;
    private TextInputEditText editTextLogin;
    private TextInputEditText editTextPassword;
    private Button btnLogin;
    private TextView btnRegistro;


    //Variables
    private String email;
    private String contrasena;

    public void nextActivity(){
        startActivity(new Intent(LoginActivity.this,MainActivity.class));
        finish();
    }

    private void setLayout (){
        editTextLogin = findViewById(R.id.editTextUsuario);
        editTextPassword = findViewById(R.id.editTextClave);
        btnLogin = findViewById(R.id.buttonIngresar);
        btnRegistro = findViewById(R.id.buttonRegistrarse);
    }

    private void setBtnLoginClick(){
        FirebaseApp.initializeApp(this);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = editTextLogin.getText().toString();
                contrasena = editTextPassword.getText().toString();
                if (!(validarEmail(email) && validarContrasena(contrasena))) {
                    Toast.makeText(LoginActivity.this, "Contraseña y/o email no válidos", Toast.LENGTH_LONG).show();
                } else {
                    new LoginService().SignIn(email, contrasena).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(LoginActivity.this, "Contraseña y/o email incorrectos", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(LoginActivity.this, "Se logueo correctamente", Toast.LENGTH_LONG).show();
                                nextActivity();
                            }
                        }
                    });
                }
            }
        });
    }

    private void setBtnRegistroClick(){
        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegistroActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        FirebaseApp.initializeApp(this);
        super.onResume();
        FirebaseUser usuarioActual = new LoginService().getUser();
        if(usuarioActual!=null){
            nextActivity();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseApp.initializeApp(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setLayout();
        setBtnLoginClick();
        setBtnRegistroClick();
    }

    //Validaciones
    private boolean validarEmail (String email){
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public boolean validarContrasena(String contrasena){
        if(contrasena.length()>=6 && contrasena.length()<=16){
                return true;
            } else return false;
    }
}
