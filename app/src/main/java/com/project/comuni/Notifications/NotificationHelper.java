package com.project.comuni.Notifications;

import android.content.Context;

import com.project.comuni.R;
import com.project.comuni.Utils.Constantes;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;



public class NotificationHelper {


    public static void displayNotification(Context context, String titulo, String texto){
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, Constantes.CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notif_icon)
                .setContentTitle(titulo)
                .setContentText(texto)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationMC = NotificationManagerCompat.from(context);
        notificationMC.notify(1,mBuilder.build());
    }

}
