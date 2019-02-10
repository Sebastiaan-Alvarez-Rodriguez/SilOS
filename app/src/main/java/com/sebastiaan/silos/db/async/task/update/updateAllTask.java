package com.sebastiaan.silos.db.async.task.update;

import android.content.Context;

import com.sebastiaan.silos.db.async.task.AsyncManager;
import com.sebastiaan.silos.db.async.task.DbAsyncTask;
import com.sebastiaan.silos.db.entities.DbEntity;
import com.sebastiaan.silos.db.interfaces.DbInterface;

public class updateAllTask <T extends DbEntity<T>> extends DbAsyncTask<Void> {
    protected T[] objects;
    protected DbInterface<T> DbInterface;

    protected updateAllTask(AsyncManager manager, Context context, T[] objects) {
        super(manager, context);
        this.objects = objects;
        DbInterface = objects[0].getInterface(context);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        DbInterface.updateAll(objects);
        return null;
    }
}
