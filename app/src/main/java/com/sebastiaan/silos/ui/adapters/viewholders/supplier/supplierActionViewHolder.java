package com.sebastiaan.silos.ui.adapters.viewholders.supplier;

import android.view.View;


import com.sebastiaan.silos.R;
import com.sebastiaan.silos.ui.adapters.viewholders.viewHolderClickCallback;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

public class supplierActionViewHolder extends supplierBaseViewHolder {
    public static final @LayoutRes int layoutResource = R.layout.supplier_list_item;

    public supplierActionViewHolder(@NonNull View itemView, viewHolderClickCallback clickCallback) {
        super(itemView);
        itemView.setOnLongClickListener(v -> clickCallback.onClick(v, true, getAdapterPosition()));
        itemView.setOnClickListener(v -> clickCallback.onClick(v, false, getAdapterPosition()));
    }
}
