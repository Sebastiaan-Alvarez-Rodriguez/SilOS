package com.sebastiaan.silos.ui.adapters.barcode;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.sebastiaan.silos.db.entities.barcode;
import com.sebastiaan.silos.ui.adapters.baseAdapter;
import com.sebastiaan.silos.ui.adapters.viewholders.barcode.barcodeBaseViewHolder;

import java.util.List;

import androidx.annotation.NonNull;

public class barcodeAdapterBase extends baseAdapter<barcodeBaseViewHolder, barcode> {

    public barcodeAdapterBase(List<barcode> list) {
        super(list);
    }

    @NonNull
    @Override
    public barcodeBaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return new barcodeBaseViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(barcodeBaseViewHolder.layoutResource, viewGroup, false));
    }
}
