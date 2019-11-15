package com.project.comuni.Adapters.Mensajes;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.project.comuni.Activities.MainActivity;
import com.project.comuni.Fragments.Mensajes.InnerMessagesFragment;
import com.project.comuni.Models.Firebase.Go;
import com.project.comuni.Models.Mensaje;
import com.project.comuni.Models.Usuario;
import com.project.comuni.R;

import java.io.Serializable;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerAdapterMessages extends RecyclerView.Adapter<RecyclerAdapterMessages.ViewHolder>  implements View.OnClickListener {
    private static final String TAG = "RecyclerAdapterMessages";

    private Go<Usuario> usuario;
    private Go<Mensaje> mensaje;
    private ArrayList<Go<Mensaje>> mensajes;
    private Context context;

    public RecyclerAdapterMessages(ArrayList<Go<Mensaje>> mensajesArray, Context context,Go<Usuario> usuario) {
        this.mensajes = mensajesArray;
        this.context = context;
        this.usuario = usuario;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_messages,null,false);
        ViewHolder holder = new ViewHolder(view);
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: ");

        holder.Mensaje.setText(mensajes.get(position).getObject().getTexto());
           if(usuario.getKey().equals(mensajes.get(position).getObject().getEmisor().getKey())){
               holder.Contacto.setText(mensajes.get(position).getObject().getReceptor().getObject().getNombre()
                       + " " + mensajes.get(position).getObject().getReceptor().getObject().getApellido());
               Glide.with(context).load(mensajes.get(position).getObject().getReceptor().getObject().getFotoPerfilURL()).into(holder.FotoUsuarioCircular);
               holder.Mensaje.setTypeface(null, Typeface.ITALIC);
           }
           else{
               holder.Contacto.setText(mensajes.get(position).getObject().getReceptor().getObject().getNombre()
                       + " " + mensajes.get(position).getObject().getReceptor().getObject().getApellido());
               Glide.with(context).load(mensajes.get(position).getObject().getEmisor().getObject().getFotoPerfilURL()).into(holder.FotoUsuarioCircular);
           }

        holder.RL.setOnClickListener((view)->{
                mensaje = mensajes.get(position);
                onClick(view);
            });
    }

    @Override
    public int getItemCount() {
        return mensajes.size();
    }

    @Override
    public void onClick(View view) {
        AppCompatActivity activity = (MainActivity) view.getContext();
        Fragment myFragment = new InnerMessagesFragment();
        Bundle args = new Bundle();
        args.putSerializable("mensaje",(Serializable) mensaje);
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, myFragment).addToBackStack(null).commit();

    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView Contacto;
        TextView Mensaje;
        CircleImageView FotoUsuarioCircular;
        RelativeLayout RL;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Contacto = itemView.findViewById(R.id.MessagesContacto);
            Mensaje = itemView.findViewById(R.id.MessagesMensaje);
            RL = itemView.findViewById(R.id.RVMesages);
            FotoUsuarioCircular = itemView.findViewById(R.id.MessagesFotoUsuario);
        }
    }
}
