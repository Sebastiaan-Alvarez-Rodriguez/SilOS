package com.sebastiaan.silos.db.interfaces;

import com.sebastiaan.silos.db.entities.barcode;
import com.sebastiaan.silos.db.entities.product;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

@Dao
public interface barcodeInterface extends DbIDInterface<barcode> {
    @Query("SELECT * FROM barcode")
    LiveData<List<barcode>> getAll();

    @Query("SELECT product.* FROM product, barcode WHERE product.id = barcode.productID AND barcode.barcodeString = :code")
    product findProductByBarcode(String code);

    @Query("SELECT * FROM barcode WHERE id = :id")
    LiveData<barcode> findByID(long id);

    @Query("SELECT * FROM barcode WHERE productID = :productID")
    LiveData<List<barcode>> getAllforProduct(long productID);

    @Query("SELECT * FROM barcode WHERE barcodeString = :barcodeString")
    barcode findExactByBarcode(String barcodeString);
}
