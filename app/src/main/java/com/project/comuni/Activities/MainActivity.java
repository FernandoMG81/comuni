package com.project.comuni.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.transition.Slide;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


import com.google.firebase.auth.FirebaseAuth;
import com.project.comuni.Fragments.HomeFragment;
import com.project.comuni.Fragments.MessagesFragment;
import com.project.comuni.Fragments.PlacesFragment;
import com.project.comuni.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {


    private TextView textView;
    private String Titulo = "Comuni";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       showToolbar(Titulo,false);



      BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
      bottomNav.setOnNavigationItemSelectedListener(navListener);

      getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
      new HomeFragment()).commit();
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
                    selectedFragment = new HomeFragment();
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


   /* public void replaceFragmentWithAnimation(Fragment fragment, String tag){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(tag);
        transaction.commit();
    }
*/

}
