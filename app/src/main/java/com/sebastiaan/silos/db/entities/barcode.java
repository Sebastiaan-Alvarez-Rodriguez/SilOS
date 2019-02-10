package com.sebastiaan.silos.db.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity
public class barcode {
    @PrimaryKey
    @NonNull
    private String barcodeString;
    @ForeignKey(entity = product.class, parentColumns = "productID", childColumns = "productID", onDelete = CASCADE)
    private int productID;

    private int amount;

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    @NonNull
    public String getBarcodeString() {
        return barcodeString;
    }

    public void setBarcodeString(@NonNull String barcodeString) {
        this.barcodeString = barcodeString;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
