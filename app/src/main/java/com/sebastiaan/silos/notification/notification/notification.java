package com.sebastiaan.silos.notification.notification;

import android.app.Notification;
import android.content.Context;

import com.sebastiaan.silos.notification.channels.channel;

public abstract class notification {
    private String channelID;
    protected Context applContext;

    private String title;
    private String text;
    private boolean autocancel;

    public notification(channel channel, Context applContext, String title, String text, boolean autocancel) {

        this.channelID = channel.getChannelID();
        this.applContext = applContext;
        this.title = title;
        this.text = text;
        this.autocancel = autocancel;
    }

    public notification(channel channel, Context applContext, String title, String text) {
        this(channel, applContext, title, text, true);
    }

    public Notification create() {
        Notification.Builder builder = new Notification.Builder(applContext, channelID);
        builder.setContentTitle(title)
                .setContentText(text)
//                .setSmallIcon(R.drawable.ic_heart)
//                .setLargeIcon(b)
//                .setStyle(new NotificationCompat.BigTextStyle().bigText(text))
//                .setContentIntent(contentIntent)
                .setAutoCancel(autocancel);
        return builder.build();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isAutocancel() {
        return autocancel;
    }

    public void setAutocancel(boolean autocancel) {
        this.autocancel = autocancel;
    }

    public abstract channel getRequiredChannel();
}
