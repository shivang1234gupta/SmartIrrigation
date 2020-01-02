package com.myapp.android.smartirrigation;

import android.content.Context;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class NotificationHelper {
    private static final String CHANNEL_NAME="Smart Irrigation";
    private static final String CHANNEL_ID="Smart Irrigation";
    private static final String CHANNEL_DESC="Smart Irrigation notification";
    public static void displayNotification(Context context, String text, String title){
        NotificationCompat.Builder mbuilder=new NotificationCompat.Builder(context,CHANNEL_ID)
                .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark)
                .setContentTitle(title)
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat mNotificationmgr=NotificationManagerCompat.from(context);
        mNotificationmgr.notify(1,mbuilder.build());
    }
}
