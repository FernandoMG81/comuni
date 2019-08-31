package com.example.comuni.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import com.example.comuni.R;

public class LoginActivity extends AppCompatActivity {

    private Button btnLogin;
    private String mensaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

     btnLogin = findViewById(R.id.buttonIngresar);
     btnLogin.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             Intent intent = new Intent(LoginActivity.this,MainActivity.class);
             intent.putExtra("mensaje",mensaje);
             startActivity(intent);
         }
     });
    }


}
