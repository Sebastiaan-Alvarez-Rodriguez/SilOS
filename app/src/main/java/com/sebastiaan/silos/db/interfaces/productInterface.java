package com.sebastiaan.silos.db.interfaces;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.sebastiaan.silos.db.entities.product;

import java.util.List;

@Dao
public interface productInterface extends DbInterface<product> {
    @Query("SELECT * FROM product")
    List<product> getAll();

    @Query("SELECT * FROM product where productID = :id")
    product findByID(int id);

    @Query("SELECT * FROM product where productName LIKE :name")
    List<product> findByName(String name);

    @Query("SELECT * FROM product where productName = :name")
    product findByNameExact(String name);

    @Query("SELECT COUNT(*) FROM product where productName = :productName")
    boolean contains(String productName);

    @Query("SELECT COUNT(*) from product")
    int countProducts();

    @Insert
    long[] insertAll(product... products);
    @Insert
    long insert(product product);

    @Update
    void updateAll(product... products);
    @Update
    void update(product product);

    @Delete
    void deleteAll(product... products);
    @Delete
    void delete(product product);
}
