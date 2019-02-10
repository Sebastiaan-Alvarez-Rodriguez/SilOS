package com.sebastiaan.silos.db.async.task.getAll;

import android.content.Context;

import com.sebastiaan.silos.db.async.task.AsyncManager;
import com.sebastiaan.silos.db.async.task.DbAsyncTask;
import com.sebastiaan.silos.db.entities.DbEntity;

import java.util.List;

public abstract class getAllTask<T extends DbEntity<T>> extends DbAsyncTask<List<T>> {
    protected getAllTask(AsyncManager manager, Context context) {
        super(manager, context);
    }
}
