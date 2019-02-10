package com.sebastiaan.silos.db.async.task.getAll;

import android.content.Context;

import com.sebastiaan.silos.db.async.task.AsyncManager;
import com.sebastiaan.silos.db.entities.supplier;

import java.util.List;

public class supplierGetAllTask extends getAllTask<supplier> {

    public supplierGetAllTask(AsyncManager manager, Context context) {
        super(manager, context);
    }

    @Override
    protected List<supplier> doInBackground(Void... voids) {
        return database.supplierDao().getAll();
    }
}
