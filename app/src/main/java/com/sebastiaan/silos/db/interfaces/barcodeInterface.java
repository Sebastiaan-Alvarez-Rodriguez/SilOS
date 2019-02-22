package com.sebastiaan.silos.db.interfaces;

import com.sebastiaan.silos.db.entities.barcode;
import com.sebastiaan.silos.db.entities.product;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Query;

@Dao
public interface barcodeInterface extends DbIDInterface<barcode> {
    @Query("SELECT product.* FROM product, barcode WHERE product.productID = barcode.productID AND barcode.barcodeString = :code")
    product findProductByBarcode(String code);

    @Query("SELECT * FROM barcode WHERE barcodeID = :id")
    barcode findByID(long id);

    @Query("SELECT * FROM barcode WHERE productID = :productID")
    List<barcode> getAllforProduct(long productID);

    @Query("SELECT * FROM barcode WHERE barcodeString = :barcodeString")
    barcode findExactByBarcode(String barcodeString);
}
