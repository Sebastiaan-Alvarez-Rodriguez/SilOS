package com.sebastiaan.silos.db.interfaces;

import com.sebastiaan.silos.db.entities.barcode;
import com.sebastiaan.silos.db.entities.product;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface barcodeInterface {
    @Query("SELECT product.* FROM product, barcode where product.productID = barcode.productID and barcode.barcodeString = :code")
    product findByBarcode(String code);

    @Insert
    void insertAll(barcode... barcodes);
    @Insert
    void insert(barcode barcode);

    @Update
    void updateAll(barcode... barcodes);
    @Update
    void update(barcode barcode);

    @Delete
    void deleteAll(barcode... barcodes);
    @Delete
    void delete(barcode barcode); 
}
