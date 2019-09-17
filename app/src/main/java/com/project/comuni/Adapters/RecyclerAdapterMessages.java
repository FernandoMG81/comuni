package com.project.comuni.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.project.comuni.Activities.MainActivity;
import com.project.comuni.Fragments.InnerMessagesFragment;
import com.project.comuni.Fragments.InnerNoticiasFragment;
import com.project.comuni.Models.Mensaje;
import com.project.comuni.R;

import java.util.ArrayList;

public class RecyclerAdapterMessages extends RecyclerView.Adapter<RecyclerAdapterMessages.ViewHolder>  implements View.OnClickListener {
    private static final String TAG = "RecyclerAdapterMessages";

    private View.OnClickListener listener;
    private ArrayList<Mensaje> mensajes;
    private Context context;

    public RecyclerAdapterMessages(ArrayList<Mensaje> mensajesArray, Context context) {
        this.mensajes = mensajesArray;
        this.context = context;
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

            holder.Contacto.setText(mensajes.get(position).getEmisor().getNombre() + " " + mensajes.get(position).getEmisor().getApellido());
            holder.Mensaje.setText(mensajes.get(position).getTexto());

    }

    @Override
    public int getItemCount() {
        return mensajes.size();
    }

    @Override
    public void onClick(View view) {
        AppCompatActivity activity = (MainActivity) view.getContext();
        Fragment myFragment = new InnerMessagesFragment();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, myFragment).addToBackStack(null).commit();

    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView Contacto;
        TextView Mensaje;
        RelativeLayout RL;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Contacto = itemView.findViewById(R.id.MessagesContacto);
            Mensaje = itemView.findViewById(R.id.MessagesMensaje);
            RL = itemView.findViewById(R.id.RVMesages);
        }
    }
}
