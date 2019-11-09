package com.project.comuni.Fragments.Mensajes;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.project.comuni.Activities.MensajeriaActivity;
import com.project.comuni.Adapters.Mensajes.RAdapterMensajes;
import com.project.comuni.Models.Firebase.MensajePersonal;
import com.project.comuni.Models.Logica.LMensajePersonal;
import com.project.comuni.Models.Logica.LUser;
import com.project.comuni.Models.Mensaje;
import com.project.comuni.Models.Noticia;
import com.project.comuni.Models.Usuario;
import com.project.comuni.Persistencia.MensajeriaDAO;
import com.project.comuni.Persistencia.UsuarioDAO;
import com.project.comuni.R;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.project.comuni.Utils.Constantes.NODO_MENSAJES;

public class InnerMessagesFragment extends Fragment {

    //Db
    private FirebaseStorage storage;
    private StorageReference storageReference;

    //variables
    private Usuario contacto = new Usuario();

    //Layout
    private CircleImageView fotoPerfil;
    private TextView txtNombre;
    private RecyclerView rvMensajes;
    private EditText txtMensaje;
    private Button btnEnviar;
    private RAdapterMensajes adapter;
    private ImageButton btnEnviarFoto;
    private static final int PHOTO_SEND = 1;
    private static final int PHOTO_PERFIL = 2;
    private String fotoPerfilCadena;
    private FirebaseAuth mAuth;
    private String NOMBRE_USUARIO;
    private String KEY_RECEPTOR;
    private String PHOTO_RECEPTOR;
    private String NAME_RECEPTOR;

    private void getData(){
        Bundle bundle = getArguments();
        Mensaje mensaje = new Mensaje();
        mensaje = (Mensaje) bundle.getSerializable("mensaje");
        //contacto =
    }

    private void setLayoutReference(View view){
        fotoPerfil = view.findViewById(R.id.bubbleFotoPerfil);
        Glide.with(getActivity()).load(PHOTO_RECEPTOR).into(fotoPerfil);
        txtNombre = view.findViewById(R.id.bubbleNombreUsuario);
        txtNombre.setText(NAME_RECEPTOR);
        rvMensajes = view.findViewById(R.id.bubbleRV);
        txtMensaje = view.findViewById(R.id.bubbleMensaje);
        btnEnviar = view.findViewById(R.id.bubbleEnviar);
        btnEnviarFoto = view.findViewById(R.id.bubbleEnviarImagen);
        fotoPerfilCadena = "";
    }

    private void setRecyclerMensajes(){
        adapter = new RAdapterMensajes(getContext());
        LinearLayoutManager l = new LinearLayoutManager(getContext());
        rvMensajes.setLayoutManager(l);
        rvMensajes.setAdapter(adapter);

        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                setScrollbar();
            }
        });
    }

    private void setBotonEnviarTexto(){
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mensajeEnviar = txtMensaje.getText().toString();
                if(!mensajeEnviar.isEmpty()){
                    MensajePersonal mensaje = new MensajePersonal();
                    mensaje.setMensaje(mensajeEnviar);
                    mensaje.setContieneFoto(false);
                    mensaje.setKeyEmisor(UsuarioDAO.getInstance().getKeyUsuario());
                    MensajeriaDAO.getInstance().nuevoMensaje(UsuarioDAO.getInstance().getKeyUsuario(),KEY_RECEPTOR,mensaje);
                    SendNotification();
                    txtMensaje.setText("");
                }
            }
        });
    }

    private void setBotonEnviarImagen(){
        btnEnviarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent (Intent.ACTION_GET_CONTENT);
                i.setType("image/jpeg");
                i.putExtra(Intent.EXTRA_LOCAL_ONLY,true);
                startActivityForResult(Intent.createChooser(i,"Selecciona una imagen"),PHOTO_SEND);
            }
        });
    }

    //Vacio
    private void SendNotification() {

    }

    //Funcion para que la pantalla scrollee al ultimo mensaje
    private void setScrollbar (){
        rvMensajes.scrollToPosition(adapter.getItemCount()-1);
    }

    public static boolean verifyStoragePermissions(Activity activity) {
        String[] PERMISSIONS_STORAGE = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
        int REQUEST_EXTERNAL_STORAGE = 1;
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
            return false;
        }else{
            return true;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_mensajeria, container, false);

        getData();
        setLayoutReference(view);
        setRecyclerMensajes();
        setBotonEnviarTexto();
        setBotonEnviarImagen();
        setScrollbar();

        storage = FirebaseStorage.getInstance();
        mAuth = FirebaseAuth.getInstance();

        FirebaseDatabase
                .getInstance()
                .getReference(NODO_MENSAJES)
                .child(UsuarioDAO.getInstance().getKeyUsuario())
                .child(KEY_RECEPTOR)
                .addChildEventListener(new ChildEventListener() {

                    Map<String, LUser> mapUsuariosTemporales = new HashMap<>();


                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        final MensajePersonal mensaje = dataSnapshot.getValue(MensajePersonal.class);
                        final LMensajePersonal lMensaje = new LMensajePersonal(dataSnapshot.getKey(),mensaje);
                        final int posicion = adapter.addMensaje(lMensaje);

                        if(mapUsuariosTemporales.get(mensaje.getKeyEmisor())!=null){
                            lMensaje.setlUsuario(mapUsuariosTemporales.get(mensaje.getKeyEmisor()));
                            adapter.actualizarMensaje(posicion,lMensaje);
                        }else{
                            UsuarioDAO.getInstance().obtenerInformacionUsuarioPorLlave(mensaje.getKeyEmisor(), new UsuarioDAO.IDevolverUsuario() {
                                @Override
                                public void devolverUsuario(LUser lUsuario) {
                                    mapUsuariosTemporales.put(mensaje.getKeyEmisor(),lUsuario);
                                    lMensaje.setlUsuario(lUsuario);
                                    adapter.actualizarMensaje(posicion,lMensaje);
                                }

                                @Override
                                public void devolverError(String error) {
                                    Toast.makeText(getContext(),"Error "+error,Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

        verifyStoragePermissions(getActivity());

        return view;
    }








//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode == PHOTO_SEND && resultCode == RESULT_OK){
//            Uri u = data.getData();
//            storageReference = storage.getReference("imagenes_chat");//imagenes_chat
//            final StorageReference fotoReferencia = storageReference.child(u.getLastPathSegment());
//            fotoReferencia.putFile(u).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
//                @Override
//                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
//                    if(!task.isSuccessful()){
//                        throw task.getException();
//                    }
//                    return fotoReferencia.getDownloadUrl();
//                }
//            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
//                @Override
//                public void onComplete(@NonNull Task<Uri> task) {
//                    if(task.isSuccessful()){
//                        Uri uri = task.getResult();
//                        MensajePersonal mensaje = new MensajePersonal();
//                        mensaje.setMensaje("El usuario ha enviado una imagen");
//                        mensaje.setUrlFoto(uri.toString());
//                        mensaje.setContieneFoto(true);
//                        mensaje.setKeyEmisor(UsuarioDAO.getInstance().getKeyUsuario());
//                        MensajeriaDAO.getInstance().nuevoMensaje(UsuarioDAO.getInstance().getKeyUsuario(),KEY_RECEPTOR,mensaje);
//                    }
//                }
//            });
//        }



    }


