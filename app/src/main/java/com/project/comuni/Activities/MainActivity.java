package com.project.comuni.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.transition.Slide;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.project.comuni.Fragments.AcercaDeFragment;
import com.project.comuni.Fragments.Espacios.PlacesFragment;
import com.project.comuni.Fragments.Noticias.NewsFragment;
import com.project.comuni.Fragments.Mensajes.MessagesFragment;
import com.project.comuni.Fragments.Espacios.PostsFragment;
import com.project.comuni.Fragments.PerfilFragment;
import com.project.comuni.Models.Firebase.Go;
import com.project.comuni.Models.Usuario;
import com.project.comuni.Persistencia.UsuarioDAO;
import com.project.comuni.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.project.comuni.Utils.Constantes;


public class MainActivity extends AppCompatActivity {

    private String Titulo = "Comuni";
    private static final String CHANNEL_ID = "1";
    private static final String CHANNEL_HIGH = "HIGH CHANNEL";
    private static final String CHANNEL_LOW = "LOW CHANNEL";
    private static final String CHANNEL_DESC = "COMUNI CHANNEL";
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

       showToolbar(Titulo,false);

       if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
           NotificationChannel channel = new NotificationChannel(CHANNEL_ID,CHANNEL_HIGH, NotificationManager.IMPORTANCE_DEFAULT);
           channel.setDescription(CHANNEL_DESC);
           NotificationManager manager = getSystemService(NotificationManager.class);
           manager.createNotificationChannel(channel);
        }

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                       if(task.isSuccessful()){
                           String token = task.getResult().getToken();
                           guardarToken(token);
                       }else{
                           Toast.makeText(MainActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                       }
                    }
                });

      BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
      bottomNav.setOnNavigationItemSelectedListener(navListener);

      getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
      new NewsFragment()).commit();
    }

    private void guardarToken(String token) {


        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference(Constantes.NODO_USUARIOS);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_usuario,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Fragment fragment;
        switch (item.getItemId()){
            case R.id.menu_logout:
                logOut();
                return true;
            case R.id.menu_mi_perfil:
                Titulo = "Mi Perfil";
                showToolbar(Titulo,false);
                fragment = new PerfilFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
                return true;
            case R.id.menu_acerca_de:
                Titulo = "Acerca De";
                showToolbar(Titulo,false);
                fragment = new AcercaDeFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void showToolbar(String title, boolean upButton){
        Toolbar toolbar = findViewById(R.id.my_toolbar); //Declaramos un objeto tipo Toolbar y lo instanciamos
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
    }


    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;

            switch(item.getItemId()){
                case R.id.navigation_home:
                    Titulo = "Comuni";
                    showToolbar(Titulo,false);
                    selectedFragment = new NewsFragment();
                    selectedFragment.setEnterTransition(new Slide(Gravity.BOTTOM));
                    selectedFragment.setExitTransition(new Slide(Gravity.TOP));
                    break;
                case R.id.navigation_places:
                    Titulo = "Espacios";
                    selectedFragment = new PlacesFragment();

                    selectedFragment.setEnterTransition(new Slide(Gravity.BOTTOM));
                    selectedFragment.setExitTransition(new Slide(Gravity.TOP));
                    showToolbar(Titulo,false);

                    break;
                case R.id.navigation_messages:
                    Titulo = "Mensajes";
                    showToolbar(Titulo,false);
                    selectedFragment = new MessagesFragment();
                    selectedFragment.setEnterTransition(new Slide(Gravity.BOTTOM));
                    selectedFragment.setExitTransition(new Slide(Gravity.TOP));
                    break;
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();

            return true;

        }
    };

    private void logOut (){

        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(MainActivity.this,LoginActivity.class));
        finish();
    }



    @Override
    protected void onResume() {
        super.onResume();
        if(UsuarioDAO.getInstance().isUserLogged()){
            //el usuario logueado
        }else{
            startActivity(new Intent(MainActivity.this,LoginActivity.class));
            finish();
        }
    }


}
