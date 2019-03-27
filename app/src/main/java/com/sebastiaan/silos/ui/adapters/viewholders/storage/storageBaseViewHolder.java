package com.sebastiaan.silos.ui.adapters.viewholders.storage;

import android.view.View;
import android.widget.TextView;

import com.sebastiaan.silos.R;
import com.sebastiaan.silos.db.entities.storage;
import com.sebastiaan.silos.ui.adapters.viewholders.baseViewHolder;

import androidx.annotation.NonNull;
public class storageBaseViewHolder extends baseViewHolder<storage> {

    protected TextView storage_amount;
    protected TextView storage_productname;


    public storageBaseViewHolder(@NonNull View itemView) {
        super(itemView);
        storage_amount = itemView.findViewById(R.id.storage_list_number);
        storage_productname = itemView.findViewById(R.id.storage_list_product);
    }

    @Override
    public void set(storage storage) {

    }

    @Override
    public storage get() {
        return null;
    }
}
