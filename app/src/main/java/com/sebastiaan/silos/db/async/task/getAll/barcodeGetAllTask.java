package com.sebastiaan.silos.db.async.task.getAll;

import android.content.Context;

import com.sebastiaan.silos.db.async.task.AsyncManager;
import com.sebastiaan.silos.db.entities.barcode;

import java.util.List;

public class barcodeGetAllTask extends getAllTask<barcode> {
    long productID;

    public barcodeGetAllTask(AsyncManager manager, Context context, long productID) {
        super(manager, context);
        this.productID = productID;
    }

    @Override
    protected List<barcode> doInBackground(Void... voids) {
        return database.barcodeDao().getAllforProduct(productID);
    }
}
