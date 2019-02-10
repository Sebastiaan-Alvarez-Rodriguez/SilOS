package com.sebastiaan.silos.db.async.task.update;

import android.content.Context;

import com.sebastiaan.silos.db.async.task.AsyncManager;
import com.sebastiaan.silos.db.async.task.DbAsyncTask;
import com.sebastiaan.silos.db.entities.DbEntity;
import com.sebastiaan.silos.db.interfaces.DbInterface;

public class updateTask<T extends DbEntity<T>> extends DbAsyncTask<Void> {
    protected T object;
    protected DbInterface<T> DbInterface;

    public updateTask(AsyncManager manager, Context context, T object) {
        super(manager, context);
        this.object = object;
        DbInterface = object.getInterface(context);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        DbInterface.update(object);
        return null;
    }
}
