package com.project.comuni.Adapters.Espacios;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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

public class RecyclerAdapterAgregarUsuarios extends RecyclerView.Adapter<RecyclerAdapterAgregarUsuarios.ViewHolder> {
    private static final String TAG = "RecyclerAdapterMessages";

    private Go<Espacio> espacio;
    private Go<Usuario> usuarioActual;
    private ArrayList<Go<Usuario>> usuarios = new ArrayList<>();
    private Go<Usuario> usuariox = new Go<>(new Usuario());
    private Context context;
    //Que Hacer?
    // 1 -> Agregar a Admin
    // 2 -> Agregar a Miembros
    private int queHacer;

    public RecyclerAdapterAgregarUsuarios(Context context, Go<Espacio> espacio, ArrayList<Go<Usuario>> usuarios, int queHacer) {
        this.context = context;
        this.espacio = espacio;
        this.usuarios = usuarios;
        this.queHacer = queHacer;
    }

    public void updateDatabase(){
        new EspacioService(espacio).updateSoloEspacio()
                .addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {


                        }
                    }
                });

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
        if(usuarios.get(position).getObject().getApellido()!= null){
            holder.Nombre.setText(usuarios.get(position).getObject().getNombre()
                    + " " + usuarios.get(position).getObject().getApellido());
        }
        else{
            holder.Nombre.setText(usuarios.get(position).getObject().getNombre());
        }

       //Foto
            holder.AgregarButton.setOnClickListener((view) -> {
                this.usuariox = usuarios.get(position);
                new UsuarioService(this.usuariox).getObject()
                        .addListenerForSingleValueEvent(new ValueEventListener() {

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }

                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot x : dataSnapshot.getChildren()) {
                                    Go<Usuario> aux = new Go<>(x.getKey(), new Usuario());
                                    aux.setObject(x.getValue(usuariox.getObject().getClass()));
                                    usuariox = aux;
                                }

                                //usuarios.remove(usuariox);

                                //Si es null inicializar para evitar errores
                                if (!(usuariox.getObject().getAdministradores() != null)) {
                                    usuariox.getObject().setAdministradores(new HashMap<>());
                                }
                                if (!(usuariox.getObject().getMiembros() != null)) {
                                    usuariox.getObject().setMiembros(new HashMap<>());
                                }
                                if(!(espacio.getObject().getMiembros()!=null)) {
                                    espacio.getObject().setMiembros(new HashMap<>());
                                }
                                if(!(espacio.getObject().getAdministradores()!=null)) {
                                    espacio.getObject().setAdministradores(new HashMap<>());
                                }

                                //Guardar en admin sacar de Miembros
                                if (queHacer == 1) {
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
                                    espacio.getObject().getAdministradores().put(usuariox.getKey(), usuariox.getObject());
                                    usuariox.getObject().getAdministradores().put(espacio.getKey(), espacio.getObject().returnSmall());
                                    new UsuarioService(usuariox).updateSoloUsuario().addOnCompleteListener(new OnCompleteListener() {
                                        @Override
                                        public void onComplete(@NonNull Task task) {
                                            String texto = "miembro.";
                                            if(queHacer == 1){
                                                texto = "administrador.";

                                            }
                                            Toast.makeText(context, usuariox.getObject().getNombre() + " " +
                                                    usuariox.getObject().getNombre()
                                                    + " ahora es " + texto, Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    updateDatabase();

                                }


                                //QueHacer 2
                                //Guardar en Miembros sacar de Admins
                                else {
                                    boolean unicoAdministrador = false;
                                    if (espacio.getObject().getAdministradores().size() == 1) {
                                        for (Map.Entry<String, Usuario> x : espacio.getObject().getAdministradores().entrySet()) {
                                            if (x.getKey().equals(usuariox.getKey())) {
                                                unicoAdministrador = true;
                                            }
                                        }
                                    }
                                    if(!unicoAdministrador){
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

                                        espacio.getObject().getMiembros().put(usuariox.getKey(), usuariox.getObject());
                                        usuariox.getObject().getMiembros().put(espacio.getKey(), espacio.getObject().returnSmall());
                                        new UsuarioService(usuariox).updateSoloUsuario().addOnCompleteListener(new OnCompleteListener() {
                                            @Override
                                            public void onComplete(@NonNull Task task) {
                                                String texto = "miembro.";
                                                if(queHacer == 1){
                                                    texto = "administrador.";

                                                }
                                                Toast.makeText(context, usuariox.getObject().getNombre() + " " +
                                                        usuariox.getObject().getNombre()
                                                        + " ahora es " + texto, Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                        updateDatabase();

                                    }
                                }
                            }

                        });
            });
    }

    @Override
    public int getItemCount() {
        return usuarios.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView Nombre;
        //Foto
        Button AgregarButton;
        LinearLayout LL;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Nombre = itemView.findViewById(R.id.RecyclerUsuariosNombre);
            AgregarButton= itemView.findViewById(R.id.RecyclerUsuariosButton);
            LL = itemView.findViewById(R.id.RVUsuarios);
        }
    }
}
