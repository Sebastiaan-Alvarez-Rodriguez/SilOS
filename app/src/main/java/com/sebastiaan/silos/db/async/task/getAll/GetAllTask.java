package com.sebastiaan.silos.db.async.task.getAll;

import android.content.Context;

import com.sebastiaan.silos.db.async.task.AsyncManager;
import com.sebastiaan.silos.db.async.task.DbAsyncTask;
import com.sebastiaan.silos.db.entities.DbEntity;
import com.sebastiaan.silos.db.interfaces.DbIDInterface;

import java.util.List;

import androidx.lifecycle.LiveData;

public class GetAllTask<T extends DbEntity<T>> extends DbAsyncTask<LiveData<List<T>>> {
    private DbIDInterface<T> DbInterface;

    public GetAllTask(AsyncManager manager, Context context, T object) {
        super(manager, context);
        DbInterface = object.getInterface(context);
    }

    @Override
    protected LiveData<List<T>> doInBackground(Void... voids) {
        return DbInterface.getAll();
    }
}
