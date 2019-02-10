package com.sebastiaan.silos.db.async.task.find;

import android.content.Context;

import com.sebastiaan.silos.db.async.task.AsyncManager;
import com.sebastiaan.silos.db.entities.product;

public class productFindTask extends findTask<product> {
    protected String name;
    public productFindTask(AsyncManager manager, Context context, String name) {
        super(manager, context, name);
        this.name = name;
    }

    @Override
    protected product doInBackground(Void... voids) {
        return database.productDao().findByNameExact(name);
    }
}
