package com.sebastiaan.silos.db.async.helper;

import android.content.Context;

import com.sebastiaan.silos.db.async.DbAsyncInterface;
import com.sebastiaan.silos.db.async.task.AsyncManager;
import com.sebastiaan.silos.db.async.task.find.findByNameExactTask;
import com.sebastiaan.silos.db.entities.DbEntityNamed;

public class helperNamed<T extends DbEntityNamed<T>> extends helper<T> {
    public helperNamed(AsyncManager manager, Context context) {
        super(manager, context);
    }

    public void findByNameExact(T objectType, String name, DbAsyncInterface<T> onFinish) {
        findByNameExactTask<T> task = new findByNameExactTask<>(manager, context, objectType, name);
        task.setCallback(onFinish).execute();
    }
}
