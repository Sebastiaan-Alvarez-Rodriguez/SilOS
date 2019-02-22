package com.sebastiaan.silos.ui.entities;

import com.sebastiaan.silos.db.async.helper.barcodeHelper;
import com.sebastiaan.silos.db.async.helper.helper;
import com.sebastiaan.silos.db.entities.barcode;
public class ui_barcode extends UiEntity {
    public String barcodeString;
    public int amount;

    public ui_barcode(String barcode, int amount) {
        this.barcodeString = barcode;
        this.amount = amount;
    }

    public barcode to_barcode(int productID) {
        barcode b = new barcode(barcodeString, amount);
        b.setProductID(productID);
        return b;
    }

    public barcode to_barcode(long productID) {
        barcode b = new barcode(barcodeString, amount);
        b.setProductID(productID);
        return b;
    }
}
