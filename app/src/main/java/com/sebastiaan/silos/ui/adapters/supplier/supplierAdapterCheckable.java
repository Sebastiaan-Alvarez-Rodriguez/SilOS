package com.sebastiaan.silos.ui.adapters.supplier;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sebastiaan.silos.db.entities.supplier;
import com.sebastiaan.silos.ui.adapters.checkableAdapter;
import com.sebastiaan.silos.ui.adapters.interfaces.clickCallback;
import com.sebastiaan.silos.ui.adapters.viewholders.supplier.supplierCheckableViewHolder;
import com.sebastiaan.silos.ui.adapters.viewholders.viewHolderCheckedCallback;

import java.util.List;
import java.util.Set;

import androidx.annotation.NonNull;

public class supplierAdapterCheckable extends checkableAdapter<supplierCheckableViewHolder, supplier> implements viewHolderCheckedCallback {

    public supplierAdapterCheckable(List<supplier> list, clickCallback<supplier> clickCallback) {
        super(list, clickCallback);
    }

    public supplierAdapterCheckable(List<supplier> list, clickCallback<supplier> clickCallback, Set<supplier> enabledList) {
        super(list, clickCallback, enabledList);
    }

    @NonNull
    @Override
    public supplierCheckableViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View inflated = LayoutInflater.from(viewGroup.getContext()).inflate(supplierCheckableViewHolder.layoutResource, viewGroup,false);
        return new supplierCheckableViewHolder(inflated, this);
    }

    @Override
    public void onBindViewHolder(@NonNull supplierCheckableViewHolder viewholder, int position) {
        super.onBindViewHolder(viewholder, position);
        supplier ref = list.get(position);//TODO: werkt dit?
        boolean checked = containsSelectedItem(ref);
        viewholder.setChecked(checked);
    }

    @Override
    public void onCheckedChanged(boolean isChecked, int position) {
        onCheckedChanged(list.get(position), isChecked);//TODO: werkt dit?
    }
}
