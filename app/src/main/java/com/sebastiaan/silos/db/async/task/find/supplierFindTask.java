package com.sebastiaan.silos.db.async.task.find;

import android.content.Context;

import com.sebastiaan.silos.db.async.task.AsyncManager;
import com.sebastiaan.silos.db.entities.supplier;

public class supplierFindTask extends findTask<supplier> {

    public supplierFindTask(AsyncManager manager, Context context, String name) {
        super(manager, context, name);
    }

    @Override
    protected supplier doInBackground(Void... voids) {
        return database.supplierDao().findExactByName(name);
    }
}
