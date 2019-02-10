package com.sebastiaan.silos.db.async.task.insert;

import android.content.Context;

import com.sebastiaan.silos.db.async.task.AsyncManager;
import com.sebastiaan.silos.db.async.task.DbAsyncTask;
import com.sebastiaan.silos.db.entities.DbEntity;
import com.sebastiaan.silos.db.interfaces.DbInterface;

public class insertAllTask<T extends DbEntity<T>> extends DbAsyncTask<long[]> {
    private T[] objects;
    private DbInterface<T> DbInterface;

    public insertAllTask(AsyncManager manager, Context context, T[] objects) {
        super(manager, context);
        this.objects = objects;
        DbInterface = objects[0].getInterface(context);
    }

    @Override
    protected long[] doInBackground(Void... voids) {
        return DbInterface.insertAll(objects);
    }
}
