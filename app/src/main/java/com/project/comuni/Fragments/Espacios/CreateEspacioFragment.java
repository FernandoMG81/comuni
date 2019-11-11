package com.project.comuni.Fragments.Espacios;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.project.comuni.Activities.MainActivity;
import com.project.comuni.Adapters.Espacios.RecyclerAdapterTags;
import com.project.comuni.Models.Espacio;
import com.project.comuni.Models.Firebase.Go;
import com.project.comuni.Models.Post;
import com.project.comuni.Models.Tag;
import com.project.comuni.Models.Usuario;
import com.project.comuni.R;
import com.project.comuni.Servicios.EspacioService;
import com.project.comuni.Servicios.PostService;
import com.project.comuni.Servicios.TagService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.transform.Result;

public class CreateEspacioFragment extends Fragment {

    //Db
    private FirebaseStorage dbF;

    //Variables
    private Go<Usuario> usuario = new Go<>(new Usuario());
    private Go<Espacio> espacio = new Go<>(new Espacio());
    private Go<Espacio> espacioActual = new Go<>(new Espacio());

    //Layout
    private TextView titulo;
    private TextView descripcion;
    private Button submit;

    private void getData() {
        Bundle bundle = getArguments();
        this.usuario = (Go<Usuario>) bundle.getSerializable("usuario");
        this.espacio = (Go<Espacio>) bundle.getSerializable("espacioActual");
    }

    private void cuestionarioAObjeto(){
        espacio = new Go<>(new Espacio());
        espacio.getObject().setNombre(titulo.getText().toString());
        espacio.getObject().setDescripcion(descripcion.getText().toString());
        //post.getObject().setTags(new Go<>(tag));
        Map<String,Usuario> admin = new HashMap<>();
        admin.put(usuario.getKey(),usuario.getObject());
        espacio.getObject().setAdministradores(admin);
    }

    private void setLayoutReference(View view){
        titulo = view.findViewById(R.id.CreateEspacioTitulo);
        descripcion = view.findViewById(R.id.CreateEspacioDescripción);
        submit = view.findViewById(R.id.CreateEspacioSubmit);
    }

    private void setBoton(){
        submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                cuestionarioAObjeto();

                if(!espacio.getObject().validar().equals("Ok")){
                    Toast.makeText(getContext(),espacio.getObject().validar(),Toast.LENGTH_LONG).show();
                }
                else{
                    Task task = new EspacioService(espacio).create()
                            .addOnCompleteListener(getActivity(), new OnCompleteListener() {
                                @Override
                                public void onComplete(@NonNull Task task) {
                                    if (!task.isSuccessful()) {
                                        Toast.makeText(getContext(), "Ocurrio un error", Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(getContext(), "Se creo el espacio", Toast.LENGTH_LONG).show();
                                        goToEspacios();
                                    }
                                }
                            });
                }
            }
        });
    }

    private void goToEspacios(){
        AppCompatActivity activity = (MainActivity) this.getContext();
        Fragment myFragment = new PlacesFragment();
        Bundle args = new Bundle();
        args.putSerializable("usuario",usuario);
        myFragment.setArguments(args);
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, myFragment).addToBackStack(null).commit();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_espacio, container, false);

        getData();
        setLayoutReference(view);
        setBoton();

        return view;
    }



}
