package com.sebastiaan.silos.db.interfaces;

import com.sebastiaan.silos.db.entities.supplier;
import com.sebastiaan.silos.db.entities.supplier_product;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface supplier_productInterface extends DbInterface<supplier_product> {
    @Query("SELECT supplier.* FROM supplier_product, supplier WHERE supplier_product.supplierID = supplier.supplierID AND supplier_product.productID = :productID")
    List<supplier> findSuppliersForProduct(long productID);

    @Query("SELECT COUNT(*) FROM supplier_product WHERE supplierID = :supplierID AND productID = :productID")
    boolean contains(int supplierID, int productID);

    @Query("SELECT COUNT(*) FROM supplier_product")
    int count();

    @Query("DELETE FROM supplier_product where productID = :productIDs")
    void deleteForProduct(Integer... productIDs);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long[] insertAll(supplier_product... supplier_products);
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(supplier_product supplier_product);

    @Update
    void updateAll(supplier_product... supplier_products);
    @Update
    void update(supplier_product supplier_product);

    @Delete
    void deleteAll(supplier_product... supplier_products);
    @Delete
    void delete(supplier_product supplier_product);
}