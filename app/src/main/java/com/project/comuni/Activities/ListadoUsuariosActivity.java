package com.project.comuni.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import com.project.comuni.Models.Firebase.User;
import com.project.comuni.Models.Logica.LUser;
import com.project.comuni.R;

import static com.project.comuni.Utils.Constantes.NODO_USUARIOS;

public class ListadoUsuariosActivity extends AppCompatActivity {

    private RecyclerView rvUsuarios;
    private FirebaseRecyclerAdapter adapter;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_usuarios);

        rvUsuarios = findViewById(R.id.rvUsuarios);
        linearLayoutManager = new LinearLayoutManager(this);
        rvUsuarios.setLayoutManager(linearLayoutManager);


        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child(NODO_USUARIOS)
                .orderByChild("nombre")
                ; //Paginacion.


        FirebaseRecyclerOptions<User> options =
                new FirebaseRecyclerOptions.Builder<User>()
                        .setQuery(query, User.class)
                        .build();

        adapter = new FirebaseRecyclerAdapter<User, UserViewHolder>(options) {
            @Override
            public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                // Create a new instance of the ViewHolder, in this case we are using a custom
                // layout called R.layout.message for each item
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.cardview_usuarios, parent, false);

                return new UserViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(UserViewHolder holder, int position, User model) {
                // Bind the Chat object to the ChatHolder
                Glide.with(ListadoUsuariosActivity.this).load(model.getFotoPerfilURL()).into(holder.getCivFotoPerfil());
                holder.getCivNombre().setText(model.getNombre());

                LUser lUsuario = new LUser(getSnapshots().getSnapshot(position).getKey(),model);

                holder.getLinearLayout().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ListadoUsuariosActivity.this, MensajeriaActivity.class);
                        intent.putExtra("key_receptor",lUsuario.getKey());
                        intent.putExtra("photo_receptor",model.getFotoPerfilURL());
                        intent.putExtra("name_receptor",model.getNombre());
                        startActivity(intent);
                    }
                });

            }
        };
        rvUsuarios.setAdapter(adapter);

    }


    public class UserViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView civFotoPerfil;
        private TextView civNombre;
        private LinearLayout linearLayout;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            civFotoPerfil = itemView.findViewById(R.id.civImagenPerfil);
            civNombre = itemView.findViewById(R.id.civNombre);
            linearLayout = itemView.findViewById(R.id.cardview_layout_usuario);
        }

        public LinearLayout getLinearLayout() {
            return linearLayout;
        }

        public void setLinearLayout(LinearLayout linearLayout) {
            this.linearLayout = linearLayout;
        }

        public CircleImageView getCivFotoPerfil() {
            return civFotoPerfil;
        }

        public void setCivFotoPerfil(CircleImageView civFotoPerfil) {
            this.civFotoPerfil = civFotoPerfil;
        }

        public TextView getCivNombre() {
            return civNombre;
        }

        public void setCivNombre(TextView civNombre) {
            this.civNombre = civNombre;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }


}
