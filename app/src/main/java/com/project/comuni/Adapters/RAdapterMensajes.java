package com.project.comuni.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import com.project.comuni.Models.MensajeRecibir;
import com.project.comuni.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class RAdapterMensajes extends RecyclerView.Adapter<RAdapterMensajes.HolderMensajes> {

    private List<MensajeRecibir> listaMensajes = new ArrayList<>();
    private Context c;

    public RAdapterMensajes(Context c) {
        this.c = c;
    }

    public void addMensaje(MensajeRecibir mp){
        listaMensajes.add(mp);
        notifyItemInserted(listaMensajes.size());
    }

    @NonNull
    @Override
    public HolderMensajes onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(c).inflate(R.layout.cardview_mensajes,parent,false);

        return new HolderMensajes(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderMensajes holder, int position) {
        holder.getNombre().setText(listaMensajes.get(position).getNombre());
        holder.getMensaje().setText(listaMensajes.get(position).getMensaje());

        if(listaMensajes.get(position).getType_mensaje().equals("2")){
            holder.getFotoMensaje().setVisibility(View.VISIBLE);
            holder.getMensaje().setVisibility(View.VISIBLE);
            Glide.with(c).load(listaMensajes.get(position).getUrlFoto()).into(holder.getFotoMensaje());
        } else if(listaMensajes.get(position).getType_mensaje().equals("1")){
            holder.getFotoMensaje().setVisibility(View.GONE);
            holder.getMensaje().setVisibility(View.VISIBLE);
            }
        if(listaMensajes.get(position).getFotoPerfil().isEmpty()){
            holder.getFotoMensajePerfil().setImageResource(R.mipmap.ic_launcher);
        } else {
            Glide.with(c).load(listaMensajes.get(position).getFotoPerfil()).into(holder.getFotoMensajePerfil());
        }
        Long codigoHora = listaMensajes.get(position).getHora();
        Date d = new Date(codigoHora);
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
        holder.getHora().setText(sdf.format(d));
    }

    @Override
    public int getItemCount() {
        return listaMensajes.size();
    }

    public class HolderMensajes extends RecyclerView.ViewHolder{

        private TextView nombre;
        private TextView mensaje;
        private TextView hora;
        private CircleImageView fotoMensajePerfil;
        private ImageView fotoMensaje;

        public HolderMensajes(@NonNull View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.nombreMensaje);
            mensaje = itemView.findViewById(R.id.mensajeMensaje);
            hora = itemView.findViewById(R.id.horaMensaje);
            fotoMensajePerfil = itemView.findViewById(R.id.fotoPerfilMensaje);
            fotoMensaje = itemView.findViewById(R.id.mensajeFoto);
        }

        public ImageView getFotoMensaje() {
            return fotoMensaje;
        }

        public void setFotoMensaje(ImageView fotoMensaje) {
            this.fotoMensaje = fotoMensaje;
        }

        public TextView getNombre() {
            return nombre;
        }

        public void setNombre(TextView nombre) {
            this.nombre = nombre;
        }

        public TextView getMensaje() {
            return mensaje;
        }

        public void setMensaje(TextView mensaje) {
            this.mensaje = mensaje;
        }

        public TextView getHora() {
            return hora;
        }

        public void setHora(TextView hora) {
            this.hora = hora;
        }

        public CircleImageView getFotoMensajePerfil() {
            return fotoMensajePerfil;
        }

        public void setFotoMensajePerfil(CircleImageView fotoMensajePerfil) {
            this.fotoMensajePerfil = fotoMensajePerfil;
        }
    }

}


