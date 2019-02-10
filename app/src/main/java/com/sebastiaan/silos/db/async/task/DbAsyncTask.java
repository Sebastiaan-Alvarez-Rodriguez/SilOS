package com.sebastiaan.silos.db.async.task;

import android.content.Context;
import android.os.AsyncTask;

import com.sebastiaan.silos.db.AppDatabase;
import com.sebastiaan.silos.db.async.DbAsyncInterface;

public abstract class DbAsyncTask<T> extends AsyncTask<Void, Void, T> {
    protected static AppDatabase database = null;
    protected DbAsyncInterface<T> Dbcallback;
    private AsyncManager manager;

    protected DbAsyncTask(AsyncManager manager, Context context) {
        this.manager = manager;
        database = AppDatabase.getDatabase(context);
    }

    public DbAsyncTask<T> setCallback(DbAsyncInterface<T> dai) {
        Dbcallback = dai;
        return this;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        manager.registerWorkerStart(this);
    }

    @Override
    protected void onPostExecute(T t) {
        super.onPostExecute(t);
        manager.registerWorkerStop(this);
        if (Dbcallback != null)
            Dbcallback.onComplete(t);
    }
}
