package com.project.comuni.Adapters.Mensajes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import com.project.comuni.Models.Logica.LMensajePersonal;
import com.project.comuni.Models.Logica.LUser;
import com.project.comuni.Persistencia.UsuarioDAO;
import com.project.comuni.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class RAdapterMensajes extends RecyclerView.Adapter<RAdapterMensajes.HolderMensajes> {

    private List<LMensajePersonal> listaMensajes = new ArrayList<>();
    private Context c;

    public RAdapterMensajes(Context c) {
        this.c = c;
    }

    public int addMensaje(LMensajePersonal lMensaje){
        listaMensajes.add(lMensaje);
        int posicion = listaMensajes.size()-1;
        notifyItemInserted(listaMensajes.size());
        return posicion;
    }

    public void actualizarMensaje(int posicion,LMensajePersonal lMensaje){
        listaMensajes.set(posicion,lMensaje);
        notifyItemChanged(posicion);
    }

    @NonNull
    @Override
    public HolderMensajes onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if(viewType==1){
            view = LayoutInflater.from(c).inflate(R.layout.cardview_mensajes_emisor,parent,false);
        }else{
           view = LayoutInflater.from(c).inflate(R.layout.cardview_mensajes_receptor,parent,false);
        }

        return new HolderMensajes(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderMensajes holder, int position) {

        LMensajePersonal lMensaje = listaMensajes.get(position);
        LUser lUsuario = lMensaje.getlUsuario();

        if(lUsuario!=null){
            holder.getNombre().setText(lUsuario.getUsuario().getNombre());
            Glide.with(c).load(lUsuario.getUsuario().getFotoPerfilURL()).into(holder.getFotoMensajePerfil());
        }


        holder.getMensaje().setText(lMensaje.getMensaje().getMensaje());

        if(lMensaje.getMensaje().isContieneFoto()){
            holder.getFotoMensaje().setVisibility(View.VISIBLE);
            holder.getMensaje().setVisibility(View.VISIBLE);
            Glide.with(c).load(lMensaje.getMensaje().getUrlFoto()).into(holder.getFotoMensaje());
        }else{
            holder.getFotoMensaje().setVisibility(View.GONE);
            holder.getMensaje().setVisibility(View.VISIBLE);
        }

        holder.getHora().setText(lMensaje.fechaCreacionMensaje());
    }

    @Override
    public int getItemCount() {
        return listaMensajes.size();
    }


    @Override
    public int getItemViewType(int position) {
        //return super.getItemViewType(position);
        if(listaMensajes.get(position).getlUsuario() != null){
            if(listaMensajes.get(position).getlUsuario().getKey().equals(UsuarioDAO.getInstance().getKeyUsuario())){
                return 1;
            }else{
                return -1;
            }
        }else{
            return -1;
        }
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


