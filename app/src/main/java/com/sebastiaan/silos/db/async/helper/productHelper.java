package com.sebastiaan.silos.db.async.helper;

import android.content.Context;

import com.sebastiaan.silos.db.async.DbAsyncInterface;
import com.sebastiaan.silos.db.async.task.AsyncManager;
import com.sebastiaan.silos.db.async.task.getAll.productGetAllTask;
import com.sebastiaan.silos.db.entities.product;
import com.sebastiaan.silos.ui.entities.ui_product;

import java.util.List;

public class productHelper extends helperNamed<product> {

    public productHelper(AsyncManager manager, Context context) {
        super(manager, context);
    }

    public void insert(ui_product input, DbAsyncInterface<Long> onFinish) {
        insert(new product(input.productname, input.description), onFinish);
    }

    public void update(long productID, ui_product product, DbAsyncInterface<Void> onFinish) {
        product p = new product(product.productname, product.description);
        p.setProductID(productID);
        update(p, onFinish);
    }

    public void deleteAll(List<product> products, DbAsyncInterface<Void> onFinish) {
        deleteAll(products.toArray(new product[0]), onFinish);
    }

    public void getAll(DbAsyncInterface<List<product>> onFinish) {
        productGetAllTask task = new productGetAllTask(manager, context);
        task.setCallback(onFinish).execute();
    }
}
