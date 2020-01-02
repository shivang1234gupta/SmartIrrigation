package com.myapp.android.smartirrigation;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class NotificationHelper {
    private static final String CHANNEL_NAME="Smart Irrigation";
    private static final String CHANNEL_ID="Smart Irrigation";
    private static final String CHANNEL_DESC="Smart Irrigation notification";
    public static void displayNotification(Context context, String text, String title){

        Intent intent=new Intent(context,MainActivity.class);

        PendingIntent pendingIntent=PendingIntent.getActivity(
                context,
                100,
                intent,
                PendingIntent.FLAG_CANCEL_CURRENT
        );

        NotificationCompat.Builder mbuilder=new NotificationCompat.Builder(context,CHANNEL_ID)
                .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark)
                .setContentTitle(title)
                .setContentText(text)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat mNotificationmgr=NotificationManagerCompat.from(context);
        mNotificationmgr.notify(1,mbuilder.build());
    }
}
