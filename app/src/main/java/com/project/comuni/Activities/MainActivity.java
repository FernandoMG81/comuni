package com.project.comuni.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.transition.Slide;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


import com.google.firebase.auth.FirebaseAuth;
import com.project.comuni.Fragments.AcercaDeFragment;
import com.project.comuni.Fragments.Espacios.PlacesFragment;
import com.project.comuni.Fragments.Noticias.NewsFragment;
import com.project.comuni.Fragments.Mensajes.MessagesFragment;
import com.project.comuni.Fragments.Espacios.PostsFragment;
import com.project.comuni.Persistencia.UsuarioDAO;
import com.project.comuni.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {


    private TextView textView;
    private String Titulo = "Comuni";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

       showToolbar(Titulo,false);



      BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
      bottomNav.setOnNavigationItemSelectedListener(navListener);

      getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
      new NewsFragment()).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_usuario,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_logout:
                logOut();
                return true;
            case R.id.menu_mi_perfil:
                Titulo = "Mi Perfil";
                showToolbar(Titulo,false);
                return true;
            case R.id.menu_acerca_de:
                Titulo = "Acerca De";
                showToolbar(Titulo,false);
                Fragment fragment = new AcercaDeFragment();
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
