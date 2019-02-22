package com.sebastiaan.silos.db.interfaces;

import com.sebastiaan.silos.db.entities.DbEntity;

public interface DbIDInterface<T extends DbEntity<T>> extends DbInterface<T> {
    T findByID(long id);
}
