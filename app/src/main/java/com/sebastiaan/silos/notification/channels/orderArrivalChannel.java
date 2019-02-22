package com.sebastiaan.silos.notification.channels;

import android.content.Context;

import com.sebastiaan.silos.R;

import androidx.core.content.res.ResourcesCompat;

public class orderArrivalChannel extends channel {

    private static final String mID   = "com.sebastiaan.silos.order_arrival";
    private static final String mName = "Order Arrival";
    private static final String mDescription = "Notifies you when arrival of orders are expected";

    public orderArrivalChannel(Context mContext) {
        super(mID, mName, mDescription, ResourcesCompat.getColor(mContext.getResources(), R.color.colorPrimary, null));
    }
}
