package com.sebastiaan.silos.ui.adapters.supplier;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.sebastiaan.silos.db.entities.supplier;
import com.sebastiaan.silos.ui.adapters.baseAdapter;
import com.sebastiaan.silos.ui.adapters.interfaces.clickCallback;
import com.sebastiaan.silos.ui.adapters.viewholders.supplier.supplierBaseViewHolder;

import java.util.List;

import androidx.annotation.NonNull;

public class supplierAdapterBase extends baseAdapter<supplierBaseViewHolder, supplier> {

    public supplierAdapterBase(List<supplier> list, clickCallback<supplier> clickCallback) {
        super(list, clickCallback);
    }

    @NonNull
    @Override
    public supplierBaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return new supplierBaseViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(supplierBaseViewHolder.layoutResource, viewGroup,false));
    }
}
