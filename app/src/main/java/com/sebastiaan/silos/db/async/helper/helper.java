package com.sebastiaan.silos.db.async.helper;

import android.content.Context;

import com.sebastiaan.silos.db.async.DbAsyncInterface;
import com.sebastiaan.silos.db.async.task.AsyncManager;
import com.sebastiaan.silos.db.async.task.delete.deleteAllTask;
import com.sebastiaan.silos.db.async.task.delete.deleteTask;
import com.sebastiaan.silos.db.async.task.find.findByIDTask;
import com.sebastiaan.silos.db.async.task.getAll.getAllTask;
import com.sebastiaan.silos.db.async.task.insert.insertTask;
import com.sebastiaan.silos.db.async.task.update.updateTask;
import com.sebastiaan.silos.db.entities.DbEntity;

import java.util.List;

import androidx.lifecycle.LiveData;

public abstract class helper<T extends DbEntity<T>> {
    protected AsyncManager manager;
    protected Context context;

    public helper(AsyncManager manager, Context context) {
        this.manager = manager;
        this.context = context;
    }

    public void insert(T object, DbAsyncInterface<Long> onFinish) {
        insertTask<T> task = new insertTask<>(manager, context, object);
        task.setCallback(onFinish).execute();
    }

    public void update(T object, DbAsyncInterface<Void> onFinish) {
        updateTask<T> task = new updateTask<>(manager, context, object);
        task.setCallback(onFinish).execute();
    }

    public void deleteAll(T[] objects, DbAsyncInterface<Void> onFinish) {
        deleteAllTask<T> task = new deleteAllTask<>(manager, context, objects);
        task.setCallback(onFinish).execute();
    }

    public void delete(T object, DbAsyncInterface<Void> onFinish) {
        deleteTask<T> task = new deleteTask<>(manager, context, object);
        task.setCallback(onFinish).execute();
    }

    public void findByID(T objectType, long id, DbAsyncInterface<LiveData<T>> onFinish) {
        findByIDTask<T> task = new findByIDTask<>(manager, context, objectType, id);
        task.setCallback(onFinish).execute();
    }

    public void getAll(T objectType, DbAsyncInterface<LiveData<List<T>>> onFinish) {
        getAllTask<T> task = new getAllTask<>(manager, context, objectType);
        task.setCallback(onFinish).execute();
    }
}
