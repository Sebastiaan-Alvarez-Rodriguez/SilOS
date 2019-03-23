package com.sebastiaan.silos.ui.supplier;

import android.app.Application;

import com.sebastiaan.silos.db.AppDatabase;
import com.sebastiaan.silos.db.async.DbAsyncInterface;
import com.sebastiaan.silos.db.async.helper.supplierHelper;
import com.sebastiaan.silos.db.async.task.AsyncManager;
import com.sebastiaan.silos.db.entities.supplier;
import com.sebastiaan.silos.db.interfaces.supplierInterface;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class SupplierViewModel extends AndroidViewModel {
    private LiveData<List<supplier>> supplierList;

    private AsyncManager manager;
    private supplierHelper helper;

    private supplierInterface i;

    public SupplierViewModel(Application application) {
        super(application);
        manager = new AsyncManager();
        helper = new supplierHelper(manager, application);

        i = AppDatabase.getDatabase(application).supplierDao();
        supplierList = i.getAll();
    }

    public LiveData<List<supplier>> getAll() {
        return supplierList;
    }

    public void deleteAll(List<supplier> s, DbAsyncInterface<Void> onFinish) {
        helper.deleteAll(s, onFinish);
    }
}
