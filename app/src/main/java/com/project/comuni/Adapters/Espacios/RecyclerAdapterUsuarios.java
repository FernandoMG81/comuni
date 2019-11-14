package com.project.comuni.Adapters.Espacios;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.project.comuni.Models.Espacio;
import com.project.comuni.Models.Firebase.Go;
import com.project.comuni.Models.Usuario;
import com.project.comuni.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerAdapterUsuarios extends RecyclerView.Adapter<RecyclerAdapterUsuarios.ViewHolder> {
    private static final String TAG = "RecyclerAdapterMessages";

    private Go<Espacio> espacio;
    private Go<Usuario> usuarioActual;
    private ArrayList<Go<Usuario>> usuarios = new ArrayList<>();
    private Go<Usuario> usuariox;
    private Context context;
    private Boolean administrador;


    public RecyclerAdapterUsuarios(Context context, Go<Espacio> espacio, ArrayList<Go<Usuario>> usuarios, Boolean administrador) {
        this.context = context;
        this.espacio = espacio;
        this.usuarios = usuarios;
        this.administrador = administrador;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_usuarios,null,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: ");

        holder.Nombre.setText(usuarios.get(position).getObject().getNombre()
                + " " + usuarios.get(position).getObject().getApellido());
        Glide.with(context).load(usuarios.get(position).getObject().getFoto()).into(holder.FotoUsuarioCircular);
        if(administrador) {
            holder.LL.setOnClickListener((view) -> {
                        usuariox = usuarios.get(position);
                        Toast.makeText(context, usuariox.getObject().getNombre() + " " +
                                usuariox.getObject().getNombre()
                                + "fue eliminado del espacio.", Toast.LENGTH_SHORT).show();
                        usuarios.remove(usuariox);
                        //eliminar de base de datos
                    }
            );
        }
    }

    @Override
    public int getItemCount() {
        return usuarios.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView Nombre;
        ImageView FotoUsuario;
        CircleImageView FotoUsuarioCircular;
        LinearLayout LL;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Nombre = itemView.findViewById(R.id.civNombre);
            FotoUsuarioCircular = itemView.findViewById(R.id.civImagenPerfil);
            LL = itemView.findViewById(R.id.cardview_layout_usuario);
        }
    }
}
