package com.sebastiaan.silos.db.async.task.find;

import android.content.Context;

import com.sebastiaan.silos.db.async.task.AsyncManager;
import com.sebastiaan.silos.db.async.task.DbAsyncTask;
import com.sebastiaan.silos.db.entities.DbEntity;
import com.sebastiaan.silos.db.interfaces.DbIDInterface;

import androidx.lifecycle.LiveData;

public class findByIDTask<T extends DbEntity<T>> extends DbAsyncTask<LiveData<T>> {
    protected long id;
    private DbIDInterface<T> DbInterface;

    public findByIDTask(AsyncManager manager, Context context, T object, long id) {
        super(manager, context);
        DbInterface = object.getInterface(context);
        this.id = id;
    }

    @Override
    protected LiveData<T> doInBackground(Void... voids) {
        return DbInterface.findByID(id);
    }
}
