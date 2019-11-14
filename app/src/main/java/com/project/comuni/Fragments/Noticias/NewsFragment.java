package com.project.comuni.Fragments.Noticias;


import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
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
import com.google.firebase.messaging.FirebaseMessaging;
import com.project.comuni.Adapters.Noticias.RecyclerAdapterNews;
import com.project.comuni.Models.Firebase.Go;
import com.project.comuni.Models.Noticia;
import com.project.comuni.Notifications.NotificationHandler;
import com.project.comuni.R;
import com.project.comuni.Utils.Constantes;

import org.json.JSONObject;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class NewsFragment extends Fragment implements RecyclerAdapterNews.OnItemListener {


    private RecyclerView postRecyclerView ;
    private FloatingActionButton addNewsButton;
    private Dialog popAddNews;
    private ImageView popupAddBtn,imagenCreador;
    private TextView popupTitle, popupDescription;
    private ProgressBar popupClickProgress;
    private RecyclerAdapterNews newsAdapter ;
    private ArrayList<Go<Noticia>> newsList;

    //Variables Firebase
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference ;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;


    //TODO:Notificaciones
    private static final String CHANNEL_ID = "simplified_coding";
    private static final String CHANNEL_NAME = "Simplified Coding";
    private static final String CHANNEL_DESC = "Simplified Coding Notifications";



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);

        /*if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
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

        postRecyclerView = view.findViewById(R.id.RVNews);
        postRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        addNewsButton = view.findViewById(R.id.newsButton);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference(Constantes.NODO_NOTICIAS);

        //TODO: CAMBIAR
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        //Dialog nueva noticia
        iniPopup();

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
    private void displayNotification(){
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getContext(),CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notif_icon)
                .setContentTitle("Titulo de prueba")
                .setContentText("Prueba de notificacion")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationMC = NotificationManagerCompat.from(getContext());
        notificationMC.notify(1,mBuilder.build());
    }


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
                   sendNotifications(popupTitle.getText().toString(), popupDescription.getText().toString());
                   displayNotification();
                }

            }
        });
    }

    private void sendNotifications(String toString, String toString1) {

        // The topic name can be optionally prefixed with "/topics/".
        String topic = "highScores";

        // See documentation on defining a message payload.
        Message message = Message.builder()
                .putData("score", "850")
                .putData("time", "2:45")
                .setTopic(topic)
                .build();

// Send a message to the devices subscribed to the provided topic.
        String response = FirebaseMessaging.getInstance().send(message);
// Response is a message ID string.
        System.out.println("Successfully sent message: " + response);
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



