package com.project.comuni.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.project.comuni.R;
import com.project.comuni.Servicios.LoginService;


public class LoginActivity extends AppCompatActivity {

    //private SharedPreferences prefs;
    private TextInputEditText editTextLogin;
    private TextInputEditText editTextPassword;
    private Button btnLogin;
    private TextView btnRegistro;
    private ProgressBar progressBar;

    //Variables
    private String email;
    private String contrasena;

    public void nextActivity(){
        startActivity(new Intent(LoginActivity.this,MainActivity.class));
        finish();
    }

    private void setLayout (){
        editTextLogin = findViewById(R.id.editTextUsuario);
        editTextPassword = findViewById(R.id.loginClave);
        btnLogin = findViewById(R.id.LoginButtonIngresar);
        btnRegistro = findViewById(R.id.loginButtonRegistrarse);
        progressBar = findViewById(R.id.loginProgressbar);
        progressBar.setVisibility(View.INVISIBLE);
        btnLogin.setEnabled(true);
    }

    private void setBtnLoginClick(){
        FirebaseApp.initializeApp(this);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = editTextLogin.getText().toString();
                contrasena = editTextPassword.getText().toString();
                progressBar.setVisibility(View.VISIBLE);
                btnLogin.setEnabled(false);
                if (!(validarEmail(email) && validarContrasena(contrasena))) {
                    Toast.makeText(LoginActivity.this, "Contraseña y/o email no válidos", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.INVISIBLE);
                    btnLogin.setEnabled(true);
                } else {
                    new LoginService().SignIn(email, contrasena).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(LoginActivity.this, task.getException().toString(), Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.INVISIBLE);
                                btnLogin.setEnabled(true);
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
                startActivity(new Intent(LoginActivity.this, RegistroActivityAnterior.class));
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
