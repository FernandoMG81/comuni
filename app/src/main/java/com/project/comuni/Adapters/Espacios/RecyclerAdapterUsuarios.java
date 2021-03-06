package com.project.comuni.Adapters.Espacios;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
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
import com.project.comuni.Utils.Constantes;

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

    private Dialog popUp;


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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_usuarios, null, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: ");

        holder.AgregarButton.setVisibility(View.GONE);
        holder.Nombre.setText(usuarios.get(position).getObject().getNombre()
                + " " + usuarios.get(position).getObject().getApellido());

        if(usuarios.get(position).getObject().getFotoPerfilURL()!= null) {
            Glide.with(context).load(usuarios.get(position).getObject().getFotoPerfilURL()).into(holder.FotoUsuarioCircular);
        }else {
            Glide.with(context).load(Constantes.URL_FOTO_POR_DEFECTO_USUARIOS).into(holder.FotoUsuarioCircular);
        }

        if(position == usuarios.size()){
            holder.Linea.setVisibility(View.GONE);
        }

        if (administrador) {
            holder.LL.setOnLongClickListener((view) -> {
                        usuariox = usuarios.get(position);
                        setPopUp();
                        popUp.show();
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
        Button AgregarButton;
        TextView Linea;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Nombre = itemView.findViewById(R.id.RecyclerUsuariosNombre);
            AgregarButton= itemView.findViewById(R.id.RecyclerUsuariosButton);
            LL = itemView.findViewById(R.id.RVUsuarios);
            FotoUsuarioCircular = itemView.findViewById(R.id.RecyclerImagenPerfil);
            Linea = itemView.findViewById(R.id.RecyclerUsuariosLinea);

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
                delete();
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

    public void delete(){
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
                                    notifyDataSetChanged();
                                    break;
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
    }
}
