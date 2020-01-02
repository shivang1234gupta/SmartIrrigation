package com.myapp.android.smartirrigation;

import android.app.NotificationManager;
import android.content.Context;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class MessagingService extends FirebaseMessagingService {
    private static final String CHANNEL_NAME="Smart Irrigation";
    private static final String CHANNEL_ID="Smart Irrigation";
    private static final String CHANNEL_DESC="Smart Irrigation notification";
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        if (remoteMessage.getNotification()!=null){
            String title=remoteMessage.getNotification().getTitle();
            String body=remoteMessage.getNotification().getBody();

            NotificationHelper.displayNotification(getApplicationContext(),body,title);
        }
    }

}
