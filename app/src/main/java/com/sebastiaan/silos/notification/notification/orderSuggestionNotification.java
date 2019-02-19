package com.sebastiaan.silos.notification.notification;

import android.content.Context;

import com.sebastiaan.silos.notification.channels.orderSuggestionChannel;

public class orderSuggestionNotification extends notification {
    public orderSuggestionNotification(Context applContext, String title, String text) {
        super(new orderSuggestionChannel(applContext), applContext, title, text);
    }
}
