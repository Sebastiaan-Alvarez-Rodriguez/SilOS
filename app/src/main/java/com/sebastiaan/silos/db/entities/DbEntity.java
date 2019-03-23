package com.sebastiaan.silos.db.entities;

import android.content.Context;

import com.sebastiaan.silos.db.interfaces.DbIDInterface;

import androidx.room.PrimaryKey;

public abstract class DbEntity<T extends DbEntity<T>> {
    @PrimaryKey(autoGenerate = true)
    protected long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public abstract DbIDInterface<T> getInterface(Context context);
}
