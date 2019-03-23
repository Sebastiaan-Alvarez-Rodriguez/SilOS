package com.sebastiaan.silos.notification.channels;

import android.content.Context;

import com.sebastiaan.silos.R;

import androidx.core.content.res.ResourcesCompat;

public class orderSuggestionChannel extends channel{

    private static final String mID   = "com.sebastiaan.silos.order_suggestion";
    private static final String mName = "Order Suggestion";
    private static final String mDescription = "Notifies you when arrival of orders are suggested";

    public orderSuggestionChannel(Context mContext) {
        super(mID, mName, mDescription, ResourcesCompat.getColor(mContext.getResources(), R.color.colorPrimary, null));
    }
}
