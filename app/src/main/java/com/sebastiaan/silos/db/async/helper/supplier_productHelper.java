package com.sebastiaan.silos.db.async.helper;

import android.content.Context;

import com.sebastiaan.silos.db.async.DbAsyncInterface;
import com.sebastiaan.silos.db.async.group.parallelgroup;
import com.sebastiaan.silos.db.async.task.AsyncManager;
import com.sebastiaan.silos.db.async.task.DbAsyncTask;
import com.sebastiaan.silos.db.async.task.delete.deleteAllTask;
import com.sebastiaan.silos.db.async.task.insert.insertAllTask;
import com.sebastiaan.silos.db.async.task.other.supplier_productGetSuppliersTask;
import com.sebastiaan.silos.db.entities.supplier;
import com.sebastiaan.silos.db.entities.supplier_product;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import androidx.lifecycle.LiveData;

public class supplier_productHelper extends helper<supplier_product> {

    public supplier_productHelper(AsyncManager manager, Context context) {
        super(manager, context);
    }

    private supplier_product[] buildRelations(long productID, Set<supplier> suppliers) {
        Set<supplier_product> relations = new HashSet<>();

        for (supplier supplier : suppliers)
            relations.add(new supplier_product(supplier.getId(), productID));

        return relations.toArray(new supplier_product[0]);
    }

    private insertAllTask<supplier_product> buildInsertTask(long productID, Set<supplier> suppliers) {
        return new insertAllTask<>(manager, context, buildRelations(productID, suppliers));
    }

    private deleteAllTask<supplier_product> buildDeleteTask(long productID, Set<supplier> suppliers) {
        return new deleteAllTask<>(manager, context, buildRelations(productID, suppliers));
    }



    public void insert(long productID, Set<supplier> suppliers, DbAsyncInterface<long[]> async) {
        insertAllTask<supplier_product> task = buildInsertTask(productID, suppliers);
        task.setCallback(async).execute();
    }

    public void delete(long productID, Set<supplier> suppliers, DbAsyncInterface<Void> async) {
        deleteAllTask<supplier_product> task = buildDeleteTask(productID, suppliers);
        task.setCallback(async).execute();
    }

    public void update(long productID, Set<supplier> suppliersBefore, Set<supplier> suppliersAfter, DbAsyncInterface<Void> async) {
        Set<supplier> removedRelations = new HashSet<>();

        for (supplier s : suppliersBefore) {
            //if supplier was checked but now is not, it is removed
            if (!suppliersAfter.contains(s))
                removedRelations.add(s);
        }

        insertAllTask<supplier_product> task1 = buildInsertTask(productID, suppliersAfter);
        deleteAllTask<supplier_product> task2 = buildDeleteTask(productID, removedRelations);

        parallelgroup parallelgroup = new parallelgroup(new ArrayList<DbAsyncTask>(){{add(task1); add(task2);}}, async);
        parallelgroup.execute();
    }

    public void getForProduct(long productID, DbAsyncInterface<LiveData<List<supplier>>> onFinish) {
        supplier_productGetSuppliersTask task = new supplier_productGetSuppliersTask(manager, context, productID);
        task.setCallback(onFinish).execute();
    }

}
