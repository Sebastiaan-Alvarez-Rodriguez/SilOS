package com.sebastiaan.silos.db.async.task.other;

import android.content.Context;

import com.sebastiaan.silos.db.async.task.AsyncManager;
import com.sebastiaan.silos.db.async.task.DbAsyncTask;
import com.sebastiaan.silos.db.entities.barcode;

import java.util.List;

import androidx.lifecycle.LiveData;

public class barcodesForProductTask extends DbAsyncTask<LiveData<List<barcode>>> {
    private long productID;

    public barcodesForProductTask(AsyncManager manager, Context context, long productID) {
        super(manager, context);
        this.productID = productID;
    }

    @Override
    protected LiveData<List<barcode>> doInBackground(Void... voids) {
        return database.barcodeDao().getAllforProduct(productID);
    }
}
