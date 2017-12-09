package com.example.mauri_r95.mimascota10;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by Mauri_R95 on 08-12-2017.
 */
//Recibe mensaje
public class MiFirebaseMessagingService extends FirebaseMessagingService{

    public static final String TAG = "NOTICIAS";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        String from = remoteMessage.getFrom();
        Log.d(TAG, "Mensaje recibido de: "+ remoteMessage.getNotification().getBody());

        if(remoteMessage.getNotification() != null){
            Log.d(TAG, "Notificación: "+remoteMessage.getNotification().getBody());
        }

    }
}
