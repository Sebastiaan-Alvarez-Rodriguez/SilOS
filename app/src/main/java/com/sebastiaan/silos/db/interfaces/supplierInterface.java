package com.sebastiaan.silos.db.interfaces;

import com.sebastiaan.silos.db.entities.supplier;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface supplierInterface extends DbInterface<supplier> {
    @Query("SELECT * FROM supplier")
    List<supplier> getAll();

    @Query("SELECT * FROM supplier where supplierID = :id")
    supplier findByID(int id);

    @Query("SELECT * FROM supplier where name LIKE :name")
    List<supplier> findByName(String name);

    @Query("SELECT * FROM supplier where name = :name")
    supplier findExactByName(String name);

    @Query("SELECT COUNT(*) from supplier")
    int count();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertAll(supplier... suppliers);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(supplier supplier);
}
