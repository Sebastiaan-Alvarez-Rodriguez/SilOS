package com.sebastiaan.silos.ui.adapters.viewholders.product;

import android.view.View;

import com.sebastiaan.silos.R;
import com.sebastiaan.silos.ui.adapters.viewholders.viewHolderClickCallback;

import com.sebastiaan.silos.db.async.helper.supplier_productHelper;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

public class productActionViewHolder extends productBaseViewHolder {
    public static final @LayoutRes int layoutResource = R.layout.product_list_item;

    public productActionViewHolder(@NonNull View itemView, supplier_productHelper supplier_productHelper, viewHolderClickCallback clickCallback) {
        super(itemView, supplier_productHelper);
        itemView.setOnLongClickListener(v -> clickCallback.onClick(v, true, getAdapterPosition()));
        itemView.setOnClickListener(v -> clickCallback.onClick(v, false, getAdapterPosition()));
    }
}
