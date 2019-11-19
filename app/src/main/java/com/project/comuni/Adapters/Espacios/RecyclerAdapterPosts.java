package com.project.comuni.Adapters.Espacios;

import android.app.Dialog;
import android.content.Context;
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
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.project.comuni.Activities.MainActivity;
import com.project.comuni.Fragments.Espacios.InnerPostsFragment;
import com.project.comuni.Models.Espacio;
import com.project.comuni.Models.Firebase.Go;
import com.project.comuni.Models.Post;
import com.project.comuni.Models.Usuario;
import com.project.comuni.R;
import com.project.comuni.Servicios.PostService;
import com.project.comuni.Servicios.UsuarioService;
import com.project.comuni.Utils.PopUp;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerAdapterPosts extends RecyclerView.Adapter<RecyclerAdapterPosts.ViewHolder> implements View.OnClickListener {
    private static final String TAG = "RecyclerAdapterMessages";

    //Variable Recycler
    private ArrayList<Go<Post>> posts;

    //Variables de Bundle
    private Go<Usuario> usuario = new Go<>();
    private Go<Espacio> espacio = new Go<>();
    private Go<Post> post = new Go<>();

    //Otras Variables
    private View.OnClickListener listener;
    private Context context;

    private Dialog popUp;

    public RecyclerAdapterPosts(Context context,Go<Usuario> usuario, Go<Espacio> espacio, ArrayList<Go<Post>> posts) {
        this.context = context;
        this.usuario = usuario;
        this.espacio = espacio;
        this.posts = posts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_posts,null,false);
        ViewHolder holder = new ViewHolder(view);
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: ");
            holder.Titulo.setText(posts.get(position).getObject().getTitulo());
            if(posts.get(position).getObject().getTag() != null) {
                if (posts.get(position).getObject().getTag().getObject()!= null) {
                    holder.Tag.setText(posts.get(position).getObject().getTag().getObject().getText());
                    holder.Tag.setBackgroundColor(Color.parseColor(posts.get(position).getObject().getTag().getObject().ColorB()));
                    holder.Tag.setTextColor(Color.parseColor(posts.get(position).getObject().getTag().getObject().ColorT()));
                }
            }
            holder.Fecha.setText(posts.get(position).getObject().getCreated());
            //String TextoTruncado = truncate(posts.get(position).getObject().getTexto(), 50);
            holder.Descripcion.setText(posts.get(position).getObject().getTexto());
            Glide.with(context).load(posts.get(position).getObject().getUsuario().getObject().getFotoPerfilURL()).into(holder.FotoUsuario);
            holder.NombreUsuario.setText(posts.get(position).getObject().getUsuario().getObject().getNombre()
                    + " " + posts.get(position).getObject().getUsuario().getObject().getApellido());

            holder.RL.setOnClickListener((view) -> {
                this.post = posts.get(position);
                onClick(holder.RL);
            });

        if(new UsuarioService(usuario).isAdmin(espacio)){
            holder.RL.setOnLongClickListener((view -> {
                this.post = posts.get(position);
                setPopUp();
                popUp.show();
                return true;
            }));
        }

    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    @Override
    public void onClick(View view) {
        AppCompatActivity activity = (MainActivity) view.getContext();
        Fragment myFragment = new InnerPostsFragment();
        Bundle args = new Bundle();
        args.putSerializable("usuario",usuario);
        args.putSerializable("espacioActual",espacio);
        args.putSerializable("post",post);
        myFragment.setArguments(args);
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, myFragment).addToBackStack(null).commit();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView Titulo;
        TextView Tag;
        TextView Fecha;
        TextView Descripcion;
        CircleImageView FotoUsuario;
        TextView NombreUsuario;
        RelativeLayout RL;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Fecha = itemView.findViewById(R.id.PlacesFecha);
            Tag = itemView.findViewById(R.id.Tag);
            Titulo = itemView.findViewById(R.id.PlacesTituloPosteo);
            Descripcion = itemView.findViewById(R.id.PlacesDescripcionPosteo);
            RL = itemView.findViewById(R.id.RVPosts);
            FotoUsuario = itemView.findViewById(R.id.PlacesFotoUsuario);
            NombreUsuario = itemView.findViewById(R.id.PlacesUsuario);
        }
    }

    public void setPopUp() {
        popUp = new Dialog(context);
        popUp.setContentView(R.layout.delete_pop_up);
        popUp.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popUp.getWindow().setLayout(Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.WRAP_CONTENT);
        popUp.getWindow().getAttributes().gravity = Gravity.TOP;

        //Inicio de los widgets

        Button BotonSi = popUp.findViewById(R.id.DeleteButtonSi);
        Button BotonNo = popUp.findViewById(R.id.DeleteButtonNo);

        //Agregar Listener
        BotonSi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new PostService(post).delete()
                        .addOnCompleteListener(new OnCompleteListener() {
                            @Override
                            public void onComplete(@NonNull Task task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(context, "El post se borró exitosamente.", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(context, "No se pudo borrar el post.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                popUp.dismiss();
            }
        });

        BotonNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popUp.dismiss();
            }
        });
    }

}
