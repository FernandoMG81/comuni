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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
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

public class RecyclerAdapterUsuarios extends RecyclerView.Adapter<RecyclerAdapterUsuarios.ViewHolder> {
    private static final String TAG = "RecyclerAdapterMessages";

    private Go<Espacio> espacio;
    private Go<Usuario> usuarioActual;
    private ArrayList<Go<Usuario>> usuarios = new ArrayList<>();
    private Go<Usuario> usuariox;
    private Context context;
    private Boolean administrador;
    //Que listado es
    // 1 -> Admins
    // 2 -> Miembros
    private int queListado;


    public RecyclerAdapterUsuarios(Context context, Go<Espacio> espacio, ArrayList<Go<Usuario>> usuarios, Boolean administrador, int queListado) {
        this.context = context;
        this.espacio = espacio;
        this.usuarios = usuarios;
        this.administrador = administrador;
        this.queListado = queListado;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_usuarios, null, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: ");

        holder.Nombre.setText(usuarios.get(position).getObject().getNombre()
                + " " + usuarios.get(position).getObject().getApellido());
        Glide.with(context).load(usuarios.get(position).getObject().getFoto()).into(holder.FotoUsuarioCircular);

        if (administrador) {
            holder.LL.setOnLongClickListener((view) -> {
                        usuariox = usuarios.get(position);
                        new UsuarioService(usuariox).getObject()
                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }

                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        for (DataSnapshot x: dataSnapshot.getChildren()) {
                                            Go<Usuario> aux = new Go<>(x.getKey(), new Usuario());
                                            aux.setObject(x.getValue(usuariox.getObject().getClass()));
                                            usuariox = aux;
                                        }

                                        boolean unicoAdministrador = borrarDeListas();
                                        if(!(queListado == 1 & unicoAdministrador == true)) {

                                            for (Go<Usuario> x:usuarios) {
                                                if(x.getKey().equals(usuariox.getKey())){
                                                    usuarios.remove(x);
                                                }
                                            }

                                            new EspacioService(espacio).updateSoloEspacio();
                                            new UsuarioService(usuariox).updateSoloUsuario()
                                                    .addOnCompleteListener(new OnCompleteListener() {
                                                        @Override
                                                        public void onComplete(@NonNull Task task) {
                                                            if (task.isSuccessful()) {
                                                                Toast.makeText(context, usuariox.getObject().getNombre() + " " +
                                                                        usuariox.getObject().getNombre()
                                                                        + "fue eliminado del espacio.", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    });
                                        }
                                        else{
                                            Toast.makeText(context, "No se puede borrar al único administrador", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                        return true;
                    }
            );
        }
    }

    @Override
    public int getItemCount() {
        return usuarios.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

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

    public boolean borrarDeListas() {

        //Si es null inicializar para evitar errores
        if (!(usuariox.getObject().getAdministradores() != null)) {
            usuariox.getObject().setAdministradores(new HashMap<>());
        }
        if (!(usuariox.getObject().getMiembros() != null)) {
            usuariox.getObject().setMiembros(new HashMap<>());
        }
        if (!(espacio.getObject().getMiembros() != null)) {
            espacio.getObject().setMiembros(new HashMap<>());
        }
        if (!(espacio.getObject().getAdministradores() != null)) {
            espacio.getObject().setAdministradores(new HashMap<>());
        }

        for (Map.Entry<String, Usuario> x : espacio.getObject().getMiembros().entrySet()) {
            if (x.getKey().equals(usuariox.getKey())) {
                Map<String, Usuario> aux;
                aux = espacio.getObject().getMiembros();
                aux.remove(x.getKey());
                espacio.getObject().setMiembros(aux);
                break;
            }
        }

        for (Map.Entry<String, Espacio> x : usuariox.getObject().getMiembros().entrySet()) {
            if (x.getKey().equals(espacio.getKey())) {
                Map<String, Espacio> aux;
                aux = usuariox.getObject().getMiembros();
                aux.remove(x.getKey());
                usuariox.getObject().setMiembros(aux);
                break;
            }
        }

        boolean unicoAdministrador = false;
        if (espacio.getObject().getAdministradores().size() == 1) {
            for (Map.Entry<String, Usuario> x : espacio.getObject().getAdministradores().entrySet()) {
                if (x.getKey().equals(usuariox.getKey())) {
                    unicoAdministrador = true;
                }
            }
        }

        if (!unicoAdministrador) {
            for (Map.Entry<String, Usuario> x : espacio.getObject().getAdministradores().entrySet()) {
                if (x.getKey().equals(usuariox.getKey())) {
                    Map<String, Usuario> aux;
                    aux = espacio.getObject().getAdministradores();
                    aux.remove(x.getKey());
                    espacio.getObject().setAdministradores(aux);
                    break;
                }
            }
            for (Map.Entry<String, Espacio> x : usuariox.getObject().getAdministradores().entrySet()) {
                if (x.getKey().equals(espacio.getKey())) {
                    Map<String, Espacio> aux;
                    aux = usuariox.getObject().getAdministradores();
                    aux.remove(x.getKey());
                    usuariox.getObject().setAdministradores(aux);
                    break;
                }
            }
        }
        return unicoAdministrador;
    }
}
