package com.project.comuni.Fragments.Mensajes;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.project.comuni.Activities.ListadoUsuariosActivity;
import com.project.comuni.Activities.MainActivity;
import com.project.comuni.Fragments.Espacios.ConfigPlaceFragment;
import com.project.comuni.Models.Firebase.Go;
import com.project.comuni.Models.Firebase.MensajePersonal;
import com.project.comuni.Models.Mensaje;
import com.project.comuni.Models.Usuario;
import com.project.comuni.Servicios.LoginService;
import com.project.comuni.Servicios.MensajeService;

import com.project.comuni.R;
import com.project.comuni.Adapters.Mensajes.RecyclerAdapterMessages;
import com.project.comuni.Servicios.UsuarioService;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.project.comuni.Utils.Util.filtrarString;


public class MessagesFragment extends Fragment {


    //Variables Locales
    String searchText = "";

    //Variables Datos
    private ArrayList<Go<Usuario>> usuarios = new ArrayList<>();
    private Go<Usuario> usuario = new Go<>(new Usuario());
    private ArrayList<Go<MensajePersonal>> mensajesDb = new ArrayList<>();
    private ArrayList<Go<Mensaje>> mensajes = new ArrayList<>();
    private ArrayList<Go<Mensaje>> mensajesAMostrar = new ArrayList<>();

    //Layout
    private Button botonNuevoMensaje;
    private RecyclerView recyclerView;
    private EditText search;

    private void getdata(){

    }

    private void setLayoutReferences(View view){
        recyclerView = view.findViewById(R.id.RVMesages);
        botonNuevoMensaje = view.findViewById(R.id.addBtnMensaje);
        search = view.findViewById(R.id.MessagesSearch);
    }

    private void setSearch(){
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                searchText = editable.toString();
                filterData();
                setRecycler();
            }
        });
    }

    private void filterData(){
        mensajesAMostrar.clear();
        for (Go<Mensaje> mensaje : mensajes){
            if (filtrarString(mensaje.getObject().getTexto(),searchText) ||
                    filtrarString(mensaje.getObject().getEmisor().getObject().getNombre()
                            + " " + mensaje.getObject().getEmisor().getObject().getApellido(), searchText) ||
                    filtrarString(mensaje.getObject().getReceptor().getObject().getNombre()
                            + " " + mensaje.getObject().getReceptor().getObject().getApellido(), searchText)
            ){
              mensajesAMostrar.add(mensaje);
            }
        }
    }

    private void setRecycler(){
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        RecyclerAdapterMessages adapter = new RecyclerAdapterMessages(mensajesAMostrar, this.getContext(), usuario);
        recyclerView.setAdapter(adapter);
    }

    private void addButton(){
        botonNuevoMensaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (MainActivity) v.getContext();
                Fragment myFragment = new ListadoContactosFragment();
                Bundle args = new Bundle();
                args.putSerializable("usuario",usuario);
                myFragment.setArguments(args);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, myFragment).addToBackStack(null).commit();

            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_messages, container, false);

        getdata();
        setLayoutReferences(view);
        addButton();

        usuario = new LoginService().getGoUser();
        new UsuarioService(usuario).getObject()
                .addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        mensajes.clear();

                        for (DataSnapshot x: dataSnapshot.getChildren()) {
                            usuario.setObject((x.getValue(usuario.getObject().getClass())));
                        }

                        new UsuarioService().getAll()
                                .addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }

                                    @Override
                                     public void onDataChange(@NonNull DataSnapshot dataSnapshotu) {
                                        mensajes.clear();
                                        for (DataSnapshot x:dataSnapshotu.getChildren())
                                        {
                                            Go<Usuario> usuariox = new Go<>(x.getKey(),new Usuario());
                                            usuariox.setObject(x.getValue(usuario.getObject().getClass()));
                                            if (!(usuariox.getKey().equals(usuario.getKey()))){
                                                usuarios.add(usuariox);
                                            }
                                        }

                                        new MensajeService().getAllFromUsuario(usuario)
                                                .addValueEventListener(new ValueEventListener() {
                                                    class TuplaMensaje<T, T1> {
                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                                    }

                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                        Go<Mensaje> mensaje = new Go<>(new Mensaje());
                                                        Go<MensajePersonal> mensajePersonal = new Go<>(new MensajePersonal());
                                                        Map<String,Go<MensajePersonal>> TodosLosMensajes = new HashMap<>();
                                                        mensajes.clear();
                                                        for (DataSnapshot x:snapshot.getChildren())
                                                        {
                                                            for (DataSnapshot w:x.getChildren() ) {
                                                                mensajePersonal.setKey(w.getKey());
                                                                mensajePersonal.setObject(w.getValue(mensajePersonal.getObject().getClass()));
                                                                TodosLosMensajes.put(x.getKey(),mensajePersonal);
                                                            }
                                                        }
                                                        for (Map.Entry<String,Go<MensajePersonal>> x: TodosLosMensajes.entrySet())
                                                        {
                                                            Boolean usuarioExistente = false;
                                                            for (Go<Mensaje> y: mensajes)
                                                            {
                                                                if (x.getValue().getKey().equals(y.getKey())){
                                                                    usuarioExistente = true;
                                                                    break;
                                                                }
                                                            }
                                                            if (!usuarioExistente){
                                                                Go<Mensaje> mensajex = new Go<>(x.getValue().getKey(), new Mensaje());
                                                                mensajex.getObject().setTexto(x.getValue().getObject().getMensaje());
                                                                mensajex.getObject().setUrlFoto(x.getValue().getObject().getUrlFoto());
                                                                mensajex.getObject().setCreated(x.getValue().getObject().getCreatedTimestamp());
                                                                for (Go<Usuario> z: usuarios)
                                                                {
                                                                    if(x.getKey().equals(z.getKey())){
                                                                        if(x.getValue().getObject().getKeyEmisor().equals(usuario.getKey())){
                                                                            mensajex.getObject().setEmisor(usuario);
                                                                            mensajex.getObject().setReceptor(z);

                                                                        }
                                                                        else{
                                                                            mensajex.getObject().setEmisor(z);
                                                                            mensajex.getObject().setReceptor(usuario);
                                                                        }
                                                                        mensajes.add(mensajex);
                                                                        break;
                                                                    }
                                                                }
                                                            }

                                                        }
                                                        setSearch();
                                                        if (mensajes.size() > 0) {
                                                            filterData();
                                                            setRecycler();
                                                            //vacio.setVisibility(View.GONE);
                                                        }
                                                        else{
                                                            //vacio.setVisibility(View.VISIBLE);
                                                        }
                                                    }
                                                });
                                    }
                        });
                    }
                });

        return view;
    }



}
