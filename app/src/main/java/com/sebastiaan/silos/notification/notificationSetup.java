package com.sebastiaan.silos.notification;

import android.app.NotificationManager;
import android.content.Context;

import com.sebastiaan.silos.notification.channels.channel;
import com.sebastiaan.silos.notification.channels.orderArrivalChannel;
import com.sebastiaan.silos.notification.channels.orderDueChannel;
import com.sebastiaan.silos.notification.channels.orderSuggestionChannel;

import static android.content.Context.NOTIFICATION_SERVICE;

public class notificationSetup {

    public void buildNotificationChannels(Context applContext) {
        channel[] channels = {new orderArrivalChannel(applContext), new orderDueChannel(applContext), new orderSuggestionChannel(applContext)};
        NotificationManager n = ((NotificationManager) (applContext.getSystemService(NOTIFICATION_SERVICE)));
        assert n != null;
        for (channel channel : channels)
            n.createNotificationChannel(channel.makeChannel());
    }
    //Dispatch notification
    /*
    buildChannels(applContext);
            Notification n = buildNotificationO(applContext, title, text, heart, Channel_ID);
            NotificationManager notificationManager = (NotificationManager) applContext.getSystemService(NOTIFICATION_SERVICE);
            if (notificationManager != null)
                notificationManager.notify(REQ_code, n);
     */

    //BUILD notification
    /*
    builder = new NotificationCompat.Builder(applContext, Channel_ID).setContentTitle(title)
                    .setContentText(text).setSmallIcon(R.drawable.ic_heart).setLargeIcon(b)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(text))
                    .setContentIntent(contentIntent)
                    .setAutoCancel(true);
     */
}
