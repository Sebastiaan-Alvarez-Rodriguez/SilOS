package com.sebastiaan.silos.notification.notification;

import android.content.Context;

import com.sebastiaan.silos.notification.channels.channel;
import com.sebastiaan.silos.notification.channels.orderArrivalChannel;

public class orderArrivalNotification extends notification {
    public orderArrivalNotification(Context applContext, String title, String text) {
        super(new orderArrivalChannel(applContext), applContext, title, text);
    }

    @Override
    public channel getRequiredChannel() {
        return new orderArrivalChannel(applContext);
    }
}
