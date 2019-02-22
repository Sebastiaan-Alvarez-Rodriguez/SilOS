package com.sebastiaan.silos.db.async.task.find;

import android.content.Context;

import com.sebastiaan.silos.db.async.task.AsyncManager;
import com.sebastiaan.silos.db.async.task.DbAsyncTask;
import com.sebastiaan.silos.db.entities.DbEntityNamed;
import com.sebastiaan.silos.db.interfaces.DbNamedInterface;

public class findByNameExactTask<T extends DbEntityNamed<T>> extends DbAsyncTask<T> {
    protected String name;
    private DbNamedInterface<T> DbInterface;

    public findByNameExactTask(AsyncManager manager, Context context, T object, String name) {
        super(manager, context);
        this.DbInterface = object.getInterface(context);
        this.name = name;
    }

    @Override
    protected T doInBackground(Void... voids) {
        return DbInterface.findByNameExact(name);
    }
}
