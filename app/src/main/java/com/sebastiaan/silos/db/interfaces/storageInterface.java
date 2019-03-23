package com.sebastiaan.silos.db.interfaces;

import com.sebastiaan.silos.db.entities.storage;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

@Dao
public interface storageInterface extends DbIDInterface<storage>{
    @Query("SELECT * FROM storage")
    LiveData<List<storage>> getAll();

    @Query("SELECT * FROM storage where id = :id")
    LiveData<storage> findByID(long id);

    @Query("SELECT * FROM storage where productID = :productID")
    LiveData<storage> findByProduct(long productID);
}
