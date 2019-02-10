package com.sebastiaan.silos.db.async.task.insert;

import android.content.Context;

import com.sebastiaan.silos.db.async.task.AsyncManager;
import com.sebastiaan.silos.db.async.task.DbAsyncTask;
import com.sebastiaan.silos.db.entities.DbEntity;
import com.sebastiaan.silos.db.interfaces.DbInterface;

public class insertTask<T extends DbEntity<T>> extends DbAsyncTask<Long> {
    private T object;
    private DbInterface<T> DbInterface;

    public insertTask(AsyncManager manager, Context context, T object) {
        super(manager, context);
        this.object = object;
        this.DbInterface = object.getInterface(context);
    }

    @Override
    protected Long doInBackground(Void... voids) {
        return DbInterface.insert(object);
    }
}
