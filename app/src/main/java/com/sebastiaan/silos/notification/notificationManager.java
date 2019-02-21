package com.sebastiaan.silos.notification;

import android.app.NotificationManager;
import android.content.Context;

import com.sebastiaan.silos.notification.channels.channel;
import com.sebastiaan.silos.notification.notification.notification;

import static android.content.Context.NOTIFICATION_SERVICE;

public class notificationManager {
    private Context applContext;

    public notificationManager(Context applContext) {
        this.applContext = applContext;
    }

    private void buildNotificationChannel(NotificationManager manager, channel channel) {
        manager.createNotificationChannel(channel.makeChannel());
    }

    private void dispatchNotification(notification notification, int notificationID) {
        NotificationManager manager = ((NotificationManager) (applContext.getSystemService(NOTIFICATION_SERVICE)));
        assert manager != null;

        channel req = notification.getRequiredChannel();
        if (!req.isRegistered(manager)) {
            buildNotificationChannel(manager, req);
        }
        manager.notify(notificationID, notification.create());
        //Dispatch notification
    /*
    buildChannels(applContext);
            Notification n = buildNotificationO(applContext, title, text, heart, Channel_ID);
            NotificationManager notificationManager = (NotificationManager) applContext.getSystemService(NOTIFICATION_SERVICE);
            if (notificationManager != null)
                notificationManager.notify(REQ_code, n);
     */
    }
}
