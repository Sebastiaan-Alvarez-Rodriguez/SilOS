package com.sebastiaan.silos.db.interfaces;

import com.sebastiaan.silos.db.entities.DbEntity;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

@Dao
public interface DbInterface<T extends DbEntity<T>> {
    @Insert
    long[] insertAll(T[] ts);
    @Insert
    long insert(T t);

    @Update
    void updateAll(T[] ts);
    @Update
    void update(T t);

    @Delete
    void deleteAll(T[] ts);
    @Delete
    void delete(T t);

}
