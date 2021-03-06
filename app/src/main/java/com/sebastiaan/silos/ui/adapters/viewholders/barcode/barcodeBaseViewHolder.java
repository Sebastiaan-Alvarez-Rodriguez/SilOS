package com.sebastiaan.silos.ui.adapters.viewholders.barcode;

import android.util.Log;
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

    private long productID = -1;

    public barcodeBaseViewHolder(@NonNull View itemView) {
        super(itemView);
        barcode_string = itemView.findViewById(R.id.barcode_list_string);
        barcode_amount = itemView.findViewById(R.id.barcode_list_amount);
        Log.e("TESTTTT", "barcode_string in barcodeBaseViewholder is " + (barcode_string == null ? "not found" : "found"));
    }

    @Override
    public void set(barcode barcode) {
        Log.e("TESTTTT", "barcode_string in barcodeBaseViewholder is " + (barcode_string == null ? "null" : "not null"));
        barcode_string.setText(barcode.getBarcodeString());
        barcode_amount.setText(String.valueOf(barcode.getAmount()));
        productID = barcode.getProductID();
    }

    @Override
    public barcode get() {
        return new barcode(barcode_string.getText().toString(), productID, Integer.valueOf(barcode_amount.getText().toString()));
    }
}
