package com.sebastiaan.silos.db.entities;

import android.content.Context;

import com.sebastiaan.silos.db.interfaces.DbInterface;

public abstract class DbEntity<T extends DbEntity<T>> {
    public abstract DbInterface<T> getInterface(Context context);
}
