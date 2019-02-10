package com.sebastiaan.silos.db.async.task.getAll;

import android.content.Context;

import com.sebastiaan.silos.db.async.task.AsyncManager;
import com.sebastiaan.silos.db.entities.product;

import java.util.List;

public class productGetAllTask extends getAllTask<product>{

    public productGetAllTask(AsyncManager manager, Context context) {
        super(manager, context);
    }

    @Override
    protected List<product> doInBackground(Void... voids) {
        return database.productDao().getAll();
    }
}
