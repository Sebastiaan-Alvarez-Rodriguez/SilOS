package com.sebastiaan.silos.ui.entities;

import com.sebastiaan.silos.db.entities.barcode;
public class ui_barcode extends UiEntity {
    public String barcodeString;
    public long productID;
    public int amount;

    public ui_barcode(String barcode, int amount) {
        this.barcodeString = barcode;
        this.amount = amount;
    }

    public barcode to_barcode(long barcodeID) {
        barcode b = new barcode(barcodeString, productID, amount);
        b.setId(barcodeID);
        return b;
    }
}
