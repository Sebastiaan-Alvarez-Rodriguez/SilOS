package com.sebastiaan.silos.db.interfaces;

import com.sebastiaan.silos.db.entities.product;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Query;

@Dao
public interface productInterface extends DbNamedInterface<product>, DbIDInterface<product> {
    @Query("SELECT * FROM product")
    List<product> getAll();

    @Query("SELECT * FROM product where productID = :id")
    product findByID(long id);

    @Query("SELECT * FROM product where name LIKE :name")
    List<product> findByName(String name);

    @Query("SELECT * FROM product where name = :name")
    product findByNameExact(String name);

    @Query("SELECT COUNT(*) from product")
    int countProducts();
}
