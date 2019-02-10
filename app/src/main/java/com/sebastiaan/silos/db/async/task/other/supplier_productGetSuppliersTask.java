package com.sebastiaan.silos.db.async.task.other;

import android.content.Context;

import com.sebastiaan.silos.db.async.task.AsyncManager;
import com.sebastiaan.silos.db.async.task.DbAsyncTask;
import com.sebastiaan.silos.db.entities.supplier;

import java.util.List;

public class supplier_productGetSuppliersTask extends DbAsyncTask<List<supplier>> {
    private long productID;

    public supplier_productGetSuppliersTask(AsyncManager manager, Context context, long productID) {
        super(manager, context);
        this.productID = productID;
    }

    @Override
    protected List<supplier> doInBackground(Void... voids) {
        return database.supplier_productDao().findSuppliersForProduct(productID);
    }
}
