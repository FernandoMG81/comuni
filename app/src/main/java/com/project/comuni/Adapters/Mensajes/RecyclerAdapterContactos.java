package com.project.comuni.Adapters.Mensajes;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.project.comuni.Activities.MensajeriaActivity;
import com.project.comuni.Models.Espacio;
import com.project.comuni.Models.Firebase.Go;
import com.project.comuni.Models.Usuario;
import com.project.comuni.R;
import com.project.comuni.Servicios.EspacioService;
import com.project.comuni.Servicios.UsuarioService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerAdapterContactos extends RecyclerView.Adapter<RecyclerAdapterContactos.ViewHolder> {
    private static final String TAG = "RecyclerAdapterMessages";

    private Go<Usuario> usuarioActual;
    private ArrayList<Go<Usuario>> usuarios = new ArrayList<>();
    private Go<Usuario> usuariox = new Go<>(new Usuario());
    private Context context;

    public RecyclerAdapterContactos(Context context, ArrayList<Go<Usuario>> usuarios) {
        this.context = context;
        this.usuarios = usuarios;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_usuarios,null,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: ");
        if (usuarios.get(position).getObject().getApellido() != null) {
            holder.Nombre.setText(usuarios.get(position).getObject().getNombre()
                    + " " + usuarios.get(position).getObject().getApellido());
        } else {
            holder.Nombre.setText(usuarios.get(position).getObject().getNombre());
        }

        Glide.with(context).load(usuarios.get(position).getObject().getFotoPerfilURL()).into(holder.FotoUsuarioCircular);
        if (position == usuarios.size()) {
            holder.Linea.setVisibility(View.GONE);
        }
        holder.LL.setOnClickListener((view -> {
            usuariox = usuarios.get(position);
            Intent intent = new Intent(context, MensajeriaActivity.class);
            intent.putExtra("key_receptor",usuariox.getKey());
            intent.putExtra("photo_receptor",usuariox.getObject().getFotoPerfilURL());
            intent.putExtra("name_receptor",usuariox.getObject().getNombre());
            context.startActivity(intent);
                })
        );
    }

    @Override
    public int getItemCount() {
        return usuarios.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView Nombre;
        ImageView FotoUsuario;
        CircleImageView FotoUsuarioCircular;
        Button AgregarButton;
        LinearLayout LL;
        TextView Linea;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Nombre = itemView.findViewById(R.id.RecyclerUsuariosNombre);
            AgregarButton= itemView.findViewById(R.id.RecyclerUsuariosButton);
            AgregarButton.setVisibility(View.GONE);
            LL = itemView.findViewById(R.id.RVUsuarios);
            FotoUsuarioCircular = itemView.findViewById(R.id.RecyclerImagenPerfil);
            Linea = itemView.findViewById(R.id.RecyclerUsuariosLinea);

        }
    }
}
