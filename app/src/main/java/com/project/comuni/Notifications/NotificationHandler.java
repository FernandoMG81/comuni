package com.project.comuni.Notifications;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import com.project.comuni.R;

public class NotificationHandler extends ContextWrapper {

    private NotificationManager manager;

    public static final String CHANNEL_HIGH_ID = "1";
    public final String CHANNEL_HIGH_NAME = "HIGH CHANNEL";
    public static final String CHANNEL_LOW_ID = "2";
    public final String CHANNEL_LOW_NAME = "LOW CHANNEL";


    public NotificationHandler(Context context) {
        super(context);
        createChannels();
    }

    public NotificationManager getManager(){
        if(manager==null){
            manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return manager;
    }

    private void createChannels(){
        if(Build.VERSION.SDK_INT >= 26){
            //Creacion del High Channel
            NotificationChannel highChannel = new NotificationChannel(CHANNEL_HIGH_ID, CHANNEL_HIGH_NAME,NotificationManager.IMPORTANCE_HIGH);

            //Extra config
            highChannel.enableLights(true); //Dispositivos con led de avisos
            highChannel.setLightColor(Color.GREEN); //Color de la luz de aviso
            highChannel.setShowBadge(true); //Puntito de aviso en el icono de la app
            highChannel.enableVibration(true); //Vibracion
            //highChannel.setVibrationPattern(new long[]{100,200,300,400,500,400,300,200,400}); //Patron de vibracion
            //Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION); //Audio personalizado
            //highChannel.setSound(defaultSoundUri,null);// audio personalizado
            highChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);

            NotificationChannel lowChannel = new NotificationChannel(CHANNEL_LOW_ID, CHANNEL_LOW_NAME,NotificationManager.IMPORTANCE_LOW);
            lowChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

            getManager().createNotificationChannel(highChannel);
            getManager().createNotificationChannel(lowChannel);
        }
    }

    public Notification.Builder createNotification(String title,String message,boolean isHighImportance){
        if(Build.VERSION.SDK_INT >= 26){
            if(isHighImportance){
                return createNotificationWithChannel(title,message, CHANNEL_HIGH_ID);
            }
            return createNotificationWithChannel(title,message, CHANNEL_LOW_ID);
        }
        return this.createNotificationWithoutChannel(title,message);
    }

    private Notification.Builder createNotificationWithChannel(String title,String message,String channelId){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            return new Notification.Builder(getApplicationContext(), channelId)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setColor(getColor(R.color.colorPrimary))
                    .setSmallIcon(android.R.drawable.stat_notify_chat)
                    .setAutoCancel(true);
        }
        return null;
    }


    private Notification.Builder createNotificationWithoutChannel(String title,String message){
        return new Notification.Builder(getApplicationContext())
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(android.R.drawable.stat_notify_chat)
                .setAutoCancel(true);
    }

}
