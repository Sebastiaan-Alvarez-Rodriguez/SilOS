package com.sebastiaan.silos.ui.supplier;

import android.content.Context;

import com.sebastiaan.silos.db.async.helper.supplierHelper;
import com.sebastiaan.silos.db.async.task.AsyncManager;
import com.sebastiaan.silos.db.entities.supplier;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SuppliersViewModelActivity extends ViewModel {
    private MutableLiveData<List<supplier>> supplierList;

    public LiveData<List<supplier>> getsupplierList(Context applContext) {
        if (supplierList == null) {
            supplierList = new MutableLiveData<>();
            loadSupplierList(applContext);
        }
        return supplierList;
    }

    public void loadSupplierList(Context applContext) {
        AsyncManager manager = new AsyncManager();
        supplierHelper helper = new supplierHelper(manager, applContext);
        helper.getAll(list -> supplierList.setValue(list.getValue()));
    }
}
