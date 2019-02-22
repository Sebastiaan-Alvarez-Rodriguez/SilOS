package com.sebastiaan.silos.db.interfaces;

import com.sebastiaan.silos.db.entities.DbEntityNamed;

import java.util.List;

public interface DbNamedInterface<T extends DbEntityNamed<T>> extends DbIDInterface<T> {
    List<T> findByName(String name);

    T findByNameExact(String name);
}
