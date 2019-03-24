package com.sebastiaan.silos.ui.storage;

import android.app.Application;

import com.sebastiaan.silos.db.AppDatabase;
import com.sebastiaan.silos.db.async.task.AsyncManager;
import com.sebastiaan.silos.db.entities.storage;
import com.sebastiaan.silos.db.interfaces.storageInterface;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class StorageViewModel extends AndroidViewModel {
    private LiveData<List<storage>> storageList;

    private AsyncManager manager;

    private storageInterface i;

    public StorageViewModel(Application application) {
        super(application);
        manager = new AsyncManager();

        i = AppDatabase.getDatabase(application).storageDao();
        storageList = i.getAll();
    }

    public LiveData<List<storage>> getAll() {return storageList;}

}
