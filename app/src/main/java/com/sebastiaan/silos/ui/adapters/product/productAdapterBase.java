package com.sebastiaan.silos.ui.adapters.product;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sebastiaan.silos.db.async.helper.supplier_productHelper;
import com.sebastiaan.silos.db.entities.product;
import com.sebastiaan.silos.ui.adapters.baseAdapter;
import com.sebastiaan.silos.ui.adapters.interfaces.clickCallback;
import com.sebastiaan.silos.ui.adapters.viewholders.product.productBaseViewHolder;

import java.util.List;

import androidx.annotation.NonNull;

public class productAdapterBase extends baseAdapter<productBaseViewHolder, product> {
    protected supplier_productHelper supplier_productHelper;

    public productAdapterBase(List<product> list, clickCallback<product> actionCallback, supplier_productHelper supplier_productHelper) {
        super(list, actionCallback);
        this.supplier_productHelper =supplier_productHelper;
    }

    @NonNull
    @Override
    public productBaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View inflated = LayoutInflater.from(viewGroup.getContext()).inflate(productBaseViewHolder.layoutResource, viewGroup,false);
        return new productBaseViewHolder(inflated, supplier_productHelper, this);
    }
}
