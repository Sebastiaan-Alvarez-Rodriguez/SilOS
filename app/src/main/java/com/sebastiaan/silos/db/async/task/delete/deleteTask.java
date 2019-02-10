package com.sebastiaan.silos.db.async.task.delete;

import android.content.Context;

import com.sebastiaan.silos.db.async.task.AsyncManager;
import com.sebastiaan.silos.db.async.task.DbAsyncTask;
import com.sebastiaan.silos.db.entities.DbEntity;
import com.sebastiaan.silos.db.interfaces.DbInterface;

import java.util.Collection;

public class deleteTask<T extends DbEntity<T>> extends DbAsyncTask<Void> {
    private T object;
    private DbInterface<T> DbInterface;

    public deleteTask(AsyncManager manager, Context context, T object) {
        super(manager, context);
        this.object = object;
        this.DbInterface = object.getInterface(context);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        DbInterface.delete(object);
        return null;
    }
}
