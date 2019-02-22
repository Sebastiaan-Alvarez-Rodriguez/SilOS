package com.sebastiaan.silos.db.entities;

import android.content.Context;

import com.sebastiaan.silos.db.interfaces.DbIDInterface;

public abstract class DbEntity<T extends DbEntity<T>> {
    public abstract DbIDInterface<T> getInterface(Context context);
}
