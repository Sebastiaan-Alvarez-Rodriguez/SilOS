package com.sebastiaan.silos.db.async.helper;

import android.content.Context;

import com.sebastiaan.silos.db.async.DbAsyncInterface;
import com.sebastiaan.silos.db.async.task.AsyncManager;
import com.sebastiaan.silos.db.async.task.find.findByBarcodeTask;
import com.sebastiaan.silos.db.async.task.getAll.barcodeGetAllTask;
import com.sebastiaan.silos.db.entities.barcode;
import com.sebastiaan.silos.ui.entities.ui_barcode;

import java.util.List;

public class barcodeHelper extends helper<barcode> {
    public barcodeHelper(AsyncManager manager, Context context) {
        super(manager, context);
    }

    public void insert(ui_barcode barcode, long productID, DbAsyncInterface<Long> onFinish) {
        super.insert(barcode.to_barcode(productID), onFinish);
    }

    public void deleteAll(List<barcode> barcodes, DbAsyncInterface<Void> onFinish) {
        super.deleteAll(barcodes.toArray(new barcode[0]), onFinish);
    }

    public void getAll(long productID, DbAsyncInterface<List<barcode>> onFinish) {
        barcodeGetAllTask task = new barcodeGetAllTask(manager, context, productID);
        task.setCallback(onFinish).execute();
    }

    public void find(String barcodeString, DbAsyncInterface<barcode> onFinish) {
        findByBarcodeTask task = new findByBarcodeTask(manager, context, barcodeString);
        task.setCallback(onFinish).execute();
    }
}
