package com.sebastiaan.silos.ui.adapters.supplier;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sebastiaan.silos.R;
import com.sebastiaan.silos.db.entities.supplier;
import com.sebastiaan.silos.ui.adapters.actionAdapter;
import com.sebastiaan.silos.ui.adapters.interfaces.actionCallback;
import com.sebastiaan.silos.ui.adapters.interfaces.clickCallback;
import com.sebastiaan.silos.ui.adapters.viewholders.supplier.supplierActionViewHolder;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;


public class supplierAdapterAction extends actionAdapter<supplierActionViewHolder, supplier> {

    public supplierAdapterAction(List<supplier> list, clickCallback<supplier> clickCallback, actionCallback actionCallback) {
        super(list, clickCallback, actionCallback);
    }

    @Override
    protected void selectItem(View v, int itempos) {
        ConstraintLayout layout = v.findViewById(R.id.supplier_list_layout);
        if (selectedItems.add(itempos)) {
            layout.setBackgroundColor(selectedColor);
        } else {
            selectedItems.remove(itempos);
            layout.setBackgroundColor(Color.TRANSPARENT);
            if (selectedItems.isEmpty())
                if (actionCallback != null)
                    actionCallback.onEmptyItemSelection();
        }
    }

    @NonNull
    @Override
    public supplierActionViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View inflated = LayoutInflater.from(viewGroup.getContext()).inflate(supplierActionViewHolder.layoutResource, viewGroup,false);
        return new supplierActionViewHolder(inflated, this);
    }
}
