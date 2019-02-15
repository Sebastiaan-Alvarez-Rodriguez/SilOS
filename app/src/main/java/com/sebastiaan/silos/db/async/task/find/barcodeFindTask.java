package com.sebastiaan.silos.db.async.task.find;

import android.content.Context;

import com.sebastiaan.silos.db.async.task.AsyncManager;
import com.sebastiaan.silos.db.entities.barcode;

public class barcodeFindTask extends findTask<barcode> {
    public barcodeFindTask(AsyncManager manager, Context context, String name) {
        super(manager, context, name);
    }

    @Override
    protected barcode doInBackground(Void... voids) {
        return database.barcodeDao().findExactByBarcode(name);
    }
}
