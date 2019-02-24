package com.sebastiaan.silos.db.interfaces;

import com.sebastiaan.silos.db.entities.DbEntity;

import java.util.List;

import androidx.lifecycle.LiveData;

public interface DbIDInterface<T extends DbEntity<T>> extends DbInterface<T> {
    LiveData<T> findByID(long id);
    LiveData<List<T>> getAll();
}
