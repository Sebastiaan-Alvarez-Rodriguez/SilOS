package com.sebastiaan.silos.db.async.task.find;

import android.content.Context;

import com.sebastiaan.silos.db.async.task.AsyncManager;
import com.sebastiaan.silos.db.async.task.DbAsyncTask;
import com.sebastiaan.silos.db.entities.DbEntity;

public abstract class findTask<T extends DbEntity<T>> extends DbAsyncTask<T> {
    protected String name;

    protected findTask(AsyncManager manager, Context context, String name) {
        super(manager, context);
        this.name = name;

    }
}
