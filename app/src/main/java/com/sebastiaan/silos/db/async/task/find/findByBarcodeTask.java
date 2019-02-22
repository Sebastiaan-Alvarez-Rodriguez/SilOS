package com.sebastiaan.silos.db.async.task.find;

import android.content.Context;

import com.sebastiaan.silos.db.AppDatabase;
import com.sebastiaan.silos.db.async.task.AsyncManager;
import com.sebastiaan.silos.db.async.task.DbAsyncTask;
import com.sebastiaan.silos.db.entities.barcode;
import com.sebastiaan.silos.db.interfaces.barcodeInterface;

public class findByBarcodeTask extends DbAsyncTask<barcode> {
    protected String barcode;
    private barcodeInterface DbInterface;

    public findByBarcodeTask(AsyncManager manager, Context context, String barcode) {
        super(manager, context);
        DbInterface = AppDatabase.getDatabase(context).barcodeDao();
        this.barcode = barcode;
    }

    @Override
    protected barcode doInBackground(Void... voids) {
        return DbInterface.findExactByBarcode(barcode);
    }
}
