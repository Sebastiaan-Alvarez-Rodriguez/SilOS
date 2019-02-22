package com.sebastiaan.silos.notification.channels;

import android.content.Context;

import com.sebastiaan.silos.R;

import androidx.core.content.res.ResourcesCompat;

public class orderDueChannel extends channel {
    private static final String mID   = "com.sebastiaan.silos.order_due";
    private static final String mName = "Order Due";
    private static final String mDescription = "Notifies you when arrival of orders are due";

    public orderDueChannel(Context mContext) {
        super(mID, mName, mDescription, ResourcesCompat.getColor(mContext.getResources(), R.color.colorPrimary, null));
    }
}
