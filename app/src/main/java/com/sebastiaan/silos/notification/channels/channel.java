package com.sebastiaan.silos.notification.channels;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;

public abstract class channel {
    protected final String channelID;
    protected final String channelName;
    protected final String channelDescription;
    protected final boolean lightEnabled;
    protected final int ledColor;
    
    protected final boolean vibrateEnabled;
    
    protected final int lockscreenVisibility;
    protected final int importance;

    public channel(String channelID, String channelName, String channelDescription, boolean lightEnabled, int ledColor, boolean vibrateEnabled, int lockscreenVisibility, int importance) {
        this.channelID = channelID;
        this.channelName = channelName;
        this.channelDescription = channelDescription;
        this.lightEnabled = lightEnabled;
        this.ledColor = ledColor;
        this.vibrateEnabled = vibrateEnabled;
        this.lockscreenVisibility = lockscreenVisibility;
        this.importance = importance;
    }

    public channel(String channelID, String channelName, String channelDescription, int ledColor){
        this(channelID, channelName, channelDescription, true, ledColor, true, Notification.VISIBILITY_PUBLIC, NotificationManager.IMPORTANCE_DEFAULT);
    }

    public NotificationChannel makeChannel() {
        NotificationChannel returnChannel = new NotificationChannel(channelID, channelName, importance);
        returnChannel.setDescription(channelDescription);
        returnChannel.enableLights(lightEnabled);
        returnChannel.setLightColor(ledColor);
        returnChannel.enableVibration(vibrateEnabled);
        returnChannel.setLockscreenVisibility(lockscreenVisibility);
        return returnChannel;
    }

    public String getChannelID() {
        return channelID;
    }

    public boolean isRegistered(Context applContext) {
        return isRegistered((NotificationManager) applContext.getSystemService(Context.NOTIFICATION_SERVICE));
    }

    public boolean isRegistered(NotificationManager manager) {
        return manager != null && manager.getNotificationChannel(channelID) != null;
    }
}