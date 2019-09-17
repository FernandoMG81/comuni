package com.project.comuni.Models.Logica;

import com.project.comuni.Models.Firebase.MensajePersonal;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class LMensajePersonal {

    private String key;
    private MensajePersonal mensaje;
    private LUser lUsuario;

    public LMensajePersonal(String key, MensajePersonal mensaje) {
        this.key = key;
        this.mensaje = mensaje;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public MensajePersonal getMensaje() {
        return mensaje;
    }

    public void setMensaje(MensajePersonal mensaje) {
        this.mensaje = mensaje;
    }

    public long getCreatedTimestampLong (){
        return (long)mensaje.getCreatedTimestamp();
    }

    public LUser getlUsuario() {
        return lUsuario;
    }

    public void setlUsuario(LUser lUsuario) {
        this.lUsuario = lUsuario;
    }

    public String fechaCreacionMensaje(){
        Date date = new Date(getCreatedTimestampLong());
        PrettyTime prettyTime = new PrettyTime(new Date(),Locale.getDefault());
        return prettyTime.format(date);
       /* Date date = new Date(getCreatedTimestampLong());
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a", Locale.getDefault());
        return sdf.format(date);*/
    }
}
