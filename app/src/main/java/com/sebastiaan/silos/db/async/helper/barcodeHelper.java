package com.sebastiaan.silos.db.async.helper;

import android.content.Context;

import com.sebastiaan.silos.db.async.DbAsyncInterface;
import com.sebastiaan.silos.db.async.task.AsyncManager;
import com.sebastiaan.silos.db.async.task.find.findByBarcodeTask;
import com.sebastiaan.silos.db.async.task.getAll.GetAllTask;
import com.sebastiaan.silos.db.async.task.other.barcodesForProductTask;
import com.sebastiaan.silos.db.entities.barcode;
import com.sebastiaan.silos.ui.entities.ui_barcode;

import java.util.List;

import androidx.lifecycle.LiveData;

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

    public void getAll(DbAsyncInterface<LiveData<List<barcode>>> onFinish) {
        GetAllTask<barcode> task = new GetAllTask<>(manager, context, new barcode("", 0, 0));
        task.setCallback(onFinish).execute();
    }

    public void GetBarcodesForProduct(long productID, DbAsyncInterface<LiveData<List<barcode>>> onFinish) {
        barcodesForProductTask task = new barcodesForProductTask(manager, context, productID);
        task.setCallback(onFinish).execute();
    }

    public void find(String barcodeString, DbAsyncInterface<barcode> onFinish) {
        findByBarcodeTask task = new findByBarcodeTask(manager, context, barcodeString);
        task.setCallback(onFinish).execute();
    }
}
