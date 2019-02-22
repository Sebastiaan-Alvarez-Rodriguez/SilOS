package com.sebastiaan.silos.db.async.helper;

import android.content.Context;

import com.sebastiaan.silos.db.async.DbAsyncInterface;
import com.sebastiaan.silos.db.async.task.AsyncManager;
import com.sebastiaan.silos.db.async.task.getAll.supplierGetAllTask;
import com.sebastiaan.silos.db.entities.supplier;
import com.sebastiaan.silos.ui.entities.ui_supplier;

import java.util.List;

public class supplierHelper extends helperNamed<supplier> {

    public supplierHelper(AsyncManager manager, Context context) {
        super(manager, context);
    }

    public void insert(ui_supplier input, DbAsyncInterface<Long> onFinish) {
        insert(new supplier(input.name, input.streetname, input.housenumber, input.city, input.postalcode, input.phonenumber, input.emailaddress, input.website), onFinish);
    }

    public void update(long supplierID, ui_supplier input, DbAsyncInterface<Void> onFinish) {
        supplier s = new supplier(input.name, input.streetname, input.housenumber, input.city, input.postalcode, input.phonenumber, input.emailaddress, input.website);
        s.setSupplierID(supplierID);
        update(s, onFinish);
    }

    public void deleteAll(List<supplier> suppliers, DbAsyncInterface<Void> onFinish) {
        deleteAll(suppliers.toArray(new supplier[0]), onFinish);
    }

    public void getAll(DbAsyncInterface<List<supplier>> onFinish) {
        supplierGetAllTask task = new supplierGetAllTask(manager, context);
        task.setCallback(onFinish).execute();
    }
}
