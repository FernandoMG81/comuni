package com.project.comuni.Fragments.Noticias;


import com.google.firebase.messaging.FirebaseMessaging;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;


import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.project.comuni.Adapters.Noticias.RecyclerAdapterNews;
import com.project.comuni.Models.Firebase.Go;
import com.project.comuni.Models.Noticia;
import com.project.comuni.Models.Usuario;
import com.project.comuni.Notifications.ApiNotification;
import com.project.comuni.R;
import com.project.comuni.Servicios.LoginService;
import com.project.comuni.Servicios.UsuarioService;
import com.project.comuni.Utils.Constantes;

import java.util.ArrayList;
import java.util.Collections;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static androidx.constraintlayout.widget.Constraints.TAG;
import static com.project.comuni.Utils.Util.filtrarString;
import static com.project.comuni.Utils.Util.truncate;

public class NewsFragment extends Fragment implements RecyclerAdapterNews.OnItemListener {


    private RecyclerView postRecyclerView ;
    private FloatingActionButton addNewsButton;
    private Dialog popAddNews;
    private ImageView popupAddBtn,imagenCreador;
    private TextView popupTitle, popupDescription;
    private ProgressBar popupClickProgress;
    private RecyclerAdapterNews newsAdapter ;
    private ProgressBar progressBar;
    private ArrayList<Go<Noticia>> newsList;
    private ArrayList<Go<Noticia>> noticiasAMostrar = new ArrayList<>();


    //Variables Filtrado
    private String searchText = "";
    private EditText search;

    //Variables Firebase
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference ;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;


    //TODO:Notificaciones
    private static final String CHANNEL_ID = "simplified_coding";
    private static final String CHANNEL_NAME = "Simplified Coding";
    private static final String CHANNEL_DESC = "Simplified Coding Notifications";

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
                noticiasAMostrar.clear();
                for (Go<Noticia> x: newsList){
                    if (filtrarString(x.getObject().getTitulo(), searchText) ||
                            filtrarString (x.getObject().getTexto(), searchText) ||
                                    filtrarString (x.getObject().getNombre(), searchText))
                    {
                        noticiasAMostrar.add(x);
                    }
                }
                newsAdapter = new RecyclerAdapterNews(noticiasAMostrar,getContext());
                postRecyclerView.setAdapter(newsAdapter);
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);

        /*if(Build.VERSION.SDK_INTnewsAdapter = new RecyclerAdapterNews(newsList,getContext());
                if(newsAdapter.getItemCount()!=0){
                    postRecyclerView.setAdapter(newsAdapter); >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(CHANNEL_DESC);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }*/

        //TODO:Notificaciones
        FirebaseMessaging.getInstance().subscribeToTopic("Noticias");
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                       if(task.isSuccessful()){
                            String token = task.getResult().getToken();
                       }else{

                       }
                    }
                });

        search = view.findViewById(R.id.postNewsSearch);
        postRecyclerView = view.findViewById(R.id.RVNews);
        postRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        addNewsButton = view.findViewById(R.id.newsButton);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference(Constantes.NODO_NOTICIAS);
        progressBar = view.findViewById(R.id.newsProgressbar);
        progressBar.setVisibility(View.VISIBLE);

        //TODO: CAMBIAR
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        //Dialog nueva noticia
        iniPopup();
        setSearch();

        addNewsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Glide.with(getContext()).load(currentUser.getPhotoUrl()).into(imagenCreador);
                popAddNews.show();
            }
        });

        return view;

    }

    //TODO:Notificaciones
    /*private void displayNotification(){
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getContext(),CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notif_icon)
                .setContentTitle("Titulo de prueba")
                .setContentText("Prueba de notificacion")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationMC = NotificationManagerCompat.from(getContext());
        notificationMC.notify(1,mBuilder.build());
    }*/


    @Override
    public void onStart() {
        super.onStart();

        // Obtener el listado de noticias de la base de datos

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                newsList = new ArrayList<>();

                for (DataSnapshot postsnap: dataSnapshot.getChildren()) {

                    Go<Noticia> noticia = new Go<>(postsnap.getKey(),postsnap.getValue(Noticia.class));
                    newsList.add(noticia);
                }


                Collections.reverse(newsList);
                newsAdapter = new RecyclerAdapterNews(newsList,getContext());
                if(newsAdapter.getItemCount()!=0){
                    postRecyclerView.setAdapter(newsAdapter);
                }

                progressBar.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }



    private void iniPopup() {
        popAddNews = new Dialog(getContext());
        popAddNews.setContentView(R.layout.fragment_add_news);
        popAddNews.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popAddNews.getWindow().setLayout(Toolbar.LayoutParams.MATCH_PARENT,Toolbar.LayoutParams.WRAP_CONTENT);
        popAddNews.getWindow().getAttributes().gravity = Gravity.TOP;

        //Inicio de los widgets

        popupTitle = popAddNews.findViewById(R.id.createNewsTitulo);
        popupDescription = popAddNews.findViewById(R.id.createNewsDescripción);
        popupClickProgress = popAddNews.findViewById(R.id.createProgressBarNews);
        popupAddBtn = popAddNews.findViewById(R.id.createPostNews);
        imagenCreador = popAddNews.findViewById(R.id.createImagenCreador);

        //Agregar Noticia Listener

        popupAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(popupTitle.getText().toString().isEmpty() && popupDescription.getText().toString().isEmpty()){
                    Toast.makeText(getContext(),"Complete los dos campos para publicar",Toast.LENGTH_LONG).show();
                    popupAddBtn.setVisibility(View.VISIBLE);
                    popupClickProgress.setVisibility(View.INVISIBLE);
                }else if(!popupTitle.getText().toString().isEmpty() && popupDescription.getText().toString().isEmpty()){
                    Toast.makeText(getContext(),"Falta la descripción",Toast.LENGTH_LONG).show();
                    popupAddBtn.setVisibility(View.VISIBLE);
                    popupClickProgress.setVisibility(View.INVISIBLE);
                }else if(!popupDescription.getText().toString().isEmpty() && popupTitle.getText().toString().isEmpty()){
                    Toast.makeText(getContext(),"Falta el título",Toast.LENGTH_LONG).show();
                    popupAddBtn.setVisibility(View.VISIBLE);
                    popupClickProgress.setVisibility(View.INVISIBLE);
                }else{
                    popupAddBtn.setVisibility(View.INVISIBLE);
                    popupClickProgress.setVisibility(View.VISIBLE);


                   Noticia noticia = new Noticia(currentUser.getDisplayName()
                                                 ,currentUser.getPhotoUrl().toString()
                                                 ,popupTitle.getText().toString()
                                                 ,popupDescription.getText().toString()
                                                 ,currentUser.getUid());
                   addNews(noticia);
                   sendNotifications(popupTitle.getText().toString(), truncate(popupDescription.getText().toString(),20));
                }

            }
        });
    }

    private void sendNotifications(String title, String body) {


    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://comuniapp-316d5.firebaseapp.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
        ArrayList<Go<Usuario>> usuarios = new ArrayList<>();
        Go<Usuario> usuario = new Go<>(new Usuario());
        ArrayList<String> lista = new ArrayList<>();

        new UsuarioService().getAll()
                .addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        lista.clear();
                        String userActual = new LoginService().getUser().getUid();
                        for (DataSnapshot x:dataSnapshot.getChildren() )
                        {
                         Go<Usuario> usuariox = new Go<>(new Usuario());
                         usuariox.setObject(x.getValue(usuariox.getObject().getClass()));
                         if(!userActual.equals(x.getKey())){
                             lista.add(usuariox.getObject().getToken());
                         }
                        }

                        ApiNotification api = retrofit.create(ApiNotification.class);
                        for(String token: lista) {

                            Call<ResponseBody> call = api.sendNotification(token, title, body);
                            call.enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                    //Toast.makeText(getContext(), response.body().toString(), Toast.LENGTH_LONG).show();
                                }

                                @Override
                                public void onFailure(Call<ResponseBody> call, Throwable t) {

                                }
                            });
                        }

                    }

                });


    }

    @Override
    public void onItemClick(int position) {
        Log.d(TAG, "onItemClick: Clicked.");
    }

    private void addNews (Noticia noticia){

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference(Constantes.NODO_NOTICIAS).push();

        String key = reference.getKey();
        noticia.setNewsKey(key);

        reference.setValue(noticia).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getContext(),"Noticia creada exitosamente",Toast.LENGTH_LONG).show();
                popupAddBtn.setVisibility(View.VISIBLE);
                popupClickProgress.setVisibility(View.INVISIBLE);
                popupTitle.setText("");
                popupDescription.setText("");
                popAddNews.dismiss();


            }
        });

    }
}



