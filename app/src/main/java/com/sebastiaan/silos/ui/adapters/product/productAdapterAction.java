package com.sebastiaan.silos.ui.adapters.product;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sebastiaan.silos.R;
import com.sebastiaan.silos.db.async.helper.supplier_productHelper;
import com.sebastiaan.silos.db.entities.product;
import com.sebastiaan.silos.ui.adapters.actionAdapter;
import com.sebastiaan.silos.ui.adapters.interfaces.actionCallback;
import com.sebastiaan.silos.ui.adapters.viewholders.product.productActionViewHolder;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

public class productAdapterAction extends actionAdapter<productActionViewHolder, product> {
    private supplier_productHelper supplier_productHelper;

    public productAdapterAction(List<product> list, actionCallback<product> callback, supplier_productHelper supplier_productHelper) {
        super(list, callback);
        this.supplier_productHelper = supplier_productHelper;
    }

    @Override
    protected void selectItem(View v, int itempos) {
        ConstraintLayout layout = v.findViewById(R.id.product_list_layout);
        if (selectedItems.add(itempos)) {
            layout.setBackgroundColor(selectedColor);
        } else {
            selectedItems.remove(itempos);
            layout.setBackgroundColor(Color.TRANSPARENT);
            if (selectedItems.isEmpty())
                callback.onEmptyItemSelection();
        }
    }

    @NonNull
    @Override
    public productActionViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View inflated = LayoutInflater.from(viewGroup.getContext()).inflate(productActionViewHolder.layoutResource, viewGroup,false);
        return new productActionViewHolder(inflated, supplier_productHelper, this);
    }
}
