package com.sebastiaan.silos.ui.adapters.viewholders.barcode;

import android.view.View;

import com.sebastiaan.silos.R;
import com.sebastiaan.silos.ui.adapters.viewholders.viewHolderClickCallback;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

public class barcodeActionViewHolder extends barcodeBaseViewHolder {
    public static final @LayoutRes
    int layoutResource = R.layout.barcode_list_item;

    public barcodeActionViewHolder(@NonNull View itemView, viewHolderClickCallback clickCallback) {
        super(itemView);
        itemView.setOnLongClickListener(v -> clickCallback.onClick(v, true, getAdapterPosition()));
        itemView.setOnClickListener(v -> clickCallback.onClick(v, false, getAdapterPosition()));
    }

}
