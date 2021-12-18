package ar.edu.utn.frsf.isi.dam.reminder.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import ar.edu.utn.frsf.isi.dam.reminder.R;
import ar.edu.utn.frsf.isi.dam.reminder.activity.AltaRecordatorioActivity;

public class RecordatorioReceiver extends BroadcastReceiver {
    public static final String RECORDATORIO = "ar.edu.utn.frsf.isi.dam.reminder.RECORDATORIO";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (RECORDATORIO.equals(intent.getAction())) {
            //String nota = intent.getStringExtra("nota");
            //int i = intent.getIntExtra("int", 1);
            //Bundle b = intent.getExtras();
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, NotificacionService.CHANNEL_ID)
                    .setSmallIcon(R.drawable.outline_notifications_none_24)
                    .setContentTitle("Nota")
                    .setContentText(intent.getStringExtra("nota"))
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setAutoCancel(true);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
            notificationManager.notify(NotificacionService.getNotificacionId(), builder.build());
            //System.out.println("RecordatorioReceiver.onReceive() RECORDATORIO:"+nota);
            //Log.d("nota", intent.getStringExtra("nota"));
        }
        //System.out.println("RecordatorioReceiver.onReceive()");
    }
}
