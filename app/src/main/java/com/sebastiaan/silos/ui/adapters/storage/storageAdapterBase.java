package com.sebastiaan.silos.ui.adapters.storage;

import android.view.ViewGroup;

import com.sebastiaan.silos.db.entities.storage;
import com.sebastiaan.silos.ui.adapters.baseAdapter;
import com.sebastiaan.silos.ui.adapters.viewholders.storage.storageBaseViewHolder;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class storageAdapterBase extends baseAdapter<storageBaseViewHolder, storage> {
    public storageAdapterBase(List list, @Nullable com.sebastiaan.silos.ui.adapters.interfaces.clickCallback clickCallback) {
        super(list, clickCallback);
    }

    @NonNull
    @Override
    public storageBaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull storageBaseViewHolder holder, int position) {

    }
}
