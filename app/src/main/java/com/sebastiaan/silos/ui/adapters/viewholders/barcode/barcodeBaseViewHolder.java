package com.sebastiaan.silos.ui.adapters.viewholders.barcode;

import android.view.View;
import android.widget.TextView;

import com.sebastiaan.silos.R;
import com.sebastiaan.silos.db.entities.barcode;
import com.sebastiaan.silos.ui.adapters.viewholders.baseViewHolder;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

public class barcodeBaseViewHolder extends baseViewHolder<barcode> {
    public static final @LayoutRes int layoutResource = R.layout.barcode_list_item;

    private TextView barcode_string, barcode_amount;

    public barcodeBaseViewHolder(@NonNull View itemView) {
        super(itemView);
        barcode_string = itemView.findViewById(R.id.barcode_list_string);
        barcode_amount = itemView.findViewById(R.id.barcode_list_amount);
    }

    @Override
    public void set(barcode barcode) {
        barcode_string.setText(barcode.getBarcodeString());
        barcode_amount.setText(String.valueOf(barcode.getAmount()));
    }

    @Override
    public barcode get() {
        return new barcode(barcode_string.getText().toString(), Integer.valueOf(barcode_amount.getText().toString()));
    }
}
