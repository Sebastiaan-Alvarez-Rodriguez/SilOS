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
import com.sebastiaan.silos.ui.adapters.interfaces.clickCallback;
import com.sebastiaan.silos.ui.adapters.viewholders.product.productBaseViewHolder;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

public class productAdapterAction extends actionAdapter<productBaseViewHolder, product> {
    private supplier_productHelper supplier_productHelper;

    public productAdapterAction(List<product> list, clickCallback<product> clickCallback, actionCallback actionCallback, supplier_productHelper supplier_productHelper) {
        super(list, clickCallback, actionCallback);
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
                if (actionCallback != null)
                    actionCallback.onEmptyItemSelection();
        }
    }

    @NonNull
    @Override
    public productBaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View inflated = LayoutInflater.from(viewGroup.getContext()).inflate(productBaseViewHolder.layoutResource, viewGroup,false);
        return new productBaseViewHolder(inflated, supplier_productHelper, this);
    }
}
