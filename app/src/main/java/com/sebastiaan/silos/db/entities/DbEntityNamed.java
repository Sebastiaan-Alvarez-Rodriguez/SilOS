package com.sebastiaan.silos.db.entities;

import android.content.Context;

import com.sebastiaan.silos.db.interfaces.DbNamedInterface;

public abstract class DbEntityNamed<T extends DbEntityNamed<T>> extends DbEntity<T> {
    protected String name;

    public DbEntityNamed(String name) {
        this.name = name;
    }

    @Override
    public abstract DbNamedInterface<T> getInterface(Context context);

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
