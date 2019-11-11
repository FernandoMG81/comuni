package com.project.comuni.Fragments.Espacios;


import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.project.comuni.Activities.MainActivity;
import com.project.comuni.Models.Espacio;
import com.project.comuni.Models.Firebase.Go;
import com.project.comuni.Models.Tag;
import com.project.comuni.Models.Usuario;
import com.project.comuni.R;
import com.project.comuni.Servicios.TagService;

public class CreateTagFragment extends Fragment {

    //Db
    private FirebaseStorage dbF;

    //Variables
    private Go<Tag> tag = new Go<>(new Tag());
    private Go<Usuario> usuario = new Go<>(new Usuario());
    private Go<Espacio> espacio = new Go<>(new Espacio());

    //Layout
    private TextView nombre;
    private TextView colorT;
    private TextView colorB;
    private TextView demo;
    private Button submit;

    private void getData() {
        Bundle bundle = getArguments();
        this.espacio = (Go<Espacio>) bundle.getSerializable("espacioActual");
    }

    private void cuestionarioAObjeto(){
        tag = new Go<>(new Tag());
        tag.getObject().setEspacio(espacio);
        tag.getObject().setText(nombre.getText().toString());
        tag.getObject().setTextColor(colorT.getText().toString());
        tag.getObject().setBackgroundColor(colorB.getText().toString());
    }

    private void setLayoutReference(View view){
        nombre = view.findViewById(R.id.CreateTagNombre);
        colorT = view.findViewById(R.id.CreateTagColorT);
        colorB = view.findViewById(R.id.CreateTagColorB);
        demo = view.findViewById(R.id.CreateTagDemo);
        submit = view.findViewById(R.id.CreateTagSubmit);
    }

    private void setBoton(){
        submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                cuestionarioAObjeto();

                if(!tag.getObject().validar().equals("Ok")){
                    Toast.makeText(getContext(),tag.getObject().validar(),Toast.LENGTH_LONG).show();
                }
                else{
                    new TagService(tag).create()
                            .addOnCompleteListener(getActivity(), new OnCompleteListener() {
                                @Override
                                public void onComplete(@NonNull Task task) {
                                    if (!task.isSuccessful()) {
                                        Toast.makeText(getContext(), "Ocurrio un error", Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(getContext(), "Se creo la etiqueta", Toast.LENGTH_LONG).show();
                                        goToEspacios();
                                    }
                                }
                            });
                }
            }
        });
    }

    private void setDemo(){

        nombre.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                tag.getObject().setText(editable.toString());
                if (!tag.getObject().getText().isEmpty()){
                    demo.setText(tag.getObject().getText());
                }
                else{
                    demo.setText("Texto");
                }
            }
        });

        colorT.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                tag.getObject().setTextColor(editable.toString());
                if (!tag.getObject().validarHex(colorT.getText().toString()).equals("Ok")){
                    demo.setTextColor(Color.parseColor("#111111"));
                }
                else{
                    demo.setTextColor(Color.parseColor(tag.getObject().getColorT()));
                }
            }
        });

        colorB.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                tag.getObject().setBackgroundColor(editable.toString());
                if (!tag.getObject().validarHex(colorB.getText().toString()).equals("Ok")) {
                    demo.setBackgroundColor(Color.parseColor("#EEEEEE"));
                } else {
                    demo.setBackgroundColor(Color.parseColor(tag.getObject().getColorB()));
                }
            }
        });
    }

    private void goToEspacios(){
        AppCompatActivity activity = (MainActivity) this.getContext();
        Fragment myFragment = new PostsFragment();
        Bundle args = new Bundle();
        args.putSerializable("usuario",usuario);
        myFragment.setArguments(args);
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, myFragment).addToBackStack(null).commit();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_tag, container, false);

        getData();
        setLayoutReference(view);
        setDemo();
        setBoton();

        return view;
    }



}
