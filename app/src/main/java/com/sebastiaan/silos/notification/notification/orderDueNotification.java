package com.sebastiaan.silos.notification.notification;

import android.content.Context;

import com.sebastiaan.silos.notification.channels.orderDueChannel;

public class orderDueNotification extends notification {
    public orderDueNotification(Context applContext, String title, String text) {
        super(new orderDueChannel(applContext), applContext, title, text);
    }
}
