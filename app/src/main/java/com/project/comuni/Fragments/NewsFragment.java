package com.project.comuni.Fragments;


import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;


import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.project.comuni.Activities.MainActivity;
import com.project.comuni.Adapters.RecyclerAdapterNews;
import com.project.comuni.Models.Noticia;
import com.project.comuni.Persistencia.UsuarioDAO;
import com.project.comuni.R;
import com.project.comuni.Servicios.NoticiaService;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class NewsFragment extends Fragment implements RecyclerAdapterNews.OnItemListener {

    private ArrayList<Noticia> noticias = new ArrayList<>();
    private FloatingActionButton addNewsButton;
    private RecyclerView recyclerNews;
    private Dialog popAddNews;
    private ImageView popupAddBtn;
    private TextView popupTitle, popupDescription;
    ProgressBar popupClickProgress;
    RecyclerView postRecyclerView ;
    RecyclerAdapterNews newsAdapter ;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference ;
    ArrayList<Noticia> newsList;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);

        recyclerNews = view.findViewById(R.id.RVNews);
        addNewsButton = view.findViewById(R.id.newsButton);

        recyclerNews.setLayoutManager(new LinearLayoutManager(getContext()));
        NoticiaService noticiaService= new NoticiaService();

        //Dialog nueva noticia
        iniPopup();

        this.noticias = noticiaService.getNoticias();

        RecyclerAdapterNews adapter = new RecyclerAdapterNews(this.noticias, this.getContext(),this);

        recyclerNews.setAdapter(adapter);

        addNewsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popAddNews.show();
            }
        });

        return view;
    }

/* //TODO RESOLVER EL LISTADO
    @Override
    public void onStart() {
        super.onStart();

        // Get List Posts from the database

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                newsList = new ArrayList<>();
                for (DataSnapshot postsnap: dataSnapshot.getChildren()) {

                    Noticia noticia = postsnap.getValue(Noticia.class);
                    newsList.add(noticia);
                }

                newsAdapter = new RecyclerAdapterNews(newsList,this.getContext(),this);
                postRecyclerView.setAdapter(newsAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

*/

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

                    Noticia noticia = new Noticia(UsuarioDAO.getInstance().getKeyUsuario(),popupTitle.getText().toString(),popupDescription.getText().toString());

                    addNews(noticia);
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
        DatabaseReference reference = database.getReference("Noticias").push();

        String key = reference.getKey();
        noticia.setNewsKey(key);

        reference.setValue(noticia).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getContext(),"Noticia creada exitosamente",Toast.LENGTH_LONG).show();
                popupAddBtn.setVisibility(View.VISIBLE);
                popupClickProgress.setVisibility(View.INVISIBLE);
                popAddNews.dismiss();
            }
        });

    }

}
