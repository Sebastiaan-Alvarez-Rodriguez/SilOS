package com.sebastiaan.silos.ui.adapters.barcode;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sebastiaan.silos.R;
import com.sebastiaan.silos.db.entities.barcode;
import com.sebastiaan.silos.ui.adapters.actionAdapter;
import com.sebastiaan.silos.ui.adapters.interfaces.actionCallback;
import com.sebastiaan.silos.ui.adapters.interfaces.clickCallback;
import com.sebastiaan.silos.ui.adapters.viewholders.barcode.barcodeActionViewHolder;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

public class barcodeAdapterAction extends actionAdapter<barcodeActionViewHolder, barcode> {

    public barcodeAdapterAction(List<barcode> list, clickCallback<barcode> clickCallback, actionCallback actionCallback) {
        super(list, clickCallback, actionCallback);
    }

    @Override
    protected void selectItem(View v, int itempos) {
        ConstraintLayout layout = v.findViewById(R.id.barcode_list_layout);
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
    public barcodeActionViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View inflated = LayoutInflater.from(viewGroup.getContext()).inflate(barcodeActionViewHolder.layoutResource, viewGroup,false);
        return new barcodeActionViewHolder(inflated, this);
    }
}
