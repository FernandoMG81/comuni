package com.project.comuni.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.project.comuni.R;

public class LoginActivity extends AppCompatActivity {

    private SharedPreferences prefs;
    private EditText editTextLogin;
    private EditText editTextPassword;
    private Button btnLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        bindUI();

        prefs = getSharedPreferences("PreferencesComuni", Context.MODE_PRIVATE);

        setCredentialIfExist();


     btnLogin.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             String email= editTextLogin.getText().toString();
             String password = editTextPassword.getText().toString();
             if(login(email,password)){
              goToMail();
              saveOnPreferences(email,password);
             }
         }
     });



    }

    //Validar login
    private boolean login (String email, String password){
        if(!isValidEmail(email)){
            Toast.makeText(this,"El email no es válido",Toast.LENGTH_LONG).show();
            return false;
        } else if (!isValidPassword(password)){
            Toast.makeText(this,"El password no es válido",Toast.LENGTH_LONG).show();
            return false;
        } else {
            return true;
        }
    }

    private void saveOnPreferences(String email, String password){
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("email", email);
        editor.putString("pass", password);
        editor.apply();
    }

    //Validacion de mail
    private boolean isValidEmail (String email){
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    //Validacion de password
    private boolean isValidPassword (String password){
        return password.length() > 4;
    }

    //recoge los datos del login
    private void bindUI(){
        editTextLogin = findViewById(R.id.editTextUsuario);
        editTextPassword = findViewById(R.id.editTextClave);
        btnLogin = findViewById(R.id.buttonIngresar);
    }

    //Ir al MailActivity
    private void goToMail(){
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    //Obtiene mail guardado en preferences
    private String getUserMailPrefs(){
        return prefs.getString("email","");
    }

    //Obtiene pass guardado en preferences
    private String getUserPassPrefs(){
        return prefs.getString("pass","");
    }

    //setea credenciales guardadas
    private void setCredentialIfExist(){
        String email = getUserMailPrefs();
        String pass = getUserPassPrefs();
        if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(pass)){
            editTextLogin.setText(email);
            editTextPassword.setText(pass);
        }
    }
}
