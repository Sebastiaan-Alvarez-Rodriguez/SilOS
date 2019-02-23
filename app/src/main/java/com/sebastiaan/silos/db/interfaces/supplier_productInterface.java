package com.sebastiaan.silos.db.interfaces;

import com.sebastiaan.silos.db.entities.supplier;
import com.sebastiaan.silos.db.entities.supplier_product;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface supplier_productInterface extends DbIDInterface<supplier_product> {
    @Query("SELECT supplier.* FROM supplier_product, supplier WHERE supplier_product.supplierID = supplier.id AND supplier_product.productID = :productID")
    List<supplier> findSuppliersForProduct(long productID);

    @Query("SELECT * FROM supplier_product WHERE supplierID=:id")
    supplier_product findByID(long id);

    @Query("SELECT COUNT(*) FROM supplier_product WHERE supplierID = :supplierID AND productID = :productID")
    boolean contains(long supplierID, long productID);

    @Query("SELECT COUNT(*) FROM supplier_product")
    int count();

    @Query("DELETE FROM supplier_product where productID = :productIDs")
    void deleteForProduct(Integer... productIDs);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long[] insertAll(supplier_product... supplier_products);
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(supplier_product supplier_product);

}