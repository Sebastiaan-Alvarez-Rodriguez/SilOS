package com.sebastiaan.silos.db.async.task.delete;

import android.content.Context;

import com.sebastiaan.silos.db.async.task.AsyncManager;
import com.sebastiaan.silos.db.async.task.DbAsyncTask;
import com.sebastiaan.silos.db.entities.DbEntity;
import com.sebastiaan.silos.db.interfaces.DbInterface;

public class deleteAllTask<T extends DbEntity<T>> extends DbAsyncTask<Void> {
    private T[] objects;
    private DbInterface<T> DbInterface;

    public deleteAllTask(AsyncManager manager, Context context, T[] objects) {
        super(manager, context);
        this.objects = objects;
        DbInterface = objects[0].getInterface(context);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        DbInterface.deleteAll(objects);
        return null;
    }
}
