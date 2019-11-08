package com.project.comuni.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
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
    private LoginService LService = new LoginService();

    private void setLayout (){
        editTextLogin = findViewById(R.id.editTextUsuario);
        editTextPassword = findViewById(R.id.editTextClave);
        btnLogin = findViewById(R.id.buttonIngresar);
        btnRegistro = findViewById(R.id.buttonRegistrarse);
    }

    private void setBtnLoginClick(){
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String correo = editTextLogin.getText().toString();
                if(isValidEmail(correo) && validarContraseña()){
                    String contraseña = editTextPassword.getText().toString();
                    LService.SignIn(correo,contraseña)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Toast.makeText(LoginActivity.this,"Sesión iniciada correctamente", Toast.LENGTH_LONG).show();
                                        nextActivity();

                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(LoginActivity.this,"Error, credenciales incorrectas", Toast.LENGTH_LONG).show();
                                    }

                                }
                            });
                }else{
                    Toast.makeText(LoginActivity.this,"Error en la contraseña ingresada",Toast.LENGTH_LONG).show();
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
        FirebaseUser currentUser = LService.getUser();
        if(currentUser!=null){
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

    //VALIDACIONES

    //Validacion de mail
    private boolean isValidEmail (String email){
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    //Validacion de password
    private boolean isValidPassword (String password){
        return password.length() > 5;
    }

    public boolean validarContraseña(){
        String contraseña;
        contraseña = editTextPassword.getText().toString();

        if(contraseña.length()>=6 && contraseña.length()<=16){
                return true;
            } else return false;

    }

    public void nextActivity(){
        startActivity(new Intent(LoginActivity.this,MainActivity.class));
        finish();
    }

    //  prefs = getSharedPreferences("PreferencesComuni", Context.MODE_PRIVATE);

    // setCredentialIfExist();

/*
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

    private void saveOnPreferences(String email, String password){
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("email", email);
        editor.putString("pass", password);
        editor.apply();
    }
*/

    //setea credenciales guardadas
/*    private void setCredentialIfExist(){
        String email = Util.getUserMailPrefs(prefs);
        String pass = Util.getUserPassPrefs(prefs);
        if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(pass)){
            editTextLogin.setText(email);
            editTextPassword.setText(pass);
        }
    }
*/
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

    private boolean isValidEmail(CharSequence target){
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    //Ir al MailActivity
    private void goToMail(){
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
