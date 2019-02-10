package com.sebastiaan.silos.ui.adapters.viewholders.supplier;

import android.view.View;
import android.widget.CheckBox;

import com.sebastiaan.silos.R;
import com.sebastiaan.silos.ui.adapters.viewholders.viewHolderCheckedCallback;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

public class supplierCheckableViewHolder extends supplierBaseViewHolder {
    public static final @LayoutRes int layoutResource = R.layout.supplier_list_item_checkable;
    private CheckBox box;

    public supplierCheckableViewHolder(@NonNull View itemView, viewHolderCheckedCallback listener) {
        super(itemView);
        box = itemView.findViewById(R.id.supplier_list_checkbox);
        box.setOnCheckedChangeListener((buttonView, isChecked) -> listener.onCheckedChanged(isChecked, getAdapterPosition()));
    }

    public void setChecked(boolean checked) {
        box.setChecked(checked);
    }
}
