package com.project.comuni.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.Manifest;
import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.project.comuni.Adapters.Mensajes.RAdapterMensajes;

import com.project.comuni.Models.Firebase.Go;
import com.project.comuni.Models.Firebase.MensajePersonal;
import com.project.comuni.Models.Logica.LMensajePersonal;
import com.project.comuni.Models.Logica.LUser;
import com.project.comuni.Models.Usuario;
import com.project.comuni.Notifications.ApiNotification;
import com.project.comuni.Persistencia.MensajeriaDAO;
import com.project.comuni.Persistencia.UsuarioDAO;
import com.project.comuni.R;
import com.project.comuni.Servicios.LoginService;
import com.project.comuni.Servicios.UsuarioService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.project.comuni.Utils.Constantes.NODO_MENSAJES;
import static com.project.comuni.Utils.Util.truncate;

public class MensajeriaActivity extends AppCompatActivity {

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

    //Notificaciones
    private static final String CHANNEL_ID = "simplified_coding";
    private static final String CHANNEL_NAME = "Simplified Coding";
    private static final String CHANNEL_DESC = "Simplified Coding Notifications";

    private FirebaseStorage storage;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mensajeria);

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            KEY_RECEPTOR = bundle.getString("key_receptor");
            PHOTO_RECEPTOR = bundle.getString("photo_receptor");
            NAME_RECEPTOR = bundle.getString("name_receptor");
        }else{
            finish();
        }
        fotoPerfil = findViewById(R.id.bubbleFotoPerfil);
        Glide.with(MensajeriaActivity.this).load(PHOTO_RECEPTOR).into(fotoPerfil);
        txtNombre = findViewById(R.id.bubbleNombreUsuario);
        txtNombre.setText(NAME_RECEPTOR);
        rvMensajes = findViewById(R.id.bubbleRV);
        txtMensaje = findViewById(R.id.bubbleMensaje);
        btnEnviar = findViewById(R.id.bubbleEnviar);
        btnEnviarFoto = findViewById(R.id.bubbleEnviarImagen);
        fotoPerfilCadena = "";

        storage = FirebaseStorage.getInstance();
        mAuth = FirebaseAuth.getInstance();


        adapter = new RAdapterMensajes(this);
        LinearLayoutManager l = new LinearLayoutManager(this);
        rvMensajes.setLayoutManager(l);
        rvMensajes.setAdapter(adapter);

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
                    Go<Usuario> usuarioGo = new Go<>(KEY_RECEPTOR, new Usuario());
                    new UsuarioService(usuarioGo).getObject().addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot x:dataSnapshot.getChildren()) {
                                String usuarioEmisor = mAuth.getCurrentUser().getDisplayName();
                                String tokenReceptor = x.getValue(usuarioGo.getObject().getClass()).getToken();
                                sendNotificationWithToken(usuarioEmisor, truncate(mensajeEnviar,20),tokenReceptor);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    SendNotification();
                    txtMensaje.setText("");
                }
            }
        });

        btnEnviarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent (Intent.ACTION_GET_CONTENT);
                i.setType("image/jpeg");
                i.putExtra(Intent.EXTRA_LOCAL_ONLY,true);
                startActivityForResult(Intent.createChooser(i,"Selecciona una imagen"),PHOTO_SEND);
            }
        });

       /* fotoPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent (Intent.ACTION_GET_CONTENT);
                i.setType("image/jpeg");
                i.putExtra(Intent.EXTRA_LOCAL_ONLY,true);
                startActivityForResult(Intent.createChooser(i,"Selecciona una imagen"),PHOTO_PERFIL);
            }
        });*/
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                setScrollbar();
            }
        });

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
                            Toast.makeText(MensajeriaActivity.this,"Error "+error,Toast.LENGTH_LONG).show();
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

        verifyStoragePermissions(this);

    }

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



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PHOTO_SEND && resultCode == RESULT_OK){
            Uri u = data.getData();
            storageReference = storage.getReference("imagenes_chat");//imagenes_chat
            final StorageReference fotoReferencia = storageReference.child(u.getLastPathSegment());
            fotoReferencia.putFile(u).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if(!task.isSuccessful()){
                        throw task.getException();
                    }
                    return fotoReferencia.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if(task.isSuccessful()){
                        Uri uri = task.getResult();
                        MensajePersonal mensaje = new MensajePersonal();
                        mensaje.setMensaje("El usuario ha enviado una imagen");
                        mensaje.setUrlFoto(uri.toString());
                        mensaje.setContieneFoto(true);
                        mensaje.setKeyEmisor(UsuarioDAO.getInstance().getKeyUsuario());
                        MensajeriaDAO.getInstance().nuevoMensaje(UsuarioDAO.getInstance().getKeyUsuario(),KEY_RECEPTOR,mensaje);
                    }
                }
            });
        }/*else if(requestCode == PHOTO_PERFIL && resultCode == RESULT_OK){
            Uri u = data.getData();
            storageReference = storage.getReference("foto_perfil");//imagenes_chat
            final StorageReference fotoReferencia = storageReference.child(u.getLastPathSegment());
            fotoReferencia.putFile(u).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if(!task.isSuccessful()){
                        throw task.getException();
                    }
                    return fotoReferencia.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if(task.isSuccessful()){
                        Uri uri = task.getResult();
                        fotoPerfilCadena = uri.toString();
                        MensajeEnviar m = new MensajeEnviar(NOMBRE_USUARIO+" ha actualizado su foto de perfil",uri.toString(),NOMBRE_USUARIO,fotoPerfilCadena,"2",ServerValue.TIMESTAMP);
                        databaseReference.push().setValue(m);
                        Glide.with(MensajeriaActivity.this).load(uri.toString()).into(fotoPerfil);
                    }
                }
            });
        }*/
    }
/*
    @Override
    protected void onResume() {
        super.onResume();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser!=null){
            btnEnviar.setEnabled(false);
            DatabaseReference reference = database.getReference("Usuarios/"+currentUser.getUid());
            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    User usuario = dataSnapshot.getValue(User.class);
                    NOMBRE_USUARIO = usuario.getNombre();
                    txtNombre.setText(NOMBRE_USUARIO);
                    btnEnviar.setEnabled(true);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }else{
            startActivity(new Intent(MensajeriaActivity.this,LoginActivity.class));
            finish();
        }
    }
*/


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_mensajeria);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,CHANNEL_NAME,NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(CHANNEL_DESC);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }



    }

    private void sendNotificationWithToken(String title, String body, String token) {


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://comuniapp-316d5.firebaseapp.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiNotification api = retrofit.create(ApiNotification.class);

        Call<ResponseBody> call = api.sendNotification(token, title, body);
        call.enqueue(new Callback<ResponseBody>() {
              @Override
              public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                //Toast.makeText(MensajeriaActivity.this, response.body().toString(), Toast.LENGTH_LONG).show();
                }
              @Override
              public void onFailure(Call<ResponseBody> call, Throwable t) {

              }
            });
    }



    private void displayNotification(){
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this,CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notif_icon)
                .setContentTitle("Titulo de prueba")
                .setContentText("Prueba de notificacion")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationMC = NotificationManagerCompat.from(this);
        notificationMC.notify(1,mBuilder.build());
    }




}
